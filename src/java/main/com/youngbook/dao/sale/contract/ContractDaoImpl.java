package com.youngbook.dao.sale.contract;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.contract.*;
import com.youngbook.entity.vo.Sale.contract.ContractVO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/30.
 */
@Component("contractDao")
public class ContractDaoImpl implements IContractDao {

    public int changeProduction(String contractNO, String productionId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得产品编号", "method=ContractDao.changeProduction").throwException();
        }

        List<ContractPO> contractPOs = listContractPOByContractNO(contractNO, conn);

        if (contractPOs == null || contractPOs.size() == 0) {
            MyException.newInstance("无法获得合同").throwException();
        }


        for (int i = 0; i < contractPOs.size(); i++) {
            ContractPO contractPO = contractPOs.get(i);
            contractPO.setProductionId(productionId);

            MySQLDao.insertOrUpdate(contractPO, conn);
        }

        return 1;
    }

    public int setContractStatus(String contractNO, int status, Connection conn) throws Exception {
        List<ContractPO> contractPOs = listContractPOByContractNO(contractNO, conn);

        if (contractPOs == null || contractPOs.size() == 0) {
            MyException.newInstance("无法获得合同").throwException();
        }


        for (int i = 0; i < contractPOs.size(); i++) {
            ContractPO contractPO = contractPOs.get(i);
            contractPO.setStatus(status);

            MySQLDao.insertOrUpdate(contractPO, conn);
        }

        return 1;

    }

    public List<ContractPO> listContractPOByContractNO(String contractNO, Connection conn) throws Exception {

        if (StringUtils.isEmpty(contractNO)) {
            MyException.newInstance("无法获得合同号").throwException();
        }

        ContractPO contractPO = new ContractPO();
        contractPO.setState(Config.STATE_CURRENT);
        contractPO.setContractNo(contractNO);

        List<ContractPO> list = MySQLDao.search(contractPO, ContractPO.class, null, null, conn);

        return list;
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
     * 总部调配合同列表
     *
     * @param contractVO 销售合同
     * @param conditions 时间段查询条件
     * @param request    http请求
     * @param conn       数据库链接
     * @return
     */
    public Pager listContractVOs(ContractVO contractVO, List<KVObject> conditions ,HttpServletRequest request, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listContractVOs", "ContractDaoImplSQL", ContractDaoImpl.class);
        dbSQL.initSQL();


        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        return Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), contractVO, conditions, request, queryType, conn);
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
     * 设置ActionType 的值为 ContractrouteListStatus.ACTIONTYPE_CANCELSING  取消签约
     * 创建一个ContractRouteListService 的业务类对象，调用 ContractRouteListService 的InsertOfUpdate 方法执行业务操作返回影响行数。
     * <p/>
     * 取消签约合同
     *
     * @param contractNO   合同编号
     * @param operatorUser 操作员
     * @param conn         数据库链接
     * @return
     */
    public int cancelSignedContract(String contractNO, UserPO operatorUser, Connection conn) throws Exception {

        if (StringUtils.isEmpty(contractNO)) {
            MyException.newInstance("取消合同签约失败，合同号为空").throwException();
        }

        if (contractNO == null) {
            MyException.deal(MyException.newInstance("取消签约销售合同参数异常"));
        }

        if (operatorUser == null || StringUtils.isEmpty(operatorUser.getId())) {
            MyException.deal(MyException.newInstance("取消签约销售合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("cancelSignedContract", "ContractDaoImplSQL", ContractDaoImpl.class);
        dbSQL.addParameter4All("contractNO", contractNO);
        dbSQL.initSQL();

        //获取销售合同集合
        List<ContractPO> contractPOs = MySQLDao.search(dbSQL, ContractPO.class, conn);

        for (int i = 0; contractPOs != null && i < contractPOs.size(); i++) {
            ContractPO contractPO = contractPOs.get(i);


            //合同状态不等于签约
            if (contractPO.getStatus() != ContractStatus.Signed) {
                MyException.deal(MyException.newInstance("取消签约销售合同状态异常，当前合同状态并不是【已签约】状态"));
            }

            //取消签约销售合同
            int count = contractCancelSigned(contractPO, operatorUser, conn);
            if (count != 1) {
                MyException.deal(MyException.newInstance("取消签约销售合同失败"));
            }
            //合同流转对象
            ContractRouteListPO contractRouteListPO = new ContractRouteListPO();
            //取消签约销售合同
            contractRouteListPO.setActionType(ContractRouteListStatus.ACTIONTYPE_CANCELSING);

            contractRouteListPO.setContractNo(contractPO.getContractNo());

            //获取上一条的记录
            contractRouteListPO.setActionDescription("【"+operatorUser.getName()+"】取消签约合同");
            MySQLDao.insertOrUpdate(contractRouteListPO, operatorUser.getId(), conn);
        }

        return 1;
    }


    /**
     * 验证 contractPO
     * 验证 optionUser
     * 验证 conn
     * 更新当前合同，设置操作员，设置合同状态、合同明细状态 返回影响行数
     * 取消签约合同
     *
     * @param contractPO 销售合同
     * @param optionUser 操作员
     * @param conn       数据库连接
     * @return
     * @throws Exception
     */
    public int contractCancelSigned(ContractPO contractPO, UserPO optionUser, Connection conn) throws Exception {
        if (contractPO == null) {
            MyException.deal(MyException.newInstance("取消签约销售合同参数异常"));
        }

        if (optionUser == null || StringUtils.isEmpty(optionUser.getId())) {
            MyException.deal(MyException.newInstance("取消签约销售合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }

        contractPO.setStatus(ContractStatus.Unsigned);//未签约
        contractPO.setDetailStatus(ContractDetailStatus.Active);//正常
        return MySQLDao.insertOrUpdate(contractPO, optionUser.getId(), conn);
    }


//    public ContractRouteListPO getLastContractRouteListPO(String contractNo, Connection conn) throws Exception {
//        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from sale_contractroutelist where state=0 and ContractNo=? ORDER BY sid DESC limit 1").addParameter(1, contractNo);
//
//        List<ContractRouteListPO> list = MySQLDao.search(dbSQL, ContractRouteListPO.class, conn);
//
//        if (list != null && list.size() == 1) {
//            return list.get(0);
//        }
//
//        return null;
//    }


    /**
     * 验证  contractId, orgid ,conn
     * 设置属性 获取对象
     * 获取最后一条流转记录
     *
     * @return
     * @throws Exception
     */
    public ContractRouteListPO getLastContractRouteListPO(String contractNO, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getLastContractRouteListPO", "ContractDaoImplSQL", ContractDaoImpl.class);
        dbSQL.addParameter4All("contractNO", contractNO);
        dbSQL.initSQL();
        List ls = MySQLDao.search(dbSQL, ContractRouteListPO.class, conn);

        if (ls == null || ls.size() == 0 || ls.size() != 1) {
            MyException.newInstance("获取销售流转记录失败").throwException();
        }
        return (ContractRouteListPO) ls.get(0);
    }

    /**
     * 验证 contractPO
     * 验证 optionUser
     * 验证 conn
     * 更新当前合同，设置操作员，设置合同状态、合同明细状态 返回影响行数
     *
     * @param contractNo 销售合同
     * @param userId 操作员
     * @param conn       数据库连接
     * @return
     * @throws Exception
     */
    public int contractSigned(String contractNo,  String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(contractNo)) {
            MyException.deal(MyException.newInstance("签约销售合同参数异常"));
        }

        if (StringUtils.isEmpty(userId)) {
            MyException.deal(MyException.newInstance("签约销售合同操作员异常"));
        }

        if (conn == null) {
            MyException.deal(MyException.newInstance("数据库连接异常"));
        }


        List<ContractPO> contractPOs = this.getContractByContractNO(contractNo, conn);

        for (ContractPO contractPO : contractPOs) {
            contractPO.setStatus(ContractStatus.Signed);
            MySQLDao.insertOrUpdate(contractPO, userId, conn);
        }

        this.saveContractRouteList4Signed(contractNo, userId, conn);


        return 1;
    }

    public int saveContractRouteList4Signed(String contractNo, String actionUserId, Connection conn) throws Exception {

        int actionType = ContractRouteListStatus.ACTIONTYPE_SING_OK;

        if (StringUtils.isEmpty(actionUserId)) {
            MyException.newInstance("销售合同操作员异常").throwException();
        }

        String description = "订单签约";
        int count = 0;


        ContractRouteListPO contractRouteListPO = this.getLastContractRouteListPO(contractNo, conn);

        if (contractRouteListPO != null) {
            int sid = MySQLDao.getMaxSid("Sale_ContractRouteList", conn);
            contractRouteListPO.setSid(sid);
            contractRouteListPO.setId(IdUtils.getUUID32());
            contractRouteListPO.setActionType(actionType);
            contractRouteListPO.setActionDescription(description);
            contractRouteListPO.setActionTime(TimeUtils.getNow());
            contractRouteListPO.setOperateTime(TimeUtils.getNow());
            contractRouteListPO.setOperatorId(actionUserId);
            MySQLDao.insert(contractRouteListPO,conn);
        }


        return 1;
    }


    public boolean checkContractNO(String contractNO, String productionId, Connection conn) throws Exception {

        if (StringUtils.isEmptyAny(contractNO, productionId)) {
            MyException.newInstance("传入参数有误", "contractNO="+contractNO+"&productionId="+productionId).throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("checkContractNO", this);
        dbSQL.addParameter4All("contractNO", contractNO);
        dbSQL.addParameter4All("productionId", productionId);
        dbSQL.initSQL();

        List<ContractPO> list = MySQLDao.search(dbSQL, ContractPO.class, conn);

        if (list != null && list.size() > 0) {
            return true;
        }

        return false;
    }


    public List<ContractRouteListPO> getContractRouteListByContranctNo(String contracrNo, Connection conn) throws Exception {

        DatabaseSQL databaseSQL = DatabaseSQL.getInstance("select * from sale_contractroutelist where state=0 and contractNo=?").addParameter(1, contracrNo);

        List<ContractRouteListPO> list = MySQLDao.search(databaseSQL, ContractRouteListPO.class, conn);

        return list;
    }

    public ContractPO getContractByContractId(String contractId, Connection conn) throws Exception {

        ContractPO contractPO = new ContractPO();
        contractPO.setId(contractId);
        contractPO.setState(Config.STATE_CURRENT);

        contractPO = MySQLDao.load(contractPO,ContractPO.class, conn);

        return contractPO;
    }

    /**
     * 根据合同编号所有销售合同。
     *
     * @param contractNO
     * @param conn
     * @return
     */
    public List<ContractPO> getContractByContractNO(String contractNO, Connection conn) throws Exception {
        if (StringUtils.isEmpty(contractNO)) {
            MyException.newInstance("合同编号获取异常").throwException();
        }
        if (conn == null) {
            MyException.newInstance("数据库连接异常").throwException();
        }


        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getContractByContractNO", "ContractDaoImplSQL", ContractDaoImpl.class);
        dbSQL.addParameter4All("contractNo", contractNO);
        dbSQL.initSQL();

        return MySQLDao.search(dbSQL, ContractPO.class, conn);
    }
}
