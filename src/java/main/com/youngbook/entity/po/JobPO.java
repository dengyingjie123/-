package com.youngbook.entity.po;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Jayden
 * Date: 14-5-9
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "system_job",jsonPrefix = "job")
public class JobPO extends BasePO {
    @Id
    private String id = new String();
    private String jobName = new String();
    private String departmentId = new String();
    private String departmentName = new String();

    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id",this.getId());
        json.element("text",this.getJobName());
        json.element("departmentId",this.getDepartmentId());
        json.element("departmentName",this.getDepartmentName());
        return json;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
