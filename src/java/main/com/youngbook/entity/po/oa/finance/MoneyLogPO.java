package com.youngbook.entity.po.oa.finance;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

@Table(name = "finance_moneyLog", jsonPrefix = "moneyLog")
public class MoneyLogPO extends BasePO {

    // 序号
    @Id
    private int sid = Integer.MAX_VALUE;

    // 编号
    private String id = new String();

    private String departmentId = new String();

    // 类型 : 必填,下拉菜单
    private String type = new String();

    private int inOrOut = Integer.MAX_VALUE;

    // 名称 : 必填
    private String name = new String();

    // 金额 : 必填
    private double money = Double.MAX_VALUE;

    // 备注
    private String comment = new String();

    // 账务时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String moneyTime = new String();

    // 操作人
    private String operatorId = new String();

    // 操作时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 状态
    private int state = Integer.MAX_VALUE;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMoneyTime() {
        return moneyTime;
    }

    public void setMoneyTime(String moneyTime) {
        this.moneyTime = moneyTime;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public int getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(int inOrOut) {
        this.inOrOut = inOrOut;
    }

    @Override
    public String toString() {
        return "MoneyLogPO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", type='" + type + '\'' +
                ", inOrOut=" + inOrOut +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", comment='" + comment + '\'' +
                ", moneyTime='" + moneyTime + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", state=" + state +
                '}';
    }
}
