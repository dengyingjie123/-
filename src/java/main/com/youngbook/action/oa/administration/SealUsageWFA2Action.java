package com.youngbook.action.oa.administration;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.administration.SealUsageWFA2PO;
import com.youngbook.entity.vo.oa.administration.SealUsageWFA2VO;
import com.youngbook.service.oa.administration.SealUsageWFA2Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 用章申请ACtion类 用来处理用章申请业务逻辑
 */
public class SealUsageWFA2Action extends BaseAction {

    /**
     * 用章模型类
     */
    private SealUsageWFA2PO sealUsageWFA2 = new SealUsageWFA2PO ();
    private SealUsageWFA2VO sealUsageWFA2VO = new SealUsageWFA2VO ();
    //用章逻辑处理类
    private SealUsageWFA2Service service = new SealUsageWFA2Service ();

    /**
     * 调用serviece  添加或更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate () throws Exception {
        //调用service 添加或更新数据
        service.insertOrUpdate (sealUsageWFA2, sealUsageWFA2VO, getLoginUser (), getConnection ());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete () throws Exception {
        //调用serviced 删除数据
        service.delete (sealUsageWFA2, getLoginUser (), getConnection ());
        return SUCCESS;
    }

    /**
     * 根据条件获取单条输
     *
     * @return
     * @throws Exception
     */
    public String load () throws Exception {
        //设置查询条件
        sealUsageWFA2.setState (Config.STATE_CURRENT);

        //获取数据
        sealUsageWFA2 = MySQLDao.load (sealUsageWFA2, SealUsageWFA2PO.class);

        //数据返回页面
        getResult ().setReturnValue (sealUsageWFA2.toJsonObject4Form ());
        return SUCCESS;
    }

    /**
     * 获取申请用章列表
     *
     * @return
     * @throws Exception
     */
    public String list () throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();


        //获取范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters (request, sealUsageWFA2VO.getClass (), conditions);

        //获取分页列表对象
        Pager pager = service.list (sealUsageWFA2VO, request, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 获取等待审核用章列表
     *
     * @return
     * @throws Exception
     */
    public String waitList () throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();

        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();


        //获取范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters (request, sealUsageWFA2VO.getClass (), conditions);

        //获取分页列表对象
        Pager pager = service.waitList (sealUsageWFA2VO, request, getLoginUser (), conditions);

        //返回页面数据
        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 获取用章列表
     *
     * @return
     * @throws Exception
     */
    public String particiPantlist () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();

        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //获取范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters (request, sealUsageWFA2VO.getClass (), conditions);

        //获取分页列表对象
        Pager pager = service.particiPantList (sealUsageWFA2VO, request, getLoginUser (), conditions);

        //返回页面数据
        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    public SealUsageWFA2PO getSealUsageWFA2() {
        return sealUsageWFA2;
    }

    public void setSealUsageWFA2(SealUsageWFA2PO sealUsageWFA2) {
        this.sealUsageWFA2 = sealUsageWFA2;
    }

    public SealUsageWFA2VO getSealUsageWFA2VO() {
        return sealUsageWFA2VO;
    }

    public void setSealUsageWFA2VO(SealUsageWFA2VO sealUsageWFA2VO) {
        this.sealUsageWFA2VO = sealUsageWFA2VO;
    }

    public SealUsageWFA2Service getService() {
        return service;
    }

    public void setService(SealUsageWFA2Service service) {
        this.service = service;
    }
}
