package com.youngbook.entity.po;

import com.youngbook.annotation.*;
import net.sf.json.JSONObject;

/**
 * User: Lee
 * Date: 14-4-8
 */
@Table(name = "system_kv", jsonPrefix = "kv", backupTableName = "system_kv_archive")
public class KVPO extends BasePO {

    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;

    private int state = Integer.MAX_VALUE;
    // 操作ID
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 编号
    private String ID = new String();

    // 键
    private String K = new String();

    // 值
    private String V = new String();

    private String parameter = "";

    // 组名
    private String GroupName = new String();

    // 排序
    private int Orders = Integer.MAX_VALUE;


    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id", K);
        json.element("text", V);

        return json;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getK() {
        return K;
    }

    public void setK(String k) {
        K = k;
    }

    public String getV() {
        return V;
    }

    public void setV(String v) {
        V = v;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public int getOrders() {
        return Orders;
    }

    public void setOrders(int orders) {
        Orders = orders;
    }

    @Override
    public String toString() {
        return "KVPO{" +
                "ID='" + ID + '\'' +
                ", K='" + K + '\'' +
                ", V='" + V + '\'' +
                ", parameter='" + parameter + '\'' +
                ", GroupName='" + GroupName + '\'' +
                ", Orders=" + Orders +
                '}';
    }
}
