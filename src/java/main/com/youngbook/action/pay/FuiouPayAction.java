package com.youngbook.action.pay;

import com.youngbook.common.KVObjects;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.production.OrderPayChannel;
import com.youngbook.service.core.APICommandService;
import com.youngbook.service.pay.FuiouService;
import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Security;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Bank;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.core.APICommandPO;
import com.youngbook.entity.po.core.OrderPayPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.service.customer.*;
import com.youngbook.service.pay.FuiouAuthenticationService;
import com.youngbook.service.pay.FuiouPaymentService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.production.ProductionService;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.TokenService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class FuiouPayAction extends BaseAction {

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductionService productionService;

    @Autowired
    CustomerAuthenticationStatusService customerAuthenticationStatusService;

    @Autowired
    CustomerCertificateService customerCertificateService;

    @Autowired
    FuiouPaymentService paymentService;

    @Autowired
    FuiouService fuiouService;

    @Autowired
    CustomerAccountService customerAccountService;

    @Autowired
    TokenService tokenService;

    @Autowired
    LogService logService;

    @Autowired
    FuiouAuthenticationService fuiouAuthenticationService;

    @Autowired
    APICommandService apiCommandService;



    public String getPCPayMd5() throws Exception {

        String orderId = getHttpRequestParameter("orderId");
        String iss_ins_cd = getHttpRequestParameter("iss_ins_cd");
        String goods_name = getHttpRequestParameter("goods_name");

        StringUtils.checkIsEmpty(orderId, "订单编号");
        StringUtils.checkIsEmpty(iss_ins_cd, "银行代码");

        OrderPO orderPO = orderService.loadByOrderId(orderId, getConnection());

        APICommandPO cmd = new APICommandPO();

        cmd.setAPIType("富友支付");
        cmd.setAPIName("富友支付-网关支付-发送");
        cmd.setBizId(orderPO.getId().substring(0, 29));
        cmd.setAPIURL(Config.getSystemConfig("fuiou.pay.pc.send.url"));

        MySQLDao.insertOrUpdate(cmd, getConnection());

        // 接口要求order_id位数为小于30


        String mchnt_cd = Config.getSystemConfig("fuiou.merchant.id");
        String order_id = orderPO.getId().substring(0, 29);
        String order_amt = NumberUtils.getMoney2FenString(orderPO.getMoney());
        String order_pay_type = "B2C";
        String page_notify_url = Config.getSystemConfig("fuiou.pay.pc.callback.front.url");
        String back_notify_url = Config.getSystemConfig("fuiou.pay.pc.callback.back.url");
        String order_valid_time = "";
        String goods_display_url = "";
        String rem = "";
        String ver = "1.0.1";

        String md5 = fuiouService.getMd54PC(mchnt_cd, order_id, order_amt, order_pay_type, page_notify_url, back_notify_url, order_valid_time, iss_ins_cd, goods_name, goods_display_url, rem, ver);



        KVObjects parameters = new KVObjects();
        parameters.addItem("mchnt_cd", mchnt_cd);
        parameters.addItem("order_id", order_id);
        parameters.addItem("order_amt", order_amt);
        parameters.addItem("order_pay_type", order_pay_type);
        parameters.addItem("page_notify_url", page_notify_url);
        parameters.addItem("back_notify_url", back_notify_url);
        parameters.addItem("order_valid_time", order_valid_time);
        parameters.addItem("iss_ins_cd", iss_ins_cd);
        parameters.addItem("goods_name", goods_name);
        parameters.addItem("goods_display_url", goods_display_url);
        parameters.addItem("rem", rem);
        parameters.addItem("ver", ver);
        parameters.addItem("mchnt_key", md5);

        cmd.setCommandType(3);
        cmd.setCommands(StringUtils.buildUrlParameters(parameters));

        MySQLDao.insertOrUpdate(cmd, getConnection());


        JSONObject jsonObject = new JSONObject();
        jsonObject.element("order_id", orderPO.getId().substring(0, 29)).element("md5", md5);


        getResult().setReturnValue(jsonObject);

        return SUCCESS;

    }


    public String buildMobilePayOrder() throws Exception {

        String orderId = "";

        String customerName = getHttpRequestParameter("name");
        if (StringUtils.isEmpty(customerName)) {
            MyException.newInstance("客户姓名为空", "customerName="+customerName).throwException();
        }

        String customerId = getHttpRequestParameter("customerId");
        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("客户编号为空", "customerId="+customerId).throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(customerId, getConnection());
        if (customerPersonalPO == null) {
            MyException.newInstance("无法获取客户信息", "customerId="+customerId).throwException();
        }
        if (!StringUtils.isEmpty(customerPersonalPO.getCustomerCatalogId()) && customerPersonalPO.getCustomerCatalogId().equals(CustomerCatalog.Confirmed)) {
            if (!customerPersonalPO.getName().equals(customerName)) {
                MyException.newInstance("客户姓名与系统不匹配", "customerId="+customerId).throwException();
            }
        }
        else {
            customerPersonalPO.setName(customerName);
            customerPersonalService.insertOrUpdate(customerPersonalPO, Config.getDefaultOperatorId(), getConnection());
        }


        String customerIdCardNumber = getHttpRequestParameter("customerIdCardNumber");
        if (StringUtils.isEmpty(customerIdCardNumber)) {
            MyException.newInstance("客户身份证号为空", "customerIdCardNumber="+customerIdCardNumber).throwException();
        }

        String productionId = getHttpRequestParameter("productionId");
        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("产品编号为空", "productionId="+productionId).throwException();
        }


        /**
         * 处理身份证信息
         *
         * 如果客户状态确认，则比对输入的证件号码
         * 如果实名认证未确认，则更新此次输入的证件号码
         */

        CustomerCertificatePO customerCertificatePO = customerCertificateService.loadByCustomerId(customerId, getConnection());

        if (StringUtils.isEmpty(customerPersonalPO.getCustomerCatalogId()) || customerPersonalPO.getCustomerCatalogId().equals(CustomerCatalog.Template)) {
            if (customerCertificatePO != null) {
                customerCertificatePO.setNumber(customerIdCardNumber);
                customerCertificatePO.setName("98");
                customerCertificateService.insertOrUpdate(customerCertificatePO, Config.getDefaultOperatorId(), getConnection());
            }
        }
        // 客户已实名认证，需要比对输入的证件信息
        else {
            if (customerCertificatePO != null) {
                String idCardNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());

                if (!idCardNumber.equals(customerIdCardNumber)) {
                    MyException.newInstance("客户身份证号与系统预留身份证号不匹配", "customerIdCardNumber="+customerIdCardNumber+"&System_customerIdCardNumber="+idCardNumber).throwException();
                }
            }
        }

        // 尚未保存身份证信息，新添加
        if(customerCertificatePO == null) {
            customerCertificatePO = new CustomerCertificatePO();
            customerCertificatePO.setCustomerId(customerId);
            customerCertificatePO.setNumber(customerIdCardNumber);
            customerCertificatePO.setName("98");

            customerCertificateService.insertOrUpdate(customerCertificatePO, Config.getDefaultOperatorId(), getConnection());
        }

        // 创建时，值保留为空，service中会查询
        String productionCompositionId = "";

        String moneyString = getHttpRequestParameter("money");
        if (StringUtils.isEmpty(moneyString)) {
            MyException.newInstance("交易金额为空", "moneyString="+moneyString).throwException();
        }
        double money = Double.parseDouble(moneyString);

        String contractNo = "";
        boolean isPersonal = true;
        String appointmentTime = TimeUtils.getNow();
        String payTime = "";
        String createTime = TimeUtils.getNow();
        String referralCode = getHttpRequestParameter("referralCode");
        int orderStatusOfAppointmentOrWaitingForConfirm = OrderStatus.Appointment;
        String accountId = getHttpRequestParameter("accountId");
        if (StringUtils.isEmpty(accountId)) {
            MyException.newInstance("客户银行账号编号为空", "accountId="+accountId).throwException();
        }
        String bankCode = "";
        String description = "";



        OrderPO orderPO = orderService.appointmentOrder(orderId, customerId, productionId, productionCompositionId, money, contractNo, isPersonal, appointmentTime, payTime, createTime, referralCode, orderStatusOfAppointmentOrWaitingForConfirm, accountId, bankCode, description, getConnection());

        String orderXml = fuiouService.buildMobilePayOrder(orderPO, getConnection());

        JSONObject jsonObject = new JSONObject();
        jsonObject.element("orderId", orderPO.getId()).element("FM", orderXml);


        getResult().setReturnValue(jsonObject);

        return SUCCESS;
    }

    /**
     * 富友 PC 端支付网关回调
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月9日
     *
     * @return
     * @throws Exception
     */
    public String fuiouPCPaymentCallback() throws Exception {

        CustomerPersonalPO loginUser = null;
        String merchantId = Config.getSystemConfig("fuiou.pc.merchant.id");
        String merchantPwd = Config.getSystemConfig("fuiou.pc.key");

        // 获取请求对象
        HttpServletRequest request = getRequest();

        // 获取会话
        HttpSession session = request.getSession();

        // 获取数据库连接
        Connection conn = getConnection();

        // 获取参数
        String merchantCode = HttpUtils.getParameter(request, "mchnt_cd");  // 商户号
        String orderNum = HttpUtils.getParameter(request, "order_id");  // 传给富友的订单号
        String orderDate = HttpUtils.getParameter(request, "order_date");   // 订单时间，格式 YYYYMMDD
        String orderAmount = HttpUtils.getParameter(request, "order_amt");  // 订单金额
        String fuiouStatus = HttpUtils.getParameter(request, "order_st");   // 富友返回的订单（交易）状态
        String responseCode = HttpUtils.getParameter(request, "order_pay_code");    // 反馈编码
        String responseMessage = HttpUtils.getParameter(request, "order_pay_error");    // 反馈信息
        String resV1 = HttpUtils.getParameter(request, "resv1");    // 保留字段
        String fuiouSequence = HttpUtils.getParameter(request, "fy_ssn");   // 富友流水号
        String sign = HttpUtils.getParameter(request, "md5");   // 签名

        // 校验参数
        if(!StringUtils.isEmpty(responseCode) && !"0000".equals(responseCode)) {
            System.out.println("富友网关支付失败，参考信息：" + responseCode + "，" + responseMessage);
            MyException.newInstance(responseCode, responseCode + "，" + responseMessage).throwException();
        }
        if(StringUtils.isEmpty(responseCode) || StringUtils.isEmpty(merchantCode) || StringUtils.isEmpty(orderNum) || StringUtils.isEmpty(orderDate) || StringUtils.isEmpty(orderAmount) || StringUtils.isEmpty(fuiouStatus) || StringUtils.isEmpty(fuiouSequence) || StringUtils.isEmpty(sign)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        // 验签
        String rawString = merchantCode + "|" + orderNum + "|" + orderDate + "|" + orderAmount + "|" + fuiouStatus + "|" + responseCode + "|" + responseMessage + "|" + resV1 + "|" + fuiouSequence + "|" + merchantPwd;
        String md5Value = StringUtils.md5(rawString);
        if(!md5Value.equals(sign)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_SIGN_NOT_CURRECT, "数据签名不匹配").throwException();
        }

        // 校验商户编号
        if(!merchantId.equals(merchantCode)) {
            MyException.newInstance(ReturnObjectCode.ORDER_PAY_FAILED, "第三方数据有误").throwException();
        }

        // 校验富友的支付状态
        if(!fuiouStatus.equals("11")) {
            MyException.newInstance(ReturnObjectCode.ORDER_PAY_FAILED, "支付不成功").throwException();
        }

        // 构造登录用户，如果没有，则通过富友返回的订单号查找客户
        if (session.getAttribute(Config.SESSION_LOGINUSER_STRING) == null) {
            // 没有 session 用户，说明是富友后台返回的数据
            loginUser = fuiouService.getCustomerFromFuiouCallback(orderNum, conn);
        }
        else {
            loginUser = (CustomerPersonalPO) session.getAttribute(Config.SESSION_LOGINUSER_STRING);
        }
        if (loginUser == null) {
            MyException.newInstance(Config.getWords("w.gloabl.error.login"), "登录失效或者无法通过通联订单获得客户数据").throwException();
        }

        // 获取银行卡
        List<CustomerAccountPO> accounts = customerAccountService.getCustomerAccounts(loginUser.getId(), conn);
        if(accounts.size() == 0) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_BANKCARD_NOT_BIND, "未绑定银行卡").throwException();
        }
        String bankNumber = accounts.get(0).getNumber();

        // 查询订单，如果只有预约状态才可进行操作
        OrderPO order = orderService.getOrderByOrderNo(orderNum, conn);
        if(order.getStatus() != OrderStatus.Appointment) {
            // 如果已支付，直接返回到支付成功的页面，不用报错
            return "productSuccess";
        }

        fuiouService.sellProduction(orderNum, orderDate, conn);

        String userId = Config.getSystemConfig("web.default.operatorId");
        orderService.saleOrderOnlineFormNow(order, userId, conn);

        getResult().setMessage("购买产品请求已接受，正在等待银行确认，稍后检查订单状态。");
        return "productSuccess";

    }

    /**
     * 富友 PC 端支付网关页面回调
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月9日
     *
     * @return
     * @throws Exception
     */
    public String fuiouPCClientCallback() throws Exception {
        return this.fuiouPCPaymentCallback();
    }

    /**
     * 添加银行卡号码接口
     * 暂时不和富友的接口绑定，只保存到数据库
     *
     * @return
     * @throws Exception
     */
    //@Security(needToken = true, needMobileCode = true)
    public String addSalemanOuterBankCard() throws Exception {
        Connection conn = getConnection();
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String bankNumber = HttpUtils.getParameter(request, "bankNumber");                  // 银行卡号
        String bankCode = HttpUtils.getParameter(request, "bankCode");                  // 银行编码
        String reservedMobile = HttpUtils.getParameter(request, "reservedMobile");      // 预留手机号
        String idCard = HttpUtils.getParameter(request, "idCard");                      // 身份证号
        String salemanName = HttpUtils.getParameter(request, "salemanName");            // 销售姓名
        String salemanId = HttpUtils.getParameter(request, "salemanId");                // 销售ID
        String userId = Config.getSystemConfig("web.default.operatorId");             // 系统预留的操作者编号

        // 校验参数
        if (StringUtils.isEmpty(bankNumber)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少银行卡号").throwException();
        }
        if (StringUtils.isEmpty(bankCode)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少银行编码").throwException();
        }
        if (StringUtils.isEmpty(reservedMobile)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少预留手机号码").throwException();
        }
        if (StringUtils.isEmpty(salemanId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少销售人员编号").throwException();
        }
        if (StringUtils.isEmpty(salemanName)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少销售人员姓名").throwException();
        }
        if (reservedMobile.length() != 11 ) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "手机号码参数不正确").throwException();
        }

        // 双边去空
        bankNumber = bankNumber.trim();
        bankCode = bankCode.trim();
        reservedMobile = reservedMobile.trim();

        // 校验银行是否在系统的支持范围，如果支持，获取银行名称
        Map<String, String> banks = Config4Bank.getBanks(conn);
        if(!banks.containsKey(bankCode)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_BANK_CODE_NOT_FOUND, "暂时不支持该银行").throwException();
        }
        String bankName = banks.get(bankCode);

        // 如果身份证号、姓名参数为空，则校验是否已经实名认证
        CustomerCertificatePO certificatePO = customerCertificateService.loadByCustomerId(salemanId, "98", conn);
        if (certificatePO == null) {
            if(StringUtils.isEmpty(idCard) || StringUtils.isEmpty(salemanName)) {
                MyException.newInstance(ReturnObjectCode.CUSTOMER_NOT_REAL_AUTHENTICATION, "请您填写身份证号码，才能成功添加银行卡，或者您也可以在购买产品时支付过程中填写。").throwException();
            }
        }
        else {
            idCard = AesEncrypt.decrypt(certificatePO.getNumber());
        }

        // 进行富友实名认证
        Boolean isSuccess = fuiouAuthenticationService.authenticationByCustomerName(salemanName, reservedMobile, idCard, bankNumber, conn);

        // 没有认证通过
        if(!isSuccess) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_REBIND_BANK_CARD_FAILED, "您的信息没有通过，请重新检查您的银行卡账号是否支持绑定，或您的卡为信用卡（不允许使用），或您核对实名认证的身份证号、姓名是否与您本人一致。").throwException();
        }

        // 修改客户的身份证信息
        customerCertificateService.insertOrUpdateCertificate(salemanId, CustomerCertificateType.ID_CARD, AesEncrypt.encrypt(idCard), userId, conn);

        // 修改银行卡信息（在此处加密）
        Integer execCount = customerAccountService.insertBankAccountForSaleman(salemanId, AesEncrypt.encrypt(bankNumber), bankCode, bankName, conn);
        if(execCount != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "更新银行卡信息失败，请重新再试。").throwException();
        }

        // 修改真实姓名
        SalemanOuterService outerService = new SalemanOuterService();
        UserPO outerPO = outerService.loadSalemanById(salemanId, conn);
        if(outerPO == null) {
            MyException.newInstance(ReturnObjectCode.SALEMAN_OUTER_NOT_EXISTENT, "当前销售人员不存在").throwException();
        }
        outerPO.setName(salemanName);
        Integer realNameExecCount = outerService.update(outerPO, conn);
        if(realNameExecCount != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "身份信息操作失败").throwException();
        }

        return SUCCESS;
    }

    /**
     * 添加银行卡
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年1月5日
     *
     * @return
     * @throws Exception
     */
    @Security(needMobileCode = true)
    public String addBankCard() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 校验是否登录（Token 或 Session）
        try {
            Config.checkToken(request, conn);
        } catch (Exception e) {
            HttpSession session = request.getSession();
            CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute("loginUser");
            if(loginUser == null) {
                e.printStackTrace();
                throw e;
            }
        }

        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");              // 客户编号
        String name = HttpUtils.getParameter(request, "name");                          // 真实姓名
        String idCard = HttpUtils.getParameter(request, "idCard");                      // 身份证号
        String bankNumber = HttpUtils.getParameter(request, "bankNumber");                  // 银行卡号
        String bankCode = HttpUtils.getParameter(request, "bankCode");                  // 银行编码
        String reservedMobile = HttpUtils.getParameter(request, "reservedMobile");      // 预留手机号
        String userId = Config.getSystemConfig("web.default.operatorId");             // 系统预留的操作者编号

        // 校验参数
        if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(bankNumber) || StringUtils.isEmpty(reservedMobile) || StringUtils.isEmpty(bankCode)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if(customerId.length() != 32 || reservedMobile.length() != 11 ) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 双边去空
        customerId = customerId.trim();
        bankNumber = bankNumber.trim();
        bankCode = bankCode.trim();
        reservedMobile = reservedMobile.trim();

        // 校验银行是否在系统的支持范围，如果支持，获取银行名称
        Map<String, String> banks = Config4Bank.getBanks(conn);
        if(!banks.containsKey(bankCode)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_BANK_CODE_NOT_FOUND, "暂时不支持该银行").throwException();
        }
        String bankName = banks.get(bankCode);


        // 校验身份证号
        CustomerCertificatePO certificatePO = null;
        if(StringUtils.isEmpty(idCard)) {
            certificatePO = customerCertificateService.loadByCustomerId(customerId, conn);
            if(certificatePO == null) {
                MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少身份证号").throwException();
            }
            idCard = certificatePO.getNumber();
        }
        else {
            idCard = AesEncrypt.encrypt(idCard);
        }

        // 加密身份证号
        // 查询绑定了该身份证账号的客户
        List<CustomerCertificatePO> certificates = customerCertificateService.listByCardAesString(idCard, "98", conn);
        if(certificates.size() > 1) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_ID_CARD_EXISTENT, "您的证件信息异常，请联系我们的客服热线，联系电话：0755-85024000").throwException();
        }
        // 校验是否本客户使用了本身份证号
        if(certificates.size() == 1) {
            certificatePO = certificates.get(0);
            if(!certificatePO.getCustomerId().equals(customerId) || !certificatePO.getNumber().equals(idCard)) {
                MyException.newInstance(ReturnObjectCode.CUSTOMER_ID_CARD_EXISTENT, "您的证件信息异常，请联系我们的客服热线，联系电话：0755-85024000").throwException();
            }
        }

        // 进行富友实名认证
        Boolean isSuccess = fuiouAuthenticationService.authenticationByCustomerName(name, reservedMobile, AesEncrypt.decrypt(idCard), bankNumber, conn);

        // 没有认证通过
        if(!isSuccess) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_REBIND_BANK_CARD_FAILED, "您的信息没有通过，请重新检查您的银行卡账号是否支持绑定，或您的卡为信用卡（不允许使用），或您核对实名认证的身份证号、姓名是否与您本人一致。").throwException();
        }

        // 修改客户的身份证信息
        customerCertificateService.insertOrUpdateCertificate(customerId, CustomerCertificateType.ID_CARD, idCard, userId, conn);

        // 修改银行卡信息（在此处加密）
        Integer execCount = customerAccountService.insertOrUpdateBankAccount(customerId, name, AesEncrypt.encrypt(bankNumber), bankCode, bankName, conn);

        // 修改客户的真实名字
        customerPersonalService.updateCustomerRealName(customerId, name, conn);

        // 修改客户的实名认证状态（同时身份证和银行卡）
        customerAuthenticationStatusService.saveAuthenticationStatus(customerId, CustomerAuthenticationStatus.AUTH_TYPE_ACCOUNT, conn);

        if(execCount != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "更新银行卡信息失败，请重新再试。").throwException();
        }

        return SUCCESS;

    }

    /**
     * 实名认证接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月31日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String authentication() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String name = HttpUtils.getParameter(request, "name");
        String mobile = HttpUtils.getParameter(request, "mobile");
        String idCard = HttpUtils.getParameter(request, "idCard");
        String bankNumber = HttpUtils.getParameter(request, "bankNumber");

        // 校验参数
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(name) || StringUtils.isEmpty(name) || StringUtils.isEmpty(name)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if (mobile.length() != 11 || idCard.length() != 18) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 进行实名认证
        Boolean isSuccess = fuiouAuthenticationService.authenticationByCustomerName(name, mobile, idCard, bankNumber, conn);

        // 构造返回的 JSON
        JSONObject jsonObject = new JSONObject();
        if (isSuccess) {
            jsonObject.put("isSuccess", 1);
        }
        else {
            jsonObject.put("isSuccess", 0);
        }

        // 返回数据
        getResult().setReturnValue(jsonObject);

        return SUCCESS;

    }

    /**
     * 验证支付状态
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月20日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String isPaied() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        String bizId = HttpUtils.getParameter(request, "orderid");

        // 校验参数
        if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(bizId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if(customerId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 实例化服务
        FuiouPaymentService paymentService = new FuiouPaymentService();
        Boolean isPaied = paymentService.isPaied(customerId, bizId, conn);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isPaied", isPaied == true ? 1 : 0);

        getResult().setReturnValue(jsonObject);

        return SUCCESS;

    }

    /**
     * 富友支付回调
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月18日
     *
     * @return
     * @throws Exception
     */
    public String paymentCallback() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String rcd = HttpUtils.getParameter(request, "rcd");
        String rdesc = HttpUtils.getParameter(request, "rdesc");
        String orderId = HttpUtils.getParameter(request, "orderid");
        String ono = HttpUtils.getParameter(request, "ono");
        String amt = HttpUtils.getParameter(request, "amt");
        String md5 = HttpUtils.getParameter(request, "md5");

        // 记录回调的参数
        String parameter = HttpUtils.getParametersStringValue(getRequest());

        logService.save("富友支付", "富友支付返回值 paymentCallback", parameter, conn);

        // 记录回调的日志
        StringBuffer log = new StringBuffer();
        log.append("状态码：" + rcd + "|");
        log.append("状态：" + rdesc + "|");
        log.append("富友订单号：" + orderId + "|");
        log.append("支付金额：" + amt + "|");
        log.append("支付卡号：" + ono + "|");
        log.append("回调时间：" + TimeUtils.getNow());
        logService.save("富友支付","富友支付回调", log.toString(), conn);

        // 校验参数
        if(StringUtils.isEmpty(rcd) || StringUtils.isEmpty(rdesc) || StringUtils.isEmpty(orderId) || StringUtils.isEmpty(md5)/* || StringUtils.isEmpty(ono) || StringUtils.isEmpty(amt)*/) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if(md5.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 参数去空与转换（金额单位转换为元，Double 型）
        rcd = rcd.trim();
        rdesc = rdesc.trim();
        orderId = orderId.trim();
        /*ono = ono.trim();
        amt = amt.trim();
        Double amtDouble = Double.parseDouble(amt);*/
        md5 = md5.trim();

        // 实例化服务
        String userId = Config.getSystemConfig("web.default.operatorId");

        // 校验支付结果
        if(!"0000".equals(rcd)) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "支付接口出现异常，异常码为：" +  rcd).throwException();
        }

        // 调用回调服务
        OrderPayPO orderPay = paymentService.paymentCallback(rcd, rdesc, orderId, null, null, md5, request.getRemoteAddr(), conn); // null ：ono, amtDouble

        if(orderPay == null) {
            MyException.newInstance(ReturnObjectCode.ORDER_PAY_FAILED, "订单支付失败").throwException();
        }

        // 查询客户
        String customerId = orderPay.getCustomerId();
        String name = orderPay.getCustomerName();
        CustomerPersonalPO personalPO = customerPersonalService.loadByCustomerPersonalId(customerId, conn);
        if(personalPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到客户").throwException();
        }

        // 获取并校验订单支付时的证件信息、银行卡信息
        String certificateNo = orderPay.getCustomerCertificateNo();
        String certificateType = orderPay.getCustomerCertificateType();
        String bankAccount = orderPay.getBankAccount();
        if(StringUtils.isEmpty(certificateNo) || StringUtils.isEmpty(certificateType) || StringUtils.isEmpty(bankAccount)) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询订单支付时的证件信息与银行卡信息").throwException();
        }

        // 新增或更新实名认证
        Integer authExecCount = customerCertificateService.insertOrUpdateCertificate(customerId, certificateType, certificateNo, userId, conn);
        if(authExecCount != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据库异常").throwException();
        }

        // 返回跳转页面的地址
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", "<a href='#'>支付成功</a>");

        getResult().setReturnValue(jsonObject);

        logService.save("富友支付", "富友支付返回值paymentCallback", parameter);

        return SUCCESS;

    }



    /**
     * 页面回调
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月20日
     *
     * @return
     * @throws Exception
     *
     * 作者：quan.zeng
     * 内容：修改代码
     * 时间：2015年12月23日
     * 描述：跨域发送消息
     *
     */
    public void homeCallback() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String rcd = HttpUtils.getParameter(request, "rcd");
        String rdesc = HttpUtils.getParameter(request, "rdesc");
        String orderId = HttpUtils.getParameter(request, "orderid");
        String ono = HttpUtils.getParameter(request, "ono");
        String amt = HttpUtils.getParameter(request, "amt");
        String md5 = HttpUtils.getParameter(request, "md5");

        // 校验参数
        if(StringUtils.isEmpty(rcd) || StringUtils.isEmpty(rdesc) || StringUtils.isEmpty(orderId) || StringUtils.isEmpty(md5)/* || StringUtils.isEmpty(ono) || StringUtils.isEmpty(amt)*/) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if(md5.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 参数去空与转换（金额单位转换为元，Double 型）
        rcd = rcd.trim();
        rdesc = rdesc.trim();
        orderId = orderId.trim();
        /*ono = ono.trim();
        amt = amt.trim();
        Double amtDouble = Double.parseDouble(amt);*/
        md5 = md5.trim();

        // 校验响应码，如果支付失败，抛异常
        if(!"0000".equals(rcd)) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "APP 支付失败，响应码为 " + rcd).throwException();
        }

        // 进行验签
        String key = StringUtils.md5(rcd + "|" + orderId + "|" + Config.getSystemConfig("fuiou.key")).toLowerCase();
        if(!md5.equals(key)) {
            MyException.newInstance(ReturnObjectCode.ORDER_PAY_ENCRYPE_FAILED, "您的支付环境不安全").throwException();
        }

        // 查询接口指令
        FuiouPaymentService paymentService = new FuiouPaymentService();
        APICommandPO apiCommand = paymentService.loadAPICommandByBizId(orderId, conn);
        if(apiCommand == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询该业务的接口指令").throwException();
        }

        // 查询订单支付
        String apiCommandId = apiCommand.getId();
        OrderPayPO orderPay = paymentService.loadOrderPay(apiCommandId, conn);
        if(orderPay == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询该业务的订单支付内容").throwException();
        }

        // 查询系统订单
        String sysOrderId = orderPay.getOrderId();
        OrderPO order = orderService.loadByOrderId(sysOrderId, conn);
        if(order == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到对应的系统订单").throwException();
        }
        Double money = order.getMoney();

        // 查询对单对应的产品
        String productionId = order.getProductionId();
        ProductionPO production = productionService.loadProductionById(productionId, conn);
        if(production == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到对应的产品").throwException();
        }
    }

    /**
     * 重新支付回调
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月20日
     *
     * @return
     * @throws Exception
     *
     * 作者：quan.zeng
     * 内容：修改代码
     * 时间：2015年12月23日
     * 描述：重新支付时跨域发送消息
     *
     */
    public void rePayCallback() throws Exception {
        getResponse().getWriter().print("<script>window.parent.postMessage('rePay','*');</script>");
    }

}
