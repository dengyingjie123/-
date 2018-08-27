package com.youngbook.dao.allinpaycircle;

import com.alibaba.fastjson.JSONObject;
import com.emulator.paymentgateway.util.PaymentGatewayService;
import com.emulator.paymentgateway.util.SecurityUtil;
import com.mind.platform.system.base.CMData;
import com.mind.platform.system.base.DataRow;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.allinpay.AllinpayCircleUtils;
import com.youngbook.dao.core.IAPICommandDao;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import com.youngbook.entity.po.core.APICommandType;
import encryption.DataGramB2cUtil;
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

import java.security.Key;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leevits on 7/19/2018.
 */
@Component("allinpayCircleDao")
public class AllinpayCircleDaoImpl implements IAllinpayCircleDao {

    @Autowired
    IAPICommandDao apiCommandDao;

    public ReturnObject sendTransaction(TransactionPO transactionPO, Connection conn) throws Exception {


        transactionPO.setTrans_date(TimeUtils.getNowDateYYYYMMDD());
        transactionPO.setTrans_time(TimeUtils.getNowTimeHH24MMSS());


        DataRow transactionDataRow = new CMData();
        DataRow headListRow = new CMData(), requestListRow = new CMData();

        headListRow.put("processing_code", transactionPO.getProcessing_code());
        headListRow.put("inst_id", transactionPO.getInst_id());
        headListRow.put("trans_date", transactionPO.getTrans_date());
        headListRow.put("trans_time", transactionPO.getTrans_time());
        headListRow.put("ver_num", transactionPO.getVer_num());

        for (int i = 0; i < transactionPO.getRequest().size(); i++) {
            String key = transactionPO.getRequest().get(i).getKeyStringValue();
            String value = transactionPO.getRequest().get(i).getValueStringValue();
            requestListRow.put(key, value);
        }


        transactionDataRow.put("head", headListRow);
        transactionDataRow.put("request", requestListRow);


        String xml = DataGramB2cUtil.createRequestJrQianyueCryptoMsg(transactionDataRow);

        System.out.println("待发送加密前的xml ===" + transactionPO.toXmlString());
        System.out.println("待发送加密后的xml ===" + xml);

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("msgPlain", xml));

        DefaultHttpClient httpClient = new DefaultHttpClient();

        String url = "http://116.228.64.55:28082/AppStsWeb/service/acquireAction.action";
        // XmlHelper helper = new XmlHelper();

        String bizId = transactionPO.getRequest().getItemString("req_trace_num");

        String apiName = AllinpayCircleUtils.getAPIName(transactionPO.getProcessing_code());

        apiCommandDao.saveCommand("通联支付万小宝", "通联支付万小宝-" + apiName + "-发送", bizId, transactionPO.toXmlString(), APICommandType.Xml, url, "", "");




        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println("反馈 ==" + response);


        HttpEntity entity = response.getEntity();

        String resultHtml = EntityUtils.toString(entity, "UTF-8");
        System.out.println(" resultHtml111  === " + resultHtml);

        BASE64Decoder base64 = new BASE64Decoder();
        resultHtml = new String(base64.decodeBuffer(resultHtml), Consts.UTF_8);
        System.out.println(" resultHtml222  === " + resultHtml);



        String encryptedText = PaymentGatewayService.getNodeValue(resultHtml, "EncryptedText");// resultHtml.substring(resultHtml.indexOf("<EncryptedText>")+"<EncryptedText>".length(),resultHtml.indexOf("</EncryptedText>"));


        String receiverX509CertSN = PaymentGatewayService.getNodeValue(resultHtml, "ReceiverX509CertSN");// resultHtml.substring(resultHtml.indexOf("<ReceiverX509CertSN>")+"<ReceiverX509CertSN>".length(),resultHtml.indexOf("</ReceiverX509CertSN>"));
        System.out.println("receiverX509CertSN == " + receiverX509CertSN);


        String encryptedKey = PaymentGatewayService.getNodeValue(resultHtml, "EncryptedKey");

        Key pfxKey = SecurityUtil.decryptSymmetricKey(base64.decodeBuffer(encryptedKey),
                SecurityUtil.getPrivateKey());

        encryptedText = SecurityUtil.decryptSymmetry(base64.decodeBuffer(encryptedText), pfxKey);

        System.out.println("加密后的 ==" + encryptedText);

        String signMsg = PaymentGatewayService.getNodeValue(encryptedText, "sign_code");// encryptedText.substring(encryptedText.indexOf("<sign_code>")+"<sign_code>".length(),encryptedText.indexOf("</sign_code>"));
        String respMsg = encryptedText.substring(0, encryptedText.indexOf("<sign_code>"))
                + encryptedText.substring(encryptedText.indexOf("</sign_code>") + "</sign_code>".length());

        System.out.println("signMsg ==" + signMsg);
        System.out.println("respMsg ==" + respMsg);
        System.out.println("String length ==" + respMsg.trim().length());
        boolean flag = SecurityUtil.verifySign(signMsg, respMsg.trim());
        System.out.println("verifySign == " + flag);


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

}
