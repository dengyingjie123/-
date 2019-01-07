package com.youngbook.entity.vo.Sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.dao.JSONDao;
import com.youngbook.entity.vo.BaseVO;
import net.sf.json.JSONObject;

import java.util.DoubleSummaryStatistics;

/**
 * Created by Administrator on 2015/3/25.
 */
@Table(name = "finance_PaymentPlanVO", jsonPrefix = "paymentPlanVO")
public class PaymentPlanVO extends BaseVO {
    // 序号
    @Id
    private int sid = Integer.MAX_VALUE;
    // 编号
    private String id = new String();
    //状态
    private int state = Integer.MAX_VALUE;
    //名称
    private String name = new String();
    //兑付类型
    private String type = new String();
    //兑付金额
    private double paymentMoney = Double.MAX_VALUE;
    //兑付日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String paymentTime = new String();
    //兑付总期数
    private int totalInstallment = Integer.MAX_VALUE;
    //当前兑付期数
    private int currentInstallment = Integer.MAX_VALUE;
    //兑付状态
    private String statusName = new String();
    //应兑付总金额
    private double totalPaymentMoney = Double.MAX_VALUE;

    private String totalPaymentMoneyFormatted = "";
    //应兑付本金总金额
    private double totalPaymentPrincipalMoney = Double.MAX_VALUE;

    String totalPaymentPrincipalMoneyFormatted = "";
    //应兑付收益总金额
    private double totalProfitMoney = Double.MAX_VALUE;

    String totalProfitMoneyFormatted = "";

    //已兑付本金金额
    private double paiedPrincipalMoney = Double.MAX_VALUE;
    //已兑付收益金额
    private double paiedProfitMoney = Double.MAX_VALUE;
    //备注
    private String comment = new String();
    //进行兑换查询
    private int search_status = Integer.MAX_VALUE;
    // 客户注册用户名
    private String loginName = new String();
    //客户名称
    private String customerName = new String();


    //产品名称
    private String productionName = new String();
    //订单编号
    private String orderId = new String();
    //订单名称
    private String orderName = new String();

    //项目名称
    private String projectName = new String();
    //项目编号
    private String projectId = new String();
    //产品id
    private String productId = new String();
    //以兑付时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String paiedPaymentTime= new String();


    // 部门负责人编号
    private String departmentLeaderId = new String();

    // 部门负责人名称 : 支持查询
    private String departmentLeaderName = new String();

    // 部门负责人审核内容 : 支持查询
    private String departmentLeaderContent = new String();

    // 部门负责人审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentLeaderTime = new String();

    // 部门负责人审核状态
    private int departmentLeaderStatus = Integer.MAX_VALUE;

    private String customerId = new String();

    //认购时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String payTime = new String();

    // 分管领导编号
    private String chargeLeaderId = new String();

    // 分管领导名称 : 支持查询
    private String chargeLeaderName = new String();

    // 分管领导审核内容 : 支持查询
    private String chargeLeaderContent = new String();

    // 分管领导审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String chargeLeaderTime = new String();

    // 分管领导审核状态
    private int chargeLeaderStatus = Integer.MAX_VALUE;

    // 经手人编号
    private String submitterId = new String();

    private String weekOfDay = new String();

    // 经手人名称 : 支持查询
    private String submitterName = new String();

    // 经手人时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String submitterTime = new String();
    //标识号
    private String controlString3 = new String();

    private int status = Integer.MAX_VALUE;
    private int interestCycle = Integer.MAX_VALUE;
    private int interestTimes = Integer.MAX_VALUE;
    private int interestUnit = Integer.MAX_VALUE;

    private String interestDescription = new String();

    // 审核提交人（申请人）
    private String confirmorId = new String();
    private String confirmorName = new String();
    @DataAdapter(fieldType = FieldType.DATE)
    private String confirmTime = new String();


    // 审核执行人（审核人）
    private String auditExecutorId = new String();

    //总兑付条数
    private String totalRecord  = new String();


    private String checkName = "";
    private String checkTime = "";

    private String checkName2 = "";
    private String checkTime2 = "";


    private String bankName = "";
    private String bankNumber = "";
    private String bankBranchName = "";


    private String saleManName = "";
    private String saleGroupName= "";
    private String mobile = "";

    public String getTotalPaymentPrincipalMoneyFormatted() {
        return totalPaymentPrincipalMoneyFormatted;
    }

    public void setTotalPaymentPrincipalMoneyFormatted(String totalPaymentPrincipalMoneyFormatted) {
        this.totalPaymentPrincipalMoneyFormatted = totalPaymentPrincipalMoneyFormatted;
    }

    public String getTotalProfitMoneyFormatted() {
        return totalProfitMoneyFormatted;
    }

    public void setTotalProfitMoneyFormatted(String totalProfitMoneyFormatted) {
        this.totalProfitMoneyFormatted = totalProfitMoneyFormatted;
    }

    public String getTotalPaymentMoneyFormatted() {
        return totalPaymentMoneyFormatted;
    }

    public void setTotalPaymentMoneyFormatted(String totalPaymentMoneyFormatted) {
        this.totalPaymentMoneyFormatted = totalPaymentMoneyFormatted;
    }

    public String getSaleManName() {
        return saleManName;
    }

    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
    }

    public String getSaleGroupName() {
        return saleGroupName;
    }

    public void setSaleGroupName(String saleGroupName) {
        this.saleGroupName = saleGroupName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getInterestCycle() {
        return interestCycle;
    }

    public void setInterestCycle(int interestCycle) {
        this.interestCycle = interestCycle;
    }

    public int getInterestTimes() {
        return interestTimes;
    }

    public void setInterestTimes(int interestTimes) {
        this.interestTimes = interestTimes;
    }

    public int getInterestUnit() {
        return interestUnit;
    }

    public void setInterestUnit(int interestUnit) {
        this.interestUnit = interestUnit;
    }

    public String getInterestDescription() {
        return interestDescription;
    }

    public void setInterestDescription(String interestDescription) {
        this.interestDescription = interestDescription;
    }

    private double money = Double.MAX_VALUE;

    public String getWeekOfDay() {
        return weekOfDay;
    }

    public void setWeekOfDay(String weekOfDay) {
        this.weekOfDay = weekOfDay;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckName2() {
        return checkName2;
    }

    public void setCheckName2(String checkName2) {
        this.checkName2 = checkName2;
    }

    public String getCheckTime2() {
        return checkTime2;
    }

    public void setCheckTime2(String checkTime2) {
        this.checkTime2 = checkTime2;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaiedPaymentTime() {
        return paiedPaymentTime;
    }

    public void setPaiedPaymentTime(String paiedPaymentTime) {
        this.paiedPaymentTime = paiedPaymentTime;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getTotalInstallment() {
        return totalInstallment;
    }

    public void setTotalInstallment(int totalInstallment) {
        this.totalInstallment = totalInstallment;
    }

    public int getCurrentInstallment() {
        return currentInstallment;
    }

    public void setCurrentInstallment(int currentInstallment) {
        this.currentInstallment = currentInstallment;
    }

    public double getTotalPaymentMoney() {
        return totalPaymentMoney;
    }

    public void setTotalPaymentMoney(double totalPaymentMoney) {
        this.totalPaymentMoney = totalPaymentMoney;
    }

    public double getTotalPaymentPrincipalMoney() {
        return totalPaymentPrincipalMoney;
    }

    public void setTotalPaymentPrincipalMoney(double totalPaymentPrincipalMoney) {
        this.totalPaymentPrincipalMoney = totalPaymentPrincipalMoney;
    }


    public double getTotalProfitMoney() {
        return totalProfitMoney;
    }

    public void setTotalProfitMoney(double totalProfitMoney) {
        this.totalProfitMoney = totalProfitMoney;
    }

    public double getPaiedPrincipalMoney() {
        return paiedPrincipalMoney;
    }

    public void setPaiedPrincipalMoney(double paiedPrincipalMoney) {
        this.paiedPrincipalMoney = paiedPrincipalMoney;
    }

    public double getPaiedProfitMoney() {
        return paiedProfitMoney;
    }

    public void setPaiedProfitMoney(double paiedProfitMoney) {
        this.paiedProfitMoney = paiedProfitMoney;
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

    public double getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(double paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getSearch_status() {
        return search_status;
    }

    public void setSearch_status(int search_status) {
        this.search_status = search_status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDepartmentLeaderId() {
        return departmentLeaderId;
    }

    public void setDepartmentLeaderId(String departmentLeaderId) {
        this.departmentLeaderId = departmentLeaderId;
    }

    public String getDepartmentLeaderName() {
        return departmentLeaderName;
    }

    public void setDepartmentLeaderName(String departmentLeaderName) {
        this.departmentLeaderName = departmentLeaderName;
    }

    public String getDepartmentLeaderContent() {
        return departmentLeaderContent;
    }

    public void setDepartmentLeaderContent(String departmentLeaderContent) {
        this.departmentLeaderContent = departmentLeaderContent;
    }

    public String getDepartmentLeaderTime() {
        return departmentLeaderTime;
    }

    public void setDepartmentLeaderTime(String departmentLeaderTime) {
        this.departmentLeaderTime = departmentLeaderTime;
    }

    public int getDepartmentLeaderStatus() {
        return departmentLeaderStatus;
    }

    public void setDepartmentLeaderStatus(int departmentLeaderStatus) {
        this.departmentLeaderStatus = departmentLeaderStatus;
    }

    public String getChargeLeaderId() {
        return chargeLeaderId;
    }

    public void setChargeLeaderId(String chargeLeaderId) {
        this.chargeLeaderId = chargeLeaderId;
    }

    public String getChargeLeaderName() {
        return chargeLeaderName;
    }

    public void setChargeLeaderName(String chargeLeaderName) {
        this.chargeLeaderName = chargeLeaderName;
    }

    public String getChargeLeaderContent() {
        return chargeLeaderContent;
    }

    public void setChargeLeaderContent(String chargeLeaderContent) {
        this.chargeLeaderContent = chargeLeaderContent;
    }

    public String getChargeLeaderTime() {
        return chargeLeaderTime;
    }

    public void setChargeLeaderTime(String chargeLeaderTime) {
        this.chargeLeaderTime = chargeLeaderTime;
    }

    public int getChargeLeaderStatus() {
        return chargeLeaderStatus;
    }

    public void setChargeLeaderStatus(int chargeLeaderStatus) {
        this.chargeLeaderStatus = chargeLeaderStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getSubmitterTime() {
        return submitterTime;
    }

    public void setSubmitterTime(String submitterTime) {
        this.submitterTime = submitterTime;
    }

    public String getControlString3() {
        return controlString3;
    }

    public void setControlString3(String controlString3) {
        this.controlString3 = controlString3;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getConfirmorId() {
        return confirmorId;
    }

    public void setConfirmorId(String confirmorId) {
        this.confirmorId = confirmorId;
    }

    public String getConfirmorName() {
        return confirmorName;
    }

    public void setConfirmorName(String confirmorName) {
        this.confirmorName = confirmorName;
    }

    public String getAuditExecutorId() {
        return auditExecutorId;
    }

    public void setAuditExecutorId(String auditExecutorId) {
        this.auditExecutorId = auditExecutorId;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }


    @Override
    public JSONObject toJsonObject4Form(){
        try {
            String prefix = new String();
            Table tableAnnotation = this.getClass().getAnnotation(Table.class);

            if (tableAnnotation != null) {
                prefix = tableAnnotation.jsonPrefix();
            }
            return JSONDao.get(this,prefix);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
