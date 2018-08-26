package com.youngbook.entity.po.customer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by admin on 2015/4/28.
 */
public class CustomerMoneyLogType {
    public static final String Deposit  = "充值";
    public static final String Withdraw = "提现";
    public static final String Pay =  "购买";
    public static final String Payment =  "兑付";
    public static final String Refund = "退款";
    /**
     * 创建json数组
     * @return
     */
    public static JSONArray toJsonArray() {
        JSONObject jArrays = new JSONObject();//创建一个json对象
        JSONArray array  = new JSONArray();//创建一个json数组
        jArrays.element("id",CustomerMoneyLogType.Deposit);
        jArrays.element("text",CustomerMoneyLogType.Deposit);
        array.add(jArrays);//添加到json数组中
        jArrays.element("id",CustomerMoneyLogType.Withdraw);
        jArrays.element("text",CustomerMoneyLogType.Withdraw);
        array.add(jArrays);//添加到json数组中
        jArrays.element("id",CustomerMoneyLogType.Pay);
        jArrays.element("text",CustomerMoneyLogType.Pay);

        jArrays.element("id",CustomerMoneyLogType.Payment);
        jArrays.element("text",CustomerMoneyLogType.Payment);

        jArrays.element("id",CustomerMoneyLogType.Refund);
        jArrays.element("text",CustomerMoneyLogType.Refund);
        array.add(jArrays);//添加到json数组中
        return  array;
    }
}
