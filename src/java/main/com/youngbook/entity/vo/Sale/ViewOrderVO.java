package com.youngbook.entity.vo.Sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Table;
import com.youngbook.common.IJsonable;
import com.youngbook.entity.vo.BaseVO;

/**
 * 视图view_order对象
 * @author: 徐明煜
 * @version:1.1
 * @create: 2018-11-28 15:48
 */
@Table(name = "view_order", jsonPrefix = "view_order")
public class ViewOrderVO extends BaseVO {
    //订单编号
    private String orderNum = new String();
    //销售人员姓名
    private String salesmanName = new String();
    //兑付状态
    private int paymentPlanStatus = Integer.MAX_VALUE;
    //客户姓名
    private String customerName = new String();
    //产品名
    private String productionName = new String();
    //金额
    private double money = Double.MAX_VALUE;
    //
    @DataAdapter(fieldType = FieldType.DATETIME)
    private String paymentPlanLastTime = new String();
    //支付时间
    @DataAdapter(fieldType = FieldType.DATETIME)
    private String payTime = new String();


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPaymentPlanLastTime() {
        return paymentPlanLastTime;
    }

    public void setPaymentPlanLastTime(String paymentPlanLastTime) {
        this.paymentPlanLastTime = paymentPlanLastTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public int getPaymentPlanStatus() {
        return paymentPlanStatus;
    }

    public void setPaymentPlanStatus(int paymentPlanStatus) {
        this.paymentPlanStatus = paymentPlanStatus;
    }
}
