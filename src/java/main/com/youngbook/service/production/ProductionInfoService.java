package com.youngbook.service.production;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.ProductionInfoPO;
import com.youngbook.entity.vo.production.ProductionInfoVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是产品扩展信息模块的对应的Service类
 * Created by Administrator on 2015/4/9.
 */
@Component("productionInfoService")
public class ProductionInfoService extends BaseService {
    /**
     * 查询数据，然后存放到list里面
     *
     * @param productionInfoVO
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager list(ProductionInfoVO productionInfoVO, List<KVObject> conditions) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        // sql查询语句
        String sql = "SELECT pi.*,pc.`Name` productionName,u.`name` operatorName " +
                " FROM CRM_ProductionInfo pi,system_user u,crm_production pc " +
                " WHERE 1 = 1 AND pi.state = 0 AND u.state = 0 AND pc.state = 0 AND pi.OperatorId = u.Id AND pi.ProductionId = pc.id AND pc.OperatorId = u.Id";
        Pager pager = Pager.query(sql, productionInfoVO, conditions, request, queryType);

        return pager;
    }

    /**
     * 新增和更新数据
     *
     * @param productionInfo
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ProductionInfoPO productionInfo, UserPO user, Connection conn) throws Exception {
        int count = 0;

        count = MySQLDao.insertOrUpdate(productionInfo, user.getId(), conn);
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }


    public int insertProductionDes(ProductionInfoPO productionInfo, UserPO user, Connection conn) throws Exception {
        int count = 0;

        count = MySQLDao.insertOrUpdate(productionInfo, user.getId(), conn);

        if (count != 1) {
            MyException.newInstance("插入或更新产品描述失败").throwException();
        }
        return count;
    }

    /**
     * 删除数据
     *
     * @param productionInfo
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(ProductionInfoPO productionInfo, UserPO user, Connection conn) throws Exception {
        int count = 0;

        productionInfo.setState(Config.STATE_CURRENT);
        productionInfo = MySQLDao.load(productionInfo, ProductionInfoPO.class);
        productionInfo.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(productionInfo, conn);
        if (count == 1) {
            productionInfo.setSid(MySQLDao.getMaxSid("CRM_ProductionInfo", conn));
            productionInfo.setState(Config.STATE_DELETE);
            productionInfo.setOperateTime(TimeUtils.getNow());
            productionInfo.setOperatorId(user.getId());
            count = MySQLDao.insert(productionInfo, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 获取产品介绍信息
     *
     * @param productionId 产品编号
     * @param optionId     产品介绍编号
     * @param conn         连接
     * @return
     * @throws Exception
     */
    public JSONObject getProductionArticle(String productionId, int optionId, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("  select * from crm_productioninfo where state =0 AND ProductionId =? ");
        List<KVObject> paramters = new ArrayList<KVObject>();
        paramters = Database.addQueryKVObject(1, productionId, paramters);
        List<ProductionInfoPO> pager = MySQLDao.search(sbSQL.toString(), paramters, ProductionInfoPO.class, null, conn);
        if (pager != null && pager.size() == 1) {
            ProductionInfoPO info = new ProductionInfoPO();
            JSONObject object = new JSONObject();
            switch (optionId) {
                case 0:
                    object.put("title", pager.get(0).getTitle1());
                    object.put("content", pager.get(0).getContent1());
                    break;
                case 1:
                    object.put("title", pager.get(0).getTitle2());
                    object.put("content", pager.get(0).getContent2());
                    break;
                case 2:
                    object.put("title", pager.get(0).getTitle3());
                    object.put("content", pager.get(0).getContent3());
                    break;
                case 3:
                    object.put("title", pager.get(0).getTitle4());
                    object.put("content", pager.get(0).getContent4());
                    break;
                case 4:
                    object.put("title", pager.get(0).getTitle5());
                    object.put("content", pager.get(0).getContent5());
                    break;
                case 5:
                    object.put("title", pager.get(0).getTitle6());
                    object.put("content", pager.get(0).getContent6());
                    break;
                case 6:
                    object.put("title", pager.get(0).getTitle7());
                    object.put("content", pager.get(0).getContent7());
                    break;
                case 7:
                    object.put("title", pager.get(0).getTitle8());
                    object.put("content", pager.get(0).getContent8());
                    break;
                case 8:
                    object.put("title", pager.get(0).getTitle9());
                    object.put("content", pager.get(0).getContent9());
                    break;
                case 9:
                    object.put("title", pager.get(0).getTitle10());
                    object.put("content", pager.get(0).getContent10());
                    break;
            }
            object.put("productionId", productionId);
            return object;
        }
        return null;
    }

    public ProductionInfoPO loadByProductionId(String productionId, Connection conn) throws Exception {
        ProductionInfoPO productionInfo = new ProductionInfoPO();
        productionInfo.setProductionId(productionId);
        productionInfo.setState(Config.STATE_CURRENT);
        return MySQLDao.load(productionInfo, ProductionInfoPO.class, conn);
    }

    /**
     * 供移动端使用，获取大额产品的介绍内容(6-8)，包括title和content
     * @param productionId
     * @param conn
     * @return
     * @throws Exception
     */
    public JSONObject getProductionInfoContent(String productionId, Connection conn) throws Exception {
        String sql = "select * from crm_productioninfo where state =0 AND ProductionId = '" + productionId + "'";

        List<ProductionInfoPO> list = MySQLDao.search(sql, new ArrayList<KVObject>(), ProductionInfoPO.class, null, conn);

        if (list != null && list.size() == 1) {

            ProductionInfoPO po = list.get(0);
            JSONArray jsonArr = new JSONArray();
            JSONObject object = new JSONObject();

            object.put("id", po.getId());
            object.put("sid", po.getSid());
            object.put("productionId", po.getProductionId());

            // isDisplay状态码为3946才说明显示具体内容
            if (!StringUtils.isEmpty(po.getIsDisplay6()) && po.getIsDisplay6().equals("3946")) {
                object.put("title", po.getTitle6());
                object.put("content", po.getContent6());
                object.put("index", 6);
                jsonArr.add(object);
            }
            if (!StringUtils.isEmpty(po.getIsDisplay7()) && po.getIsDisplay7().equals("3946")) {
                object.put("title", po.getTitle7());
                object.put("content", po.getContent7());
                object.put("index", 7);
                jsonArr.add(object);
            }
            if (!StringUtils.isEmpty(po.getIsDisplay8()) && po.getIsDisplay8().equals("3946")) {
                object.put("title", po.getTitle8());
                object.put("content", po.getContent8());
                object.put("index", 8);
                jsonArr.add(object);
            }

            JSONObject ob = new JSONObject();
            ob.put("rows", jsonArr);
            return ob;
        }
        // 数据不正确则返回null
        return null;
    }

    /**
     * HOPEWEALTH-1301<br/>
     * 根据产品ID来获取产品信息的productionId和title，有则添加，以JSONObject形式返回<br/>
     * <p/>
     * 作者：曾威恺
     * 内容：创建代码
     * 时间：2016年03月23日
     *
     * @param productionId
     * @param conn
     * @return
     * @throws Exception
     */
    public JSONObject getProductionInfoTitle(String productionId, Connection conn) throws Exception {

        // 组装 SQL
        String sql = "select * from crm_productioninfo where state =0 AND ProductionId = '" + productionId + "'";
        // 查询数据
        List<ProductionInfoPO> list = MySQLDao.search(sql, new ArrayList<KVObject>(), ProductionInfoPO.class, null, conn);

        if (list != null && list.size() == 1) {

            ProductionInfoPO po = list.get(0);
            JSONArray jsonArr = new JSONArray();
            JSONObject object = new JSONObject();

            object.put("id", po.getId());
            object.put("sid", po.getSid());
            object.put("productionId", po.getProductionId());

            // isDisplay状态码为3946才说明显示具体内容
            if (!StringUtils.isEmpty(po.getIsDisplay6()) && po.getIsDisplay6().equals("3946")) {
                object.put("title", po.getTitle6());
                object.put("index", 5);
                jsonArr.add(object);
                object.remove("title");
            }

            if (!StringUtils.isEmpty(po.getIsDisplay7()) && po.getIsDisplay7().equals("3946")) {
                object.put("title", po.getTitle7());
                object.put("index", 6);
                jsonArr.add(object);
                object.remove("title");
            }

            if (!StringUtils.isEmpty(po.getIsDisplay8()) && po.getIsDisplay8().equals("3946")) {
                object.put("title", po.getTitle8());
                object.put("index", 7);
                jsonArr.add(object);
                object.remove("title");
            }
            if (!StringUtils.isEmpty(po.getIsDisplay9()) && po.getIsDisplay9().equals("3946")) {
                object.put("title", po.getTitle9());
                object.put("index", 8);
                jsonArr.add(object);
                object.remove("title");
            }
            if (!StringUtils.isEmpty(po.getIsDisplay10()) && po.getIsDisplay10().equals("3946")) {
                object.put("title", po.getTitle10());
                object.put("index", 9);
                jsonArr.add(object);
                object.remove("title");
            }

            JSONObject ob = new JSONObject();
            ob.put("rows", jsonArr);
            return ob;
        }

        return null;
    }
}
