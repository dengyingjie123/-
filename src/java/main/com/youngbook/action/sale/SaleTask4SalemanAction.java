package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.sale.SaleTask4SalemanPO;
import com.youngbook.entity.vo.Sale.SaleTask4SalemanVO;
import com.youngbook.service.sale.SaleTask4SalemanService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/22.
 *产品销售人员分配
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class SaleTask4SalemanAction extends BaseAction{
    //实例化PO、VO、Servlet 类对象
    private SaleTask4SalemanPO saleTask4Saleman = new SaleTask4SalemanPO();
    private SaleTask4SalemanVO saleTask4SalemanVO = new SaleTask4SalemanVO();
    private SaleTask4SalemanService service = new SaleTask4SalemanService();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取数据列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {


        HttpServletRequest request = getRequest();

        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions = MySQLDao.getQueryDatetimeParameters(request, saleTask4Saleman.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, saleTask4Saleman.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }
         Pager pager= service.list(saleTask4SalemanVO,conditions);

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
        int count = service.insertOrUpdate(saleTask4Saleman, getLoginUser(), getConnection());
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

        saleTask4Saleman = service.loadSaleTask4SalemanPO(saleTask4Saleman.getId());

        getResult().setReturnValue(saleTask4Saleman.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {

        service.delete(saleTask4Saleman, getLoginUser(), getConnection());

        return SUCCESS;
    }


    public SaleTask4SalemanPO getSaleTask4Saleman() {
        return saleTask4Saleman;
    }

    public void setSaleTask4Saleman(SaleTask4SalemanPO saleTask4Saleman) {
        this.saleTask4Saleman = saleTask4Saleman;
    }

    public SaleTask4SalemanVO getSaleTask4SalemanVO() {
        return saleTask4SalemanVO;
    }

    public void setSaleTask4SalemanVO(SaleTask4SalemanVO saleTask4SalemanVO) {
        this.saleTask4SalemanVO = saleTask4SalemanVO;
    }

    public SaleTask4SalemanService getService() {
        return service;
    }

    public void setService(SaleTask4SalemanService service) {
        this.service = service;
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
