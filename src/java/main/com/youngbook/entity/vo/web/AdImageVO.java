package com.youngbook.entity.vo.web;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by admin on 2015/4/22.
 */
@Table(name = "Web_AdImage", jsonPrefix = "adImageVO")
public class AdImageVO extends BaseVO{
    /**
     * sid
     */
    @Id
    private int sid = Integer.MAX_VALUE;

    /**
     * id
     */
    private String id = new String();

    /**
     * state
     */
    private int state = Integer.MAX_VALUE;

    /**
     * operatorId
     */
    private String operatorId = new String();

    private String catalogType = new String();

    public String getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    /**
     * operateTime
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    /**
     * 归类编号 : 必填,KV下拉菜单【Web_AdImageCatalog】
     */
    private String catalogId = new String();

    /**
     * 名称 : 支持查询,必填
     */
    private String name = new String();

    /**
     * 描述 : 支持查询
     */
    private String description = new String();

    /**
     * URL : 必填
     */
    private String url = new String();

    /**
     * 大小
     */
    private double size = Double.MAX_VALUE;

    /**
     * 宽
     */
    private double width = Double.MAX_VALUE;

    /**
     * 高
     */
    private double height = Double.MAX_VALUE;

    /**
     * 响应地址
     */
    private String responseURL = new String();


    private String IsAvaliableType = new String();

    /**
     * 排序
     */
    private int orders = Integer.MAX_VALUE;

    /**
     * 是否使用
     */
    private int isAvaliable = Integer.MAX_VALUE;


    public String getIsAvaliableType() {
        return IsAvaliableType;
    }

    public void setIsAvaliableType(String isAvaliableType) {
        IsAvaliableType = isAvaliableType;
    }

    /**
     * 启用时间 : 时间段查询
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();

    /**
     * 停用时间 : 时间段查询
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String endTime = new String();
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

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getResponseURL() {
        return responseURL;
    }

    public void setResponseURL(String responseURL) {
        this.responseURL = responseURL;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getIsAvaliable() {
        return isAvaliable;
    }

    public void setIsAvaliable(int isAvaliable) {
        this.isAvaliable = isAvaliable;
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
}
