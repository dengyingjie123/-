package com.youngbook.dao.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.QueryType;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.PositionPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Huchaoyi on 2018/11/24.
 */
@Component("positionDao")
public class PositionDaoImpl implements IPositionDao {



    @Override
    public int remove(PositionPO position, String id, Connection conn) throws Exception {

        int count = MySQLDao.remove(position,id,conn);

        return count;
    }

    @Override
    public List<PositionPO> search(PositionPO position, Class<PositionPO> positionPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws Exception {

        List<PositionPO> positionList = MySQLDao.search(position, PositionPO.class,conditions, queryType, conn);


        return positionList;
    }



    @Override
    public void insertOrUpdate(PositionPO position, Connection conn) throws Exception {

        MySQLDao.insertOrUpdate(position, conn);

    }

    @Override
    public List<PositionPO> searchByDepartment(DepartmentPO department, Connection conn) throws Exception {

        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("12ds14q5");
        databaseSQL.addParameter4All("DepartmentId",department.getId());
        databaseSQL.initSQL();
        List<PositionPO> search = MySQLDao.search(databaseSQL, PositionPO.class, conn);
        return search;
    }



}
