package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.IdType;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by leevits on 6/23/2018.
 */
public class CustomerScoreVO extends BaseVO {

    // sid
    @Id(type= IdType.LONG)
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

    private int totalScore = Integer.MAX_VALUE;

    private int availableScore = Integer.MAX_VALUE;

    private int usedScore = Integer.MAX_VALUE;

    private int scoreLevel = Integer.MAX_VALUE;

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getScoreLevel() {
        return scoreLevel;
    }

    public void setScoreLevel(int scoreLevel) {
        this.scoreLevel = scoreLevel;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getAvailableScore() {
        return availableScore;
    }

    public void setAvailableScore(int availableScore) {
        this.availableScore = availableScore;
    }

    public int getUsedScore() {
        return usedScore;
    }

    public void setUsedScore(int usedScore) {
        this.usedScore = usedScore;
    }
}
