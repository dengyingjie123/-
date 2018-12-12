package com.youngbook.entity.vo.Sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
* @description: 特定时间段内产品兑付计划VO对象
* @author: 徐明煜
* @createDate: 2018/12/10 16:58
* @version: 1.1
*/
public class SpecificPaymentPlanVO extends BaseVO {

    //兑付时间，显示月份
    private Integer PaymentTime = Integer.MAX_VALUE;

    //产品名称
    private String productionName = new String();

    //兑付本金
    private Double principalMoney = Double.MAX_VALUE;

    //兑付利息
    private Double profitMoney = Double.MAX_VALUE;

    public Integer getPaymentTime() {
        return PaymentTime;
    }

    public void setPaymentTime(Integer paymentTime) {
        PaymentTime = paymentTime;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public Double getPrincipalMoney() {
        return principalMoney;
    }

    public void setPrincipalMoney(Double principalMoney) {
        this.principalMoney = principalMoney;
    }

    public Double getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(Double profitMoney) {
        this.profitMoney = profitMoney;
    }
}
