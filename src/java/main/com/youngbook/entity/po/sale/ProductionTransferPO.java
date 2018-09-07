package com.youngbook.entity.po.sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by zsq on 4/22/2015.
 */
@Table(name = "Sale_ProductionTransfer", jsonPrefix = "productionTransfer")
public class ProductionTransferPO extends BasePO {
    /**
     * sid
     */
    @Id
    private int sid = Integer.MAX_VALUE;

    /**
     * id
     */
    private String id = new String();

    /**
     * state
     */
    private int state = Integer.MAX_VALUE;

    /**
     * operatorId
     */
    private String operatorId = new String();

    /**
     * operateTime
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    /**
     * 原产品编号 : 支持查询
     */
    private String originalProductionId = new String();

    /**
     * 原订单编号
     */
    private String originalOrderId = new String();

    /**
     * 原产品持有人 : 支持查询
     */
    private String originalCustumerId = new String();

    /**
     * 原投资金额
     */
    private double originalMoney = Double.MAX_VALUE;

    /**
     * 原收益率
     */
    private double originalProfitRate = Double.MAX_VALUE;

    /**
     * 现产品编号 : 支持查询
     */
    private String currentProductionId = new String();

    /**
     * 现产品持有人 : 支持查询
     */
    private String currentCustomerId = new String();

    /**
     * 现投资金额
     */
    private double currentMoney = Double.MAX_VALUE;

    /**
     * 现收益率
     */
    private double currentProfitRate = Double.MAX_VALUE;

    /**
     * 转让金额
     */
    private double transferMoney = Double.MAX_VALUE;

    /**
     * 转让收益率
     */
    private double transferProfitRate = Double.MAX_VALUE;

    /**
     * 转让状态 : 支持查询
     */
    private int transferSatues = Integer.MAX_VALUE;

    /**
     * 审核人
     */
    private String checkerId = new String();

    /**
     * 审核时间 : 时间类型
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String checkTime = new String();

    /**
     * 转让开始时间 : 时间类型
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String transferStartTime = new String();

    /**
     * 转让结束时间 : 时间类型
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String transferEndTime = new String();

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

    public String getOriginalProductionId() {
        return originalProductionId;
    }

    public void setOriginalProductionId(String originalProductionId) {
        this.originalProductionId = originalProductionId;
    }

    public String getOriginalOrderId() {
        return originalOrderId;
    }

    public void setOriginalOrderId(String originalOrderId) {
        this.originalOrderId = originalOrderId;
    }

    public String getOriginalCustumerId() {
        return originalCustumerId;
    }

    public void setOriginalCustumerId(String originalCustumerId) {
        this.originalCustumerId = originalCustumerId;
    }

    public double getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(double originalMoney) {
        this.originalMoney = originalMoney;
    }

    public double getOriginalProfitRate() {
        return originalProfitRate;
    }

    public void setOriginalProfitRate(double originalProfitRate) {
        this.originalProfitRate = originalProfitRate;
    }

    public String getCurrentProductionId() {
        return currentProductionId;
    }

    public void setCurrentProductionId(String currentProductionId) {
        this.currentProductionId = currentProductionId;
    }

    public String getCurrentCustomerId() {
        return currentCustomerId;
    }

    public void setCurrentCustomerId(String currentCustomerId) {
        this.currentCustomerId = currentCustomerId;
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public double getCurrentProfitRate() {
        return currentProfitRate;
    }

    public void setCurrentProfitRate(double currentProfitRate) {
        this.currentProfitRate = currentProfitRate;
    }

    public double getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(double transferMoney) {
        this.transferMoney = transferMoney;
    }

    public double getTransferProfitRate() {
        return transferProfitRate;
    }

    public void setTransferProfitRate(double transferProfitRate) {
        this.transferProfitRate = transferProfitRate;
    }

    public int getTransferSatues() {
        return transferSatues;
    }

    public void setTransferSatues(int transferSatues) {
        this.transferSatues = transferSatues;
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getTransferStartTime() {
        return transferStartTime;
    }

    public void setTransferStartTime(String transferStartTime) {
        this.transferStartTime = transferStartTime;
    }

    public String getTransferEndTime() {
        return transferEndTime;
    }

    public void setTransferEndTime(String transferEndTime) {
        this.transferEndTime = transferEndTime;
    }



}
