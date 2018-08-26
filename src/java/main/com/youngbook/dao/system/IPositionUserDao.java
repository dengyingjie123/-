package com.youngbook.dao.system;

import java.sql.Connection;

/**
 * Created by Lee on 10/25/2017.
 */
public interface IPositionUserDao {

    public void setDefaultFinanceCircle(String userId, Connection conn) throws Exception;
}
