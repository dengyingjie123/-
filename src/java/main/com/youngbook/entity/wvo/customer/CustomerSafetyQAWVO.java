package com.youngbook.entity.wvo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.wvo.BaseWVO;

@Table(name = "CRM_CustomerSafetyQA", jsonPrefix = "customerSafetyQA")
public class CustomerSafetyQAWVO extends BaseWVO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 状态
    private int state = Integer.MAX_VALUE;

    // 客户编号
    private String customerId = new String();

    // 安全问题一编号
    private String q1Id = new String();

    // 安全问题一内容
    private String q1Content = new String();

    // 安全问题一答案
    private String a1 = new String();

    // 安全问题二编号
    private String q2Id = new String();

    // 安全问题二内容
    private String q2Content = new String();

    // 安全问题二答案
    private String a2 = new String();

    // 安全问题三编号
    private String q3Id = new String();

    // 安全问题三内容
    private String q3Content = new String();

    // 安全问题三答案
    private String a3 = new String();

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getQ1Id() {
        return q1Id;
    }

    public void setQ1Id(String q1Id) {
        this.q1Id = q1Id;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getQ2Id() {
        return q2Id;
    }

    public void setQ2Id(String q2Id) {
        this.q2Id = q2Id;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getQ3Id() {
        return q3Id;
    }

    public void setQ3Id(String q3Id) {
        this.q3Id = q3Id;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public int getState() {return state;}

    public void setState(int state) {this.state = state;}

    public String getQ1Content() {return q1Content;}

    public void setQ1Content(String q1Content) {this.q1Content = q1Content;}

    public String getQ2Content() {return q2Content;}

    public void setQ2Content(String q2Content) {this.q2Content = q2Content;}

    public String getQ3Content() {return q3Content;}

    public void setQ3Content(String q3Content) {this.q3Content = q3Content;}
}

