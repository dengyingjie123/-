package com.youngbook.entity.po.sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2015/5/22.
 * 产品分配实体类
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */
@Table(name = "sale_SaleTask4Group", jsonPrefix = "saleTask4Group")
public class SaleTask4GroupPO extends BasePO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;// id
    private String id = new String();// state
    private int state = Integer.MAX_VALUE;// operatorId
    private String operatorId = new String();// operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();// 产品编号
    private String productionId = new String();// 销售组编号
    private String saleGroupId = new String();// 分配金额
    private double taskMoney = Double.MAX_VALUE;// 开始时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();// 结束时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String endTime = new String();// 待售金额
    private double waitingMoney = Double.MAX_VALUE;// 预约金额
    private double appointmengMoney = Double.MAX_VALUE;// 打款金额
    private double soldMoney = Double.MAX_VALUE;// 累计取消金额
    private double totoalCancelMoney = Double.MAX_VALUE;

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

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getSaleGroupId() {
        return saleGroupId;
    }

    public void setSaleGroupId(String saleGroupId) {
        this.saleGroupId = saleGroupId;
    }

    public double getTaskMoney() {
        return taskMoney;
    }

    public void setTaskMoney(double taskMoney) {
        this.taskMoney = taskMoney;
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

    public double getWaitingMoney() {
        return waitingMoney;
    }

    public void setWaitingMoney(double waitingMoney) {
        this.waitingMoney = waitingMoney;
    }

    public double getAppointmengMoney() {
        return appointmengMoney;
    }

    public void setAppointmengMoney(double appointmengMoney) {
        this.appointmengMoney = appointmengMoney;
    }

    public double getSoldMoney() {
        return soldMoney;
    }

    public void setSoldMoney(double soldMoney) {
        this.soldMoney = soldMoney;
    }

    public double getTotoalCancelMoney() {
        return totoalCancelMoney;
    }

    public void setTotoalCancelMoney(double totoalCancelMoney) {
        this.totoalCancelMoney = totoalCancelMoney;
    }

    public SaleTask4GroupPO() {
    }
}
