package com.youngbook.entity.po.example;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created by ThinkPad on 5/11/2015.
 */
@Table(name = "Example_DetailTable", jsonPrefix = "detailTable")
public class DetailTablePO extends BasePO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // MainId
    private String mainId = new String();

    // B1
    private String b1 = new String();

    // B2
    private String b2 = new String();

    // B3
    private int b3 = Integer.MAX_VALUE;

    // B4
    private double b4 = Double.MAX_VALUE;

    // B5
    @DataAdapter(fieldType = FieldType.DATE)
    private String b5 = new String();

    // B6
    @DataAdapter(fieldType = FieldType.DATE)
    private String b6 = new String();

    // B7
    private String b7 = new String();

    // B8
    private int b8 = Integer.MAX_VALUE;

    @IgnoreJson
    @IgnoreDB
    @EnumType(id = "search_B8", display = "B8打开", value = "0")
    public static final int B8_Open = 0;

    @IgnoreJson
    @IgnoreDB
    @EnumType(id = "search_B8", display = "B8关闭", value = "1")
    public static final int B8_Close = 1;

    /**
     * 创建GetSet方法 字段首字母必须大写
     */

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

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getB1() {
        return b1;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }

    public String getB2() {
        return b2;
    }

    public void setB2(String b2) {
        this.b2 = b2;
    }

    public int getB3() {
        return b3;
    }

    public void setB3(int b3) {
        this.b3 = b3;
    }

    public double getB4() {
        return b4;
    }

    public void setB4(double b4) {
        this.b4 = b4;
    }

    public String getB5() {
        return b5;
    }

    public void setB5(String b5) {
        this.b5 = b5;
    }

    public String getB6() {
        return b6;
    }

    public void setB6(String b6) {
        this.b6 = b6;
    }

    public String getB7() {
        return b7;
    }

    public void setB7(String b7) {
        this.b7 = b7;
    }

    public int getB8() {
        return b8;
    }

    public void setB8(int b8) {
        this.b8 = b8;
    }
}
