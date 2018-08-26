package com.youngbook.entity.po.oa.administration;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.wf.common.WorkflowDao;
import com.youngbook.common.wf.engines.IBizDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.BasePO;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 15-4-7
 * Time: 下午9:04
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "OA_SealUsageWFA", jsonPrefix = "sealUsageWFA")
public class SealUsageWFAPO extends BasePO {

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

    // 审核人编号
    private String checkerId = new String();

    // 审核人名称 : 支持查询
    private String checkerName = new String();

    // 审核时间 : 时间类型,时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String checkTime = new String();

    // 审核回复
    private String checkReplay = new String();

    // 状态编号 : 必填,KV下拉菜单【OA_SealUsageStatus】
    private String statusId = new String();

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

    public String getApplicationPurpose() {
        return applicationPurpose;
    }

    public void setApplicationPurpose(String applicationPurpose) {
        this.applicationPurpose = applicationPurpose;
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckReplay() {
        return checkReplay;
    }

    public void setCheckReplay(String checkReplay) {
        this.checkReplay = checkReplay;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getSentto() {
        return sentto;
    }

    public void setSentto(String sentto) {
        this.sentto = sentto;
    }
}

