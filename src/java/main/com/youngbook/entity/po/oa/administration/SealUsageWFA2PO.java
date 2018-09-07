package com.youngbook.entity.po.oa.administration;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 15-4-7
 * Time: 下午9:04
 * 印章主表
 */
@Table(name = "OA_SealUsageWFA2", jsonPrefix = "sealUsageWFA2")
public class SealUsageWFA2PO extends BasePO {

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

    //用章发往处
    private String sentto = new String();

    // 申请用途 : 支持查询,必填
    private String applicationPurpose = new String();
    //是否需要外带 ：0：没有，1 有
    private int isOut=Integer.MAX_VALUE;
    //是否需要全部接收 ：0：没有，1 有
    private String isAllReceive=new String();
    //是否全部归还 ：0：没有，1 有
    private String isAllOutBack=new String();


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

    public String getSentto() {
        return sentto;
    }

    public void setSentto(String sentto) {
        this.sentto = sentto;
    }

    public String getApplicationPurpose() {
        return applicationPurpose;
    }

    public void setApplicationPurpose(String applicationPurpose) {
        this.applicationPurpose = applicationPurpose;
    }

    public int getIsOut() {
        return isOut;
    }

    public void setIsOut(int isOut) {
        this.isOut = isOut;
    }

    public String getIsAllReceive() {
        return isAllReceive;
    }

    public void setIsAllReceive(String isAllReceive) {
        this.isAllReceive = isAllReceive;
    }

    public String getIsAllOutBack() {
        return isAllOutBack;
    }

    public void setIsAllOutBack(String isAllOutBack) {
        this.isAllOutBack = isAllOutBack;
    }
}

