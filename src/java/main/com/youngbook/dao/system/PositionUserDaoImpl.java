package com.youngbook.dao.system;

import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.PositionUserPO;
import com.youngbook.entity.vo.system.PositionUserVO;
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


    /**
     * 增肌注释
     *
     * getPagerPositionUserVO
     *
     * 模糊查询按文档方式
     *
     * 命名
     * @param positionUserVO
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return
     * @throws Exception
     */
    @Override
    public Pager showList(PositionUserVO positionUserVO, int currentPage, int showRowCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("822A1600");
        dbSQL.addParameter4All("userName",positionUserVO.getUserName());
        dbSQL.addParameter4All("mobile",positionUserVO.getMobile());
        dbSQL.initSQL();
        dbSQL.init4Pager();

        Pager search = MySQLDao.search(dbSQL, positionUserVO, null, currentPage, showRowCount, null, conn);

        return search;
    }
}
