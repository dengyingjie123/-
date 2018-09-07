package com.youngbook.dao.customer;

import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerDistributionPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/6/2.
 */
public interface ICustomerDistributionDao {
    public int distributeCustomer(String customerId, String userId, String salesmanGroupId, String userDepartmentId, int customerPersonalOrInstitutionType, boolean defaultPassCheck, Connection conn) throws Exception;
    public int distributeToSalesman(CustomerDistributionPO customerDistribution, String operatorId, Connection conn) throws Exception;
    public int remove (String customerPersonalId, String userId,Connection conn) throws  Exception;
    public List<CustomerDistributionPO> getListCustomerDistrbutionPO (String customerId, Connection conn) throws Exception;
}
