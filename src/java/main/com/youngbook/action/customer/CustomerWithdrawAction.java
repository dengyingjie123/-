package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentDetailPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentStatus;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.vo.customer.CustomerWithdrawVO;
import com.youngbook.service.allinpay.AllinpayBatchPaymentDetailService;
import com.youngbook.service.customer.*;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.system.CaptchaService;
import com.youngbook.service.system.UserService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

public class CustomerWithdrawAction extends BaseAction {

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerAccountService customerAccountService;

    private CustomerWithdrawPO customerWithdraw = new CustomerWithdrawPO();
    private CustomerMoneyLogPO customerMoneyLog = new CustomerMoneyLogPO();
    private AllinpayBatchPaymentDetailPO allinpayBatchPaymentDetail=new AllinpayBatchPaymentDetailPO();
    private CustomerMoneyPO customerMoney = new CustomerMoneyPO();

    private CustomerWithdrawVO customerWithdrawVO = new CustomerWithdrawVO();

    private CustomerWithdrawService service = new CustomerWithdrawService();
    private CustomerMoneyService customerMoneyService = new CustomerMoneyService();

    private CaptchaService captchaService = new CaptchaService();
    private UserService userService = new UserService();
    private AllinpayBatchPaymentDetailService detailService = new AllinpayBatchPaymentDetailService();

    @Autowired
    CustomerMoneyLogService customerMoneyLogService;

    /**
     * 交易平台请求的 Action，实现客户资金提现
     * 前提是网站的 Customer 已经登录，提现后添加日志
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @throws Exception * 修改人：姚章鹏
     *                   时间：2015年7月13日17:39:44
     *                   内容：添加交易密码的验证
     * @author 邓超

     * @throws Exception
     *
     * 修改人：姚章鹏
     * 时间：2015年7月13日17:39:44
     * 内容：添加交易密码的验证
     */
    public String customerWithdrawForWeb() throws Exception {

        // 获取请求和会话对象
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();

        // 验证登录
        if (session.getAttribute(Config.SESSION_LOGINUSER_STRING) == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 获取验证码和验证码的用途代码
        String captcha = getRequest().getParameter("captcha");
        String u = getRequest().getParameter("u");
        // 校验验证码
        if (!captchaService.validateCode(captcha, u, getConnection())) {
            getResult().setMessage("验证码有误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 获取前台传入的交易密码
        String businessPwd = request.getParameter("transactionPassword");
        if (businessPwd == null || businessPwd.equals("")) {
            getResult().setMessage("请输入你的交易密码");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        else {
            // 对交易密码进行二次加密
            businessPwd = StringUtils.md5(businessPwd + Config.MD5String);
        }

        // 获取连接
        Connection conn = getConnection();

        // 实例化结果返回对象
        ReturnObject returnObject = new ReturnObject();

        // 获取登录用户
        CustomerPersonalPO loginUser = (CustomerPersonalPO)session.getAttribute(Config.SESSION_LOGINUSER_STRING);

        // 获取网站操作者用户
        UserPO operator = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);

        try {

            // 查询出用户，这里的用户不直接使用会话的 loginUser，目的是为安全和查询出的数据完整性考虑
            CustomerPersonalPO customer = customerPersonalDao.loadByCustomerPersonalId(loginUser.getId(), conn);
            if(customer == null) {
                getResult().setMessage("没有查到用户信息");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            // 查询是否设置交易密码，前往设置交易密码
            if (customer.getTransactionPassword().equals("")) {
                return "sendTransaction";
            }

            // 查出来的交易密码与输入的交易密码匹配
            if (customer.getTransactionPassword().equals(businessPwd)) {
                getResult().setMessage("交易密码错误");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            // 查询是否绑定银行卡
            CustomerAccountPO customerAccount = customerAccountService.getCustomerAccount(loginUser.getId(), conn);
            if(customerAccount == null) {
                getResult().setMessage("您没有绑定银行卡");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            // 执行操作
            try {

                CustomerWithdrawPO withdrawPO = new CustomerWithdrawPO();
                withdrawPO.setCustomerId(loginUser.getId());

                // 新增客户取现记录，返回客户编号
                String bizId = service.insertCustomerWithdraw4W(withdrawPO, operator, conn);

                // 根据客户查出资金
                CustomerMoneyPO moneyPO = new CustomerMoneyPO();
                moneyPO.setCustomerId(loginUser.getId());
                moneyPO.setState(Config.STATE_CURRENT);
                moneyPO = MySQLDao.load(moneyPO, CustomerMoneyPO.class);
                double currentMoney = moneyPO.getAvailableMoney();

                // 如果当前的资金比取现的资金小，抛出异常
                if (currentMoney < withdrawPO.getMoney()) {
                    getResult().setMessage("您的可用资金不足");
                    getRequest().setAttribute("returnObject", getResult());
                    return "info";
                }

                // 新增客户资金日志
                int intType = Integer.parseInt(customerMoneyLog.getType());
                if (intType != 2) {
                    getResult().setMessage("提现失败");
                    getRequest().setAttribute("returnObject", getResult());
                    return "info";
                }
                customerMoneyLog.setType(CustomerMoneyLogType.WithdrawOrPayment); // 提现类型 2
                customerMoneyLog.setContent("提现" + withdrawPO.getMoney() + "元");
                customerMoneyLog.setStatus(CustomerMoneyLogStatus.Default); // 默认状态：未提现
                customerMoneyLog.setCustomerId(loginUser.getId());
                customerMoneyLog.setBizId(bizId);
                MySQLDao.insertOrUpdate(customerMoneyLog, operator.getId(), conn);

                // 添加通联批量代付明细表
                allinpayBatchPaymentDetail.setSn(StringUtils.buildNumberString(MySQLDao.getSequence("SN"), 4));
//                allinpayBatchPaymentDetail.setBank_code(Config4W.BANK_CODE);   // 招商的测试的银行代码 308
                allinpayBatchPaymentDetail.setAccount_type("00");// 00 银行卡，01 存折
                allinpayBatchPaymentDetail.setAccount_no(customerAccount.getNumber());// 银行卡号
                allinpayBatchPaymentDetail.setAccount_name(customerAccount.getName());// 银行账户名字
                allinpayBatchPaymentDetail.setBank_name(customerAccount.getBankBranchName());// 开户名称
                allinpayBatchPaymentDetail.setAccount_pror(0);// 账户属性：0 代表私人
                allinpayBatchPaymentDetail.setAmount((int) (withdrawPO.getMoney() * 100));// 金额
                allinpayBatchPaymentDetail.setBizId(bizId);// 业务 Id
                allinpayBatchPaymentDetail.setBizType(Config4Status.STATUS_CUSTOMER_WITHDRAW_TYPE);// 提现类型
                allinpayBatchPaymentDetail.setStatus(AllinpayBatchPaymentStatus.UNFINISH);
                allinpayBatchPaymentDetail.setRevised(Config4Status.STATUS_ALLINPAY_REVISED_NULL); // 修正状态为初始的
                // 插入通联资金表
                detailService.insertAllinpaybatchPaymentDetail(allinpayBatchPaymentDetail, operator, conn);

                // 返回通知结果
                getResult().setMessage("提现成功，将在2-3个工作返回到你的账户上。");
                getRequest().setAttribute("returnObject", getResult());
                return "info";

            } catch (Exception e) {

                MyException.deal(e);

                // 插入客户资金日志
                customerMoneyLog.setType(CustomerMoneyLogType.WithdrawOrPayment);
                customerMoneyLog.setContent("提现失败");
                customerMoneyLog.setStatus(CustomerMoneyLogStatus.Failed);// 提现失败
                customerMoneyLog.setCustomerId(loginUser.getId());
                MySQLDao.insertOrUpdate(customerMoneyLog, conn);
                getResult().setMessage("提现失败");
                getRequest().setAttribute("returnObject", returnObject);
                return "info";

            }
        } catch (Exception e) {

            returnObject.setMessage(e.getMessage());
            getRequest().setAttribute("returnObject", returnObject);
            return ERROR;

        }
    }

    /**
     * 新增或修改的数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerWithdraw_insertOrUpdate.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     * @author 邓超
     */
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(customerWithdraw, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 查询单条数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/customer/CustomerWithdraw_load.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     * @author 邓超
     */
    public String load() throws Exception {
        customerWithdraw.setState(Config.STATE_CURRENT);
        customerWithdraw = MySQLDao.load(customerWithdraw, CustomerWithdrawPO.class);
        getResult().setReturnValue(customerWithdraw.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 后台获取数据
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, CustomerWithdrawVO.class);
        Pager pager = service.list(customerWithdrawVO, conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 后台删除
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        service.delete(customerWithdraw, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public CustomerWithdrawPO getCustomerWithdraw() {
        return customerWithdraw;
    }
    public void setCustomerWithdraw(CustomerWithdrawPO customerWithdraw) {
        this.customerWithdraw = customerWithdraw;
    }
    public CustomerWithdrawVO getCustomerWithdrawVO() {
        return customerWithdrawVO;
    }
    public void setCustomerWithdrawVO(CustomerWithdrawVO customerWithdrawVO) {this.customerWithdrawVO = customerWithdrawVO;}
    public CustomerWithdrawService getService() {
        return service;
    }
    public void setService(CustomerWithdrawService service) {
        this.service = service;
    }
    public CustomerMoneyPO getCustomerMoney() {
        return customerMoney;
    }
    public void setCustomerMoney(CustomerMoneyPO customerMoney) {
        this.customerMoney = customerMoney;
    }
    public CustomerMoneyService getCustomerMoneyService() {
        return customerMoneyService;
    }
    public void setCustomerMoneyService(CustomerMoneyService customerMoneyService) {this.customerMoneyService = customerMoneyService;}
    public CustomerMoneyLogPO getCustomerMoneyLog() {
        return customerMoneyLog;
    }
    public void setCustomerMoneyLog(CustomerMoneyLogPO customerMoneyLog) {
        this.customerMoneyLog = customerMoneyLog;
    }
    public CustomerMoneyLogService getCustomerMoneyLogService() {
        return customerMoneyLogService;
    }
    public void setCustomerMoneyLogService(CustomerMoneyLogService customerMoneyLogService) {this.customerMoneyLogService = customerMoneyLogService;}
    public OrderService getOrderService() {
        return orderService;
    }
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

}
