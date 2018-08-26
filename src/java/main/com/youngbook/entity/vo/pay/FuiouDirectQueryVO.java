package com.youngbook.entity.vo.pay;

import com.youngbook.entity.vo.BaseVO;

/**
 * 富友企业直联交易查询实体
 *
 * 作者：邓超
 * 内容：创建代码
 * 时间：2016年7月5日
 */
public class FuiouDirectQueryVO extends BaseVO {

    // 原请求时间
    String requestDate = new String();

    // 原流水号
    String bizId = new String();

    // 交易银行卡号
    String bankNumber = new String();

    // 交易人姓名
    String customerName = new String();

    // 交易金额，单位：分
    String money = new String();

    // 企业流水号（不常用）
    String sequence = new String();

    // 交易备注
    String content = new String();

    // 富友端交易状态
    String state = new String();

    // 交易结果
    String result = new String();

    // 结果原因
    String reson = new String();

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

}
