package com.youngbook.entity.vo.info;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

import java.sql.Date;

/**
 * Created by Administrator on 2015/4/1.
 */
@Table(name="info_LegalAgreement", jsonPrefix="legalAgreementVO")
public class LegalAgreementVO extends BaseVO {

    @Id
    private int sid = Integer.MAX_VALUE;

    private String id = new String();

    private String name = new String();

    private String content = new String();

    private String operatorId = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String endTime = new String();

    // 当前登录的用户名
    private String username = new String();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
