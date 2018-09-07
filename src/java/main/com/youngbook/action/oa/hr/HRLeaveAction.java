package com.youngbook.action.oa.hr;

/**
 * Created by haihong on 2015/6/26.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.hr.HRLeavePO;
import com.youngbook.entity.vo.oa.hr.HRLeaveVO;
import com.youngbook.service.oa.hr.HRLeaveService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个休假类，继承BaseAction累
 *
 * @author Codemaker
 */

public class HRLeaveAction extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private HRLeavePO hrleave = new HRLeavePO();
    private HRLeaveVO hrleaveVO = new HRLeaveVO();
    private HRLeaveService service = new HRLeaveService();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取申请请假休假数据列表
     *
     * @return
     * @throws Exception
     */
    public String listHRLeave() throws Exception {


        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

       
        conditions = MySQLDao.getQueryDatetimeParameters(request, hrleaveVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, hrleaveVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

       Pager pager = service.listHRLeave(hrleaveVO,getLoginUser(),conditions);

     //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 我参与过的请假休假数据
     * @return
     * @throws Exception
     */
    public String listHRLeaveParticipant() throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();
       
        conditions = MySQLDao.getQueryDatetimeParameters(request, hrleaveVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, hrleaveVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

       Pager pager = service.listHRLeaveParticipant(hrleaveVO,getLoginUser(),conditions);

        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 我等待我审批的请假休假数据
     * @return
     * @throws Exception
     */
    public String listHRLeavewait() throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

       
        conditions = MySQLDao.getQueryDatetimeParameters(request, hrleaveVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, hrleaveVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

       Pager pager = service.listHRLeavewait(hrleaveVO,getLoginUser(),conditions);

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
        //添加并更新数据
        int count = service.insertOrUpdate(hrleave,hrleaveVO, getLoginUser(), getConnection());

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
        //根据条件获获取信息
        hrleave = service.loadHRLeavePO(hrleave.getId());

        getResult().setReturnValue(hrleave.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        //删除数据
        service.delete(hrleave, getLoginUser(), getConnection());

        return SUCCESS;
    }

    public HRLeavePO getHrleave() {
        return hrleave;
    }

    public void setHrleave(HRLeavePO hrleave) {
        this.hrleave = hrleave;
    }

    public HRLeaveVO getHrleaveVO() {
        return hrleaveVO;
    }

    public void setHrleaveVO(HRLeaveVO hrleaveVO) {
        this.hrleaveVO = hrleaveVO;
    }

    public HRLeaveService getService() {
        return service;
    }

    public void setService(HRLeaveService service) {
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