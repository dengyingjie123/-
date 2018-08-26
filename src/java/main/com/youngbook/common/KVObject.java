package com.youngbook.common;

import com.youngbook.common.utils.StringUtils;
import net.sf.json.JSONObject;

/**
 * User: Lee
 * Date: 14-4-7
 */
public class KVObject{
    private int index;
    private Object key;
    private Object value;

    public KVObject(){

    }


    public KVObject(Object key, Object value){
        this.key = key;
        this.value = value;
    }

    public KVObject(int index, Object key, Object value){
        this.index = index;
        this.key = key;
        this.value = value;
    }

    public KVObject(int index, Object value){
        this.index = index;
        this.value = value;
    }

    public String getKeyStringValue() {
        return StringUtils.getValue(key);
    }

    public String getValueStringValue() {
        return StringUtils.getValue(value);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
