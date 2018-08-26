package com.youngbook.dao.customer;

import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerAuthenticationStatusPO;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/28.
 */
public interface ICustomerAuthenticationStatusDao {
    public Integer saveAuthenticationStatus(String customerId, Integer authenticationType, Connection conn) throws Exception;
    public CustomerAuthenticationStatusPO loadByCustomerId(String id, Connection conn) throws Exception;

    public int insertOrUpdate(CustomerAuthenticationStatusPO customerAuthenticationStatus, UserPO user, Connection conn) throws Exception;

}
