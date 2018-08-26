package com.youngbook.entity.po.allinpay;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

@Table(name = "bank_AllinPayTransfer", jsonPrefix = "allinPayTransfer")
public class AllinPayOrderPO extends BasePO {
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
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 字符集
    private int inputCharset = Integer.MAX_VALUE;

    // 付款客户的取货url地址
    private String pickupUrl = new String();

    // 服务器接受支付结果的后台
    private String receiveUrl = new String();

    // 网关接收支付，请求接口版本，固定为v1.0
    private String version = "v1.0";

    // 网关页面显示语言种类
    private int language = Integer.MAX_VALUE;

    // 签名类型
    private int signType = Integer.MAX_VALUE;

    // 商户号
    private String merchantId = new String();

    // 付款人姓名
    private String payerName = new String();

    // 付款人邮件联系方式
    private String payerEmail = new String();

    // 付款人电话联系方式
    private String payerTelephone = new String();

    // 付款人类型及证件号
    private String payerIDCard = new String();

    // 合作伙伴的商户号
    private String pid = new String();

    // 商户订单号
    private String orderNo = new String();

    // 商户订单余额
    private int orderAmount = Integer.MAX_VALUE;

    // 订单余额币种类型
    private int orderCurrency = Integer.MAX_VALUE;

    // 商户订单提交时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String orderDatetime = new String();

    // 订单过期时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String orderExpireDatetime = new String();

    // 商品名称
    private String productName = new String();

    // 商品价格
    private int productPrice = Integer.MAX_VALUE;

    // 商品数量
    private int productNum = Integer.MAX_VALUE;

    // 商品代码
    private int productId = Integer.MAX_VALUE;

    // 商品描述
    private String productDesc = new String();

    // 扩展字段1
    private String ext1 = new String();

    // 扩展字段2
    private String ext2 = new String();

    // 业务扩展字段
    private String extTL = new String();

    // 支付方式
    private int payType = Integer.MAX_VALUE;

    // 发卡方代码
    private int issuerId = Integer.MAX_VALUE;

    // 付款人支付卡号
    private String pan = new String();

    // 贸易类型
    private String tradeNature = new String();

    // 签名字符串
    private String signMsg = new String();

    // 海关扩展字段
    private String customsExt = new String();

    // 业务ID
    private String bizId = new String();

    // 业务类型
    private String bizType = new String();

    // 交易状态
    private int tradingStatus = Integer.MAX_VALUE;

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

    public int getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(int inputCharset) {
        this.inputCharset = inputCharset;
    }

    public String getPickupUrl() {
        return pickupUrl;
    }

    public void setPickupUrl(String pickupUrl) {
        this.pickupUrl = pickupUrl;
    }

    public String getReceiveUrl() {
        return receiveUrl;
    }

    public void setReceiveUrl(String receiveUrl) {
        this.receiveUrl = receiveUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getPayerTelephone() {
        return payerTelephone;
    }

    public void setPayerTelephone(String payerTelephone) {
        this.payerTelephone = payerTelephone;
    }

    public String getPayerIDCard() {
        return payerIDCard;
    }

    public void setPayerIDCard(String payerIDCard) {
        this.payerIDCard = payerIDCard;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(int orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getOrderExpireDatetime() {
        return orderExpireDatetime;
    }

    public void setOrderExpireDatetime(String orderExpireDatetime) {
        this.orderExpireDatetime = orderExpireDatetime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExtTL() {
        return extTL;
    }

    public void setExtTL(String extTL) {
        this.extTL = extTL;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(int issuerId) {
        this.issuerId = issuerId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTradeNature() {
        return tradeNature;
    }

    public void setTradeNature(String tradeNature) {
        this.tradeNature = tradeNature;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    public String getCustomsExt() {
        return customsExt;
    }

    public void setCustomsExt(String customsExt) {
        this.customsExt = customsExt;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public int getTradingStatus() {
        return tradingStatus;
    }

    public void setTradingStatus(int tradingStatus) {
        this.tradingStatus = tradingStatus;
    }
}
