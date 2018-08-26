package com.youngbook.entity.wvo.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.wvo.BaseWVO;

/**
 * Created by Administrator on 2015-4-17.
 */
@Table(name = "crm_production_vo", jsonPrefix = "productionWVO")
public class ProductionWVO extends BaseWVO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    //所属项目
    private String projectName = new String();

    //名称
    private String productionNo = new String();
    private String name = new String();
    //起投资金
    private double sizeStart =  Double.MAX_VALUE;
    private double sizeStop =  Double.MAX_VALUE;
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

    //
    private String projectId = new String();
    private String productHomeId = new String();

    // 状态
    private int status = Integer.MAX_VALUE;
    // 类型
    private String interestType =new String();
    //预约金额
    private double appointmentMoney =  Double.MAX_VALUE;

    // 期限
    private double process = Double.MAX_VALUE;

    //销售金额
    private double saleMoney =  Double.MAX_VALUE;

    // 产品详情
    private String description = new String();

    // 最小收益率
    private double minExpectedYield = Double.MAX_VALUE;


    // 付息单位
    private int interestUnit = Integer.MAX_VALUE;


    //付息周期
    private int interestCycle = Integer.MAX_VALUE;

    public double getSizeStart() {
        return sizeStart;
    }

    public void setSizeStart(double sizeStart) {
        this.sizeStart = sizeStart;
    }

    // 最大收益率
    private double maxExpectedYield = Double.MAX_VALUE;

    private double expectedYield = Double.MAX_VALUE;
    private String expectedYieldDescription  = "";

    private double transferMoney = Double.MAX_VALUE;

    private double paymentMoney = Double.MAX_VALUE;

    private double paymentProfitMoney = Double.MAX_VALUE;

    private double appointmentProcess = Double.MAX_VALUE;

    private double saleProcess = Double.MAX_VALUE;

    private double paymentProcess = Double.MAX_VALUE;

    // 网站显示的期限
    private String timeLimit = new String();

    // 产品构成ID
    private String compositionId = new String();

    // 查询列表匹配值，页面选择投资期限范围查询时跟此字段来查
    private int investTerm = Integer.MAX_VALUE;

    // 用户保存后台录入的投资期限，前台直接显示(如1年2个月、2.3+4.5月)
    private String investTermView = new String();

    /**
     *网站显示名称
     */
    private String websiteDisplayName = new String();


    private int incomeFactor =Integer.MAX_VALUE;

    private int safeFactor =Integer.MAX_VALUE;

    //当前时间与结束时间之差
    private String stopTimeDay = new String();

    private String interestPaymentDescription = "";

    //产品描述
    private String productionDescription = new String();

    private long remainSeconds = Long.MAX_VALUE;

    /**
     * 排序
     */
    private String orders = "";

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getInterestPaymentDescription() {
        return interestPaymentDescription;
    }

    public void setInterestPaymentDescription(String interestPaymentDescription) {
        this.interestPaymentDescription = interestPaymentDescription;
    }

    public String getProductHomeId() {
        return productHomeId;
    }

    public void setProductHomeId(String productHomeId) {
        this.productHomeId = productHomeId;
    }

    public String getExpectedYieldDescription() {
        return expectedYieldDescription;
    }

    public void setExpectedYieldDescription(String expectedYieldDescription) {
        this.expectedYieldDescription = expectedYieldDescription;
    }

    public long getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(long remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public String getWebsiteDisplayName() {
        return websiteDisplayName;
    }

    public void setWebsiteDisplayName(String websiteDisplayName) {
        this.websiteDisplayName = websiteDisplayName;
    }

    public double getSizeStop() {
        return sizeStop;
    }

    public void setSizeStop(double sizeStop) {
        this.sizeStop = sizeStop;
    }

    public double getProcess() {
        return process;
    }
    public void setProcess(double process) {
        this.process = process;
    }

    public String getProductionNo() {
        return productionNo;
    }

    public void setProductionNo(String productionNo) {
        this.productionNo = productionNo;
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

    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public double getAppointmentMoney() {
        return appointmentMoney;
    }
    public void setAppointmentMoney(double appointmentMoney) {
        this.appointmentMoney = appointmentMoney;
    }

    public double getSaleMoney() {
        return saleMoney;
    }
    public void setSaleMoney(double saleMoney) {
        this.saleMoney = saleMoney;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getTransferMoney() {return transferMoney;}
    public void setTransferMoney(double transferMoney) {this.transferMoney = transferMoney;}

    public double getPaymentMoney() {return paymentMoney;}
    public void setPaymentMoney(double paymentMoney) {this.paymentMoney = paymentMoney;}

    public double getPaymentProfitMoney() {return paymentProfitMoney;}
    public void setPaymentProfitMoney(double paymentProfitMoney) {this.paymentProfitMoney = paymentProfitMoney;}

    public double getAppointmentProcess() {return appointmentProcess;}
    public void setAppointmentProcess(double appointmentProcess) {this.appointmentProcess = appointmentProcess;}

    public double getSaleProcess() {return saleProcess;}
    public void setSaleProcess(double saleProcess) {this.saleProcess = saleProcess;}

    public double getPaymentProcess() {return paymentProcess;}
    public void setPaymentProcess(double paymentProcess) {this.paymentProcess = paymentProcess;}

    public double getMinExpectedYield() {return minExpectedYield;}
    public void setMinExpectedYield(double minExpectedYield) {this.minExpectedYield = minExpectedYield;}

    public String getCompositionId() {return compositionId;}
    public void setCompositionId(String compositionId) {this.compositionId = compositionId;}

    public double getMaxExpectedYield() {return maxExpectedYield;}
    public void setMaxExpectedYield(double maxExpectedYield) {this.maxExpectedYield = maxExpectedYield;}

    public double getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(double expectedYield) {
        this.expectedYield = expectedYield;
    }

    public String getInterestType() {
        return interestType;
    }
    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getTimeLimit() {return timeLimit;}
    public void setTimeLimit(String timeLimit) {this.timeLimit = timeLimit;}

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

    public int getInterestCycle() {
        return interestCycle;
    }

    public void setInterestCycle(int interestCycle) {
        this.interestCycle = interestCycle;
    }

    public String getStopTimeDay() {
        return stopTimeDay;
    }

    public void setStopTimeDay(String stopTimeDay) {
        this.stopTimeDay = stopTimeDay;
    }

    public int getIncomeFactor() {
        return incomeFactor;
    }

    public void setIncomeFactor(int incomeFactor) {
        this.incomeFactor = incomeFactor;
    }

    public int getSafeFactor() {
        return safeFactor;
    }

    public void setSafeFactor(int safeFactor) {
        this.safeFactor = safeFactor;
    }

    public String getProductionDescription() {
        return productionDescription;
    }

    public void setProductionDescription(String productionDescription) {
        this.productionDescription = productionDescription;
    }
}
