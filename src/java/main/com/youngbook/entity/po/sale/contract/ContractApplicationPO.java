package com.youngbook.entity.po.sale.contract;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/12/21
 * 描述：
 * ContractApplication: 销售合同申请表
 */

@Table(name = "sale_contractApplication", jsonPrefix = "contractApplicationPO")
public class ContractApplicationPO extends BasePO {
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

    // 申请人编号
    private String applicationUserId = new String();

    // 产品编号
    private String productionId = new String();

    // 套数
    private int counts = Integer.MAX_VALUE;

    // 申请时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String applicationTime = new String();

    // 审核人编号
    private String checkId = new String();

    // 审核意见
    private String checkComment = new String();

    // 审核状态
    private int checkState =Integer.MAX_VALUE;

    // 审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String checkTime = new String();
    //部门编号
    private String departmentId = new String();
    //部门名称
    private String departmentName = new String();

    //getset
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

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCheckComment() {
        return checkComment;
    }

    public void setCheckComment(String checkComment) {
        this.checkComment = checkComment;
    }

    public int getCheckState() {
        return checkState;
    }

    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
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

    @Override
    public String toString() {
        return "ContractApplicationPO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", applicationUserId='" + applicationUserId + '\'' +
                ", productionId='" + productionId + '\'' +
                ", counts=" + counts +
                ", applicationTime='" + applicationTime + '\'' +
                ", checkId='" + checkId + '\'' +
                ", checkComment='" + checkComment + '\'' +
                ", checkState=" + checkState +
                ", checkTime='" + checkTime + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
