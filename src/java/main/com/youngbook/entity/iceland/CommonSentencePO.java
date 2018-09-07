package com.youngbook.entity.iceland;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created by leevits on 3/7/2017.
 */
@Table(name = "iceland_commonsentence", jsonPrefix = "commonSentencePO")
public class CommonSentencePO extends BasePO {

    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;
    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;
    // 操作人
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();


    private String salesmanId = "";

    private String sentence = "";

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

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
