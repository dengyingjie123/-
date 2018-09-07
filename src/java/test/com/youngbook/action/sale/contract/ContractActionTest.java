package com.youngbook.action.sale.contract;

import com.opensymphony.xwork2.ActionProxy;
import com.youngbook.action.customer.CustomerPersonalAction;
import com.youngbook.common.config.Config;
import org.apache.struts2.StrutsTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

/**
 * ContractAction Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>销售合同Action 24, 2015</pre>
 */
public class ContractActionTest extends StrutsTestCase {

    Connection conn = null;
    ContractAction cus = null;

    @Before
    public void before() throws Exception {
        conn = Config.getConnection();
        ActionProxy proxy = getActionProxy("/sale/contract/ContractAction.action");
        cus = (ContractAction) proxy.getAction();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: receiveContracts()
     */
    @Test
    public void testReceiveContracts() throws Exception {

//        assertEquals("SUCCESS", cus.receiveContracts());
    }

    /**
     * Method: getListBlankContracts()
     */
    @Test
    public void testGetListBlankContracts() throws Exception {

        assertEquals("SUCCESS", cus.getListBlankContracts());
    }

    /**
     * Method: getListSignedContracts()
     */
    @Test
    public void testGetListSignedContracts() throws Exception {

        assertEquals("SUCCESS", cus.getListSignedContracts());
    }

    /**
     * Method: getListUnsualContracts()
     */
    @Test
    public void testGetListUnsualContracts() throws Exception {


        assertEquals("SUCCESS", cus.getListUnsualContracts());
    }

    /**
     * Method: getListArchiveContracts()
     */
    @Test
    public void testGetListArchiveContracts() throws Exception {

        assertEquals("SUCCESS", cus.getListArchiveContracts());
    }

    /**
     * Method: distributeContract()
     */
    @Test
    public void testDistributeContract() throws Exception {

        assertEquals("SUCCESS", cus.distributeContract());
    }

    /**
     * Method: setSendContracts()
     */
    @Test
    public void testSetSendContracts() throws Exception {

        assertEquals("SUCCESS", cus.setSendContracts());
    }

    /**
     * Method: getListSendContracts()
     */
    @Test
    public void testGetListSendContracts() throws Exception {
//TODO: Test goes here...

        assertEquals("SUCCESS", cus.getListSendContracts());
    }

    /**
     * Method: confirmContract()
     */
    @Test
    public void testConfirmContract() throws Exception {
//TODO: Test goes here...

        assertEquals("SUCCESS", cus.confirmContract());
    }

    /**
     * Method: getLlistContracts()
     */
    @Test
    public void testGetListContracts() throws Exception {
//TODO: Test goes here...

        assertEquals("SUCCESS", cus.getListContracts());
    }

    /**
     * Method: cancelContract()
     */
    @Test
    public void testCancelContract() throws Exception {
//TODO: Test goes here...
//
//        assertEquals("SUCCESS", cus.cancelContract());
    }

    /**
     * Method: archiveContracts()
     */
    @Test
    public void testArchiveContracts() throws Exception {

        assertEquals("SUCCESS", cus.archiveContracts());
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
     * Method: getContractPO()
     */
    @Test
    public void testGetContractPO() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setContractPO(ContractPO contractPO)
     */
    @Test
    public void testSetContractPO() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getContractVO()
     */
    @Test
    public void testGetContractVO() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setContractVO(ContractVO contractVO)
     */
    @Test
    public void testSetContractVO() throws Exception {
//TODO: Test goes here... 
    }


} 
