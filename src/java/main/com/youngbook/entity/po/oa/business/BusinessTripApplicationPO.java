package com.youngbook.entity.po.oa.business;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2015/7/14.
 */
@Table(name = "OA_BusinessTripApplication", jsonPrefix = "businessTripApplication")
public class BusinessTripApplicationPO extends BasePO {
    //构造类成员
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

    // 出差人编号
    private String userId = new String();
    private String userName = new String();

    // 部门
    private String departmentId = new String();
    private String departmentName = new String();

    // 申请时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String applicationTime = new String();

    // 出差原因
    private String purpose = new String();

    //出差人员
    private String evections = new String();
    // 出差地点
    private String businessAddress = new String();

    // 计划天数
    private int planFate = Integer.MAX_VALUE;

    // 实际天数
    private int actualFate = Integer.MAX_VALUE;

    // 费用预算
    private double expenseBudge = Double.MAX_VALUE;

    // 实际费用
    private double expenseActual = Double.MAX_VALUE;

    public String getEvections() {
        return evections;
    }

    public void setEvections(String evections) {
        this.evections = evections;
    }

    // 经办人签字
    private String operatorSign = new String();

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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public int getPlanFate() {
        return planFate;
    }

    public void setPlanFate(int planFate) {
        this.planFate = planFate;
    }

    public int getActualFate() {
        return actualFate;
    }

    public void setActualFate(int actualFate) {
        this.actualFate = actualFate;
    }

    public double getExpenseBudge() {
        return expenseBudge;
    }

    public void setExpenseBudge(double expenseBudge) {
        this.expenseBudge = expenseBudge;
    }

    public double getExpenseActual() {
        return expenseActual;
    }

    public void setExpenseActual(double expenseActual) {
        this.expenseActual = expenseActual;
    }

    public String getOperatorSign() {
        return operatorSign;
    }

    public void setOperatorSign(String operatorSign) {
        this.operatorSign = operatorSign;
    }
}
