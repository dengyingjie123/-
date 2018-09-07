package com.youngbook.action.report;

import com.opensymphony.xwork2.ActionProxy;
import com.youngbook.common.config.Config;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.sql.Connection;

/** 
* ProductDailyPaymentReportAction Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 9, 2015</pre> 
* @version 1.0 
*/ 
public class ProductDailyPaymentReportActionTest extends StrutsTestCase {

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
    ProductDailyPaymentReportAction productDailyPaymentReportAction = null;

    ActionProxy proxy =getActionProxy("/report/ProductDailyPaymentReport.action");
    productDailyPaymentReportAction = (ProductDailyPaymentReportAction)proxy.getAction();

    assertEquals("SUCCESS",productDailyPaymentReportAction.list());
}

/**
* 
* Method: getProductDailyPaymentReportVO() 
* 
*/ 
@Test
public void testGetProductDailyPaymentReportVO() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setProductDailyPaymentReportVO(ProductDailyPaymentReportVO productDailyPaymentReportVO) 
* 
*/ 
@Test
public void testSetProductDailyPaymentReportVO() throws Exception { 
//TODO: Test goes here... 
} 


} 
