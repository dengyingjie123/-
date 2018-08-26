package com.youngbook.service.sale;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;

public class PaymentPlanServiceTest extends TestCase {

    @Test
    public void testDoPayment() throws Exception {
        PaymentPlanService service = new PaymentPlanService();

        Connection conn = Config.getConnection();
        try {
            String paymentPlanId = "1FBE32B220B24C4EB96EAE5A9215EF32";
            service.doPayment(paymentPlanId, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }
}