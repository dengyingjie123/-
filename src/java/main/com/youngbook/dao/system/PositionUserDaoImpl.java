package com.youngbook.dao.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.system.PositionUserPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 10/25/2017.
 */
@Component("positionUserDao")
public class PositionUserDaoImpl implements IPositionUserDao {

    public void setDefaultFinanceCircle(String userId, Connection conn) throws Exception {

        String positionId = Config.getSystemConfig("financeCircle.position.default.id");
        PositionUserPO positionUserPO = new PositionUserPO();

        positionUserPO.setId(IdUtils.getUUID32());
        positionUserPO.setUserId(userId);
        positionUserPO.setPositionId(positionId);
        positionUserPO.setStates(1);

        MySQLDao.insert(positionUserPO, conn);

    }

    @Override
    public int remove(PositionUserPO positionUser, String id, Connection conn) throws Exception {
        int count = MySQLDao.remove(positionUser,id,conn);
        return count;
    }

    @Override
    public List<PositionUserPO> search(PositionUserPO positionUser, Class<PositionUserPO> positionUserPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws Exception {
        List<PositionUserPO> positionList = MySQLDao.search(positionUser, PositionUserPO.class,conditions, queryType, conn);

        return positionList;
    }

    @Override
    public void insertOrUpdate(PositionUserPO positionUser, Connection conn)  throws Exception{
        MySQLDao.insertOrUpdate(positionUser, conn);
    }

    @Override
    public List<PositionUserPO> searchByPosition(PositionPO position, Connection conn) throws Exception{

        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("12ds14q2");
        databaseSQL.addParameter4All("positionId",position.getId());
        databaseSQL.initSQL();
        List<PositionUserPO> search = MySQLDao.search(databaseSQL, PositionUserPO.class, conn);

        return search;
    }

    @Override
    public List<PositionUserPO>
    searchByPositionAndUser(PositionUserPO positionUserPO, Class<PositionUserPO> positionUserPOClass, Connection conn) throws Exception {

        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("12ds14q4");
        databaseSQL.addParameter4All("positionId",positionUserPO.getPositionId());
        databaseSQL.addParameter4All("userId",positionUserPO.getUserId());
        databaseSQL.initSQL();
        List<PositionUserPO> search = MySQLDao.search(databaseSQL, PositionUserPO.class, conn);

        return search;
    }


    /**
     * @description 通过 userId 查询权限表
     *
     *
     *  searchByUserId
     *
     *  getListPositionUserPOByUserId
     *
     * @author 徐明煜
     * @date 2018/12/17 16:30
     * @param userId
     * @param positionUserPOClass
     * @param conn
     * @return java.util.List<com.youngbook.entity.po.system.PositionUserPO>
     * @throws Exception
     */
    public List<PositionUserPO> getListPositionUserPOByUserId(String userId, Class<PositionUserPO> positionUserPOClass, Connection conn) throws Exception {

        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("2A11125");
        databaseSQL.addParameter4All("userId", userId);
        databaseSQL.initSQL();
        List<PositionUserPO> search = MySQLDao.search(databaseSQL, PositionUserPO.class, conn);
        return search;
    }
}
