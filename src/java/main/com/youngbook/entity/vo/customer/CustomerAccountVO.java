package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/23/14
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_customeraccount_vo", jsonPrefix = "customerAccountVO")
public class CustomerAccountVO extends BaseVO{
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // 客户编号
    private String customerId = new String();

    // 开户行
    private String bank = new String();

    // 账户名称
    private String name = new String();

    //账号
    private String number = new String();

    private String numberWithoutMask = new String();

    private String number4Short = new String();

    // 开户行地址
    private String bankBranchName = new String();

    // 银行代码
    private String bankCode = new String();


    public String getNumberWithoutMask() {
        return numberWithoutMask;
    }

    public void setNumberWithoutMask(String numberWithoutMask) {
        this.numberWithoutMask = numberWithoutMask;
    }

    public String getNumber4Short() {
        return number4Short;
    }

    public void setNumber4Short(String number4Short) {
        this.number4Short = number4Short;
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
}
