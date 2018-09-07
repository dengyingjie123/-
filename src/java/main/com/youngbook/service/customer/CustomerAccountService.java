package com.youngbook.service.customer;

import com.youngbook.action.wsi.ServiceInvoker;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Bank;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.allinpay.IAllinpayAuthenticationDao;
import com.youngbook.dao.customer.ICustomerAccountDao;
import com.youngbook.dao.customer.ICustomerAuthenticationStatusDao;
import com.youngbook.dao.customer.ICustomerCertificateDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentDetailPO;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.fdcg.FdcgCustomerAccountBindStatus;
import com.youngbook.entity.po.fdcg.FdcgCustomerAccountPO;
import com.youngbook.entity.po.fdcg.FdcgCustomerPO;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.vo.customer.CustomerAccountVO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component("customerAccountService")
public class CustomerAccountService extends BaseService {

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;


    @Autowired
    IUserDao userDao;


    @Autowired
    ICustomerAuthenticationStatusDao customerAuthenticationStatusDao;

    @Autowired
    ICustomerAccountDao customerAccountDao;

    @Autowired
    ICustomerCertificateDao customerCertificateDao;

    @Autowired
    IAllinpayAuthenticationDao allinpayAuthenticationDao;


    public FdcgCustomerAccountPO fdcgGetCustomerAccountPO(String accountNo, String userName, String bindStatus, Connection conn) throws Exception {

        StringUtils.checkIsEmpty(accountNo, "accountNo为空");
        StringUtils.checkIsEmpty(userName, "userName为空");

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("99AD1803");
        dbSQL.addParameter4All("accountNo", accountNo);
        dbSQL.addParameter4All("userName", userName);
        dbSQL.addParameter4All("bindStatus", bindStatus);
        dbSQL.initSQL();

        List<FdcgCustomerAccountPO> customerAccountPOs = MySQLDao.search(dbSQL, FdcgCustomerAccountPO.class, conn);

        if (customerAccountPOs != null && customerAccountPOs.size() == 1) {
            return customerAccountPOs.get(0);
        }

        return null;
    }


    /**
     * 保存存管账户信息
     *
     * 处理存管账户信息时，如果系统已有开户用户的对应，则仅保留一条对应的数据
     * @param customerAccountPO
     * @param conn
     * @return
     * @throws Exception
     */
    public FdcgCustomerAccountPO fdcgBind(FdcgCustomerAccountPO customerAccountPO, Connection conn) throws Exception {


        /**
         * 如果客户已有绑定，则直接返回
         */
        FdcgCustomerAccountPO checkCustomerAccountPO = fdcgGetCustomerAccountPO(customerAccountPO.getAccountNo(), customerAccountPO.getUserName(), FdcgCustomerAccountBindStatus.Bind, conn);


        if (checkCustomerAccountPO != null && !checkCustomerAccountPO.getCrmCustomerPersonalId().equals(customerAccountPO.getExtMark())) {
            MyException.newInstance("关联客户失败，CRM用户编号不一致", "accountNo=" + customerAccountPO.getAccountNo() + "&userName=" + customerAccountPO.getUserName() + "&customerPersonalId_Wait=" + customerAccountPO.getCrmCustomerPersonalId()).throwException();
        }

        if (checkCustomerAccountPO != null) {
            return checkCustomerAccountPO;
        }


        if (StringUtils.isEmpty(customerAccountPO.getExtMark())) {
            MyException.newInstance("关联的客户编号为空", "fdcg_customer_id=" + customerAccountPO.getId()).throwException();
        }


        String customerPersonalId = customerAccountPO.getExtMark();


        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerPersonalId, conn);

        if (customerPersonalPO == null) {
            MyException.newInstance("无法找到对应的客户", "customerId=" + customerPersonalId).throwException();
        }


        customerAccountPO.setCrmCustomerPersonalId(customerPersonalPO.getId());


        customerAccountPO.setBindStatus("1");

        MySQLDao.insertOrUpdate(customerAccountPO, conn);


        return customerAccountPO;
    }


    public FdcgCustomerAccountPO fdcgUnbind(FdcgCustomerAccountPO customerAccountPO, Connection conn) throws Exception {


        /**
         * 如果客户已有绑定，则直接返回
         */
        FdcgCustomerAccountPO checkCustomerAccountPO = fdcgGetCustomerAccountPO(customerAccountPO.getAccountNo(), customerAccountPO.getUserName(), FdcgCustomerAccountBindStatus.Bind, conn);

        if (checkCustomerAccountPO != null && checkCustomerAccountPO.getBindStatus().equals(FdcgCustomerAccountBindStatus.Unbind)) {
            return checkCustomerAccountPO;
        }

        if (checkCustomerAccountPO != null) {
            checkCustomerAccountPO.setBindStatus(FdcgCustomerAccountBindStatus.Unbind);
            MySQLDao.insertOrUpdate(checkCustomerAccountPO, conn);
        }


        return checkCustomerAccountPO;
    }


    public FdcgCustomerAccountPO fdcgGetCustomerAccountPO(String crmCustomerPersonalId, String bindStatus, Connection conn) throws Exception {

        return customerAccountDao.fdcgGetCustomerAccountPO(crmCustomerPersonalId, bindStatus, conn);

    }


    public CustomerPersonalVO fdcgGetCustomerPersonalVO(String fdcgCustomerId, Connection conn) throws Exception {

        FdcgCustomerPO customerPO = customerPersonalDao.fdcgLoadCustomerPO(fdcgCustomerId, conn);

        if (customerPO == null) {
            return null;
        }

        CustomerPersonalVO customerPersonalVO = customerPersonalDao.loadCustomerVOByCustomerPersonalId(customerPO.getCrmCustomerPersonalId(), conn);

        return customerPersonalVO;
    }


    public int removeById(String customerAccountId, String userId, Connection conn) throws Exception {
        return customerAccountDao.removeById(customerAccountId, userId, conn);
    }

    public int getBankCardCount(String customerId, Connection conn) throws Exception {
        return customerAccountDao.getBankCardCount(customerId, conn);
    }


    public Integer insertCustomerAccount4W(CustomerAccountPO account, Connection conn) throws Exception {
        return customerAccountDao.insertCustomerAccount4W(account, conn);
    }

    public Pager list(CustomerAccountPO customerAccount, HttpServletRequest request, Connection conn) throws Exception {
        return customerAccountDao.list(customerAccount, request, conn);
    }


    public List<CustomerAccountPO> list(CustomerAccountPO customerAccount, Connection conn) throws Exception {
        return customerAccountDao.list(customerAccount, conn);
    }

    /**
     * 创建人：姚章鹏
     * 时间：2015年12月10日
     * 内容：获取客户银行卡信息
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerAccountVO getCustomerBankCardInfo(String id, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("   SELECT ");
        sbSQL.append("  cc.id, ");
        sbSQL.append("        cc.bank, ");
        sbSQL.append("        cc. NAME, ");
        sbSQL.append("       cc.number, ");
        sbSQL.append("       cc.bankBranchName, ");
        sbSQL.append("       cc.bankCode ");
        sbSQL.append("  FROM ");
        sbSQL.append("  crm_customeraccount cc ");
        sbSQL.append("  WHERE ");
        sbSQL.append("  cc.state = 0 ");
        sbSQL.append("  AND cc.CustomerId = ?");
        //创建kv对象
        List<KVObject> parmeters = new ArrayList<KVObject>();
        parmeters = Database.addQueryKVObject(1, id, parmeters);
        //执行查询
        List<CustomerAccountVO> pager = MySQLDao.search(sbSQL.toString(), parmeters, CustomerAccountVO.class, null, conn);
        if (pager != null && pager.size() == 1) {
            String number = AesEncrypt.decrypt(pager.get(0).getNumber());
            number = number.replace(number.substring(4, 12), "********");
            pager.get(0).setNumber(number);
            return pager.get(0);
        }
        return null;
    }


    public void verifyRealNameAndBindingBankCard(String customerId, String peopleName, String peopleIdNumber, String bankCode, String bankNumber, Connection conn) throws Exception {

        // 获得待验证的银行卡号和身份证号
        if (StringUtils.isEmpty(bankNumber) || StringUtils.isEmpty(peopleName) || StringUtils.isEmpty(peopleIdNumber) || StringUtils.isEmpty(bankCode)) {
            MyException.newInstance(Config.getWords("w.customer.auth.bank.missingdata.error"), "认证的银行持卡信息不完整").throwException();
        }

        // 认证状态里查询是否绑定过银行卡
        CustomerBankAuthenticationPO authenticationStatus = allinpayAuthenticationDao.getBankAuthenticationStatus(customerId, Config4Status.STATUS_CUSTOMER_BANKCARD_UNFINISH, conn);
        if (authenticationStatus != null) {
            String message = Config.getWords("customer.account.band.mutilCards");
            MyException.newInstance(message, "客户多次添加银行卡").throwException();
        }

        // 检测是否支持该银行卡
        CustomerAccountPO customerAccount = new CustomerAccountPO();
        customerAccount.setCustomerId(customerId);
        customerAccount.setNumber(bankNumber); // 加密存储
        Boolean hasBankCode = false;
        Map<String, String> banks = Config4Bank.getBanks(conn);
        // 校验银行编码是否正确
        if (banks.containsKey(bankCode)) {
            customerAccount.setBank(banks.get(bankCode));
            hasBankCode = true;
        }
        if (!hasBankCode) {
            MyException.newInstance("不支持这张银行卡", "不支持这张银行卡").throwException();
        }

        // 银行卡记录里是否有多条银行卡记录
        int customerAccountStatus = customerAccountDao.getBankCardCount(customerId, conn);
        if (CustomerAccountStatus.ONLY_ONE_CARD == customerAccountStatus || CustomerAccountStatus.MORE_THAN_ONE_CARD == customerAccountStatus) {
            String message = Config.getWords("customer.account.band.mutilCards");
            MyException.newInstance(message, "客户已绑定过银行卡，请勿重复绑定").throwException();
        }


        int validateIdentityCode = CustomerPersonalValidateIdentityCode.UNKNOW;
        com.youngbook.action.wsi.RequestObject requestObject = new com.youngbook.action.wsi.RequestObject();
        requestObject.setName("allinpay.realNameVerification.v2");
        requestObject.setToken(Config.getSystemConfig("wsi.token"));
        requestObject.addData("name", peopleName);
        requestObject.addData("number", peopleIdNumber);
        requestObject.addData("bankCode", bankCode);
        requestObject.addData("bankNumber", bankNumber);
        ServiceInvoker invoker = new ServiceInvoker();
        ReturnObject returnObject = invoker.invoke(requestObject);

        if (returnObject.getCode() != ReturnObject.CODE_SUCCESS) {
            MyException.newInstance(Config.getWords4WebGeneralError(), "实名认证接口v2调用失败").throwException();
        }

        validateIdentityCode = Integer.parseInt(returnObject.getReturnValue().toString());

        // 未知错误
        if(validateIdentityCode == CustomerPersonalValidateIdentityCode.UNKNOW) {
            MyException.newInstance(Config.getSystemConfig("w.customer.auth.bank.customerinfo.error"), "您填写的信息有误，请核对后提交").throwException();
        }
        // isPass：3009  无此账户
        if(validateIdentityCode == CustomerPersonalValidateIdentityCode.ERROR_BANK_NUMBER) {
            MyException.newInstance(Config.getWords("w.customer.auth.bank.nocard.error"), "您填写的信息有误，无此账户").throwException();
        }
        // isPass：3031 账号户名不符
        if(validateIdentityCode == CustomerPersonalValidateIdentityCode.ERROR_CUSTOMER_NAME) {
            MyException.newInstance(Config.getWords("w.customer.auth.bank.accountname.error"), "您填写的信息有误，账号户名不符").throwException();
        }

        if (validateIdentityCode != CustomerPersonalValidateIdentityCode.PASS) {
            MyException.newInstance(Config.getSystemConfig("w.customer.auth.bank.customerinfo.error"), "您填写的信息有误，账号户名不符").throwException();
        }



        // 验证通过，执行绑定
        UserPO operator = userDao.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);

        // 身份认证通过后，姓名以认证通过后的为准，因此修改
        customerPersonalService.updateCustomerRealName(customerId, peopleName, conn);

        // 判断是否有实名数据
        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerId, conn);

        if(customerCertificatePO == null) {
            customerCertificateDao.insertOrUpdateCertificate(customerId, Config.Type_PeopleId, peopleIdNumber, operator.getId(), conn);
        }

        // 执行绑定
        customerAccount.setCustomerType(CustomerType.insideCustomer);   // 内部客户
        customerAccount.setNumber(AesEncrypt.encrypt(customerAccount.getNumber()));
        int count = customerAccountDao.insertCustomerAccount4W(customerAccount, conn);

        if (count != 1) {
            MyException.newInstance("绑定失败", "绑定失败").throwException();
        }

        // 新增或修改认证状态
        count = customerAuthenticationStatusDao.saveAuthenticationStatus(customerId, CustomerAuthenticationStatus.AUTH_TYPE_ACCOUNT, conn);

        if (count != 1) {
            MyException.newInstance("绑定失败", "绑定失败").throwException();
        }
    }

    /**
     * 获取客户的所有银行账号信息
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年1月6日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public List<CustomerAccountPO> getCustomerAccounts(String customerId, Connection conn) throws Exception {
        List<CustomerAccountPO> customerAccountPOs =  customerAccountDao.getCustomerAccounts(customerId, conn);

        if (customerAccountPOs == null) {
            customerAccountPOs = new ArrayList<>();
        }

        return customerAccountPOs;
    }

    /**
     * 添加或修改银行卡号
     *
     * @param salemanId
     * @param bankAccount
     * @param bankCode
     * @param bankName
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer insertBankAccountForSaleman(String salemanId, String bankAccount, String bankCode, String bankName, Connection conn) throws Exception {

        // 查询对外销售人员
        SalemanOuterService outerService = new SalemanOuterService();
        UserPO outerPO = outerService.loadSalemanById(salemanId, conn);
        if(outerPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到该理财师").throwException();
        }

        // 查询是否有已添加的银行账户
        CustomerAccountPO accountPO = new CustomerAccountPO();
        accountPO.setCustomerId(salemanId);
        accountPO.setNumber(bankAccount);
        accountPO.setState(Config.STATE_CURRENT);
        accountPO = MySQLDao.load(accountPO, CustomerAccountPO.class, conn);

        // 如果已经存在了，则不能做任何修改
        if(accountPO != null) {
            MyException.newInstance(ReturnObjectCode.SALEMAN_OUTER_BANKCARD_EXISTENT, "您的银行卡已存在，无需重复添加").throwException();
        }

        accountPO = new CustomerAccountPO();
        accountPO.setCustomerId(salemanId);
        accountPO.setNumber(bankAccount);
        if(!StringUtils.isEmpty(bankCode)) {
            accountPO.setBankCode(bankCode);
        }
        if(!StringUtils.isEmpty(bankName)) {
            accountPO.setBank(bankName);
        }
        accountPO.setName(outerPO.getName());
        accountPO.setCustomerType(CustomerType.outsideSaleman);

        return MySQLDao.insertOrUpdate(accountPO, conn);

    }

    /**
     * 添加或修改银行卡号
     *
     * @param customerId
     * @param bankAccount
     * @param bankCode
     * @param bankName
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer insertOrUpdateBankAccount(String customerId, String customerName, String bankAccount, String bankCode, String bankName, Connection conn) throws Exception {

        if (StringUtils.isEmpty(customerId) || StringUtils.isEmpty(customerName) || StringUtils.isEmpty(bankAccount) || StringUtils.isEmpty(bankCode)) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "请输入正确参数").throwException();
        }

        // 判断是否是线上客户
        CustomerPersonalPO personalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        if (personalPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到该客户").throwException();
        }

        // 判断是否添加了相同的银行卡
        String sql = "select * from crm_customeraccount a where a.state = 0 and a.customerId = '" + customerId + "' and a.number = '" + bankAccount + "'";
        List<CustomerAccountPO> list = MySQLDao.search(sql, new ArrayList<KVObject>(), CustomerAccountPO.class, null, conn);
        if (list == null || list.size() == 0) {

            // 插入或更新银行卡
            CustomerAccountPO accountPO = new CustomerAccountPO();
            accountPO.setCustomerId(customerId);
            accountPO.setName(customerName);
            accountPO.setNumber(bankAccount);
            accountPO.setBankCode(bankCode);
            accountPO.setCustomerType(CustomerType.insideCustomer);

            // 获取支持的银行
            Map<String, String> banks = Config4Bank.getBanks(conn);
            if (!banks.containsKey(bankCode)) {
                MyException.newInstance(ReturnObject.CODE_EXCEPTION, "暂时不支持该银行").throwException();
            }
            accountPO.setBank(banks.get(bankCode));

            return MySQLDao.insertOrUpdate(accountPO, conn);
        }
        return 0;
    }

    /**
     * 解绑银行卡
     * @param customerId
     * @param bankAccount
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer unBindBankAccount(String customerId, String bankAccount, Connection conn) throws Exception {

        // 构造实体
        CustomerAccountPO accountPO = new CustomerAccountPO();
        accountPO.setCustomerId(customerId);
        accountPO.setNumber(AesEncrypt.encrypt(bankAccount));
        accountPO.setState(Config.STATE_CURRENT);
        accountPO = MySQLDao.load(accountPO, CustomerAccountPO.class, conn);
        if(accountPO == null){
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "找不到银行卡信息，请确认参数是否正确").throwException();
        }

        // 判断是否有未兑付的兑付计划，没有则解绑
        Boolean hasPaymentPlan = this.hasUnpaiedPaymentPlan(customerId, accountPO.getNumber(), conn);
        if(hasPaymentPlan) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_PAYMENTPLAN_EXIST, "该银行卡存在“未兑付”的信息，为此不能解绑，如您的银行卡不能使用，请联系客服！").throwException();
        }

        // 记录标志为删除
        accountPO.setState(Config.STATE_DELETE);
        return MySQLDao.update(accountPO, conn);
    }

    /**
     * 检测某个客户的银行卡是否含有未兑付的兑付计划
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2016年1月7日10:25:46
     * 
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public Boolean hasUnpaiedPaymentPlan(String customerId, String bankNumber, Connection conn) throws Exception {

        // 组装 SQL
        StringBuffer sbSQL = new StringBuffer("select * from core_paymentplan p, crm_order o where p.state = 0 and o.state = 0 and p.orderId = o.id and p.status = 0 and o.customerId = ? and o.bankCard = ?");
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, customerId, parameters);
        parameters = Database.addQueryKVObject(2, bankNumber, parameters);

        // 执行查询
        List<PaymentPlanPO> list = MySQLDao.search(sbSQL.toString(), parameters, PaymentPlanPO.class, null, conn);

        return list.size() > 0 ? true : false;

    }



    /**
     * 插入或更新
     *
     * 作者：邓超
     * 内容：新增注释
     * 时间：2015-12-01
     *
     * @param customerAccount
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerAccountPO insertOrUpdate(CustomerAccountPO customerAccount, String userId, Connection conn) throws Exception{

        //aes加密银行账号
        if(customerAccount != null && !StringUtils.isEmpty(customerAccount.getNumber())) {
            customerAccount.setNumber(AesEncrypt.encrypt(customerAccount.getNumber()));
        }


        // 保存账号名称
        if (StringUtils.isEmpty(customerAccount.getName()) && !StringUtils.isEmpty(customerAccount.getCustomerId())) {
            CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerAccount.getCustomerId(), conn);
            customerAccount.setName(customerPersonalPO.getName());
        }

        MySQLDao.insertOrUpdate(customerAccount, userId, conn);


        return customerAccount;
    }

    /**
     *创建人：姚章鹏
     * 时间：2015年8月26日14:48:53
     * 内容：添加银行卡认证
     *
     * 修改人：方斌杰
     * 时间：2015年8月31日14:48:53
     * 内容：添加银行卡认证表其它属性
     *
     * @param temp
     * @param conn  @return
     * @throws Exception
     */
    public int insertAccount(AllinpayBatchPaymentDetailPO temp, Connection conn) throws Exception{
        CustomerAccountPO customerAccount=new CustomerAccountPO();
            int count=0;
            // 新增
            customerAccount.setSid(MySQLDao.getMaxSid("crm_customeraccount", conn));
            customerAccount.setId(IdUtils.getUUID32());
            customerAccount.setState(Config.STATE_CURRENT);
            customerAccount.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            customerAccount.setOperateTime(TimeUtils.getNow());
            customerAccount.setName(temp.getAccount_name());
            customerAccount.setNumber(temp.getAccount_no());
            customerAccount.setCustomerId(temp.getE_user_code());
            customerAccount.setBank(temp.getBank_name());
            customerAccount.setBankCode(temp.getBank_code());
            customerAccount.setCustomerType(CustomerType.insideCustomer);

           count=MySQLDao.insert(customerAccount, conn);
           return count;
    }



    /**
     * 判断数据是否能进行添加或者修改的操作
     *
     * 修改人：周海鸿
     * 修改时间：2015-7-1
     *
     * @return
     * @throws Exception
     */
    public boolean idForinsertOrUpdate()throws Exception{
        return false;
    }

    /**
     * 通过客户查询银行卡（仅限一张卡）
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-11-7
     *
     * @author 邓超
     * @date 2015-11-7
     *
     * @return
     * @throws Exception
     */
    public CustomerAccountPO loadByCustomerId(String id, Connection conn) throws Exception {
        CustomerAccountPO account = new CustomerAccountPO();
        account.setState(Config.STATE_CURRENT);
        account.setCustomerId(id);
        account = MySQLDao.load(account, CustomerAccountPO.class, conn);
        return account;
    }




    /**
     * 根据id查询客户账户
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerAccountPO getCustomerAccount(String customerId, Connection conn) throws Exception {
        return customerAccountDao.getCustomerAccount(customerId, conn);
    }

    /**
     *根据id和卡号查询客户账户
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerAccountPO getCustomerAccountCard(String customerId, String card, Connection conn) throws Exception {
        CustomerAccountPO customerAccount = new CustomerAccountPO();
        customerAccount.setState(Config.STATE_CURRENT);
        customerAccount.setCustomerId(customerId);
        customerAccount.setNumber(card);
        customerAccount = MySQLDao.load(customerAccount, CustomerAccountPO.class, conn);
        return customerAccount;

    }


}
