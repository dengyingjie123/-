package com.youngbook.entity.vo.production;

import com.youngbook.annotation.Id;
import com.youngbook.entity.vo.BaseVO;

public class OrderReportMonthlyVO extends BaseVO {

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

    /**
     * 机构
     */
    private String groupName = "";

    /**
     * 姓名
     */
    private String name = "";

    // 年初存量
    private double money_remain_year_open = Double.MAX_VALUE;

    // 年初存量折标
    private double money_remain_year_open_discount_rate = Double.MAX_VALUE;

    // 月初存量
    private double money_remain_month_open = Double.MAX_VALUE;
    private double money_remain_month_open_discount_rate = Double.MAX_VALUE;

    // 客户存量数
    private int customer_remain_count = Integer.MAX_VALUE;
    private int customer_new_count = Integer.MAX_VALUE;

    // 本月募集
    private double money_add_this_month = Double.MAX_VALUE;
    private double money_add_this_month_discount_rate = Double.MAX_VALUE;

    // 本月兑付
    private double money_payment_this_month = Double.MAX_VALUE;
    private double money_payment_this_month_discount_rate = Double.MAX_VALUE;

    // 本月净增
    private double money_new_this_month = Double.MAX_VALUE;
    private double money_new_this_month_discount_rate = Double.MAX_VALUE;

    // 期末存量
    private double money_remain_this_month_end = Double.MAX_VALUE;
    private double money_remain_this_month_end_discount_rate = Double.MAX_VALUE;

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

    public double getMoney_remain_year_open() {
        return money_remain_year_open;
    }

    public void setMoney_remain_year_open(double money_remain_year_open) {
        this.money_remain_year_open = money_remain_year_open;
    }

    public double getMoney_remain_year_open_discount_rate() {
        return money_remain_year_open_discount_rate;
    }

    public void setMoney_remain_year_open_discount_rate(double money_remain_year_open_discount_rate) {
        this.money_remain_year_open_discount_rate = money_remain_year_open_discount_rate;
    }

    public double getMoney_remain_month_open() {
        return money_remain_month_open;
    }

    public void setMoney_remain_month_open(double money_remain_month_open) {
        this.money_remain_month_open = money_remain_month_open;
    }

    public double getMoney_remain_month_open_discount_rate() {
        return money_remain_month_open_discount_rate;
    }

    public void setMoney_remain_month_open_discount_rate(double money_remain_month_open_discount_rate) {
        this.money_remain_month_open_discount_rate = money_remain_month_open_discount_rate;
    }

    public int getCustomer_remain_count() {
        return customer_remain_count;
    }

    public void setCustomer_remain_count(int customer_remain_count) {
        this.customer_remain_count = customer_remain_count;
    }

    public int getCustomer_new_count() {
        return customer_new_count;
    }

    public void setCustomer_new_count(int customer_new_count) {
        this.customer_new_count = customer_new_count;
    }

    public double getMoney_add_this_month() {
        return money_add_this_month;
    }

    public void setMoney_add_this_month(double money_add_this_month) {
        this.money_add_this_month = money_add_this_month;
    }

    public double getMoney_add_this_month_discount_rate() {
        return money_add_this_month_discount_rate;
    }

    public void setMoney_add_this_month_discount_rate(double money_add_this_month_discount_rate) {
        this.money_add_this_month_discount_rate = money_add_this_month_discount_rate;
    }

    public double getMoney_payment_this_month() {
        return money_payment_this_month;
    }

    public void setMoney_payment_this_month(double money_payment_this_month) {
        this.money_payment_this_month = money_payment_this_month;
    }

    public double getMoney_payment_this_month_discount_rate() {
        return money_payment_this_month_discount_rate;
    }

    public void setMoney_payment_this_month_discount_rate(double money_payment_this_month_discount_rate) {
        this.money_payment_this_month_discount_rate = money_payment_this_month_discount_rate;
    }

    public double getMoney_new_this_month() {
        return money_new_this_month;
    }

    public void setMoney_new_this_month(double money_new_this_month) {
        this.money_new_this_month = money_new_this_month;
    }

    public double getMoney_new_this_month_discount_rate() {
        return money_new_this_month_discount_rate;
    }

    public void setMoney_new_this_month_discount_rate(double money_new_this_month_discount_rate) {
        this.money_new_this_month_discount_rate = money_new_this_month_discount_rate;
    }

    public double getMoney_remain_this_month_end() {
        return money_remain_this_month_end;
    }

    public void setMoney_remain_this_month_end(double money_remain_this_month_end) {
        this.money_remain_this_month_end = money_remain_this_month_end;
    }

    public double getMoney_remain_this_month_end_discount_rate() {
        return money_remain_this_month_end_discount_rate;
    }

    public void setMoney_remain_this_month_end_discount_rate(double money_remain_this_month_end_discount_rate) {
        this.money_remain_this_month_end_discount_rate = money_remain_this_month_end_discount_rate;
    }
}
