package com.youngbook.entity.po.production;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;


@Table(name = "crm_order", jsonPrefix = "order")
public class OrderPO extends BasePO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;
    // id
    private String id = new String();
    // state
    private int state = Integer.MAX_VALUE;
    // 操作人
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 订单号
    private String orderNum = new String();

    // 客户编号
    private String customerId = new String();

    // 客户名称
    private String customerName = new String();

    // 证件号
    private String customerCertificateNumber = new String();

    // 产品编号
    private String productionId = new String();

    // 产品构成编号
    private String productionCompositionId = new String();

    // 收益率
    private double expectedYield = Double.MAX_VALUE;

    // 购买金额
    private double money = Double.MAX_VALUE;

    // 描述
    private String description = new String();
    // 订单属性状态
    private int status = Integer.MAX_VALUE;
    // 创建时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();


    // 客户属性  0-个人客户  1-机构客户
    private int customerAttribute = Integer.MAX_VALUE;

    // 预约时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String appointmentTime = new String();

    // 打款时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String payTime = new String();

    // 作废时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String cancelTime = new String();

    // 已兑付时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String paybackTime = new String();

    // 推荐人
    private String referralCode = new String();

    // 购买起息日
    @DataAdapter(fieldType = FieldType.DATE)
    private String valueDate = new String();

    // 合同编号
    private String contractNo = new String();


    // 银行编码
    private String bankCode = new String();
    // 返佣等级
    private String commissionLevel = new String();
    // 返佣率
    private double commissionRate = Double.MAX_VALUE;
    // 返佣金额
    private double commissionMoney = Double.MAX_VALUE;


    private int moneyTransferStatus = Integer.MAX_VALUE;

    @DataAdapter(fieldType = FieldType.DATE)
    private String moneyTransferTime = new String();

    /**
     * 支付渠道
     *
     * Date: 2016-05-25 15:48:20
     * Author: leevits
     */
    private int payChannel = Integer.MAX_VALUE;

    // 该订单客户选择的账户ID
    private String accountId = new String();
    // 销售员ID
    private String salemanId = new String();

    private String signature = "";


    /**
     * 财务确认人01
     */
    private String orderConfirmUserId01 = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String orderConfirmUserTime01 = new String();

    /**
     * 财务确认人02
     */
    private String orderConfirmUserId02 = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String orderConfirmUserTime02 = new String();

    /**
     * 财务确认
     * 0：未确认
     * 1：日终扎帐确认
     */
    private String financeMoneyConfirm = "";
    private String financeMoneyConfirmUserId = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String financeMoneyConfirmTime = new String();


    private String allinpayCircle_req_trace_num = "";

    /**
     * 通联金融生态圈-充值状态
     * 0：未充值
     * 1：已充值
     * 2：充值已受理
     * 3：充值失败
     */
    private String allinpayCircle_deposit_status = "";


    /**
     * 通联金融生态圈-份额支付
     * 0：未支付
     * 1：已支付
     * 2：待确认
     * 3：支付失败
     */
    private String allinpayCircle_payByShare_status = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String allinpayCircle_payByShare_time = "";

    public String getAllinpayCircle_payByShare_time() {
        return allinpayCircle_payByShare_time;
    }

    public void setAllinpayCircle_payByShare_time(String allinpayCircle_payByShare_time) {
        this.allinpayCircle_payByShare_time = allinpayCircle_payByShare_time;
    }

    public String getAllinpayCircle_payByShare_status() {
        return allinpayCircle_payByShare_status;
    }

    public void setAllinpayCircle_payByShare_status(String allinpayCircle_payByShare_status) {
        this.allinpayCircle_payByShare_status = allinpayCircle_payByShare_status;
    }

    public String getAllinpayCircle_deposit_status() {
        return allinpayCircle_deposit_status;
    }

    public void setAllinpayCircle_deposit_status(String allinpayCircle_deposit_status) {
        this.allinpayCircle_deposit_status = allinpayCircle_deposit_status;
    }

    public String getAllinpayCircle_req_trace_num() {
        return allinpayCircle_req_trace_num;
    }

    public void setAllinpayCircle_req_trace_num(String allinpayCircle_req_trace_num) {
        this.allinpayCircle_req_trace_num = allinpayCircle_req_trace_num;
    }

    public int getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(int depositStatus) {
        this.depositStatus = depositStatus;
    }

    public String getFinanceMoneyConfirmUserId() {
        return financeMoneyConfirmUserId;
    }

    public void setFinanceMoneyConfirmUserId(String financeMoneyConfirmUserId) {
        this.financeMoneyConfirmUserId = financeMoneyConfirmUserId;
    }

    public String getFinanceMoneyConfirmTime() {
        return financeMoneyConfirmTime;
    }

    public void setFinanceMoneyConfirmTime(String financeMoneyConfirmTime) {
        this.financeMoneyConfirmTime = financeMoneyConfirmTime;
    }

    public String getFinanceMoneyConfirm() {
        return financeMoneyConfirm;
    }

    public void setFinanceMoneyConfirm(String financeMoneyConfirm) {
        this.financeMoneyConfirm = financeMoneyConfirm;
    }

    public String getOrderConfirmUserTime01() {
        return orderConfirmUserTime01;
    }

    public void setOrderConfirmUserTime01(String orderConfirmUserTime01) {
        this.orderConfirmUserTime01 = orderConfirmUserTime01;
    }

    public String getOrderConfirmUserTime02() {
        return orderConfirmUserTime02;
    }

    public void setOrderConfirmUserTime02(String orderConfirmUserTime02) {
        this.orderConfirmUserTime02 = orderConfirmUserTime02;
    }

    public String getOrderConfirmUserId01() {
        return orderConfirmUserId01;
    }

    public void setOrderConfirmUserId01(String orderConfirmUserId01) {
        this.orderConfirmUserId01 = orderConfirmUserId01;
    }

    public String getOrderConfirmUserId02() {
        return orderConfirmUserId02;
    }

    public void setOrderConfirmUserId02(String orderConfirmUserId02) {
        this.orderConfirmUserId02 = orderConfirmUserId02;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getMoneyTransferStatus() {
        return moneyTransferStatus;
    }

    public void setMoneyTransferStatus(int moneyTransferStatus) {
        this.moneyTransferStatus = moneyTransferStatus;
    }

    public String getMoneyTransferTime() {
        return moneyTransferTime;
    }

    public void setMoneyTransferTime(String moneyTransferTime) {
        this.moneyTransferTime = moneyTransferTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCertificateNumber() {
        return customerCertificateNumber;
    }

    public void setCustomerCertificateNumber(String customerCertificateNumber) {
        this.customerCertificateNumber = customerCertificateNumber;
    }

    public double getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(double expectedYield) {
        this.expectedYield = expectedYield;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOperateTime() {
        return operateTime;
    }
    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getProductionCompositionId() {
        return productionCompositionId;
    }
    public void setProductionCompositionId(String productionCompositionId) {this.productionCompositionId = productionCompositionId;}

    public String getProductionId() {
        return productionId;
    }
    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public int getSid() {
        return sid;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCustomerAttribute() {
        return customerAttribute;
    }
    public void setCustomerAttribute(int customerAttribute) {
        this.customerAttribute = customerAttribute;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }
    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }


    public String getPaybackTime() {
        return paybackTime;
    }
    public void setPaybackTime(String paybackTime) {
        this.paybackTime = paybackTime;
    }

    public String getPayTime() {
        return payTime;
    }
    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSalemanId() {
        return salemanId;
    }
    public void setSalemanId(String salemanId) {
        this.salemanId = salemanId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }


    public String getCommissionLevel() {
        return commissionLevel;
    }

    public void setCommissionLevel(String commissionLevel) {
        this.commissionLevel = commissionLevel;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public double getCommissionMoney() {
        return commissionMoney;
    }

    public void setCommissionMoney(double commissionMoney) {
        this.commissionMoney = commissionMoney;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        return "OrderPO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", customerId='" + customerId + '\'' +
                ", productionId='" + productionId + '\'' +
                ", productionCompositionId='" + productionCompositionId + '\'' +
                ", money=" + money +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", customerAttribute=" + customerAttribute +
                ", accountId='" + accountId + '\'' +
                ", salemanId='" + salemanId + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", cancelTime='" + cancelTime + '\'' +
                ", paybackTime='" + paybackTime + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", valueDate='" + valueDate + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerCertificateNumber='" + customerCertificateNumber + '\'' +
                ", expectedYield=" + expectedYield +
                ", commissionLevel='" + commissionLevel + '\'' +
                ", commissionRate=" + commissionRate +
                ", commissionMoney=" + commissionMoney +
                ", payChannel=" + payChannel +
                '}';
    }
}
