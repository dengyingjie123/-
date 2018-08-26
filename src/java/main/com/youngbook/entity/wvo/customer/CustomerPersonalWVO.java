package com.youngbook.entity.wvo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.wvo.BaseWVO;

/**
 * Created by Jepson on 2015/7/8.
 */
@Table(name = "crm_customerpersonal_vo", jsonPrefix = "personalVO")
public class CustomerPersonalWVO extends BaseWVO{
    @Id
    private int sid = Integer.MAX_VALUE;
    // id
    private String id = new String();

    // 姓名 : 支持查询,必填
    private String name = new String();

    // 性别 : 下拉菜单,必填
    private String gender =  new String();
    private String sex =  new String();


    // 出生日期 : 日期类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String birthday = new String();

    // 移动电话 : 支持查询,必填
    private String mobile = new String();

    // 固定电话
    private String phone = new String();

    // 地址 : 支持查询,必填
    private String address = new String();

    // 邮编
    private String postNo = new String();

    // 电子邮箱
    private String email = new String();

    // 冻结资本
    private double frozenMoney = Double.MAX_VALUE;

    // 可用资本
    private double availableMoney = Double.MAX_VALUE;

    // 创建时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String creatTime = new String();

    private String saleManId = "";
    private String saleManName = "";

    private String distributionStatus = "";

    // 交易密码
    private String transactionPassword = new String();

    // 产品名称
    private String productionName = new String();

    public String getTransactionPassword() {return transactionPassword;}

    public void setTransactionPassword(String transactionPassword) {this.transactionPassword = transactionPassword;}

    public String getSaleManId() {
        return saleManId;
    }
    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public String getDistributionStatus() {
        return distributionStatus;
    }
    public void setDistributionStatus(String distributionStatus) {
        this.distributionStatus = distributionStatus;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getSaleManName() {
        return saleManName;
    }
    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
    }

    public double getAvailableMoney() {return availableMoney;}
    public void setAvailableMoney(double availableMoney) {this.availableMoney = availableMoney;}

    public double getFrozenMoney() {return frozenMoney;}
    public void setFrozenMoney(double frozenMoney) {this.frozenMoney = frozenMoney;}

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }
}
