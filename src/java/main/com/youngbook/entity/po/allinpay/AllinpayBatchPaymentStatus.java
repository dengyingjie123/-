package com.youngbook.entity.po.allinpay;

/**
 * Created by Lee on 2015/9/7.
 */
public class AllinpayBatchPaymentStatus {
    // 未开始
    public final static int UNFINISH  = 0;
    // 提现状态：成功
    public final static int SUCCESS = 1;
    // 提现状态：失败
    public final static int FAILED = 2;
    // 已接受，但未知最后状态
    public final static int ACCEPTED = 3; // accepted

    public final static int UNACCEPTED = 4; // accepted
}
