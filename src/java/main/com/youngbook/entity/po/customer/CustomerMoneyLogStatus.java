package com.youngbook.entity.po.customer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by admin on 2015/4/28.
 */
public class CustomerMoneyLogStatus {
    public static final String Successd = "成功";
    public static final String Failed = "失败";
    public static final String Accepted = "已接受";
    /**
     * 创建json数组
     * @return
     */
    public static JSONArray toJsonArray() {
        JSONObject jArrays = new JSONObject();//创建一个json对象
        JSONArray array  = new JSONArray();//创建一个json数组
        jArrays.element("id",CustomerMoneyLogStatus.Successd);
        jArrays.element("text",CustomerMoneyLogStatus.Successd);
        array.add(jArrays);//添加到json数组中
        jArrays.element("id",CustomerMoneyLogStatus.Failed);
        jArrays.element("text",CustomerMoneyLogStatus.Failed);

        jArrays.element("id",CustomerMoneyLogStatus.Accepted);
        jArrays.element("text",CustomerMoneyLogStatus.Accepted);
        array.add(jArrays);//添加到json数组中
        return  array;
    }
}
