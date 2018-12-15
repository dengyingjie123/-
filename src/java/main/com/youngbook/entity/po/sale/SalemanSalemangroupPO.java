package com.youngbook.entity.po.sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2015/5/20.
 *销售小组辅助类
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */
@Table(name = "CRM_SaleMan_SaleManGroup", jsonPrefix = "saleManSaleManGroup")
public class SalemanSalemangroupPO extends BasePO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // 操作人
    private String operatorId = new String();

    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 销售员编号
    private String saleManId = new String();

    //销售小组编号
    private String saleManGroupId = new String();

    //销售身份 1:销售员；2：小组负责人
    private int saleManStatus = Integer.MAX_VALUE;

    //默认销售组  1：是  0：否
    private int defaultGroup = Integer.MAX_VALUE;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public String getSaleManGroupId() {
        return saleManGroupId;
    }

    public void setSaleManGroupId(String saleManGroupId) {
        this.saleManGroupId = saleManGroupId;
    }

    public int getSaleManStatus() {
        return saleManStatus;
    }

    public void setSaleManStatus(int saleManStatus) {
        this.saleManStatus = saleManStatus;
    }

    public int getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(int defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

    public SalemanSalemangroupPO() {
    }


}
