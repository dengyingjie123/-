package com.youngbook.entity.vo.oa.finance;


import com.youngbook.entity.po.BasePO;

public class MoneyLogSummaryVO extends BasePO {

    private String id = new String();
    private String name = new String();
    private double moneyIn = Double.MAX_VALUE;
    private double moneyOut = Double.MAX_VALUE;
    private double total = Double.MAX_VALUE;
    private String parentId = new String ();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoneyIn() {
        return moneyIn;
    }

    public void setMoneyIn(double moneyIn) {
        this.moneyIn = moneyIn;
    }

    public double getMoneyOut() {
        return moneyOut;
    }

    public void setMoneyOut(double moneyOut) {
        this.moneyOut = moneyOut;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
