package com.youngbook.entity.vo.Sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * HOPEWEALTH-1293
 * 返佣率VO
 * Created by zengwk on 2016/03/21.
 */
@Table(name = "sale_ProductionCommission", jsonPrefix = "productionCommissionVO")
public class ProductionCommissionVO extends BaseVO {
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

    // 返佣等级
    private String commissionLevel = new String();

    // 返佣率
    private double commissionRate = Double.MAX_VALUE;

    // 区域编号
    private String areaCode = new String();

    // 生效时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String effectieTime = new String();

    private double sizeStart = Double.MAX_VALUE;
    private double sizeStop = Double.MAX_VALUE;

    private double expectedYield = Double.MAX_VALUE;
    // 最大返佣率
    private double maxCommissionRate = Double.MAX_VALUE;

    public double getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(double expectedYield) {
        this.expectedYield = expectedYield;
    }

    public double getSizeStart() {
        return sizeStart;
    }

    public void setSizeStart(double sizeStart) {
        this.sizeStart = sizeStart;
    }

    public double getSizeStop() {
        return sizeStop;
    }

    public void setSizeStop(double sizeStop) {
        this.sizeStop = sizeStop;
    }

    public double getMaxCommissionRate() {
        return maxCommissionRate;
    }

    public void setMaxCommissionRate(double maxCommissionRate) {
        this.maxCommissionRate = maxCommissionRate;
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

    public String getEffectieTime() {
        return effectieTime;
    }

    public void setEffectieTime(String effectieTime) {
        this.effectieTime = effectieTime;
    }
}
