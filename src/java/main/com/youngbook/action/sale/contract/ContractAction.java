package com.youngbook.action.sale.contract;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.contract.*;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.Sale.contract.ContractVO;
import com.youngbook.entity.vo.system.UserVO;
import com.youngbook.service.sale.contract.ContractService;
import com.youngbook.service.system.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/12/23
 * 描述：
 * ContractAction:销售合同
 */
public class ContractAction extends BaseAction {

    @Autowired
    UserService userService;

    /***
     * 销售合同业务类
     */
    @Autowired
    ContractService contractService;

    //销售合同对象
    private ContractPO contractPO = new ContractPO();
    //销售合同视图对象
    private ContractVO contractVO = new ContractVO();

    /**
     * 销售员
     */
    private UserVO userVO = new UserVO();


    public String setContractStatusCancelRequest() throws Exception {

        String contractNO = getHttpRequestParameter("contractNO");
        StringUtils.checkIsEmpty(contractNO, "合同号为空，请检查");

        String comment = getHttpRequestParameter("comment");
        StringUtils.checkIsEmpty(comment, "原因为空，请检查");


        contractService.setContractStatusCancelRequest(contractNO, comment, getLoginUser(), getConnection());


        getResult().setReturnValue("1");

        return SUCCESS;
    }

    @Permission(require = "销售合同管理_合同作废审核")
    public String setContractStatusCancelConfirmed() throws Exception {

        String contractNO = getHttpRequestParameter("contractNO");
        StringUtils.checkIsEmpty(contractNO, "合同号为空，请检查");

        String comment = getHttpRequestParameter("comment");
        StringUtils.checkIsEmpty(comment, "原因为空，请检查");

        contractService.setContractStatusCancelConfirmed(contractNO, comment, getLoginUser(), getConnection());

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    /**
     * 获取合同状态列表树
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年6月16日
     *
     * @return
     * @throws Exception
     */
    public String getRouteListStatusTree() throws Exception {
        String json = ContractRouteListStatus.getStatusTreeJson();
        this.getResult().setReturnValue(json);
        return SUCCESS;
    }

    /**
     * 修改合同号
     *
     * @return
     * @throws Exception
     */
    public String changeContractNo() throws Exception {

        String id = HttpUtils.getParameter(getRequest(), "id");
        String contractNo = HttpUtils.getParameter(getRequest(), "contractNo");

        contractService.changeContractNo(id, contractNo, getConnection());

        return SUCCESS;
    }

    /**
     * 获取等待分配销售员的空白合同，
     * 调用业务类的 listBlankContracts 获取合同列表
     *
     * @return
     * @throws Exception
     */
    public String getListBlankContracts() throws Exception {
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);
        Connection conn = getConnection();

        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());


        Pager pager = contractService.listBlankContracts(contractVO, conditions, userPositionInfoPO.getDepartmentId(), request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 销售合同详情列表
     *
     * @return
     * @throws Exception
     */
    public String getListDetailContracts() throws Exception {
        String contractNo = contractPO.getContractNo();
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);

        Pager pager = contractService.getListDetailContracts(contractNo, conditions, request, getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取合同状态为已经签约的合同数据列表
     * 调用业务类的 listSignedContracts 获取合同数据
     *
     * @return
     * @throws Exception
     */
    public String getListSignedContracts() throws Exception {
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);

        Connection conn = getConnection();

        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());

        Pager pager = contractService.listSignedContracts(contractVO, conditions, userPositionInfoPO.getDepartmentId(), request, conn);

        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取合同状态为异常的销售合同数据列表
     * 调用业务类的 listUnsualContracts 获取异常合同数据列表
     *
     * @return
     * @throws Exception
     */
    public String getListUnsualContracts() throws Exception {
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);

        Connection conn = getConnection();

        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());

        Pager pager = contractService.listUnsualContracts(contractVO, conditions, userPositionInfoPO.getDepartmentId(), request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取合同流转状态为 归档的销售合同数据列表
     * 调用业务类 listArchiveContracts 获取。
     *
     * @return
     * @throws Exception
     */
    public String getListArchiveContracts() throws Exception {
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);

        Pager pager = contractService.listArchiveContracts(contractVO, conditions, request, getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 归档销售合同移出。
     *
     * @return
     * @throws Exception
     */
    public String archiveContractDetach() throws Exception {
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO receiveUser = getLoginUser();
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        String description = "【"+receiveUser.getName()+"】移出错误归档销售合同";
        try {
            List<ContractPO> contractPOs = contractService.getContractByContractNo(contractNO, conn);
            if (contractPOs == null || contractPOs.size() == 0) {
                MyException.newInstance("移出错误归档销售合同").throwException();
            }
            int count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(), receiveUser, userPositionInfoPO, description, ContractRouteListStatus.ACTIONTYPE_WAITSEND, conn);
            if (count == 0) {
                MyException.deal(MyException.newInstance("错误归档合同移交失败"));
            }
        } catch (Exception e) {
            getResult().setReturnValue("false");
            MyException.deal(MyException.newInstance("错误归档合同移交失败"));
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }

    /**
     * 销售合同移交总部管理员
     * 调用业务类的 moveTotleManager 移到总部管理员
     *
     * @return
     * @throws Exception
     */
    public String moveTotalManager() throws Exception {
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO moveUser = getLoginUser();
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        //分隔字符串
        String[] contractNos = contractNO.split(",");
        try {
            if (contractNos.length != 0) {
                for (int i = 0; i < contractNos.length; i++) {
                    //获取销售合同集合
                    String contractNo2 = contractNos[i];
                    List<ContractPO> contractPOs = contractService.getContractByContractNo(contractNo2, conn);
                    if (contractPOs == null || contractPOs.size() == 0) {
                        MyException.newInstance("移交总部合同管理员失败").throwException();
                    }
                    String description = "【" + moveUser.getName()+"】移交总部合同管理员修改财富中心";
                    int count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(), moveUser, userPositionInfoPO,description,ContractRouteListStatus.ACTIONTYPE_TOTALMANAGEER, conn);
                    if (count == 0) {
                        MyException.deal(MyException.newInstance("移交总部合同管理员失败"));
                    }
                }
            }
        } catch (Exception e) {
            getResult().setReturnValue("false");
            MyException.deal(MyException.newInstance("移交总部管理员失败"));
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }

    /**
     * 将指定的合同非配给制定销售员
     * 调用业务类的 distributeContract 处理业务
     *
     * @return
     * @throws Exception
     */
    public String distributeContract() throws Exception {
        String contractNo = contractPO.getContractNo();
        Connection conn = getConnection();
        //根据销售员的编号获取销售员信息
        String receiveUserId = contractPO.getReceiveUserId();

        UserPO receiveUser = userService.loadUserByUserId(receiveUserId, conn);
        if (receiveUser == null) {
            MyException.deal(MyException.newInstance("分配销售合同失败"));
        }
        UserPO optionUuser = getLoginUser();

        //分隔字符串
        String[] contractNos = contractNo.split(",");
        if (contractNos.length != 0) {
            for (int i = 0; i < contractNos.length; i++) {
                List<ContractPO> contractPOs = contractService.getContractPOsByContractNo(contractNos[i], conn);
                int count = contractService.distributeContract(contractNos[i], receiveUser, optionUuser, conn);

                // 保存流转记录
                UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
                String description = "【" + optionUuser.getName() + "】分配销售合同销售员【" + receiveUser.getName() + "】";
                contractService.saveContractRouteList(contractNos[i], receiveUser, userPositionInfoPO,description,ContractRouteListStatus.ACTIONTYPE_DISTRIBUTECONTRACT, conn);

                if (count == 0) {
                    MyException.deal(MyException.newInstance("调配销售合同失败"));
                }
            }
        }
        return SUCCESS;
    }

    /**
     * 将合同按照合同的状态调配到指定的环境。
     * 调用业务类的 sendContracts 方法给进行处理
     *
     * @return
     * @throws Exception
     */
    public String setSendContracts() throws Exception {
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO receiveUser = getLoginUser();
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        //分隔字符串
        String[] contractNos = contractNO.split(",");
        try {
            if (contractNos.length != 0) {
                for (int i = 0; i < contractNos.length; i++) {
                    //获取销售合同集合
                    String contractNo2 = contractNos[i];
                    List<ContractPO> contractPOs = contractService.getContractByContractNo(contractNo2, conn);

                    if (contractPOs == null || contractPOs.size() ==0) {
                        MyException.newInstance("调配销售合同失败").throwException();
                    }

                    int count = contractService.sendContracts(contractPOs.get(0), receiveUser, userPositionInfoPO, conn);
                    if (count == 0) {
                        MyException.newInstance("调配销售合同失败").throwException();
                    }
                }
            }
        } catch (Exception e) {
            getResult().setReturnValue("false");
            MyException.newInstance("调配销售合同失败").throwException();
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }

    /**
     * 合同移交给调配管理员
     *
     * @return
     * @throws Exception
     */
    public String setSendContractsManager() throws Exception {
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO receiveUser = getLoginUser();
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        String description = "【"+receiveUser.getName()+"】移交调配销售合同管理员";
        //分隔字符串
        String[] contractNos = contractNO.split(",");
        try {
            if (contractNos.length != 0) {
                for (int i = 0; i < contractNos.length; i++) {
                    //获取销售合同集合
                    String contractNo2 = contractNos[i];
                    List<ContractPO> contractPOs = contractService.getContractByContractNo(contractNo2, conn);
                    if (contractPOs == null || contractPOs.size() ==0) {
                        MyException.newInstance("调配销售合同失败").throwException();
                    }
                    int count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(), receiveUser, userPositionInfoPO, description, ContractRouteListStatus.ACTIONTYPE_SENDMANAGER, conn);
                    if (count == 0) {
                        MyException.deal(MyException.newInstance("调配销售合同失败"));
                    }
                }
            }
        } catch (Exception e) {
            getResult().setReturnValue("false");
            MyException.deal(MyException.newInstance("调配销售合同失败"));
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }

    /**
     * 移交给空白管理员
     *
     * @return
     * @throws Exception
     */
    public String setSendContractsToBlankManager() throws Exception {
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO actionUser = getLoginUser();
        //分隔字符串
        String[] contractNos = contractNO.split(",");
        try {
            if (contractNos.length != 0) {
                for (int i = 0; i < contractNos.length; i++) {
                    //获取销售合同集合
                    String contractNo2 = contractNos[i];
                    List<ContractPO> contractPOs = contractService.getContractByContractNo(contractNo2, conn);

                    if (contractPOs == null || contractPOs.size() ==0) {
                        MyException.newInstance("调配销售合同失败").throwException();
                    }


                    String description = "【" + actionUser.getName() + "】移交空白销售合同管理员";
                    UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
                    int count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(), actionUser, userPositionInfoPO, description, ContractRouteListStatus.ACTIONTYPE_BLANKCONTRACTMANAGER, conn);
                    if (count == 0) {
                        MyException.newInstance("调配销售合同失败").throwException();
                    }
                }
            }
        } catch (Exception e) {
            getResult().setReturnValue("false");
            MyException.deal(MyException.newInstance("调配销售合同失败"));
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }

    /**
     * 合同移交给归档管理员
     *
     * @return
     * @throws Exception
     */
    public String setSendContractsToArchiveManager() throws Exception {
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO receiveUser = getLoginUser();
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        //分隔字符串
        String[] contractNos = contractNO.split(",");
        try {
            if (contractNos.length != 0) {
                for (int i = 0; i < contractNos.length; i++) {
                    //获取销售合同集合
                    String contractNo2 = contractNos[i];
                    List<ContractPO> contractPOs = contractService.getContractByContractNo(contractNo2, conn);
                    if (contractPOs == null || contractPOs.size() ==0) {
                        MyException.newInstance("调配销售合同失败").throwException();
                    }
                    String description = "【" + receiveUser.getName() + "】移交归档销售合同管理员";
                    int count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(), receiveUser, userPositionInfoPO, description, ContractRouteListStatus.ACTIONTYPE_ARCHIVEMANAGER, conn);
                    if (count == 0) {
                        MyException.deal(MyException.newInstance("调配销售合同失败"));
                    }
                }
            }
        } catch (Exception e) {
            getResult().setReturnValue("false");
            MyException.deal(MyException.newInstance("调配销售合同失败"));
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }

    /**
     * 获取销售合同调配列表
     * 调用业务类的 listSendContracts 获取分页数据
     *
     * @return
     * @throws Exception
     */
    public String getListSendContracts() throws Exception {
        Connection conn = getConnection();

        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());

        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);
        conditions = MySQLDao.getQueryNumberParameters(request, ContractVO.class, conditions);
        Pager pager = contractService.listSendContracts(contractVO, conditions, userPositionInfoPO.getDepartmentId(), request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    /**
     * 合同调配
     * @return
     * @throws Exception
     */
    public String sendContract() throws Exception {

        Connection conn = getConnection();

        String contractNOsString = HttpUtils.getParameter(getRequest(), "contractNOs");
        String receiveUserId = HttpUtils.getParameter(getRequest(), "receiveUserId");
        String groupName = HttpUtils.getParameter(getRequest(), "groupName");
        String groupId = HttpUtils.getParameter(getRequest(), "groupId");

        String [] contractNOs = contractNOsString.split(",");

        UserPO userPO = userService.loadUserByUserId(receiveUserId, conn);

        for (int i = 0; i < contractNOs.length; i++) {
            String contractNo = contractNOs[i];

            if (StringUtils.isEmpty(contractNo)) {
                MyException.newInstance("无法获得合同编号").throwException();
            }

            ContractPO searchPO = new ContractPO();
            searchPO.setState(Config.STATE_CURRENT);
            searchPO.setContractNo(contractNo);
            List<ContractPO> list = MySQLDao.search(searchPO, ContractPO.class, null, null, getConnection());



            for (int j = 0; list != null && j < list.size(); j++) {
                ContractPO c = list.get(j);
                c.setReceiveUserId(receiveUserId);


                if (groupId == null) {
                    MyException.newInstance("无法获得销售所属部门").throwException();
                }


                c.setOrgId(groupId);

                // 设置为未签收
                c.setSignedStatus(ContractSignedStatus.NotSigned);

                MySQLDao.insertOrUpdate(c, getConnection());
            }


            // 创建合同流转记录
            contractService.newContractRouteList(contractNo, "调配合同至【" + groupName + "】【" + userPO.getName() + "】", getLoginUser().getId(), conn);

        }



        return SUCCESS;
    }

    public String signedContract() throws Exception {


        String contractNOs = HttpUtils.getParameter(getRequest(), "contractNOs");
        String singedStatusString = HttpUtils.getParameter(getRequest(), "singedStatus");

        if (StringUtils.isEmpty(singedStatusString) || !StringUtils.isNumeric(singedStatusString)) {
            MyException.newInstance("签收标识有误【"+singedStatusString+"】").throwException();
        }

        if (!StringUtils.isEmpty(contractNOs)) {
            String [] contractNOsArray = contractNOs.split(",");

            for (int i = 0; i < contractNOsArray.length; i++) {
                String contractNO = contractNOsArray[i];

                List<ContractPO> listContractPO = contractService.listContractPOByContractNO(contractNO, getConnection());

                for (int j = 0; listContractPO != null && j < listContractPO.size(); j++) {

                    ContractPO contractPO = listContractPO.get(j);

                    // 检查合同持有人

                    if (!Config.hasPermission("销售合同管理_超级调配", getRequest())) {
                        if (!contractPO.getReceiveUserId().equals(getLoginUser().getId())) {
                            MyException.newInstance("持有人并非当前登录用户","contract.receiveUserId="+contractPO.getReceiveUserId()+"&loginUserId="+getLoginUser().getId()).throwException();
                        }
                    }

                    contractPO.setSignedStatus(Integer.parseInt(singedStatusString));

                    MySQLDao.insertOrUpdate(contractPO, getConnection());
                }
                String description = "【"+getLoginUser().getName()+"】签收合同【"+contractNO+"】";
                contractService.newContractRouteList(contractNO, description, getLoginUser().getId(), getConnection());
            }
        }

        return SUCCESS;
    }

    /**
     * 总部销售合同调配列表
     * 调用业务类的 listSendContracts 获取分页数据
     *
     * @return
     * @throws Exception
     */
    public String listContractVOs() throws Exception {
        Connection conn = getConnection();
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);
        conditions = MySQLDao.getQueryNumberParameters(request, ContractVO.class, conditions);



        if (Config.hasPermission("合同岗合同调配", getRequest())) {
            contractVO.setReceiveUserId(null);
        }
        else {
            contractVO.setReceiveUserId(getLoginUser().getId());
        }


        Pager pager = contractService.listContractVOs(contractVO, conditions, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 确定合同状态
     * 调用业务类的确认合同方法进行合同确认 confirmContract
     *
     * @return
     * @throws Exception
     */
    public String confirmContract() throws Exception {
        int count = 0;
        Connection conn = getConnection();
        UserPO configUser = getLoginUser();
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        List<ContractPO> contractPOs = contractService.getContractByContractNo(contractNO, conn);
        if (contractPOs == null || contractPOs.size() ==0) {
            MyException.newInstance("确认销售合同失败").throwException();
        }

        String description = "【"+configUser.getName()+"】确认合同状态";
        count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(), configUser, userPositionInfoPO,description, ContractRouteListStatus.ACTIONTYPE_CONFIRMCONTRACT, conn);
        if (count == 0) {
            MyException.deal(MyException.newInstance("确认销售合同失败"));
        }

        //移交到调配管理员
        description = "【"+configUser.getName()+"】确认合同状态";
        count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(), configUser, userPositionInfoPO, description, ContractRouteListStatus.ACTIONTYPE_SENDMANAGER, conn);
        if (count == 0) {
            MyException.deal(MyException.newInstance("移交调配管理员失败"));
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }

    public String changeProduction() throws Exception {

        String productionId = getHttpRequestParameter("productionId");
        String contractNo = getHttpRequestParameter("contractNo");


        contractService.changeProduction(contractNo, productionId, getConnection());

        getResult().setCode(ReturnObject.CODE_SUCCESS);
        getResult().setMessage("操作成功");
        getResult().setReturnValue("1");

        return SUCCESS;
    }


    public String queryContractNo() throws Exception {

        String contractNo = HttpUtils.getParameter(getRequest(), "contractNo");
        String productionId = HttpUtils.getParameter(getRequest(), "productionId");

        if (!StringUtils.isEmpty(contractNo) && !StringUtils.isEmpty(productionId)) {
            DatabaseSQL dbSQL = DatabaseSQL.getInstance("select distinct c.contractNo, c.status from sale_contract c where c.state=0 and c.ContractNo like ? and c.ProductionId= ?").addParameter(1, "%"+contractNo+"%").addParameter(2, productionId);

            List<ContractPO> list = MySQLDao.search(dbSQL, ContractPO.class, getConnection());

            JSONArray array = new JSONArray();

            for (ContractPO contract : list) {
                String status = contract.getStatus() == ContractStatus.Signed ? "已签约" : "未签约";

                JSONObject json = new JSONObject();


                json.put("text", contract.getContractNo() + "【"+status+"】");
                json.put("id", contract.getContractNo());

                array.add(json);
            }

            getResult().setReturnValue(array);
            getResult().setMessage("操作成功");
            getResult().setCode(ReturnObject.CODE_SUCCESS);
        }


        return SUCCESS;
    }

    /**
     * 确定合同归档状态状态
     * 调用业务类的确认合同方法进行合同确认 confirmContract
     *
     * @return
     * @throws Exception
     */
    public String confirmArchiveContract() throws Exception {
        Connection conn = getConnection();
        UserPO configUser = getLoginUser();

        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());

        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        List<ContractPO> contractPOs = contractService.getContractByContractNo(contractNO, conn);
        String description = "【"+configUser.getName()+"】确认合同归档状态";

        if (contractPOs == null || contractPOs.size() == 0) {
            MyException.newInstance("确认销售合同失败").throwException();
        }
        int count = contractService.saveContractRouteList(contractPOs.get(0).getContractNo(), configUser, userPositionInfoPO, description, ContractRouteListStatus.ACTIONYUPE_CONFIRMARCHIVECONTRACT, conn);
        if (count == 0) {
            MyException.deal(MyException.newInstance("确认销售合同失败"));
            getResult().setReturnValue("false");
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }

    /**
     * 获取我的历史合同
     * 调用业务类的 listContracts 获取分页数据
     *
     * @return
     * @throws Exception
     */
    public String getListHistoryContract() throws Exception {
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);
        UserPO sale = getLoginUser();
        Pager pager = contractService.getListHistoryContract(contractVO, sale.getId(), conditions, request, getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取我的合同
     * 调用业务类的 listContracts 获取分页数据
     *
     * @return
     * @throws Exception
     */
    public String getListContracts() throws Exception {
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);
        UserPO sale = getLoginUser();
        Pager pager = contractService.listContracts(contractVO, sale.getId(), conditions, request, getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取销售合同id,根据指定字符分割成数组。
     * <p/>
     * 调用业务类的 cancelContract 设置合同状态
     *
     * @return
     * @throws Exception
     */
    public String setContractStatus() throws Exception {

        Connection conn = getConnection();
        //获取销售合同编号
        String id = contractPO.getId();
        String[] ids = new String[]{id};
        String contractNo = contractPO.getContractNo();
        String comment = contractPO.getComment();
        //获取合同状态
        int detailStatus = contractPO.getDetailStatus();
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());

        int count = 0;
        //正常
        if (detailStatus == ContractDetailStatus.Active) {

            count = contractService.averageContract(contractNo, ids, comment, getLoginUser(), userPositionInfoPO, conn);
        }
        //遗失
        else {
            count = contractService.cancelContract(contractNo, ids, comment, getLoginUser(), userPositionInfoPO,conn);
        }
        if (count == 0) {
            MyException.deal(MyException.newInstance("销售合同遗失失败"));
        }
        return SUCCESS;
    }

    /**
     * 归档销售合同
     *
     * @return
     * @throws Exception
     */
    public String archiveContracts() throws Exception {
        //获取销售合同编号，
        String contractNO = contractPO.getContractNo();
        Connection conn = getConnection();
        UserPO archiveUser = getLoginUser();
        try {
            //分隔字符串
            String[] contractNos = contractNO.split(",");
            if (contractNos.length != 0) {
                for (int i = 0; i < contractNos.length; i++) {
                    //获取销售合同集合
                    String contractNo2 = contractNos[i];
                    List<ContractPO> contractPOs = contractService.getContractByContractNo(contractNo2, conn);
                    if (contractPOs == null || contractPOs.size() ==0) {
                        MyException.newInstance("归档销售合同失败").throwException();
                    }
                    int count = contractService.archiveContracts(contractPOs.get(0), archiveUser, conn);
                    if (count == 0) {
                        MyException.deal(MyException.newInstance("归档销售合同失败"));

                    }
                }
            }
        } catch (Exception e) {
            getResult().setReturnValue("false");
            MyException.deal(MyException.newInstance(e.getMessage()));
        }

        getResult().setReturnValue("true");
        return SUCCESS;
    }

    /**
     * 更具当前登录的人员部门获取销售员信息。
     *
     * @return
     * @throws Exception
     */
    public String getSaleUser() throws Exception {
        Connection conn = getConnection();
        //更具当前操作员获取部门信息
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());

        Pager pager = contractService.getSaleUser(userPositionInfoPO.getDepartmentId(), userVO, getRequest(), conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    /***
     * 获取合同综合查询的列表数据（所有合同信息）
     * @return
     */
    public String getContractCompositeSearchList() throws Exception {
        //获取前台传递过来的数据
        Connection conn = getConnection();
        HttpServletRequest request = getRequest();

        //组装查询条件
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractVO.class);
        conditions = MySQLDao.getQueryNumberParameters(request, ContractVO.class, conditions);
        Integer currentPage = Pager.getInstance(request).getCurrentPage();
        Integer showRowCount = Pager.getInstance(request).getShowRowCount();

        //调用service查询相应数据
        Pager pager;
        try {
            pager= contractService.getContractCompositeSearchList(contractVO, conditions, currentPage, showRowCount, conn);
        } catch (Exception e) {
            getResult().setCode(ReturnObject.CODE_EXCEPTION);
            getResult().setMessage(e.getMessage());
            return SUCCESS;
        }

        //将结果封装成JSON传递给前台
        getResult().setReturnValue(pager.toJsonObject());
        getResult().setMessage("获取合同信息成功");
        getResult().setCode(ReturnObject.CODE_SUCCESS);
        return SUCCESS;
    }


    /***
     * 通过productionId获取对应的产品分期的合同摘要信息
     * productionId
     * @return
     * @throws Exception
     */
    public String getContractAbstractByProduction() throws Exception {
        //1.获取传递过来的productionId
        String productionId = getHttpRequestParameter("productionId");
        Integer currentPage = Pager.getInstance(getRequest()).getCurrentPage();
        Integer showRowCount = Pager.getInstance(getRequest()).getShowRowCount();

        if (StringUtils.isEmpty(productionId)){
            getResult().setMessage("获取产品分期ID失败");
            getResult().setCode(ReturnObject.CODE_EXCEPTION);
        } else {
            //2.调用service通过productionId获取产品对应合同的摘要数据
            Pager pager = contractService.getContractAbstractByProduction(contractVO, productionId, currentPage, showRowCount, getConnection());

            //3.将获取的数据拼成JSON传递给前台
            getResult().setReturnValue(pager.toJsonObject());
            getResult().setMessage("获取产品分期对应合同摘要信息成功");
            getResult().setCode(ReturnObject.CODE_SUCCESS);
        }

        return SUCCESS;
    }


    /***
     * 通过productionHomeId获取该产品对应的合同摘要
     * @return
     * @throws Exception
     */
    public String getContractAbstractByProductionHome() throws Exception {
        //1.获取传递过来的productionHomeId
        String productionHomeId = getHttpRequestParameter("productionHomeId");
        Integer currentPage = Pager.getInstance(getRequest()).getCurrentPage();
        Integer showRowCount = Pager.getInstance(getRequest()).getShowRowCount();

        if (StringUtils.isEmpty(productionHomeId)){
            getResult().setMessage("获取产品ID失败");
            getResult().setCode(ReturnObject.CODE_EXCEPTION);
        } else {
            //2.调用service通过productionId获取产品对应合同的摘要数据
            Pager pager = contractService.getContractAbstractByProductionHome(contractVO, productionHomeId, currentPage, showRowCount, getConnection());

            //3.将获取的数据拼成JSON传递给前台
            getResult().setReturnValue(pager.toJsonObject());
            getResult().setMessage("获取产品对应合同摘要信息成功");
            getResult().setCode(ReturnObject.CODE_SUCCESS);
        }

        return SUCCESS;
    }


    public ContractService getContractService() {
        return contractService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    public ContractPO getContractPO() {
        return contractPO;
    }

    public void setContractPO(ContractPO contractPO) {
        this.contractPO = contractPO;
    }

    public ContractVO getContractVO() {
        return contractVO;
    }

    public void setContractVO(ContractVO contractVO) {
        this.contractVO = contractVO;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }
}
