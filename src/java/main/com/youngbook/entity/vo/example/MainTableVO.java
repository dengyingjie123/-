package com.youngbook.entity.vo.example;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.common.Database;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015/5/11.
 *创建一个MainTableVO类 继承BaseVO类
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

@Table(name = "Example_MainTable", jsonPrefix = "mainTableVO")
public class MainTableVO extends BaseVO {
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

    // A1
    private String a1 = new String();

    // A2
    private String a2 = new String();

    // A3
    private int a3 = Integer.MAX_VALUE;

    // A4
    private double a4 = Double.MAX_VALUE;

    // A5
    @DataAdapter(fieldType = FieldType.DATE)
    private String a5 = new String();

    // A6
    @DataAdapter(fieldType = FieldType.DATE,fieldFormat = Database.DATEFORMAT_YYYYMMDD)
    private String a6 = new String();

    // A7
    private String a7 = new String();

    // A8
    private int a8 = Integer.MAX_VALUE;

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

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public int getA3() {
        return a3;
    }

    public void setA3(int a3) {
        this.a3 = a3;
    }

    public double getA4() {
        return a4;
    }

    public void setA4(double a4) {
        this.a4 = a4;
    }

    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    public String getA6() {
        return a6;
    }

    public void setA6(String a6) {
        this.a6 = a6;
    }

    public String getA7() {
        return a7;
    }

    public void setA7(String a7) {
        this.a7 = a7;
    }

    public int getA8() {
        return a8;
    }

    public void setA8(int a8) {
        this.a8 = a8;
    }


    /**
     * 创建一个人空参数 构造方法
     */
    public MainTableVO() {
    }
}
