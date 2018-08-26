package com.youngbook.entity.po.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Table;
import com.youngbook.annotation.Id;
import com.youngbook.entity.po.BasePO;

/**
 * 产品审核
 * Created by zhouhaihong on 2015/4/9.
 */

@Table(name = "CRM_ProductionCheck", jsonPrefix = "productionCheck")
public class ProductionCheckPO extends BasePO{

    /**
     * Sid
     */
    @Id
    private int sid = Integer.MAX_VALUE;

    /**
     * Id
     */
    private String id = new String();

    /**
     * state
     */
    private int state = Integer.MAX_VALUE;

    /**
     * OperatorId
     */
    private String operatorId = new String();

    /**
     * OperateTime
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    /**
     * 产品编号 : 支持查询
     */
    private String productionId = new String();

    /**
     * 审核人1
     */
    private String checker1Id = new String();

    /**
     * 审核时间1
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String checker1Time = new String();

    /**
     * 审核标识1 : 取值：0：未通过，1：通过
     */
    private int checker1Tag = Integer.MAX_VALUE;

    /**
     * 审核内容1
     */
    private String checker1Content = new String();

    /**
     * 审核人2
     */
    private String checker2Id = new String();

    /**
     * 审核时间2
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String checker2Time = new String();

    /**
     * 审核标识2
     */
    private int checker2Tag = Integer.MAX_VALUE;

    /**
     * 审核内容2
     */
    private String checker2Content = new String();

    /**
     * 审核人3
     */
    private String checker3Id = new String();

    /**
     * 审核时间3
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String checker3Time = new String();

    /**
     * 审核标识3
     */
    private int checker3Tag = Integer.MAX_VALUE;

    /**
     * 审核内容3
     */
    private String checker3Content = new String();

    //getset


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

    public String getChecker1Id() {
        return checker1Id;
    }

    public void setChecker1Id(String checker1Id) {
        this.checker1Id = checker1Id;
    }

    public String getChecker1Time() {
        return checker1Time;
    }

    public void setChecker1Time(String checker1Time) {
        this.checker1Time = checker1Time;
    }

    public int getChecker1Tag() {
        return checker1Tag;
    }

    public void setChecker1Tag(int checker1Tag) {
        this.checker1Tag = checker1Tag;
    }

    public String getChecker1Content() {
        return checker1Content;
    }

    public void setChecker1Content(String checker1Content) {
        this.checker1Content = checker1Content;
    }

    public String getChecker2Id() {
        return checker2Id;
    }

    public void setChecker2Id(String checker2Id) {
        this.checker2Id = checker2Id;
    }

    public String getChecker2Time() {
        return checker2Time;
    }

    public void setChecker2Time(String checker2Time) {
        this.checker2Time = checker2Time;
    }

    public int getChecker2Tag() {
        return checker2Tag;
    }

    public void setChecker2Tag(int checker2Tag) {
        this.checker2Tag = checker2Tag;
    }

    public String getChecker2Content() {
        return checker2Content;
    }

    public void setChecker2Content(String checker2Content) {
        this.checker2Content = checker2Content;
    }

    public String getChecker3Id() {
        return checker3Id;
    }

    public void setChecker3Id(String checker3Id) {
        this.checker3Id = checker3Id;
    }

    public String getChecker3Time() {
        return checker3Time;
    }

    public void setChecker3Time(String checker3Time) {
        this.checker3Time = checker3Time;
    }

    public int getChecker3Tag() {
        return checker3Tag;
    }

    public void setChecker3Tag(int checker3Tag) {
        this.checker3Tag = checker3Tag;
    }

    public String getChecker3Content() {
        return checker3Content;
    }

    public void setChecker3Content(String checker3Content) {
        this.checker3Content = checker3Content;
    }

    public ProductionCheckPO() {
    }
}
