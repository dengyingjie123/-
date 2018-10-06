package com.youngbook.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2015/11/2.
 */
public class KVObjects {
    private List<KVObject> objects = null;

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
}
