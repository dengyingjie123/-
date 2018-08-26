package com.youngbook.entity.po.oa.Information;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by haihong on 2015/5/29.
 * 对内及对外信息报送审批
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */
@Table(name = "OA_InformationSubmitted", jsonPrefix = "informationSubmitted")
public class InformationSubmittedPO extends BasePO {
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

    // 经办人 : 支持查询
    private String handlingId = new String();

    // 主送单位 : 支持查询
    private String mainOrg = new String();

    // 抄送单位 : 支持查询
    private String otherOrg = new String();

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

    // 原件移交接收人 : 支持查询
    private String transferRecipient = new String();

    // 原件归还时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String revertTime = new String();

    // 原件归还人 : 支持查询
    private String revertOperatorId = new String();

    // 原件归还接收人 : 支持查询
    private String revertRecipientId = new String();

    // 部门负责人意见 : 支持查询
    private String departmentLeaderOpinion = new String();

    // 分管领导意见 : 支持查询
    private String leaderShipOpinion = new String();

    // 执行董事意见 : 支持查询
    private String directorOpinion = new String();

    //getset


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
}
