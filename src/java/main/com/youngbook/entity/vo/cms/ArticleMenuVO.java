package com.youngbook.entity.vo.cms;

import com.youngbook.entity.vo.BaseVO;

public class ArticleMenuVO extends BaseVO {

    // id
    private String id = new String();

    //����
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
