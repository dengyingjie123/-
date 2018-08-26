package com.youngbook.entity.po.fdcg;

import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Lee on 3/14/2018.
 */
@Table(name = "fdcg_customer_account_withdraw", jsonPrefix = "customerAccountWithdraw")
public class FdcgCustomerAccountWithdrawPO extends BasePO {


    private String accountNo = "";
    private String amount = "";
    private String extMark = "";
    private String fee = "";
    private String merchantNo = "";
    private String notifyUrl = "";
    private String returnUrl = "";
    private String orderDate = "";
    private String orderNo = "";
    private String userName = "";

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExtMark() {
        return extMark;
    }

    public void setExtMark(String extMark) {
        this.extMark = extMark;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
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
}
