package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

@Table(name = "crm_saleman_outer", jsonPrefix = "salemanOuter")
public class SalemanOuterVO extends BaseVO {

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

    // createTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();

    // username
    private String username = new String();

    // password
    private String password = new String();

    // name
    private String name = new String();

    // sex
    private int sex = Integer.MAX_VALUE;

    // birthday
    @DataAdapter(fieldType = FieldType.DATE)
    private String birthday = new String();

    // mobile
    private String mobile = new String();

    // homeAddress
    private String homeAddress = new String();

    // workAddress
    private String workAddress = new String();

    // email
    private String email = new String();

    // industry
    private String industry = new String();

    // industry 中文描述
    private String industryText = new String();

    // status
    private String status = new String();

    // 待兑付的佣金
    private double prePayCommissionMoney = Double.MAX_VALUE;

    // 总兑付佣金
    private double totalCommissionMoney = Double.MAX_VALUE;

    // 推荐人
    private String referralCode = new String();

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
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

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndustryText() {
        return industryText;
    }

    public void setIndustryText(String industryText) {
        this.industryText = industryText;
    }

    public double getPrePayCommissionMoney() {
        return prePayCommissionMoney;
    }

    public void setPrePayCommissionMoney(double prePayCommissionMoney) {
        this.prePayCommissionMoney = prePayCommissionMoney;
    }

    public double getTotalCommissionMoney() {
        return totalCommissionMoney;
    }

    public void setTotalCommissionMoney(double totalCommissionMoney) {
        this.totalCommissionMoney = totalCommissionMoney;
    }
}
