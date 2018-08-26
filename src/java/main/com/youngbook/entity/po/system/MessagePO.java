package com.youngbook.entity.po.system;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by fugy on 2016/3/11.
 */
@Table(name = "system_systemmessage", jsonPrefix = "message")
public class MessagePO extends BasePO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // 操作人
    private String operatorId = new String();

    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 标题 : 支持查询,必填
    private String title = new String();

    //内容
    private String content = new String();

    //发送到
    private String sendTo = new String();

    //部门id
    private String departmentIds = new String();

    //员工id
    private String staffIds = new String();

    //消息类型
    private String type = new String();

    //消息级别
    private String level = new String();

    //发布时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String publishedTime = new String();

    //是否置顶
    private String isTop = new String();

    //发送范围
    private String sendRange = new String();

    //消息类型
    private String status = new String();

    //排序
    private int orders = Integer.MAX_VALUE;

    //是否回执
    private String isReceipt = new String();

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
    }

    public String getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(String staffIds) {
        this.staffIds = staffIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getSendRange() {
        return sendRange;
    }

    public void setSendRange(String sendRange) {
        this.sendRange = sendRange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(String isReceipt) {
        this.isReceipt = isReceipt;
    }

    @Override
    public String toString() {
        return "MessagePO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendTo='" + sendTo + '\'' +
                ", departmentIds='" + departmentIds + '\'' +
                ", staffIds='" + staffIds + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", publishedTime='" + publishedTime + '\'' +
                ", isTop='" + isTop + '\'' +
                ", sendRange='" + sendRange + '\'' +
                ", status='" + status + '\'' +
                ", orders=" + orders +
                ", isReceipt='" + isReceipt + '\'' +
                '}';
    }
}
