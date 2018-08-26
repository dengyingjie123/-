package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/10/14
 * Time: 8:14 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_customerpersonal_vo", jsonPrefix = "personalVO")
public class CustomerPersonalAuditVO extends BaseVO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    private String personalNumber = "";

    // 姓名 : 支持查询,必填
    private String name = new String();

    // 性别 : 下拉菜单,必填
    private String sex = new String();

    // 出生日期 : 日期类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String birthday = new String();

    // 移动电话 : 支持查询,必填
    private String mobile = new String();

    // 固定电话
    private String phone = new String();

    // 地址 : 支持查询,必填
    private String identityCardAddress = new String();

    // 邮编
    private String postNo = new String();

    // 电子邮箱
    private String email = new String();

    private String createTime = "";

    //销售员
    private String salesmanName = new String();

    //销售员ID
    private String saleManId = new String();

    //审核状态
    private String auditStatus = new String();

    //审核状态数字
    private int status = Integer.MAX_VALUE;

    private String groupName = "";

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityCardAddress() {
        return identityCardAddress;
    }

    public void setIdentityCardAddress(String identityCardAddress) {
        this.identityCardAddress = identityCardAddress;
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

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
