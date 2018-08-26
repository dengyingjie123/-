package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/13/15
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_customerfeedback", jsonPrefix = "customerFeedback")
public class CustomerFeedbackVO extends BaseVO {
    // id
    @Id
    private String id = new String();

    // state
    private String typeName = "";
    private String customerName = "";
    private String customerId = "";

    private String content = "";
    private String feedbackManName = "";
    @DataAdapter(fieldType = FieldType.DATE)
    private String time = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeedbackManName() {
        return feedbackManName;
    }

    public void setFeedbackManName(String feedbackManName) {
        this.feedbackManName = feedbackManName;
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

    @Override
    public String toString() {
        return "CustomerFeedbackVO{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerId='" + customerId + '\'' +
                ", content='" + content + '\'' +
                ", feedbackManName='" + feedbackManName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
