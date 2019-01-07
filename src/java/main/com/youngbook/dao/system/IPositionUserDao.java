package com.youngbook.dao.system;

import com.youngbook.common.Pager;
import com.youngbook.entity.vo.system.PositionUserVO;

import java.sql.Connection;

/**
 * Created by Lee on 10/25/2017.
 */
public interface IPositionUserDao {

    void setDefaultFinanceCircle(String userId, Connection conn) throws Exception;

    Pager getListPositionUser(PositionUserVO positionUserVO, int currentPage, int showRowCount, Connection conn) throws Exception;
}
