package com.youngbook.dao.customer;

import com.youngbook.entity.po.customer.CustomerMoneyLogPO;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/28.
 */
public interface ICustomerMoneyLogDao {
    public CustomerMoneyLogPO newCustomerMoneyLog(double principalMoney, double profitMoney, String type, String content, String status, String bizId, String customerId, Connection conn) throws Exception;
}
