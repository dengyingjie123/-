package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015/4/7.
 */
@Table(name = "CRM_CustomerAuthenticationStatus", jsonPrefix = "customerAuthenticationStatus")
public class CustomerAuthenticationStatusVO extends BaseVO {
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

    // 客户编号 : 支持查询
    private String customerId = new String();

    // 手机认证状态 : 下拉菜单,支持查询
    private int mobileStatus = Integer.MAX_VALUE;

    // 手机认证时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String mobileTime = new String();

    // 邮箱认证状态 : 下拉菜单,支持查询
    private int emailStatus = Integer.MAX_VALUE;

    // 邮箱认证时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String emailTime = new String();

    // 账户认证状态 : 下拉菜单,支持查询
    private int accountStatus = Integer.MAX_VALUE;

    // 账户认证时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String accountTime = new String();

    // 安全问题认证状态 : 下拉菜单,支持查询
    private int qaStatus = Integer.MAX_VALUE;

    // 安全问题认证时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String qaTime = new String();

    // 视频认证状态 : 下拉菜单,支持查询
    private int videoStatus = Integer.MAX_VALUE;

    // 视频认证时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String videoTime = new String();

    // 现场认证状态 : 下拉菜单,支持查询
    private int faceStatus = Integer.MAX_VALUE;

    // 现场认证时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String faceTime = new String();

    //操作员
    private String operatorName = new String();

    //客户名称
    private String customerName = new String();

    private String mobile_Status = new String();

    private String email_Status = new String();

    private String account_Status = new String();

    private String qa_Status = new String();

    private String video_Status = new String();

    private String face_Status = new String();

    public String getMobile_Status() {
        return mobile_Status;
    }

    public void setMobile_Status(String mobile_Status) {
        this.mobile_Status = mobile_Status;
    }

    public String getEmail_Status() {
        return email_Status;
    }

    public void setEmail_Status(String email_Status) {
        this.email_Status = email_Status;
    }

    public String getAccount_Status() {
        return account_Status;
    }

    public void setAccount_Status(String account_Status) {
        this.account_Status = account_Status;
    }

    public String getQa_Status() {
        return qa_Status;
    }

    public void setQa_Status(String qa_Status) {
        this.qa_Status = qa_Status;
    }

    public String getVideo_Status() {
        return video_Status;
    }

    public void setVideo_Status(String video_Status) {
        this.video_Status = video_Status;
    }

    public String getFace_Status() {
        return face_Status;
    }

    public void setFace_Status(String face_Status) {
        this.face_Status = face_Status;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(int mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public String getMobileTime() {
        return mobileTime;
    }

    public void setMobileTime(String mobileTime) {
        this.mobileTime = mobileTime;
    }

    public int getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getEmailTime() {
        return emailTime;
    }

    public void setEmailTime(String emailTime) {
        this.emailTime = emailTime;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(String accountTime) {
        this.accountTime = accountTime;
    }

    public int getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(int qaStatus) {
        this.qaStatus = qaStatus;
    }

    public String getQaTime() {
        return qaTime;
    }

    public void setQaTime(String qaTime) {
        this.qaTime = qaTime;
    }

    public int getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(int videoStatus) {
        this.videoStatus = videoStatus;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public int getFaceStatus() {
        return faceStatus;
    }

    public void setFaceStatus(int faceStatus) {
        this.faceStatus = faceStatus;
    }

    public String getFaceTime() {
        return faceTime;
    }

    public void setFaceTime(String faceTime) {
        this.faceTime = faceTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
