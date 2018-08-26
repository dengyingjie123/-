package com.youngbook.entity.vo.report;

import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by 张舜清 on 2015/9/8.
 */
@Table(jsonPrefix = "customerDailyPaymentReportVO")
public class CustomerDailyPaymentReportVO extends BaseVO {
    //汇总兑付利息
    private String sumTotalProfitMoney = new String();

    //汇总兑付本金
    private String sumTotalPaymentMoney = new String();

    //汇总兑付收益
    private String sumTotalPaymentPrincipalMoney = new String();

    //客户名称
    private String customerName = new String();

    //兑付日期
    private String paymentTime = new String();

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

    public String getSumTotalProfitMoney() {
        return sumTotalProfitMoney;
    }

    public void setSumTotalProfitMoney(String sumTotalProfitMoney) {
        this.sumTotalProfitMoney = sumTotalProfitMoney;
    }

    public String getSumTotalPaymentMoney() {
        return sumTotalPaymentMoney;
    }

    public void setSumTotalPaymentMoney(String sumTotalPaymentMoney) {
        this.sumTotalPaymentMoney = sumTotalPaymentMoney;
    }

    public String getSumTotalPaymentPrincipalMoney() {
        return sumTotalPaymentPrincipalMoney;
    }

    public void setSumTotalPaymentPrincipalMoney(String sumTotalPaymentPrincipalMoney) {
        this.sumTotalPaymentPrincipalMoney = sumTotalPaymentPrincipalMoney;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getTotalProfitMoney() {
        return totalProfitMoney;
    }

    public void setTotalProfitMoney(String totalProfitMoney) {
        this.totalProfitMoney = totalProfitMoney;
    }

    public String getTotalPaymentMoney() {
        return totalPaymentMoney;
    }

    public void setTotalPaymentMoney(String totalPaymentMoney) {
        this.totalPaymentMoney = totalPaymentMoney;
    }

    public String getTotalPaymentPrincipalMoney() {
        return totalPaymentPrincipalMoney;
    }

    public void setTotalPaymentPrincipalMoney(String totalPaymentPrincipalMoney) {
        this.totalPaymentPrincipalMoney = totalPaymentPrincipalMoney;
    }

    public String getCurrentInstallment() {
        return currentInstallment;
    }

    public void setCurrentInstallment(String currentInstallment) {
        this.currentInstallment = currentInstallment;
    }

    public String getTotalInstallment() {
        return totalInstallment;
    }

    public void setTotalInstallment(String totalInstallment) {
        this.totalInstallment = totalInstallment;
    }

    public String getSurplusPaymentMoney() {
        return surplusPaymentMoney;
    }

    public void setSurplusPaymentMoney(String surplusPaymentMoney) {
        this.surplusPaymentMoney = surplusPaymentMoney;
    }

    public String getSurplusPaymentPrincipalMoney() {
        return surplusPaymentPrincipalMoney;
    }

    public void setSurplusPaymentPrincipalMoney(String surplusPaymentPrincipalMoney) {
        this.surplusPaymentPrincipalMoney = surplusPaymentPrincipalMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
