package com.youngbook.entity.po;


import com.youngbook.annotation.*;
import net.sf.json.JSONObject;

@Table(name = "system_user", jsonPrefix = "user")
public class UserPO extends BasePO {

    // 编号
    private String id = new String();
    // SID
    @Id
    private int sid = Integer.MAX_VALUE;
    // State
    private int state = Integer.MAX_VALUE;
    // 操作ID
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();
    //姓名、用户名
    private String name = new String();
    // 员工编码
    private String staffCode = new String();
    // 岗位K键
    private String positionTypeId = new String();
    //密码
    private String password = new String();
    //身份证
    private String idnumber = new String();
    //电话
    private String mobile = new String();
    //地址
    private String address = new String();
    //邮箱
    private String email = new String();
    //性别Id
    private String gender = new String();
    //生日
    @DataAdapter(fieldType = FieldType.DATE)
    private String birthday = new String();
    //入职日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String jointime = new String();
    //状态ID
    private String status = new String();
    // 离职日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String leftTime = new String();

    private String position = new String();
    //企业邮箱
    private String emailAccountName = new String();
    //企业箱密码
    private String emailAccountPassword = new String();

    // 推荐码
    private String referralCode = new String();


    //行业
    private String industry=new String();


    //销售人员类别
    /**
     *
     * 0: 内部员工，1：理财圈用户
     *
     */
    private String userType = "";

    //销售等级
    private String saleLevel=new String();

    // 微信号
    private String weChatId=new String();

    // 介绍
    private String description = "";


    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id", this.getId());
        json.element("text", this.getName());
        json.element("user", true);
        return json;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getPositionTypeId() {
        return positionTypeId;
    }

    public void setPositionTypeId(String positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmailAccountName() {
        return emailAccountName;
    }

    public void setEmailAccountName(String emailAccountName) {
        this.emailAccountName = emailAccountName;
    }

    public String getEmailAccountPassword() {
        return emailAccountPassword;
    }

    public void setEmailAccountPassword(String emailAccountPassword) {
        this.emailAccountPassword = emailAccountPassword;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSaleLevel() {
        return saleLevel;
    }

    public void setSaleLevel(String saleLevel) {
        this.saleLevel = saleLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserPO{" +
                "id='" + id + '\'' +
                ", sid=" + sid +
                ", state=" + state +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", idnumber='" + idnumber + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", jointime='" + jointime + '\'' +
                ", emailAccountName='" + emailAccountName + '\'' +
                ", emailAccountPassword='" + emailAccountPassword + '\'' +
                '}';
    }
}