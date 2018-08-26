package com.youngbook.entity.vo;

import com.youngbook.common.IJsonable;
import com.youngbook.dao.JSONDao;
import net.sf.json.JSONObject;

/**
 * User: Lee
 * Date: 14-5-21
 */
public class BaseVO implements IJsonable {
    private String _state = "open";

    @Override
    public JSONObject toJsonObject4Treegrid() {

        JSONObject json = this.toJsonObject();

        json.element("state", this.get_state());

        return json;
    }

    @Override
    public JSONObject toJsonObject4Form(){
        try {
            return JSONDao.get(this, "");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject toJsonObject4Form(String profix){
        try {
            return JSONDao.get(this, profix);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject toJsonObject() {
        try {
            return JSONDao.get(this, "");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject toJsonObject4Tree() {
        try {
            return JSONDao.get(this, "");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }
}
