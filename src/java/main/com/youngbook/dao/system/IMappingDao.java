package com.youngbook.dao.system;

import com.youngbook.entity.po.system.MappingPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by leevits on 7/22/2018.
 */
public interface IMappingDao {
    public MappingPO updateBasedOnBiz01(String biz01, String biz02, String type, Connection conn) throws Exception;
    public MappingPO updateBasedOnBiz02(String biz01, String biz02, String type, Connection conn) throws Exception;
    public MappingPO newMapping(String biz01, String biz02, String type, Connection conn) throws Exception;
    public List<MappingPO> search(String biz01, String biz02, String type, Connection conn) throws Exception;
}
