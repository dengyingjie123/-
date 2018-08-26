package com.youngbook.entity.po.system.chart;

import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

@Table(name = "finance_moneyLog", jsonPrefix = "pie")
public class PiePO extends BasePO {
    private String name = new String();
    private double y = Double.MAX_VALUE;
    private boolean sliced = false;
    private boolean selected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean getSliced() {
        return sliced;
    }

    public void setSliced(boolean sliced) {
        this.sliced = sliced;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
