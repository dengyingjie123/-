package com.youngbook.entity.po.sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * HOPEWEALTH-1293
 * 返佣率PO
 * 一个产品构成(ProductionComposition)可以包含多个不同的返佣率
 * Created by Lee on 2016/3/21.
 */
@Table(name = "sale_ProductionCommission", jsonPrefix = "productionCommission")
public class ProductionCommissionPO extends BasePO {

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

    // 返佣类型
    private int commissionType = Integer.MAX_VALUE;

    // 返佣等级
    private String commissionLevel = new String();

    // 返佣率
    private double commissionRate = Double.MAX_VALUE;



    // 区域编号
    private String areaCode = new String();

    // 生效时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String effectiveTime = new String();

    // 期限
    @DataAdapter(fieldType = FieldType.DATE)
    private String commissionTime = new String();


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

    public int getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }

    public String getCommissionLevel() {
        return commissionLevel;
    }

    public void setCommissionLevel(String commissionLevel) {
        this.commissionLevel = commissionLevel;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getCommissionTime() {
        return commissionTime;
    }

    public void setCommissionTime(String commissionTime) {
        this.commissionTime = commissionTime;
    }
}
