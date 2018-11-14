package com.youngbook.entity.po;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import net.sf.json.JSONObject;

/**
 * User: Lee
 * Date: 14-5-21
 */
@Table(name="system_position",jsonPrefix="position")
public class PositionPO extends BasePO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;
    // id
    private String id = new String();
    // state
    private int state = Integer.MAX_VALUE;
    // 操作人
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();


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

    public int getSid() {return sid;}

    public void setSid(int sid) {this.sid = sid;}

    public int getState() {return state;}

    public void setState(int state) {this.state = state;}

    public String getOperatorId() {return operatorId;}

    public void setOperatorId(String operatorId) {this.operatorId = operatorId;}

    public String getOperateTime() {return operateTime;}

    public void setOperateTime(String operateTime) {this.operateTime = operateTime;}
}
