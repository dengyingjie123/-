package com.youngbook.entity.po.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * 产品订单详情
 *
 * 用于记录订单的操作历史，如购买、转让、赎回、兑付等
 *
 * Date: 2016-05-17 10:51:19
 * Author: leevits
 */
@Table(name = "crm_orderdetail", jsonPrefix = "orderDetail")
public class OrderDetailPO extends BasePO {

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

    private String orderId = new String();
    private String orderNum = "";
    private int orderLine = Integer.MAX_VALUE;

    private String customerId = "";
    private String customerName = "";
    private String productionId = "";

    private int status = Integer.MAX_VALUE;
    private String statusName = "";

    private double money = Double.MAX_VALUE;

    private String description = "";


    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = "";

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(int orderLine) {
        this.orderLine = orderLine;
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

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "OrderDetailPO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", orderLine=" + orderLine +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", productionId='" + productionId + '\'' +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", money=" + money +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
