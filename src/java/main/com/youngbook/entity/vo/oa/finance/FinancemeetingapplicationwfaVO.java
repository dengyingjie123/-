package com.youngbook.entity.vo.oa.finance;

/**
 * Created by admin on 2015/4/29.
 */

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;


import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.common.wf.admin.Participant;
import com.youngbook.entity.po.BasePO;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by admin on 2015/4/29.
 */
@Table(name = "OA_Financemeetingapplicationwfa", jsonPrefix = "financemeetingapplicationwfaVO")
public class FinancemeetingapplicationwfaVO extends BaseVO {
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

    // 组织编号 : 必填,支持查询
    private String orgId = new String();

    // 名称 : 必填,支持查询
    private String name = new String();

    // 开始时间 : 必填,时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();

    // 结束时间 : 必填
    @DataAdapter(fieldType = FieldType.DATE)
    private String endTime = new String();

    // 地点 : 必填,支持查询
    private String address = new String();

    // 参与人员 : 必填,支持查询
    private String participant = new String();

    // 会议报批金额 : 必填
    private double money = Double.MAX_VALUE;

    // 申请部门负责人编号
    private String departmentLeaderId = new String();

    // 申请部门负责人时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentLeaderTime = new String();

    // 申请部门负责人意见 : KV下拉菜单【OA_WFAPassType】
    private String departmentLeaderCommentTypeId = new String();

    // 所属公司总经理编号
    private String generalManagerId = new String();

    // 所属公司总经理时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String generalManagerTime = new String();

    // 所属公司总经理意见 : KV下拉菜单【OA_WFAPassType】
    private String generalManagerCommentTypeId = new String();

    private String DepartmentLeaderCommentTypeName = new String();

    private String GeneralManagerCommentTypeName = new String();
    private String HQFinanceDirectorCommentTypeName = new String();
    private String HQLeaderCommentTypeIdName = new String();
    private String HQCEOCommentTypeName = new String();


    public String getDepartmentLeaderCommentTypeName() {
        return DepartmentLeaderCommentTypeName;
    }

    public void setDepartmentLeaderCommentTypeName(String departmentLeaderCommentTypeName) {
        DepartmentLeaderCommentTypeName = departmentLeaderCommentTypeName;
    }

    public String getGeneralManagerCommentTypeName() {
        return GeneralManagerCommentTypeName;
    }

    public void setGeneralManagerCommentTypeName(String generalManagerCommentTypeName) {
        GeneralManagerCommentTypeName = generalManagerCommentTypeName;
    }

    public String getHQFinanceDirectorCommentTypeName() {
        return HQFinanceDirectorCommentTypeName;
    }

    public void setHQFinanceDirectorCommentTypeName(String HQFinanceDirectorCommentTypeName) {
        this.HQFinanceDirectorCommentTypeName = HQFinanceDirectorCommentTypeName;
    }

    public String getHQLeaderCommentTypeIdName() {
        return HQLeaderCommentTypeIdName;
    }

    public void setHQLeaderCommentTypeIdName(String HQLeaderCommentTypeIdName) {
        this.HQLeaderCommentTypeIdName = HQLeaderCommentTypeIdName;
    }

    public String getHQCEOCommentTypeName() {
        return HQCEOCommentTypeName;
    }

    public void setHQCEOCommentTypeName(String HQCEOCommentTypeName) {
        this.HQCEOCommentTypeName = HQCEOCommentTypeName;
    }

    // 总公司财务总监编号
    private String hQFinanceDirectorId = new String();

    // 总公司财务总监时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String hQFinanceDirectorTime = new String();

    // 总公司财务总监意见 : KV下拉菜单【OA_WFAPassType】
    private String hQFinanceDirectorCommentTypeId = new String();

    // 总公司分管领导编号
    private String hQLeaderId = new String();

    // 总公司分管领导时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String hQLeaderTime = new String();

    // 总公司分管领导意见 : KV下拉菜单【OA_WFAPassType】
    private String hQLeaderCommentTypeId = new String();

    // 总公司执行董事编号
    private String hQCEOId = new String();

    // 总公司执行董事时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String hQCEOTime = new String();

    // 总公司执行董事意见 : KV下拉菜单【OA_WFAPassType】
    private String hQCEOCommentTypeId = new String();

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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDepartmentLeaderId() {
        return departmentLeaderId;
    }

    public void setDepartmentLeaderId(String departmentLeaderId) {
        this.departmentLeaderId = departmentLeaderId;
    }

    public String getDepartmentLeaderTime() {
        return departmentLeaderTime;
    }

    public void setDepartmentLeaderTime(String departmentLeaderTime) {
        this.departmentLeaderTime = departmentLeaderTime;
    }

    public String getDepartmentLeaderCommentTypeId() {
        return departmentLeaderCommentTypeId;
    }

    public void setDepartmentLeaderCommentTypeId(String departmentLeaderCommentTypeId) {
        this.departmentLeaderCommentTypeId = departmentLeaderCommentTypeId;
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

    public String getGeneralManagerCommentTypeId() {
        return generalManagerCommentTypeId;
    }

    public void setGeneralManagerCommentTypeId(String generalManagerCommentTypeId) {
        this.generalManagerCommentTypeId = generalManagerCommentTypeId;
    }

    public String getHQFinanceDirectorId() {
        return hQFinanceDirectorId;
    }

    public void setHQFinanceDirectorId(String hQFinanceDirectorId) {
        this.hQFinanceDirectorId = hQFinanceDirectorId;
    }

    public String getHQFinanceDirectorTime() {
        return hQFinanceDirectorTime;
    }

    public void setHQFinanceDirectorTime(String hQFinanceDirectorTime) {
        this.hQFinanceDirectorTime = hQFinanceDirectorTime;
    }

    public String getHQFinanceDirectorCommentTypeId() {
        return hQFinanceDirectorCommentTypeId;
    }

    public void setHQFinanceDirectorCommentTypeId(String hQFinanceDirectorCommentTypeId) {
        this.hQFinanceDirectorCommentTypeId = hQFinanceDirectorCommentTypeId;
    }

    public String getHQLeaderId() {
        return hQLeaderId;
    }

    public void setHQLeaderId(String hQLeaderId) {
        this.hQLeaderId = hQLeaderId;
    }

    public String getHQLeaderTime() {
        return hQLeaderTime;
    }

    public void setHQLeaderTime(String hQLeaderTime) {
        this.hQLeaderTime = hQLeaderTime;
    }

    public String getHQLeaderCommentTypeId() {
        return hQLeaderCommentTypeId;
    }

    public void setHQLeaderCommentTypeId(String hQLeaderCommentTypeId) {
        this.hQLeaderCommentTypeId = hQLeaderCommentTypeId;
    }

    public String getHQCEOId() {
        return hQCEOId;
    }

    public void setHQCEOId(String hQCEOId) {
        this.hQCEOId = hQCEOId;
    }

    public String getHQCEOTime() {
        return hQCEOTime;
    }

    public void setHQCEOTime(String hQCEOTime) {
        this.hQCEOTime = hQCEOTime;
    }

    public String getHQCEOCommentTypeId() {
        return hQCEOCommentTypeId;
    }

    public void setHQCEOCommentTypeId(String hQCEOCommentTypeId) {
        this.hQCEOCommentTypeId = hQCEOCommentTypeId;
    }
}
