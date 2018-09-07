package com.youngbook.dao.allinpay;

import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerBankAuthenticationPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/31.
 */
@Component("allinpayAuthenticationDao")
public class AllinpayAuthenticationDaoImpl implements IAllinpayAuthenticationDao {

    public CustomerBankAuthenticationPO getBankAuthenticationStatus(String customerId, int authenticationStatus, Connection conn) throws Exception {
        CustomerBankAuthenticationPO bankauthentication = new CustomerBankAuthenticationPO();
        bankauthentication.setState(Config.STATE_CURRENT);
        bankauthentication.setCustomerId(customerId);
        bankauthentication.setAuthenticationStatus(authenticationStatus); //客户认证状态
        bankauthentication = MySQLDao.load(bankauthentication, CustomerBankAuthenticationPO.class, conn);
        return bankauthentication;
    }
}
