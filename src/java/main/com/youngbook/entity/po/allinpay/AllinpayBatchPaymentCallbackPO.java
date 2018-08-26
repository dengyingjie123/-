package com.youngbook.entity.po.allinpay;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by jepson on 2015/8/4.
 */
@Table(name = "bank_AllinpayBatchPaymentCallback", jsonPrefix = "allinpaybatchpaymentcallback")
public class AllinpayBatchPaymentCallbackPO extends BasePO {
    @Id
    private int sid = Integer.MAX_VALUE;

    private String id = new String();

    private int state = Integer.MAX_VALUE;

    private String operatorId = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    //交易代码
    private int trx_code = Integer.MAX_VALUE;
    //版本
    private String version = new String();

    //数据格式
    private int data_type = Integer.MAX_VALUE;

    //交易批次号
    private String req_sn = new String();

    //返回代码
    private String ret_code=new String();

    //错误信息
    private String err_msg=new String();

    //签名信息
    private String signed_msg=new String();

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

    public int getTrx_code() {
        return trx_code;
    }

    public void setTrx_code(int trx_code) {
        this.trx_code = trx_code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    public String getReq_sn() {
        return req_sn;
    }

    public void setReq_sn(String req_sn) {
        this.req_sn = req_sn;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getSigned_msg() {
        return signed_msg;
    }

    public void setSigned_msg(String signed_msg) {
        this.signed_msg = signed_msg;
    }
}
