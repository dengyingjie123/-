package com.youngbook.entity.po.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;


@Table(name = "crm_production", jsonPrefix = "production",backupTableName = "crm_production_archive")
public class ProductionPO extends BasePO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    //项目编号
    private String projectId = new String();

    //名称
    private String name = new String();

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

    // 状态
    private int status = Integer.MAX_VALUE;

    /**
     * 网站显示名称
     */
    private String websiteDisplayName = new String();

    // 操作人
    private String operatorId = new String();

    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();
    //付息周期
    private int interestCycle = Integer.MAX_VALUE;
    //付息期数
    private int interestTimes = Integer.MAX_VALUE;
    //付息类型
    private String interestType = new String();

    // 付息单位
    private int interestUnit = Integer.MAX_VALUE;

    // 合同一式份
    private int contractCopies = Integer.MAX_VALUE;

    // 合同套数
    private int contractCount = Integer.MAX_VALUE;

    // 查询列表匹配值，页面选择投资期限范围查询时跟此字段来查
    private int investTerm = Integer.MAX_VALUE;

    // 用户保存后台录入的投资期限，前台直接显示(如1年2个月、2.3+4.5月)
    private String investTermView = new String();

    // 产品自定义编号
    private String productionNo = "";

    // 产品ID - 对应产品
    private String productHomeId = "";

    //产品描述
    private String productionDescription = new String();


    private String interestPaymentDescription = new String();

    //融资机构
    private String financeInstitution = "";

    /**
     * 展示类型
     * 0：内部，1：理财圈
     */
    private String displayType = "";

    /**
     * 排序
     */
    private String orders = "";


    /**
     * 折标费率
     */
    private double discountRate = Double.MAX_VALUE;


    /**
     * 总成本
     */
    private double totalCost = Double.MAX_VALUE;


    /**
     * 通联万小宝供应商编码
     */
    private String allinpayCircle_SupplyInstCode = "";

    /**
     * 通联万小宝产品编码
     */
    private String allinpayCircle_ProductNum = "";


    /**
     * 通联万小宝现金账户代码
     */
    private String allinpayCircle_ProductCodeCashAcct = "";

    public String getAllinpayCircle_ProductCodeCashAcct() {
        return allinpayCircle_ProductCodeCashAcct;
    }

    public void setAllinpayCircle_ProductCodeCashAcct(String allinpayCircle_ProductCodeCashAcct) {
        this.allinpayCircle_ProductCodeCashAcct = allinpayCircle_ProductCodeCashAcct;
    }

    public String getAllinpayCircle_SupplyInstCode() {
        return allinpayCircle_SupplyInstCode;
    }

    public void setAllinpayCircle_SupplyInstCode(String allinpayCircle_SupplyInstCode) {
        this.allinpayCircle_SupplyInstCode = allinpayCircle_SupplyInstCode;
    }

    public String getAllinpayCircle_ProductNum() {
        return allinpayCircle_ProductNum;
    }

    public void setAllinpayCircle_ProductNum(String allinpayCircle_ProductNum) {
        this.allinpayCircle_ProductNum = allinpayCircle_ProductNum;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * get和set方法
     *
     * @return
     */


    public int getContractCopies() {
        return contractCopies;
    }

    public void setContractCopies(int contractCopies) {
        this.contractCopies = contractCopies;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProductionNo() {
        return productionNo;
    }

    public void setProductionNo(String productionNo) {
        this.productionNo = productionNo;
    }

    public String getWebsiteDisplayName() {
        return websiteDisplayName;
    }

    public void setWebsiteDisplayName(String websiteDisplayName) {
        this.websiteDisplayName = websiteDisplayName;
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

    public int getInterestUnit() {
        return interestUnit;
    }

    public void setInterestUnit(int interestUnit) {
        this.interestUnit = interestUnit;
    }

    public String getProductionDescription() {
        return productionDescription;
    }

    public void setProductionDescription(String productionDescription) {
        this.productionDescription = productionDescription;
    }

    public String getInterestPaymentDescription() {
        return interestPaymentDescription;
    }

    public void setInterestPaymentDescription(String interestPaymentDescription) {
        this.interestPaymentDescription = interestPaymentDescription;
    }

    public String getFinanceInstitution() {
        return financeInstitution;
    }

    public void setFinanceInstitution(String financeInstitution) {
        this.financeInstitution = financeInstitution;
    }

    public String getProductHomeId() {
        return productHomeId;
    }

    public void setProductHomeId(String productHomeId) {
        this.productHomeId = productHomeId;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }
}
