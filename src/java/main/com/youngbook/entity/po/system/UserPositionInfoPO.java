package com.youngbook.entity.po.system;

import com.youngbook.annotation.Table;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Lee on 2016/1/24.
 */
@Table(name = "System_UserPositionInfo", jsonPrefix = "userPositionInfo")
public class UserPositionInfoPO extends BasePO {

    private String userId = "";
    private String userName = "";
    private String departmentId = "";
    private String departmentFullName = "";
    private String departmentName = "";
    private String departmentTypeId = "";
    private String positionName = "";
    private String positionId = "";
    private String status = "";


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentFullName() {

        if (StringUtils.isEmpty(departmentFullName)) {
            return departmentName;
        }

        return departmentFullName;
    }

    public void setDepartmentFullName(String departmentFullName) {
        this.departmentFullName = departmentFullName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentTypeId() {
        return departmentTypeId;
    }

    public void setDepartmentTypeId(String departmentTypeId) {
        this.departmentTypeId = departmentTypeId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
