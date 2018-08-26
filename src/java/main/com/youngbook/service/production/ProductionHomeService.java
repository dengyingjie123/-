package com.youngbook.service.production;

import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.wf.Database;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.ProductionHomePO;
import com.youngbook.entity.vo.production.ProductionHomeVO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jepson-pc on 2015/8/17.
 */
@Component("productionHomeService")
public class ProductionHomeService extends BaseService {


    /**
     * 修改人:姚章鹏
     * 时间：2015年8月17日17:54:01
     * 查询产品头数据
     *
     * @param productionHome
     * @param conditions
     * @param request
     * @return
     * @throws Exception
     */
    public Pager list(ProductionHomeVO productionHome, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     ph.*, p.NAME");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_ProductionHome ph,");
        sbSQL.append("     crm_project p");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND ph.state = 0");
        sbSQL.append(" AND p.state = 0");
        sbSQL.append(" AND ph.ProjectId = p.id");
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), productionHome, conditions, request, queryType);

        return pager;
    }


    /**
     * * 修改人:姚章鹏
     * 时间：2015年8月17日17:54:01
     * 添加或修改数据，并修改数据状态
     *
     * @param productionHome
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ProductionHomePO productionHome, UserPO user, Connection conn) throws Exception {
        int count = 0;

        // 新增
        if (productionHome.getId().equals("")) {

            // 获取年份
            Calendar a = Calendar.getInstance();
            String strYear = String.valueOf(a.get(Calendar.YEAR)).substring(2); // 得到年

            // 生成产品头顺序号
            String number = StringUtils.buildNumberString(MySQLDao.getSequence("productionCode"), 3);

            // 0102 暂时固定。
            productionHome.setProductionId("0102" + strYear + number);

            if (productionHome.getProductionId().length() != 9) {
                MyException.newInstance("创建产品编号失败").throwException();
            }
        }

        count = MySQLDao.insertOrUpdate(productionHome, user.getId(), conn);

        if (count != 1) {
            MyException.newInstance("保存产品失败").throwException();
        }

        return count;
    }

    /**
     * * 修改人:姚章鹏
     * 时间：2015年8月17日17:54:01
     * 根据制定的ID获取数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public ProductionHomePO loadProductionHomePO(String id) throws Exception {
        ProductionHomePO po = new ProductionHomePO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, ProductionHomePO.class);

        return po;
    }

    /**
     * * 修改人:姚章鹏
     * 时间：2015年8月17日17:54:01
     * 根据条改编数据的状态
     *
     * @param productionHome
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(ProductionHomePO productionHome, UserPO user, Connection conn) throws Exception {
        int count = 0;

        productionHome.setState(Config.STATE_CURRENT);
        productionHome = MySQLDao.load(productionHome, ProductionHomePO.class);
        productionHome.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(productionHome, conn);
        if (count == 1) {
            productionHome.setSid(MySQLDao.getMaxSid("crm_ProductionHome", conn));
            productionHome.setState(Config.STATE_DELETE);
            productionHome.setOperateTime(TimeUtils.getNow());
            productionHome.setOperatorId(user.getId());
            count = MySQLDao.insert(productionHome, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }
}
