package com.youngbook.dao.customer;

import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerMoneyLogPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("customerMoneyLogDao")
public class CustomerMoneyLogDaoImpl implements ICustomerMoneyLogDao {


    /**
     * 插入新的客户资金日志
     * @param type
     * @param content
     * @param status
     * @param orderId
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer insertCustomerMoneyLog(String type, String content, String status, String orderId, String customerId, Connection conn) throws Exception {

        CustomerMoneyLogPO moneyLog = new CustomerMoneyLogPO();
        moneyLog.setType(type);
        moneyLog.setContent(content);
        moneyLog.setStatus(status);
        moneyLog.setBizId(orderId);
        moneyLog.setCustomerId(customerId);
        return MySQLDao.insertOrUpdate(moneyLog, conn);

    }
}
