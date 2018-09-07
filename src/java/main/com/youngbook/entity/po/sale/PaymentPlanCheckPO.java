package com.youngbook.entity.po.sale;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * 兑付计划 表
 *
 * Created by Administrator on 2015/3/25.
 */
@Table(name = "Core_PaymentPlan_Check", jsonPrefix = "paymentPlanCheck")
public class PaymentPlanCheckPO extends BasePO {

    // 序号
    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;

    // 编号
    private String id = new String();

    //状态
    private int state = Integer.MAX_VALUE;

    //操作人编号
    private String operatorId = new String();
    //操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime= new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String paymentPlanDate = "";

    private String checkId = "";
    private String checkName = "";
    private String checkTime = "";

    private String checkId2 = "";
    private String checkName2 = "";
    private String checkTime2 = "";
    private String checkComment2 = "";

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
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

    public String getPaymentPlanDate() {
        return paymentPlanDate;
    }

    public void setPaymentPlanDate(String paymentPlanDate) {
        this.paymentPlanDate = paymentPlanDate;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
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

    public String getCheckId2() {
        return checkId2;
    }

    public void setCheckId2(String checkId2) {
        this.checkId2 = checkId2;
    }

    public String getCheckName2() {
        return checkName2;
    }

    public void setCheckName2(String checkName2) {
        this.checkName2 = checkName2;
    }

    public String getCheckTime2() {
        return checkTime2;
    }

    public void setCheckTime2(String checkTime2) {
        this.checkTime2 = checkTime2;
    }

    public String getCheckComment2() {
        return checkComment2;
    }

    public void setCheckComment2(String checkComment2) {
        this.checkComment2 = checkComment2;
    }
}
