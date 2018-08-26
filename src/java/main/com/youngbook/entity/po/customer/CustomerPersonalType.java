package com.youngbook.entity.po.customer;

/**
 * Created by Lee on 2016/5/19.
 */
public class CustomerPersonalType {

    public static final String UNKNOW = "100";
    public static final String NORMAL = "1";
    public static final String NEW = "0";
    public static final String VIP = "2";
    public static final String POTENTIAL = "3";
    public static final String UNTOUCHABLE = "4";



    public static String getCustomerPersonalTypeName (String customerPersonalType) {
        if (customerPersonalType.equals(UNKNOW)) {
            return "不确定";
        }

        if (customerPersonalType.equals(NORMAL)) {
            return "普通客户";
        }

        if (customerPersonalType.equals(NEW)) {
            return "新客户";
        }

        if (customerPersonalType.equals(VIP)) {
            return "VIP";
        }

        if (customerPersonalType.equals(POTENTIAL)) {
            return "潜在客户";
        }

        if (customerPersonalType.equals(UNTOUCHABLE)) {
            return "超期未联系";
        }

        return "不确定";
    }
}
