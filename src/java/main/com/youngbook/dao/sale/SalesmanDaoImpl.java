package com.youngbook.dao.sale;

import com.youngbook.common.Database;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.SalesmanPO;
import com.youngbook.entity.vo.Sale.SalesManVO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 2016/8/10.
 */
@Component("salesmanDao")
public class SalesmanDaoImpl implements ISalesmanDao {

    public Pager listPagerSalesmanGroup(SalesManVO salesManVO, int currentPage, int showRowCount, Connection conn) throws Exception {


        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listPagerSalesmanGroup", "SalesmanDaoImplSQL", SalesmanDaoImpl.class);
//        dbSQL.addParameter4All("groupAreaName", "%" + salesManVO.getGroupName() +"%");
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), salesManVO, null, currentPage, showRowCount, queryType, conn);

        return pager;
    }

    /**
     * 修改人：张舜清
     * 修改时间：2015.06.08
     * 描述：添加用户时如果选择了销售就调用这个Service添加销售人员
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public SalesmanPO insertSalesman(UserPO user, Connection conn) throws Exception {
        int count=0;
        SalesmanPO salesman = new SalesmanPO();
        salesman.setUserId(user.getId());
        salesman.setState(Config.STATE_CURRENT);
        salesman = MySQLDao.load(salesman, SalesmanPO.class);
        if (salesman ==  null){
            SalesmanPO temp = new SalesmanPO();
            temp.setSid(MySQLDao.getMaxSid("crm_saleman", conn));
            temp.setId(IdUtils.getUUID32());
            temp.setState(Config.STATE_CURRENT);
            temp.setOperatorId(user.getId());
            temp.setOperateTime(TimeUtils.getNow());
            temp.setUserId(user.getId());
            count = MySQLDao.insert(temp, conn);
            return temp;
        } else {
            salesman.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(salesman, conn);
            if(count == 1){
                salesman.setSid(MySQLDao.getMaxSid("crm_saleman", conn));
                salesman.setId(IdUtils.getUUID32());
                salesman.setState(Config.STATE_CURRENT);
                salesman.setOperatorId(user.getId());
                salesman.setOperateTime(TimeUtils.getNow());
                salesman.setUserId(user.getId());
                count = MySQLDao.insert(salesman, conn);
            }
        }
        return salesman;
    }
}
