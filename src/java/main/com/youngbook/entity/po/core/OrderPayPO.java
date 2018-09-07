package com.youngbook.entity.po.core;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

@Table(name = "Core_OrderPay", jsonPrefix = "orderPay")
public class OrderPayPO extends BasePO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 支付接口流水编号
    private String APIId = new String();

    // 支付接口
    private String APIName = new String();

    // 订单编号
    private String orderId = new String();

    // 商户编号
    private String merchantId = new String();

    // 客户编号
    private String customerId = new String();

    // 客户名称
    private String customerName = new String();

    // 商品编号
    private String productionId = new String();

    // 商品名称
    private String productionName = new String();

    // 金额
    private double money = Double.MAX_VALUE;

    // 支付类型
    private int payType = Integer.MAX_VALUE;

    // 银行账号
    private String bankAccount = new String();

    // 银行编号
    private String bankCode = new String();

    // 银行名称
    private String bankName = new String();

    // 证件类型
    private String customerCertificateType = new String();

    // 证件号
    private String customerCertificateNo = new String();

    // 手机号
    private String mobile = new String();

    // 指令编号
    private String APICommandId = new String();

    // 状态
    private int status = Integer.MAX_VALUE;

    // 回调网页URL
    private String callbackPageURL = new String();

    // 回调服务URL
    private String callbackServiceURL = new String();

    // 版本号
    private String version = new String();

    // 指令发送次数
    private int APICommandSendTimes = Integer.MAX_VALUE;

    // 来源
    private String source = new String();

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

    public String getAPIId() {
        return APIId;
    }

    public void setAPIId(String APIId) {
        this.APIId = APIId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCustomerCertificateType() {
        return customerCertificateType;
    }

    public void setCustomerCertificateType(String customerCertificateType) {
        this.customerCertificateType = customerCertificateType;
    }

    public String getCustomerCertificateNo() {
        return customerCertificateNo;
    }

    public void setCustomerCertificateNo(String customerCertificateNo) {
        this.customerCertificateNo = customerCertificateNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAPIName() {
        return APIName;
    }

    public void setAPIName(String APIName) {
        this.APIName = APIName;
    }

    public String getAPICommandId() {
        return APICommandId;
    }

    public void setAPICommandId(String APICommandId) {
        this.APICommandId = APICommandId;
    }

    public int getAPICommandSendTimes() {
        return APICommandSendTimes;
    }

    public void setAPICommandSendTimes(int APICommandSendTimes) {
        this.APICommandSendTimes = APICommandSendTimes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCallbackPageURL() {
        return callbackPageURL;
    }

    public void setCallbackPageURL(String callbackPageURL) {
        this.callbackPageURL = callbackPageURL;
    }

    public String getCallbackServiceURL() {
        return callbackServiceURL;
    }

    public void setCallbackServiceURL(String callbackServiceURL) {
        this.callbackServiceURL = callbackServiceURL;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
