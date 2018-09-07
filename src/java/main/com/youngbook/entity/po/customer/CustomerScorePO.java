package com.youngbook.entity.po.customer;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 14-10-9
 * Time: 上午12:58
 *
 *
 */
@Table(name = "crm_customerScore", jsonPrefix = "customerScore")
public class CustomerScorePO extends BasePO {

    // sid
    @Id(type=IdType.LONG)
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

    // 姓名 : 支持查询,必填
    private String customerId = new String();


    /**
     * 对于使用的积分，在数据库里保存为负数
     */
    private int score = Integer.MAX_VALUE;


    private String bizId = "";
    private String comment = "";


    /**
     * 0: 增加积分
     * 1：消费积分
     */
    private int type = Integer.MAX_VALUE;


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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
