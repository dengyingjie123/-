package com.youngbook.entity.po.system;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2015/4/22.
 * 系统管理
 */

@Table(name = "System_Token", jsonPrefix = "token")
public class TokenPO extends BasePO {
    /**
     * id
     */
    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;

    private String id = new String();

    private int state = Integer.MAX_VALUE;
    private String operatorId = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = "";

    /**
     * code
     */
    private String token = new String();

    private String checkCode = "";

    /**
     * 创建时间 : 时间段查询
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();

    /**
     * 失效时间
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String expiredTime = new String();

    /**
     * 使用时间 : 时间段查询
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String usedTime = new String();

    /**
     * 使用编号 : 支持查询
     */
    private String bizId = new String();


    /**
     * 用户类型：0：客户；1：销售；10：短信验证码
     */
    private String bizType = "";

    /**
     * 使用者IP : 支持查询
     */
    private String ip = new String();

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
