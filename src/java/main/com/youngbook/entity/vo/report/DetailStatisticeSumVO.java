package com.youngbook.entity.vo.report;

import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 11/12/14
 * Time: 5:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class DetailStatisticeSumVO extends BaseVO{

    //产品名称
    String productionName = new String();

    //配额
    String size = new String();

   //购买总额
    int sumMoney=Integer.MAX_VALUE;

    //平均收益
    double expectedYield=Double.MAX_VALUE;

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

    public int getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(int sumMoney) {
        this.sumMoney = sumMoney;
    }

    public double getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(double expectedYield) {
        this.expectedYield = expectedYield;
    }
}
