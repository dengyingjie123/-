package com.youngbook.dao.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.QueryType;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.system.PositionUserPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 10/25/2017.
 */
public interface IPositionUserDao {

    void setDefaultFinanceCircle(String userId, Connection conn) throws Exception;

    int remove(PositionUserPO positionUser, String id, Connection conn) throws Exception;

    List<PositionUserPO> search(PositionUserPO positionUser, Class<PositionUserPO> positionUserPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws Exception;

    void insertOrUpdate(PositionUserPO positionUser, Connection conn) throws Exception;

    List<PositionUserPO> searchByPosition(PositionPO position, Connection conn) throws Exception;

    List<PositionUserPO> searchByPositionAndUser(PositionUserPO positionUserPO, Class<PositionUserPO> positionUserPOClass, Connection conn) throws Exception;

    List<PositionUserPO> getListPositionUserPOByUserId(String userId, Class<PositionUserPO> positionUserPOClass, Connection conn) throws Exception;
}
