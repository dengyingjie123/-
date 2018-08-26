package com.youngbook.entity.po.sale;

import com.youngbook.entity.po.BasePO;

public class LeaderboardPO extends BasePO {

    // 销售人员姓名
    private String salemanName = new String();

    // 所属部门（财富中心）
    private String departmentName = new String();

    // 指定时间内的销售总额
    private double totalSalesMoney = Double.MAX_VALUE;

    // 指定时间内固定收益的销售总额
    private double fixedSalesMoney = Double.MAX_VALUE;

    // 指定时间内浮动收益的销售总额
    private double floatSalesMoney = Double.MAX_VALUE;

    public String getSalemanName() {
        return salemanName;
    }

    public void setSalemanName(String salemanName) {
        this.salemanName = salemanName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public double getTotalSalesMoney() {
        return totalSalesMoney;
    }

    public void setTotalSalesMoney(double totalSalesMoney) {
        this.totalSalesMoney = totalSalesMoney;
    }

    public double getFixedSalesMoney() {
        return fixedSalesMoney;
    }

    public void setFixedSalesMoney(double fixedSalesMoney) {
        this.fixedSalesMoney = fixedSalesMoney;
    }

    public double getFloatSalesMoney() {
        return floatSalesMoney;
    }

    public void setFloatSalesMoney(double floatSalesMoney) {
        this.floatSalesMoney = floatSalesMoney;
    }
}
