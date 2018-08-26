package com.youngbook.entity.vo.production;

import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015/2/4.
 */
@Table(jsonPrefix = "productionCustomer")
public class ProductionCustomerVO extends BaseVO {

    private String sid = "";
    private String id = "";
    private String customerName = "";
    private String mobile = "";
    private String money = "";
    private String orderNum = "";
    private int orderStatus = Integer.MAX_VALUE;
    private String orderStatusName = "";
    private String payTime = "";
    private String paybackTime = "";

    public String getPaybackTime() {
        return paybackTime;
    }

    public void setPaybackTime(String paybackTime) {
        this.paybackTime = paybackTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
