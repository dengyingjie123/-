package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.vo.BaseVO;

import java.util.List;

@Table(name = "crm_customerpersonal", jsonPrefix = "personalVO")
public class CustomerPersonalVO  extends BaseVO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // 姓名 : 支持查询,必填
    private String name = new String();

    // 客户号
    private String personalNumber = new String();

    // 登录名
    private String loginName = new String();

    // 性别 : 下拉菜单,必填
    private String sex =  new String();

    @IgnoreDB
    private List<CustomerAccountVO> accountVOs = null;

    // 出生日期 : 日期类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String birthday = new String();

    // 移动电话 : 支持查询,必填
    private String mobile = new String();
    private String mobileNotMasked = new String();

    // 固定电话
    private String phone = new String();


    // 电子邮箱
    private String email = new String();

    // 工作地址 : 支持查询,必填
    private String workAddress = new String();

    //家庭地址
    private String homeAddress = new String();

    //身份证地址
    private String identityCardAddress = new String();

    //备注
    private String remark = new String();

    // 冻结资本
    private double frozenMoney = Double.MAX_VALUE;

    // 可用资本
    private double availableMoney = Double.MAX_VALUE;

    // 创建时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();

    // 销售组名称
    private String groupName = "";

    private String saleManId = "";

    private String saleManName = "";

    private String idCardNumber = "";

    private int distributionStatus = Integer.MAX_VALUE;

    // 交易密码
    private String transactionPassword = new String();

    // 产品名称
    private String productionName = new String();

    // 客户类型
    private String customerTypeId = new String();
    private String customerTypeName = new String();

    private String customerCatalogId = new String();
    private String customerChannelTypeId = new String();


    // 申请额度待确认的订单数量统计
    private int preAppointmentCount = Integer.MAX_VALUE;

    // 预约的订单数量统计
    private int appointmentOrderCount = Integer.MAX_VALUE;

    // 已支付的订单数量统计
    private int soldOrderCount = Integer.MAX_VALUE;

    // 已结束订单
    private int closedOrderCount = Integer.MAX_VALUE;

    // 即将到期的产品订单数量统计
    private int preExpireCount = Integer.MAX_VALUE;

    // 订单在投金额统计
    private double totalPaymentPrincipalMoney = Double.MAX_VALUE;
    private String totalPaymentPrincipalMoneyWithFormat = "";

    // 总收益
    private double totalProfitMoney = Double.MAX_VALUE;
    private String totalProfitMoneyWithFormat = "";

    // 订单即将到期金额统计
    private double totalPreExpireMoney = Double.MAX_VALUE;

    // 订单已返佣的统计
    private int rebateCount = Integer.MAX_VALUE;

    //crm_customer_outer的id
    private String temporaryCustomerId = new String();

    // 推荐码
    private String referralCode = new String();

    // 确认合格投资者
    private String confirmInvestor = new String();

    // 关联系统里crm_CustomerPersonal-ID
    private String linkCustomerId = new String();


    /**
     * 富滇存管部分
     */
    private String fdcgCustomerId = "";
    private String fdcgAccountNo = "";
    private String fdcgUserName = "";


    public String getFdcgCustomerId() {
        return fdcgCustomerId;
    }

    public void setFdcgCustomerId(String fdcgCustomerId) {
        this.fdcgCustomerId = fdcgCustomerId;
    }

    public String getFdcgAccountNo() {
        return fdcgAccountNo;
    }

    public void setFdcgAccountNo(String fdcgAccountNo) {
        this.fdcgAccountNo = fdcgAccountNo;
    }

    public String getFdcgUserName() {
        return fdcgUserName;
    }

    public void setFdcgUserName(String fdcgUserName) {
        this.fdcgUserName = fdcgUserName;
    }

    public String getCustomerChannelTypeId() {
        return customerChannelTypeId;
    }

    public void setCustomerChannelTypeId(String customerChannelTypeId) {
        this.customerChannelTypeId = customerChannelTypeId;
    }

    public String getLinkCustomerId() {
        return linkCustomerId;
    }

    public void setLinkCustomerId(String linkCustomerId) {
        this.linkCustomerId = linkCustomerId;
    }

    public String getTotalProfitMoneyWithFormat() {
        return totalProfitMoneyWithFormat;
    }

    public void setTotalProfitMoneyWithFormat(String totalProfitMoneyWithFormat) {
        this.totalProfitMoneyWithFormat = totalProfitMoneyWithFormat;
    }

    public List<CustomerAccountVO> getAccountVOs() {
        return accountVOs;
    }

    public void setAccountVOs(List<CustomerAccountVO> accountVOs) {
        this.accountVOs = accountVOs;
    }


    public String getTotalPaymentPrincipalMoneyWithFormat() {
        return totalPaymentPrincipalMoneyWithFormat;
    }

    public void setTotalPaymentPrincipalMoneyWithFormat(String totalPaymentPrincipalMoneyWithFormat) {
        this.totalPaymentPrincipalMoneyWithFormat = totalPaymentPrincipalMoneyWithFormat;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public String getMobileNotMasked() {
        return mobileNotMasked;
    }

    public void setMobileNotMasked(String mobileNotMasked) {
        this.mobileNotMasked = mobileNotMasked;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getIdentityCardAddress() {
        return identityCardAddress;
    }

    public void setIdentityCardAddress(String identityCardAddress) {
        this.identityCardAddress = identityCardAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(double frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public double getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(double availableMoney) {
        this.availableMoney = availableMoney;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public String getSaleManName() {
        return saleManName;
    }

    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
    }

    public int getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(int distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public String getTransactionPassword() {
        return transactionPassword;
    }

    public void setTransactionPassword(String transactionPassword) {
        this.transactionPassword = transactionPassword;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(String customerTypeId) {
        this.customerTypeId = customerTypeId;
    }


    public int getPreAppointmentCount() {
        return preAppointmentCount;
    }

    public void setPreAppointmentCount(int preAppointmentCount) {
        this.preAppointmentCount = preAppointmentCount;
    }

    public int getAppointmentOrderCount() {
        return appointmentOrderCount;
    }

    public void setAppointmentOrderCount(int appointmentOrderCount) {
        this.appointmentOrderCount = appointmentOrderCount;
    }

    public int getSoldOrderCount() {
        return soldOrderCount;
    }

    public void setSoldOrderCount(int soldOrderCount) {
        this.soldOrderCount = soldOrderCount;
    }

    public int getClosedOrderCount() {
        return closedOrderCount;
    }

    public void setClosedOrderCount(int closedOrderCount) {
        this.closedOrderCount = closedOrderCount;
    }

    public int getPreExpireCount() {
        return preExpireCount;
    }

    public void setPreExpireCount(int preExpireCount) {
        this.preExpireCount = preExpireCount;
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

    public double getTotalPreExpireMoney() {
        return totalPreExpireMoney;
    }

    public void setTotalPreExpireMoney(double totalPreExpireMoney) {
        this.totalPreExpireMoney = totalPreExpireMoney;
    }

    public int getRebateCount() {
        return rebateCount;
    }

    public void setRebateCount(int rebateCount) {
        this.rebateCount = rebateCount;
    }

    public String getTemporaryCustomerId() {
        return temporaryCustomerId;
    }

    public void setTemporaryCustomerId(String temporaryCustomerId) {
        this.temporaryCustomerId = temporaryCustomerId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getConfirmInvestor() {
        return confirmInvestor;
    }

    public void setConfirmInvestor(String confirmInvestor) {
        this.confirmInvestor = confirmInvestor;
    }

    public String getCustomerCatalogId() {
        return customerCatalogId;
    }

    public void setCustomerCatalogId(String customerCatalogId) {
        this.customerCatalogId = customerCatalogId;
    }
}
