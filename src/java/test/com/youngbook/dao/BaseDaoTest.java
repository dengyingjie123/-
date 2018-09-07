package com.youngbook.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
public class BaseDaoTest {
    @Resource
    BaseDao demoDao;

    @Test
    public void testSelect() throws Exception {
        demoDao.select();
    }
}