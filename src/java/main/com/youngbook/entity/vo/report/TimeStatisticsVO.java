package com.youngbook.entity.vo.report;

import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/30/14
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimeStatisticsVO extends BaseVO {

    //销售人员
    String name =new String();

    //客户人数
    int count =Integer.MAX_VALUE;

    //销售金额
    int money =Integer.MAX_VALUE;

    //金额百分比
    double proportion=Double.MAX_VALUE;


    //销售人员id
    String userId =new String();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}