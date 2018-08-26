package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.wf.Database;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.ProductionHomePO;
import com.youngbook.entity.vo.production.ProductionHomeVO;
import com.youngbook.service.production.ProductionHomeService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jepson-pc on 2015/8/17.
 */
public class ProductionHomeAction extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private ProductionHomePO productionHome = new ProductionHomePO();
    private ProductionHomeVO productionHomeVO = new ProductionHomeVO ();


    @Autowired
    ProductionHomeService productionHomeService;

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 修改人:姚章鹏
     * 时间：2015年8月17日17:54:01
     * 获取数据列表
     *
     * @return
     * @throws Exception
     *
     */
    public String list() throws Exception {

        productionHomeVO = HttpUtils.getInstanceFromRequest(getRequest(), "productionHomeVO", ProductionHomeVO.class);

        HttpServletRequest request = getRequest();
        //设置排序
        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions = MySQLDao.getQueryDatetimeParameters(request, productionHomeVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, productionHomeVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }
        Pager pager = productionHomeService.list(productionHomeVO, conditions, request);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * * 修改人:姚章鹏
     * 时间：2015年8月17日17:54:01
     * 添加过更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        productionHome = HttpUtils.getInstanceFromRequest(getRequest(), "productionHome", ProductionHomePO.class);

        int count = productionHomeService.insertOrUpdate(productionHome, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * * 修改人:姚章鹏
     * 时间：2015年8月17日17:54:01
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {

        productionHome = HttpUtils.getInstanceFromRequest(getRequest(), "productionHome", ProductionHomePO.class);

        productionHome = productionHomeService.loadProductionHomePO(productionHome.getId());

        getResult().setReturnValue(productionHome.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * * 修改人:姚章鹏
     * 时间：2015年8月17日17:54:01
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {

        productionHome = HttpUtils.getInstanceFromRequest(getRequest(), "productionHome", ProductionHomePO.class);
        productionHomeService.delete(productionHome, getLoginUser(), getConnection());

        return SUCCESS;
    }

    public String productionHomeList() throws Exception {
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("    id,productionName text ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_productionhome ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1 ");
        sqlDB.append(" AND State = 0 ");
        List<ProductionHomeVO> list= MySQLDao.query(sqlDB.toString(), ProductionHomeVO.class, null);
        JSONArray array=new JSONArray();
        for(ProductionHomeVO p:list){
            array.add(p.toJsonObject4Form());
        }
        getResult().setReturnValue(array);
        return SUCCESS;
    }


    public ProductionHomePO getProductionHome() {
        return productionHome;
    }

    public void setProductionHome(ProductionHomePO productionHome) {
        this.productionHome = productionHome;
    }

    public ProductionHomeService getProductionHomeService() {
        return productionHomeService;
    }

    public void setProductionHomeService(ProductionHomeService productionHomeService) {
        this.productionHomeService = productionHomeService;
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

    public ProductionHomeVO getProductionHomeVO() {
        return productionHomeVO;
    }

    public void setProductionHomeVO(ProductionHomeVO productionHomeVO) {
        this.productionHomeVO = productionHomeVO;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
