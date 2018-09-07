package com.youngbook.entity.wvo.sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.wvo.BaseWVO;

/**
 * Created by 邓超
 * Date 2015-4-17
 */
@Table(name = "Sale_InvestmentPlan", jsonPrefix = "investmentPlan")
public class InvestmentPlanWVO extends BaseWVO {

    //sid
    @Id
    private int sid = Integer.MAX_VALUE;
    //id
    private String id = new String();
    //state
    private int state = Integer.MAX_VALUE;
    //CustomerPersonalWVO
    private String operatorId = new String();
    //操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();
    //名称
    private String name = new String();
    //描述
    private String description = new String();
    //类型编号
    private String typeId = new String();
    //最小投资额
    private int investMoneyMin = Integer.MAX_VALUE;
    //最大投资额
    private int investMoneyMax = Integer.MAX_VALUE;

    //最短投资期限
    private int investTimeMin = Integer.MAX_VALUE;
    //最长投资期限
    private int investTimeMax = Integer.MAX_VALUE;

    //最小回报率
    private double returnRateMin = Double.MAX_VALUE;
    //最大回报率
    private double returnRateMax = Double.MAX_VALUE;
    //计划开始时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String planStartTime = new String();
    //计划结束时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String planTimeStop = new String();
    //创建时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();
    //创建人编号
    private String creatorId = new String();
    //审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String checkTime = new String();
    //审核人编号
    private String checkerId = new String();
    //发布时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String publishTime = new String();
    @DataAdapter(fieldType = FieldType.DATE)
    private String joinTime = new String();
    //发布人编号
    private String publisherId = new String();
    //操作员
    private String operatorName = new String();
    //创建人
    private String creatorName = new String();
    //审核人
    private String checkerName = new String();
    //发布人
    private String publisherName = new String();
    //参与金额
    private double joinMoney = Double.MAX_VALUE;

    // 网站显示的期限
    private String timeLimit = new String();

    // 查询列表匹配值，页面选择投资期限范围查询时跟此字段来查
    private int investTerm= Integer.MAX_VALUE;

    // 用户保存后台录入的投资期限，前台直接显示(如1年2个月、2.3+4.5月)
    private String investTermView = new String();

    public String getCreatorName() {
        return creatorName;
    }
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getPublisherName() {
        return publisherName;
    }
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getCheckerName() {
        return checkerName;
    }
    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getOperatorName() {
        return operatorName;
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getInvestMoneyMin() {
        return investMoneyMin;
    }
    public void setInvestMoneyMin(int investMoneyMin) {
        this.investMoneyMin = investMoneyMin;
    }

    public int getInvestMoneyMax() {
        return investMoneyMax;
    }
    public void setInvestMoneyMax(int investMoneyMax) {
        this.investMoneyMax = investMoneyMax;
    }

    public int getInvestTimeMin() {
        return investTimeMin;
    }
    public void setInvestTimeMin(int investTimeMin) {
        this.investTimeMin = investTimeMin;
    }

    public int getInvestTimeMax() {
        return investTimeMax;
    }
    public void setInvestTimeMax(int investTimeMax) {
        this.investTimeMax = investTimeMax;
    }

    public double getReturnRateMin() {
        return returnRateMin;
    }
    public void setReturnRateMin(double returnRateMin) {
        this.returnRateMin = returnRateMin;
    }

    public double getReturnRateMax() {
        return returnRateMax;
    }
    public void setReturnRateMax(double returnRateMax) {
        this.returnRateMax = returnRateMax;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }
    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanTimeStop() {
        return planTimeStop;
    }
    public void setPlanTimeStop(String planTimeStop) {
        this.planTimeStop = planTimeStop;
    }

    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCheckTime() {
        return checkTime;
    }
    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckerId() {
        return checkerId;
    }
    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public String getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublisherId() {
        return publisherId;
    }
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public double getJoinMoney() {return joinMoney;}
    public void setJoinMoney(double joinMoney) {this.joinMoney = joinMoney;}

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getInvestTerm() {
        return investTerm;
    }

    public void setInvestTerm(int investTerm) {
        this.investTerm = investTerm;
    }

    public String getInvestTermView() {
        return investTermView;
    }

    public void setInvestTermView(String investTermView) {
        this.investTermView = investTermView;
    }
}
