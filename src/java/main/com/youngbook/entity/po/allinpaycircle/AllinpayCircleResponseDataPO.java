package com.youngbook.entity.po.allinpaycircle;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created by leevits on 9/1/2018.
 */
@Table(name = "allinpaycircle_response_data", jsonPrefix = "allinpayCircleResponseDataPO")
public class AllinpayCircleResponseDataPO extends BasePO {

    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;


    private String id = "";
    //登录用户状态
    private int state = Integer.MAX_VALUE;
    //操作者Id
    private String operatorId = "";


    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = "";

    private String processing_code = "";

    @DataAdapter(fieldType = FieldType.DATE)
    private String trans_time = "";

    private String req_trace_num = "";
    private String resp_trace_num = "";
    private String resp_code = "";
    private String resp_msg = "";
    private String xml = "";

    /**
     * 数据状态
     * 未处理：0
     * 已处理：1
     */
    private String status = "";

    public String getProcessing_code() {
        return processing_code;
    }

    public void setProcessing_code(String processing_code) {
        this.processing_code = processing_code;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getReq_trace_num() {
        return req_trace_num;
    }

    public void setReq_trace_num(String req_trace_num) {
        this.req_trace_num = req_trace_num;
    }

    public String getResp_trace_num() {
        return resp_trace_num;
    }

    public void setResp_trace_num(String resp_trace_num) {
        this.resp_trace_num = resp_trace_num;
    }

    public String getResp_code() {
        return resp_code;
    }

    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }

    public String getResp_msg() {
        return resp_msg;
    }

    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
