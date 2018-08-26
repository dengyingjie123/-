package com.youngbook.entity.vo.oa.assetFixation;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Jepson on 2015/6/8.
 */
@Table(name = "oa_AssetApplication", jsonPrefix = "assetApplication")
public class AssetApplicationVO extends BaseVO{
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
    //产品名称
    private String productName = new String();


    // 资产类型 : 支持查询
    private String assetTypeId = new String();

    private String assetTypeName = new String();

// 部门编号 : 支持查询
    private String departmentId = new String();
    private String departmentName = new String();

    // 请购人 : 支持查询
    private String applicantId = new String();
        private String applicantName = new String();
    //请购时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String assApplicantTime = new String();

    private String applicationDepartmentId= new String();//申请部门
    private String applicationDepartmentName= new String();//申请部门名称
    // 申请原因 : 支持查询
    private String purpose = new String();

    private double moneys= Double.MAX_VALUE;

    /**
     * 2015-6-17 周海鸿 添加了查询需要的元素，业务查询需要的元素
     */
    // 执行董事审核意见
    private String executiveDirectorContent = new String();

    // 分管领导审核意见
    private String chargeLeaderContent = new String();


    //用于控制业务流程
    private String currentNodeId = new String();
    private String currentNodeTitle = new String();

    private String routeListId = new String();
    private String currentStatus = new String();


    // 分管领导名称 : 支持查询
    private String chargeLeaderName = new String();
    private String chargeLeaderTime = new String();

    // 执行董事名称 : 支持查询
    private String executiveDirectorName = new String();
    private String executiveDirectorTime= new String();

    private String content1 = new String();
    private String content2 = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String time1 = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String time2 = new String();

    private String name1 = new String();
    private String name2= new String();


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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public double getMoneys() {
        return moneys;
    }

    public void setMoneys(double moneys) {
        this.moneys = moneys;
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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
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

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getApplicationDepartmentId() {
        return applicationDepartmentId;
    }

    public void setApplicationDepartmentId(String applicationDepartmentId) {
        this.applicationDepartmentId = applicationDepartmentId;
    }

    public String getApplicationDepartmentName() {
        return applicationDepartmentName;
    }

    public void setApplicationDepartmentName(String applicationDepartmentName) {
        this.applicationDepartmentName = applicationDepartmentName;
    }

    public String getCurrentNodeTitle() {
        return currentNodeTitle;
    }

    public void setCurrentNodeTitle(String currentNodeTitle) {
        this.currentNodeTitle = currentNodeTitle;
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

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getAssApplicantTime() {
        return assApplicantTime;
    }

    public void setAssApplicantTime(String assApplicantTime) {
        this.assApplicantTime = assApplicantTime;
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
