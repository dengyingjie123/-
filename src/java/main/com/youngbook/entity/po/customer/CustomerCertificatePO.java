package com.youngbook.entity.po.customer;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/18/14
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_customercertificate", jsonPrefix = "customerCertificate")
public class CustomerCertificatePO extends BasePO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // 客户编号
    private String customerId = new String();

    // 号码
    private String number = new String();

    @IgnoreDB
    private String numberWithoutMask = new String();

    //名称
    private String name = new String();

    //有效期
    @DataAdapter(fieldType = FieldType.DATE)
    private String validityDate = new String();

    private int isLongValidityDate = Integer.MAX_VALUE;

    // 有效期开始时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String validityDateStart = new String();

    // 有效期结束时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String validityDateEnd = new String();

    // 发证机构
    private String authenticationInstitution = new String();

    // 操作人
    private String operatorId = new String();

    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();


    public String getNumberWithoutMask() {
        return numberWithoutMask;
    }

    public void setNumberWithoutMask(String numberWithoutMask) {
        this.numberWithoutMask = numberWithoutMask;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getValidityDateStart() {
        return validityDateStart;
    }

    public void setValidityDateStart(String validityDateStart) {
        this.validityDateStart = validityDateStart;
    }

    public String getValidityDateEnd() {
        return validityDateEnd;
    }

    public void setValidityDateEnd(String validityDateEnd) {
        this.validityDateEnd = validityDateEnd;
    }

    public String getAuthenticationInstitution() {
        return authenticationInstitution;
    }

    public void setAuthenticationInstitution(String authenticationInstitution) {
        this.authenticationInstitution = authenticationInstitution;
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

    public int getIsLongValidityDate() {
        return isLongValidityDate;
    }

    public void setIsLongValidityDate(int longValidityDate) {
        isLongValidityDate = longValidityDate;
    }


    @Override
    public String toString() {
        return "CustomerCertificatePO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", customerId='" + customerId + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", validityDate='" + validityDate + '\'' +
                ", isLongValidityDate=" + isLongValidityDate +
                ", validityDateStart='" + validityDateStart + '\'' +
                ", validityDateEnd='" + validityDateEnd + '\'' +
                ", authenticationInstitution='" + authenticationInstitution + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                '}';
    }
}