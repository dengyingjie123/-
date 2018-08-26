package com.youngbook.entity.po;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;


/**
 * User: Lee
 * Date: 14-5-21
 */
@Table(name = "system_positionuser", jsonPrefix = "positionUser")
public class PositionUserPO extends BasePO {

    // 编号
    @Id
    private String id = new String();

    // 岗位编号
    private String positionId = new String();

    // 用户编号
    private String userId = new String();

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
}
