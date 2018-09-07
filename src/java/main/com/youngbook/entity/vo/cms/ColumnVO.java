package com.youngbook.entity.vo.cms;

import com.youngbook.annotation.Id;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-17
 * Time: 下午1:04
 * To change this template use File | Settings | File Templates.
 */
public class ColumnVO extends BaseVO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // 姓名 : 支持查询,必填
    private String name = new String();

    //描述
    private String description =new String();

    //父级ID
    private String parentId =new String();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
