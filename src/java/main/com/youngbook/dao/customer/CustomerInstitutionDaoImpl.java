package com.youngbook.dao.customer;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerInstitutionPO;
import com.youngbook.entity.vo.customer.CustomerInstitutionVO;
import com.youngbook.service.customer.CustomerInstitutionService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("customerInstitutionDao")
public class CustomerInstitutionDaoImpl implements ICustomerInstitutionDao {

    public CustomerInstitutionPO loadByCustomerInstitutionId(String customerInstitutionId, Connection conn) throws Exception {

        CustomerInstitutionPO customerInstitutionPO = new CustomerInstitutionPO();
        customerInstitutionPO.setId(customerInstitutionId);
        customerInstitutionPO.setState(Config.STATE_CURRENT);

        customerInstitutionPO = MySQLDao.load(customerInstitutionPO, CustomerInstitutionPO.class, conn);

        return customerInstitutionPO;
    }


    /**
     * 团队管理员查看机构客户
     * @param customerInstitutionVO
     * @param conditions
     * @param currentPage
     * @param showRowCount
     * @param userId
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager listCustomers4DistributionToManagedSaleGroup(CustomerInstitutionVO customerInstitutionVO, List<KVObject> conditions, int currentPage, int showRowCount, String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得用户编号").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("FFC01804");
        dbSQL.addParameter4All("userId", userId);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), customerInstitutionVO, conditions, currentPage, showRowCount, queryType, conn);

        return pager;
    }


    public Pager getPagerCustomers4All(CustomerInstitutionVO customerInstitutionVO, int currentPage, int showRowCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("775D1810");
        dbSQL.addParameter4All("name", customerInstitutionVO.getName());
        dbSQL.initSQL();
        dbSQL.init4Pager();

        Pager pager = Pager.search(dbSQL, customerInstitutionVO, currentPage, showRowCount, conn);

        return pager;
    }


    /**
     * 查询分配给自己的机构客户
     * @param customerInstitutionVO
     * @param conditions
     * @param currentPage
     * @param showRowCount
     * @param userId
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager listCustomers4DistributionToMe(CustomerInstitutionVO customerInstitutionVO, List<KVObject> conditions, int currentPage, int showRowCount, String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得用户编号").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("775D1810");
        dbSQL.addParameter4All("userId", userId);
        dbSQL.initSQL();
        dbSQL.init4Pager();

        Pager pager = Pager.search(dbSQL, customerInstitutionVO, currentPage, showRowCount, conn);

        return pager;
    }
}
