package com.youngbook.dao.customer;

import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.po.customer.CustomerInstitutionPO;
import com.youngbook.entity.vo.customer.CustomerInstitutionVO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
public interface ICustomerInstitutionDao {
    public CustomerInstitutionPO loadByCustomerInstitutionId(String customerInstitutionId, Connection conn) throws Exception;
    public Pager listCustomers4DistributionToManagedSaleGroup(CustomerInstitutionVO customerInstitutionVO, List<KVObject> conditions, int currentPage, int showRowCount, String userId, Connection conn) throws Exception;
    public Pager listCustomers4DistributionToMe(CustomerInstitutionVO customerInstitutionVO, List<KVObject> conditions, int currentPage, int showRowCount, String userId, Connection conn) throws Exception;
    public Pager getPagerCustomers4All(CustomerInstitutionVO customerInstitutionVO, int currentPage, int showRowCount, Connection conn) throws Exception;
}
