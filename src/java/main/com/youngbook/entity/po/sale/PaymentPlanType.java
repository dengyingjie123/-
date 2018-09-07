package com.youngbook.entity.po.sale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 15-4-9
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public class PaymentPlanType {
    /**
     * 一次性本息兑付
     */
    public static final String OncePayment = "一次性本息兑付";
    public static final String OncePaymentText = "一次性本息兑付";


    // Periodicity
    public static final String Periodicity = "周期性兑付";
    public static final String Periodicity_Text = "周期性兑付";

    /**
     * 等额本息： Average Capital Plus Interest，简称ACPI
     */
    public static final String ACPI = "等额本息";
    public static final String ACPIText = "等额本息";

    public static JSONArray getStrutsJSONArray() {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        //等额等息
        array.add(getJSONObject(object, PaymentPlanType.OncePayment, PaymentPlanType.OncePaymentText));
        array.add(getJSONObject(object, PaymentPlanType.Periodicity, PaymentPlanType.Periodicity_Text));
        //array.add(getJSONObject(object, PaymentPlanType.ACPI, PaymentPlanType.ACPIText));
        return array;
    }

    public static JSONObject getJSONObject(JSONObject object, String id, String text) {
        object.element("id", id);
        object.element("text", text);
        return object;
    }
}
