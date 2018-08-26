package com.youngbook.entity.vo.system;

import com.youngbook.entity.vo.BaseVO;

/**
 * Created by yux on 2016/6/29.
 */
public class DepartmentVO extends BaseVO {
    // id
    private String id = new String();

    //名称
    private String text = new String();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
