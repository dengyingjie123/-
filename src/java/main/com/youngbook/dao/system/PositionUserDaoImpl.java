package com.youngbook.dao.system;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.PositionUserPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 10/25/2017.
 */
@Component("positionUserDao")
public class PositionUserDaoImpl implements IPositionUserDao {

    public void setDefaultFinanceCircle(String userId, Connection conn) throws Exception {

        String positionId = Config.getSystemConfig("financeCircle.position.default.id");
        PositionUserPO positionUserPO = new PositionUserPO();

        positionUserPO.setId(IdUtils.getUUID32());
        positionUserPO.setUserId(userId);
        positionUserPO.setPositionId(positionId);
        positionUserPO.setStates(1);

        MySQLDao.insert(positionUserPO, conn);

    }
}
