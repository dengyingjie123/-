package com.youngbook.entity.vo.system;

import com.youngbook.entity.vo.BaseVO;
import net.sf.json.JSONArray;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-19
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class MenuVO  extends BaseVO {
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
