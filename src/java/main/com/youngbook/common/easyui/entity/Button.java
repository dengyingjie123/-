package com.youngbook.common.easyui.entity;

/**
 * Created by Lee on 2016/6/8.
 */
public class Button {
    private String id = "";
    private String icon = "";
    private String name = "";

    private String permission = "";

    public Button (String id, String name) {
        this(id, name, IconStyle.BLANK, "");
    }

    public Button (String id, String name, String icon, String permission) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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
}
