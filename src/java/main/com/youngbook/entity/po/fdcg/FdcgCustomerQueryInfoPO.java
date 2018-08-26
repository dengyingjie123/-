package com.youngbook.entity.po.fdcg;

import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Lee on 3/14/2018.
 */
@Table(name = "fdcg_customer_query_info", jsonPrefix = "customerQueryInfo")
public class FdcgCustomerQueryInfoPO extends BasePO {


    private String accountNo = "";
    private String authorization = "";
    private String balance = "";
    private String withdrawBalance = "";
    private String extMark = "";
    private String freezeBalance = "";
    private String identityCode = "";
    private String merchantNo = "";
    private String realName = "";
    private String orderDate = "";
    private String orderNo = "";
    private String status = "";


    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getWithdrawBalance() {
        return withdrawBalance;
    }

    public void setWithdrawBalance(String withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
    }

    public String getExtMark() {
        return extMark;
    }

    public void setExtMark(String extMark) {
        this.extMark = extMark;
    }

    public String getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(String freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
