package com.youngbook.entity.po.production;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2015/5/18.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class ProductionsInterestType  {
    public static final String waitInterestText = "一次性还本付息";

    public static final String periodText = "周期付息";
    public static JSONArray getStrutsJSONArray() {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        //等额等息
        array.add(getJSONObject(object, ProductionsInterestType.waitInterestText, ProductionsInterestType.waitInterestText));
        array.add(getJSONObject(object, ProductionsInterestType.periodText, ProductionsInterestType.periodText));
        return array;
    }

    public static JSONObject getJSONObject(JSONObject object,String id,String text){
        object.element("id",id);
        object.element("text",text);

        return object;
    }
}
