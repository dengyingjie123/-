package com.youngbook.common.utils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Lee on 2015/8/22.
 */
public class Struts2Utils {

    /**
     *
     * @param ai
     * @return json 或者 html
     */
    public static String getActionReturnType(ActionInvocation ai) {
        Map<String, ResultConfig> results = ai.getProxy().getConfig().getResults();

        String key = results.keySet().iterator().next();
        ResultConfig resultConfig = results.get(key);
        if (resultConfig.getClassName().equalsIgnoreCase("org.apache.struts2.json.JSONResult")) {
            return "json";
        }

        return "html";
    }
}
