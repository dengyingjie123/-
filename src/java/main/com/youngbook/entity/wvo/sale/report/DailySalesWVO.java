package com.youngbook.entity.wvo.sale.report;

import com.youngbook.annotation.Table;
import com.youngbook.entity.wvo.BaseWVO;

@Table(name = "", jsonPrefix = "dailySales")
public class DailySalesWVO extends BaseWVO {

    // 产品
    private String productionName;

    // 当日销售额
    private double saleMoney = Double.MAX_VALUE;

    // 产品配额
    private double size = Double.MAX_VALUE;

    // 当天销售比例
    private double daySalesRatio = Double.MAX_VALUE;

    // 累计销售比例
    private double totalSalesRatio = Double.MAX_VALUE;

    public String getProductionName() {return productionName;}
    public void setProductionName(String productionName) {this.productionName = productionName;}
    public double getSaleMoney() {return saleMoney;}
    public void setSaleMoney(double saleMoney) {this.saleMoney = saleMoney;}
    public double getSize() {return size;}
    public void setSize(double size) {this.size = size;}
    public double getDaySalesRatio() {return daySalesRatio;}
    public void setDaySalesRatio(double daySalesRatio) {this.daySalesRatio = daySalesRatio;}
    public double getTotalSalesRatio() {return totalSalesRatio;}
    public void setTotalSalesRatio(double totalSalesRatio) {this.totalSalesRatio = totalSalesRatio;}
}
