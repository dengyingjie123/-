package com.youngbook.dao.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.calendar.EventPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * @author Administrator
 * @create 2018/11/28
 */
@Component("calendarDao")
public class CalendarDaoImpl implements ICalendarDao {

    /**
     * @param intervalStart
     * @param conn
     * @return java.util.List<com.youngbook.entity.po.calendar.EventPO>
     * @throws Exception
     * @description 规范显示生日的功能代码
     * @author 胡超怡
     * @date 2018/11/28 18:00
     */
    @Override
    public List<EventPO> getListEventPOOfBrithday(String intervalStart, Connection conn, String loginId) throws Exception {

        List<EventPO> search;

        BaseAction baseUtils = new BaseAction();


        /**
         * 获取每个客户的生日
         *
         * 使用view_customer查询
         *
         * 只显示生日
         */
        if (baseUtils.hasPermission("个人日历_查看全部")) {
            DatabaseSQL databaseSQL = DatabaseSQL.newInstance("4D541000");
            databaseSQL.addParameter4All("intervalStart", intervalStart);
            databaseSQL.initSQL();
            search = MySQLDao.search(databaseSQL, EventPO.class, conn);
        } else {
            DatabaseSQL databaseSQL = DatabaseSQL.newInstance("4D541001");
            databaseSQL.addParameter4All("intervalStart", intervalStart);
            databaseSQL.addParameter4All("loginUserId", loginId);
            databaseSQL.initSQL();
            search = MySQLDao.search(databaseSQL, EventPO.class, conn);
        }

        return search;
    }

    @Override
    public List<EventPO> getListEventPOOfPayment(String intervalStart, Connection conn) throws Exception {

        /**
         * 获取所有兑付的金额和日期
         */
        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("822E1800");
        databaseSQL.addParameter4All("intervalStart", intervalStart);
        databaseSQL.initSQL();

        return MySQLDao.search(databaseSQL, EventPO.class, conn);
    }

}
