package com.youngbook.entity.vo.system;

import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015/4/29.
 * 系统统计辅助类
 * @author
 */

public class StatisticsForWebVO  extends BaseVO {
    //投资人数
    private int us_Count =Integer.MAX_VALUE;

    //可用资金
    private double availableMoney = Double.MAX_VALUE;

    //冻结资金
    private double frozenMoney  =Double.MAX_VALUE;

    //累计盈利资金
    private double totalProfitMoney= Double.MAX_VALUE;

    //投资金额
    private double investedMoney = Double.MAX_VALUE;

    public int getUs_Count() {
        return us_Count;
    }

    public void setUs_Count(int us_Count) {
        this.us_Count = us_Count;
    }

    public double getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(double availableMoney) {
        this.availableMoney = availableMoney;
    }

    public double getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(double frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public double getTotalProfitMoney() {
        return totalProfitMoney;
    }

    public void setTotalProfitMoney(double totalProfitMoney) {
        this.totalProfitMoney = totalProfitMoney;
    }

    public StatisticsForWebVO() {
    }

    public double getInvestedMoney() {
        return investedMoney;
    }

    public void setInvestedMoney(double investedMoney) {
        this.investedMoney = investedMoney;
    }
}
