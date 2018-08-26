package com.youngbook.entity.po.sale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by ThinkPad on 5/6/2015.
 */
public class ProductionTransferStatus {
    public static final int STATUS_0 = 0;
    public static final int STATUS_1 = 1;

    public static final String STATUS_NAME_0 = "未转让";
    public static final String STATUS_NAME_1 = "已转让";

    public static JSONArray toJsonArray(){
        JSONObject jArrays = new JSONObject();//创建一个json对象
        JSONArray array  = new JSONArray();//创建一个json数组

        jArrays.element("id",ProductionTransferStatus.STATUS_0);
        jArrays.element("text",ProductionTransferStatus.STATUS_NAME_0);
        array.add(jArrays);//添加到json数组中

        jArrays.element("id",ProductionTransferStatus.STATUS_1);
        jArrays.element("text",ProductionTransferStatus.STATUS_NAME_1);
        array.add(jArrays);//添加到json数组中


        return  array;
    }
}
