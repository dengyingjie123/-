package com.youngbook.entity.vo.production;

import com.youngbook.entity.vo.BaseVO;

/**
 * 产品销量统计展示类
 *
 * Date: 2016-05-17 10:44:08
 * Author: leevits
 */
public class ProductionSaleStatisticsVO extends BaseVO {

    private String customerId = "";
    private String customerName = "";

    private String productionId = "";
    private String productionName = "";

    private String orderId = "";

    private double totalSaleMoney = Double.MAX_VALUE;
    private double totalTransferMoney = Double.MAX_VALUE;

    /**
     * 兑付金额
     *
     * Date: 2016-05-17 10:46:50
     * Author: leevits
     */
    private double totalPaybackMoney = Double.MAX_VALUE;


    private double totalRemainMoney = Double.MAX_VALUE;

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

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotalSaleMoney() {
        return totalSaleMoney;
    }

    public void setTotalSaleMoney(double totalSaleMoney) {
        this.totalSaleMoney = totalSaleMoney;
    }

    public double getTotalTransferMoney() {
        return totalTransferMoney;
    }

    public void setTotalTransferMoney(double totalTransferMoney) {
        this.totalTransferMoney = totalTransferMoney;
    }

    public double getTotalPaybackMoney() {
        return totalPaybackMoney;
    }

    public void setTotalPaybackMoney(double totalPaybackMoney) {
        this.totalPaybackMoney = totalPaybackMoney;
    }

    public double getTotalRemainMoney() {
        return totalRemainMoney;
    }

    public void setTotalRemainMoney(double totalRemainMoney) {
        this.totalRemainMoney = totalRemainMoney;
    }

    @Override
    public String toString() {
        return "ProductionSaleStatisticsVO{" +
                "totalSaleMoney=" + totalSaleMoney +
                ", totalTransferMoney=" + totalTransferMoney +
                ", totalPaybackMoney=" + totalPaybackMoney +
                ", totalRemainMoney=" + totalRemainMoney +
                '}';
    }
}
