package com.youngbook.service.system;

import com.youngbook.common.Database;
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


    public static void main(String [] args) throws Exception {

        KVService kvService = Config.getBeanByName("kvService", KVService.class);

        KVObjects kvObjects = new KVObjects();

        kvObjects.addItem("1000000", "邮储银行");
        kvObjects.addItem("1020000", "工商银行");
        kvObjects.addItem("1030000", "农业银行");
        kvObjects.addItem("1040000", "中国银行");
        kvObjects.addItem("1050000", "建设银行");
        kvObjects.addItem("3010000", "交通银行");
        kvObjects.addItem("3020000", "中信银行");
        kvObjects.addItem("3030000", "光大银行");
        kvObjects.addItem("3040000", "华夏银行");
        kvObjects.addItem("3050000", "民生银行");
        kvObjects.addItem("3060000", "广发银行");
        kvObjects.addItem("3080000", "招商银行");
        kvObjects.addItem("3090000", "兴业银行");
        kvObjects.addItem("3100000", "浦发银行");
        kvObjects.addItem("4012900", "上海银行");
        kvObjects.addItem("4031000", "北京银行");
        kvObjects.addItem("4105840", "平安银行");
        kvObjects.addItem("4240001","南京银行");
        kvObjects.addItem("14012900", "上海农商银行");

        Connection conn = Config.getConnection();
        try {
            for (int i = 0; i < kvObjects.size(); i++) {
                KVObject kvObject = kvObjects.get(i);
                KVPO kvpo = new KVPO();

                kvpo.setK(kvObject.getKeyStringValue());
                kvpo.setV(kvObject.getValueStringValue());
                kvpo.setGroupName("AllinpayCircleBankCode");
                kvService.insertOrUpdate(kvpo, conn);
            }
        }catch (Exception e) {
            throw e;
        }finally {
            Database.close(conn);
        }



    }

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
