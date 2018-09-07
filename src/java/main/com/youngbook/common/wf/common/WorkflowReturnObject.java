package com.youngbook.common.wf.common;

import com.youngbook.common.wf.admin.RouteList;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 15-3-29
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 */
public class WorkflowReturnObject {
    private String result = "";

    private String message = "";

    private RouteList routeList = new RouteList();

    public RouteList getRouteList() {
        return routeList;
    }

    public void setRouteList(RouteList routeList) {
        this.routeList = routeList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
