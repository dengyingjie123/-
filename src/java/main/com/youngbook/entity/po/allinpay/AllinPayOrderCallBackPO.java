package com.youngbook.entity.po.allinpay;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by 张舜清 on 2015/8/3.
 */
@Table(name = "bank_AllinPayOrderCallBack", jsonPrefix = "allinPayOrderCallBack")
public class AllinPayOrderCallBackPO extends BasePO {
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

    // 商户号
    private String merchantId = new String();

    // 网关返回支付结果接口版本
    private String version = new String();

    // 网页显示语言种类
    private int language = Integer.MAX_VALUE;

    // 签名类型
    private int signType = Integer.MAX_VALUE;

    // 支付方式
    private String payType = new String();

    // 发卡方机构代码
    private String issuerId = new String();

    // 通联订单号
    private String paymentOrderId = new String();

    // 商户订单号
    private String orderNo = new String();

    // 商户订单提交时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String orderDatetime = new String();

    // 商户订单金额
    private int orderAmount = Integer.MAX_VALUE;

    // 支付完成时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String payDatetime = new String();

    // 订单实际支付金额
    private int payAmount = Integer.MAX_VALUE;

    // 扩展字段1
    private String ext1 = new String();

    // 扩展字段2
    private String ext2 = new String();

    // 处理结果
    private int payResult = Integer.MAX_VALUE;

    // 错误代码
    private String errorCode = new String();

    // 结果返回时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String returnDatetime = new String();

    // 签名字符串
    private String signMsg = new String();

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(String paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(String payDatetime) {
        this.payDatetime = payDatetime;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
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

    public int getPayResult() {
        return payResult;
    }

    public void setPayResult(int payResult) {
        this.payResult = payResult;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getReturnDatetime() {
        return returnDatetime;
    }

    public void setReturnDatetime(String returnDatetime) {
        this.returnDatetime = returnDatetime;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }
}
