package com.youngbook.dao.system;

import java.sql.Connection;

/**
 * Created by leevits on 7/1/2018.
 */
public interface ITokenDao {
    public int checkAndDestroyToken(String tokenString, String bizId, String bizType, String ip, Connection conn) throws Exception;
}
