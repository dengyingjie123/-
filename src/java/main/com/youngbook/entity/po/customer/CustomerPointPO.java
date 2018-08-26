package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2015/4/20.
 * 客户积分
 */

@Table(name = "CRM_CustomerPoint", jsonPrefix = "customerPoint")
public class CustomerPointPO  extends BasePO{
    /**
     * sid
     */
    @Id
    private int sid = Integer.MAX_VALUE;

    /**
     * id
     */
    private String id = new String();

    /**
     * state
     */
    private int state = Integer.MAX_VALUE;

    /**
     * operatorId
     */
    private String operatorId = new String();

    /**
     * operateTime
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    /**
     * 客户编号
     */
    private String customerId = new String();

    /**
     * 可用积分
     */
    private int availablePoint = Integer.MAX_VALUE;

    /**
     * 已使用积分
     */
    private int usedPoint = Integer.MAX_VALUE;

    //getset

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getAvailablePoint() {
        return availablePoint;
    }

    public void setAvailablePoint(int availablePoint) {
        this.availablePoint = availablePoint;
    }

    public int getUsedPoint() {
        return usedPoint;
    }

    public void setUsedPoint(int usedPoint) {
        this.usedPoint = usedPoint;
    }

    public CustomerPointPO() {
    }
}
