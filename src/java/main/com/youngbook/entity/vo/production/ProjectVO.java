package com.youngbook.entity.vo.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/14/14
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_project", jsonPrefix = "projectVO")
public class ProjectVO extends BaseVO{
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    //名称
    private String name = new String();

    // 规模(钱)
    private double size = Double.MAX_VALUE;

    // 开始时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();

    // 结束时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String endTime =  new String();

    // 状态
    private String status = new String();

    // 类型
    private String type = new String();

    // 投资方向
    private String investmentDirection = new String();

    // 合作方向
    private String partner = new String();

    //描述
    private String description = new String();

    //所属行业
    private  String industry = new String();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInvestmentDirection() {
        return investmentDirection;
    }

    public void setInvestmentDirection(String investmentDirection) {
        this.investmentDirection = investmentDirection;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
