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
 * Contract:销售合同
 */
@Table(name = "Sale_Contract", jsonPrefix = "contractPO")
public class ContractPO extends BasePO {
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
    //合同申请组织编号
    private String orgId = new String();
    // 产品编号
    private String productionId = new String();

    // 申请编号
    private String applicationId = new String();

    //合同状态
    private int status = Integer.MAX_VALUE;
    //合同明细状态,
    private int detailStatus = Integer.MAX_VALUE;
    //合同编号
    private String contractNo = new String();
    //合同序号
    private String contractDetailNo = new String();
    //合同组合编号
    private String contractDisplayNo = new String();
    //作废人编号
    private String cancelId = new String();
    //作废人姓名
    private String cancelName = new String();
    //作废时间

    @DataAdapter(fieldType = FieldType.DATE)
    private String cancelTime = new String();
    //备注
    private String comment = new String();
    //领用人编号
    private String receiveUserId = new String();

    // 签收标识
    private int signedStatus = Integer.MAX_VALUE;

    public int getSignedStatus() {
        return signedStatus;
    }

    public void setSignedStatus(int signedStatus) {
        this.signedStatus = signedStatus;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(int detailStatus) {
        this.detailStatus = detailStatus;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractDetailNo() {
        return contractDetailNo;
    }

    public void setContractDetailNo(String contractDetailNo) {
        this.contractDetailNo = contractDetailNo;
    }

    public String getContractDisplayNo() {
        return contractDisplayNo;
    }

    public void setContractDisplayNo(String contractDisplayNo) {
        this.contractDisplayNo = contractDisplayNo;
    }

    public String getCancelId() {
        return cancelId;
    }

    public void setCancelId(String cancelId) {
        this.cancelId = cancelId;
    }

    public String getCancelName() {
        return cancelName;
    }

    public void setCancelName(String cancelName) {
        this.cancelName = cancelName;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
}
