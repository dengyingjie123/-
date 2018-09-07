package com.youngbook.entity.po.system;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.IdType;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Lee on 3/21/2017.
 */
public class SystemFinanceSimpleRecordPO extends BasePO {
    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;

    private String id = "";

    //登录用户状态
    private int state = Integer.MAX_VALUE;
    //操作者Id
    private String operatorId = "";
    private String operatorName = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = "";

    private String accountId = "";
    private String accountName = "";
    private int moneyIn = 0;
    private int moneyOut = 0;

    @DataAdapter(fieldType = FieldType.DATE)
    private String openTime = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String confirmTime = "";

    private String bizId = "";
    private String comment = "";

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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getMoneyIn() {
        return moneyIn;
    }

    public void setMoneyIn(int moneyIn) {
        this.moneyIn = moneyIn;
    }

    public int getMoneyOut() {
        return moneyOut;
    }

    public void setMoneyOut(int moneyOut) {
        this.moneyOut = moneyOut;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
