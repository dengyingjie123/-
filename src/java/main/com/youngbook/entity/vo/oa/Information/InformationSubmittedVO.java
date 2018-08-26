package com.youngbook.entity.vo.oa.Information;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by haihong on 2015/5/29.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */
@Table(name = "OA_InformationSubmitted", jsonPrefix = "informationSubmittedVO")
public class InformationSubmittedVO extends BaseVO{
    // Sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();

    // State
    private int state = Integer.MAX_VALUE;

    // OperatorId
    private String operatorId = new String();

    // OperateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 经办部门 : 支持查询
    private String department = new String();
    private String departmentName  = new String();//部分名称

    // 经办人 : 支持查询
    private String handlingId = new String();
    //经办人名称
    private String handlingName= new String();

    // 主送单位 : 支持查询
    private String mainOrg = new String();
    private String mainOrgName = new String();
    // 抄送单位 : 支持查询
    private String otherOrg = new String();
    private String otherOrgName = new String();

    // 报送事由 : 支持查询
    private String reason = new String();

    // 报送内容 : 支持查询
    private String content = new String();

    // 报送时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String submitTime = new String();

    // 原件移交时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String transferTime = new String();

    // 原件移交人 : 支持查询
    private String transferOperatorId = new String();
   private String  transferOperatorName = new String();//原件移交人

    // 原件移交接收人 : 支持查询
    private String transferRecipient = new String();
    // 原件移交接收人 : 支持查询
    private String transferRecipientName = new String();

    // 原件归还时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String revertTime = new String();

    // 原件归还人 : 支持查询
    private String revertOperatorId = new String();
   private String revertOperatorName = new String();//原件归还人
    // 原件归还接收人 : 支持查询
    private String revertRecipientId = new String();
    private String revertRecipientName = new String();//原件归还接收人

    // 部门负责人意见 : 支持查询
    private String departmentLeaderOpinion = new String();

    // 分管领导意见 : 支持查询
    private String leaderShipOpinion = new String();

    // 执行董事意见 : 支持查询
    private String directorOpinion = new String();

    /**
     * 修改人： 周海鸿
     * 修改时间：2015-6-26
     * 修改事件：添加了查询需要的元素，业务查询需要的元素
     */
    // 执行董事审核意见
    private String executiveDirectorContent = new String();

    // 分管领导审核意见
    private String chargeLeaderContent = new String();

    // 部门负责人审核意见
    private String departmentLeaderContent = new String();


    // 所属公司总经理意见
    private String generalManagerContent = new String();

    //用于控制业务流程
    private String currentNodeId = new String();
    //节点名称
    private String currentNodeTitle = new String();

    private String routeListId = new String();
    private String currentStatus = new String();

    // 部门负责人名称 : 支持查询
    private String departmentLeaderName = new String();

    // 所属总经理名称 : 支持查询
    private String generalManagerName = new String();



    // 分管领导名称 : 支持查询
    private String chargeLeaderName = new String();

    // 执行董事名称 : 支持查询
    private String executiveDirectorName = new String();


    //getset
    /* 修改：周海鸿
    * 时间：2015-7-20
      * 内容：添加数据字段*/

    // 部门负责人审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentLeaderTime = new String();

    // 所属总经理审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String generalManagerTime = new String();



    // 分管领导审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String chargeLeaderTime = new String();

    // 执行董事审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String executiveDirectorTime = new String();

    // 申请人名称 : 支持查询
    private String applicantName = new String();

    // 申请备注 : 支持查询
    private String applicantComment = new String();

    // 申请日期 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String applicantTime = new String();

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHandlingId() {
        return handlingId;
    }

    public void setHandlingId(String handlingId) {
        this.handlingId = handlingId;
    }

    public String getMainOrg() {
        return mainOrg;
    }

    public void setMainOrg(String mainOrg) {
        this.mainOrg = mainOrg;
    }

    public String getOtherOrg() {
        return otherOrg;
    }

    public void setOtherOrg(String otherOrg) {
        this.otherOrg = otherOrg;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getTransferOperatorId() {
        return transferOperatorId;
    }

    public void setTransferOperatorId(String transferOperatorId) {
        this.transferOperatorId = transferOperatorId;
    }

    public String getTransferRecipient() {
        return transferRecipient;
    }

    public void setTransferRecipient(String transferRecipient) {
        this.transferRecipient = transferRecipient;
    }

    public String getRevertTime() {
        return revertTime;
    }

    public void setRevertTime(String revertTime) {
        this.revertTime = revertTime;
    }

    public String getRevertOperatorId() {
        return revertOperatorId;
    }

    public void setRevertOperatorId(String revertOperatorId) {
        this.revertOperatorId = revertOperatorId;
    }

    public String getRevertRecipientId() {
        return revertRecipientId;
    }

    public void setRevertRecipientId(String revertRecipientId) {
        this.revertRecipientId = revertRecipientId;
    }

    public String getDepartmentLeaderOpinion() {
        return departmentLeaderOpinion;
    }

    public void setDepartmentLeaderOpinion(String departmentLeaderOpinion) {
        this.departmentLeaderOpinion = departmentLeaderOpinion;
    }

    public String getLeaderShipOpinion() {
        return leaderShipOpinion;
    }

    public void setLeaderShipOpinion(String leaderShipOpinion) {
        this.leaderShipOpinion = leaderShipOpinion;
    }

    public String getDirectorOpinion() {
        return directorOpinion;
    }

    public void setDirectorOpinion(String directorOpinion) {
        this.directorOpinion = directorOpinion;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHandlingName() {
        return handlingName;
    }

    public void setHandlingName(String handlingName) {
        this.handlingName = handlingName;
    }

    public String getTransferOperatorName() {
        return transferOperatorName;
    }

    public void setTransferOperatorName(String transferOperatorName) {
        this.transferOperatorName = transferOperatorName;
    }

    public String getTransferRecipientName() {
        return transferRecipientName;
    }

    public void setTransferRecipientName(String transferRecipientName) {
        this.transferRecipientName = transferRecipientName;
    }

    public String getRevertOperatorName() {
        return revertOperatorName;
    }

    public void setRevertOperatorName(String revertOperatorName) {
        this.revertOperatorName = revertOperatorName;
    }

    public String getRevertRecipientName() {
        return revertRecipientName;
    }

    public void setRevertRecipientName(String revertRecipientName) {
        this.revertRecipientName = revertRecipientName;
    }

    public String getMainOrgName() {
        return mainOrgName;
    }

    public void setMainOrgName(String mainOrgName) {
        this.mainOrgName = mainOrgName;
    }

    public String getOtherOrgName() {
        return otherOrgName;
    }

    public void setOtherOrgName(String otherOrgName) {
        this.otherOrgName = otherOrgName;
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


    public String getGeneralManagerContent() {
        return generalManagerContent;
    }

    public void setGeneralManagerContent(String generalManagerContent) {
        this.generalManagerContent = generalManagerContent;
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

    public String getCurrentNodeTitle() {
        return currentNodeTitle;
    }

    public void setCurrentNodeTitle(String currentNodeTitle) {
        this.currentNodeTitle = currentNodeTitle;
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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantComment() {
        return applicantComment;
    }

    public void setApplicantComment(String applicantComment) {
        this.applicantComment = applicantComment;
    }

    public String getApplicantTime() {
        return applicantTime;
    }

    public void setApplicantTime(String applicantTime) {
        this.applicantTime = applicantTime;
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
