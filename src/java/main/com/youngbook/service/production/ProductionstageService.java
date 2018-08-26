package com.youngbook.service.production;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.ProductionstagePO;
import com.youngbook.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/15/14
 * Time: 12:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductionstageService extends BaseService {
    public Pager list(ProductionstagePO productionstage, List<KVObject> conditions, HttpServletRequest request) throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //Pager pager = Pager.query(moneyLog,conditions,request,queryType);
        String sql ="SELECT a.sid,a.id,a.productionId,a.name,a.StartTime,a.StopTime,a.ValueDate,a.ExpiringDate,a.InterestDate"+
        " FROM crm_productionstage a,crm_production b"+
        " WHERE a.Productionid= b.id and a.state=0 and b.state=0";
        Pager pager = Pager.query(sql,productionstage,conditions,request,queryType);

        return pager;
    }

    /**
     *
     * @param productionstage
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ProductionstagePO productionstage, UserPO user, Connection conn) throws Exception{

        int count = 0;
        // 新增
        if (productionstage.getId().equals("")) {
            productionstage.setSid(MySQLDao.getMaxSid("crm_productionstage", conn));
            productionstage.setId(IdUtils.getUUID32());
            productionstage.setState(Config.STATE_CURRENT);
            productionstage.setOperatorId(user.getId());
            productionstage.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(productionstage, conn);
        }
        // 更新
        else {
            ProductionstagePO temp = new ProductionstagePO();
            temp.setSid(productionstage.getSid());
            temp = MySQLDao.load(temp, ProductionstagePO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                productionstage.setSid(MySQLDao.getMaxSid("crm_productionstage", conn));
                productionstage.setState(Config.STATE_CURRENT);
                productionstage.setOperatorId(user.getId());
                productionstage.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(productionstage, conn);
            }
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    public int delete(ProductionstagePO productionstage, UserPO user, Connection conn) throws Exception {
        int count = 0;
        productionstage = MySQLDao.load(productionstage, ProductionstagePO.class);
        productionstage.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(productionstage, conn);
        if (count == 1) {
            productionstage.setSid(MySQLDao.getMaxSid("crm_productionstage", conn));
            productionstage.setState(Config.STATE_DELETE);
            productionstage.setOperateTime(TimeUtils.getNow());
            productionstage.setOperatorId(user.getId());
            count = MySQLDao.insert(productionstage, conn);
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }
}
