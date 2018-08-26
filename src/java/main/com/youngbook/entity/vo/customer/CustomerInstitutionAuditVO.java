package com.youngbook.entity.vo.customer;


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
public class CustomerInstitutionAuditVO extends BaseVO{

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // 姓名 : 支持查询,必填
    private String name = new String();

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

     // 电子邮箱
    private String email = new String();

    //销售员
    private String salesmanName=new String();

    //销售员ID
    private String saleManId=new String();

    //审核状态数字
    private int status=Integer.MAX_VALUE;

    //审核状态
    private String auditStatus=new String();

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
}
