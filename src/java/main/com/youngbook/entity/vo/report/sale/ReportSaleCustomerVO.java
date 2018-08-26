package com.youngbook.entity.vo.report.sale;

import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Lee on 2015/11/15.
 */
public class ReportSaleCustomerVO extends BaseVO {
    private String customerId = "";
    private String customerIdCardNumber = "";
    private String customerName = "";
    private String productionName = "";
    private double money = Double.MAX_VALUE;
    private double expectedYield = Double.MAX_VALUE;
    private String payTime = "";
    private String valueDate = "";
    private String paymentTime = "";
    private String totalPaymentPrincipalMoney = "";
    private String totalProfitMoney = "";
    private String paymentPlanStatus = "";
    private String referralCode = "";
    private String referralName = "";

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerIdCardNumber() {
        return customerIdCardNumber;
    }

    public void setCustomerIdCardNumber(String customerIdCardNumber) {
        this.customerIdCardNumber = customerIdCardNumber;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(double expectedYield) {
        this.expectedYield = expectedYield;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getTotalPaymentPrincipalMoney() {
        return totalPaymentPrincipalMoney;
    }

    public void setTotalPaymentPrincipalMoney(String totalPaymentPrincipalMoney) {
        this.totalPaymentPrincipalMoney = totalPaymentPrincipalMoney;
    }

    public String getTotalProfitMoney() {
        return totalProfitMoney;
    }

    public void setTotalProfitMoney(String totalProfitMoney) {
        this.totalProfitMoney = totalProfitMoney;
    }

    public String getPaymentPlanStatus() {
        return paymentPlanStatus;
    }

    public void setPaymentPlanStatus(String paymentPlanStatus) {
        this.paymentPlanStatus = paymentPlanStatus;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }


    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }
}
