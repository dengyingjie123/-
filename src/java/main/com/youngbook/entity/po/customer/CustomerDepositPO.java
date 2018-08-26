package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 15-3-31
 * Time: 下午11:05
 * To change this template use File | Settings | File Templates.
 * CustomerDeposit >>>>> 客户充值
 */
@Table(name = "CRM_CustomerDeposit", jsonPrefix = "customerDeposit")
public class CustomerDepositPO extends BasePO {

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

    // 客户编号
    private String customerId = new String();

    // 金额
    private double money = Double.MAX_VALUE;

    // 状态
    private String status = new String();

    // 费率
    private double rate = Double.MAX_VALUE;

    // 时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String time = new String();

    // 客户IP
    private String customerIP = new String();

    // 充值手续费
    private double fee = Double.MAX_VALUE;

    // 实际到账金额
    private double moneyTransfer = Double.MAX_VALUE;

    // 充值来源账户(就是充值的平台)
    private String fromAccountId = new String();

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

    public double getMoneyTransfer() {
        return moneyTransfer;
    }

    public void setMoneyTransfer(double moneyTransfer) {
        this.moneyTransfer = moneyTransfer;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }
}
