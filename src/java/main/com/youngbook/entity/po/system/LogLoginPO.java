package com.youngbook.entity.po.system;


import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * 类型参考LogType
 */
@Table(name = "system_log_login", jsonPrefix = "log")
public class LogLoginPO extends BasePO {

    @IgnoreDB
    public static final String Name_Sms = "短信发送日志";

    private int sid = Integer.MAX_VALUE;

    @Id
    private String id = "";
    //名称
    private String Name = "";
    //登录用户状态
    private int state = Integer.MAX_VALUE;
    //操作者Id
    private String operatorId = "";
    //内容
    private String Content = "";
    //请求地址
    private String URL = "";
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = "";
    //参数
    private String Parameters = "";

    //类型，参考LogType
    private int Type = Integer.MAX_VALUE;
    // IP
    private String IP = new String();

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

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

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getParameters() {
        return Parameters;
    }

    public void setParameters(String parameters) {
        Parameters = parameters;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
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

}
