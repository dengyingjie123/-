package com.youngbook.entity.vo.system;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/16/14
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */

public class ProjectMenuVO extends BaseVO {
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
