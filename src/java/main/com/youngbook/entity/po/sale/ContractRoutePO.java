package com.youngbook.entity.po.sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * 修改人：李昕骏
 * 日期：2015年8月18日 20:01:59
 * 内容
 * 增加关联订单和签约时间成员
 */
@Table(name = "crm_contractRoute", jsonPrefix = "contractRoute")
public class ContractRoutePO extends BasePO {
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
    // 申请编号
    private String applicationId = new String();
    // 申请人编号
    private String applicationUserId = new String();
    // 产品编号
    private String productionId = new String();
    /**
     * 修改人：张舜清
     * 时间：2015年9月25日11:41:38
     * 合同编号
     * 由于设计需求变更字段
     * private String contractId = new String();
     */
    //合同套组
    private String contractNo = new String();
    //数字编号
    private String numberId = new String();
    //订单id
    private String orderId = new String();
    // 签约时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String signDate = "";

    // 寄出时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String sendTime = new String();
    // 寄出人编号
    private String senderId = new String();
    // 签收时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String receiveTime = new String();
    // 签收人编号
    private String receiverId = new String();
    // 快递公司
    private String sendExpress = new String();
    // 快递单号
    private String sendExpressId = new String();
    // 领用时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String takeTime = new String();
    // 领用人编号
    private String takeUserId = new String();
    // 合同状态编号
    private String contractStateId = new String();
    // 回寄时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String backTime = new String();
    // 回寄人编号
    private String backUserId = new String();
    // 回寄快递公司
    private String backExpress = new String();
    // 回寄快递单号
    private String backExpressId = new String();
    // 回寄签收时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String backReceiveTime = new String();
    // 回寄签收人编号
    private String backReceiverId = new String();
    // 合同可操作行，Y和N。Y表示可以操作，N表示不可以操作
    private String yOrN = new String();
    /**
     * get和set
     */
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

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
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

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getTakeUserId() {
        return takeUserId;
    }

    public void setTakeUserId(String takeUserId) {
        this.takeUserId = takeUserId;
    }

    public String getContractStateId() {
        return contractStateId;
    }

    public void setContractStateId(String contractStateId) {
        this.contractStateId = contractStateId;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public String getBackUserId() {
        return backUserId;
    }

    public void setBackUserId(String backUserId) {
        this.backUserId = backUserId;
    }

    public String getBackExpress() {
        return backExpress;
    }

    public void setBackExpress(String backExpress) {
        this.backExpress = backExpress;
    }

    public String getBackExpressId() {
        return backExpressId;
    }

    public void setBackExpressId(String backExpressId) {
        this.backExpressId = backExpressId;
    }

    public String getBackReceiveTime() {
        return backReceiveTime;
    }

    public void setBackReceiveTime(String backReceiveTime) {
        this.backReceiveTime = backReceiveTime;
    }

    public String getBackReceiverId() {
        return backReceiverId;
    }

    public void setBackReceiverId(String backReceiverId) {
        this.backReceiverId = backReceiverId;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getyOrN() {
        return yOrN;
    }

    public void setyOrN(String yOrN) {
        this.yOrN = yOrN;
    }
}
