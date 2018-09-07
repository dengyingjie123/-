package com.youngbook.action.customer;

import com.opensymphony.xwork2.ActionProxy;
import com.youngbook.action.customer.CustomerPersonalAction;
import com.youngbook.common.Database;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.interceptor.DefaultInterceptor;
import com.youngbook.service.customer.CustomerPersonalService;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

/** 
* CustomerPersonalAction Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 21, 2015</pre> 
* @version 1.0 
*/ 
public class CustomerPersonalActionTest extends StrutsTestCase {

    @Autowired
    CustomerPersonalService customerPersonalService;

    Connection conn ;


@Before
public void before() throws Exception {

    conn =  Config.getConnection();
} 

@After
public void after() throws Exception { 
} 



/**
*
* Method: resetPassword4Mobile4W()
*
*/
@Test
public void testResetPassword4Mobile4W() throws Exception {
    CustomerPersonalAction cus=null;  //����Action��ʼ��

    //����request����
    request.setParameter("mobile", "18822871601");
    request.setParameter("captcha", "wesdfe");

    //��ȡActionʵ�����
    ActionProxy proxy =getActionProxy("/customer/CustomerPersonal.action");
    cus = (CustomerPersonalAction)proxy.getAction();

    //����ֵΪ��success  ��ȷͨ����ȷ����ͨ��
    assertEquals("SUCCESS", cus.resetPassword4Mobile4W());
}

    @Test
    public void testGetMobileCode() throws Exception {
        super.initServletMockObjects();



        Connection conn = Config.getConnection();
        try {

            request.setParameter("mobile", "13888939712");

            CustomerPersonalAction action = null;

            ActionProxy proxy = getActionProxy("/api/customer/CustomerPersonal.action");
            action = (CustomerPersonalAction)proxy.getAction();

            action.setConnection(conn);
            action.setResult(new ReturnObject());

            assertEquals("success", action.getMobileCode());

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }
}
