package com.youngbook.entity.vo.Sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

@Table(name = "sale_customertypecommissioncorrection", jsonPrefix = "customertypecommissioncorrection")
public class CustomerTypeCommissionCorrectionVO extends BaseVO {

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

    // customerType
    private int customerType = Integer.MAX_VALUE;

    // correctionValue
    private double correctionValue = Double.MAX_VALUE;

    // areaNo
    private String areaNo = new String();

    // effectieTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String effectieTime = new String();

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

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public double getCorrectionValue() {
        return correctionValue;
    }

    public void setCorrectionValue(double correctionValue) {
        this.correctionValue = correctionValue;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getEffectieTime() {
        return effectieTime;
    }

    public void setEffectieTime(String effectieTime) {
        this.effectieTime = effectieTime;
    }
}
