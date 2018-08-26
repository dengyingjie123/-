package com.youngbook.entity.po.customer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CustomerAuthenticationStatus {

    public static final int STATUS_0 = 0;
    public static final int STATUS_1 = 1;

    // 是否已认证
    public static final String STATUS_NAME_0 = "未认证";
    public static final String STATUS_NAME_1 = "已认证";

    // 认证类型
    public static final Integer AUTH_TYPE_MOBILE = 0;   // 手机认证
    public static final Integer AUTH_TYPE_EMAIL = 1;    // 邮箱认证
    public static final Integer AUTH_TYPE_ACCOUNT = 2;  // 银行卡认证
    public static final Integer AUTH_TYPE_QA = 3;       // 安全问题认证
    public static final Integer AUTH_TYPE_VIDEO = 4;    // 视频认证
    public static final Integer AUTH_TYPE_FACE = 5;     // 现场见面认证

    public static JSONArray toJsonArray(){
        JSONObject jArrays = new JSONObject();//创建一个json对象
        JSONArray array  = new JSONArray();//创建一个json数组

        jArrays.element("id",CustomerAuthenticationStatus.STATUS_0);
        jArrays.element("text",CustomerAuthenticationStatus.STATUS_NAME_0);
        array.add(jArrays);//添加到json数组中

        jArrays.element("id",CustomerAuthenticationStatus.STATUS_1);
        jArrays.element("text",CustomerAuthenticationStatus.STATUS_NAME_1);
        array.add(jArrays);//添加到json数组中

        return  array;
    }
}
