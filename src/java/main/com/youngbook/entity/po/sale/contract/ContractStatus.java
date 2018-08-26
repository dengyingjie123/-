package com.youngbook.entity.po.sale.contract;

/**
 * 合同状态
 */
public class ContractStatus {

    /**
     * 签约
     */

    public static final int Signed = 0;

    /**
     * 未签约
     */
    public static final int Unsigned = 1;

    /**
     * 合同异常
     */
    public static final int Exception = 2;

    public static final int CancelRequest = 3;

    public static final int CancelConfirmed = 4;

    public static String getName(int status) {
        String name = "";
        switch (status) {
            case Signed : name = "已签约";
                break;
            case Unsigned : name = "未签约";
                break;
            case Exception : name = "异常";
                break;
            case CancelRequest : name = "申请作废";
                break;
            case CancelConfirmed : name = "已作废";
                break;
            default: name = "未知";
                break;
        }

        return name;
    }
}
