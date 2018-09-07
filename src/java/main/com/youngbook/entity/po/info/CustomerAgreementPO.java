package com.youngbook.entity.po.info;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;


@Table(name = "info_customeragreement", jsonPrefix = "customerAgreement")
public class CustomerAgreementPO extends BasePO {
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

    //客户id
    private String  customerId=new String();

    // 协议id
    private String  agreementId =new String();

    //协议名
    private String  agreementName=new String();

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

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

}
