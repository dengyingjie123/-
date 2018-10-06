package com.youngbook.entity.po.customer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by admin on 2015/4/28.
 */
public class CustomerMoneyLogStatus {
    public static final String Default = "0";
    public static final String DefaultName = "未操作";
    public static final String Success = "1";
    public static final String SuccessName = "成功";
    public static final String Accepted = "2";
    public static final String AcceptedName = "已受理";
    public static final String Failed = "3";
    public static final String FailedName = "失败";

    /**
     * 创建json数组
     * @return
     */
    public static JSONArray toJsonArray() {
        JSONObject jArrays = new JSONObject();//创建一个json对象
        JSONArray array  = new JSONArray();//创建一个json数组

        jArrays.element("id",CustomerMoneyLogStatus.Default);
        jArrays.element("text",CustomerMoneyLogStatus.DefaultName);
        jArrays.element("id",CustomerMoneyLogStatus.Success);
        jArrays.element("text",CustomerMoneyLogStatus.SuccessName);
        jArrays.element("id",CustomerMoneyLogStatus.Failed);
        jArrays.element("text",CustomerMoneyLogStatus.FailedName);
        jArrays.element("id",CustomerMoneyLogStatus.Accepted);
        jArrays.element("text",CustomerMoneyLogStatus.AcceptedName);
        array.add(jArrays);//添加到json数组中
        return  array;
    }
}
