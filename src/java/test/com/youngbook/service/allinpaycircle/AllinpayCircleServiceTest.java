package com.youngbook.service.allinpaycircle;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by leevits on 9/1/2018.
 */
public class AllinpayCircleServiceTest {

    AllinpayCircleService allinpayCircleService = Config.getBeanByName("allinpayCircleService", AllinpayCircleService.class);

    @Test
    public void dealRawData() throws Exception {
        Connection conn = Config.getConnection();
        try {
            allinpayCircleService.dealRawData(conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

}