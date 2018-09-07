package com.youngbook.entity.po.customer;

/**
 * Created by Lee on 2015/11/26.
 */
public class CustomerPersonalValidateIdentityCode {
    public static final int UNKNOW = 0;
    public static final int PASS = 1;

    // 无此账户
    public static final int ERROR_BANK_NUMBER = 2;

    // 账号户名不符
    public static final int ERROR_CUSTOMER_NAME = 3;
}
