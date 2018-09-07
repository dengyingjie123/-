package com.youngbook.entity.po.sale.contract;

/**
 * 合同审核状态
 */
public class ContractApplicationStatus {
    /**
     * 未审核
     */
    public static final int Uncheck = 0;

    /**
     * 审核成功
     */
    public static final int Checked = 1;

    /**
     * 审核失败
     */
    public static final int CheckedFailure = 2;
}
