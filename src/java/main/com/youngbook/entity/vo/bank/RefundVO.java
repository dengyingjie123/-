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
@Table(name = "bank_Refund", jsonPrefix = "refund")
public class RefundVO extends BaseVO {

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

    // 订单日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String bankDate = new String();

    // 订单号
    private String bankBillNo = new String();

    // 退款金额
    private double bankAmt = Double.MAX_VALUE;

    // 退款说明
    private String bankDesc = new String();

    // 商户密钥
    private String bankKey = new String();

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

    public String getBankDate() {
        return bankDate;
    }

    public void setBankDate(String bankDate) {
        this.bankDate = bankDate;
    }

    public String getBankBillNo() {
        return bankBillNo;
    }

    public void setBankBillNo(String bankBillNo) {
        this.bankBillNo = bankBillNo;
    }

    public double getBankAmt() {
        return bankAmt;
    }

    public void setBankAmt(double bankAmt) {
        this.bankAmt = bankAmt;
    }

    public String getBankDesc() {
        return bankDesc;
    }

    public void setBankDesc(String bankDesc) {
        this.bankDesc = bankDesc;
    }

    public String getBankKey() {
        return bankKey;
    }

    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }
}
