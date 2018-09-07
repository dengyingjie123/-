package com.youngbook.entity.vo.oa.borrow;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by 借款申请 on 2015/9/21.
 */


@Table(name = "OA_BorrowMoney", jsonPrefix = "borrowMoneyVO")
public class BorrowMoneyVO  extends BaseVO {


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

    // 申请人编号
    private String applicantId = new String();

    // 申请人名称 : 支持查询
    private String applicantName = new String();

    // 申请时间 : 时间类型,时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String applicationTime = new String();

    //用章发往处
    private String sentto = new String();

    // 申请用途 : 支持查询,必填
    private String applicationPurpose = new String();

    // 申请金额
    private String money = new String();


    //工作流

    //用于控制业务流程
    private String currentNodeId = new String();
    //状态标题
    private String currentNodeTitle = new String();

    private String routeListId = new String();
    private String currentStatus = new String();



    // 部门负责人编号
    private String departmentLeaderId = new String();

    // 部门负责人名称 : 支持查询
    private String departmentLeaderName = new String();

    // 部门负责人审核内容 : 支持查询
    private String departmentLeaderContent = new String();

    // 部门负责人审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentLeaderTime = new String();

    // 部门负责人审核状态
    private int departmentLeaderStatus = Integer.MAX_VALUE;

    // 所属总经理编号
    private String generalManagerId = new String();

    // 所属总经理名称 : 支持查询
    private String generalManagerName = new String();

    // 所属总经理意见 : 支持查询
    private String generalManagerContent = new String();

    // 所属总经理审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String generalManagerTime = new String();

    // 所属总经理状态
    private int generalManagerStatus = Integer.MAX_VALUE;

    // 会计编号
    private String accountingId = new String();

    // 会计名称 : 支持查询
    private String accountingName = new String();

    // 会计内容 : 支持查询
    private String accountingContent = new String();

    // 会计时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String accountingTime = new String();

    // 会计状态
    private int accountingStatus = Integer.MAX_VALUE;

    // 财务总监编号
    private String financeDirectorId = new String();

    // 财务总监名称 : 支持查询
    private String financeDirectorName = new String();

    // 财务总监审核内容 : 支持查询
    private String financeDirectorContent = new String();

    // 财务总监审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String financeDirectorTime = new String();

    // 财务总监审核状态
    private int financeDirectorStatus = Integer.MAX_VALUE;


    // 执行懂事编号
    private String executiveDirectorId = new String();

    // 执行董事名称 : 支持查询
    private String executiveDirectorName = new String();

    // 执行董事审核内容 : 支持查询
    private String executiveDirectorContent = new String();

    // 执行董事审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String executiveDirectorTime = new String();

    // 执行董事审核状态
    private int executiveDirectorStatus = Integer.MAX_VALUE;

    // 出纳编号
    private String cashierId = new String();

    // 出纳名称 : 支持查询
    private String cashierName = new String();

    // 出纳意见 : 支持查询
    private String cashierContent = new String();

    // 出纳时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String cashierTime = new String();

    // 出纳状态
    private int cashierStatus = Integer.MAX_VALUE;


    /*
    * 作者：周海鸿
    * 时间：2015-7-31
    * 内容：添加公司部门的字段属性*/


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

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getSentto() {
        return sentto;
    }

    public void setSentto(String sentto) {
        this.sentto = sentto;
    }

    public String getApplicationPurpose() {
        return applicationPurpose;
    }

    public void setApplicationPurpose(String applicationPurpose) {
        this.applicationPurpose = applicationPurpose;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getDepartmentLeaderId() {
        return departmentLeaderId;
    }

    public void setDepartmentLeaderId(String departmentLeaderId) {
        this.departmentLeaderId = departmentLeaderId;
    }

    public String getDepartmentLeaderName() {
        return departmentLeaderName;
    }

    public void setDepartmentLeaderName(String departmentLeaderName) {
        this.departmentLeaderName = departmentLeaderName;
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

    public String getGeneralManagerId() {
        return generalManagerId;
    }

    public void setGeneralManagerId(String generalManagerId) {
        this.generalManagerId = generalManagerId;
    }

    public String getGeneralManagerName() {
        return generalManagerName;
    }

    public void setGeneralManagerName(String generalManagerName) {
        this.generalManagerName = generalManagerName;
    }

    public String getGeneralManagerContent() {
        return generalManagerContent;
    }

    public void setGeneralManagerContent(String generalManagerContent) {
        this.generalManagerContent = generalManagerContent;
    }

    public String getGeneralManagerTime() {
        return generalManagerTime;
    }

    public void setGeneralManagerTime(String generalManagerTime) {
        this.generalManagerTime = generalManagerTime;
    }

    public int getGeneralManagerStatus() {
        return generalManagerStatus;
    }

    public void setGeneralManagerStatus(int generalManagerStatus) {
        this.generalManagerStatus = generalManagerStatus;
    }

    public String getAccountingId() {
        return accountingId;
    }

    public void setAccountingId(String accountingId) {
        this.accountingId = accountingId;
    }

    public String getAccountingName() {
        return accountingName;
    }

    public void setAccountingName(String accountingName) {
        this.accountingName = accountingName;
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

    public String getFinanceDirectorId() {
        return financeDirectorId;
    }

    public void setFinanceDirectorId(String financeDirectorId) {
        this.financeDirectorId = financeDirectorId;
    }

    public String getFinanceDirectorName() {
        return financeDirectorName;
    }

    public void setFinanceDirectorName(String financeDirectorName) {
        this.financeDirectorName = financeDirectorName;
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

    public String getExecutiveDirectorId() {
        return executiveDirectorId;
    }

    public void setExecutiveDirectorId(String executiveDirectorId) {
        this.executiveDirectorId = executiveDirectorId;
    }

    public String getExecutiveDirectorName() {
        return executiveDirectorName;
    }

    public void setExecutiveDirectorName(String executiveDirectorName) {
        this.executiveDirectorName = executiveDirectorName;
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

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getCashierContent() {
        return cashierContent;
    }

    public void setCashierContent(String cashierContent) {
        this.cashierContent = cashierContent;
    }

    public String getCashierTime() {
        return cashierTime;
    }

    public void setCashierTime(String cashierTime) {
        this.cashierTime = cashierTime;
    }

    public int getCashierStatus() {
        return cashierStatus;
    }

    public void setCashierStatus(int cashierStatus) {
        this.cashierStatus = cashierStatus;
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

    public String getControlString3() {
        return controlString3;
    }

    public void setControlString3(String controlString3) {
        this.controlString3 = controlString3;
    }
}

