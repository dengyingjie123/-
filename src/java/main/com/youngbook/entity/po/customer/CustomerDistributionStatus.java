package com.youngbook.entity.po.customer;

/**
 * Created by Lee on 2/11/2015.
 */
public class CustomerDistributionStatus {
    /**
     * 等待审核
     */
    public static final int VERIFY_WAITING = 0;

    /**
     * 审核通过
     */
    public static final int VERIFY_PASS = 1;

    /**
     * 审核不通过
     */
    public static final int VERIFY_NOT_PASS = 2;

    public static String getName(int status) {
        switch (status) {
            case 0: return "等待审核";
            case 1: return "已审核";
            case 2: return "未通过";
            default: return "未知状态";
        }
    }
}
