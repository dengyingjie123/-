package com.youngbook.dao.sale;

import com.youngbook.common.Database;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.security.RunAs;

import java.sql.Connection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml"})
public class PaymentPlanDaoImplTest {

    @Autowired
    IPaymentPlanDao paymentPlanDao;

    Connection conn = null;

    ApplicationContext context;

    @Before
    public void before() throws Exception {
        conn = Config.getConnection();

        context = new ClassPathXmlApplicationContext("classpath:spring/spring-service.xml");
    }

    @After
    public void after() throws Exception {
        Database.close(conn);
    }

    @Test
    public void testGetPaymentPlansVOByDateAndStatus() throws Exception {

        Resource resource = context.getResource("classpath:config/common/system.xml");

        System.out.println(resource.getFile().getAbsolutePath());

//        Pager p = paymentPlanDao.getPaymentPlansVOByDateAndStatus("2016-6-01", "2016-06-15", 1,10, conn);

//        System.out.println(p.getData().toString());
    }
}