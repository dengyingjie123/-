package com.youngbook.action.customer;

import com.allinpay.ets.client.StringUtil;
import com.youngbook.action.BaseAction;
import com.youngbook.action.wsi.RequestObject;
import com.youngbook.action.wsi.ServiceInvoker;
import com.youngbook.annotation.Security;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Bank;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.fdcg.FdcgCommon;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.bank.BankUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerAccountDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.fdcg.*;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.service.allinpay.AllinpayAuthenticationService;
import com.youngbook.service.customer.CustomerAccountService;
import com.youngbook.service.customer.CustomerAuthenticationStatusService;
import com.youngbook.service.customer.CustomerCertificateService;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerAccountAction extends BaseAction {

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    LogService logService;

    private CustomerAccountPO customerAccount = new CustomerAccountPO();

    @Autowired
    CustomerAuthenticationStatusService customerAuthenticationStatusService;

    @Autowired
    UserService userService;

    @Autowired
    ICustomerAccountDao customerAccountDao;

    @Autowired
    CustomerAccountService customerAccountService;

    @Autowired
    CustomerCertificateService customerCertificateService;


    @Autowired
    AllinpayAuthenticationService allinpayAuthenticationService;



    public String phAddCard() throws Exception {

        String customerId = getLoginCustomer().getId();

        if (!StringUtils.isEmpty(customerId)) {
            List<CustomerAccountPO> customerAccounts = customerAccountService.getCustomerAccounts(customerId, getConnection());

            if (customerAccounts == null || customerAccounts.size() == 0) {
                return SUCCESS;
            }
            else {
                return "manyCards";
            }
        }

        return SUCCESS;
    }

    /**
     * 获取客户 1 张银行卡
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月10日
     */
    @Security(needToken = true)
    public String getCustomerBankCard() throws Exception {
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        String customerId = HttpUtils.getParameter(request, "customerId");
        // 校验参数合法性
        if(StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        List<CustomerAccountPO> list = customerAccountService.getCustomerAccounts(customerId, conn);
        // 校验是否为空
        if(list.size() == 0){
            getResult().setReturnValue(new Pager());
        } else {
            getResult().setReturnValue(list.get(0).toJsonObject());
        }
        return SUCCESS;
    }

    /**
     * 获取外部销售人员的所有银行卡
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月17日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String getSalemanOuterBankCards() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String salemanId = HttpUtils.getParameter(request, "salemanId");

        // 校验参数合法性
        if(StringUtils.isEmpty(salemanId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        // 查询银行卡
        List<CustomerAccountPO> list = customerAccountService.getCustomerAccounts(salemanId, conn);

        // 组装 JSON
        JSONArray jsonArray = new JSONArray();

        // 校验是否为空
        if(list.size() != 0){
            for(CustomerAccountPO po : list) {
                JSONObject jsonObject = new JSONObject();
                String accountId = po.getId();
                String bankCode=po.getBankCode();
                String accountNumber = StringUtils.replaceToStars(po.getNumber(),4, 4);
                jsonObject.put("id", accountId);
                jsonObject.put("number", accountNumber);
                // 获取支持的银行
                Map<String, String> banks = Config4Bank.getBanks(conn);
                if(!banks.containsKey(bankCode)) {
                    MyException.newInstance(ReturnObject.CODE_EXCEPTION, "暂时不支持该银行").throwException();
                }
                jsonObject.put("bank", banks.get(bankCode));
                jsonObject.put("bankCode", po.getBankCode());
                jsonArray.add(jsonObject);
            }
        }

        // 返回 JSON
        getResult().setReturnValue(jsonArray);

        return SUCCESS;
    }

    public String loadPage_PH_card_list() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        if (StringUtils.isEmpty(customerId)) {
            CustomerPersonalVO customerVOInSession = Config.getLoginCustomerVOInSession(getRequest());

            if (customerVOInSession != null) {
                customerId = customerVOInSession.getId();
            }


        }

        List<CustomerAccountPO> customerAccounts = customerAccountService.getCustomerAccounts(customerId, getConnection());


        if (customerAccounts != null) {
            getRequest().setAttribute("customerAccounts", customerAccounts);
        }


        /**
         * 获得存管银行信息
         */
//        if (!StringUtils.isEmpty(customerId)) {
//            FdcgCustomerAccountPO fdcgCustomerAccountPO = customerAccountService.fdcgGetCustomerAccountPO(customerId, FdcgCustomerAccountBindStatus.Bind, getConnection());
//            if (fdcgCustomerAccountPO != null) {
//                getRequest().setAttribute("fdcgCustomerAccountPO", fdcgCustomerAccountPO);
//            }
//
//        }


        return "card_list";
    }

    public String loadPage_dehecircle_card_list() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        if (StringUtils.isEmpty(customerId)) {
            CustomerPersonalVO customerVOInSession = Config.getLoginCustomerVOInSession(getRequest());

            if (customerVOInSession != null) {
                customerId = customerVOInSession.getId();
            }


        }

        List<CustomerAccountPO> customerAccounts = customerAccountService.getCustomerAccounts(customerId, getConnection());


        if (customerAccounts != null) {
            getRequest().setAttribute("customerAccounts", customerAccounts);
        }


        return "card_list";
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
        customerAccountBindPO.setReturnUrl(Config.getSystemConfig("system.url") + "/customer/CustomerAccount_fdcgDealReturnBind");
        customerAccountBindPO.setNotifyUrl(Config.getSystemConfig("system.url") +"/customer/fdcgDealNotifyBind");
        customerAccountBindPO.setOrderDate(TimeUtils.getNowDateYYYYMMDD());
        customerAccountBindPO.setOrderNo(FdcgCommon.getId20());

        String data = customerAccountBindPO.toJsonObject().toString();

        String sign = FdcgCommon.sign(data);

        getResult().setReturnValue(sign);

        return SUCCESS;
    }


    public String fdcgDealNotifyUnbind() throws Exception {

        String name = "用户解绑银行卡-异步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealNotify(name, getRequest(), getConnection());
    }

    public String fdcgDealNotifyBind() throws Exception {

        String name = "用户绑定银行卡-异步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealNotify(name, getRequest(), getConnection());
    }

    public String fdcgDealReturnUnbind() throws Exception {

        String name = "用户解绑银行卡-同步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealReturn(name, getRequest(), getConnection());
    }


    public String fdcgGetUnbindData() throws Exception {

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
        customerAccountBindPO.setReturnUrl(Config.getSystemConfig("system.url") + "/customer/CustomerAccount_fdcgDealReturnUnbind");
        customerAccountBindPO.setNotifyUrl(Config.getSystemConfig("system.url") +"/customer/fdcgDealNotifyUnbind");
        customerAccountBindPO.setOrderDate(TimeUtils.getNowDateYYYYMMDD());
        customerAccountBindPO.setOrderNo(FdcgCommon.getId20());

        String data = customerAccountBindPO.toJsonObject().toString();

        String sign = FdcgCommon.sign(data);

        getResult().setReturnValue(sign);

        return SUCCESS;
    }



    public String fdcgDealReturnBind() throws Exception {

        String name = "用户绑定银行卡-同步回调";

        logService.save("富滇存管回调", name, getRequest(), getConnection());

        return FdcgCommon.dealReturn(name, getRequest(), getConnection());
    }

    /**
     * 获取客户的所有银行卡
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年1月7日
     *
     * @return
     * @throws Exception
     */
    public String getCustomerBankCards() throws Exception {
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        String customerId = HttpUtils.getParameter(request, "customerId");
        // 校验参数合法性
        if(StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }



        // 组装 JSON
        JSONArray jsonArray = new JSONArray();

        List<CustomerAccountPO> list = customerAccountService.getCustomerAccounts(customerId, conn);
        if (list != null) {
            for(CustomerAccountPO po : list) {
                JSONObject jsonObject = new JSONObject();
                String accountId = po.getId();
                String bankCode=po.getBankCode();
                String accountNumber = po.getNumber();
                jsonObject.put("id", accountId);
                jsonObject.put("number", accountNumber);
                // 获取支持的银行
                Map<String, String> banks = Config4Bank.getBanks(conn);

                String bankName = banks.get(bankCode);
                if (StringUtils.isEmpty(bankName)) {
                    bankName = po.getBank();
                }
                jsonObject.put("bank", bankName);
                jsonObject.put("bankCode", po.getBankCode());
                jsonArray.add(jsonObject);
            }
        }



        /**
         * 构建存管账户
         */
        FdcgCustomerAccountPO fdcgCustomerAccountPO = customerAccountService.fdcgGetCustomerAccountPO(customerId, FdcgCustomerAccountBindStatus.Bind, conn);

        if (fdcgCustomerAccountPO != null) {
            JSONObject jsonObject = new JSONObject();
            String accountId = fdcgCustomerAccountPO.getId();
            String bankCode = fdcgCustomerAccountPO.getBankCode();
            String accountNumber = fdcgCustomerAccountPO.getBankAccountNo();
            jsonObject.put("id", accountId);
            jsonObject.put("number", accountNumber);
            // 获取支持的银行
            Map<String, String> banks = Config4Bank.getBanks(conn);

            String bankName = banks.get(bankCode);
            if (StringUtils.isEmpty(bankName)) {
                bankName = fdcgCustomerAccountPO.getBank();
            }
            jsonObject.put("bank", bankName);
            jsonObject.put("bankCode", fdcgCustomerAccountPO.getBankCode());
            jsonArray.add(jsonObject);
        }


        // 返回 JSON
        getResult().setReturnValue(jsonArray);
        return SUCCESS;
    }

    /**
     * 解绑银行卡
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2016年1月6日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String unBindBankCard() throws Exception {

        String mobile400 = Config.getSystemConfig("hwbanksapp_400");

        MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "暂时不能解绑银行卡，如您需要更换银行卡请联系客服！【"+mobile400+"】").throwException();

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        String bankNumber = HttpUtils.getParameter(request, "bankNumber");

        // 校验参数
        if (StringUtils.isEmpty(customerId) || StringUtils.isEmpty(bankNumber)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if (customerId.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 双边去空
        customerId = customerId.trim();
        bankNumber = bankNumber.trim();

        // 解绑银行卡
        Integer execCount = customerAccountService.unBindBankAccount(customerId, bankNumber, conn);

        if(execCount != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "解绑银行卡信息失败，请重新再试。").throwException();
        }

        return SUCCESS;
    }

    public String insertOrUpdateForFuiou() throws Exception {

        String bankCode = getHttpRequestParameter("bankCode");
        String number = getHttpRequestParameter("number");
        String bankBranchName = getHttpRequestParameter("bankBranchName");
        String customerId = getHttpRequestParameter("customerId");

        CustomerAccountPO customerAccountPO = new CustomerAccountPO();
        customerAccountPO.setBankCode(bankCode);
        customerAccountPO.setNumber(number);
        customerAccountPO.setBankBranchName(bankBranchName);
        customerAccountPO.setCustomerId(customerId);


        KVObjects banks = new KVObjects();
        banks.addItem("0801020000","中国工商银行");
        banks.addItem("0801030000","中国农业银行");
        banks.addItem("0801040000","中国银行");
        banks.addItem("0801050000","中国建设银行");
        banks.addItem("0801000000","中国邮政储蓄银行");
        banks.addItem("0804100000","平安银行");
        banks.addItem("0803050000","中国民生银行");
        banks.addItem("0803030000","中国光大银行");
        banks.addItem("0803060000","广发银行");
        banks.addItem("0803020000","中信银行");
        banks.addItem("0803090000","兴业银行");
        banks.addItem("0803040000","华夏银行");
        banks.addItem("0803010000","交通银行");
        banks.addItem("08030080000","招商银行");
        banks.addItem("0803100000","上海浦东发展银行");
        banks.addItem("0804031000","北京银行");
        banks.addItem("0804010000","上海银行");


        String bank = banks.getItem(bankCode).toString();
        customerAccountPO.setBank(bank);

        customerAccountService.insertOrUpdate(customerAccountPO, "0000", getConnection());

        return SUCCESS;
    }

    /**
     * 新增或修改银行编码的数据
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-11-12
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        Map<String, String> banks = Config4Bank.getBanks(getConnection());

        // 修正数据
        String bankCode = customerAccount.getBank();
        customerAccount.setBankCode(bankCode);

        if(banks.containsKey(bankCode)) {
            customerAccount.setBank(banks.get(bankCode));
        }

        /**
         * 修改和删除需要设定权限
         */
        boolean isNewAccount = true;

        if (!StringUtils.isEmpty(customerAccount.getId())) {
            isNewAccount = false;
        }

        if (!isNewAccount) {
            if (!hasPermission("客户管理_机构客户管理_账号维护")) {
                MyException.newInstance("没有操作权限").throwException();
            }
        }

        customerAccountService.insertOrUpdate(customerAccount, getLoginUser().getId(), getConnection());

        return SUCCESS;
    }

    /**
     * 删除一条数据
     * 通过 ID 或 SID，把查询出来的记录状态设置为已删除，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerAccount_delete.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     */
    public String delete() throws Exception {

        String accountId = getHttpRequestParameter("id");

        StringUtils.checkIsEmpty(accountId, "客户账号编号");

        if (!hasPermission("客户管理_机构客户管理_账号维护")) {
            MyException.newInstance("没有操作权限").throwException();
        }

        String userId = Config.getDefaultOperatorId();
        if (getLoginUser() != null) {
            userId = getLoginUser().getId();
        }

        customerAccountService.removeById(accountId, userId, getConnection());
        return SUCCESS;
    }

    /**
     * 查询单条数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/customer/CustomerAccount_load.action，如未成功，请检查 struts 配置
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String load() throws Exception {
        customerAccount.setState(Config.STATE_CURRENT);
        customerAccount = MySQLDao.load(customerAccount, CustomerAccountPO.class);
        //解决银行卡号码
        if(!StringUtils.isEmpty(customerAccount.getNumber()))
        {
            customerAccount.setNumber(AesEncrypt.decrypt(customerAccount.getNumber()));
        }
        getResult().setReturnValue(customerAccount.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 查询出所有的数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/customer/CustomerAccount_list.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        Pager pager = customerAccountService.list(customerAccount, request, getConnection());
        getResult().setReturnValue(pager.toJsonCustomerAccountPO());
        return SUCCESS;
    }

    /**
     * 绑定银行卡信息（旧版）
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-10-9
     *
     * @author 邓超
     * @return String
     * @throws Exception
     */
    public String bind() throws Exception {

        return this.verifyRealNameAndBindingBankCard();

    }


    /**
     * 认证实名和绑定银行卡信息（新版）
     *
     * 修改：李昕骏
     * 2016年5月13日 09:59:30
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年11月27日
     *
     * @author 姚章鹏
     * @return String
     * @throws Exception
     */
    @Security(needWebLogin = true)
    public String verifyRealNameAndBindingBankCard() throws Exception {

        HttpServletRequest request = this.getRequest();
        HttpSession session = request.getSession();

        // 登录用户信息
        CustomerPersonalPO loginCustomer = Config.getLoginCustomerInSession(getRequest());

        // 数据库连接
        Connection conn = getConnection();


        // 获得待验证的银行卡号和身份证号
        String accountNumber = customerAccount.getNumber();
        String accountName = customerAccount.getName();
        String realId = request.getParameter("realId");
        if (StringUtils.isEmpty(accountNumber) || StringUtils.isEmpty(accountName) || StringUtils.isEmpty(realId)) {
            MyException.newInstance(Config.getWords("w.customer.auth.bank.missingdata.error"), "认证的银行持卡信息不完整").throwException();
        }


        boolean hasCustomerIdNumberTaken = customerPersonalService.hasCustomerIdNumberTaken(loginCustomer.getId(), realId, conn);
        if (hasCustomerIdNumberTaken) {
            MyException.newInstance("您输入的身份证号已被注册，如有疑问，请联系客服").throwException();
        }

        // 认证状态里查询是否绑定过银行卡
        CustomerBankAuthenticationPO authenticationStatus = customerPersonalService.getBankauthenticationStatus(loginCustomer.getId(), Config4Status.STATUS_CUSTOMER_BANKCARD_UNFINISH, conn);
        if (authenticationStatus != null) {
            String message = Config.getWords("customer.account.band.mutilCards");
            MyException.newInstance(message, "客户多次添加银行卡").throwException();
        }

        // 检测是否支持该银行卡
        customerAccount.setCustomerId(loginCustomer.getId());
        customerAccount.setNumber(accountNumber); // 加密存储
        Boolean hasBankCode = false;
        String bankCode = customerAccount.getBankCode();
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
        int customerAccountStatus = customerAccountDao.getBankCardCount(loginCustomer.getId(), conn);
        if (CustomerAccountStatus.ONLY_ONE_CARD == customerAccountStatus || CustomerAccountStatus.MORE_THAN_ONE_CARD == customerAccountStatus) {
            String message = Config.getWords("customer.account.band.mutilCards");
            MyException.newInstance(message, "客户已绑定过银行卡，请勿重复绑定").throwException();
        }


        int validateIdentityCode = CustomerPersonalValidateIdentityCode.UNKNOW;
        RequestObject requestObject = new RequestObject();
        requestObject.setName("allinpay.realNameVerification.v2");
        requestObject.setToken(Config.getSystemConfig("wsi.token"));
        requestObject.addData("name", customerAccount.getName());
        requestObject.addData("number", realId);
        requestObject.addData("bankCode", customerAccount.getBankCode());
        requestObject.addData("bankNumber", customerAccount.getNumber());
        ServiceInvoker invoker = new ServiceInvoker();
        ReturnObject returnObject = invoker.invoke(requestObject);

        if (returnObject.getCode() != ReturnObject.CODE_SUCCESS) {
            MyException.newInstance(Config.getWords4WebGeneralError(), "实名认证接口v2调用失败").throwException();
        }

        validateIdentityCode = Integer.parseInt(returnObject.getReturnValue().toString());

        String thirdPartyMessage = "【支付渠道反馈: " + returnObject.getMessage() + "】";

        // 未知错误
        if(validateIdentityCode == CustomerPersonalValidateIdentityCode.UNKNOW) {
            MyException.newInstance(Config.getWords("w.customer.auth.bank.customerinfo.error") + thirdPartyMessage, "您填写的信息有误，请核对后提交").throwException();
        }
        // isPass：3009  无此账户
        if(validateIdentityCode == CustomerPersonalValidateIdentityCode.ERROR_BANK_NUMBER) {
            MyException.newInstance(Config.getWords("w.customer.auth.bank.nocard.error") + thirdPartyMessage, "您填写的信息有误，无此账户").throwException();
        }
        // isPass：3031 账号户名不符
        if(validateIdentityCode == CustomerPersonalValidateIdentityCode.ERROR_CUSTOMER_NAME) {
            MyException.newInstance(Config.getWords("w.customer.auth.bank.accountname.error"), "您填写的信息有误，账号户名不符").throwException();
        }

        if (validateIdentityCode != CustomerPersonalValidateIdentityCode.PASS) {
            MyException.newInstance(Config.getWords("w.customer.auth.bank.customerinfo.error") + thirdPartyMessage, "您填写的信息有误，账号户名不符").throwException();
        }



        // 验证通过，执行绑定
        UserPO operator = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);

        // 身份认证通过后，姓名以认证通过后的为准，因此修改
        customerPersonalService.updateCustomerRealName(loginCustomer.getId(), accountName, conn);
        // 设置用户信息
        session.setAttribute(Config.SESSION_LOGINUSER_STRING, loginCustomer);

        // 判断是否有实名数据
        CustomerCertificatePO customerCertificatePO = customerAuthenticationStatusService.loadCertificate(loginCustomer.getId(), conn);
        if(customerCertificatePO == null) {
            customerCertificateService.insertOrUpdateCertificate(loginCustomer.getId(), CustomerCertificateType.ID_CARD, realId, operator.getId(), conn);
        }

        // 执行绑定
        customerAccount.setCustomerType(CustomerType.insideCustomer);   // 内部客户
        customerAccount.setNumber(AesEncrypt.encrypt(customerAccount.getNumber()));
        int count = customerAccountService.insertCustomerAccount4W(customerAccount, conn);

        if (count != 1) {
            MyException.newInstance("绑定失败", "绑定失败").throwException();
        }

        // 新增或修改认证状态
        count = customerAuthenticationStatusService.updateCustomerAccountAuthStatus(loginCustomer.getId(), operator.getId(), conn);

        if (count != 1) {
            MyException.newInstance("绑定失败", "绑定失败").throwException();
        }


        return SUCCESS;

    }

    /**
     * 检测是否绑定了银行卡
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-10-9
     *
     * @author 邓超
     * @return String
     * @throws Exception
     */
    public String isBinded() throws Exception {

        String customerId = (String) getRequest().getSession().getAttribute("loginUserId");
        Connection conn = getConnection();

        // 获取银行卡认证表的数据
        CustomerBankAuthenticationPO bankauthentication = allinpayAuthenticationService.getBankAuthenticationStatus(customerId, Config4Status.STATUS_CUSTOMER_BANKCARD_SUCCESS, conn);

        if (bankauthentication == null) {
            // 查询历史绑定数据
            bankauthentication = allinpayAuthenticationService.getBankAuthenticationStatus(customerId, Config4Status.STATUS_CUSTOMER_BANKCARD_SUCCESS, conn);
            if (bankauthentication == null) {
                MyException.newInstance("认证失败，请稍后再试", "查询不到银行卡认证数据").throwException();
            }
        }

        CustomerAccountPO accountPO = new CustomerAccountPO();
        accountPO.setState(Config.STATE_CURRENT);
        accountPO.setCustomerId(customerId);
        accountPO = MySQLDao.load(accountPO, CustomerAccountPO.class, conn);

        if(accountPO == null) {
            MyException.newInstance("认证失败，请稍后再试", "查询不到客户的银行卡账户信息").throwException();
        }

        getRequest().setAttribute("accountName", accountPO.getName());

        String accountNo= AesEncrypt.decrypt(accountPO.getNumber());// 解密银行卡号
        accountNo = accountNo.substring(0,4)+"********"+accountNo.substring(accountNo.length()-5,accountNo.length()-1);

        getRequest().setAttribute("accountNo", accountNo);//账号解密显示
        return SUCCESS;

    }

    /**
     * 获取银行列表（JSONArray）
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
    public String getBankList() throws Exception {
        getResult().setReturnValue(BankUtils.getBankList(getConnection()));
        return SUCCESS;
    }

    public CustomerAccountPO getCustomerAccount() {
        return customerAccount;
    }
    public void setCustomerAccount(CustomerAccountPO customerAccount) {
        this.customerAccount = customerAccount;
    }


}
