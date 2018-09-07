package com.youngbook.entity.po.fuiou;

import com.youngbook.entity.po.BasePO;

public class FuiouPCPayPO extends BasePO {

    // 商户代码
    private String merchantCode = new String();

    // 商户订单号
    private String orderId = new String();

    // 交易金额
    private String orderAmount = new String();

    // 支付类型
    private String orderPayType = new String();

    // 页面跳转 URL
    private String pageNotifyURL = new String();

    // 后台通知 URL
    private String backNotifyURL = new String();

    // 订单超时时间
    private String orderValidTime = new String();

    // 银行代码
    private String bankCode = new String();

    // 商品名称
    private String goodsName = new String();

    // 商品展示网址
    private String goodsDisplayURL = new String();

    // 备注
    private String remark = new String();

    // 版本号
    private String version = new String();

    // MD5Utils 签名
    private String md5 = new String();

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAmount() {return orderAmount;}

    public void setOrderAmount(String orderAmount) {this.orderAmount = orderAmount;}

    public String getOrderPayType() {
        return orderPayType;
    }

    public void setOrderPayType(String orderPayType) {
        this.orderPayType = orderPayType;
    }

    public String getPageNotifyURL() {
        return pageNotifyURL;
    }

    public void setPageNotifyURL(String pageNotifyURL) {
        this.pageNotifyURL = pageNotifyURL;
    }

    public String getBackNotifyURL() {
        return backNotifyURL;
    }

    public void setBackNotifyURL(String backNotifyURL) {
        this.backNotifyURL = backNotifyURL;
    }

    public String getOrderValidTime() {
        return orderValidTime;
    }

    public void setOrderValidTime(String orderValidTime) {
        this.orderValidTime = orderValidTime;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDisplayURL() {
        return goodsDisplayURL;
    }

    public void setGoodsDisplayURL(String goodsDisplayURL) {
        this.goodsDisplayURL = goodsDisplayURL;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
