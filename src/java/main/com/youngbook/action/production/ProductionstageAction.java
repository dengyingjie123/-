package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.ProductionstagePO;
import com.youngbook.service.production.ProductionstageService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ProductionstageAction extends BaseAction {
    private ProductionstagePO productionstage = new ProductionstagePO();
    private ProductionstageService service = new ProductionstageService();

    //添加，修改
    public String insertOrUpdate() throws Exception {
        service.insertOrUpdate(productionstage, getLoginUser(), getConnection());
        return SUCCESS;
    }

    // 删除
    public String delete() throws Exception {
        service.delete(productionstage, getLoginUser(), getConnection());
        return SUCCESS;
    }

    //读取
    public String load() throws Exception {
        productionstage.setState(Config.STATE_CURRENT);
        productionstage = MySQLDao.load(productionstage, ProductionstagePO.class);
        getResult().setReturnValue(productionstage.toJsonObject4Form());
        return SUCCESS;
    }

    //列出数据
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        conditions.add(new KVObject("productionId", " in ('"+productionstage.getProductionId()+"')"));
        Pager pager = service.list(productionstage, conditions, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public ProductionstagePO getProductionstage() {
        return productionstage;
    }
    public void setProductionstage(ProductionstagePO productionstage) {
        this.productionstage = productionstage;
    }

    public ProductionstageService getService() {
        return service;
    }
    public void setService(ProductionstageService service) {
        this.service = service;
    }

}
