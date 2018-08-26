package com.youngbook.action.oa.Information;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.Information.InformationSubmittedPO;
import com.youngbook.entity.vo.oa.Information.InformationSubmittedVO;
import com.youngbook.service.oa.Information.InformationSubmittedService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class InformationSubmittedAction extends BaseAction {

    private InformationSubmittedPO informationSubmitted = new InformationSubmittedPO ();
    private InformationSubmittedVO informationSubmittedVO = new InformationSubmittedVO ();
    private InformationSubmittedService service = new InformationSubmittedService ();

    private String sort = new String ();//需要排序的列名
    private String order = new String ();

    /*修改人：周海鸿
    * 时间：2015-7-8
    * 内容 :修改请求处理调用的业务方法*/
    public String list () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //设置范围查询
        conditions = MySQLDao.getQueryDatetimeParameters (request, informationSubmittedVO.getClass (), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters (request, informationSubmittedVO.getClass (), conditions);
        //设置排序
        if ( !getSort ().equals ("") ) {
            conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, getSort () + " " + getOrder ()));
        }

        //获取分页对象
        Pager pager = service.list (informationSubmittedVO, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /*
        获取等待审核
    修改人：周海鸿
    * 时间：2015-7-8
    * 内容 :修改请求处理调用的业务方法*/
    public String Waitlist () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //设置范围查询
        conditions = MySQLDao.getQueryDatetimeParameters (request, informationSubmittedVO.getClass (), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters (request, informationSubmittedVO.getClass (), conditions);
        //设置排序
        if ( !getSort ().equals ("") ) {
            conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, getSort () + " " + getOrder ()));
        }

        //获取分页对象
        Pager pager = service.waitlist (informationSubmittedVO, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());
        return SUCCESS;
    }

    /*
    获取参与列表
    修改人：周海鸿
    * 时间：2015-7-8
    * 内容 :修改请求处理调用的参与业务方法*/
    public String Participantlist () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //设置范围查询
        conditions = MySQLDao.getQueryDatetimeParameters (request, informationSubmittedVO.getClass (), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters (request, informationSubmittedVO.getClass (), conditions);
        //设置排序
        if ( !getSort ().equals ("") ) {
            conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, getSort () + " " + getOrder ()));
        }

        //获取分页对象
        Pager pager = service.Participantlist (informationSubmittedVO, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 添加更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate () throws Exception {
       //添加更新数据
        int count = service.insertOrUpdate (informationSubmitted, informationSubmittedVO, getLoginUser (), getConnection ());

        if ( count != 1 ) {
            getResult ().setMessage ("操作失败");
        }

        return SUCCESS;
    }

    /**
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String load () throws Exception {
        //根据条件获取数据
        informationSubmitted = service.loadInformationSubmittedPO (informationSubmitted.getId ());

        getResult ().setReturnValue (informationSubmitted.toJsonObject4Form ());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete () throws Exception {
        //删除书
        service.delete (informationSubmitted, getLoginUser (), getConnection ());

        return SUCCESS;
    }

    /**
     * 获取部门列表
     *
     * @param
     * @return
     */
    public String getUsers () throws Exception {


        getResult ().setReturnValue (service.getUsers ());

        return SUCCESS;
    }

    public InformationSubmittedPO getInformationSubmitted () {
        return informationSubmitted;
    }

    public void setInformationSubmitted (InformationSubmittedPO informationSubmitted) {
        this.informationSubmitted = informationSubmitted;
    }

    public InformationSubmittedService getService () {
        return service;
    }

    public void setService (InformationSubmittedService service) {
        this.service = service;
    }

    public String getSort () {
        return sort;
    }

    public void setSort (String sort) {
        this.sort = sort;
    }

    public String getOrder () {
        return order;
    }

    public void setOrder (String order) {
        this.order = order;
    }

    public InformationSubmittedVO getInformationSubmittedVO () {
        return informationSubmittedVO;
    }

    public void setInformationSubmittedVO (InformationSubmittedVO informationSubmittedVO) {
        this.informationSubmittedVO = informationSubmittedVO;
    }

}
