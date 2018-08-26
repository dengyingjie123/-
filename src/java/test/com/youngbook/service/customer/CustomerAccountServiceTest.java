package com.youngbook.service.customer;

import com.youngbook.action.wsi.RequestObject;
import com.youngbook.action.wsi.ServiceInvoker;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import junit.framework.TestCase;
import org.junit.Test;

public class CustomerAccountServiceTest extends TestCase {

    @Test
    public void testVerifyRealNameAndBindingBankCard() throws Exception {

        String peopleName = "李扬";
        String peopleIdNumber = "6225888712203953";

        RequestObject requestObject = new RequestObject();
        requestObject.setName("allinpay.realNameVerification.v2");
        requestObject.setToken(Config.getSystemConfig("wsi.token"));
        requestObject.addData("name", peopleName);
        requestObject.addData("number", peopleIdNumber);
        requestObject.addData("bankCode", "23");
        ServiceInvoker invoker = new ServiceInvoker();
        ReturnObject returnObject = invoker.invoke(requestObject);

        System.out.println(returnObject.getMessage());
    }
}