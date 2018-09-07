package com.youngbook.entity.po;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.dao.MySQLDao;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Table(name = "system_department", jsonPrefix = "department")
public class DepartmentPO extends BasePO {
    @Id
    private String id = new String();
    private String icon = new String();
    private String name = new String();
    private String fromName = new String();
    private String parentId = new String();
    private int orders = Integer.MAX_VALUE;
    private String typeID = new String();

    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id", this.getId());
        json.element("text", this.getName());
        json.element("icon", this.getIcon());
        json.element("parentId", this.getParentId());
        json.element("typeID", this.getTypeID());
        json.element("fromName", this.getFromName());
        // json.element(this.getTypeID(), true);
        return json;    //To change body of overridden methods use File | Settings | File Templates.
    }

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
}
