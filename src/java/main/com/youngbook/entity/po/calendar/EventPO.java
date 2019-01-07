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
    private String id = "";// 可选，事件唯一标识，重复的事件具有相同的id
    private String title = "";// 必须，事件在日历上显示的title
    private String start = "";// 必须，事件的开始时间。
    private String end = "";// 可选，结束时间。
    private String url = "";// 可选，当指定后，事件被点击将打开对应url。
    private String color = "";// 背景和边框颜色。
    private String backgroundColor = "";// 背景颜色。
    private String borderColor = "";// 边框颜色。
    private String textColor = "";// 文本颜色。
    private String className = "";// 指定事件的样式。
    private String editable = "";// 事件是否可编辑，可编辑是指可以移动, 改变大小等。


    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.element("id", id);
        json.element("title", title);
        json.element("start", start);
        json.element("end", end);
        json.element("url", url);
        json.element("color", color);
        json.element("backgroundColor", backgroundColor);
        json.element("borderColor", borderColor);
        json.element("textColor", textColor);
        json.element("className", className);
        json.element("editable", editable);
        return json;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
