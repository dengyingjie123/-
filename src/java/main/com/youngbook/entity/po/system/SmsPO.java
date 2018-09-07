package com.youngbook.entity.po.system;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/24/15
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "System_Sms", jsonPrefix = "sms")
public class SmsPO extends BasePO {

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

    // 主题
    private String subject = new String();

    // 内容
    private String content = "";

    private String signature = "";


    // 类型 : SmsType类
    private int type = Integer.MAX_VALUE;

    // 发送者编号
    private String senderId = new String();

    //发送者姓名
    private String senderName = new String();

    //发送者手机
    private String senderMobile = new String();

    // 等待发送时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String waitingTime = "";

    // 发送时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String sendTime = new String();

    // 接收者编号
    private String receiverId = new String();

    // 接收者名称
    private String receiverName = new String();

    // 接收者手机
    private String receiverMobile = new String();

    // 接收时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String receiveTime = new String();

    // 反馈状态 : 取值【0：未发送，1：已发送，2：发送确认，3：发送失败】
    private int feedbackStatus = Integer.MAX_VALUE;

    // 反馈时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String feedbackTime = new String();

    // 反愧内容
    private String feedbackContent = new String();

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(String waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getFeedbackContent() {return feedbackContent;}

    public void setFeedbackContent(String feedbackContent) {this.feedbackContent = feedbackContent;}

    public String getSenderMobile() {return senderMobile;}

    public void setSenderMobile(String senderMobile) {this.senderMobile = senderMobile;}

    public String getSenderName() {return senderName; }

    public void setSenderName(String senderName) { this.senderName = senderName;}

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(int feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    @Override
    public String toString() {
        return "SmsPO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", senderId='" + senderId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderMobile='" + senderMobile + '\'' +
                ", waitingTime='" + waitingTime + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", feedbackStatus=" + feedbackStatus +
                ", feedbackTime='" + feedbackTime + '\'' +
                ", feedbackContent='" + feedbackContent + '\'' +
                '}';
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
