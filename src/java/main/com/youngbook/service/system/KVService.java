package com.youngbook.service.system;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.IKVDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("kvService")
public class KVService extends BaseService {

    @Autowired
    IKVDao kvdao;

    /**
     * 通过 K 和 GroupName 获取 KV
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月24日
     *
     * @param k
     * @param groupName
     * @param conn
     * @return
     * @throws Exception
     */
    public KVPO load(String k, String groupName, Connection conn) throws Exception {

        return kvdao.loadKVPO(k, groupName, conn);

    }


    public List<KVPO> getListKVPO(KVPO kvpo, Connection conn) throws Exception {
        return kvdao.getListKVPO(kvpo, conn);
    }

    public KVPO loadById(String id, Connection conn) throws Exception {

        KVPO kvpo = new KVPO();
        kvpo.setID(id);
        kvpo.setState(Config.STATE_CURRENT);

        kvpo = MySQLDao.load(kvpo, KVPO.class);

        return kvpo;
    }


    public KVPO insertOrUpdate(KVPO kvpo, Connection conn) throws Exception {

        MySQLDao.insertOrUpdate(kvpo, conn);

        return kvpo;
    }

}
