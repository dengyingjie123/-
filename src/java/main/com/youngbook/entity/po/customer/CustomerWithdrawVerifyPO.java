package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by 张舜清 on 7/19/2015.
 */
@Table(name = "CRM_CustomerWithdrawVerify", jsonPrefix = "customerWithdrawVerify")
public class CustomerWithdrawVerifyPO extends BasePO {
    // SID
    @Id
    private int sid = Integer.MAX_VALUE;

    // ID
    private String id = new String();

    // State
    private int state = Integer.MAX_VALUE;

    // OperatorID
    private String operatorId = new String();

    // OperateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 客户编号
    private String customerId = new String();

    // 提现金额
    private double withdrawMoney = Double.MAX_VALUE;

    // 审核人1编号
    private String examineUserId1 = new String();

    // 审核人2编号
    private String examineUserId2 = new String();

    // 审核人3编号
    private String examineUserId3 = new String();

    // 打款人编号
    private String remittanceUserId = new String();

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(double withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getExamineUserId1() {
        return examineUserId1;
    }

    public void setExamineUserId1(String examineUserId1) {
        this.examineUserId1 = examineUserId1;
    }

    public String getExamineUserId2() {
        return examineUserId2;
    }

    public void setExamineUserId2(String examineUserId2) {
        this.examineUserId2 = examineUserId2;
    }

    public String getExamineUserId3() {
        return examineUserId3;
    }

    public void setExamineUserId3(String examineUserId3) {
        this.examineUserId3 = examineUserId3;
    }

    public String getRemittanceUserId() {
        return remittanceUserId;
    }

    public void setRemittanceUserId(String remittanceUserId) {
        this.remittanceUserId = remittanceUserId;
    }
}
