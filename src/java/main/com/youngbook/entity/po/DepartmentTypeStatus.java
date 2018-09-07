package com.youngbook.entity.po;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by 张舜清 on 7/2/2015.
 */
public class DepartmentTypeStatus {
    public static final String TYPE_14506 = "14506";
    public static final String TYPE_14509 = "14509";
    public static final String TYPE_14510 = "14510";
    public static final String TYPE_14511 = "14511";
    public static final String TYPE_14512 = "14512";
    public static final String TYPE_14513 = "14513";
    public static final String TYPE_NAME_14506 = "公司";
    public static final String TYPE_NAME_14509 = "分公司";
    public static final String TYPE_NAME_14510 = "部门";
    public static final String TYPE_NAME_14511 = "中心";
    public static final String TYPE_NAME_14512 = "渠道";
    public static final String TYPE_NAME_14513 = "其他";

    public static JSONArray toJsonArray() {
        JSONObject jArrays = new JSONObject();//创建一个json对象
        JSONArray array  = new JSONArray();//创建一个json数组
        jArrays.element("id",DepartmentTypeStatus.TYPE_14506);
        jArrays.element("text",DepartmentTypeStatus.TYPE_NAME_14506);
        array.add(jArrays);//添加到json数组中
        jArrays.element("id",DepartmentTypeStatus.TYPE_14509);
        jArrays.element("text",DepartmentTypeStatus.TYPE_NAME_14509);
        array.add(jArrays);//添加到json数组中
        jArrays.element("id",DepartmentTypeStatus.TYPE_14510);
        jArrays.element("text",DepartmentTypeStatus.TYPE_NAME_14510);
        array.add(jArrays);//添加到json数组中
        jArrays.element("id",DepartmentTypeStatus.TYPE_14511);
        jArrays.element("text",DepartmentTypeStatus.TYPE_NAME_14511);
        array.add(jArrays);//添加到json数组中
        jArrays.element("id",DepartmentTypeStatus.TYPE_14512);
        jArrays.element("text",DepartmentTypeStatus.TYPE_NAME_14512);
        array.add(jArrays);//添加到json数组中
        jArrays.element("id",DepartmentTypeStatus.TYPE_14513);
        jArrays.element("text",DepartmentTypeStatus.TYPE_NAME_14513);
        array.add(jArrays);//添加到json数组中
        return  array;
    }
}
