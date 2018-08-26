package com.youngbook.entity.vo.oa.assetInfo;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by haihong on 2015/6/2.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */
@Table(name = "OA_AssetInfo", jsonPrefix = "assetInfoVO")
public class AssetInfoVO extends BaseVO {
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

    // 名称 : 支持查询,必填
    private String name = new String();

    // 所属部门 : 支持查询,必填
    private String departmentName= new String ();
    private String departmentId = new String();

    // 申购用途
    private String purpose = new String();

    // 规格型号 : 支持查询,
    private String specification = new String();

    // 数量 : 验类型【整数】
    private double quanity = Double.MAX_VALUE;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }


}
