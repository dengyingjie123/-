package com.youngbook.dao.allinpaycircle;

import com.alibaba.fastjson.JSONObject;
import com.emulator.paymentgateway.util.PaymentGatewayService;
import com.emulator.paymentgateway.util.SecurityUtil;
import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.RSAHelper;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.allinpay.AllinpayCircleUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.core.IAPICommandDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IOrderDetailDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.allinpaycircle.AllinpayCircleReceiveRawDataPO;
import com.youngbook.entity.po.allinpaycircle.AllinpayCircleResponseDataPO;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import com.youngbook.entity.po.core.APICommandType;
import com.youngbook.entity.po.production.OrderPO;
import encryption.STSTxData;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.Key;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


@Component("allinpayCircleDao")
public class AllinpayCircleDaoImpl implements IAllinpayCircleDao {

    @Autowired
    IAPICommandDao apiCommandDao;

    @Autowired
    ILogDao logDao;

    @Autowired
    IOrderDao orderDao;

    @Autowired
    IOrderDetailDao orderDetailDao;

    public static void main(String [] args) throws Exception {

        AllinpayCircleDaoImpl allinpayCircleDao = new AllinpayCircleDaoImpl();

        String message = "repMsg=PFNUU1BhY2thZ2U+PEVuY3J5cHRlZFRleHQ+c21GZEtBd1Q1OXNjMHhpTi9vcUpEK05sYm1SWndJN3ZCUWJjU3lxcXZkTDFMZ09iekpNZUQ5NFQyOGlFRVM5SGI5N1VrYklnd1lTb1NUb2o3TjN3MjJ2dDJTcHZXbnlFdzA2Tm0yUlN3NG9OOVU3SEMyZjVaYXlnL3VtVlhOSTFjYzJGdUFSbTU1Tnp5Y1BadjdwQWFtOVI3WmZNdDl4RGNUd3VpNFBadXBOUUM0bk9UOXorTlVUdCtkYTBFM3duSDlMcDNPNUdILytXczltTHVxK3ZuUzQ0THVqcnltNkorQlRxUkY2S2dsZ1lTbzQwNFloamk4dHZTY3RtRkZhNlVFc2NkYWJ0R0FuWEMwaHNtU1pQOHo2WHZtOVdzUFNzT21QQ3hCMTd6eW85ak8rdGZrRXlKWUhCWFZQcDBoM1JCSjdCdS80Q2ZqQ3Q4RzRjTERwN3pnTThZTHhGQjcvNkhQVWhqZldOZnE4a2pNazJESlJuNkZiSStUZnl1UFNycjVOczhhZi9qQjF5NXR4V0FEZm9nQ3g0cUx4S1F1dWZrOUtodHU5Sm5BVUsyV3d6NzY2SEp6NkxYMGo4YkR1Y1UveVRvdXZkbDJjeFpsQzFQVmxsaytmbDdPc2cxdWZaNFp4QUZxeWt6eEsrZWd0R0FYQzdmNUhGMmdZeTdya1ZMSUpZQ0xYT0llZjlYb3MzdGltemI5THZiY0QvVnFZUUZHMjBJdHIzd0Z4VGpVTGhMMUUwdmV2SzJGOHdMVFhwZStMK25OclJ3Ui9xSzZCNXNWL01uWEw5Tmx2bEpqazBqc1YzMlJ3Z0M1U09FVndweC9PY3EwZW13bHZYbE1XTG4zNFZiQzQrYm4rZFlRYnA4UHM5RWZLUWNxaGhzMUJMYTZSZWJTdTJvWjdlZXFveFBUQnhHK09vNEpacUIyZVdudC9rSC8yeGFSTVd2TE1BeHcxOSttekRFSDFwQVAvK2tLY2pscUxhbGVrUVp1NkVhWXpqdUR4NmczUEE1WVlVbG9kdmlrZm16TzYrMjVzNVlUOW5XTjN4Wi82NS9tWEloRmZmRlpPeCtnRFl3M3NTYjVtOWd3Y1ovQklkOGJPRmhwdEhZYUhaWC81Q3Uzc1draTZleEZvQkpyV1ZmaFFHSmxXb3ZxbDNDT1hLNzBWR0pUQWRqckZDRThSNjlRZ0xTOURjRTNNbjFwcW81YW8vL0tGVHR6aVdKcEh4dXpNZS9KbDJ0L0dvaGFOZnFYUXF0ZzQ5UnBYRE1PRmhDVnlEbnplaGxzZkEyajhpbFNOZHhpMEF4NFFyd1JNR05GTXU4QlJoZVJYd1F6SXlkd09JbWNIakxGcVdidmQ2SHJsMmZTbzJqc1dHS3l0MUgxcFpMcUZMQXNVOHpwU3ZzMkNzYURSbVJVK0dzN3o5T2QxT0prS1drclJONVVhbWkwYWE4dmxjN2lhdjN1eUQxNS9OYVhMRExKVnRpUkRDNko5TEM2ck5DMDYyM0JUM05EL3hBaTVyRUVzbXFnUFVzWUtJc2ZIbkVsU0xtSUorbGJCbEQyY3FGSTd1eUhSUGtVYTF2NzIrRmFCV3lRNnFCMEx6QUI5ODFvQmtxZG9rUHBBaXhKQThhU0FWaUNjY1BmUkFOTGR5NkxLWWZpdlozN3lZTFJkTlVNb3Z2c3NBdnRIU1plcVNCRDJwcHpVOFpBTkI2R3lXOXdNU201L0o5VzFqbFkwMzY3aSsxdG5FTU1ka01xQ3hTUmszUWx1aHlnb2hORzNHeDl1UFhuRzJUNkF5S1VLUjc5V0JQNjNSejEwK01YL2pDbzhyMGpvcHpPRFM0Z1BCZDdjRkpIdEdWa1NHSmNVbzF2RFU3Mmd2dTdkWVZ3Z3VWN1dta2VNRXdvVENSUzFuc0pFTjJHZzBDTHdxUC9zRkErWmcrUkR1bVpLU3hKek9GOXlCdkYrb2hZNjVOaEh2RDgxc1M3M2dvVS9VQkhWeHJ4QlUwQW5XWFk0SlZEM05xbldSUERYdzFPOW9MN3UzV01za3o3d3dxUGdJY3ZucTFSMWV4YTNKd3FmeEhPZWZiaTZsMGc0L0FDQys8L0VuY3J5cHRlZFRleHQ+PEtleUluZm8+PFJlY2VpdmVyWDUwOUNlcnRTTj45MDg1MzM3NjYzNDc2NjI3OTM2OTQxNjA3MDc0Mjk0NDcxNzA0PC9SZWNlaXZlclg1MDlDZXJ0U04+PEVuY3J5cHRlZEtleT5GQnpFMlpWZzV1QnRJdGJRYnh2MVNUTE91Mnh4VncycU1tTXhlZG9oTHFrcDdUaUVNTzVFRit0SHNIbVFPUVpKTUQ0VEpWMW9FbnJ2YVllcHlxTlBGQzlrYnlINUtSYmF2amIxUUNHOURxWmhCbGwzWkJIOGNpUzhEQ29YaXJyaFZuay9ZUWtueEJBWUFKSkkxeStDRjJjbVRFaWJ5bjdnZTBETzhRWnRSZE09PC9FbmNyeXB0ZWRLZXk+PC9LZXlJbmZvPjwvU1RTUGFja2FnZT4=";
        message = message.substring(7);

        String decode = allinpayCircleDao.decode(message);

        System.out.println(decode);

    }


    public AllinpayCircleResponseDataPO loadAllinpayCircleResponseDataPOBy_resp_trace_num(String resp_trace_num, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("073C1809");
        dbSQL.addParameter4All("resp_trace_num", resp_trace_num);
        dbSQL.initSQL();

        List<AllinpayCircleResponseDataPO> list = MySQLDao.search(dbSQL, AllinpayCircleResponseDataPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }


    public void dealPayByShare(Connection conn) throws Exception {
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("32DC1809");
        dbSQL.addParameter4All("processing_code", "2085");
        dbSQL.initSQL();

        List<AllinpayCircleResponseDataPO> list = MySQLDao.search(dbSQL, AllinpayCircleResponseDataPO.class, conn);

        for (int i = 0; list != null && i < list.size(); i++) {
            AllinpayCircleResponseDataPO allinpayCircleResponseDataPO = list.get(i);

            String responseDataStatus = "1";

            try {
                dealPayByShareDo(allinpayCircleResponseDataPO);
            }
            catch (Exception e) {
                responseDataStatus = "2";
            }


            allinpayCircleResponseDataPO.setStatus(responseDataStatus);
            MySQLDao.insertOrUpdate(allinpayCircleResponseDataPO, conn);

        }
    }

    private void dealPayByShareDo(AllinpayCircleResponseDataPO allinpayCircleResponseDataPO) throws Exception {
        XmlHelper helper = new XmlHelper(allinpayCircleResponseDataPO.getXml());

        String orderId = helper.getValue("/transaction/response/order_num");


        Connection conn = Config.getConnection();
        try {
            OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);

            String comment = "通联金融生态圈份额支付成功";
            if (allinpayCircleResponseDataPO.getResp_code().equals("0000")) {
                // 成功
                orderPO.setAllinpayCircle_payByShare_status("1");
                orderPO.setAllinpayCircle_payByShare_time(TimeUtils.getNow());
            }
            else {
                // 失败
                comment = "通联金融生态圈份额支付失败";
                orderPO.setAllinpayCircle_payByShare_status("3");
                orderPO.setAllinpayCircle_payByShare_time(TimeUtils.getNow());
            }

            orderDao.insertOrUpdate(orderPO, Config.getDefaultOperatorId(), conn);

            orderDetailDao.saveOrderDetail(orderPO, orderPO.getMoney(), TimeUtils.getNow(), comment, Config.getDefaultOperatorId(), conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }


    }

    public void dealDepositByInstitution(Connection conn) throws Exception {
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("32DC1809");
        dbSQL.addParameter4All("processing_code", "2080");
        dbSQL.initSQL();

        List<AllinpayCircleResponseDataPO> list = MySQLDao.search(dbSQL, AllinpayCircleResponseDataPO.class, conn);

        for (int i = 0; list != null && i < list.size(); i++) {
            AllinpayCircleResponseDataPO allinpayCircleResponseDataPO = list.get(i);

            String responseDataStatus = "1";

            try {
                dealDepositByInstitutionDo(allinpayCircleResponseDataPO);
            }
            catch (Exception e) {
                responseDataStatus = "2";
            }


            allinpayCircleResponseDataPO.setStatus(responseDataStatus);
            MySQLDao.insertOrUpdate(allinpayCircleResponseDataPO, conn);

        }
    }

    private void dealDepositByInstitutionDo(AllinpayCircleResponseDataPO allinpayCircleResponseDataPO) throws Exception {

        Connection conn = Config.getConnection();
        try {
            OrderPO orderPO = orderDao.loadOrderPOBy_allinpayCircle_req_trace_num(allinpayCircleResponseDataPO.getReq_trace_num(), conn);

            if (orderPO == null) {
                MyException.newInstance("无法找到需要处理的订单").throwException();
            }

            String comment = "通联金融生态圈充值成功";
            if (allinpayCircleResponseDataPO.getResp_code().equals("0000")) {
                // 成功
                orderPO.setAllinpayCircle_deposit_status("1");
            }
            else {
                // 失败
                comment = "通联金融生态圈充值失败";
                orderPO.setAllinpayCircle_deposit_status("3");
            }

            orderDao.insertOrUpdate(orderPO, Config.getDefaultOperatorId(), conn);

            orderDetailDao.saveOrderDetail(orderPO, orderPO.getMoney(), TimeUtils.getNow(), comment, Config.getDefaultOperatorId(), conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }


    }

    public void dealRawData(Connection conn) throws Exception {

        DatabaseSQL dbSQL =  DatabaseSQL.newInstance("B3391809");
        dbSQL.initSQL();

        List<AllinpayCircleReceiveRawDataPO> listRawDataPO = MySQLDao.search(dbSQL, AllinpayCircleReceiveRawDataPO.class, conn);

        for (int i = 0; listRawDataPO != null && i < listRawDataPO.size(); i++) {

            AllinpayCircleReceiveRawDataPO allinpayCircleReceiveRawDataPO = listRawDataPO.get(i);

            try {
                String message = allinpayCircleReceiveRawDataPO.getMessage();

                message = message.substring(7);

                String responseXml = decode(message);

                XmlHelper helper = new XmlHelper(responseXml);


                String processing_code = helper.getValue("/transaction/head/processing_code");
                String trans_date = helper.getValue("/transaction/head/trans_date");
                String trans_time = helper.getValue("/transaction/head/trans_time");
                String req_trace_num = helper.getValue("/transaction/response/req_trace_num");
                String resp_code = helper.getValue("/transaction/response/resp_code");
                String resp_msg = helper.getValue("/transaction/response/resp_msg");
                String resp_trace_num = helper.getValue("/transaction/response/resp_trace_num");

                String apiName = AllinpayCircleUtils.getAPIName(processing_code);


                AllinpayCircleResponseDataPO allinpayCircleResponseDataPO = loadAllinpayCircleResponseDataPOBy_resp_trace_num(resp_trace_num, conn);

                if (allinpayCircleResponseDataPO != null) {

                    logDao.save("通联支付金融生态圈", "处理【"+apiName+"】【忽略处理】【"+allinpayCircleResponseDataPO.getResp_code()+"】【"+allinpayCircleResponseDataPO.getResp_msg()+"】", "RawDataId=" + allinpayCircleResponseDataPO.getId(), conn);

                }
                else {
                    allinpayCircleResponseDataPO = new AllinpayCircleResponseDataPO();


                    StringBuffer sbTime = new StringBuffer();
                    sbTime.append(trans_date).append(" ").append(trans_time);
                    sbTime.insert(4, "-").insert(7, "-").insert(13, ":").insert(16, ":");

                    allinpayCircleResponseDataPO.setProcessing_code(processing_code);
                    allinpayCircleResponseDataPO.setTrans_time(sbTime.toString());
                    allinpayCircleResponseDataPO.setReq_trace_num(req_trace_num);
                    allinpayCircleResponseDataPO.setResp_code(resp_code);
                    allinpayCircleResponseDataPO.setResp_msg(resp_msg);
                    allinpayCircleResponseDataPO.setResp_trace_num(resp_trace_num);
                    allinpayCircleResponseDataPO.setXml(responseXml);
                    allinpayCircleResponseDataPO.setStatus("0");

                    MySQLDao.insertOrUpdate(allinpayCircleResponseDataPO, conn);

                    logDao.save("通联支付金融生态圈", "处理【"+apiName+"】【"+allinpayCircleResponseDataPO.getResp_code()+"】【"+allinpayCircleResponseDataPO.getResp_msg()+"】", "RawDataId=" + allinpayCircleResponseDataPO.getId(), conn);

                }


                allinpayCircleReceiveRawDataPO.setStatus("1");

                MySQLDao.insertOrUpdate(allinpayCircleReceiveRawDataPO, conn);

            }
            catch (Exception e) {

                /**
                 * 处理异常
                 *
                 */
                Connection conn2 = Config.getConnection();

                try {

                    allinpayCircleReceiveRawDataPO.setStatus("2");
                    MySQLDao.insertOrUpdate(allinpayCircleReceiveRawDataPO, conn2);

                    logDao.save("通联支付金融生态圈", "处理【"+allinpayCircleReceiveRawDataPO.getId()+"】【失败】【"+e.getMessage()+"】", "", conn2);
                }
                catch (Exception ex) {
                    logDao.save("异常", "AllinpayCircleDao.dealRawData", "RawDataId=" + allinpayCircleReceiveRawDataPO.getId(), conn2);
                }
                finally {
                    Database.close(conn2);
                }

            }
            finally {
            }

        }

    }


    public AllinpayCircleReceiveRawDataPO saveReceiveRawData(AllinpayCircleReceiveRawDataPO allinpayCircleReceiveRawDataPO, Connection conn) throws Exception {

        MySQLDao.insertOrUpdate(allinpayCircleReceiveRawDataPO, conn);

        return allinpayCircleReceiveRawDataPO;
    }


    private String getSignedXml(TransactionPO transactionPO) throws Exception {

        String signCode = STSTxData.signMsgPriKey(transactionPO.toXmlString(), STSTxData.getPrivateKeyB2c());

        transactionPO.setSign_code(signCode);

        String signedXml = transactionPO.toXmlString();

        //对称加密
        Key key = SecurityUtil.generateSymmetricKey();
        byte[] encryptedText = SecurityUtil.encryptSymmetry(signedXml, key);

        X509Certificate cert = SecurityUtil.getCer();
        byte[] encryptedKey = SecurityUtil.encryptSymmetricKey(key, cert.getPublicKey());
        BASE64Encoder encoder = new BASE64Encoder();

        String xml = "<STSPackage>"+
                "<EncryptedText>"+encoder.encode(encryptedText)+"</EncryptedText>"+
                "<KeyInfo>"+
                "<ReceiverX509CertSN>"+cert.getSerialNumber().toString(10)+"</ReceiverX509CertSN>" +
                "<EncryptedKey>"+encoder.encode(encryptedKey)+"</EncryptedKey>" +
                "</KeyInfo>"+
                "</STSPackage>";

//        System.out.println("待发送组装好的数据 min 未编码 === " + xml);

        String signformat = encoder.encode(xml.getBytes("UTF-8"));

//        System.out.println("待发送组装好的数据 min 已编码 === " + signformat);
        return signformat;
    }

    public ReturnObject sendTransaction(TransactionPO transactionPO, Connection conn) throws Exception {


        transactionPO.setTrans_date(TimeUtils.getNowDateYYYYMMDD());
        transactionPO.setTrans_time(TimeUtils.getNowTimeHH24MMSS());

//        System.out.println("待发送加密前的xml ===" + transactionPO.toXmlString());

        String signedXml = getSignedXml(transactionPO);
//        System.out.println("待发送加密后的xml mine ===" + signedXml);


//        String unsignXml = decode(signedXml);
//        System.out.println("解密待发送的xml====" + unsignXml);


        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("msgPlain", signedXml));

        DefaultHttpClient httpClient = new DefaultHttpClient();

        String url = Config.getSystemConfig("allinpay_circle_action_url");

        String bizId = transactionPO.getRequest().getItemString("req_trace_num");

        if (!StringUtils.isEmpty(transactionPO.getBizId())) {
            bizId = transactionPO.getBizId();
        }

        String apiName = AllinpayCircleUtils.getAPIName(transactionPO.getProcessing_code());

        apiCommandDao.saveCommand("通联支付金融生态圈", "通联支付金融生态圈-" + apiName + "-发送", bizId, transactionPO.toXmlString(), APICommandType.Xml, url, "", "");

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
        HttpResponse response = httpClient.execute(httpPost);
//        System.out.println("反馈 ==" + response);


        HttpEntity entity = response.getEntity();

        String resultHtml = EntityUtils.toString(entity, "UTF-8");
//        System.out.println(" resultHtml111  === " + resultHtml);


        /**
         *  自己实现开始
         */
        logDao.save("通联支付正式环境测试", "反馈", resultHtml);




        BASE64Decoder base64 = new BASE64Decoder();
        resultHtml = new String(base64.decodeBuffer(resultHtml), Consts.UTF_8);
//        System.out.println(" resultHtml222  === " + resultHtml);



        String encryptedText = PaymentGatewayService.getNodeValue(resultHtml, "EncryptedText");


        String receiverX509CertSN = PaymentGatewayService.getNodeValue(resultHtml, "ReceiverX509CertSN");

//        System.out.println("receiverX509CertSN == " + receiverX509CertSN);


        String encryptedKey = PaymentGatewayService.getNodeValue(resultHtml, "EncryptedKey");

        Key pfxKey = SecurityUtil.decryptSymmetricKey(base64.decodeBuffer(encryptedKey),
                SecurityUtil.getPrivateKey());

        encryptedText = SecurityUtil.decryptSymmetry(base64.decodeBuffer(encryptedText), pfxKey);

//        System.out.println("加密后的 ==" + encryptedText);

        String signMsg = PaymentGatewayService.getNodeValue(encryptedText, "sign_code");
        String respMsg = encryptedText.substring(0, encryptedText.indexOf("<sign_code>"))
                + encryptedText.substring(encryptedText.indexOf("</sign_code>") + "</sign_code>".length());

//        System.out.println("signMsg ==" + signMsg);
//        System.out.println("respMsg ==" + respMsg);
//        System.out.println("String length ==" + respMsg.trim().length());
//        boolean flag = SecurityUtil.verifySign(signMsg, respMsg.trim());
//        System.out.println("verifySign == " + flag);


        String responseCode = PaymentGatewayService.getNodeValue(encryptedText, "resp_code");

        String responseMessage = Config.getKVString(responseCode, "AllinpayCircleCallbackCode", conn);

        ReturnObject returnObject = new ReturnObject();
        returnObject.setCode(0);

        if (responseCode.equals("0000") || responseCode.equals("1111") || responseCode.equals("1112") || responseCode.equals("4444")) {
            returnObject.setCode(100);
        }

        returnObject.setMessage(responseMessage);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("responseCode", responseCode);
        jsonObject.put("responseMessage", responseMessage);
        jsonObject.put("responseXml", respMsg);

        returnObject.setReturnValue(jsonObject.toJSONString());

        apiCommandDao.saveCommand("通联支付金融生态圈", "通联支付金融生态圈-" + apiName + "-接收", bizId, respMsg, APICommandType.Xml, url, responseCode, responseMessage);

        return returnObject;
    }

    private String decode(String code) throws Exception {

        BASE64Decoder base64 = new BASE64Decoder();
        String xmlDecode = new String(base64.decodeBuffer(code), Consts.UTF_8);
        System.out.println(" xmlDecode  === " + xmlDecode);


        String encryptedText = PaymentGatewayService.getNodeValue(xmlDecode, "EncryptedText");// resultHtml.substring(resultHtml.indexOf("<EncryptedText>")+"<EncryptedText>".length(),resultHtml.indexOf("</EncryptedText>"));


        String receiverX509CertSN = PaymentGatewayService.getNodeValue(xmlDecode, "ReceiverX509CertSN");// resultHtml.substring(resultHtml.indexOf("<ReceiverX509CertSN>")+"<ReceiverX509CertSN>".length(),resultHtml.indexOf("</ReceiverX509CertSN>"));
        System.out.println("receiverX509CertSN == " + receiverX509CertSN);


        String encryptedKey = PaymentGatewayService.getNodeValue(xmlDecode, "EncryptedKey");

        Key pfxKey = SecurityUtil.decryptSymmetricKey(base64.decodeBuffer(encryptedKey),
                SecurityUtil.getPrivateKey());

        encryptedText = SecurityUtil.decryptSymmetry(base64.decodeBuffer(encryptedText), pfxKey);

        System.out.println("加密后的 ==" + encryptedText);

        return encryptedText;
    }

    private String decode2(String code) throws Exception {

        BASE64Decoder base64 = new BASE64Decoder();

        String xmlDecode = new String(base64.decodeBuffer(code), Consts.UTF_8);
        System.out.println(" xmlDecode  === " + xmlDecode);

        XmlHelper xmlHelper = new XmlHelper(xmlDecode);

        String encryptedKeyWithBase64 = xmlHelper.getText("/STSPackage/KeyInfo/EncryptedKey");

//        String encryptedKey = new String(base64.decodeBuffer(encryptedKeyWithBase64));

//        RSAHelper.setAlias_private("allinpay_private");
//        RSAHelper.setJks_pfx("C:\\Users\\leevits\\Desktop\\ttt\\allinpayCircle\\private.jks");
//        RSAHelper.setPassword_private("111111");
//
//        String desKey = new String(RSAHelper.decrypt2(RSAHelper.getPrivateKey(), encryptedKey.getBytes()));
//
//        System.out.println(desKey);
//
//        String encryptedText = xmlHelper.getText("/STSPackage/EncryptedText");
//
//
//
//        System.out.println(encryptedText);
//
//        byte[] decrypt = RSAUtils.decrypt(encryptedText);
//
//        System.out.println(new String(decrypt));

//        String encryptedText = PaymentGatewayService.getNodeValue(xmlDecode, "EncryptedText");// resultHtml.substring(resultHtml.indexOf("<EncryptedText>")+"<EncryptedText>".length(),resultHtml.indexOf("</EncryptedText>"));


//        String receiverX509CertSN = PaymentGatewayService.getNodeValue(xmlDecode, "ReceiverX509CertSN");// resultHtml.substring(resultHtml.indexOf("<ReceiverX509CertSN>")+"<ReceiverX509CertSN>".length(),resultHtml.indexOf("</ReceiverX509CertSN>"));
//        System.out.println("receiverX509CertSN == " + receiverX509CertSN);
//
////
        String encryptedKey = PaymentGatewayService.getNodeValue(xmlDecode, "EncryptedKey");
////
        Key pfxKey = SecurityUtil.decryptSymmetricKey(base64.decodeBuffer(encryptedKey),
                SecurityUtil.getPrivateKey());
////
//        encryptedText = SecurityUtil.decryptSymmetry(base64.decodeBuffer(encryptedText), pfxKey);
        SecurityUtil.decryptSymmetricKey(base64.decodeBuffer(""), null);
        SecurityUtil.decryptSymmetry(base64.decodeBuffer(""), null);
//
//
//        System.out.println("加密后的 ==" + encryptedText);



        return "";
    }
}
