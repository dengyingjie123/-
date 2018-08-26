package com.youngbook.common.utils.allinpay;

import com.youngbook.common.config.Config;
import com.youngbook.entity.po.allinpay.AllinPayOrderCallBackPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderPO;

import java.text.SimpleDateFormat;

/**
 * Created by 张舜清 on 2015/8/4.
 */
public class AllinPayUtils {

    public static String getBASE64(String s) {
        if (s == null) return null;
        return (new sun.misc.BASE64Encoder()).encode( s.getBytes() );
    }

    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {//汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }


    /**
     * 创建人：张舜清
     * 时间：8/4/2015
     * 内容：生成通联支付订单号
     *
     * @return
     * @throws Exception
     */
    public static String getAllinPayOrderNumber() throws Exception {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sDateFormat.format(new java.util.Date());
        return "NO" + time;
    }

    /**
     * 创建人：张舜清
     * 时间：8/4/2015
     * 内容：根据通联支付的需求生成指定格式的日期
     *
     * @return
     * @throws Exception
     */
    public static String getOrderDateTime() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = simpleDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * 创建人：张舜清
     * 时间：8/4/2015
     * 内容：我们自己组装报文
     *
     * @param allinPayOrder
     * @return
     * @throws Exception
     */
    public static String getSignSrc(AllinPayOrderPO allinPayOrder) throws Exception {
        StringBuffer str = new StringBuffer();
        // 组装的报文要按照通联的手册顺序
        str.append("inputCharset=" + allinPayOrder.getInputCharset() + "&");
        str.append("pickupUrl=" + allinPayOrder.getPickupUrl() + "&");
        str.append("receiveUrl=" + allinPayOrder.getReceiveUrl() + "&");
        str.append("version=" + allinPayOrder.getVersion() + "&");
        str.append("language=" + allinPayOrder.getLanguage() + "&");
        str.append("signType=" + allinPayOrder.getSignType() + "&");
        str.append("merchantId=" + allinPayOrder.getMerchantId() + "&");
        str.append("payerName=" + allinPayOrder.getPayerName() + "&");
        str.append("orderNo=" + allinPayOrder.getOrderNo() + "&");
        str.append("orderAmount=" + allinPayOrder.getOrderAmount() + "&");
        str.append("orderCurrency=" + allinPayOrder.getOrderCurrency() + "&");
        str.append("orderDatetime=" + allinPayOrder.getOrderDatetime() + "&");
        str.append("productName=" + allinPayOrder.getProductName() + "&");
        str.append("payType=" + allinPayOrder.getPayType());
        return str.toString();
    }

    /**
     * 创建人：邓超
     * 时间：2015-8-31
     * 内容：通过通联订单组装一串报文
     * 修改人：张舜清
     * 时间：2015年9月1日14:13:19
     * 内容：增加了两个字段的获取
     *
     * 注意：通联组装报文所有非空字段都必须要串起来，之前的BUG就是少了language和payType，最后的signMsg不要加上
     *
     * @param allinPayOrderCallBackPO
     * @return
     * @throws Exception
     */
    public static String getSignSrc(AllinPayOrderCallBackPO allinPayOrderCallBackPO) throws Exception {
        StringBuffer str = new StringBuffer();
        // 组装的报文要按照通联的手册顺序
        str.append("merchantId=" + allinPayOrderCallBackPO.getMerchantId() + "&");
        str.append("version=" + allinPayOrderCallBackPO.getVersion() + "&");
        // 少了language
        str.append("language=" + allinPayOrderCallBackPO.getLanguage() + "&");
        str.append("signType=" + allinPayOrderCallBackPO.getSignType() + "&");
        // 少了payType
        str.append("payType=" + allinPayOrderCallBackPO.getPayType() + "&");
        str.append("paymentOrderId=" + allinPayOrderCallBackPO.getPaymentOrderId() + "&");
        str.append("orderNo=" + allinPayOrderCallBackPO.getOrderNo() + "&");
        str.append("orderDatetime=" + allinPayOrderCallBackPO.getOrderDatetime() + "&");
        str.append("orderAmount=" + allinPayOrderCallBackPO.getOrderAmount() + "&");
        str.append("payDatetime=" + allinPayOrderCallBackPO.getPayDatetime() + "&");
        str.append("payAmount=" + allinPayOrderCallBackPO.getPayAmount() + "&");
        str.append("payResult=" + allinPayOrderCallBackPO.getPayResult() + "&");
        str.append("returnDatetime=" + allinPayOrderCallBackPO.getReturnDatetime());
        return str.toString();
    }

    /**
     * 创建人：邓超
     * 时间：2015-8-31
     * 内容：通过证书验证通联返回的结果
     *
     * @param allinPayOrderCallBackPO
     * @return
     * @throws Exception
     */
    public static Boolean getSignWithCert(AllinPayOrderCallBackPO allinPayOrderCallBackPO) throws Exception {
        com.allinpay.ets.client.PaymentResult paymentResult = new com.allinpay.ets.client.PaymentResult();
        paymentResult.setMerchantId(allinPayOrderCallBackPO.getMerchantId());
        paymentResult.setVersion(allinPayOrderCallBackPO.getVersion());
        paymentResult.setLanguage(String.valueOf(allinPayOrderCallBackPO.getLanguage()));
        paymentResult.setSignType(String.valueOf(allinPayOrderCallBackPO.getSignType()));
        paymentResult.setPayType(allinPayOrderCallBackPO.getPayType());
        paymentResult.setIssuerId(allinPayOrderCallBackPO.getIssuerId());
        paymentResult.setPaymentOrderId(allinPayOrderCallBackPO.getPaymentOrderId());
        paymentResult.setOrderNo(allinPayOrderCallBackPO.getOrderNo());
        paymentResult.setOrderDatetime(allinPayOrderCallBackPO.getOrderDatetime());
        paymentResult.setOrderAmount(String.valueOf(allinPayOrderCallBackPO.getOrderAmount()));
        paymentResult.setPayDatetime(allinPayOrderCallBackPO.getPayDatetime());
        paymentResult.setPayAmount(String.valueOf(allinPayOrderCallBackPO.getPayAmount()));
        paymentResult.setExt1(allinPayOrderCallBackPO.getExt1());
        paymentResult.setExt2(allinPayOrderCallBackPO.getExt2());
        paymentResult.setPayResult(String.valueOf(allinPayOrderCallBackPO.getPayResult()));
        paymentResult.setErrorCode(allinPayOrderCallBackPO.getErrorCode());
        paymentResult.setReturnDatetime(allinPayOrderCallBackPO.getReturnDatetime());
        //signMsg为服务器端返回的签名值。
        paymentResult.setSignMsg(allinPayOrderCallBackPO.getSignMsg());
        //signType为"1"时，必须设置证书路径。
        paymentResult.setCertPath(Config.getSystemConfig("bank.pay.allinpay.ertPath"));//读取通联公钥证书存放路径
        //验证签名：返回true代表验签成功；否则验签失败。
        boolean verifyResult = paymentResult.verify();
        //验签成功，还需要判断订单状态，为"1"表示支付成功。
        return (verifyResult && paymentResult.getPayResult().equals("1"));
    }

}
