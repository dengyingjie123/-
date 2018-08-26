package com.youngbook.entity.po.system;

import com.youngbook.annotation.EnumType;
import com.youngbook.annotation.IgnoreDB;
import com.youngbook.annotation.IgnoreJson;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by ThinkPad on 6/2/2015.
 */
@Table(name = "system_PositionUser", jsonPrefix = "positionUser")
public class PositionUserPO extends BasePO {
    // ID
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
}
