package com.youngbook.entity.po;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;

/**
 * User: Lee
 * Date: 14-5-23
 */
@Table(name = "system_positionpermission", jsonPrefix = "positionPermission")
public class PositionPermissionPO extends BasePO {
    // 编号
    @Id
    private String id = new String();

    // 岗位编号
    private String positionId = new String();

    // 权限编号
    private String permissionId = new String();

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

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
