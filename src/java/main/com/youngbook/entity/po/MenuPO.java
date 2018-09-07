package com.youngbook.entity.po;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import net.sf.json.JSONObject;

@Table(name="system_menu",jsonPrefix="menu")
public class MenuPO extends BasePO {
    // Version: 0.1.5  /////////////////////////////////////


    // 编号
    @Id
    private String id = new String();

    // 图标
    private String icon = new String();

    // 权限名称
    private String permissionName = new String();

    // 父级编号
    private String parentId = new String();

    // 菜单名称
    private String name = new String();

    // 类型
    private int type = Integer.MAX_VALUE;

    // 链接
    private String url = new String();

    private int orders = Integer.MAX_VALUE;


    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id", this.getId());
        json.element("text", this.getName());
        json.element("iconCls", this.getIcon());

        JSONObject attribute = new JSONObject();
        attribute.element("url", this.getUrl());
        attribute.element("moduleName", this.getName());
        attribute.element("permissionName", this.getPermissionName());

        json.element("attributes", attribute);
        return json;
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

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }
}
