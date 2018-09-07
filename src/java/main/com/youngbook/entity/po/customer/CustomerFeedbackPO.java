package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/13/15
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_customerfeedback", jsonPrefix = "customerFeedback")
public class CustomerFeedbackPO extends BasePO {

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

    private String typeId = "";

    private String content = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String time = "";

    private String customerId = "";

    private String customerName = "";

    private String feedbackManId = "";

    private String bizId = "";

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFeedbackManId() {
        return feedbackManId;
    }

    public void setFeedbackManId(String feedbackManId) {
        this.feedbackManId = feedbackManId;
    }

    @Override
    public String toString() {
        return "CustomerFeedbackPO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", typeId='" + typeId + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", feedbackManId='" + feedbackManId + '\'' +
                '}';
    }
}
