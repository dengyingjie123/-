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
 * Time: 下午11:21
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "CRM_CustomerMoney", jsonPrefix = "customerMoney")
public class CustomerMoneyPO extends BasePO {
    // Sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();

    // State
    private int state = Integer.MAX_VALUE;

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 客户编号
    private String customerId = new String();

    // 冻结资金
    private double frozenMoney = Double.MAX_VALUE;

    // 待收资金
    private double expectedMoney = Double.MAX_VALUE;

    // 可用资金
    private double availableMoney = Double.MAX_VALUE;

    // 已投资资金
    private double investedMoney = Double.MAX_VALUE;

    // 累计收益资金
    private double totalProfitMoney = Double.MAX_VALUE;

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

    public double getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(double frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public double getExpectedMoney() {
        return expectedMoney;
    }

    public void setExpectedMoney(double expectedMoney) {
        this.expectedMoney = expectedMoney;
    }

    public double getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(double availableMoney) {
        this.availableMoney = availableMoney;
    }

    public double getInvestedMoney() {
        return investedMoney;
    }

    public void setInvestedMoney(double investedMoney) {
        this.investedMoney = investedMoney;
    }

    public double getTotalProfitMoney() {
        return totalProfitMoney;
    }

    public void setTotalProfitMoney(double totalProfitMoney) {
        this.totalProfitMoney = totalProfitMoney;
    }
}
