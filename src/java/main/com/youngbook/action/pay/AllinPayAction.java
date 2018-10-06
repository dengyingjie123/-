package com.youngbook.action.pay;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.allinpay.AllinPayUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderCallBackPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderTradingStatus;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderPayChannel;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.service.allinpay.AllinPayOrderCallBackService;
import com.youngbook.service.allinpay.AllinPayOrderService;
import com.youngbook.service.customer.*;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.production.ProductionService;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

/**
 * Created by 张舜清 on 2015/8/5.
 */

public class AllinPayAction extends BaseAction {

    @Autowired
    LogService logService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductionService productionService;

    @Autowired
    AllinPayOrderCallBackService allinPayOrderCallBackService;

    private AllinPayOrderPO allinPayOrder = new AllinPayOrderPO();

    private AllinPayOrderCallBackPO allinPayOrderCallBack = new AllinPayOrderCallBackPO();

    private CustomerDepositPO customerDeposit = new CustomerDepositPO();

    private CustomerMoneyPO customerMoney = new CustomerMoneyPO();

    @Autowired
    CustomerMoneyService customerMoneyService;

    @Autowired
    CustomerDepositService customerDepositService;

    private CustomerMoneyLogPO customerMoneyLog = new CustomerMoneyLogPO();

    @Autowired
    CustomerMoneyLogService customerMoneyLogService;

    private OrderPO order = new OrderPO();


    private ProductionPO production = new ProductionPO();

    @Autowired
    UserService userService;

    private CustomerRefundService customerRefundService = new CustomerRefundService();
    private CustomerWithdrawService customerWithdrawService = new CustomerWithdrawService();


    /**
     * 创建人：张舜清
     * 时间：8/5/2015
     * 内容：这是web客户充值后进行跳转要处理数据的action，展示到web显示
     *
     * @return
     * @throws Exception
     */
    public String pickUp() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = getConnection();
        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute(Config.SESSION_LOGINUSER_STRING);
        // 得到通联支付返回来的指定参数
        String merchantId = request.getParameter("merchantId");
        String orderDatetime = request.getParameter("orderDatetime");
        String orderNo = request.getParameter("orderNo");

        if (loginUser == null) {
            MyException.newInstance(Config.getWords("w.gloabl.error.login"), "登录失效").throwException();
        }

        // 通联支付接口返回的指定参数进行非空验证
        if (StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(orderDatetime) || StringUtils.isEmpty(orderNo)) {
            // 支付参数失效
            MyException.newInstance(Config.getWords("w.gloabl.error.unknown"), "支付参数失效").throwException();
        }



        // 传入参数验证完成，执行业务操作

        // 通联支付结果返回表里操作，返回结果只保存一条
        allinPayOrderCallBack.setState(Config.STATE_CURRENT);
        allinPayOrderCallBack.setMerchantId(merchantId);
        allinPayOrderCallBack.setOrderDatetime(orderDatetime);
        allinPayOrderCallBack.setOrderNo(orderNo);
        allinPayOrderCallBack = MySQLDao.load(allinPayOrderCallBack, AllinPayOrderCallBackPO.class, conn);
        if (allinPayOrderCallBack == null) {
//            allinPayOrderCallBackService.insertAllinPayOrderCallBack(request, conn);
        }

        // 通联支付交互表操作
        allinPayOrder.setState(Config.STATE_CURRENT);
        allinPayOrder.setMerchantId(merchantId);
        allinPayOrder.setOrderDatetime(orderDatetime);
        allinPayOrder.setOrderNo(orderNo);
        allinPayOrder = MySQLDao.load(allinPayOrder, AllinPayOrderPO.class, conn);
        if (allinPayOrder == null) {
            getResult().setMessage("系统生成充值订单错误，请重新充值");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        } else {
            String date = allinPayOrder.getOrderDatetime();
            date = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10) + date.substring(11, 13) + date.substring(14, 16) + date.substring(17);
            if (!allinPayOrder.getMerchantId().equals(merchantId) || !date.equals(orderDatetime) || !allinPayOrder.getOrderNo().equals(orderNo)) {
                getResult().setMessage("充值校验失败，请重新充值");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            // 由于通联支付结果返回有两次，这里判断是避免出现重复数据，如果通联支付查询出来的数据的支付状态已经为受理通过就直接返回到客户主页
            if (allinPayOrder.getTradingStatus() == AllinPayOrderTradingStatus.Accepted) {
                return "depositSuccess";
            }


            // 以上验证通过后对通联支付表进行自定义的规格数据库操作
            allinPayOrder.setState(Config.STATE_UPDATE);
            MySQLDao.update(allinPayOrder, conn);
            allinPayOrder.setSid(MySQLDao.getMaxSid("bank_AllinPayTransfer", conn));
            allinPayOrder.setState(Config.STATE_CURRENT);
            allinPayOrder.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            allinPayOrder.setOperateTime(TimeUtils.getNow());
            allinPayOrder.setTradingStatus(AllinPayOrderTradingStatus.Accepted);
            MySQLDao.insert(allinPayOrder, conn);

            // 对客户充值记录进行更新操作
            customerDeposit.setState(Config.STATE_CURRENT);
            customerDeposit.setId(allinPayOrder.getBizId());
            customerDeposit = MySQLDao.load(customerDeposit, CustomerDepositPO.class, conn);
            if (customerDeposit == null) {
                getResult().setMessage("没有充值记录");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            }

            customerDeposit.setStatus(String.valueOf(Config4Status.STATUS_CUSTOMER_DEPOSIT_SUCCESS));
            customerDepositService.insertOrUpdate4W(customerDeposit, conn);

            // 增加客户的资金，单位是分转换为元
            double money = allinPayOrder.getOrderAmount() / 100;
            customerMoney.setCustomerId(loginUser.getId());
            customerMoney.setState(Config.STATE_CURRENT);
            customerMoney = MySQLDao.load(customerMoney, CustomerMoneyPO.class, conn);
            customerMoney.setAvailableMoney(money + customerMoney.getAvailableMoney());
            customerMoneyService.insertOrUpdate4W(customerMoney, conn);

            // 增加资金日志记录
            customerMoneyLog.setType(CustomerMoneyLogType.Deposit);
            customerMoneyLog.setContent(CustomerMoneyLogType.Deposit + money + "元");
            customerMoneyLog.setStatus(CustomerMoneyLogStatus.Success);
            customerMoneyLog.setBizId(customerDeposit.getId());
            customerMoneyLog.setCustomerId(loginUser.getId());
            MySQLDao.insertOrUpdate(customerMoneyLog, conn);

            return "depositSuccess";
        }
    }

    /**
     * 创建人：张舜清
     * 时间：8/5/2015
     * 内容：充值成功返回我们服务器处理，不展示给客户显示
     *
     * @return
     * @throws Exception
     */
    public String receive() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = getConnection();
        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute(Config.SESSION_LOGINUSER_STRING);
        // 得到通联支付返回来的指定参数
        String merchantId = request.getParameter("merchantId");
        String orderDatetime = request.getParameter("orderDatetime");
        String orderNo = request.getParameter("orderNo");

        if (loginUser == null) {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 通联支付接口返回的指定参数进行非空验证
        if (StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(orderDatetime) || StringUtils.isEmpty(orderNo)) {
            getResult().setMessage("参数错误");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }

        // 通联支付结果返回表里操作，返回结果只保存一条
        allinPayOrderCallBack.setState(Config.STATE_CURRENT);
        allinPayOrderCallBack.setMerchantId(merchantId);
        allinPayOrderCallBack.setOrderDatetime(orderDatetime);
        allinPayOrderCallBack.setOrderNo(orderNo);
        allinPayOrderCallBack = MySQLDao.load(allinPayOrderCallBack, AllinPayOrderCallBackPO.class, conn);
        if (allinPayOrderCallBack == null) {
//            allinPayOrderCallBackService.insertAllinPayOrderCallBack(request, conn);
        }

        // 通联支付交互表操作
        allinPayOrder.setState(Config.STATE_CURRENT);
        allinPayOrder.setMerchantId(merchantId);
        allinPayOrder.setOrderDatetime(orderDatetime);
        allinPayOrder.setOrderNo(orderNo);
        allinPayOrder = MySQLDao.load(allinPayOrder, AllinPayOrderPO.class, conn);
        if (allinPayOrder == null) {
            getResult().setMessage("系统生成充值订单错误，请重新充值");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        } else {
            String date = allinPayOrder.getOrderDatetime();
            date = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10) + date.substring(11, 13) + date.substring(14, 16) + date.substring(17);
            if (!allinPayOrder.getMerchantId().equals(merchantId) || !date.equals(orderDatetime) || !allinPayOrder.getOrderNo().equals(orderNo)) {
                getResult().setMessage("充值校验失败，请重新充值");
                getRequest().setAttribute("returnObject", getResult());
                return "info";
            } else {
                // 由于通联支付结果返回有两次，这里判断是避免出现重复数据，如果通联支付查询出来的数据的支付状态已经为受理通过就直接返回到客户主页
                if (allinPayOrder.getTradingStatus() == AllinPayOrderTradingStatus.Accepted) {
                    return SUCCESS;
                } else {
                    // 以上验证通过后对通联支付表进行自定义的规格数据库操作
                    allinPayOrder.setState(Config.STATE_UPDATE);
                    MySQLDao.update(allinPayOrder, conn);
                    allinPayOrder.setSid(MySQLDao.getMaxSid("bank_AllinPayTransfer", conn));
                    allinPayOrder.setState(Config.STATE_CURRENT);
                    allinPayOrder.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                    allinPayOrder.setOperateTime(TimeUtils.getNow());
                    allinPayOrder.setTradingStatus(AllinPayOrderTradingStatus.Accepted);
                    MySQLDao.insert(allinPayOrder, conn);

                    // 对客户充值记录进行更新操作
                    customerDeposit.setState(Config.STATE_CURRENT);
                    customerDeposit.setId(allinPayOrder.getBizId());
                    customerDeposit = MySQLDao.load(customerDeposit, CustomerDepositPO.class, conn);
                    if (customerDeposit == null) {
                        getResult().setMessage("没有充值记录");
                        getRequest().setAttribute("returnObject", getResult());
                        return "info";
                    }
                    customerDeposit.setStatus(String.valueOf(Config4Status.STATUS_CUSTOMER_DEPOSIT_SUCCESS));
                    customerDepositService.insertOrUpdate4W(customerDeposit, conn);

                    // 增加客户的资金，单位是分转换为元
                    double money = allinPayOrder.getOrderAmount() / 100;
                    customerMoney.setCustomerId(loginUser.getId());
                    customerMoney.setState(Config.STATE_CURRENT);
                    customerMoney = MySQLDao.load(customerMoney, CustomerMoneyPO.class, conn);
                    customerMoney.setAvailableMoney(money + customerMoney.getAvailableMoney());
                    customerMoneyService.insertOrUpdate4W(customerMoney, conn);

                    // 增加资金日志记录
                    customerMoneyLog.setType(CustomerMoneyLogType.Deposit);
                    customerMoneyLog.setContent(CustomerMoneyLogType.Deposit + money + "元");
                    customerMoneyLog.setStatus(CustomerMoneyLogStatus.Success);
                    customerMoneyLog.setBizId(customerDeposit.getId());
                    customerMoneyLog.setCustomerId(loginUser.getId());
                    MySQLDao.insertOrUpdate(customerMoneyLog, conn);

                    return "depositSuccess";
                }
            }
        }
    }

    public boolean checkAllinPayOrderCallbackData(AllinPayOrderCallBackPO callBackPO) throws Exception {

        // 若 signType 为 0，用 MD5Utils 算法计算该签名字符串的消息摘要，将生成的摘要与通联返回的 signMsg 进行比对，如果相等则验签通过，反之不通过
        if(callBackPO.getSignType() == 0) {

            String md5 = StringUtils.md5WithHex(AllinPayUtils.getSignSrc(callBackPO) + "&key=" + Config.getSystemConfig("bank.pay.allinpay.md5key"));
            return callBackPO.getSignMsg().equals(md5);

        }

        // 若 signType 为 1，则使用通联的公钥证书进行验签，调用通联提供的 allinpay-security-xxx.jar 中的 verify() 方法
        else if(callBackPO.getSignType() == 1) {

            return AllinPayUtils.getSignWithCert(callBackPO);

        }

        return false;
    }


    public static void main(String [] args) {
        try {

//            AllinPayAction action = new AllinPayAction();
//
//            String orderNo = "NO20151210183422";
//
//            action.sellProductionManual(orderNo);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String sellProductionManual() throws Exception {

        String orderNo = HttpUtils.getParameter(getRequest(), "OrderNo");

        if (StringUtils.isEmpty(orderNo)) {
            MyException.newInstance("接口参数失败").throwException();
        }

        String paidTime = HttpUtils.getParameter(getRequest(), "PaidTime");
        if (StringUtils.isEmpty(paidTime)) {
            paidTime = TimeUtils.getNow();
        }

        String wistoken = HttpUtils.getParameter(getRequest(), "token");

        if (StringUtils.isEmpty(wistoken) || !wistoken.equals(Config.getSystemConfig("wsi.token"))) {
            MyException.newInstance("接口认证失败").throwException();
        }

        Connection conn = getConnection();

        productionService.sellProductionManualDo(orderNo, paidTime, conn);

        return SUCCESS;
    }

    public String unsellProductionManual() throws Exception {

        String orderNo = HttpUtils.getParameter(getRequest(), "OrderNo");

        if (StringUtils.isEmpty(orderNo)) {
            MyException.newInstance("接口参数失败").throwException();
        }


        String wistoken = HttpUtils.getParameter(getRequest(), "token");

        if (StringUtils.isEmpty(wistoken) || !wistoken.equals(Config.getSystemVariable("wsi.token"))) {
            MyException.newInstance("接口认证失败").throwException();
        }

        Connection conn = getConnection();

        productionService.unsellProductionManualDo(orderNo, 0,0, conn);

        return SUCCESS;
    }


    public void sellProduction(AllinPayOrderPO allinPayOrder, AllinPayOrderCallBackPO allinPayOrderCallBack, Connection conn) throws Exception {

        // 判断是否与系统订单保持一致
        OrderPO order = orderService.getOrderByOrderNo(allinPayOrderCallBack.getOrderNo(), conn);

        if (order == null) {
            logService.save("通联支付", "通联支付","没有找到通联订单【"+allinPayOrderCallBack.getOrderNo()+"】对应的系统订单");
            MyException.newInstance("无法找到订单，请检查").throwException();
        }

        // 检查订单状态是否已经是付款状态
        if (order.getStatus() == OrderStatus.Saled) {
            MyException.newInstance("此订单已经付款，请检查").throwException();
        }

        // 设置购买产品订单的起息日
        String payDateTime = TimeUtils.format(allinPayOrderCallBack.getPayDatetime(), TimeUtils.Format_YYYYMMDDHHMMSS, TimeUtils.Format_YYYY_MM_DD_HH_M_S);
        String valueDate = TimeUtils.getTime(payDateTime, 1, TimeUtils.DATE);
        order.setValueDate(valueDate);

        // 设置订单为已打款
        order.setStatus(OrderStatus.Saled);

        // 订单的操作者，使用预定好的系统用户
        UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);

        orderService.saleOrderOnlineFormNow(order, user.getId(), conn);
    }



    /**
     * 处理通联支付返回的数据
     * 无论是页面url还是后台两次的调用
     * @param allinPayOrderCallBack
     * @param conn
     * @throws Exception
     */
    private void dealAllipayCallbackData(AllinPayOrderCallBackPO allinPayOrderCallBack, CustomerPersonalPO customerPersonal, Connection conn) throws Exception {

        // 此事务用于在异常情况下保存通联支付传入的数据
        Connection conn4SaveAllinPayData = null;
        AllinPayOrderPO allinPayOrder = null;
        HttpServletRequest request = this.getRequest();

        try {

            // 验证传入参数
            if (allinPayOrderCallBack == null) {
                MyException.newInstance(Config.getWords("w.gloabl.error.login"), "无法获得通联支付返回数据").throwException();
            }

            // 验证返回值的有效性 通联支付接口返回的指定参数进行非空验证
            if (!checkAllinPayOrderCallbackData(allinPayOrderCallBack)) {
                // 支付参数失效
                MyException.newInstance(Config.getWords("w.gloabl.error.unknown"), "支付参数失效").throwException();
            }

            // 系统生成购买产品订单
            allinPayOrder = this.getAlliPayOrder(allinPayOrderCallBack, conn);
            if (allinPayOrder == null) {
                MyException.newInstance(Config.getWords("w.gloabl.error.unknown"), "系统生成购买产品订单错误").throwException();
            }

            // 转换成系统（元）以单位
            allinPayOrder.setOrderAmount(allinPayOrder.getOrderAmount() / 100);

            // 如果是通联支付第二次反馈，只保存反馈数据，不做业务处理
            if (allinPayOrder.getTradingStatus() == AllinPayOrderTradingStatus.Accepted) {
                allinPayOrderCallBackService.saveAllinPayOrderCallBack(allinPayOrderCallBack, conn);
                return;
            }

            // 校验订单
            String date = allinPayOrder.getOrderDatetime();
            date = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10) + date.substring(11, 13) + date.substring(14, 16) + date.substring(17);
            if (!allinPayOrder.getMerchantId().equals(allinPayOrderCallBack.getMerchantId()) ||
                    !date.equals(allinPayOrderCallBack.getOrderDatetime()) ||
                    !allinPayOrder.getOrderNo().equals(allinPayOrderCallBack.getOrderNo())) {
                MyException.newInstance(Config.getWords("w.gloabl.error.unknown"), "购买产品订单校验失败").throwException();
            }

            // 数据校验完毕，执行购买产品操作
            // 保存通联支付返回数据
            allinPayOrderCallBackService.saveAllinPayOrderCallBack(allinPayOrderCallBack, conn);

            // todo 执行产品购买，出现异常上面的数据保存操作事务没有关闭就跳入异常处理
            sellProduction(allinPayOrder, allinPayOrderCallBack, conn);

            // 设置通联订单状态
            allinPayOrder.setTradingStatus(AllinPayOrderTradingStatus.Accepted);
            MySQLDao.insertOrUpdate(allinPayOrder, conn);

        }
        catch (Exception e) {

            //进入异常，回滚try块处理的业务逻辑
            Database.rollback(conn);

            e.printStackTrace();

            LogService.info("购买产品失败，开始保存通联支付返回数据，异常为【"+e+"】", this.getClass());
            LogService.info("通联返回数据：" + allinPayOrderCallBack.toJsonObject(), this.getClass());

            // 异常情况下，保存通联支付返回的数据，避免回滚
            conn4SaveAllinPayData = Config.getConnection();
            conn4SaveAllinPayData.setAutoCommit(false);
            allinPayOrderCallBackService.saveAllinPayOrderCallBack(allinPayOrderCallBack, conn4SaveAllinPayData);

            // 保存支付订单状态，设置为接受但存有异常
            if (allinPayOrder.getTradingStatus() != AllinPayOrderTradingStatus.Accepted) {

                LogService.info("设置通联支付订单主表状态为【接受但存有异常】", this.getClass());
                LogService.info("通联支付主表订单修改前数据", this.getClass());
                LogService.info(allinPayOrder);

                allinPayOrder.setState(Config.STATE_UPDATE);
                MySQLDao.update(allinPayOrder, conn4SaveAllinPayData);
                allinPayOrder.setSid(MySQLDao.getMaxSid("bank_AllinPayTransfer", conn4SaveAllinPayData));
                allinPayOrder.setState(Config.STATE_CURRENT);
                allinPayOrder.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                allinPayOrder.setOperateTime(TimeUtils.getNow());
                allinPayOrder.setTradingStatus(AllinPayOrderTradingStatus.AcceptedWithDataException);

                LogService.info("通联支付主表订单修改后数据", this.getClass());
                LogService.info(allinPayOrder);
                MySQLDao.insert(allinPayOrder, conn4SaveAllinPayData);
            }


            // 出现异常对客户退款表新增数据，并返回退款对象
            LogService.info("出现异常对客户退款表新增数据", this.getClass());
            String reason = "所购买产品额度不足或已售罄，产品名称【"+allinPayOrder.getProductName()+"】";
            CustomerRefundPO customerRefund = customerRefundService.dealRefund(customerPersonal.getId(),
                    allinPayOrder.getOrderAmount(), allinPayOrderCallBack.getOrderNo(), allinPayOrder.getId(),
                    reason,
                    Config.getIP(request), conn4SaveAllinPayData);
            LogService.info(customerRefund);

            // 新增资金日志
            customerMoneyLogService.log4Refund(customerPersonal.getId(), customerRefund.getId(),
                    allinPayOrderCallBack.getPayAmount() / 100, CustomerMoneyLogStatus.Accepted,
                    conn4SaveAllinPayData);

            conn4SaveAllinPayData.commit();
            throw e;
        }
        finally {
            Database.close(conn4SaveAllinPayData);
        }
    }

    private AllinPayOrderPO getAlliPayOrder(AllinPayOrderCallBackPO allinPayOrderCallBack, Connection conn) throws Exception {
        AllinPayOrderPO allinPayOrder = new AllinPayOrderPO();
        allinPayOrder.setState(Config.STATE_CURRENT);
        allinPayOrder.setMerchantId(allinPayOrderCallBack.getMerchantId());
        allinPayOrder.setOrderDatetime(allinPayOrderCallBack.getOrderDatetime());
        allinPayOrder.setOrderNo(allinPayOrderCallBack.getOrderNo());
        allinPayOrder = MySQLDao.load(allinPayOrder, AllinPayOrderPO.class, conn);
        return allinPayOrder;
    }

    public String productReceive() throws Exception {
        return "productSuccess";
    }

    /**
     * 创建人：张舜清
     * 时间：8/12/2015
     * 内容：购买产品通联支付返回处理
     *
     * @return
     * @throws Exception
     */

    public String productPickUp() throws Exception {

        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = getConnection();

        // 得到通联支付返回来的指定参数
        allinPayOrderCallBack = HttpUtils.getInstanceFromRequest(request, "", allinPayOrderCallBack.getClass());
        LogService.info("通联支付网关返回值", this.getClass());
        LogService.info(allinPayOrderCallBack);

        logService.save("通联支付", "通联支付反馈", allinPayOrderCallBack.toJsonObject().toString());

        CustomerPersonalPO loginUser = null;
        // 从session构造loginUser
        if (session.getAttribute(Config.SESSION_LOGINUSER_STRING) == null) {
            // 没有session用户，说明是通联后台返回的数据
            AllinPayOrderService allinPayOrderService = new AllinPayOrderService();
            loginUser = allinPayOrderService.getCustomerPersonalFromOrderNo(allinPayOrderCallBack.getOrderNo(), conn);
        }
        else {
            loginUser = (CustomerPersonalPO) session.getAttribute(Config.SESSION_LOGINUSER_STRING);
        }

        if (loginUser == null) {
            MyException.newInstance(Config.getWords("w.gloabl.error.login"), "登录失效或者无法通过通联订单获得客户数据").throwException();
        }

        try {
            dealAllipayCallbackData(allinPayOrderCallBack, loginUser, conn);
        }
        catch (Exception e) {
            logService.save("通联支付", "处理订单返回值失败，通联订单编号【"+allinPayOrderCallBack.getOrderNo()+"】", MyException.getExceptionMessage(e));
            throw e;
        }

        getResult().setMessage("购买产品请求已接受，正在等待银行确认，稍后检查订单状态。");

        return "productSuccess";
    }



    public AllinPayOrderPO getAllinPayOrder() {
        return allinPayOrder;
    }

    public void setAllinPayOrder(AllinPayOrderPO allinPayOrder) {
        this.allinPayOrder = allinPayOrder;
    }

    public AllinPayOrderCallBackPO getAllinPayOrderCallBack() {
        return allinPayOrderCallBack;
    }

    public void setAllinPayOrderCallBack(AllinPayOrderCallBackPO allinPayOrderCallBack) {
        this.allinPayOrderCallBack = allinPayOrderCallBack;
    }

    public AllinPayOrderCallBackService getAllinPayOrderCallBackService() {
        return allinPayOrderCallBackService;
    }

    public void setAllinPayOrderCallBackService(AllinPayOrderCallBackService allinPayOrderCallBackService) {
        this.allinPayOrderCallBackService = allinPayOrderCallBackService;
    }

    public CustomerDepositPO getCustomerDeposit() {
        return customerDeposit;
    }

    public void setCustomerDeposit(CustomerDepositPO customerDeposit) {
        this.customerDeposit = customerDeposit;
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

    public void setCustomerMoneyService(CustomerMoneyService customerMoneyService) {
        this.customerMoneyService = customerMoneyService;
    }

    public CustomerMoneyLogPO getCustomerMoneyLog() {
        return customerMoneyLog;
    }

    public void setCustomerMoneyLog(CustomerMoneyLogPO customerMoneyLog) {
        this.customerMoneyLog = customerMoneyLog;
    }

    public CustomerMoneyLogService getCustomerMoneyLogService() {
        return customerMoneyLogService;
    }

    public void setCustomerMoneyLogService(CustomerMoneyLogService customerMoneyLogService) {
        this.customerMoneyLogService = customerMoneyLogService;
    }

    public OrderPO getOrder() {
        return order;
    }

    public void setOrder(OrderPO order) {
        this.order = order;
    }


    public ProductionPO getProduction() {
        return production;
    }

    public void setProduction(ProductionPO production) {
        this.production = production;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public CustomerRefundService getCustomerRefundService() {
        return customerRefundService;
    }

    public void setCustomerRefundService(CustomerRefundService customerRefundService) {
        this.customerRefundService = customerRefundService;
    }

    public CustomerWithdrawService getCustomerWithdrawService() {
        return customerWithdrawService;
    }

    public void setCustomerWithdrawService(CustomerWithdrawService customerWithdrawService) {
        this.customerWithdrawService = customerWithdrawService;
    }
}
