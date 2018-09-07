package com.youngbook.action.sale.contract;

import com.opensymphony.xwork2.ActionProxy;
import com.youngbook.common.config.Config;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.contract.ContractApplicationPO;
import com.youngbook.entity.po.sale.contract.ContractApplicationStatus;
import com.youngbook.entity.po.sale.contract.ContractStatus;
import com.youngbook.service.sale.contract.ContractService;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.sql.Connection;

/**
 * ContractApplicationAction Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>销售合同申请24, 2015</pre>
 */
public class ContractApplicationActionTest extends StrutsTestCase {

    ContractApplicationPO contractApplicationPO = new ContractApplicationPO();
    ContractApplicationAction cus = null;
    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: newApplicationContract()
     */
    @Test
    public void testNewApplicationContract() throws Exception {
//TODO: Test goes here...

        Connection conn = Config.getConnection();
        ActionProxy proxy = getActionProxy("/sale/contract/ContractApplication.action");
         cus = (ContractApplicationAction) proxy.getAction();

        UserPO user = new UserPO();
        user.setId("ab7996b505df42cda37400f550f7cf1c");
        request.getSession().setAttribute("loginPO", user);
        contractApplicationPO.setProductionId("4B646EB1D6F4480892ABD0233E667E75");
        contractApplicationPO.setCounts(10);
//        assertEquals("success", cus.newApplicationContract(contractApplicationPO,conn));
    }

    /**
     * Method: listApplications()
     */
    @Test
    public void testListApplications() throws Exception {


        Connection conn = Config.getConnection();
        ActionProxy proxy = getActionProxy("/sale/contract/ContractApplication.action");
        cus = (ContractApplicationAction) proxy.getAction();

        UserPO user = new UserPO();
        user.setId("ab7996b505df42cda37400f550f7cf1c");
        request.getSession().setAttribute("loginPO", user);
//        assertEquals("success", cus.listApplications(conn));
    }

    /**
     * Method: checkApplication()
     */
    @Test
    public void testCheckApplication() throws Exception {
        Connection conn = Config.getConnection();
        ActionProxy proxy = getActionProxy("/sale/contract/ContractApplication.action");
        cus = (ContractApplicationAction) proxy.getAction();

        UserPO user = new UserPO();
        user.setId("ab7996b505df42cda37400f550f7cf1c");
        request.getSession().setAttribute("loginPO", user);
        contractApplicationPO.setId("");
        contractApplicationPO.setCheckState(ContractApplicationStatus.Checked);
        contractApplicationPO.setProductionId("4B646EB1D6F4480892ABD0233E667E75");
        contractApplicationPO.setCounts(10);
//        assertEquals("success", cus.checkApplication(contractApplicationPO,conn));
//TODO: Test goes here... 
    }

    /**
     * Method: getContractService()
     */
    @Test
    public void testGetContractService() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setContractService(ContractService contractService)
     */
    @Test
    public void testSetContractService() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getContractApplicationPO()
     */
    @Test
    public void testGetContractApplicationPO() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setContractApplicationPO(ContractApplicationPO contractApplicationPO)
     */
    @Test
    public void testSetContractApplicationPO() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getContractRouteListVO()
     */
    @Test
    public void testGetContractRouteListVO() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setContractRouteListVO(ContractApplicationVO contractRouteListVO)
     */
    @Test
    public void testSetContractRouteListVO() throws Exception {
//TODO: Test goes here... 
    }


} 
