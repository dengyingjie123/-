package com.youngbook.action.report;

import com.opensymphony.xwork2.ActionProxy;
import com.youngbook.entity.vo.report.CustomerDailyPaymentReportVO;
import com.youngbook.service.report.CustomerDailyPaymentReportService;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import javax.servlet.http.HttpServletRequest;

/** 
* CustomerDailyPaymentReportAction Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 9, 2015</pre> 
* @version 1.0 
*/ 
public class CustomerDailyPaymentReportActionTest extends StrutsTestCase {

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: list() 
* 
*/ 
@Test
public void testList() throws Exception { 
//TODO: Test goes here...
    CustomerDailyPaymentReportAction customerDailyPaymentReportAction = null;

    ActionProxy proxy =getActionProxy("/report/CustomerDailyPaymentReport.action");
    customerDailyPaymentReportAction = (CustomerDailyPaymentReportAction)proxy.getAction();

    assertEquals("SUCCESS",customerDailyPaymentReportAction.list());
} 

/** 
* 
* Method: getCustomerDailyPaymentReportVO() 
* 
*/ 
@Test
public void testGetCustomerDailyPaymentReportVO() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setCustomerDailyPaymentReportVO(CustomerDailyPaymentReportVO customerDailyPaymentReportVO) 
* 
*/ 
@Test
public void testSetCustomerDailyPaymentReportVO() throws Exception { 
//TODO: Test goes here... 
} 


} 
