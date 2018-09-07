package com.youngbook.service.oa.finance;

import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.finance.FinanceExpenseDetailPO;
import com.youngbook.entity.po.wf.FinanceExpensePO;
import com.youngbook.entity.vo.oa.finance.FinanceExpenseVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 15-4-2
 * Time: 上午9:37
 * To change this template use File | Settings | File Templates.
 */
public class FinanceExpenseService extends BaseService {

    /**
     *
     * 获取当前数据对象的列表数据
     * @param expenseVO
     * @param conditions  查询条件
     * @param departments 部门
     * @param financeExpense
     * @return 列表分页对象
     * @throws Exception
     */
    /**
     * 2015-5-9 海鸿
     * 修改了SQL语句
     */
    public Pager list(FinanceExpenseVO expenseVO,UserPO user, List<KVObject> conditions, String departments, FinanceExpensePO financeExpense) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "operateTime desc"));

        if (departments != null && !departments.equals("")) {
            KVObject kvDepartment = new KVObject("controlString2Id", " in ('" + Database.encodeSQL(departments) + "')");
            conditions.add(kvDepartment);
        }

        if (financeExpense.getMoney() != Double.MAX_VALUE) {
            KVObject mon = new KVObject("money", ">=" + financeExpense.getMoney());
            conditions.add(mon);
        }

        //构建sql语句
        //构建sql语句

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" ");
        sbSQL.append(" SELECT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" expense.sid,");
        sbSQL.append(" expense.id AS id,");
        sbSQL.append(" expense.submitterId,");
        sbSQL.append(" expense.operatorId,");
        sbSQL.append(" u. NAME submitterName,");
        sbSQL.append(" expense.time,");
        sbSQL.append(" expense.month,");
        sbSQL.append(" expense.money,");
        sbSQL.append(" expense.expenseId,");
        sbSQL.append(" "+ BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	ob.submitterId yz_submitterId,");
        sbSQL.append("	ob.WORKFLOWID,");
        sbSQL.append(" expense.STATUS,");
        sbSQL.append(" expense.operateTime,");
        sbSQL.append(" expense.state AS state,");
        sbSQL.append(" expense.accessoryNumber,");
        sbSQL.append(" po.DepartmentName,");
        sbSQL.append(" po.DepartmentId");
        sbSQL.append(" FROM");
        sbSQL.append(" workflowaction_financeexpense expense,");
        sbSQL.append(" system_user u,");
        sbSQL.append(" system_positionuser pu,");
        sbSQL.append(" system_position po,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" AND expense.state = 0");
        sbSQL.append(" AND pu.states = 1");
        sbSQL.append(" AND u.id = expense.submitterId");
        sbSQL.append(" AND po.id = pu.positionId");
        sbSQL.append(" AND u.id = pu.userId");
        sbSQL.append(" AND rl.YWID = expense.id");
        sbSQL.append(" AND ob.Id_YWID=expense.Id");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" UNION");
        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" -1 currentNodeId,");
        sbSQL.append(" -1 routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" expense.sid,");
        sbSQL.append(" expense.id AS id,");
        sbSQL.append(" expense.submitterId,");
        sbSQL.append(" expense.operatorId,");
        sbSQL.append(" u.NAME submitterName,");
        sbSQL.append(" expense.time,");
        sbSQL.append(" expense.month,");
        sbSQL.append(" expense.money,");
        sbSQL.append(" expense.expenseId,");
        sbSQL.append(" "+ BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append(" expense.STATUS,");
        sbSQL.append("	ob.submitterId yz_submitterId,");
        sbSQL.append("	ob.WORKFLOWID,");
        sbSQL.append(" expense.operateTime,");
        sbSQL.append(" expense.state AS state,");
        sbSQL.append(" expense.accessoryNumber,");
        sbSQL.append(" po.DepartmentName,");
        sbSQL.append(" po.DepartmentId");
        sbSQL.append(" FROM");
        sbSQL.append(" workflowaction_financeexpense expense,");
        sbSQL.append(" system_user u,");
        sbSQL.append(" system_positionuser pu,");
        sbSQL.append(" system_position po,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append(" WHERE ");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" AND expense.state = 0");
        sbSQL.append(" AND u.id = expense.submitterId");
        sbSQL.append(" AND po.id = pu.positionId");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND u.id = pu.userId");
        sbSQL.append(" AND pu.states = 1");
        sbSQL.append(" AND rl.YWID = expense.id");
        sbSQL.append(" AND ob.Id_YWID=expense.Id");
        sbSQL.append(" AND rl.STATU IN (5)");
        sbSQL.append(" UNION");
        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" 1 currentNodeId,");
        sbSQL.append(" 0 routeListId,");
        sbSQL.append(" -1 currentStatus,");
        sbSQL.append(" expense.sid,");
        sbSQL.append(" expense.id AS id,");
        sbSQL.append(" expense.submitterId,");
        sbSQL.append(" expense.operatorId,");
        sbSQL.append(" u.NAME submitterName,");
        sbSQL.append(" expense.time,");
        sbSQL.append(" expense.month,");
        sbSQL.append(" expense.money,");
        sbSQL.append(" expense.expenseId,");
        sbSQL.append(" "+ BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	ob.submitterId yz_submitterId,");
        sbSQL.append("	ob.WORKFLOWID,");
        sbSQL.append(" expense.STATUS,");
        sbSQL.append(" expense.operateTime,");
        sbSQL.append(" expense.state AS state,");
        sbSQL.append(" expense.accessoryNumber,");
        sbSQL.append(" po.DepartmentName,");
        sbSQL.append(" po.DepartmentId");
        sbSQL.append(" FROM ");
        sbSQL.append(" workflowaction_financeexpense expense,");
        sbSQL.append(" system_user u,");
        sbSQL.append(" system_positionuser pu,");
        sbSQL.append(" system_position po,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append(" WHERE ");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" AND expense.state = 0");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" AND pu.states = 1");
        sbSQL.append(" AND u.id = expense.submitterId");
        sbSQL.append(" AND po.id = pu.positionId");
        sbSQL.append(" AND u.id = pu.userId");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND ob.Id_YWID=expense.Id");
        sbSQL.append(" AND expense.id NOT IN(");
        sbSQL.append(" SELECT");
        sbSQL.append(" YWID");
        sbSQL.append(" FROM");
        sbSQL.append(" workflow_routelist rl");
        sbSQL.append(" where ");
        sbSQL.append(" 1=1");
        sbSQL.append(")");

        //添加用户条件
        conditions.add(new KVObject("operatorId = ","'"+Database.encodeSQL(user.getId())+"'"));
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //查询数据
        Pager pager = Pager.query(sbSQL.toString(), expenseVO, conditions, request, queryType);

        //返回格式化数据
        return insertTitle( pager);
    }
   /**
     * 2015-5-9
    * 海鸿
    * 修改了SQL语句
     */
    public Pager wsitlist(FinanceExpenseVO expenseVO, UserPO user,List<KVObject> conditions, String departments, FinanceExpensePO financeExpense) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "operateTime desc"));

        if (departments != null && !departments.equals("")) {
            KVObject kvDepartment = new KVObject("departmentId", " in (" + Database.encodeSQL(departments) + ")");
            conditions.add(kvDepartment);
        }
        if (financeExpense.getMoney() != Double.MAX_VALUE) {
            KVObject mon = new KVObject("money", ">=" + financeExpense.getMoney());
            conditions.add(mon);
        }

        //构建sql语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" ");
        sbSQL.append(" SELECT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" expense.sid,");
        sbSQL.append(" expense.id AS id,");
        sbSQL.append(" expense.submitterId,");
        sbSQL.append(" u. NAME submitterName,");
        sbSQL.append(" expense.time,");
        sbSQL.append(" expense.month,");
        sbSQL.append(" expense.money,");
        sbSQL.append(" expense.expenseId,");
        sbSQL.append(" "+ BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append(" expense.STATUS,");
        sbSQL.append(" expense.operateTime,");
        sbSQL.append(" expense.state AS state,");
        sbSQL.append(" expense.accessoryNumber,");
        sbSQL.append(" po.DepartmentName,");
        sbSQL.append(" po.DepartmentId");
        sbSQL.append(" FROM");
        sbSQL.append(" workflowaction_financeexpense expense,");
        sbSQL.append(" system_user u,");
        sbSQL.append(" system_positionuser pu,");
        sbSQL.append(" system_position po,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" AND expense.state = 0");
        sbSQL.append(" AND pu.states = 1");
        sbSQL.append(" AND u.id = expense.submitterId");
        sbSQL.append(" AND po.id = pu.positionId");
        sbSQL.append(" AND u.id = pu.userId");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND ob.controlString1  in (" + BizRouteConfig.getDepartmentParentId(user.getId())+")");
        sbSQL.append(" AND rl.YWID = expense.id");
        sbSQL.append(" AND ob.Id_YWID=expense.Id");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append("   AND rl.CURRENTNODE IN (");
        sbSQL.append("   	SELECT");
        sbSQL.append("   		NODEID");
        sbSQL.append("   	FROM");
        sbSQL.append("   		workflow_participant p,");
        sbSQL.append("   		workflow_role r");
        sbSQL.append("   	WHERE");
        sbSQL.append("   		p.WORKFLOWID = "+Config.getSystemVariable("WORKFLOWID.Finance.FinanceExpende")  );
        sbSQL.append("   	AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");
        sbSQL.append("   	AND r.USERLIST LIKE '%|" + Database.encodeSQL(user.getId()) + "|%'");
        sbSQL.append("   )");
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取数据
        Pager pager = Pager.query(sbSQL.toString(), expenseVO, conditions, request, queryType);

        //返回格式化数据
        return insertTitle( pager);
    }
   /**
     * 2015-5-9 海鸿 修改了SQL语句
     */
    public Pager participantlist(FinanceExpenseVO expenseVO,UserPO user, List<KVObject> conditions, String departments, FinanceExpensePO financeExpense) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "operateTime desc"));

        if (departments != null && !departments.equals("")) {
            KVObject kvDepartment = new KVObject("departmentId", " in (" + Database.encodeSQL(departments) + ")");
            conditions.add(kvDepartment);
        }
        if (financeExpense.getMoney() != Double.MAX_VALUE) {
            KVObject mon = new KVObject("money", ">=" + financeExpense.getMoney());
            conditions.add(mon);
        }

        //构建sql语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" ");

        sbSQL.append(" SELECT");
        sbSQL.append(" 	*");
        sbSQL.append(" FROM");
        sbSQL.append(" 	(");
        sbSQL.append(" SELECT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" expense.sid,");
        sbSQL.append(" expense.id AS id,");
        sbSQL.append(" expense.submitterId,");
        sbSQL.append(" u. NAME submitterName,");
        sbSQL.append(" expense.time,");
        sbSQL.append(" expense.month,");
        sbSQL.append(" expense.money,");
        sbSQL.append(" expense.expenseId,");
        sbSQL.append(" "+ BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append(" expense.STATUS,");
        sbSQL.append(" expense.operateTime,");
        sbSQL.append(" expense.state AS state,");
        sbSQL.append(" expense.accessoryNumber,");
        sbSQL.append(" po.DepartmentName,");
        sbSQL.append(" po.DepartmentId");
        sbSQL.append(" FROM");
        sbSQL.append(" workflowaction_financeexpense expense,");
        sbSQL.append(" system_user u,");
        sbSQL.append(" system_positionuser pu,");
        sbSQL.append(" system_position po,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" AND expense.state = 0");
        sbSQL.append(" AND pu.states = 1");
        sbSQL.append(" AND u.id = expense.submitterId");
        sbSQL.append(" AND po.id = pu.positionId");
        sbSQL.append(" AND u.id = pu.userId");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.YWID = expense.id");
        sbSQL.append(" AND ob.Id_YWID=expense.Id");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" UNION");
        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" -1 currentNodeId,");
        sbSQL.append(" -1 routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" expense.sid,");
        sbSQL.append(" expense.id AS id,");
        sbSQL.append(" expense.submitterId,");
        sbSQL.append(" u.NAME submitterName,");
        sbSQL.append(" expense.time,");
        sbSQL.append(" expense.month,");
        sbSQL.append(" expense.money,");
        sbSQL.append(" expense.expenseId,");
        sbSQL.append(" "+ BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append(" expense.STATUS,");
        sbSQL.append(" expense.operateTime,");
        sbSQL.append(" expense.state AS state,");
        sbSQL.append(" expense.accessoryNumber,");
        sbSQL.append(" po.DepartmentName,");
        sbSQL.append(" po.DepartmentId");
        sbSQL.append(" FROM");
        sbSQL.append(" workflowaction_financeexpense expense,");
        sbSQL.append(" system_user u,");
        sbSQL.append(" system_positionuser pu,");
        sbSQL.append(" system_position po,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append(" WHERE ");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" AND expense.state = 0");
        sbSQL.append(" AND u.id = expense.submitterId");
        sbSQL.append(" AND po.id = pu.positionId");
        sbSQL.append(" AND u.id = pu.userId");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND pu.states = 1");
        sbSQL.append(" AND rl.YWID = expense.id");
        sbSQL.append(" AND ob.Id_YWID=expense.Id");
        sbSQL.append(" AND rl.STATU IN (5)");
        sbSQL.append(" 	) t");
        sbSQL.append(" WHERE");
        sbSQL.append(" 	1 = 1");
        sbSQL.append(" AND t.Id IN (");
        sbSQL.append(" 	SELECT DISTINCT");
        sbSQL.append(" 		YWID");
        sbSQL.append(" 	FROM");
        sbSQL.append(" 		workflow_action");
        sbSQL.append(" 	WHERE");
        sbSQL.append(" 		1 = 1");
        //添加业务编号
        sbSQL.append(" 	AND WORKFLOWID = " + Config.getSystemVariable("WORKFLOWID.Finance.FinanceExpende")  );
        sbSQL.append(" 	AND PARTICIPANT = '" + Database.encodeSQL(user.getId()) + "'");
        sbSQL.append(" )");
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //分页数据
        Pager pager = Pager.query(sbSQL.toString(), expenseVO, conditions, request, queryType);

        //格式化数据
       return insertTitle( pager);
    }

    public Pager listDetail(FinanceExpenseDetailPO expenseDetail) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
//        StringBuffer sbSQL = new StringBuffer();
//        sbSQL.append(" ");
        String sql = "SELECT * from oa_financeexpensedetail where ExpenseId='" + Database.encodeSQL(expenseDetail.getExpenseId()) + "' and state = 0";
        Pager pager = Pager.query(sql, expenseDetail, null, request, queryType);

        return pager;
    }

    /**
     * 2015-6-9
     * 海鸿
     * 创建一个计算费用报销的项目的金额方法
     *
     * @param ExpenseId
     * @return
     * @throws Exception
     */
    public String getFinanceExpenseMoney(String ExpenseId) throws Exception {
        if( StringUtils.isEmpty (ExpenseId)){
            MyException.newInstance ("费用报销项目金额获取失败");
        }
        List<Map<String, Object>> list = MySQLDao.query("SELECT Money from oa_financeexpensedetail WHERE state=0 and ExpenseId='" + Database.encodeSQL(ExpenseId) + "'");

        //计算金额
        Double moneySum = 0.0;
        for (Map<String, Object> map : list) {
            for (String k : map.keySet()) {
                System.out.println(k + " : " + map.get(k));
                moneySum += Double.parseDouble(map.get(k).toString());
            }
        }

        return moneySum.toString();
    }

    /**
     * 2015-6-10 海鸿
     * 创建一个根据ID获取名称的方法
     *
     * @param obj     json对象
     * @param jsonstr json 前缀
     * @param id      需要查询的id
     * @return
     * @throws Exception
     */
    public JSONObject getUserName(JSONObject obj, String jsonstr, String id) throws Exception {
        if(StringUtils.isEmpty (id)){
            MyException.newInstance ("费用报销项目id不能为null");
        }

        //创建sql语句
        String sql = "select u.`name` from system_user u where u.id='" + Database.encodeSQL(id) + "'";

        //获取数据
        List<Map<String, Object>> list = MySQLDao.query(sql);

        //数据处理
        for (Map<String, Object> map : list) {
            for (String k : map.keySet()) {
                obj.element(jsonstr, map.get(k));
            }
        }

        return obj;
    }

    /**
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 返回影响行数
     * 添加或修改数据，并修改数据状态
     *
     * @param financeExpense 数据对象
     * @param user           当前操作用户对象
     * @param conn           数据链接
     * @return 影响行数
     * @throws Exception
     */
    public int insertOrUpdate(FinanceExpensePO financeExpense,FinanceExpenseVO financeExpenseVO,  UserPO user, Connection conn) throws Exception {

        if (financeExpense == null) {
            throw new Exception("费用报销数据提交失败");
        }

        //当前用户操作类是否为空
        if (user == null) {
            throw new Exception("当前用户失效");
        }

        //当前数据链接是否空
        if (conn == null) {
            throw new Exception("链接错误");
        }

        int count = 0;
        // 新增数据
        if (financeExpense.getId().equals("")) {
            //获取金额数据列表
            List<Map<String, Object>> list = MySQLDao.query("SELECT Money from oa_financeexpensedetail WHERE state=0 and ExpenseId='" + Database.encodeSQL(financeExpense.getExpenseId()) + "'");

            //计算总金额
            Double moneySum = 0.0;
            for (Map<String, Object> map : list) {
                for (String k : map.keySet()) {
                    System.out.println(k + " : " + map.get(k));
                    moneySum += Double.parseDouble(map.get(k).toString());
                }
            }

            financeExpense.setMoney(moneySum);
            financeExpense.setSid(MySQLDao.getMaxSid("workflowaction_financeexpense", conn));
            financeExpense.setId(IdUtils.getUUID32());
            financeExpense.setState(Config.STATE_CURRENT);
            financeExpense.setOperatorId(user.getId());
            financeExpense.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(financeExpense, conn);

            /**
             * 2015 -7-8 小周
             * 当数据添加成功时 向流程表中添加数据
             */

            if (count == 1) {
                count = BizRouteService.insertOrUpdate(financeExpense.getId(), financeExpenseVO.getControlString3(),financeExpenseVO.getControlString1(), financeExpenseVO.getControlString2(),
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Finance.FinanceExpende")), true, user, conn);
            }

        }
        // 更新
        else {
            FinanceExpensePO temp = new FinanceExpensePO();
            temp.setSid(financeExpense.getSid());
            temp = MySQLDao.load(temp, FinanceExpensePO.class);
            temp.setState(Config.STATE_UPDATE);

            count = MySQLDao.update(temp, conn);


            if (count == 1) {

                financeExpense.setSid(MySQLDao.getMaxSid("workflowaction_financeexpense", conn));
                financeExpense.setState(Config.STATE_CURRENT);
                financeExpense.setOperatorId(user.getId());
                financeExpense.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(financeExpense, conn);

                if (count == 1) {
                    count = BizRouteService.insertOrUpdate(financeExpense.getId(), financeExpenseVO.getControlString3(),financeExpenseVO.getControlString1(), financeExpenseVO.getControlString2(),
                            Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Finance.FinanceExpende")), false, user, conn);
                }
            }

        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    /**
     * 添加或修改项目
     *
     * @param expenseDetail 项目数据对象
     * @param user          当前操作用户对象
     * @param conn          数据库链接
     * @return
     * @throws Exception
     */

    public int insertOrUpdateDetail(FinanceExpenseDetailPO expenseDetail, UserPO user, Connection conn) throws Exception {

        int count = 0;
        // 新增
        if (expenseDetail.getId().equals("")) {
            expenseDetail.setSid(MySQLDao.getMaxSid("oa_financeexpensedetail", conn));
            expenseDetail.setId(IdUtils.getUUID32());
            expenseDetail.setState(Config.STATE_CURRENT);
            expenseDetail.setOperatorId(user.getId());
            expenseDetail.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(expenseDetail, conn);

        }
        // 更新
        else {
            FinanceExpenseDetailPO temp = new FinanceExpenseDetailPO();
            temp.setSid(expenseDetail.getSid());
            temp = MySQLDao.load(temp, FinanceExpenseDetailPO.class);
            temp.setState(Config.STATE_UPDATE);

            count = MySQLDao.update(temp, conn);

            if (count == 1) {
                expenseDetail.setSid(MySQLDao.getMaxSid("oa_financeexpensedetail", conn));
                expenseDetail.setState(Config.STATE_CURRENT);
                expenseDetail.setOperatorId(user.getId());
                expenseDetail.setOperateTime(TimeUtils.getNow());

                count = MySQLDao.insert(expenseDetail, conn);
            }
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    /**
     * 将数据对象修改为删除状态
     *
     * @param financeExpense 需要被修改的数据对象
     * @param user           当前操作用户对象
     * @param conn           数据连接
     * @return
     * @throws Exception
     */

    public int delete(FinanceExpensePO financeExpense, UserPO user, Connection conn) throws Exception {
        int count = 0;

        financeExpense = MySQLDao.load(financeExpense, FinanceExpensePO.class);
        financeExpense.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(financeExpense, conn);
        if (count == 1) {
            financeExpense.setSid(MySQLDao.getMaxSid("workflowaction_financeexpense", conn));
            financeExpense.setState(Config.STATE_DELETE);
            financeExpense.setOperateTime(TimeUtils.getNow());
            financeExpense.setOperatorId(user.getId());
            count = MySQLDao.insert(financeExpense, conn);
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    /**
     * 将项目数据对象修改为删除状态
     *
     * @param expenseDetail 需要被修改的数据对象
     * @param user          当前操作用户对象
     * @param conn          数据连接
     * @return
     * @throws Exception
     */

    public int deleteDetail(FinanceExpenseDetailPO expenseDetail, UserPO user, Connection conn) throws Exception {
        int count = 0;

        expenseDetail = MySQLDao.load(expenseDetail, FinanceExpenseDetailPO.class);
        expenseDetail.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(expenseDetail, conn);
        if (count == 1) {
            expenseDetail.setSid(MySQLDao.getMaxSid("oa_financeexpensedetail", conn));
            expenseDetail.setState(Config.STATE_DELETE);
            expenseDetail.setOperateTime(TimeUtils.getNow());
            expenseDetail.setOperatorId(user.getId());
            count = MySQLDao.insert(expenseDetail, conn);
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    /*
    * 修改：周海鸿
    * 时间：2015-7-17
    * 内容：获取费用报销打印类表*/
    public FinanceExpenseVO getPrintDate(String id) throws Exception{
        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("费用报销打印数据获取失败");
        }

        //构建sql语句
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append(" SELECT ");
        sbSQL.append(" 	* ");
        sbSQL.append(" FROM ");
        sbSQL.append(" 	( ");
        sbSQL.append(" 		SELECT ");
        sbSQL.append(" 			rl.CURRENTNODE currentNodeId, ");
        sbSQL.append(" 			rl.id routeListId, ");
        sbSQL.append(" 			rl.STATU currentStatus, ");
        sbSQL.append(" "+ BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append(" 			expense.sid, ");
        sbSQL.append(" 			expense.id AS id, ");
        sbSQL.append(" 			expense.submitterId, ");
        sbSQL.append(" 			expense.submitterTime, ");
        sbSQL.append(" 			u. NAME submitterName, ");
        sbSQL.append(" 			expense.time, ");
        sbSQL.append(" 			expense. MONTH, ");
        sbSQL.append(" 			expense.money, ");
        sbSQL.append(" 			expense. STATUS, ");
        sbSQL.append(" 			expense.operateTime, ");
        sbSQL.append(" 			expense.state AS state, ");
        sbSQL.append(" expense.accessoryNumber,");
        sbSQL.append(" 			po.DepartmentName, ");
        sbSQL.append(" 			po.DepartmentId ");
        sbSQL.append(" 		FROM ");
        sbSQL.append(" 			workflowaction_financeexpense expense, ");
        sbSQL.append(" 			system_user u, ");
        sbSQL.append(" 			system_positionuser pu, ");
        sbSQL.append(" 			system_position po, ");
        sbSQL.append(" 			workflow_routelist rl, ");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" 			oa_bizroute ob ");
        sbSQL.append(" 		WHERE ");
        sbSQL.append(" 			1 = 1 ");
        sbSQL.append(" 		AND u.state = 0 ");
        sbSQL.append(" 		AND expense.state = 0 ");
        sbSQL.append(" 		AND pu.states = 1 ");
        sbSQL.append(" 		AND u.id = expense.submitterId ");
        sbSQL.append(" 		AND po.id = pu.positionId ");
        sbSQL.append(" 		AND u.id = pu.userId ");
        sbSQL.append(" 		AND rl.YWID = expense.id ");
        sbSQL.append(" 		AND ob.Id_YWID = expense.Id ");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" 		AND rl.STATU IN (1) ");
        sbSQL.append(" 		UNION ");
        sbSQL.append(" 			SELECT DISTINCT ");
        sbSQL.append(" 				- 1 currentNodeId, ");
        sbSQL.append(" 				- 1 routeListId, ");
        sbSQL.append(" 				rl.STATU currentStatus, ");
        sbSQL.append(" "+ BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append(" 				expense.sid, ");
        sbSQL.append(" 				expense.id AS id, ");
        sbSQL.append(" 				expense.submitterId, ");
        sbSQL.append(" 			expense.submitterTime, ");
        sbSQL.append(" 				u. NAME submitterName, ");
        sbSQL.append(" 				expense.time, ");
        sbSQL.append(" 				expense. MONTH, ");
        sbSQL.append(" 				expense.money, ");
        sbSQL.append(" 				expense. STATUS, ");
        sbSQL.append(" 				expense.operateTime, ");
        sbSQL.append(" 				expense.state AS state, ");
        sbSQL.append(" expense.accessoryNumber,");
        sbSQL.append(" 				po.DepartmentName, ");
        sbSQL.append(" 				po.DepartmentId ");
        sbSQL.append(" 			FROM ");
        sbSQL.append(" 				workflowaction_financeexpense expense, ");
        sbSQL.append(" 				system_user u, ");
        sbSQL.append(" 				system_positionuser pu, ");
        sbSQL.append(" 				system_position po, ");
        sbSQL.append(" 				workflow_routelist rl, ");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" 				oa_bizroute ob ");
        sbSQL.append(" 			WHERE ");
        sbSQL.append(" 				1 = 1 ");
        sbSQL.append(" 			AND u.state = 0 ");
        sbSQL.append(" 			AND expense.state = 0 ");
        sbSQL.append(" 			AND u.id = expense.submitterId ");
        sbSQL.append(" 			AND po.id = pu.positionId ");
        sbSQL.append(" 			AND u.id = pu.userId ");
        sbSQL.append(" 			AND pu.states = 1 ");
        sbSQL.append(" 			AND rl.YWID = expense.id ");
        sbSQL.append(" 			AND ob.Id_YWID = expense.Id ");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" 			AND rl.STATU IN (5) ");
        sbSQL.append(" 			UNION ");
        sbSQL.append(" 				SELECT DISTINCT ");
        sbSQL.append(" 					1 currentNodeId, ");
        sbSQL.append(" 					0 routeListId, ");
        sbSQL.append(" 					- 1 currentStatus, ");
        sbSQL.append(" "+ BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append(" 					expense.sid, ");
        sbSQL.append(" 					expense.id AS id, ");
        sbSQL.append(" 					expense.submitterId, ");
        sbSQL.append(" 			expense.submitterTime, ");
        sbSQL.append(" 					u. NAME submitterName, ");
        sbSQL.append(" 					expense.time, ");
        sbSQL.append(" 					expense. MONTH, ");
        sbSQL.append(" 					expense.money, ");
        sbSQL.append(" 					expense. STATUS, ");
        sbSQL.append(" 					expense.operateTime, ");
        sbSQL.append(" 					expense.state AS state, ");
        sbSQL.append(" expense.accessoryNumber,");
        sbSQL.append(" 					po.DepartmentName, ");
        sbSQL.append(" 					po.DepartmentId ");
        sbSQL.append(" 				FROM ");
        sbSQL.append(" 					workflowaction_financeexpense expense, ");
        sbSQL.append(" 					system_user u, ");
        sbSQL.append(" 					system_positionuser pu, ");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" 					system_position po, ");
        sbSQL.append(" 					workflow_routelist rl, ");
        sbSQL.append(" 					oa_bizroute ob ");
        sbSQL.append(" 				WHERE ");
        sbSQL.append(" 					1 = 1 ");
        sbSQL.append(" 				AND u.state = 0 ");
        sbSQL.append(" 				AND expense.state = 0 ");
        sbSQL.append(" 				AND rl.STATU IN (1) ");
        sbSQL.append(" 				AND pu.states = 1 ");
        sbSQL.append(" 				AND u.id = expense.submitterId ");
        sbSQL.append(" 				AND po.id = pu.positionId ");
        sbSQL.append(" 				AND u.id = pu.userId ");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" 				AND ob.Id_YWID = expense.Id ");
        sbSQL.append(" 				AND expense.id NOT IN ( ");
        sbSQL.append(" 					SELECT ");
        sbSQL.append(" 						YWID ");
        sbSQL.append(" 					FROM ");
        sbSQL.append(" 						workflow_routelist rl ");
        sbSQL.append(" 					WHERE ");
        sbSQL.append(" 						1 = 1 ");
        sbSQL.append(" 				) ");
        sbSQL.append(" 	) b ");
        sbSQL.append(" WHERE ");
        sbSQL.append(" 	1 = 1 ");
        sbSQL.append(" AND id = '" + Database.encodeSQL(id) +"' ");


        //获取数据
        List li = MySQLDao.query(sbSQL.toString(), FinanceExpenseVO.class, null);



        return (FinanceExpenseVO) li.get(0);

    }
    /*获得费用报销项目数据*/
    public List<FinanceExpenseDetailPO> getDetailPrintDate(String id) throws Exception{
        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("费用报销项目数据获取失败");
        }

        //sql语句
        String sql = "SELECT * from oa_financeexpensedetail where ExpenseId='" + Database.encodeSQL(id) + "' and state = 0";

        //获取数据
        List li = MySQLDao.query(sql.toString(), FinanceExpenseDetailPO.class, null);


        return  li;

    }

    /**
     * 组装数据添加业务状态title
     *
     * @param pager 分页对象
     * @return
     *
     * 修改人：周海鸿：
     * 时间：2015-7-8
     *
     */
    public Pager insertTitle(Pager pager) throws Exception {

        //如果分页对象为null 直接返回
        if (null == pager) {
            return null;
        }

        //获取分页集合对象
        List list = pager.getData();
        List tempList = new ArrayList();//创建一个临时集合对象
        ClientApplications clientApplications = new ClientApplications();//创建一个读取文件对象
        //获取集合迭代对象
        Iterator iteratot = list.iterator();

        //迭代数据
        while (iteratot.hasNext()) {
            String title = null;
            FinanceExpenseVO financeExpenseVO = (FinanceExpenseVO) iteratot.next();

            try {
                //判断数据状态是都为完成状态
                if ("5".equals(financeExpenseVO.getCurrentStatus())) {
                    title = "已完成";
                }
                //非完成状态
                else {
                    //判断阶段是否是开始节点
                    if ("1" .equals( financeExpenseVO.getCurrentNodeId())) {
                        title = "申请";
                    }
                    //节点为中间节点
                    else {
                        //根据业务流编号与节点编号获取节点title
                        title = clientApplications.getNodeTitle(Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Finance.FinanceExpende")), Integer.valueOf(financeExpenseVO.getCurrentNodeId()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //添加标题
            financeExpenseVO.setCurrentNodeTitle(title);
            //添加到临时集合中
            tempList.add(financeExpenseVO);
        }


        Pager page = pager;//创建一个分页对象

        try {
            //封住分页数据
            page.set_state(pager.get_state());
            page.setCurrentPage(pager.getCurrentPage());
            page.setShowRowCount(page.getShowRowCount());
            page.setTotal(page.getTotal());
            page.setData(tempList);
        } catch (Exception e) {
            MyException.deal(e);
        }
        return page;
    }
}
