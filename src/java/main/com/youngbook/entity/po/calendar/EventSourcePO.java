package com.youngbook.entity.po.calendar;

import com.youngbook.entity.po.BasePO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EventSourcePO extends BasePO {
    private List<EventPO> events = new ArrayList<EventPO>();
    private String color = "";
    private String textColor = "black";

    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();

        JSONArray eventsArray = new JSONArray();
        for (int i = 0; events != null && i < events.size(); i++) {
            eventsArray.add(events.get(i).toJsonObject());
        }

        json.element("color", color);
        json.element("textColor", textColor);

        return json;
    }

    public List<EventPO> getEvents() {
        return events;
    }

    public void setEvents(List<EventPO> events) {
        this.events = events;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
