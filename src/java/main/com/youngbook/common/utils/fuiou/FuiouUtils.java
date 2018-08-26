package com.youngbook.common.utils.fuiou;

import com.youngbook.common.config.Config;
import com.youngbook.entity.po.fuiou.FuiouPCPayPO;

public class FuiouUtils {

    /**
     * 生成富友 PC 在线支付的签名
     *
     * 说明：通过指定的字段组成字符串，再 MD5Utils 一次
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月9日
     *
     * @param payPO
     * @return
     * @throws Exception
     */
    public static String generateMd5Sign(FuiouPCPayPO payPO) throws Exception {

        // 获取商户密钥
        String merchantPwd = Config.getSystemConfig("fuiou.pc.key");

        String rawString =
            payPO.getMerchantCode() + "|" +
            payPO.getOrderId() + "|" +
            payPO.getOrderAmount() + "|" +
            payPO.getOrderPayType() + "|" +
            payPO.getPageNotifyURL() + "|" +
            payPO.getBackNotifyURL() + "|" +
            payPO.getOrderValidTime() + "|" +
            payPO.getBankCode() + "|" +
            payPO.getGoodsName() + "|" +
            payPO.getGoodsDisplayURL() + "|" +
            payPO.getRemark() + "|" +
            payPO.getVersion() + "|" +
            merchantPwd;

        String md5 = FuiouMD5Utils.MD5Encode(rawString).toLowerCase();

        return md5;

    }

}
