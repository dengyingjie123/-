package com.youngbook.service.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.IPositionUserDao;
import com.youngbook.entity.po.system.PositionUserPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component("positionUserService")
public class PositionUserService extends BaseService {

    @Autowired
    IPositionUserDao positionUserDao;

    public void setDefaultFinanceCircle(String userId, Connection conn) throws Exception {
        positionUserDao.setDefaultFinanceCircle(userId, conn);
    }

    public PositionUserPO loadPositionUserPO(String id) throws Exception{

        PositionUserPO po = new PositionUserPO();
        po.setId(id);
        po = MySQLDao.load(po, PositionUserPO.class);

        return po;
    }

    public int remove(PositionUserPO positionUser, String id, Connection conn) throws Exception {

        return positionUserDao.remove(positionUser,id,conn);


    }

    public List<PositionUserPO> search(PositionUserPO positionUser, Class<PositionUserPO> positionUserPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws  Exception
    {

        return positionUserDao.search(positionUser, PositionUserPO.class,conditions, queryType, conn);

    }

    public void insertOrUpdate(PositionUserPO positionUser, Connection conn) throws Exception {
        positionUserDao.insertOrUpdate(positionUser, conn);
    }

    public List<PositionUserPO> searchByPositionAndUser(PositionUserPO positionUserPO, Class<PositionUserPO> positionUserPOClass, Connection conn)throws Exception {
        return positionUserDao.searchByPositionAndUser(positionUserPO,positionUserPOClass,conn);
    }


    public List<PositionUserPO> searchByUserId(String userId, Class<PositionUserPO> positionUserPOClass, Connection conn)throws Exception {
        return positionUserDao.searchByUserID(userId, positionUserPOClass, conn);
    }
}
