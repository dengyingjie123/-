package com.youngbook.action.oa.assetInfo;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.assetInfo.AssetInfoPO;
import com.youngbook.entity.vo.oa.assetInfo.AssetInfoVO;
import com.youngbook.service.oa.assetInfo.AssetInfoService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AssetInfoAction extends BaseAction {

    private AssetInfoPO assetInfo = new AssetInfoPO();
    private AssetInfoService service = new AssetInfoService();
    private AssetInfoVO assetInfoVO = new AssetInfoVO();
    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取数据列表
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();

        
        //设置范围查询
        conditions = MySQLDao.getQueryDatetimeParameters(request, assetInfoVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, assetInfoVO.getClass(), conditions);
        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        //获取分页列表数据

        Pager pager  = service.List(assetInfoVO ,conditions);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 添加过更新数据
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        //添加或更新数据
        int count = service.insertOrUpdate(assetInfo, getLoginUser(), getConnection());

        //判断影响行数是否为一条
        if (count != 1) {
            getResult().setMessage("操作失败");
        }

        return SUCCESS;
    }

    /**
     * 获取单条数据
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        //根据指定的id查询
        assetInfo = service.loadAssetInfoPO(assetInfo.getId());

        getResult().setReturnValue(assetInfo.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        //删除数据
        service.delete(assetInfo, getLoginUser(), getConnection());

        return SUCCESS;
    }

    public AssetInfoPO getAssetInfo() {
        return assetInfo;
    }
    public void setAssetInfo(AssetInfoPO assetInfo) {
        this.assetInfo = assetInfo;
    }

    public AssetInfoService getService() {
        return service;
    }
    public void setService(AssetInfoService service) {
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

    public AssetInfoVO getAssetInfoVO() {
        return assetInfoVO;
    }
    public void setAssetInfoVO(AssetInfoVO assetInfoVO) {
        this.assetInfoVO = assetInfoVO;
    }

}