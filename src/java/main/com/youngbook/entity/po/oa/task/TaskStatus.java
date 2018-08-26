package com.youngbook.entity.po.oa.task;

import com.youngbook.common.IJsonable;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2115/4/27.
 * 用来封装状态下拉列表
 * @author
 */

public class TaskStatus  {

   public static final int STATUS_1 = 1;
   public static final int STATUS_2 = 2;

   public static final String STATUS_NAME_1 = "状态1";
   public static final String STATUS_NAME_2 = "状态2";


    public static JSONArray toJsonArray() {

        JSONObject jArrays = new JSONObject();//创建一个json对象
        JSONArray array  = new JSONArray();//创建一个json数组

        jArrays.element("id",TaskStatus.STATUS_1);
        jArrays.element("text",TaskStatus.STATUS_NAME_1);
        array.add(jArrays);//添加到json数组中

        jArrays.element("id",TaskStatus.STATUS_2);
        jArrays.element("text",TaskStatus.STATUS_NAME_2);
        array.add(jArrays);//添加到json数组中


        return  array;
    }


}
