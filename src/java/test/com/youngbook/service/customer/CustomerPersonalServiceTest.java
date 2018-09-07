package com.youngbook.service.customer;

import static org.junit.Assert.assertEquals;

import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.service.production.OrderService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml"})
public class CustomerPersonalServiceTest {

    @Autowired
    CustomerPersonalService customerPersonalService;



    @Test
    public void testIsOldCustomer() throws Exception {
        boolean temp = customerPersonalService.isOldCustomer("1388888888");
        assertEquals(false, temp);
        assertEquals(true, temp);
    }

    @Test
    public void testValidateIdentityV2() throws Exception {
//        int r = service.validateIdentityV2ByAllinpay("李扬", "530103198203091219", "308", "6225888712203953");
        ReturnObject r = customerPersonalService.validateIdentityV2ByAllinpay("张宏璧", "53010219860115184X", "301", "6222600590004155543");
        System.out.println(r);
    }

    public static void main(String [] args) {

        try {
            CustomerPersonalServiceTest customerPersonalServiceTest = new CustomerPersonalServiceTest();
            customerPersonalServiceTest.testValidateIdentityV2();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Autowired
    OrderService orderService;

    @Test
    public void testGetCustomerTotalPrincipalMoney() throws Exception {
        Connection conn = Config.getConnection();

        System.out.println(customerPersonalService);
        System.out.println(orderService);


        double money = customerPersonalService.getCustomerTotalPrincipalMoney("", conn);

        System.out.println(money);

    }
}
