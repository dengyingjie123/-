package com.youngbook.entity.po.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/16/14
 * Time: 3:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_productioncomposition", jsonPrefix = "productionComposition")
public class ProductionCompositionPO extends BasePO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    //产品编号
    private String productionId = new String();

    //名称
    private String name = new String();

    // 范围开始
    private double sizeStart = Double.MAX_VALUE;

    // 范围结束
    private double sizeStop = Double.MAX_VALUE;

    // 预期收益率
    private double expectedYield = Double.MAX_VALUE;

    // 浮动收益率
    private double floatingRate = Double.MAX_VALUE;

    //购买费率
    private double buyingRate = Double.MAX_VALUE;

    //支付费率
    private double payRate = Double.MAX_VALUE;

    /**
     * 直销费率
     */
    private double directSellingRate = Double.MAX_VALUE;

    /**
     * 渠道费率
     */
    private double channelSellingRate = Double.MAX_VALUE;


    /**
     * 银行托管费率
     */
    private double bankingRate = Double.MAX_VALUE;

    // 操作人
    private String operatorId = new String();

    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 返佣等级
    private String commissionLevel = new String();

    // 是否开启返佣修正，0否1是
    private int needCommissionCorrection = Integer.MAX_VALUE;

    // 是否开启客户类型返佣修正，0否1是
    private int needCustomerTypeCommissionCorrection = Integer.MAX_VALUE;

    public double getDirectSellingRate() {
        return directSellingRate;
    }

    public void setDirectSellingRate(double directSellingRate) {
        this.directSellingRate = directSellingRate;
    }

    public double getChannelSellingRate() {
        return channelSellingRate;
    }

    public void setChannelSellingRate(double channelSellingRate) {
        this.channelSellingRate = channelSellingRate;
    }

    public double getBankingRate() {
        return bankingRate;
    }

    public void setBankingRate(double bankingRate) {
        this.bankingRate = bankingRate;
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

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(double expectedYield) {
        this.expectedYield = expectedYield;
    }

    public double getFloatingRate() {
        return floatingRate;
    }

    public void setFloatingRate(double floatingRate) {
        this.floatingRate = floatingRate;
    }

    public double getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(double buyingRate) {
        this.buyingRate = buyingRate;
    }

    public double getPayRate() {
        return payRate;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
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

    public int getNeedCommissionCorrection() {
        return needCommissionCorrection;
    }

    public void setNeedCommissionCorrection(int needCommissionCorrection) {
        this.needCommissionCorrection = needCommissionCorrection;
    }

    public int getNeedCustomerTypeCommissionCorrection() {
        return needCustomerTypeCommissionCorrection;
    }

    public void setNeedCustomerTypeCommissionCorrection(int needCustomerTypeCommissionCorrection) {
        this.needCustomerTypeCommissionCorrection = needCustomerTypeCommissionCorrection;
    }
}
