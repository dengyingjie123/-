package com.youngbook.entity.po.allinpaycircle;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created by leevits on 9/1/2018.
 */
@Table(name = "allinpaycircle_receive_raw_data", jsonPrefix = "allinpayCircleReceiveRawDataPO")
public class AllinpayCircleReceiveRawDataPO extends BasePO {

    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;


    private String id = "";
    //登录用户状态
    private int state = Integer.MAX_VALUE;
    //操作者Id
    private String operatorId = "";


    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = "";

    private String message = "";

    /**
     * 数据状态
     * 未处理：0
     * 已处理：1
     */
    private String status = "";

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
