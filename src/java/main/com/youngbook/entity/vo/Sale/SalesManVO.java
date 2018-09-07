package com.youngbook.entity.vo.Sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "CRM_SalemanGroup", jsonPrefix = "salesManVO")
public class SalesManVO extends BaseVO{
    // SID
    @Id
    private int sid = Integer.MAX_VALUE;
    // ID
    private String id = new String();
    //用户编号
    private String saleManId = new String();
    // State
    private int state = Integer.MAX_VALUE;
    // 操作员ID
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();
    // 员工ID
    private String userId = new String();
    // 员工名称
    private String userName = new String();

    private String groupId = new String();
    private String groupName = new String();
    private String departmentName = new String();
    private String groupAreaName = new String();



    // 员工编号
    private String staffCode = new String();
    // 身份证
    private String idCard = new String();
    // 手机号码
    private String mobile = new String();
    //销售等级V键
    private String salesLevel = new String();
    //是否为默认销售组
    private int defaultGroup = Integer.MAX_VALUE;

    /**
     * 修改：周海鸿
     * 时间2015-7-2
     * 添加字段
     */
    //销售小组状态
    private String saleManStatus = new String();

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGroupAreaName() {
        return groupAreaName;
    }

    public void setGroupAreaName(String groupAreaName) {
        this.groupAreaName = groupAreaName;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSalesLevel() {
        return salesLevel;
    }

    public void setSalesLevel(String salesLevel) {
        this.salesLevel = salesLevel;
    }


    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSaleManStatus() {
        return saleManStatus;
    }

    public void setSaleManStatus(String saleManStatus) {
        this.saleManStatus = saleManStatus;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public int getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(int defaultGroup) {
        this.defaultGroup = defaultGroup;
    }
}
