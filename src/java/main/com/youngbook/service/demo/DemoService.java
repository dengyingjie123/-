package com.youngbook.service.demo;

import com.youngbook.common.Database;
import com.youngbook.dao.BaseDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.production.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionManager;
import java.sql.Connection;

/**
 * Created by Lee on 2016/5/25.
 */
@Component
public class DemoService {

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    ProductionService productionService;

    public void foo1() throws Exception {

        System.out.println(customerPersonalService);



        foo2();

    }

    public void foo2() throws Exception{

        System.out.println(customerPersonalService);
    }

}
