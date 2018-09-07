package com.youngbook.entity.po.cms;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-17
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "CMS_ColumnPermission",jsonPrefix = "columnPermission")
public class ColumnPermissionPO extends BasePO {
    // 编号
    private String id = new String();
    // SID
    @Id
    private int sid = Integer.MAX_VALUE;
    // State
    private int state = Integer.MAX_VALUE;
    // 操作人
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();
    //栏目编号
    private String columnId = new String();
    //岗位编号
    private String positionId = new String();

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
