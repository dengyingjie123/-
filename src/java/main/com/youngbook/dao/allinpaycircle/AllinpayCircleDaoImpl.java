package com.youngbook.dao.allinpaycircle;

import com.alibaba.fastjson.JSONObject;
import com.emulator.paymentgateway.util.PaymentGatewayService;
import com.emulator.paymentgateway.util.SecurityUtil;
import com.mind.platform.system.base.CMData;
import com.mind.platform.system.base.DataRow;
import com.youngbook.action.api.dehecircle.DeheCircleAction;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.allinpay.AllinpayCircleUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.core.IAPICommandDao;
import com.youngbook.entity.po.allinpaycircle.AllinpayCircleReceiveRawDataPO;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import com.youngbook.entity.po.core.APICommandType;
import encryption.DataGramB2cUtil;
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


    public static void main(String [] args) throws Exception {

        AllinpayCircleDaoImpl allinpayCircleDao = new AllinpayCircleDaoImpl();

        String decode = allinpayCircleDao.decode("PFNUU1BhY2thZ2U+PEVuY3J5cHRlZFRleHQ+bEQ5SnBHdTl6a0pqVEJWakFtRmRuVlpsSGtFU1EwWXVVVElQbHRaUHdIN1JxK0ZvUnZPRmw3NDJ3UkRPMTZyYXE2ZGFYUkI5MzMyaXVLMTV5YUdmbTB3dWFaYmV3QnIyb2lyZTQzRUdXV3V1R3JLaC92M1plR3Y3My9DVUNOdUNCWkVwTHhDazJVZjd2d3YvUzFOWEs2UWVhWEhpZ3NLRkcrK0VxcHZkVVdaZlg1MlYzTkpKY1VFd3ZuRS9PTkQrdHdWNUhZVTZKK3V0OUNQWVVKMEZ5VTZIMEJmbzNRc2ZZMmZjcW5YT2NVdVI4bDJnWFRzYjU0L295cy9oZWZKVEpWTXNsV2xib0t1WG9nNlppdmtIQmtTcHN4VWRWVktjVUVJTTNhWEtxZkFmSGwzc0JlMHJTUHpEckZHYWJ3c3B0dlZJYXdMcFVhYVVKenV0VU1mcnoxYi9iMzFQd3Y3QU5lUFNMTGo1QzJ2WmY1SjZ3U3JWa2lUYkN1dTRvNHRSalFSTmtBbjR0UVQyYjFKZmcvcTVXYTR4QjF5K241WmZhbXYyeHBSc28wa0c2citUbmZDWEFPN3ZQMmZ2MGJYZWY4UURWUjgvaUdUb1UyZEhVd1JrbjZCSUp4aS9MVmhQVVQ1Y3Z5YzBsWENqSjhuSFQzRjNMdURXVWU5RkUvckdqS284MFhIaTJ5dVJvQm9sK3hBSHlmUkYvb2txdnM1NVovSFpSUXdWa1orNHpTUUExSFBZV2JRQTdDc0pXV0pJaytlKzd3V1F4Zy8xRDJ1WUViVzZzVy9NZ0N4YitwMmIvaEtjSTBmNm52WitTcU50cVI5dWJyWmdvd2dMcXZWM20vZEVyQ241bGtEQm5jNlJSbE5GeFdkRFFxOGhXa0Y4eE9HNEpyT2ZmR3pad3M2eTVGNDBHWUZHTy9pZGRqMzkyK3drN3ZCamE2UE84ZlVSbkF5L3FMc3d0MmtuRnJ1Sm0rcng1NU9VTURKZ1hFL0hFTW8xY0QxZVdrZ2pGR0JhQVhYSFEzS1p1M2dVRXpKdkFaM0VGV09acldIY083aHEyRmsyZVluYmpTK3BXYmJGWXdUVVREeG9QRzU4SXZYUDhXbVhMU0ZmNHBJMnpJT3ZGTHZsVHJFS0lFNDgrVm94OUxJZ2UzQmkzcUJuOTJPNEtQdWhiY3g2TCthWFZoVStZTW5SMnZCbVZlQUZiT0hEckpnY3Z5aGtsNjg3NmZaelNDVGlBQTU3RmxpRlA5ZFAvMWVSTVRadnpCWEZWdmVzMU9JOHYydmYvY1JTQnl6bjdWY09XR3ZMWWRKb2s4QjUrS1c3VzBsWWdLTWdqeG5HajZpRTFacFZDY3FNbDI1eFRRNzMxQ01NcDlXdW8xMWkrVlJ3dFFLV04zcmRtcytlTjVOeThIMnVRb3VvblZmMHN5VnBjcGtGMThTK1ZUYk1QbndPTnJYK0J0Qjh2ejFGRzlGRjhqdXJwWlNMN2NoRG1qUHpKV2tlY2YrUThXbXcybldwVkNWVGZsZjhwSUcwZ29zcjR4Um5NWjlrSTNhbUd4bjkvSGhlUFVkMmdaTEg1T1laM1lPUU40aHRQZHkyVEdxSWtzZFQraHp0TmErSWRVbzdFSE0zVk9TclIzd21CdWpYdTBPQU1BZk03OGlqM3l2QTJMTE5yTm4rWUhQTjhrOWI5Tk01eFFENUcwRzV4VEhOV2o3djhmNDBjL2wwaW5mS3BQSHQ2WUZpMEE1TjFJZUMzZ2lrRjhlL05HZUwreGErMVRaMWJUUjFXb3p4ZW5YUTdmdmwxMndVMUJlTmdlSTdyR3Ruc1RRbXdDRllDNDU3SFdHMDF1UjNscmJqckpLbkJwOXdNL0F2ZWNIUk0zUTNVWlFHZ3l4d3pOWDdVRkFaWUFpYm5Femk5SHhzMjRUUlJic2dXWW5TdWZTODNWazFqaGVxSEdUSDJnVi9OQktuY0piVFpIeUlOanRXSEhlWFZERVpXbzEyaWpBZjB1TE1WVzFJMzBPZmNEUHdMM25CMFhLZnBheUVxWnBwUkdPMGxPMUltTjVpak9rWDJUaVJpR2NWZ1AybGhXbE48L0VuY3J5cHRlZFRleHQ+PEtleUluZm8+PFJlY2VpdmVyWDUwOUNlcnRTTj45MDg1MzM3NjYzNDc2NjI3OTM2OTQxNjA3MDc0Mjk0NDcxNzA0PC9SZWNlaXZlclg1MDlDZXJ0U04+PEVuY3J5cHRlZEtleT5TZitSVncrcVcrSFhqOVRQOXJ2c3Q3ZTZaRDJSRzJuK1IyMnAraDFCaU1ZS3VZQ2Zla0EvV01mQkRrQXRxZjdJcU5acStPOExGcjl0aUduYkZoMS9jUzBOMVdyNzZsdnk2d25XVDUxTVpOYi9GTUI1Rnd2b0luRGxtT2hhTEFVNHU4cnFPYWhaS0s5OGtDbGx0RWVxMTdNWTVQMEt0ZXUxdSt5Q2RQR3dWT1E9PC9FbmNyeXB0ZWRLZXk+PC9LZXlJbmZvPjwvU1RTUGFja2FnZT4=");

        System.out.println(decode);

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
                "<KeyInfo>"+
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


        String unsignXml = decode(signedXml);
//        System.out.println("解密待发送的xml====" + unsignXml);


        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("msgPlain", signedXml));

        DefaultHttpClient httpClient = new DefaultHttpClient();

        String url = "http://116.228.64.55:28082/AppStsWeb/service/acquireAction.action";

        String bizId = transactionPO.getRequest().getItemString("req_trace_num");

        String apiName = AllinpayCircleUtils.getAPIName(transactionPO.getProcessing_code());

        apiCommandDao.saveCommand("通联支付万小宝", "通联支付万小宝-" + apiName + "-发送", bizId, unsignXml, APICommandType.Xml, url, "", "");

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
        HttpResponse response = httpClient.execute(httpPost);
//        System.out.println("反馈 ==" + response);


        HttpEntity entity = response.getEntity();

        String resultHtml = EntityUtils.toString(entity, "UTF-8");
//        System.out.println(" resultHtml111  === " + resultHtml);

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

        apiCommandDao.saveCommand("通联支付万小宝", "通联支付万小宝-" + apiName + "-接收", bizId, respMsg, APICommandType.Xml, url, responseCode, responseMessage);

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
}
