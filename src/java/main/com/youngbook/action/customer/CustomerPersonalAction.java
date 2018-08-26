package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.action.wsi.RequestObject;
import com.youngbook.action.wsi.ServiceInvoker;
import com.youngbook.annotation.Security;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.config.Config4W;
import com.youngbook.common.fdcg.FdcgCommon;
import com.youngbook.common.utils.*;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.core.OrderPayPO;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.fdcg.*;
import com.youngbook.entity.po.info.LegalAgreementPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.production.ProductionCompositionPO;
import com.youngbook.entity.po.system.*;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.entity.vo.customer.CustomerMoneyLogVO;
import com.youngbook.entity.vo.customer.CustomerMoneyVO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.entity.wvo.ProtectionQuestionWVO;
import com.youngbook.entity.wvo.customer.CustomerSafetyQAWVO;
import com.youngbook.entity.wvo.production.ProductionWVO;
import com.youngbook.service.core.OrderPayService;
import com.youngbook.service.customer.*;
import com.youngbook.service.info.CustomerAgreementService;
import com.youngbook.service.info.LegalAgreementService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.production.ProductionCompositionService;
import com.youngbook.service.production.ProductionService;
import com.youngbook.service.report.SimpleReportService;
import com.youngbook.service.sale.PaymentPlanService;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.SmsService;
import com.youngbook.service.system.TokenService;
import com.youngbook.service.system.UserService;
import com.youngbook.service.wechat.WeChatService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerPersonalAction extends BaseAction {

    private CustomerPersonalPO personal = new CustomerPersonalPO();
    private CustomerDistributionPO customerDistribution = new CustomerDistributionPO();
    private LegalAgreementPO legalAgreement = new LegalAgreementPO();
    private CustomerPersonalVO personalVO = new CustomerPersonalVO();
    private CustomerCertificatePO customerCertificate = new CustomerCertificatePO();
    private CustomerPersonalPO customerPersonal = new CustomerPersonalPO();
    private CustomerMoneyPO customerMoney = new CustomerMoneyPO();
    private CustomerMoneyVO customerMoneyVO = new CustomerMoneyVO();
    private Boolean transPasswordNeedMobile = true;
    private CustomerMoneyLogVO customerMoneyLogVO = new CustomerMoneyLogVO();


    @Autowired
    LegalAgreementService legalAgreementService;


    @Autowired
    CustomerCertificateService customerCertificateService;


    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationCodeService authenticationCodeService;

    @Autowired
    CustomerAccountService customerAccountService;

    @Autowired
    CustomerMoneyService customerMoneyService;

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    CustomerAuthenticationStatusService customerAuthenticationStatusService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerInfoService customerInfoService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductionService productionService;

    @Autowired
    ProductionCompositionService productionCompositionService;

    @Autowired
    CustomerAgreementService customerAgreementService;

    @Autowired
    WeChatService weChatService;

    @Autowired
    SmsService smsService;

    @Autowired
    PaymentPlanService paymentPlanService;

    @Autowired
    CustomerMoneyLogService customerMoneyLogService;

    @Autowired
    CustomerScoreService customerScoreService;


    @Autowired
    LogService logService;

    //Excel导出属性
    private String fields;
    private String titles;


    private CustomerAccountPO customerAccount = new CustomerAccountPO();
    private LogPO log = new LogPO();
    private KVPO kv = new KVPO();
    private String sort = new String();//需要排序的列名
    private String order = new String();



    public String appointmentOrder4Modern() throws Exception {


        CustomerPersonalPO customerPersonalPO = HttpUtils.getInstanceFromRequest(getRequest(), CustomerPersonalPO.class);

        String productionId = getHttpRequestParameter("productionId");
        String moneyString = getHttpRequestParameter("money");

        if (!StringUtils.isNumeric(moneyString)) {
            MyException.newInstance("输入预约金额有误", "productionId="+productionId+"&money="+moneyString).throwException();
        }


        double money = Double.parseDouble(moneyString);

        ProductionCompositionPO productionCompositionPO = productionCompositionService.getProductionCompositionPOByProductionIdAndMoney(productionId, money, getConnection());

        orderService.appointmentOrder("", customerPersonalPO.getId(), productionId, productionCompositionPO.getId(), money, null, true, TimeUtils.getNow(), null, TimeUtils.getNow(), getLoginUser().getReferralCode(), OrderStatus.Appointment, null, null, null, getConnection());

        return SUCCESS;
    }

    /**
     * 确认客户为合格投资者
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：不详
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String confirmInvestor() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求对象
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        // 校验参数
        if(StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少客户ID").throwException();
        }

        Integer count = customerPersonalService.confirmInvestor(customerId, conn);
        if(count != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据操作异常，请重新再试！");
        }

        return SUCCESS;

    }

    /**
     * HOPEWEALTH-28-后台对风险测评的支持
     * 根据客户ID查询记录，暂时只支持CustomerTestType为1即风险测评的查询
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String getTestScoreByCustomerId() throws Exception {
        // 获取数据库连接
        Connection conn = getConnection();
        // 获取请求
        HttpServletRequest request = getRequest();
        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        // 验证参数
        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少客户ID").throwException();
        }
        if (customerId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "客户ID不正确").throwException();
        }

        CustomerTestPO customerTestPO = customerPersonalService.getTestScore(customerId, CustomerTestType.TEST_TYPE_RISK, conn);

        // 返回评分记录
        if (customerTestPO == null) {
            // 若无该客户记录，将评分设为0
            customerTestPO = new CustomerTestPO();
            customerTestPO.setCustomerId(customerId);
            customerTestPO.setType(CustomerTestType.TEST_TYPE_RISK);
            customerTestPO.setScore(0.0);
        }
        getResult().setReturnValue(customerTestPO.toJsonObject());

        return SUCCESS;
    }

    /**
     * HOPEWEALTH-28-后台对风险测评的支持
     * 增加一条评分记录
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String bearTest() throws Exception {
        // 获取数据库连接
        Connection conn = getConnection();
        // 获取请求
        HttpServletRequest request = getRequest();
        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        String score = HttpUtils.getParameter(request, "score");
        // 验证参数
        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少客户ID").throwException();
        }
        if (StringUtils.isEmpty(score)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少分数").throwException();
        }
        if (customerId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "客户ID不正确").throwException();
        }
        double scoreDouble = Double.parseDouble(score);
        if (scoreDouble < 0.0) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "分数不正确").throwException();
        }
        int testType = CustomerTestType.TEST_TYPE_RISK;

        customerPersonalService.bearTest(customerId, scoreDouble, testType, conn);

        return SUCCESS;
    }

    /**
     * 支付生成实名信息接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月18日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String buildPayAuthentication() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String bizId = HttpUtils.getParameter(request, "bizId");
        String customerId = HttpUtils.getParameter(request, "customerId");
        String name = HttpUtils.getParameter(request, "name");
        String idCard = HttpUtils.getParameter(request, "idCard");
        String bankNumber = HttpUtils.getParameter(request, "bankNumber");
        String bankCode = HttpUtils.getParameter(request, "bankCode");

        // 校验参数合法性
        if(StringUtils.isEmpty(bizId) || StringUtils.isEmpty(customerId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(idCard) || StringUtils.isEmpty(bankNumber) || StringUtils.isEmpty(bankCode)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if(customerId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 双边去空
        bizId = bizId.trim();
        customerId = customerId.trim();
        name = name.trim();
        idCard = idCard.trim();
        bankNumber = bankNumber.trim();
        bankCode = bankCode.trim();

        // 实例化客户服务
        OrderPayService orderPayService = new OrderPayService();
        OrderPayPO orderPay = orderPayService.buildPayAuthentication(bizId, customerId, name, idCard, bankNumber, bankCode, conn);

        if(orderPay == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "建立实名信息失败了，请稍候再试").throwException();
        }

        return SUCCESS;
    }

    /**
     * 忘记密码action跳转
     *
     * @return
     * @throws Exception
     */
    public String forgetPassword() throws Exception {
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：7/13/2015
     * 内容：根据用户名查询出所有基本信息
     *
     * @return
     * @throws Exception
     */
    @Security(needWebCode = true)
    public String resetPassword4Mobile4W() throws Exception {

        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = this.getConnection();

        String mobile = request.getParameter("mobile");

        if (StringUtils.isEmpty(mobile)) {
            return "forgetPassword";
        }

        // 查询手机号是否已经注册了
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("    * ");
        sqlDB.append(" FROM ");
        sqlDB.append("    crm_customerpersonal ");
        sqlDB.append(" WHERE ");
        sqlDB.append("    1 = 1 ");
        sqlDB.append(" AND state = " + Config.STATE_CURRENT + " ");
        sqlDB.append(" AND loginName = '" + mobile + "' ");
        List<CustomerPersonalPO> list = MySQLDao.query(sqlDB.toString(), CustomerPersonalPO.class, null, getConnection());
        if (list == null || list.size() == 0) {
            MyException.newInstance("该手机号没有注册").throwException();
        }
        if (list.size() > 1) {
            MyException.newInstance(Config.getWords4WebGeneralError(), "系统存在多条手机号数据").throwException();
        }

        CustomerPersonalPO customerPersonal = list.get(0);
        session.setAttribute(Config4W.TEMP_SESSION_LOGINUSER_STRING, customerPersonal);

        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：7/13/2015
     * 内容：忘记密码验证手机号码和验证码
     *
     * @return
     * @throws Exception
     */
    public String verifyMobile() throws Exception {

        String code = getRequest().getParameter("mobiValidateCode");
        String mobile = getRequest().getParameter("mobile");
        HttpSession temp = getRequest().getSession();
        Object id = temp.getAttribute(Config4W.TEMP_CUSTOMER_ID);
        Connection conn = this.getConnection();

        if (code == null || "".equals(code) || mobile == null || "".equals(mobile)) {
            throw new Exception("参数异常");
        }

        // 验证手机号码是否已经注册
        Boolean isRegistered = customerPersonalService.isMobileRegistered(mobile, conn);
        if (!isRegistered) {
            MyException.newInstance("该手机号未注册").throwException();
        }

        if (id == null) {
            getResult().setReturnValue("2");
            return SUCCESS;
        }

        String customerId = (String) id;

        CustomerAuthenticationCodePO codePO = VarificationUtils.viladateMobileCode(code, mobile);

        if (codePO != null) {
            // 验证码错误返回0作为标识
            getResult().setReturnValue("0");
            return SUCCESS;
        }

        // 验证时间是否失效
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date expiredTime = formatter.parse(codePO.getExpiredTime().substring(0, 10));

        Date now = new Date();

        if (expiredTime.getTime() > now.getTime()) {

            // 修改认证码的认证时间
            codePO.setAuthenticationTime(TimeUtils.getNow());
            MySQLDao.update(codePO, conn);

            // 新增或修改认证状态
            CustomerAuthenticationStatusPO statusPO = customerAuthenticationStatusService.loadByCustomerId(customerId, conn);
            if (statusPO == null) {
                statusPO = new CustomerAuthenticationStatusPO();
                statusPO.setCustomerId(customerId);
            }
            statusPO.setMobileStatus(1);
            statusPO.setMobileTime(TimeUtils.getNow());

            // 网站用户
            UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);
            customerAuthenticationStatusService.insertOrUpdate(statusPO, user, conn);

            // 组建sql查询该客户是否正确
            StringBuffer sqlDB = new StringBuffer();
            sqlDB.append(" SELECT ");
            sqlDB.append("    * ");
            sqlDB.append(" FROM ");
            sqlDB.append("    crm_customerpersonal ");
            sqlDB.append(" WHERE ");
            sqlDB.append("    1 = 1 ");
            sqlDB.append(" AND state = 0 ");
            sqlDB.append(" AND id = '" + customerId + "' ");
            List<CustomerPersonalPO> list = MySQLDao.query(sqlDB.toString(), CustomerPersonalPO.class, null, conn);
            if (list != null && list.size() > 0) {
                // 成功返回1作为标识
                getResult().setReturnValue("1");
            } else {
                // 查询不到该客户的相关电话信息
                getResult().setReturnValue("4");
            }

        }
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：7/13/2015
     * 内容：忘记密码验证身份证信息
     *
     * @return
     * @throws Exception
     */
    public String verifyIDCard() throws Exception {
        if (getRequest().getParameter("fullName") == null || getRequest().getParameter("cardNo") == null || getRequest().getSession().getAttribute(Config4W.TEMP_CUSTOMER_ID) == null) {
            // 相关信息获取为空返回0标识给web，非法操作
            getResult().setReturnValue("0");
            MyException.deal(new Exception(getClass() + "非法操作，对应字段获取为空"));
        } else {
            String fullName = getRequest().getParameter("fullName");
            String cardNO = getRequest().getParameter("cardNo");
            String id = getRequest().getSession().getAttribute(Config4W.TEMP_CUSTOMER_ID).toString();
            StringBuffer sqlDB = new StringBuffer();
            sqlDB.append(" SELECT DISTINCT ");
            sqlDB.append("    cp.`Name`, ");
            sqlDB.append("    cc.Number ");
            sqlDB.append(" FROM ");
            sqlDB.append("    crm_customerpersonal cp, ");
            sqlDB.append("    crm_customercertificate cc ");
            sqlDB.append(" WHERE ");
            sqlDB.append("    1 = 1 ");
            sqlDB.append(" AND cp.state = 0 ");
            sqlDB.append(" AND cc.state = 0 ");
            sqlDB.append(" AND cp.`Name` = '" + fullName + "' ");
            sqlDB.append(" AND cc.Number = '" + cardNO + "' ");
            sqlDB.append(" AND cc.CustomerId = '" + id + "' ");
            List<CustomerPersonalVO> list = MySQLDao.query(sqlDB.toString(), CustomerPersonalVO.class, null, getConnection());
            // 如果查询出有对应数据，返回一个标识给web
            if (list.size() > 0) {
                getResult().setReturnValue("1");
            } else {
                // 否则返回一个标识给web，提示该用户没有实名验证
                getResult().setReturnValue("2");
            }
        }
        return SUCCESS;
    }



    /**
     * 创建人：张舜清
     * 时间：7/13/2015
     * 内容：忘记密码重置action
     * 修改:周海鸿
     * 内容：前台传入密码加上特性字符串进行加密
     * 时间：2015-7-23
     *
     * @return
     * @throws Exception
     */
    public String resetPassword() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        String password = request.getParameter("password");
        Connection conn = getConnection();

        if (session.getAttribute(Config4W.TEMP_SESSION_LOGINUSER_STRING) == null) {
//            getResult().setMessage("请重新输入手机号码");
//            getRequest().setAttribute("returnObject",getResult());
//            return "info";
            return "forgetPassword";
        }
        if (StringUtils.isEmpty(password)) {
            getResult().setMessage("请输入新的登录密码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        CustomerPersonalPO customerPersonal = (CustomerPersonalPO) session.getAttribute(Config4W.TEMP_SESSION_LOGINUSER_STRING);
        if (!StringUtils.isEmpty(customerPersonal.getId()) && !StringUtils.isEmpty(password)) {

            // 加入特定的字符 再进行MD处理
            String customerPassword = StringUtils.md5(password + Config.MD5String);
            int count = 0;
            count = customerPersonalService.resetPassword(customerPersonal.getId(), customerPassword, Config.getSystemConfig("web.default.operatorId"), conn);
            if (count == 1) {
                getResult().setMessage("重置密码成功，请重新登录");
                getRequest().setAttribute("returnObject", getResult());
                session.removeAttribute(Config4W.TEMP_SESSION_LOGINUSER_STRING);
                return "info";
            } else {
                getResult().setMessage("重置密码失败");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }
        }
        return SUCCESS;
    }



    public String loadPage_PH_mine_list() throws Exception {

        String customerPersonalId = Config.getLoginCustomerInSession(getRequest()).getId();

        CustomerPersonalVO customerPersonalVO = customerPersonalService.loadCustomerVOByCustomerPersonalId(customerPersonalId, getConnection());

        if (customerPersonalVO != null) {
            getRequest().setAttribute("customerPersonalVO", customerPersonalVO);


            PaymentPlanVO paymentPlanVO = paymentPlanService.getCustomerPaymentPlanInfo4ph(customerPersonalVO.getId(), getConnection());

            paymentPlanVO.setTotalPaymentPrincipalMoneyFormatted(MoneyUtils.format2String(paymentPlanVO.getTotalPaymentPrincipalMoney()));

            paymentPlanVO.setTotalProfitMoneyFormatted(MoneyUtils.format2String(paymentPlanVO.getTotalProfitMoney()));
            paymentPlanVO.setTotalPaymentMoneyFormatted(MoneyUtils.format2String(paymentPlanVO.getTotalPaymentMoney()));

            // 客户积分
            CustomerScorePO customerScorePO = customerScoreService.getCustomerScorePOByMoney(paymentPlanVO.getTotalPaymentPrincipalMoney());
            customerScorePO.setCustomerId(customerPersonalVO.getId());


            FdcgCustomerQueryInfoPO fdcgCustomerQueryInfoPO = customerPersonalService.fdcgQueryCustomerQueryInfoPO(customerPersonalVO.getId(), getConnection());


            getRequest().setAttribute("paymentPlanVO", paymentPlanVO);
            getRequest().setAttribute("customerScorePO", customerScorePO);
            getRequest().setAttribute("fdcgCustomerQueryInfoPO", fdcgCustomerQueryInfoPO);

            return "mine_list";
        }

        return SUCCESS;
    }

    public String loadPage_PH_mobile_list() throws Exception {

        String customerPersonalId = Config.getLoginCustomerInSession(getRequest()).getId();

        CustomerPersonalVO customerPersonalVO = customerPersonalService.loadCustomerVOByCustomerPersonalId(customerPersonalId, getConnection());

        if (customerPersonalVO != null) {
            getRequest().setAttribute("customerPersonalVO", customerPersonalVO);

            return "mobile_list";
        }

        return SUCCESS;
    }


    public String loadPage_PH_account_list() throws Exception {

        String customerPersonalId = Config.getLoginCustomerInSession(getRequest()).getId();

        CustomerPersonalVO customerPersonalVO = customerPersonalService.loadCustomerVOByCustomerPersonalId(customerPersonalId, getConnection());

        if (customerPersonalVO != null) {
            getRequest().setAttribute("customerPersonalVO", customerPersonalVO);

            return "account_list";
        }

        return SUCCESS;
    }

    public String loadPage_PH_password_list() throws Exception {

        String customerPersonalId = Config.getLoginCustomerInSession(getRequest()).getId();

        CustomerPersonalVO customerPersonalVO = customerPersonalService.loadCustomerVOByCustomerPersonalId(customerPersonalId, getConnection());

        if (customerPersonalVO != null) {
            getRequest().setAttribute("customerPersonalVO", customerPersonalVO);

            return "password_list";
        }

        return SUCCESS;
    }


    public String loadCustomerPersonalVOByCustomerPersonalId() throws Exception {
        String customerId = HttpUtils.getParameter(getRequest(), "customerId");

        CustomerPersonalVO customerPersonalVO = customerPersonalService.loadCustomerVOByCustomerPersonalId(customerId, getConnection());

        if (customerPersonalVO != null) {
            getResult().setReturnValue(customerPersonalVO);
        }

        return SUCCESS;
    }


    public String refreshLoginSession() throws Exception {

        CustomerPersonalPO loginCustomerInSession = Config.getLoginCustomerInSession(getRequest());

        if (loginCustomerInSession != null) {
            customerPersonalService.loginFinish(loginCustomerInSession, getRequest().getSession(), getConnection());
        }

        return SUCCESS;
    }


    public String fdcgCheckRegister() throws Exception {

        getResult().setReturnValue("0");

        return SUCCESS;
    }


    public String fdcgGetCustomerPersonalVO() throws Exception {

        String fdcgCustomerId = getHttpRequestParameter("fdcgCustomerId");

        CustomerPersonalVO customerPersonalVO = customerPersonalService.loadCustomerVOByCustomerPersonalId(fdcgCustomerId, getConnection());

        if (customerPersonalVO != null) {
            getResult().setReturnValue(customerPersonalVO.toJsonObject());
        }

        return SUCCESS;
    }


    public String fdcgRegisterDealReturn() throws Exception {

        String name = "用户开户-同步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealReturn(name, getRequest(), getConnection());
    }

    public String fdcgRegisterDealNotify() throws Exception {
        String name = "用户开户-异步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealNotify(name, getRequest(), getConnection());
    }


    public String fdcgChangeMobileDealReturn() throws Exception {

        String name = "用户更改手机号-同步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealReturn(name, getRequest(), getConnection());
    }

    public String fdcgChangeMobileDealNotify() throws Exception {

        String name = "用户更改手机号-异步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealNotify(name, getRequest(), getConnection());
    }


    public String fdcgDealReturnChangePassword() throws Exception {

        String name = "用户重置交易密码-同步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealReturn(name, getRequest(), getConnection());
    }

    public String fdcgDealNotifyChangePassword() throws Exception {

        String name = "用户重置交易密码-异步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealNotify(name, getRequest(), getConnection());
    }



    public String fdcgDealReturnAccountRecharge() throws Exception {

        String name = "用户充值申请-同步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealReturn(name, getRequest(), getConnection());
    }

    public String fdcgDealNotifyAccountRecharge() throws Exception {

        String name = "用户充值申请-异步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealNotify(name, getRequest(), getConnection());
    }



    public String fdcgDealReturnAccountWithdraw() throws Exception {

        String name = "用户提现申请-同步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealReturn(name, getRequest(), getConnection());
    }

    public String fdcgDealNotifyAccountWithdraw() throws Exception {

        String name = "用户提现申请-异步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealNotify(name, getRequest(), getConnection());
    }



    // loadCustomerPOByCrmCustomerPersonalId

    public String fdcgLoadCustomerPOByCrmCustomerPersonalId() throws Exception {

        String crmCustomerPersonalId = getHttpRequestParameter("crmCustomerPersonalId");

        FdcgCustomerPO customerPO = customerPersonalService.fdcgLoadCustomerPOByCrmCustomerPersonalId(crmCustomerPersonalId, getConnection());

        if (customerPO != null) {
            getResult().setReturnValue(customerPO.toJsonObject());
        }

        return SUCCESS;
    }


    public String getCustomerPersonalVO() throws Exception {

        String fdcgCustomerId = getHttpRequestParameter("fdcgCustomerId");

        CustomerPersonalVO customerPersonalVO = customerPersonalService.loadCustomerVOByCustomerPersonalId(fdcgCustomerId, getConnection());

        if (customerPersonalVO != null) {
            getResult().setReturnValue(customerPersonalVO.toJsonObject());
        }

        return SUCCESS;
    }

    public String fdcgGetRegisterData() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        String realName = getHttpRequestParameter("realName");
        String identityCode = getHttpRequestParameter("identityCode");
        String mobilePhone = getHttpRequestParameter("mobilePhone");


        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(customerId, getConnection());

        if (customerPersonalPO == null) {
            MyException.newInstance("无法获得客户信息", "customerId=" + customerId).throwException();
        }

        if (!StringUtils.isEmpty(customerPersonalPO.getName()) && !customerPersonalPO.getName().equals(realName)) {
            MyException.newInstance("客户名字前后不一致", "customerId=" + customerId + "&realName=" + realName).throwException();
        }


        if (!StringUtils.isEmpty(customerPersonalPO.getMobile()) && !customerPersonalPO.getMobile().equals(mobilePhone)) {
            MyException.newInstance("客户手机号前后不一致", "customerId=" + customerId + "&mobilePhone=" + mobilePhone).throwException();
        }


        customerPersonalPO.setName(realName);
        customerPersonalPO.setMobile(mobilePhone);
        customerPersonalPO.setPassword(null);
        customerPersonalService.insertOrUpdate(customerPersonalPO, "0000", getConnection());

        customerCertificateService.insertOrUpdateCertificate(customerPersonalPO.getId(), CustomerCertificateType.ID_CARD, AesEncrypt.encrypt(identityCode), "0000", getConnection());

        FdcgCustomerRegisterPO customerRegisterPO = new FdcgCustomerRegisterPO();
        customerRegisterPO.setMerchantNo(FdcgCommon.getMerchantNo());
        customerRegisterPO.setRealName(realName);
        customerRegisterPO.setIdentityCode(identityCode);
        customerRegisterPO.setMobilePhone(mobilePhone);
        customerRegisterPO.setExtMark(customerPersonalPO.getId());
        customerRegisterPO.setReturnUrl(Config.getSystemConfig("system.url") + "/customer/CustomerPersonal_fdcgRegisterDealReturn");
        customerRegisterPO.setNotifyUrl(Config.getSystemConfig("system.url") + "/customer/fdcgRegisterDealNotify");
        customerRegisterPO.setOrderDate(TimeUtils.getNowDateYYYYMMDD());
        customerRegisterPO.setOrderNo(FdcgCommon.getId20());

        String data = customerRegisterPO.toJsonObject().toString();

        String sign = FdcgCommon.sign(data);

        getResult().setReturnValue(sign);

        return SUCCESS;
    }


    public String fdcgGetChangeMobileData() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        String newPhone = getHttpRequestParameter("newPhone");

        String type = "1";


        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(customerId, getConnection());

        if (customerPersonalPO == null) {
            MyException.newInstance("无法获得客户信息", "customerId=" + customerId).throwException();
        }


        FdcgCustomerPO fdcgCustomerPO = customerPersonalService.fdcgLoadCustomerPOByCrmCustomerPersonalId(customerPersonalPO.getId(), getConnection());

        JSONObject jsonObject = new JSONObject();
        jsonObject.element("accountNo", fdcgCustomerPO.getAccountNo());
        jsonObject.element("extMark", "");
        jsonObject.element("merchantNo", FdcgCommon.getMerchantNo());
        jsonObject.element("newPhone", newPhone);
        jsonObject.element("returnUrl", Config.getSystemConfig("system.url") + "/customer/CustomerPersonal_fdcgChangeMobileDealReturn");
        jsonObject.element("notifyUrl", Config.getSystemConfig("system.url") + "/customer/fdcgChangeMobileDealNotify");
        jsonObject.element("orderDate", TimeUtils.getNowDateYYYYMMDD());
        jsonObject.element("orderNo", FdcgCommon.getId20());
        jsonObject.element("type", type);
        jsonObject.element("userName", fdcgCustomerPO.getUserName());

        String data = jsonObject.toString();

        String sign = FdcgCommon.sign(data);

        getResult().setReturnValue(sign);

        return SUCCESS;
    }


    public String fdcgGetBindData() throws Exception {

        String customerId = getHttpRequestParameter("customerId");


        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(customerId, getConnection());

        if (customerPersonalPO == null) {
            MyException.newInstance("无法获得客户信息", "customerId=" + customerId).throwException();
        }

        FdcgCustomerAccountBindPO customerAccountBindPO = new FdcgCustomerAccountBindPO();


        FdcgCustomerPO customerPO = customerPersonalService.fdcgLoadCustomerPOByCrmCustomerPersonalId(customerId, getConnection());

        if (customerPO == null) {
            MyException.newInstance("无法找到存管账户", "customerId="+customerId);
        }

        customerAccountBindPO.setUserName(customerPO.getUserName());
        customerAccountBindPO.setAccountNo(customerPO.getAccountNo());
        customerAccountBindPO.setMerchantNo(FdcgCommon.getMerchantNo());
        customerAccountBindPO.setExtMark(customerPersonalPO.getId());
        customerAccountBindPO.setReturnUrl(Config.getSystemConfig("system.url") + "/fdcg/CustomerAccount_dealReturnBind");
        customerAccountBindPO.setNotifyUrl(Config.getSystemConfig("system.url") +"/fdcg/CustomerAccount_dealNotifyBind");
        customerAccountBindPO.setOrderDate(TimeUtils.getNowDateYYYYMMDD());
        customerAccountBindPO.setOrderNo(FdcgCommon.getId20());

        String data = customerAccountBindPO.toJsonObject().toString();

        String sign = FdcgCommon.sign(data);

        getResult().setReturnValue(sign);

        return SUCCESS;
    }


    public String fdcgGetChangePasswordData() throws Exception {

        String customerId = getLoginCustomer().getId();


        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(customerId, getConnection());

        if (customerPersonalPO == null) {
            MyException.newInstance("无法获得客户信息", "customerId=" + customerId).throwException();
        }



        FdcgCustomerPO fdcgCustomerPO = customerPersonalService.fdcgLoadCustomerPOByCrmCustomerPersonalId(customerId, getConnection());

        if (fdcgCustomerPO == null) {
            MyException.newInstance("无法找到存管账户", "customerId="+customerId);
        }

        KVObjects kvObjects = new KVObjects();
        kvObjects.addItem("accountNo", fdcgCustomerPO.getAccountNo());
        kvObjects.addItem("userName", fdcgCustomerPO.getUserName());
        kvObjects.addItem("extMark", customerPersonalPO.getId());
        kvObjects.addItem("merchantNo", fdcgCustomerPO.getMerchantNo());
        kvObjects.addItem("returnUrl", Config.getSystemConfig("system.url") + "/customer/CustomerPersonal_fdcgDealReturnChangePassword");
        kvObjects.addItem("notifyUrl", Config.getSystemConfig("system.url") + "/customer/fdcgDealNotifyChangePassword");
        kvObjects.addItem("orderDate", TimeUtils.getNowDateYYYYMMDD());
        kvObjects.addItem("orderNo", FdcgCommon.getId20());


        String data = kvObjects.toJSONObject().toString();

        String sign = FdcgCommon.sign(data);

        getResult().setReturnValue(sign);

        return SUCCESS;
    }


    public String fdcgGetAccountRechargedData() throws Exception {

        String customerId = getLoginCustomer().getId();

        String amount = getHttpRequestParameter("amount");

        StringUtils.checkIsEmpty(amount, "充值金额不能为空");

        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(customerId, getConnection());

        if (customerPersonalPO == null) {
            MyException.newInstance("无法获得客户信息", "customerId=" + customerId).throwException();
        }



        FdcgCustomerPO fdcgCustomerPO = customerPersonalService.fdcgLoadCustomerPOByCrmCustomerPersonalId(customerId, getConnection());

        if (fdcgCustomerPO == null) {
            MyException.newInstance("无法找到存管账户", "customerId="+customerId);
        }

        KVObjects kvObjects = new KVObjects();
        kvObjects.addItem("accountNo", fdcgCustomerPO.getAccountNo());
        kvObjects.addItem("userName", fdcgCustomerPO.getUserName());
        kvObjects.addItem("extMark", customerPersonalPO.getId());
        kvObjects.addItem("merchantNo", fdcgCustomerPO.getMerchantNo());
        kvObjects.addItem("returnUrl", Config.getSystemConfig("system.url") + "/customer/CustomerPersonal_fdcgDealReturnAccountRecharge");
        kvObjects.addItem("notifyUrl", Config.getSystemConfig("system.url") + "/customer/fdcgDealNotifyAccountRecharge");
        kvObjects.addItem("orderDate", TimeUtils.getNowDateYYYYMMDD());
        kvObjects.addItem("orderNo", FdcgCommon.getId20());

        kvObjects.addItem("fee", "0");
        kvObjects.addItem("amount", amount);
        kvObjects.addItem("payType", "1");


        String data = kvObjects.toJSONObject().toString();

        String sign = FdcgCommon.sign(data);

        getResult().setReturnValue(sign);

        return SUCCESS;
    }


    public String fdcgGetAccountWithdrawdData() throws Exception {

        String customerId = getLoginCustomer().getId();

        String amount = getHttpRequestParameter("amount");

        StringUtils.checkIsEmpty(amount, "充值金额不能为空");

        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(customerId, getConnection());

        if (customerPersonalPO == null) {
            MyException.newInstance("无法获得客户信息", "customerId=" + customerId).throwException();
        }



        FdcgCustomerPO fdcgCustomerPO = customerPersonalService.fdcgLoadCustomerPOByCrmCustomerPersonalId(customerId, getConnection());

        if (fdcgCustomerPO == null) {
            MyException.newInstance("无法找到存管账户", "customerId="+customerId);
        }

        FdcgCustomerAccountWithdrawPO fdcgCustomerAccountWithdrawPO = new FdcgCustomerAccountWithdrawPO();

        fdcgCustomerAccountWithdrawPO.setAccountNo(fdcgCustomerPO.getAccountNo());
        fdcgCustomerAccountWithdrawPO.setUserName(fdcgCustomerPO.getUserName());
        fdcgCustomerAccountWithdrawPO.setAmount(amount);
        fdcgCustomerAccountWithdrawPO.setExtMark(fdcgCustomerPO.getExtMark());
        fdcgCustomerAccountWithdrawPO.setFee("0");
        fdcgCustomerAccountWithdrawPO.setMerchantNo(fdcgCustomerPO.getMerchantNo());
        fdcgCustomerAccountWithdrawPO.setReturnUrl(Config.getSystemConfig("system.url") + "/customer/CustomerPersonal_fdcgDealReturnAccountWithdraw");
        fdcgCustomerAccountWithdrawPO.setNotifyUrl(Config.getSystemConfig("system.url") + "/customer/fdcgDealNotifyAccountWithdraw");
        fdcgCustomerAccountWithdrawPO.setOrderDate(TimeUtils.getNowDateYYYYMMDD());
        fdcgCustomerAccountWithdrawPO.setOrderNo(FdcgCommon.getId20());


        String data = fdcgCustomerAccountWithdrawPO.toJsonObject().toString();

        String sign = FdcgCommon.sign(data);

        getResult().setReturnValue(sign);

        return SUCCESS;
    }




    public String fdcgQueryUserInfoData() throws Exception {

        String customerId = getLoginCustomer().getId();

        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(customerId, getConnection());

        if (customerPersonalPO == null) {
            MyException.newInstance("无法获得客户信息", "customerId=" + customerId).throwException();
        }


        FdcgCustomerPO fdcgCustomerPO = customerPersonalService.fdcgLoadCustomerPOByCrmCustomerPersonalId(customerId, getConnection());

        if (fdcgCustomerPO == null) {
            MyException.newInstance("无法找到存管账户", "customerId="+customerId);
        }

        FdcgCustomerQueryInfoPO fdcgCustomerQueryInfoPO = customerPersonalService.fdcgQueryCustomerQueryInfoPO(fdcgCustomerPO);

        getResult().setReturnValue(fdcgCustomerQueryInfoPO);

        return SUCCESS;
    }






    public String loadCustomerPersonalVOById() throws Exception {

        String customerId = HttpUtils.getParameter(getRequest(), "customerId");

        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("客户编号不能为空", "method=loadCustomerPersonalVOById&customerId=" + customerId).throwException();
        }

        String referralCode = null;
        if (getLoginUser() != null) {
            referralCode = getLoginUser().getReferralCode();
        }

        List<CustomerPersonalVO> list = customerPersonalService.listCustomerPersonalVO(null, customerId, referralCode, getConnection());


        if (list == null || list.size() == 0) {
            MyException.newInstance("无法找到对应客户","customerId="+customerId).throwException();
        }


        CustomerPersonalVO customerPersonalVO = list.get(0);

        getResult().setReturnValue(customerPersonalVO);

        return SUCCESS;
    }

    public String reportListPagerCustomerNew() throws Exception {

        List<KVObjects> list = customerPersonalService.reportListPagerCustomerNew(getConnection());
        JSONObject json = new JSONObject();

        /**
         * 构造列表表头
         */
        JSONArray columnNames = SimpleReportService.buildColumnNames(list, "CustomerPersonalAction_reportListPagerCustomerNew");
        json.put("columnNames", columnNames);


        /**
         * 构造数据
         */
        JSONObject tableJson = new JSONObject();
        JSONArray dataArray = new JSONArray();
        for (int i = 0; list != null && i < list.size(); i++) {
            KVObjects kvObjects = list.get(i);

            JSONObject rowData = new JSONObject();
            for (int j = 0; j < kvObjects.size(); j++) {
                KVObject kv = kvObjects.get(j);
                rowData.put(kv.getKey().toString(), kv.getValue().toString());
            }
            dataArray.add(rowData);
        }
        tableJson.put("total", dataArray.size());
        tableJson.put("rows", dataArray);
        json.put("data", tableJson);

        getResult().setReturnValue(json.toString());

        return SUCCESS;
    }

    /**
     * 重载修改密码方法
     *
     * @param newPassword 新密码
     * @param conn        数据库连接
     * @return 成功返回true 失败返回false
     * @throws Exception
     */
    public boolean resetPassword(String newPassword, Connection conn) throws Exception {
        //获取当前登陆人信息
        Object id = getRequest().getSession().getAttribute("loginUserId");

        if (id == null || newPassword == null) {
            return false;
        } else {
            String CustomerID = (String) id;
        /*加入特定的字符 再进行MD处理*/
            newPassword = StringUtils.md5(newPassword + Config.MD5String);

            int count = 0;
            count = customerPersonalService.resetPassword(CustomerID, newPassword, Config.getSystemConfig("web.default.operatorId"), conn);

            //返回影响行数为一
            if (count == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建人：张舜清
     * 时间：7/13/2015
     * 内容：忘记密码根据选择验证类型跳转对应页面
     *
     * @return
     * @throws Exception
     */
    public String resetPS4VerifyMobileCode() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        String verifyMobileCode = request.getParameter("mobiValidateCode");
        Connection conn = this.getConnection();

        if (session.getAttribute(Config4W.TEMP_SESSION_LOGINUSER_STRING) == null) {
            return "forgetPassword";
        }

        CustomerPersonalPO customerPersonal = (CustomerPersonalPO) session.getAttribute(Config4W.TEMP_SESSION_LOGINUSER_STRING);
        if (StringUtils.isEmpty(customerPersonal.getMobile())) {
            getResult().setMessage("该用户没有手机号码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        if (StringUtils.isEmpty(verifyMobileCode)) {
            getResult().setMessage("请输入动态码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        CustomerAuthenticationCodePO codePO = VarificationUtils.viladateMobileCode(verifyMobileCode, customerPersonal.getMobile());

        if (codePO != null) {
            // 验证时间是否失效
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date expiredTime = formatter.parse(codePO.getExpiredTime().substring(0, 10));
            Date now = new Date();
            if (expiredTime.getTime() > now.getTime()) {
                // 修改认证码的认证时间
                codePO.setAuthenticationTime(TimeUtils.getNow());
                MySQLDao.update(codePO, getConnection());
                // 新增或修改认证状态
                CustomerAuthenticationStatusPO statusPO = customerAuthenticationStatusService.loadByCustomerId(customerPersonal.getId(), getConnection());
                if (statusPO == null) {
                    statusPO = new CustomerAuthenticationStatusPO();
                    statusPO.setCustomerId(customerPersonal.getId());
                }
                statusPO.setMobileStatus(1);
                statusPO.setMobileTime(TimeUtils.getNow());
                // 网站用户
                UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), getConnection());
                customerAuthenticationStatusService.insertOrUpdate(statusPO, user, getConnection());
                // 组建sql查询该客户是否正确
                StringBuffer sqlDB = new StringBuffer();
                sqlDB.append(" SELECT ");
                sqlDB.append("    * ");
                sqlDB.append(" FROM ");
                sqlDB.append("    crm_customerpersonal ");
                sqlDB.append(" WHERE ");
                sqlDB.append("    1 = 1 ");
                sqlDB.append(" AND state = 0 ");
                sqlDB.append(" AND id = '" + customerPersonal.getId() + "' ");
                List<CustomerPersonalPO> list = MySQLDao.query(sqlDB.toString(), CustomerPersonalPO.class, null, getConnection());
                if (list != null && list.size() > 0) {
                    return SUCCESS;
                } else {
                    // 查询不到该客户的相关电话信息
                    getResult().setMessage("该用户没有绑定手机号码");
                    getRequest().setAttribute("returnObject", getResult());
                    return "info";
                }
            }

        } else {
            // 验证码返回错误信息
            getResult().setMessage("动态码错误，请重新输入");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，获取到当前登录人在页面所需展示的信息
     * 前提是网站的 Customer 已经登录，通过 Session 获取到登录的人，然后查询客户资金等表
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于网站交易平台的 json
     * @throws Exception
     * @author 邓超
     * <p/>
     * * 修改人：姚章鹏
     * 时间：2015年7月13日17:39:44
     * 内容：计算等级
     *
     * 修改人：quan.zeng
     * 时间：2015年12月6日
     * 内容：计算等级代码抽取到service中
     *
     */
    @Security(needWebLogin = true)
    public String indexShow4W() throws Exception {
        HttpServletRequest request = getRequest();
        Connection conn = this.getConnection();
        CustomerPersonalPO customer = Config.getLoginCustomerInSession(getRequest());

        // 查询客户资金信息
        CustomerMoneyPO customerMoney = new CustomerMoneyPO();
        customerMoney.setCustomerId(customer.getId());
        customerMoney.setState(Config.STATE_CURRENT);
        customerMoney = MySQLDao.load(customerMoney, CustomerMoneyPO.class, conn);
        getRequest().getSession().setAttribute("customerMoney", customerMoney);

        // 安全等级
        int sum = customerAuthenticationStatusService.calSecurtyLevel(customer.getId(), conn);

        getRequest().getSession().setAttribute("sum", sum);
        // 查询推荐项目
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ProductionWVO.class);
        String projectNames = getRequest().getParameter("ProjectNames");
        if (projectNames == null) {
            projectNames = "";
        }
        return SUCCESS;
    }


    //添加，修改
    // @Permission(require = "客户管理-个人客户管理-客户添加修改")
    public String insertOrUpdate() throws Exception {

        CustomerPersonalPO customerPersonalPO = HttpUtils.getInstanceFromRequest(getRequest(), "personal", CustomerPersonalPO.class);

        // 被隐藏的号码，不予修改
        if (!StringUtils.isEmpty(customerPersonalPO.getMobile()) && customerPersonalPO.getMobile().contains("****")) {
            customerPersonalPO.setMobile(null);
        }

        CustomerPersonalPO customerPersonalCheck = customerPersonalService.loadByCustomerPersonalId(customerPersonalPO.getId(), getConnection());

        if (customerPersonalCheck != null && !StringUtils.isEmpty(customerPersonalCheck.getMobile()) && !customerPersonalCheck.getMobile().equals(customerPersonalPO.getMobile())) {
            if (!hasPermission("客户管理-个人客户管理-客户电话号码修改")) {
                MyException.newInstance("您没有修改客户电话号码的权限").throwException();
            }
        }


        Connection conn = getConnection();
        // 保存基本信息
        customerPersonalService.insertOrUpdate(customerPersonalPO, getLoginUser().getId(), getConnection());

        // 新增或修改认证状态
        customerPersonalService.saveCustomerAuthenticationStatus(customerPersonalPO, conn);

        return SUCCESS;
    }

    public String listPagerCustomerVO() throws Exception {

        Pager pager = Pager.getInstance(getRequest());

        CustomerPersonalVO customerVO = new CustomerPersonalVO();

        String customerName = getHttpRequestParameter("customerName");
        String customerMobile = getHttpRequestParameter("customerMobile");

        customerVO.setName(customerName);
        customerVO.setMobile(customerMobile);

        if (!getPermission().has("销售管理_订单管理_财务核对")) {
            customerVO.setSaleManId(getLoginUser().getId());
        }



        pager = customerPersonalService.listPagerCustomerVO(customerVO, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }


    public String updateModern() throws Exception {

        personal = HttpUtils.getInstanceFromRequest(getRequest(), "personal", CustomerPersonalPO.class);

        Connection conn = getConnection();

        // 客户号不能删除
        personal.setPersonalNumber(null);
        personal.setLoginName(null);
        personal.setState(Config.STATE_CURRENT);
        personal.setCustomerCatalogId(null);


        if (!StringUtils.isEmpty(personal.getId())) {
            CustomerPersonalPO tempCustomer = customerPersonalService.loadByCustomerPersonalId(personal.getId(), conn);

            if (tempCustomer == null) {
                MyException.newInstance("数据异常", "customerId=" + personal.getId()).throwException();
            }

            if (!personal.getName().equals(tempCustomer.getName()) && customerPersonalService.isCustomerCatalogConfirmed(tempCustomer)) {
                MyException.newInstance("客户身份已确认，【姓名】暂不允许修改", "customerId=" + personal.getId()).throwException();
            }

            if (!personal.getMobile().equals(tempCustomer.getMobile()) && customerPersonalService.isCustomerCatalogConfirmed(tempCustomer)) {
                MyException.newInstance("客户身份已确认，【手机号】暂不允许修改", "customerId=" + personal.getId()).throwException();
            }
        }
        else {
            personal.setCustomerCatalogId(CustomerCatalog.Template);
        }



        // 保存基本信息
//        customerPersonalService.insertOrUpdate(personal, getLoginUser().getId(), conn);
        customerPersonalService.updateModern(personal, getLoginUser().getReferralCode(), conn);
        return SUCCESS;
    }

    public String deleteModern() throws Exception {

        personal = HttpUtils.getInstanceFromRequest(getRequest(), "personal", CustomerPersonalPO.class);

        Connection conn = getConnection();


        if (!StringUtils.isEmpty(personal.getId())) {
            CustomerPersonalPO tempCustomer = customerPersonalService.loadByCustomerPersonalId(personal.getId(), conn);

            if (tempCustomer != null && tempCustomer.getCustomerCatalogId() != null && tempCustomer.getCustomerCatalogId().equals(CustomerCatalog.Confirmed)) {
                MyException.newInstance("客户身份已确认，暂不允许删除", "customerId=" + personal.getId()).throwException();
            }

            if (tempCustomer != null && tempCustomer.getCustomerCatalogId() == null) {
                MyException.newInstance("客户身份已确认，暂不允许删除", "customerId=" + personal.getId()).throwException();
            }


            customerPersonalService.removeByCustomerPersonalId(personal.getId(), getLoginUser().getId(), conn);

        }

        return SUCCESS;
    }



    //密码修改
    public String passwordUpdate() throws Exception {

        String userId = Config.getDefaultOperatorId();
        if (getLoginUser() != null) {
            userId = getLoginUser().getId();
        }

        personal = HttpUtils.getInstanceFromRequest(getRequest(), "personal", CustomerPersonalPO.class);

        customerPersonalService.passwordUpdate(personal, userId, getConnection());

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    //原密码验证

    public String checkPassword() throws Exception {
        String beforePassword = StringUtils.md5(personal.getPassword());
        /*加入特定的字符 再进行MD处理*/
        beforePassword = StringUtils.md5(beforePassword + Config.MD5String);
        CustomerPersonalPO temp = new CustomerPersonalPO();
        temp.setSid(personal.getSid());
        temp = MySQLDao.load(temp, CustomerPersonalPO.class);

        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        if (beforePassword.equals(temp.getPassword())) {
            json.element("successful", "1");
        } else {
            json.element("successful", "0");
        }
        array.add(json);
        getResult().setReturnValue(array);
        return SUCCESS;
    }



    //原密码验证

    /**
     * 修改:周海鸿
     * 时间：2015-7-23
     * 内容：重载原密码验证
     *
     * @param password 原密码
     * @param conn     数据库连接
     * @return 成功返回 true 失败返回false
     * @throws Exception
     */
    public boolean checkPassword(String password, Connection conn) throws Exception {
        //获取当前登录人的心性
        Object loginName = getRequest().getSession().getAttribute("loginUserName");
        /*加入特定的字符 再进行MD处理*/
        String beforePassword = StringUtils.md5(password + Config.MD5String);

        // 查询用户信息
        String sql = "select * from crm_customerpersonal p where p.state = 0 and p.loginName = '" + loginName + "' and  p.password='" + beforePassword + "'";
        List<CustomerPersonalPO> list = MySQLDao.query(sql, CustomerPersonalPO.class, new ArrayList<KVObject>(), conn);

        //根据条件获取不到用户信息
        if (list.size() == 0) {
            return false;
        }
        return true;
    }

    public String add() throws Exception {
        //获得个人客户编号
        String s = this.getSequence();
        System.out.println(s);
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        json.element("personalNumber", s);
        json.element("creatTime", TimeUtils.getNow());
        array.add(json);
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    /**
     * 获得个人客户编号
     *
     * @return
     * @throws Exception
     */
    public String getSequence() throws Exception {
        //序列
        int sequence = MySQLDao.getSequence("customerCode");
        //补位
        return "04" + StringUtils.buildNumberString(sequence, 8);
    }

    // 删除
    public String delete() throws Exception {

        // personal.id

        String customerPersonalId = getHttpRequestParameter("personal.id");

        customerPersonalService.removeByCustomerPersonalId(customerPersonalId, getLoginUser().getId(), getConnection());

        return SUCCESS;
    }

    //读取
    public String load() throws Exception {
        // int sid = Integer.valueOf(getRequest().getParameter("personal.sid"));
        personal.setState(Config.STATE_CURRENT);
        // personal.setSid(sid);
        personal = MySQLDao.load(personal, CustomerPersonalPO.class);

        if (getPermission().has("客户管理_个人客户管理_呼叫中心查看")) {
            String mobile = personal.getMobile();
            personal.setMobile(StringUtils.maskMobile(mobile));
            personal.setMobileNotMasked(mobile);
        }

        getResult().setReturnValue(personal.toJsonObject4Form());
        return SUCCESS;
    }


    /**
     * 列出登录用户所能操作的个人客户列表
     * <p/>
     * 修改人：李昕骏
     * 修改日期：2015年8月19日 08:39:22
     * 修改内容
     * 调整查询顺序，高权限的查询放在前面
     * <p/>
     * 修改人：李昕骏
     * 修改日期：2015年8月18日 15:08:45
     * 修改内容
     * 增加客服权限判断，客服人员可以看到所属公司的客户
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        List<KVObject> conditions = new ArrayList<KVObject>();
        

        Pager pager = Pager.getInstance(request);


        if (getPermission().has("客户管理_个人客户管理_查看所有用户")) {
            pager = customerPersonalService.listPagerCustomerVO(personalVO, pager.getCurrentPage(), pager.getShowRowCount(), conn);
        }
        // 团队负责人和本团队的客服，可以看到所有的客户
        // 客户管理_个人客户管理_管理员查看
        // 客户管理_个人客户管理_呼叫中心查看
        else if (getPermission().has("客户管理_个人客户管理_管理员查看") || getPermission().has("客户管理_个人客户管理_呼叫中心查看")) {
            pager = customerPersonalService.listCustomrs4DistributionToManagedSaleGroup(personalVO, conditions, pager.getCurrentPage(), pager.getShowRowCount(), getLoginUser(), conn);
        }

        // 普通销售人员只能查看分配给自己的客户
        // 客户管理_个人客户管理_销售人员查看
        else if (getPermission().has("客户管理_个人客户管理_销售人员查看")) {
            pager = customerPersonalService.listCustomrs4DistributionToMe(personalVO, conditions, pager.getCurrentPage(), pager.getShowRowCount(), getLoginUser());
        }


        // 呼叫中心人员权限，去除敏感信息
        boolean isCallCenterPermission = getPermission().has("客户管理_个人客户管理_呼叫中心查看");
        List<IJsonable> customers = pager.getData();
        for (IJsonable data : customers) {
            CustomerPersonalVO customer = (CustomerPersonalVO)data;

            if (isCallCenterPermission) {
                String mobile = customer.getMobile();
                customer.setMobile(StringUtils.maskMobile(mobile));
            }

            String customerType = customer.getCustomerTypeId();
            customer.setCustomerTypeName(CustomerPersonalType.getCustomerPersonalTypeName(customerType));
        }


        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月16日10:35:45
     * 内容：根据客户的id查询出对应兑付数据列表
     *
     * @return
     * @throws Exception
     */
    private PaymentPlanVO paymentPlanVO = new PaymentPlanVO();

    public String paymentPlanQuery() throws Exception {
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        List<KVObject> conditions = new ArrayList<KVObject>();
        String customerId = request.getParameter("customerId");
        Pager pager = customerPersonalService.paymentPlanQuery(paymentPlanVO, conditions, customerId, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月16日10:35:45
     * 内容：根据客户的id查询出对应产品数据列表
     *
     * @return
     * @throws Exception
     */
    private ProductionVO productionVO = new ProductionVO();

    public String productionQuery() throws Exception {
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        List<KVObject> conditions = new ArrayList<KVObject>();
        String customerId = request.getParameter("customerId");
        Pager pager = customerPersonalService.productionQuery(productionVO, conditions, customerId, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    private OrderVO orderVO = new OrderVO();

    /**
     * 创建人：张舜清
     * 时间：2015年9月16日14:51:07
     * 内容：根据客户的id查询出对应订单数据列表
     *
     * @return
     * @throws Exception
     */
    public String orderQuery() throws Exception {
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = getConnection();
        String customerId = request.getParameter("customerId");
        Pager pager = customerPersonalService.orderQuery(orderVO, conditions, customerId, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月16日14:51:07
     * 内容：根据客户的id查询出对应资金日志数据列表
     *
     * @return
     * @throws Exception
     */
    public String customerMoneyLogQuery() throws Exception {
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = getConnection();
        String customerId = request.getParameter("customerId");

        Pager pager = customerMoneyLogService.customerMoneyLogQuery(customerId, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String getCustomerPersonalAssignedByUserId() throws Exception {

        String userId = HttpUtils.getParameter(getRequest(), "userId");

        List<CustomerPersonalPO> customerPersonals = new ArrayList<CustomerPersonalPO>();

        customerPersonals = customerPersonalService.getCustomerPersonalAssignedByUserId(userId, getConnection());

        getRequest().setAttribute("customerPersonals", customerPersonals);

        return SUCCESS;
    }

    /**
     * 获取需要导出的列表数据
     *
     * @return
     * @author David
     */
    public List getCustomerPersonalListExport() throws Exception {

        HttpServletRequest request = getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = getConnection();

        Pager pager = Pager.getInstance(request);
        // 团队负责人和本团队的客服，可以看到所有的客户
        // 客户管理_个人客户管理_管理员查看
        // 客户管理_个人客户管理_呼叫中心查看
        if (getPermission().has("客户管理_个人客户管理_管理员查看") || getPermission().has("客户管理_个人客户管理_呼叫中心查看")) {
            pager = customerPersonalService.listCustomrs4DistributionToManagedSaleGroup(personalVO, conditions, pager.getCurrentPage(), 30000, getLoginUser(), conn);
        }

        // 普通销售人员只能查看分配给自己的客户
        // 客户管理_个人客户管理_销售人员查看
        else if (getPermission().has("客户管理_个人客户管理_销售人员查看")) {
            pager = customerPersonalService.listCustomrs4DistributionToMe(personalVO, conditions, pager.getCurrentPage(), 30000, getLoginUser());
        }

        List list = pager.getData();
        return list;
    }

    /**
     * 导出前台列表为excel文件
     *
     * @author David
     */
    public void export() throws IOException {

        //设置日期格式
        Date d = new Date();
        long longTime = d.getTime();
        //输出Excel的文件名称
        String fileName = "个人客户管理_" + longTime;
        //转换输出格式防止乱码
        fileName = new String(fileName.getBytes("gb2312"), "iso8859-1");

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/octet-stream");

        //设置Excel导出文件名称
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        //获取输出流
        ServletOutputStream out = response.getOutputStream();

        //创建Excel
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("个人客户信息");  //Excel导出sheet页签名称

        try {
            //获取导出数据
            List list = getCustomerPersonalListExport();

            if (null != titles && null != fields) {
                // titles = new String(titles.getBytes("ISO-8859-1"), "UTF-8");
                ExportUtils.outputHeaders(titles.split(","), sheet);
                ExportUtils.outputColumns(fields.split(","), list, sheet, 1);
            }

            //获取输出流，写入excel 并关闭
            wb.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {

            //异常情况下判断流并关闭
            if (null != out) {
                out.flush();
                out.close();
            }
            e.printStackTrace();
        }

    }

    /**
     * 交易平台请求的 Action，获取到当前登录人的个人基本信息和资金信息
     * 前提是网站的 Customer 已经登录，通过 Session 获取到登录的人，然后查询客户表、资金表等
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String getInfo4Web() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUserId") != null && session.getAttribute("loginUserName") != null) {
            String id = session.getAttribute("loginUserId").toString();
            String name = session.getAttribute("loginUserName").toString();
            List<KVObject> conditions = new ArrayList<KVObject>();
            Pager pager = customerPersonalService.loadCustomerInfo4Web(id, personalVO, conditions, request);
            getResult().setReturnValue(pager.toJsonObject());
        }
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，获取到当前登录人的个人基本信息和资金信息（经过安全处理）
     * 前提是网站的 Customer 已经登录，通过 Session 获取到登录的人，然后查询客户表、资金表等
     * 经过了安全处理，会查询到客户的密码和交易密码等，查询到后在这里后期对他们进行改值，再显示到页面上
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String getInfo4WebSafly() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUserId") != null && session.getAttribute("loginUserName") != null) {
            String id = session.getAttribute("loginUserId").toString();
            String name = session.getAttribute("loginUserName").toString();
            List<KVObject> conditions = new ArrayList<KVObject>();
            Pager pager = customerPersonalService.loadCustomerInfo4Web(id, personalVO, conditions, request);
            // 后期处理
            List<IJsonable> datas = pager.getData();
            for (IJsonable data : datas) {
                data = (CustomerPersonalVO) data;
                if (((CustomerPersonalVO) data).getTransactionPassword() != null && !((CustomerPersonalVO) data).getTransactionPassword().equals("")) {
                    ((CustomerPersonalVO) data).setTransactionPassword("1");    // 传输到前台的交易密码的值变成 1
                } else {
                    ((CustomerPersonalVO) data).setTransactionPassword("0");
                }
                //将手机号取出来
                String mobile = ((CustomerPersonalVO) data).getMobile();
                //替换中间四位数
                String phone = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
                ((CustomerPersonalVO) data).setMobile(phone);
            }
            getResult().setReturnValue(pager.toJsonObject());
        }
        return SUCCESS;
    }



    /**
     * 创建人：张舜清
     * 时间：8/13/2015
     * 内容：验证手机动态是否正确
     *
     * @return
     * @throws Exception
     */
    public String verificationMobileCode() throws Exception {

        // 获取请求与连接
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();

        // 获取手机号码和动态码
        String code = request.getParameter("mobileValidateCode");
        String mobile = request.getParameter("mobile");

        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)) {
            getResult().setReturnValue("参数有误");
            return SUCCESS;
        }

        // 验证手机号码是否已经注册
        Boolean isRegistered = customerPersonalService.isMobileRegistered(mobile, conn);
        if (!isRegistered) {
            getResult().setReturnValue("该手机号未注册");
            return SUCCESS;
        }

        // 验证手机动态码
        CustomerAuthenticationCodePO codePO = VarificationUtils.viladateMobileCode(code, mobile);

        if (codePO == null) {
            getResult().setReturnValue("手机动态码输入有误");
            return SUCCESS;
        }

        // 返回前端
        return SUCCESS;
    }

    /**
     *
     * 修改人：李昕骏
     * 时间：2015年11月21日 11:08:52
     * 内容：
     * 将用户注册的用户归属状态改为在线客户
     *
     * 修改人：张舜清
     * 时间：7/30/2015
     * 内容：传统注册，传统注册通道先暂时关闭
     *
     * @return
     * @throws Exception
     */
    public String register4W() throws Exception {

        String exccptionLocation = "CustomerPersonalAction.register4W()";

        Connection conn = getConnection();

        String referralCode = getHttpRequestParameter("referralCode");

        try {

            String mobile = personal.getLoginName();

            // 检查用户名是否可用
            boolean isMobileTaken = customerPersonalService.isMobileRegistered(mobile, conn);

            // 用户名是否被占用
            if (isMobileTaken) {
                LogPO log = new LogPO();
                MyException.newInstance("【" + personal.getLoginName() + "】用户名已被占用").addLocation(exccptionLocation).throwException();
            }


            /**
             * 执行注册
             */
            LogPO logPO = new LogPO();
            logPO.setMachineMessage("用户【"+mobile+"】注册，推荐码【"+referralCode+"】，渠道【PC端】");
            customerPersonalService.registerCustomer(personal, referralCode, conn);
            getResult().setMessage("注册成功");
            getRequest().setAttribute("returnObject", getResult());


            // 自动登录
            HttpSession session = getRequest().getSession();
            session.setAttribute("loginUserId", personal.getId());
            session.setAttribute("loginUserName", personal.getLoginName());
            session.setAttribute(Config.SESSION_LOGINUSER_STRING, personal);

            String authUrl = "/w2/customer/getCustomerCertificate";
            getRequest().setAttribute("url", "/w2/customer/autoLogin?url=" + authUrl);

            return "success";

        }
        catch (MyException me) {
            me.printStackTrace();
            // 处理异常
            me.findLog().setPeopleMessage(me.getMessage());
            me.findLog().setIP(Config.getIP(getRequest()));
            me.deal();

            // 返回错误信息到界面
            getResult().setMessage("注册失败，" + me.getMessage());
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        catch (Exception e) {
            e.printStackTrace();
            getResult().setMessage("注册失败，" + e.getMessage());
            getRequest().setAttribute("returnObject", getResult());
            MyException.newInstance(e.getMessage()).addConnection(conn).addLocation("CustomerPersonalAction.register4W()").deal();
            return "info";
        }
    }


    /**
     * 移动端注册客户
     * @return
     * @throws Exception
     */
    public String registerModern() throws Exception {

        Connection conn = getConnection();

        personal = HttpUtils.getInstanceFromRequest(getRequest(), CustomerPersonalPO.class);

        String referralCode = getHttpRequestParameter("referralCode");
        String mobile = personal.getMobile();
        String mobileCode = getHttpRequestParameter("mobileCode");
        CustomerAuthenticationCodePO authenticationCodePO = VarificationUtils.viladateMobileCode(mobileCode, mobile);

        if (authenticationCodePO == null) {
            MyException.newInstance("验证码输入有误").throwException();
        }

        // 检查用户名是否可用
        boolean isMobileTaken = customerPersonalService.isMobileRegistered(mobile, conn);

        // 用户名是否被占用
        if (isMobileTaken) {
            MyException.newInstance("【" + personal.getMobile() + "】用户名已被占用").throwException();
        }



        personal = customerPersonalService.registerCustomer(personal, referralCode, conn);


        /**
         * 绑定微信号
         */
        String userInfoId = getHttpRequestParameter("userInfoId");
        if (!StringUtils.isEmpty(userInfoId)) {
            weChatService.bindCustomer(personal.getId(), userInfoId, conn);
        }

        // 自动登录
        HttpSession session = getRequest().getSession();
        session.setAttribute("loginUserId", personal.getId());
        session.setAttribute("loginUserName", personal.getLoginName());
        session.setAttribute(Config.SESSION_LOGINUSER_STRING, personal);

        getResult().setReturnValue("1");

        return SUCCESS;

    }

    public String registerModernByUser() throws Exception {

        Connection conn = getConnection();

        personal = HttpUtils.getInstanceFromRequest(getRequest(), CustomerPersonalPO.class);
        personal.setCustomerCatalogId(CustomerCatalog.Template);

        String referralCode = getHttpRequestParameter("referralCode");
        if (StringUtils.isEmpty(referralCode)) {
            referralCode = getLoginUser().getReferralCode();
        }

        personal = customerPersonalService.registerCustomer(personal, referralCode, conn);


        getResult().setReturnValue(personal);

        return SUCCESS;

    }


    public String autoLogin() throws Exception {

        String url = HttpUtils.getParameter(getRequest(), "url");
        HttpSession session = getRequest().getSession();

        Connection conn = getConnection();

        String loginUserName = "";
        if (session.getAttribute("loginUserName") != null) {
            loginUserName = (String)session.getAttribute("loginUserName");

            CustomerPersonalService customerPersonalService = Config.getBeanByName("customerPersonalService", CustomerPersonalService.class);
            CustomerPersonalPO customer = customerPersonalService.loadCustomerByLoginName(loginUserName, conn);

            if (customer != null) {
                session.setAttribute("loginUserId", customer.getId());
                session.setAttribute("loginUserName", customer.getLoginName());
                session.setAttribute(Config.SESSION_LOGINUSER_STRING, customer);

                getRequest().setAttribute("url", url);

                return SUCCESS;
            }

        }

        return "info";
    }


    /**
     * 创建人；张舜清
     * 时间：7/29/2015
     * 内容：手机注册页面跳转
     * <p/>
     * 创建人：姚章鹏
     * 时间：2015年8月20日11:46:17
     * 内容：添加会员服务协议内容
     *
     * @return
     * @throws Exception
     */
    public String mobileRequestRegister4W() throws Exception {
        Connection conn = getConnection();
        LegalAgreementPO legalAgreement = legalAgreementService.getAgreementById(Config.getSystemVariable("web.register.lawId"), conn);
        getRequest().setAttribute("legalAgreement", legalAgreement);
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，跳转到注册页面
     * 直接返回 SUCCESS，并在 struts 中配置 resuslt 跳入到注册页面
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return SUCCESS
     * @throws Exception
     * @author 邓超
     */
    public String requestRegister4W() throws Exception {
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，跳转到登录页面
     * 直接返回 SUCCESS，并在 struts 中配置 resuslt 跳入到注册页面
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     * <p/>
     * 修改:周海鸿
     * 内容：前台传入密码加上特性字符串进行加密
     * 时间：2015-7-23
     *
     * @return SUCCESS
     * @throws Exception
     * @author 邓超
     */
    public String requestLogin4W() throws Exception {
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，执行登录
     * 获取到了前台的用户名和密码、验证码等，在后台验证数据并创建会话
     * 这里有一个人性化处理，即登录后就返回登录之前的页面，如果不是本网站跳入登录页面的，则返回用户中心
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * 修改：李昕骏
     * 时间：2015年11月21日 11:48:43
     * 内容
     * 重写网站验证码部分
     *
     * 修改:周海鸿
     * 内容：前台传入密码加上特性字符串进行加密
     * 时间：2015-7-23
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @throws Exception
     * @author 邓超
     * @author 姚章鹏
     * 时间：2015年8月28日17:51:45
     * 内容：如果客户密码错误登录失败，失败达到 5 次后，
     * 通过最后登录时间计算，5 分钟内不能再次登录，过了 5 分钟后可以登录
     */
    @Security(needWebCode = true)
    public String login4W() throws Exception {


        Connection conn = getConnection();

        String loginName = getRequest().getParameter("loginName");
        String password = getRequest().getParameter("password");

        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            MyException.newInstance("用户名和密码输入有误，请检查", "loginName="+loginName+"&password="+password).throwException();
        }

        personal = customerPersonalService.login(loginName, password, conn);

        // 创建会话
        HttpSession session = getRequest().getSession();
        session.setAttribute("loginUserId", personal.getId());
        session.setAttribute("loginUserName", personal.getLoginName());
        session.setAttribute(Config.SESSION_LOGINUSER_STRING, personal);


        // 标识1为成功，重定向到首页
        getResult().setReturnValue("1");

        return SUCCESS;
    }



    /**
     * 登录接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月4日
     *
     * @return
     * @throws Exception
     *
     * 作者：quan.zeng
     * 内容：修改代码
     * 时间：2015年12月4日
     * 描述：token失效时间使用常量配置
     *
     * 作者：quan.zeng
     * 内容：修改代码
     * 时间：2015年12月14日
     * 描述：带出登录用户是否设置交易密码
     *
     * 作者：quan.zeng
     * 内容：修改代码
     * 时间：2015年12月17日
     * 描述：登录成功带出是否实名认证标记
     */
    public String login() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 定义实体对象
        CustomerPersonalPO customerPersonal = null;

        // 获取参数
        String mobile = HttpUtils.getParameter(request, "mobile");
        String password = HttpUtils.getParameter(request, "password");
        // 校验参数合法性
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if(password.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_ENCRYPT, "请将密码加密后进行传输").throwException();
        }
        // 参数双边去空
        mobile = mobile.trim();
        password = password.trim();

        // 校验手机号码和密码
        customerPersonal = customerPersonalService.login(mobile, password, conn);
        if(customerPersonal == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "您的用户名或密码不正确").throwException();
        }

        // 插入新 Token
        TokenPO tokenPO = tokenService.newToken(customerPersonal.getId(), TokenBizType.Customer, request.getRemoteAddr(), conn);
        if(tokenPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "Token 添加失败").throwException();
        }

        // 获取是否设置交易密码
        boolean isSetTransactionPassword = customerPersonalService.isSetTransactionPassword(customerPersonal.getId(),conn);
        // 获取是否设置密保问题
        CustomerProtectionQAService protectionQAService = new CustomerProtectionQAService();
        Pager isSetQAPager = protectionQAService.getQAByCustomerId(customerPersonal.getId(), conn);

        // 是否实名认证
        CustomerCertificatePO certificateInfo = customerCertificateService.getCustomerCertificate(customerPersonal.getId(), conn);

        // 填充返回数据
        JSONObject object = new JSONObject();
        object.put("customerId", customerPersonal.getId());

        // 设置了交易密码
        object.put("isSetTransactionPassword", isSetTransactionPassword ? 1 : 0);

        // 设置了密保问题
        object.put("isSetQA", isSetQAPager.getData().size() > 0 ? 1 : 0);

        // 是否实名认证
        object.put("hasRealName", certificateInfo == null ? 0 : 1);

        object.put("referralCode", customerPersonal.getReferralCode());

        object.put("customerMobile", customerPersonal.getMobile());

        getResult().setReturnValue(object);
        getResult().setToken(tokenPO.getToken());
        getResult().setCode(100);
        getResult().setMessage("登录成功");

        return SUCCESS;
    }

    public String changeCustomerMobileStep1() throws Exception {

        String mobile = getHttpRequestParameter("mobile");
        String mobileCode = getHttpRequestParameter("mobileCode");

        CustomerAuthenticationCodePO authenticationCodePO = VarificationUtils.viladateMobileCode(mobileCode, mobile);

        if (authenticationCodePO == null) {
            MyException.newInstance("验证码输入有误").throwException();
        }

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    public String changeCustomerMobileDoChange() throws Exception {

        CustomerPersonalPO customerPersonalPO = Config.getLoginCustomerInSession(getRequest());

        String mobile = getHttpRequestParameter("mobile");
        String mobileCode = getHttpRequestParameter("mobileCode");

        CustomerAuthenticationCodePO authenticationCodePO = VarificationUtils.viladateMobileCode(mobileCode, mobile);

        if (authenticationCodePO == null) {
            MyException.newInstance("验证码输入有误").throwException();
        }


        customerPersonalPO.setMobile(mobile);

        MySQLDao.insertOrUpdate(customerPersonalPO, getConnection());

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    public String changePasswordByMobieCode() throws Exception {

        Connection conn = getConnection();

        String mobile = getHttpRequestParameter("mobile");
        String mobileCode = getHttpRequestParameter("mobileCode");

        CustomerAuthenticationCodePO authenticationCodePO = VarificationUtils.viladateMobileCode(mobileCode, mobile);

        if (authenticationCodePO == null) {
            MyException.newInstance("验证码输入有误").throwException();
        }


        String password = getHttpRequestParameter("password");

        if (StringUtils.isEmpty(password)) {
            MyException.newInstance("输入的新密码异常").throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadCustomerByMobile(mobile, conn);

        if (customerPersonalPO == null) {
            MyException.newInstance("所输入手机号尚未注册，请重新检查").throwException();
        }

        String newPassword = StringUtils.md5(password + Config.MD5String);
        customerPersonalPO.setPassword(newPassword);

        customerPersonalService.insertOrUpdate(customerPersonalPO, Config.getDefaultOperatorId(), conn);

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    /**
     * 注册接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月5日
     *
     * @return
     * @throws Exception
     */
    public String register() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取请求对象
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String name = getHttpRequestParameter("name");
        String mobile = HttpUtils.getParameter(request, "mobile");
        String mobileCode = HttpUtils.getParameter(request, "mobileCode");
        String password = HttpUtils.getParameter(request, "password");
        String referralCode = HttpUtils.getParameter(request, "referralCode");

        // 校验参数合法性
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(mobileCode)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "系统参数不完整").throwException();
        }

        if(mobile.length() != 11) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "请输入正确的手机号码").throwException();
        }

        // 参数双边去空
        mobile = mobile.trim();
        mobileCode = mobileCode.trim();

        /**
         * 密码处理
         */
        if (StringUtils.isEmpty(password)) {
            password = IdUtils.getUUID32();
        }
        password = password.trim();
        if (password.length() != 32) {
            password = StringUtils.md5(password);
            password = StringUtils.md5(password + Config.MD5String);
        }

        if(!StringUtils.isEmpty(referralCode)) {
            referralCode = referralCode.trim();
        }

        // 校验手机是否被占用
        Boolean isRegistered = customerPersonalService.isMobileRegistered(mobile, conn);
        if(isRegistered) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_IS_REGISTERED, "您的手机号码已被注册，建议您通过找回密码的方式进行密码重置，如果您仍有疑问，请致电客服人员，我们及时地为您处理，联系电话：0755-85024000").throwException();
        }


        tokenService.checkAndDestroyToken(mobileCode, mobile, TokenBizType.MobileCode, Config.getIP(getRequest()), getConnection());


        CustomerPersonalPO customer = new CustomerPersonalPO();
        customer.setName(name);
        customer.setMobile(mobile);
        customer.setPassword(password);
        customer.setCustomerCatalogId(CustomerCatalog.Template);

        CustomerPersonalPO personalPO = customerPersonalService.registerCustomer(customer, referralCode, conn);

        // 生成新的客户资金记录
        List<CustomerMoneyPO> customerMoneyList = customerMoneyService.getByCustomerId4W(personalPO.getId(), conn);
        if(customerMoneyList.size() > 1) {
            MyException.newInstance("抱歉，您的信息有异常，请致电客服人员，我们及时地为您处理，联系电话：0755-85024000").throwException();
        }
        if(customerMoneyList.size() == 0) {
            customerMoneyService.insertEmptyCustomerMoney(personalPO.getId(), conn);
        }

        // 填充返回数据
        getResult().setMessage("注册成功");

        return SUCCESS;

    }

    /**
     * 用户注销
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月29日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String logout() throws Exception {

        // 获取客户 ID
        String customerId = HttpUtils.getParameter(getRequest(), "customerId");

        // 获取 Token
        TokenPO token = this.getToken();
        if(token == null) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_TOKEN_NOT_CORRECT, "请检查参数是否正确！").throwException();
        }

        // 删除客户的 Token
        TokenService tokenService = new TokenService();
        int count = tokenService.cancelToken(token, getConnection());

        String message = "注销成功";
        if (count != 1) {
            message = "注销失败";
        }

        getResult().setMessage(message);
        return SUCCESS;
    }

    /**
     * 获取客户信息接口
     *
     * 作者：quan.zeng
     * 内容：创建代码
     * 时间：2015年12月6日
     *
     * @return
     * @throws Exception
     *
     * 作者：quan.zeng
     * 内容：创建代码
     * 时间：2015年12月6日
     * 描述：一次获取我的账户信息的手机号码、安全等级、总资产并返回，
     *      由于token校验需要查询数据库，如果分三次获取则发出三次请求
     *      每次请求都需要经过校验会影响效率
     */
    @Security(needToken = true)
    public String getCustomerInfo() throws Exception{

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取登录成功后通过校验的 token
        TokenPO tokenPO = this.getToken();
        if(tokenPO == null) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_TOKEN_NOT_CORRECT, "您好！请先登录！").throwException();
        }

        // 取出用户 id
        String customerId = tokenPO.getBizId();

        // 查询客户信息
        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadCustomerById4W(customerId, conn);
        if(customerPersonalPO == null){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "十分抱歉，您还没有注册！").throwException();
        }

        // 查询客户安全等级、客户总资产
        int securityClassSum = customerAuthenticationStatusService.calSecurtyLevel(customerId, conn);

        // 查询客户本金
        Double principalMoney = customerPersonalService.getCustomerTotalPrincipalMoney(customerId, conn);

        // 查询客户的收益（利息）
        double profitMoney = customerPersonalService.getCustomerTotalProfitMoney(customerId, conn);

        // 返回对象
        JSONObject returnValue = new JSONObject();

        // 返回客户的手机号码
        returnValue.put("customerMobile",customerPersonalPO.getMobile());

        // 返回客户的安全级别
        String securityClass = "低";
        if (securityClassSum <= 50) {  securityClass = "低"; }
        else if (securityClassSum > 50 && securityClassSum <= 75) {securityClass = "中";  }
        else if (securityClassSum > 75) { securityClass = "高";}
        returnValue.put("customerSecurityClass",securityClass);

        // 返回客户的总资产
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        returnValue.put("customerTotalMoney",dcmFmt.format(principalMoney + profitMoney));

        // 返回其他信息
        getResult().setToken(tokenPO.getToken());
        getResult().setCode(100);
        getResult().setMessage("获取成功");
        getResult().setReturnValue(returnValue);

        return SUCCESS;
    }

    /**
     * 校验是否设置交易密码接口
     *
     * 作者:quan.zeng
     * 内容：创建代码
     * 时间：2015-12-9
     *
     * @throws Exception
     * @return String
     */
    @Security(needToken = true)
    public String isSetTransactionPassword() throws Exception {

        // 获取参数
        String customerId = HttpUtils.getParameter(getRequest(), "customerId");
        // 获取数据库连接
        Connection conn = this.getConnection();

        // 校验参数
        if(StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        // 校验是否设置了交易密码
        boolean isSet = customerPersonalService.isSetTransactionPassword(customerId,conn);
        if(!isSet){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_TRANDING_PASSWORD_NOT_SET, "未设置交易密码").throwException();
        }
        getResult().setCode(100);
        getResult().setMessage("已设置交易密码");

        return SUCCESS;
    }

    /**
     * 设置或修改交易密码接口
     *
     * 作者:quan.zeng
     * 内容：创建代码
     * 时间：2015-12-9 16：50
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String setOrModifyTransactionPassword() throws Exception {

        // 获取参数
        String customerId = HttpUtils.getParameter(getRequest(), "customerId");
        String mobile = HttpUtils.getParameter(getRequest(), "mobile");
        String transactionPassword = HttpUtils.getParameter(getRequest(), "transactionPassword");
        String reTransactionPassword = HttpUtils.getParameter(getRequest(), "reTransactionPassword");

        // 校验参数
        if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(transactionPassword) || StringUtils.isEmpty(reTransactionPassword)){
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不完整").throwException();
        }
        if(transactionPassword.length() != 32 || reTransactionPassword.length()!=32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_ENCRYPT, "请将密码加密后进行传输").throwException();
        }
        if(!transactionPassword.equals(reTransactionPassword)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PASSWORDS_NOT_SAME, "两次输入的密码不匹配").throwException();
        }

        // 获取数据库连接
        Connection conn = this.getConnection();


        // 判断该手机号是否是该用户的
        CustomerPersonalPO customer = customerPersonalService.loadByCustomerPersonalId(customerId, conn);

        if (customer == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "用户不存在").throwException();
        }



        // 不是第一次设置交易密码，需要验证手机验证码
        if (!StringUtils.isEmpty(customer.getTransactionPassword())) {

            if (!customer.getMobile().equals(mobile)) {
                MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "用户或手机号不匹配").throwException();
            }

            String code = HttpUtils.getParameter(getRequest(), "mobileCode");
            if (!Config.checkMobileCode(mobile, code, conn)) {
                MyException.newInstance(ReturnObjectCode.PUBLIC_MOBILECODE_NOT_CORRECT, "短信验证码输入无效").throwException();
            }
        }


        int count = customerPersonalService.setOrModifyTransactionPassword(customer, transactionPassword, conn);
        if(count != 1){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_TRANDING_PASSWORD_OPERATION_ERROR, "操作失败").throwException();
        }

        getResult().setCode(100);
        getResult().setMessage("操作成功");

        return SUCCESS;
    }

    @Security(needMobileCode = true)
    public String findLoginPassword() throws Exception {
        String mobile = HttpUtils.getParameter(getRequest(), "mobile");
        String password = HttpUtils.getParameter(getRequest(), "password");
        String rePassword = HttpUtils.getParameter(getRequest(), "rePassword");


        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(rePassword)){
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不完整").throwException();
        }

        if(password.length() != 32 || rePassword.length()!=32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_ENCRYPT, "请将密码加密后进行传输").throwException();
        }

        if(!password.equals(rePassword)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PASSWORDS_NOT_SAME, "两次密码不一致").throwException();
        }

        // 获取数据库连接
        Connection conn = this.getConnection();

        int count = customerPersonalService.modifyLoginPassword(mobile,password,conn);

        if(count != 1){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_TRANDING_PASSWORD_OPERATION_ERROR, "操作失败").throwException();
        }

        getResult().setCode(100);
        getResult().setMessage("操作成功");

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，获取到当前登录人的个人基本信息和资金信息
     * 前提是网站的 Customer 已经登录，通过 Session 获取到登录的人，然后查询客户表、资金表等
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String checkUserName4W() throws Exception {
        HttpServletRequest request = getRequest();
        String name = request.getParameter("userName");
        // 处理中文
        String customerName = new String(name.getBytes("ISO8859-1"), "UTF-8");
        if (!StringUtils.isEmpty(customerName)) {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("select loginName from crm_customerpersonal where loginName='" + Database.encodeSQL(customerName) + "' and state=0");
            List list = MySQLDao.query(sbSQL.toString());
            if (list.size() > 0) {
                getResult().setReturnValue("1");
            } else {
                getResult().setReturnValue("0");
            }
        } else {
            getResult().setReturnValue("2");
        }
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，新增邮箱认证码
     * 前提是网站的 Customer 已经登录，会删除相同客户以前所有的邮箱认证码，并生成新的认证码，用于认证邮箱，支持生成 URL 给客户
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String insertEmailValidate4W() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null) {
            if (request.getParameter("address") != null && !request.getParameter("address").equals("")) {
                CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");
                // 修改用户的邮箱，用来准备认证
                String emailAddress = request.getParameter("address").toString();
                Integer count = customerPersonalService.updateEmail(emailAddress, loginUser, getConnection());
                if (count == 1) {
                    // 删除以前的客户认证码
                    AuthenticationCodeService authCodeService = new AuthenticationCodeService();
                    CustomerAuthenticationCodePO authCode = new CustomerAuthenticationCodePO();
                    authCode.setCustomerId(loginUser.getId());
                    authCodeService.delete4W(authCode, getConnection());
                    // 重新生成客户认证码
                    String code = customerPersonalService.insertEmailValidate4W(loginUser, emailAddress, request, getConnection());
                    getResult().setMessage(code + "," + emailAddress);
                    getRequest().setAttribute("returnObject", getResult());
                } else {
                    getResult().setMessage("邮箱修改错误");
                    getRequest().setAttribute("returnObject", getResult());
                    return "info";
                }
            } else {
                getResult().setMessage("电子邮箱地址格式不正确");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }
        } else {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，验证客户认证邮箱时，从邮箱点击的链接进行校验
     * 前提是网站的 Customer 已经登录
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-30
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String validateEmailCallback4W() throws Exception {
        HttpServletRequest request = getRequest();

        String code = request.getParameter("code");
        if (StringUtils.isEmpty(code)) {
            MyException.newInstance("请输入正确的验证码").throwException();
        }

        HttpSession session = request.getSession();
        Connection conn = this.getConnection();

        CustomerPersonalPO loginUser = Config.getLoginCustomerInSession(getRequest());
        ReturnObject returnObject = customerPersonalService.validateEmail(loginUser, code, conn);


        getResult().setCode(returnObject.getCode());
        getResult().setMessage(returnObject.getMessage());

        if (getResult().getCode() == ReturnObject.CODE_DB_EXCEPTION) {
            return  "info";
        }


        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，新增实名认证
     * 前提是网站的 Customer 已经登录，会在 CustomerAuthenticationStatus 表中新增身份的认证记录
     * 在交易平台里，身份认证是不能二次修改的
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     * <p/>
     * 修改人：张舜清
     * 时间：7/14/2015
     * 内容：没有把用户真实姓名存放到客户表，增加存放到客户表
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     * 修改人：姚章鹏
     * 时间：2015年8月13日15:15:07
     * 内容：添加通联实名认证
     */
    @Security(needWebLogin = true)
    public String realAuthentication4W() throws Exception {

        HttpServletRequest request = this.getRequest();
        HttpSession session = request.getSession();

        // 数据库连接
        Connection conn = getConnection();

        // 登录用户信息
        CustomerPersonalPO loginUser = Config.getLoginCustomerInSession(request);

        // 获得待验证真实姓名
        String realName = request.getParameter("realName");
        // 获得待验证身份证号
        String realId = request.getParameter("realId");

        if (StringUtils.isEmpty(realName) || StringUtils.isEmpty(realId)) {
            MyException.newInstance("待验证参数无效，请重新输入", "无法获得姓名和身份证号").throwException();
        }

        // 根据身份证号码查询实名认证表是否已经认证
        boolean hasIdCardTaken = customerCertificateService.hasBeenTaken(loginUser.getId(), AesEncrypt.encrypt(realId), Config.getSystemVariable("web.customer.certificate.idcard.kv.v"), conn);
        if (hasIdCardTaken) {
            MyException.newInstance("您的身份证信息已被绑定，请检查输入的数据是否正确", "身份证号已被绑定").throwException();
        }

        // 这是第三方认证实名认证
        RequestObject requestObject = new RequestObject();
        requestObject.setName("allinpay.realNameVerification");
        requestObject.setToken(Config.getSystemConfig("wsi.token"));
        requestObject.addData("name", realName);
        requestObject.addData("number", realId);
        ServiceInvoker invoker = new ServiceInvoker();
        ReturnObject returnObject = invoker.invoke(requestObject);

        boolean isPassRealAuth = false;
        if (returnObject != null) {
            isPassRealAuth = returnObject.getReturnValue().equals("1") ? true : false;
        }

        if (!isPassRealAuth) {
            MyException.newInstance("没有通过实名认证，请检查数据是否填写正确", "没有通过实名认证").throwException();
        }

        // 身份认证通过后，姓名以认证通过后的为准，因此修改
        customerPersonalService.updateCustomerRealName(loginUser.getId(), realName, conn);

        // 设置用户信息
        session.setAttribute("loginUser", loginUser);

        UserPO operator = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);

        // 新增客户的身份证信息
        customerCertificateService.insertOrUpdateCertificate(loginUser.getId(), Config.Type_PeopleId, realId, operator, conn);

        // 新增或修改认证状态
        customerAuthenticationStatusService.saveAuthenticationStatus(loginUser.getId(), CustomerAuthenticationStatus.AUTH_TYPE_ACCOUNT, conn);

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，设置交易密码
     * 前提是网站的 Customer 已经登录，如果有原密码则先验证原密码，如果没有则直接新增交易密码
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String transPassword4W() throws Exception {

        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = this.getConnection();

        // 当前登录用户
        if (session.getAttribute("loginUser") == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");
        loginUser = customerPersonalService.loadByCustomerPersonalId(loginUser.getId(), conn);
        if(loginUser == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有找到客户").throwException();
        }

        if (transPasswordNeedMobile) {
            // 校验手机动态码是否为空
            if (request.getParameter("mobiValidateCode") == null || "".equals(request.getParameter("mobiValidateCode"))) {
                getResult().setMessage("请输入手机动态码");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            // 校验手机号码是否为空
            if (request.getParameter("mobile") == null || "".equals(request.getParameter("mobile"))) {
                getResult().setMessage("请输入手机号码");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            String code = request.getParameter("mobiValidateCode");
            String mobile = request.getParameter("mobile");

            // 检测手机是否注册
            Boolean isRegistered = customerPersonalService.isMobileRegistered(mobile, conn);
            if (!isRegistered) {
                getResult().setMessage("请确认手机号属于您本人，如果更换了手机号码请重新绑定。");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            // 验证手机动态码是否正确
            if (VarificationUtils.viladateMobileCode(code, mobile) == null) {
                getResult().setMessage("手机动态码不正确");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }
        }


        // 获取并校验交易密码
        String transPassword = request.getParameter("transPassword");
        String transPasswordAgain = request.getParameter("transPasswordAgain");

        if (transPassword == null || "".equals(transPassword) || transPasswordAgain == null || "".equals(transPasswordAgain)) {
            getResult().setMessage("请输入两次交易密码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 修改交易密码
        if (transPassword.equals(transPasswordAgain)) {
            // 创建操作者用户
            UserPO userPO = new UserPO();
            userPO.setId(Config.getSystemConfig("web.default.operatorId"));
            // md5 加密
            transPassword = Utils.md5(transPassword);
            // 再次调用加密规则加密
            loginUser.setTransactionPassword(StringUtils.md5(transPassword + Config.MD5String));
            customerPersonalService.insertOrUpdate(loginUser, userPO.getId(), conn);
        } else {
            getResult().setMessage("两次密码不一致");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，生成手机的客户认证码
     * 前提是网站的 Customer 已经登录，如果有原密码则先验证原密码，如果没有则直接新增交易密码
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String insertMobiValidate4W() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = getConnection();

        if (session.getAttribute("loginUser") != null) {
            CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");
            // 删除以前的客户认证码
            AuthenticationCodeService authCodeService = new AuthenticationCodeService();
            CustomerAuthenticationCodePO authCode = new CustomerAuthenticationCodePO();
            authCode.setCustomerId(loginUser.getId());
            authCodeService.delete4W(authCode, conn);
            // 随机生成 6 位不重复的数字
            int result = NumberUtils.randomNumbers(6);
            // 重新生成客户认证码
            String code = customerPersonalService.insertMobiValidate4W(loginUser, result, conn);
            // 成功后去调用System_Sms服务，插入数据

            // 设置Sms需要字段
            String id = loginUser.getId();
            String name = loginUser.getLoginName();

            String mobile = loginUser.getMobile();
            if (request.getParameter("mobile") != null && !"".equals(request.getParameter("mobile"))) {
                mobile = request.getParameter("mobile");
            }

            Integer type = SmsType.TYPE_IDENTIFY_CODE;
            String subject = "获取动态码";
            String content = Config.getSystemVariable("web.code.view.content.before") + code
                    + Config.getSystemVariable("web.code.view.content.after");
            String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");
            smsService.insertSMS(id, name, mobile, subject, content, signature, type, conn);

            getResult().setMessage(code + "," + result);
            getRequest().setAttribute("returnObject", getResult());
        } else {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        getResult().setMessage("OK");
        request.setAttribute("returnObject", getResult());
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：7/13/2015
     * 内容：忘记密码获取手机动态码调用的action
     *
     * @return
     * @throws Exception
     */
    public String insertMobileCode4W() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = getConnection();

        if (session.getAttribute(Config4W.TEMP_CUSTOMER_ID) == null || session.getAttribute(Config4W.TEMP_CUSTOMER_LoginName) == null) {
            MyException.deal(new Exception(getClass().getName() + " 非法操作，session参数或者URL请求参数为空"));
        } else {
            String loginUserID = session.getAttribute(Config4W.TEMP_CUSTOMER_ID).toString();
            String loginName = session.getAttribute(Config4W.TEMP_CUSTOMER_LoginName).toString();
            String mobile = customerPersonalService.getMobile(loginUserID, getConnection());

            // 删除以前的客户认证码
            AuthenticationCodeService authCodeService = new AuthenticationCodeService();
            CustomerAuthenticationCodePO authCode = new CustomerAuthenticationCodePO();
            authCode.setCustomerId(loginUserID);
            authCodeService.delete4W(authCode, getConnection());
            // 随机生成 6 位不重复的数字
            int result = NumberUtils.randomNumbers(6);
            // 重新生成客户认证码
            String code = customerPersonalService.insertMobiValidate4W(loginUserID, result, request, getConnection());
            getResult().setMessage(code + "," + result);
            getRequest().setAttribute("returnObject", getResult());
            // 成功后去调用System_Sms服务，插入数据
            SmsService smsService = new SmsService();
            String id = loginUserID;
            String name = loginName;
            String subject = "获取动态码";
            String content = Config.getSystemVariable("web.code.view.content.before") + code
                    + Config.getSystemVariable("web.code.view.content.after");
            Integer type = SmsType.TYPE_IDENTIFY_CODE;
            String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");
            smsService.insertSMS(id, name, mobile, subject, content, signature, type, conn);
        }
        getResult().setMessage("OK");
        request.setAttribute("returnObject", getResult());

        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：7/29/2015
     * 内容：重载一个为注册获取验证码使用的action方法
     *
     * @return
     * @throws Exception
     */
    public String mobileRegisterCode() throws Exception {

        Connection conn = getConnection();
        HttpServletRequest request = getRequest();
        String mobileRegister = request.getParameter("mobile");

        if (StringUtils.isEmpty(mobileRegister)) {
            getResult().setMessage("请输入手机号码");
            request.setAttribute("returnObject", getResult());
            return "info";
        }

        // 删除以前的客户认证码
        CustomerAuthenticationCodePO authCode = new CustomerAuthenticationCodePO();
        authCode.setMobile(mobileRegister);
        authenticationCodeService.delete4W(authCode, conn);

        // 随机生成 6 位不重复的数字
        int result = NumberUtils.randomNumbers(6);
        // 重新生成客户认证码
        String code = customerPersonalService.insertMobileValidate4W(mobileRegister, result, conn);
        getResult().setMessage(code + "," + result);
        getRequest().setAttribute("returnObject", getResult());

        // 成功后去调用System_Sms服务，插入数据
        SmsService smsService = new SmsService();
        String id = "WEB注册";
        String name = "WEB注册";
        String subject = Config.getSystemVariable("system.oa.sms.identityCode.subject");
        String content = Config.getSystemVariable("web.code.view.content.before") + code + Config.getSystemVariable("web.code.view.content.after");
        Integer type = SmsType.TYPE_IDENTIFY_CODE;
        String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");
        smsService.insertSMS(id, name, mobileRegister, subject, content, signature, type, conn);

        getResult().setMessage("OK");
        request.setAttribute("returnObject", getResult());
        return SUCCESS;
    }

    /**
     * 创建人：姚章鹏
     * 时间：时间
     * 内容：手机端发送短信
     * @return
     * @throws Exception
     */
    @Security(needToken =true)
    public String getMobileSms()throws  Exception{
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        //获取参数
        String mobile = HttpUtils.getParameter(request, "mobile");
        String customerId = HttpUtils.getParameter(request, "customerId");
        String smsContent = HttpUtils.getParameter(request, "smsContent");
        // 校验参数合法性
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(customerId)||StringUtils.isEmpty(smsContent)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        //获取客户信息
        CustomerPersonalPO customerPersonal = customerPersonalService.loadCustomerById4W(customerId, conn);
        if(customerPersonal==null){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "账户不存在").throwException();
        }
        // 成功后去调用System_Sms服务，插入数据
        SmsService smsService = new SmsService();
        String subject = Config.getSystemVariable("system.oa.sms.identityCode.subject");
        Integer type = SmsType.TYPE_IDENTIFY_CODE;
        String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");
        int count = smsService.insertSMS(customerId, customerPersonal.getName(), mobile, subject, smsContent, signature, type, conn);
        if(count!=1){
            MyException.newInstance(ReturnObjectCode.PUBLIC_SMS_SEND_ERROR, "短信发送失败").throwException();
        }
        getResult().setCode(100);
        getResult().setMessage("短信发送成功");
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：7/29/2015
     * 内容：重载一个为注册获取验证码使用的action方法
     *
     * @return
     * @throws Exception
     */
    public String getMobileCode4ForgetPwd() throws Exception {

        HttpServletRequest request = getRequest();
        Connection conn = getConnection();

        String mobile = request.getParameter("mobile");

        // 校验验证码数据
        if (mobile == null || StringUtils.isEmpty(mobile)) {
            getResult().setMessage("请输入手机号码");
            request.setAttribute("returnObject", getResult());
            return "info";
        }

        // 校验手机号是否是存在的
        Boolean isRegistered = customerPersonalService.isMobileRegistered(mobile, conn);
        if (!isRegistered) {
            getResult().setMessage("该手机号未注册");
            request.setAttribute("returnObject", getResult());
            return "info";
        }

        // 删除以前的客户认证码
        AuthenticationCodeService authCodeService = new AuthenticationCodeService();
        CustomerAuthenticationCodePO authCode = new CustomerAuthenticationCodePO();
        authCode.setMobile(mobile);
        authCodeService.delete4W(authCode, conn);

        // 随机生成 6 位不重复的数字
        int result = NumberUtils.randomNumbers(6);
        // 重新生成客户认证码
        String code = customerPersonalService.insertMobileValidate4W(mobile, result, conn);
        getResult().setMessage(code + "," + result);
        getRequest().setAttribute("returnObject", getResult());

        // 成功后去调用 System_Sms 服务，插入数据
        SmsService smsService = new SmsService();
        String id = "WEB注册";
        String name = "WEB注册";
        String subject = Config.getSystemVariable("system.oa.sms.identityCode.subject");
        String content = Config.getSystemConfig("web.code.view.content.before") + code + Config.getSystemVariable("web.code.view.content.after");
        Integer type = SmsType.TYPE_IDENTIFY_CODE;
        String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");
        smsService.insertSMS(id, name, mobile, subject, content, signature, type, conn);

        getResult().setReturnValue(StringUtils.md5(code));
        getResult().setMessage("验证码已发送");
        request.setAttribute("returnObject", getResult());

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，验证手机的动态码
     * 前提是网站的 Customer 已经登录，在这里会检测手机动态码是否失效和正确
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String validateMobileCode4W() throws Exception {

        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = this.getConnection();

        // 校验登录并获取登录的客户
        if (session.getAttribute("loginUser") == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");

        // 校验手机号码
        String mobile = request.getParameter("mobile");
        if (mobile == null || "".equals(mobile)) {
            getResult().setMessage("请填写手机号码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 校验手机动态码
        String code = request.getParameter("mobiValidateCode");
        if (code == null || "".equals(code)) {
            getResult().setMessage("请填写手机动态码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        CustomerAuthenticationCodePO codePO = VarificationUtils.viladateMobileCode(code, mobile);

        // 校验手机动态码是否正确
        if (codePO != null) {
            getResult().setMessage("手机动态码有误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 验证时间是否失效
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date expiredTime = formatter.parse(codePO.getExpiredTime().substring(0, 10));
        Date now = new Date();

        if (expiredTime.getTime() > now.getTime()) {

            // 修改认证码的认证时间
            codePO.setAuthenticationTime(TimeUtils.getNow());
            MySQLDao.update(codePO, conn);
            // 新增或修改认证状态
            CustomerAuthenticationStatusService statusService = new CustomerAuthenticationStatusService();
            CustomerAuthenticationStatusPO statusPO = statusService.loadByCustomerId(loginUser.getId(), conn);
            if (statusPO == null) {
                statusPO = new CustomerAuthenticationStatusPO();
                statusPO.setCustomerId(loginUser.getId());
            }
            statusPO.setMobileStatus(1);
            statusPO.setMobileTime(TimeUtils.getNow());
            // 网站用户
            UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);
            statusService.insertOrUpdate(statusPO, user, conn);

        } else {

            getResult().setMessage("动态码已失效，请重新发送");
            getRequest().setAttribute("returnObject", getResult());
            return "info";

        }

        getResult().setMessage("OK");
        request.setAttribute("returnObject", getResult());
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，验证了手机动态码之后进入设置安全问题界面
     * 前提是网站的 Customer 已经登录，这里是在发送了手机动态码之后，然后验证成功再进入设置安全问题界面
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String getProtectionQuestion() throws Exception {

        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = this.getConnection();

        // 校验用户是否登录，并获取用户实体
        if (session.getAttribute("loginUser") == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");

        // 获取手机号码、手机动态码
        String code = request.getParameter("mobiValidateCode");
        String mobile = request.getParameter("mobile");

        if (code == null || "".equals(code)) {
            getResult().setMessage("手机动态码为空");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        if (mobile == null || "".equals(mobile)) {
            getResult().setMessage("手机号码为空");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 校验手机是否已经存在
        Boolean isRegistered = customerPersonalService.isMobileRegistered(mobile, conn);
        if (!isRegistered) {
            getResult().setMessage("请确认手机号属于您本人，如果更换了手机号请重新绑定。");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 查询校验码是否存在
        CustomerAuthenticationCodePO codePO = VarificationUtils.viladateMobileCode(code, mobile);
        if (codePO == null) {
            getResult().setMessage("手机动态码有误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 查询是否已经设置过安全保护问题
        CustomerAuthenticationStatusService statusService = new CustomerAuthenticationStatusService();
        CustomerSafetyQAWVO wvo = statusService.getCustomerQuestions(loginUser, conn);

        // 如果已经设置了，则验证原来的答案是否正确
        if (wvo != null) {
            String answer1 = request.getParameter("answer1");
            String answer2 = request.getParameter("answer2");
            String answer3 = request.getParameter("answer3");
            if (answer1 == null || !answer1.equals(wvo.getA1()) || answer2 == null || !answer2.equals(wvo.getA2()) || answer3 == null || !answer3.equals(wvo.getA3())) {
                getResult().setMessage("您输入的答案不正确");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }
        }

        // 验证时间是否失效
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date expiredTime = formatter.parse(codePO.getExpiredTime().substring(0, 10));
        Date now = new Date();

        if (expiredTime.getTime() > now.getTime()) {

            // 修改认证码的认证时间
            codePO.setAuthenticationTime(TimeUtils.getNow());
            MySQLDao.update(codePO, conn);

            // 新增或修改认证状态
            CustomerAuthenticationStatusPO statusPO = customerAuthenticationStatusService.loadByCustomerId(loginUser.getId(), conn);
            if (statusPO == null) {
                statusPO = new CustomerAuthenticationStatusPO();
                statusPO.setCustomerId(loginUser.getId());
            }
            statusPO.setMobileStatus(1);
            statusPO.setMobileTime(TimeUtils.getNow());

            // 网站用户
            UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);
            customerAuthenticationStatusService.insertOrUpdate(statusPO, user, conn);

            // 获取安全问题
            List<ProtectionQuestionWVO> list = statusService.getSystemProtectionQuestions(conn);
            getRequest().setAttribute("questions", list);

        } else {

            getResult().setMessage("动态码已失效，请重新发送哦");
            getRequest().setAttribute("returnObject", getResult());
            return "info";

        }
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，保存设置的安全问题和答案
     * 前提是网站的 Customer 已经登录
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-26
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String saveProtectionQuestion() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null) {
            CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");
            // 获取各个值
            String question1 = request.getParameter("question1");
            String question2 = request.getParameter("question2");
            String question3 = request.getParameter("question3");
            String answer1 = request.getParameter("answer1");
            String answer2 = request.getParameter("answer2");
            String answer3 = request.getParameter("answer3");
            // 验证
            if (question1 != null && !question1.equals("") && question2 != null && !question2.equals("") && question3 != null && !question3.equals("") &&
                    answer1 != null && !answer1.equals("") && answer2 != null && !answer2.equals("") && answer3 != null && !answer3.equals("")) {
                // 组装所选问题
                List<String> questions = new ArrayList<String>();
                questions.add(question1);
                questions.add(question2);
                questions.add(question3);
                // 组装所选答案
                List<String> answers = new ArrayList<String>();
                answers.add(answer1);
                answers.add(answer2);
                answers.add(answer3);
                // 新增或修改
                customerPersonalService.saveProtectionQuestion(questions, answers, loginUser, getConnection());
            } else {
                getResult().setMessage("请完整选择问题和输入答案");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }
        } else {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，客户注销
     * 前提是网站的 Customer 已经登录，把 Session 设为 null
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String removeUser() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("loginUser", null);
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，读取 Customer 的认证状态信息
     * 前提是网站的 Customer 已经登录，返回 CustomerAuthenticationStatus 表的认证信息，里面包含了很多认证状态
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    @Security(needWebLogin = true)
    public String getAuthenticationStatus4W() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = this.getConnection();

        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");
        CustomerAuthenticationStatusService statusService = new CustomerAuthenticationStatusService();
        CustomerAuthenticationStatusPO statusPO = statusService.loadByCustomerId(loginUser.getId(), conn);
        if (statusPO != null) {
            getResult().setReturnValue(statusPO.toJsonObject());
        } else {
            statusPO = new CustomerAuthenticationStatusPO();
            getResult().setReturnValue(statusPO.toJsonObject());
        }

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，检测 Customer 是否已经设置了安全保护问题
     * 前提是网站的 Customer 已经登录，返回的值是 1 或 0
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json，例如 {hasQuestions : 1}
     * @throws Exception
     * @author 邓超
     */
    public String checkHasQuestionAuth() throws Exception {
        HttpSession session = getRequest().getSession();
        if (session.getAttribute("loginUser") != null) {
            CustomerPersonalPO personalPO = (CustomerPersonalPO) session.getAttribute("loginUser");
            CustomerSafetyQAPO po = new CustomerSafetyQAPO();
            po.setState(Config.STATE_CURRENT);
            po.setCustomerId(personalPO.getId());
            CustomerSafetyQAPO valueObject = MySQLDao.load(po, CustomerSafetyQAPO.class, getConnection());
            if (valueObject != null) {
                JSONObject json = new JSONObject();
                json.put("hasQuestions", 1);
                getResult().setReturnValue(json);
            } else {
                JSONObject json = new JSONObject();
                json.put("hasQuestions", 0);
                getResult().setReturnValue(json);
            }
        } else {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：7/24/2015
     * 内容：web客户管理界面调用action查询出客户有木有绑定银行卡
     *
     * @return 返回0代表没有，1代表有
     * @throws Exception
     */
    @Security(needWebLogin = true)
    public String checkBackCard() throws Exception {
        Connection conn = getConnection();
        CustomerPersonalPO customerPersonal = Config.getLoginCustomerInSession(getRequest());

        JSONObject json = new JSONObject();

        CustomerAccountPO customerAccount = new CustomerAccountPO();
        customerAccount.setState(Config.STATE_CURRENT);
        customerAccount.setCustomerId(customerPersonal.getId());
        List<CustomerAccountPO> list = customerAccountService.list(customerAccount, conn);
        if (list != null && list.size() > 0) {
            json.put("hasBankCard", 2);
        } else {
            json.put("hasBankCard", 0);
        }

        getResult().setReturnValue(json);

        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：7/27/2015
     * 内容：web账户管理点击添加银行卡后跳转的action
     *
     * @return
     * @throws Exception
     */
    @Security(needWebLogin = true)
    public String setBankCardForCustomer() throws Exception {
        HttpSession session = getRequest().getSession();
        Connection conn = getConnection();
        CustomerPersonalPO customerPersonal = (CustomerPersonalPO) session.getAttribute(Config.SESSION_LOGINUSER_STRING);

        //判断是否有实名认证
        List<CustomerCertificatePO> customerCertificatePOList = customerPersonalService.isCertificate(customerPersonal, getConnection());
        if (customerCertificatePOList == null || customerCertificatePOList.size() == 0) {
            MyException.newInstance(Config.getWords("w.customer.auth.realname.error"), "没有实名认证").throwException();
        }

        int customerAccountStatus = customerAccountService.getBankCardCount(customerPersonal.getId(), conn);
        if (CustomerAccountStatus.ONLY_ONE_CARD == customerAccountStatus || CustomerAccountStatus.MORE_THAN_ONE_CARD == customerAccountStatus) {
            String message = Config.getWords("customer.account.band.mutilCards");
            MyException.newInstance(message, "客户已绑定过银行卡，请勿重复绑定").throwException();
        }


        //取出真实名字传到前台
        getRequest().setAttribute("realName", customerPersonal.getName());

        return SUCCESS;

    }

    public String bindCard() throws Exception {
        HttpSession session = getRequest().getSession();
        HttpServletResponse response = getResponse();
        if (StringUtils.isEmpty(session.getAttribute(Config.SESSION_LOGINUSER_STRING).toString())) {
            String url = Config.getWebRoot() + Config.Web_URL_Login;
            response.sendRedirect(url);
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        } else {
            return SUCCESS;
        }
    }


    @Security(needWebLogin = true)
    public String getBankKVOption() throws Exception {
        HttpSession session = getRequest().getSession();
        HttpServletResponse response = getResponse();
        if (StringUtils.isEmpty(session.getAttribute(Config.SESSION_LOGINUSER_STRING).toString())) {
            String url = Config.getWebRoot() + Config.Web_URL_Login;
            response.sendRedirect(url);
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        } else {
            kv.setGroupName(Config4W.W_BANK_KV_GROUP);
            List<KVPO> listKV = MySQLDao.query(kv, KVPO.class, getConnection());
            if (listKV.size() == 0) {
                getResult().setReturnValue("0");
            } else {
                JSONArray jsonArray = JSONArray.fromObject(listKV);
                getResult().setReturnValue(jsonArray);
            }
        }
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，获取客户最后登录时间
     * 前提是网站的 Customer 已经登录，通过查询日志表，查询该客户登录日志，按操作时间倒序排序，取第一条
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json，例如 {lastTime : 上次登录时间：2015-01-01 01:01:01} 或 {lastTime : 上次登录时间：无}
     * @throws Exception
     * @author 邓超
     */
    public String getLastLoginTime() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null) {
            CustomerPersonalPO personalPO = (CustomerPersonalPO) session.getAttribute("loginUser");
            LogPO logPO = customerPersonalService.getLastLoginTime(personalPO.getId(), getConnection());
            JSONObject json = new JSONObject();
            if (logPO != null) {
                String time = logPO.getOperateTime().substring(0, logPO.getOperateTime().length() - 2);
                json.put("lastTime", time);
            } else {
                json.put("lastTime", "无");
            }
            getResult().setReturnValue(json);
        } else {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，进入设置交易密码的界面
     * 前提是网站的 Customer 已经登录，这里检测是否已经存在交易密码，如果有则前台需要输入原交易密码，没有则不用
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json，例如：{hasTransactionPassword : 1}
     * @throws Exception
     * @author 邓超
     */
    @Security(needWebLogin = true)
    public String toTransPassword() throws Exception {

        CustomerPersonalPO personalPO = Config.getLoginCustomerInSession(getRequest());
        if (personalPO.getTransactionPassword() != null && !personalPO.getTransactionPassword().equals("")) {
            getRequest().setAttribute("hasTransactionPassword", 1);
        } else {
            getRequest().setAttribute("hasTransactionPassword", 0);
        }

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，进入设置安全保护问题页面
     * 前提是网站的 Customer 已经登录，这里检测是否已经设置过安全问题，如果有则验证
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-29
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String toProtectionQuestion() throws Exception {
        // 检测是否已设置过安全问题
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null) {
            CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");
            CustomerAuthenticationStatusService statusService = new CustomerAuthenticationStatusService();
            CustomerSafetyQAWVO wvo = statusService.getCustomerQuestions(loginUser, getConnection());
            request.setAttribute("safetyQA", wvo);
        } else {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        return SUCCESS;
    }

    /**
     * 验证原来的手机号码
     * 前提是网站的 Customer 已经登录
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-10-15
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String validateOldMobile() throws Exception {

        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = getConnection();

        // 校验用户是否登录
        if (session.getAttribute("loginUser") == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");

        // 验证手机参数
        String mobile = request.getParameter("mobile");
        if (mobile == null || "".equals(mobile)) {
            getResult().setMessage("请输入手机号码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 验证手机动态码
        String code = request.getParameter("mobiValidateCode");
        if (code == null || "".equals(code)) {
            getResult().setMessage("请输入手机动态码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 验证手机动态码是否有误
        CustomerAuthenticationCodePO codePO = VarificationUtils.viladateMobileCode(code, mobile);
        if (codePO == null) {
            getResult().setMessage("手机动态码有误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 验证手机号码是否已被注册
        Boolean isRegistered = customerPersonalService.isMobileRegistered(mobile, conn);
        if (!isRegistered) {
            getResult().setMessage("请确认手机号码属于您本人，如果更换了手机号请重新绑定");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 验证时间是否失效
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date expiredTime = formatter.parse(codePO.getExpiredTime().substring(0, 10));
        Date now = new Date();
        if (expiredTime.getTime() <= now.getTime()) {
            getResult().setMessage("动态码已失效，请重新发送哦");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 修改认证码的认证时间
        codePO.setAuthenticationTime(TimeUtils.getNow());
        MySQLDao.update(codePO, conn);

        // 新增或修改认证状态
        CustomerAuthenticationStatusService statusService = new CustomerAuthenticationStatusService();
        CustomerAuthenticationStatusPO statusPO = statusService.loadByCustomerId(loginUser.getId(), conn);
        if (statusPO == null) {
            statusPO = new CustomerAuthenticationStatusPO();
            statusPO.setCustomerId(loginUser.getId());
        }
        statusPO.setMobileStatus(1);
        statusPO.setMobileTime(TimeUtils.getNow());

        // 网站用户
        UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);
        statusService.insertOrUpdate(statusPO, user, conn);

        return SUCCESS;

    }

    /**
     * 交易平台请求的 Action，保存修改的手机号码
     * 前提是网站的 Customer 已经登录
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-29
     * <p/>
     * 修改：邓超
     * 内容：修改逻辑
     * 时间：2015-10-15
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String saveMobile() throws Exception {

        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = this.getConnection();

        // 检测用户是否登录
        if (session.getAttribute("loginUser") == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");

        // 验证手机参数
        String mobile = request.getParameter("mobile");
        if (mobile == null || mobile.equals("")) {
            getResult().setMessage("请输入手机号码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 验证手机动态码
        String code = request.getParameter("mobiValidateCode");
        if (code == null || "".equals(code)) {
            getResult().setMessage("请输入手机动态码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        CustomerAuthenticationCodePO codePO = VarificationUtils.viladateMobileCode(code, mobile);
        if (codePO == null) {
            getResult().setMessage("手机动态码有误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 验证时间是否失效
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date expiredTime = formatter.parse(codePO.getExpiredTime().substring(0, 10));
        Date now = new Date();

        if (expiredTime.getTime() <= now.getTime()) {
            getResult().setMessage("动态码已失效，请重新发送哦");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 修改认证码的认证时间
        codePO.setAuthenticationTime(TimeUtils.getNow());
        MySQLDao.update(codePO, conn);

        // 新增或修改认证状态
        CustomerAuthenticationStatusService statusService = new CustomerAuthenticationStatusService();
        CustomerAuthenticationStatusPO statusPO = statusService.loadByCustomerId(loginUser.getId(), conn);
        if (statusPO == null) {
            statusPO = new CustomerAuthenticationStatusPO();
            statusPO.setCustomerId(loginUser.getId());
        }
        statusPO.setMobileStatus(1);
        statusPO.setMobileTime(TimeUtils.getNow());

        // 网站用户
        UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);
        statusService.insertOrUpdate(statusPO, user, conn);

        Integer count0 = customerPersonalService.updateMobile(mobile, loginUser, conn);
        customerPersonal.setId(Config.SESSION_LOGINUSER_STRING);
        customerPersonal.setState(Config.STATE_CURRENT);
        customerPersonal = MySQLDao.load(customerPersonal, CustomerPersonalPO.class);

        session.setAttribute(Config.SESSION_LOGINUSER_STRING, customerPersonal);
        Integer count1 = statusService.saveAuthenticationStatus(loginUser.getId(), CustomerAuthenticationStatus.AUTH_TYPE_MOBILE, conn);


        if (count0 != 1 || count1 != 1) {
            getResult().setMessage("系统出现错误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，修改密码
     * 前提是网站的 Customer 已经登录，这里先验证原密码再修改
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-30
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String updatePassword4W() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null) {
            CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");
            String oldPassword = request.getParameter("oldPassword");
            String newPassowrd = request.getParameter("newPassword");
            String newPasswordAgain = request.getParameter("newPasswordAgain");
            if (oldPassword != null && !oldPassword.equals("") &&
                    newPassowrd != null && !newPassowrd.equals("") &&
                    newPasswordAgain != null && !newPasswordAgain.equals("") &&
                    newPassowrd.equals(newPasswordAgain)) {
                if (loginUser.getPassword().equals(oldPassword)) {   // 前台传过来时已经加密，此时无需再 MD5Utils 加密
                    if (customerPersonalService.updatePassword4W(newPassowrd, loginUser, getConnection()) != 1) {
                        getResult().setMessage("修改密码失败");
                        getRequest().setAttribute("returnObject", getResult());
                        return "info";
                    }
                } else {
                    getResult().setMessage("原密码错误");
                    getRequest().setAttribute("returnObject", getResult());
                    return "info";
                }
            } else {
                getResult().setMessage("您填写的数据有误");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }
        } else {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：7/18/2015
     * 内容：校验交易是否正确
     *
     * @return
     * @throws Exception
     */
    public String authenticationTransactionPW4Web() throws Exception {
        // 前台传过来的交易密码必须加密过
        Object transactionPassword = getRequest().getParameter("transactionPassword");
        //前台传入已经MD5加密，本次再调用设定规则加密再比较
        transactionPassword = StringUtils.md5(transactionPassword + Config.MD5String);

        Object user = getRequest().getSession().getAttribute("loginUser");
        if (user == null || transactionPassword == null) {
            getResult().setReturnValue("3");
        } else {
            CustomerPersonalPO loginUser = (CustomerPersonalPO) user;
            // 组织sql语句验证交易密码
            StringBuffer sqlDB = new StringBuffer();
            sqlDB.append(" SELECT ");
            sqlDB.append("     TransactionPassword ");
            sqlDB.append(" FROM ");
            sqlDB.append("     crm_customerpersonal ");
            sqlDB.append(" WHERE ");
            sqlDB.append("     1 = 1 ");
            sqlDB.append(" AND state = 0 ");
            // 修改人：张舜清 内容：逻辑出错，不应该把前台的交易密码作为sql语句条件查询
//            sqlDB.append(" AND TransactionPassword = '" + Database.encodeSQL(transactionPassword.toString()) + "' ");
            sqlDB.append(" AND LoginName = '" + Database.encodeSQL(loginUser.getLoginName()) + "' ");
            List<CustomerPersonalPO> list = MySQLDao.query(sqlDB.toString(), CustomerPersonalPO.class, null, getConnection());
            if (list != null) {
                if (list.size() == 1) {
                    CustomerPersonalPO customerPersonalPO = list.get(0);
                    //交易密码为空时
                    if (StringUtils.isEmpty(customerPersonalPO.getTransactionPassword())) {
                        // 标识0为用户没有设置交易密码，前台跳转
                        getResult().setReturnValue("0");
                    } else if (!transactionPassword.equals(list.get(0).getTransactionPassword())) {
                        // 标识2为用户输入的交易密码错误，前台给提示
                        getResult().setReturnValue("2");
                    }
                }
            } else {
                // 标识0为用户没有设置交易密码，前台跳转
                getResult().setReturnValue("0");
            }

        }
        return SUCCESS;
    }


    public String changePassword() throws Exception {

        String oldPassword = getHttpRequestParameter("oldPassword");
        String password = getHttpRequestParameter("password");

        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(password)) {
            MyException.newInstance("旧密码或新密码有误").throwException();
        }

        CustomerPersonalPO customerPersonalPO = Config.getLoginCustomerInSession(getRequest());

        if (!customerPersonalPO.getPassword().equals(StringUtils.md5(oldPassword + Config.MD5String))) {
            MyException.newInstance("旧密码或新密码有误").throwException();
        }

        customerPersonalPO.setPassword(StringUtils.md5(password + Config.MD5String));
        MySQLDao.insertOrUpdate(customerPersonalPO, getConnection());

        getResult().setReturnValue("查询密码修改完成");

        return SUCCESS;
    }


    public String changePasswordWithToken() throws Exception {

        String tokenString = getHttpRequestParameter("tokenString");
        String password = getHttpRequestParameter("password");

        if (StringUtils.isEmpty(password)) {
            MyException.newInstance("旧密码或新密码有误").throwException();
        }

        if (StringUtils.isEmpty(tokenString)) {
            MyException.newInstance("验证码无效").throwException();
        }

        CustomerPersonalPO customerPersonalPO = Config.getLoginCustomerInSession(getRequest());

        String ip = Config.getIP(getRequest());

        if (tokenService.checkAndDestroyToken(tokenString, customerPersonalPO.getId(), TokenBizType.Customer, ip, getConnection()) != 0) {
            MyException.newInstance("验证码无效").throwException();
        }

//        customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(customerPersonalPO.getId(), getConnection());

        customerPersonalPO.setPassword(StringUtils.md5(password + Config.MD5String));

        MySQLDao.insertOrUpdate(customerPersonalPO, getConnection());

        getResult().setReturnValue("查询密码修改完成");

        return SUCCESS;
    }


    public String saveCustomerAgreement() throws Exception {

        String agreementId = getHttpRequestParameter("agreementId");
        String agreementName = getHttpRequestParameter("agreementName");

        CustomerPersonalPO customerPersonalPO = Config.getLoginCustomerInSession(getRequest());

        customerAgreementService.saveCustomerAgreement(customerPersonalPO.getId(), agreementId, agreementName, getConnection());

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    /*
    * 作者：周海鸿
    * 时间：2015-7-23
    * 内容：修改登录密码
    * */
    public String changePassword4web() throws Exception {

        //获取原始密码
        String oldPassword = getRequest().getParameter("oldPassword");
        //获取数据库连接
        Connection conn = getConnection();
        //判断原密码是正确
        boolean flag = checkPassword(oldPassword, conn);

        //原密码验证失败
        if (!flag) {
            getResult().setMessage("原密码错误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        //获取修改的新密码
        String newPassword = getRequest().getParameter("newPassword");
        if ("".equals(newPassword) || null == newPassword) {
            getResult().setMessage("密码错误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        ///修改密码
        flag = resetPassword(newPassword, conn);
        //修改密码不成功
        if (!flag) {
            getResult().setMessage("密码错误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }//移除Session
        else {
            removeUser();
            getResult().setMessage("修改成功 重新 <a href='/core/w2/customer/LoginRequest'><b>登陆</b></a>");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
    }


    /**
     * 创建人：张舜清
     * 时间：7/23/2015
     *
     * @return
     * @throws Exception
     */
    public String getMobile() throws Exception {
        HttpSession session = getRequest().getSession();
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        } else {
            CustomerPersonalPO loginUser4W = (CustomerPersonalPO) loginUser;
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" SELECT ");
            sbSQL.append("     * ");
            sbSQL.append(" FROM ");
            sbSQL.append("     crm_customerpersonal ");
            sbSQL.append(" WHERE ");
            sbSQL.append("     1 = 1 ");
            sbSQL.append(" AND state = 0 ");
            sbSQL.append(" AND id = '" + loginUser4W.getId() + "' ");
            sbSQL.append(" AND LoginName = '" + loginUser4W.getLoginName() + "' ");

            List<CustomerPersonalPO> list = MySQLDao.query(sbSQL.toString(), CustomerPersonalPO.class, null, getConnection());

            if (list.size() == 0) {
                MyException.newInstance("未知异常，无法获得手机号").throwException();
            }

            String mobile = list.get(0).getMobile();
            if (mobile != null) {
                //mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
                getResult().setReturnValue(mobile);
                return SUCCESS;
            }
            getResult().setReturnValue(mobile);
        }

        return SUCCESS;
    }

    public String listCustomerPersonalVO() throws Exception {

        String userId = HttpUtils.getParameter(getRequest(), "userId");
        String customerId = HttpUtils.getParameter(getRequest(), "customerId");

        String referralCode = null;
        if (getLoginUser() != null) {
            referralCode = getLoginUser().getReferralCode();
        }

        List<CustomerPersonalVO> customerPersonalVOs = customerPersonalService.listCustomerPersonalVO(userId, customerId, referralCode, getConnection());


        getResult().setReturnValue(customerPersonalVOs);

        return SUCCESS;
    }

    /*
    *设置银行
     * 修改：周海鸿
      *时间：2015-7-24
      *内容：添加银行卡数据
      * */
    @Security(needWebLogin = true)
    public String getBankCard() throws Exception {

        //判断session是否存在用户
        CustomerPersonalPO customer = Config.getLoginCustomerInSession(getRequest());
        Connection conn = getConnection();


        // 银行卡记录里是否有多条银行卡记录
        int customerAccountStatus = customerAccountService.getBankCardCount(customer.getId(), conn);
        if (CustomerAccountStatus.MORE_THAN_ONE_CARD == customerAccountStatus) {
            String message = Config.getWords("customer.account.band.mutilCards");
            MyException.newInstance(message, "客户已绑定过银行卡，请勿重复绑定").throwException();
        }

        //判断有没有绑定银行卡
        CustomerAccountPO account = customerAccountService.getCustomerAccount(customer.getId(), getConnection());
        if (account == null) { //银行卡没有绑定
            return "backSetBankCard";
        }

        String accountNo = AesEncrypt.decrypt(account.getNumber());// 解密银行卡号
        accountNo = accountNo.substring(0, 4) + "********" + accountNo.substring(accountNo.length() - 4, accountNo.length());

        getRequest().setAttribute("accountName", account.getName());
        getRequest().setAttribute("accountNo", accountNo); //显示银行卡号aes解密数据


        return SUCCESS;
    }
    /**
     *设置银行
     * 修改：姚章鹏
     *时间：2015年11月27日
     *内容：获取银行卡信息和实名认证信息
     * */
    @Security(needWebLogin = true)
    public String getCustomerCertificate4W() throws Exception {

        //判断session是否存在用户
        CustomerPersonalPO customer = Config.getLoginCustomerInSession(getRequest());
        Connection conn = getConnection();

        //获取身份证
        customerCertificate.setCustomerId(customer.getId()); //用户编号
        String customerCertificateName = Config.getSystemVariable("web.customer.certificate.idcard.kv.v");
        CustomerCertificatePO customerCertificatePO = customerCertificateService.loadByCustomerId(customer.getId(), customerCertificateName, conn);
        if (customerCertificatePO == null) {
            return "reinstallBankCard";
        }

        // 解密证件号
        String idCard = AesEncrypt.decrypt(customerCertificatePO.getNumber());
        idCard = idCard.substring(0, 4) + "********" + idCard.substring(idCard.length() - 4, idCard.length());

        //判断有没有绑定银行卡
        CustomerAccountPO account = customerAccountService.getCustomerAccount(customer.getId(), getConnection());
        getRequest().setAttribute("customerNumber", idCard);//客户身份证
        if (account == null) {
            //获取客户名称
            getRequest().setAttribute("accountName", customer.getName());
            String idCardNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());
            getRequest().setAttribute("idCardNumber", idCardNumber);
            return "reinstallBankCard";
        }

        // 银行卡记录里是否有多条银行卡记录
//        CustomerAccountService customerAccountService = new CustomerAccountService();
//        int customerAccountStatus = customerAccountService.getBankCardCount(customer.getId(), conn);
//        if (CustomerAccountStatus.MORE_THAN_ONE_CARD == customerAccountStatus) {
//            String message = Config.getWords("customer.account.band.mutilCards");
//            MyException.newInstance(message, "客户已绑定过银行卡，请勿重复绑定", LogType.General).throwException();
//        }

        // 解密银行卡号
        String accountNo = account.getNumber();
        accountNo = accountNo.substring(0, 4) + "********" + accountNo.substring(accountNo.length() - 4, accountNo.length());

        getRequest().setAttribute("accountName", account.getName());
        getRequest().setAttribute("accountNo", accountNo); //显示银行卡号 AES 解密数据

        return SUCCESS;
    }

    @Security(needWebLogin = true)
    public String goCheckMoneyBankCard4W() throws Exception {
        //判断session是否存在用户
        String customerId = (String) getRequest().getSession().getAttribute("loginUserId");
        if (StringUtils.isEmpty(customerId)) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        return SUCCESS;
    }

//    @Transactional(need = true)
//    @Security(needWebLogin = true)
//    public String isBindBankCard4W() throws Exception {
//        String customerId = (String) getRequest().getSession().getAttribute("loginUserId");
//        Connection conn = getConnection();
//        //获取银行卡认证表的数据
//        CustomerbankauthenticationPO bankauthentication = service.getBankauthenticationStatus(customerId, Config4Status.STATUS_CUSTOMER_BANKCARD_UNFINISH, conn);
//
//
//        if (bankauthentication == null) {
//            //查询历史绑定数据
//            bankauthentication = service.getBankauthenticationStatus(customerId, Config4Status.STATUS_CUSTOMER_BANKCARD_SUCCESS, conn);
//            if (bankauthentication == null) {
//                MyException.newInstance("认证失败，请稍后再试", "查询不到银行卡认证数据", LogType.General).throwException();
//            }
//
//        }
//
//        //根据出批量代付明细表
//        AllinpayBatchPaymentDetailPO temp = new AllinpayBatchPaymentDetailPO();
//        temp.setBizId(bankauthentication.getId());
//        temp.setState(Config.STATE_CURRENT);
//        temp = MySQLDao.load(temp, AllinpayBatchPaymentDetailPO.class, conn);
//
//        if(temp == null) {
//            MyException.newInstance("认证失败，请稍后再试", "查询不到代付明细表", LogType.General).throwException();
//        }
//
//        getRequest().setAttribute("accountName", temp.getAccount_name());
//
//        String accountNo= AesEncrypt.decrypt(temp.getAccount_no());// 解密银行卡号
//        accountNo = accountNo.substring(0,4)+"********"+accountNo.substring(accountNo.length()-5,accountNo.length()-1);
//
//        getRequest().setAttribute("accountNo", accountNo);//账号解密显示
//        return SUCCESS;
//    }

    @Security(needWebLogin = true)
    public String goChangeBankCard4W() throws Exception {

        String customerId = (String) getRequest().getSession().getAttribute("loginUserId");

        //更改银行卡state=1
        Connection conn = getConnection();
        CustomerBankAuthenticationPO bankauthentication = new CustomerBankAuthenticationPO();
        bankauthentication.setState(Config.STATE_CURRENT);
        bankauthentication.setCustomerId(customerId);
        bankauthentication.setAuthenticationStatus(Config4Status.STATUS_CUSTOMER_BANKCARD_UNFINISH);
        bankauthentication = MySQLDao.load(bankauthentication, CustomerBankAuthenticationPO.class, conn);
        bankauthentication.setState(Config.STATE_UPDATE);
        MySQLDao.update(bankauthentication, conn);
        return SUCCESS;
    }

    /**
     * 检测银行卡认证的金额
     * <p/>
     * 修改：邓超
     * 内容：新增通联结果查询后的反馈
     * 时间：2015-9-13
     *
     * @return
     * @throws Exception
     */
//    @Transactional(need = true)
//    @Security(needWebLogin = true)
//    public String checkBankCardMoney() throws Exception {
//
//        Connection conn = getConnection();
//
//        // 判断 session 是否存在用户
//        String customerId = (String) getRequest().getSession().getAttribute("loginUserId");
//
//        // 验证金额
//        String verifyMoney = getRequest().getParameter("verifyMoney");
//        if (StringUtils.isEmpty(verifyMoney)) {
//            getResult().setMessage("请输入金额");
//            getRequest().setAttribute("returnObject", getResult());
//            return "info";
//        }
//
//        // TODO 通联支付正式代码
//        CustomerbankauthenticationPO bankauthentication = service.getCustomerBankauthentication(customerId, Config4Status.STATUS_CUSTOMER_BANKCARD_UNFINISH, Config4Status.STATUS_CUSTOMER_BANKCARD_PAYSUCCESS, conn);
//        // TODO 通联支付测试代码
//        // CustomerbankauthenticationPO bankauthentication = service.getCustomerBankauthentication(customerId, Config4Status.STATUS_CUSTOMER_BANKCARD_UNFINISH, Config4Status.STATUS_CUSTOMER_BANKCARD_UNFINISH,conn);
//
//        // 通联还没发送或者失败
//        if (bankauthentication == null) {
//            MyException.newInstance("请填写正确的到账金额，我们稍后会将验证金额发到您的卡上。", "通联支付请求尚未发送或发送失败", LogType.General).throwException();
//        }
//
//        // 如果含有错误码，说明该认证有问题
//        String code = bankauthentication.getCode();
//        if(!"".equals(code) && !Config.getSystemVariable("bank.pay.allinpay.callback.success.flag").equals(code)) {
//            MyException.newInstance("抱歉，" + Config.getAllinpayFailedMessage(code) + "，或联系客服人员。", Config.getAllinpayFailedMessage(code), LogType.General).throwException();
//        }
//
//        // 判断通联支付成功后根据用户输入金额跟通联支付金额判断下一步
//        double money = Double.parseDouble(verifyMoney);
//        double bankMoney = bankauthentication.getMoney();
//
//        // 验证金额是否匹配
//        if (money != bankMoney) {
//            return "verifyFailed";
//        }
//
//        // 更改并添加银行卡认证表,更改客户认证状态为认证成功
//        int count = service.updateAndinsertBankauthentication(bankauthentication, conn);
//        if (count != 1) {
//            MyException.newInstance(Config.getWords4WebGeneralError(), "认证已通过，但无法更新数据", LogType.General).throwException();
//        }
//
//        // 根据批量代付明细表查出卡号和用户名
//        AllinpayBatchPaymentDetailPO temp = new AllinpayBatchPaymentDetailPO();
//        temp.setBizId(bankauthentication.getId());
//        temp.setState(Config.STATE_CURRENT);
//        temp = MySQLDao.load(temp, AllinpayBatchPaymentDetailPO.class, conn);
//
//        // 查询通联
//        CustomerAccountService accountService = new CustomerAccountService();
//        temp.setE_user_code(customerId);
//        accountService.insertAccount(temp, conn);   // 增加 crm_customeraccount 表数据
//        getRequest().setAttribute("accountName", temp.getAccount_name());
//
//        String accountNo= AesEncrypt.decrypt(temp.getAccount_no());// 解密银行卡号
//        accountNo = accountNo.substring(0,4)+"********"+accountNo.substring(accountNo.length()-5,accountNo.length()-1);
//
//        getRequest().setAttribute("accountNo",accountNo); // 账号解密显示
//
//        return SUCCESS;
//
//    }

    /**
     * 创建人：姚章鹏
     * 时间：2015年9月1日16:59:54
     * 内容：解绑银行卡
     *
     * @return
     * @throws Exception
     */
    @Security(needWebLogin = true)
    public String unBindBankCard() throws Exception {

        MyException.newInstance("银行卡解绑需要与我们客服联系").throwException();

        String customerId = Config.getLoginCustomerInSession(getRequest()).getId();
        Connection conn = getConnection();

        //查出银行认证表的数据并且更新
        CustomerAccountPO customerAccountPO = customerAccountService.getCustomerAccount(customerId, conn);
        customerAccountPO.setState(Config.STATE_UPDATE);    //更新账户信息
        MySQLDao.update(customerAccountPO);

        return SUCCESS;
    }


    /**
     * 创建人：姚章鹏
     * 时间：2015年9月1日16:59:54
     * 内容：重新认证银行卡
     *
     * @return
     * @throws Exception
     */
    @Security(needWebLogin = true)
    public String againBindBankCard() throws Exception {
        String customerId = (String) getRequest().getSession().getAttribute("loginUserId");
        //判断session中是否存在用户
        if (StringUtils.isEmpty(customerId)) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        Connection conn = getConnection();
        CustomerBankAuthenticationPO bankauthentication = new CustomerBankAuthenticationPO();
        bankauthentication.setState(Config.STATE_CURRENT);
        bankauthentication.setCustomerId(customerId);
        bankauthentication.setAuthenticationStatus(0);
        bankauthentication = MySQLDao.load(bankauthentication, CustomerBankAuthenticationPO.class, conn);
        //更新customerAccount表
        if (bankauthentication == null) {
            //通联还没发送或者失败     TODO 错误提示需要修改
            getResult().setMessage("重新认证失败，请稍后再试");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        } else {
            //更新银行卡认证表
            bankauthentication.setState(Config.STATE_UPDATE);
            MySQLDao.update(bankauthentication);
        }
        return SUCCESS;
    }



    /**
     * 获取手机状态的
     * <p/>
     * 作者：周海鸿
     * 时间：2015-7-27
     *
     * @return
     * @throws Exception
     */
    public String getMobilesStatus() throws Exception {
        Connection conn = getConnection();
        //判断session是否存在用户
        String customerId = (String) getRequest().getSession().getAttribute("loginUserId");
        //判断session中是否存在用户
        if (StringUtils.isEmpty(customerId)) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        //根据customerid 获取当前用户的手机认证状态
        int status = customerCertificateService.getMobileStatus(customerId, conn);
        //判断返回的数据是否合理
        if (1 == status) {
            return SUCCESS;
        }
        if (0 == status) {
            return "checkout";
        }

        getResult().setMessage("数据繁忙请稍后");
        getRequest().setAttribute("returnObject", getResult());
        return "info";
    }

    /**
     * 创建人：张舜清
     * 时间：7/26/2015
     * 内容：通用的查询客户资金action
     *
     * @return
     * @throws Exception
     */
    public String getMoney() throws Exception {

        HttpSession session = getRequest().getSession();
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        } else {
            CustomerPersonalPO loginUser4W = (CustomerPersonalPO) loginUser;
            DecimalFormat dcmFmt = new DecimalFormat("0.00");
            customerMoney.setState(Config.STATE_CURRENT);
            customerMoney.setCustomerId(loginUser4W.getId());
            customerMoney = MySQLDao.load(customerMoney, CustomerMoneyPO.class, getConnection());

            if (customerMoney == null) {
                MyException.newInstance("查询客户资金失败" + loginUser4W.getId() + "查询客户资金失败").throwException();
            }

            customerMoney.setAvailableMoney(Double.valueOf(dcmFmt.format(customerMoney.getAvailableMoney())));
            customerMoney.setFrozenMoney(Double.valueOf(dcmFmt.format(customerMoney.getFrozenMoney())));
            getResult().setReturnValue(customerMoney);
        }
        return SUCCESS;
    }

    /**
     * 创建人：姚章鹏
     * 时间：2015年9月7日10:06:30
     * 内容：获取客户总资产，将付款成功的金额累加起来
     *
     * @return
     * @throws Exception
     */
    @Security(needWebLogin = true)
    public String getTotalCustomerMoney() throws Exception {

        Connection conn = getConnection();
        CustomerPersonalPO personalPO = Config.getLoginCustomerInSession(getRequest());
        String customerId = personalPO.getId();

        // 查询客户本金
        Double principalMoney = customerPersonalService.getCustomerTotalPrincipalMoney(customerId, conn);

        // 查询客户的收益（利息）
        double profitMoney = 0;
//        PaymentPlanService paymentPlanService = new PaymentPlanService();
//        double profitMoney = paymentPlanService.getCustomerTotalProfitMoney(customerId, conn);

        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        customerMoney.setAvailableMoney(Double.valueOf(dcmFmt.format(principalMoney + profitMoney)));
        getResult().setReturnValue(customerMoney);

        return SUCCESS;
    }

    @Security(needWebLogin = true)
    public String getCustomerProfit() throws Exception {
        HttpSession session = getRequest().getSession();
        Connection conn = getConnection();
        Object loginUser = session.getAttribute("loginUser");

        CustomerPersonalPO loginUser4W = Config.getLoginCustomerInSession(getRequest());

        double profitMoney = customerPersonalService.getCustomerTotalProfitMoney(loginUser4W.getId(), conn);
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        getResult().setReturnValue(Double.valueOf(profitMoney));

        if (loginUser == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        return SUCCESS;
    }

    /**
     * 1、验证是否登录
     * 2验证当前用户是否填写必填信息
     * 海鸿
     * 2015-8-17
     * 跳转到提现页面
     *
     * @return
     * @throws Exception
     */

    public String gotowithdrawal() throws Exception {
        // 执行操作
        String customerId = getRequest().getSession().getAttribute("loginUserId").toString();

        if (StringUtils.isEmpty(customerId)) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        //获取数据库连接

        Connection conn = getConnection();

        //判断用户是否有进行实名验证与设置交易密码

        //用户编号不能为null

        if (customerId != null) {
            //判断当前用户是否有经过实名验证与设置交易密码
            if (!customerPersonalService.isTransactionPassword(customerId.toString(), conn)) {
                //没有进行实名验证 跳转到账户用户管理 进行用户认证
                getRequest().setAttribute("mark", "password");
                return "customerError";
            }
            if (!customerPersonalService.isCertificate(customerId.toString(), conn)) {
                //没有设置交易密码 跳转到账户用户管理 进交易密码认证
                getRequest().setAttribute("mark", "");
                return "customerError";
            }
            //判断是否有绑定银行卡
            if (!customerPersonalService.hasBankCard(customerId.toString(), conn)) {
                return "HasBankCard";
            }

        }

        return SUCCESS;
    }

    /**
     * 调用之前的方法添加交易密码
     * 判断返回的类型 设置条状的标签的状态
     * <p/>
     * 海鸥
     * 2015-8-20
     * 修改交易密码
     *
     * @return
     * @throws Exception
     */
    public String transPassword4W_2() throws Exception {

        //调用之前的修改密码，且设置不需要短信验证，在 transPassword4W() 中有判断
        transPasswordNeedMobile = false;
        String str = this.transPassword4W();

        //判断返回的类型
        if (SUCCESS.equals(str)) {
            getRequest().setAttribute("mark", "");
        }

        transPasswordNeedMobile = true;

        return str;
    }


    /**
     * 根据产品编号关联订单查询客户信息
     *
     * @return
     * @throws Exception
     */
    public String loadCustomer() throws Exception {

        Connection conn = getConnection();

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        String productionId = request.getParameter("productionId");
        Pager pager = customerPersonalService.loadCustomerByProductionId(personalVO, productionId, conditions, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    /**
     * 验证是都是老客户
     *
     * @return
     * @throws Exception
     */
    public String isOldCustomer() throws Exception {
        if (StringUtils.isEmpty(personal.getMobile())) {
            getResult().setReturnValue("false");
            return SUCCESS;
        }
        //得到结果
        boolean flgs = customerPersonalService.isOldCustomer(personal.getMobile());
        //返回结果
        getResult().setReturnValue(flgs);
        return SUCCESS;
    }

    /**
     * HWBANKSAPP-22-创建接口检查客户(CustomerPersonal)是否进行了实名验证，存在certificate记录和客户name不为空才满足
     * 参数: customerId
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String isCertificatedCustomer() throws Exception {
        // 获取数据库连接
        Connection conn = getConnection();
        // 获取请求
        HttpServletRequest request = getRequest();
        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        // 校验参数合法性
        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "客户ID缺失").throwException();
        }
        // 验证
        JSONObject jo = customerPersonalService.isCertificatedCustomer(customerId, conn);
        if (jo == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_NOT_CERTIFICATED, "客户没有通过实名验证，客户编号["+customerId+"]").throwException();
        }
        getResult().setReturnValue(jo);

        return SUCCESS;
    }

    /**
     * 获取客户身份证信息
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月10日
     */
    @Security(needToken = true)
    public String getCustomercertificate() throws Exception {
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        String customerId = HttpUtils.getParameter(request, "customerId");

        // 校验参数合法性
        if(StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        CustomerCertificatePO certificateInfo = customerCertificateService.getCustomerCertificate(customerId, conn);

        // 校验是否为空
        if(certificateInfo == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_NOT_REAL_AUTHENTICATION, "此客户没有实名认证，客户编号["+customerId+"]").throwException();
        }

        getResult().setReturnValue(certificateInfo.toJsonObject());

        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，读取 Customer 的认证状态信息
     * 前提是网站的 Customer 已经登录，返回 CustomerAuthenticationStatus 表的认证信息，里面包含了很多认证状态
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年11月27日
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 姚章鹏
     */
    @Security(needWebLogin = true)
    public String getAuthenticationAndBankCardStatus4W() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = getConnection();

        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");
        CustomerAuthenticationStatusPO statusPO = customerAuthenticationStatusService.loadByCustomerId(loginUser.getId(), conn);

        //判断客户是否实名认证
        if (statusPO.getAccountStatus()==0) {
            //未实名认证
            getResult().setReturnValue("0");
            return SUCCESS;
        }

        //判断有没有绑定银行卡
        CustomerAccountPO account = customerInfoService.getCustomerAccount(loginUser.getId(), getConnection());
        if (account == null) { //银行卡没有绑定
            getResult().setReturnValue("1");
        }
        return SUCCESS;
    }

    /**
     * 获取手机动态码
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月11日
     */
    public String getMobileCode() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String mobile = HttpUtils.getParameter(request, "mobile");
        // 校验参数合法性
        if(StringUtils.isEmpty(mobile)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        // 删除以前的客户认证码
        authenticationCodeService.deleteAuthentucatuibCode(mobile,conn);
        // 随机生成 6 位不重复的数字
        int result = NumberUtils.randomNumbers(6);
        // 重新生成客户认证码
        String code = customerPersonalService.insertMobileValidate4W(mobile, result, conn);


        // 成功后去调用System_Sms服务，插入数据
        int count = smsService.sendSms4ValidateGuestMobile(mobile, code, conn);

        if (count == 1) {
            getResult().setMessage("验证码已发送");
            getRequest().setAttribute("returnObject", getResult());
        }

        return SUCCESS;

    }

    public CustomerPersonalPO getPersonal() {
        return personal;
    }


    /**
     * 修改手机号码
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月17日10:28:56
     *
     * 修改：邓超
     * 内容：新增旧手机的验证
     * 说明：========== 特别注意 ========== 新手机的手机动态码在拦截器里已验证，这里再验证老的手机动态码，以防绕过接口
     * 时间：2016年1月25日
     *
     * @throws Exception
     * @return String
     */
    @Security(needMobileCode = true, needToken = true)
    public String updateMobileNumber() throws Exception {
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String mobile = HttpUtils.getParameter(request, "mobile");
        String customerId = HttpUtils.getParameter(request, "customerId");
        // 获取老的手机号
        String oldMobile = HttpUtils.getParameter(request, "oldMobile");
        // 获取老的手机动态码
        String oldMobileCode = HttpUtils.getParameter(request, "oldMobileCode");

        // 校验参数合法性
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(customerId) || StringUtils.isEmpty(oldMobile) || StringUtils.isEmpty(oldMobileCode)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        // 校验新手机号码是否已经被注册
        CustomerPersonalPO po = customerPersonalService.loadCustomerByMobile(mobile, conn);
        if(po != null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_IS_REGISTERED, "当前手机号已被注册，请填写另一个手机号").throwException();
        }

        // 校验原手机和原动态码（以防绕过验证）
        Boolean isOldCodeSuccess = Config.checkMobileCode(oldMobile, oldMobileCode, conn);
        if(!isOldCodeSuccess) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_MOBILECODE_NOT_CORRECT, "原手机动态码校验失败").throwException();
        }

        // 修改手机号码（新手机动态码在拦截器里已经验证）
        int count = customerPersonalService.resetMobile(mobile, customerId, conn);
        if(count!=1){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_MOBILE_NUMBER_OPERATION_ERROR, "操作失败").throwException();
        }

        // 返回数据
        getResult().setCode(100);
        getResult().setMessage("操作成功");
        return SUCCESS;
    }

    /**
     * 修改密码
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月23日10:09:57
     */
    @Security(needToken = true)
    public String updatePassword() throws  Exception{
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        String oldPassword = HttpUtils.getParameter(request, "oldPassword");
        String newPassword = HttpUtils.getParameter(request, "newPassword");
        // 校验参数合法性
        if(StringUtils.isEmpty(customerId)||StringUtils.isEmpty(oldPassword)||StringUtils.isEmpty(newPassword)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        //验证密码是否加密
        if(oldPassword.length() != 32||newPassword.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_ENCRYPT, "请将密码加密后进行传输").throwException();
        }
        //获取客户信息
        CustomerPersonalPO po = customerPersonalService.loadByCustomerPersonalId(customerId, conn);
        if (po == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "账户不存在").throwException();
        }
        String password = StringUtils.md5(oldPassword + Config.MD5String);
        //验证密码是否正确
        if(!po.getPassword().equals(password)){
            MyException.newInstance(ReturnObjectCode.PUBLIC_PASSWORDS_NOT_SAME, "密码错误").throwException();
        }
        //修改密码
        int count = customerPersonalService.resetMobilePassword(po,newPassword,customerId, conn);
        if(count!=1){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_MOBILE_PASSWORD_OPERATION_ERROR, "操作失败").throwException();
        }
        getResult().setCode(100);
        getResult().setMessage("操作成功");

        return SUCCESS;
    }

    /**
     * 获取客户资金详情，兑付后的客户资金
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月23日10:49:33
     */
    @Security(needToken = true)
    public String getCustomerPrincipalAndProfitMoney() throws  Exception{
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        // 校验参数合法性
        if(StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        JSONObject object = new JSONObject();

        Double principalMoney = customerPersonalService.getCustomerTotalPrincipalMoney(customerId, conn);
        object.put("totalPrincipalMoney", NumberUtils.format(principalMoney, 2));
        Double profitMoney = customerPersonalService.getCustomerTotalProfitMoney(customerId, conn);
        object.put("totalProfitMoney",NumberUtils.format(profitMoney, 2));
        
        getResult().setReturnValue(object);
        getResult().setCode(100);
        getResult().setMessage("操作成功");
        return SUCCESS;
    }

    /**
     *内容：添加或者更改手势密码
     *创建人：姚章鹏
     * 时间：2015年12月25日11:44:11
     * @param
     */
    @Security(needToken = true)
    public String addOrUpdateGesturePassword() throws  Exception{
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        String gesturePassword = HttpUtils.getParameter(request, "gesturePassword");
        String status = HttpUtils.getParameter(request, "status");
        // 校验参数合法性
        if(StringUtils.isEmpty(customerId)||StringUtils.isEmpty(gesturePassword)||StringUtils.isEmpty(status)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        int gesturePasswordStatus=0;
        try {
            gesturePasswordStatus= Integer.parseInt(status);
        }catch (Exception e){
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }
        //添加或者更改手势密码
        int count = customerPersonalService.setGesturePassword(customerId, gesturePassword, gesturePasswordStatus, conn);
        if(count!=1){
            MyException.newInstance(ReturnObjectCode.CUSTOMER_MOBILE_PASSWORD_OPERATION_ERROR, "操作失败").throwException();
        }
        getResult().setCode(100);
        getResult().setMessage("操作成功");
        return SUCCESS;
    }

    /**
     *内容：查询是否启用手势密码
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月25日11:44:31
     * @param
     */
    @Security(needToken = true)
    public String getGesturePassword() throws  Exception{
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        // 校验参数合法性
        if(StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        int GesturePasswordStatus = customerPersonalService.getGesturePassword(customerId, conn);
        JSONObject object=new JSONObject();
        object.put("status",GesturePasswordStatus);
        getResult().setReturnValue(object);
        getResult().setCode(100);
        getResult().setMessage("操作成功");
        return SUCCESS;
    }

    public void setPersonal(CustomerPersonalPO personal) {
        this.personal = personal;
    }

    public CustomerPersonalVO getPersonalVO() {
        return personalVO;
    }

    public void setPersonalVO(CustomerPersonalVO personalVO) {
        this.personalVO = personalVO;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public CustomerDistributionPO getCustomerDistribution() {
        return customerDistribution;
    }

    public void setCustomerDistribution(CustomerDistributionPO customerDistribution) {
        this.customerDistribution = customerDistribution;
    }

    public CustomerCertificatePO getCustomerCertificate() {
        return customerCertificate;
    }

    public LegalAgreementPO getLegalAgreement() {
        return legalAgreement;
    }

    public void setLegalAgreement(LegalAgreementPO legalAgreement) {
        this.legalAgreement = legalAgreement;
    }

    public void setCustomerCertificate(CustomerCertificatePO customerCertificate) {
        this.customerCertificate = customerCertificate;
    }

    public CustomerCertificateService getCustomerCertificateService() {
        return customerCertificateService;
    }

    public void setCustomerCertificateService(CustomerCertificateService customerCertificateService) {
        this.customerCertificateService = customerCertificateService;
    }

    public CustomerAccountPO getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccountPO customerAccount) {
        this.customerAccount = customerAccount;
    }

    public LogPO getLog() {
        return log;
    }

    public void setLog(LogPO log) {
        this.log = log;
    }

    public CustomerMoneyPO getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(CustomerMoneyPO customerMoney) {
        this.customerMoney = customerMoney;
    }

    public CustomerMoneyVO getCustomerMoneyVO() {
        return customerMoneyVO;
    }

    public void setCustomerMoneyVO(CustomerMoneyVO customerMoneyVO) {
        this.customerMoneyVO = customerMoneyVO;
    }

    public KVPO getKv() {
        return kv;
    }

    public void setKv(KVPO kv) {
        this.kv = kv;
    }

    public CustomerPersonalPO getCustomerPersonal() {
        return customerPersonal;
    }

    public void setCustomerPersonal(CustomerPersonalPO customerPersonal) {
        this.customerPersonal = customerPersonal;
    }


    public PaymentPlanVO getPaymentPlanVO() {
        return paymentPlanVO;
    }

    public void setPaymentPlanVO(PaymentPlanVO paymentPlanVO) {
        this.paymentPlanVO = paymentPlanVO;
    }

    public ProductionVO getProductionVO() {
        return productionVO;
    }

    public void setProductionVO(ProductionVO productionVO) {
        this.productionVO = productionVO;
    }

    public OrderVO getOrderVO() {
        return orderVO;
    }

    public void setOrderVO(OrderVO orderVO) {
        this.orderVO = orderVO;
    }

    public CustomerMoneyLogVO getCustomerMoneyLogVO() {
        return customerMoneyLogVO;
    }

    public void setCustomerMoneyLogVO(CustomerMoneyLogVO customerMoneyLogVO) {
        this.customerMoneyLogVO = customerMoneyLogVO;
    }


    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

}