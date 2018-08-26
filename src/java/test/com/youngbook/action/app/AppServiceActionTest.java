package com.youngbook.action.app;

import com.opensymphony.xwork2.ActionProxy;
import com.youngbook.action.api.ApiServiceAction;
import org.apache.struts2.StrutsTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** 
* AppServiceAction Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 23, 2015</pre> 
* @version 1.0 
*/ 
public class AppServiceActionTest extends StrutsTestCase {

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: invoke() 
* 
*/ 
@Test
public void testInvoke() throws Exception {
    //����request����
    request.setParameter("name", "mobile.login");
    request.setParameter("token", "");
    request.setParameter("data", "{loginName:'13005215733',password:'dc483e80a7a0bd9ef71d8cf973673924'}");

    //��ȡActionʵ�����
    ActionProxy proxy =getActionProxy("/app/AppService/invokes.action");
    ApiServiceAction cus = (ApiServiceAction)proxy.getAction();
    ///cus.invokes();
}
@Test
public void testServices() throws Exception {
    //����request����
    request.setParameter("name", "mobile.login");
    request.setParameter("token", "sds");
    request.setParameter("data", "{loginName:'13005215734 ',password:'dc483e80a7a0bd9ef71d8cf973673924'}");

    //��ȡActionʵ�����
    ActionProxy proxy =getActionProxy("/app/AppService/services.action");
    ApiServiceAction cus = (ApiServiceAction)proxy.getAction();
    cus.services();
}

    /**
* 
* Method: checkToken(String token) 
* 
*/ 
@Test
public void testCheckToken() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getMethodName(String name) 
* 
*/ 
@Test
public void testGetMethodName() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getName() 
* 
*/ 
@Test
public void testGetName() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setName(String name) 
* 
*/ 
@Test
public void testSetName() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getVersion() 
* 
*/ 
@Test
public void testGetVersion() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setVersion(String version) 
* 
*/ 
@Test
public void testSetVersion() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getData() 
* 
*/ 
@Test
public void testGetData() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setData(String data) 
* 
*/ 
@Test
public void testSetData() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getToken() 
* 
*/ 
@Test
public void testGetToken() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setToken(String token) 
* 
*/ 
@Test
public void testSetToken() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getTokenMap() 
* 
*/ 
@Test
public void testGetTokenMap() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setTokenMap(Map<String, String> tokenMap) 
* 
*/ 
@Test
public void testSetTokenMap() throws Exception { 
//TODO: Test goes here... 
} 


} 
