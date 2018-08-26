package com.youngbook.action.sale.contract;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.contract.ContractPO;
import com.youngbook.entity.po.sale.contract.ContractRouteListPO;
import com.youngbook.entity.po.sale.contract.ContractRouteListStatus;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.Sale.contract.ContractRouteListVO;
import com.youngbook.service.sale.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/12/23
 * 描述：
 * ContyractRouteAction: 销售合同流转action
 */
public class ContractRouteAction extends BaseAction {

    //销售合同业务类
    @Autowired
    ContractService contractService;

    //模型
    private ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
    private ContractRouteListVO contractRouteListVO = new ContractRouteListVO();
    private ContractPO contractPO = new ContractPO();

    /**
     * 修改合同的流转状态
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年6月16日
     *
     * @return
     * @throws Exception
     */
    public String updateRouteListStatus() throws Exception {

        // 获取请求对象
        HttpServletRequest request = this.getRequest();
        // 获取参数
        String status = request.getParameter("status");
        if(StringUtils.isEmpty(status)) {
            MyException.newInstance("缺少流转状态参数").throwException();
        }
        Integer statusInt = Integer.parseInt(status);

        // 获取销售合同编号
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO receiveUser = getLoginUser();

        // 分隔字符串
        String[] contractNos = contractNO.split(",");
        if (contractNos.length != 0) {
            for (int i = 0; i < contractNos.length; i++) {
                // 获取销售合同集合
                String contractNo2 = contractNos[i];
                List<ContractPO> contractPOs = contractService.getContractPOsByContractNo(contractNo2, conn);
                if (contractPOs == null || contractPOs.size() == 0) {
                    MyException.newInstance("寄送的合同失败").throwException();
                }

                String description = "流转信息手动修改为【" + ContractRouteListStatus.getName(statusInt) + "】";
                // 获得登录用户目前的部门信息，有可能是切换过后的信息
                UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
                int count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(),receiveUser, userPositionInfoPO, description, statusInt, conn);
                if (count == 0) {
                    MyException.newInstance("寄送的合同失败").throwException();
                }
            }
        }
        return SUCCESS;

    }

    /**
     * 寄送 销售合同信息
     * 调用业务类的 dispatchContracts 寄送合同
     *
     * @return
     * @throws Exception
     */
    public String dispatchContracts() throws Exception {
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO receiveUser = getLoginUser();

        String sendExpress = contractRouteListPO.getSendExpress();
        String sendExpressId = contractRouteListPO.getSendExpressId();
        //分隔字符串
        String[] contractNos = contractNO.split(",");
        if (contractNos.length != 0) {
            for (int i = 0; i < contractNos.length; i++) {
                //获取销售合同集合
                String contractNo2 = contractNos[i];
                List<ContractPO> contractPOs = contractService.getContractPOsByContractNo(contractNo2, conn);
                if (contractPOs == null || contractPOs.size() == 0) {
                    MyException.newInstance("寄送的合同失败").throwException();
                }

                String description = "【"+receiveUser.getName() + "】寄送销售合同，快递【" + sendExpress + "】，快递号码【"+sendExpressId+"】";

                /**
                 * 获得登录用户目前的部门信息
                 *
                 * 有可能是切换过后的信息
                 */
                UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
                int count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(),receiveUser, userPositionInfoPO, description, ContractRouteListStatus.ACTIONTYPE_SENDING, conn);
                if (count == 0) {
                    MyException.newInstance("寄送的合同失败").throwException();
                }
            }
        }
        return SUCCESS;
    }

    /**
     * 签收合同状态为寄送的销售合同信息
     * 调用业务类的 receiveContracts 签收合同
     *
     * @return
     * @throws Exception
     */
    public String receiveContracts() throws Exception {
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO receiveUser = getLoginUser();
        //分隔字符串
        String[] contractNos = contractNO.split(",");
        try {
            if (contractNos.length != 0) {
                for (int i = 0; i < contractNos.length; i++) {
                    //获取销售合同集合
                    String contractNo2 = contractNos[i];
                    List<ContractPO> contractPOs = contractService.getContractPOsByContractNo(contractNo2, conn);
                    if (contractPOs == null || contractPOs.size() == 0) {
                        MyException.newInstance("签收寄送的合同失败").throwException();
                    }

                    String description = "【"+receiveUser.getName() + "】签收销售合同";
                    UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
                    int count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(), receiveUser, userPositionInfoPO, description, ContractRouteListStatus.ACTIONTYPE_SIGN, conn);
                    if (count == 0) {
                        MyException.newInstance("签收寄送的合同失败").throwException();
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            getResult().setReturnValue("false");
            MyException.deal(MyException.newInstance("签收寄送的合同失败"));
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }



    /**
     * 获取销售合同状态列表的合同流转记录
     * 创建contractRouteListVO 流转对象，设置组织编号
     * 调用业务流的 listContractRouteList 获取销售合同状态流。
     *
     * @return
     * @throws Exception
     */
    public String getListContractRouteList() throws Exception {
        //获取销售合同编号
        String contractNO = contractRouteListVO.getContractNo();
        String orgId = contractRouteListVO.getActionDepartmentId();
        Connection conn = getConnection();
        HttpServletRequest request = getRequest();

        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractRouteListVO.class);
        List<ContractRouteListVO> contractRouteListVOList = contractService.listContractRouteListVOs(contractNO, conditions, conn);
        getRequest().setAttribute("contractRouteListVOList", contractRouteListVOList);
        return SUCCESS;
    }

    public ContractRouteListPO getContractRouteListPO() {
        return contractRouteListPO;
    }

    public void setContractRouteListPO(ContractRouteListPO contractRouteListPO) {
        this.contractRouteListPO = contractRouteListPO;
    }

    public ContractRouteListVO getContractRouteListVO() {
        return contractRouteListVO;
    }

    public void setContractRouteListVO(ContractRouteListVO contractRouteListVO) {
        this.contractRouteListVO = contractRouteListVO;
    }

    public ContractPO getContractPO() {
        return contractPO;
    }

    public void setContractPO(ContractPO contractPO) {
        this.contractPO = contractPO;
    }
}
