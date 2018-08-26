package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.annotation.Security;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Bank;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.reflaction.MyClass;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderPO;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.fuiou.FuiouPCPayPO;
import com.youngbook.entity.po.info.LegalAgreementPO;
import com.youngbook.entity.po.production.*;
import com.youngbook.entity.po.sale.contract.ContractPO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.service.allinpay.AllinPayOrderService;
import com.youngbook.service.core.OrderPayService;
import com.youngbook.service.customer.CustomerCertificateService;
import com.youngbook.service.customer.CustomerInfoService;
import com.youngbook.service.customer.CustomerMoneyService;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.pay.FuiouPaymentService;
import com.youngbook.service.pay.FuiouService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.production.ProductionCompositionService;
import com.youngbook.service.production.ProductionService;
import com.youngbook.service.sale.contract.ContractService;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.SmsService;
import com.youngbook.service.system.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class OrderAction extends BaseAction {

    @Autowired
    ProductionService productionService;

    @Autowired
    ProductionCompositionService productionCompositionService;

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    CustomerMoneyService customerMoneyService;

    @Autowired
    CustomerCertificateService customerCertificateService;

    @Autowired
    OrderService orderService;

    @Autowired
    AllinPayOrderService allinPayOrderService;

    @Autowired
    FuiouPaymentService fuiouPaymentService;

    @Autowired
    CustomerInfoService customerInfoService;

    @Autowired
    OrderPayService orderPayService;

    @Autowired
    FuiouService fuiouService;

    @Autowired
    LogService logService;


    @Autowired
    SmsService smsService;

    private OrderVO orderVO = new OrderVO();
    private CustomerPersonalVO customerPersonalVO = new CustomerPersonalVO();
    private OrderPO order = new OrderPO();
    private CustomerMoneyPO customerMoney = new CustomerMoneyPO();

    private LegalAgreementPO legalAgreement = new LegalAgreementPO();
    private ReturnObject result;

    // 创建通联对象
    private AllinPayOrderPO allinPayOrder = new AllinPayOrderPO();



    @Autowired
    UserService userService;

    @Autowired
    ContractService contractService;


    public String loadOrderVO() throws Exception {

        try {
            String mobile = getHttpRequestParameter("mobile");
            String payCode = getHttpRequestParameter("payCode");

            OrderVO orderVO = orderService.loadOrderVO(mobile, payCode, getConnection());

            if (orderVO == null) {
                MyException.newInstance("无法获得订单信息", "mobile="+mobile+"&payCode=" + payCode).throwException();
            }

            String customerCertificateNumber = orderVO.getCustomerCertificateNumber();
            orderVO.setCustomerCertificateNumber(AesEncrypt.decrypt(customerCertificateNumber));

            getResult().setReturnValue(orderVO);

            getRequest().setAttribute("orderVO", orderVO);

            return SUCCESS;
        }
        catch (MyException me) {

            getRequest().setAttribute("info", me.getPeopleMessage() + "<br>请与客服联系【"+Config.getSystemConfig("ph.service.mobile")+"】");

            return "info";
        }
        catch (Exception e) {

            getRequest().setAttribute("info", e.getMessage());

            return "info";
        }

    }

    public String phGetPageOrderSave() throws Exception {


        return "page_success";
    }


    public String phGetPagePaperInvestment() throws Exception {

        String orderId = getHttpRequestParameter("orderId");

        OrderVO orderVO = orderService.loadOrderVOByOrderId(orderId, getConnection());

        if (orderVO == null) {
            return "no_order_info";
        }

        String customerCertificateNumber = orderVO.getCustomerCertificateNumber();
        orderVO.setCustomerCertificateNumber(AesEncrypt.decrypt(customerCertificateNumber));

        if (orderVO.getProductionName().contains("政信通")) {

            getRequest().setAttribute("orderVO", orderVO);

            return "order_zxt";
        }

        if (orderVO.getProductionName().contains("中能3号")) {
            return "order_zn";
        }

        return SUCCESS;
    }

    public String loadOrderVOByOrderId() throws Exception {

        String orderId = getHttpRequestParameter("orderId");

        OrderVO orderVO = orderService.loadOrderVOByOrderId(orderId, getConnection());

        if (orderVO == null) {
            MyException.newInstance("无法获得订单信息", "orderId="+orderId).throwException();
        }

        String customerCertificateNumber = orderVO.getCustomerCertificateNumber();
        orderVO.setCustomerCertificateNumber(AesEncrypt.decrypt(customerCertificateNumber));

        getResult().setReturnValue(orderVO);

        getRequest().setAttribute("orderVO", orderVO);

        return SUCCESS;
    }


    public String generatePaymentPlan() throws Exception {

        String orderId = getHttpRequestParameter("orderId");
        Connection conn = getConnection();


        OrderPO order = orderService.loadByOrderId(orderId, conn);

        orderService.generatePaymentPlan(order, getLoginUser().getId(), conn);

        getResult().setReturnValue("{'message':'生成兑付计划成功'}");

        return SUCCESS;
    }


    @Security(needWebLogin = true)
    public String getInvestmentAccountInfo() {

        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("银行账户是XXXXX");
        sbHtml.append("<br>");
        sbHtml.append("若已转账，则与XX联系");


        getResult().setCode(ReturnObject.CODE_SUCCESS);
        getResult().setMessage(sbHtml.toString());

        return SUCCESS;
    }

    @Permission(require = "销售管理_订单管理_合同签约")
    public String signContract() throws Exception {

        String contractNo = getHttpRequestParameter("contracNo");
        String orderId = getHttpRequestParameter("orderId");
        UserPO operatorUser = getLoginUser();
        Connection conn = getConnection();

        int count = contractService.signContract(contractNo, orderId, operatorUser, conn);

        getResult().setCode(ReturnObject.CODE_SUCCESS);
        getResult().setMessage("操作成功");
        getResult().setReturnValue(count);

        return SUCCESS;
    }


    /**
     * 取消合同签约
     * @return
     * @throws Exception
     */
    @com.youngbook.annotation.Permission(require = "销售管理_订单管理_取消合同签约")
    public String cancelSignContract() throws Exception {

        String orderId = getHttpRequestParameter("orderId");
        UserPO operatorUser = getLoginUser();
        Connection conn = getConnection();

        int count = contractService.cancelSignContract(orderId, operatorUser, conn);

        getResult().setCode(ReturnObject.CODE_SUCCESS);
        getResult().setMessage("操作成功");
        getResult().setReturnValue(count);

        return SUCCESS;
    }

    @com.youngbook.annotation.Permission(require = "销售管理_订单管理_转账到公达")
    public String moneyTransfer2Gongda() throws Exception {

        String orderId = HttpUtils.getParameter(getRequest(), "orderId");

        if (StringUtils.isEmpty(orderId)) {
            MyException.newInstance("无法获得订单编号", "method=moneyTransfer2Gongda").throwException();
        }

        ReturnObject returnObject = orderService.moneyTransfer2Gongda(orderId, getLoginUser().getId(), getConnection());


        getResult().setCode(returnObject.getCode());
        getResult().setMessage(returnObject.getMessage());

        JSONObject json = new JSONObject();
        json.put("message", returnObject.getMessage());

        getResult().setReturnValue(json);


        return SUCCESS;
    }

    /**
     * HOPEWEALTH-1305<br/>
     * 客户投资列表（订单列表），通过销售referee手机号码和status来获取符合条件的所有订单<br/>
     * test url<br/>
     * http://localhost:8080/core/api/production/Order_getAllOrder?referee=13759400622&&status=1
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String getAllOrder() throws Exception {
        Connection conn = this.getConnection();
        HttpServletRequest request = this.getRequest();

        String customerId = HttpUtils.getParameter(request, "customerId");
        String salemanId = HttpUtils.getParameter(request, "salemanId");
        String status = HttpUtils.getParameter(request, "status");
        String currentPage = HttpUtils.getParameter(request, "currentPage");
        String showRowCount = HttpUtils.getParameter(request, "showRowCount");

        // 校验参数
        if (StringUtils.isEmpty(salemanId) || StringUtils.isEmpty(status) || StringUtils.isEmpty(customerId) || StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(showRowCount)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        Integer statusInt = Integer.parseInt(status);
        if (OrderStatus.getStatusName(statusInt).equals("未知状态")) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        Integer currentPageInt = Integer.parseInt(currentPage);
        Integer showRowCountInt = Integer.parseInt(showRowCount);

        // 获取结果
        Pager pager = orderService.getAllOrder(customerId, salemanId, status, currentPageInt, showRowCountInt, conn);
        getResult().setReturnValue(pager);

        return SUCCESS;
    }

    /**
     * HOPEWEALTH-1312<br/>
     * 根据订单ID获取对外销售的客户投资详情，用于销客APP<br/>
     * 参数为orderId<br/>
     * test url<br/>
     * http://localhost:8080/core/api/production/Order_getCustomerOuterOrderAPI?orderId=22A1A5E93AAB44AC9363CCAC16DEE848
     */
    @Security(needToken = true)
    public String getPreFinishOrders() throws Exception {

        // 获取连接
        Connection conn = this.getConnection();
        // 获取请求对象
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        String referralCode = HttpUtils.getParameter(request, "referralCode");
        String currentPage = HttpUtils.getParameter(request, "currentPage");
        String showRowCount = HttpUtils.getParameter(request, "showRowCount");

        // 校验参数
        if (StringUtils.isEmpty(referralCode) || StringUtils.isEmpty(customerId) || StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(showRowCount)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        Integer currentPageInt = Integer.parseInt(currentPage);
        Integer showRowCountInt = Integer.parseInt(showRowCount);

        Pager pager = orderService.getPreFinishOrders(customerId, referralCode, currentPageInt, showRowCountInt, conn);

        getResult().setReturnValue(pager);

        return SUCCESS;
    }

    /**
     * 获取对外销售的客户详情接口
     *
     * 作者：曾威恺
     * 内容：创建代码
     * 时间：2016年3月28日
     *
     * @return
     * @throws Exception
     */
    public String getCustomerOuterOrderAPI() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String orderId = HttpUtils.getParameter(request, "orderId");
        // 验证参数
        if(StringUtils.isEmpty(orderId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少订单编号").throwException();
        }
        // 获取结果
        List<OrderVO> list = orderService.getCustomerOuterOrder(orderId, conn);
        // 对结果集进行判断
        if (list == null || list.size() != 1) {
            MyException.newInstance(ReturnObjectCode.ORDER_GET_DETIAL_ERROR, "获取订单详情结果错误").throwException();
        }
        // OrderVO
        OrderVO orderVO = list.get(0);
        // 银行账户处理
        orderVO.setBankNumber(AesEncrypt.decrypt(orderVO.getBankNumber()));

        getResult().setReturnValue(orderVO);
        return SUCCESS;

    }


    public String getContractNoMenu() throws Exception {
        HttpServletRequest request = getRequest();
        String productId = request.getParameter("productId");
        Connection conn = getConnection();
        //更具产品编号与销售员获取销售合同
        List<ContractPO> contractPOs = contractService.getContractPOs(productId, getLoginUser().getId(), conn);
        JSONArray array = contractService.getContractPOToMenuArray(contractPOs);
        result.setReturnValue(array.toString());
        return SUCCESS;
    }


    public String loadContractNoMenu() throws Exception {
        HttpServletRequest request = getRequest();
        String productId = request.getParameter("productId");
        String orderId = request.getParameter("orderId");
        String sql = "SELECT contractNo AS id,contractNo AS text FROM crm_contractroute WHERE 1=1 AND state = 0 AND YorN ='Y' AND ProductionId = '" + productId + "' AND (OrderId = '" + orderId + "' or OrderId = '') GROUP BY contractNo";
        JSONArray array = orderService.getMenuArray(sql);
        if (array.size() == 0) {
            sql = "SELECT contractNo AS id,contractNo AS text FROM crm_contractroute WHERE 1=1 AND state = 0 AND YorN ='Y' AND ProductionId = '" + productId + "' AND OrderId = '' GROUP BY contractNo";
            array = orderService.getMenuArray(sql);
        }
        result.setReturnValue(array.toString());
        return SUCCESS;
    }

    /**
     * 获得订单状态JSON值
     * 传入参数决定显示不同的类型
     * name=FinanceConfirm 财务确认
     * name=Sale 正式下单
     * @return
     * @throws Exception
     */
    public String getJsonOfOrderStatus() throws Exception {

        result = new ReturnObject();
        result.setCode(ReturnObject.CODE_SUCCESS);


        String name = HttpUtils.getParameter(getRequest(), "name");

        if (name.equals("FinanceConfirm")) {
            result.setReturnValue(OrderStatus.getJsonFinanceConfirm());
        }
        else if (name.equals("FinanceConfirm01")) {
            result.setReturnValue(OrderStatus.getJsonFinanceConfirm01());
        }
        else if (name.equals("SaleAndWaiting")) {
            result.setReturnValue(OrderStatus.getJsonSaleAndWaiting());
        }
        else if (name.equals("Appointment")) {
            result.setReturnValue(OrderStatus.getJsonAppointment());
        }
        else if (name.equals("Transfer")) {
            result.setReturnValue(OrderStatus.getJsonTransfer());
        }
        else if (name.equals("Payback")) {
            result.setReturnValue(OrderStatus.getJsonPayback());
        }
        else if (name.equals("Appointment")) {
            result.setReturnValue(OrderStatus.getJsonAppointment());
        }
        else if (name.equals("Feedback1")) {
            result.setReturnValue(OrderStatus.getJsonFeedback1());
        }

        else if (name.equals("Feedback2")) {
            result.setReturnValue(OrderStatus.getJsonFeedback2());
        }

        return SUCCESS;
    }


    public String feedback1() throws Exception {

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        orderService.feedback(order, OrderStatus.Feedback1, getLoginUser().getId(), getConnection());

        return SUCCESS;
    }

    public String feedback2() throws Exception {

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        orderService.feedback(order, OrderStatus.Feedback2, getLoginUser().getId(), getConnection());

        return SUCCESS;
    }

    /**
     * 内容：增加了权限判断，能根据登录的用户是否负责人来查看对应组下的所有订单
     * 修改人：张舜清
     * 时间：2015年8月18日10:24:16
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        Connection conn = getConnection();
        HttpServletRequest request = getRequest();
        Pager pager = Pager.getInstance(request);

        orderVO = HttpUtils.getInstanceFromRequest(getRequest(), "orderVO", OrderVO.class);

        if (!StringUtils.isEmpty(getHttpRequestParameter("CustomerName"))) {
            orderVO.setCustomerName(getHttpRequestParameter("CustomerName"));
        }

        if (!StringUtils.isEmpty(getHttpRequestParameter("OrderNum"))) {
            orderVO.setOrderNum(getHttpRequestParameter("OrderNum"));
        }

        if (!StringUtils.isEmpty(getHttpRequestParameter("ProductName"))) {
            orderVO.setProductionName(getHttpRequestParameter("ProductName"));
        }

        if(!StringUtils.isEmpty(getHttpRequestParameter("ReferralCode"))) {
            orderVO.setReferralCode(getHttpRequestParameter("ReferralCode"));
        }

        // status
        if (!StringUtils.isEmpty(getHttpRequestParameter("status"))) {
            orderVO.setStatus(NumberUtils.parse2Int(getHttpRequestParameter("status")));
        }

        String payTimeStart = getHttpRequestParameter("PayTime_Start");
        String payTimeEnd = getHttpRequestParameter("PayTime_End");


        // 此订单的销售人员
        String salesmanName = getHttpRequestParameter("salesmanName");


        /**
         * 分不同权限对客户列表进行查询
         *
         * 权限大的判断放在前面
         */


        // 财务核对
        if (hasPermission("销售管理_订单管理_财务核对")) {

            String orderStatus = HttpUtils.getParameter(getRequest(), "status");

            pager = orderService.getPagerOrderVO(orderVO, payTimeStart, payTimeEnd, "", null, salesmanName, orderStatus, pager.getCurrentPage(), pager.getShowRowCount(),  conn);

            /**
             *
             * 处理敏感数据
             *
             */
            for (int i = 0; pager != null && i < pager.getData().size(); i++) {
                OrderVO orderVO = (OrderVO) pager.getData().get(i);

                // 处理电话号码
                String mobile = orderVO.getMobile();
                orderVO.setMobile(StringUtils.maskStringKeepLast(mobile, 4));
            }
        }
        // 销售负责人查看
        else if (getPermission().has("销售管理_订单管理_销售负责人查看")) {
            pager = orderService.showListSaleGroup(orderVO, payTimeStart, payTimeEnd, getLoginUser(), request, conn);
        }
        else if (hasPermission("订单管理_第一次回访")) {

            String orderStatus = HttpUtils.getParameter(getRequest(), "status");

            pager = orderService.getPagerOrderVO(orderVO, payTimeStart, payTimeEnd, null, null, salesmanName, orderStatus, pager.getCurrentPage(), pager.getShowRowCount(), conn);


            /**
             *
             * 处理敏感数据
             *
             */
            for (int i = 0; pager != null && i < pager.getData().size(); i++) {
                OrderVO orderVO = (OrderVO) pager.getData().get(i);

                // 处理电话号码
                String mobile = orderVO.getMobile();
                orderVO.setMobile(StringUtils.maskStringKeepLast(mobile, 4));
            }
        }
        // 普通销售人员查看
        else {

            String salesmanId = getLoginUser().getId();

            String orderStatus = HttpUtils.getParameter(getRequest(), "status");

            pager = orderService.getPagerOrderVO(orderVO, payTimeStart, payTimeEnd, null, getLoginUser().getId(), null, orderStatus, pager.getCurrentPage(), pager.getShowRowCount(), conn);
        }
        // 对数据后期处理
        List<IJsonable> datas = pager.getData();
        for (IJsonable data : datas) {
            OrderVO order = (OrderVO) data;

            // 设置订单状态名称
            int status = order.getStatus();
            String statusName = OrderStatus.getStatusName(status);
            order.setStatusName(statusName);


            // 客户类型：个人客户、机构客户
            if (order.getCustomerAttribute().equals("0")) {
                order.setCustomerAttribute("个人客户");
            } else if (order.getCustomerAttribute().equals("1")) {
                order.setCustomerAttribute("机构客户");
            }


            /**
             * 设置支付渠道信息
             */

            order.setPayChannelName(OrderPayChannel.getName(order.getPayChannel()));


            /**
             * 处理银行账号信息
             */
            String bankNumber = order.getBankNumber();
            if (!StringUtils.isEmpty(bankNumber)) {
                bankNumber = AesEncrypt.decrypt(bankNumber);
                order.setBankNumber(bankNumber);
            }

            /**
             * 设置订单状态
             */
            if (order.getStatus() != Integer.MAX_VALUE) {
                order.setStatusName(OrderStatus.getStatusName(order.getStatus()));
            }
        }
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    // 获取订单编号
    public String getOrderNum() throws Exception {
        String orderNum = this.orderService.getOrderNum4Web(getConnection());
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        json.element("orderNum", orderNum);
        array.add(json);
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    // 获取当前登录者为推荐人
    public String getReferralCode() throws Exception {
        getResult().setReturnValue(getLoginUser().toJsonObject());
        return SUCCESS;
    }

    @Permission(require = "销售管理_订单管理_修改推荐码")
    public String saveReferralCode() throws Exception {

        String orderId = HttpUtils.getParameter(getRequest(), "orderId");
        String referralCode = HttpUtils.getParameter(getRequest(), "referralCode");

        orderService.saveReferralCode(orderId, referralCode, getLoginUser().getId(), getConnection());

        return SUCCESS;
    }



    // 读取 Order_load 原有代码
//    public String load() throws Exception {
//        order.setState(Config.STATE_CURRENT);
//        order = MySQLDao.load(order, OrderPO.class);
//        // 加载数据的时候判断合同是否作废了
//        String sql = "SELECT contractNo AS id,contractNo AS text FROM crm_contractroute WHERE 1=1 AND state = 0 AND YorN ='Y' AND ProductionId = '" + order.getProductionId() + "' AND OrderId = '" + order.getId() + "'  GROUP BY contractNo";
//        JSONArray array = orderService.getMenuArray(sql);
//        if (array.size() == 0) {
//            // 把合同编号字段清空
//            order.setContractNo("");
//        }
//        JSONObject jsonObject = order.toJsonObject4Form();
//        result.setReturnValue(jsonObject);
//        return SUCCESS;
//    }
//
//    // 获取产品下拉列表数据
//    public String getProductMenu() throws Exception {
//        String sql = "select id,name text from crm_production where state=0 and Status=2";
//        JSONArray array = orderService.getMenuArray(sql);
//        result.setReturnValue(array.toString());
//        return SUCCESS;
//    }

    // 获取产品下拉列表数据
    public String getProductMenu() throws Exception {
        Connection conn = getConnection();
        String sql = "select id,name text from crm_production where state=0 and Status=2";
        JSONArray array = orderService.getMenuArray(sql, conn);
        result.setReturnValue(array.toString());
        return SUCCESS;
    }


    public String saleAndWaitingOrder() throws Exception {

        try {
            String moneyString = HttpUtils.getParameter(getRequest(), "operationMoney");

            order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

            if (StringUtils.isEmptyAll(order.getAppointmentTime(), order.getPayTime())) {
                MyException.newInstance("预约时间或打款时间有误，请检查").throwException();
            }


            if (StringUtils.isEmpty(moneyString)) {
                MyException.newInstance("无法获得操作金额和操作时间").throwException();
            }
            order.setMoney(Double.parseDouble(moneyString));



            order = orderService.appointmentOrderFromNow(order.getId(), order.getCustomerId(), order.getProductionId(), order.getProductionCompositionId(), order.getMoney(), order.getPayTime(), order.getContractNo(), order.getReferralCode(), order.getStatus(), order.getAccountId(), order.getBankCode(), order.getDescription(), getConnection());

            if (order == null) {
                getResult().setMessage("产品支付待确认失败");
            }

            // 发送短信
            smsService.sendSms4OrderConfirm(order.getId(), getConnection());
        }
        catch (Exception e) {
            getResult().setCode(ReturnObject.CODE_EXCEPTION);
            getResult().setMessage(e.getMessage());
        }


        return SUCCESS;
    }

    public String saleAndWaitingOrderCancel() throws Exception {

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        int count = orderService.appointmentOrderCancel(order.getId(), getLoginUser().getId(), getConnection());

        if (count != 1) {
            getResult().setMessage("取消打款待确认失败");
        }

        return SUCCESS;
    }

    // 读取
    public String load() throws Exception {
        Connection conn = getConnection();

        String orderId = getHttpRequestParameter("order.id");
        orderVO = orderService.getOrderVOByOrderId(orderId, conn);


        /**
         * 补充客户名称
         * 2016年5月4日 15:27:13 leevits
         */
        String customerName = "";
        CustomerPersonalPO customerPersonal = new CustomerPersonalPO();
        customerPersonal.setId(orderVO.getCustomerId());
        customerPersonal.setState(Config.STATE_CURRENT);
        customerPersonal = MySQLDao.load(customerPersonal, CustomerPersonalPO.class, conn);

        if (customerPersonal != null) {
            customerName = customerPersonal.getName();
        }

        // 该客户可能是机构客户
        if (StringUtils.isEmpty(customerName)) {
            CustomerInstitutionPO customerInstitution = new CustomerInstitutionPO();
            customerInstitution.setId(orderVO.getCustomerId());
            customerInstitution.setState(Config.STATE_CURRENT);
            customerInstitution = MySQLDao.load(customerInstitution, CustomerInstitutionPO.class, conn);

            if (customerInstitution != null) {
                customerName = customerInstitution.getName();
            }

        }

        if (StringUtils.isEmpty(customerName)) {
            MyException.newInstance("获取订单信息失败，客户名称为空").throwException();
        }

        orderVO.setCustomerName(customerName);


        result.setReturnValue(orderVO.toJsonObject4Form("order"));
        return SUCCESS;
    }


    // 获取产品构成下拉列表数据
    public String getProductionCompositionMenu() throws Exception {
        Connection conn = getConnection();
        HttpServletRequest request = ServletActionContext.getRequest();
        String productId = request.getParameter("productId");
        String sql = "select id,name text from crm_productioncomposition where state=0 and ProductionId ='" + Database.encodeSQL(productId) + "'";
        JSONArray array = orderService.getMenuArray(sql, conn);
        result.setReturnValue(array);
        return SUCCESS;
    }


    // 订单信息查询
    public String loadOrder() throws Exception {

        orderVO = HttpUtils.getInstanceFromRequest(getRequest(), "orderVO", OrderVO.class);

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        String productionId = request.getParameter("productionId");
        Pager pager = orderService.loadOrder(orderVO, productionId, conditions, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String paybackOrder() throws Exception {

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        String moneyString = HttpUtils.getParameter(getRequest(), "operationMoney");
        String paybackTime = TimeUtils.getNow();

        if (StringUtils.isEmpty(moneyString)) {
            MyException.newInstance("无法获得操作金额和操作时间").throwException();
        }


        int count = orderService.paybackOrder(order.getId(), Double.parseDouble(moneyString), order.getStatus(), paybackTime, getLoginUser().getId(), getConnection());

        if (count != 1) {
            getResult().setMessage("产品销售确认失败");
        }

        return SUCCESS;
    }


    public String appointmentOrder() throws Exception {

        String moneyString = HttpUtils.getParameter(getRequest(), "operationMoney");

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        if (StringUtils.isEmptyAll(order.getAppointmentTime(), order.getPayTime())) {
            MyException.newInstance("预约时间或打款时间有误，请检查").throwException();
        }

        if (StringUtils.isEmpty(moneyString)) {
            MyException.newInstance("无法获得操作金额").throwException();
        }

        order.setMoney(Double.parseDouble(moneyString));

        order = orderService.appointmentOrderFromNow(order.getId(), order.getCustomerId(), order.getProductionId(), order.getProductionCompositionId(), order.getMoney(), order.getPayTime(), order.getContractNo(), order.getReferralCode(), order.getStatus(), order.getAccountId(), order.getBankCode(), order.getDescription(), getConnection());

        if (order == null) {
            getResult().setMessage("产品预约失败");
        }
        return SUCCESS;
    }




    public String appointmentOrderCancel() throws Exception {

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        int count = orderService.appointmentOrderCancel(order.getId(), getLoginUser().getId(), getConnection());

        if (count != 1) {
            getResult().setMessage("取消预约失败");
        }

        return SUCCESS;
    }


    /**
     * 手动创建并支付在线订单
     *
     *
     * @return
     * @throws Exception
     */
    public String buildAndPayOrderManually() throws Exception {

        String token = HttpUtils.getParameter(getRequest(), "token");
        if (StringUtils.isEmpty(token)) {
            MyException.newInstance("无法获得校验码").throwException();
        }

        String mask = Config.getSystemConfig("sysem.mask");

        if (StringUtils.isEmpty(mask)) {
            MyException.newInstance("无法获得系统校验码").throwException();
        }


        String customerId = HttpUtils.getParameter(getRequest(), "customerId");
        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }


        String productionId = HttpUtils.getParameter(getRequest(), "productionId");
        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }


        String strMoney = HttpUtils.getParameter(getRequest(), "money");
        if (StringUtils.isEmpty(strMoney)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        StringBuffer sbCode = new StringBuffer();
        sbCode.append(customerId).append(productionId).append(strMoney).append(mask);

        if (!StringUtils.md5(sbCode.toString()).equals(token)) {
            MyException.newInstance("订单数据校验失败").throwException();
        }


        double money = Double.parseDouble(strMoney);

        if (money < 50000) {
            MyException.newInstance("投资金额有误").throwException();
        }

        String createTime = HttpUtils.getParameter(getRequest(), "createTime");
        if (StringUtils.isEmpty(createTime)) {
            createTime = TimeUtils.getNow();
        }

        String payTime = HttpUtils.getParameter(getRequest(), "payTime");
        if (StringUtils.isEmpty(payTime)) {
            payTime = TimeUtils.getNow();
        }


        String referralCode = HttpUtils.getParameter(getRequest(), "referralCode");
        if (StringUtils.isEmpty(referralCode)) {
            referralCode = "";
        }

        // 保存请求日志
        String parameters = HttpUtils.getParametersStringValue(getRequest());
        logService.save("订单日志","手动生成并支付订单【"+token+"】", parameters, getConnection());


        /**
         *
         * 执行业务
         *
         */

        Connection conn = getConnection();


        String operatorId = "0000";

        OrderPO orderPO = orderService.appointmentOrder("", customerId, productionId, null, money, null, true, null, payTime, createTime, referralCode, OrderStatus.Appointment, null, null, null, conn);

        System.out.println("订单编号" + orderPO.getId());

        System.out.println("开始销售订单");

        orderPO.setCreateTime(createTime);
        String valueDate = TimeUtils.getTime(payTime, 1, TimeUtils.DATE);
        orderService.saleOrder(orderPO, createTime, payTime, valueDate, customerId, conn);
        System.out.println("结束销售订单");


        // 保存订单请求日志
        logService.save("订单日志", "手动生成并支付订单【"+token+"】", "订单编号【"+orderPO.getId()+"】", getConnection());
        return SUCCESS;
    }

    @com.youngbook.annotation.Permission(require = "销售管理_订单管理_财务二次核对")
    public String saleOrder() throws Exception {

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        Connection conn = getConnection();

        String moneyString = HttpUtils.getParameter(getRequest(), "operationMoney");

        if (StringUtils.isEmpty(moneyString) || StringUtils.isEmpty(order.getPayTime())) {
            MyException.newInstance("无法获得操作金额或打款时间").throwException();
        }

        order.setMoney(Double.parseDouble(moneyString));

        String now = TimeUtils.getNow();


        /**
         * 处理兑付计划
         *
         * 如果配有配置起息日，则说明付款后的第二天起息，否则按照配置的起息日起息
         */

        if (StringUtils.isEmpty(order.getValueDate())) {
            String valueDate = TimeUtils.getTime(order.getPayTime(), 1, TimeUtils.DATE);
            order.setValueDate(valueDate);
        }



        int count = orderService.saleOrder(order, now, order.getPayTime(), order.getValueDate(), getLoginUser().getId(), conn);

        if (count != 1) {
            getResult().setMessage("产品销售确认失败");
        }

        return SUCCESS;
    }

    @com.youngbook.annotation.Permission(require = "销售管理_订单管理_财务核对")
    public String financeConfirm01() throws Exception {

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        Connection conn = getConnection();

        String moneyString = HttpUtils.getParameter(getRequest(), "operationMoney");

        if (StringUtils.isEmpty(moneyString) || StringUtils.isEmpty(order.getPayTime())) {
            MyException.newInstance("无法获得操作金额或打款时间").throwException();
        }

        order.setMoney(Double.parseDouble(moneyString));

        String now = TimeUtils.getNow();


        OrderPO orderPO = orderService.financeConfirm01(order, getLoginUser().getId(), conn);



        if (orderPO != null) {
            getResult().setMessage("财务一次审核失败");
        }

        return SUCCESS;
    }


    public String transferOrder() throws Exception {

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        String moneyString = HttpUtils.getParameter(getRequest(), "operationMoney");

        if (StringUtils.isEmpty(moneyString) || StringUtils.isEmpty(order.getAppointmentTime())) {
            MyException.newInstance("无法获得操作金额和操作时间").throwException();
        }

        order.setMoney(Double.parseDouble(moneyString));


        int count = orderService.transferOrder(order.getId(), Double.parseDouble(moneyString), order.getStatus(), order.getAppointmentTime(), getLoginUser().getId(), getConnection());

        if (count != 1) {
            getResult().setMessage("产品销售确认失败");
        }

        return SUCCESS;
    }



    public String getOrders4Modern() throws Exception {

        String orderType = HttpUtils.getParameter(getRequest(), "orderType");
        String customerId = HttpUtils.getParameter(getRequest(), "customerId");


        int orderStatus = 0;

        if (!StringUtils.isEmpty(orderType) && orderType.equals("0")) {
            orderStatus = OrderStatus.Appointment;
        }
        else if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {
            orderStatus = OrderStatus.Saled;
        }

        String referralCode = null;
        if (getLoginUser() != null) {
            referralCode = getLoginUser().getReferralCode();
        }

        List<OrderPO> orders = orderService.getOrdersByCustomerId(customerId, orderStatus, referralCode, getConnection());
        getResult().setReturnValue(orders);

        return SUCCESS;
    }


    public String getListOrderVO() throws Exception {

        String orderStatus = HttpUtils.getParameter(getRequest(), "orderStatus");
        String customerId = HttpUtils.getParameter(getRequest(), "customerId");
        String projectTypeId = HttpUtils.getParameter(getRequest(), "projectTypeId");


        if (StringUtils.isEmpty(orderStatus)) {
            MyException.newInstance("请确定查询的订单状态", "orderStatus="+orderStatus).throwException();
        }

        OrderVO orderVO = new OrderVO();
        orderVO.setCustomerId(customerId);


        List<String> projectTypeIds = new ArrayList<String>();
        if (!StringUtils.isEmpty(projectTypeId)) {

            String [] types = projectTypeId.split(",");

            for (int i = 0; i < types.length; i++) {
                projectTypeIds.add(types[i]);
            }

        }

        List<OrderVO> orderVOs = orderService.getListOrderVO(orderVO, orderStatus, projectTypeIds, getConnection());

        for (int i = 0; orderVOs != null && i < orderVOs.size(); i++) {
            orderVO = orderVOs.get(i);
            String bankNumberMasked = StringUtils.maskStringKeepLast(AesEncrypt.decrypt(orderVO.getBankNumber()), 4);
            orderVO.setBankNumber(bankNumberMasked);

            orderVO.setPayCode(StringUtils.lastString(orderVO.getOrderNum(), 6));
        }

        getResult().setReturnValue(orderVOs);

        return SUCCESS;
    }

    public String feedback() throws Exception {

        String orderId = getHttpRequestParameter("orderId");
        String status = getHttpRequestParameter("status");
        String userId = getHttpRequestParameter("userId");

        if (StringUtils.isEmptyAny(orderId, status, userId)) {
            MyException.newInstance("无法获得必要参数","orderId="+orderId+"&status="+status+"&userId="+userId).throwException();
        }

        OrderPO orderPO = orderService.loadByOrderId(orderId, getConnection());

        if (orderPO == null) {
            MyException.newInstance("无法获得对应订单", "orderId="+orderId).throwException();
        }

        orderService.feedback(orderPO, Integer.parseInt(status), userId, getConnection());

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    public String listPagerOrderDetails() throws Exception {
        String orderId = HttpUtils.getParameter(getRequest(), "orderId");
        Connection conn = getConnection();
        Pager pager = Pager.getInstance(getRequest());
        pager = orderService.listPagerOrderDetails(orderId, pager.getCurrentPage(), pager.getShowRowCount(), conn);

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 获取订单列表接口
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String getOrders() throws Exception {
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        String currentPage = HttpUtils.getParameter(request, "currentPage");
        String showRowCount = HttpUtils.getParameter(request, "showRowCount");

        Integer currentPageInt = Integer.parseInt(currentPage);
        Integer showRowCountInt = Integer.parseInt(showRowCount);

        // 获取订单列表
        Pager pager = orderService.getOrders(customerId, currentPageInt, showRowCountInt, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;

    }

    /**
     * 交易平台请求的 Action，生成产品订单
     * 前提是网站的 Customer 已经登录
     * 这里生成两种类型的订单，一是系统订单，一是招商银行的订单，当订单支付完成，
     * 在线结账后，才把订单修改为已销售或已支付状态，然后才能再生成对付计划
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * 参考：招商银行提供的开发帮助文档
     * <p/>
     * <p/>
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     * <p/>
     * 修改人：张舜清
     * 时间：7/10/2015
     * 内容：修复if判断出现逻辑出错误，并且加了校验验证码和校验交易密码
     * <p/>
     * <p/>
     * 修改：李昕骏
     * 时间：2015年8月24日 10:25:38
     * 内容
     * 重新梳理流程
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     * <p/>
     * 修改人：姚章鹏
     * 时间：2015年8月24日17:03:52
     * 内容：//添加用户同意的投资管理协议
     */
    @Security(needWebLogin = true, needWebCode = true)
    public String buildOrder4W() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Connection conn = getConnection();

        // 获得登录用户
        CustomerPersonalPO loginUser = (CustomerPersonalPO) session.getAttribute(Config.SESSION_LOGINUSER_STRING);


        // 验证客户是否设置过交易密码
        // 获取客户基本信息
        CustomerPersonalPO customerPersonals = customerPersonalService.loadByCustomerPersonalId(loginUser.getId(), conn);


        // 客户没有设置交易密码
        if (StringUtils.isEmpty(customerPersonals.getTransactionPassword())) {
            // 返回一个标识，前台用来识别有没有交易密码，如果没有直接跳转设置交易密码
            MyException.newInstance("您没有设置交易密码，请先设置交易密码", "您没有设置交易密码，请先设置交易密码", "1").throwException();
        }


        // 验证客户交易密码
        String transactionPassWD = getRequest().getParameter("businessPwd");
        //再次加密
        transactionPassWD = StringUtils.md5(transactionPassWD + Config.MD5String);
        // 前台转成 md5 过来，判断输入的交易和查询出客户的交易密码是不是一样
        if (!customerPersonals.getTransactionPassword().equals(transactionPassWD)) {
            MyException.newInstance("您的交易密码输入有误，请重新输入！", "交易密码验证失败", "2").throwException();
        }


        // 验证客户是否绑定银行卡
        CustomerAccountPO customerAccount = customerInfoService.getCustomerAccount(loginUser.getId(), conn);
        if (customerAccount == null) {
            MyException.newInstance("您尚未绑定银行卡，请首先绑定银行卡！", "绑定银行卡失败", "3").throwException();
        }


        // 检验客户资金记录
        List<CustomerMoneyPO> customerMoneyList = customerMoneyService.getByCustomerId4W(loginUser.getId(), conn);
        if (customerMoneyList == null || customerMoneyList.size() != 1) {
            MyException.newInstance("系统没有为您生成资金记录", "系统没有为您生成资金记录").throwException();
        }


        // 订单的操作者，使用预定好的系统用户
        UserPO operator = userService.loadUserByUserId(Config.getSystemConfig("web.default.saleId"), conn);
        if (operator == null) {
            MyException.newInstance("订单内部操作员信息获取有误").throwException();
        }

        // 得到前台隐藏域的订单id
        String orderId = request.getParameter("orderId");
        OrderPO orderPO = new OrderPO();

        String now = TimeUtils.getNow();
        // 判断前台过来的订单id是不是空的
        if (StringUtils.isEmpty(orderId)) {
            // 数据检查完毕，开始生成订单
            String referralCode = "";
            if(!StringUtils.isEmpty(order.getReferralCode())) {
                referralCode = order.getReferralCode();
            }
            else {
                referralCode = Config.getReferralCode(operator.getStaffCode());
            }
            orderPO = orderService.appointmentOrder("", loginUser.getId(), order.getProductionId(), order.getProductionCompositionId(), order.getMoney(), order.getContractNo(), true, now, order.getPayTime(), now, referralCode, OrderStatus.Appointment, null, null, null, conn);
        }
        // 如果不为空load之前对应的订单数据
        else {
            orderPO.setId(orderId);
            orderPO.setState(Config.STATE_CURRENT);
            orderPO.setStatus(OrderStatus.Appointment);
            orderPO = MySQLDao.load(orderPO, OrderPO.class, conn);
        }

        if (orderPO == null) {
            MyException.newInstance(Config.getWords4WebGeneralError(), "支付时，订单创建错误").throwException();
        }

        // 格式化金额，单位为分
        Double money = orderPO.getMoney() * 100;

        // 获取产品名称
        ProductionPO production = new ProductionPO();
        production.setId(orderPO.getProductionId());
        production.setState(Config.STATE_CURRENT);
        production = MySQLDao.load(production, ProductionPO.class, conn);
        if (production == null) {
            MyException.newInstance("系统繁忙，请稍候再试！", "创建订单时获取产品分期数据失败").throwException();
        }

        // 判断产品募集期是否结束，投资金额是否满足要求
        boolean checkIsProduction4Invest = productionService.checkIsOk(production.getId(),  orderPO.getMoney());
        if (!checkIsProduction4Invest) {
            String message = Config.getLanguageVariable("w.pay.production.stopOrMoneyError");
            MyException.newInstance(message).throwException();
        }

        // 生成富友订单
        FuiouPCPayPO payPO = fuiouService.buildPCPayData(production.getName(), orderPO.getOrderNum(), money.intValue(), customerAccount.getBankCode());

        LogService.info("生成富友支付网关请求", this.getClass());
        LogService.info(payPO);

        getResult().setReturnValue(payPO.toJsonObject());

        return SUCCESS;
    }

    /**
     * 通过 HTML 输入信息，走通联网关支付
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年2月29日
     *
     * @return
     * @throws Exception
     */
    public String fastPay() throws Exception {

        HttpServletRequest request = getRequest();
        Connection conn = getConnection();

        // 操作者名称
        String operatorName = HttpUtils.getParameter(request, "name");
        // 产品名称
        String productionName = HttpUtils.getParameter(request, "production");
        // 金额
        String money = HttpUtils.getParameter(request, "money");

        // 非空检查
        if(StringUtils.isEmpty(operatorName) || StringUtils.isEmpty(productionName) || StringUtils.isEmpty(money)) {
            MyException.newInstance("参数不正确或为空", "参数不正确或为空").throwException();
        }


        // 金额数据类型转换，单位转换成分
        double doubleMoney =  Double.parseDouble(money);

        Integer moneyInt = MoneyUtils.getMoney2Fen(doubleMoney);

        // 封装购买客户
        CustomerPersonalPO personal = new CustomerPersonalPO();
        personal.setName(operatorName);

        // 生成订单 ID
        String orderId = StringUtils.md5(IdUtils.getUUID32());

        // 生成通联订单
        allinPayOrder = allinPayOrderService.build4W( personal, Config.getSystemConfig("bank.pay.productPickUp"), Config.getSystemConfig("bank.pay.productReceive"), orderId, productionName, moneyInt, Config4Bank.PAY_TYPE_4, conn);

        LogService.info("生成通联支付网关请求", this.getClass());
        LogService.info(allinPayOrder);

        getResult().setReturnValue(allinPayOrder.toJsonObject());


        return SUCCESS;
    }


    public String appointmentOrder4Modern() throws Exception {

        Connection conn = getConnection();

        String productionId = HttpUtils.getParameter(getRequest(), "productionId");
        String referralCode = HttpUtils.getParameter(getRequest(), "referralCode");
        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("获取产品编号失败").throwException();
        }

        if (StringUtils.isEmpty(referralCode)) {
            if (getLoginUser() != null) {
                referralCode = getLoginUser().getReferralCode();
            }
        }


        double moneyDouble = Double.parseDouble(HttpUtils.getParameter(getRequest(), "money"));

        String now = TimeUtils.getNow();


        // 获取预期收益率
        ProductionCompositionPO compositionPO = productionCompositionService.getProductionCompositionPOByProductionIdAndMoney(productionId, moneyDouble, conn);
        if(compositionPO == null) {
            MyException.newInstance(ReturnObjectCode.PRODUCTION_COMPOSITION_NOT_EXISTENT, "您好，请输入正确的预约金额。").throwException();
        }

        CustomerPersonalPO customerPersonalPO = HttpUtils.getInstanceFromRequest(getRequest(), CustomerPersonalPO.class);


        orderService.appointmentOrder("", customerPersonalPO.getId(), productionId, compositionPO.getId(), moneyDouble, null, true, now, null, now, referralCode, OrderStatus.Appointment, null, null, null, conn);

        return SUCCESS;
    }

    public String getOrderById() throws Exception {

        String orderId = getHttpRequestParameter("orderId");
        OrderPO order = new OrderPO();
        order = orderService.loadByOrderId(orderId,getConnection());

        getResult().setReturnValue(order);

        return SUCCESS;
    }

    /**
     * 获得订单的攻击信息
     * @return
     * @throws Exception
     */
    public String getOrderInfo() throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        int index = 1;

        String referralCode = HttpUtils.getParameter(getRequest(), "referralCode");

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from crm_order where state=0");
        if (!StringUtils.isEmpty(referralCode)) {
            sbSQL.append(" and referralCode=?");
            dbSQL.addParameter(index, referralCode);
            index++;
        }

        String customerId = HttpUtils.getParameter(getRequest(), "customerId");
        if (!StringUtils.isEmpty(customerId)) {
            sbSQL.append(" and customerId=?");
            dbSQL.addParameter(index, customerId);
            index++;
        }

        dbSQL.setSQL(sbSQL.toString());

        List<OrderPO> orders = MySQLDao.search(dbSQL, OrderPO.class, getConnection());

        int appointmentOrderCount = 0;
        int saledOrderCount = 0;

        for (int i = 0; orders != null && i < orders.size(); i++) {
            OrderPO order = orders.get(i);

            if (order.getStatus() == OrderStatus.Appointment) {
                appointmentOrderCount++;
            }

            if (order.getStatus() == OrderStatus.Saled) {
                saledOrderCount++;
            }
        }

        JSONObject json = new JSONObject();
        json.put("appointmentOrderCount", appointmentOrderCount);
        json.put("saledOrderCount", saledOrderCount);

        getResult().setReturnValue(json);

        return SUCCESS;
    }


    /**
     * 预约产品接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年5月10日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String appointment() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取请求
        HttpServletRequest request = this.getRequest();

        // 实例化 Service

        // 获取参数
        String productionId = HttpUtils.getParameter(request, "productionId");
        String customerId = HttpUtils.getParameter(request, "customerId");
        String money = HttpUtils.getParameter(request, "money");
        String referralCode = HttpUtils.getParameter(request, "referralCode");

        // 校验参数
        if (StringUtils.isEmpty(money) || StringUtils.isEmpty(productionId) || StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        Double moneyDouble = Double.parseDouble(money);

        // 校验投资金额
        if(moneyDouble <= 0) {
            MyException.newInstance(ReturnObjectCode.ORDER_MONEY_NOT_CURRECT, "投资金额不正确").throwException();
        }

        // 校验临时客户是否存在
        CustomerPersonalPO personalPO = customerPersonalService.loadCustomerById4W(customerId, conn);
        if(personalPO == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_NOT_EXISTENT, "数据库异常").throwException();
        }

        // 查询该订单关联的产品
        ProductionPO productionPO = productionService.getProductionById(productionId, conn);
        if (productionPO == null) {
            throw new Exception("产品信息获取有误！");
        }

        // 查询产品是否已售罄
        Double size = productionPO.getSize();
        Double saleMoney = productionService.getSaleMoney(productionPO.getId(), conn);
        if (saleMoney >= size) {
            MyException.newInstance(ReturnObjectCode.PRODUCTION_NOT_SALING, "您选择的产品已售罄！").throwException();
        }

        // 检查产品是否已到期
        long remainSeconds = TimeUtils.getSecondDifference(TimeUtils.getNow(), productionPO.getStopTime(), TimeUtils.Format_YYYY_MM_DD_HH_M_S);
        if (remainSeconds < 0) {
            MyException.newInstance(ReturnObjectCode.PRODUCTION_EXPIRED, "您选择的产品已过期！").throwException();
        }

        // 获取预期收益率
        ProductionCompositionPO compositionPO = productionCompositionService.getProductionCompositionPOByProductionIdAndMoney(productionId, moneyDouble, conn);
        if(compositionPO == null) {
            MyException.newInstance(ReturnObjectCode.PRODUCTION_COMPOSITION_NOT_EXISTENT, "您好，请输入正确的预约金额。").throwException();
        }

        // 预约订单
        OrderPO order = new OrderPO();
        order.setProductionId(productionId);
        order.setProductionCompositionId(compositionPO.getId());
        order.setCustomerId(customerId);
        order.setMoney(moneyDouble);
        order.setCustomerAttribute(0); // 个人客户
        order.setStatus(OrderStatus.Appointment);
        order.setCreateTime(TimeUtils.getNow());
        String orderNum = orderService.getOrderNum4Web(conn);
        order.setOrderNum(orderNum);

        UserPO operator = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);
        String now = TimeUtils.getNow();
        orderService.appointmentOrder("", customerId, productionPO.getId(), order.getProductionCompositionId(), Double.parseDouble(money), null, true, now, null, now, referralCode, OrderStatus.Appointment, null, null, null, conn);

        return SUCCESS;

    }




    /**
     * 生成订单接口（走富友支付）
     *
     * ------------------------------ 特别说明 ------------------------------
     * 1、客户 ID、交易密码任意情况都必须传入
     * 2、如果传入了订单 ID，则会查询出旧的、状态为未支付的订单，进行重新下单支付
     * 3、如果没有传入订单 ID，则产品 ID、推荐人手机号码，金额是必须传入的参数
     * ------------------------------ 特别说明 ------------------------------
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月7日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String build() throws Exception {

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取请求
        HttpServletRequest request = this.getRequest();


        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");
        String trandingPassword = HttpUtils.getParameter(request, "trandingPassword");
        String orderId = HttpUtils.getParameter(request, "orderId");
        String referralCode = HttpUtils.getParameter(request, "referralCode");
        String bankNumber = HttpUtils.getParameter(request, "bankNumber");
        String bankCode = HttpUtils.getParameter(request, "bankCode");

        // 校验交易密码
        if(StringUtils.isEmpty(trandingPassword) || trandingPassword.length() != 32 || StringUtils.isEmpty(customerId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        // 双边去空
        customerId = customerId.trim();
        trandingPassword = trandingPassword.trim();
        trandingPassword = StringUtils.md5(trandingPassword + Config.MD5String);    // 交易密码二次加密
        if(!StringUtils.isEmpty(referralCode)){
            referralCode = referralCode.trim();
        }

        // 初始化实体
        OrderPO orderPO = null;
        ProductionPO productionPO = null;
        CustomerPersonalPO personalPO = null;

        // 如果已经传入订单 ID，则只验证订单 ID、客户 ID，如果订单存在，针对这个订单单独处理
        if(!StringUtils.isEmpty(orderId) && orderId.length() == 32) {

            orderId = orderId.trim();

            // 获取客户
            personalPO = customerPersonalService.loadByCustomerPersonalId(customerId, conn);
            if(personalPO == null) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "系统没有找到您的账户").throwException();
            }

            // 检验客户的资金记录
            List<CustomerMoneyPO> customerMoneyList = customerMoneyService.getByCustomerId4W(personalPO.getId(), conn);
            if (customerMoneyList == null || customerMoneyList.size() != 1) {
                MyException.newInstance(ReturnObjectCode.CUSTOMER_MONEY_NOT_FOUND, "您还没有资金记录").throwException();
            }

            // 校验是否设置了交易密码
            if (StringUtils.isEmpty(personalPO.getTransactionPassword())) {
                MyException.newInstance(ReturnObjectCode.CUSTOMER_TRANDING_PASSWORD_NOT_FOUND, "请先设置交易密码").throwException();
            }

            // 校验交易密码
            if (!personalPO.getTransactionPassword().equals(trandingPassword)) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "您的交易密码有误").throwException();
            }

            // 获取订单
            orderPO = orderService.getOrderById(orderId, conn);
            if(orderPO == null) {
                MyException.newInstance(ReturnObjectCode.ORDER_GET_DETIAL_ERROR, "没有找到订单").throwException();
            }

            // 获取产品
            productionPO = productionService.getProductionById(orderPO.getProductionId(), conn);
            if(productionPO == null) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有找到订单对应的产品").throwException();
            }

            // 判断产品募集期是否结束、投资金额是否满足要求
            Boolean isCanInvest = productionService.checkIsOk(productionPO.getId(),  orderPO.getMoney());
            if (!isCanInvest) {
                MyException.newInstance(ReturnObjectCode.ORDER_CAN_NOT_PAY_AGAIN, Config.getLanguageVariable("w.pay.production.stopOrMoneyError")).throwException();
            }

            // 修改推荐人
            orderService.updateReferralCode(orderPO, referralCode, conn);

        }

        // 否则验证全部（除了订单 ID），生成新的订单
        else {

            // 获取参数
            String money = HttpUtils.getParameter(request, "money");
            String productionId = HttpUtils.getParameter(request, "productionId");

            // 校验参数
            if(StringUtils.isEmpty(money) || StringUtils.isEmpty(productionId) || StringUtils.isEmpty(customerId)) {
                MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
            }
            if(customerId.length() != 32 || productionId.length() != 32) {
                MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
            }

            // 双边去空
            customerId = customerId.trim();
            money = money.trim();
            productionId = productionId.trim();

            // 获取客户
            personalPO = customerPersonalService.loadByCustomerPersonalId(customerId, conn);
            if(personalPO == null) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "系统没有找到您的账户").throwException();
            }

            // 检验客户的资金记录
            List<CustomerMoneyPO> customerMoneyList = customerMoneyService.getByCustomerId4W(personalPO.getId(), conn);
            if (customerMoneyList == null || customerMoneyList.size() != 1) {
                MyException.newInstance(ReturnObjectCode.CUSTOMER_MONEY_NOT_FOUND, "您还没有资金记录").throwException();
            }

            // 校验是否设置了交易密码
            if (StringUtils.isEmpty(personalPO.getTransactionPassword())) {
                MyException.newInstance(ReturnObjectCode.CUSTOMER_TRANDING_PASSWORD_NOT_FOUND, "请先设置交易密码").throwException();
            }

            // 校验交易密码
            if (!personalPO.getTransactionPassword().equals(trandingPassword)) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "您的交易密码有误").throwException();
            }

            // 订单的操作者
            UserPO operator = userService.loadUserByUserId(Config.getSystemConfig("web.default.saleId"), conn);
            if (operator == null) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有找到可用的订单操作者").throwException();
            }

            // 获取产品
            productionPO = productionService.getProductionById(productionId, conn);
            if(productionPO == null) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有找到订单对应的产品").throwException();
            }

            // 判断产品募集期是否结束、投资金额是否满足要求
            Boolean isCanInvest = productionService.checkIsOk(productionPO.getId(),  Double.parseDouble(money));
            if (!isCanInvest) {
                MyException.newInstance(ReturnObjectCode.ORDER_CAN_NOT_PAY_AGAIN, Config.getLanguageVariable("w.pay.production.stopOrMoneyError")).throwException();
            }

            // 生成新订单
            String now = TimeUtils.getNow();
            orderPO = orderService.appointmentOrder("", customerId, productionPO.getId(), null, Double.parseDouble(money), null, true, now, null, now, referralCode, OrderStatus.Appointment, null, null, null, conn);

        }

        // 生成订单支付
//        OrderPayPO orderPayPO = fuiouPaymentService.buildOrderPayment(orderPO, bankCard, bankCode, conn);

        // 生成接口指令
//        APICommandPO commandPO = fuiouPaymentService.buildAPICommand(orderPayPO, Config.getSystemVariableAsInt("pay.param.string.type.xml"), conn);

        // 发起 POST 请求
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("FM", commandPO.getCommands());
//        String responseXml = HttpUtils.postRequest(Config.getSystemConfig("fuiou.order.api.name"), params);

        // 订单支付的回调
//        Map<String, String> map = fuiouPaymentService.orderPaymentCallback(responseXml, orderPayPO, commandPO, conn);

        // 解析数据
        JSONObject jsonObject = new JSONObject();
//        for(String key : map.keySet()) {
//            String value = map.get(key);
//            jsonObject.put(key, value);
//        }
//
//        // 往 APP 构造数据
        String name = "";
        String idCard = "";
//        jsonObject.put("merchantId", Config.getSystemConfig("fuiou.merchant.id"));
//        jsonObject.put("key", Config.getSystemConfig("fuiou.key"));
//        jsonObject.put("backUrl", Config.getSystemConfig("fuiou.callback.service.addr"));
//        jsonObject.put("reUrl", Config.getSystemConfig("fuiou.repay.addr"));
//        jsonObject.put("homeUrl", Config.getSystemConfig("fuiou.callback.web.addr"));

        // 是否实名认证
        CustomerCertificatePO certificatePO = customerCertificateService.loadByCustomerId(personalPO.getId(), "98", conn);
        if(certificatePO == null) {
            jsonObject.put("hasRealName", 0);
        }
        else {
            name = personalPO.getName();
            idCard = AesEncrypt.decrypt(certificatePO.getNumber());
            jsonObject.put("hasRealName", 1);
            jsonObject.put("name", name);
            jsonObject.put("idCard", idCard);
        }

        // 把银行卡号传回给前端，发送给富友
        if(!StringUtils.isEmpty(bankNumber)) {
            jsonObject.put("hasBankAccount", 1);
            jsonObject.put("bankAccount", bankNumber);
        }
        else {
            jsonObject.put("hasBankAccount", 0);
        }

        // 如果实名认证了，往支付订单里写实名和绑卡信息
//        if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(idCard) && !StringUtils.isEmpty(bankCard)) {
//            OrderPayPO orderPay = orderPayService.buildPayAuthentication(jsonObject.get("OrderId").toString(), customerId, personalPO.getName(), idCard, bankCard, null, conn);
//            if(orderPay == null) {
//                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据处理异常").throwException();
//            }
//        }

        jsonObject.put("productionName", productionPO.getWebsiteDisplayName());
        jsonObject.put("money", orderPO.getMoney());
        jsonObject.put("orderNum", orderPO.getOrderNum());

        // 返回数据
        this.getResult().setReturnValue(jsonObject);
        return SUCCESS;

    }


    /**
     * 获取已经兑付好的订单列表
     *
     * 创建人：姚章鹏
     * 时间：2015年12月23日17:32:47
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String getOrderPaymentDone() throws  Exception {
        // 获取数据库连接
        Connection conn = this.getConnection();
        // 获取请求
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String customerId = HttpUtils.getParameter(request, "customerId");

        // 根据客户编号获取订单
        Pager orders = orderService.loadFinishOrderByCustomerId(customerId, request, conn);

        getResult().setReturnValue(orders);
        return SUCCESS;
    }

    // 读取客户
    public String loadCustomer() throws Exception {

        customerPersonalVO = HttpUtils.getInstanceFromRequest(getRequest(), "personalVO", CustomerPersonalVO.class);

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = customerPersonalService.loadCustomerById(customerPersonalVO, conditions, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    @Security(needWebCode = true)
    public String appointmentOrPay4Web() throws Exception {

        String parameters = HttpUtils.getParametersStringValue(getRequest());
        System.out.println(parameters);

        order = HttpUtils.getInstanceFromRequest(getRequest(), "order", OrderPO.class);

        order.setSalemanId("");
        order.setContractNo("");
        OrderPO appointmentOrder = orderService.appointmentOrderFromNow("", order.getCustomerId(), order.getProductionId(), order.getProductionCompositionId(), order.getMoney(), order.getPayTime(), order.getContractNo(), order.getReferralCode(), OrderStatus.Appointment, null, null, null, getConnection());

        if (appointmentOrder != null) {
            String paymentInfo = productionService.getPaymentInfo(order.getProductionId(), getConnection());
            getResult().setMessage("预约成功");
        }
        else {
            getResult().setMessage("预约失败");
        }

        getRequest().setAttribute("returnObject", getResult());

        return SUCCESS;
    }


    /***
     * 通宝订单点击支付后跳转到显示打款界面
     * @return
     */
    public String showPayPage() throws Exception {
        //从request中取得传递过来的数据
        String productName = getHttpRequestParameter("goods_name");
        String orderNo = getHttpRequestParameter("order_id");
        String orderAmount = getHttpRequestParameter("order_amt");





       /* //日期格式转化为yyyy-MM-dd hh:mm:ss形式
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date =  sdf1.parse(orderDatetime);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String orderDate = sdf2.format(date);*/



        //将值传递给前台JSP显示
        getRequest().setAttribute("productName", productName);
       /* getRequest().setAttribute("payerName", payerName);*/
        getRequest().setAttribute("orderNo", orderNo);
/*        getRequest().setAttribute("orderDate", orderDate);*/
        getRequest().setAttribute("orderAmount", orderAmount);

        return SUCCESS;
    }




    public CustomerPersonalVO getCustomerPersonalVO() {
        return customerPersonalVO;
    }

    public void setCustomerPersonalVO(CustomerPersonalVO customerPersonalVO) {
        this.customerPersonalVO = customerPersonalVO;
    }

    public OrderPO getOrder() {
        return order;
    }

    public void setOrder(OrderPO order) {
        this.order = order;
    }

    public OrderVO getOrderVO() {
        return orderVO;
    }

    public void setOrderVO(OrderVO orderVO) {
        this.orderVO = orderVO;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public CustomerMoneyPO getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(CustomerMoneyPO customerMoney) {
        this.customerMoney = customerMoney;
    }


    public AllinPayOrderPO getAllinPayOrder() {
        return allinPayOrder;
    }

    public LegalAgreementPO getLegalAgreement() {
        return legalAgreement;
    }

    public void setLegalAgreement(LegalAgreementPO legalAgreement) {
        this.legalAgreement = legalAgreement;
    }

    public void setAllinPayOrder(AllinPayOrderPO allinPayOrder) {
        this.allinPayOrder = allinPayOrder;
    }
}
