package com.youngbook.action.report;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.vo.report.ProductionStatisticsVO;
import com.youngbook.service.report.ProductionStatisticsService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ProductionStatisticsAction extends BaseAction {

    private ProductionStatisticsVO proStatistics = new ProductionStatisticsVO();
    private ProductionStatisticsService service = new ProductionStatisticsService();

    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions=new ArrayList<KVObject>();
        String saleMan = getRequest().getParameter("SaleMan");
        Pager pager = service.list(proStatistics, conditions, request,saleMan);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public ProductionStatisticsVO getProStatistics() {
        return proStatistics;
    }
    public void setProStatistics(ProductionStatisticsVO proStatistics) {
        this.proStatistics = proStatistics;
    }

    public ProductionStatisticsService getService() {
        return service;
    }
    public void setService(ProductionStatisticsService service) {
        this.service = service;
    }

}

