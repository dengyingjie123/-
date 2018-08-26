package com.youngbook.entity.vo.business;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015/7/14.
 */
@Table(name = "OA_BusinessTripApplication", jsonPrefix = "businessTripApplication")
public class BusinessTripApplicationVO extends BaseVO {
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
    private double planFate = Double.MAX_VALUE;

    // 实际天数
    private double actualFate = Double.MAX_VALUE;

    // 费用预算
    private double expenseBudge = Double.MAX_VALUE;

    // 实际费用
    private double expenseActual = Double.MAX_VALUE;


/*修改：周海鸿
* 时间：2015-7-15
* 内容 ：添加业务相关字段属性*/
    // 执行董事审核意见
    private String executiveDirectorContent = new String();
    private String executiveDirectorTime = new String();

    // 分管领导审核意见
    private String chargeLeaderContent = new String();
    private String chargeLeaderTime = new String();

    // 部门负责人审核意见
    private String departmentLeaderContent = new String();
    private String departmentLeaderTime = new String();

    // 财务总监审核意见
    private String financeDirectorContent = new String();
    private String financeDirectorTime = new String();

    // 会计审核内容
    private String accountingContent = new String();
    private String accountingTime = new String();

    // 所属公司总经理意见
    private String generalManagerContent = new String();
    private String generalManagerTime = new String();

    //出纳意见
    private String cashierContent = new String();
    private String cashierTime = new String();

    //是否完成
    private String status = new String();
    //用于控制业务流程
    private String currentNodeId = new String();
    //状态标题
    private String currentNodeTitle = new String();

    private String routeListId = new String();
    private String currentStatus = new String();


    // 部门负责人名称 : 支持查询
    private String departmentLeaderName = new String();

    // 所属总经理名称 : 支持查询
    private String generalManagerName = new String();


    // 会计名称 : 支持查询
    private String accountingName = new String();

    // 财务总监名称 : 支持查询
    private String financeDirectorName = new String();

    // 分管领导名称 : 支持查询
    private String chargeLeaderName = new String();

    // 执行董事名称 : 支持查询
    private String executiveDirectorName = new String();

    // 出纳名称 : 支持查询
    private String cashierName = new String();

    //人力名称
    private String name1 = new String();
    //人力意见
    private String content1 = new String();
    private String time1 = new String();
    private String status1 = new String();

    //公司
    private String controlString1 = new String();
    //部门
    private String controlString2 = new String();
    //公司编号
    private String controlString1Id = new String();
    //部门编号
    private String controlString2Id = new String();
    //唯一标识
    private String controlString3 = new String();

    public String getControlString3() {
        return controlString3;
    }

    public void setControlString3(String controlString3) {
        this.controlString3 = controlString3;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEvections() {
        return evections;
    }

    public void setEvections(String evections) {
        this.evections = evections;
    }

    // 经办人签字
    private String operatorSign = new String();

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public double getPlanFate() {
        return planFate;
    }

    public void setPlanFate(double planFate) {
        this.planFate = planFate;
    }

    public double getActualFate() {
        return actualFate;
    }

    public void setActualFate(double actualFate) {
        this.actualFate = actualFate;
    }

    public String getExecutiveDirectorContent() {
        return executiveDirectorContent;
    }

    public void setExecutiveDirectorContent(String executiveDirectorContent) {
        this.executiveDirectorContent = executiveDirectorContent;
    }

    public String getChargeLeaderContent() {
        return chargeLeaderContent;
    }

    public void setChargeLeaderContent(String chargeLeaderContent) {
        this.chargeLeaderContent = chargeLeaderContent;
    }

    public String getDepartmentLeaderContent() {
        return departmentLeaderContent;
    }

    public void setDepartmentLeaderContent(String departmentLeaderContent) {
        this.departmentLeaderContent = departmentLeaderContent;
    }

    public String getFinanceDirectorContent() {
        return financeDirectorContent;
    }

    public void setFinanceDirectorContent(String financeDirectorContent) {
        this.financeDirectorContent = financeDirectorContent;
    }

    public String getAccountingContent() {
        return accountingContent;
    }

    public void setAccountingContent(String accountingContent) {
        this.accountingContent = accountingContent;
    }

    public String getGeneralManagerContent() {
        return generalManagerContent;
    }

    public void setGeneralManagerContent(String generalManagerContent) {
        this.generalManagerContent = generalManagerContent;
    }

    public String getCashierContent() {
        return cashierContent;
    }

    public void setCashierContent(String cashierContent) {
        this.cashierContent = cashierContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public String getCurrentNodeTitle() {
        return currentNodeTitle;
    }

    public void setCurrentNodeTitle(String currentNodeTitle) {
        this.currentNodeTitle = currentNodeTitle;
    }

    public String getRouteListId() {
        return routeListId;
    }

    public void setRouteListId(String routeListId) {
        this.routeListId = routeListId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getDepartmentLeaderName() {
        return departmentLeaderName;
    }

    public void setDepartmentLeaderName(String departmentLeaderName) {
        this.departmentLeaderName = departmentLeaderName;
    }

    public String getGeneralManagerName() {
        return generalManagerName;
    }

    public void setGeneralManagerName(String generalManagerName) {
        this.generalManagerName = generalManagerName;
    }

    public String getAccountingName() {
        return accountingName;
    }

    public void setAccountingName(String accountingName) {
        this.accountingName = accountingName;
    }

    public String getFinanceDirectorName() {
        return financeDirectorName;
    }

    public void setFinanceDirectorName(String financeDirectorName) {
        this.financeDirectorName = financeDirectorName;
    }

    public String getChargeLeaderName() {
        return chargeLeaderName;
    }

    public void setChargeLeaderName(String chargeLeaderName) {
        this.chargeLeaderName = chargeLeaderName;
    }

    public String getExecutiveDirectorName() {
        return executiveDirectorName;
    }

    public void setExecutiveDirectorName(String executiveDirectorName) {
        this.executiveDirectorName = executiveDirectorName;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getExecutiveDirectorTime() {
        return executiveDirectorTime;
    }

    public void setExecutiveDirectorTime(String executiveDirectorTime) {
        this.executiveDirectorTime = executiveDirectorTime;
    }

    public String getChargeLeaderTime() {
        return chargeLeaderTime;
    }

    public void setChargeLeaderTime(String chargeLeaderTime) {
        this.chargeLeaderTime = chargeLeaderTime;
    }

    public String getDepartmentLeaderTime() {
        return departmentLeaderTime;
    }

    public void setDepartmentLeaderTime(String departmentLeaderTime) {
        this.departmentLeaderTime = departmentLeaderTime;
    }

    public String getFinanceDirectorTime() {
        return financeDirectorTime;
    }

    public void setFinanceDirectorTime(String financeDirectorTime) {
        this.financeDirectorTime = financeDirectorTime;
    }

    public String getAccountingTime() {
        return accountingTime;
    }

    public void setAccountingTime(String accountingTime) {
        this.accountingTime = accountingTime;
    }

    public String getGeneralManagerTime() {
        return generalManagerTime;
    }

    public void setGeneralManagerTime(String generalManagerTime) {
        this.generalManagerTime = generalManagerTime;
    }

    public String getCashierTime() {
        return cashierTime;
    }

    public void setCashierTime(String cashierTime) {
        this.cashierTime = cashierTime;
    }

    public String getControlString1() {
        return controlString1;
    }

    public void setControlString1(String controlString1) {
        this.controlString1 = controlString1;
    }

    public String getControlString2() {
        return controlString2;
    }

    public void setControlString2(String controlString2) {
        this.controlString2 = controlString2;
    }

    public String getControlString1Id() {
        return controlString1Id;
    }

    public void setControlString1Id(String controlString1Id) {
        this.controlString1Id = controlString1Id;
    }

    public String getControlString2Id() {
        return controlString2Id;
    }

    public void setControlString2Id(String controlString2Id) {
        this.controlString2Id = controlString2Id;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }
}
