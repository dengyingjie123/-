package com.youngbook.service.customer;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.customer.CustomerMoneyPO;
import com.youngbook.entity.wvo.customer.CustomerPersonalWVO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by 姚章鹏 on 2015/7/9.
 */
@Component("customerInfoService")
public class CustomerInfoService extends BaseService{
    /**
     * 创建人：姚章鹏
     * 内容：查询交易密码是否存在
     */
    public CustomerPersonalWVO getTransactionPasswordWVO(String loginUserId) throws Exception{
        CustomerPersonalWVO wvo = null;
        String sql = "select * from crm_customerpersonal p where p.state = 0  and  id='"+ Database.encodeSQL(loginUserId)+"'";
        List<CustomerPersonalWVO> list = MySQLDao.query(sql, CustomerPersonalWVO.class, null);
        if (list.size() == 1) {
            wvo = list.get(0);
        }
        return wvo;

    }

    /**
     * 查询客户资金 可用余额是都满足
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerMoneyPO getCustomerMoney(String customerId, Connection conn) throws Exception {
        CustomerMoneyPO customerMoney = new CustomerMoneyPO();
        customerMoney.setState(Config.STATE_CURRENT);
        customerMoney.setCustomerId(customerId);
        customerMoney = MySQLDao.load(customerMoney, CustomerMoneyPO.class, conn);
        return customerMoney;

    }

    /**
     *根据id查询客户账户
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerAccountPO getCustomerAccount(String customerId, Connection conn) throws Exception {
        CustomerAccountPO customerAccount = new CustomerAccountPO();
        customerAccount.setState(Config.STATE_CURRENT);
        customerAccount.setCustomerId(customerId);

        List<CustomerAccountPO> listCustomerAccounts = MySQLDao.query(customerAccount, CustomerAccountPO.class, conn);

        if (listCustomerAccounts != null) {
            return listCustomerAccounts.get(0);
        }

        return null;

    }

    /**
     *根据id和卡号查询客户账户
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerAccountPO getCustomerAccountCard(String customerId,String card, Connection conn) throws Exception {
        CustomerAccountPO customerAccount = new CustomerAccountPO();
        customerAccount.setState(Config.STATE_CURRENT);
        customerAccount.setCustomerId(customerId);
        customerAccount.setNumber(card);
        customerAccount = MySQLDao.load(customerAccount, CustomerAccountPO.class, conn);
        return customerAccount;

    }

}
