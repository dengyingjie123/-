package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by admin on 2015/4/28.
 */
@Table(name = "CRM_CustomerMoneyLog", jsonPrefix = "customerMoneyLog")
public class CustomerMoneyLogPO extends BasePO{
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

    /**
     * 购买、兑付、充值、提现
     */
    private String type = new String();

    // 内容
    private String content = new String();

    /**
     * 成功、受理、失败
     */
    private String status = new String();

    /**
     * 本金
     */
    private double principalMoney = Double.MAX_VALUE;

    /**
     * 收益
     */
    private double profitMoney = Double.MAX_VALUE;

    /**
     * 手续费
     */
    private double feeMoney = Double.MAX_VALUE;

    // 模块编号 : 用于确定是哪个模块对此表的操作
    private String moduleId = new String();

    // 业务编号
    private String bizId = new String();

    // 客户编号
    private String customerId = new String();


    public double getPrincipalMoney() {
        return principalMoney;
    }

    public void setPrincipalMoney(double principalMoney) {
        this.principalMoney = principalMoney;
    }

    public double getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(double profitMoney) {
        this.profitMoney = profitMoney;
    }

    public double getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(double feeMoney) {
        this.feeMoney = feeMoney;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
