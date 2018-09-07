package com.youngbook.entity.vo.Sale.contract;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/12/21
 * 描述：
 * ContractVO:
 */
@Table(name = "Sale_Contract", jsonPrefix = "contractVO")
public class ContractVO extends BaseVO {
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

    private String orgId = new String();
    private String orgName=new String();

    // 产品分期编号
    private String productionId = new String();
    //产品分期名称
    private String productionName = new String();
    // 申请编号
    private String applicationId = new String();

    //合同状态
    private int status = Integer.MAX_VALUE;
    private String statusName = "";
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
    //领用人
    private String receiveUserName = new String();

    //行为人
    private String actionUserName = new String();
    //行为时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String actionTime = new String();

    //流转状态
    private int actionType = Integer.MAX_VALUE;
    //快递公司
    private String sendExpress = new String();
    //快递单号
    private String sendExpressId = new String();

    //合同申请组织部门
    private String departmentName = new String();
    //申请内容
    //申请人
    private String applicationUserName = new String();

    // 申请时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String applicationTime = new String();

    //审批人
    private String checkName = new String();
    // 审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String checkTime = new String();

    //订单
    // 客户名称
    private String customerName = new String();
    //客户ID
    private String customerId = new String();

    // 购买金额
    private double money = Double.MAX_VALUE;

    // 签约时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String payTime = new String();


    //签收时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String sigTime = new String();

    //项目名称
    private String projectName=new String();
    //项目ID
    private String projectId=new String();

    //销售ID
    private String salemanId=new String();

    //销售人名称
    private String salesmanName=new String();

    //产品ID
    private String productionHomeId = new String();

    //产品名称
    private String productionHomeName = new String();

    //产品分期名称
    private String productName = new String();

    //产品分期ID
    private String productId = new String();

    private int size = Integer.MAX_VALUE;

    private int saleMoney = Integer.MAX_VALUE;

    //根据财富中心区分的所有合同数
    private int allContract = Integer.MAX_VALUE;
    //统计同意产品的所有合同数
    private int totalContract = Integer.MAX_VALUE;
    //根据财富中心区分的已签约合同数
    private int allSignedConract = Integer.MAX_VALUE;
    //统计同意产品的已签约合同数
    private int totalSignedContract = Integer.MAX_VALUE;
    //根据财富中心区分的未签约合同数
    private int allUnsignConract = Integer.MAX_VALUE;
    //统计同意产品的未签约合同数
    private int totalUnsignContract = Integer.MAX_VALUE;
    //根据财富中心区分的已作废合同数
    private int allCanceledConract = Integer.MAX_VALUE;
    //统计同意产品的已作废合同数
    private int totalCanceledContract = Integer.MAX_VALUE;


    // 签收标识
    private int signedStatus = Integer.MAX_VALUE;
    private String signedStatusName = new String();

    public int getSignedStatus() {
        return signedStatus;
    }

    public void setSignedStatus(int signedStatus) {
        this.signedStatus = signedStatus;
    }

    public String getSignedStatusName() {
        return signedStatusName;
    }

    public void setSignedStatusName(String signedStatusName) {
        this.signedStatusName = signedStatusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
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


    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getActionUserName() {
        return actionUserName;
    }

    public void setActionUserName(String actionUserName) {
        this.actionUserName = actionUserName;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getApplicationUserName() {
        return applicationUserName;
    }

    public void setApplicationUserName(String applicationUserName) {
        this.applicationUserName = applicationUserName;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getSigTime() {
        return sigTime;
    }

    public void setSigTime(String sigTime) {
        this.sigTime = sigTime;
    }


    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSalemanId() {
        return salemanId;
    }

    public void setSalemanId(String salemanId) {
        this.salemanId = salemanId;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductionHomeId() {
        return productionHomeId;
    }

    public void setProductionHomeId(String productionHomeId) {
        this.productionHomeId = productionHomeId;
    }

    public String getProductionHomeName() {
        return productionHomeName;
    }

    public void setProductionHomeName(String productionHomeName) {
        this.productionHomeName = productionHomeName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getAllContract() {
        return allContract;
    }

    public void setAllContract(int allContract) {
        this.allContract = allContract;
    }

    public int getTotalContract() {
        return totalContract;
    }

    public void setTotalContract(int totalContract) {
        this.totalContract = totalContract;
    }

    public int getAllSignedConract() {
        return allSignedConract;
    }

    public void setAllSignedConract(int allSignedConract) {
        this.allSignedConract = allSignedConract;
    }

    public int getTotalSignedContract() {
        return totalSignedContract;
    }

    public void setTotalSignedContract(int totalSignedContract) {
        this.totalSignedContract = totalSignedContract;
    }

    public int getAllUnsignConract() {
        return allUnsignConract;
    }

    public void setAllUnsignConract(int allUnsignConract) {
        this.allUnsignConract = allUnsignConract;
    }

    public int getTotalUnsignContract() {
        return totalUnsignContract;
    }

    public void setTotalUnsignContract(int totalUnsignContract) {
        this.totalUnsignContract = totalUnsignContract;
    }

    public int getAllCanceledConract() {
        return allCanceledConract;
    }

    public void setAllCanceledConract(int allCanceledConract) {
        this.allCanceledConract = allCanceledConract;
    }

    public int getTotalCanceledContract() {
        return totalCanceledContract;
    }

    public void setTotalCanceledContract(int totalCanceledContract) {
        this.totalCanceledContract = totalCanceledContract;
    }

    public int getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(int saleMoney) {
        this.saleMoney = saleMoney;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
