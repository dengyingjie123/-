package com.youngbook.entity.vo.report;

import com.youngbook.annotation.Id;
import com.youngbook.entity.vo.BaseVO;

/**
 * 用于生成兑付计划报表
 * 所有成员都为String类型
 * Created by 曾威恺 on 2016/04/05.
 */
public class PaymentPlanReportVO extends BaseVO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // productionId 产品ID
    private String productionId = new String();

    // compositionId 产品构成ID
    private String compositionId = new String();

    // customerId 客户ID
    private String customerId = new String();

    // customerNumber 客户身份证号
    private String customerNumber = new String();

    // customerName 客户名称
    private String customerName = new String();

    // bank 银行
    private String bank = new String();

    // bankNumber 银行卡号
    private String bankNumber = new String();

    // ProductionName 产品名称
    private String ProductionName = new String();

    // money 认购金额
    private String money = new String();

    // expectedYield 预期收益率
    private String expectedYield = new String();

    // payTime 认购日期
    private String payTime = new String();

    // valueDate 起息日
    private String valueDate = new String();

    // paymentTime 兑付日
    private String paymentTime = new String();

    // principalMoney 兑付本金
    private String principalMoney = new String();

    // profitMoney 兑付利息
    private String profitMoney = new String();

    // paymentStatus 兑付状态
    private String paymentStatus = new String();

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(String compositionId) {
        this.compositionId = compositionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getProductionName() {
        return ProductionName;
    }

    public void setProductionName(String productionName) {
        ProductionName = productionName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(String expectedYield) {
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

    public String getPrincipalMoney() {
        return principalMoney;
    }

    public void setPrincipalMoney(String principalMoney) {
        this.principalMoney = principalMoney;
    }

    public String getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(String profitMoney) {
        this.profitMoney = profitMoney;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
