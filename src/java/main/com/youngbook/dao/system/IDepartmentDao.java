package com.youngbook.dao.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.QueryType;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
public interface IDepartmentDao {
    public List<UserPositionInfoPO> getUserDepartmentInfo(String userId, Connection conn) throws Exception;
    public UserPositionInfoPO getDefaultUserPositionInfo(String userId, Connection conn) throws Exception;

    int remove(DepartmentPO department, String id,Connection conn) throws Exception;

    List<DepartmentPO> search(DepartmentPO department, Class<DepartmentPO> departmentPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws Exception;

    void insertOrUpdate(DepartmentPO department, Connection conn) throws Exception;
}
