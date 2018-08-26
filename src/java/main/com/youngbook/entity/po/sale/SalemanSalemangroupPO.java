package com.youngbook.entity.po.sale;

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
    @Id
    private String id =new String();
    // 销售员编号
    private String saleManId = new String();
    //销售小组编号
    private String saleManGroupId = new String();
    //销售身份 1:销售员；2：小组负责人
    private int saleManStatus = Integer.MAX_VALUE;
    //默认销售组  1：是  0：否
    private int defaultGroup = Integer.MAX_VALUE;

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
