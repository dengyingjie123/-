package com.youngbook.entity.po.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;
/**
 * Created by jepson-pc on 2015/8/17.
 */
@Table(name = "crm_ProductionHome", jsonPrefix = "productionHome")
public class ProductionHomePO extends BasePO {

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

    // 产品名称
    private String productionName = new String();

    // 产品描述
    private String description = new String();

    // 项目ID
    private String projectId = new String();

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

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
