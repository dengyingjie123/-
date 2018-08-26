package com.youngbook.dao.system;

import com.youngbook.entity.po.KVPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 3/20/2017.
 */
public interface IKVDao {
    public KVPO loadKVPO(String k, String groupName, Connection conn) throws Exception;
    public List<KVPO> getListKVPO(KVPO kvpo, Connection conn) throws Exception;
}
