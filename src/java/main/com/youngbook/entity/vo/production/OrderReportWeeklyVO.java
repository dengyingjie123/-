package com.youngbook.entity.vo.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-19
 * Time: 下午1:36
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_order", jsonPrefix = "orderVO")
public class OrderReportWeeklyVO extends BaseVO {

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
    private String operateTime = new String();


    private String groupName = "";
    private String name = "";
    private double money_open = Double.MAX_VALUE;
    private double money_open_discountRate = Double.MAX_VALUE;
    private double money_open_add = Double.MAX_VALUE;
    private double money_open_discountRate_add = Double.MAX_VALUE;

    private int customer_count = Integer.MAX_VALUE;
    private int customer_count_add = Integer.MAX_VALUE;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney_open() {
        return money_open;
    }

    public void setMoney_open(double money_open) {
        this.money_open = money_open;
    }

    public double getMoney_open_discountRate() {
        return money_open_discountRate;
    }

    public void setMoney_open_discountRate(double money_open_discountRate) {
        this.money_open_discountRate = money_open_discountRate;
    }

    public double getMoney_open_add() {
        return money_open_add;
    }

    public void setMoney_open_add(double money_open_add) {
        this.money_open_add = money_open_add;
    }

    public double getMoney_open_discountRate_add() {
        return money_open_discountRate_add;
    }

    public void setMoney_open_discountRate_add(double money_open_discountRate_add) {
        this.money_open_discountRate_add = money_open_discountRate_add;
    }

    public int getCustomer_count() {
        return customer_count;
    }

    public void setCustomer_count(int customer_count) {
        this.customer_count = customer_count;
    }

    public int getCustomer_count_add() {
        return customer_count_add;
    }

    public void setCustomer_count_add(int customer_count_add) {
        this.customer_count_add = customer_count_add;
    }
}
