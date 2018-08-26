package com.youngbook.entity.po.calendar;

import com.youngbook.annotation.Id;
import com.youngbook.entity.po.BasePO;
import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 11/21/14
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventPO extends BasePO {
    @Id
    private String id = "";
    private String title = "";
    private String start = "";
    private String end = "";
    private String url = "";

    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.element("id", id);
        json.element("title", title);
        json.element("start", start);
        json.element("end", end);
        json.element("url", url);
        return json;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
