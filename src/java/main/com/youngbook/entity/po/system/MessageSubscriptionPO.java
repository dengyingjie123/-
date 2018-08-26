package com.youngbook.entity.po.system;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;
import com.youngbook.annotation.Id;
/**
 * Created by Jepson on 2015/6/25.
 */
@Table(name = "system_MessageSubscription", jsonPrefix = "messageSubscription")
public class MessageSubscriptionPO extends BasePO{

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

    // 用户编号
    private String userId = new String();

    // 消息类型编号
    private String messageTypeId = new String();

    // 邮件提醒
    private int isEmail = Integer.MAX_VALUE;
    // 短信提醒
    private int isSms = Integer.MAX_VALUE;
    // 系统代办提醒
    private int isTodoList = Integer.MAX_VALUE;


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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(String messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public int getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(int isEmail) {
        this.isEmail = isEmail;
    }
    public int getIsSms() {
        return isSms;
    }

    public void setIsSms(int isSms) {
        this.isSms = isSms;
    }

    public int getIsTodoList() {
        return isTodoList;
    }

    public void setIsTodoList(int isTodoList) {
        this.isTodoList = isTodoList;
    }
}
