package com.youngbook.dao.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.QueryType;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Huchaoyi on 2018/11/24.
 */
public interface IPositionDao {

    int remove(PositionPO position, String id, Connection conn) throws Exception;

    List<PositionPO> search(PositionPO position, Class<PositionPO> positionPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws Exception;

    void insertOrUpdate(PositionPO position, Connection conn) throws Exception;

    List<PositionPO> searchByDepartment(DepartmentPO department, Connection conn) throws Exception;
}
