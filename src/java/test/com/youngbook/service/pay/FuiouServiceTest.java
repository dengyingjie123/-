package com.youngbook.service.pay;

import com.youngbook.common.Database;
import com.youngbook.common.KVObjects;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.core.APICommandPO;
import com.youngbook.entity.po.pay.APICommandStatus;
import com.youngbook.service.core.APICommandService;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by Lee on 11/25/2017.
 */
public class FuiouServiceTest {

    APICommandService apiCommandService = Config.getBeanByName("apiCommandService", APICommandService.class);

    FuiouService fuiouService = Config.getBeanByName("fuiouService", FuiouService.class);

    @Test
    public void dealMobilePayCallback() throws Exception {

        Connection conn = Config.getConnection();
        try {

            conn.setAutoCommit(false);

            KVObjects parameters = new KVObjects();
            parameters.addItem("VERSION", "2.0");
            parameters.addItem("TYPE", "10");
            parameters.addItem("RESPONSECODE", "1111");
            parameters.addItem("RESPONSEMSG", "ok");
            parameters.addItem("MCHNTCD", "00000");
            parameters.addItem("MCHNTORDERID", "B4F8B762197543A59394C4BA07A223DB");
            parameters.addItem("ORDERID", "ORDERID-0000");
            parameters.addItem("BANKCARD", "1234567890");
            parameters.addItem("AMT", "50000");
            parameters.addItem("SIGN", "000");

            APICommandPO apiCommandPO = new APICommandPO();
            apiCommandPO.setBizId(parameters.getItem("MCHNTORDERID").toString());
            apiCommandPO.setRemain01(parameters.getItem("ORDERID").toString());
            apiCommandPO.setCommands(StringUtils.buildUrlParameters(parameters));
            apiCommandPO.setCallbackCode(parameters.getItem("RESPONSECODE").toString());
            apiCommandPO.setCallbackMessage(parameters.getItem("RESPONSEMSG").toString());

            apiCommandPO.setStatus(APICommandStatus.UNDEAL);
            apiCommandPO.setCommandType(3);

            apiCommandService.receiveFuiouMobilePay(apiCommandPO, conn);

            fuiouService.dealMobilePayCallback(conn);

            conn.commit();
        }
        catch (Exception e) {

            conn.rollback();

            throw e;
        }
        finally {
            Database.close(conn);
        }


    }

}