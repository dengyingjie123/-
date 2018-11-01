package com.youngbook.service.pay;

import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Bank;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerAuthenticationStatusDao;
import com.youngbook.dao.customer.ICustomerCertificateDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.entity.po.core.APICommandPO;
import com.youngbook.entity.po.core.OrderPayPO;
import com.youngbook.entity.po.core.OrderPaySearchPO;
import com.youngbook.entity.po.customer.CustomerAuthenticationStatus;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.pay.APICommandStatus;
import com.youngbook.entity.po.pay.OrderPayStatus;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderPayChannel;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.service.customer.CustomerAccountService;
import com.youngbook.service.customer.CustomerAuthenticationStatusService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.production.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("fuiouPaymentService")
public class FuiouPaymentService {

    @Autowired
    ICustomerAuthenticationStatusDao customerAuthenticationStatusDao;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    ProductionService productionService;

    @Autowired
    CustomerAccountService customerAccountService;

    @Autowired
    IOrderDao orderDao;

    @Autowired
    OrderService orderService;

    @Autowired
    ICustomerCertificateDao customerCertificateDao;

    /**
     * 校验是否支付
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月20日
     *
     * @param customerId
     * @param bizId
     * @param conn
     * @return
     * @throws Exception
     */
    public Boolean isPaied(String customerId, String bizId, Connection conn) throws Exception {

        // 查询接口指令
        APICommandPO commandPO = this.loadAPICommandByBizId(bizId, conn);
        if(commandPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到支付接口指令").throwException();
        }

        // 查询支付订单
        OrderPayPO orderPay = this.loadOrderPay(commandPO.getId(), customerId, conn);
        if(orderPay == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到订单支付内容").throwException();
        }

        return orderPay.getStatus() == OrderPayStatus.SUCCESS ? true : false;

    }

    /**
     * 构建订单支付表
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param order     系统订单对象
     * @param bankCard  银行卡号
     * @param bankCode  银行编码
     * @param conn      数据库连接
     * @return
     * @throws Exception
     *
     * 作者：quan.zeng
     * 内容：修改代码
     * 时间：2015年12月18日
     * 内容：去掉实名认证和银行卡校验
     *
     */
    public OrderPayPO buildOrderPayment(OrderPO order, String bankNumber, String bankCode, Connection conn) throws Exception {

        // 校验订单为未支付的订单
        if(order.getStatus() != OrderStatus.Appointment){
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "订单状态无效").throwException();
        }

        // 获取、检查订单的产品状态是否为在售
        Boolean productionStatus = productionService.getProductionStatus(order.getProductionId(), conn);
        if(!productionStatus){
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "该产品当前不可售").throwException();
        }

        // 查询客户信息
        CustomerPersonalPO personalPO = customerPersonalDao.loadByCustomerPersonalId(order.getCustomerId(), conn);
        if(personalPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到当前客户").throwException();
        }

        // 查询客户的银行卡信息
       /* CustomerAccountPO accountPO = accountService.loadByCustomerId(personalPO.getId(), conn);
        if(accountPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到客户的银行卡信息").throwException();
        }*/

        // 查询产品信息
        ProductionPO productionPO = productionService.loadProductionById(order.getProductionId(), conn);
        if(productionPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到对应的产品").throwException();
        }

        // 通过 orderId 查询 OrderPayPO，如果没有则实例化新的对象
        OrderPayPO orderPay = this.getOrderPay(order.getId(), conn);
        if(orderPay == null) {
            // 如果没有查询到 OrderPayPO，新建一个 OrderPayPO
            orderPay = new OrderPayPO();
            orderPay.setState(Config.STATE_CURRENT);
            orderPay.setOperateTime(TimeUtils.getNow());
            orderPay.setOperatorId(Config.getDefaultOperatorId());
        }

        // orderPay.setAPIId("支付接口流水编号");
        orderPay.setAPIName(Config.getSystemConfig("fuiou.api.name"));
        orderPay.setOrderId(order.getId());
        orderPay.setMerchantId(Config.getSystemConfig("fuiou.merchant.id"));
        orderPay.setCustomerId(personalPO.getId());
        orderPay.setCustomerName(personalPO.getName());
        orderPay.setProductionId(productionPO.getId());
        orderPay.setProductionName(productionPO.getName());
        orderPay.setMoney(order.getMoney() * 100); // 支付接口单位是分
        orderPay.setPayType(1);

        if(!StringUtils.isEmpty(bankNumber)) {
            orderPay.setBankAccount(AesEncrypt.encrypt(bankNumber));
        }

        if(!StringUtils.isEmpty(bankCode)) {
            Map<String, String> banks = Config4Bank.getBanks(conn);
            if(banks.containsKey(bankCode)) {
                orderPay.setBankCode(bankCode);
                orderPay.setBankName(banks.get(bankCode));
            }
            else {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "暂不支持该银行").throwException();
            }
        }
        //orderPay.setCustomerCertificateType(certificatePO.getName());
        //orderPay.setCustomerCertificateNo(certificatePO.getNumber());
        orderPay.setMobile(personalPO.getMobile());
        // orderPay.setAPICommandId("指令编号");
        // orderPay.setStatus("状态");
        orderPay.setCallbackPageURL(Config.getSystemConfig("fuiou.callback.web.addr"));
        orderPay.setCallbackServiceURL(Config.getSystemConfig("fuiou.callback.service.addr"));
        // orderPay.setVersion("版本号");
        orderPay.setAPICommandSendTimes(0);
        orderPay.setSource(Config.getSystemVariable("api.pay.source.app"));

        Integer count = MySQLDao.insertOrUpdate(orderPay, conn);
        if(count != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据库异常").throwException();
        }

        return count == 1 ? orderPay : null;

    }

    /**
     * 构建接口指令，返回指令，供前端发送请求用
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param orderPayPO        订单支付对象
     * @param paramStringType   参数字符串类型（xml？json？http？）
     * @param conn
     * @return
     * @throws Exception
     */
    public APICommandPO buildAPICommand(OrderPayPO orderPayPO, Integer paramStringType, Connection conn) throws Exception {

        // 校验参数字符串类型是否是 XML
        if(paramStringType != Config.getSystemVariableAsInt("pay.param.string.type.xml")) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "参数字符串类型不正确").throwException();
        }

        // 校验支付订单的状态是否可以生成支付指令
        if(orderPayPO.getStatus() == OrderPayStatus.SUCCESS) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "该支付订单的状态不正确").throwException();
        }

        // 获取支付资料
        String merchantId = Config.getSystemConfig("fuiou.merchant.id");
        String fuiouKey = Config.getSystemConfig("fuiou.key");
        int amt = (int)orderPayPO.getMoney();
        String rmk1 = "hide";
        String md5Sign = StringUtils.md5(merchantId + "|" + amt + "|" + fuiouKey).toLowerCase();
        String command = "<FM><MchntCd>" + merchantId + "</MchntCd><Amt>" + amt + "</Amt><Rmk1>" + rmk1 + "</Rmk1><Rmk2></Rmk2><Rmk3></Rmk3><Sign>" + md5Sign + "</Sign></FM>";

        APICommandPO apiCommand = null;

        // 校验 OrderPayPO 是否有指令编号，有则查询出来
        String APICommandId = orderPayPO.getAPICommandId();
        if(!StringUtils.isEmpty(APICommandId)) {
            apiCommand = this.loadAPICommand(APICommandId, conn);
            if(apiCommand == null) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "接口指令编号有误").throwException();
            }
            if(StringUtils.isEmpty(apiCommand.getCommands())) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "指令有误").throwException();
            }
            // 递增指令发送次数
            apiCommand.setSendTimes(apiCommand.getSendTimes() + 1);
        }

        // 如果查询的结果为空
        else {
            apiCommand = new APICommandPO();
            apiCommand.setState(Config.STATE_CURRENT);
            apiCommand.setOperateTime(TimeUtils.getNow());
            apiCommand.setOperatorId(Config.getDefaultOperatorId());
            apiCommand.setAPIType(Config.getSystemVariable("api.type.fuiou"));
            apiCommand.setAPIName(Config.getSystemConfig("fuiou.api.name"));
            apiCommand.setAPIURL(Config.getSystemConfig("fuiou.api.name"));
            apiCommand.setCommands(command);
            apiCommand.setCommandType(Config.getSystemVariableAsInt("pay.param.string.type.xml"));
            apiCommand.setSendTimes(1);
            apiCommand.setSuccessTimes(0);
            apiCommand.setFailedTimes(0);
        }

        // 新增或修改
        Integer apiCommandExecCount = MySQLDao.insertOrUpdate(apiCommand, conn);
        if(apiCommandExecCount != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据库异常").throwException();
        }

        // 修改订单支付的业务编号、递增指令发送次数
        orderPayPO.setAPICommandId(apiCommand.getId());
        orderPayPO.setAPICommandSendTimes(orderPayPO.getAPICommandSendTimes() + 1);
        MySQLDao.insertOrUpdate(orderPayPO, conn);

        // 返回指令
        return apiCommandExecCount == 1 ? apiCommand : null;

    }

    /**
     * 订单支付回调
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月18日
     *
     * @param responseXml
     * @return
     * @throws Exception
     */
    public Map<String, String> orderPaymentCallback(String responseXml, OrderPayPO orderPayPO, APICommandPO commandPO, Connection conn) throws Exception {

        Map<String, String> responseMap = FuiouPaymentService.parseFuiouXmlCallback(responseXml);

        String rcd = responseMap.get("Rcd");            // 响应码
        String rDesc = responseMap.get("RDesc");        // 响应文字
        String bizId = responseMap.get("OrderId");      // 富友的订单 ID
        String sign = responseMap.get("Sign");          // MD5Utils 摘要数据

        if(StringUtils.isEmpty(rcd) || StringUtils.isEmpty(rDesc) || StringUtils.isEmpty(bizId) || StringUtils.isEmpty(sign)) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "接口回调数据不完整，或支付出现问题，响应码：" + rcd).throwException();
        }

        // 修改接口指令数据，如果是 0000，说明成功，否则失败
        if("0000".equals(rcd)) {
            commandPO.setBizId(bizId);
            commandPO.setSuccessTimes(commandPO.getSuccessTimes() + 1);
            commandPO.setCallbackCode(rcd);
            commandPO.setCallbackMessage(rDesc);
        }
        else {
            commandPO.setFailedTimes(commandPO.getFailedTimes() + 1);
        }
        Integer apiCommandExecCount = MySQLDao.insertOrUpdate(commandPO, conn);

        return apiCommandExecCount != null ? responseMap : null;
    }

    /**
     * 支付回调
     *
     * --------------------------------------------- 说明开始 --------------------------------------------------
     * 1、先查询前提条件，前提条件如果都失败，也许是属于非正规异常（可能是本来就没支付成功或存在不安全操作），不给予退款
     * 2、属于正规的异常，先把订单支付、支付接口的状态设为失败，系统订单设为退款
     * --------------------------------------------- 说明结束 --------------------------------------------------
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param returnCode
     * @param returnString
     * @param bizId
     * @param bankCard
     * @param realPaiedMoney
     * @param sign
     * @param ip
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPayPO paymentCallback(String returnCode, String returnString, String bizId, String bankNumber, Double realPaiedMoney, String sign, String ip, Connection conn) throws Exception {

        // 校验数据是否完整
        if(StringUtils.isEmpty(returnCode) ||  StringUtils.isEmpty(bizId) || /*StringUtils.isEmpty(bankCard) || realPaiedMoney == null ||*/ StringUtils.isEmpty(sign)) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "接口回调数据不完整").throwException();
        }

        // 校验响应码，如果支付失败，抛异常
        if(!"0000".equals(returnCode)) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "APP 支付失败，响应码为 " + returnCode).throwException();
        }

        // 进行验签
        String key = returnCode + "|" + bizId + "|" + Config.getSystemConfig("fuiou.key");
        String md5 = StringUtils.md5(key).toLowerCase();
        if(!md5.equals(sign)) {
            MyException.newInstance(ReturnObjectCode.ORDER_PAY_ENCRYPE_FAILED, "您的支付环境不安全").throwException();
        }

        // 查询接口指令
        APICommandPO apiCommand = this.loadAPICommandByBizId(bizId, conn);
        if(apiCommand == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询该业务的接口指令").throwException();
        }

        // 查询订单支付
        String apiCommandId = apiCommand.getId();
        OrderPayPO orderPay = this.loadOrderPay(apiCommandId, conn);
        if(orderPay == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询该业务的订单支付内容").throwException();
        }

        // 校验订单支付的金额和卡号
        /*
        if(orderPay.getMoney() != realPaiedMoney) {
            MyLog.saveLog("实际支付的金额与订单支付的金额不符", LogType.PayMoneyNotCurrect);
            MyException.newInstance(ReturnObjectCode.ORDER_CALLBACK_MONEY_NOT_CORRECT, "您实际支付的金额与系统订单的金额不相符，请联系客服！").throwException();
        }
        if(!orderPay.getBankAccount().equals(AesEncrypt.encrypt(bankCard))) {
            MyLog.saveLog("实际支付的银行卡与订单支付的银行卡不符", LogType.PayBankCardNotCurrect);
            MyException.newInstance(ReturnObjectCode.ORDER_CALLBACL_BANK_CARD_NOT_CURRENT, "您实际支付的银行卡与系统订单的银行卡不相符，请联系客服！").throwException();
        }*/

        // 查询客户
        customerPersonalDao = this.customerPersonalDao;
        CustomerPersonalPO personalPO = customerPersonalDao.loadByCustomerPersonalId(orderPay.getCustomerId(), conn);
        if(personalPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到该客户").throwException();
        }

        // 实例化某些服务、获取用户
        CustomerAuthenticationStatusService statusService = new CustomerAuthenticationStatusService();
        String userId = Config.getSystemConfig("web.default.operatorId");

        // 查询系统订单
        String orderId = orderPay.getOrderId();
        OrderPO order = orderDao.loadByOrderId(orderId, conn);
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

        try {

            // 销售产品
            orderService.saleOrderOnlineFormNow(order, userId, conn);

            // 修改客户的身份证信息
            customerCertificateDao.insertOrUpdateCertificate(orderPay.getCustomerId(), orderPay.getCustomerCertificateType(), orderPay.getCustomerCertificateNo(), userId, conn);

            // 修改客户的银行卡信息
            Map<String, String> banks = Config4Bank.getBanks(conn);
            if(banks.containsKey(orderPay.getBankCode())) {
                customerAccountService.insertOrUpdateBankAccount(orderPay.getCustomerId(), orderPay.getCustomerName(), orderPay.getBankAccount(), orderPay.getBankCode(), banks.get(orderPay.getBankCode()), conn);
            }
            else {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "暂不支持该银行卡").throwException();
            }

            // 修改客户的真实名字
            customerPersonalDao.updateCustomerRealName(orderPay.getCustomerId(), orderPay.getCustomerName(), conn);

            // 修改客户的实名认证状态（同时身份证和银行卡）
            customerAuthenticationStatusDao.saveAuthenticationStatus(personalPO.getId(), CustomerAuthenticationStatus.AUTH_TYPE_ACCOUNT, conn);


            // 修改接口指令为成功状态、 业务编号、递增成功次数、令反馈代码、指令反馈文本
            apiCommand.setStatus(APICommandStatus.SUCCESS);
            apiCommand.setBizId(bizId);
            Integer successTimes = apiCommand.getSuccessTimes();
            if(successTimes == Integer.MAX_VALUE) {
                apiCommand.setSuccessTimes(1);
            }
            else {
                apiCommand.setSuccessTimes(apiCommand.getSuccessTimes() + 1);
            }
            apiCommand.setCallbackCode(returnCode);
            apiCommand.setCallbackMessage(returnString);
            Integer apiCommandUpdateCount = MySQLDao.insertOrUpdate(apiCommand, conn);
            if(apiCommandUpdateCount != 1) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据库异常").throwException();
            }

            // 修改订单支付的状态
            orderPay.setStatus(OrderPayStatus.SUCCESS);
            Integer orderPayUpdateCount = MySQLDao.insertOrUpdate(orderPay, conn);
            if(orderPayUpdateCount != 1) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据库异常").throwException();
            }

            // 出现异常，特殊处理
        } catch (Exception e) {

            e.printStackTrace();

            // Action 的事务进行回滚
            conn.rollback();

            throw e;
        }

        return orderPay;

    }



    /**
     * 支付订单查询回调
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param fuiouOrderId
     * @param result
     * @param paramStringType
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPaySearchPO queryCallback(String fuiouOrderId, String result, Integer paramStringType, HttpServletRequest request, Connection conn) throws Exception {

        // 校验富友的订单 ID 是否合法
        if(!StringUtils.isEmpty(fuiouOrderId)) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "缺失接口的订单参数").throwException();
        }

        // 查询支付订单
        OrderPayPO orderPayPO = this.getOrderPay(fuiouOrderId, conn);
        if(orderPayPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有对应的支付订单").throwException();
        }

        // 查询接口指令
        APICommandPO apiCommand = this.loadAPICommand(orderPayPO.getAPICommandId(), conn);
        if(apiCommand == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有对应的接口指令").throwException();
        }

        // 校验参数的字符串类型是否为 XML
        if(paramStringType != Config.getSystemVariableAsInt("pay.param.string.type.xml")) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "参数字符串类型不正确，无法进行 XML 解析").throwException();
        }

        // 解析出富友回调的结果
        Map<String, String> map = FuiouPaymentService.parseFuiouXmlCallback(result);
        String rcd = map.get("Rcd");            // 响应码
        String rDesc = map.get("RDesc");        // 响应文字
        String sign = map.get("Sign");          // MD5Utils 摘要数据

        // 校验数据是否完整
        if(StringUtils.isEmpty(rcd) || StringUtils.isEmpty(rDesc) || StringUtils.isEmpty(sign)) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "接口回调数据不完整").throwException();
        }

        // 校验响应码，如果失败，抛异常
        if(!"5185".equals(rcd) || !"5077".equals(rcd) || !"11V3".equals(rcd) || !"11E3".equals(rcd)) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "APP 支付查询失败").throwException();
        }

        // 新增 OrderPaySearchPO
        OrderPaySearchPO orderPaySearch = new OrderPaySearchPO();
        orderPaySearch.setSid(MySQLDao.getMaxSid("Core_OrderPaySearch", conn));
        orderPaySearch.setId(IdUtils.getUUID32());
        orderPaySearch.setState(Config.STATE_CURRENT);
        orderPaySearch.setOperateTime(TimeUtils.getNow());
        orderPaySearch.setOperatorId(Config.getDefaultOperatorId());
        orderPaySearch.setOrderPayId(orderPayPO.getId());
        orderPaySearch.setCallbackPageURL(Config.getSystemVariable("fuiou.pay.search.callback.web.addr"));
        orderPaySearch.setCallbackServiceURL(Config.getSystemVariable("fuiou.pay.search.callback.service.addr"));
        orderPaySearch.setCallbackOrderId(apiCommand.getBizId());
        orderPaySearch.setCallbackOrderTime(TimeUtils.getNow());
        orderPaySearch.setCallbackOrderStatus(rDesc);
        orderPaySearch.setCallbackMoney(orderPayPO.getMoney());
        Integer execCount = MySQLDao.insert(orderPaySearch, conn);

        return execCount == 1 ? null : orderPaySearch;

    }

    /**
     * 根据订单支付的 ID，获取订单支付查询的记录
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param orderPayId
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPaySearchPO loadSearchByOrderPayId(String orderPayId, Connection conn) throws Exception {

        // 组织 SQL 和参数
        String sql = "select * from Core_OrderPaySearch s where s.state = " + Config.STATE_CURRENT + " and s.orderPayId = ?";
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, orderPayId, parameters);

        // 查询
        List<OrderPaySearchPO> orderPaySearchs = MySQLDao.search(sql, parameters, OrderPaySearchPO.class, null, conn);

        // 返回
        return orderPaySearchs.size() == 1 ? orderPaySearchs.get(0) : null;

    }

    /**
     * 根据富友的 ID，查询接口指令
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param bizId
     * @param conn
     * @return
     * @throws Exception
     */
    public APICommandPO loadAPICommandByBizId(String bizId, Connection conn) throws Exception {

        // 组织 SQL 和参数
        String sql = "select * from core_apicommand api where api.state = " + Config.STATE_CURRENT + " and api.bizId = ?";
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, bizId, parameters);

        // 查询
        List<APICommandPO> apiCommands = MySQLDao.search(sql, parameters, APICommandPO.class, null, conn);

        // 返回
        return apiCommands.size() == 1 ? apiCommands.get(0) : null;

    }

    /**
     * 根据 ID，查询接口指令
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public APICommandPO loadAPICommand(String id, Connection conn) throws Exception {

        // 组织 SQL 和参数
        String sql = "select * from core_apicommand api where api.state = " + Config.STATE_CURRENT + " and api.id = ?";
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, id, parameters);

        // 查询
        List<APICommandPO> apiCommands = MySQLDao.search(sql, parameters, APICommandPO.class, null, conn);

        // 返回
        return apiCommands.size() == 1 ? apiCommands.get(0) : null;

    }

    /**
     * 通过系统订单 ID，查询订单支付表
     *
     * 创建者：姚章鹏
     * 时间：2015年12月17日14:16:00
     * 内容：根据订单id判断订单支付表
     *
     * @param orderId
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPayPO getOrderPay(String orderId, Connection conn) throws Exception {

        String sql = "select * from Core_OrderPay co where  co.state=0 and co.orderId= ?";

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, orderId, parameters);
        List<OrderPayPO> orderPayPO = MySQLDao.search(sql, parameters, OrderPayPO.class, null, conn);

        if (orderPayPO != null && orderPayPO.size() == 1) {
            return orderPayPO.get(0);
        }

        return null;
    }

    /**
     * 通过接口指令的 ID，获取订单支付
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月18日
     *
     * @param commandId
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPayPO loadOrderPay(String commandId, String customerId, Connection conn) throws Exception {

        String sql = "select * from Core_OrderPay co where co.state = 0 and co.apiCommandId = ? and co.customerId = ?";

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, commandId, parameters);
        parameters = Database.addQueryKVObject(2, customerId, parameters);
        List<OrderPayPO> orderPayPO = MySQLDao.search(sql, parameters, OrderPayPO.class, null, conn);

        if (orderPayPO != null && orderPayPO.size() == 1) {
            return orderPayPO.get(0);
        }

        return null;

    }

    /**
     * 根据 API 指令的 ID，查询订单支付
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param ApiCommandId
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPayPO loadOrderPay(String ApiCommandId, Connection conn) throws Exception {

        // 组织 SQL 和参数
        String sql = "select * from core_orderPay p where p.state = " + Config.STATE_CURRENT + " and p.ApiCommandId = ?";
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, ApiCommandId, parameters);

        // 查询
        List<OrderPayPO> orderPays = MySQLDao.search(sql, parameters, OrderPayPO.class, null, conn);

        // 返回
        return orderPays.size() == 1 ? orderPays.get(0) : null;

    }

    /**
     * 解析富友的 XML 回调数据
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseFuiouXmlCallback(String result) throws Exception {

        // 实例化 XML 帮助类
        XmlHelper helper = new XmlHelper(result);

        // 解析并获取富友接口回调的 XML 参数
        String rcd = helper.getValue("//FM/Rcd");
        String rDesc = helper.getValue("//FM/RDesc");
        String orderId = helper.getValue("//FM/OrderId");
        String sign = helper.getValue("//FM/Sign");

        // 构造到 Map
        Map<String, String> map = new HashMap<String, String>();
        map.put("Rcd", rcd);
        map.put("RDesc", rDesc);
        map.put("OrderId", orderId);
        map.put("Sign", sign);

        // 返回数据
        return map;

    }

}
