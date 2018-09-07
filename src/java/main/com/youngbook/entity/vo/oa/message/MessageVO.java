package com.youngbook.entity.vo.oa.message;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2014/12/4.
 */
@Table(jsonPrefix = "messageVO")
public class MessageVO extends BaseVO {
    @Id
    private int sid = Integer.MAX_VALUE;

    private String id="";

    //状态
    private int state = Integer.MAX_VALUE;

    //主题
    private String Subject="";

    //内容
    private String Content="";

    //操作者Id
    private String OperatorId="";

    //操作时间
    @DataAdapter(fieldType=FieldType.DATE)
    private String OperateTime="";


    //发送者编号
    private String SenderId;

    //发送时间
    @DataAdapter(fieldType= FieldType.DATE)
    private String FromTime;

    //接收者编号
    private String ReceiverId;

    //优先级
    private String Priority ="";


    //接收时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String ReceiveTime;

    //类型
    private String IsRead = "";


    //信息类型
    private String Type="";



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

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }

    public String getReceiveTime() {
        return ReceiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        ReceiveTime = receiveTime;
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String operatorId) {
        OperatorId = operatorId;
    }

    public String getOperateTime() {
        return OperateTime;
    }

    public void setOperateTime(String operateTime) {
        OperateTime = operateTime;
    }

}
