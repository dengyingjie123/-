package com.youngbook.entity.vo.bank;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by 邓超
 * Date 2015-6-3
 */
@Table(name = "bank_MerchantCode", jsonPrefix = "merchantCode")
public class MerchantCodeVO extends BaseVO {

    // Sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();

    // State
    private int state = Integer.MAX_VALUE;

    // OperatorId
    private String operatorId = new String();

    // OperateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 商户密钥
    private String bankKey = new String();

    // 订单日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String bankDate = new String();

    // 商户所在分行
    private String bankBranchId = new String();

    // 商户号
    private String bankCono = new String();

    // 订单号
    private String bankBillNo = new String();

    // 订单金额
    private double bankAmount = Double.MAX_VALUE;

    // 商户参数
    private String bankMerchantPara = new String();

    // 通知地址
    private String bankMerchantUrl = new String();

    // 付款方
    private String bankPayerId = new String();

    // 收款方
    private String bankPayeeId = new String();

    // 商户取得的客户端IP
    private String bankClientIP = new String();

    // 商品类型
    private String bankGoodsType = new String();

    // 保留字段
    private String bankReserved = new String();

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

    public String getBankKey() {
        return bankKey;
    }

    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    public String getBankDate() {
        return bankDate;
    }

    public void setBankDate(String bankDate) {
        this.bankDate = bankDate;
    }

    public String getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(String bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public String getBankCono() {
        return bankCono;
    }

    public void setBankCono(String bankCono) {
        this.bankCono = bankCono;
    }

    public String getBankBillNo() {
        return bankBillNo;
    }

    public void setBankBillNo(String bankBillNo) {
        this.bankBillNo = bankBillNo;
    }

    public double getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(double bankAmount) {
        this.bankAmount = bankAmount;
    }

    public String getBankMerchantPara() {
        return bankMerchantPara;
    }

    public void setBankMerchantPara(String bankMerchantPara) {
        this.bankMerchantPara = bankMerchantPara;
    }

    public String getBankMerchantUrl() {
        return bankMerchantUrl;
    }

    public void setBankMerchantUrl(String bankMerchantUrl) {
        this.bankMerchantUrl = bankMerchantUrl;
    }

    public String getBankPayerId() {
        return bankPayerId;
    }

    public void setBankPayerId(String bankPayerId) {
        this.bankPayerId = bankPayerId;
    }

    public String getBankPayeeId() {
        return bankPayeeId;
    }

    public void setBankPayeeId(String bankPayeeId) {
        this.bankPayeeId = bankPayeeId;
    }

    public String getBankClientIP() {
        return bankClientIP;
    }

    public void setBankClientIP(String bankClientIP) {
        this.bankClientIP = bankClientIP;
    }

    public String getBankGoodsType() {
        return bankGoodsType;
    }

    public void setBankGoodsType(String bankGoodsType) {
        this.bankGoodsType = bankGoodsType;
    }

    public String getBankReserved() {
        return bankReserved;
    }

    public void setBankReserved(String bankReserved) {
        this.bankReserved = bankReserved;
    }
}