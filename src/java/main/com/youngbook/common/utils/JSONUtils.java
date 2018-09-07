package com.youngbook.common.utils;

import net.sf.json.JSONObject;

/**
 * Created by haihong on 2015/6/1.
 *JSON辅助类
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class JSONUtils {


    /**
     * 添加数据
     * @param obj
     * @param id
     * @param text
     * @return
     */
    public  static JSONObject toOBject(JSONObject obj ,String id,String text){
        obj.element("id",id);
        obj.element("text",text);
        return obj;
    }
}
