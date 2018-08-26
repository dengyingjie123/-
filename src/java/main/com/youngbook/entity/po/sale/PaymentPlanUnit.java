package com.youngbook.entity.po.sale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PaymentPlanUnit {

    public static final int UnitDay = 0;
    public static final String UnitDayText = "按日";

    public static final int UnitMonth = 1;
    public static final String UnitMonthText = "按月";

    public static final int UnitYear = 2;
    public static final String UnitYearText = "按年";

    public static JSONArray getStrutsJSONArray() {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        array.add(getJSONObject(object, PaymentPlanUnit.UnitDay, PaymentPlanUnit.UnitDayText));
        array.add(getJSONObject(object, PaymentPlanUnit.UnitMonth, PaymentPlanUnit.UnitMonthText));
        array.add(getJSONObject(object, PaymentPlanUnit.UnitYear, PaymentPlanUnit.UnitYearText));
        return array;
    }

    public static JSONObject getJSONObject(JSONObject object, int id, String text) {
        object.element("id", id);
        object.element("text", text);
        return object;
    }

}
