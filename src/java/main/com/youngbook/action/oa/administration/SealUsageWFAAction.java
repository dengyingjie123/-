package com.youngbook.action.oa.administration;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.administration.SealUsageWFAPO;
import com.youngbook.entity.vo.oa.administration.SealUsageWFAVO;
import com.youngbook.service.oa.administration.SealUsageWFAService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 用章申请ACtion类 用来处理用章申请业务逻辑
 */
public class SealUsageWFAAction extends BaseAction {

    /**
     * 用章模型类
     */
    private SealUsageWFAPO sealUsageWFA = new SealUsageWFAPO ();
    private SealUsageWFAVO sealUsageWFAVO = new SealUsageWFAVO ();
    //用章逻辑处理类
    private SealUsageWFAService service = new SealUsageWFAService ();

    /**
     * 调用serviece  添加或更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate () throws Exception {
        //调用service 添加或更新数据
        service.insertOrUpdate (sealUsageWFA, sealUsageWFAVO, getLoginUser (), getConnection ());

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
        service.delete (sealUsageWFA, getLoginUser (), getConnection ());
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
        sealUsageWFA.setState (Config.STATE_CURRENT);

        //获取数据
        sealUsageWFA = MySQLDao.load (sealUsageWFA, SealUsageWFAPO.class);

        //数据返回页面
        getResult ().setReturnValue (sealUsageWFA.toJsonObject4Form ());
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
        conditions = MySQLDao.getQueryDatetimeParameters (request, sealUsageWFAVO.getClass (), conditions);

        //获取分页列表对象
        Pager pager = service.list (sealUsageWFAVO, request, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 获取等待审核用章列表
     *
     * @return
     * @throws Exception
     */
    public String waitlist () throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();

        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

       
        //获取范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters (request, sealUsageWFAVO.getClass (), conditions);

        //获取分页列表对象
        Pager pager = service.Waitlist (sealUsageWFAVO, request, getLoginUser (), conditions);

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
    public String Participantlist () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();

        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //获取范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters (request, sealUsageWFAVO.getClass (), conditions);

        //获取分页列表对象
        Pager pager = service.Participantlist (sealUsageWFAVO, request, getLoginUser (), conditions);

        //返回页面数据
        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }


    public SealUsageWFAPO getSealUsageWFA () {
        return sealUsageWFA;
    }

    public void setSealUsageWFA (SealUsageWFAPO sealUsageWFA) {
        this.sealUsageWFA = sealUsageWFA;
    }

    public SealUsageWFAService getService () {
        return service;
    }

    public void setService (SealUsageWFAService service) {
        this.service = service;
    }

    public SealUsageWFAVO getSealUsageWFAVO () {
        return sealUsageWFAVO;
    }

    public void setSealUsageWFAVO (SealUsageWFAVO sealUsageWFAVO) {
        this.sealUsageWFAVO = sealUsageWFAVO;
    }


}
