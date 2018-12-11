package com.youngbook.entity.vo.Sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Table;

/**
* @description: 特定时间段内产品兑付计划VO对象
* @author: 徐明煜
* @createDate: 2018/12/10 16:58
* @version: 1.1
*/
@Table(name = "view_paymentPlan", jsonPrefix = "view_paymentPlan")
public class SpecificPaymentPlanVO {

    //兑付计划编号
    private String paymentPlanId = new String();

    //订单编号
    private String orderId = new String();

    //客户编号
    private String customerId = new String();

    //客户姓名
    private String customerName = new String();

    //产品编号
    private String productionId = new String();

    //产品名称
    @DataAdapter(fieldType = FieldType.DATE)
    private String productionName = new String();

    //应兑付时间
    private String paymentTime = new String();

    //应兑付本金金额
    private double TotalPaymentPrincipalMoney = Double.MAX_VALUE;

    //应兑付收益金额
    private double TotalProfitMoney = Double.MAX_VALUE;

    //实际兑付本金金额
    private double PaiedPrincipalMoney = Double.MAX_VALUE;

    //实际兑付收益金额
    private double PaiedProfitMoney = Double.MAX_VALUE;

    //实际兑付时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String PaiedPaymentTime = new String();

    public String getPaymentPlanId() {
        return paymentPlanId;
    }

    public void setPaymentPlanId(String paymentPlanId) {
        this.paymentPlanId = paymentPlanId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public double getTotalPaymentPrincipalMoney() {
        return TotalPaymentPrincipalMoney;
    }

    public void setTotalPaymentPrincipalMoney(double totalPaymentPrincipalMoney) {
        TotalPaymentPrincipalMoney = totalPaymentPrincipalMoney;
    }

    public double getTotalProfitMoney() {
        return TotalProfitMoney;
    }

    public void setTotalProfitMoney(double totalProfitMoney) {
        TotalProfitMoney = totalProfitMoney;
    }

    public double getPaiedPrincipalMoney() {
        return PaiedPrincipalMoney;
    }

    public void setPaiedPrincipalMoney(double paiedPrincipalMoney) {
        PaiedPrincipalMoney = paiedPrincipalMoney;
    }

    public double getPaiedProfitMoney() {
        return PaiedProfitMoney;
    }

    public void setPaiedProfitMoney(double paiedProfitMoney) {
        PaiedProfitMoney = paiedProfitMoney;
    }

    public String getPaiedPaymentTime() {
        return PaiedPaymentTime;
    }

    public void setPaiedPaymentTime(String paiedPaymentTime) {
        PaiedPaymentTime = paiedPaymentTime;
    }
}
