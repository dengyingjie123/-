package com.youngbook.entity.po.production;

/**
 * Created by Lee on 2016/5/25.
 */
public class OrderPayChannel {

    /**
     * 通联支付
     *
     * Date: 2016-05-25 15:52:20
     * Author: leevits
     */
    public static final int ALLINPAY = 1;


    /**
     * 富友支付
     *
     * Date: 2016-05-25 15:52:31
     * Author: leevits
     */
    public static final int FUYOU = 2;


    /**
     * 线下支付
     */
    public static final int BANK_TRANSFER = 3;


    public static final int UNKNOWN = -1;


    public static String getName(int channel) {

        String name = "-";
        switch (channel) {
            case ALLINPAY : name = "通联支付"; break;
            case FUYOU : name = "富友支付"; break;
            case BANK_TRANSFER : name = "线下银行转账"; break;
            case UNKNOWN : name = "未知"; break;
            default:  name = "-"; break;
        }

        return name;
    }

}
