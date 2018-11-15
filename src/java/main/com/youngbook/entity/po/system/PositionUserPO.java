package com.youngbook.entity.po.system;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created by ThinkPad on 6/2/2015.
 */
@Table(name = "system_positionuser", jsonPrefix = "positionUser")
public class PositionUserPO extends BasePO {
    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;

    private int state = Integer.MAX_VALUE;
    // 操作ID
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();
    // 编号
    private String id = new String();

    // 上级菜单编号
    private String positionId = new String();

    // 用户编号
    private String userId = new String();

    // 默认标识
    private int states = Integer.MAX_VALUE;

    @IgnoreJson
    @IgnoreDB
    @EnumType(id = "search_States", display = "否", value = "0")
    public static final int States_no = 0;

    @IgnoreJson
    @IgnoreDB
    @EnumType(id = "search_States", display = "是", value = "1")
    public static final int States_yes = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
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
}
