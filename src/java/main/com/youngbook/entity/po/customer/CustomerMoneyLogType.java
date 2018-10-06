package com.youngbook.entity.po.customer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by admin on 2015/4/28.
 */
public class CustomerMoneyLogType {

    public static final String Deposit  = "充值";
    public static final String WithdrawOrPayment = "兑付或提现";
    public static final String Buy =  "购买";
    public static final String Profit =  "收益";
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
        jArrays.element("id",CustomerMoneyLogType.WithdrawOrPayment);
        jArrays.element("text",CustomerMoneyLogType.WithdrawOrPayment);
        array.add(jArrays);//添加到json数组中
        jArrays.element("id",CustomerMoneyLogType.Buy);
        jArrays.element("text",CustomerMoneyLogType.Buy);

        jArrays.element("id",CustomerMoneyLogType.Refund);
        jArrays.element("text",CustomerMoneyLogType.Refund);
        array.add(jArrays);//添加到json数组中
        return  array;
    }
}
