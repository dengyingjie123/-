package com.youngbook.action.report;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.*;
import com.youngbook.service.report.SimpleReportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class SimpleReportAction extends BaseAction {

    @Autowired
    SimpleReportService simpleReportService;

    private void checkPermission(String reportName) throws Exception {

        List<KVObject> check = new ArrayList<KVObject>();
        check.add(new KVObject("listReportPaymentPlanMonth", "移动端查看兑付计划"));
        check.add(new KVObject("listReportPaymentPlan_Production_Month", "移动端查看兑付计划"));
        check.add(new KVObject("listReportPaymentPlan_Production_Customer_Month", "移动端查看兑付计划"));


        for (int i = 0; i < check.size(); i++) {
            KVObject p = check.get(i);

            if (reportName.equals(p.getKeyStringValue())) {
                if (!Config.hasPermission(p.getValueStringValue(), getRequest())) {
                    MyException.newInstance("用户没有权限访问，请检查。").throwException();
                }
            }
        }
    }


    public String getReport() throws Exception {



        // 获得报表名
        String reportName = getHttpRequestParameter("reportName");
        checkPermission(reportName);




        // 表头配置信息
        String titleConfigString = getHttpRequestParameter("titleConfigString");

        // 构造传入参数
        String p = getHttpRequestParameter("p");
        List<KVObject> parameters = null;
        if (!StringUtils.isEmpty(p)) {
            p = StringUtils.urlDecode(p);
            parameters = HttpUtils.getParameters(p);
        }


        List<KVObjects> list = simpleReportService.getListReportData(reportName, parameters, getConnection());

        JSONObject json = new JSONObject();

        /**
         * 构造列表表头
         */
        JSONArray columnNames = SimpleReportService.buildColumnNames(list, titleConfigString);
        json.put("columnNames", columnNames);


        /**
         * 构造数据
         */
        JSONObject tableJson = new JSONObject();
        JSONArray dataArray = new JSONArray();
        for (int i = 0; list != null && i < list.size(); i++) {
            KVObjects kvObjects = list.get(i);

            JSONObject rowData = new JSONObject();
            for (int j = 0; j < kvObjects.size(); j++) {
                KVObject kv = kvObjects.get(j);

                rowData.put(StringUtils.getValue(kv.getKey()), MoneyUtils.format2String(kv.getValueStringValue()));

            }
            dataArray.add(rowData);
        }
        tableJson.put("total", dataArray.size());
        tableJson.put("rows", dataArray);
        json.put("data", tableJson);

        getResult().setReturnValue(json.toString());

        return SUCCESS;
    }
}
