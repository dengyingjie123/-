package com.youngbook.action.report;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.vo.report.DetailStatisticsVO;
import com.youngbook.service.report.DetailStatisticsService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DetailStatisticsAction extends BaseAction {

    private DetailStatisticsVO detail = new DetailStatisticsVO();
    private DetailStatisticsService service = new DetailStatisticsService();

    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions=new ArrayList<KVObject>();
        String saleMan = getRequest().getParameter("SaleMan");
        List<Object> obj= service.list(detail, conditions, request,saleMan);
        Pager pager=(Pager)obj.get(0);
        Pager pager1=(Pager)obj.get(1);
        JSONObject json = new JSONObject();
        json.element("pager",pager.toJsonObject());
        json.element("pager1",pager1.toJsonObject());
        getResult().setReturnValue(json);
        return SUCCESS;
    }

    public DetailStatisticsVO getDetail() {
        return detail;
    }
    public void setDetail(DetailStatisticsVO detail) {
        this.detail = detail;
    }

    public DetailStatisticsService getService() {
        return service;
    }
    public void setService(DetailStatisticsService service) {
        this.service = service;
    }

}

