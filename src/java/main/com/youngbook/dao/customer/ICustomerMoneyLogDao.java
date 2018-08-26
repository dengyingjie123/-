package com.youngbook.dao.customer;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/28.
 */
public interface ICustomerMoneyLogDao {
    public Integer insertCustomerMoneyLog(String type, String content, String status, String orderId, String customerId, Connection conn) throws Exception;
}
