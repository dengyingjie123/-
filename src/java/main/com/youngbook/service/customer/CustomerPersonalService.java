package com.youngbook.service.customer;

import com.aipg.common.AipgRsp;
import com.aipg.common.InfoReq;
import com.aipg.common.InfoRsp;
import com.aipg.common.XSUtil;
import com.aipg.singleacctvalid.ValidR;
import com.aipg.singleacctvalid.ValidRet;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.config.SessionConfig;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.fdcg.FdcgCommon;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.bank.TranxServiceImpl;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.*;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IOrderDetailDao;
import com.youngbook.dao.sale.IPaymentPlanDao;
import com.youngbook.dao.sale.ISalemanGroupDao;
import com.youngbook.dao.system.IDepartmentDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.dao.system.ITokenDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.fdcg.FdcgCustomerPO;
import com.youngbook.entity.po.fdcg.FdcgCustomerQueryInfoPO;
import com.youngbook.entity.po.production.OrderDetailPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.sale.SalemanGroupPO;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.entity.po.system.TokenBizType;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("customerPersonalService")
public class CustomerPersonalService extends BaseService {

    @Autowired
    IOrderDetailDao orderDetailDao;

    @Autowired
    ICustomerAuthenticationStatusDao customerAuthenticationStatusDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    IPaymentPlanDao paymentPlanDao;

    @Autowired
    ICustomerCertificateDao customerCertificateDao;

    @Autowired
    ICustomerDistributionDao customerDistributionDao;

    @Autowired
    ILogDao logDao;

    @Autowired
    IDepartmentDao departmentDao;

    @Autowired
    ISalemanGroupDao salemanGroupDao;

    @Autowired
    ITokenDao tokenDao;

    @Autowired
    IOrderDao orderDao;

    public FdcgCustomerQueryInfoPO fdcgQueryCustomerQueryInfoPO(String customerPersonalId, Connection conn) throws Exception {

        FdcgCustomerPO fdcgCustomerPO = fdcgLoadCustomerPOByCrmCustomerPersonalId(customerPersonalId, conn);
        FdcgCustomerQueryInfoPO fdcgCustomerQueryInfoPO = fdcgQueryCustomerQueryInfoPO(fdcgCustomerPO);

        return fdcgCustomerQueryInfoPO;
    }

    public FdcgCustomerQueryInfoPO fdcgQueryCustomerQueryInfoPO(FdcgCustomerPO fdcgCustomerPO) throws Exception {
        KVObjects kvObjects = new KVObjects();
        kvObjects.addItem("accountNo", fdcgCustomerPO.getAccountNo());
        kvObjects.addItem("userName", fdcgCustomerPO.getUserName());
        kvObjects.addItem("extMark", fdcgCustomerPO.getCrmCustomerPersonalId());
        kvObjects.addItem("merchantNo", fdcgCustomerPO.getMerchantNo());
        kvObjects.addItem("orderDate", TimeUtils.getNowDateYYYYMMDD());
        kvObjects.addItem("orderNo", FdcgCommon.getId20());


        String data = kvObjects.toJSONObject().toString();

        String sign = FdcgCommon.sign(data);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("reqData", sign);

        String url = FdcgCommon.getApiUrl("thirdparty.fdcg.api.user.query.user");
        String responseDataString = HttpUtils.postRequest(url, parameters);


        FdcgCustomerQueryInfoPO fdcgCustomerQueryInfoPO = FdcgCommon.getDataInstanceResponseData(responseDataString, FdcgCustomerQueryInfoPO.class);

        return fdcgCustomerQueryInfoPO;
    }

    /**
     * 保存存管账户信息
     *
     * 处理存管账户信息时，如果系统已有开户用户的对应，则仅保留一条对应的数据
     * @param customerPO
     * @param conn
     * @return
     * @throws Exception
     */
    public FdcgCustomerPO fdcgLinkCustomer(FdcgCustomerPO customerPO, Connection conn) throws Exception {

        if (StringUtils.isEmpty(customerPO.getExtMark())) {
            MyException.newInstance("关联的客户编号为空", "fdcg_customer_id=" + customerPO.getId()).throwException();
        }


        String customerPersonalId = customerPO.getExtMark();


        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerPersonalId, conn);

        if (customerPersonalPO == null) {
            MyException.newInstance("无法找到对应的客户", "customerId=" + customerPersonalId).throwException();
        }


        /**
         * 检查是否有重复的对应关系
         */
        FdcgCustomerPO checkCustomerPO = customerPersonalDao.fdcgLoadFdcgCustomerPOByCrmCustomerPersonalId(customerPersonalPO.getId(), conn);

        if (checkCustomerPO != null) {
            return checkCustomerPO;
        }

        customerPO.setCrmCustomerPersonalId(customerPersonalPO.getId());

        customerPersonalDao.fdcgSave(customerPO, conn);


        return customerPO;
    }

    public FdcgCustomerPO fdcgLoadCustomerPOByCrmCustomerPersonalId(String customerPersonalId, Connection conn) throws Exception {
        return customerPersonalDao.fdcgLoadFdcgCustomerPOByCrmCustomerPersonalId(customerPersonalId, conn);
    }


    public FdcgCustomerPO fdcgLoadFdcgCustomerPO(String accountNo, String userName, Connection conn) throws Exception {
        return customerPersonalDao.fdcgLoadFdcgCustomerPO(accountNo, userName, conn);
    }


    /**
     * 查询所有用户-分页
     * @param customerVO
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager listPagerCustomerVO(CustomerPersonalVO customerVO, int currentPage, int showRowCount, Connection conn) throws Exception {
        Pager pager = customerPersonalDao.listPagerCustomerVO(customerVO, currentPage, showRowCount, conn);
        return pager;
    }

    public List<CustomerPersonalVO> listCustomerPersonalVO(String userId, String customerId, String referralCode, Connection conn) throws Exception {

        List<CustomerPersonalVO> list = customerPersonalDao.listCustomerPersonalVO(userId, customerId, referralCode, conn);

        return list;
    }


    /**
     * 检查客户密码情况
     * 0： 正常
     * 1： 初始密码，需要修改
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public int checkCustomerPassword(String customerId, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        if (customerPersonalPO != null) {

            if (customerPersonalPO.getPassword().equals("60a64db4f0c6ec6150cee50b0eb57cb7")) {
                return 1;
            }

        }

        return 0;
    }


    public CustomerPersonalPO loadCustomerByMobile(String mobile, Connection conn) throws Exception {
        return customerPersonalDao.loadCustomerByMobile(mobile, conn);
    }

    public CustomerPersonalPO loadCustomerByLoginName(String loginName, Connection conn) throws Exception {
        return customerPersonalDao.loadCustomerByLoginName(loginName, conn);
    }

    public boolean isCustomerCatalogConfirmed(CustomerPersonalPO customerPersonalPO) {

        return customerPersonalDao.isCustomerCatalogConfirmed(customerPersonalPO);
    }

    public CustomerPersonalPO loadByCustomerPersonalId(String customerPersonalId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(customerPersonalId)) {
            return null;
        }

        return  customerPersonalDao.loadByCustomerPersonalId(customerPersonalId, conn);
    }

    public CustomerPersonalPO loadByCustomerPersonalNumber(String personalNumber, Connection conn) throws Exception {

        if (StringUtils.isEmpty(personalNumber)) {
            return null;
        }

        return  customerPersonalDao.loadByCustomerPersonalNumber(personalNumber, conn);
    }

    public List<KVObjects> reportListPagerCustomerNew(Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("reportListPagerCustomerNew", this);
        dbSQL.initSQL();

        List<KVObjects> list = MySQLDao.search(dbSQL, conn);

        return list;
    }

    /**
     * 客户确认为合格投资者
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年5月27日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer confirmInvestor(String customerId, Connection conn) throws Exception {

        // 查询客户
        CustomerPersonalPO personalPO = this.loadByCustomerPersonalId(customerId, conn);
        if(personalPO == null) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "没有查询到客户").throwException();
        }
        // 设置为合格投资者
        personalPO.setConfirmInvestor(InvestorStatusPO.INVESTOR_STSTUS_QUALIFIED);

        return MySQLDao.insertOrUpdate(personalPO, conn);

    }

    /**
     * 获取客户总本金
     *
     * 修改：邓超
     * 内容：从 CustomerPersonalService 移动代码至此
     * 时间：2016年5月18日
     *
     * 修改：邓超
     * 内容：客户数据合并后进行 SQL 修改
     * 时间：2016年5月21日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public Double getCustomerTotalPrincipalMoney(String customerId, Connection conn) throws Exception {


        List<OrderDetailPO> detils = orderDetailDao.getOrderDetailsByCustomerId(customerId, conn);

        double money = 0;

        for (OrderDetailPO detail : detils) {

            double m = detail.getMoney();
            int status = detail.getStatus();

            switch (status) {
                case OrderStatus.Saled : money += m; break;
                case OrderStatus.Transfered : money -= m; break;
                case OrderStatus.Payback : money -= m; break;
                case OrderStatus.PaybackSomePart : money -= m; break;
                default: break;
            }
        }

        return money;

    }

    public double getCustomerTotalProfitMoney(String customerId, Connection conn ) throws Exception {
        double profitMoney = paymentPlanDao.getCustomerTotalProfitMoney(customerId, conn);
        return profitMoney;
    }


    /**
     * 判断身份证号是否被绑定过
     *
     * 传入的身份证号不需要加密
     *
     * Date: 2016-05-22 6:02:45
     * Author: leevits
     */
    public boolean hasCustomerIdNumberTaken(String customerId, String customerIdNumber, Connection conn) throws Exception {

        if (StringUtils.isEmpty(customerIdNumber)) {
            MyException.newInstance("身份证号有误，无法进行判断").throwException();
        }


        customerIdNumber = AesEncrypt.encrypt(customerIdNumber);

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from crm_customercertificate cc, crm_customerpersonal c where cc.state=0 and c.state=0 and c.id=? and cc.Number=? and c.id=cc.CustomerId").addParameter(1, customerId).addParameter(2, customerIdNumber);



        List<CustomerCertificatePO> list = MySQLDao.search(dbSQL, CustomerCertificatePO.class, conn);

        if (list != null && list.size() > 0) {
            return true;
        }

        return false;
    }


    /**
     * 获取客户未认证的数据
     *
     * @return
     */
    public CustomerBankAuthenticationPO getBankauthenticationStatus(String customerId, int authenticationStatus, Connection conn) throws Exception {
        CustomerBankAuthenticationPO bankauthentication = new CustomerBankAuthenticationPO();
        bankauthentication.setState(Config.STATE_CURRENT);
        bankauthentication.setCustomerId(customerId);
        bankauthentication.setAuthenticationStatus(authenticationStatus); //客户认证状态
        bankauthentication = MySQLDao.load(bankauthentication, CustomerBankAuthenticationPO.class, conn);
        return bankauthentication;
    }

    /**
     * 通过 Token 获取客户
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年5月19日
     *
     * @param tokenPO
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerPersonalPO getByToken(TokenPO tokenPO, Connection conn) throws Exception {
        // 获取客户 ID
        String customerId = tokenPO.getBizId();
        if(StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }
        // 获取客户
        CustomerPersonalPO personalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);
        if(personalPO == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_NOT_EXISTENT, "客户不存在").throwException();
        }

        return personalPO;
    }

    /**
     * 根据客户ID和评测类型返回一条记录
     *
     * 作者：曾威恺
     * 内容：创建代码
     * 时间：不明
     *
     * 修改：邓超
     * 内容：优化代码
     * 时间：2016年5月18日
     *
     * @param customerId
     * @param type
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerTestPO getTestScore(String customerId, int type, Connection conn) throws Exception {

        CustomerTestPO customerTestPO = new CustomerTestPO();
        customerTestPO.setState(Config.STATE_CURRENT);
        customerTestPO.setCustomerId(customerId);
        customerTestPO.setType(type);

        customerTestPO = MySQLDao.load(customerTestPO, CustomerTestPO.class, conn);
        return customerTestPO;
    }

    /**
     * 风险测评
     *
     * 修改：邓超
     * 内容：优化代码
     * 时间：2016年5月18日
     *
     * @param customerId
     * @param score
     * @param testType
     * @param conn
     * @return
     * @throws Exception
     */
    public int bearTest(String customerId, double score, int testType, Connection conn) throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        // 查询
        CustomerTestPO customerTestPO = new CustomerTestPO();
        customerTestPO.setState(Config.STATE_CURRENT);
        customerTestPO.setCustomerId(customerId);
        customerTestPO.setType(testType);
        List<CustomerTestPO> list = MySQLDao.search(customerTestPO, CustomerTestPO.class, new ArrayList<KVObject>(), queryType, conn);
        if (list != null && list.size() == 1) {
            customerTestPO = list.get(0);
        }

        // 记录分数(是否变化都要更新)
        customerTestPO.setScore(score);

        // 插入或更新
        int count = MySQLDao.insertOrUpdate(customerTestPO, conn);
        if (count != 1) {
            throw new Exception("保存测评信息失败");
        }
        return count;
    }

    /**
     * 通过临时客户注册新的个人客户
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月8日
     *
     * @param outerPO
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerPersonalPO setOuterToInner(CustomerOuterPO outerPO, String referralCode, Connection conn) throws Exception {

        // 校验参数
        if(outerPO == null) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数缺失").throwException();
        }

        // 先通过手机号查询这个客户是否存在
        CustomerPersonalPO personalPO = this.loadCustomerByMobile(outerPO.getMobile(), conn);

        if(personalPO == null) {
            personalPO = new CustomerPersonalPO();
            personalPO.setName(outerPO.getName());
            personalPO.setSex(outerPO.getSex());
            personalPO.setMobile(outerPO.getMobile());
            personalPO.setBirthday(outerPO.getBirthday());
            personalPO.setCreateTime(TimeUtils.getNow());
            personalPO.setReferralCode(referralCode);
        }

        personalPO.setReferralCode(referralCode);
        Integer count = this.insertOrUpdate(personalPO, null, conn);
        if(count != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }

        return MySQLDao.load(personalPO, CustomerPersonalPO.class, conn);
    }

    /**
     * 校验登录名和密码
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月4日
     *
     * @param loginName   String
     * @param password String
     * @return Boolean
     * @throws Exception
     */
    public CustomerPersonalPO login(String loginName, String password, Connection conn) throws Exception {

        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            MyException.newInstance("用户名和密码输入有误，请检查", "loginName="+loginName+"&password="+password).throwException();
        }



        // 构建实体
        CustomerPersonalPO customerPersonal = new CustomerPersonalPO();
        customerPersonal.setMobile(loginName);

        // 通过系统指定的字符进行二次加密
        if (password.length() != 32) {
            password = StringUtils.md5(password);
        }
        customerPersonal.setPassword(StringUtils.md5(password + Config.MD5String));
        customerPersonal.setState(Config.STATE_CURRENT);


        // 执行查询并返回
        customerPersonal = MySQLDao.load(customerPersonal, CustomerPersonalPO.class, conn);

        if (customerPersonal == null) {
            MyException.newInstance("用户登录失败，请检查输入数据是否正确", "loginName="+loginName).throwException();
        }


        if (StringUtils.isEmpty(customerPersonal.getCustomerTypeId()) || customerPersonal.getCustomerTypeId().equals("100") || customerPersonal.getCustomerTypeId().equals("4")) {
            MyException.newInstance("用户登录失败，请与客服联系", "用户名：" + loginName).throwException();
        }

        this.login(customerPersonal.getId(), conn);


        return customerPersonal;
    }

    public CustomerPersonalPO login(String customerId, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonal = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        if (customerPersonal == null) {
            MyException.newInstance("登录失败", "{'customerPersonalId':'"+customerId+"'}").throwException();
        }

        // 写入登录日志
        logDao.save("客户日志", "客户登录日志-成功", "customerId=" + customerPersonal + "&customerName="+customerPersonal.getName(), conn);

        //更改最后登录时间
        customerPersonal.setLastLoginTime(TimeUtils.getNow()); //最后登录时间
        customerPersonal.setLoginFailureCount(0);  //登录错误次数清零
        MySQLDao.insertOrUpdate(customerPersonal, conn);

        return customerPersonal;
    }

    public void loginFinish(CustomerPersonalPO customer, HttpSession session, Connection conn) throws Exception {

        CustomerPersonalVO customerPersonalVO = customerPersonalDao.loadCustomerVOByCustomerPersonalId(customer.getId(), conn);


        session.setAttribute(Config.SESSION_CUSTOMERVO_STRING, customerPersonalVO);

        session.setAttribute("loginUserId", customer.getId());
        session.setAttribute("loginUserName", customer.getLoginName());
        session.setAttribute(Config.SESSION_LOGINUSER_STRING, customer);
        session.setAttribute(Config.SESSION_LOGINUSER_TYPE, SessionConfig.LOGIN_USER_TYPE_CUSTOMER_PERSONAL);
    }

    public void logoutFinish(HttpSession session) {
        session.setAttribute("loginUserId", null);
        session.setAttribute("loginUserName", null);
        session.setAttribute(Config.SESSION_LOGINUSER_STRING, null);
        session.setAttribute(Config.SESSION_LOGINUSER_TYPE, null);
    }

    /**
     * 更新客户的错误登录次数
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月4日
     *
     * @param mobile
     * @param number
     * @param conn
     * @return Boolean
     * @throws Exception
     */
    public void updateNumberOfLogonFailed(String mobile, Integer number, Connection conn) throws Exception {

        // 构建实体
        CustomerPersonalPO customerPersonalPO = new CustomerPersonalPO();
        customerPersonalPO.setLoginName(mobile);
        customerPersonalPO.setState(Config.STATE_CURRENT);

        // 查询并更新
        CustomerPersonalPO result = MySQLDao.load(customerPersonalPO, CustomerPersonalPO.class);
        if (result != null) {
            result.setLoginFailureCount(number);
            MySQLDao.insertOrUpdate(result, conn);
        }

    }

    /**
     * 检测手机号码是否被占用
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月4日
     *
     * @param mobile
     * @return Boolean
     * @throws Exception
     */
    public Boolean isMobileRegistered(String mobile, Connection conn) throws Exception {
        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from crm_customerpersonal p where p.state = 0 and (p.LoginName =? or p.mobile=?)").addParameter(1, mobile).addParameter(2, mobile);
        List<CustomerPersonalPO> list = MySQLDao.search(dbSQL, CustomerPersonalPO.class, conn);

        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 注册客户
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月4日
     *
     * @throws Exception
     */
    public CustomerPersonalPO registerCustomer(CustomerPersonalPO customer, String referralCode, Connection conn) throws Exception {

        if (customer == null) {
            MyException.newInstance("无法获得客户信息").throwException();
        }

        /**
         * 新客户，清除Id和sid
         */
        customer.setId("");
        customer.setSid(Integer.MAX_VALUE);

        String customerCatalog = StringUtils.isEmpty(customer.getCustomerCatalogId()) ? CustomerCatalog.Confirmed : customer.getCustomerCatalogId();

        // 正式确认的用户，需要验证手机号
        if (customerCatalog.equals(CustomerCatalog.Confirmed)) {
            if (StringUtils.isEmpty(customer.getMobile())) {
                MyException.newInstance("注册失败，用户手机号不能为空").throwException();
            }

            if (customerPersonalDao.getCustomerPersonalsByMobile(customer.getMobile(), conn) != null) {
                MyException.newInstance("此手机号已被占用", "mobile="+customer.getMobile()).throwException();
            }
        }



        /**
         * 预处理推荐码
         */
        if (!StringUtils.isEmpty(referralCode)) {
            referralCode = referralCode.toUpperCase();
        }

        if (!StringUtils.isEmpty(referralCode) && StringUtils.isNumeric(referralCode)) {
            int number = Integer.parseInt(referralCode);
            referralCode = "S" + StringUtils.buildNumberString(number, 4);
        }

        // 构造实体
        customer.setReferralCode(referralCode);
        customer.setCustomerTypeId(CustomerPersonalType.NEW);

        // 密码二次加密
        if (!StringUtils.isEmpty(customer.getPassword()) && customer.getPassword().length() != 32) {
            String password = customer.getPassword();
            password = StringUtils.md5(password);
            password = StringUtils.md5(password + Config.MD5String);
            customer.setPassword(password);
        }

        // 客户来源
        if (StringUtils.isEmpty(customer.getCustomerSourceId())) {
            customer.setCustomerSourceId(Config.getSystemConfig("web.customer.source.kv.v"));
        }

        // 设置客户编号
        customer.setPersonalNumber(customerPersonalDao.getCustomerPersonalNumber(conn));
        // 设置客户默认性别
        if (StringUtils.isEmpty(customer.getSex())) {
            customer.setSex(Config.getSystemConfig("customer.sex.default"));
        }

        // 设置创建时间
        String now = TimeUtils.getNow();
        customer.setCreateTime(now);
        customer.setOperateTime(now);

        Integer count = MySQLDao.insertOrUpdate(customer, conn);
        if(count != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据库异常").throwException();
        }


        /**
         * 刚注册客户设置为未确认客户
         */
        customer.setCustomerCatalogId(CustomerCatalog.Template);

        /**
         * 客户分配，首选分给填入推荐码的销售，否则分配给sale4w
         *
         * Date: 2016-05-27 16:51:36
         * Author: leevits
         */
        UserPO user = userDao.loadByReferralCode(referralCode, conn);
        String distributeUserId = Config.getSystemConfig("web.default.saleId");
        if (user != null) {
            distributeUserId = user.getId();
        }

        UserPositionInfoPO userPositionInfo = departmentDao.getDefaultUserPositionInfo(distributeUserId, conn);

        SalemanGroupPO salemanGroup = salemanGroupDao.getDefaultSalemanGroupByUserId(distributeUserId, conn);

        customerDistributionDao.distributeCustomer(customer.getId(), distributeUserId, salemanGroup.getId(), userPositionInfo.getDepartmentId(), 0, true, conn);

        // 生成手机认证状态
        customerAuthenticationStatusDao.saveAuthenticationStatus(customer.getId(), CustomerAuthenticationStatus.AUTH_TYPE_MOBILE, conn);

        // 生成客户的资金信息
        customerPersonalDao.initCustomerMoney(customer, conn);



        return count == 1 ? customer : null;
    }

    public ReturnObject validateEmail(CustomerPersonalPO customer, String code, Connection conn) throws Exception {
        ReturnObject returnObject = new ReturnObject();
        Boolean success = validateEmailCallback(code, customer, conn);
        if (!success) {
            returnObject.setCode(ReturnObject.CODE_EXCEPTION);
            returnObject.setMessage("校验失败");
            return returnObject;
        }


        Integer count = customerAuthenticationStatusDao.saveAuthenticationStatus(customer.getId(), CustomerAuthenticationStatus.AUTH_TYPE_EMAIL, conn);

        if (count != 1) {
            returnObject.setCode(ReturnObject.CODE_EXCEPTION);
            returnObject.setMessage("数据保存失败");
            return returnObject;
        }


        return returnObject;
    }


    /**
     * 获取数据
     *
     * @param personalVO
     * @param conditions
     * @param request
     * @param user
     * @param permission
     * @return
     * @throws Exception
     */
    public Pager list(CustomerPersonalVO personalVO, List<KVObject> conditions, HttpServletRequest request, UserPO user, Permission permission) throws Exception {
        boolean hasAllUserPermission = false;
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String certificate = request.getParameter("certificate");
        String userId = user.getId();
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append("select distinct p.sid, p.id, p.state, p.operatorId, p.operateTime,p.loginName, p.name, p.mobile, p.workaddress,p.HomeAddress,p.IdentityCardAddress,p.Remark, p.email, p.birthday, p.creatTime jointime, kv.v gender, d.saleManId saleManId, u.name saleManName ");
        sbSQL.append("from crm_customerpersonal p ");
        sbSQL.append("left join crm_customerdistribution d on d.customerId = p.id and d.state = 0 ");
        sbSQL.append(" left join crm_saleman cs on cs.UserId = d.SaleManId and cs.state=0 ");
        sbSQL.append(" LEFT JOIN system_user u ON cs.UserId = u.id");
        sbSQL.append(" AND d.customerId = p.id");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" LEFT JOIN system_kv kv ON kv.k = p.sex AND kv.GroupName='Sex'");
        sbSQL.append(" where p.state = 0 ");
        if (permission.has("客户管理_个人客户管理_销售人员查看")) {
            sbSQL.append("and u.positionTypeId = 8953 ");
        }
        Pager pager = Pager.query(sbSQL.toString(), personalVO, conditions, request, queryType);
        return pager;
    }






    /**
     * 根据客户编号查询客户信息
     *
     * @param personalVO
     * @param conditions
     * @param request
     * @return
     * @throws Exception
     */
    public Pager loadCustomerById(CustomerPersonalVO personalVO, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select distinct p.sid, p.id, p.state, p.operatorId, p.operateTime,p.loginName, p.name, p.mobile, p.workaddress,p.HomeAddress,p.IdentityCardAddress,p.Remark, p.email, p.birthday, p.creatTime jointime, kv.v gender, d.saleManId saleManId, u.name saleManName ");
        sbSQL.append("from crm_customerpersonal p ");
        sbSQL.append("left join crm_customerdistribution d on d.customerId = p.id and d.state = 0 ");
        sbSQL.append(" left join crm_saleman cs on cs.UserId = d.SaleManId and cs.state=0 ");
        sbSQL.append(" LEFT JOIN system_user u ON cs.UserId = u.id");
        sbSQL.append(" AND d.customerId = p.id");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" LEFT JOIN system_kv kv ON kv.k = p.sex AND kv.GroupName='Sex'");
        sbSQL.append(" where p.state = 0 and p.id='" + Database.encodeSQL(personalVO.getId()) + "'");


        Pager pager = Pager.query(sbSQL.toString(), personalVO, conditions, request, queryType);
        return pager;


    }

    /**
     * 根据产品编号关联订单查询客户信息
     *
     * @param personalVO
     * @param productionId
     * @param conditions
     * @param request
     * @return
     * @throws Exception
     */
    public Pager loadCustomerByProductionId(CustomerPersonalVO personalVO, String productionId, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);


        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT ");
        sbSQL.append("     C.name, ");
        sbSQL.append("     C.sid, ");
        sbSQL.append("     C.LoginName, ");
        sbSQL.append("     C.id, ");
        sbSQL.append("     C.sex, ");
        sbSQL.append("     C.mobile, ");
        sbSQL.append("     C.workAddress, ");
        sbSQL.append("     C.homeAddress, ");
        sbSQL.append("     O.OrderNum, ");
        sbSQL.append("     O.saleManId, ");
        sbSQL.append("     P.`Name` productionName, ");
        sbSQL.append("     PC.`Name` productionCompositionName ");
        sbSQL.append(" FROM ");
        sbSQL.append("     crm_order O ");
        sbSQL.append(" LEFT JOIN crm_customerpersonal C ON C.id = O.CustomerId ");
        sbSQL.append(" AND C.state = 0 ");
        sbSQL.append(" LEFT JOIN crm_production P ON P.id = O.ProductionId ");
        sbSQL.append(" AND P.state = 0 ");
        sbSQL.append(" LEFT JOIN crm_productioncomposition PC ON PC.id = O.ProductionCompositionId ");
        sbSQL.append(" AND PC.state = 0 ");
        sbSQL.append(" WHERE ");
        sbSQL.append("     1 = 1 ");
        sbSQL.append(" AND O.state = 0 ");
        sbSQL.append(" AND O.customerAttribute = 0 ");
        sbSQL.append(" AND O.productionId=? ");
        sbSQL.append(" group by  C.LoginName ");

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, productionId);

        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), personalVO, conditions, request, queryType, conn);
        return pager;
    }



    /**
     * 获得分配给我的客户列表
     * <p/>
     * 修改：leevits
     * 时间：2015年8月5日 15:26:42
     *
     * @param personalVO
     * @param conditions
     * @param currentPage
     * @param showRowCount
     * @param salesman
     * @return
     * @throws Exception
     */
    public Pager listCustomrs4DistributionToMe(CustomerPersonalVO personalVO, List<KVObject> conditions, int currentPage, int showRowCount, UserPO salesman) throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String salesmanId = salesman.getId();
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append(" select * from view_customerpersonal where distributionStatus=1 and salemanId='"+salesmanId+"' ");


        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ order by _ft_.operateTime desc");
        System.out.println(sbSQL);
        Pager pager = Pager.query(sbSQL.toString(), personalVO, conditions, currentPage, showRowCount, queryType);
        return pager;
    }



    /**
     * 获得我管辖销售组的客户列表
     *
     * @param personalVO
     * @param conditions
     * @param currentPage
     * @param showRowCount
     * @param user
     * @return
     * @throws Exception
     */
    public Pager listCustomrs4DistributionToManagedSaleGroup(CustomerPersonalVO personalVO, List<KVObject> conditions, int currentPage, int showRowCount, UserPO user, Connection conn) throws Exception {
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listCustomrs4DistributionToManagedSaleGroup", "CustomerPersonalServiceSQL", CustomerPersonalService.class);

        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ order by _ft_.operateTime desc");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), personalVO, conditions, currentPage, showRowCount, queryType, conn);
        return pager;
    }

    /**
     * 修改密码
     *
     * @param personal
     * @param conn
     * @return
     * @throws Exception
     */
    public int passwordUpdate(CustomerPersonalPO personal, String userId, Connection conn) throws Exception {

        String newPassword = personal.getPassword();

        newPassword = Config.getPasswordDeal4Customer(newPassword);

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(personal.getId(), conn);

        customerPersonalPO.setPassword(newPassword);

        MySQLDao.insertOrUpdate(customerPersonalPO, userId, conn);

        return 1;
    }

    /**
     * 创建人：张舜清
     * 时间：7/14/2015
     * 内容：重置密码
     *
     * @param id
     * @param password
     * @param operatorID
     * @param conn
     * @return
     * @throws Exception
     */
    public int resetPassword(String id, String password, String operatorID, Connection conn) throws Exception {
        int count = 0;
        CustomerPersonalPO customerPersonalPO = new CustomerPersonalPO();
        customerPersonalPO.setId(id);
        customerPersonalPO.setState(Config.STATE_CURRENT);
        customerPersonalPO = MySQLDao.load(customerPersonalPO, CustomerPersonalPO.class);
        customerPersonalPO.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(customerPersonalPO, conn);
        if (count == 1) {
            customerPersonalPO.setSid(MySQLDao.getMaxSid("crm_customerpersonal", conn));
            customerPersonalPO.setState(Config.STATE_CURRENT);
            customerPersonalPO.setOperatorId(operatorID);
            customerPersonalPO.setOperateTime(TimeUtils.getNow());
            customerPersonalPO.setPassword(password);
            count = MySQLDao.insert(customerPersonalPO, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 交易平台请求的 Service，修改登录密码
     * 前提是网站的 Customer 已经登录
     * 用法：在 Action 中调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-30
     *
     * @param password
     * @param loginUser
     * @param conn
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public Integer updatePassword4W(String password, CustomerPersonalPO loginUser, Connection conn) throws Exception {
        Integer count = 0;
        // 修改状态为 update
        loginUser.setState(Config.STATE_UPDATE);
        loginUser.setOperateTime(TimeUtils.getNow());
        MySQLDao.update(loginUser, conn);
        // 创建新记录
        loginUser.setState(Config.STATE_CURRENT);
        loginUser.setOperateTime(TimeUtils.getNow());
        loginUser.setPassword(password);    // 在页面上已经 MD5Utils 加密，此时不需要再加密
        loginUser.setSid(MySQLDao.getMaxSid("crm_customerpersonal", conn));
        count = MySQLDao.insert(loginUser, conn);
        return count;
    }

//    public CustomerPersonalPO updateModern(CustomerPersonalPO customer, String userId, Connection conn) throws Exception {
//
//        return customerPersonalDao.updateModern(customer, userId, conn);
//    }

    /**
     * 后台新增或修改
     *
     * 修改：邓超
     * 内容：优化代码
     * 时间：2016年5月18日
     *
     * @param personal
     * @param userId
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(CustomerPersonalPO personal, String userId, Connection conn) throws Exception {




        if (!StringUtils.isEmpty(personal.getMobile())) {
            CustomerPersonalPO customerCheck = customerPersonalDao.getCustomerPersonalsByMobile(personal.getMobile(), conn);
            if (customerCheck != null && !customerCheck.getId().equals(personal.getId())) {
                MyException.newInstance("客户的手机号有重复，请检查", "name="+personal.getName()+"&mobile=" + personal.getMobile()).throwException();
            }
        }


        boolean isNewCustomer = false;

        if (StringUtils.isEmpty(personal.getId())) {
            isNewCustomer = true;
        }


        if (isNewCustomer) {
            String customerPersonalNumber = customerPersonalDao.getCustomerPersonalNumber(conn);
            personal.setPersonalNumber(customerPersonalNumber);
            personal.setCreateTime(TimeUtils.getNow());
            personal.setCustomerCatalogId("0");
        }
        else {
            personal.setCreateTime(null);
        }


        // 设置性别
        if (StringUtils.isEmpty(personal.getSex())) {
            personal.setSex("3");
        }


        int count = MySQLDao.insertOrUpdate(personal, userId, conn);



        // 第一次自动分配给操作的销售人员
        if (isNewCustomer) {

            UserPositionInfoPO userPositionInfo = departmentDao.getDefaultUserPositionInfo(userId, conn);

            SalemanGroupPO salemanGroup = salemanGroupDao.getDefaultSalemanGroupByUserId(userId, conn);

            if (userPositionInfo == null) {
                MyException.newInstance("无法获得用户岗位信息", "userId=" + userId).throwException();
            }


            if (salemanGroup == null) {
                MyException.newInstance("无法获得用户销售组信息", "userId=" + userId).throwException();
            }

            /**
             * 分配客户给对应销售
             */
            customerDistributionDao.distributeCustomer(personal.getId(), userId, salemanGroup.getId(), userPositionInfo.getDepartmentId(), 0, true, conn);

        }

        return count;

    }

    public int removeByCustomerPersonalId(String customerId, String userId, Connection conn) throws Exception {

        List<OrderPO> listOrderPO = orderDao.getListOrderPOByCustomerId(customerId, conn);

        if (listOrderPO != null && listOrderPO.size() > 0) {
            MyException.newInstance("客户有关联订单，无法删除", "customerId=" + customerId).throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        if (customerPersonalPO != null) {
            return MySQLDao.remove(customerPersonalPO, userId, conn);
        }

        return 0;

    }

    /**
     * 网站：读取客户资料，包括客户信息、客户资产
     *
     * @param personalVO
     * @param conditions
     * @param request
     * @return
     * @throws Exception
     */
    public Pager loadCustomerInfo4Web(String id, CustomerPersonalVO personalVO, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        Pager pager = null;
        if (id != null && !id.equals("")) {
            String sql = "SELECT p.*, m.frozenMoney frozenMoney, m.availableMoney availableMoney " +
                    "FROM crm_customerpersonal p, crm_customermoney m " +
                    "WHERE p.id = '" + Database.encodeSQL(id) + "' " +
                    "AND p.id = m.customerId " +
                    "AND p.state = 0 " +
                    "AND m.state = 0 " +
                    "limit 0,1";
            QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
            pager = Pager.query(sql, personalVO, conditions, request, queryType);
        }
        return pager;
    }


    /**
     * 网站：新增邮箱认证信息，过期时间为 1 天
     *
     * @param customerPersonalPO
     * @param emaiAddress
     * @param request
     * @param conn
     * @return String 返回客户的认证码
     * @throws Exception
     */
    public String insertEmailValidate4W(CustomerPersonalPO customerPersonalPO, String emaiAddress, HttpServletRequest request, Connection conn) throws Exception {
        Integer count = 0;
        String nowTime = TimeUtils.getNow();
        String code = IdUtils.getUUID32();
        CustomerAuthenticationCodePO customerAuthCode = new CustomerAuthenticationCodePO();
        customerAuthCode.setCustomerId(customerPersonalPO.getId());
        customerAuthCode.setCode(code); // 客户认证码通过 UUID 生成，发送链接的时候，带上 UUID
        customerAuthCode.setExpiredTime(TimeUtils.getTime(nowTime, 1, "DATE"));
        customerAuthCode.setInfo("email|" + code);
        customerAuthCode.setOperateTime(nowTime);
        customerAuthCode.setOperatorId(userDao.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn).getId());
        customerAuthCode.setId(IdUtils.getUUID32());
        customerAuthCode.setSendTime(nowTime);
        customerAuthCode.setSendType("1531");
        customerAuthCode.setSid(MySQLDao.getMaxSid("crm_customerauthenticationcode", conn));
        customerAuthCode.setState(Config.STATE_CURRENT);
        customerAuthCode.setStatus("1534");
        count = MySQLDao.insert(customerAuthCode, conn);
        if (count == 1) {
            return code;
        } else {
            throw new Exception("服务器发生错误了");
        }
    }

    /**
     * 网站：新增手机认证信息，过期时间为 1 天
     *
     * @param customerPersonalPO
     * @param mobiValidateCode
     * @param conn
     * @return String 返回客户的认证码
     * @throws Exception
     */
    public String insertMobiValidate4W(CustomerPersonalPO customerPersonalPO, Integer mobiValidateCode, Connection conn) throws Exception {
        Integer count = 0;
        String nowTime = TimeUtils.getNow();
        CustomerAuthenticationCodePO customerAuthCode = new CustomerAuthenticationCodePO();
        customerAuthCode.setCustomerId(customerPersonalPO.getId());
        customerAuthCode.setCode(mobiValidateCode + ""); // 客户认证码通过 UUID 生成，发送链接的时候，带上 UUID
        customerAuthCode.setExpiredTime(TimeUtils.getTime(nowTime, 1, "DATE"));
        customerAuthCode.setInfo("mobile|" + mobiValidateCode);
        customerAuthCode.setOperateTime(nowTime);
        customerAuthCode.setOperatorId(userDao.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn).getId());
        customerAuthCode.setId(IdUtils.getUUID32());
        customerAuthCode.setSendTime(nowTime);
        customerAuthCode.setSendType("1531");
        customerAuthCode.setSid(MySQLDao.getMaxSid("crm_customerauthenticationcode", conn));
        customerAuthCode.setState(Config.STATE_CURRENT);
        customerAuthCode.setStatus("1534");
        count = MySQLDao.insert(customerAuthCode, conn);
        if (count == 1) {
            return mobiValidateCode + "";
        } else {
            throw new Exception("服务器发生错误了");
        }
    }



    /**
     * 重载方法
     *
     * @param mobiValidateCode
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public String insertMobiValidate4W(String id, Integer mobiValidateCode, HttpServletRequest request, Connection conn) throws Exception {
        Integer count = 0;
        String nowTime = TimeUtils.getNow();
        CustomerAuthenticationCodePO customerAuthCode = new CustomerAuthenticationCodePO();
        customerAuthCode.setCustomerId(id);
        customerAuthCode.setCode(mobiValidateCode + ""); // 客户认证码通过 UUID 生成，发送链接的时候，带上 UUID
        customerAuthCode.setExpiredTime(TimeUtils.getTime(nowTime, 1, "DATE"));
        customerAuthCode.setInfo("mobile|" + mobiValidateCode);
        customerAuthCode.setOperateTime(nowTime);
        customerAuthCode.setOperatorId(userDao.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn).getId());
        customerAuthCode.setId(IdUtils.getUUID32());
        customerAuthCode.setSendTime(nowTime);
        customerAuthCode.setSendType("1531");
        customerAuthCode.setSid(MySQLDao.getMaxSid("crm_customerauthenticationcode", conn));
        customerAuthCode.setState(Config.STATE_CURRENT);
        customerAuthCode.setStatus("1534");
        count = MySQLDao.insert(customerAuthCode, conn);
        if (count == 1) {
            return mobiValidateCode + "";
        } else {
            throw new Exception("服务器发生错误了");
        }
    }

    public CustomerPersonalVO loadCustomerVOByCustomerPersonalId(String customerPersonalId, Connection conn) throws Exception {

        CustomerPersonalVO customerPersonalVO = customerPersonalDao.loadCustomerVOByCustomerPersonalId(customerPersonalId, conn);

        return customerPersonalVO;
    }

    public CustomerPersonalVO loadCustomerVOByCustomerPersonalId(String customerPersonalId) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return customerPersonalDao.loadCustomerVOByCustomerPersonalId(customerPersonalId, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    /**
     * 创建人：张舜清
     * 时间：7/29/2015
     * 内容：重载一个插入验证码的方法，因为注册没有登录的相关信息
     *
     * @param mobile
     * @param mobiValidateCode
     * @param conn
     * @return
     * @throws Exception
     */
    public String insertMobileValidate4W(String mobile, Integer mobiValidateCode, Connection conn) throws Exception {
        Integer count = 0;
        String nowTime = TimeUtils.getNow();
        CustomerAuthenticationCodePO customerAuthCode = new CustomerAuthenticationCodePO();
        customerAuthCode.setMobile(mobile);
        customerAuthCode.setCode(mobiValidateCode + ""); // 客户认证码通过 UUID 生成，发送链接的时候，带上 UUID
        customerAuthCode.setExpiredTime(TimeUtils.getTime(nowTime, 1, "DATE"));
        customerAuthCode.setInfo("mobile|" + mobiValidateCode);
        customerAuthCode.setOperateTime(nowTime);
        customerAuthCode.setOperatorId(userDao.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn).getId());
        customerAuthCode.setId(IdUtils.getUUID32());
        customerAuthCode.setSendTime(nowTime);
        customerAuthCode.setSendType("1531");
        customerAuthCode.setSid(MySQLDao.getMaxSid("crm_customerauthenticationcode", conn));
        customerAuthCode.setState(Config.STATE_CURRENT);
        customerAuthCode.setStatus("1534");
        count = MySQLDao.insert(customerAuthCode, conn);
        if (count == 1) {
            return mobiValidateCode + "";
        } else {
            throw new Exception("服务器发生错误了");
        }
    }



    /**
     * 网站：通过 Customer ID 获取 Customer
     *
     * 修改人：张舜清
     * 时间：7/10/2015
     * 内容：增加异常处理
     *
     * @param id
     * @param connection
     * @return
     * @throws Exception
     */
    public CustomerPersonalPO loadCustomerById4W(String id, Connection connection) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from crm_customerpersonal customer where customer.state = 0 and customer.id = ?");

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, id, parameters);

        List<CustomerPersonalPO> customers = MySQLDao.search(sbSQL.toString(), parameters, CustomerPersonalPO.class, null, connection);
        if (customers != null && customers.size() == 1) {
            return customers.get(0);
        }

        return null;
    }

    /**
     * 交易平台请求的 Service，保存用户的安全问题和答案
     * 前提是网站的 Customer 已经登录
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-26
     *
     * @param loginUser
     * @param conn
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public Integer saveProtectionQuestion(List<String> questions, List<String> answers, CustomerPersonalPO loginUser, Connection conn) throws Exception {
        String sql = "SELECT * FROM crm_customersafetyqa qa WHERE qa.state = 0 AND qa.customerId = '" + Database.encodeSQL(loginUser.getId()) + "' ";
        List<CustomerSafetyQAPO> list = MySQLDao.query(sql, CustomerSafetyQAPO.class, new ArrayList<KVObject>(), conn);
        for (CustomerSafetyQAPO po : list) {
            po.setState(Config.STATE_UPDATE);
            po.setOperateTime(TimeUtils.getNow());
            MySQLDao.update(po, conn);
        }
        Integer count = 0;
        if (questions.size() == 3 && answers.size() == 3) {
            CustomerSafetyQAPO safetyQA = new CustomerSafetyQAPO();
            // 基本表信息
            safetyQA.setState(Config.STATE_CURRENT);
            safetyQA.setId(IdUtils.getUUID32());
            safetyQA.setOperateTime(TimeUtils.getNow());
            safetyQA.setSid(MySQLDao.getMaxSid("crm_customersafetyqa", conn));
            safetyQA.setOperatorId(userDao.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn).getId());
            safetyQA.setCustomerId(loginUser.getId());
            // 问题和答案
            safetyQA.setQ1Id(questions.get(0));
            safetyQA.setQ2Id(questions.get(1));
            safetyQA.setQ3Id(questions.get(2));
            safetyQA.setA1(answers.get(0));
            safetyQA.setA2(answers.get(1));
            safetyQA.setA3(answers.get(2));
            MySQLDao.insert(safetyQA, conn);
        } else {
            throw new Exception("问题与答案的数目不相同");
        }
        return count;
    }

    public CustomerPersonalPO changeMobileByPassword(String customerId, String newMobile, String password, String checkCode, String userId, Connection conn) throws Exception {


        tokenDao.checkAndDestroyToken(checkCode, newMobile, TokenBizType.MobileCode, "0.0.0.0", conn);


        CustomerPersonalPO customerPersonalPO = loadByCustomerPersonalId(customerId, conn);


        StringUtils.checkIsEmpty(password, "客户密码");

        password = Config.getPasswordDeal4Customer(password);

        if (!customerPersonalPO.getPassword().equals(password)) {
            MyException.newInstance("用户密码输入错误", "customerId="+customerId).throwException();
        }

        customerPersonalPO.setMobile(newMobile);

        MySQLDao.insertOrUpdate(customerPersonalPO, conn);


        return customerPersonalPO;
    }

    /**
     * 交易平台请求的 Service，修改用户的手机号码
     * 前提是网站的 Customer 已经登录
     * 用法：在 Action 中调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-28
     *
     * @param mobile
     * @param loginUser
     * @param conn
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public Integer updateMobile(String mobile, CustomerPersonalPO loginUser, Connection conn) throws Exception {
        Integer count = 0;
        // 修改原有的数据，状态设为 update
        String sql = "select * from crm_customerpersonal p where p.state = 0 and p.id = '" + Database.encodeSQL(loginUser.getId()) + "'";
        List<CustomerPersonalPO> list = MySQLDao.query(sql, CustomerPersonalPO.class, new ArrayList<KVObject>(), conn);
        if (list.size() == 1) {
            loginUser = list.get(0);
            loginUser.setState(Config.STATE_UPDATE);
            loginUser.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.update(loginUser);
            if (count == 1) {
                // 在原有数据的基础上修改手机号码，新增一条新的记录
                loginUser.setSid(MySQLDao.getMaxSid("crm_customerpersonal", conn));
                loginUser.setMobile(mobile);
                loginUser.setLoginName(mobile);
                loginUser.setState(Config.STATE_CURRENT);
                loginUser.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(loginUser);
            }
        }
        return count;
    }

    /**
     * 交易平台请求的 Service，修改用户的电子邮箱
     * 前提是网站的 Customer 已经登录
     * 用法：在 Action 中调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-30
     *
     * @param email
     * @param loginUser
     * @param conn
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public Integer updateEmail(String email, CustomerPersonalPO loginUser, Connection conn) throws Exception {
        Integer count = 0;
        // 修改原有的数据，状态设为 update
        String sql = "select * from crm_customerpersonal p where p.state = 0 and p.id = '" + Database.encodeSQL(loginUser.getId()) + "'";
        List<CustomerPersonalPO> list = MySQLDao.query(sql, CustomerPersonalPO.class, new ArrayList<KVObject>(), conn);
        if (list.size() == 1) {
            loginUser = list.get(0);
            loginUser.setState(Config.STATE_UPDATE);
            loginUser.setOperateTime(TimeUtils.getNow());
            MySQLDao.update(loginUser);
            // 在原有数据的基础上修改电子邮箱，新增一条新的记录
            loginUser.setSid(MySQLDao.getMaxSid("crm_customerpersonal", conn));
            loginUser.setEmail(email);
            loginUser.setState(Config.STATE_CURRENT);
            loginUser.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(loginUser);
        }
        return count;
    }

    /**
     * 交易平台请求的 Service，验证客户认证邮箱时，从邮箱点击的链接进行校验
     * 前提是网站的 Customer 已经登录
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-30
     *
     * @param code
     * @param loginUser
     * @param conn
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public Boolean validateEmailCallback(String code, CustomerPersonalPO loginUser, Connection conn) throws Exception {
        String sql = "SELECT * FROM crm_customerauthenticationcode code " +
                "WHERE code.state = 0 AND " +
                "code.code = '" + Database.encodeSQL(code) + "' AND " +
                "code.customerId = '" + Database.encodeSQL(loginUser.getId()) + "'";
        List<CustomerAuthenticationCodePO> list = MySQLDao.query(sql, CustomerAuthenticationCodePO.class, new ArrayList<KVObject>(), conn);
        return list.size() == 1 ? true : false;
    }


    /**
     * 通联支付新版实名认证
     *
     * @param customerName
     * @param customerIdNumber
     * @param bankCode
     * @param bankNumber
     * @return 参看 CustomerPersonalValidateIdentityCode
     * @throws Exception
     */
    public ReturnObject validateIdentityV2ByAllinpay(String customerName, String customerIdNumber, String bankCode, String bankNumber) throws Exception {

        if (StringUtils.isEmpty(customerName)) {
            MyException.newInstance("客户名称验证失败").throwException();
        }

        if (StringUtils.isEmpty(customerIdNumber)) {
            MyException.newInstance("客户身份证号码验证失败").throwException();
        }

        if (StringUtils.isEmpty(bankCode)) {
            MyException.newInstance("银行编号验证失败").throwException();
        }

        if (StringUtils.isEmpty(bankNumber)) {
            MyException.newInstance("银行卡号验证失败").throwException();
        }

        String url = Config.getSystemConfig("bank.pay.allinpay.auth.url");


        boolean isTLTFront = false;

        InfoReq info = new InfoReq();
        info.setTRX_CODE("211003");
        info.setVERSION("03");
        info.setDATA_TYPE("02");
        info.setLEVEL("5");
        info.setUSER_NAME(Config.getSystemConfig("bank.pay.allinpay.userName"));
        info.setUSER_PASS(Config.getSystemConfig("bank.pay.allinpay.password"));
        info.setREQ_SN(String.valueOf(TimeUtils.getSimpleTimestamp()));

        ValidR requestBody = new ValidR();
        requestBody.setMERCHANT_ID(Config.getSystemConfig("bank.pay.allinpay.daifu.merchantId"));
        requestBody.setSUBMIT_TIME(TimeUtils.getNow(TimeUtils.Format_YYYYMMDDHHMMSS));
        requestBody.setBANK_CODE(bankCode);
        requestBody.setACCOUNT_NO(bankNumber);
        requestBody.setACCOUNT_NAME(customerName);
        requestBody.setACCOUNT_PROP("0");
        requestBody.setID_TYPE("0");
        requestBody.setID(customerIdNumber);

        TranxServiceImpl tranxService = new TranxServiceImpl();

        String responseXml = tranxService.sendRequest(info, requestBody, isTLTFront, url);

        KVObject response = paresvalidateIdentityResponse(responseXml);

        String code = response.getKey().toString();
        String message = response.getValue().toString();

        ReturnObject returnObject = new ReturnObject();
        returnObject.setCode(ReturnObject.CODE_SUCCESS);
        returnObject.setMessage(message);

        int returnCode = CustomerPersonalValidateIdentityCode.UNKNOW;
        if (code.equals("0000")) {
            returnCode = CustomerPersonalValidateIdentityCode.PASS;
        }

        // 3009 无此账户
        if (code.equals("3009")) {
            returnCode = CustomerPersonalValidateIdentityCode.ERROR_BANK_NUMBER;
        }

        // 3031 账号户名不符
        else if (code.equals("3031")) {
            returnCode = CustomerPersonalValidateIdentityCode.ERROR_CUSTOMER_NAME;
        }

        returnObject.setReturnValue(returnCode);

        return returnObject;
    }

    private KVObject paresvalidateIdentityResponse(String responseXml) throws Exception {
        KVObject response = new KVObject();

        AipgRsp aipgrsp = XSUtil.xmlRsp(responseXml);
        InfoRsp info = aipgrsp.getINFO();

        ValidRet validRet = (ValidRet) aipgrsp.getTrxData().get(0);


        response.setKey(validRet.getRET_CODE());
        response.setValue(validRet.getERR_MSG());

        return response;

    }

    /**
     * 获取客户的常用手机号码
     *
     * 作者：张舜清
     * 内容：创建代码
     * 时间：2015年7月23日
     *
     * 修改：邓超
     * 内容：优化代码
     * 时间：2016年5月18日
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public String getMobile(String id, Connection conn) throws Exception {
        CustomerPersonalPO personalPO = customerPersonalDao.loadByCustomerPersonalId(id, conn);
        return personalPO.getMobile();
    }

    /**
     * 网站：获取客户最后一次登录时间
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public LogPO getLastLoginTime(String customerId, Connection conn) throws Exception {
        LogPO po = null;
        String sql = "select * from system_log l where l.operatorId = '' order by l.operateTime desc limit 0,1";
        List<LogPO> list = MySQLDao.query(sql, LogPO.class, new ArrayList<KVObject>(), conn);
        if (list.size() == 1) {
            po = list.get(0);
        }
        return po;
    }



    /**
     * 更新客户的真实姓名
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-12-01
     *
     * @param id
     * @param realName
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer updateCustomerRealName(String id, String realName, Connection conn) throws Exception {
        CustomerPersonalPO customerPersonal = new CustomerPersonalPO();
        customerPersonal.setId(id);
        customerPersonal.setState(Config.STATE_CURRENT);
        customerPersonal = MySQLDao.load(customerPersonal, CustomerPersonalPO.class, conn);

        // 更新用户信息
        customerPersonal.setState(Config.STATE_UPDATE);
        customerPersonal.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        customerPersonal.setOperateTime(TimeUtils.getNow());
        Integer count = MySQLDao.update(customerPersonal, conn);

        // 新增加用户信息
        if (count == 1) {
            customerPersonal.setSid(MySQLDao.getMaxSid("crm_customerpersonal", conn));
            customerPersonal.setState(Config.STATE_CURRENT);
            customerPersonal.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            customerPersonal.setOperateTime(TimeUtils.getNow());
            customerPersonal.setName(realName);
            count = MySQLDao.insert(customerPersonal, conn);
        }

        return count;
    }


    /**
     * 创建人：方斌杰
     * 时间：8/07/2015
     * 内容： 新增或修改认证状态
     *
     * @param personal
     * @param conn
     * @return
     * @throws Exception
     */
    public int saveCustomerAuthenticationStatus(CustomerPersonalPO personal, Connection conn) throws Exception {
        // 新增或修改认证状态

        CustomerAuthenticationStatusPO statusPO = customerAuthenticationStatusDao.loadByCustomerId(personal.getId(), conn);
        if (statusPO == null) {
            statusPO = new CustomerAuthenticationStatusPO();
            statusPO.setCustomerId(personal.getId());
        }
        //设置手机认证状态为已认证
        statusPO.setMobileStatus(1);
        statusPO.setMobileTime(TimeUtils.getNow());
        // 网站用户
        UserPO user = userDao.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);
        int count = customerAuthenticationStatusDao.insertOrUpdate(statusPO, user, conn);
        return count;
    }



    /**
     * 添加登陆用户的会话
     * 周海鸿
     * 2015-8-12
     *
     * @param request
     */
    public void setLoginUser(CustomerPersonalPO personal, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("loginUserId", personal.getId());
        session.setAttribute("loginUserName", personal.getLoginName());
        session.setAttribute(Config.SESSION_LOGINUSER_STRING, personal);
    }

    /**
     * 1、判断数据有效性
     * 2、创建一个customerPersonalpo 对象
     * 3、设置用户id
     * 5、根据用户id获取用户信息
     * 6、判断是否设置交易密码
     * 海鸿
     * 时间2015-8-12
     * 内容:用来判断是否设置交易密码 跳转页面：w/modules/customer/account.jsp
     *
     * @return 返回
     * @throws Exception
     */
    public boolean isTransactionPassword(String id, Connection conn) throws Exception {
        //判断数据有效性
        if (StringUtils.isEmpty(id)) {
            throw new Exception("用户验证信息获取失败 id is null");
        }

        //创建一个customerPersonalpo 对象
        CustomerPersonalPO customerPersonalPO = new CustomerPersonalPO();

        customerPersonalPO.setId(id);//设置用户id
        customerPersonalPO.setState(Config.STATE_CURRENT);
        //根据id获取用户信息
        customerPersonalPO = MySQLDao.load(customerPersonalPO, CustomerPersonalPO.class, conn);

        //判断用户是否有设置交易密码
        if (StringUtils.isEmpty(customerPersonalPO.getTransactionPassword())) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * HWBANKSAPP-22-创建接口检查用户是否进行了实名验证
     * 参数: customerId，进行两重判定，有certificate记录，客户name不为空，才返回组装好的JSONObject
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public JSONObject isCertificatedCustomer(String customerId, Connection conn) throws Exception {
        // 判断数据有效性
        if (StringUtils.isEmpty(customerId)) {
            throw new Exception("用户验证信息获取失败，customerId为空");
        }
        // 设置certificate对象
        CustomerCertificatePO certificate = new CustomerCertificatePO();
        certificate.setCustomerId(customerId);
        certificate.setState(Config.STATE_CURRENT);
        // load记录
        certificate = MySQLDao.load(certificate, CustomerCertificatePO.class, conn);
        if (certificate == null) {
            return null;
        }

        //设置customerPersonal对象
        CustomerPersonalPO customer = new CustomerPersonalPO();
        customer.setId(customerId);
        customer.setState(Config.STATE_CURRENT);
        // load记录
        customer = MySQLDao.load(customer, CustomerPersonalPO.class, conn);
        if (customer == null ) {
            return null;
        }

        JSONObject jo = new JSONObject();
        jo.put("customerId", customer.getId());
        jo.put("name", customer.getName());
        jo.put("mobile", customer.getMobile());
        // 掩盖敏感数据：身份证号
        String number = AesEncrypt.decrypt(certificate.getNumber());
        String numberMasked = StringUtils.replaceToStars(number, 4, 4);
        jo.put("number", numberMasked);

        return jo;
    }



    /**
     * 1、判断数据有效性
     * 2、创建custoemrCertificatepo对象
     * 3、设置用户id
     * 5、根据用户id获取用户实名认证
     * 6、判断是否设置实名认证
     * 周海鸿
     * 2015-8-12
     * 根据用户id判断是否有实名验证
     *
     * @return
     * @throws Exception
     */
    public boolean isCertificate(String id, Connection conn) throws Exception {
        //判断数据有效性
        if (StringUtils.isEmpty(id)) {
            throw new Exception("用户验证信息获取失败 id is null");
        }
        //创建custoemrCertificatepo对象
        CustomerCertificatePO customerCertificatePO = new CustomerCertificatePO();

        customerCertificatePO.setCustomerId(id);//s设置用户id
        customerCertificatePO.setState(Config.STATE_CURRENT);
        //获取用户实名认证信息
        customerCertificatePO = MySQLDao.load(customerCertificatePO, CustomerCertificatePO.class, conn);

        //判断用户是否有设置实名认证信息
        if (null == customerCertificatePO) {
            return false;
        } else {
            return true;
        }


    }

    /**
     * 检测客户是否有银行卡
     *
     * 修改：邓超
     * 内容：优化代码
     * 时间：2016年5月18日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public boolean hasBankCard(String customerId, Connection conn) throws Exception {

        // 创建一个客户银行卡对象
        CustomerAccountPO customerAccount = new CustomerAccountPO();
        customerAccount.setCustomerId(customerId);
        customerAccount.setState(Config.STATE_CURRENT);

        //查询数据
        List<CustomerAccountPO> customerAccountPOList = MySQLDao.query(customerAccount, CustomerAccountPO.class, conn);

        //数据大小 ==  0
        if (customerAccountPOList.size() == 0) {
            return false;
        }

        return true;
    }



    /**
     * 创建人：姚章鹏
     * 时间：2015年9月8日16:47:42
     * 内容：判断是否实名认证
     */
    public List<CustomerCertificatePO> isCertificate(CustomerPersonalPO customerPersonal, Connection conn) throws Exception {
        //判断是否有实名认证

        CustomerCertificatePO customerCertificate = new CustomerCertificatePO();
        customerCertificate.setState(Config.STATE_CURRENT);
        customerCertificate.setCustomerId(customerPersonal.getId());
        List<CustomerCertificatePO> customerCertificatePOList = MySQLDao.query(customerCertificate, CustomerCertificatePO.class, conn);

        return customerCertificatePOList;
    }





    /**
     * 更改并添加银行卡认证表
     *
     * @param bankauthentication
     * @param conn
     * @return
     * @throws Exception
     */
    public int updateAndinsertBankauthentication(CustomerBankAuthenticationPO bankauthentication, Connection conn) throws Exception {
        int count = 0;
        bankauthentication.setState(Config.STATE_UPDATE);
        MySQLDao.update(bankauthentication, conn);
        bankauthentication.setSid(MySQLDao.getMaxSid("crm_customerbankauthentication", conn));
        bankauthentication.setState(Config.STATE_CURRENT);
        bankauthentication.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        bankauthentication.setOperateTime(TimeUtils.getNow());
        bankauthentication.setAuthenticationStatus(Config4Status.STATUS_CUSTOMER_BANKCARD_SUCCESS); //客户手动认证成功
        count = MySQLDao.insert(bankauthentication, conn);

        return count;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月16日10:46:44
     * 内容：根据客户的id查询出对应兑付数据列表
     *
     * @param paymentPlanVO
     * @param customerId
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager paymentPlanQuery(PaymentPlanVO paymentPlanVO, List<KVObject> conditions, String customerId, HttpServletRequest request, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //组装SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append("  SELECT DISTINCT cp.sid sid ,cp.id id ,cp.type TYPE,cp2.id AS productId,cc.id AS CustomerId,cc.loginName,cc.Name customerName,cp.orderId orderId, ");
        sql.append("   cp2.Name productName,cp.paymentMoney paymentMoney,cp.paymentTime paymentTime,cp.paiedPaymentTime paiedPaymentTime,cp.totalInstallment totalInstallment, ");
        sql.append("   cp.currentInstallment currentInstallment,kv.v statusName ,cp.status search_status ,cp.status STATUS ,cp.totalPaymentMoney totalPaymentMoney, ");
        sql.append("   cp.totalPaymentPrincipalMoney totalPaymentPrincipalMoney , cp.totalProfitMoney totalProfitMoney, cp.paiedPrincipalMoney paiedPrincipalMoney, ");
        sql.append("   cp.paiedProfitMoney paiedProfitMoney,cp.comment, o.orderNum orderName  FROM core_paymentplan cp");
        sql.append("  LEFT JOIN crm_customerpersonal cc ON cc.id = cp.CustomerId ");
        sql.append("  LEFT JOIN crm_production cp2 ON cp2.id=cp.ProductId ");
        sql.append("  LEFT JOIN system_kv kv ON kv.K=cp.Status");
        sql.append("  LEFT JOIN crm_order o ON o.id = cp.orderId ");
        sql.append("  WHERE kv.K=cp.Status AND ");
        sql.append("   kv.GroupName ='Sale_PaymentPlan_Status' ");
        sql.append("   and cp.State=0 ");
        sql.append("   and cc.State=0 ");
        sql.append("   and cp2.state=0 ");
        sql.append("   and o.state = 0");
        sql.append("   and cp.PaymentTime is not null ");
        sql.append("   and cc.id='" + Database.encodeSQL(customerId) + "' ");
        //查询数据
        Pager pager = Pager.query(sql.toString(), paymentPlanVO, conditions, request, queryType);
        List<IJsonable> data = pager.getData();
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        for (IJsonable dataJson : data) {
            double totalPaymentMoney = ((PaymentPlanVO) dataJson).getTotalPaymentMoney(); //应兑付总金额
            double tPaymentMoney = Double.valueOf(dcmFmt.format(totalPaymentMoney));
            ((PaymentPlanVO) dataJson).setTotalPaymentMoney(tPaymentMoney);

            double totalProfitMoney = ((PaymentPlanVO) dataJson).getTotalProfitMoney(); //应兑付收益总金额
            double tTotalProfitMoney = Double.valueOf(dcmFmt.format(totalProfitMoney));
            ((PaymentPlanVO) dataJson).setTotalProfitMoney(tTotalProfitMoney);
        }
        pager.setData(data);

        return pager;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月16日13:49:54
     * 内容：根据客户的id查询出对应产品数据列表
     *
     * @param productionVO
     * @param conditions
     * @param customerId
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager productionQuery(ProductionVO productionVO, List<KVObject> conditions, String customerId, HttpServletRequest request, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("     product.sid, ");
        sqlDB.append("     product.id, ");
        sqlDB.append("     product.productHomeId, ");
        sqlDB.append("     product.InvestTerm, ");
        sqlDB.append("     product.InvestTermView, ");
        sqlDB.append("     product.paymentProcess, ");
        sqlDB.append("     product.appointmentProcess, ");
        sqlDB.append("     product.saleProcess, ");
        sqlDB.append("     product.productId, ");
        sqlDB.append("     product.size, ");
        sqlDB.append("     product.contractCopies, ");
        sqlDB.append("     product. NAME, ");
        sqlDB.append("     product.startTime, ");
        sqlDB.append("     product.stopTime, ");
        sqlDB.append("     product.ValueDate, ");
        sqlDB.append("     product.ExpiringDate, ");
        sqlDB.append("     product.InterestDate, ");
        sqlDB.append("     product.AppointmentMoney, ");
        sqlDB.append("     product.SaleMoney, ");
        sqlDB.append("     product.WebsiteDisplayName, ");
        sqlDB.append("     CASE product.`Status` ");
        sqlDB.append(" WHEN 0 THEN ");
        sqlDB.append("     '草稿' ");
        sqlDB.append(" WHEN 1 THEN ");
        sqlDB.append("     '发布待售' ");
        sqlDB.append(" WHEN 2 THEN ");
        sqlDB.append("     '在售' ");
        sqlDB.append(" WHEN 3 THEN ");
        sqlDB.append("     '暂停' ");
        sqlDB.append(" WHEN 4 THEN ");
        sqlDB.append("     '售完' ");
        sqlDB.append(" ELSE ");
        sqlDB.append("     '作废' ");
        sqlDB.append(" END STATUS, ");
        sqlDB.append("  product.interestType, ");
        sqlDB.append("  product.interestCycle, ");
        sqlDB.append("  CASE interestUnit ");
        sqlDB.append(" WHEN 0 THEN ");
        sqlDB.append("     '按日' ");
        sqlDB.append(" WHEN 1 THEN ");
        sqlDB.append("     '按月' ");
        sqlDB.append(" WHEN 2 THEN ");
        sqlDB.append("     '按年' ");
        sqlDB.append(" END interestUnit, ");
        sqlDB.append("  product.interestTimes, ");
        sqlDB.append("  home.ProductionName ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_production product, ");
        sqlDB.append("     crm_productionhome home, ");
        sqlDB.append("     crm_order o ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1 ");
        sqlDB.append(" AND product.state = 0 ");
        sqlDB.append(" AND home.State = 0 ");
        sqlDB.append(" AND product.productHomeId = home.Id ");
        sqlDB.append(" AND o.ProductionId = product.id ");
        sqlDB.append(" AND o.state = 0 ");
        sqlDB.append(" AND o.CustomerId = '" + customerId + "' ");
        Pager pager = Pager.query(sqlDB.toString(), productionVO, conditions, request, queryType, conn);
        return pager;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月16日14:50:37
     * 内容：根据客户id查询出对应订单数据列表
     *
     * @param orderVO
     * @param conditions
     * @param customerId
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager orderQuery(OrderVO orderVO, List<KVObject> conditions, String customerId, HttpServletRequest request, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("     O.id, ");
        sqlDB.append("     O.sid, ");
        sqlDB.append("     O.OrderNum, ");
        sqlDB.append("     C.LoginName, ");
        sqlDB.append("     C. NAME customerName, ");
        sqlDB.append("     P.`Name` productionName, ");
        sqlDB.append("     PC.`Name` productionCompositionName, ");
        sqlDB.append("     O.createTime, ");
        sqlDB.append("     O.money, ");
        sqlDB.append("     O.Description, ");
        sqlDB.append("     O. STATUS STATUS, ");
        sqlDB.append("     O.customerAttribute, ");
        sqlDB.append("     O.referralCode ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_order O ");
        sqlDB.append(" LEFT JOIN crm_customerpersonal C ON C.id = O.CustomerId ");
        sqlDB.append(" AND C.state = 0 ");
        sqlDB.append(" LEFT JOIN crm_production P ON P.id = O.ProductionId ");
        sqlDB.append(" AND P.state = 0 ");
        sqlDB.append(" LEFT JOIN crm_productioncomposition PC ON PC.id = O.ProductionCompositionId ");
        sqlDB.append(" AND PC.state = 0 ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1 ");
        sqlDB.append(" AND O.state = 0 ");
        sqlDB.append(" AND O.customerAttribute = 0 ");
        sqlDB.append(" AND c.id='" + customerId + "' ");
        Pager pager = Pager.query(sqlDB.toString(), orderVO, conditions, request, queryType, conn);
        return pager;
    }

    /**
     * 根据用户手机与密码判断用户是否是老用户
     *
     * @param mobile
     * @return 是 返回 true 否 返回false；
     */
    public boolean isOldCustomer(String mobile) throws Exception {
        //获取默认密码MD5字符串
        String defMD5 = Config.getSystemConfig("default.Password.Md5");

        //创建SQL
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append(" select * from crm_customerpersonal cc ");
        sbSQL.append(" where 1=1 ");
        sbSQL.append(" and (cc.Mobile = '" + mobile + "' or cc.LoginName='" + mobile + "') ");
        sbSQL.append(" and cc.`Password`='" + defMD5 + "' and cc.state = " + Config.STATE_CURRENT + " ");

        //判断是否存在数据


        if (MySQLDao.query(sbSQL.toString()).size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否设置交易密码
     * 作者:quan.zeng
     * 内容：创建代码
     * 时间：2015-12-9 15：40
     *
     * @param customerId:客户id
     * @return 是 返回 boolean；
     */
    public boolean isSetTransactionPassword(String customerId, Connection conn) throws Exception {
        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);
        if (customerPersonalPO != null) {
            String transactionPwd = customerPersonalPO.getTransactionPassword();
            if (!StringUtils.isEmpty(transactionPwd)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置或修改交易密码
     * 作者:quan.zeng
     * 内容：创建代码
     * 时间：2015-12-9 16：50
     *
     * @param customer :客户id
     * @param transactionPassword:交易密码
     * @return 是 返回 int；
     */
    public int setOrModifyTransactionPassword(CustomerPersonalPO customer, String transactionPassword, Connection conn) throws Exception {
        if (customer == null || StringUtils.isEmpty(customer.getId())) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "账户不存在").throwException();
        }
        customer.setTransactionPassword(StringUtils.md5(transactionPassword + Config.MD5String));
        customer.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        customer.setOperateTime(TimeUtils.getNow());
        int count = MySQLDao.insertOrUpdate(customer, conn);

        return count;
    }

    /**
     * 修改登录密码
     * 作者:quan.zeng
     * 内容：创建代码
     * 时间：2015-12-9 16：50
     *
     * @param mobile:客户手机号码
     * @param password:交易密码
     * @return 是 返回 int；
     * <p/>
     * 修改代码
     * 作者：quan.zeng
     * 时间：2015-12-15
     * 内容：使用手机号修改
     */
    public int modifyLoginPassword(String mobile, String password, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonalPO = new CustomerPersonalPO();
        customerPersonalPO.setMobile(mobile);
        customerPersonalPO.setState(0);

        CustomerPersonalPO po = MySQLDao.load(customerPersonalPO, CustomerPersonalPO.class, conn);

        if (po == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "账户不存在").throwException();
        }

        po.setState(0);
        po.setPassword(StringUtils.md5(password + Config.MD5String));
        po.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        po.setOperateTime(TimeUtils.getNow());
        int count = MySQLDao.insertOrUpdate(po, conn);

        logDao.save("客户日志", "CustomerPersonalService.modifyLoginPassword()", "重置密码成功，用户手机号【"+mobile+"】", conn);

        return count;
    }

    /**
     * 修改手机号码
     * <p/>
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月21日
     *
     * @param mobile
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public int resetMobile(String mobile, String customerId, Connection conn) throws Exception {
        int count = 0;
        CustomerPersonalPO po = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);
        if (po == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "账户不存在").throwException();
        }
        po.setId(customerId);
        po.setState(0);
        po.setMobile(mobile);
        po.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        po.setOperateTime(TimeUtils.getNow());
        count = MySQLDao.insertOrUpdate(po, conn);
        return count;
    }

    /**
     * 修改密码
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月21日
     *
     * @param newPassword
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public int resetMobilePassword(CustomerPersonalPO po, String newPassword, String customerId, Connection conn) throws Exception {
        int count = 0;
        //验证新旧密码
        po.setId(customerId);
        po.setState(0);
        po.setPassword(StringUtils.md5(newPassword + Config.MD5String));
        po.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        po.setOperateTime(TimeUtils.getNow());
        count = MySQLDao.insertOrUpdate(po, conn);
        return count;
    }

    /**
     * 添加手势密码
     *
     * 作者：邓超
     * 内容：优化代码
     * 时间：2016年5月18日
     *
     * @param customerId
     * @param gesturePassword
     * @param status
     * @param conn
     * @return
     * @throws Exception
     */
    public int setGesturePassword(String customerId, String gesturePassword, Integer status, Connection conn) throws Exception {

        // 尝试加载客户
        CustomerPersonalPO po = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        // 如果不存在，抛异常
        if (po == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "账户不存在").throwException();
        }

        // 存在：添加或修改手势密码
        po.setId(customerId);
        po.setGesturePassword(gesturePassword); // 手势密码
        po.setGesturePasswordStatus(status);
        return MySQLDao.insertOrUpdate(po, conn);
    }


    /**
     * 查询客户是否启用手势密码
     *
     * 修改：邓超
     * 内容：优化代码
     * 时间：2016年5月18日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public int getGesturePassword(String customerId, Connection conn) throws Exception {
        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);
        return customerPersonalPO.getGesturePasswordStatus();
    }


    public List<CustomerPersonalPO> getCustomerPersonalAssignedByUserId(String userId, Connection conn) throws Exception {
        return customerPersonalDao.getCustomerPersonalAssignedByUserId(userId, conn);
    }


    /**
     * @description  根据name查询CustomerPersonalPO
     * 
     * @author
     * 
     * @date 2018/12/14 9:37
     * @param name
     * @param conn
     * @return com.youngbook.entity.po.customer.CustomerPersonalPO
     * @throws Exception
     */
    public CustomerPersonalPO loadCustomerByName(String name, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonalPO = new CustomerPersonalPO();
        customerPersonalPO.setName(name);

        customerPersonalPO = customerPersonalDao.loadCustomerPO(customerPersonalPO, conn);


        return customerPersonalPO;
    }



    /**
     * @description  根据name查询CustomerPersonalPO
     *
     * @author
     *
     * @date 2018/12/14 9:37
     * @param idCardNumber
     * @param conn
     * @return com.youngbook.entity.po.customer.CustomerPersonalPO
     * @throws Exception
     */
    public int loadCustomerByIdCardNumber(String idCardNumber, Connection conn) throws Exception {

        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("CWQIP5DA");
        databaseSQL.addParameter4All("idCardNumber", AesEncrypt.encrypt(idCardNumber));
        databaseSQL.initSQL();




        List<CustomerCertificatePO> search = MySQLDao.search(databaseSQL, CustomerCertificatePO.class, conn);
        if(search.size()==0){
            return 0;
        }




        return 1;
    }

}

