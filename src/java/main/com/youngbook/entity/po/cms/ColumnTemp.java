package com.youngbook.entity.po.cms;

import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-17
 * Time: 下午6:40
 * To change this template use File | Settings | File Templates.
 */
public class ColumnTemp extends BasePO {
    private String id=new String();
    private String text=new String();
    private String parentId=new String();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
