package com.youngbook.service.demo;

import com.youngbook.service.BaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml"})
public class DemoServiceTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void testFoo1() throws Exception {
        demoService.foo1();
    }

    @Test
    public void testFoo2() throws Exception {

    }
}