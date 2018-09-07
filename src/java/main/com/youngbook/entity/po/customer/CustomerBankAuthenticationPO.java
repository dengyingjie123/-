package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2015/8/26.
 */
@Table(name = "crm_customerbankauthentication", jsonPrefix = "customerbankauthentication")
public class CustomerBankAuthenticationPO extends BasePO {
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

    // 客户编号 : 支持查询
    private String customerId = new String();

    //客户银行卡编号
    private String customerAccountId = new String();

    //资金
    private double money = Double.MAX_VALUE;

    //最后认证时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String expireTime = new String();

    //通联状态码
    private String code = new String();

    private int authenticationStatus = Integer.MAX_VALUE;

    private int paymentStatus = Integer.MAX_VALUE;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(String customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public int getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(int authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
