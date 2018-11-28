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
     * @description
     * 规范显示生日的功能代码
     * @author 胡超怡
     *
     * @date 2018/11/28 18:00
     * @param intervalStart
     * @param conn
     * @return java.util.List<com.youngbook.entity.po.calendar.EventPO>
     * @throws Exception
     */
    @Override
    public List<EventPO> getEventPO(String intervalStart, Connection conn) throws Exception {

        List<EventPO> search;

        BaseAction baseUtils = new BaseAction();

        String loginUserId = baseUtils.getLoginUser().getId();


        if (!baseUtils.hasPermission("个人日历_查看全部")){
            DatabaseSQL databaseSQL = DatabaseSQL.newInstance("4D541001");
            databaseSQL.addParameter4All("intervalStart",intervalStart);
            databaseSQL.addParameter4All("loginUserId",loginUserId);
            databaseSQL.initSQL();
            search = MySQLDao.search(databaseSQL, EventPO.class, conn);
        }else{
            DatabaseSQL databaseSQL = DatabaseSQL.newInstance("4D541000");
            databaseSQL.addParameter4All("intervalStart",intervalStart);
            databaseSQL.initSQL();
            search = MySQLDao.search(databaseSQL, EventPO.class, conn);
        }
        return search;
    }
}
