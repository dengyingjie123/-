package com.youngbook.entity.po.jeasyui;

import com.youngbook.entity.po.BasePO;
import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 14-11-7
 * Time: 上午12:16
 * To change this template use File | Settings | File Templates.
 */
public class ButtonPO extends BasePO {
    private String id = "";
    private String text = "";
    private String iconCls = "";
    private String permissionName = "";


    public ButtonPO(String id, String text, String iconCls) {
        this.id = id;
        this.text = text;
        this.iconCls = iconCls;
    }

    public ButtonPO(String id, String text, String iconCls, String permissionName) {
        this.id = id;
        this.text = text;
        this.iconCls = iconCls;
        this.permissionName = permissionName;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.element("id", id);
        json.element("text", text);
        json.element("iconCls", iconCls);
        return json;
    }

    public String toHtml() {
        return "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
