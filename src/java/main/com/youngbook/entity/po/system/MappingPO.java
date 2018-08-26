package com.youngbook.entity.po.system;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created by leevits on 7/22/2018.
 */
@Table(name = "system_mapping", jsonPrefix = "mapping")
public class MappingPO extends BasePO {
    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;


    private String id = "";
    //登录用户状态
    private int state = Integer.MAX_VALUE;
    //操作者Id
    private String operatorId = "";
    private String operatorName = "";


    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = "";

    /**
     * 参考 KV groupName: SystemMappingType
     */
    private String type = "";
    private String bizId01 = "";
    private String bizId02 = "";
    private String comment = "";

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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBizId01() {
        return bizId01;
    }

    public void setBizId01(String bizId01) {
        this.bizId01 = bizId01;
    }

    public String getBizId02() {
        return bizId02;
    }

    public void setBizId02(String bizId02) {
        this.bizId02 = bizId02;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
