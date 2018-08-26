package com.youngbook.entity.vo.report;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/30/14
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class DetailStatisticsVO  extends BaseVO {

    //销售人员
    String saleName =new String();

    //客户名称
    String customerName =new String();

    //打款时间
    @DataAdapter(fieldType = FieldType.DATE)
    String time=new String();

    //购买金额
    int money =Integer.MAX_VALUE;

    //收益率
    double proportion=Double.MAX_VALUE;

    //销售人员id
    String userId =new String();

    //产品名称
    String productionName = new String();

    //配额
    String size = new String();


    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}