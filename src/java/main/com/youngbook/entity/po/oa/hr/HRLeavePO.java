package com.youngbook.entity.po.oa.hr;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by haihong on 2015/6/26.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 *
 *   创建请假类
 */
//构造类成员
@Table(name = "OA_HRLeave", jsonPrefix = "hrleave")
public class HRLeavePO extends BasePO {
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

    // 请假类别 : 支持查询,必填,KV下拉菜单【OA_HRLeaveType】
    private String leaveTypeId = new String();

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


}
