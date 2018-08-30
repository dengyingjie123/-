package com.youngbook.dao.customer;

import com.youngbook.common.MyException;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerDistributionPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/6/2.
 */
@Component("customerDistributionDao")

public class CustomerDistributionDaoImpl implements ICustomerDistributionDao {


    public int distributeCustomer(String customerId, String userId, String salesmanGroupId, String userDepartmentId, int customerPersonalOrInstitutionType, boolean defaultPassCheck, Connection conn) throws Exception {


        StringUtils.checkIsEmpty(salesmanGroupId, "无法找到销售组信息");
        StringUtils.checkIsEmpty(userDepartmentId, "无法找到销售所属的部门");


        CustomerDistributionPO distribution = new CustomerDistributionPO();
        distribution.setCustomerId(customerId);
        distribution.setSaleManId(userId);
        distribution.setStatus(defaultPassCheck ? 1 : 0); // 0 未审核, 1 审核
        distribution.setRemark(customerPersonalOrInstitutionType);
        distribution.setDepartmentId(userDepartmentId);
        distribution.setSaleGroupId(salesmanGroupId);


        int count = MySQLDao.insertOrUpdate(distribution, userId, conn);
        if (count != 1) {
            MyException.newInstance("客户分配失败").throwException();
        }


        return 1;

    }


    public int distributeToSalesman (CustomerDistributionPO customerDistribution, String operatorId, Connection conn) throws Exception {

        /**
         * 检查原有分配方案，如果存在已通过的分配，则无需新建
         */
        DatabaseSQL dbSQLCheck = DatabaseSQL.newInstance("0FF31808");
        dbSQLCheck.addParameter4All("customerId", customerDistribution.getCustomerId());
        dbSQLCheck.addParameter4All("saleManId", customerDistribution.getSaleManId());
        dbSQLCheck.initSQL();

        List<CustomerDistributionPO> list = MySQLDao.search(dbSQLCheck, CustomerDistributionPO.class, conn);
        if (list != null && list.size() > 0) {
            return 1;
        }


        /**
         * 执行分配操作
         */
        return MySQLDao.insertOrUpdate(customerDistribution, operatorId, conn);
    }


    public List<CustomerDistributionPO> getListCustomerDistrbutionPO (String customerId, Connection conn) throws Exception {

        /**
         * 检查原有分配方案，如果存在已通过的分配，则无需新建
         */
        DatabaseSQL dbSQLCheck = DatabaseSQL.newInstance("0FF31808");
        dbSQLCheck.addParameter4All("customerId", customerId);
        dbSQLCheck.initSQL();

        List<CustomerDistributionPO> list = MySQLDao.search(dbSQLCheck, CustomerDistributionPO.class, conn);
        return list;
    }


    public int remove (String customerPersonalId, String userId ,Connection conn) throws  Exception {

        /**
         * 检查分配有效性
         */
//        DatabaseSQL dbSQLCheck = new DatabaseSQL();
//        dbSQLCheck.newSQL("removeCheck", "CustomerDistributionDaoImplSQL", CustomerDistributionDaoImpl.class);
//        dbSQLCheck.addParameter4All("customerId", customerPersonalId);
//        dbSQLCheck.initSQL();
//
//        List<CustomerDistributionPO> list = MySQLDao.search(dbSQLCheck, CustomerDistributionPO.class, conn);
//
//
//        if (list == null || list.size() == 1) {
//            MyException.newInstance("无法删除客户分配，这是最后一条客户分配记录").throwException();
//        }


        /**
         * 执行删除分配操作
         */

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("58861808");
        dbSQL.addParameter4All("customerId", customerPersonalId);
        dbSQL.addParameter4All("saleManId", userId);
        dbSQL.initSQL();

        List<CustomerDistributionPO> distributionPOs = MySQLDao.search(dbSQL, CustomerDistributionPO.class, conn);

        for (int i = 0; distributionPOs != null && i < distributionPOs.size(); i++) {
            MySQLDao.remove(distributionPOs.get(i),"0000", conn);
        }

        return 1;
    }
}
