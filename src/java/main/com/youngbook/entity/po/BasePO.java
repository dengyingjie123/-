package com.youngbook.entity.po;

import com.youngbook.annotation.Table;
import com.youngbook.common.IJsonable;
import com.youngbook.common.IXmlable;
import com.youngbook.dao.JSONDao;
import net.sf.json.JSONObject;
import org.dom4j.Element;

import java.util.List;

/**
 * User: Lee
 * Date: 14-4-5
 */
public class BasePO implements IJsonable, IXmlable {

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
            String prefix = new String();
            Table tableAnnotation = this.getClass().getAnnotation(Table.class);

            if (tableAnnotation != null) {
                prefix = tableAnnotation.jsonPrefix();
            }
            return JSONDao.get(this,prefix);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject toJsonObject4Form(String prefix){
        try {
            return JSONDao.get(this,prefix);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject toJsonObject() {
        try {
            String prefix = new String();
            //System.out.println("BasePO.toJsonObject(): " + prefix);

            return JSONDao.get(this, prefix);
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


    @Override
    public String toXmlString() throws Exception {
        return null;
    }

    @Override
    public List<Element> toXmlNodes() throws Exception {
        return null;
    }

    @Override
    public String toXmlString(String encoding) throws Exception {
        return null;
    }

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }
}
