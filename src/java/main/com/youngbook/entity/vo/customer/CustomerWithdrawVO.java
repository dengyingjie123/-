package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by ZSQ on 2015/3/25.
 */

@Table(name = "CRM_CustomerWithdraw",jsonPrefix = "customerWithdrawVO")
public class CustomerWithdrawVO extends BaseVO {
    private int sid  = Integer.MAX_VALUE;
    private String id = new String();
    private int state = Integer.MAX_VALUE;
    private String operatorId = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();
    private String customerId = new String();
    private double money = Double.MAX_VALUE;
    private String status = new String();
    private double rate = Double.MAX_VALUE;

    @DataAdapter(fieldType = FieldType.DATE)
    private String time = new String();
    private String customerIP = new String();
    private double fee = Double.MAX_VALUE;
    private double feeModifed = Double.MAX_VALUE;
    private double moneyTransfer = Double.MAX_VALUE;
    //客户名称
    private String customerName = new String();
    //操作员
    private String operatorName = new String();


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

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerIP() {
        return customerIP;
    }

    public void setCustomerIP(String customerIP) {
        this.customerIP = customerIP;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getFeeModifed() {
        return feeModifed;
    }

    public void setFeeModifed(double feeModifed) {
        this.feeModifed = feeModifed;
    }

    public double getMoneyTransfer() {
        return moneyTransfer;
    }

    public void setMoneyTransfer(double moneyTransfer) {
        this.moneyTransfer = moneyTransfer;
    }


}
