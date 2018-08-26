package com.youngbook.dao.allinpay;

import com.youngbook.entity.po.customer.CustomerBankAuthenticationPO;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/31.
 */
public interface IAllinpayAuthenticationDao {

    public CustomerBankAuthenticationPO getBankAuthenticationStatus(String customerId, int authenticationStatus, Connection conn) throws Exception;
}
