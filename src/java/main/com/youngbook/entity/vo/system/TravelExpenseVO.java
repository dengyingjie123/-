package com.youngbook.entity.vo.system;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-25
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
@Table(name = " ", jsonPrefix = "detail")
public class TravelExpenseVO extends BaseVO {

    //序号
    private int sid = Integer.MAX_VALUE;

    //编号
    private String id = new  String();

    // 状态
    private int state = Integer.MAX_VALUE;

    //申请部门
    private String sqbm = new String();

    //预算部门
    private  String ysbm = new String();

    //借款人
    private  String  jkr = new String();

    //出差事由
    private  String  ccsy = new String();





    //合计
    private  double  hj = Double.MAX_VALUE;


    //起始时间
    @DataAdapter(fieldType = FieldType.DATE)
    private  String qssj = new String();

    //起始地点
    private String qsdd = new String();

    //结束时间
    @DataAdapter(fieldType = FieldType.DATE)
    private  String jssj = new String();

    //结束地点
    private  String jsdd = new String();

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

    public String getSqbm() {
        return sqbm;
    }

    public void setSqbm(String sqbm) {
        this.sqbm = sqbm;
    }

    public String getYsbm() {
        return ysbm;
    }

    public void setYsbm(String ysbm) {
        this.ysbm = ysbm;
    }

    public String getJkr() {
        return jkr;
    }

    public void setJkr(String jkr) {
        this.jkr = jkr;
    }

    public String getQssj() {
        return qssj;
    }

    public void setQssj(String qssj) {
        this.qssj = qssj;
    }

    public String getQsdd() {
        return qsdd;
    }

    public void setQsdd(String qsdd) {
        this.qsdd = qsdd;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public String getJsdd() {
        return jsdd;
    }

    public void setJsdd(String jsdd) {
        this.jsdd = jsdd;
    }

    public double getHj() {
        return hj;
    }

    public void setHj(double hj) {
        this.hj = hj;
    }

    public String getCcsy() {
        return ccsy;
    }

    public void setCcsy(String ccsy) {
        this.ccsy = ccsy;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
