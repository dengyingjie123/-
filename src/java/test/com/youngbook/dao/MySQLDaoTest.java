package com.youngbook.dao;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.system.CodePO;
import com.youngbook.entity.po.system.LogPO;
import junit.framework.TestCase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MySQLDaoTest extends TestCase {

    public void testQueryx() throws Exception {

        // queryx(T object, Class<T> clazz, List<KVObject> conditions, QueryType queryType, Connection conn)
        Connection conn = Config.getConnection();
        try {
            CustomerPersonalPO customer = new CustomerPersonalPO();
            customer.setName("a");

            QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
            List<CustomerPersonalPO> customers = MySQLDao.query(customer, CustomerPersonalPO.class, null, queryType, conn);

            System.out.println("Size; " + customers.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Database.close(conn);
        }
    }


    public void testQueryx2() throws Exception {

        Connection conn = Config.getConnection();
        try {
            String sql = "select * from system_user where 1=1 and name like ?";
            KVObject paramter = new KVObject(1, null, "%a%");
            List<KVObject> parameters = new ArrayList<KVObject>();
            parameters.add(paramter);

            QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
            List<UserPO> customers = MySQLDao.search(sql, parameters, UserPO.class, null, conn);

            System.out.println("Size; " + customers.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Database.close(conn);
        }


    }

    public void testInsertOrUpdate() throws Exception {


        Connection conn = Config.getConnection();
        try {
            CustomerPersonalPO c = new CustomerPersonalPO();
            c.setId("ADD197CF4A274D7589977064E3F79272");
            c.setName("这是新数据1");
            MySQLDao.insertOrUpdate(c, "22123", conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    public void testRemove() throws Exception {
        Connection conn = Config.getConnection();
        try {
            CustomerPersonalPO c = new CustomerPersonalPO();
            c.setId("ADD197CF4A274D7589977064E3F79272");
            MySQLDao.remove(c, "123", conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }
}