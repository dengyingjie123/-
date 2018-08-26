package com.youngbook.entity.vo.Sale.contract;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/12/22
 * 描述：
 * ContractRouteListVO:
 */
@Table(name = "Sale_ContractRouteList", jsonPrefix = "contractRouteListVO")
public class ContractRouteListVO {
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

    //合同号
    private String contractNo = new String();
    //行为时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String actionTime = new String();
    //行为描述
    private String actionDescription = new String();
    //流转类型
    private int actionType = Integer.MAX_VALUE;
    //行为人
    private String actionUserId = new String();
    //部门编号
    private String actionDepartmentId = new String();
    //行为人名称
    private String actionUserName = new String();
    // 快递公司
    private String sendExpress = new String();
    // 快递单号
    private String sendExpressId = new String();
    //部门名称
    private String departmentName = new String();
    //产品名称
    private String productionName = new String();


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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(String actionUserId) {
        this.actionUserId = actionUserId;
    }

    public String getActionDepartmentId() {
        return actionDepartmentId;
    }

    public void setActionDepartmentId(String actionDepartmentId) {
        this.actionDepartmentId = actionDepartmentId;
    }

    public String getActionUserName() {
        return actionUserName;
    }

    public void setActionUserName(String actionUserName) {
        this.actionUserName = actionUserName;
    }

    public String getSendExpress() {
        return sendExpress;
    }

    public void setSendExpress(String sendExpress) {
        this.sendExpress = sendExpress;
    }

    public String getSendExpressId() {
        return sendExpressId;
    }

    public void setSendExpressId(String sendExpressId) {
        this.sendExpressId = sendExpressId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }
}
