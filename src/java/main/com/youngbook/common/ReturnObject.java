package com.youngbook.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class ReturnObject {

    public static int CODE_SUCCESS = 100;
    public static int CODE_EXCEPTION = 200;
    public static int CODE_LOGIN_EXCEPTION = 201;
    public static int CODE_PERMISSION_EXCEPTION = 202;
    public static int CODE_DB_EXCEPTION = 300;


    public static int CODE_WF_EXCEPTION = 500;
    public static int CODE_WF_PERMISSION_EXCEPTION = 501;
    public static int CODE_WF_DATA_EXCEPTION = 502;

    private int code;
    private String message;
    private Object returnValue;
    private Exception exception;
    private Class clazz; // full name, like:  com.youngbook.test.Tester

    private String token;

    public static ReturnObject parse(String text) throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(text);
        ReturnObject returnObject = (ReturnObject)JSONObject.toBean(jsonObject, ReturnObject.class);
        return returnObject;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "ReturnObject{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", returnValue=" + returnValue +
                ", exception=" + exception +
                ", clazz=" + clazz +
                ", token='" + token + '\'' +
                '}';
    }
}
