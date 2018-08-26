package com.youngbook.entity.vo.oa.expense;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015-5-19.
 */
public class FinanceBizTripExpenseItemVO extends BaseVO {

    @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();

    // ExpenseId : 主表关系【FinanceBizTripExpenseWFA,id,ExpenseId】
    private String expenseId = new String();

    // 开始时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();

    // 结束时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String endTime = new String();

    // 起始地
    private String startAddress = new String();

    // 结束地
    private String endAddress = new String();

    // 过路费
    private double roadFee = Double.MAX_VALUE;

    // 飞机票费
    private double airplaneFee = Double.MAX_VALUE;

    // 火车票费
    private double trainFee = Double.MAX_VALUE;

    // 汽车票费
    private double busFee = Double.MAX_VALUE;

    // 伙食补贴
    private double foodFee = Double.MAX_VALUE;

    // 住宿费
    private double liveFee = Double.MAX_VALUE;

    // 其他
    private double otherFee = Double.MAX_VALUE;

    // 合计
    private double totalFee = Double.MAX_VALUE;

    // 备注
    private String comment = new String();

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    //状态
    private int state = Integer.MAX_VALUE;

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

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public double getRoadFee() {
        return roadFee;
    }

    public void setRoadFee(double roadFee) {
        this.roadFee = roadFee;
    }

    public double getAirplaneFee() {
        return airplaneFee;
    }

    public void setAirplaneFee(double airplaneFee) {
        this.airplaneFee = airplaneFee;
    }

    public double getTrainFee() {
        return trainFee;
    }

    public void setTrainFee(double trainFee) {
        this.trainFee = trainFee;
    }

    public double getBusFee() {
        return busFee;
    }

    public void setBusFee(double busFee) {
        this.busFee = busFee;
    }

    public double getFoodFee() {
        return foodFee;
    }

    public void setFoodFee(double foodFee) {
        this.foodFee = foodFee;
    }

    public double getLiveFee() {
        return liveFee;
    }

    public void setLiveFee(double liveFee) {
        this.liveFee = liveFee;
    }

    public double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(double otherFee) {
        this.otherFee = otherFee;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

}
