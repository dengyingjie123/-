package com.youngbook.common.utils;

import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.sale.PaymentPlanType;
import junit.framework.TestCase;

public class MoneyUtilsTest extends TestCase {

    /**
     * 测试
     * @throws Exception
     */
    public void testGetProfit() throws Exception {
        OrderPO order = new OrderPO();
        int duration = 1;
        String paymentType = PaymentPlanType.OncePayment;



        order.setId("71058CDE093A4D1A9D0196D47A8A3666");
        order.setProductionId("6BB1872891C14BBCB7A5C26A8792C93A");
        order.setProductionCompositionId("40DAE6651B64498A8F47BCD5E6984FD1");
        order.setCustomerId("BD74B58992E34344ACF5AF4334C6338A");
    }

}