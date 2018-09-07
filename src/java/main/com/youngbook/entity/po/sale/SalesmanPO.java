package com.youngbook.entity.po.sale;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_saleman", jsonPrefix = "salesman")
public class SalesmanPO extends BasePO {
    // SID
    @Id
    private int sid = Integer.MAX_VALUE;
    // ID
    private String id = new String();
    // State
    private int state = Integer.MAX_VALUE;
    // 操作员ID
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();
    // 用户ID
    private String userId = new String();
    //销售等级K键
    private String sales_levelId = new String();

    /**
     * get和set方法
     * @return
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSales_levelId() {
        return sales_levelId;
    }

    public void setSales_levelId(String sales_levelId) {
        this.sales_levelId = sales_levelId;
    }
}
