package com.youngbook.action.oa.business;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.business.BusinessTripApplicationPO;
import com.youngbook.entity.vo.business.BusinessTripApplicationVO;
import com.youngbook.service.oa.business.BusinessTripApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/14.
 */
public class BusinessTripApplicationAction extends BaseAction {

    /*创建请假休假PO,vo，service 实体类*/
    private BusinessTripApplicationPO businessTripApplication = new BusinessTripApplicationPO();
    private BusinessTripApplicationVO businessTripApplicationVO = new BusinessTripApplicationVO();
    private BusinessTripApplicationService service = new BusinessTripApplicationService();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取数据列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //设置范围传查询条件
        conditions = MySQLDao.getQueryDatetimeParameters(request, businessTripApplicationVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, businessTripApplicationVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        //获取分页对象
        Pager pager = service.listBusinessTrip(businessTripApplicationVO, getLoginUser(),conditions, request);

        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /*修改：周海鸿
    * 时间：2015-7-15
    * 内容：添加获取列表数据*/
    /*等待审批列表*/
     public String Waitlist() throws Exception {

         //获取请求对象
         HttpServletRequest request = getRequest ();
         //创建查询条件对象
         List<KVObject> conditions = new ArrayList<KVObject> ();

         
         //设置范围传查询条件
        conditions = MySQLDao.getQueryDatetimeParameters(request, businessTripApplicationVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, businessTripApplicationVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }


        //获取分页对象
        Pager pager = service.listBusinessTripWait(businessTripApplicationVO, getLoginUser(),conditions, request);

        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 已完成列表
     *
     * @return
     * @throws Exception
     */
    public String Participantlist() throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //设置范围传查询条件
        conditions = MySQLDao.getQueryDatetimeParameters(request, businessTripApplicationVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, businessTripApplicationVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }


        //获取分页对象
        Pager pager = service.listBusinessTripParticipant(businessTripApplicationVO, getLoginUser(),conditions, request);

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
        int count = service.insertOrUpdate(businessTripApplication,businessTripApplicationVO, getLoginUser(), getConnection());
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

        businessTripApplication = service.loadBusinessTripApplicationPO(businessTripApplication.getId());

        getResult().setReturnValue(businessTripApplication.toJsonObject4Form());

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
        service.delete(businessTripApplication, getLoginUser(), getConnection());

        return SUCCESS;
    }

    public BusinessTripApplicationVO getBusinessTripApplicationVO() {
        return businessTripApplicationVO;
    }

    public void setBusinessTripApplicationVO(BusinessTripApplicationVO businessTripApplicationVO) {
        this.businessTripApplicationVO = businessTripApplicationVO;
    }

    public BusinessTripApplicationPO getBusinessTripApplication() {
        return businessTripApplication;
    }

    public void setBusinessTripApplication(BusinessTripApplicationPO businessTripApplication) {
        this.businessTripApplication = businessTripApplication;
    }

    public BusinessTripApplicationService getService() {
        return service;
    }

    public void setService(BusinessTripApplicationService service) {
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
