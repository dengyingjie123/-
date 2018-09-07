package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

@Table(name = "crm_customerinstitution", jsonPrefix = "institution")
public class CustomerInstitutionPO extends BasePO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // 姓名 : 支持查询,必填
    private String name = new String();

    // 性质 : 支持查询,必填
    private int type = Integer.MAX_VALUE;

    // 法人
    private String legalPerson = new String();

    // 注册资本
    private double registeredCapital = Double.MAX_VALUE;

    // 移动电话 : 支持查询,必填
    private String mobile = new String();

    // 移动电话2
    private String mobile2 = new String();

    // 移动电话3
    private String mobile3 = new String();

    // 移动电话4
    private String mobile4 = new String();

    // 移动电话5
    private String mobile5 = new String();

    // 固定电话
    private String phone = new String();

    // 固定电话2
    private String phone2 = new String();

    // 固定电话3
    private String phone3 = new String();

    // 注册地址 : 支持查询,必填
    private String address = new String();

    // 邮编
    private String postNo = new String();

    // 电子邮箱
    private String email = new String();

    // 电子邮箱2
    private String email2 = new String();

    // 电子邮箱3
    private String email3 = new String();

    // 电子邮箱4
    private String email4 = new String();

    // 电子邮箱5
    private String email5 = new String();

    // 客户来源编号 : 下拉菜单,必填
    private String customerSourceId = new String();

    // 客户种类编号 : 下拉菜单,必填
    private String customerTypeId = new String();

    // 关系等级编号 : 下拉菜单,必填
    private String relationshipLevelId = new String();

    // 信用等级编号 : 下拉菜单,必填
    private String creditRateId = new String();

    // 所属行业编号 : 下拉菜单,必填
    private String careerId = new String();

    // 人员规模编号 : 下拉菜单,必填
    private String staffSizeId = new String();

    // 操作人
    private String operatorId = new String();

    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 人员ID
    private String institutionNumber = new String();

    // 创建时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();

    //密码
    private String password = new String();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public double getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(double registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getMobile3() {
        return mobile3;
    }

    public void setMobile3(String mobile3) {
        this.mobile3 = mobile3;
    }

    public String getMobile4() {
        return mobile4;
    }

    public void setMobile4(String mobile4) {
        this.mobile4 = mobile4;
    }

    public String getMobile5() {
        return mobile5;
    }

    public void setMobile5(String mobile5) {
        this.mobile5 = mobile5;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getEmail4() {
        return email4;
    }

    public void setEmail4(String email4) {
        this.email4 = email4;
    }

    public String getEmail5() {
        return email5;
    }

    public void setEmail5(String email5) {
        this.email5 = email5;
    }

    public String getCustomerSourceId() {
        return customerSourceId;
    }

    public void setCustomerSourceId(String customerSourceId) {
        this.customerSourceId = customerSourceId;
    }

    public String getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(String customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getRelationshipLevelId() {
        return relationshipLevelId;
    }

    public void setRelationshipLevelId(String relationshipLevelId) {
        this.relationshipLevelId = relationshipLevelId;
    }

    public String getCreditRateId() {
        return creditRateId;
    }

    public void setCreditRateId(String creditRateId) {
        this.creditRateId = creditRateId;
    }

    public String getCareerId() {
        return careerId;
    }

    public void setCareerId(String careerId) {
        this.careerId = careerId;
    }

    public String getStaffSizeId() {
        return staffSizeId;
    }

    public void setStaffSizeId(String staffSizeId) {
        this.staffSizeId = staffSizeId;
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

    public String getInstitutionNumber() {
        return institutionNumber;
    }

    public void setInstitutionNumber(String institutionNumber) {
        this.institutionNumber = institutionNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
