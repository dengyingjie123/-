package com.youngbook.entity.vo.system;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Jepson on 2015/6/25.
 */
@Table(name = "system_MessageSubscription", jsonPrefix = "messageSubscription")
public class MessageSubscriptionVO extends BaseVO{

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
    private String messageTypeName = new String();

    // 邮件提醒
    private int isEmail = Integer.MAX_VALUE;
    private String emailName = new String();
    // 短信提醒
    private int isSms = Integer.MAX_VALUE;
    private String smsName = new String();
    // 系统代办提醒
    private int isTodoList = Integer.MAX_VALUE;

    private String todoListName = new String();

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

    public String getSmsName() {
        return smsName;
    }

    public void setSmsName(String smsName) {
        this.smsName = smsName;
    }

    public String getTodoListName() {
        return todoListName;
    }

    public void setTodoListName(String todoListName) {
        this.todoListName = todoListName;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }
    public int getIsSms() {
        return isSms;
    }

    public String getMessageTypeName() {
        return messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
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
