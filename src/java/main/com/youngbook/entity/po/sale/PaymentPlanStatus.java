package com.youngbook.entity.po.sale;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 15-4-9
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public class PaymentPlanStatus {
    /**
     * 未兑付
     */
    public static final int Unpaid = 0;

    /**
     * 已兑付
     */
    public static final int Paid = 1;

    /**
     * 兑付失败
     */
    public static final int PayFailed = 2;

    /**
     * 取消兑付
     */
    public static final int Cancel = 3;

    /**
     * 已转让
     */
    public static final int Transfered = 4;

    /**
     * 兑付已受理，未确认最终状态
     */
    public static final int Accepted = 5;

    /**
     * 等待兑付
     */
    public static final int Waiting4Pay = 6;

    /**
     * 审核中
     */
    public static final int Auditing = 7;

    /**
     * 审核成功
     */
    public static final int AuditSuccess = 8;

    /**
     * 审核失败
     */
    public static final int AuditFailure = 9;

    public static String getStatusName(int status) {
        String name = "";


        switch (status) {
            case Unpaid: name = "未兑付"; break;
            case Paid: name = "已兑付"; break;
            case PayFailed: name = "兑付失败"; break;
            case Cancel: name = "取消兑付"; break;
            case Transfered: name = "已转让"; break;
            case Accepted: name = "已受理"; break;
            case Waiting4Pay: name = "等待银行处理"; break;
            case Auditing: name = "审核中"; break;
            case AuditSuccess: name = "审核成功"; break;
            case AuditFailure: name = "审核失败"; break;
            default: name = "未知状态";break;
        }

        return name;
    }
}
