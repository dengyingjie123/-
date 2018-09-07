package com.youngbook.dao;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.IgnoreDB;
import com.youngbook.annotation.IgnoreJson;
import com.youngbook.annotation.Table;
import com.youngbook.common.*;
import com.youngbook.common.utils.ObjectUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.Utils;
import com.youngbook.entity.po.BasePO;
import com.youngbook.entity.po.wechat.UserInfoPO;
import com.youngbook.entity.vo.BaseVO;
import com.youngbook.service.system.LogService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.*;

/**
 * User: Lee
 * Date: 14-4-2
 */
public class JSONDao {

    public static void main(String [] args) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickname", "leevits");

        UserInfoPO userInfoPO = JSONDao.parse(jsonObject, UserInfoPO.class);

        System.out.println(userInfoPO.getNickname());
    }

    public static <T> T parse(String jsonString, Class<T> clazz) throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        return parse(jsonObject, clazz);
    }


    public static <T> T parse(JSONObject jsonObject, Class<T> clazz) throws Exception {
        T object = clazz.newInstance();

        for(Field field : object.getClass().getDeclaredFields()) {
            String fieldName = field.getName();

            if (jsonObject.containsKey(fieldName)) {

                if (!ObjectUtils.isAssignableFrom(field.getType(), String.class) && !ObjectUtils.isAssignableFrom(field.getType(), boolean.class)) {
                    MyException.newInstance("JSON转换失败，暂不支持【"+field.getType()+"】类型").throwException();
                }


                /**
                 * 字符串类型
                 */
                if (ObjectUtils.isAssignableFrom(field.getType(), String.class)) {
                    String value = jsonObject.getString(fieldName);

                    String firstLetter = field.getName().substring(0, 1).toUpperCase();
                    String setMethodName = "set" + firstLetter + field.getName().substring(1);
                    //System.out.println("JSONDao.get(): " + getMethodName);
                    Method setMethod = Utils.getMethod(clazz, setMethodName, new Class[]{String.class});
                    setMethod.invoke(object, new Object[] {value});
                }
                else if (ObjectUtils.isAssignableFrom(field.getType(), boolean.class)) {
                    boolean value = Boolean.valueOf(jsonObject.getString(fieldName));

                    String firstLetter = field.getName().substring(0, 1).toUpperCase();
                    String setMethodName = "set" + firstLetter + field.getName().substring(1);
                    //System.out.println("JSONDao.get(): " + getMethodName);
                    Method setMethod = Utils.getMethod(clazz, setMethodName, new Class[]{boolean.class});
                    setMethod.invoke(object, new Object[] {value});
                }

            }
        }

        return object;
    }

    public static Map<String, String> toMap (String jsonString) throws JSONException {

        JSONObject jsonObject = JSONObject.fromObject(jsonString);

        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;

        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);

        }
        return result;

    }


    public static KVObjects toKVObjects (String jsonString) throws JSONException {

        JSONObject jsonObject = JSONObject.fromObject(jsonString);


        KVObjects kvObjects = new KVObjects();

        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;

        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);

            kvObjects.addItem(key, value);
        }
        return kvObjects;

    }

    public static <T> JSONObject get(List<T> list) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; list != null && i < list.size(); i++) {
            IJsonable temp = (IJsonable)list.get(i);
            array.add(temp.toJsonObject());
        }
        json.element("rows", array);
        return json;
    }

    public static <T> JSONArray getArray(List<T> list) {
        JSONArray array = new JSONArray();
        for (int i = 0; list != null && i < list.size(); i++) {
            IJsonable temp = (IJsonable)list.get(i);
            array.add(temp.toJsonObject());
        }
        return array;
    }

    public static <T> JSONObject get4Tree(List<T> list) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; list != null && i < list.size(); i++) {
            IJsonable temp = (IJsonable)list.get(i);
            array.add(temp.toJsonObject4Tree());
        }
        json.element("rows", array);
        return json;
    }

    public static JSONObject get4Form(Object object) throws Exception {
        String jsonPrefix = "";

        Table annTable = object.getClass().getAnnotation(Table.class);
        if (annTable != null && !annTable.jsonPrefix().equals("")) {
            jsonPrefix = annTable.jsonPrefix();
        }

        return get(object, jsonPrefix);
    }


    public static JSONObject parse(Map<String, String> map) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        Set<String> setKeys = map.keySet();
        Iterator<String> itKey = setKeys.iterator();
        while(itKey.hasNext()) {
            String key = itKey.next();
            String value = map.get(key);

            JSONObject temp = new JSONObject();
            temp.element("key", key);
            temp.element("value", value);
            array.add(temp);
        }
        json.element("rows", array);
        return json;
    }

    /**
     * 判断一个列是否被忽略注解标记
     * @param field
     * @return
     */
    public static boolean isIgnore(Field field) {

        if (field == null) {
            return true;
        }

        IgnoreJson ignoreJson = field.getAnnotation(IgnoreJson.class);

        if (ignoreJson != null) {
            return true;
        }
        return false;
    }

    public static String decode(String s) throws Exception {

        s = s.replaceAll("'","\\'");

        return s;
    }

    public static JSONObject get(Object object) throws Exception {
        return get(object, "");
    }

    public static String getJsonString(Object object) throws Exception {

        JSONObject jsonObject = get(object);
        return jsonObject.toString();

    }

    public static JSONObject get(Object object, String prefix) throws Exception{

        if (prefix != null && !prefix.equals("")) {
            prefix = prefix + ".";
        }
        else {
            prefix = "";
        }

        JSONObject json = new JSONObject();
        Class clazz = object.getClass();


        for(Field field : object.getClass().getDeclaredFields()) {

            if (isIgnore(field)) {
                continue;
            }

            String fieldName = field.getName();
            String tag = "";
            if (Database.isStringType(field.getType())
                    || Database.isDateTime(field.getType())) {
                tag = "'";
            }

            String firstLetter = field.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + field.getName().substring(1);
            //System.out.println("JSONDao.get(): " + getMethodName);
            Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});
            Object value = getMethod.invoke(object, new Object[] {});

            if (value == null) {
                value = new String();
            }

            LogService.debug("JSONDao.get(): field: ["+prefix + fieldName +"] value: ["+String.valueOf(value)+"]", JSONDao.class);

            // 日期类型
            if (Database.isDateTime(value.getClass())) {
                Date temp = (Date)value;
                String tempValue = TimeUtils.getDateSimpleString(temp);
                json.element(prefix+fieldName, tempValue);
            }
            // int
            else if (Database.isInteger(value.getClass())) {
                int tempValue = Integer.parseInt(String.valueOf(value));
                json.element(prefix+fieldName, tempValue);
            }
            // float
            else if (Database.isFloat(value.getClass())) {
                float tempValue = Float.parseFloat(String.valueOf(value));
                json.element(prefix+fieldName, tempValue);
            }
            // double
            else if (Database.isDouble(value.getClass())) {
                double tempValue = Double.parseDouble(String.valueOf(value));
                json.element(prefix+fieldName, tempValue);
            }
            // boolean
            else if (Database.isBoolean(value.getClass())) {
                boolean tempValue = Boolean.parseBoolean(String.valueOf(value));
                json.element(prefix+fieldName, tempValue);
            }
            else  if (value instanceof List<?> ) {

                List list = (List)value;

                JSONArray array = new JSONArray();

                for (int i = 0; list != null && i < list.size(); i++) {
                    Object o = list.get(i);
                    if (o instanceof BaseVO) {
                        BaseVO baseVO = (BaseVO)o;
                        array.add(baseVO.toJsonObject());
                    }

                    if (o instanceof BasePO) {
                        BasePO basePO = (BasePO)o;
                        array.add(basePO.toJsonObject());
                    }
                }
                json.element(prefix+fieldName, array);

            }
            else if (BasePO.class.isInstance(value.getClass())) {
                BasePO basePO = (BasePO)value;
                json.element(prefix+fieldName, basePO.toJsonObject());
            }
            else if (BaseVO.class.isInstance(value.getClass())) {
                BaseVO basePO = (BaseVO)value;
                json.element(prefix+fieldName, basePO.toJsonObject());
            }
            // 其他类型
            else {
                json.element(prefix+fieldName, decode(String.valueOf(value)));
            }

        }
        return json;

    }
}
