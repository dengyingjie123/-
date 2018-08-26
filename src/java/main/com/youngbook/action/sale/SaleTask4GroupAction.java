package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.sale.SaleTask4GroupPO;
import com.youngbook.entity.vo.Sale.SaleTask4GroupVO;
import com.youngbook.entity.vo.Sale.SalesManVO;
import com.youngbook.service.sale.SaleTask4GroupService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2015/5/22.
 *销售产品分配小组Action
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */
public class SaleTask4GroupAction extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private SaleTask4GroupPO saleTask4Group = new SaleTask4GroupPO();
    private SaleTask4GroupVO saleTask4GroupVO = new SaleTask4GroupVO();
    private SaleTask4GroupService service = new SaleTask4GroupService();

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
        conditions = MySQLDao.getQueryDatetimeParameters(request, saleTask4GroupVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, saleTask4GroupVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        //获取分页对象
       Pager pager= service.list(saleTask4GroupVO,conditions);
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
        int count = service.insertOrUpdate(saleTask4Group, getLoginUser(), getConnection());
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

        saleTask4Group = service.loadSaleTask4GroupPO(saleTask4Group.getId());

        getResult().setReturnValue(saleTask4Group.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        service.delete(saleTask4Group, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 获取本小组为分配该项目的人员
     *
     * @return
     * @throws Exception
     */
    public String getNotAllocationSaleMans() throws Exception {
        HttpServletRequest request = getRequest();
        SalesManVO salemanVO = new SalesManVO();
        saleTask4Group.setState(Config.STATE_CURRENT);
        saleTask4Group = MySQLDao.load(saleTask4Group, SaleTask4GroupPO.class);

        StringBuffer SQL= new StringBuffer();
        SQL.append(" id  in (select SalemanId from crm_saleman_salemangroup where  saleManGroupId = '"+Database.encodeSQL(saleTask4Group.getSaleGroupId())+"')");
        SQL.append(") temp WHERE temp.id NOT IN (SELECT SalemanId FROM sale_saletask4saleman ss  WHERE");
        SQL.append("   ss.ProductionId='"+Database.encodeSQL(saleTask4Group.getProductionId())+"' ANd ss.SaleGroupId='"+Database.encodeSQL(saleTask4Group.getSaleGroupId())+"' AND state = 0 )");
         Pager pager = service.SaleManlist(salemanVO, SQL.toString(), null);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取以分配小组的项目名称
     * @return
     * @throws Exception
     */
    public String getProductName() throws  Exception{
        getResult().setReturnValue(service.getProductName());
        return SUCCESS;
    }
    /**
     * 获取所有小组名称
     * @return
     * @throws Exception
     */
    public String getSalemGroupName() throws  Exception{
       getResult().setReturnValue(service.getSaleManGroupName());
        return SUCCESS;
    }



    public SaleTask4GroupPO getSaleTask4Group() {
        return saleTask4Group;
    }

    public void setSaleTask4Group(SaleTask4GroupPO saleTask4Group) {
        this.saleTask4Group = saleTask4Group;
    }

    public SaleTask4GroupVO getSaleTask4GroupVO() {
        return saleTask4GroupVO;
    }

    public void setSaleTask4GroupVO(SaleTask4GroupVO saleTask4GroupVO) {
        this.saleTask4GroupVO = saleTask4GroupVO;
    }

    public SaleTask4GroupService getService() {
        return service;
    }

    public void setService(SaleTask4GroupService service) {
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
