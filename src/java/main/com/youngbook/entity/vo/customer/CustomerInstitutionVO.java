package com.youngbook.entity.vo.customer;


import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-15
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "crm_customerinstitution_vo", jsonPrefix = "institutionVO")
public class CustomerInstitutionVO extends BaseVO{


    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // 姓名 : 支持查询,必填
    private String name = new String();

    // 性质 : 支持查询,必填
    private String type = new String();

    // 法人
    private String legalPerson = new String();

    // 注册资本
    private double registeredCapital = Double.MAX_VALUE;

    // 移动电话 : 支持查询,必填
    private String mobile = new String();

    // 固定电话
    private String phone = new String();

    // 注册地址 : 支持查询,必填
    private String address = new String();

    // 邮编
    private String postNo = new String();

    // 电子邮箱
    private String email = new String();
    //审批状态
    private String distributionStatus = new String();

    //销售人员相关
    private String saleManName = new String();
    private String saleManId = new String();
    private String groupName = new String();
    //用于KV中显示融资机构名称
    private String text = new String();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(String distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public String getSaleManName() {
        return saleManName;
    }

    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
