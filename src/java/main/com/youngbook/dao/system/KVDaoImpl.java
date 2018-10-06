package com.youngbook.dao.system;

import com.youngbook.common.Database;
import com.youngbook.common.QueryType;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.BaseDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 3/20/2017.
 */
@Component("kvDao")
public class KVDaoImpl extends BaseDao implements IKVDao {

    public KVPO loadKVPO(String k, String groupName, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("3DAF1810");
        dbSQL.addParameter4All("k", k);
        dbSQL.addParameter4All("groupName", groupName);
        dbSQL.initSQL();


        List<KVPO> list = MySQLDao.search(dbSQL, KVPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }


    public List<KVPO> getListKVPO(KVPO kvpo, Connection conn) throws Exception {

        List<KVPO> search = MySQLDao.search(kvpo, KVPO.class, null, new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL), conn);

        return search;
    }

}
