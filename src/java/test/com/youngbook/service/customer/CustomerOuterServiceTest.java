package com.youngbook.service.customer;

import com.youngbook.common.config.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class CustomerOuterServiceTest {

    private CustomerOuterService service;
    Connection conn;

    @Before
    public void before() throws Exception {
        service = new CustomerOuterService();
        conn = Config.getConnection();
    }

    @After
    public void after() throws Exception {}


    @Test
    public void testAdd() throws Exception {
        //service.add("邓超", "1", "18007550150", "1954-12-05", "我的备注内容", "我的属性", "441322195412052317", "6225887867377315", "asdfghjklqwertyuiopmnbvcxzasdfgh", conn);
    }

}
