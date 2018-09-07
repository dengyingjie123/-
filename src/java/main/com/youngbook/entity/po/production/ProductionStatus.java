package com.youngbook.entity.po.production;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 15-5-3
 * Time: 下午7:14
 * To change this template use File | Settings | File Templates.
 */
public class ProductionStatus  {

    public static final int Draft = 0;
    public static final String DraftText = "草稿";

    public static final int WaitingForPublish = 1;
    public static final String WaitingForPublishText = "计划";

    public static final int Sale = 2;
    public static final String SaleText = "在售";

    public static final int Pause = 3;
    public static final String PauseText = "暂停";

    public static final int Sold = 4;
    public static final String SoldText = "售完";

    public static final int Cancel = 5;
    public static final String CancelText = "作废";

    public static JSONArray getStrutsJSONArray() {
        JSONArray array = new JSONArray();
        JSONObject object= new JSONObject();
        //草稿
        array.add(getJSONObject(object,ProductionStatus.Draft,ProductionStatus.DraftText));
        //等待发布
        array.add(getJSONObject(object,ProductionStatus.WaitingForPublish,ProductionStatus.WaitingForPublishText));
        //在售
        array.add(getJSONObject(object,ProductionStatus.Sale,ProductionStatus.SaleText));
        //暂停
        array.add(getJSONObject(object,ProductionStatus.Pause,ProductionStatus.PauseText));
        //售完
        array.add(getJSONObject(object,ProductionStatus.Sold,ProductionStatus.SoldText));
        //作废
        array.add(getJSONObject(object,ProductionStatus.Cancel,ProductionStatus.CancelText));

        return array;
    }

    public static JSONObject getJSONObject(JSONObject object, int id, String text ){
        object.element("id",id);
        object.element("text",text);
        return object;
    }
}
