package com.youngbook.entity.vo.oa.assetFixation;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Jepson on 2015/6/8.
 */
public class AssetItemVO extends BasePO {

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
    // 申请编号 : 主表关系【AssetApplication,ApplicationId,id】,支持查询
    private String applicationId = new String();

    // 名称 : 支持查询,必填
    private String name = new String();
// 所属部门 : 支持查询,必填
private String departmentId = new String();

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }


    // 申购用途 : 支持查询
    private String tusage = new String();

    public String getTusage() {
        return tusage;
    }

    public void setTusage(String tusage) {
        this.tusage = tusage;
    }

    // 规格型号 : 支持查询
    private String specification = new String();
    // 数量 : 验类型【整数】
    private double quanity = Double.MAX_VALUE;
    // 预计单价 : 支持查询
    private double expectedUnitPrice = Double.MAX_VALUE;
    // 预计金额 : 支持查询
    private double expectedMoney = Double.MAX_VALUE;
    // 单价 : 支持查询,数字范围查询,验类型【浮点数】
    private double unitPrice = Double.MAX_VALUE;
    // 金额 : 支持查询,,数字范围查询,验类型【浮点数】
    private double money = Double.MAX_VALUE;
    // 采购时间 : 支持查询,日期类型,时间段查询,必填
    @DataAdapter(fieldType = FieldType.DATE)
    private String buyTime = new String();
    // 存放地点 : 支持查询,必填
    private String storagePlace = new String();
    // 保管人编号 : 支持查询
    private String keeperId = new String();

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

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public double getQuanity() {
        return quanity;
    }

    public void setQuanity(double quanity) {
        this.quanity = quanity;
    }

    public double getExpectedUnitPrice() {
        return expectedUnitPrice;
    }

    public void setExpectedUnitPrice(double expectedUnitPrice) {
        this.expectedUnitPrice = expectedUnitPrice;
    }

    public double getExpectedMoney() {
        return expectedMoney;
    }

    public void setExpectedMoney(double expectedMoney) {
        this.expectedMoney = expectedMoney;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getStoragePlace() {
        return storagePlace;
    }

    public void setStoragePlace(String storagePlace) {
        this.storagePlace = storagePlace;
    }

    public String getKeeperId() {
        return keeperId;
    }

    public void setKeeperId(String keeperId) {
        this.keeperId = keeperId;
    }
}