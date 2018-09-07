package com.youngbook.service.report;

import com.youngbook.common.KVObject;
import com.youngbook.common.config.Config;
import com.youngbook.entity.vo.report.CustomerDailyPaymentReportVO;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/** 
* CustomerDailyPaymentReportService Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 9, 2015</pre> 
* @version 1.0 
*/ 
public class CustomerDailyPaymentReportServiceTest extends StrutsTestCase {

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: list(CustomerDailyPaymentReportVO customerDailyPaymentReportVO, List<KVObject> conditions, String time, HttpServletRequest request) 
* 
*/ 
@Test
public void testListForCustomerDailyPaymentReportVOConditionsTimeRequest() throws Exception { 
//TODO: Test goes here...
    CustomerDailyPaymentReportVO customerDailyPaymentReportVO = new CustomerDailyPaymentReportVO();
    CustomerDailyPaymentReportService customerDailyPaymentReportService = new CustomerDailyPaymentReportService();
    List<KVObject> conditions = new ArrayList<KVObject>();
    //正确
    customerDailyPaymentReportService.list(customerDailyPaymentReportVO,conditions,"2015-12-07",request);
} 

/** 
* 
* Method: list(String time, Connection conn) 
* 
*/ 
@Test
public void testListForTimeConn() throws Exception { 
//TODO: Test goes here...
    CustomerDailyPaymentReportService customerDailyPaymentReportService = new CustomerDailyPaymentReportService();
    //正确
    customerDailyPaymentReportService.list("2015-07-07", Config.getConnection());
    //失败
//    customerDailyPaymentReportService.list(null, Config.getConnection());
    //失败
//    customerDailyPaymentReportService.list("2015-07-07 00:00:00", Config.getConnection());
}


} 
