package com.youngbook.action.oa.assetFixation;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.assetFixation.AssetItemPO;
import com.youngbook.entity.vo.oa.assetFixation.AssetItemVO;
import com.youngbook.service.oa.assetFixation.AssetItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AssetItemAction extends BaseAction {
    private AssetItemPO assetItem = new AssetItemPO();
    private AssetItemVO assetItemVO = new AssetItemVO();
    private AssetItemService service = new AssetItemService();

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
        conditions = MySQLDao.getQueryDatetimeParameters(request, assetItem.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, assetItem.getClass(), conditions);

        //获取分页对象
        Pager pager = service.list( assetItem,assetItem.getApplicationId (), conditions, request);

        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }
    /**
     * 2015-6-17 周海鸿
     * 创建一个获取指定编号的费用报销的总金额事件
     * @return
     * @throws Exception
     */
    public String getMoneys () throws Exception{
        //使用类数据编号传送
        getResult().setReturnValue(service.getMoneys(assetItem.getApplicationId()));

        return SUCCESS;
    }

    /**
     * 添加过更新数据
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        //添加或更新数据
        int count = service.insertOrUpdate(assetItem, getLoginUser(), getConnection());

        //判断返回影响行数是否为一条。
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
        //设置查询id
        assetItem = service.loadAssetItemPO(assetItem.getId());

        //获取数据
        getResult().setReturnValue(assetItem.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        //删除数据
        service.delete(assetItem, getLoginUser(), getConnection());

        return SUCCESS;
    }


    public AssetItemPO getAssetItem() {
        return assetItem;
    }
    public void setAssetItem(AssetItemPO assetItem) {
        this.assetItem = assetItem;
    }

    public AssetItemVO getAssetItemVO() {
        return assetItemVO;
    }
    public void setAssetItemVO(AssetItemVO assetItemVO) {
        this.assetItemVO = assetItemVO;
    }

    public AssetItemService getService() {
        return service;
    }
    public void setService(AssetItemService service) {
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
