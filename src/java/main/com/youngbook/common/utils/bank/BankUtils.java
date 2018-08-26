package com.youngbook.common.utils.bank;

import com.youngbook.common.config.Config4Bank;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.util.Map;

public class BankUtils {

    public static String getBankList(Connection conn) throws Exception {

        JSONArray array = new JSONArray();

        Map<String, String> banks = Config4Bank.getBanks(conn);
        for(String key : banks.keySet()) {
            String value = banks.get(key);
            JSONObject object = new JSONObject();
            object.element("id", key);
            object.element("text", value);
            array.add(object);
        }

        return array.toString();

    }

}
