package com.youngbook.entity.vo.system;

import com.youngbook.annotation.*;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015/4/10.
 */
@Table(name = "system_user", jsonPrefix = "userVO")
public class UserVO extends BaseVO {

    // ID
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
    //姓名
    private String name = new String();
    //密码
    @IgnoreJson
    private String password = new String();
    // 员工编码
    private String staffCode = new String();
    // 岗位K键
    private String positionTypeId = new String();
    private String positionType = new String();
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
    //性别
    private String genderName = new String();
    //生日
    @DataAdapter(fieldType = FieldType.DATE)
    private String birthday = new String();
    //入职日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String jointime = new String();
    //部门名称
    private String departmentName = new String();
    //部门编号
    private String departmentId = new String();
    //状态ID
    private String staffStatus = new String();
    // 离职日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String leftTime = new String();

    // 推荐码
    private String referralCode = new String();

    // 行业K键
    private String industry = new String();
    private String industryType = new String();

    // 销售类别K键
    private String UserType = new String();
    private String UserTypeStr = new String();

    // 待兑付的佣金
    private double prePayCommissionMoney = Double.MAX_VALUE;

    // 总兑付佣金
    private double totalCommissionMoney = Double.MAX_VALUE;

    // 微信号
    private String weChatId=new String();

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

    public String getId() {return id;}
    public void setId(String id) {
        this.id = id;
    }

    public String getPositionType() {
        return positionType;
    }
    public void setPositionType(String positionType) {
        this.positionType = positionType;
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
    public void setStaffCode(String staffCode) {this.staffCode = staffCode;}

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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    public String getGenderName() {
        return genderName;
    }
    public void setGenderName(String genderName) {
        this.genderName = genderName;
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

    public String getIdnumber() {
        return idnumber;
    }
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
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

    public String getStaffStatus() {return staffStatus;}
    public void setStaffStatus(String staffStatus) {this.staffStatus = staffStatus;}

    public String getLeftTime() {return leftTime;}
    public void setLeftTime(String leftTime) {this.leftTime = leftTime;}

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getUserTypeStr() {
        return UserTypeStr;
    }

    public void setUserTypeStr(String userTypeStr) {
        UserTypeStr = userTypeStr;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
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
