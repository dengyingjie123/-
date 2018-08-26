package com.youngbook.entity.vo.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

@Table(name = "crm_productionmarketinglog", jsonPrefix = "productionmarketinglog")
public class ProductionMarketingLogVO extends BaseVO {

    @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();

    // State
    private int state = Integer.MAX_VALUE;

    // OperatorId
    private String operatorId = new String();

    // OperateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 产品编号
    private String productionId = new String();

    // 对外销售人员编号
    private String salemanOuterId = new String();

    // 产品总分享次数统计
    private String productionTotalMarketing = new String();

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

    public String getSalemanOuterId() {
        return salemanOuterId;
    }

    public void setSalemanOuterId(String salemanOuterId) {
        this.salemanOuterId = salemanOuterId;
    }

    public String getProductionTotalMarketing() {
        return productionTotalMarketing;
    }

    public void setProductionTotalMarketing(String productionTotalMarketing) {
        this.productionTotalMarketing = productionTotalMarketing;
    }
}
