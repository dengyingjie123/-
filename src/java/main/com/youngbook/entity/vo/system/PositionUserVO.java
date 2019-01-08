package com.youngbook.entity.vo.system;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * User: Lee
 * Date: 14-5-21
 */
@Table(name = "V_PositionUser", jsonPrefix = "positionUser")
public class PositionUserVO extends BaseVO {

    // 编号
    @Id
    private String id = new String();

    // 岗位编号
    private String positionId = new String();

    // 岗位名称
    private String positionName = new String();

    // 用户编号
    private String userId = new String();

    // 用户名称
    private String userName = new String();

    // 部门编号
    private String departmentId = new String();

    // 部门名称
    private String departmentName = new String();

    // 手机号
    private String mobile = new String();

    // 默认标识
    private int states = Integer.MAX_VALUE;

    private String statesName = new String();

    public String getStatesName() {
        return statesName;
    }

    public void setStatesName(String statesName) {
        this.statesName = statesName;
    }

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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

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

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
