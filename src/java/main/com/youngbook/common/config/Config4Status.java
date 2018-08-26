package com.youngbook.common.config;

public class Config4Status {

    // 充值状态：未充值
    public final static Integer STATUS_CUSTOMER_DEPOSIT_UNFINISH = 0;
    // 充值状态：成功
    public final static Integer STATUS_CUSTOMER_DEPOSIT_SUCCESS = 1;
    // 充值状态：失败
    public final static Integer STATUS_CUSTOMER_DEPOSIT_FAILED = 2;
    // 提现状态：未提现
    public final static Integer STATUS_CUSTOMER_WITHDRAW_UNFINISHs  = 0;
    // 提现状态：成功
    public final static Integer STATUS_CUSTOMER_WITHDRAW_SUCCESSs = 1;
    // 提现状态：失败
    public final static Integer STATUS_CUSTOMER_WITHDRAW_FAILEDs = 2;
    // 提现类型
    public final static Integer STATUS_CUSTOMER_WITHDRAW_TYPE = 1;


    // 兑付状态：未兑付
    public final static Integer STATUS_CUSTOMER_PAYMENT_UNFINISH  = 0;
    // 兑付状态：成功
    public final static Integer STATUS_CUSTOMER_PAYMENT_SUCCESS = 1;
    // 兑付状态：失败
    public final static Integer STATUS_CUSTOMER_PAYMENT_FAILED = 2;

    // 银行卡认证类型
    public final static Integer STATUS_CUSTOMER_BANKCARD_TYPE = 3;
    // 银行卡认证状态：未认证
    public final static Integer STATUS_CUSTOMER_BANKCARD_UNFINISH  = 0;
    // 银行卡认证状态：认证成功
    public final static Integer STATUS_CUSTOMER_BANKCARD_SUCCESS  = 1;
    // 银行卡认证状态：认证失败
    public final static Integer STATUS_CUSTOMER_BANKCARD_FAILED  = 2;
    // 银行卡认证支付状态：未支付
    public final static Integer STATUS_CUSTOMER_BANKCARD_PAYFINISH = 0;
    // 银行卡认证支付状态：支付成功
    public final static Integer STATUS_CUSTOMER_BANKCARD_PAYSUCCESS = 1;
    // 银行卡认证支付状态：支付失败
    public final static Integer STATUS_CUSTOMER_BANKCARD_PAYFAILED = 2;


    public final static String CUSTOMER_MONEY_PAYMENT_UNFINISH  ="未兑付";
    public final static String CUSTOMER_MONEY_LOG_UNFINISH="未提现";
    public final static String CUSTOMER_MONEY_LOG_TYPE_FAILED = "失败";
    public final static String CUSTOMER_MONEY_LOG_TYPE_SUCCESS = "成功";

    // 通联代付修正：初始状态
    public final static Integer STATUS_ALLINPAY_REVISED_NULL = 0;
    // 通联代付修正：未修正
    public final static Integer STATUS_ALLINPAY_REVISED_FAIL = 1;
    // 通联代付修正：已修正
    public final static Integer STATUS_ALLINPAY_REVISED_SUSS = 2;

    // 通联回调明细的查询结果状态
    public final static Integer STATUS_ALLINPAY_CALLBACK_QUERY_UNFINISH = 0;    //  未查询
    public final static Integer STATUS_ALLINPAY_CALLBACK_QUERY_SUCCESS = 1;     // 查询并核对成功
    public final static Integer STATUS_ALLINPAY_CALLBACK_QUERY_FAILED = 2;      // 查询或核对失败


}
