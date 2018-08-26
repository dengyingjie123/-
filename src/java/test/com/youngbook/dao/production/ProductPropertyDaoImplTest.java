package com.youngbook.dao.production;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml"})
public class ProductPropertyDaoImplTest {

    @Autowired
    private IProductPropertyDao productPropertyDao;

    private Connection conn;



    @org.junit.Before
    public void initConnection() throws Exception {
        if (conn == null) {
            conn = Config.getConnection();
        }
    }

    @org.junit.After
    public void afterAll() throws Exception {
        Database.close(conn);
    }

    @Test
    public void testGetPropertiesByProductId() throws Exception {
        productPropertyDao.getPropertiesByProductId("112", 1,10, conn);
    }
}