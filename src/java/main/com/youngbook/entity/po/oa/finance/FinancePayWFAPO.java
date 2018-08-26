package com.youngbook.entity.po.oa.finance;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Lee on 2015/6/15.
 */
@Table(name = "OA_FinancePayWFA", jsonPrefix = "financePayWFA")
public class FinancePayWFAPO extends BasePO {

    // Sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();

    // State
    private int state = Integer.MAX_VALUE;

    // OperatorId
    private String operatorId = new String();

    // OperateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 组织编号
    private String orgId = new String();

    // 日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String time = new String();

    // 收款方名称
    private String payeeName = new String();

    // 收款方开户行
    private String payeeBankName = new String();

    // 收款方帐号
    private String payeeBankAccount = new String();

    // 资金支付项目
    private String projectName = new String();

    // 合同名称
    private String contractName = new String();

    // 合同编号
    private String contractNo = new String();

    // 合同金额
    private double contractMoney = Double.MAX_VALUE;

    // 累计已支付金额
    private double paidMoney = Double.MAX_VALUE;

    // 本次支付金额
    private double money = Double.MAX_VALUE;

    // 经办人编号
    private String applicantId = new String();

    // 经办人时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String applicantTime = new String();
    /*修改：周海鸿
    * 时间：2015-7-14
    * 内容：添加新字段 方*/
    private String payName = new String();//付款方名称
    private String payBankName = new String(); //付款开户行
    private String payBankAccount = new String(); //付款方账号


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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeBankName() {
        return payeeBankName;
    }

    public void setPayeeBankName(String payeeBankName) {
        this.payeeBankName = payeeBankName;
    }

    public String getPayeeBankAccount() {
        return payeeBankAccount;
    }

    public void setPayeeBankAccount(String payeeBankAccount) {
        this.payeeBankAccount = payeeBankAccount;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(double contractMoney) {
        this.contractMoney = contractMoney;
    }

    public double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantTime() {
        return applicantTime;
    }

    public void setApplicantTime(String applicantTime) {
        this.applicantTime = applicantTime;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayBankName() {
        return payBankName;
    }

    public void setPayBankName(String payBankName) {
        this.payBankName = payBankName;
    }

    public String getPayBankAccount() {
        return payBankAccount;
    }

    public void setPayBankAccount(String payBankAccount) {
        this.payBankAccount = payBankAccount;
    }
}
