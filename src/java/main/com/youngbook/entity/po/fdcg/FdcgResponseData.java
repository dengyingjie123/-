package com.youngbook.entity.po.fdcg;

import com.youngbook.annotation.*;

/**
 * Created by Lee on 2/27/2018.
 */
@Table(name = "fdcg_response_data", jsonPrefix = "responseData")
public class FdcgResponseData {

    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;


    private String id = "";
    //登录用户状态
    private int state = Integer.MAX_VALUE;
    //操作者Id
    private String operatorId = "";
    private String operatorName = "";


    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = "";

    private String name = "";

    private String retCode = "";
    private String retMsg = "";
    private String sign = "";
    private String certInfo = "";
    private String content = "";

    /**
     * 处理状态
     * 0：未处理
     * 1：已处理
     */
    private String dealStatus = "";

    /**
     * 0： 校验不通过
     * 1: 校验通过
     */
    private String verifyStatus = "";


    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCertInfo() {
        return certInfo;
    }

    public void setCertInfo(String certInfo) {
        this.certInfo = certInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
