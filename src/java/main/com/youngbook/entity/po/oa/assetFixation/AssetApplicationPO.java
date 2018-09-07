package com.youngbook.entity.po.oa.assetFixation;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Jepson on 2015/6/5.
 */
@Table(name = "oa_AssetApplication", jsonPrefix = "assetApplication")
public class AssetApplicationPO extends BasePO {

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

    //产品产品
    private String productName = new String();

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // 资产类型 : 支持查询
    private String assetTypeId = new String();
    // 部门编号 : 支持查询
    private String departmentId = new String();
    private String applicationDepartmentId = new String();//申请部门
    // 请购人 : 支持查询
    private String applicantId = new String();
    //请购时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String assApplicantTime = new String();
    // 申请原因 : 支持查询
    private String purpose = new String();
    // 部门负责人编号 : 支持查询
    private String departmentHeadrId = new String();
    // 部门负责人意见 : KV下拉菜单【AssetApplicationType】
    private String departmentHeaderCommentType = new String();
    // 部门负责人时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentHeaderTime = new String();
    // 部门领导人编号 : 支持查询
    private String departmentLeaderId = new String();
    // 部门领导人意见 : KV下拉菜单【AssetApplicationType】
    private String departmentLeaderCommentType = new String();
    // 部门领导人时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentLeaderTime = new String();
    // 人力行政部门负责人编号 : 支持查询
    private String administrativeDepartmentHeadId = new String();
    // 人力行政部门负责人意见 : KV下拉菜单【AssetApplicationType】
    private String administrativeDepartmentHeadCommentType = new String();
    // 人力行政部门负责人时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String administrativeDepartmentHeadTime = new String();
    // 财务部门负责人编号 : 支持查询
    private String financeDepartmentHeadId = new String();
    // 财务部门负责人意见 : KV下拉菜单【AssetApplicationType】
    private String financeDepartmentHeadCommentType = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String financeDepartmentHeadTime = new String();
    // 验收部门负责人时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String technicalDepartmentHeadTime = new String();

    //z总金额
    private Double Moneys = Double.MAX_VALUE;

    public String getFinanceDepartmentHeadTime() {
        return financeDepartmentHeadTime;
    }

    public void setFinanceDepartmentHeadTime(String financeDepartmentHeadTime) {
        this.financeDepartmentHeadTime = financeDepartmentHeadTime;
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

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDepartmentHeadrId() {
        return departmentHeadrId;
    }

    public void setDepartmentHeadrId(String departmentHeadrId) {
        this.departmentHeadrId = departmentHeadrId;
    }

    public String getDepartmentHeaderCommentType() {
        return departmentHeaderCommentType;
    }

    public void setDepartmentHeaderCommentType(String departmentHeaderCommentType) {
        this.departmentHeaderCommentType = departmentHeaderCommentType;
    }

    public String getDepartmentHeaderTime() {
        return departmentHeaderTime;
    }

    public void setDepartmentHeaderTime(String departmentHeaderTime) {
        this.departmentHeaderTime = departmentHeaderTime;
    }

    public String getDepartmentLeaderId() {
        return departmentLeaderId;
    }

    public void setDepartmentLeaderId(String departmentLeaderId) {
        this.departmentLeaderId = departmentLeaderId;
    }

    public String getDepartmentLeaderCommentType() {
        return departmentLeaderCommentType;
    }

    public void setDepartmentLeaderCommentType(String departmentLeaderCommentType) {
        this.departmentLeaderCommentType = departmentLeaderCommentType;
    }

    public String getDepartmentLeaderTime() {
        return departmentLeaderTime;
    }

    public void setDepartmentLeaderTime(String departmentLeaderTime) {
        this.departmentLeaderTime = departmentLeaderTime;
    }

    public String getAdministrativeDepartmentHeadId() {
        return administrativeDepartmentHeadId;
    }

    public void setAdministrativeDepartmentHeadId(String administrativeDepartmentHeadId) {
        this.administrativeDepartmentHeadId = administrativeDepartmentHeadId;
    }

    public String getAdministrativeDepartmentHeadCommentType() {
        return administrativeDepartmentHeadCommentType;
    }

    public void setAdministrativeDepartmentHeadCommentType(String administrativeDepartmentHeadCommentType) {
        this.administrativeDepartmentHeadCommentType = administrativeDepartmentHeadCommentType;
    }

    public String getAdministrativeDepartmentHeadTime() {
        return administrativeDepartmentHeadTime;
    }

    public void setAdministrativeDepartmentHeadTime(String administrativeDepartmentHeadTime) {
        this.administrativeDepartmentHeadTime = administrativeDepartmentHeadTime;
    }

    public String getFinanceDepartmentHeadId() {
        return financeDepartmentHeadId;
    }

    public void setFinanceDepartmentHeadId(String financeDepartmentHeadId) {
        this.financeDepartmentHeadId = financeDepartmentHeadId;
    }

    public String getFinanceDepartmentHeadCommentType() {
        return financeDepartmentHeadCommentType;
    }

    public void setFinanceDepartmentHeadCommentType(String financeDepartmentHeadCommentType) {
        this.financeDepartmentHeadCommentType = financeDepartmentHeadCommentType;
    }

    public String getTechnicalDepartmentHeadTime() {
        return technicalDepartmentHeadTime;
    }

    public void setTechnicalDepartmentHeadTime(String technicalDepartmentHeadTime) {
        this.technicalDepartmentHeadTime = technicalDepartmentHeadTime;
    }

    public Double getMoneys() {
        return Moneys;
    }

    public void setMoneys(Double moneys) {
        Moneys = moneys;
    }

    public String getApplicationDepartmentId() {
        return applicationDepartmentId;
    }

    public void setApplicationDepartmentId(String applicationDepartmentId) {
        this.applicationDepartmentId = applicationDepartmentId;
    }

    public String getAssApplicantTime() {
        return assApplicantTime;
    }

    public void setAssApplicantTime(String assApplicantTime) {
        this.assApplicantTime = assApplicantTime;
    }


}
