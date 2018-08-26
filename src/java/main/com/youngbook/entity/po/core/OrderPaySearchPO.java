package com.youngbook.entity.po.core;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

@Table(name = "Core_OrderPaySearch", jsonPrefix = "orderPaySearch")
public class OrderPaySearchPO extends BasePO {

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

    // 订单支付编号
    private String orderPayId = new String();

    // 回调网页URL
    private String callbackPageURL = new String();

    // 回调服务URL
    private String callbackServiceURL = new String();

    // 反馈订单号
    private String callbackOrderId = new String();

    // 反馈订单日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String callbackOrderTime = new String();

    // 反馈订单状态
    private String callbackOrderStatus = "";

    // 反馈金额
    private double callbackMoney = Double.MAX_VALUE;

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

    public String getOrderPayId() {
        return orderPayId;
    }

    public void setOrderPayId(String orderPayId) {
        this.orderPayId = orderPayId;
    }

    public String getCallbackPageURL() {
        return callbackPageURL;
    }

    public void setCallbackPageURL(String callbackPageURL) {
        this.callbackPageURL = callbackPageURL;
    }

    public String getCallbackServiceURL() {
        return callbackServiceURL;
    }

    public void setCallbackServiceURL(String callbackServiceURL) {
        this.callbackServiceURL = callbackServiceURL;
    }

    public String getCallbackOrderId() {
        return callbackOrderId;
    }

    public void setCallbackOrderId(String callbackOrderId) {
        this.callbackOrderId = callbackOrderId;
    }

    public String getCallbackOrderTime() {
        return callbackOrderTime;
    }

    public void setCallbackOrderTime(String callbackOrderTime) {
        this.callbackOrderTime = callbackOrderTime;
    }

    public String getCallbackOrderStatus() {
        return callbackOrderStatus;
    }

    public void setCallbackOrderStatus(String callbackOrderStatus) {
        this.callbackOrderStatus = callbackOrderStatus;
    }

    public double getCallbackMoney() {
        return callbackMoney;
    }

    public void setCallbackMoney(double callbackMoney) {
        this.callbackMoney = callbackMoney;
    }
}
