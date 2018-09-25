package com.youngbook.dao.customer;

import com.youngbook.common.MyException;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerMoneyLogPO;
import com.youngbook.entity.po.customer.CustomerMoneyLogType;
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
     * @param bizId
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerMoneyLogPO newCustomerMoneyLog(double principalMoney, double profitMoney, String type, String content, String status, String bizId, String customerId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(type)) {
            MyException.newInstance("无法获得日志类型").throwException();
        }

        if (type.equals(CustomerMoneyLogType.Deposit) || type.equals(CustomerMoneyLogType.Profit)) {
            principalMoney = Math.abs(principalMoney);
            profitMoney = Math.abs(profitMoney);
        }
        else if (type.equals(CustomerMoneyLogType.Buy) || type.equals(CustomerMoneyLogType.WithdrawOrPayment)) {
            principalMoney = 0 - Math.abs(principalMoney);
            profitMoney = 0 - Math.abs(profitMoney);
        }

        if (StringUtils.isEmpty(status)) {
            MyException.newInstance("无法获得资金日志类型").throwException();
        }

        int statusCheck = NumberUtils.parse2Int(status);

        if (!NumberUtils.checkNumberIn(statusCheck, 0, 1, 2, 3)) {
            MyException.newInstance("无法获得资金日志类型无效", "status=" + status).throwException();
        }


        CustomerMoneyLogPO moneyLog = new CustomerMoneyLogPO();
        moneyLog.setType(type);
        moneyLog.setContent(content);
        moneyLog.setStatus(status);
        moneyLog.setBizId(bizId);
        moneyLog.setCustomerId(customerId);
        moneyLog.setPrincipalMoney(principalMoney);
        moneyLog.setProfitMoney(profitMoney);
        MySQLDao.insertOrUpdate(moneyLog, conn);

        return moneyLog;
    }
}
