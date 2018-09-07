package com.youngbook.dao.customer;

import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/31.
 */
@Component("customerCertificateDao")
public class CustomerCertificateDaoImpl implements ICustomerCertificateDao {

    /**
     * 修改客户的证件信息
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月18日
     *
     * @param customerId
     * @param type
     * @param number
     * @param userId
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer insertOrUpdateCertificate(String customerId, String type, String number, String userId, Connection conn) throws Exception {

        CustomerCertificatePO customerCertificatePO = new CustomerCertificatePO();
        customerCertificatePO.setCustomerId(customerId);
        customerCertificatePO.setState(Config.STATE_CURRENT);
        customerCertificatePO = MySQLDao.load(customerCertificatePO, CustomerCertificatePO.class, conn);

        if(customerCertificatePO == null) {
            customerCertificatePO = new CustomerCertificatePO();
        }

        customerCertificatePO.setCustomerId(customerId); //用户编号
        customerCertificatePO.setName(type);
        customerCertificatePO.setNumber(number);

        return MySQLDao.insertOrUpdate(customerCertificatePO, userId, conn);
    }

    public int delete(CustomerCertificatePO customerCertificate, String userId, Connection conn) throws Exception {

        if (customerCertificate != null) {
            MySQLDao.remove(customerCertificate, userId, conn);
        }

        return 1;
    }


    /**
     * 加载客户的证件信息
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年11月27日
     *
     * 修改：邓超
     * 内容：从 CustomerAuthenticationStatusService 移动代码至此
     * 时间：2016年5月18日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerCertificatePO loadByCustomerId(String customerId,Connection conn) throws Exception {
        CustomerCertificatePO customerCertificate = new CustomerCertificatePO();
        customerCertificate.setCustomerId(customerId);
        customerCertificate.setState(Config.STATE_CURRENT);
        customerCertificate = MySQLDao.load(customerCertificate, CustomerCertificatePO.class, conn);
        return customerCertificate;
    }
}
