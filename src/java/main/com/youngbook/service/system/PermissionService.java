package com.youngbook.service.system;

import com.youngbook.dao.system.PermissionDao;
import com.youngbook.entity.po.PermissionPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component("permissionService")
public class PermissionService extends BaseService {

    private PermissionDao permissionDao;

    public List<PermissionPO> listById(String id, Connection conn) throws Exception{
        return permissionDao.listById(id,conn);
    }
}
