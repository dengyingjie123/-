package com.youngbook.service.core;

import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.core.APICommandPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 2016/2/28.
 */
@Component("apiCommandService")
public class APICommandService extends BaseService {

    @Autowired
    ILogDao logDao;

    public APICommandPO receiveFuiouMobilePay(APICommandPO apiCommandPO, Connection conn) throws Exception {

        String name = "富友支付-手机支付-接收";

        apiCommandPO.setAPIType("富友支付");
        apiCommandPO.setAPIName(name);
        apiCommandPO.setAPIURL(Config.getSystemConfig("fuiou.pay.mobile.send.url"));

        MySQLDao.insertOrUpdate(apiCommandPO, conn);

        logDao.save("富友支付", name, "【commandId="+apiCommandPO.getId()+"】【"+apiCommandPO.getCallbackMessage() +"】【bizId="+apiCommandPO.getBizId()+"】", conn);

        return apiCommandPO;
    }


    public APICommandPO receiveFuiouPCPay(APICommandPO apiCommandPO, Connection conn) throws Exception {

        String name = "富友支付-网关支付-接收";

        apiCommandPO.setAPIType("富友支付");
        apiCommandPO.setAPIName(name);
        apiCommandPO.setAPIURL(Config.getSystemConfig("fuiou.pay.pc.send.url"));

        MySQLDao.insertOrUpdate(apiCommandPO, conn);

        logDao.save("富友支付", name, "【commandId="+apiCommandPO.getId()+"】【"+apiCommandPO.getCallbackMessage() +"】【bizId="+apiCommandPO.getBizId()+"】", conn);

        return apiCommandPO;
    }


    public void save4AllinpaySend(String bizId, String commands, Connection conn) throws Exception {
        APICommandPO apiCommandPO = new APICommandPO();
        apiCommandPO.setAPIType("通联支付-代付-发送");
        apiCommandPO.setBizId(bizId);
        apiCommandPO.setCommands(commands);
        MySQLDao.insertOrUpdate(apiCommandPO, conn);
    }

    public void save4AllinpayReceive(String bizId, String commands, Connection conn) throws Exception {
        APICommandPO apiCommandPO = new APICommandPO();
        apiCommandPO.setAPIType("通联支付-代付-接收");
        apiCommandPO.setBizId(bizId);
        apiCommandPO.setCommands(commands);
        MySQLDao.insertOrUpdate(apiCommandPO, conn);
    }
}
