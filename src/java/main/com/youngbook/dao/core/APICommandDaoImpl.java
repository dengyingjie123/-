package com.youngbook.dao.core;

import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.core.APICommandPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 11/23/2017.
 */
@Component("apiCommandDao")
public class APICommandDaoImpl implements IAPICommandDao {

    public List<APICommandPO> loadByBIzId(String bizId, Connection conn) throws Exception {

        APICommandPO apiCommandPO = new APICommandPO();
        apiCommandPO.setBizId(bizId);
        apiCommandPO.setState(Config.STATE_CURRENT);

        List<APICommandPO> list = MySQLDao.search(apiCommandPO, APICommandPO.class, conn);

        return list;
    }


    public APICommandPO saveCommand(String apiType, String apiName, String bizId, String commands, int commandType, String url, String callbackCode, String callbackMessage, Connection conn) throws Exception {
        APICommandPO apiCommandPO = new APICommandPO();

        apiCommandPO.setAPIURL(url);
        apiCommandPO.setAPIType(apiType);
        apiCommandPO.setAPIName(apiName);

        apiCommandPO.setBizId(bizId);
        apiCommandPO.setCommands(commands);
        apiCommandPO.setCommandType(commandType);
        apiCommandPO.setCallbackCode(callbackCode);
        apiCommandPO.setCallbackMessage(callbackMessage);

        MySQLDao.insertOrUpdate(apiCommandPO, conn);

        return apiCommandPO;
    }

    public APICommandPO saveCommand(String apiType, String apiName, String bizId, String commands, int commandType, String url, String callbackCode, String callbackMessage) throws Exception {

        Connection conn = Config.getConnection();

        try {

            APICommandPO apiCommandPO = saveCommand(apiType, apiName, bizId, commands, commandType, url, callbackCode, callbackMessage, conn);

            return apiCommandPO;

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }


    public APICommandPO buildFuiouMobilePay(String bizId, String commands, Connection conn, String ... remains) throws Exception {

        if (remains != null && remains.length > 5) {
            MyException.newInstance("无法接受过多的保留数据").throwException();
        }

        APICommandPO apiCommandPO = new APICommandPO();

        for (int i = 0; remains != null && i < remains.length; i++) {
            switch (i) {
                case 0: apiCommandPO.setRemain01(remains[i]);break;
                case 1: apiCommandPO.setRemain02(remains[i]);break;
                case 2: apiCommandPO.setRemain03(remains[i]);break;
                case 3: apiCommandPO.setRemain04(remains[i]);break;
                case 4: apiCommandPO.setRemain05(remains[i]);break;
            }
        }


        apiCommandPO.setAPIType("富友支付");
        apiCommandPO.setAPIName("富友支付-手机支付-发送");
        apiCommandPO.setAPIURL(Config.getSystemConfig("fuiou.pay.mobile.send.url"));
        apiCommandPO.setCommandType(1);
        apiCommandPO.setBizId(bizId);
        apiCommandPO.setCommands(commands);
        MySQLDao.insertOrUpdate(apiCommandPO, conn);

        return apiCommandPO;
    }
}
