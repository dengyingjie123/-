package com.youngbook.action.wsi;

import com.youngbook.dao.JSONDao;
import net.sf.json.JSONObject;

/**
 * Created by Lee on 11/11/2015.
 */
public class RequestObject {

    private String name = "";
    private String version = "";
    private String data = "";
    private String callback = "";
    private String token = "";

    private JSONObject jsonObject = null;

    public void addData(String key, String value) {
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        jsonObject.element(key, value);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getData() {
        return jsonObject.toString();
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
