package com.youngbook.entity.vo.oa.hr;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by haihong on 2015/6/26.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 *         <p/>
 *         创建请休假视图控制类
 */

@Table(name = "OA_HRLeave", jsonPrefix = "hrleaveVO")
public class HRLeaveVO extends BaseVO {
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

    // 申请人编号 : 支持查询
    private String applicantId = new String();
    private String oh_applicationName = new String();//申请人名称
    private String oh_applicationtime = new String();//申请人时间

    // 请假类别 : 支持查询,必填,KV下拉菜单【groupname = oa_hrleave_hrleaveType】
    private String leaveTypeId = new String();

    private String leaveTypeName = new String();//请假类别

    // 其他类别描述
    private String otherTypeDescription = new String();

    // 天数 : 必填,数字范围查询
    /*
    * 修改：周海鸿
    * 时间：2015-7-14
    * 内容：修改天数字段类型由int修改为double*/
    private double days = Double.MAX_VALUE;

    // 请假去处
    private String whereToLeave = new String();

    // 开始时间 : 必填,时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();

    // 结束时间 : 必填
    @DataAdapter(fieldType = FieldType.DATE)
    private String endTime = new String();

    // 工作交接人
    private String handoverName = new String();

    // 请假原因 : 支持查询,必填
    private String reason = new String();

    //业务流相关


    // 执行董事审核意见
    private String executiveDirectorContent = new String();

    // 分管领导审核意见
    private String chargeLeaderContent = new String();

    // 部门负责人审核意见
    private String departmentLeaderContent = new String();

    // 财务总监审核意见
    private String financeDirectorContent = new String();

    // 会计审核内容
    private String accountingContent = new String();

    // 所属公司总经理意见
    private String generalManagerContent = new String();

    //出纳意见
    private String cashierContent = new String();

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
    private String departmentLeaderTime = new String();

    // 所属总经理名称 : 支持查询
    private String generalManagerName = new String();
    private String generalManagerTime = new String();


    // 会计名称 : 支持查询
    private String accountingName = new String();
    private String accountingTime = new String();

    // 财务总监名称 : 支持查询
    private String financeDirectorName = new String();
    private String financeDirectorTime = new String();

    // 分管领导名称 : 支持查询
    private String chargeLeaderName = new String();
    private String chargeLeaderTime = new String();

    // 执行董事名称 : 支持查询
    private String executiveDirectorName = new String();
    private String executiveDirectorTime = new String();

    // 出纳名称 : 支持查询
    private String cashierName = new String();
    private String cashierTime = new String();
    /*修改：周海鸿
    * 时间：2015-7-17
    * 内容：添加人力审核内容*/
    //人力名称
    private String name1 = new String();
    //人力意见
    private String content1 = new String();
    private String time1 = new String();

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

    public String getControlString3() {
        return controlString3;
    }

    public void setControlString3(String controlString3) {
        this.controlString3 = controlString3;
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

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(String leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getOtherTypeDescription() {
        return otherTypeDescription;
    }

    public void setOtherTypeDescription(String otherTypeDescription) {
        this.otherTypeDescription = otherTypeDescription;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public String getWhereToLeave() {
        return whereToLeave;
    }

    public void setWhereToLeave(String whereToLeave) {
        this.whereToLeave = whereToLeave;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHandoverName() {
        return handoverName;
    }

    public void setHandoverName(String handoverName) {
        this.handoverName = handoverName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOh_applicationName() {
        return oh_applicationName;
    }

    public void setOh_applicationName(String oh_applicationName) {
        this.oh_applicationName = oh_applicationName;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
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

    public String getCurrentNodeTitle() {
        return currentNodeTitle;
    }

    public void setCurrentNodeTitle(String currentNodeTitle) {
        this.currentNodeTitle = currentNodeTitle;
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

    public String getDepartmentLeaderTime() {
        return departmentLeaderTime;
    }

    public void setDepartmentLeaderTime(String departmentLeaderTime) {
        this.departmentLeaderTime = departmentLeaderTime;
    }

    public String getGeneralManagerTime() {
        return generalManagerTime;
    }

    public void setGeneralManagerTime(String generalManagerTime) {
        this.generalManagerTime = generalManagerTime;
    }

    public String getAccountingTime() {
        return accountingTime;
    }

    public void setAccountingTime(String accountingTime) {
        this.accountingTime = accountingTime;
    }

    public String getFinanceDirectorTime() {
        return financeDirectorTime;
    }

    public void setFinanceDirectorTime(String financeDirectorTime) {
        this.financeDirectorTime = financeDirectorTime;
    }

    public String getChargeLeaderTime() {
        return chargeLeaderTime;
    }

    public void setChargeLeaderTime(String chargeLeaderTime) {
        this.chargeLeaderTime = chargeLeaderTime;
    }

    public String getExecutiveDirectorTime() {
        return executiveDirectorTime;
    }

    public void setExecutiveDirectorTime(String executiveDirectorTime) {
        this.executiveDirectorTime = executiveDirectorTime;
    }

    public String getCashierTime() {
        return cashierTime;
    }

    public void setCashierTime(String cashierTime) {
        this.cashierTime = cashierTime;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getOh_applicationtime() {
        return oh_applicationtime;
    }

    public void setOh_applicationtime(String oh_applicationtime) {
        this.oh_applicationtime = oh_applicationtime;
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

}
