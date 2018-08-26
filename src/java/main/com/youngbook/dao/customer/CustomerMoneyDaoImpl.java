package com.youngbook.dao.customer;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerMoneyPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("customerMoneyDao")
public class CustomerMoneyDaoImpl implements ICustomerMoneyDao {

    /**
     * 修改客户的可用资金
     *
     * @param money
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer updateAvailableMoney(Double money, String customerId, Connection conn) throws Exception {

        // 查询
        CustomerMoneyPO customerMoney = new CustomerMoneyPO();
        customerMoney.setCustomerId(customerId);
        customerMoney.setState(Config.STATE_CURRENT);
        customerMoney = MySQLDao.load(customerMoney, CustomerMoneyPO.class, conn);

        // 新增或修改
        if(customerMoney == null) {
            customerMoney = new CustomerMoneyPO();
            customerMoney.setCustomerId(customerId);
        }
        customerMoney.setAvailableMoney(money + customerMoney.getAvailableMoney());
        // 返回
        return insertOrUpdate4W(customerMoney, conn);
    }


    // 网站：新增或修改客户资金记录
    public int insertOrUpdate4W (CustomerMoneyPO customerMoney, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (customerMoney.getId().equals("")) {
            customerMoney.setSid(MySQLDao.getMaxSid("CRM_CustomerMoney", conn));
            customerMoney.setId(IdUtils.getUUID32());
            customerMoney.setState(Config.STATE_CURRENT);
            customerMoney.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            customerMoney.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerMoney, conn);
        }
        // 更新
        else {
            CustomerMoneyPO temp = new CustomerMoneyPO();
            temp.setSid(customerMoney.getSid());
            temp = MySQLDao.load(temp, CustomerMoneyPO.class, conn);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerMoney.setSid(MySQLDao.getMaxSid("CRM_CustomerMoney", conn));
                customerMoney.setState(Config.STATE_CURRENT);
                customerMoney.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                customerMoney.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerMoney, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }
}
