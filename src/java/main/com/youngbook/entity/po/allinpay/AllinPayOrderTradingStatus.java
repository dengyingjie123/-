package com.youngbook.entity.po.allinpay;

/**
 * Created by Lee on 2015/8/24.
 */
public class AllinPayOrderTradingStatus {
    // 0：支付未受理；
    public static final int Unaccepted = 0;

    // 1：支付已受理；
    public static final int Accepted = 1;

    // 2：已支付受理但不通过；
    public static final int AcceptedButNotPass = 2;

    // 3：支付已受理，但存在数据异常；
    public static final int AcceptedWithDataException = 3;

    // 4：支付已受理，但存在其他异常；
    public static final int AcceptedWithOtherException = 4;
}
