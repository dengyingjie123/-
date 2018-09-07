package com.youngbook.entity.po;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import net.sf.json.JSONObject;

/**
 * User: Lee
 * Date: 14-5-21
 */
@Table(name="system_position",jsonPrefix="position")
public class PositionPO extends BasePO {
    // 编号
    @Id
    private String id = new String();

    // 名称
    private String name = new String();

    // 部门编号
    private String departmentId = new String();

    // 部门名称
    private String departmentName = new String();

    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id", this.getId());
        json.element("text", this.getName());
        json.element("position", true);
        json.element("departmentName", this.getDepartmentName());
        return json;
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
