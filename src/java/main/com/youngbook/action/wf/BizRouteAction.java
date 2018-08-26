package com.youngbook.action.wf;

import com.youngbook.action.BaseAction;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.wf.BizRoutePO;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by 小周 on 2015/6/12.
 * 实现业务表业务逻辑
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class BizRouteAction extends BaseAction {

    private BizRoutePO bizRoute = new BizRoutePO();
    private static final int BIZROUTE_LOAD_ERROR=301;
    /**
     * 初始化加载业务数据
     * @return
     * @throws Exception
     */
    /**
     *
     * 修改人：周海鸿
     * 修改时间 2015-6-24
     * 修改事件 添加逻辑处理，判断获取的对象是否为空，增加提示用户数据
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
//        根据制定的ID编号获取指定的数据并返回脚本处理
        bizRoute = MySQLDao.load(bizRoute, BizRoutePO.class);
        if(bizRoute == null){
            //设置错误提示 状态为301
            getResult().setCode(BizRouteAction.BIZROUTE_LOAD_ERROR);
          throw  new Exception("当前业务数据出错");
        }
        getResult().setReturnValue(bizRoute.toJsonObject4Form());

        return SUCCESS;
    }

    public BizRoutePO getBizRoute() {
        return bizRoute;
    }

    public void setBizRoute(BizRoutePO bizRoute) {
        this.bizRoute = bizRoute;
    }
}
