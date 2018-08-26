package com.youngbook.action.oa.finance;

// 构造 FinancePayWFAAction 开始 //////////////////////////////////////////////////////

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.finance.FinancePayWFAPO;
import com.youngbook.entity.vo.oa.finance.FinancePayWFAVO;
import com.youngbook.service.oa.finance.FinancePayWFAService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个FinancePayWFAAction类，继承BaseAction累
 *
 * @author Codemaker
 */

public class FinancePayWFAAction extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private FinancePayWFAPO financePayWFA = new FinancePayWFAPO();
    private FinancePayWFAVO financePayWFAVO = new FinancePayWFAVO();
    private FinancePayWFAService service = new FinancePayWFAService();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     *获取申请列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        HttpServletRequest request = getRequest();

         //获取数据查询条件
        List<KVObject> conditions = new ArrayList<KVObject>();

        conditions = MySQLDao.getQueryDatetimeParameters(request, financePayWFAVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, financePayWFAVO.getClass(), conditions);

        int workflowId = Integer.parseInt(HttpUtils.getParameter(getRequest(), "financePayWFAVO.workflowId"));

        //获取分页对象
        Pager pager = service.list(financePayWFAVO, workflowId, getLoginUser(), conditions, request, getConnection());
        //返回数据
        getResult().setReturnValue(pager.toJsonObject ());

        return SUCCESS;
    }
    /**
     * 获取等待审核列表
     *
     * @return
     * @throws Exception
     */
    public String waitList() throws Exception {

        HttpServletRequest request = getRequest();

         //获取数据查询条件
        List<KVObject> conditions = new ArrayList<KVObject>();
        

        conditions = MySQLDao.getQueryDatetimeParameters(request, financePayWFAVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, financePayWFAVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        int workflowId = Integer.parseInt(HttpUtils.getParameter(getRequest(), "financePayWFAVO.workflowId"));

        //获取分页对象
        Pager pager =service.waitList(financePayWFAVO, workflowId, getLoginUser(), conditions, request, getConnection());

        //返回数据
        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }
    /**
     * 获取参与列表
     *
     * @return
     * @throws Exception
     */
    public String participantList() throws Exception {

        HttpServletRequest request = getRequest();


         //获取数据查询条件
        List<KVObject> conditions = new ArrayList<KVObject>();

        
        conditions = MySQLDao.getQueryDatetimeParameters(request, financePayWFAVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, financePayWFAVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        int workflowId = Integer.parseInt(HttpUtils.getParameter(getRequest(), "financePayWFAVO.workflowId"));
        //获取分页对象
        Pager pager =service.participantList(financePayWFAVO, workflowId, getLoginUser(), conditions, request, getConnection());
        //返回数据
        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    public String getPrintDate() throws  Exception{
        service.getPrintDate(financePayWFA.getId());
        return SUCCESS;
    }

    /**
     * 添加过更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        int workflowId = Integer.parseInt(HttpUtils.getParameter(getRequest(), "financePayWFAVO.workflowId"));

        int count = service.insertOrUpdate(financePayWFA, financePayWFAVO, workflowId, getLoginUser(), getConnection());
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

        financePayWFA = service.loadFinancePayWFAPO(financePayWFA.getId());

        getResult().setReturnValue(financePayWFA.toJsonObject4Form ());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {

        service.delete(financePayWFA, getLoginUser(), getConnection());

        return SUCCESS;
    }


    public FinancePayWFAPO getFinancePayWFA() {
        return financePayWFA;
    }

    public void setFinancePayWFA(FinancePayWFAPO financePayWFA) {
        this.financePayWFA = financePayWFA;
    }

    public FinancePayWFAService getService() {
        return service;
    }

    public void setService(FinancePayWFAService service) {
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

    public FinancePayWFAVO getFinancePayWFAVO() {
        return financePayWFAVO;
    }

    public void setFinancePayWFAVO(FinancePayWFAVO financePayWFAVO) {
        this.financePayWFAVO = financePayWFAVO;
    }
}
