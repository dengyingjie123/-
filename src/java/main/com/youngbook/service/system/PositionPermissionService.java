package com.youngbook.service.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.QueryType;
import com.youngbook.dao.system.IPositionPermissionDao;
import com.youngbook.entity.po.PositionPermissionPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Huchaoyi on 2018/11/24.
 */

@Component("positionPermissionService")
public class PositionPermissionService extends BaseService {

    @Autowired
    IPositionPermissionDao positionPermissionDao;

    public int remove(PositionPermissionPO positionPermission, String id, Connection conn) throws Exception {

        return positionPermissionDao.remove(positionPermission,id,conn);


    }

    public List<PositionPermissionPO> search(PositionPermissionPO positionPermission, Class<PositionPermissionPO> positionPermissionPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws  Exception
    {

        return positionPermissionDao.search(positionPermission, PositionPermissionPO.class,conditions, queryType, conn);

    }

    public void insertOrUpdate(PositionPermissionPO positionPermission, Connection conn) throws Exception{

        positionPermissionDao.insertOrUpdate(positionPermission, conn);
    }
}
