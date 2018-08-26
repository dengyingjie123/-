package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015-4-7.
 */

@Table(name = "CRM_CustomerAuthenticationCode", jsonPrefix = "customerAuthenticationCodeVO")
public class CustomerAuthenticationCodeVO extends BaseVO {

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
    // 客户编号
    private String customerId = new String();
    // 认证码
    private String code = new String();
    // 发送时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String sendTime = new String();
    // 发送类型 : KV下拉菜单【CRM_CustomerAuthenticationSendType】
    private String sendType = new String();
    // 过期时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String expiredTime = new String();
    // 认证时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String authenticationTime = new String();
    // 状态 : KV下拉菜单【CRM_CustomerAuthenticationStatus】
    private String status = new String();
    // 信息 : 支持查询
    private String info = new String();
    // 客户名称
    private String name = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String sendTimeStart = new String();
    @DataAdapter(fieldType = FieldType.DATE)
    private String sendTimeEnd = new String();
    @DataAdapter(fieldType = FieldType.DATE)
    private String expiredTimeStart = new String();
    @DataAdapter(fieldType = FieldType.DATE)
    private String expiredTimeEnd = new String();
    @DataAdapter(fieldType = FieldType.DATE)
    private String authenticationTimeStart = new String();
    @DataAdapter(fieldType = FieldType.DATE)
    private String authenticationTimeEnd = new String();

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getAuthenticationTime() {
        return authenticationTime;
    }

    public void setAuthenticationTime(String authenticationTime) {
        this.authenticationTime = authenticationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSendTimeStart() {
        return sendTimeStart;
    }

    public void setSendTimeStart(String sendTimeStart) {
        this.sendTimeStart = sendTimeStart;
    }

    public String getSendTimeEnd() {
        return sendTimeEnd;
    }

    public void setSendTimeEnd(String sendTimeEnd) {
        this.sendTimeEnd = sendTimeEnd;
    }

    public String getExpiredTimeStart() {
        return expiredTimeStart;
    }

    public void setExpiredTimeStart(String expiredTimeStart) {
        this.expiredTimeStart = expiredTimeStart;
    }

    public String getExpiredTimeEnd() {
        return expiredTimeEnd;
    }

    public void setExpiredTimeEnd(String expiredTimeEnd) {
        this.expiredTimeEnd = expiredTimeEnd;
    }

    public String getAuthenticationTimeStart() {
        return authenticationTimeStart;
    }

    public void setAuthenticationTimeStart(String authenticationTimeStart) {
        this.authenticationTimeStart = authenticationTimeStart;
    }

    public String getAuthenticationTimeEnd() {
        return authenticationTimeEnd;
    }

    public void setAuthenticationTimeEnd(String authenticationTimeEnd) {
        this.authenticationTimeEnd = authenticationTimeEnd;
    }
}
