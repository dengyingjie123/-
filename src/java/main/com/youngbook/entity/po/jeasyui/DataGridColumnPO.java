package com.youngbook.entity.po.jeasyui;

import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.BasePO;
import net.sf.json.JSONObject;

/**
 * Created by Lee on 2/14/2017.
 */
public class DataGridColumnPO extends BasePO {

    String field = "";
    String title = "";
    String width = "";
    boolean hidden = false;

    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();

        if (!StringUtils.isEmpty(field)) {
            json.put("field", field);
        }

        if (!StringUtils.isEmpty(title)) {
            json.put("title", title);
        }

        if (!StringUtils.isEmpty(width)) {
            json.put("width", width);
        }

        if (hidden) {
            json.put("hidden", hidden);
        }

        return json;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
