package com.youngbook.entity.vo.Sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015/4/7.
 * 投资参与者
 */

@Table(name = "Sale_InvestmentParticipant", jsonPrefix = "investmentParticipantVO")
public class InvestmentParticipantVO extends BaseVO {
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

    // 投资计划编号
    private String investmentId = new String();

    // 客户编号
    private String customerId = new String();


    // 参与状态 : KV下拉菜单【Sale_InvestmentParticipantJoinStatus】
    private String joinStatus = new String();

    // 参与类型 : KV下拉菜单【Sale_InvestmentParticipantJoinType】
    private String joinType = new String();

    // 参与金额
    private double joinMoney = Double.MAX_VALUE;

    // 参与时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String joinTime = new String();

    // 预约时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String appointmentTime = new String();

    // 退出时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String quitTime = new String();

    // 客户注册名
    private String loginName = new String();

    //客户名称
    private String customerName = new String();

    //参与状态
    private String kv_StatusName = new String();

    //参与类型
    private String kv_TypeName = new String();

    //操作员名称
    private String operatorName= new String();
    //投资项目名称
    private String investmentplanName = new String();

   //getset

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

    public String getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(String investmentId) {
        this.investmentId = investmentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getKv_StatusName() {
        return kv_StatusName;
    }

    public void setKv_StatusName(String kv_StatusName) {
        this.kv_StatusName = kv_StatusName;
    }

    public String getKv_TypeName() {
        return kv_TypeName;
    }

    public void setKv_TypeName(String kv_TypeName) {
        this.kv_TypeName = kv_TypeName;
    }

    public String getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(String joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public double getJoinMoney() {
        return joinMoney;
    }

    public void setJoinMoney(double joinMoney) {
        this.joinMoney = joinMoney;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(String quitTime) {
        this.quitTime = quitTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getInvestmentplanName() {
        return investmentplanName;
    }

    public void setInvestmentplanName(String investmentplanName) {
        this.investmentplanName = investmentplanName;
    }

    public InvestmentParticipantVO() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
