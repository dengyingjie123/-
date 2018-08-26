package com.youngbook.entity.vo.mobile;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-30
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class CustomerEarningVO {
    //当日收益
    private double dayEarning = Double.MAX_VALUE;
    //平均百分比
    private double average =Double.MAX_VALUE;
    //总资产
    private double totalAssets = Double.MAX_VALUE;

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getDayEarning() {
        return dayEarning;
    }

    public void setDayEarning(double dayEarning) {
        this.dayEarning = dayEarning;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(double totalAssets) {
        this.totalAssets = totalAssets;
    }
}
