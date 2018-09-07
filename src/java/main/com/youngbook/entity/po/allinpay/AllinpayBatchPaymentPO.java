package com.youngbook.entity.po.allinpay;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by jepson on 2015/8/4.
 */
@Table(name = "bank_AllinpayBatchPayment", jsonPrefix = "allinpaybatchpayment")
public class AllinpayBatchPaymentPO extends BasePO {
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

    //处理级别
    private int level = Integer.MAX_VALUE;
   //用户名
    private String user_name = new String();
    //用户密码
    private String password = new String();
    //交易批次号
    private String req_sn = new String();
    //签名信息
    private String signed_msg = new String();
    //业务代码
    private String business_code = new String();
    //商户代码
    private String merchant_id = new String();
    //清算日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String settday = new String();
    //提交时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String submit_time = new String();
    //总记录数
    private int total_item = Integer.MAX_VALUE;
    //总金额
    private int total_sum = Integer.MAX_VALUE;
    //状态
    private int status = Integer.MAX_VALUE;

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getBusiness_code() {
        return business_code;
    }

    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getSettday() {
        return settday;
    }

    public void setSettday(String settday) {
        this.settday = settday;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public int getTotal_item() {
        return total_item;
    }

    public void setTotal_item(int total_item) {
        this.total_item = total_item;
    }

    public int getTotal_sum() {
        return total_sum;
    }

    public void setTotal_sum(int total_sum) {
        this.total_sum = total_sum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
