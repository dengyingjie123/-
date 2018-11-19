package com.youngbook.entity.po;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
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
    // 编号
    private String id = new String();
    // SID
    @Id
    private int sid = Integer.MAX_VALUE;
    // State
    private int state = Integer.MAX_VALUE;
    // 操作ID
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

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

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
