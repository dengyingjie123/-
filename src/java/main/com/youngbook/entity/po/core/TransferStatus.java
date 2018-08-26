package com.youngbook.entity.po.core;

/**
 * Created by Lee on 2016/2/22.
 */
public class TransferStatus {
    // 未发送
    public static final int Unsend = 0;

    // 已发送
    public static final int Sent = 1;

    // 等待确认
    public static final int Waiting4Confirmation = 2;

    // 确认成功
    public static final int Confirmed4Succssed = 3;

    // 确认失败
    public static final int Confirmed4Failed = 4;

    // 异常
    public static final int Exception = 5;
}
