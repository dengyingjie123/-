package com.youngbook.entity.vo.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-19
 * Time: 下午1:36
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_order", jsonPrefix = "orderVO")
public class OrderVO extends BaseVO {

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
    private String operateTime = new String();

    //订单号
    private String orderNum = new String();

    // 客户用户名
    private String loginName = new String();

    //客户名
    private String customerName = new String();

    //客户id
    private String customerId = new String();

    // 产品 ID
    private String productionId = new String();

    // 产品构成 ID
    private String productionCompositionId = new String();

    //产品名
    private String productionName = new String();

    //构成名
    private String productionCompositionName = new String();

    //购买金额
    private double money = Double.MAX_VALUE;
    private String moneyString = "";

    private String bankName = "";
    private String bankNumber = "";
    private String bankBranchName = "";

    private int status = Integer.MAX_VALUE;

    /**
     * 1：未兑付，5：已全部兑付，8：部分兑付
     * 在view_order视图里定义
     */
    private int paymentPlanStatus = Integer.MAX_VALUE;
    private String paymentPlanStatusName = "";

    //描述
    private String description = new String();

    // 项目名称
    private String projectName = new String();

    // 创建时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();

    //客户属性
    private String customerAttribute = new String();

    // 推荐人
    private String referralCode = new String();

    // 购买起息日
    @DataAdapter(fieldType = FieldType.DATE)
    private String valueDate = new String();

    // 证件号
    private String customerCertificateNumber = "";

    // 手机号
    private String mobile = "";

    // 收益率
    private double expectedYield = Double.MAX_VALUE;

    // 下订单时选择的账户 ID
    private String accountId = new String();

    // 销售人员 ID
    private String salesmanId = new String();
    private String salesmanName = new String();


    private String groupName = "";


    // 预约时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String appointmentTime = new String();

    // 打款时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String payTime = new String();
    private String payDate = "";

    // 申请退款时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String applyDrawbackTime = new String();

    // 退款时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String drawbackTime = new String();

    // 作废时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String cancelTime = new String();

    // 已兑付时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String paybackTime = new String();

    // 合同编号
    private String contractNo = new String();
    //订单状态
    private String statusName = "";


    /**
     * 0: 预约订单
     * 1：已支付状态的订单，包括部分兑付、已确认等
     * 2：已兑付订单
     */
    private String commonStatus = "";

    //到期收益
    private double expireIncome = Double.MAX_VALUE;

    // 下一个兑付时间
    @DataAdapter(fieldType = FieldType.DATE)
    public String nextPaymentTime = new String();

    // 返佣率等级
    private String commissionLevel = new String();
    // 返佣率
    private double commissionRate = Double.MAX_VALUE;
    // 返佣金额
    private double commissionMoney = Double.MAX_VALUE;

    private int moneyTransferStatus = Integer.MAX_VALUE;
    private String moneyTransferTime = "";

    /**
     * 兑付计划确认人
     */
    private String confirmorName = "";
    private String payCode = "";

    private int payChannel = Integer.MAX_VALUE;

    /**
     * 已兑付的兑付计划数
     */
    private int paymentPlanPaidCount = Integer.MAX_VALUE;

    /**
     * 未兑付的兑付计划数
     */
    private int paymentPlanUnpaidCount = Integer.MAX_VALUE;

    @DataAdapter(fieldType = FieldType.DATE)
    private String paymentPlanLastTime = new String();

    private String payChannelName = "";

    private String investTermView = "";

    /**
     * 项目类型编号
     */
    private String projectTypeId = "";

    /**
     * 财务确认人01
     */
    private String orderConfirmUserId01 = "";
    private String orderConfirmUserName01 = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String orderConfirmUserTime01 = "";

    /**
     * 财务确认人02
     */
    private String orderConfirmUserId02 = "";
    private String orderConfirmUserName02 = "";


    @DataAdapter(fieldType = FieldType.DATE)
    private String orderConfirmUserTime02 = new String();

    private String financeMoneyConfirm = "";


    public String getFinanceMoneyConfirm() {
        return financeMoneyConfirm;
    }

    public void setFinanceMoneyConfirm(String financeMoneyConfirm) {
        this.financeMoneyConfirm = financeMoneyConfirm;
    }

    private String financeMoneyConfirmUserId = "";
    private String financeMoneyConfirmUserName = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String financeMoneyConfirmTime = new String();

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPaymentPlanLastTime() {
        return paymentPlanLastTime;
    }

    public void setPaymentPlanLastTime(String paymentPlanLastTime) {
        this.paymentPlanLastTime = paymentPlanLastTime;
    }

    public String getFinanceMoneyConfirmUserName() {
        return financeMoneyConfirmUserName;
    }

    public void setFinanceMoneyConfirmUserName(String financeMoneyConfirmUserName) {
        this.financeMoneyConfirmUserName = financeMoneyConfirmUserName;
    }

    public String getFinanceMoneyConfirmUserId() {
        return financeMoneyConfirmUserId;
    }

    public void setFinanceMoneyConfirmUserId(String financeMoneyConfirmUserId) {
        this.financeMoneyConfirmUserId = financeMoneyConfirmUserId;
    }

    public String getFinanceMoneyConfirmTime() {
        return financeMoneyConfirmTime;
    }

    public void setFinanceMoneyConfirmTime(String financeMoneyConfirmTime) {
        this.financeMoneyConfirmTime = financeMoneyConfirmTime;
    }

    public String getCommonStatus() {
        return commonStatus;
    }

    public void setCommonStatus(String commonStatus) {
        this.commonStatus = commonStatus;
    }

    public int getPaymentPlanStatus() {
        return paymentPlanStatus;
    }

    public void setPaymentPlanStatus(int paymentPlanStatus) {
        this.paymentPlanStatus = paymentPlanStatus;
    }

    public int getPaymentPlanPaidCount() {
        return paymentPlanPaidCount;
    }

    public void setPaymentPlanPaidCount(int paymentPlanPaidCount) {
        this.paymentPlanPaidCount = paymentPlanPaidCount;
    }

    public int getPaymentPlanUnpaidCount() {
        return paymentPlanUnpaidCount;
    }

    public void setPaymentPlanUnpaidCount(int paymentPlanUnpaidCount) {
        this.paymentPlanUnpaidCount = paymentPlanUnpaidCount;
    }

    public String getOrderConfirmUserName01() {
        return orderConfirmUserName01;
    }

    public void setOrderConfirmUserName01(String orderConfirmUserName01) {
        this.orderConfirmUserName01 = orderConfirmUserName01;
    }

    public String getOrderConfirmUserName02() {
        return orderConfirmUserName02;
    }

    public void setOrderConfirmUserName02(String orderConfirmUserName02) {
        this.orderConfirmUserName02 = orderConfirmUserName02;
    }

    public String getOrderConfirmUserId01() {
        return orderConfirmUserId01;
    }

    public void setOrderConfirmUserId01(String orderConfirmUserId01) {
        this.orderConfirmUserId01 = orderConfirmUserId01;
    }

    public String getOrderConfirmUserTime01() {
        return orderConfirmUserTime01;
    }

    public void setOrderConfirmUserTime01(String orderConfirmUserTime01) {
        this.orderConfirmUserTime01 = orderConfirmUserTime01;
    }

    public String getOrderConfirmUserId02() {
        return orderConfirmUserId02;
    }

    public void setOrderConfirmUserId02(String orderConfirmUserId02) {
        this.orderConfirmUserId02 = orderConfirmUserId02;
    }

    public String getOrderConfirmUserTime02() {
        return orderConfirmUserTime02;
    }

    public void setOrderConfirmUserTime02(String orderConfirmUserTime02) {
        this.orderConfirmUserTime02 = orderConfirmUserTime02;
    }

    public String getMoneyString() {
        return moneyString;
    }

    public void setMoneyString(String moneyString) {
        this.moneyString = moneyString;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getConfirmorName() {
        return confirmorName;
    }

    public void setConfirmorName(String confirmorName) {
        this.confirmorName = confirmorName;
    }

    public String getInvestTermView() {
        return investTermView;
    }

    public void setInvestTermView(String investTermView) {
        this.investTermView = investTermView;
    }


    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayChannelName() {
        return payChannelName;
    }

    public void setPayChannelName(String payChannelName) {
        this.payChannelName = payChannelName;
    }

    public int getMoneyTransferStatus() {
        return moneyTransferStatus;
    }

    public void setMoneyTransferStatus(int moneyTransferStatus) {
        this.moneyTransferStatus = moneyTransferStatus;
    }

    public String getMoneyTransferTime() {
        return moneyTransferTime;
    }

    public void setMoneyTransferTime(String moneyTransferTime) {
        this.moneyTransferTime = moneyTransferTime;
    }

    // 证件号码
    public String getCustomerCertificateNumber() {
        return customerCertificateNumber;
    }

    public void setCustomerCertificateNumber(String customerCertificateNumber) {
        this.customerCertificateNumber = customerCertificateNumber;
    }

    // HOPEWEALTH-1312 begin
    // 产品规模
    private String productionScale = new String();
    // 产品期限
    private String productionTerm = new String();
    // 预期收益（结果）
    private double expectedProfit = Double.MAX_VALUE;
    // 付息方式
    private String interestType = new String();
    // 打款银行
    private String payBank = new String();

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(double expectedYield) {
        this.expectedYield = expectedYield;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getProductionCompositionName() {
        return productionCompositionName;
    }

    public void setProductionCompositionName(String productionCompositionName) {
        this.productionCompositionName = productionCompositionName;
    }

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerAttribute() {
        return customerAttribute;
    }

    public void setCustomerAttribute(String customerAttribute) {
        this.customerAttribute = customerAttribute;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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

    public String getProductionCompositionId() {
        return productionCompositionId;
    }

    public void setProductionCompositionId(String productionCompositionId) {
        this.productionCompositionId = productionCompositionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getApplyDrawbackTime() {
        return applyDrawbackTime;
    }

    public void setApplyDrawbackTime(String applyDrawbackTime) {
        this.applyDrawbackTime = applyDrawbackTime;
    }

    public String getDrawbackTime() {
        return drawbackTime;
    }

    public void setDrawbackTime(String drawbackTime) {
        this.drawbackTime = drawbackTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getPaybackTime() {
        return paybackTime;
    }

    public void setPaybackTime(String paybackTime) {
        this.paybackTime = paybackTime;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public double getExpireIncome() {
        return expireIncome;
    }

    public void setExpireIncome(double expireIncome) {
        this.expireIncome = expireIncome;
    }

    public String getNextPaymentTime() {
        return nextPaymentTime;
    }

    public void setNextPaymentTime(String nextPaymentTime) {
        this.nextPaymentTime = nextPaymentTime;
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

    public double getCommissionMoney() {
        return commissionMoney;
    }

    public void setCommissionMoney(double commissionMoney) {
        this.commissionMoney = commissionMoney;
    }

    public String getProductionScale() {
        return productionScale;
    }

    public void setProductionScale(String productionScale) {
        this.productionScale = productionScale;
    }

    public String getProductionTerm() {
        return productionTerm;
    }

    public void setProductionTerm(String productionTerm) {
        this.productionTerm = productionTerm;
    }

    public double getExpectedProfit() {
        return expectedProfit;
    }

    public void setExpectedProfit(double expectedProfit) {
        this.expectedProfit = expectedProfit;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getPaymentPlanStatusName() {
        return paymentPlanStatusName;
    }

    public void setPaymentPlanStatusName(String paymentPlanStatusName) {
        this.paymentPlanStatusName = paymentPlanStatusName;
    }
}
