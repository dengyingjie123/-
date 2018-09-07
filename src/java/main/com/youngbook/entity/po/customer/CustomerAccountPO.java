package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/18/14
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_customeraccount", jsonPrefix = "customerAccount")
public class CustomerAccountPO extends BasePO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // 客户编号
    private String customerId = new String();

    // 开户行
    private String bank = new String();

    // 账户名称
    private String name = new String();

    //账号
    private String number = new String();

    // 开户行地址
    private String bankBranchName = new String();

    // 银行代码
    private String bankCode = new String();


    private String cityCode = "";

    // 操作人
    private String operatorId = new String();

    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 客户类型
    private int customerType = Integer.MAX_VALUE;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }



    @Override
    public String toString() {
        return "CustomerAccountPO{" +
                "sid=" + sid +
                ", id='" + id + '\'' +
                ", state=" + state +
                ", customerId='" + customerId + '\'' +
                ", bank='" + bank + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", bankBranchName='" + bankBranchName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", customerType=" + customerType +
                '}';
    }
}
