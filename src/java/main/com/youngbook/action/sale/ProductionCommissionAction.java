package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import com.youngbook.entity.po.sale.ProductionCommissionPO;
import com.youngbook.service.sale.ProductionCommissionService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * HOPEWEALTH-1293
 * 返佣率action类
 */
public class ProductionCommissionAction extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private ProductionCommissionPO productionCommission = new ProductionCommissionPO();
    @Autowired
    private ProductionCommissionService productionCommissionService;

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取数据列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();

        Pager pager=productionCommissionService.list(productionCommission,conditions,request,getConnection());

        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 添加过更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = productionCommissionService.insertOrUpdate(productionCommission, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {

        productionCommission = productionCommissionService.loadProductionCommissionPO(productionCommission.getId(),getConnection());

        getResult().setReturnValue(productionCommission.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {

        productionCommissionService.delete(productionCommission, getLoginUser(), getConnection());

        return SUCCESS;
    }


    public ProductionCommissionPO getProductionCommission() {
        return productionCommission;
    }

    public void setProductionCommission(ProductionCommissionPO productionCommission) {
        this.productionCommission = productionCommission;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
