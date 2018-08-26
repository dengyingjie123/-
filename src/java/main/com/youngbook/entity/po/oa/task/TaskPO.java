package com.youngbook.entity.po.oa.task;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2015/4/14.
 * OA_任务
 */

@Table(name = "OA_Task", jsonPrefix = "task")
public class TaskPO  extends BasePO{
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 分类编号 : 必填
    private String catalogId = new String();

    // 名称 : 必填
    private String name = new String();

    // 描述
    private String description = new String();

    // 完成进度
    private double process = Double.MAX_VALUE;

    // 状态 : 必填
    private int status = Integer.MAX_VALUE;

    // 开始时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String startTime = new String();

    // 结束时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String stopTime = new String();

    // 创建者 : 必填
    private String creatorId = new String();

    // 创建时间 : 时间段查询
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();

    // 执行人 : 必填
    private String executorId = new String();

    // 执行时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String executeTime = new String();

    // 检查人
    private String checkerId = new String();

    // 检查时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String checkTime = new String();

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

    public double getProcess() {
        return process;
    }

    public void setProcess(double process) {
        this.process = process;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public TaskPO() {
    }
}
