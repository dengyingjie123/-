package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;
@Table(jsonPrefix="customerProductionVO")
public class CustomerProductionVO extends BaseVO {
    private String orderNum;
    private String customerpersonalId;
    private String customerId;
    private String customerName;
    private String productCompositionName;

    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime;

    private int money=Integer.MAX_VALUE;

    private String moneyStatus = "";
    private String moneyStatusName = "";

    private String originSalesman;

    //订单显示预期收益
    private String sumTotalProfitMoney = new String();

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    //所属项目
    private String projectName = new String();

    //名称
    private String productId = new String();
    private String name = new String();

    // 配额(钱)
    private double size = Double.MAX_VALUE;

    public String getMoneyStatusName() {
        return moneyStatusName;
    }

    public void setMoneyStatusName(String moneyStatusName) {
        this.moneyStatusName = moneyStatusName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    // 开始时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();

    // 结束时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String stopTime = new String();

    // 起息日
    @DataAdapter(fieldType = FieldType.DATE)
    private String valueDate = new String();

    // 到期日
    @DataAdapter(fieldType = FieldType.DATE)
    private String expiringDate = new String();

    // 付息日
    @DataAdapter(fieldType = FieldType.DATE)
    private String interestDate = new String();


    private String payTime = new String();

    /**
     *网站显示名称
     */
    private String websiteDisplayName = new String();

    //
    private String projectId = new String();

    // 状态
    private String status =new String();
    private String paymentPlanStatus =new String();

    //预约金额
    private double appointmentMoney =  Double.MAX_VALUE;

    // 期限
    private double process = Double.MAX_VALUE;

    //销售金额
    private double saleMoney =  Double.MAX_VALUE;

    // 产品详情
    private String description = new String();

    private double transferMoney = Double.MAX_VALUE;

    private double paymentMoney = Double.MAX_VALUE;

    private double paymentProfitMoney = Double.MAX_VALUE;

    private double appointmentProcess = Double.MAX_VALUE;

    private double saleProcess = Double.MAX_VALUE;

    private double paymentProcess = Double.MAX_VALUE;

    private String orderId = new String();

    private String interestUnit = new String();

    private String interestCycle = new String();

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPaymentPlanStatus() {
        return paymentPlanStatus;
    }

    public void setPaymentPlanStatus(String paymentPlanStatus) {
        this.paymentPlanStatus = paymentPlanStatus;
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

    public String getProductCompositionName() {return productCompositionName;}
    public void setProductCompositionName(String productCompositionName) {this.productCompositionName = productCompositionName;}

    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public String getMoneyStatus() {
        return moneyStatus;
    }

    public void setMoneyStatus(String moneyStatus) {
        this.moneyStatus = moneyStatus;
    }

    public String getOriginSalesman() {
        return originSalesman;
    }
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public void setOriginSalesman(String originSalesman) {this.originSalesman = originSalesman;}

    public String getValueDate() {return valueDate;}
    public void setValueDate(String valueDate) {this.valueDate = valueDate;}

    public String getInterestDate() {return interestDate;}
    public void setInterestDate(String interestDate) {this.interestDate = interestDate;}

    public double getSaleMoney() {return saleMoney;}
    public void setSaleMoney(double saleMoney) {this.saleMoney = saleMoney;}

    public String getProductId() {return productId;}
    public void setProductId(String productId) {this.productId = productId;}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(String expiringDate) {
        this.expiringDate = expiringDate;
    }

    public String getWebsiteDisplayName() {
        return websiteDisplayName;
    }

    public void setWebsiteDisplayName(String websiteDisplayName) {
        this.websiteDisplayName = websiteDisplayName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAppointmentMoney() {
        return appointmentMoney;
    }

    public void setAppointmentMoney(double appointmentMoney) {
        this.appointmentMoney = appointmentMoney;
    }

    public double getProcess() {
        return process;
    }

    public void setProcess(double process) {
        this.process = process;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(double transferMoney) {
        this.transferMoney = transferMoney;
    }

    public double getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(double paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public double getPaymentProfitMoney() {
        return paymentProfitMoney;
    }

    public void setPaymentProfitMoney(double paymentProfitMoney) {
        this.paymentProfitMoney = paymentProfitMoney;
    }

    public double getAppointmentProcess() {
        return appointmentProcess;
    }

    public void setAppointmentProcess(double appointmentProcess) {
        this.appointmentProcess = appointmentProcess;
    }

    public double getSaleProcess() {
        return saleProcess;
    }

    public void setSaleProcess(double saleProcess) {
        this.saleProcess = saleProcess;
    }

    public double getPaymentProcess() {
        return paymentProcess;
    }

    public void setPaymentProcess(double paymentProcess) {
        this.paymentProcess = paymentProcess;
    }

    public String getCustomerpersonalId() {
        return customerpersonalId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCustomerpersonalId(String customerpersonalId) {
        this.customerpersonalId = customerpersonalId;
    }

    public String getSumTotalProfitMoney() {
        return sumTotalProfitMoney;
    }

    public void setSumTotalProfitMoney(String sumTotalProfitMoney) {
        this.sumTotalProfitMoney = sumTotalProfitMoney;
    }

    public String getInterestUnit() {
        return interestUnit;
    }

    public void setInterestUnit(String interestUnit) {
        this.interestUnit = interestUnit;
    }

    public String getInterestCycle() {
        return interestCycle;
    }

    public void setInterestCycle(String interestCycle) {
        this.interestCycle = interestCycle;
    }
}
