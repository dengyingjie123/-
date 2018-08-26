package com.youngbook.entity.vo.Sale;

import com.youngbook.entity.vo.BaseVO;

/**
 * /**
 * 创建人：zhouhaihong
 * 创建时间：2015/11/11
 * 描述： 用来作为Excel 数据封装的类
 * PaymentPlanTempVO:
 */
public class PaymentPlanTempVO extends BaseVO {
    //客户编号
    private String customerPersonalNumber = new String();
    //卡号
    private String accountNumber = new String();
    //客户名称
    private String customerName = new String();
    //产品名称
    private String productionName = new String();
    //认购金额
    private double oMoney = Double.MAX_VALUE;
    //兑付本金
    private String totalPaymentMoney1 = new String();
    //总期数
    private String totalInstallment = new String();
    //当前期数
    private String currentInstallment = new String();
    //认购日期
    private String oCreateTime = new String();
    //起息日
    private String oValueDate = new String();
    //兑付日期
    private String paymentTime = new String();
    //预期收益率（年化）
    private String ExpectedYield = new String();
    //兑付利息
    private String totalProfitMoney1 = new String();
    //兑付本息
    private String totalPaymentMoney2 = new String();

    //剩余未兑付本金
    private String totalPaymentPrincipalMoney1 = new String();


    //剩余未兑付收益
    private String totalPaymentMoney3 = new String();
    //兑付状态
    private String planSTATUS = new String();

    //认购金额总计
    private String o1Money = new String();

    //兑付本金总计
    private String totalPaymentPrincipalMoney2 = new String();

    // 兑付利息总计
    private String totalProfitMoney2 = new String();

    // 兑付本息总计
    private String totalPaymentMoney4 = new String();
    //开户行
    private String bank = new String();
    //银行代码
    private String BankCode = new String();

    public String getCustomerPersonalNumber() {
        return customerPersonalNumber;
    }

    public void setCustomerPersonalNumber(String customerPersonalNumber) {
        this.customerPersonalNumber = customerPersonalNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public double getoMoney() {
        return oMoney;
    }

    public void setoMoney(double oMoney) {
        this.oMoney = oMoney;
    }

    public String getTotalPaymentMoney1() {
        return totalPaymentMoney1;
    }

    public void setTotalPaymentMoney1(String totalPaymentMoney1) {
        this.totalPaymentMoney1 = totalPaymentMoney1;
    }

    public String getTotalInstallment() {
        return totalInstallment;
    }

    public void setTotalInstallment(String totalInstallment) {
        this.totalInstallment = totalInstallment;
    }

    public String getCurrentInstallment() {
        return currentInstallment;
    }

    public void setCurrentInstallment(String currentInstallment) {
        this.currentInstallment = currentInstallment;
    }

    public String getoCreateTime() {
        return oCreateTime;
    }

    public void setoCreateTime(String oCreateTime) {
        this.oCreateTime = oCreateTime;
    }

    public String getoValueDate() {
        return oValueDate;
    }

    public void setoValueDate(String oValueDate) {
        this.oValueDate = oValueDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getExpectedYield() {
        return ExpectedYield;
    }

    public void setExpectedYield(String expectedYield) {
        ExpectedYield = expectedYield;
    }

    public String getTotalProfitMoney1() {
        return totalProfitMoney1;
    }

    public void setTotalProfitMoney1(String totalProfitMoney1) {
        this.totalProfitMoney1 = totalProfitMoney1;
    }

    public String getTotalPaymentMoney2() {
        return totalPaymentMoney2;
    }

    public void setTotalPaymentMoney2(String totalPaymentMoney2) {
        this.totalPaymentMoney2 = totalPaymentMoney2;
    }

    public String getTotalPaymentPrincipalMoney1() {
        return totalPaymentPrincipalMoney1;
    }

    public void setTotalPaymentPrincipalMoney1(String totalPaymentPrincipalMoney1) {
        this.totalPaymentPrincipalMoney1 = totalPaymentPrincipalMoney1;
    }

    public String getTotalPaymentMoney3() {
        return totalPaymentMoney3;
    }

    public void setTotalPaymentMoney3(String totalPaymentMoney3) {
        this.totalPaymentMoney3 = totalPaymentMoney3;
    }

    public String getPlanSTATUS() {
        return planSTATUS;
    }

    public void setPlanSTATUS(String planSTATUS) {
        this.planSTATUS = planSTATUS;
    }

    public String getO1Money() {
        return o1Money;
    }

    public void setO1Money(String o1Money) {
        this.o1Money = o1Money;
    }

    public String getTotalPaymentPrincipalMoney2() {
        return totalPaymentPrincipalMoney2;
    }

    public void setTotalPaymentPrincipalMoney2(String totalPaymentPrincipalMoney2) {
        this.totalPaymentPrincipalMoney2 = totalPaymentPrincipalMoney2;
    }

    public String getTotalProfitMoney2() {
        return totalProfitMoney2;
    }

    public void setTotalProfitMoney2(String totalProfitMoney2) {
        this.totalProfitMoney2 = totalProfitMoney2;
    }

    public String getTotalPaymentMoney4() {
        return totalPaymentMoney4;
    }

    public void setTotalPaymentMoney4(String totalPaymentMoney4) {
        this.totalPaymentMoney4 = totalPaymentMoney4;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }
}
