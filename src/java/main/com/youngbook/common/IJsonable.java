package com.youngbook.common;

import net.sf.json.JSONObject;


public interface IJsonable {
    public JSONObject toJsonObject();
    public JSONObject toJsonObject4Form();
    public JSONObject toJsonObject4Form(String profix);
    public JSONObject toJsonObject4Tree();
    public JSONObject toJsonObject4Treegrid();
}
