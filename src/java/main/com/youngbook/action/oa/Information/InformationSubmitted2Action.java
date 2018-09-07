package com.youngbook.action.oa.Information;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.Information.InformationSubmitted2PO;
import com.youngbook.entity.vo.oa.Information.InformationSubmitted2VO;
import com.youngbook.service.oa.Information.InformationSubmitted2Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 资料报送申请ACtion类 用来处理资料报送申请业务逻辑
 */
public class InformationSubmitted2Action extends BaseAction {

    /**
     * 资料报送模型类
     */
    private InformationSubmitted2PO informationSubmitted2 = new InformationSubmitted2PO ();
    private InformationSubmitted2VO informationSubmitted2VO = new InformationSubmitted2VO ();
    //资料报送逻辑处理类
    private InformationSubmitted2Service service = new InformationSubmitted2Service ();

    /**
     * 调用serviece  添加或更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate () throws Exception {
        //调用service 添加或更新数据
        service.insertOrUpdate(informationSubmitted2, informationSubmitted2VO, getLoginUser(), getConnection());

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
        service.delete(informationSubmitted2, getLoginUser(), getConnection());
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
        informationSubmitted2.setState (Config.STATE_CURRENT);

        //获取数据
        informationSubmitted2 = MySQLDao.load (informationSubmitted2, InformationSubmitted2PO.class);

        //数据返回页面
        getResult ().setReturnValue (informationSubmitted2.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 获取申请资料报送列表
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
        conditions = MySQLDao.getQueryDatetimeParameters (request, informationSubmitted2VO.getClass(), conditions);

        //获取分页列表对象
        Pager pager = service.list (informationSubmitted2VO, request, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 获取等待审核资料报送列表
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
        conditions = MySQLDao.getQueryDatetimeParameters (request, informationSubmitted2VO.getClass (), conditions);

        //获取分页列表对象
        Pager pager = service.waitList(informationSubmitted2VO, request, getLoginUser(), conditions);

        //返回页面数据
        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 获取资料报送列表
     *
     * @return
     * @throws Exception
     */
    public String particiPantList () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();

        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //获取范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters (request, informationSubmitted2VO .getClass (), conditions);

        //获取分页列表对象
        Pager pager = service.particiPantList(informationSubmitted2VO, request, getLoginUser(), conditions);

        //返回页面数据
        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    public InformationSubmitted2PO getInformationSubmitted2() {
        return informationSubmitted2;
    }

    public void setInformationSubmitted2(InformationSubmitted2PO informationSubmitted2) {
        this.informationSubmitted2 = informationSubmitted2;
    }

    public InformationSubmitted2VO getInformationSubmitted2VO() {
        return informationSubmitted2VO;
    }

    public void setInformationSubmitted2VO(InformationSubmitted2VO informationSubmitted2VO) {
        this.informationSubmitted2VO = informationSubmitted2VO;
    }

    public InformationSubmitted2Service getService() {
        return service;
    }

    public void setService(InformationSubmitted2Service service) {
        this.service = service;
    }
}
