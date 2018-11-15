package com.youngbook.dao.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.QueryType;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.PositionPermissionPO;
import com.youngbook.entity.po.system.PositionUserPO;

import java.sql.Connection;
import java.util.List;

public interface IPositionPermissionDao {

    int remove(PositionPermissionPO positionPermission, String id, Connection conn) throws Exception;

    List<PositionPermissionPO> search(PositionPermissionPO positionPermission, Class<PositionPermissionPO> positionPermissionPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws Exception;

    void insertOrUpdate(PositionPermissionPO positionPermission, Connection conn) throws Exception;

    List<PositionPermissionPO> searchByPosition(PositionPO po, Connection conn) throws Exception;
}
