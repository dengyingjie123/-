package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by jepson-pc on 2015/8/12.
 */
@Table(name = "crm_allinpayauth", jsonPrefix = "allinpayauth")
public class AllinpayauthPO extends BasePO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    //交易代码
    private int trx_code = Integer.MAX_VALUE;
    //版本
    private String version = new String();

    //数据格式
    private int data_type = Integer.MAX_VALUE;

    //处理级别
    private int level = Integer.MAX_VALUE;

    private String merchant_id = new String();
    //用户名
    private String user_name = new String();
    //用户密码
    private String user_pass = new String();
    //交易批次号
    private String req_sn = new String();
    //签名信息
    private String signed_msg = new String();

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getReq_sn() {
        return req_sn;
    }

    public void setReq_sn(String req_sn) {
        this.req_sn = req_sn;
    }

    public String getSigned_msg() {
        return signed_msg;
    }

    public void setSigned_msg(String signed_msg) {
        this.signed_msg = signed_msg;
    }
}
