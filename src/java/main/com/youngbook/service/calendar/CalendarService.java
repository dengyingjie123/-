package com.youngbook.service.calendar;

import com.youngbook.common.Database;
import com.youngbook.common.KVObjects;
import com.youngbook.common.MyException;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.calendar.EventPO;
import com.youngbook.entity.po.calendar.EventSourcePO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component("calendarService")
public class CalendarService extends BaseService {

    public EventSourcePO getCustomerBirthdays4MonthView(String intervalStart, UserPO saleMan) throws Exception {
        // SQL 编码
        intervalStart = Database.encodeSQL(intervalStart);

        EventSourcePO eventSource = new EventSourcePO();
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" ");
        sbSQL.append(" SELECT");
        sbSQL.append("     id,");
        sbSQL.append("     NAME title,");
        sbSQL.append("     CONCAT(");
        sbSQL.append("         DATE_FORMAT(date_add('"+intervalStart+"', INTERVAL - 1 MONTH), '%Y'),");
        sbSQL.append("         '-',");
        sbSQL.append("         DATE_FORMAT(Birthday, '%c-%d')");
        sbSQL.append("     ) START");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal");
        sbSQL.append(" WHERE");
        sbSQL.append("     state = 0");
        sbSQL.append("     AND DATE_FORMAT(birthday, '%c') = DATE_FORMAT(");
        sbSQL.append("         date_add('"+intervalStart+"', INTERVAL - 1 MONTH),");
        sbSQL.append("         '%c'");
        sbSQL.append("     )");
        sbSQL.append(" ");
        sbSQL.append(" UNION");
        sbSQL.append(" ");
        sbSQL.append(" ");
        sbSQL.append(" SELECT");
        sbSQL.append("     id,");
        sbSQL.append("     NAME title,");
        sbSQL.append("     CONCAT(");
        sbSQL.append("         DATE_FORMAT(date_add('"+intervalStart+"', INTERVAL 0 MONTH), '%Y'),");
        sbSQL.append("         '-',");
        sbSQL.append("         DATE_FORMAT(Birthday, '%c-%d')");
        sbSQL.append("     ) START");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal");
        sbSQL.append(" WHERE");
        sbSQL.append("     state = 0");
        sbSQL.append("     AND DATE_FORMAT(birthday, '%c') = DATE_FORMAT(");
        sbSQL.append("         date_add('"+intervalStart+"', INTERVAL 0 MONTH),");
        sbSQL.append("         '%c'");
        sbSQL.append("     )");
        sbSQL.append(" ");
        sbSQL.append(" UNION");
        sbSQL.append(" ");
        sbSQL.append(" ");
        sbSQL.append(" SELECT");
        sbSQL.append("     id,");
        sbSQL.append("     NAME title,");
        sbSQL.append("     CONCAT(");
        sbSQL.append("         DATE_FORMAT(date_add('"+intervalStart+"', INTERVAL 1 MONTH), '%Y'),");
        sbSQL.append("         '-',");
        sbSQL.append("         DATE_FORMAT(Birthday, '%c-%d')");
        sbSQL.append("     ) START");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal");
        sbSQL.append(" WHERE");
        sbSQL.append("     state = 0");
        sbSQL.append("     AND DATE_FORMAT(birthday, '%c') = DATE_FORMAT(");
        sbSQL.append("         date_add('"+intervalStart+"', INTERVAL 1 MONTH),");
        sbSQL.append("         '%c'");
        sbSQL.append("     )");

        // 如果传入了销售员，则只查询分配给此销售员的客户生日
        if (saleMan != null) {
            String loginUserId = Database.encodeSQL(saleMan.getId());

            StringBuffer sbSQLIn = new StringBuffer();
            sbSQLIn.append(" SELECT");
            sbSQLIn.append("      id CustomerId");
            sbSQLIn.append(" FROM");
            sbSQLIn.append("      crm_customerpersonal");
            sbSQLIn.append(" WHERE");
            sbSQLIn.append("      state = 0");
            sbSQLIn.append(" AND OperatorId = '"+loginUserId+"'");
            sbSQLIn.append(" AND id NOT IN (");
            sbSQLIn.append("      SELECT");
            sbSQLIn.append("           CustomerId");
            sbSQLIn.append("      FROM");
            sbSQLIn.append("           crm_customerdistribution");
            sbSQLIn.append(" )");
            sbSQLIn.append(" ");
            sbSQLIn.append(" UNION");
            sbSQLIn.append(" ");
            sbSQLIn.append(" SELECT");
            sbSQLIn.append("      CustomerId");
            sbSQLIn.append(" FROM");
            sbSQLIn.append("      crm_customerdistribution");
            sbSQLIn.append(" WHERE");
            sbSQLIn.append("      1 = 1");
            sbSQLIn.append(" AND state = 0");
            sbSQLIn.append(" AND STATUS = 1");
            sbSQLIn.append(" AND SaleManId = '"+loginUserId+"'");


            sbSQL.insert(0, " Select * from (");
            sbSQL.append(" ) t where t.id in(").append(sbSQLIn).append(")");
        }

        try {
            List<EventPO> events = MySQLDao.query(sbSQL.toString(), EventPO.class, null);
            eventSource.setEvents(events);
            eventSource.setColor("#FFCCFF");
        }
        catch (Exception e) {
            MyException.deal(e);
        }

        return eventSource;
    }

    /**
     * @description 获取当前用户本月募集资金总额(打款后)
     *
     * @author 苟熙霖
     *
     * @date 2018/12/4 13:46
     * @param today
     * @param userId
     * @param connection
     * @return java.util.List<com.youngbook.entity.po.production.OrderPO>
     * @throws Exception
     */
    public List<OrderPO> getCurrentMonthRaise(String today , String userId , Connection connection) throws Exception {

        // todo: gouxilin 方法重命名  注释里提醒：today yyyy-mm-dd

        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("53I25CYF");
        databaseSQL.addParameter4All("userId",userId);
        databaseSQL.addParameter4All("today",today);
        databaseSQL.initSQL();
        List<OrderPO> search = MySQLDao.search(databaseSQL, OrderPO.class, connection);




        return search;
    }
}
