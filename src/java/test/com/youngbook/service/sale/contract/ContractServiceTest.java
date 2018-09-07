package com.youngbook.service.sale.contract;

import com.youngbook.common.KVObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.contract.*;
import com.youngbook.entity.vo.Sale.contract.ContractApplicationVO;
import com.youngbook.entity.vo.Sale.contract.ContractVO;
import org.apache.struts2.StrutsTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * ContractService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>销售合业务 24, 2015</pre>
 */
public class ContractServiceTest extends StrutsTestCase {

    @Autowired
    ContractService contractService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    public static void main(String[] args) {

        String name = "德合钜源电力产业股权投资基金";
        String prefix = "1031701201";
        int start = 1;
        int count = 30;

        for (int i = start; i < (count + start); i++) {
            String number = StringUtils.buildNumberString(i, 5);
            System.out.println( prefix + number + "-1");
            System.out.println( prefix + number + "-2");
            System.out.println( prefix + number + "-3");
        }

    }

    /**
     * Method: listApplications(ContractApplicationVO contractApplicationVO, List<KVObject> conditions, HttpServletRequest request, Connection conn)
     */
    @Test
    public void testListApplications() throws Exception {
        ContractApplicationVO contractApplicationVO = new ContractApplicationVO();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = Config.getConnection();
        contractService.listApplications(contractApplicationVO, "",conditions, request, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: newApplication(String productionId, int count, UserPO applicationUser, Connection conn)
     */
    @Test
    public void testNewApplication() throws Exception {
        Connection conn = Config.getConnection();
        UserPO applicationUser = new UserPO();
        applicationUser.setId("ab7996b505df42cda37400f550f7cf1c");
        String productionId = "4B646EB1D6F4480892ABD0233E667E75";
        int count = 10;
//        int c = service.newApplication(productionId, count,"","", applicationUser, conn);
//        assertEquals(1, c);
    }


    /**
     * Method: listSendContracts(ContractVO contractVO, List<KVObject> conditions, HttpServletRequest request, Connection conn)
     */
    @Test
    public void testListSendContracts() throws Exception {
//TODO: Test goes here...
        ContractVO contractVO = new ContractVO();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = Config.getConnection();
        contractService.listSendContracts(contractVO, conditions, null, request, conn);
    }

    /**
     * Method: sendContracts(List<ContractPO> contracts, UserPO senderUser, Connection conn)
     */
    @Test
    public void testSendContracts() throws Exception {
        Connection conn = Config.getConnection();
        List<ContractPO> contracts = new ArrayList<ContractPO>();
        ContractPO contractPO = new ContractPO();
        contractPO.setStatus(ContractApplicationStatus.Checked);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        contracts.add(contractPO);

        contractPO.setStatus(ContractApplicationStatus.CheckedFailure);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        contracts.add(contractPO);
        UserPO senderUser = new UserPO();
        senderUser.setId("ab7996b505df42cda37400f550f7cf1c");

//        int c = service.sendContracts(null, senderUser, conn);
//        assertEquals(1, c);
//TODO: Test goes here...
    }

    /**
     * Method: CleanContracltReceiveUser(ContractPO contractPO, UserPO optionUser, Connection conn)
     */
    @Test
    public void testCleanContracltReceiveUser() throws Exception {

//TODO: Test goes here...

    }

    /**
     * Method: AddContracltReceiveUser(ContractPO contractPO, UserPO receiveUser, UserPO optionUser, Connection conn)
     */
    @Test
    public void testAddContracltReceiveUser() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: dispatchContracts(List<ContractPO> contracts, String sendExpress, String sendExpressId, UserPO dispatchUser, Connection conn)
     */
    @Test
    public void testDispatchContracts() throws Exception {
//TODO: Test goes here...
        Connection conn = Config.getConnection();
        List<ContractPO> contracts = new ArrayList<ContractPO>();
        ContractPO contractPO = new ContractPO();
        contractPO.setStatus(ContractStatus.Unsigned);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        contracts.add(contractPO);

        contractPO.setStatus(ContractStatus.Exception);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        contracts.add(contractPO);
        UserPO senderUser = new UserPO();
        senderUser.setId("ab7996b505df42cda37400f550f7cf1c");

        String sendExpress = "";
        String sendExpressId = "";
//        int c = service.dispatchContracts(null, sendExpress, sendExpressId, senderUser, conn);
//        assertEquals(1, c);
    }

    /**
     * Method: receiveContracts(List<ContractPO> contracts, UserPO receiveUser, Connection conn)
     */
    @Test
    public void testReceiveContracts() throws Exception {
        Connection conn = Config.getConnection();
        List<ContractPO> contracts = new ArrayList<ContractPO>();
        ContractPO contractPO = new ContractPO();
        contractPO.setStatus(ContractStatus.Unsigned);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        contracts.add(contractPO);

        contractPO.setStatus(ContractStatus.Exception);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        contracts.add(contractPO);
        UserPO senderUser = new UserPO();
        senderUser.setId("ab7996b505df42cda37400f550f7cf1c");
        //签收合同
//        int c = service.receiveContracts(null, senderUser, conn);
//        assertEquals(1, c);
//TODO: Test goes here... 
    }

    /**
     * Method: listBlankContracts(ContractVO contractVO, List<KVObject> conditions, HttpServletRequest request, Connection conn)
     */
    @Test
    public void testListBlankContracts() throws Exception {

        ContractVO contractVO = new ContractVO();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = Config.getConnection();
        contractService.listBlankContracts(contractVO, conditions,"", request, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: distributeContract(String contractId, UserPO saleUser, UserPO operatorUser, Connection conn)
     */
    @Test
    public void testDistributeContract() throws Exception {
        Connection conn = Config.getConnection();
        ContractPO contractPO = new ContractPO();
        contractPO.setStatus(ContractStatus.Unsigned);
        contractPO.setDetailStatus(ContractDetailStatus.Active);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        UserPO senderUser = new UserPO();
        senderUser.setId("ab7996b505df42cda37400f550f7cf1c");
        UserPO sale = new UserPO();
        sale.setId("ab7996b505df42cda37400f550f7cf1c");
        contractService.distributeContract(contractPO.getContractNo(), sale, senderUser, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: cancelContract(String contractId, String[] contrctIds, UserPO operatorUser, Connection conn)
     */
    @Test
    public void testCancelContract() throws Exception {
        Connection conn = Config.getConnection();
        UserPO senderUser = new UserPO();
        senderUser.setId("ab7996b505df42cda37400f550f7cf1c");
        String[] id = new String[]{"", ""};
        String contractNo = "";
        //遗失合同
//        service.cancelContract(contractNo, id,"", senderUser, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: ContractCancel(ContractPO contractPO, String[] contrctIds, UserPO optionUser, Connection conn)
     */
    @Test
    public void testContractCancel() throws Exception {
        Connection conn = Config.getConnection();
        UserPO senderUser = new UserPO();
        senderUser.setId("ab7996b505df42cda37400f550f7cf1c");
        ContractPO contractPO = new ContractPO();
        contractPO.setStatus(ContractStatus.Unsigned);
        contractPO.setDetailStatus(ContractDetailStatus.Active);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        String[] id = new String[]{"", ""};
        //遗失合同
//        service.ContractCancel(contractPO, id,"", senderUser, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: signedContract(String contractId, UserPO operatorUser, Connection conn)
     */
    @Test
    public void testSignedContract() throws Exception {
        Connection conn = Config.getConnection();
        UserPO senderUser = new UserPO();
        senderUser.setId("ab7996b505df42cda37400f550f7cf1c");
        String[] id = new String[]{"", ""};
        String contractNo = "";
        //遗失合同
//        service.cancelContract(contractNo, id,"", senderUser, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: listSignedContracts(ContractVO contractVO, List<KVObject> conditions, HttpServletRequest request, Connection conn)
     */
    @Test
    public void testListSignedContracts() throws Exception {

        ContractVO contractVO = new ContractVO();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = Config.getConnection();
        contractService.listSignedContracts(contractVO, conditions,"", request, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: listUnsualContracts(ContractVO contractVO, List<KVObject> conditions, HttpServletRequest request, Connection conn)
     */
    @Test
    public void testListUnsualContracts() throws Exception {

        ContractVO contractVO = new ContractVO();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = Config.getConnection();
        contractService.listUnsualContracts(contractVO, conditions,"", request, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: listArchiveContracts(ContractVO contractVO, List<KVObject> conditions, HttpServletRequest request, Connection conn)
     */
    @Test
    public void testListArchiveContracts() throws Exception {

        ContractVO contractVO = new ContractVO();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = Config.getConnection();
        contractService.listArchiveContracts(contractVO, conditions, request, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: listContracts(ContractVO contractVO, List<KVObject> conditions, HttpServletRequest request, Connection conn)
     */
    @Test
    public void testListContracts() throws Exception {

        ContractVO contractVO = new ContractVO();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = Config.getConnection();
        String sale = "";
        contractService.listContracts(contractVO, sale, conditions, request, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: listContractRouteList(String orgId, String contractId, List<KVObject> conditions, Connection conn)
     */
    @Test
    public void testListContractRouteList() throws Exception {
        List<KVObject> conditions = new ArrayList<KVObject>();
        Connection conn = Config.getConnection();
        String orgId = "";
        String contractId = "";
        contractService.listContractRouteListVOs(contractId, conditions, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: confirmContract(ContractPO contractPO, UserPO confirm, Connection conn)
     */
    @Test
    public void testConfirmContract() throws Exception {
        Connection conn = Config.getConnection();
        UserPO userPO = new UserPO();
        userPO.setId("ab7996b505df42cda37400f550f7cf1c");
        ContractPO contractPO = new ContractPO();
        contractPO.setStatus(ContractStatus.Unsigned);
        contractPO.setDetailStatus(ContractDetailStatus.Active);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
//        service.confirmContract(contractPO, userPO, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: archiveContracts(List<ContractPO> contracts, UserPO operatorUser, Connection conn)
     */
    @Test
    public void testArchiveContracts() throws Exception {
        Connection conn = Config.getConnection();
        List<ContractPO> contracts = new ArrayList<ContractPO>();
        ContractPO contractPO = new ContractPO();
        contractPO.setStatus(ContractStatus.Unsigned);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        contracts.add(contractPO);

        contractPO.setStatus(ContractStatus.Exception);
        contractPO.setOrgId("");
        contractPO.setContractNo("");
        contracts.add(contractPO);
        UserPO userPO = new UserPO();
        userPO.setId("ab7996b505df42cda37400f550f7cf1c");
        contractService.archiveContracts(null, userPO, conn);
//TODO: Test goes here... 
    }

    /**
     * Method: newContract(String applicationId, String applicationOrgId, String productionId, int count, UserPO operatorUser, Connection conn)
     */
    @Test
    public void testNewContract() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: SetContractAttribute(ContractPO contractPO, UserPO operatorUser, Connection conn)
     */
    @Test
    public void testSetContractAttribute() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getContractNo(String productionID)
     */
    @Test
    public void testGetContractNo() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getContractPOs(String contracrNo, Connection conn)
     */
    @Test
    public void testGetContractPOs() throws Exception {
//TODO: Test goes here... 
    }


}
