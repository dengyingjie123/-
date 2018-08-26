package com.youngbook.action.oa.administration;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.oa.administration.SealUsageItemPO;
import com.youngbook.service.oa.administration.SealUsageItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个SealUsageItemAction类，继承BaseAction累
 *
 * @author Codemaker
 */

public class SealUsageItemAction extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private SealUsageItemPO sealUsageItem = new SealUsageItemPO ();
    private SealUsageItemService service = new SealUsageItemService ();

    private String sort = new String ();//需要排序的列名
    private String order = new String ();

    /**
     * 判断是否有用着数据的id
     * 设置查询条件
     * 调用service 的list 方法获取列表数据
     * 海鸿
     * 2015-8-18
     *获取用着类型列表数据
     * @return
     * @throws Exception
     */
    public String list () throws Exception {
        if ( StringUtils.isEmpty (sealUsageItem.getApplicationId ()) ) {
            getResult ().setException (new Exception ("用章申请数据出错 请联系管理员"));
            throw new Exception ("用章申请数据出错 请联系管理员");
        }


        //获取请求对象
        HttpServletRequest request = getRequest ();

        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        //设置排序
        if ( !getSort ().equals ("") ) {
            conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, getSort () + " " + getOrder ()));
        }

        //获取用着类型列表数据
        Pager pager = service.list (sealUsageItem,sealUsageItem.getApplicationId (),conditions,request);

        //返回数据
        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 调用serviece  添加或更新数据
     *
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate () throws Exception {

        //添加或更新数据
        int count = service.insertOrUpdate (sealUsageItem, getLoginUser (), getConnection ());

        //判断影响行数是否为一条
        if ( count != 1 ) {
            getResult ().setMessage ("操作失败");
        }

        return SUCCESS;
    }

    /**
     * 根据类型id获取用着类型数据
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String load () throws Exception {

        //根据id获取单个用章类型的数据
        sealUsageItem = service.loadSealUsageItemPO (sealUsageItem.getId ());

        //将输返回页面
        getResult ().setReturnValue (sealUsageItem.toJsonObject4Form ());

        return SUCCESS;
    }


    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete () throws Exception {

        //根据service 删除数据
        service.delete (sealUsageItem, getLoginUser (), getConnection ());

        return SUCCESS;
    }

    public SealUsageItemPO getSealUsageItem () {
        return sealUsageItem;
    }

    public void setSealUsageItem (SealUsageItemPO sealUsageItem) {
        this.sealUsageItem = sealUsageItem;
    }

    public SealUsageItemService getService () {
        return service;
    }

    public void setService (SealUsageItemService service) {
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
}
