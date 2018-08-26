package com.youngbook.dao.core;

import com.youngbook.entity.po.core.APICommandPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 11/23/2017.
 */
public interface IAPICommandDao {
    public List<APICommandPO> loadByBIzId(String bizId, Connection conn) throws Exception;
    public APICommandPO buildFuiouMobilePay(String bizId, String commands, Connection conn, String ... remains) throws Exception;
}
