package com.youngbook.entity.callcenter;

import com.youngbook.annotation.Id;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-21
 * Time: 上午8:40
 * To change this template use File | Settings | File Templates.
 */
public class CallCenterIncomingCallRegisterPO extends BasePO {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getOptid() {
        return optid;
    }

    public void setOptid(String optid) {
        this.optid = optid;
    }

    public String getOptname() {
        return optname;
    }

    public void setOptname(String optname) {
        this.optname = optname;
    }

    public String getIncomingcalls_number() {
        return incomingcalls_number;
    }

    public void setIncomingcalls_number(String incomingcalls_number) {
        this.incomingcalls_number = incomingcalls_number;
    }

    public String getIncomingcalls_datetime() {
        return incomingcalls_datetime;
    }

    public void setIncomingcalls_datetime(String incomingcalls_datetime) {
        this.incomingcalls_datetime = incomingcalls_datetime;
    }

    public String getIncomingcalls_category() {
        return incomingcalls_category;
    }

    public void setIncomingcalls_category(String incomingcalls_category) {
        this.incomingcalls_category = incomingcalls_category;
    }

    @Id
    private String id = "";

    private int sid = Integer.MAX_VALUE;

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    private String customerid = "";

    private String optid = "";

    private String optname = "";

    private String incomingcalls_number = "";

    private String incomingcalls_datetime = "";

    private String incomingcalls_category = "";

}
