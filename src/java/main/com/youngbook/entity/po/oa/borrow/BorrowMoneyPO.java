package com.youngbook.entity.po.oa.borrow;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;
/**
 * Created by 借款申请 on 2015/9/21.
 */


@Table(name = "OA_BorrowMoney", jsonPrefix = "borrowMoney")
public class BorrowMoneyPO  extends BasePO {


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

    // 申请人编号
    private String applicantId = new String();

    // 申请人名称 : 支持查询
    private String applicantName = new String();

    // 申请时间 : 时间类型,时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String applicationTime = new String();


    // 申请用途 : 支持查询,必填
    private String applicationPurpose = new String();

    // 申请金额
    private String money = new String();


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

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }


    public String getApplicationPurpose() {
        return applicationPurpose;
    }

    public void setApplicationPurpose(String applicationPurpose) {
        this.applicationPurpose = applicationPurpose;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}

