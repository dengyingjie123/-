package com.youngbook.entity.po;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Jayden
 * Date: 14-4-25
 * Time: 上午9:29
 * To change this template use File | Settings | File Templates.
 */
@Table(name="system_permission",jsonPrefix="permission")
public class PermissionPO extends BasePO{
    //编号
    @Id
    private String id = new String();

    //菜单编号
    private String menuId = new String();
    //菜单名称
    private String menuName = new String();
    //权限名称
    private String permissionName = new String();

    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id",this.getId());
        json.element("text",this.getPermissionName());
        json.element("menuID",this.getMenuId());
        json.element("menuName",this.getMenuName());
        return json;    //To change body of overridden methods use File | Settings | File Templates.
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String muenuName) {
        this.menuName = muenuName;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }


    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

}
