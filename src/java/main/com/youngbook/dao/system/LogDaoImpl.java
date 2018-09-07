package com.youngbook.dao.system;

import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.LogPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 1/19/2017.
 */
@Component("logDao")
public class LogDaoImpl implements ILogDao {

    public Pager listPagerLogPO(LogPO logPO, int currentPage, int showRowCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listPagerLogPO", this);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), logPO, null, currentPage, showRowCount, queryType, conn);

        return pager;
    }

    public void save(LogPO logPO, Connection conn) throws Exception {
        if (logPO != null) {
            MySQLDao.insertOrUpdate(logPO, conn);
        }
    }

    public void save(LogPO logPO) throws Exception {
        Connection conn = Config.getConnection();

        try {
            save(logPO, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
    }


    public void save(String name, String peopleMessage, String machineMessage, String url, Connection conn) throws Exception {
        LogPO logPO = new LogPO();
        logPO.setName(name);
        logPO.setPeopleMessage(peopleMessage);
        logPO.setMachineMessage(machineMessage);
        logPO.setUrl(url);

        save(logPO, conn);
    }

    public void save(String name, String peopleMessage, String machineMessage, Connection conn) throws Exception {
        save(name, peopleMessage, machineMessage, null, conn);
    }


    public void save(String name, String peopleMessage, String machineMessage) throws Exception {
        Connection conn = Config.getConnection();

        try {
            save(name, peopleMessage, machineMessage, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
    }
}
