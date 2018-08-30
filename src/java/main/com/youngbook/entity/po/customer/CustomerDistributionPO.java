package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

@Table(name = "crm_customerdistribution", jsonPrefix = "customerDistribution")
public class CustomerDistributionPO extends BasePO{
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

    //个人客户ID
    private String customerId=new String();

    //销售员ID
    private String saleManId = new String();

    /**
     * 状态
     * 0：未通过
     * 1：通过
     */
    private int status = Integer.MAX_VALUE;


    //remark标志，0-个人客户 1-企业客户
    private int remark = Integer.MAX_VALUE;
    //审核不通过的原因
    private String reason=new String();

    // 部门编号
    private String departmentId  = new String();

    // 销售组编号
    private String saleGroupId = new String();

    /**
     * get和set方法
     * @return
     */

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRemark() {
        return remark;
    }

    public void setRemark(int remark) {
        this.remark = remark;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSaleGroupId() {
        return saleGroupId;
    }

    public void setSaleGroupId(String saleGroupId) {
        this.saleGroupId = saleGroupId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "CustomerDistributionPO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", customerId='" + customerId + '\'' +
                ", saleManId='" + saleManId + '\'' +
                ", status=" + status +
                ", remark=" + remark +
                ", reason='" + reason + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", saleGroupId='" + saleGroupId + '\'' +
                '}';
    }
}
