package com.youngbook.service.report;

import com.youngbook.common.KVObject;
import com.youngbook.common.config.Config;
import com.youngbook.entity.vo.report.ProductDailyPaymentReportVO;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/** 
* ProductDailyPaymentReportService Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 9, 2015</pre> 
* @version 1.0 
*/ 
public class ProductDailyPaymentReportServiceTest extends StrutsTestCase {

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: list(ProductDailyPaymentReportVO productDailyPaymentReportVO, List<KVObject> conditions, String time, HttpServletRequest request) 
* 
*/ 
@Test
public void testListForProductDailyPaymentReportVOConditionsTimeRequest() throws Exception { 
//TODO: Test goes here...
    ProductDailyPaymentReportService productDailyPaymentReportService = new ProductDailyPaymentReportService();
    ProductDailyPaymentReportVO productDailyPaymentReportVO = new ProductDailyPaymentReportVO();
    List<KVObject> conditions = new ArrayList<KVObject>();
    productDailyPaymentReportService.list(productDailyPaymentReportVO,conditions,"2015-12-07",request);
} 

/** 
* 
* Method: list(String time, Connection conn) 
* 
*/ 
@Test
public void testListForTimeConn() throws Exception { 
//TODO: Test goes here...
    ProductDailyPaymentReportService productDailyPaymentReportService = new ProductDailyPaymentReportService();
    //正确
    productDailyPaymentReportService.list("2015-12-07", Config.getConnection());
    //失败
//    productDailyPaymentReportService.list(null, Config.getConnection());
    //失败
//    productDailyPaymentReportService.list("2015-12-07 00:00:00", Config.getConnection());
} 


} 
