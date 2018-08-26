package com.youngbook.entity.vo.oa.finance;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;
import net.sf.json.JSONObject;


@Table(name = "finance_moneyLog_vo", jsonPrefix = "moneyLogVO")
public class MoneyLogVO extends BaseVO {

    // 序号
    @Id
    private int sid = Integer.MAX_VALUE;

    // 编号
    private String id = new String();

    private String departmentId = new String();
    private String departmentName = new String();

    private String inOrOutName = new String();

    // 类型名称 : 必填,下拉菜单
    private String typeName = new String();

    // 名称 : 必填
    private String name = new String();

    // 金额 : 必填
    private double money = Double.MAX_VALUE;

    // 备注
    private String comment = new String();

    // 账务时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String moneyTime = new String();

    // 操作人名称
    private String operatorName = new String();

    // 操作时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id", this.getId());
        json.element("text", this.getName());

        return json;
    }

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getMoneyTime() {
        return moneyTime;
    }

    public void setMoneyTime(String moneyTime) {
        this.moneyTime = moneyTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getInOrOutName() {
        return inOrOutName;
    }

    public void setInOrOutName(String inOrOutName) {
        this.inOrOutName = inOrOutName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
