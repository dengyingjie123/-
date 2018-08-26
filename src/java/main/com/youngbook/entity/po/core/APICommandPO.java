package com.youngbook.entity.po.core;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

@Table(name = "Core_APICommand", jsonPrefix = "APICommand")
public class APICommandPO extends BasePO {

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

    // APIType：通联支付、易宝支付等
    private String APIType = new String();

    // 接口名称
    private String APIName = new String();

    // 接口地址
    private String APIURL = new String();

    // 业务编号
    private String bizId = new String();

    // 指令
    private String commands = new String();

    // 指令格式：1:xml, 2:json, 3:url
    private int commandType = Integer.MAX_VALUE;

    /**
     * 状态 0: 未处理，1：处理成功，2：处理失败
     * 对于收到的反馈，需要处理，例如修改订单状态
     */
    private int status = Integer.MAX_VALUE;

    // 发送次数
    private int sendTimes = Integer.MAX_VALUE;

    // 成功次数
    private int successTimes = Integer.MAX_VALUE;

    // 失败次数
    private int failedTimes = Integer.MAX_VALUE;

    // 指令反馈代码
    private String callbackCode = new String();

    // 指令反馈消息
    private String callbackMessage = new String();


    // 保留字段
    private String remain01 = "";

    // 保留字段
    private String remain02 = "";

    // 保留字段
    private String remain03 = "";

    // 保留字段
    private String remain04 = "";

    // 保留字段
    private String remain05 = "";


    public String getRemain01() {
        return remain01;
    }

    public void setRemain01(String remain01) {
        this.remain01 = remain01;
    }

    public String getRemain02() {
        return remain02;
    }

    public void setRemain02(String remain02) {
        this.remain02 = remain02;
    }

    public String getRemain03() {
        return remain03;
    }

    public void setRemain03(String remain03) {
        this.remain03 = remain03;
    }

    public String getRemain04() {
        return remain04;
    }

    public void setRemain04(String remain04) {
        this.remain04 = remain04;
    }

    public String getRemain05() {
        return remain05;
    }

    public void setRemain05(String remain05) {
        this.remain05 = remain05;
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

    public String getAPIType() {
        return APIType;
    }

    public void setAPIType(String APIType) {
        this.APIType = APIType;
    }

    public String getAPIName() {
        return APIName;
    }

    public void setAPIName(String APIName) {
        this.APIName = APIName;
    }

    public String getAPIURL() {
        return APIURL;
    }

    public void setAPIURL(String APIURL) {
        this.APIURL = APIURL;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(int sendTimes) {
        this.sendTimes = sendTimes;
    }

    public int getSuccessTimes() {
        return successTimes;
    }

    public void setSuccessTimes(int successTimes) {
        this.successTimes = successTimes;
    }

    public int getFailedTimes() {
        return failedTimes;
    }

    public void setFailedTimes(int failedTimes) {
        this.failedTimes = failedTimes;
    }

    public String getCallbackCode() {
        return callbackCode;
    }

    public void setCallbackCode(String callbackCode) {
        this.callbackCode = callbackCode;
    }

    public String getCallbackMessage() {
        return callbackMessage;
    }

    public void setCallbackMessage(String callbackMessage) {
        this.callbackMessage = callbackMessage;
    }
}
