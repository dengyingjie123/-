package com.youngbook.entity.po.fdcg;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Lee on 3/15/2018.
 */
@Table(name = "fdcg_customer_account", jsonPrefix = "customerAccount")
public class FdcgCustomerAccountPO extends BasePO {

    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;


    private String id = "";
    //登录用户状态
    private int state = Integer.MAX_VALUE;
    //操作者Id
    private String operatorId = "";
    private String operatorName = "";


    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = "";

    private String accountNo = "";
    private String bank = "";
    private String bankAccountNo = "";
    private String bankCode = "";
    private String status = "";
    private String deductMoney = "";
    private String extMark = "";
    private String merchantNo = "";
    private String notifyUrl = "";
    private String returnUrl = "";
    private String orderDate = "";
    private String orderNo = "";
    private String bindStatus = "";

    private String userName = "";
    private String crmCustomerPersonalId = "";

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeductMoney() {
        return deductMoney;
    }

    public void setDeductMoney(String deductMoney) {
        this.deductMoney = deductMoney;
    }

    public String getExtMark() {
        return extMark;
    }

    public void setExtMark(String extMark) {
        this.extMark = extMark;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCrmCustomerPersonalId() {
        return crmCustomerPersonalId;
    }

    public void setCrmCustomerPersonalId(String crmCustomerPersonalId) {
        this.crmCustomerPersonalId = crmCustomerPersonalId;
    }
}
