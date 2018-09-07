package com.youngbook.action.report;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.vo.report.TimeStatisticsVO;
import com.youngbook.service.report.*;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class TimeStatisticsAction extends BaseAction {

    private TimeStatisticsVO timeStatistics = new TimeStatisticsVO();
    private TimeStatisticsService service = new TimeStatisticsService();

    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions=new ArrayList<KVObject>();
        String saleMan = getRequest().getParameter("SaleMan");
        List<Object> objs = service.list(timeStatistics, conditions, request,saleMan);
        String start=(String)objs.get(0);
        String stop=(String)objs.get(1);
        Pager pager=(Pager)objs.get(2);
        JSONObject json = new JSONObject();
        json.element("pager",pager.toJsonObject());
        json.element("start",start);
        json.element("stop",stop);
        getResult().setReturnValue(json);
        return SUCCESS;
    }

    public TimeStatisticsVO getTimeStatistics() {
        return timeStatistics;
    }
    public void setTimeStatistics(TimeStatisticsVO timeStatistics) {
        this.timeStatistics = timeStatistics;
    }

    public TimeStatisticsService getService() {
        return service;
    }
    public void setService(TimeStatisticsService service) {
        this.service = service;
    }

}

