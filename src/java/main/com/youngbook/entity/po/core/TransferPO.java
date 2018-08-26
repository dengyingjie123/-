package com.youngbook.entity.po.core;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Lee on 2016/1/12.
 */
//构造类成员
@Table(name = "core_Transfer", jsonPrefix = "transfer")
public class TransferPO extends BasePO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 支付接口流水编号
    private String APIID = new String();

    // 支付接口
    private String APIName = new String();

    // 提交时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String submitTime = new String();

    // 反馈时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String callbackTime = new String();

    // 源账户类型 : 0：个人账户；1：企业账户
    private int sourceType = Integer.MAX_VALUE;

    // 源账户名称
    private String sourceAccountName = new String();

    // 源账户账号
    private String sourceAccountNo = new String();

    // 源账户银行
    private String sourceBank = new String();

    // 目标账户类型 : 0：个人账户；1：企业账户
    private int targetType = Integer.MAX_VALUE;

    // 目标账户名称
    private String targetAccountName = new String();

    // 目标账户账号
    private String targetAccountNo = new String();

    // 目标账户银行
    private String targetBank = new String();

    private String targetBankBranchName = new String();

    private String targetProvinceName = "";

    private String targetCityName = "";

    // 总金额
    private double money = Double.MAX_VALUE;

    // 确认金额
    private double moneyConfirmed = Double.MAX_VALUE;

    // 指令编号
    private String aPICommandId = new String();

    // 状态 : 0：未发送；1：已发送；2：待确认；3：已确认成功；4：已确认失败；5：其他异常
    private int status = Integer.MAX_VALUE;

    public String getTargetBankBranchName() {
        return targetBankBranchName;
    }

    public void setTargetBankBranchName(String targetBankBranchName) {
        this.targetBankBranchName = targetBankBranchName;
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

    public String getAPIID() {
        return APIID;
    }

    public void setAPIID(String APIID) {
        this.APIID = APIID;
    }

    public String getAPIName() {
        return APIName;
    }

    public void setAPIName(String APIName) {
        this.APIName = APIName;
    }

    public String getTargetProvinceName() {
        return targetProvinceName;
    }

    public void setTargetProvinceName(String targetProvinceName) {
        this.targetProvinceName = targetProvinceName;
    }

    public String getTargetCityName() {
        return targetCityName;
    }

    public void setTargetCityName(String targetCityName) {
        this.targetCityName = targetCityName;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(String callbackTime) {
        this.callbackTime = callbackTime;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceAccountName() {
        return sourceAccountName;
    }

    public void setSourceAccountName(String sourceAccountName) {
        this.sourceAccountName = sourceAccountName;
    }

    public String getSourceAccountNo() {
        return sourceAccountNo;
    }

    public void setSourceAccountNo(String sourceAccountNo) {
        this.sourceAccountNo = sourceAccountNo;
    }

    public String getSourceBank() {
        return sourceBank;
    }

    public void setSourceBank(String sourceBank) {
        this.sourceBank = sourceBank;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public String getTargetAccountName() {
        return targetAccountName;
    }

    public void setTargetAccountName(String targetAccountName) {
        this.targetAccountName = targetAccountName;
    }

    public String getTargetAccountNo() {
        return targetAccountNo;
    }

    public void setTargetAccountNo(String targetAccountNo) {
        this.targetAccountNo = targetAccountNo;
    }

    public String getTargetBank() {
        return targetBank;
    }

    public void setTargetBank(String targetBank) {
        this.targetBank = targetBank;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoneyConfirmed() {
        return moneyConfirmed;
    }

    public void setMoneyConfirmed(double moneyConfirmed) {
        this.moneyConfirmed = moneyConfirmed;
    }

    public String getaPICommandId() {
        return aPICommandId;
    }

    public void setaPICommandId(String aPICommandId) {
        this.aPICommandId = aPICommandId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
