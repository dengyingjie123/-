package com.youngbook.dao.system;

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

}
