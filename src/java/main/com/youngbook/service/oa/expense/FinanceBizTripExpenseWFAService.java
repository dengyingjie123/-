package com.youngbook.service.oa.expense;

import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.expense.FinanceBizTripExpenseWFAPO;
import com.youngbook.entity.po.wf.BizRoutePO;
import com.youngbook.entity.vo.oa.expense.FinanceBizTripExpenseWFAVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015-5-19.
 */
public class FinanceBizTripExpenseWFAService extends BaseService {

    /**
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 对业务表进行添更新操作
     * 返回影响行数
     * 添加或修改数据，并修改数据状态
     *
     * @param financeBizTripExpenseWFA
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */

    //2015-60-17 周海鸿 添加实现修改的时候计算总金额
    public int insertOrUpdate (FinanceBizTripExpenseWFAPO financeBizTripExpenseWFA, FinanceBizTripExpenseWFAVO financeBizTripExpenseWFAVO, UserPO user, Connection conn) throws Exception {
        //判读数据有效性
        // 数据操作实体类是否为空
        if ( financeBizTripExpenseWFA == null ) {
            throw new Exception ("差旅费据提交失败");
        }

        //当前用户操作类是否为空
        if ( user == null ) {
            throw new Exception ("当前用户失效");
        }

        //当前数据链接是否空
        if ( conn == null ) {
            throw new Exception ("链接错误");
        }

        int count = 0;
        // 新增
        if ( financeBizTripExpenseWFA.getId ().equals ("") ) {
            financeBizTripExpenseWFA.setSid (MySQLDao.getMaxSid ("OA_FinanceBizTripExpenseWFA", conn));
            financeBizTripExpenseWFA.setId (IdUtils.getUUID32 ());
            financeBizTripExpenseWFA.setState (Config.STATE_CURRENT);
            financeBizTripExpenseWFA.setOperatorId (user.getId ());
            financeBizTripExpenseWFA.setOperateTime (TimeUtils.getNow ());

            //添加
            count = MySQLDao.insert (financeBizTripExpenseWFA, conn);

            /**
             * 2015 -6 -13 小周
             * 当数据添加成功时 向流程表中添加数据
             */
           /* 修改人：周海鸿
            * 修改时间：2014-7-10
            * 修改时间： 添加注释，添加业务申请人
            */
            // 返回影响行数为1

            if (count == 1) {
                count = BizRouteService.insertOrUpdate(financeBizTripExpenseWFA.getId(), financeBizTripExpenseWFAVO.getControlString3(),  financeBizTripExpenseWFAVO.getControlString1(), financeBizTripExpenseWFAVO.getControlString2(),
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Finance.FinanceTripExpense")), true, user, conn);
            }
        }
        // 更新
        else {
            FinanceBizTripExpenseWFAPO temp = new FinanceBizTripExpenseWFAPO ();
            temp.setSid (financeBizTripExpenseWFA.getSid ());
            temp = MySQLDao.load (temp, FinanceBizTripExpenseWFAPO.class);
            temp.setState (Config.STATE_UPDATE);

            count = MySQLDao.update (temp, conn);
            if ( count == 1 ) {
                financeBizTripExpenseWFA.setSid (MySQLDao.getMaxSid ("OA_FinanceBizTripExpenseWFA", conn));
                financeBizTripExpenseWFA.setState (Config.STATE_CURRENT);
                financeBizTripExpenseWFA.setOperatorId (user.getId ());
                financeBizTripExpenseWFA.setOperateTime (TimeUtils.getNow ());
                //计算总金额
                FinanceBizTripExpenseItemService fs = new FinanceBizTripExpenseItemService ();
                financeBizTripExpenseWFA.setMoney (Double.parseDouble (fs.getMoneys (financeBizTripExpenseWFA.getId ())));
                count = MySQLDao.insert (financeBizTripExpenseWFA, conn);

                if (count == 1) {
                    count = BizRouteService.insertOrUpdate(financeBizTripExpenseWFA.getId(), financeBizTripExpenseWFAVO.getControlString3(),financeBizTripExpenseWFAVO.getControlString1(), financeBizTripExpenseWFAVO.getControlString2(),
                            Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Finance.FinanceTripExpense")), false, user, conn);
                }
            }
        }
        if ( count != 1 ) {
            throw new Exception ("数据库异常");
        }
        return count;
    }

    /**
     * 加载数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public FinanceBizTripExpenseWFAPO loadFinanceBizTripExpenseWFAPO (String id) throws Exception {
        if ( StringUtils.isEmpty (id) ) {
            MyException.newInstance ("差旅费id获取失败");
        }

        //设置查询数据
        FinanceBizTripExpenseWFAPO po = new FinanceBizTripExpenseWFAPO ();
        po.setId (id);
        po.setState (Config.STATE_CURRENT);

        //获取数据源
        po = MySQLDao.load (po, FinanceBizTripExpenseWFAPO.class);
        return po;
    }

    /**
     * 删除
     *
     * @param financeBizTripExpenseWFA
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete (FinanceBizTripExpenseWFAPO financeBizTripExpenseWFA, UserPO user, Connection conn) throws Exception {
        int count = 0;

        financeBizTripExpenseWFA.setState (Config.STATE_CURRENT);
        financeBizTripExpenseWFA = MySQLDao.load (financeBizTripExpenseWFA, FinanceBizTripExpenseWFAPO.class);
        financeBizTripExpenseWFA.setState (Config.STATE_UPDATE);

        count = MySQLDao.update (financeBizTripExpenseWFA, conn);
        if ( count == 1 ) {

            financeBizTripExpenseWFA.setSid (MySQLDao.getMaxSid ("OA_FinanceBizTripExpenseWFA", conn));
            financeBizTripExpenseWFA.setState (Config.STATE_DELETE);
            financeBizTripExpenseWFA.setOperateTime (TimeUtils.getNow ());
            financeBizTripExpenseWFA.setOperatorId (user.getId ());

            count = MySQLDao.insert (financeBizTripExpenseWFA, conn);
        }

        if ( count != 1 ) {
            throw new Exception ("删除失败");
        }
        return count;
    }

    /**
     * 获取数据分页列表
     *
     * @param financeBizTripExpenseWFA 需要封装的实体类
     * @param conditions               范围查询条件对象
     * @param request                  客户请求对象
     * @return
     * @throws Exception *
     * 修改人:周海鸿
     * 事件：2015-7-8
     * 内容：修改SQL语句
     */
    public Pager list (FinanceBizTripExpenseWFAVO financeBizTripExpenseWFA, HttpServletRequest request, UserPO user, List<KVObject> conditions) throws Exception {

        //判断请求对象是否为空
        if ( request == null ) {
            throw new Exception ("当前请求无效");
        }

        //查询类型
        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //添加数据排序条件

        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append (" SELECT");
        sbSQL.append (" rl.CURRENTNODE currentNodeId,");
        sbSQL.append (" rl.id routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" of.sid,");
        sbSQL.append (" of.id AS id,");
        sbSQL.append (" of.ReimburseId,");
        sbSQL.append (" of.ReimburseTime,");
        sbSQL.append (" of.OrgId,");
        sbSQL.append (" of.UserId,");
        sbSQL.append (" of.userNames,");
        sbSQL.append (" u1.name reimburseName,");
        sbSQL.append (" of.time,");
        sbSQL.append (" of.money,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append ("   	ob.SubmitterId,");
        sbSQL.append ("   	ob.WORKFLOWID,");
        sbSQL.append (" of.STATUS,");
        sbSQL.append (" of.operateTime,");
        sbSQL.append (" of.state AS state,");
        sbSQL.append (" of.accessoryNumber");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_financebiztripexpensewfa of,");
        sbSQL.append (" system_user u1,");

        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append (" WHERE");
        sbSQL.append (" 1 = 1");
        sbSQL.append (" AND u1.state = 0");
        
        sbSQL.append (" AND of.state = 0");
        sbSQL.append (" AND of.ReimburseId = u1.id");
        sbSQL.append (" AND rl.YWID = of.id");
        sbSQL.append (" AND ob.Id_YWID=of.Id");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append (" AND rl.STATU IN (1)");
        sbSQL.append (" UNION");
        sbSQL.append (" SELECT DISTINCT");
        sbSQL.append (" -1 currentNodeId,");
        sbSQL.append (" -1 routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" of.sid,");
        sbSQL.append (" of.id AS id,");
        sbSQL.append (" of.ReimburseId,");
        sbSQL.append (" of.ReimburseTime,");
        sbSQL.append (" of.OrgId,");
        sbSQL.append (" of.UserId,");
        sbSQL.append (" of.userNames,");
        
        sbSQL.append (" u1.name reimburseName,");
        sbSQL.append (" of.time,");
        sbSQL.append (" of.money,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName   as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append ("  ob.SubmitterId,");
        sbSQL.append ("  ob.WORKFLOWID,");
        sbSQL.append (" of.STATUS,");
        sbSQL.append (" of.operateTime,");
        sbSQL.append (" of.state AS state,");
        sbSQL.append (" of.accessoryNumber");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_financebiztripexpensewfa of,");
        sbSQL.append (" system_user u1,");

        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append (" WHERE ");
        sbSQL.append (" 1 = 1");
        sbSQL.append (" AND u1.state = 0");
        sbSQL.append (" AND of.state = 0");
        sbSQL.append (" AND of.ReimburseId = u1.id");
        sbSQL.append (" AND rl.YWID = of.id");
        sbSQL.append (" AND ob.Id_YWID=of.Id");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");

        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");
        sbSQL.append (" UNION");
        sbSQL.append (" SELECT DISTINCT");
        sbSQL.append (" 1 currentNodeId,");
        sbSQL.append (" 0 routeListId,");
        sbSQL.append (" -1 currentStatus,");
        sbSQL.append (" of.sid,");
        sbSQL.append (" of.id AS id,");
        sbSQL.append (" of.ReimburseId,");
        sbSQL.append (" of.ReimburseTime,");
        sbSQL.append (" of.OrgId,");
        sbSQL.append (" of.UserId,");
        sbSQL.append (" of.userNames,");
        sbSQL.append (" u1.name reimburseName,");
        sbSQL.append (" of.time,");
        sbSQL.append (" of.money,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" ob.SubmitterId,");
        sbSQL.append (" ob.WORKFLOWID,");
        sbSQL.append (" of.STATUS,");
        sbSQL.append (" of.operateTime,");
        sbSQL.append (" of.state AS state,");
        sbSQL.append (" of.accessoryNumber");
        sbSQL.append (" FROM ");
        sbSQL.append (" oa_financebiztripexpensewfa of,");
        sbSQL.append (" system_user u1,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append (" WHERE ");
        sbSQL.append (" 1 = 1");
        sbSQL.append (" AND u1.state = 0");
        sbSQL.append (" AND of.state = 0");
        sbSQL.append (" AND rl.STATU IN (1)");
        sbSQL.append (" AND of.ReimburseId = u1.id");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append (" AND ob.Id_YWID=of.Id");
        sbSQL.append (" AND of.id NOT IN(");
        sbSQL.append (" SELECT");
        sbSQL.append (" YWID");
        sbSQL.append (" FROM");
        sbSQL.append (" workflow_routelist rl");
        sbSQL.append (" where ");
        sbSQL.append (" 1=1");
        sbSQL.append (" )");

        //添加查询条件
        conditions.add (new KVObject ("SubmitterId = ", "'" + Database.encodeSQL (user.getId ()) + "'"));
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取数据
        Pager pager = Pager.query (sbSQL.toString (), financeBizTripExpenseWFA, conditions, request, queryType);

        //返回格式化数据
        return insertTitle (pager);
    }

    /**
     * 获取等待审核数据列表
     *
     * @param financeBizTripExpenseWFA 需要封装的实体类
     * @param conditions               范围查询条件对象
     * @param request                  客户请求对象
     * @return
     * @throws Exception *
     * 修改人:周海鸿
     * 事件：2015-7-8
     * 内容：修改SQL语句
     */
    public Pager Waitlist (FinanceBizTripExpenseWFAVO financeBizTripExpenseWFA, HttpServletRequest request, UserPO user, List<KVObject> conditions) throws Exception {

        //判断请求对象是否为空
        if ( request == null ) {
            throw new Exception ("当前请求无效");
        }

        //查询类型
        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);


        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append (" SELECT");
        sbSQL.append (" rl.CURRENTNODE currentNodeId,");
        sbSQL.append (" rl.id routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" of.sid,");
        sbSQL.append (" of.id AS id,");
        sbSQL.append (" of.ReimburseId,");
        sbSQL.append (" of.ReimburseTime,");
        sbSQL.append (" of.OrgId,");
        sbSQL.append (" of.UserId,");
        sbSQL.append (" of.userNames,");
        sbSQL.append (" u1.name reimburseName,");
        sbSQL.append (" of.time,");
        sbSQL.append (" of.money,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName   as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" of.STATUS,");
        sbSQL.append (" of.operateTime,");
        sbSQL.append (" of.state AS state,");
        sbSQL.append (" of.accessoryNumber");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_financebiztripexpensewfa of,");
        sbSQL.append (" system_user u1,");
        sbSQL.append (" system_department sd1,");
        sbSQL.append (" system_department sd2,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append (" WHERE");
        sbSQL.append (" 1 = 1");
        sbSQL.append (" AND u1.state = 0");
        sbSQL.append (" AND of.state = 0");
        sbSQL.append (" AND of.ReimburseId = u1.id");
        sbSQL.append (" AND rl.YWID = of.id");
        sbSQL.append (" AND ob.Id_YWID=of.Id");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append (" AND ob.controlString1  in (" + BizRouteConfig.getDepartmentParentId (user.getId ()) + ")");
        sbSQL.append (" AND rl.STATU IN (1)");
        sbSQL.append ("   AND rl.CURRENTNODE IN (");
        sbSQL.append ("   	SELECT");
        sbSQL.append ("   		NODEID");
        sbSQL.append ("   	FROM");
        sbSQL.append ("   		workflow_participant p,");
        sbSQL.append ("   		workflow_role r");
        sbSQL.append ("   	WHERE");
        sbSQL.append ("   		(p.WORKFLOWID = " + Config.getSystemVariable("WORKDLOWID.Expense.FinanceBizTripExpenseWFA") + " OR p.WORKFLOWID = " + Config.getSystemVariable("WORKFLOWID.Finance.FinanceTripExpense") + ")");
        sbSQL.append ("   	AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");
        sbSQL.append ("   	AND r.USERLIST LIKE '%|" + Database.encodeSQL (user.getId ()) + "|%'");
        sbSQL.append ("   )");
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取数据
        Pager pager = Pager.query (sbSQL.toString (), financeBizTripExpenseWFA, conditions, request, queryType);

        //返回格式化数据
        return insertTitle (pager);
    }

    /**
     * 获取已完成列表
     *
     * @param financeBizTripExpenseWFA 需要封装的实体类
     * @param conditions               范围查询条件对象
     * @param request                  客户请求对象
     * @return
     * @throws Exception 修改人:周海鸿
     *                   事件：2015-7-8
     *                   内容：修改SQL语句
     */
    public Pager Participantlist (FinanceBizTripExpenseWFAVO financeBizTripExpenseWFA, HttpServletRequest request, UserPO user, List<KVObject> conditions) throws Exception {

        //判断请求对象是否为空
        if ( request == null ) {
            throw new Exception ("当前请求无效");
        }

        //查询类型
        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);


        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append (" SELECT");
        sbSQL.append (" 	*");
        sbSQL.append (" FROM");
        sbSQL.append (" 	(");
        sbSQL.append (" SELECT");
        sbSQL.append (" rl.CURRENTNODE currentNodeId,");
        sbSQL.append (" rl.id routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" of.sid,");
        sbSQL.append (" of.id AS id,");
        sbSQL.append (" of.ReimburseId,");
        sbSQL.append (" of.ReimburseTime,");
        sbSQL.append (" of.OrgId,");
        sbSQL.append (" of.UserId,");
        sbSQL.append (" u1.name reimburseName,");
        sbSQL.append (" of.time,");
        sbSQL.append (" of.money,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName   as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" of.STATUS,");
        sbSQL.append (" of.operateTime,");
        sbSQL.append (" of.state AS state,");
        sbSQL.append (" of.accessoryNumber");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_financebiztripexpensewfa of,");
        sbSQL.append (" system_user u1,");
        sbSQL.append (" system_department sd1,");
        sbSQL.append (" system_department sd2,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append (" WHERE");
        sbSQL.append (" 1 = 1");
        sbSQL.append (" AND u1.state = 0");
        
        sbSQL.append (" AND of.state = 0");
        sbSQL.append (" AND of.ReimburseId = u1.id");
        sbSQL.append (" AND rl.YWID = of.id");
        sbSQL.append (" AND ob.Id_YWID=of.Id");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append (" AND rl.STATU IN (1)");
        sbSQL.append (" UNION");
        sbSQL.append (" SELECT DISTINCT");
        sbSQL.append (" -1 currentNodeId,");
        sbSQL.append (" -1 routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" of.sid,");
        sbSQL.append (" of.id AS id,");
        sbSQL.append (" of.ReimburseId,");
        sbSQL.append (" of.ReimburseTime,");
        sbSQL.append (" of.OrgId,");
        sbSQL.append (" of.UserId,");
        sbSQL.append (" u1.name reimburseName,");
        sbSQL.append (" of.time,");
        sbSQL.append (" of.money,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName   as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" of.STATUS,");
        sbSQL.append (" of.operateTime,");
        sbSQL.append (" of.state AS state,");
        sbSQL.append (" of.accessoryNumber");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_financebiztripexpensewfa of,");
        sbSQL.append (" system_user u1,");
        sbSQL.append (" system_department sd1,");
        sbSQL.append (" system_department sd2,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append (" WHERE ");
        sbSQL.append (" 1 = 1");
        sbSQL.append (" AND u1.state = 0");
        sbSQL.append (" AND of.state = 0");
        sbSQL.append (" AND of.ReimburseId = u1.id");
        sbSQL.append (" AND rl.YWID = of.id");
        sbSQL.append (" AND ob.Id_YWID=of.Id");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");

        sbSQL.append (" 	) t");
        sbSQL.append (" WHERE");
        sbSQL.append (" 	1 = 1");
        sbSQL.append (" AND t.Id IN (");
        sbSQL.append (" 	SELECT DISTINCT");
        sbSQL.append (" 		YWID");
        sbSQL.append (" 	FROM");
        sbSQL.append (" 		workflow_action");
        sbSQL.append (" 	WHERE");
        sbSQL.append(" 		1 = 1");
        //添加业务编号
        sbSQL.append("  AND	(WORKFLOWID = " + Config.getSystemVariable("WORKDLOWID.Expense.FinanceBizTripExpenseWFA") + " OR WORKFLOWID = " + Config.getSystemVariable("WORKFLOWID.Finance.FinanceTripExpense") + ")");
        sbSQL.append (" 	AND PARTICIPANT = '" + Database.encodeSQL (user.getId ()) + "'");
        sbSQL.append (" ) ");

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //添加数据排序条件
        Pager pager = Pager.query (sbSQL.toString (), financeBizTripExpenseWFA, conditions, request, queryType);

        //返回格式化数据
        return insertTitle (pager);
    }

    /**
     * 组装数据添加业务状态title
     *
     * @param pager 分页对象
     * @return
     */
    public Pager insertTitle (Pager pager) throws Exception {
        //如果分页对象为null 直接返回
        if ( null == pager ) {
            return null;
        }

        //获取分页集合对象
        List list = pager.getData ();
        List tempList = new ArrayList ();//创建一个临时集合对象
        ClientApplications clientApplications = new ClientApplications ();//创建一个读取文件对象
        //获取集合迭代对象
        Iterator iteratot = list.iterator ();

        //迭代数据
        while ( iteratot.hasNext () ) {
            String title = null;
            FinanceBizTripExpenseWFAVO financeBizTripExpenseWFA = (FinanceBizTripExpenseWFAVO) iteratot.next ();

            try {
                //判断数据状态是都为完成状态
                if ( "5".equals (financeBizTripExpenseWFA.getCurrentStatus ()) ) {
                    title = "已完成";
                }else if ("3".equals(financeBizTripExpenseWFA.getCurrentStatus())) {
                    title = "中止";
                }
                //非完成状态
                else {
                    //判断阶段是否是开始节点
                    if ( "1".equals (financeBizTripExpenseWFA.getCurrentNodeId ()) ) {
                        title = "申请";
                    }
                    //节点为中间节点
                    else {
                        //根据业务流编号与节点编号获取节点title
                        title = clientApplications.getNodeTitle (Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Finance.FinanceTripExpense")), Integer.valueOf (financeBizTripExpenseWFA.getCurrentNodeId ()));
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace ();
            }

            //添加标题
            financeBizTripExpenseWFA.setCurrentNodeTitle (title);

            //添加到临时集合中
            tempList.add (financeBizTripExpenseWFA);
        }


        Pager page = pager;//创建一个分页对象

        try {
            //封住分页数据
            page.set_state (pager.get_state ());
            page.setCurrentPage (pager.getCurrentPage ());
            page.setShowRowCount (page.getShowRowCount ());
            page.setTotal (page.getTotal ());
            page.setData (tempList);
        } catch ( Exception e ) {
            MyException.deal (e);
        }
        return page;
    }

    /*
    * 修改：周海鸿
    * 时间：2015-7-17
    * 内容：获取差旅费报销打印类表*/
    public FinanceBizTripExpenseWFAVO getPrintDate (String id) throws Exception {
        //判断数据有效性
        if ( "".equals (id) || null == id ) {
            throw new Exception ("差旅费打印数据获取失败");
        }

        //构建sql语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append (" SELECT  ");
        sbSQL.append (" 	*  ");
        sbSQL.append (" FROM  ");
        sbSQL.append (" 	(  ");
        sbSQL.append (" 		SELECT  ");
        sbSQL.append (" 			rl.CURRENTNODE currentNodeId,  ");
        sbSQL.append (" 			rl.id routeListId,  ");
        sbSQL.append (" 			rl.STATU currentStatus,  ");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName   as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" 			of.sid,  ");
        sbSQL.append (" 			of.id AS id,  ");
        sbSQL.append (" 			of.ReimburseId,  ");
        sbSQL.append (" 			of.ReimburseTime,  ");
        sbSQL.append (" 			of.OrgId,  ");
        sbSQL.append (" 			of.UserId,  ");
        sbSQL.append (" of.userNames,");
        sbSQL.append (" 			of.time,  ");
        sbSQL.append (" 			of.money,  ");
        sbSQL.append (" 			of.`Comment`,  ");
        sbSQL.append (" of.accessoryNumber,");
        sbSQL.append (" 			u1. NAME reimburseName  ");
        sbSQL.append (" 		FROM  ");
        sbSQL.append (" 			oa_financebiztripexpensewfa of,  ");
        sbSQL.append (" 			system_user u1,  ");
        sbSQL.append (" 			system_department sd1,  ");
        sbSQL.append (" 			system_department sd2,  ");
        sbSQL.append (" 			workflow_routelist rl,  ");
        sbSQL.append (" 			oa_bizroute ob  ");
        sbSQL.append (" 		WHERE  ");
        sbSQL.append (" 			1 = 1  ");
        sbSQL.append (" 		AND u1.state = 0  ");
        sbSQL.append (" 		AND of.state = 0  ");
        sbSQL.append (" 		AND of.ReimburseId = u1.id  ");
        sbSQL.append (" 		AND rl.YWID = of.id  ");
        sbSQL.append (" 		AND ob.Id_YWID = of.Id  ");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append (" 		AND rl.STATU IN (1)  ");
        sbSQL.append (" 		UNION  ");
        sbSQL.append (" 			SELECT DISTINCT  ");
        sbSQL.append (" 				- 1 currentNodeId,  ");
        sbSQL.append (" 				- 1 routeListId,  ");
        sbSQL.append (" 				rl.STATU currentStatus,  ");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName   as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" 				of.sid,  ");
        sbSQL.append (" 				of.id AS id,  ");
        sbSQL.append (" 				of.ReimburseId,  ");
        sbSQL.append (" 				of.ReimburseTime,  ");
        sbSQL.append (" 				of.OrgId,  ");
        sbSQL.append (" 				of.UserId,  ");
        sbSQL.append (" of.userNames,");
        sbSQL.append (" 				of.time,  ");
        sbSQL.append (" 				of.money,  ");
        sbSQL.append (" 				of.`Comment`,  ");
        sbSQL.append (" of.accessoryNumber,");
        sbSQL.append (" 				u1. NAME reimburseName ");
        sbSQL.append (" 			FROM  ");
        sbSQL.append (" 				oa_financebiztripexpensewfa of,  ");
        sbSQL.append (" 				system_user u1,  ");
        sbSQL.append (" 				system_department sd1,  ");
        sbSQL.append (" 				system_department sd2,  ");
        sbSQL.append (" 				workflow_routelist rl,  ");
        sbSQL.append (" 				oa_bizroute ob  ");
        sbSQL.append (" 			WHERE  ");
        sbSQL.append (" 				1 = 1  ");
        sbSQL.append (" 			AND u1.state = 0  ");
        sbSQL.append (" 			AND of.state = 0  ");
        sbSQL.append (" 			AND of.ReimburseId = u1.id  ");
        sbSQL.append (" 			AND rl.YWID = of.id  ");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append (" 			AND ob.Id_YWID = of.Id  ");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");
        sbSQL.append (" 			UNION  ");
        sbSQL.append (" 				SELECT DISTINCT  ");
        sbSQL.append (" 					1 currentNodeId,  ");
        sbSQL.append (" 					0 routeListId,  ");
        sbSQL.append (" 					- 1 currentStatus,  ");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName   as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" 					of.sid,  ");
        sbSQL.append (" 					of.id AS id,  ");
        sbSQL.append (" 					of.ReimburseId,  ");
        sbSQL.append (" 					of.ReimburseTime,  ");
        sbSQL.append (" 					of.OrgId,  ");
        sbSQL.append (" 					of.UserId,  ");
        sbSQL.append (" of.userNames,");
        sbSQL.append (" 					of.time,  ");
        sbSQL.append (" 					of.money,  ");
        sbSQL.append (" 					of.`Comment`,  ");
        sbSQL.append (" of.accessoryNumber,");
        sbSQL.append (" 					u1. NAME reimburseName  ");
        sbSQL.append (" 				FROM  ");
        sbSQL.append (" 					oa_financebiztripexpensewfa of,  ");
        sbSQL.append (" 					system_user u1,  ");
        sbSQL.append (" 				system_department sd1,  ");
        sbSQL.append (" 				system_department sd2,  ");
        sbSQL.append (" 					workflow_routelist rl,  ");
        sbSQL.append (" 					oa_bizroute ob  ");
        sbSQL.append (" 				WHERE  ");
        sbSQL.append (" 					1 = 1  ");
        sbSQL.append (" 				AND u1.state = 0  ");
        sbSQL.append (" 				AND of.state = 0  ");
        sbSQL.append (" 				AND rl.STATU IN (1)  ");
        sbSQL.append (" 				AND of.ReimburseId = u1.id  ");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append (" 				AND ob.Id_YWID = of.Id  ");
        sbSQL.append (" 				AND of.id NOT IN (  ");
        sbSQL.append (" 					SELECT  ");
        sbSQL.append (" 						YWID  ");
        sbSQL.append (" 					FROM  ");
        sbSQL.append (" 						workflow_routelist rl  ");
        sbSQL.append (" 					WHERE  ");
        sbSQL.append (" 						1 = 1  ");
        sbSQL.append (" 				)  ");
        sbSQL.append (" 	) d  ");
        sbSQL.append (" WHERE  ");
        sbSQL.append (" 	1 = 1  ");
        sbSQL.append (" AND id = '" + Database.encodeSQL (id) + "'  ");


        //获取数据
        List li = MySQLDao.query (sbSQL.toString (), FinanceBizTripExpenseWFAVO.class, null);



        return (FinanceBizTripExpenseWFAVO) li.get (0);

    }
}