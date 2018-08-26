package com.youngbook.dao.sale.contract;

import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.contract.ContractPO;
import com.youngbook.entity.po.sale.contract.ContractRouteListPO;
import com.youngbook.entity.vo.Sale.contract.ContractVO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/30.
 */
public interface IContractDao {

    public boolean checkContractNO(String contractNO, String productionId, Connection conn) throws Exception;
    public int changeProduction(String contractNO, String productionId, Connection conn) throws Exception;

    public int setContractStatus(String contractNO, int status, Connection conn) throws Exception;

    public List<ContractPO> listContractPOByContractNO(String contractNO, Connection conn) throws Exception;

    public Pager listContractVOs(ContractVO contractVO, List<KVObject> conditions ,HttpServletRequest request, Connection conn) throws Exception;

    public ContractRouteListPO getLastContractRouteListPO(String contractNo, Connection conn) throws Exception;
    public List<ContractRouteListPO> getContractRouteListByContranctNo(String contracrNo, Connection conn) throws Exception;
    public ContractPO getContractByContractId(String contractId, Connection conn) throws Exception;
    public List<ContractPO> getContractByContractNO(String contracrNo, Connection conn) throws Exception;

    public int contractSigned(String contractNo, String userId, Connection conn) throws Exception;
    public int saveContractRouteList4Signed(String contractNo, String actionUserId, Connection conn) throws Exception;
    public int contractCancelSigned(ContractPO contractPO, UserPO optionUser, Connection conn) throws Exception;
    public int cancelSignedContract(String contractNo, UserPO operatorUser, Connection conn) throws Exception;
}
