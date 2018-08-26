package com.youngbook.entity.po.sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * 兑付计划 表
 *
 * Created by Administrator on 2015/3/25.
 */
@Table(name = "Core_PaymentPlan", jsonPrefix = "paymentPlan")
public class PaymentPlanPO extends BasePO {

    // 序号
    @Id
    private int sid = Integer.MAX_VALUE;

    // 编号
    private String id = new String();

    //状态
    private int state = Integer.MAX_VALUE;

    //操作人编号
    private String operatorId = new String();
    //操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime= new String();

    //名称
    private String name = new String();

    //兑付类型
    private String type = new String();
    //产品编号
    private String productId = new String();

    //产品构成编号ProductCompositionId
    private String productCompositionId = new String();
    //客户编号
    private String customerId = new String();
    //订单编号
    private String orderId = new String();
    //兑付金额
    private double paymentMoney = Double.MAX_VALUE;
    //兑付日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String paymentTime = new String();

    //兑付总期数
    private int totalInstallment = Integer.MAX_VALUE;
    //当前兑付期数
    private int currentInstallment = Integer.MAX_VALUE;
    //兑付状态 未兑付，1：已兑付；2：兑付失败，3：取消兑付，4：已转让',
    private int status = Integer.MAX_VALUE;
    //应兑付总金额
    private double totalPaymentMoney = Double.MAX_VALUE;
    //应兑付本金总金额
    private double totalPaymentPrincipalMoney = Double.MAX_VALUE;
    //应兑付收益总金额
    private double totalProfitMoney = Double.MAX_VALUE;
    //已兑付本金金额
    private double paiedPrincipalMoney = Double.MAX_VALUE;
    //已兑付收益金额
    private double paiedProfitMoney = Double.MAX_VALUE;
    //备注
    private String comment = new String();

    //以兑付时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String paiedPaymentTime= new String();

    // 审核人编号
    private String confirmorId = new String();
    @DataAdapter(fieldType = FieldType.DATE)
    private String confirmTime = new String();

    // 审核执行人（审核人）
    private String auditExecutorId = new String();

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperateTime() {
        return operateTime;
    }
    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPaiedPaymentTime() {
        return paiedPaymentTime;
    }

    public void setPaiedPaymentTime(String paiedPaymentTime) {
        this.paiedPaymentTime = paiedPaymentTime;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCompositionId() {
        return productCompositionId;
    }
    public void setProductCompositionId(String productCompositionId) {this.productCompositionId = productCompositionId;}

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentTime() {
        return paymentTime;
    }
    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getTotalInstallment() {
        return totalInstallment;
    }
    public void setTotalInstallment(int totalInstallment) {
        this.totalInstallment = totalInstallment;
    }

    public int getCurrentInstallment() {
        return currentInstallment;
    }
    public void setCurrentInstallment(int currentInstallment) {
        this.currentInstallment = currentInstallment;
    }

    public int getStatus() {return status;}
    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotalPaymentMoney() {
        return totalPaymentMoney;
    }
    public void setTotalPaymentMoney(double totalPaymentMoney) {
        this.totalPaymentMoney = totalPaymentMoney;
    }

    public double getTotalPaymentPrincipalMoney() {
        return totalPaymentPrincipalMoney;
    }
    public void setTotalPaymentPrincipalMoney(double totalPaymentPrincipalMoney) {this.totalPaymentPrincipalMoney = totalPaymentPrincipalMoney;}

    public double getTotalProfitMoney() {
        return totalProfitMoney;
    }
    public void setTotalProfitMoney(double totalProfitMoney) {
        this.totalProfitMoney = totalProfitMoney;
    }

    public double getPaiedPrincipalMoney() {
        return paiedPrincipalMoney;
    }
    public void setPaiedPrincipalMoney(double paiedPrincipalMoney) {
        this.paiedPrincipalMoney = paiedPrincipalMoney;
    }

    public double getPaiedProfitMoney() {
        return paiedProfitMoney;
    }
    public void setPaiedProfitMoney(double paiedProfitMoney) {
        this.paiedProfitMoney = paiedProfitMoney;
    }

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

    public double getPaymentMoney() {
        return paymentMoney;
    }
    public void setPaymentMoney(double paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getConfirmorId() {
        return confirmorId;
    }

    public void setConfirmorId(String confirmorId) {
        this.confirmorId = confirmorId;
    }

    public String getAuditExecutorId() {
        return auditExecutorId;
    }

    public void setAuditExecutorId(String auditExecutorId) {
        this.auditExecutorId = auditExecutorId;
    }
}
