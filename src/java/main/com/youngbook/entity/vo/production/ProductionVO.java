package com.youngbook.entity.vo.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

@Table(name = "crm_production", jsonPrefix = "productionVO")
public class ProductionVO extends BaseVO {
    // id
    private String id = new String();

    // 所属项目
    private String projectName = new String();

    // 名称
    private String productId = new String();

    // 名称
    private String name = new String();

    // 产品名称
    private String productName = new String();

    // 配额(钱)
    private double size = Double.MAX_VALUE;

    // 开始时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();

    // 结束时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String stopTime = new String();

    // 起息日
    @DataAdapter(fieldType = FieldType.DATE)
    private String valueDate = new String();

    // 到期日
    @DataAdapter(fieldType = FieldType.DATE)
    private String expiringDate = new String();

    // 付息日
    @DataAdapter(fieldType = FieldType.DATE)
    private String interestDate = new String();

    // 兑付计划状态
    private String isPaymentPlanStatus = new String();

    // 网站显示的名称
    private String websiteDisplayName = new String();

    // 项目ID
    private String projectId = new String();

    // 状态
    private String status = new String();
    private String statusName = new String();

    // 预约金额
    private double appointmentMoney = Double.MAX_VALUE;

    // 期限
    private double process = Double.MAX_VALUE;

    // 销售金额
    private double saleMoney = Double.MAX_VALUE;
    private double saleMoneyPercent = Double.MAX_VALUE;

    // 产品详情
    private String description = new String();

    // 付息周期
    private int interestCycle = Integer.MAX_VALUE;

    // 付息期数
    private int interestTimes = Integer.MAX_VALUE;

    // 付息类型
    private String interestType = new String();

    // 付息单位
    private String interestUnit = new String();

    // 合同一式份
    private int contractCopies = Integer.MAX_VALUE;

    // 所属产品
    private String productHomeId = new String();

    // 合同套数
    private int contractCount = Integer.MAX_VALUE;


    private double totalCost = Double.MAX_VALUE;

    // 查询列表匹配值，页面选择投资期限范围查询时跟此字段来查
    private int investTerm = Integer.MAX_VALUE;

    // 用户保存后台录入的投资期限，前台直接显示(如1年2个月、2.3+4.5月)
    private String investTermView = new String();

    private String productionName = new String();

    private double transferMoney = Double.MAX_VALUE;
    private double paymentProfitMoney = Double.MAX_VALUE;
    private double saleProcess = Double.MAX_VALUE;
    private double paymentProcess = Double.MAX_VALUE;

    private double discountRate = Double.MAX_VALUE;


    // 最小和最大年化收益率
    private Double minExpectedYield = Double.MAX_VALUE;
    private Double maxExpectedYield = Double.MAX_VALUE;

    // 产品概述
    private String productionDescription = new String();
    // 最小起投金额
    private int minSizeStart = Integer.MAX_VALUE;

    private String statusStr=new String();


    // 最大返佣率 HOPEWEALTH-1293
    private double maxCommissionRate = Double.MAX_VALUE;


    private String projectType = "";
    private String projectTypeName = "";

    private int remainDays = Integer.MAX_VALUE;


    private String displayType = "";
    private String orders = "";

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    public double getSaleMoneyPercent() {
        return saleMoneyPercent;
    }

    public void setSaleMoneyPercent(double saleMoneyPercent) {
        this.saleMoneyPercent = saleMoneyPercent;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(String expiringDate) {
        this.expiringDate = expiringDate;
    }

    public String getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(String interestDate) {
        this.interestDate = interestDate;
    }

    public String getWebsiteDisplayName() {
        return websiteDisplayName;
    }

    public void setWebsiteDisplayName(String websiteDisplayName) {
        this.websiteDisplayName = websiteDisplayName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public double getAppointmentMoney() {
        return appointmentMoney;
    }

    public void setAppointmentMoney(double appointmentMoney) {
        this.appointmentMoney = appointmentMoney;
    }

    public double getProcess() {
        return process;
    }

    public void setProcess(double process) {
        this.process = process;
    }

    public double getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(double saleMoney) {
        this.saleMoney = saleMoney;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getInterestUnit() {
        return interestUnit;
    }

    public void setInterestUnit(String interestUnit) {
        this.interestUnit = interestUnit;
    }

    public int getContractCopies() {
        return contractCopies;
    }

    public void setContractCopies(int contractCopies) {
        this.contractCopies = contractCopies;
    }

    public String getProductHomeId() {
        return productHomeId;
    }

    public void setProductHomeId(String productHomeId) {
        this.productHomeId = productHomeId;
    }

    public int getContractCount() {
        return contractCount;
    }

    public void setContractCount(int contractCount) {
        this.contractCount = contractCount;
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

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public double getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(double transferMoney) {
        this.transferMoney = transferMoney;
    }

    public double getPaymentProfitMoney() {
        return paymentProfitMoney;
    }

    public void setPaymentProfitMoney(double paymentProfitMoney) {
        this.paymentProfitMoney = paymentProfitMoney;
    }

    public double getSaleProcess() {
        return saleProcess;
    }

    public void setSaleProcess(double saleProcess) {
        this.saleProcess = saleProcess;
    }

    public double getPaymentProcess() {
        return paymentProcess;
    }

    public void setPaymentProcess(double paymentProcess) {
        this.paymentProcess = paymentProcess;
    }

    public String getIsPaymentPlanStatus() {
        return isPaymentPlanStatus;
    }

    public void setIsPaymentPlanStatus(String isPaymentPlanStatus) {
        this.isPaymentPlanStatus = isPaymentPlanStatus;
    }

    public Double getMinExpectedYield() {
        return minExpectedYield;
    }

    public void setMinExpectedYield(Double minExpectedYield) {
        this.minExpectedYield = minExpectedYield;
    }

    public Double getMaxExpectedYield() {
        return maxExpectedYield;
    }

    public void setMaxExpectedYield(Double maxExpectedYield) {
        this.maxExpectedYield = maxExpectedYield;
    }

    public String getProductionDescription() {
        return productionDescription;
    }

    public void setProductionDescription(String productionDescription) {
        this.productionDescription = productionDescription;
    }

    public int getMinSizeStart() {
        return minSizeStart;
    }

    public void setMinSizeStart(int minSizeStart) {
        this.minSizeStart = minSizeStart;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }


    public double getMaxCommissionRate() {
        return maxCommissionRate;
    }

    public void setMaxCommissionRate(double maxCommissionRate) {
        this.maxCommissionRate = maxCommissionRate;
    }
}
