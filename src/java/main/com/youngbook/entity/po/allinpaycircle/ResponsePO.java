package com.youngbook.entity.po.allinpaycircle;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.entity.po.BasePO;


public class ResponsePO extends BasePO {

    @DataAdapter(fieldType = FieldType.XML, fieldResource = "/transaction/response")
    private String req_trace_num = "";

    @DataAdapter(fieldType = FieldType.XML, fieldResource = "/transaction/response")
    private String sub_merchant_id = "";

    public String getReq_trace_num() {
        return req_trace_num;
    }

    public void setReq_trace_num(String req_trace_num) {
        this.req_trace_num = req_trace_num;
    }

    public String getSub_merchant_id() {
        return sub_merchant_id;
    }

    public void setSub_merchant_id(String sub_merchant_id) {
        this.sub_merchant_id = sub_merchant_id;
    }
}
