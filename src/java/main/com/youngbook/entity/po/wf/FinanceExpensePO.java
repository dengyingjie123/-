package com.youngbook.entity.po.wf;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.wf.admin.RouteList;
import com.youngbook.common.wf.common.WorkflowDao;
import com.youngbook.common.wf.engines.IBizDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.BasePO;
import com.youngbook.service.oa.finance.FinanceExpenseService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;


@Table(name = "WorkflowAction_FinanceExpense", jsonPrefix = "financeExpense")
public class FinanceExpensePO extends BasePO {
    // sid

            @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();

    // 报销单号
    private String expenseId = new String();

    private String status = new String();

    // 日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String time = new String();

    // 金额
    private double money = Double.MAX_VALUE;

    // 执行董事审核内容
    private String executiveDirectorContent = new String();

    // 执行董事Id
    private String executiveDirectorId = new String();

    // 执行董事审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String executiveDirectorTime = new String();

    // 执行董事审核状态
    private int executiveDirectorStatus = Integer.MAX_VALUE;

    // 分管领导审核内容
    private String chargeLeaderContent = new String();

    // 分管领导Id
    private String chargeLeaderId = new String();

    // 分管领导审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String chargeLeaderTime = new String();

    // 分管领导审核状态
    private int chargeLeaderStatus = Integer.MAX_VALUE;

    // 部门负责人审核内容
    private String departmentLeaderContent = new String();

    // 部门负责人Id
    private String departmentLeaderId = new String();

    // 部门负责人审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentLeaderTime = new String();

    // 部门负责人审核状态
    private int departmentLeaderStatus = Integer.MAX_VALUE;

    // 财务总监审核内容
    private String financeDirectorContent = new String();

    // 财务总监Id
    private String financeDirectorId = new String();

    // 财务总监审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String financeDirectorTime = new String();

    // 财务总监审核状态
    private int financeDirectorStatus = Integer.MAX_VALUE;

    // 会计审核内容
    private String accountingContent = new String();

    // 会计Id
    private String accountingId = new String();

    // 会计审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String accountingTime = new String();

    // 会计审核状态
    private int accountingStatus = Integer.MAX_VALUE;

    // 经手人编号
    private String submitterId = new String();

    // 经手人时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String submitterTime = new String();

    // 所属公司总经理编号
    private String generalManagerId = new String();

    // 所属公司总经理时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String generalManagerTime = new String();

    // 所属公司总经理意见
    private String generalManagerContent = new String();
    //所属公司总经理审核状态
    private int generalManagerStatus = Integer.MAX_VALUE;
    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    //状态
    private int state = Integer.MAX_VALUE;
    //报销月份

    private String month = new String();

    //附件张数
    private int accessoryNumber  = Integer.MAX_VALUE;



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getExecutiveDirectorContent() {
        return executiveDirectorContent;
    }

    public void setExecutiveDirectorContent(String executiveDirectorContent) {
        this.executiveDirectorContent = executiveDirectorContent;
    }

    public String getExecutiveDirectorTime() {
        return executiveDirectorTime;
    }

    public void setExecutiveDirectorTime(String executiveDirectorTime) {
        this.executiveDirectorTime = executiveDirectorTime;
    }

    public int getExecutiveDirectorStatus() {
        return executiveDirectorStatus;
    }

    public void setExecutiveDirectorStatus(int executiveDirectorStatus) {
        this.executiveDirectorStatus = executiveDirectorStatus;
    }

    public String getChargeLeaderContent() {
        return chargeLeaderContent;
    }

    public void setChargeLeaderContent(String chargeLeaderContent) {
        this.chargeLeaderContent = chargeLeaderContent;
    }

    public String getChargeLeaderTime() {
        return chargeLeaderTime;
    }

    public void setChargeLeaderTime(String chargeLeaderTime) {
        this.chargeLeaderTime = chargeLeaderTime;
    }

    public int getChargeLeaderStatus() {
        return chargeLeaderStatus;
    }

    public void setChargeLeaderStatus(int chargeLeaderStatus) {
        this.chargeLeaderStatus = chargeLeaderStatus;
    }

    public String getDepartmentLeaderContent() {
        return departmentLeaderContent;
    }

    public void setDepartmentLeaderContent(String departmentLeaderContent) {
        this.departmentLeaderContent = departmentLeaderContent;
    }

    public String getDepartmentLeaderTime() {
        return departmentLeaderTime;
    }

    public void setDepartmentLeaderTime(String departmentLeaderTime) {
        this.departmentLeaderTime = departmentLeaderTime;
    }

    public int getDepartmentLeaderStatus() {
        return departmentLeaderStatus;
    }

    public void setDepartmentLeaderStatus(int departmentLeaderStatus) {
        this.departmentLeaderStatus = departmentLeaderStatus;
    }

    public String getFinanceDirectorContent() {
        return financeDirectorContent;
    }

    public void setFinanceDirectorContent(String financeDirectorContent) {
        this.financeDirectorContent = financeDirectorContent;
    }

    public String getFinanceDirectorTime() {
        return financeDirectorTime;
    }

    public void setFinanceDirectorTime(String financeDirectorTime) {
        this.financeDirectorTime = financeDirectorTime;
    }

    public int getFinanceDirectorStatus() {
        return financeDirectorStatus;
    }

    public void setFinanceDirectorStatus(int financeDirectorStatus) {
        this.financeDirectorStatus = financeDirectorStatus;
    }

    public String getAccountingContent() {
        return accountingContent;
    }

    public void setAccountingContent(String accountingContent) {
        this.accountingContent = accountingContent;
    }

    public String getAccountingTime() {
        return accountingTime;
    }

    public void setAccountingTime(String accountingTime) {
        this.accountingTime = accountingTime;
    }

    public int getAccountingStatus() {
        return accountingStatus;
    }

    public void setAccountingStatus(int accountingStatus) {
        this.accountingStatus = accountingStatus;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public String getSubmitterTime() {
        return submitterTime;
    }

    public void setSubmitterTime(String submitterTime) {
        this.submitterTime = submitterTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getExecutiveDirectorId() {
        return executiveDirectorId;
    }

    public void setExecutiveDirectorId(String executiveDirectorId) {
        this.executiveDirectorId = executiveDirectorId;
    }

    public String getChargeLeaderId() {
        return chargeLeaderId;
    }

    public void setChargeLeaderId(String chargeLeaderId) {
        this.chargeLeaderId = chargeLeaderId;
    }

    public String getDepartmentLeaderId() {
        return departmentLeaderId;
    }

    public void setDepartmentLeaderId(String departmentLeaderId) {
        this.departmentLeaderId = departmentLeaderId;
    }

    public String getFinanceDirectorId() {
        return financeDirectorId;
    }

    public void setFinanceDirectorId(String financeDirectorId) {
        this.financeDirectorId = financeDirectorId;
    }

    public String getAccountingId() {
        return accountingId;
    }

    public void setAccountingId(String accountingId) {
        this.accountingId = accountingId;
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

    public String getGeneralManagerId() {
        return generalManagerId;
    }

    public void setGeneralManagerId(String generalManagerId) {
        this.generalManagerId = generalManagerId;
    }

    public String getGeneralManagerTime() {
        return generalManagerTime;
    }

    public void setGeneralManagerTime(String generalManagerTime) {
        this.generalManagerTime = generalManagerTime;
    }

    public String getGeneralManagerContent() {
        return generalManagerContent;
    }

    public void setGeneralManagerContent(String generalManagerContent) {
        this.generalManagerContent = generalManagerContent;
    }

    public int getGeneralManagerStatus() {
        return generalManagerStatus;
    }

    public void setGeneralManagerStatus(int generalManagerStatus) {
        this.generalManagerStatus = generalManagerStatus;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAccessoryNumber() {
        return accessoryNumber;
    }

    public void setAccessoryNumber(int accessoryNumber) {
        this.accessoryNumber = accessoryNumber;
    }
}
