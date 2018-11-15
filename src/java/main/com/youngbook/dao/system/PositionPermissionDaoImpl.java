package com.youngbook.dao.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.QueryType;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.PositionPermissionPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Huchaoyi on 2018/11/24.
 */
@Component("PositionPermissionDao")
public class PositionPermissionDaoImpl implements IPositionPermissionDao {

    @Override
    public int remove(PositionPermissionPO positionPermission, String id, Connection conn) throws Exception {

        int count = MySQLDao.remove(positionPermission,id,conn);

        return count;
    }

    @Override
    public List<PositionPermissionPO> search(PositionPermissionPO positionPermission, Class<PositionPermissionPO> positionPermissionPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws Exception {

        List<PositionPermissionPO> positionPermissionList = MySQLDao.search(positionPermission, PositionPermissionPO.class,conditions, queryType, conn);

        return positionPermissionList;
    }

    @Override
    public void insertOrUpdate(PositionPermissionPO positionPermission, Connection conn) throws Exception {

        MySQLDao.insertOrUpdate(positionPermission, conn);

    }

    @Override
    public List<PositionPermissionPO> searchByPosition(PositionPO position, Connection conn) throws Exception {
        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("12ds14q3");
        databaseSQL.addParameter4All("positionId",position.getId());
        databaseSQL.initSQL();
        List<PositionPermissionPO> search = MySQLDao.search(databaseSQL, PositionPermissionPO.class, conn);
        return search;
    }
}
