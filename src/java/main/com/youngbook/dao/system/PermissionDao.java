package com.youngbook.dao.system;

import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.PermissionPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component
public class PermissionDao {
    public List<PermissionPO> listById(String id, Connection conn) throws Exception{
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("2A811712");
        dbSQL.addParameter4All("permissionId", id);
        dbSQL.initSQL();

        return MySQLDao.search(dbSQL,PermissionPO.class,conn);
    }
}
