package com.youngbook.dao.customer;

import com.youngbook.entity.po.customer.CustomerMoneyPO;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/28.
 */
public interface ICustomerMoneyDao {
    public Integer updateAvailableMoney(Double money, String customerId, Connection conn) throws Exception;

    public int insertOrUpdate4W (CustomerMoneyPO customerMoney, Connection conn) throws Exception;
}
