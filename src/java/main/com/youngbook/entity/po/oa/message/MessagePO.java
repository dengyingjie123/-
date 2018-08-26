package com.youngbook.entity.po.oa.message;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2014/12/3.
 */

@Table(name="oa_message" , jsonPrefix = "message")
public class MessagePO extends BasePO {

    @Id
    private int sid = Integer.MAX_VALUE;

    private String id="";

    //主题
    private String Subject="";

    //内容
    private String Content="";

    //发送者编号
    private String SenderId="";

    //操作者Id
    private String OperatorId;

    //操作时间
    @DataAdapter(fieldType=FieldType.DATE)
    private String OperateTime;



    //发送时间
    @DataAdapter(fieldType= FieldType.DATE)
    private String FromTime;

    //接收者编号
    private String ReceiverId="";

    //接收时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String ReceiveTime;

    //类型
    private int IsRead=Integer.MAX_VALUE;

    //优先级
    private int Priority=Integer.MAX_VALUE;


    //状态
    private int state = Integer.MAX_VALUE;

    private int Type = Integer.MAX_VALUE;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
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


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int isRead) {
        IsRead = isRead;
    }

    @Override
    public String toString() {
        return "MessagePO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", Subject='" + Subject + '\'' +
                ", Content='" + Content + '\'' +
                ", SenderId='" + SenderId + '\'' +
                ", OperatorId='" + OperatorId + '\'' +
                ", OperateTime='" + OperateTime + '\'' +
                ", FromTime='" + FromTime + '\'' +
                ", ReceiverId='" + ReceiverId + '\'' +
                ", ReceiveTime='" + ReceiveTime + '\'' +
                ", IsRead=" + IsRead +
                ", Priority=" + Priority +
                ", state=" + state +
                ", Type=" + Type +
                '}';
    }
}
