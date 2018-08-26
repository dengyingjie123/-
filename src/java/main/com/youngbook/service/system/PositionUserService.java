package com.youngbook.service.system;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.IPositionUserDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.PositionUserPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}
