package com.youngbook.dao.system;

import com.youngbook.common.Pager;
import com.youngbook.entity.po.system.LogPO;
import java.sql.Connection;

/**
 * Created by Lee on 1/19/2017.
 */
public interface ILogDao {
    public Pager listPagerLogPO(LogPO logPO, int currentPage, int showRowCount, Connection conn) throws Exception;
    public void save(LogPO logPO, Connection conn) throws Exception;
    public void save(LogPO logPO) throws Exception;
    public void save(String name, String peopleMessage, String machineMessage, Connection conn) throws Exception;
    public void save(String name, String peopleMessage, String machineMessage, String url, Connection conn) throws Exception;
    public void save(String name, String peopleMessage, String machineMessage) throws Exception;
}
