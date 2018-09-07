package com.youngbook.entity.po.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by jepson-pc on 2015/8/12.
 */
@Table(name = "crm_allinpayauthdetail", jsonPrefix = "allinpayauthdetail")
public class AllinpayauthdetailPO extends BasePO{
    @Id
    private int sid = Integer.MAX_VALUE;

    private String id = new String();

    private int state = Integer.MAX_VALUE;

    private String operatorId = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    private String name = new String();

    private String IDNO = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String validate = new String();

    private String REMARK = new String();

    private String ParentId = new String();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIDNO() {
        return IDNO;
    }

    public void setIDNO(String IDNO) {
        this.IDNO = IDNO;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }
}
