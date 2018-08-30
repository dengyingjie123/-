package com.youngbook.service.customer;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by leevits on 8/31/2018.
 */
public class CustomerDistributionServiceTest {

    CustomerDistributionService customerDistributionService = Config.getBeanByName("customerDistributionService", CustomerDistributionService.class);

    @Test
    public void transferCustomer() throws Exception {

        Connection conn = Config.getConnection();
        try {
            conn.setAutoCommit(false);
            customerDistributionService.transferCustomer("692A5CCCF16243168B96074A4AE8262C", "D9FC4875333E4739A0065881A9519B5C", "0000", conn);
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