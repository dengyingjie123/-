package com.youngbook.service.production;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import junit.framework.TestCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

public class ProductionServiceTest extends TestCase {

    @Autowired
    ProductionService productionService;

    public void testUnsellProductionManualDo() throws Exception {

        Connection conn = Config.getConnection();
        try {
            productionService.unsellProductionManualDo("NO20151123130532", 0,0, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }
}