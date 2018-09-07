package com.youngbook.entity.po.oa.expense;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by 邓超
 * Date 2015-5-19
 */
@Table(name = "OA_FinanceBizTripExpenseWFA", jsonPrefix = "financeBizTripExpenseWFA")
public class FinanceBizTripExpenseWFAPO extends BasePO {

    @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();


    //状态
    private int state = Integer.MAX_VALUE;

    // 组织编号
    private String orgId = new String();

    // 出差人编号使用,隔开
    private String userId = new String();
    //出差人名称使用，隔开
    private String userNames = new String();

    // 事由
    private String comment = new String();


    // 日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String time = new String();

    // 金额
    private double money = Double.MAX_VALUE;

    // 报销人编号
    private String reimburseId = new String();

    // 报销人时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String reimburseTime = new String();

    //附件张数
    private int accessoryNumber  = Integer.MAX_VALUE;

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }


    public String getReimburseId() {
        return reimburseId;
    }

    public void setReimburseId(String reimburseId) {
        this.reimburseId = reimburseId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getReimburseTime() {
        return reimburseTime;
    }

    public void setReimburseTime(String reimburseTime) {
        this.reimburseTime = reimburseTime;
    }

    public int getAccessoryNumber() {
        return accessoryNumber;
    }

    public void setAccessoryNumber(int accessoryNumber) {
        this.accessoryNumber = accessoryNumber;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }
}
