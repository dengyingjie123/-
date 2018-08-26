package com.youngbook.dao.customer;

import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerCertificatePO;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/31.
 */
public interface ICustomerCertificateDao {

    public Integer insertOrUpdateCertificate(String customerId, String type, String number, String userId, Connection conn) throws Exception;
    public int delete(CustomerCertificatePO customerCertificate, String userId, Connection conn) throws Exception;
    public CustomerCertificatePO loadByCustomerId(String customerId,Connection conn) throws Exception;
}
