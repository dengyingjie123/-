package com.youngbook.entity.po;


import com.youngbook.annotation.*;
import net.sf.json.JSONObject;

/**
 * @description 节假日日期
 *
 * @author 苟熙霖
 */
@Table(name = "system_holiday", jsonPrefix = "holiday")
public class HolidayPO extends BasePO {
    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;

    private int state = Integer.MAX_VALUE;
    // 操作ID
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 编号
    private String ID = new String();

    // 节假日日期
    private String holiday = new String();

    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("holiday", holiday);
        return json;
    }


    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }


    @Override
    public String toString() {
        return "holidayPO{" +
                "sid=" + sid +
                ", state=" + state +
                ", operatorId='" + operatorId + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", ID='" + ID + '\'' +
                ", holiday='" + holiday + '\'' +
                '}';
    }
}
