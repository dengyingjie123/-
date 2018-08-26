package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.entity.po.DepartmentPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml"})
public class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    @Test
    public void testGetDepartments4FortuneCenter() throws Exception {

        Connection conn = Config.getConnection();
        try {
            conn.setAutoCommit(false);

            DepartmentPO departmentPO = new DepartmentPO();
            departmentPO.setTypeID("14511");
            String json = departmentService.getDepartments4FortuneCenter(departmentPO, conn);

            System.out.println(json);

            conn.commit();
        }
        catch (Exception e) {

            conn.rollback();
            throw e;
        }
        finally {
            Database.close(conn);
        }


    }
}