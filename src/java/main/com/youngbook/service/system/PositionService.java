package com.youngbook.service.system;

import com.youngbook.common.*;
import com.youngbook.dao.system.IPositionDao;
import com.youngbook.dao.system.IPositionPermissionDao;
import com.youngbook.dao.system.IPositionUserDao;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.PositionPermissionPO;
import com.youngbook.entity.po.system.PositionUserPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Huchaoyi on 2018/11/24.
 */

@Component("positionService")
public class PositionService extends BaseService {

    @Autowired
    IPositionDao positionDao;
    @Autowired
    IPositionUserDao positionUserDao;
    @Autowired
    IPositionPermissionDao positionPermissionDao;

    public int remove(PositionPO position, String id, Connection conn) throws Exception {

        //通过position查询PositionUserPO，在遍历后更改状态
        List<PositionUserPO> positionUserList = positionUserDao.searchByPosition(position,conn);
        if (null != positionUserList && positionUserList.size() > 0){
            for (PositionUserPO positionUser:positionUserList) {
                positionUserDao.remove(positionUser,id,conn);
            }
        }
        //通过position查询positionPermissionPO，在遍历后更改状态
        List<PositionPermissionPO> positionPermissionList = positionPermissionDao.searchByPosition(position,conn);
        if (null != positionPermissionList && positionPermissionList.size() > 0){
            for (PositionPermissionPO positionPermission:positionPermissionList) {
                positionPermissionDao.remove(positionPermission,id,conn);
            }
        }

        return positionDao.remove(position,id,conn);

    }

    public List<PositionPO> search(PositionPO position, Class<PositionPO> PositionPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws  Exception
    {

        return positionDao.search(position, PositionPO.class,conditions, queryType, conn);

    }

    public void insertOrUpdate(PositionPO position, Connection conn) throws Exception{

        positionDao.insertOrUpdate(position, conn);
    }
}