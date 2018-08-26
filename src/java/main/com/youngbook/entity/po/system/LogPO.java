package com.youngbook.entity.po.system;


import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * 类型参考LogType
 */
@Table(name = "system_log", jsonPrefix = "log")
public class LogPO extends BasePO {

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


    private String name = "";
    private String peopleMessage = "";
    private String machineMessage = "";

    //请求地址
    private String url = "";

    // IP
    private String IP = new String();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeopleMessage() {
        return peopleMessage;
    }

    public void setPeopleMessage(String peopleMessage) {
        this.peopleMessage = peopleMessage;
    }

    public String getMachineMessage() {
        return machineMessage;
    }

    public void setMachineMessage(String machineMessage) {
        this.machineMessage = machineMessage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
