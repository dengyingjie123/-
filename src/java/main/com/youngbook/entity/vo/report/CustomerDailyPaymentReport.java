package com.youngbook.entity.vo.report;

import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by 张舜清 on 2015/9/8.
 */
@Table(jsonPrefix = "customerDailyPaymentReportVO")
public class CustomerDailyPaymentReport extends BaseVO {
    //汇总兑付利息
    private String sumTotalProfitMoney = new String();

    //汇总兑付本金
    private String sumTotalPaymentMoney = new String();

    //汇总兑付收益
    private String sumTotalPaymentPrincipalMoney = new String();

    //客户名称
    private String customerName = new String();

    //应兑付利息
    private String totalProfitMoney = new String();

    //应兑付本金
    private String totalPaymentMoney = new String();

    //应兑付收益
    private String totalPaymentPrincipalMoney = new String();

    //当前期数
    private String currentInstallment = new String();

    //总期数
    private String totalInstallment = new String();

    //剩余未兑付本金
    private String surplusPaymentMoney = new String();

    //剩余未兑付收益
    private String surplusPaymentPrincipalMoney = new String();

    //状态
    private String status = new String();
}
