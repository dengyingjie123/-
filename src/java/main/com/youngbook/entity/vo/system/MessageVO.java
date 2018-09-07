package com.youngbook.entity.vo.system;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by fugy on 2016/3/11.
 */
@Table(name = "system_message", jsonPrefix = "message")
public class MessageVO extends BaseVO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    //是否置顶
    private String isTop = new String();

    //紧急度
    private String level = new String();

    //发布时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String publishedTime = new String();

    //消息标题
    private String title = new String();

    //发布者
    private String senderName = new String();

    //发布者部门
    private String senderDepartmentName =  new String();

    //排序
    private String orders = new String();

    //状态
    private String isRead = new String();

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

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderDepartmentName() {
        return senderDepartmentName;
    }

    public void setSenderDepartmentName(String senderDepartmentName) {
        this.senderDepartmentName = senderDepartmentName;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}

