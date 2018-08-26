package com.youngbook.action.oa.assetFixation;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.assetFixation.AssetApplicationPO;
import com.youngbook.entity.vo.oa.assetFixation.AssetApplicationVO;
import com.youngbook.service.oa.assetFixation.AssetApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 固定资产申请ACtion类
 */
public class AssetApplicationAction extends BaseAction {

    //固定资产申请数据实体类
    private AssetApplicationPO assetApplication = new AssetApplicationPO ();
    private AssetApplicationVO assetApplicationVO = new AssetApplicationVO ();
    //固定资产申请逻辑操作类
    private AssetApplicationService service = new AssetApplicationService ();

    private String sort = new String ();//需要排序的列名
    private String order = new String ();

    /**
     * 获取申请数据列表
     *
     * @return
     * @throws Exception
     */
    public String list () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //设置查询条件
        conditions = MySQLDao.getQueryDatetimeParameters (request, assetApplicationVO.getClass (), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters (request, assetApplicationVO.getClass (), conditions);

        //设置排序
        if ( !getSort ().equals ("") ) {
            conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, getSort () + " " + getOrder ()));
        }

        //获取列表数据
        Pager pager = service.list (assetApplicationVO, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 创建请求对象，创建条件查询对象
     * 设置范围查询条件
     * 获取等待审核数据列表
     *
     * @return
     * @throws Exception
     */
    public String Waitlist () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //设置范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters (request, assetApplicationVO.getClass (), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters (request, assetApplicationVO.getClass (), conditions);
        //设置排序
        if ( !getSort ().equals ("") ) {
            conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, getSort () + " " + getOrder ()));
        }

        //获取列表数据
        Pager pager = service.Waitlist (assetApplicationVO, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 创建请求对象，创建条件查询对象
     * 设置范围查询条件
     * 获取等待审核数据列表
     * 获取已审核数据列表
     *
     * @return
     * @throws Exception
     */
    public String Participantlist () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters (request, assetApplicationVO.getClass (), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters (request, assetApplicationVO.getClass (), conditions);
        //设置排序
        if ( !getSort ().equals ("") ) {
            conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, getSort () + " " + getOrder ()));
        }

        //获取列表数据
        Pager pager = service.Participantlist (assetApplicationVO, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 调用serviece  添加或更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate () throws Exception {

        //执行业务操作添加并跟新数据
        int count = service.insertOrUpdate (assetApplication, assetApplicationVO, getLoginUser (), getConnection ());

        //返回影响行数是否不等于一
        if ( count != 1 ) {
            getResult ().setMessage ("操作失败");
        }
        return SUCCESS;
    }


    /**
     * 设置查询id
     *
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String load () throws Exception {
        //设置查询id
        assetApplication = service.loadAssetApplicationPO (assetApplication.getId ());

        //查询返回数据
        getResult ().setReturnValue (assetApplication.toJsonObject4Form ());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete () throws Exception {
        //调用service 删除数据
        service.delete (assetApplication, getLoginUser (), getConnection ());

        return SUCCESS;
    }


    public AssetApplicationPO getAssetApplication () {
        return assetApplication;
    }

    public void setAssetApplication (AssetApplicationPO assetApplication) {
        this.assetApplication = assetApplication;
    }

    public AssetApplicationService getService () {
        return service;
    }

    public void setService (AssetApplicationService service) {
        this.service = service;
    }

    public AssetApplicationVO getAssetApplicationVO () {
        return assetApplicationVO;
    }

    public void setAssetApplicationVO (AssetApplicationVO assetApplicationVO) {
        this.assetApplicationVO = assetApplicationVO;
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

}