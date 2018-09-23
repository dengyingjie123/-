package com.youngbook.common;

import com.youngbook.common.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2015/11/2.
 */
public class KVObjects {
    private List<KVObject> objects = null;

    public KVObjects() {

    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; objects != null && i < objects.size(); i++) {
            KVObject kvObject = objects.get(i);

            String key = kvObject.getKeyStringValue();
            String value = kvObject.getValueStringValue();

            jsonObject.element(key, value);

        }

        return jsonObject;
    }


    public void removeByKey(Object key) {

        while (containsKey(key)) {
            int index = getIndex(key);
            objects.remove(index);
        }
    }

    public int getIndex(Object key) {

        for (int i = 0; objects != null && i < objects.size(); i++) {
            KVObject o = objects.get(i);

            if (o.getKey().equals(key)) {
                return i;
            }
        }

        return -1;
    }

    public boolean containsKey(Object key) {

        for (int i = 0; objects != null && i < objects.size(); i++) {
            KVObject o = objects.get(i);

            if (o.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    public KVObjects addItem(Object key, Object value) {
        KVObject o = new KVObject(key, value);
        add(o);
        return this;
    }

    public Object getItem(Object key) {

        for (int i = 0; objects != null && i < objects.size(); i++) {
            KVObject o = objects.get(i);

            if (o.getKey().equals(key)) {
                return o.getValue();
            }
        }

        return null;
    }

    public String getItemString(Object key) {

        for (int i = 0; objects != null && i < objects.size(); i++) {
            KVObject o = objects.get(i);

            if (o.getKey().equals(key)) {
                return o.getValue().toString();
            }
        }

        return null;
    }

    public void add(KVObject o) {
        if (objects == null) {
            objects =  new ArrayList<KVObject>();
        }

        objects.add(o);
    }

    public KVObject getByIndex(int index) {
        for (KVObject o : objects) {
            if (o.getIndex() == index) {
                return o;
            }
        }
        return null;
    }

    public KVObject get(int i) {
        return  objects.get(i);
    }



    public int size() {
        if (objects == null) {
            return 0;
        }
        return objects.size();
    }

    /**
     * 判断是否包含为空的值，任意为空，则返回真
     * @param keys
     * @return
     */
    public boolean isContainsAnyEmptyValue(String ... keys) {
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (isContainsEmptyValue(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检测key对应的value是否为空
     * @param key
     * @return
     */
    public boolean isContainsEmptyValue(String key) {
        if (StringUtils.isEmpty(key)) {
            return true;
        }

        for (int i = 0; i < size(); i++) {
            KVObject kvObject = get(i);
            if (kvObject.getKeyStringValue().equals(key) && !StringUtils.isEmpty(kvObject.getValueStringValue())) {
                return false;
            }
        }

        return true;
    }
}
