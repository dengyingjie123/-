package com.youngbook.service.calendar;

import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.ICalendarDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.calendar.EventPO;
import com.youngbook.entity.po.calendar.EventSourcePO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component("calendarService")
public class CalendarService extends BaseService {

    @Autowired
    private ICalendarDao calendarDao;

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
     * @description
     * 根据日期查询前后2个月的所有客户生日和兑付计划
     * @author 胡超怡
     *
     * @date 2018/12/26 14:02
     * @param intervalStart 传入的要查询的时间 yyyy-MM-dd
     * @param conn
     * @return java.util.List<com.youngbook.entity.po.calendar.EventPO>
     * @throws Exception
     */
    public List<EventPO> getListEventPO(String intervalStart, String userId, Connection conn) throws Exception {

        List<EventPO> eventPOBirthDay = calendarDao.getEventPO(intervalStart, conn);

        // List<EventPO> eventPOPaymentPlan = calendarDao.getEventPOPaymentPlan(intervalStart, conn);

        /**
         * 颜色写到 SystemConfig
         */
        for (EventPO birthDay: eventPOBirthDay ) {

            String birthdayBgCorlor = "crm.calendar.birthday.bgcolor";

            // A2A200

            birthDay.setColor(Config.getSystemConfig(birthdayBgCorlor));
        }

//        for (EventPO paymentPlan: eventPOPaymentPlan ) {
//            paymentPlan.setColor("#B0C4DE");
//            paymentPlan.setTitle(paymentPlan.getTitle());
//            eventPOBirthDay.add(paymentPlan);
//        }

        return eventPOBirthDay;

    }
}
