package com.youngbook.service.pay;

import com.fuiou.mpay.encrypt.DESCoderFUIOU;
import com.fuiou.util.MD5;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.fuiou.FuiouMD5Utils;
import com.youngbook.common.utils.fuiou.FuiouUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.core.IAPICommandDao;
import com.youngbook.dao.customer.ICustomerAccountDao;
import com.youngbook.dao.customer.ICustomerAuthenticationStatusDao;
import com.youngbook.dao.customer.ICustomerCertificateDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IOrderDetailDao;
import com.youngbook.dao.system.IKVDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.core.APICommandPO;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.fuiou.FuiouPCPayPO;
import com.youngbook.entity.po.pay.APICommandStatus;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.service.BaseService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.system.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("fuiouService")
public class FuiouService extends BaseService {

    @Autowired
    IOrderDao orderDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    ICustomerAccountDao customerAccountDao;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    ICustomerCertificateDao customerCertificateDao;

    @Autowired
    OrderService orderService;

    @Autowired
    IKVDao kvDao;

    @Autowired
    IAPICommandDao apiCommandDao;

    @Autowired
    IOrderDetailDao orderDetailDao;

    @Autowired
    ICustomerAuthenticationStatusDao customerAuthenticationStatusDao;

    @Autowired
    ILogDao logDao;


    public static void main(String [] args) throws Exception {

//        StringBuffer sbXml = new StringBuffer();
//        sbXml.append("<ORDER>");
//        sbXml.append("<RESPONSECODE>A</RESPONSECODE>");
//        sbXml.append("<MCHNTORDERID>B</MCHNTORDERID>");
//        sbXml.append("</ORDER>");
//
//        FuiouService fuiouService = new FuiouService();
//        KVObjects kvObjects = fuiouService.parseMobilePayCallback(sbXml.toString());
//
//        System.out.println(kvObjects.size());

    }


    public void dealMobilePayCallback(Connection conn) throws Exception {
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("dealMobilePayCallback", this);
        dbSQL.initSQL();

        List<APICommandPO> commands = MySQLDao.search(dbSQL, APICommandPO.class, conn);

        for (int i = 0; commands != null && i < commands.size(); i++) {

            APICommandPO command = commands.get(i);


            LogService.console("开始处理富友订单（"+i+"/"+commands.size()+"） 反馈 [commandId="+command.getId()+"]["+command.getCallbackMessage()+"] ");



            String urlParameters = command.getCommands();

            KVObjects callback = StringUtils.getUrlParameters(urlParameters);

            String code = callback.getItem("RESPONSECODE").toString();
            String orderId = callback.getItem("MCHNTORDERID").toString();

            // 默认处理员
            String userId = Config.getSystemConfig("web.default.operatorId");


            OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);

            if(orderPO == null) {

                logDao.save("富友支付","富友支付反馈错误", "orderId="+orderId+"&commandId=" + command.getId());
                MyException.newInstance("没有查询到对应的系统订单", "orderId="+orderId).throwException();
                LogService.console("没有查询到对应的系统订单 orderId="+orderId+"&commandId=" + command.getId());
            }

            LogService.console("获得订单信息["+orderPO.getId()+"]["+orderPO.getCustomerName()+"]["+orderPO.getMoney()+"]");

            // 查询客户
            CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(orderPO.getCustomerId(), conn);
            if(customerPersonalPO == null) {
                MyException.newInstance("没有查询到该客户", "customerId=" + orderPO.getCustomerId()).throwException();
                LogService.console("没有查询到该客户 customerId=" + orderPO.getCustomerId());
            }



            /**
             * 如果同一订单的反馈，之前处理成功，则设置本次反馈为成功，不做进一步的逻辑处理
             */
            List<APICommandPO> checkCommands = apiCommandDao.loadByBIzId(orderId, conn);
            for (int j = 0; checkCommands != null && j < checkCommands.size(); j++) {
                APICommandPO checkCommand = checkCommands.get(j);
                if (checkCommand.getAPIName().contains("富友支付-手机支付-接收")) {
                    if (checkCommand.getStatus() == APICommandStatus.SUCCESS && checkCommand.getCallbackCode().equals("0000")) {
                        command.setStatus(APICommandStatus.SUCCESS);
                        MySQLDao.insertOrUpdate(command, conn);

                        LogService.console("忽略曾经重复的成功反馈 commandId=" + command.getId());

                        return;
                    }
                }
            }



            /**
             * 检验数据正确性
             */
            KVObjects parameters = StringUtils.getUrlParameters(command.getCommands());
            String SIGN = parameters.getItemString("SIGN");
            String VERSION = parameters.getItemString("VERSION");
            String TYPE = parameters.getItemString("TYPE");
            String RESPONSECODE = parameters.getItemString("RESPONSECODE");
            String RESPONSEMSG = parameters.getItemString("RESPONSEMSG");
            String MCHNTCD = parameters.getItemString("MCHNTCD");
            String MCHNTORDERID = parameters.getItemString("MCHNTORDERID");
            String ORDERID = parameters.getItemString("ORDERID");
            String BANKCARD = parameters.getItemString("BANKCARD");
            String AMT = parameters.getItemString("AMT");


            String SIGN_Check = getMd5(TYPE, VERSION, RESPONSECODE, MCHNTCD, MCHNTORDERID, ORDERID, AMT, BANKCARD);

            if (StringUtils.isEmpty(SIGN) || !SIGN.equals(SIGN_Check)) {
                MyException.newInstance("反馈检验失败，签名失败", "CommandId=" + command.getId()).throwException();
                LogService.console("签名校验失败");
            }

            LogService.console("签名校验成功");

            /**
             * 检验订单编号
             */
            if (!MCHNTORDERID.equals(orderPO.getId())) {
                MyException.newInstance("反馈检验失败，订单编号不匹配", "CommandId=" + command.getId()).throwException();
            }


            if (!AMT.equals(NumberUtils.getMoney2FenString(orderPO.getMoney()))) {
                MyException.newInstance("反馈检验失败，订单金额不匹配", "CommandId=" + command.getId()).throwException();
            }



            // 反馈成功
            if (code.equals("0000")) {
                LogService.console("富友反馈成功["+code+"]");

                /**
                 * 处理第一次反馈
                 */
                customerPersonalPO.setCustomerCatalogId(CustomerCatalog.Confirmed);
                MySQLDao.insertOrUpdate(customerPersonalPO, conn);


                try {

                    // 改变订单状态
                    LogService.console("订单原始状态为["+orderPO.getStatus()+"]");

                    if (orderPO.getStatus() == OrderStatus.Appointment || orderPO.getStatus() == OrderStatus.AppointmentCancel || orderPO.getStatus() == OrderStatus.SaleAndWaiting) {
                        orderPO.setStatus(OrderStatus.SaleAndWaiting);
                        LogService.console("更改订单状态为["+orderPO.getStatus()+"]");
                        orderService.saveOrder(orderPO, Config.getDefaultOperatorId(), conn);
                    }


                    // 销售产品
                    orderService.saleOrderOnlineFormNow(orderPO, userId, conn);
                    orderDetailDao.saveOrderDetail(orderPO, 0, TimeUtils.getNow(), "富友接口验证成功", Config.getDefaultOperatorId(), conn);
                    LogService.console("订单状态设置为已支付");

                    // 修改客户的实名认证状态（同时身份证和银行卡）
                    customerAuthenticationStatusDao.saveAuthenticationStatus(customerPersonalPO.getId(), CustomerAuthenticationStatus.AUTH_TYPE_ACCOUNT, conn);

                    logDao.save("富友支付", "富友支付反馈处理-成功处理", "customreName="+orderPO.getCustomerName()+"&orderId="+orderPO.getId()+"&commandId=" + command.getId(), conn);
                    LogService.console("完成反馈处理");
                }
                catch (Exception ex) {

                    logDao.save("富友支付", "富友支付反馈错误", "orderId=&commandId=" + command.getId() + "&exception=" + ex.getMessage());

                    ex.printStackTrace();

                    throw ex;
                }



                command.setStatus(APICommandStatus.SUCCESS);

                MySQLDao.insertOrUpdate(command, conn);

                LogService.console("处理富友订单反馈 结束");
                LogService.console("");
                LogService.console("");
                LogService.console("");



            }
            // 反馈失败
            else {
                LogService.console("富友反馈失败["+code+"]");



                // 保存订单明细
                String description = "富友反馈：【"+command.getCallbackMessage()+"】";

                orderDetailDao.saveOrderDetail(orderPO, 0, TimeUtils.getNow(), description, Config.getDefaultOperatorId(), conn);

                command.setStatus(APICommandStatus.SUCCESS);

                MySQLDao.insertOrUpdate(command, conn);


                logDao.save("富友支付", "富友支付反馈处理-失败处理", "customreName="+orderPO.getCustomerName()+"&orderId="+orderPO.getId()+"&commandId=" + command.getId(), conn);
                LogService.console("处理富友订单反馈 失败处理已结束");
                LogService.console("");
                LogService.console("");
                LogService.console("");




                if (customerPersonalPO != null && !StringUtils.isEmpty(customerPersonalPO.getCustomerCatalogId()) && customerPersonalPO.getCustomerCatalogId().equals(CustomerCatalog.Template)) {



                    /**
                     * 如果客户信息尚未确定，则删除身份证号
                     */
//                    CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerPersonalPO.getId(), conn);
//                    if (customerCertificatePO != null) {
//                        customerCertificateDao.delete(customerCertificatePO, userId, conn);
//                    }



                    /**
                     * 如果客户信息尚未确定，则删除银行卡号
                     */
//                    customerAccountDao.removeById(orderPO.getAccountId(), userId, conn);





                    /**
                     * 订单设置为支付失败
                     * 如果订单状态为【预约】、【预约待确认】或【支付待确认】，才可进行设置失操作
                     *
                     * 暂时不设置订单状态，富友同一个订单号可能返回数条反馈信息，其中有可能包含成功的
                     * 如果一直都没有包含成功的，则通过计划任务，取消订单
                     */
//                    if (NumberUtils.checkNumberIn(orderPO.getStatus(), OrderStatus.Appointment, OrderStatus.AppointmentWaiting, OrderStatus.SaleAndWaiting)) {
//                        orderPO.setStatus(OrderStatus.SaleAndWaitingCancel);
//                        MySQLDao.insertOrUpdate(orderPO, conn);
//                    }

                }


            }
        }
    }


    public void dealPCPayCallback(Connection conn) throws Exception {

        String userId = Config.getDefaultOperatorId();

        /**
         * 处理APICommand
         *
         * 获取同一批次的反馈记录，若已经处理过，则直接忽略
         * 最后生成需要处理的订单编号
         */

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("C9C91712");
        dbSQL.initSQL();

        List<APICommandPO> commands = MySQLDao.search(dbSQL, APICommandPO.class, conn);

        List<String> orderIds = new ArrayList<>();

        // 组织属于同一订单的反馈信息
        for (int i = 0; commands != null && i < commands.size(); i++) {

            APICommandPO cmd = commands.get(i);

            if (cmd.getCallbackCode().equals("0000")) {
                if (!orderIds.contains(cmd.getBizId())) {
                    orderIds.add(cmd.getBizId());
                }
            }

            cmd.setStatus(APICommandStatus.SUCCESS);
            MySQLDao.insertOrUpdate(cmd, conn);
        }




        /**
         * 处理订单
         *
         * 将成功的订单设置成已支付状态
         * 对于客户信息进行实名认证
         */
        for (int i = 0; i < orderIds.size(); i++) {
            String orderId = orderIds.get(i);
            OrderPO orderPO = orderDao.loadByLikeOrderId(orderId, conn);

            if (orderPO == null) {

                logDao.save("富友支付","富友支付反馈错误", "orderId="+orderId);
                LogService.console("没有查询到对应的系统订单 orderId="+orderId);
                MyException.newInstance("没有查询到对应的系统订单", "orderId="+orderId).throwException();

            }

            // 已经设置为支付状态的订单，直接跳过
            if (orderPO.getStatus() == OrderStatus.Saled) {
                continue;
            }

            orderId = orderPO.getId();

            try {
                CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(orderPO.getCustomerId(), conn);
                customerPersonalPO.setCustomerCatalogId(CustomerCatalog.Confirmed);
                MySQLDao.insertOrUpdate(customerPersonalPO, conn);

                orderService.saleOrderOnlineFormNow(orderPO, userId, conn);
                orderDetailDao.saveOrderDetail(orderPO, 0, TimeUtils.getNow(), "富友接口验证成功", Config.getDefaultOperatorId(), conn);
                LogService.console("订单状态设置为已支付");

                // 修改客户的实名认证状态（同时身份证和银行卡）
                customerAuthenticationStatusDao.saveAuthenticationStatus(customerPersonalPO.getId(), CustomerAuthenticationStatus.AUTH_TYPE_ACCOUNT, conn);
                logDao.save("富友支付", "富友支付反馈处理-成功处理", "customreName="+orderPO.getCustomerName()+"&orderId="+orderPO.getId(), conn);
                LogService.console("完成反馈处理");
            }
            catch (Exception ex) {
                logDao.save("富友支付", "富友支付反馈错误", "orderId="+orderPO.getId()+"&exception=" + ex.getMessage());

                ex.printStackTrace();
            }
        }

        LogService.console("处理富友订单反馈 结束");
        LogService.console("");
        LogService.console("");
        LogService.console("");

    }



    /**
     * 德恒普惠使用
     * @param order
     * @param conn
     * @return
     * @throws Exception
     */
    public String buildMobilePayOrder(OrderPO order, Connection conn) throws Exception {

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByOrderId(order.getId(), conn);
        if (customerAccountPO == null) {
            MyException.newInstance("无法获得客户账号信息", "customerId=" + order.getCustomerId()).throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(order.getCustomerId(), conn);
        if (customerPersonalPO == null) {
            MyException.newInstance("无法获得客户信息", "customerId=" + order.getCustomerId()).throwException();
        }

        if (StringUtils.isEmpty(customerPersonalPO.getName())) {
            MyException.newInstance("客户姓名不能为空", "customerId=" + order.getCustomerId()).throwException();
        }

        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(order.getCustomerId(), conn);

        if (customerCertificatePO == null) {
            MyException.newInstance("无法获得客户证件信息", "customerId=" + order.getCustomerId()).throwException();
        }


        StringBuffer sbXml = new StringBuffer();

        String MCHNTCD = Config.getSystemConfig("fuiou.merchant.id");
        String TYPE = "10";
        String VERSION = "2.0";
        String LOGOTP = "0";
        String MCHNTORDERID = order.getId();
        String USERID = order.getCustomerId();
        String AMT = String.valueOf(NumberUtils.getMoney2Fen(order.getMoney()));

        String bankCardNumber = AesEncrypt.decrypt(customerAccountPO.getNumber()).replaceAll(" ", "");
        String BANKCARD = bankCardNumber;

        String BACKURL = Config.getSystemConfig("fuiou.pay.mobile.callback.url").replaceAll(" ", "");
        String REURL = Config.getSystemConfig("fuiou.pay.mobile.callback.error.url").replaceAll(" ", "");
        String HOMEURL = Config.getSystemConfig("fuiou.pay.mobile.callback.success.url").replaceAll(" ", "");

        String NAME = customerPersonalPO.getName().replaceAll(" ", "");
        String IDTYPE = "0";

        String IDNO = AesEncrypt.decrypt(customerCertificatePO.getNumber()).replaceAll(" ", "");

        String REM1 = "";
        String REM2 = "";
        String REM3 = "";
        String SIGNTP = "md5";

        String key = Config.getSystemConfig("fuiou.key");

        if (StringUtils.isEmpty(key)) {
            MyException.newInstance("无法获得交易密钥").throwException();
        }

        String SIGN = getMd5(TYPE, VERSION, MCHNTCD, MCHNTORDERID, USERID, AMT, BANKCARD, BACKURL, NAME, IDNO, IDTYPE, LOGOTP, HOMEURL, REURL);


        sbXml.append("<ORDER>");
        sbXml.append(XmlHelper.buildNode("VERSION", VERSION));
        sbXml.append(XmlHelper.buildNode("LOGOTP", LOGOTP));
        sbXml.append(XmlHelper.buildNode("MCHNTCD", MCHNTCD));
        sbXml.append(XmlHelper.buildNode("TYPE", TYPE));
        sbXml.append(XmlHelper.buildNode("MCHNTORDERID", MCHNTORDERID));
        sbXml.append(XmlHelper.buildNode("USERID", USERID));
        sbXml.append(XmlHelper.buildNode("AMT", AMT));
        sbXml.append(XmlHelper.buildNode("BANKCARD", BANKCARD));
        sbXml.append(XmlHelper.buildNode("NAME", NAME));
        sbXml.append(XmlHelper.buildNode("IDTYPE", IDTYPE));
        sbXml.append(XmlHelper.buildNode("IDNO", IDNO));
        sbXml.append(XmlHelper.buildNode("BACKURL", BACKURL));
        sbXml.append(XmlHelper.buildNode("HOMEURL", HOMEURL));
        sbXml.append(XmlHelper.buildNode("REURL", REURL));
        sbXml.append(XmlHelper.buildNode("REM1", REM1));
        sbXml.append(XmlHelper.buildNode("REM2", REM2));
        sbXml.append(XmlHelper.buildNode("REM3", REM3));
        sbXml.append(XmlHelper.buildNode("SIGNTP", SIGNTP));
        sbXml.append(XmlHelper.buildNode("SIGN", SIGN));
        sbXml.append("</ORDER>");

        String xml = DESCoderFUIOU.desEncrypt(sbXml.toString(), key);

        apiCommandDao.buildFuiouMobilePay(order.getId(), sbXml.toString(), conn);

        return xml;

    }



    public String getMd5(String... parameters) throws Exception {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; parameters != null && i < parameters.length; i++) {
            String parameter = parameters[i];
            sb.append(parameter).append("|");
        }

        if (sb.length() > 0) {
            sb.append(Config.getSystemConfig("fuiou.key"));

            String md5 = MD5.MD5Encode(sb.toString());

            return md5;
        }

        return "";
    }

    public String getMd54PC(String... parameters) throws Exception {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; parameters != null && i < parameters.length; i++) {
            String parameter = parameters[i];
            sb.append(parameter).append("|");
        }

        if (sb.length() > 0) {
            sb.append(Config.getSystemConfig("fuiou.pc.key"));

            String md5 = StringUtils.md5(sb.toString());

            return md5;
        }

        return "";
    }

    /**
     * 富友 PC 端在线结果查询（直接后台请求的方式）
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月22日
     *
     * @param orderNum
     * @throws Exception
     */
    public Boolean pcPayQuery(String orderNum, Connection conn) throws Exception {

        // 校验参数
        if(StringUtils.isEmpty(orderNum)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数缺失").throwException();
        }

        // 获取配置数据并校验
        String url = Config.getSystemConfig("fuiou.pc.backend.query.url");
        String merchantCode = Config.getSystemConfig("fuiou.pc.merchant.id");
        String merchantKey = Config.getSystemConfig("fuiou.pc.key");
        if(StringUtils.isEmpty(url) || StringUtils.isEmpty(merchantCode) || StringUtils.isEmpty(merchantKey)) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "配置数据错误").throwException();
        }

        // 构造参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("mchnt_cd", merchantCode);
        params.put("order_id", orderNum);
        String sourceSignature = merchantCode + "|" + orderNum + "|" + merchantKey;
        String md5Signature = FuiouMD5Utils.MD5Encode(sourceSignature);
        params.put("md5", md5Signature);

        // 发起请求并接收响应数据
        String response = HttpUtils.postRequest(url, params);


        /**
         * 保存日志
         */
        logDao.save("富友支付", "查询富友结果返回值", response, conn);



        // 获取响应数据
        XmlHelper helper = new XmlHelper(response);
        String responseCode = helper.getValue("//ap/plain/order_pay_code");
        String responseDescription = helper.getValue("//ap/plain/order_pay_error");
        String responseOrderNum = helper.getValue("//ap/plain/order_id");
        String responseOrderStatus = helper.getValue("//ap/plain/order_st");
        String responseFuiouSequence = helper.getValue("//ap/plain/fy_ssn");    // 富友流水号
        String responseRemark = helper.getValue("//ap/plain/resv1");            // 富友的保留字段
        String responseSignature = helper.getValue("//ap/md5");

        // 校验响应数据
        if(!"0000".equals(responseCode)) {
            System.out.println("富友 PC 端支付结果查询失败，返回码：" + responseCode + "，描述：" + responseDescription);
            MyException.newInstance(ReturnObjectCode.ORDER_PAY_FAILED, "订单查询失败").throwException();
        }
        if(StringUtils.isEmpty(response) || StringUtils.isEmpty(responseCode) || StringUtils.isEmpty(responseOrderNum) || StringUtils.isEmpty(responseOrderStatus) || StringUtils.isEmpty(responseFuiouSequence) || StringUtils.isEmpty(responseSignature)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "回调参数缺失").throwException();
        }

        if(!responseOrderNum.equals(orderNum)) {
            MyException.newInstance(ReturnObjectCode.ORDER_PAY_FAILED, "订单号不能匹配").throwException();
        }

        // 验签
        String responseFields = "<order_pay_code>" + responseCode + "</order_pay_code><order_pay_error>" + responseDescription + "</order_pay_error><order_id>" + responseOrderNum + "</order_id><order_st>" + responseOrderStatus + "</order_st><fy_ssn>" + responseFuiouSequence + "</fy_ssn><resv1>" + responseRemark + "</resv1>" + merchantKey;
        String responseMd5 = FuiouMD5Utils.MD5Encode(responseFields);
        if(!responseMd5.equals(responseSignature)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_SIGN_NOT_CURRECT, "数据签名不正确").throwException();
        }

        if (responseOrderStatus.equals("11")) {
            return true;
        }

        return false;

    }

    /**
     * 生成富友在线支付的前台表单信息
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月9日
     *
     * @param productionName
     * @param money
     * @param bankCode
     * @return
     * @throws Exception
     */
    public FuiouPCPayPO buildPCPayData(String productionName, String orderNum, Integer money, String bankCode) throws Exception {

        FuiouPCPayPO payPO = new FuiouPCPayPO();

        // 商户编号
        payPO.setMerchantCode(Config.getSystemConfig("fuiou.pc.merchant.id"));

        // 订单编号
        payPO.setOrderId(orderNum);

        // 交易金额
        payPO.setOrderAmount(money.toString());

        // 支付类型
        payPO.setOrderPayType(Config.getSystemConfig("fuiou.pay.type.b2c"));

        // 页面跳转 URL
        payPO.setPageNotifyURL(Config.getSystemConfig("fuiou.pc.client.callback.url"));

        // 后台通知 URL
        payPO.setBackNotifyURL(Config.getSystemConfig("fuiou.pc.server.callback.url"));

        // 超时时间
        payPO.setOrderValidTime(Config.getSystemConfig("fuiou.valid.time"));

        // 银行代码
        String allowableBankCodes = Config.getSystemConfig("fuiou.allowable.bankCodes");
        if(StringUtils.contains(bankCode, allowableBankCodes)) {
            payPO.setBankCode("080" + bankCode + "0000");
        } else {
            payPO.setBankCode("0000000000");
        }

        // 商品名称
        payPO.setGoodsName(productionName);

        // 商品展示网址
        payPO.setGoodsDisplayURL("");

        // 备注
        payPO.setRemark("");

        // 版本号
        payPO.setVersion("1.0.1");

        // 签名
        payPO.setMd5(FuiouUtils.generateMd5Sign(payPO));

        return payPO;

    }

    /**
     * 支付成功后销售产品
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月10日
     *
     * @param fuiouOrderId
     * @param payTime
     * @param conn
     * @throws Exception
     */
    public void sellProduction(String fuiouOrderId, String payTime, Connection conn) throws Exception {

        // 判断是否与系统订单保持一致
        OrderPO order = orderDao.getOrderByOrderNo(fuiouOrderId, conn);

        if (order == null) {
            logDao.save("富友支付", "没有找到富友订单对应的系统订单", "fuiouOrderId=" + fuiouOrderId);
            MyException.newInstance("无法找到订单，请检查").throwException();
        }

        // 检查订单状态是否已经是付款状态
        if (order.getStatus() == OrderStatus.Saled) {
            MyException.newInstance("此订单已经付款，请检查").throwException();
        }

        // 设置购买产品订单的起息日
        String payDateTime = TimeUtils.format(payTime, TimeUtils.Format_YYYYMMDD, TimeUtils.Format_YYYY_MM_DD_HH_M_S);
        String valueDate = TimeUtils.getTime(payDateTime, 1, TimeUtils.DATE);
        order.setValueDate(valueDate);

        // 设置订单为已打款
        order.setStatus(OrderStatus.Saled);

        // 订单的操作者，使用预定好的系统用户
        UserPO user = userDao.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);

    }

    /**
     * 从富友 PC 支付的响应中获取客户
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月9日
     *
     * @param orderNum
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerPersonalPO getCustomerFromFuiouCallback(String orderNum, Connection conn) throws Exception {

        // 组织 SQL
        String sql = "select DISTINCT p.* from crm_customerpersonal p, crm_order o where p.state = 0 and o.state = 0 and o.customerId = p.id and o.orderNum = '" + orderNum + "'";

        // 查询数据
        List<CustomerPersonalPO> list = MySQLDao.search(sql, new ArrayList<KVObject>(), CustomerPersonalPO.class, new ArrayList<KVObject>(), conn);
        if(list == null || list.size() != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到对应的客户").throwException();
        }

        // 返回数据
        return list.get(0);

    }

}
