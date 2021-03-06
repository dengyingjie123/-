package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2015/4/7.
 */
@Table(name = "CRM_CustomerAuthenticationStatus", jsonPrefix = "customerAuthenticationStatus")
public class CustomerAuthenticationStatusPO extends BasePO {
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
}
