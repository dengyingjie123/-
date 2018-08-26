package com.youngbook.service.sale.contract;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IProductionDao;
import com.youngbook.dao.sale.contract.IContractDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.sale.contract.*;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.Sale.contract.ContractApplicationVO;
import com.youngbook.entity.vo.Sale.contract.ContractRouteListVO;
import com.youngbook.entity.vo.Sale.contract.ContractVO;
import com.youngbook.entity.vo.system.MenuVO;
import com.youngbook.entity.vo.system.UserVO;
import com.youngbook.service.system.DepartmentService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/12/21
 * 描述：
 * ContractService: 合同业务处理类
 */

@Component("contractService")
public class ContractService {

    @Autowired
    IContractDao contractDao;

    @Autowired
    IOrderDao orderDao;


    @Autowired
    IProductionDao productionDao;


    public int setContractStatusCancelRequest(String contractNO, String comment, UserPO user, Connection conn) throws Exception {

        contractDao.setContractStatus(contractNO, ContractStatus.CancelRequest, conn);

        String description = "【"+user.getName()+"】申请合同【"+contractNO+"】作废，原因【"+comment+"】";

        newContractRouteList(contractNO, description, user.getId(), conn);

        return 1;
    }

    public int setContractStatusCancelConfirmed(String contractNO, String comment, UserPO user, Connection conn) throws Exception {

        /**
         * 检查确认的合同是否提交申请
         */
        List<ContractPO> list = contractDao.listContractPOByContractNO(contractNO, conn);

        if (list == null || list.size() == 0) {
            MyException.newInstance("无法找到合同号对应的数据").throwException();
        }

        // 验证是否有作废申请
//        for (int i = 0; i < list.size(); i++) {
//            ContractPO contractPO = list.get(i);
//
//            if (contractPO.getStatus() != ContractStatus.CancelRequest) {
//                MyException.newInstance("此合同尚未提出作废申请，请检查").throwException();
//            }
//        }


        /**
         * 执行合同作废确认
         */
        contractDao.setContractStatus(contractNO, ContractStatus.CancelConfirmed, conn);

        String description = "【"+user.getName()+"】同意合同【"+contractNO+"】作废，原因【"+comment+"】";

        newContractRouteList(contractNO, description, user.getId(), conn);

        return 1;
    }

    public ContractRouteListPO newContractRouteList(String contractNo, String description, String actionUserId, Connection conn) throws Exception {

        // 创建合同流转记录
        ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
        contractRouteListPO.setState(Config.STATE_CURRENT);
        contractRouteListPO.setContractNo(contractNo);
        contractRouteListPO.setActionDescription(description);
        contractRouteListPO.setActionTime(TimeUtils.getNow());
        contractRouteListPO.setActionUserId(actionUserId);

        MySQLDao.insertOrUpdate(contractRouteListPO, conn);

        return contractRouteListPO;
    }

    public ContractPO getContractByContractId(String contractId, Connection conn) throws Exception {
        return contractDao.getContractByContractId(contractId, conn);
    }

    public List<ContractPO> getContractByContractNo(String contractNO, Connection conn) throws Exception {
        return contractDao.getContractByContractNO(contractNO, conn);
    }

    public List<ContractPO> listContractPOByContractNO(String contractNO, Connection conn) throws Exception {
        return  contractDao.listContractPOByContractNO(contractNO, conn);
    }

    public int changeContractNo(String contractId, String contractNo, Connection conn) throws Exception {

        /**
         * 检查是否有合同编号存在
         */
        List<ContractPO> contains = contractDao.getContractByContractNO(contractNo, conn);
        if (contains != null && contains.size() > 1) {
            MyException.newInstance("需要修改的合同编号【"+contractNo+"】已存在").throwException();
        }

        ContractPO contractPO = contractDao.getContractByContractId(contractId, conn);

        String oldContractNo = contractPO.getContractNo();

        if (contractPO != null) {

            /**
             * 更改主表
             */
            List<ContractPO> list = contractDao.getContractByContractNO(oldContractNo, conn);

            for (ContractPO c : list) {
                String contractDisplayNo = contractNo + "-" + c.getContractDetailNo();
                c.setContractNo(contractNo);
                c.setContractDisplayNo(contractDisplayNo);
                c.setComment("合同号由【"+oldContractNo+"】改为【"+contractNo+"】");

                int count = MySQLDao.insertOrUpdate(c, conn);
                if (count != 1) {
                    MyException.newInstance("保存合同编号失败", "ContractId="+contractId+", New ContractNo="+contractNo).throwException();
                }
            }


            List<ContractRouteListPO> contractRouteListPOs = contractDao.getContractRouteListByContranctNo(oldContractNo, conn);

            for (ContractRouteListPO contractRouteList : contractRouteListPOs) {
                contractRouteList.setContractNo(contractNo);

                contractRouteList.setActionDescription("合同号由【"+oldContractNo+"】改为【"+contractNo+"】");

                int count = MySQLDao.insertOrUpdate(contractRouteList, conn);

                if (count != 1) {
                    MyException.newInstance("保存合同编号失败", "contractRouteList="+contractRouteList.getId()+", New ContractNo="+contractNo).throwException();
                }
            }
        }

        return 1;
    }

    /**
     * 1、验证数据有效性，判断对象 ContractApplicationVO
     * 3、验证HttpServletRequest
     * 4、验证connection
     * 5、组装SQL 查询 ContractApplication数据库表，
     * 组装SQL 需要满足一些条件，
     * 获取产品的销售合同范围。
     * 显示产品的名称，显示部门的名称。
     * 6、根据SQL分页查询。
     * <p/>
     * 获取显示销售合同申请的列表数据。
     *
     * @param contractApplicationVO 销售合同申请视图对象
     * @param conditions            时间段查询条件条件
     * @param request               http请求
     * @param conn                  数据连接
     * @return
     */
    public Pager listApplications(ContractApplicationVO contractApplicationVO, String DepartmentId, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {

        if (contractApplicationVO == null) {
            MyException.deal(MyException.newInstance("销售合同申请参数异常"));
        }
        if (DepartmentId == null) {
            MyException.deal(MyException.newInstance("销售合同部门申请参数异常"));
        }

        if (request == null) {
            MyException.deal(MyException.newInstance("请求参数数异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT DISTINCT");
        sbSQL.append("		temp.*, CONCAT(str1,str3,str2) AS contracts,");
        sbSQL.append("		checkUser.`name` AS checkName");
        sbSQL.append("	FROM");
        sbSQL.append("		(");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				application.sid,");
        sbSQL.append("				application.id,");
        sbSQL.append("				application.state,");
        sbSQL.append("				application.operatorId,");
        sbSQL.append("				date_format(application.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("				application.ApplicationUserId,");
        sbSQL.append("				application.ProductionId,");
        sbSQL.append("				application.Counts,");
        sbSQL.append("				date_format(application.ApplicationTime,'%Y-%m-%d %H:%i:%S') as ApplicationTime ,");
        sbSQL.append("				date_format(application.CheckTime,'%Y-%m-%d %H:%i:%S') as CheckTime ,");
        sbSQL.append("				application.CheckId,");
        sbSQL.append("				application.CheckComment,");
        sbSQL.append("				application.CheckState,");
        sbSQL.append("				application.DepartmentId,");
        sbSQL.append("				application.DepartmentName,");
        sbSQL.append("				production.`Name` productionName,");
        sbSQL.append("				appuser.`Name` applicationUserName,");
        sbSQL.append("				(");
        sbSQL.append("					SELECT");
        sbSQL.append("						min(contract1.ContractNo)");
        sbSQL.append("					FROM");
        sbSQL.append("						sale_contract contract1");
        sbSQL.append("					WHERE");
        sbSQL.append("						1 = 1");
        sbSQL.append("					AND contract1.state = 0");
        sbSQL.append("					AND contract1.ProductionId = production.id");
        sbSQL.append("					AND contract1.applicationId = application.id");
        sbSQL.append("				) AS str1,");
        sbSQL.append("				(");
        sbSQL.append("					SELECT");
        sbSQL.append("						max(contract2.ContractNo) AS ContractNo2");
        sbSQL.append("					FROM");
        sbSQL.append("						sale_contract contract2");
        sbSQL.append("					WHERE");
        sbSQL.append("						1 = 1");
        sbSQL.append("					AND contract2.state = 0");
        sbSQL.append("					AND contract2.ProductionId = production.id");
        sbSQL.append("					AND contract2.applicationId = application.id");
        sbSQL.append("				) AS str2 , '-' as str3 ");
        sbSQL.append("			FROM");
        sbSQL.append("				sale_contractapplication application,");
        sbSQL.append("				crm_production production,");
        sbSQL.append("				system_user appuser");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("			AND application.state = 0");
        sbSQL.append("			AND appuser.state = 0");
        sbSQL.append("			AND production.state = 0");
        sbSQL.append("			AND application.ProductionId = production.id");
        sbSQL.append("			AND appuser.Id = application.ApplicationUserId");
        sbSQL.append("		) AS temp");
        sbSQL.append("	LEFT JOIN system_user checkUser ON temp.CheckId = checkUser.Id");
        sbSQL.append("	AND checkUser.state = 0");
        sbSQL.append("	where ");
        sbSQL.append("	1=1 ");
        sbSQL.append("	and temp.DepartmentId = '" + Database.encodeSQL(DepartmentId) + "'");
        sbSQL.append("	ORDER BY ");
        sbSQL.append("		temp.sid DESC");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        return Pager.query(sbSQL.toString(), contractApplicationVO, conditions, request, queryType, conn);

    }

    /**
     * 1、验证产品编号
     * 2、验证合同份数
     * 3、验证 applicationUser  并判断userPO对象的id 是否为有效字符串。
     * 4、验证 departmentName,departmentId,connection
     * 5、获取合同详细信息，创建 contractApplicationPO 对象，设置产品编号，设置合同份数，设置操作员信息。
     * 设置合同审核状态为 ContractStatus.CHECKSTATE 未审核
     * 6、保存 contractApplicationPO 对象 返回影响行数。
     * 验证影响行数，true，创建 ContractRouteListPO 对象，
     * 设置ContractRouteListPO对象的属性，设置 OrgId 为 contractApplicationPO 的 OrgId,
     * 设置ActionType 的值为 ContractrouteListStatus.ACTIONTYPE_APPLY 申请
     * 创建一个 ContractRouteListService 的业务类对象，
     * 调用 ContractRouteListService 的 InsertOfUpdate 方法执行业务操作返回影响行数。
     *
     * @param productionId    产品编号
     * @param count           合同份数
     * @param applicationUser 操作员信息
     * @param conn            数据库连接
     * @return
     */

    public int newApplication(String productionId, int count, UserPO applicationUser, UserPositionInfoPO userPositionInfoPO, Connection conn) throws Exception {
        //验证产品编号
        if (StringUtils.isEmpty(productionId)) {
            MyException.deal(MyException.newInstance("销售合同申请产品编号参数异常"));
        }
        //合同份数不能小于 0
        if (count < 1) {
            MyException.deal(MyException.newInstance("销售合同申请产品编号参数异常"));
        }
        //验证合同申请人
        if (applicationUser == null || StringUtils.isEmpty(applicationUser.getId())) {
            MyException.deal(MyException.newInstance("销售合同申请人参数异常"));
        }
        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }


        //创建合同申请对象
        ContractApplicationPO contractApplicationPO = new ContractApplicationPO();

        //申请人数据
        contractApplicationPO.setApplicationTime(TimeUtils.getNow());
        contractApplicationPO.setApplicationUserId(applicationUser.getId());
        //未审批
        contractApplicationPO.setCheckState(ContractApplicationStatus.Uncheck);
        //产品编号
        contractApplicationPO.setProductionId(productionId);
        //合同份数
        contractApplicationPO.setCounts(count);
        contractApplicationPO.setDepartmentId(userPositionInfoPO.getDepartmentId());
        contractApplicationPO.setDepartmentName(userPositionInfoPO.getDepartmentName());
        int number = MySQLDao.insertOrUpdate(contractApplicationPO, applicationUser.getId(), conn);

        if (number == 1) {
            ContractPO contractPO = new ContractPO();
            String description = "【" + applicationUser.getName()+"】添加销售合同申请";
            number = saveContractRouteList(contractPO.getContractNo(), applicationUser, userPositionInfoPO, description, ContractRouteListStatus.ACTIONTYPE_APPLY, conn);

        }
        if (number != 1) {
            MyException.newInstance("销售合同申请添加失败").throwException();
        }

        return number;

    }

    /**
     * 验证 contractApplicationPO、applicationUser、conn
     * 更新销售合同、添加新销售合同记录
     *
     * @param contractApplicationPO 销售合同
     * @param applicationUser       操作员信息
     * @param conn                  数据库连接
     * @return
     */
    public int updateApplication(ContractApplicationPO contractApplicationPO, UserPO applicationUser, Connection conn) throws Exception {
        //验证产品编号
        if (contractApplicationPO == null || StringUtils.isEmpty(contractApplicationPO.getId())) {
            MyException.deal(MyException.newInstance("更新销售合同参数异常"));
        }
        //验证合同申请人
        if (applicationUser == null || StringUtils.isEmpty(applicationUser.getId())) {
            MyException.deal(MyException.newInstance("更新销售合同操作员异常"));
        }
        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        ContractApplicationPO temp = new ContractApplicationPO();
        temp.setId(contractApplicationPO.getId());
        temp.setState(Config.STATE_CURRENT);
        //获取历史数据
        temp = MySQLDao.load(temp, ContractApplicationPO.class, conn);
        temp.setState(Config.STATE_UPDATE);
        //更新数据
        int count = MySQLDao.update(temp, conn);
        //新增数据
        if (count == 1) {
            contractApplicationPO.setSid(MySQLDao.getMaxSid("sale_contractApplication", conn));
            contractApplicationPO.setState(Config.STATE_CURRENT);
            contractApplicationPO.setOperatorId(applicationUser.getId());
            contractApplicationPO.setOperateTime(TimeUtils.getNow());
            //申请人数据
            contractApplicationPO.setApplicationTime(TimeUtils.getNow());
            contractApplicationPO.setApplicationUserId(applicationUser.getId());
            //未审批
            contractApplicationPO.setCheckState(ContractApplicationStatus.Uncheck);


            count = MySQLDao.insert(contractApplicationPO, conn);
        }
        if (count != 1) {
            MyException.deal(MyException.newInstance("更新销售合同失败"));
        }

        return count;

    }

    /**
     * 验证 contractApplicationPO、applicationUser、conn
     * 更新销售合同、新增删除售合同记录
     *
     * @param contractApplicationPO 销售合同
     * @param applicationUser       操作员信息
     * @param conn                  数据库连接
     * @return
     */
    public int deleteApplication(ContractApplicationPO contractApplicationPO, UserPO applicationUser, DepartmentPO departmentPO, Connection conn) throws Exception {
        //验证产品编号
        if (contractApplicationPO == null || StringUtils.isEmpty(contractApplicationPO.getId())) {
            MyException.deal(MyException.newInstance("删除销售合同参数异常"));
        }
        //验证合同申请人
        if (applicationUser == null || StringUtils.isEmpty(applicationUser.getId())) {
            MyException.deal(MyException.newInstance("删除销售合同操作员异常"));
        }
        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        ContractApplicationPO temp = new ContractApplicationPO();
        temp.setId(contractApplicationPO.getId());
        temp.setState(Config.STATE_CURRENT);
        //获取历史数据
        temp = MySQLDao.load(temp, ContractApplicationPO.class, conn);
        temp.setState(Config.STATE_UPDATE);
        //更新数据
        int count = MySQLDao.update(temp, conn);
        //新增数据
        if (count == 1) {
            contractApplicationPO.setSid(MySQLDao.getMaxSid("sale_contractApplication", conn));
            contractApplicationPO.setState(Config.STATE_DELETE);
            contractApplicationPO.setOperatorId(applicationUser.getId());
            contractApplicationPO.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(contractApplicationPO, conn);
            if (count == 1) {
                ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
                //设置状态
                contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_DELETEAPPLICATIONCONTRACR);

                //设置部门
                contractRouteListPO.setActionDescription("【"+applicationUser.getName()+"】删除销售合同申请");
                count = MySQLDao.insertOrUpdate(contractRouteListPO, applicationUser.getId(), conn);
            }
            if (count != 1) {
                MyException.deal(MyException.newInstance("删除销售合同申请"));
            }
        }
        if (count != 1) {
            MyException.deal(MyException.newInstance("删除销售合同失败"));
        }

        return count;

    }


    /**
     * 审核新增的销售合同信息。
     * 1、验证数据有效性，判断对象contractApplicationPO
     * 2、验证userPO 并判断userPO对象的id 是否为有效字符串。如果两个条件都不成立就抛出异常
     * 3、验证connection
     * 4、验证passOrNOt  不小于0
     * 4、创建一个临时的 contractApplicationPO 对象，temp，
     * 设置temp 的id设置为当前 contractApplicationPO 对象的id,
     * 设置temp.state 为 Config.STATE_CURRENT, 获取对象并赋值给temp,
     * 修改temp.state 为Config.STATE_UPDATE 更新temp对象，返回影响行数，
     * 判断影响行数是否等于 1，
     * 判断为true，设置 contractApplicationPO 对象 操作员的操作编号与操作时间，
     * 设置 contractApplicationPO 审批状态 为 passOrNOt
     * 保存对象 返回影响行数。
     * 判断影响行数是否等于 1，
     * 判断为true， 创建 ContractRouteListPO对象，
     * 设置ContractRouteListPO对象的属性，设置 OrgId 为 contractApplicationPO 的 OrgId,
     * 判断 passOrNOt 等于 ContractStatus.CHECKSTATE_CHECK_OK  审核通过
     * 设置ActionType 的值为 ContractrouteListStatus.ACTIONTYPE_CHECK_OK  审核通过 。
     * 判断 passOrNOt 等于 ContractStatus.CHECKSTATE_CHECK_NO 审批不通过，
     * 设置ActionType 的值为 ContractrouteListStatus.ACTIONTYPE_CHECK_NO  审批不通过。
     * 创建一个ContractRouteListService 的业务类对象，调用 ContractRouteListService 的InsertOfUpdate 方法执行业务操作返回影响行数。
     * 判断影响行数是否等于 1，
     * 判断为true，
     * passOrNOt 等于 ContractStatus.CHECKSTATE_CHECK_OK审批通过
     * 调用 newContract 方法生成对应的销售合同记录 返回 1或者0，1代表成功，0 代表失败。
     *
     * @param contractApplicationPO 合同申请对象
     * @param passOrNot             检查状态
     * @param checkUser             检查者
     * @param conn                  数据库连接
     * @return
     */
    public int checkApplication(ContractApplicationPO contractApplicationPO, int passOrNot, int beginNumber, UserPO checkUser, DepartmentPO departmentPO, Connection conn) throws Exception {

        //验证产品编号
        if (contractApplicationPO == null || StringUtils.isEmpty(contractApplicationPO.getId())) {
            MyException.deal(MyException.newInstance("销售合同审批申请参数异常"));
        }

        if (passOrNot < 0) {
            MyException.deal(MyException.newInstance("销售合同审批状态参数异常"));
        }
        //验证合同申请人
        if (checkUser == null || StringUtils.isEmpty(checkUser.getId())) {
            MyException.deal(MyException.newInstance("销售合同审批人参数参数异常"));
        }
        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }


        contractApplicationPO.setOperatorId(checkUser.getId());
        contractApplicationPO.setOperateTime(TimeUtils.getNow());
        //审批
        contractApplicationPO.setCheckId(checkUser.getId());
        contractApplicationPO.setCheckState(passOrNot);
        contractApplicationPO.setCheckTime(TimeUtils.getNow());

        int count = MySQLDao.insertOrUpdate(contractApplicationPO, checkUser.getId(), conn);

        if (count != 1) {
            MyException.newInstance("保存合同申请单失败").throwException();
        }

        // 保存流转记录
        ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
        // 设置状态审批通过
        if (passOrNot == ContractApplicationStatus.Checked) {

            contractRouteListPO.setActionDescription("【"+checkUser.getName()+"】审批销售合同申请成功");
            contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_CHECK_OK);
        }
        // 设置状态审批不通过
        else {
            contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_CHECK_NO);
            contractRouteListPO.setActionDescription("【"+checkUser.getName()+"】审批销售合同申请失败");
        }


        // 保存流转记录
        contractRouteListPO.setActionTime(TimeUtils.getNow());
        contractRouteListPO.setActionUserId(checkUser.getId());
        count = MySQLDao.insertOrUpdate(contractRouteListPO, checkUser.getId(), conn);
        if (count != 1) {
            MyException.newInstance("保存合同申请流转记录失败").throwException();
        }



        // 创建合同
        if (passOrNot == ContractApplicationStatus.Checked) {
            //添加销售合同记录
            count = newContract(contractApplicationPO, contractApplicationPO.getProductionId(), contractApplicationPO.getCounts(), beginNumber, checkUser, conn);
        }
        if (count != 1) {
            MyException.newInstance("创建合同失败").throwException();
        }

        return count;
    }


    /**
     * 1、验证数据有效性，ContractVO
     * 2、验证List<KVObject>
     * 2、验证HttpServletRequest
     * 3、验证connection
     * 4、组装SQL 查询 Contract数据库表，
     * 合同申请部门信息，产品信信，合同详细信息。
     * 前合同在合同流程表中最后一条数据的状态： 确定状态、等待调配、
     * <p/>
     * 5、分页查询 返回分页数据
     * <p/>
     * 调配合同列表
     *
     * @param contractVO 销售合同
     * @param conditions 时间段查询条件
     * @param request    http请求
     * @param conn       数据库链接
     * @return
     */
    public Pager listSendContracts(ContractVO contractVO, List<KVObject> conditions,String departmentId, HttpServletRequest request, Connection conn) throws Exception {
        if (contractVO == null) {
            MyException.deal(MyException.newInstance("财富中心调配销售合同参数异常"));
        }

        if (conditions == null) {
            MyException.deal(MyException.newInstance("财富中心调配时间段查询参数异常"));
        }
        if (request == null) {
            MyException.deal(MyException.newInstance("Http请求参数异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        //组装SQL
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT");
        sbSQL.append("      d.`name` DepartmentName,");
        sbSQL.append("		temp.*,");
        sbSQL.append("		crmOrder.CustomerName,");
        sbSQL.append("	    date_format(crmOrder.operateTime,'%Y-%m-%d %H:%i:%S') as sigendTime,");
        sbSQL.append("		crmOrder.Money AS money");
        sbSQL.append("	FROM");
        sbSQL.append("		(");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				contract.sid,");
        sbSQL.append("				contract.id,");
        sbSQL.append("				contract.state,");
        sbSQL.append("				contract.operatorId,");
        sbSQL.append("				date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("				contract.OrgId,");
        sbSQL.append("				contract.ProductionId,");
        sbSQL.append("				contract.ProductionName,");
        sbSQL.append("				contract.ApplicationId,");
        sbSQL.append("				contract.`STATUS`,");
        sbSQL.append("				contract.DetailStatus,");
        sbSQL.append("				contract.ContractNo,");
        sbSQL.append("				contract.ContractDetailNo,");
        sbSQL.append("				contract.ContractDisplayNo,");
        sbSQL.append("				contract.CancelId,");
        sbSQL.append("				contract.CancelName,");
        sbSQL.append("				date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("				contract.`COMMENT`,");
        sbSQL.append("				contract.receiveUserId,");
        sbSQL.append("				contract.receiveUserName,");
        sbSQL.append("				contractRoute.ActionType,");
        sbSQL.append("				contractRoute.sendExpress,");
        sbSQL.append("				contractRoute.sendExpressId,");
        sbSQL.append("				contractRoute.DepartmentId,");
        sbSQL.append("				date_format(application.ApplicationTime,'%Y-%m-%d %H:%i:%S') as ApplicationTime,");
        sbSQL.append("				date_format(application.CheckTime,'%Y-%m-%d %H:%i:%S') as CheckTime,");
        sbSQL.append("				appuser.`name` AS applicationUserName,");
        sbSQL.append("				checkuser.`name` AS checkName");
        sbSQL.append("			FROM");
        sbSQL.append("				sale_contract AS contract,");
        sbSQL.append("				sale_contractroutelist AS contractRoute,");
        sbSQL.append("				sale_contractapplication AS application,");
        sbSQL.append("				system_user AS appuser,");
        sbSQL.append("				system_user AS checkuser");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("			AND contract.state = 0");
        sbSQL.append("			AND appuser.state = 0");
        sbSQL.append("			AND checkuser.state = 0");
        sbSQL.append("			AND contractRoute.State = 0");
        sbSQL.append("			AND application.state = 0");
        sbSQL.append("			AND contract.contractNo = contractRoute.contractNo");
        sbSQL.append("			AND appuser.id = application.applicationUserId");
        sbSQL.append("			AND checkuser.id = application.CheckId");
        sbSQL.append("			and application.id=contract.ApplicationId");
        sbSQL.append("			AND contractRoute.sid = (");
        sbSQL.append("				SELECT DISTINCT");
        sbSQL.append("					MAX(contractroute2.sid)");
        sbSQL.append("				FROM");
        sbSQL.append("					sale_contractroutelist contractroute2");
        sbSQL.append("				WHERE");
        sbSQL.append("					1 = 1");
        sbSQL.append("				AND contractroute2.state = 0");
        sbSQL.append("				AND contractroute2.contractNo = contract.contractNo");
        sbSQL.append("			)");
        sbSQL.append("			AND (");
        sbSQL.append("			     contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_SENDING);
        sbSQL.append("				OR contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_SIGN);
        sbSQL.append("				OR contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_SENDMANAGER);
        sbSQL.append("			)");
        sbSQL.append("		) AS temp");
        sbSQL.append("	LEFT JOIN crm_order AS crmOrder ON crmOrder.state = 0 ");
        sbSQL.append("	AND crmOrder.contractNo = temp.ContractNo");
        sbSQL.append(" LEFT JOIN system_department d on temp.OrgId=d.id");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        sbSQL.append("	and temp.OrgId ='" + Database.encodeSQL(departmentId) + "'");
        sbSQL.append("	GROUP BY");
        sbSQL.append("		temp.ContractNo");
        sbSQL.append("	ORDER BY");
        sbSQL.append("		temp.ContractNo ASC");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        return Pager.query(sbSQL.toString(), contractVO, conditions, request, queryType, conn);
    }

    public Pager listContractVOs(ContractVO contractVO, List<KVObject> conditions ,HttpServletRequest request, Connection conn) throws Exception {
        return contractDao.listContractVOs(contractVO, conditions, request, conn);
    }


    /**
     * 1、验证 contracts 销售合同集合的 size 是否大于0 ，
     * 2、验证userPO 对象是为null， 并判断userPO对象的id 是否为有效字符串
     * 3、验证connection
     * 根据合同状态将合同移交到各个状态的管理员
     * 将合同流转状态改为移交管理员
     *
     * @param contractPO  销售合同集合
     * @param senderUser 发送调配的操作用户
     * @param conn       数据库链接
     * @return
     */
    public int sendContracts(ContractPO contractPO, UserPO senderUser, UserPositionInfoPO userPositionInfoPO, Connection conn) throws Exception {
        if (contractPO == null) {
            MyException.deal(MyException.newInstance("合同移交参数异常"));
        }

        if (senderUser == null || StringUtils.isEmpty(senderUser.getId())) {
            MyException.deal(MyException.newInstance("合同移交操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        int count = 0;

        //合同流转对象
        ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
        //判断销售合同的状态
        int contractType = contractPO.getStatus();
        int actionType = 0;
        String description = "";

        //未签约
        if (contractType == ContractStatus.Unsigned) {

            //清空合同已经领用的销售员
            count = cleanContracltReceiveUser(contractPO, senderUser, conn);
            if (count != 1) {
                MyException.newInstance("清空销售合同销售员失败").throwException();
            }
            //移交空白合同管理员
            actionType = ContractRouteListStatus.ACTIONTYPE_BLANKCONTRACTMANAGER;
            description = "【"+senderUser.getName()+"】移交空白销售合同管理员";
        }
        //签约
        else if (contractType == ContractStatus.Signed) {
            //移交空白合同管理员
            actionType = ContractRouteListStatus.ACTIONTYPE_SING_OK_MANAGER;
            description = "【"+senderUser.getName()+"】移交签约销售合同管理员";
        }
        //异常
        else if (contractType == ContractStatus.Exception) {

            actionType = ContractRouteListStatus.ACTIONTYPE_EXCEPTIONMANAGER;
            description = "【"+senderUser.getName()+"】移交异常销售合同管理员";
        }

        count = saveContractRouteList(contractPO.getContractNo(), senderUser, userPositionInfoPO, description, actionType, conn);


        if (count != 1) {
            MyException.deal(MyException.newInstance("销售合同移交失败"));
        }

        return 1;
    }


    /**
     * 验证 contractPO
     * 验证 optionUser
     * 验证 conn
     * 更新当前合同，设置操作员，新增清空销售员的合同 返回影响行数
     *
     * @param contractPO 销售合同
     * @param optionUser 操作员
     * @param conn       数据库连接
     * @return
     * @throws Exception
     */
    public int cleanContracltReceiveUser(ContractPO contractPO, UserPO optionUser, Connection conn) throws Exception {
        if (contractPO == null) {
            MyException.deal(MyException.newInstance("清空销售员，销售合同参数异常"));
        }

        if (optionUser == null || StringUtils.isEmpty(optionUser.getId())) {
            MyException.deal(MyException.newInstance("清空销售员,调配合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        contractPO.setState(Config.STATE_UPDATE);
        int count = MySQLDao.update(contractPO, conn);
        if (count != 1) {
            MyException.deal(MyException.newInstance("清空销售合同销售员失败"));
        }
        contractPO.setSid(MySQLDao.getMaxSid("Sale_Contract", conn));
        contractPO.setState(Config.STATE_CURRENT);
        contractPO.setOperatorId(optionUser.getId());
        contractPO.setOperateTime(TimeUtils.getNow());
        contractPO.setReceiveUserId("");
        return MySQLDao.insert(contractPO, conn);
    }

    /**
     * 验证 contractPO
     * 验证 optionUser
     * 验证 conn
     * 更新当前合同，设置操作员，新增设置销售员的合同 返回影响行数
     *
     * @param contractPO  合同
     * @param receiveUser 销售员
     * @param optionUser  操作员
     * @param conn
     * @return
     * @throws Exception
     */
    public int addContracltReceiveUser(ContractPO contractPO, UserPO receiveUser, UserPO optionUser, Connection conn) throws Exception {
        if (contractPO == null) {
            MyException.deal(MyException.newInstance("分配销售员，销售合同参数异常"));
        }

        if (optionUser == null || StringUtils.isEmpty(optionUser.getId())) {
            MyException.deal(MyException.newInstance("分配销售员,调配合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        contractPO.setState(Config.STATE_UPDATE);
        int count = MySQLDao.update(contractPO, conn);
        if (count != 1) {
            MyException.deal(MyException.newInstance("分配销售合同销售员失败"));
        }
        contractPO.setSid(MySQLDao.getMaxSid("Sale_Contract", conn));
        contractPO.setState(Config.STATE_CURRENT);
        contractPO.setOperatorId(optionUser.getId());
        contractPO.setOperateTime(TimeUtils.getNow());

        return MySQLDao.insert(contractPO, conn);
    }

    public int contractSigned(String contractId,  String userId, Connection conn) throws Exception {
        return contractDao.contractSigned(contractId, userId, conn);
    }

    /**
     * 保存合同流转记录
     * @param contractNo
     * @param actionUser
     * @param actionUserPositionInfo
     * @param description
     * @param conn
     * @return
     * @throws Exception
     */
    public int saveContractRouteList(String contractNo, UserPO actionUser, UserPositionInfoPO actionUserPositionInfo, String description, int actionType, Connection conn) throws Exception {

        if (actionUser == null || StringUtils.isEmpty(actionUser.getId())) {
            MyException.newInstance("销售合同操作员异常").throwException();
        }

        if (actionUserPositionInfo == null || StringUtils.isEmpty(actionUserPositionInfo.getDepartmentId()) || StringUtils.isEmpty(actionUserPositionInfo.getDepartmentFullName())) {
            MyException.newInstance("销售合同操作员岗位异常").throwException();
        }

        int count = 0;

        //合同流转对象
        ContractRouteListPO contractRouteListPO = new ContractRouteListPO();

        contractRouteListPO.setActionType(actionType);
        if(!StringUtils.isEmpty(contractNo)) {
            contractRouteListPO.setContractNo(contractNo);
        }

        //设置部门
        contractRouteListPO.setActionUserId(actionUser.getId());
        contractRouteListPO.setActionTime(TimeUtils.getNow());

        contractRouteListPO.setActionDescription(description);
        count = MySQLDao.insertOrUpdate(contractRouteListPO, actionUser.getId(), conn);

        if (count != 1) {
            MyException.newInstance("合同流转记录保存失败").throwException();
        }

        return 1;
    }








    /**
     * 1、验证数据有效性，ContractVO
     * 2、验证List<KVObject>
     * 2、验证HttpServletRequest
     * 3、验证connection
     * 4、组装SQL 查询 Contract数据库表，
     * 显示产品信息，签收人，合同的状态未签约，合同没有分配销售员 合同组部门
     * 为条件判断是否已经签收过。
     * 分页查询 返回分页数据
     * <p/>
     * 显示空白销售合同
     *
     * @param contractVO 销售合同对象
     * @param conditions 时间段查询条件对象
     * @param request    http请求
     * @param conn       数据库链接
     * @return
     */
    public Pager listBlankContracts(ContractVO contractVO, List<KVObject> conditions, String departmentId, HttpServletRequest request, Connection conn) throws Exception {
        if (contractVO == null) {
            MyException.deal(MyException.newInstance("销售合同参数异常"));
        }
        if (departmentId == null) {
            MyException.deal(MyException.newInstance("空白销售合同财富中心参数异常"));
        }
        if (conditions == null) {
            MyException.deal(MyException.newInstance("时间段查询条件参数失败"));
        }
        if (request == null) {
            MyException.deal(MyException.newInstance("请求参数异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        //组装SQL
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT");
        sbSQL.append("      d.`name` DepartmentName,");
        sbSQL.append("		temp.* ");
        sbSQL.append("	FROM");
        sbSQL.append("		(");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				contract.sid,");
        sbSQL.append("				contract.id,");
        sbSQL.append("				contract.state,");
        sbSQL.append("				contract.operatorId,");
        sbSQL.append("              date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("				contract.OrgId,");
        sbSQL.append("				contract.ProductionId,");
        sbSQL.append("				contract.ProductionName,");
        sbSQL.append("				contract.ApplicationId,");
        sbSQL.append("				contract.`STATUS` as status ,");
        sbSQL.append("				contract.DetailStatus,");
        sbSQL.append("				contract.ContractNo,");
        sbSQL.append("				contract.ContractDetailNo,");
        sbSQL.append("				contract.ContractDisplayNo,");
        sbSQL.append("				contract.CancelId,");
        sbSQL.append("				contract.CancelName,");
        sbSQL.append("              date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("				contract.`COMMENT`,");
        sbSQL.append("				contract.receiveUserId,");
        sbSQL.append("				contract.receiveUserName,");
        sbSQL.append("				contractRoute.ActionType,");
        sbSQL.append("				contractRoute.sendExpress,");
        sbSQL.append("				contractRoute.sendExpressId,");
        sbSQL.append("				contractRoute.DepartmentId,");
        sbSQL.append("              date_format(application.ApplicationTime,'%Y-%m-%d %H:%i:%S') as sigTime,");
        sbSQL.append("              date_format(application.CheckTime,'%Y-%m-%d %H:%i:%S') as CheckTime,");
        sbSQL.append("				appuser.`name` as applicationUserName,");
        sbSQL.append("				checkuser.`name` as checkName");
        sbSQL.append("			FROM");
        sbSQL.append("				sale_contract AS contract,");
        sbSQL.append("				sale_contractroutelist AS contractRoute,");
        sbSQL.append("				sale_contractapplication AS application,");
        sbSQL.append("				system_user  AS appuser,");
        sbSQL.append("				system_user as checkuser");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("			AND contract.state = 0");
        sbSQL.append("			AND appuser.state = 0");
        sbSQL.append("			AND checkuser.state = 0");
        sbSQL.append("			AND contractRoute.State = 0");
        sbSQL.append("			AND application.state = 0");
        sbSQL.append("			AND contract.contractNo = contractRoute.contractNo");
        sbSQL.append("			AND appuser.id = application.applicationUserId");
        sbSQL.append("			AND checkuser.id = application.CheckId");
        sbSQL.append("			and application.id=contract.ApplicationId");
        sbSQL.append("			AND contractRoute.sid = (");
        sbSQL.append("				SELECT DISTINCT");
        sbSQL.append("					MAX(contractroute2.sid)");
        sbSQL.append("				FROM");
        sbSQL.append("					sale_contractroutelist contractroute2");
        sbSQL.append("				WHERE");
        sbSQL.append("					1 = 1");
        sbSQL.append("				AND contractroute2.state = 0");
        sbSQL.append("				AND contractroute2.contractNo = contract.contractNo");
        sbSQL.append("			)");
        sbSQL.append("			AND (");
        sbSQL.append("				contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_BLANKCONTRACTMANAGER);
        sbSQL.append("				or  contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_DISTRIBUTECONTRACT);
        sbSQL.append("				or  contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_SING_OK);
        sbSQL.append("				or  contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_CANCELSING);
        sbSQL.append("			)");
        sbSQL.append("		) AS temp");
        sbSQL.append(" LEFT JOIN system_department d on temp.OrgId=d.id");
        sbSQL.append("	where ");
        sbSQL.append("	1=1");
        sbSQL.append("	and temp.OrgId ='" + Database.encodeSQL(departmentId) + "'");
        sbSQL.append("	GROUP BY");
        sbSQL.append("		temp.ContractNo");
        sbSQL.append("	ORDER BY");
        sbSQL.append("		temp.ContractNo ASC");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        return Pager.query(sbSQL.toString(), contractVO, conditions, request, queryType, conn);
    }

    /**
     * 1、验证数据有效性，contractNo
     * 2、验证List<KVObject>
     * 2、验证HttpServletRequest
     * 3、验证connection
     * 4、组装SQL 查询 Contract数据库表，
     * 显示产品信息，订单信息。
     * 为条件判断是否已经签收过。
     * 分页查询 返回分页数据
     * <p/>
     * 销售合同详情
     *
     * @param contractNo 销售合同对象
     * @param conditions 时间段查询条件对象
     * @param request    http请求
     * @param conn       数据库链接
     * @return
     */
    public Pager getListDetailContracts(String contractNo, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        if (StringUtils.isEmpty(contractNo)) {
            MyException.deal(MyException.newInstance("销售合同编号参数异常"));
        }

        if (conditions == null) {
            MyException.deal(MyException.newInstance("时间段查询条件参数失败"));
        }
        if (request == null) {
            MyException.deal(MyException.newInstance("请求参数异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        //组装SQL
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT");
        sbSQL.append("		temp.*, corder.CustomerName,");
        sbSQL.append("		corder.operateTime AS sigendTime,");
        sbSQL.append("		corder.Money AS money");
        sbSQL.append("	FROM");
        sbSQL.append("		(");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				contract.sid,");
        sbSQL.append("				contract.id,");
        sbSQL.append("				contract.state,");
        sbSQL.append("				contract.operatorId,");
        sbSQL.append("              date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("				contract.OrgId,");
        sbSQL.append("				contract.ProductionId,");
        sbSQL.append("				contract.ProductionName,");
        sbSQL.append("				contract.ApplicationId,");
        sbSQL.append("				contract.`STATUS`,");
        sbSQL.append("				contract.DetailStatus,");
        sbSQL.append("				contract.ContractNo,");
        sbSQL.append("				contract.ContractDetailNo,");
        sbSQL.append("				contract.ContractDisplayNo,");
        sbSQL.append("				contract.CancelId,");
        sbSQL.append("				contract.CancelName,");
        sbSQL.append("              date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("				contract.`COMMENT`,");
        sbSQL.append("				contract.receiveUserId,");
        sbSQL.append("				contract.receiveUserName");
        sbSQL.append("			FROM");
        sbSQL.append("				sale_contract AS contract");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("			AND contract.state = 0");
        sbSQL.append("			and contract.ContractNo='" + Database.encodeSQL(contractNo) + "'");
        sbSQL.append("		) AS temp");
        sbSQL.append("	LEFT JOIN crm_order AS corder ON corder.state = 0");
        sbSQL.append("	AND corder.contractNo = temp.ContractNo");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        return Pager.query(sbSQL.toString(), new ContractVO(), conditions, request, queryType, conn);
    }

    /**
     * 1、验证 contractNO
     * 2、验证 saleUser
     * 2、验证userPO 对象是为null， 并判断userPO对象的id 是否为有效字符串。如果两个条件都不成立就抛出异常
     * 3、验证connection
     * 4、根据 contractId 合同编号 组装 获取当前合同编号的所有合同,验证集合
     * 5、遍历集合
     * 创建 ContractPO 对象，temp，
     * 修改temp.state 为Config.STATE_UPDATE ,更新对象返回影响行数，
     * 判断影响行数是否等于 1，
     * 判断为true，设置 ContractPO 对象 操作员的操作编号与操作时间，
     * 设置数据 state 状态为 Config.STATE_CURRENT,
     * 设置销售员
     * 保存对象返回影响行数。
     * 判断影响行数是否等于 1， true 创建 ContractRouteListPO对象，
     * 设置ContractRouteListPO对象的属性，设置 OrgId 为 temp 的OrgId,
     * 设置ContractRouteListPO对象的ContractId 为 temp  的 ContractNo.
     * 设置ActionType 的值为 ContractrouteListStatus.ACTIONTYPE_DISTRIBUTECONTRACT  领用
     * 创建一个ContractRouteListService 的业务类对象，调用 ContractRouteListService 的InsertOfUpdate 方法执行业务操作返回影响行数。
     * <p/>
     * <p/>
     * 将指定的合同分配指定的销售人员
     *
     * @param contractNO   合同编号
     * @param saleUser     销售员
     * @param operatorUser 操作员对象
     * @param conn         数据库链接
     * @return
     */
    public int distributeContract(String contractNO, UserPO saleUser, UserPO operatorUser, Connection conn) throws Exception {
        if (StringUtils.isEmpty(contractNO)) {
            MyException.deal(MyException.newInstance("分配销售合同编号异常"));
        }

        if (saleUser == null) {
            MyException.deal(MyException.newInstance("销售员参数异常"));
        }
        if (operatorUser == null) {
            MyException.deal(MyException.newInstance("分配销售合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        //根据合同编号组装获取当前合同编号的所有合同。
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT DISTINCT");
        sbSQL.append("	contract.Sid,");
        sbSQL.append("	contract.id,");
        sbSQL.append("	contract.state,");
        sbSQL.append("	contract.operatorId,");
        sbSQL.append(" date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("	contract.OrgId,");
        sbSQL.append("	contract.ProductionId,");
        sbSQL.append("	contract.ProductionName,");
        sbSQL.append("	contract.ApplicationId,");
        sbSQL.append("	contract.`STATUS`,");
        sbSQL.append("	contract.DetailStatus,");
        sbSQL.append("	contract.ContractNo,");
        sbSQL.append("	contract.ContractDetailNo,");
        sbSQL.append("	contract.ContractDisplayNo,");
        sbSQL.append("	contract.CancelId,");
        sbSQL.append("	contract.CancelName,");
        sbSQL.append(" date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("	contract.`COMMENT`,");
        sbSQL.append("	contract.receiveUserId,");
        sbSQL.append("	contract.receiveUserName");
        sbSQL.append("	FROM");
        sbSQL.append("	sale_contract AS contract");
        sbSQL.append("	WHERE");
        sbSQL.append("	1=1");
        sbSQL.append("	and contract.state=0");
        sbSQL.append("	and contract.ContractNo ='" + Database.encodeSQL(contractNO) + "'");
        //获取销售合同集合
        List<ContractPO> contractPOs = MySQLDao.query(sbSQL.toString(), ContractPO.class, null, conn);
        if (contractPOs == null || contractPOs.size() == 0) {
            MyException.deal(MyException.newInstance("分配销售合同失败"));
        }


        int count = 0;
        //遍历销售合同集合
        for (ContractPO contractPO : contractPOs) {

            contractPO.setReceiveUserId(saleUser.getId());

            count = MySQLDao.insertOrUpdate(contractPO, operatorUser.getId(), conn);
            if (count != 1) {
                MyException.deal(MyException.newInstance("销售合同分配失败"));
            }
        }


        return count;
    }


    public int cancelContractByContractNo(String contractNo, UserPositionInfoPO userPositionInfo, Connection conn) throws Exception {

        ContractPO contractPO = new ContractPO();
        contractPO.setState(Config.STATE_CURRENT);
        contractPO.setContractNo(contractNo);

        List<ContractPO> contractPOs = MySQLDao.search(contractPO, ContractPO.class, null, null, conn);

        for (ContractPO c : contractPOs) {
            cancelContractByContractId(c.getId(), userPositionInfo, conn);
        }

        return 1;
    }
    /**
     * 作废单本合同
     *
     *
     * @author leevits
     * @param contractId
     * @param userPositionInfo
     * @param conn
     * @return
     * @throws Exception
     */
    public int cancelContractByContractId(String contractId, UserPositionInfoPO userPositionInfo, Connection conn) throws Exception {


        /**
         * 检查传入参数
         */
        if (userPositionInfo == null || StringUtils.isEmpty(userPositionInfo.getUserId()) || StringUtils.isEmpty(userPositionInfo.getDepartmentId()) || StringUtils.isEmpty(userPositionInfo.getDepartmentName()) || StringUtils.isEmpty(userPositionInfo.getUserName())) {
            MyException.newInstance("作废用户基本信息有误，请检查").throwException();
        }


        String contractNo = "";
        String contractDisplayNo = "";

        /**
         * 关联的订单检查
         *
         * 如果有关联订单，需要首先删除订单信息
         */
        DatabaseSQL dbSQLCheckOrder = new DatabaseSQL();
        dbSQLCheckOrder.newSQL("select * from crm_order o where 1=1 and o.state=0 and o.`Status`=1 and o.contractNo=?");
        dbSQLCheckOrder.addParameter(1, contractId);
        List<OrderPO> orders = MySQLDao.search(dbSQLCheckOrder.getSQL(), dbSQLCheckOrder.getParameters(), OrderPO.class, null, conn);
        if (orders != null && orders.size() != 0) {
            MyException.newInstance("作废合同失败，请首先作废其关联的订单信息").throwException();
        }


        /**
         * 作废合同
         *
         * 设置单本合同为作废状态
         * 设置整套合同为异常状态
         */
        ContractPO contract = new ContractPO();
        contract.setId(contractId);
        contract.setState(Config.STATE_CURRENT);
        contract = MySQLDao.load(contract, ContractPO.class, conn);
        if (contract == null) {
            MyException.newInstance("没有找到需要作废的合同，编号为【" + contractId + "】").throwException();
        }
        // 获取合同编号
        contractNo = contract.getContractNo();
        contractDisplayNo = contract.getContractDisplayNo();

        // 设置单本合同为作废状态
        contract.setDetailStatus(ContractDetailStatus.Cancel);
        int countOfCancelContractDetail = MySQLDao.insertOrUpdate(contract, userPositionInfo.getUserId(), conn);
        if (countOfCancelContractDetail != 1) {
            MyException.newInstance("作废的单本合同，编号为【" + contractId + "】").throwException();
        }


        // 设置整套合同为异常状态
        DatabaseSQL dbSQLCancelWholeContract = new DatabaseSQL();
        dbSQLCancelWholeContract.newSQL("select * from sale_contract c where c.state=0 and c.ContractNo=?");
        dbSQLCancelWholeContract.addParameter(1, contractNo);
        List<ContractPO> contracts = MySQLDao.search(dbSQLCancelWholeContract.getSQL(), dbSQLCancelWholeContract.getParameters(), ContractPO.class, null, conn);
        if (contracts == null) {
            MyException.newInstance("没有找到需要作废的合同，编号为【" + contractId + "】").throwException();
        }
        for (ContractPO c :contracts) {
            c.setStatus(ContractStatus.Exception);
            int countOfCancelWholeContract = MySQLDao.insertOrUpdate(c, userPositionInfo.getUserId(), conn);
            if (countOfCancelWholeContract != 1) {
                MyException.newInstance("作废的单本合同，编号为【" + c.getContractDisplayNo() + "】").throwException();
            }
        }


        /**
         * 记录合同流转记录
         *
         * 设置整套合同的流转记录
         */
        ContractRouteListPO contractRouteListPO = new ContractRouteListPO();

        String comment = "【"+userPositionInfo.getUserName()+"】执行合同作废，编号为【"+contractDisplayNo+"】";

        contractRouteListPO.setContractNo(contractNo);
        contractRouteListPO.setActionTime(TimeUtils.getNow());
        contractRouteListPO.setActionDescription(comment);
        contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_EXCEPTION);

        contractRouteListPO.setActionUserId(userPositionInfo.getUserId());

        int countOfContractRouteList = MySQLDao.insertOrUpdate(contractRouteListPO, userPositionInfo.getUserId(), conn);
        if (countOfContractRouteList != 1) {
            MyException.newInstance("保存合同流转记录信息失败").throwException();
        }

        return 1;
    }


    /**
     * 1、验证 ContractNO ，contrctIds
     * 2、验证 saleUser
     * 2、验证userPO 对象是为null， 并判断userPO对象的id 是否为有效字符串。
     * 3、验证connection
     * 4、根据 contractId 合同编号 获取当前合同编号的所有合同。 验证集合
     * 5、遍历集合 创建 ContractPO 对象，temp，
     * 修改temp.state 为Config.STATE_UPDATE ,更新对象返回影响行数，
     * 判断影响行数是否等于 1，
     * 判断为true，设置 ContractPO 对象 操作员的操作编号与操作时间，
     * 设置数据 state 状态为 Config.STATE_CURRENT, 修改合同状态，
     * 保存对象返回影响行数。
     * 判断影响行数是否等于 1， true 创建 ContractRouteListPO对象，
     * 设置ContractRouteListPO对象的属性，设置 OrgId 为 temp 的OrgId,
     * 设置ContractRouteListPO对象的ContractId 为 temp  的 ContractNo.
     * 设置ActionType 的值为 ContractrouteListStatus.ACTIONTYPE_EXCEPTION  异常
     * 创建一个ContractRouteListService 的业务类对象，调用 ContractRouteListService 的InsertOfUpdate 方法执行业务操作返回影响行数。
     * <p/>
     * 设置遗失合同
     *
     * @param ContractNo   合同编号
     * @param contractIds   需要修改合同明细的id集合
     * @param operatorUser 操作员
     * @param conn         数据库链接
     * @return
     */
    public int cancelContract(String ContractNo, String[] contractIds, String comment, UserPO operatorUser, UserPositionInfoPO userPositionPO, Connection conn) throws Exception {

        if (StringUtils.isEmpty(ContractNo)) {
            MyException.deal(MyException.newInstance("遗失销售合同参数异常"));
        }
        if (contractIds == null) {
            MyException.deal(MyException.newInstance("遗失销售合同集合参数异常"));
        }

        if (operatorUser == null || StringUtils.isEmpty(operatorUser.getId())) {
            MyException.deal(MyException.newInstance("遗失销售合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        //根据合同编号组装获取当前合同编号的所有合同。
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT DISTINCT");
        sbSQL.append("	contract.Sid,");
        sbSQL.append("	contract.id,");
        sbSQL.append("	contract.state,");
        sbSQL.append("	contract.operatorId,");
        sbSQL.append(" date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("	contract.OrgId,");
        sbSQL.append("	contract.ProductionId,");
        sbSQL.append("	contract.ProductionName,");
        sbSQL.append("	contract.ApplicationId,");
        sbSQL.append("	contract.`STATUS`,");
        sbSQL.append("	contract.DetailStatus,");
        sbSQL.append("	contract.ContractNo,");
        sbSQL.append("	contract.ContractDetailNo,");
        sbSQL.append("	contract.ContractDisplayNo,");
        sbSQL.append("	contract.CancelId,");
        sbSQL.append("	contract.CancelName,");
        sbSQL.append(" date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("	contract.`COMMENT`,");
        sbSQL.append("	contract.receiveUserId,");
        sbSQL.append("	contract.receiveUserName");
        sbSQL.append("	FROM");
        sbSQL.append("	sale_contract AS contract");
        sbSQL.append("	WHERE");
        sbSQL.append("	1=1");
        sbSQL.append("	and contract.state=0");
        sbSQL.append("	and contract.ContractNo ='" + Database.encodeSQL(ContractNo) + "'");
        //获取销售合同集合
        List<ContractPO> contractPOs = MySQLDao.query(sbSQL.toString(), ContractPO.class, null, conn);
        if (contractPOs == null || contractPOs.size() == 0) {
            MyException.deal(MyException.newInstance("遗失销售合同失败"));
        }

        int count = 0;
        //遍历销售合同集合
        for (ContractPO contractPO : contractPOs) {
            //遗失销售合同
            count = contractCancel(contractPO, contractIds, comment, operatorUser, userPositionPO, conn);
            if (count != 1) {
                MyException.newInstance("销售合同作废失败").throwException();
            }
        }

        return 1;
    }

    /**
     * 1、验证 ContractNO ，contrctIds
     * 2、验证 saleUser
     * 2、验证userPO 对象是为null， 并判断userPO对象的id 是否为有效字符串。
     * 3、验证connection
     * 4、根据 contractId 合同编号 获取当前合同编号的所有合同。 验证集合
     * 5、遍历集合 创建 ContractPO 对象，temp，
     * 修改temp.state 为Config.STATE_UPDATE ,更新对象返回影响行数，
     * 判断影响行数是否等于 1，
     * 判断为true，设置 ContractPO 对象 操作员的操作编号与操作时间，
     * 设置数据 state 状态为 Config.STATE_CURRENT, 修改合同状态，
     * 保存对象返回影响行数。
     * 判断影响行数是否等于 1， true 创建 ContractRouteListPO对象，
     * 设置ContractRouteListPO对象的属性，设置 OrgId 为 temp 的OrgId,
     * 设置ContractRouteListPO对象的ContractId 为 temp  的 ContractNo.
     * 设置ActionType 的值为 ContractrouteListStatus.ACTIONTYPE_AVERAGE  正常
     * 创建一个ContractRouteListService 的业务类对象，调用 ContractRouteListService 的InsertOfUpdate 方法执行业务操作返回影响行数。
     * <p/>
     * 设置正常合同
     *
     * @param ContractNo   合同编号
     * @param contrctIds   需要修改合同明细的id集合
     * @param operatorUser 操作员
     * @param conn         数据库链接
     * @return
     */
    public int averageContract(String ContractNo, String[] contrctIds, String comment, UserPO operatorUser, UserPositionInfoPO userPositionInfoPO, Connection conn) throws Exception {

        if (StringUtils.isEmpty(ContractNo)) {
            MyException.deal(MyException.newInstance("设置正常销售合同参数异常"));
        }
        if (contrctIds == null) {
            MyException.deal(MyException.newInstance("设置正常销售合同集合参数异常"));
        }

        if (operatorUser == null || StringUtils.isEmpty(operatorUser.getId())) {
            MyException.deal(MyException.newInstance("设置正常销售合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        //根据合同编号组装获取当前合同编号的所有合同。
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT DISTINCT");
        sbSQL.append("	contract.Sid,");
        sbSQL.append("	contract.id,");
        sbSQL.append("	contract.state,");
        sbSQL.append("	contract.operatorId,");
        sbSQL.append(" date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("	contract.OrgId,");
        sbSQL.append("	contract.ProductionId,");
        sbSQL.append("	contract.ProductionName,");
        sbSQL.append("	contract.ApplicationId,");
        sbSQL.append("	contract.`STATUS`,");
        sbSQL.append("	contract.DetailStatus,");
        sbSQL.append("	contract.ContractNo,");
        sbSQL.append("	contract.ContractDetailNo,");
        sbSQL.append("	contract.ContractDisplayNo,");
        sbSQL.append("	contract.CancelId,");
        sbSQL.append("	contract.CancelName,");
        sbSQL.append(" date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("	contract.`COMMENT`,");
        sbSQL.append("	contract.receiveUserId,");
        sbSQL.append("	contract.receiveUserName");
        sbSQL.append("	FROM");
        sbSQL.append("	sale_contract AS contract");
        sbSQL.append("	WHERE");
        sbSQL.append("	1=1");
        sbSQL.append("	and contract.state=0");
        sbSQL.append("	and contract.ContractNo ='" + Database.encodeSQL(ContractNo) + "'");
        //获取销售合同集合
        List<ContractPO> contractPOs = MySQLDao.query(sbSQL.toString(), ContractPO.class, null, conn);
        if (contractPOs == null || contractPOs.size() == 0) {
            MyException.deal(MyException.newInstance("设置正常销售合同失败"));
        }
        sbSQL.setLength(0);
        //根据合同编号获取与订单相关的合同信息
        sbSQL.append("	SELECT");
        sbSQL.append("		contract.sid");
        sbSQL.append("	FROM");
        sbSQL.append("		sale_contract AS contract");
        sbSQL.append("	    ,crm_order AS crmorder ");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        sbSQL.append("	AND contract.state = 0");
        sbSQL.append("	AND crmorder.state  = 0");
        sbSQL.append("	AND crmorder.contractNo = ContractNo");
        sbSQL.append("	AND contract.ContractNo ='" + Database.encodeSQL(ContractNo) + "'");
        //获取销售合同集合
        List<ContractPO> conOrder = MySQLDao.query(sbSQL.toString(), ContractPO.class, null, conn);

        //与订单没有相关联
        //默认未签约
        int status = ContractStatus.Unsigned;
        if (conOrder.size() > 0) {
            //签约
            status = ContractStatus.Signed;
        }

        /**
         * 遍历集合，遍历需要更改明细的数组
         * 判断当前集合元素是否包含在数组中，包含跳出循环。
         * 不包含，判断当前元素的明细状态，如果当前合同的明细状态 遗失，数组整套集合的状态 跳出循环
         *
         */
        //遍历销售合同集合
        for (ContractPO contractPO : contractPOs) {
            boolean flag = false;
            //设置合同明细编号数组
            for (String tr : contrctIds) {
                //明细数组中是否有相同的编号
                if (tr.equals(contractPO.getId()) || tr == contractPO.getId()) {
                    //跳出循环
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                //合同的明细状态为遗失状态
                if (contractPO.getDetailStatus() == ContractDetailStatus.Cancel) {
                    status = ContractStatus.Exception;
                    break;
                }
            }
        }

        //遍历销售合同集合
        for (ContractPO contractPO : contractPOs) {
            //设置正常销售合同
            int count = contractAverage(contractPO, contrctIds, comment, status, operatorUser, conn);
            if (count != 1) {
                MyException.newInstance("设置正常合同失败").throwException();
            }

            int actionType = ContractRouteListStatus.ACTIONTYPE_AVERAGE;
            String description = "【"+operatorUser.getName()+"】设置正常销售合同，合同编号【"+contractPO.getContractDisplayNo()+"】";

            // 合同遗失状态
            if (status == ContractStatus.Exception) {
                actionType = ContractRouteListStatus.ACTIONTYPE_EXCEPTION;
                description = "【"+operatorUser.getName()+"】设置遗失销售合同，合同编号【"+contractPO.getContractDisplayNo()+"】";
            }

            count = saveContractRouteList(contractPO.getContractNo(), operatorUser, userPositionInfoPO, description, actionType, conn);


            if (count != 1) {
                MyException.newInstance("设置正常合同流转失败").throwException();
            }
        }

        return 1;
    }


    /**
     * 验证 contractPO
     * 验证 optionUser
     * 验证 conn
     * 更新当前合同，设置操作员，
     * 判断合同编号集合中是否包含当前销售合同的编号，将该销售合同的明细改为遗失
     * 设置合同状态、合同明细状态 返回影响行数
     *
     * @param contractPO 销售合同
     * @param contractIds 合同编号集合
     * @param optionUser 操作员
     * @param conn       数据库连接
     * @return
     * @throws Exception
     */
    public int contractCancel(ContractPO contractPO, String[] contractIds, String comment, UserPO optionUser, UserPositionInfoPO userPositionPO, Connection conn) throws Exception {
        if (contractPO == null) {
            MyException.deal(MyException.newInstance("遗失销售合同参数异常"));
        }
        if (contractIds == null) {
            MyException.deal(MyException.newInstance("遗失销售合同集合参数异常"));
        }
        if (optionUser == null || StringUtils.isEmpty(optionUser.getId())) {
            MyException.deal(MyException.newInstance("遗失销售合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }


        contractPO.setStatus(ContractStatus.Exception);//合同异常
        contractPO.setCancelId(optionUser.getId());
        contractPO.setCancelName(optionUser.getName());
        contractPO.setCancelTime(TimeUtils.getNow());

        int count = 0;
        //设置合同明细
        for (String tr : contractIds) {
            if (tr.equals(contractPO.getId())) {
                contractPO.setDetailStatus(ContractDetailStatus.Cancel); //遗失
                contractPO.setComment(comment);
            }

            String description = "【"+optionUser.getName()+"】设置合同状态异常，合同编号【"+contractPO.getContractDisplayNo()+"】";
            count = saveContractRouteList(contractPO.getContractNo(), optionUser, userPositionPO, description, ContractRouteListStatus.ACTIONTYPE_EXCEPTION, conn);

            if (count != 1) {
                MyException.newInstance("设置合同明细异常失败").throwException();
            }
        }
        return MySQLDao.insertOrUpdate(contractPO, optionUser.getId(), conn);
    }

    /**
     * 验证 contractPO
     * 验证 optionUser
     * 验证 conn
     * 更新当前合同，设置操作员，
     * 判断合同编号集合中是否包含当前销售合同的编号，将该销售合同的明细改为遗失
     * 设置合同状态、合同明细状态 返回影响行数
     *
     * @param contractPO 销售合同
     * @param contrctIds 合同编号集合
     * @param status     合同状态
     * @param optionUser 操作员
     * @param conn       数据库连接
     * @return
     * @throws Exception
     */
    public int contractAverage(ContractPO contractPO, String[] contrctIds, String comment, int status, UserPO optionUser, Connection conn) throws Exception {
        if (contractPO == null) {
            MyException.deal(MyException.newInstance("设置正常销售合同参数异常"));
        }
        if (contrctIds == null) {
            MyException.deal(MyException.newInstance("设置正常销售合同集合参数异常"));
        }
        if (optionUser == null || StringUtils.isEmpty(optionUser.getId())) {
            MyException.deal(MyException.newInstance("设置正常销售合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        contractPO.setState(Config.STATE_UPDATE);
        int count = MySQLDao.update(contractPO, conn);
        if (count != 1) {
            MyException.deal(MyException.newInstance("设置正常销售合同失败"));
        }

        contractPO.setSid(MySQLDao.getMaxSid("Sale_Contract", conn));
        contractPO.setState(Config.STATE_CURRENT);
        contractPO.setOperatorId(optionUser.getId());
        contractPO.setOperateTime(TimeUtils.getNow());
        contractPO.setStatus(status);//设置状态
        //设置合同明细
        for (String tr : contrctIds) {
            if (tr.equals(contractPO.getId()) || tr == contractPO.getId()) {
                contractPO.setDetailStatus(ContractDetailStatus.Active);//正常
                contractPO.setComment(comment);
                contractPO.setCancelId("");
                contractPO.setCancelName("");
                contractPO.setCancelTime("");
            }else{
                contractPO.setCancelId(optionUser.getId());
                contractPO.setCancelName(optionUser.getName());
                contractPO.setCancelTime(TimeUtils.getNow());
            }
        }
        return MySQLDao.insert(contractPO, conn);
    }



    public int changeProduction(String contractNo, String productionId, Connection conn) throws Exception {

        List<ContractPO> list = contractDao.getContractByContractNO(contractNo, conn);

        for (ContractPO contract : list) {
            contract.setProductionId(productionId);
            MySQLDao.insertOrUpdate(contract, conn);
        }

        return 1;
    }




    /**
     * * 1、验证 contracNO
     * 2、验证 saleUser
     * 2、验证userPO 对象是为null， 并判断userPO对象的id 是否为有效字符串
     * 3、验证connection
     * 4、根据 contractId 合同编号 获取当前合同编号的所有合同。
     * 5、遍历集合 创建 ContractPO 对象，temp，
     * 修改temp.state 为Config.STATE_UPDATE ,更新对象返回影响行数，
     * 判断影响行数是否等于 1，
     * 判断为true，设置 ContractPO 对象 操作员的操作编号与操作时间，
     * 设置数据 state 状态为 Config.STATE_CURRENT,
     * 合同的状态改为 ContractStatus. CHECKSTATE_OK   已签约
     * 保存对象返回影响行数。
     * 判断影响行数是否等于 1， true 创建 ContractRouteListPO对象，
     * 设置ContractRouteListPO对象的属性，设置 OrgId 为 temp 的OrgId,
     * 设置ContractRouteListPO对象的ContractId 为 temp  的 ContractNo.
     * 设置ActionType 的值为 ContractrouteListStatus.ACTIONTYPE_SING_OK  已签约
     * 创建一个ContractRouteListService 的业务类对象，调用 ContractRouteListService 的InsertOfUpdate 方法执行业务操作返回影响行数。
     * <p/>
     * 签约合同
     *
     * @param contracNo    合同编号
     * @param operatorUser 操作员
     * @param conn         数据库链接
     * @return
     */
    public int signContract(String contracNo, String orderId, UserPO operatorUser, Connection conn) throws Exception {
        if (StringUtils.isEmpty(contracNo)) {
            MyException.deal(MyException.newInstance("签约销售合同参数异常"));
        }

        if (operatorUser == null || StringUtils.isEmpty(operatorUser.getId())) {
            MyException.newInstance("签约销售合同操作员异常");
        }


        /**
         * 初始化订单数据
         */
        OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);
        if (orderPO == null) {
            MyException.newInstance("无法获得所需的签约订单").throwException();
        }


        //获取销售合同集合
        List<ContractPO> contractPOs = contractDao.getContractByContractNO(contracNo, conn);

        if (contractPOs == null || contractPOs.size() == 0) {
            MyException.deal(MyException.newInstance("签约销售合同失败"));
        }

        int count = 0;

        //遍历销售合同集合
        for (ContractPO contractPO : contractPOs) {

            //合同状态不等于未签约
            if (contractPO.getStatus() != ContractStatus.Unsigned) {
                MyException.newInstance("签约销售合同状态异常", "合同编号["+contractPO.getId()+"]").throwException();
            }


            //签约销售合同
            contractPO.setStatus(ContractStatus.Signed);//合同签约
            contractPO.setDetailStatus(ContractDetailStatus.Active);//正常
            count = MySQLDao.insertOrUpdate(contractPO, operatorUser.getId(), conn);
            if (count != 1) {
                MyException.newInstance("签约销售合同失败").throwException();
            }

        }


        ContractPO contractPO = contractPOs.get(0);
        //合同流转对象
        ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
        //签约销售合同
        contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_SING_OK);

        contractRouteListPO.setContractNo(contractPO.getContractNo());
        //获取最后一条流转记录
        ContractRouteListPO last = contractDao.getLastContractRouteListPO(contractPO.getContractNo(), conn);
        //设置部门
        contractRouteListPO.setActionDescription("【"+operatorUser.getName()+"】合同签约");
        contractRouteListPO.setActionTime(TimeUtils.getNow());
        contractRouteListPO.setActionUserId(operatorUser.getId());
        MySQLDao.insertOrUpdate(contractRouteListPO, operatorUser.getId(), conn);


        /**
         * 关联订单
         */

        if (!orderPO.getProductionId().equals(contractPO.getProductionId())) {
            MyException.newInstance("合同与订单所对应的产品不匹配").throwException();
        }
        orderPO.setContractNo(contractPO.getContractNo());
        MySQLDao.insertOrUpdate(orderPO, operatorUser.getId(), conn);

        return 1;
    }


    public int cancelSignContract(String orderId, UserPO operatorUser, Connection conn) throws Exception {

        if (operatorUser == null || StringUtils.isEmpty(operatorUser.getId())) {
            MyException.newInstance("签约销售合同操作员异常");
        }


        /**
         * 初始化订单数据
         */
        OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);
        if (orderPO == null) {
            MyException.newInstance("无法获得所需的签约订单").throwException();
        }


        String contracNo = orderPO.getContractNo();

        //获取销售合同集合
        List<ContractPO> contractPOs = contractDao.getContractByContractNO(contracNo, conn);

        if (contractPOs == null || contractPOs.size() == 0) {
            MyException.deal(MyException.newInstance("签约销售合同失败"));
        }

        int count = 0;

        //遍历销售合同集合
        for (ContractPO contractPO : contractPOs) {


            if (contractPO.getStatus() != ContractStatus.Signed) {
                MyException.newInstance("签约销售合同状态并非已签约", "合同编号["+contractPO.getId()+"]").throwException();
            }


            //签约销售合同
            contractPO.setStatus(ContractStatus.Unsigned);//合同签约
            contractPO.setDetailStatus(ContractDetailStatus.Active);//正常
            count = MySQLDao.insertOrUpdate(contractPO, operatorUser.getId(), conn);
            if (count != 1) {
                MyException.newInstance("取消签约销售合同失败").throwException();
            }

        }


        ContractPO contractPO = contractPOs.get(0);
        //合同流转对象
        ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
        //签约销售合同
        contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_SING_OK);

        contractRouteListPO.setContractNo(contractPO.getContractNo());
        //获取最后一条流转记录
        ContractRouteListPO last = contractDao.getLastContractRouteListPO(contractPO.getContractNo(), conn);
        //设置部门
        contractRouteListPO.setActionDescription("【"+operatorUser.getName()+"】合同取消签约");
        contractRouteListPO.setActionTime(TimeUtils.getNow());
        contractRouteListPO.setActionUserId(operatorUser.getId());
        MySQLDao.insertOrUpdate(contractRouteListPO, operatorUser.getId(), conn);


        /**
         * 取消关联订单
         */

        if (!orderPO.getProductionId().equals(contractPO.getProductionId())) {
            MyException.newInstance("合同与订单所对应的产品不匹配").throwException();
        }
        orderPO.setContractNo("");
        MySQLDao.insertOrUpdate(orderPO, operatorUser.getId(), conn);

        return 1;
    }





    /**
     * 1、验证数据有效性，ContractVO
     * 2、验证List<KVObject>
     * 2、验证HttpServletRequest
     * 3、验证connection
     * 4、组装SQL 查询 Contract数据库表，
     * 合同申请部门信息， 产品信息，合同状态已签约 ，合同详细信息。
     * 根据当前合同的在流转状态表中的状态为 签约  ContractrouteListStatus.ACTIONTYPE_SING_OK
     * <p/>
     * 5、分页查询 返回分页数据
     * <p/>
     * 显示已经签约的合同列表
     *
     * @param contractVO 销售合同
     * @param conditions 时间段查询条件
     * @param request    http请求
     * @param conn       数据库链接
     * @return
     */
    public Pager listSignedContracts(ContractVO contractVO, List<KVObject> conditions, String departmentId, HttpServletRequest request, Connection conn) throws Exception {
        if (contractVO == null) {
            MyException.deal(MyException.newInstance("签约销售合同参数异常"));
        }
        if (departmentId == null) {
            MyException.deal(MyException.newInstance("签约销售合同财富中心参数异常"));
        }

        if (conditions == null) {
            MyException.deal(MyException.newInstance("时间段查询条件参数失败"));
        }
        if (request == null) {
            MyException.deal(MyException.newInstance("请求参数异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        //组装SQL
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append("	SELECT DISTINCT");
        sbSQL.append("      d.`name` DepartmentName,");
        sbSQL.append("		temp.*, corder.CustomerName,");
        sbSQL.append("      date_format(corder.operateTime,'%Y-%m-%d %H:%i:%S') as sigendTime,");
        sbSQL.append("		corder.Money AS money");
        sbSQL.append("	FROM");
        sbSQL.append("		(");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				contract.sid,");
        sbSQL.append("				contract.id,");
        sbSQL.append("				contract.state,");
        sbSQL.append("				contract.operatorId,");
        sbSQL.append("              date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("				contract.OrgId,");
        sbSQL.append("				contract.ProductionId,");
        sbSQL.append("				contract.ProductionName,");
        sbSQL.append("				contract.ApplicationId,");
        sbSQL.append("				contract.`STATUS`,");
        sbSQL.append("				contract.DetailStatus,");
        sbSQL.append("				contract.ContractNo,");
        sbSQL.append("				contract.ContractDetailNo,");
        sbSQL.append("				contract.ContractDisplayNo,");
        sbSQL.append("				contract.CancelId,");
        sbSQL.append("				contract.CancelName,");
        sbSQL.append("              date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("				contract.`COMMENT`,");
        sbSQL.append("				contract.receiveUserId,");
        sbSQL.append("				contract.receiveUserName,");
        sbSQL.append("				contractRoute.DepartmentId,");
        sbSQL.append("				contractRoute.actionType");
        sbSQL.append("			FROM");
        sbSQL.append("				sale_contract AS contract,");
        sbSQL.append("				sale_contractroutelist AS contractRoute");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("			AND contract.state = 0");
        sbSQL.append("			AND contractRoute.state = 0");
        sbSQL.append("			AND contract.contractNo = contractRoute.contractNo");
        sbSQL.append("			AND contractRoute.sid = (");
        sbSQL.append("				SELECT DISTINCT");
        sbSQL.append("					MAX(contractroute2.sid)");
        sbSQL.append("				FROM");
        sbSQL.append("					sale_contractroutelist contractroute2");
        sbSQL.append("				WHERE");
        sbSQL.append("					1 = 1");
        sbSQL.append("				AND contractroute2.state = 0");
        sbSQL.append("				AND contractroute2.contractNo = contract.contractNo");
        sbSQL.append("			)");
        sbSQL.append("			AND (");
        sbSQL.append("				 contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_SING_OK_MANAGER);
        sbSQL.append("			)");
        sbSQL.append("			AND contract.`STATUS` = " + ContractStatus.Signed);
        sbSQL.append("		) AS temp");
        sbSQL.append("	LEFT JOIN crm_order AS corder ON corder.state = 0");
        sbSQL.append("	AND corder.contractNo = temp.ContractNo");
        sbSQL.append(" LEFT JOIN system_department d on temp.OrgId=d.id");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        sbSQL.append("	and temp.OrgId ='" + Database.encodeSQL(departmentId) + "'");
        sbSQL.append("	GROUP BY");
        sbSQL.append("		temp.ContractNo");
        sbSQL.append("	ORDER BY");
        sbSQL.append("		temp.ContractNo ASC");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        return Pager.query(sbSQL.toString(), contractVO, conditions, request, queryType, conn);
    }

    /**
     * 1、验证数据有效性，ContractVO
     * 2、验证List<KVObject>
     * 2、验证HttpServletRequest
     * 3、验证connection
     * 4、组装SQL 查询 Contract数据库表，
     * 合同申请部门信息， 产品信息，合同状态异常状态 ，合同详细信息。
     * 根据当前合同的在流转状态表中的状态为 异常  ContractrouteListStatus.ACTIONTYPE_EXCEPTION
     * <p/>
     * 5、分页查询 返回分页数据
     * <p/>
     * 显示异常的合同列表
     *
     * @param contractVO 销售合同
     * @param conditions 时间段查询条件
     * @param request    http请求
     * @param conn       数据库链接
     * @return
     */
    public Pager listUnsualContracts(ContractVO contractVO, List<KVObject> conditions, String departmentId, HttpServletRequest request, Connection conn) throws Exception {
        if (contractVO == null) {
            MyException.deal(MyException.newInstance("销售合同参数异常"));
        }
        if (departmentId == null) {
            MyException.deal(MyException.newInstance("签约销售合同财富中心参数异常"));
        }
        if (conditions == null) {
            MyException.deal(MyException.newInstance("时间段查询条件参数失败"));
        }
        if (request == null) {
            MyException.deal(MyException.newInstance("请求参数异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        //组装SQL
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append("	SELECT DISTINCT");
        sbSQL.append("      d.`name` DepartmentName,");
        sbSQL.append("		temp.*, corder.CustomerName,");
        sbSQL.append("      date_format(corder.operateTime,'%Y-%m-%d %H:%i:%S') as sigendTime,");
        sbSQL.append("		corder.Money AS money");
        sbSQL.append("	FROM");
        sbSQL.append("		(");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				contract.sid,");
        sbSQL.append("				contract.id,");
        sbSQL.append("				contract.state,");
        sbSQL.append("				contract.operatorId,");
        sbSQL.append("              date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("				contract.OrgId,");
        sbSQL.append("				contract.ProductionId,");
        sbSQL.append("				contract.ProductionName,");
        sbSQL.append("				contract.ApplicationId,");
        sbSQL.append("				contract.`STATUS`,");
        sbSQL.append("				contract.DetailStatus,");
        sbSQL.append("				contract.ContractNo,");
        sbSQL.append("				contract.ContractDetailNo,");
        sbSQL.append("				contract.ContractDisplayNo,");
        sbSQL.append("				contract.CancelId,");
        sbSQL.append("				contract.CancelName,");
        sbSQL.append("              date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("				contract.`COMMENT`,");
        sbSQL.append("				contract.receiveUserId,");
        sbSQL.append("				contract.receiveUserName,");
        sbSQL.append("				contractRoute.departmentId,");
        sbSQL.append("				contractRoute.actionType");
        sbSQL.append("			FROM");
        sbSQL.append("				sale_contract AS contract,");
        sbSQL.append("				sale_contractroutelist AS contractRoute");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("			AND contract.state = 0");
        sbSQL.append("			AND contractRoute.state = 0");
        sbSQL.append("			AND contract.contractNo = contractRoute.contractNo");
        sbSQL.append("			AND contractRoute.sid = (");
        sbSQL.append("				SELECT DISTINCT");
        sbSQL.append("					MAX(contractroute2.sid)");
        sbSQL.append("				FROM");
        sbSQL.append("					sale_contractroutelist contractroute2");
        sbSQL.append("				WHERE");
        sbSQL.append("					1 = 1");
        sbSQL.append("				AND contractroute2.state = 0");
        sbSQL.append("				AND contractroute2.contractNo = contract.contractNo");
        sbSQL.append("			)");
        sbSQL.append("			AND (");
        sbSQL.append("				contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_EXCEPTIONMANAGER);
        sbSQL.append("			)");
        sbSQL.append("			AND contract.`STATUS` = " + ContractStatus.Exception);
        sbSQL.append("		) AS temp");
        sbSQL.append("	LEFT JOIN crm_order AS corder ON corder.state = 0");
        sbSQL.append("	AND corder.contractNo = temp.ContractNo");
        sbSQL.append(" LEFT JOIN system_department d on temp.OrgId=d.id");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        sbSQL.append("	and temp.OrgId ='" + Database.encodeSQL(departmentId) + "'");
        sbSQL.append("	GROUP BY");
        sbSQL.append("		temp.ContractNo");
        sbSQL.append("	ORDER BY");
        sbSQL.append("		temp.ContractNo ASC");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        return Pager.query(sbSQL.toString(), contractVO, conditions, request, queryType, conn);
    }


    /**
     * 1、验证数据有效性，ContractVO
     * 2、验证List<KVObject>
     * 2、验证HttpServletRequest
     * 3、验证connection
     * 4、组装SQL 查询 Contract数据库表，
     * 合同申请部门信息， 产品信息 ，合同详细信息。
     * 前合同在合同流程表中最后一条数据的状态： 归档、
     * <p/>
     * 5、分页查询 返回分页数据
     * <p/>
     * 显示 归档的合同列表
     *
     * @param contractVO 销售合同
     * @param conditions 时间段查询条件
     * @param request    http请求
     * @param conn       数据库链接
     * @return
     */
    public Pager listArchiveContracts(ContractVO contractVO, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        if (contractVO == null) {
            MyException.deal(MyException.newInstance("归档销售合同参数异常"));
        }

        if (conditions == null) {
            MyException.deal(MyException.newInstance("时间段查询参数异常"));
        }
        if (request == null) {
            MyException.deal(MyException.newInstance("请求参数异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        //组装SQL
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT");
        sbSQL.append("		temp.*, crmOrder.contractNo,");
        sbSQL.append("		crmOrder.CustomerName,");
        sbSQL.append("      date_format(crmOrder.operateTime,'%Y-%m-%d %H:%i:%S') as sigendTime,");
        sbSQL.append("		crmOrder.Money AS money");
        sbSQL.append("	FROM");
        sbSQL.append("		(");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				contractRoute.sid as csid,");
        sbSQL.append("				contract.sid,");
        sbSQL.append("				contract.id,");
        sbSQL.append("				contract.state,");
        sbSQL.append("				contract.operatorId,");
        sbSQL.append("              date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("				contract.OrgId,");
        sbSQL.append("				contract.ProductionId,");
        sbSQL.append("				contract.ProductionName,");
        sbSQL.append("				contract.ApplicationId,");
        sbSQL.append("				contract.`STATUS`,");
        sbSQL.append("				contract.DetailStatus,");
        sbSQL.append("				contract.ContractNo,");
        sbSQL.append("				contract.ContractDetailNo,");
        sbSQL.append("				contract.ContractDisplayNo,");
        sbSQL.append("				contract.CancelId,");
        sbSQL.append("				contract.CancelName,");
        sbSQL.append("              date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("				contract.`COMMENT`,");
        sbSQL.append("				contract.receiveUserId,");
        sbSQL.append("				contract.receiveUserName,");
        sbSQL.append("				contractRoute.ActionType,");
        sbSQL.append("				contractRoute.sendExpress,");
        sbSQL.append("				contractRoute.sendExpressId,");
        sbSQL.append("				application.DepartmentName,");
        sbSQL.append("              date_format(application.ApplicationTime,'%Y-%m-%d %H:%i:%S') as ApplicationTime,");
        sbSQL.append("              date_format(application.CheckTime,'%Y-%m-%d %H:%i:%S') as CheckTime,");
        sbSQL.append("				appuser.`name` AS applicationUserName,");
        sbSQL.append("				checkuser.`name` AS checkName");
        sbSQL.append("			FROM");
        sbSQL.append("				sale_contract AS contract,");
        sbSQL.append("				sale_contractroutelist AS contractRoute,");
        sbSQL.append("				sale_contractapplication AS application,");
        sbSQL.append("				system_user AS appuser,");
        sbSQL.append("				system_user AS checkuser");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("			AND contract.state = 0");
        sbSQL.append("			AND appuser.state = 0");
        sbSQL.append("			AND checkuser.state = 0");
        sbSQL.append("			AND contractRoute.State = 0");
        sbSQL.append("			AND application.state = 0");
        sbSQL.append("			AND contract.contractNo = contractRoute.contractNo");
        sbSQL.append("			AND appuser.id = application.applicationUserId");
        sbSQL.append("			AND checkuser.id = application.CheckId");
        sbSQL.append("			and application.id=contract.ApplicationId");
        sbSQL.append("			AND contractRoute.sid = (");
        sbSQL.append("				SELECT DISTINCT");
        sbSQL.append("					MAX(contractroute2.sid)");
        sbSQL.append("				FROM");
        sbSQL.append("					sale_contractroutelist contractroute2");
        sbSQL.append("				WHERE");
        sbSQL.append("					1 = 1");
        sbSQL.append("				AND contractroute2.state = 0");
        sbSQL.append("				AND contractroute2.contractNo = contract.contractNo");
        sbSQL.append("			)");
        sbSQL.append("			AND (");
        sbSQL.append("				contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_ARCHIVEMANAGER);
        sbSQL.append("				OR contractRoute.ActionType = " + ContractRouteListStatus.ACTIONYUPE_CONFIRMARCHIVECONTRACT);
        sbSQL.append("				OR contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_ARCHIVECONTRACT);
        sbSQL.append("			)");
        sbSQL.append("		) AS temp");
        sbSQL.append("	LEFT JOIN crm_order AS crmOrder ON crmOrder.state = 0 ");
        sbSQL.append("	AND crmOrder.contractNo = temp.ContractNo");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        sbSQL.append("	GROUP BY");
        sbSQL.append("		temp.ContractNo");
        sbSQL.append("	ORDER BY");
        sbSQL.append("		temp.csid DESC");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        return Pager.query(sbSQL.toString(), contractVO, conditions, request, queryType, conn);
    }

    /**
     * 1、验证数据有效性，ContractVO
     * 2、验证List<KVObject>
     * 2、验证HttpServletRequest
     * 3、验证connection
     * 4、组装SQL 查询 Contract数据库表，
     * 显示产品信息，要与订单管理显示已经签约的合同，显示流转状态，显示当前操作员领用的合同，
     * 前合同在合同流程表中最后一条数据的状态 以签约，领用。
     * <p/>
     * 5、分页查询 返回分页数据
     * <p/>
     * 显示我的归档合同列表
     *
     * @param contractVO 销售合同
     * @param conditions 时间段查询条件
     * @param request    http请求
     * @param conn       数据库链接
     * @return
     */
    public Pager getListHistoryContract(ContractVO contractVO, String SaleUserId, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {

        if (contractVO == null) {
            MyException.deal(MyException.newInstance("我的历史销售合同参数异常"));
        }
        if (StringUtils.isEmpty(SaleUserId)) {
            MyException.deal(MyException.newInstance("我的历史销售合同销售员参数异常"));
        }

        if (conditions == null) {
            MyException.deal(MyException.newInstance("时间段查询参数异常"));
        }
        if (request == null) {
            MyException.deal(MyException.newInstance("请求参数异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        //组装SQL
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT");
        sbSQL.append("		temp.*, corder.CustomerName,");
        sbSQL.append("      date_format(corder.operateTime,'%Y-%m-%d %H:%i:%S') as sigendTime,");
        sbSQL.append("		corder.Money AS money");
        sbSQL.append("	FROM");
        sbSQL.append("		(");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				contract.sid,");
        sbSQL.append("				contract.id,");
        sbSQL.append("				contract.state,");
        sbSQL.append("				contract.operatorId,");
        sbSQL.append("              date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("				contract.OrgId,");
        sbSQL.append("				contract.ProductionId,");
        sbSQL.append("				contract.ProductionName,");
        sbSQL.append("				contract.ApplicationId,");
        sbSQL.append("				contract.`STATUS`,");
        sbSQL.append("				contract.DetailStatus,");
        sbSQL.append("				contract.ContractNo,");
        sbSQL.append("				contract.ContractDetailNo,");
        sbSQL.append("				contract.ContractDisplayNo,");
        sbSQL.append("				contract.CancelId,");
        sbSQL.append("				contract.CancelName,");
        sbSQL.append("              date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("				contract.`COMMENT`,");
        sbSQL.append("				contract.receiveUserId,");
        sbSQL.append("				contract.receiveUserName,");
        sbSQL.append("				contractroute.ActionType");
        sbSQL.append("			FROM");
        sbSQL.append("				sale_contract AS contract,");
        sbSQL.append("				sale_contractroutelist AS contractroute");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("			AND contract.state = 0");
        sbSQL.append("			AND contractRoute.State = 0");
        sbSQL.append("			AND contract.contractNo = contractRoute.contractNo");
        sbSQL.append("			AND contract.receiveUserId = '" + Database.encodeSQL(SaleUserId) + "'");
        sbSQL.append("			AND contractRoute.operatorId = '" + Database.encodeSQL(SaleUserId) + "'");
        sbSQL.append("			AND (");
        //异常管理员
        sbSQL.append("				 contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_EXCEPTIONMANAGER);
        //签约管理员
        sbSQL.append("				OR contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_SING_OK_MANAGER);
        sbSQL.append("			)");
        /*sbSQL.append("			AND contractRoute.sid = (");
        sbSQL.append("				SELECT DISTINCT");
        sbSQL.append("					MAX(contractroute2.sid)");
        sbSQL.append("				FROM");
        sbSQL.append("					sale_contractroutelist contractroute2");
        sbSQL.append("				WHERE");
        sbSQL.append("					1 = 1");
        sbSQL.append("				AND contractroute2.state = 0");
        sbSQL.append("				AND contractroute2.OrgId = contractRoute.OrgId");
        sbSQL.append("				AND contractroute2.OrgId = contract.OrgId");
        sbSQL.append("				AND contractroute2.ContractId = contract.id");
        sbSQL.append("			)");*/
        sbSQL.append("			GROUP BY");
        sbSQL.append("				contract.ContractNo");
        sbSQL.append("		) AS temp");
        sbSQL.append("	LEFT JOIN crm_order AS corder ON corder.state = 0");
        sbSQL.append("	AND corder.contractNo = temp.ContractNo");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        sbSQL.append("		order by operateTime desc");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        return Pager.query(sbSQL.toString(), contractVO, conditions, request, queryType, conn);
    }

    /**
     * 1、验证数据有效性，ContractVO
     * 2、验证List<KVObject>
     * 2、验证HttpServletRequest
     * 3、验证connection
     * 4、组装SQL 查询 Contract数据库表，
     * 显示产品信息，要与订单管理显示已经签约的合同，显示流转状态，显示当前操作员领用的合同，
     * 前合同在合同流程表中最后一条数据的状态 以签约，领用。
     * <p/>
     * 5、分页查询 返回分页数据
     * <p/>
     * 显示我的销售合同列表
     *
     * @param contractVO 销售合同
     * @param conditions 时间段查询条件
     * @param request    http请求
     * @param conn       数据库链接
     * @return
     */
    public Pager listContracts(ContractVO contractVO, String SaleUserId, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {

        if (contractVO == null) {
            MyException.deal(MyException.newInstance("我的销售合同参数异常"));
        }
        if (StringUtils.isEmpty(SaleUserId)) {
            MyException.deal(MyException.newInstance("我的销售合同销售员参数异常"));
        }

        if (conditions == null) {
            MyException.deal(MyException.newInstance("时间段查询参数异常"));
        }
        if (request == null) {
            MyException.deal(MyException.newInstance("请求参数异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        //组装SQL
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT");
        sbSQL.append("		temp.*, corder.CustomerName,");
        sbSQL.append("      date_format(corder.operateTime,'%Y-%m-%d %H:%i:%S') as sigendTime,");
        sbSQL.append("		corder.Money AS money");
        sbSQL.append("	FROM");
        sbSQL.append("		(");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				contract.sid,");
        sbSQL.append("				contract.id,");
        sbSQL.append("				contract.state,");
        sbSQL.append("				contract.operatorId,");
        sbSQL.append("              date_format(contract.operateTime,'%Y-%m-%d %H:%i:%S') as operateTime,");
        sbSQL.append("				contract.OrgId,");
        sbSQL.append("				contract.ProductionId,");
        sbSQL.append("				contract.ProductionName,");
        sbSQL.append("				contract.ApplicationId,");
        sbSQL.append("				contract.`STATUS`,");
        sbSQL.append("				contract.DetailStatus,");
        sbSQL.append("				contract.ContractNo,");
        sbSQL.append("				contract.ContractDetailNo,");
        sbSQL.append("				contract.ContractDisplayNo,");
        sbSQL.append("				contract.CancelId,");
        sbSQL.append("				contract.CancelName,");
        sbSQL.append("              date_format(contract.CancelTime,'%Y-%m-%d %H:%i:%S') as CancelTime,");
        sbSQL.append("				contract.`COMMENT`,");
        sbSQL.append("				contract.receiveUserId,");
        sbSQL.append("				contract.receiveUserName,");
        sbSQL.append("				contractroute.ActionType");
        sbSQL.append("			FROM");
        sbSQL.append("				sale_contract AS contract,");
        sbSQL.append("				sale_contractroutelist AS contractroute");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("			AND contract.state = 0");
        sbSQL.append("			AND contractRoute.State = 0");
        sbSQL.append("			AND contract.contractNo = contractRoute.contractNo");
        sbSQL.append("			AND contract.receiveUserId = '" + Database.encodeSQL(SaleUserId) + "'");
        sbSQL.append("			AND (");
        sbSQL.append("				contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_DISTRIBUTECONTRACT);
        sbSQL.append("				OR contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_SING_OK);
        sbSQL.append("				OR contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_EXCEPTION);
        sbSQL.append("				OR contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_AVERAGE);
        sbSQL.append("				OR contractRoute.ActionType = " + ContractRouteListStatus.ACTIONTYPE_CANCELSING);
        sbSQL.append("			)");
        sbSQL.append("			AND contractRoute.sid = (");
        sbSQL.append("				SELECT DISTINCT");
        sbSQL.append("					MAX(contractroute2.sid)");
        sbSQL.append("				FROM");
        sbSQL.append("					sale_contractroutelist contractroute2");
        sbSQL.append("				WHERE");
        sbSQL.append("					1 = 1");
        sbSQL.append("				AND contractroute2.state = 0");
        sbSQL.append("				AND contractroute2.contractNo = contract.contractNo");
        sbSQL.append("			)");
        sbSQL.append("			GROUP BY");
        sbSQL.append("				contract.ContractNo");
        sbSQL.append("		) AS temp");
        sbSQL.append("	LEFT JOIN crm_order AS corder ON corder.state = 0");
        sbSQL.append("	AND corder.contractNo = temp.ContractNo");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        return Pager.query(sbSQL.toString(), contractVO, conditions, request, queryType, conn);
    }


    /**
     * 1、验证数据有效性， orgId
     * 2、验证List<KVObject>
     * 3、验证connection
     * 4、组装SQL 查询 Contract数据库表，
     * 需要显示行为人的部门信息，产品的信息，合同的信息。
     * 5、查询返回List 数据集合。
     * <p/>
     * <p/>
     * 根据合同号显示该合同号中的所有流转记录。
     *
     * @param contractNO 销售合同编号
     * @param conditions sql查询条件
     * @param conn       数据库连接
     * @return
     */
    public List<ContractRouteListVO> listContractRouteListVOs(String contractNO, List<KVObject> conditions, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listContractRouteListVOs", "ContractServiceSQL", ContractService.class);
        dbSQL.addParameter4All("contractNO", contractNO);
        dbSQL.initSQL();

        return MySQLDao.search(dbSQL.getSQL(),dbSQL.getParameters(), ContractRouteListVO.class, conditions, conn);
    }

    /**
     * 根据合同id 获取最后一条 流转状态
     * 验证 contractId ，conn
     *
     * @param contractNo 合同号
     * @param conn       数据库连接
     * @return
     * @throws Exception
     */
    public ContractRouteListVO getLastContractRouteListVO(String contractNo, Connection conn) throws Exception {
        if (StringUtils.isEmpty(contractNo)) {
            MyException.deal(MyException.newInstance("合同编号获取异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT");
        sbSQL.append("	max(saleList.Sid),");
        sbSQL.append("	saleList.Id,");
        sbSQL.append("	saleList.State,");
        sbSQL.append("	saleList.OperatorId,");
        sbSQL.append("	saleList.OperateTime,");
        sbSQL.append("	saleList.ContractId,");
        sbSQL.append("	saleList.ActionTime,");
        sbSQL.append("	saleList.ActionDescription,");
        sbSQL.append("	saleList.ActionType,");
        sbSQL.append("	saleList.ActionUserId,");
        sbSQL.append("	saleList.sendExpress,");
        sbSQL.append("	saleList.sendExpressId,");
        sbSQL.append("	saleList.departmentName,");
        sbSQL.append("	saleList.departmentId");
        sbSQL.append("	FROM");
        sbSQL.append("	sale_contractroutelist AS saleList ");
        sbSQL.append("	WHERE");
        sbSQL.append("	1 = 1 ");
        sbSQL.append("	AND saleList.state =0");
        sbSQL.append("	AND saleList.contractNo ='" + Database.encodeSQL(contractNo) + "'");
        sbSQL.append("	AND saleList.sid = (");
        sbSQL.append("		SELECT");
        sbSQL.append("			max(sc.sid)");
        sbSQL.append("		FROM");
        sbSQL.append("			sale_contractroutelist sc");
        sbSQL.append("		WHERE");
        sbSQL.append("			sc.state = 0");
        sbSQL.append("	    AND sc.contractNo ='" + Database.encodeSQL(contractNo) + "'");
        sbSQL.append("	)");
        List<ContractRouteListVO> lis = MySQLDao.query(sbSQL.toString(), ContractRouteListVO.class, null, conn);
        if (lis.size() != 1) {
            MyException.deal(MyException.newInstance("获取最后合同流转数据失败"));
        }
        return lis.get(0);
    }


    /**
     * 1、验证数据有效性，contracts  size 大与 0  ，
     * 2、验证operatorUser 对象是为null， 并判断userPO对象的id 是否为有效字符串
     * 3、验证connection
     * 4、遍历 集合
     * 创建 ContractRouteListPO对象，
     * 设置 ContractRouteListPO对象的属性，设置 OrgId 为新增的 ContractPO 的OrgId,
     * 设置 ContractRouteListPO对象的  ContractId 为 ContractPO  的 ContractNo
     * 设置ActionType 的值为 ContractrouteListStatus.ACTIONTYPE_ARCHIVECONTRACT 归档
     * 创建一个ContractRouteListService 的业务类对象，调用 ContractRouteListService 的InsertOfUpdate 方法执行业务操作返回影响行数。
     * <p/>
     * <p/>
     * 将确认合同状态的数据进行归档
     *
     * @param contractPO    合同集合
     * @param operatorUser 操作员
     * @param conn         数据库连接
     * @return
     */
    public int archiveContracts(ContractPO contractPO, UserPO operatorUser, Connection conn) throws Exception {
        if (contractPO == null) {
            MyException.deal(MyException.newInstance("归档销售合同参数异常"));
        }

        if (operatorUser == null || StringUtils.isEmpty(operatorUser.getId())) {
            MyException.deal(MyException.newInstance("归档销售合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        int count = 0;
        //增强for循环
        //合同流转对象
        ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
        //归档合同合同
        contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_ARCHIVECONTRACT);

        contractRouteListPO.setContractNo(contractPO.getContractNo());

        //获取上一条的记录
        ContractRouteListPO last = contractDao.getLastContractRouteListPO(contractPO.getContractNo(), conn);
        contractRouteListPO.setActionDescription("【"+operatorUser.getName()+"】归档销售合同");
        count = MySQLDao.insertOrUpdate(contractRouteListPO, operatorUser.getId(), conn);

        if (count != 1) {
            MyException.deal(MyException.newInstance("归档销售合同流转失败"));
        }
        return count;

    }

    /**
     * 1、验证 applicationOrgId
     * 2、验证 applicationId
     * 3、验证 productId
     * 4、验证 count
     * 5、验证 数据库连接 conn
     * 6、根据产品编号获取产品的信息，
     * 7、根据产品获取合同编号的前缀 getContractNo ，
     * 8、根据产品编号获取以该产品已经存在的最大合同编号，
     * 9、创建ContractPO对象，循环 count 合同套数
     * 10、根据最大合同编号与合同编号前缀设置、与产品的合同份数 生成销售合同记录。
     * 创建 ContractRouteListPO对象，
     * 设置 ContractRouteListPO对象的属性，设置 OrgId 为新增的 ContractPO 的OrgId,
     * 设置 ContractRouteListPO对象的  ContractId 为 ContractPO  的 ContractNo
     * 等待调配的 ContractRouteListStatus.ACTIONTYPE_WAITSEND 合同流转
     * 11、返回影响行数。
     *
     * @param productionId     产品编号
     * @param count            合同套数
     * @param operatorUser     操作员信息
     * @param conn             数据库连接
     * @return
     */
    public int newContract(ContractApplicationPO contractApplicationPO, String productionId, int count, int beginNumber, UserPO operatorUser, Connection conn) throws Exception {

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("销售合同申请产品参数异常").throwException();
        }

        if (count < 1) {
            MyException.newInstance("销售合同申请套数获取异常").throwException();
        }

        //根据合同编号获取合同的信息
        ProductionPO productionPO = new ProductionPO();
        productionPO.setId(productionId);
        productionPO.setState(Config.STATE_CURRENT);
        productionPO = MySQLDao.load(productionPO, ProductionPO.class, conn);

        //产品要求的合同份数
        int contractCopies = productionPO.getContractCopies();

        //获取合同编号的前缀
        String beforeContractNO = getContractNo(productionPO.getProductionNo());


        DepartmentService departmentService = new DepartmentService();
        DepartmentPO departmentPO = departmentService.load(contractApplicationPO.getDepartmentId(), conn);

        if (departmentPO == null || StringUtils.isEmpty(departmentPO.getId())) {
            MyException.newInstance("无法获得部门编号").throwException();
        }

        //循环合同套数
        for (int i = 0; i < count; i++) {
            //设置合同编号
            String contractNo = beforeContractNO + StringUtils.buildNumberString(beginNumber + i, 5);
            //循环产品合同份数
            for (int j = 1; j <= contractCopies; j++) {
                ContractPO contractPO = new ContractPO();

                //设置合同组织编号
                contractPO.setOrgId(departmentPO.getId());
                //产品编号
                contractPO.setProductionId(productionPO.getId());
                //申请编号
                contractPO.setApplicationId(contractApplicationPO.getId());
                //合同编号
                contractPO.setContractNo(contractNo);
                //合同序号
                contractPO.setContractDetailNo(String.valueOf(j));
                //合同组织好
                contractPO.setContractDisplayNo(contractNo + "-" + j);


                contractPO.setStatus(ContractStatus.Unsigned);//未签约
                contractPO.setDetailStatus(ContractDetailStatus.Active); //正常状态

                int countOfNewContract = MySQLDao.insertOrUpdate(contractPO, operatorUser.getId(), conn);

                if (countOfNewContract != 1) {
                    MyException.newInstance("销售合同添加失败").throwException();
                }


            }

            ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
            contractRouteListPO.setContractNo(contractNo);
            //设置状态等待配送
            contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_WAITSEND);
            contractRouteListPO.setActionDescription("【"+operatorUser.getName()+"】添加销售合同成功");
            contractRouteListPO.setActionTime(TimeUtils.getNow());
            contractRouteListPO.setActionUserId(operatorUser.getId());

            int countOfNewContractRouteList = MySQLDao.insertOrUpdate(contractRouteListPO, operatorUser.getId(), conn);
            if (countOfNewContractRouteList != 1) {
                MyException.newInstance("销售合同添加失败").throwException();
            }
        }

        return 1;
    }

    /**
     * 设置销售合同属性
     *
     * @return
     */
    public int setContractAttribute(ContractPO contractPO, UserPO operatorUser, DepartmentPO departmentPO, Connection conn) throws Exception {

        contractPO.setId(IdUtils.getUUID32());
        contractPO.setSid(MySQLDao.getMaxSid("Sale_Contract", conn));
        contractPO.setState(Config.STATE_CURRENT);
        contractPO.setOperatorId(operatorUser.getId());
        contractPO.setOperateTime(TimeUtils.getNow());
        contractPO.setStatus(ContractStatus.Unsigned);//未签约
        contractPO.setDetailStatus(ContractDetailStatus.Active); //正常状态
        //添加合同
        int numbers = MySQLDao.insert(contractPO, conn);
        if (numbers != 1) {
            MyException.deal(MyException.newInstance("销售合同添加失败"));
        }
        /*
         * 申请是更改财富中心区分列表的，所有审批的人只有对应的财富中心的人才可以看到，所有试一次生成的
         * 的合同的部门就是审批人的部门
         */
        ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
        contractRouteListPO.setContractNo(contractPO.getContractNo());
        //设置状态等待配送
        contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_WAITSEND);
        contractRouteListPO.setActionDescription("【"+operatorUser.getName()+"】添加销售合同成功");

        return MySQLDao.insertOrUpdate(contractRouteListPO, operatorUser.getId(), conn);

    }


    /**
     * 验证 productionId,
     * 根据产品编号计算合同编号同编号规定的前十位
     *
     * @param productionID 产品编号
     * @return 合同编号前缀，
     * @throws Exception
     */
    public String getContractNo(String productionID) throws Exception {
        if (StringUtils.isEmpty(productionID)) {
            MyException.deal(MyException.newInstance("产品获取失败"));
        }

        // 得到年份的后两位
        Calendar week = Calendar.getInstance();
        String year = String.valueOf(week.get(Calendar.YEAR)).substring(2);

        // 处理产品编号;
        productionID = productionID.substring(4);

        // 返回合同编号规定的前十位
        return "103" + productionID;
    }

    public List<ContractPO> getContractPOsByContractNo(String contractNo, Connection conn) throws Exception {

        ContractPO contractPO = new ContractPO();
        contractPO.setState(Config.STATE_CURRENT);
        contractPO.setContractNo(contractNo);

        List<ContractPO> list = MySQLDao.search(contractPO, ContractPO.class, null, null, conn);

        return list;
    }



    /**
     * 根据产品编号销售员所有销售合同。
     *
     * @param productionId  产品编号
     * @param receiveUserId 销售员编号
     * @param conn
     * @return
     */
    public List<ContractPO> getContractPOs(String productionId, String receiveUserId, Connection conn) throws Exception {
        if (StringUtils.isEmpty(productionId)) {
            MyException.deal(MyException.newInstance("产品编号异常"));
        }
        if (StringUtils.isEmpty(receiveUserId)) {
            MyException.deal(MyException.newInstance("销售员编号"));
        }
        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getContractPOs", "ContractServiceSQL", ContractService.class);
        dbSQL.addParameter4All("productionId", productionId);
        dbSQL.addParameter4All("receiveUserId", receiveUserId);
        dbSQL.initSQL();


        return MySQLDao.search(dbSQL, ContractPO.class, conn);
    }

    /**
     * 订单编辑页面下拉列表(combotree)控件数据的构造
     *
     * @param list 销售合同集合对象
     * @return JSONArray
     * @throws Exception
     */
    public JSONArray getContractPOToMenuArray(List<ContractPO> list) throws Exception {
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            for (ContractPO c : list) {
                MenuVO m = new MenuVO();
                m.setId(c.getContractNo());
                m.setText(c.getContractNo());
                array.add(m.toJsonObject4Form());
            }
        }
        return array;
    }

    /**
     * 验证数据有效性
     * 根据部门获取改部门小组的所有销售员
     *
     * @param departmentId 部门编号
     * @param conn         数据库连接
     * @return
     */
    public Pager getSaleUser(String departmentId, UserVO userVO, HttpServletRequest request, Connection conn) throws Exception {
        if (StringUtils.isEmpty(departmentId)) {
            MyException.deal(MyException.newInstance("销售部门参数异常"));
        }
        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT");
        sbSQL.append("	salemanUser.*");
        sbSQL.append("	FROM");
        sbSQL.append("		crm_saleman_salemangroup AS salemangroup,");
        sbSQL.append("		crm_salemangroup AS `group`,");
        sbSQL.append("		system_user AS salemanUser");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        sbSQL.append("	AND salemanUser.State = 0");
        sbSQL.append("	AND `group`.State = 0");
        sbSQL.append("	AND salemangroup.saleManGroupId = `group`.Id");
        sbSQL.append("	AND salemanUser.id = salemangroup.saleManId");
        sbSQL.append("	AND `group`.DepartmentId = '" + Database.encodeSQL(departmentId) + "'");

        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        return Pager.query(sbSQL.toString(), userVO, null, request, queryType, conn);
    }


    /***
     * 获取合同综合查询的列表数据
     * @param contractVO
     * @param conditions
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager getContractCompositeSearchList(ContractVO contractVO, List<KVObject> conditions, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {
        //异常处理
        if (contractVO == null) {
            MyException.newInstance("合同综合查询参数异常").throwException();
        }

        if (conditions == null) {
            MyException.newInstance("合同综合查询时间段查询参数异常").throwException();
        }

        if (conn == null) {
            MyException.newInstance("数据库连接异常").throwException();
        }

        //组装SQL语句
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getContractCompositeSearchList","ContractServiceSQL",ContractService.class);
        dbSQL.initSQL();

        //查询
        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), contractVO, conditions, currentPage, showRowCount, queryType, conn);

        //返回结果
        return pager;


    }


    /***
     * 通过productionId获取产品分期对应的合同摘要信息
     * @param contractVO
     * @param productionId
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager getContractAbstractByProduction(ContractVO contractVO, String productionId, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {
        //组装SQL语句
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getContractAbstractByProduction","ContractServiceSQL",ContractService.class);
        dbSQL.addParameter4All("productionId",productionId);
        dbSQL.initSQL();

        //查询
        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), contractVO, null, currentPage, showRowCount, queryType, conn);

        //返回结果
        return pager;


    }

    /***
     * 通过productionHomeId获取该产品对应的合同摘要信息
     * @param contractVO
     * @param productionHomeId
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager getContractAbstractByProductionHome(ContractVO contractVO, String productionHomeId, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {
        //组装SQL
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getContractAbstractByProductionHome","ContractServiceSQL",ContractService.class);
        dbSQL.addParameter4All("productionHomeId",productionHomeId);
        dbSQL.initSQL();

        //查询
        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), contractVO, null, currentPage, showRowCount, queryType, conn);

        //返回结果
        return pager;
    }
}
