package com.youngbook.service.customer;

import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4W;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerWithdrawVerifyPO;
import com.youngbook.service.BaseService;

import java.sql.Connection;

/**
 * Created by 张舜清 on 7/19/2015.
 */
public class CustomerWithdrawVerifyService extends BaseService {
    /**
     * 添加或修改数据，并修改数据状态
     * @param customerWithdrawVerify
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(CustomerWithdrawVerifyPO customerWithdrawVerify, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (customerWithdrawVerify.getId().equals("")) {
            customerWithdrawVerify.setSid(MySQLDao.getMaxSid("CRM_CustomerWithdrawVerify", conn));
            customerWithdrawVerify.setId(IdUtils.getUUID32());
            customerWithdrawVerify.setState(Config.STATE_CURRENT);
            customerWithdrawVerify.setOperatorId(user.getId());
            customerWithdrawVerify.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerWithdrawVerify, conn);
        }
        // 更新
        else {
            CustomerWithdrawVerifyPO temp = new CustomerWithdrawVerifyPO();
            temp.setSid(customerWithdrawVerify.getSid());
            temp = MySQLDao.load(temp, CustomerWithdrawVerifyPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerWithdrawVerify.setSid(MySQLDao.getMaxSid("CRM_CustomerWithdrawVerify", conn));
                customerWithdrawVerify.setState(Config.STATE_CURRENT);
                customerWithdrawVerify.setOperatorId(user.getId());
                customerWithdrawVerify.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerWithdrawVerify, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 根据制定的ID获取数据
     * @param id
     * @return
     * @throws Exception
     */
    public CustomerWithdrawVerifyPO loadCustomerWithdrawVerifyPO(String id) throws Exception{
        CustomerWithdrawVerifyPO po = new CustomerWithdrawVerifyPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, CustomerWithdrawVerifyPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     * @param customerWithdrawVerify
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(CustomerWithdrawVerifyPO customerWithdrawVerify, UserPO user, Connection conn) throws Exception {
        int count = 0;

        customerWithdrawVerify.setState(Config.STATE_CURRENT);
        customerWithdrawVerify = MySQLDao.load(customerWithdrawVerify, CustomerWithdrawVerifyPO.class);
        customerWithdrawVerify.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(customerWithdrawVerify, conn);
        if (count == 1) {
            customerWithdrawVerify.setSid(MySQLDao.getMaxSid("CRM_CustomerWithdrawVerify", conn));
            customerWithdrawVerify.setState(Config.STATE_DELETE);
            customerWithdrawVerify.setOperateTime(TimeUtils.getNow());
            customerWithdrawVerify.setOperatorId(user.getId());
            count = MySQLDao.insert(customerWithdrawVerify, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 创建人：张舜清
     * 时间：7/19/2015
     * 内容：前台客户提现完成后插入对应记录
     *
     * @param customerID
     * @param money
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertRecord4W(String customerID,Double money,Connection conn) throws Exception{
        int count = 0;
        CustomerWithdrawVerifyPO customerWithdrawVerify = new CustomerWithdrawVerifyPO();
        customerWithdrawVerify.setSid(MySQLDao.getMaxSid("CRM_CustomerWithdrawVerify", conn));
        customerWithdrawVerify.setId(IdUtils.getUUID32());
        customerWithdrawVerify.setState(Config.STATE_CURRENT);
        customerWithdrawVerify.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        customerWithdrawVerify.setOperateTime(TimeUtils.getNow());
        customerWithdrawVerify.setCustomerId(customerID);
        customerWithdrawVerify.setWithdrawMoney(money);
        count = MySQLDao.insert(customerWithdrawVerify,conn);
        return count;
    }
}
