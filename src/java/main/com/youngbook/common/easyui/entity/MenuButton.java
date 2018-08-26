package com.youngbook.common.easyui.entity;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class MenuButton {

    private String id = "";
    private String icon = "";
    private String name = "";

    private String permission = "";

    public MenuButton(String id, String name) {
        this(id, name, IconStyle.BLANK, "");
    }

    public MenuButton(String id, String name, String icon, String permission) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.permission = permission;
    }

    public MenuButton(String id, String name, String icon) {
        this(id, name, icon, "");
    }


    public String getMenuId() {
        return id + "_menu";
    }

    public String printDefinition(HttpServletRequest request) {

        if (!StringUtils.isEmpty(permission) && !Config.hasPermission(permission, request)) {
            return "";
        }

        /**
         * 如果所属按钮没有操作权限，则返回空
         */
        boolean hasPermission = false;
        for (int i = 0; i < this.getButtons().size(); i++) {
            Button button = (Button)this.getButtons().getByIndex(i+1).getValue();

            /**
             * 如果包含不需要权限的按钮，直接返回可以显示
             */
            if (StringUtils.isEmpty(button.getPermission())) {
                hasPermission = true;
                break;
            }


            /**
             * 如果有按钮权限满足条件，则直接返回显示
             */
            if (!StringUtils.isEmpty(button.getPermission()) && Config.hasPermission(button.getPermission(), request)) {
                hasPermission = true;
                break;
            }
        }

        if (!hasPermission) {
            return "";
        }

        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<a href=\"javascript:void(0)\" id=\""+this.getId()+"\" class=\"easyui-menubutton\" data-options=\"menu:'#"+this.getMenuId()+"',iconCls:'"+this.getIcon()+"'\">"+this.getName()+"</a>");

        return sbHtml.toString();
    }

    public String printButtons(HttpServletRequest request) {

        StringBuffer sbHtml = new StringBuffer();


        for (int i = 0; i < this.getButtons().size(); i++) {
            Button button = (Button)this.getButtons().getByIndex(i+1).getValue();

            if (!StringUtils.isEmpty(button.getPermission()) && !Config.hasPermission(button.getPermission(), request)) {
                continue;
            }

            sbHtml.append("<div id=\""+button.getId()+"\" data-options=\"iconCls:'"+button.getIcon()+"'\">"+button.getName()+"</div>");
        }

        if (sbHtml.length() > 0) {
            sbHtml.insert(0, "<div id=\"" + this.getMenuId() + "\" style=\"width:" + this.getWidth() + "px;\">");
            sbHtml.append("</div>");
        }

        return sbHtml.toString();
    }

    private double width = 150;

    private KVObjects buttons = new KVObjects();

    public MenuButton addButton(String id, String name, int index) {

        Button button = new Button(id, name);
        KVObject kvObject = new KVObject(index, button);
        buttons.add(kvObject);

        return this;
    }

    public MenuButton addButton(String id, String name, String icon, int index, String permission) {

        Button button = new Button(id, name, icon, permission);

        KVObject kvObject = new KVObject(index, button);
        buttons.add(kvObject);

        return this;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KVObjects getButtons() {
        return buttons;
    }

    public void setButtons(KVObjects buttons) {
        this.buttons = buttons;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
