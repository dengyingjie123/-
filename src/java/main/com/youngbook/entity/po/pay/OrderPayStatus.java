package com.youngbook.entity.po.pay;

import com.youngbook.entity.po.BasePO;

public class OrderPayStatus extends BasePO {

    //  订单支付状态
    public static final Integer SUCCESS = 1;
    public static final Integer FAILED = 2;
    public static final Integer SUCCESS_BUT_EXCEPTION = 3;

}
