package com.youngbook.entity.po.customer;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 14-10-9
 * Time: 上午12:58
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_customerpersonal", jsonPrefix = "personal", backupTableName = "crm_customerpersonal_archive")
public class CustomerPersonalPO extends BasePO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // 姓名 : 支持查询,必填
    private String name = new String();

    // 登录名
    private String loginName = new String();

    // 性别 : 下拉菜单,必填
    private String sex = new String();

    // 国籍编号，如果是游客，则此值为UserInfoId
    private String nationId = new String();

    // 出生日期 : 日期类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String birthday = new String();

    // 移动电话 : 支持查询,必填
    private String mobile = new String();

    @IgnoreDB
    private String mobileNotMasked = new String();



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

    // 工作地址 : 支持查询,必填
    private String workAddress = new String();

    //家庭地址
    private String homeAddress = new String();

    //身份证地址
    private String identityCardAddress = new String();

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

    // 从事职业编号 : 下拉菜单,必填
    private String careerId = new String();

    /**
     * 客户分类
     * 0：临时客户，身份尚未确认
     * 1：正式客户，身份已确认
     */
    private String customerCatalogId = "";

    /**
     * 客户渠道属性
     * 0: 个人客户
     * 1：渠道客户
     */
    private String customerChannelTypeId = "";

    // 操作人
    private String operatorId = new String();

    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 人员ID
    private String personalNumber = new String();

    // 创建时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();

    //密码
    private String password = new String();

    // 交易密码
    private String transactionPassword = new String();

    //备注
    private String remark = new String();

    //登录失败
    private int loginFailureCount=Integer.MAX_VALUE;

    //最后登录时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String lastLoginTime=new String();

    //手势密码
    private String gesturePassword = new String();

    //手势密码是否启动
    private int gesturePasswordStatus=Integer.MAX_VALUE;

    // 推荐码
    private String referralCode = "";




    // 确认合格投资者
    private String confirmInvestor = new String();


    /**
     * 通联万小宝会员号
     */
    private String allinpayCircle_SignNum = "";


    public String getAllinpayCircle_SignNum() {
        return allinpayCircle_SignNum;
    }

    public void setAllinpayCircle_SignNum(String allinpayCircle_SignNum) {
        this.allinpayCircle_SignNum = allinpayCircle_SignNum;
    }


    public String getCustomerChannelTypeId() {
        return customerChannelTypeId;
    }

    public void setCustomerChannelTypeId(String customerChannelTypeId) {
        this.customerChannelTypeId = customerChannelTypeId;
    }

    public String getMobileNotMasked() {
        return mobileNotMasked;
    }

    public void setMobileNotMasked(String mobileNotMasked) {
        this.mobileNotMasked = mobileNotMasked;
    }

    public String getTransactionPassword() {
        return transactionPassword;
    }

    public void setTransactionPassword(String transactionPassword) {
        this.transactionPassword = transactionPassword;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationId() {
        return nationId;
    }

    public void setNationId(String nationId) {
        this.nationId = nationId;
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

    public int getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(int loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getIdentityCardAddress() {
        return identityCardAddress;
    }

    public void setIdentityCardAddress(String identityCardAddress) {
        this.identityCardAddress = identityCardAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
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

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGesturePassword() {
        return gesturePassword;
    }

    public void setGesturePassword(String gesturePassword) {
        this.gesturePassword = gesturePassword;
    }

    public int getGesturePasswordStatus() {
        return gesturePasswordStatus;
    }

    public void setGesturePasswordStatus(int gesturePasswordStatus) {
        this.gesturePasswordStatus = gesturePasswordStatus;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getConfirmInvestor() {
        return confirmInvestor;
    }

    public void setConfirmInvestor(String confirmInvestor) {
        this.confirmInvestor = confirmInvestor;
    }

    public String getCustomerCatalogId() {
        return customerCatalogId;
    }

    public void setCustomerCatalogId(String customerCatalogId) {
        this.customerCatalogId = customerCatalogId;
    }

    @Override
    public String toString() {
        return "CustomerPersonalPO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", loginName='" + loginName + '\'' +
                ", sex='" + sex + '\'' +
                ", nationId='" + nationId + '\'' +
                ", birthday='" + birthday + '\'' +
                ", mobile='" + mobile + '\'' +
                ", mobileNotMasked='" + mobileNotMasked + '\'' +
                ", mobile2='" + mobile2 + '\'' +
                ", mobile3='" + mobile3 + '\'' +
                ", mobile4='" + mobile4 + '\'' +
                ", mobile5='" + mobile5 + '\'' +
                ", phone='" + phone + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", phone3='" + phone3 + '\'' +
                ", workAddress='" + workAddress + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", identityCardAddress='" + identityCardAddress + '\'' +
                ", postNo='" + postNo + '\'' +
                ", email='" + email + '\'' +
                ", email2='" + email2 + '\'' +
                ", email3='" + email3 + '\'' +
                ", email4='" + email4 + '\'' +
                ", email5='" + email5 + '\'' +
                ", customerSourceId='" + customerSourceId + '\'' +
                ", customerTypeId='" + customerTypeId + '\'' +
                ", relationshipLevelId='" + relationshipLevelId + '\'' +
                ", creditRateId='" + creditRateId + '\'' +
                ", careerId='" + careerId + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", createTime='" + createTime + '\'' +
                ", password='" + password + '\'' +
                ", transactionPassword='" + transactionPassword + '\'' +
                ", remark='" + remark + '\'' +
                ", loginFailureCount=" + loginFailureCount +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", gesturePassword='" + gesturePassword + '\'' +
                ", gesturePasswordStatus=" + gesturePasswordStatus +
                ", referralCode='" + referralCode + '\'' +
                '}';
    }
}
