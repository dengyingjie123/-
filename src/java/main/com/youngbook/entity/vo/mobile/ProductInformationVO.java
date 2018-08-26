package com.youngbook.entity.vo.mobile;

import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-29
 * Time: 下午10:46
 * To change this template use File | Settings | File Templates.
 */
public class ProductInformationVO extends BaseVO {
    //产品SID
    private int sid=Integer.MAX_VALUE;
    //产品ID
    private String id = new String();
    //产品名称
    private String name=new String();
    //产品规模
    private double size=Double.MAX_VALUE;
    //产品期限
    private int timeLimit =Integer.MAX_VALUE;
    //产品最低收益
    private double lowestYield=Double.MAX_VALUE;
    //产品最高收益
    private double highestYield=Double.MAX_VALUE;
    //进度
    private double progress =Double.MAX_VALUE;

    public double getHighestYield() {
        return highestYield;
    }

    public void setHighestYield(double highestYield) {
        this.highestYield = highestYield;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLowestYield() {
        return lowestYield;
    }

    public void setLowestYield(double lowestYield) {
        this.lowestYield = lowestYield;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
