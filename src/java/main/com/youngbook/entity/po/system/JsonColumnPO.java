package com.youngbook.entity.po.system;

import com.youngbook.entity.po.BasePO;

public class JsonColumnPO extends BasePO {
    private String title = new String();
    private String field = new String();
    private boolean hidden = false;
    private int width = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}