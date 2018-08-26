package com.youngbook.service.oa.hr;

/**
 * Created by haihong on 2015/6/26.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.hr.HRLeavePO;
import com.youngbook.entity.po.wf.BizRoutePO;
import com.youngbook.entity.vo.oa.hr.HRLeaveVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;
import org.apache.struts2.ServletActionContext;
import org.junit.runner.Request;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 创建一个HRLeaveServlet 类 继承BaseServlet类
 *
 * @author Codemaker
 */

public class HRLeaveService extends BaseService {
    private static final String WORKFLOW = "WORKFLOWID.HR.Leave";
    private static final String WORKFLOW_NEW = "WORKFLOWID.HR.LeaveNew";

    /**
     * 我申请的请假休假数据
     *
     * @param hRLeaveVO  数据封装实体类
     * @param conditions 条件查询集合
     * @return 数据分页对象
     * @throws Exception
     */
    /*修改：周海鸿
    * 时间：2015-7-17
    * 内容：更改SQL语句添加人力审核*/
    public Pager listHRLeave(HRLeaveVO hRLeaveVO, UserPO user, List<KVObject> conditions) throws Exception {

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("	rl.CURRENTNODE currentNodeId,");
        sbSQL.append("	rl.id routeListId,");
        sbSQL.append("	rl.STATU currentStatus,");
        sbSQL.append("	oh.Sid,");
        sbSQL.append("	oh.Id,");
        sbSQL.append("	oh.State,");
        sbSQL.append("	oh.OperatorId,");
        sbSQL.append("	oh.OperateTime,");
        sbSQL.append("	oh.ApplicantId,");
        sbSQL.append("	sus.`name` AS oh_applicationName,");
        sbSQL.append("	oh.LeaveTypeId,");
        sbSQL.append("	leaveType.v AS leaveTypeName,");
        sbSQL.append("	oh.OtherTypeDescription,");
        sbSQL.append("	oh.Days,");
        sbSQL.append("	oh.WhereToLeave,");
        sbSQL.append("	oh.StartTime,");
        sbSQL.append("	oh.EndTime,");
        sbSQL.append("	oh.HandoverName,");
        sbSQL.append("	oh.Reason,");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	ob.SubmitterId,");
        sbSQL.append("	ob.WORKFLOWID");
        sbSQL.append(" FROM");
        sbSQL.append("	oa_hrleave oh,");
        sbSQL.append("	system_user sus,");
        sbSQL.append("	system_kv leaveType,");
        sbSQL.append("	workflow_routelist rl,");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append("	oa_bizroute ob");
        sbSQL.append(" WHERE");
        sbSQL.append("	1 = 1");
        sbSQL.append(" AND oh.state = 0");
        sbSQL.append(" AND sus.state = 0");
        sbSQL.append(" AND oh.ApplicantId = sus.id");
        sbSQL.append(" AND leaveType.k = LeaveTypeId");
        sbSQL.append(" AND leaveType.GroupName = 'oa_hrleave_hrleaveType'");
        sbSQL.append(" AND ob.Id_YWID = oh.Id");
        sbSQL.append(" AND rl.YWID = oh.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" UNION");
        sbSQL.append("	SELECT");
        sbSQL.append("		1 currentNodeId,");
        sbSQL.append("		0 routeListId,");
        sbSQL.append("		rl.STATU currentStatus,");
        sbSQL.append("		oh.Sid,");
        sbSQL.append("		oh.Id,");
        sbSQL.append("		oh.State,");
        sbSQL.append("		oh.OperatorId,");
        sbSQL.append("		oh.OperateTime,");
        sbSQL.append("		oh.ApplicantId,");
        sbSQL.append("		sus.`name` AS oh_applicationName,");
        sbSQL.append("		oh.LeaveTypeId,");
        sbSQL.append("		leaveType.v AS leaveTypeName,");
        sbSQL.append("		oh.OtherTypeDescription,");
        sbSQL.append("		oh.Days,");
        sbSQL.append("		oh.WhereToLeave,");
        sbSQL.append("		oh.StartTime,");
        sbSQL.append("		oh.EndTime,");
        sbSQL.append("		oh.HandoverName,");
        sbSQL.append("		oh.Reason,");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("		ob.SubmitterId,");
        sbSQL.append("		ob.WORKFLOWID");
        sbSQL.append("	FROM");
        sbSQL.append("		oa_hrleave oh,");
        sbSQL.append("		system_user sus,");
        sbSQL.append("		system_kv leaveType,");
        sbSQL.append("		workflow_routelist rl,");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append("		oa_bizroute ob");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        sbSQL.append("	AND oh.state = 0");
        sbSQL.append("	AND sus.state = 0");
        sbSQL.append("	AND oh.ApplicantId = sus.id");
        sbSQL.append("	AND leaveType.k = LeaveTypeId");
        sbSQL.append("	AND leaveType.GroupName = 'oa_hrleave_hrleaveType'");
        sbSQL.append("	AND ob.Id_YWID = oh.Id");
        sbSQL.append("	AND rl.YWID = oh.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");

        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");
        sbSQL.append("	UNION");
        sbSQL.append("		SELECT");
        sbSQL.append("			1 currentNodeId,");
        sbSQL.append("			0 routeListId,");
        sbSQL.append("			- 1 currentStatus,");
        sbSQL.append("			oh.Sid,");
        sbSQL.append("			oh.Id,");
        sbSQL.append("			oh.State,");
        sbSQL.append("			oh.OperatorId,");
        sbSQL.append("			oh.OperateTime,");
        sbSQL.append("			oh.ApplicantId,");
        sbSQL.append("			sus.`name` AS oh_applicationName,");
        sbSQL.append("			oh.LeaveTypeId,");
        sbSQL.append("			leaveType.V AS leaveTypeName,");
        sbSQL.append("			oh.OtherTypeDescription,");
        sbSQL.append("			oh.Days,");
        sbSQL.append("			oh.WhereToLeave,");
        sbSQL.append("			oh.StartTime,");
        sbSQL.append("			oh.EndTime,");
        sbSQL.append("			oh.HandoverName,");
        sbSQL.append("			oh.Reason,");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("			ob.SubmitterId,");
        sbSQL.append("			ob.WORKFLOWID");
        sbSQL.append("		FROM");
        sbSQL.append("			oa_hrleave oh,");
        sbSQL.append("			system_user sus,");
        sbSQL.append("			system_kv leaveType,");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append("			oa_bizroute ob");
        sbSQL.append("		WHERE");
        sbSQL.append("			1 = 1");
        sbSQL.append("		AND oh.state = 0");
        sbSQL.append("		AND sus.state = 0");
        sbSQL.append("		AND oh.ApplicantId = sus.id");
        sbSQL.append("		AND leaveType.k = LeaveTypeId");
        sbSQL.append("		AND leaveType.GroupName = 'oa_hrleave_hrleaveType'");
        sbSQL.append("		AND ob.Id_YWID = oh.Id");
        sbSQL.append("      AND sd1.id = ob.controlString1 ");
        sbSQL.append("      AND sd2.id = ob.controlString2 ");
        sbSQL.append("		AND oh.id not IN (");
        sbSQL.append("			SELECT");
        sbSQL.append("				YWID");
        sbSQL.append("			FROM");
        sbSQL.append("				workflow_routelist rl");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("		)");

        //添加用户条件
        conditions.add(new KVObject("SubmitterId = ","'"+Database.encodeSQL(user.getId())+"'"));
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        HttpServletRequest request = ServletActionContext.getRequest();
        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), hRLeaveVO, conditions, request, queryType);

        return insertTitle(pager);
    }

    /**
     * 我参与过的请假休假数据
     *
     * @param hRLeaveVO
     * @param conditions
     * @return
     * @throws Exception
     */
    /*修改：周海鸿
    * 时间：2015-7-17
    * 内容：更改SQL语句添加人力审核*/
    public Pager listHRLeaveParticipant(HRLeaveVO hRLeaveVO, UserPO user, List<KVObject> conditions) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append(" 	*");
        sbSQL.append(" FROM");
        sbSQL.append(" 	(");
        sbSQL.append(" 		SELECT");
        sbSQL.append(" 			rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" 			rl.id routeListId,");
        sbSQL.append(" 			rl.STATU currentStatus,");
        sbSQL.append(" 			oh.Sid,");
        sbSQL.append(" 			oh.Id,");
        sbSQL.append(" 			oh.State,");
        sbSQL.append(" 			oh.OperatorId,");
        sbSQL.append(" 			oh.OperateTime,");
        sbSQL.append(" 			oh.ApplicantId,");
        sbSQL.append(" 			sus.`name` AS oh_applicationName,");
        sbSQL.append(" 			oh.LeaveTypeId,");
        sbSQL.append(" 			leaveType.v AS leaveTypeName,");
        sbSQL.append(" 			oh.OtherTypeDescription,");
        sbSQL.append(" 			oh.Days,");
        sbSQL.append(" 			oh.WhereToLeave,");
        sbSQL.append(" 			oh.StartTime,");
        sbSQL.append(" 			oh.EndTime,");
        sbSQL.append(" 			oh.HandoverName,");
        sbSQL.append(" 			oh.Reason,");
        sbSQL.append("  "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append(" 	ob. WORKFLOWID");
        sbSQL.append(" 		FROM");
        sbSQL.append(" 			oa_hrleave oh,");
        sbSQL.append(" 			system_user sus,");
        sbSQL.append(" 			system_kv leaveType,");
        sbSQL.append(" 			workflow_routelist rl,");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append(" 			oa_bizroute ob");
        sbSQL.append(" 		WHERE");
        sbSQL.append(" 			1 = 1");
        sbSQL.append(" 		AND oh.state = 0");
        sbSQL.append(" 		AND sus.state = 0");
        sbSQL.append(" 		AND oh.ApplicantId = sus.id");
        sbSQL.append(" 		AND leaveType.k = LeaveTypeId");
        sbSQL.append(" 		AND leaveType.GroupName = 'oa_hrleave_hrleaveType'");
        sbSQL.append(" 		AND ob.Id_YWID = oh.Id");
        sbSQL.append(" 		AND rl.YWID = oh.Id");
        sbSQL.append("      AND sd1.id = ob.controlString1 ");
        sbSQL.append("      AND sd2.id = ob.controlString2 ");
        sbSQL.append(" 		AND rl.STATU IN (1)");
        sbSQL.append(" 		UNION");
        sbSQL.append(" 			SELECT");
        sbSQL.append(" 				1 currentNodeId,");
        sbSQL.append(" 				0 routeListId,");
        sbSQL.append(" 				rl.STATU currentStatus,");
        sbSQL.append(" 				oh.Sid,");
        sbSQL.append(" 				oh.Id,");
        sbSQL.append(" 				oh.State,");
        sbSQL.append(" 				oh.OperatorId,");
        sbSQL.append(" 				oh.OperateTime,");
        sbSQL.append(" 				oh.ApplicantId,");
        sbSQL.append(" 				sus.`name` AS oh_applicationName,");
        sbSQL.append(" 				oh.LeaveTypeId,");
        sbSQL.append(" 				leaveType.v AS leaveTypeName,");
        sbSQL.append(" 				oh.OtherTypeDescription,");
        sbSQL.append(" 				oh.Days,");
        sbSQL.append(" 				oh.WhereToLeave,");
        sbSQL.append(" 				oh.StartTime,");
        sbSQL.append(" 				oh.EndTime,");
        sbSQL.append(" 				oh.HandoverName,");
        sbSQL.append(" 				oh.Reason,");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append(" 				ob. WORKFLOWID");
        sbSQL.append(" 			FROM");
        sbSQL.append(" 				oa_hrleave oh,");
        sbSQL.append(" 				system_user sus,");
        sbSQL.append(" 				system_kv leaveType,");
        sbSQL.append(" 				workflow_routelist rl,");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append(" 				oa_bizroute ob");
        sbSQL.append(" 			WHERE");
        sbSQL.append(" 				1 = 1");
        sbSQL.append(" 			AND oh.state = 0");
        sbSQL.append(" 			AND sus.state = 0");
        sbSQL.append(" 			AND oh.ApplicantId = sus.id");
        sbSQL.append(" 			AND leaveType.k = LeaveTypeId");
        sbSQL.append(" 			AND leaveType.GroupName = 'oa_hrleave_hrleaveType'");
        sbSQL.append(" 			AND ob.Id_YWID = oh.Id");
        sbSQL.append(" 			AND rl.YWID = oh.Id");
        sbSQL.append("          AND sd1.id = ob.controlString1 ");
        sbSQL.append("          AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");

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
        sbSQL.append(" 	AND WORKFLOWID = " + Config.getSystemVariable("WORKFLOWID.HR.Leave")  );
        sbSQL.append(" 	AND PARTICIPANT = '" + Database.encodeSQL(user.getId()) + "'");
        sbSQL.append(" )");


        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        HttpServletRequest request = ServletActionContext.getRequest();

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), hRLeaveVO, conditions, request, queryType);

        return  insertTitle(pager);
    }

    /**
     * 我等待我审批的请假休假数据
     *
     * @param hRLeaveVO
     * @param conditions
     * @return
     * @throws Exception
     */
    /*修改：周海鸿
    * 时间：2015-7-17
    * 内容：更改SQL语句添加人力审核*/
    public Pager listHRLeavewait(HRLeaveVO hRLeaveVO, UserPO user, List<KVObject> conditions) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("   SELECT DISTINCT");
        sbSQL.append("   	rl.CURRENTNODE currentNodeId,");
        sbSQL.append("   	rl.id routeListId,");
        sbSQL.append("   	rl.STATU currentStatus,");
        sbSQL.append("   	oh.Sid,");
        sbSQL.append("   	oh.Id,");
        sbSQL.append("   	oh.State,");
        sbSQL.append("   	oh.OperatorId,");
        sbSQL.append("   	oh.OperateTime,");
        sbSQL.append("   	oh.ApplicantId,");
        sbSQL.append("   	sus.`name` AS oh_applicationName,");
        sbSQL.append("   	oh.LeaveTypeId,");
        sbSQL.append("   	leaveType.v AS leaveTypeName,");
        sbSQL.append("   	oh.OtherTypeDescription,");
        sbSQL.append("   	oh.Days,");
        sbSQL.append("   	oh.WhereToLeave,");
        sbSQL.append("   	oh.StartTime,");
        sbSQL.append("   	oh.EndTime,");
        sbSQL.append("   	oh.HandoverName,");
        sbSQL.append("   	oh.Reason,");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("   	ob.SubmitterId,");
        sbSQL.append("   	ob.WORKFLOWID");
        sbSQL.append("   FROM");
        sbSQL.append("   	oa_hrleave oh,");
        sbSQL.append("   	system_user sus,");
        sbSQL.append("   	system_kv leaveType,");
        sbSQL.append("   	workflow_routelist rl,");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append("   	oa_bizroute ob");
        sbSQL.append("   WHERE");
        sbSQL.append("   	1 = 1");
        sbSQL.append("   AND oh.state = 0");
        sbSQL.append("   AND sus.state = 0");
        sbSQL.append("   AND oh.ApplicantId = sus.id");
        sbSQL.append("   AND leaveType.k = LeaveTypeId");
        sbSQL.append("   AND leaveType.GroupName = 'oa_hrleave_hrleaveType'");
        sbSQL.append("   AND ob.Id_YWID = oh.Id");
        sbSQL.append("   AND rl.YWID = oh.Id");
        sbSQL.append("   AND rl.STATU IN (1)");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND ob.controlString1  in (" + BizRouteConfig.getDepartmentParentId(user.getId())+")");
        sbSQL.append("   AND rl.CURRENTNODE IN (");
        sbSQL.append("   	SELECT");
        sbSQL.append("   		NODEID");
        sbSQL.append("   	FROM");
        sbSQL.append("   		workflow_participant p,");
        sbSQL.append("   		workflow_role r");
        sbSQL.append("   	WHERE");
        sbSQL.append("   		p.WORKFLOWID = "+   Config.getSystemVariable("WORKFLOWID.HR.Leave")  );
        sbSQL.append("   	AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");
        sbSQL.append("   	AND r.USERLIST LIKE '%|" + Database.encodeSQL(user.getId()) + "|%'");
        sbSQL.append("   )");



        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        HttpServletRequest request = ServletActionContext.getRequest();
        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), hRLeaveVO, conditions, request, queryType);
        return insertTitle(pager);
    }


    /**
     *
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 返回影响行数
     * 添加或修改数据，并修改数据状态
     * @param hRLeave
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(HRLeavePO hRLeave, HRLeaveVO hrleaveVO,UserPO user, Connection conn) throws Exception {
        if (hRLeave == null) {
            throw new Exception("请假休假数据提交失败");
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
        // 新增
        if (hRLeave.getId().equals("")) {
            hRLeave.setSid(MySQLDao.getMaxSid("OA_HRLeave", conn));
            hRLeave.setId(IdUtils.getUUID32());
            hRLeave.setState(Config.STATE_CURRENT);
            hRLeave.setOperatorId(user.getId());
            hRLeave.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(hRLeave, conn);
            /**
             *
             * 当数据添加成功时 向流程表中添加数据
             */
            if (count == 1) {
                count = BizRouteService.insertOrUpdate(hRLeave.getId(), hrleaveVO.getControlString3(),hrleaveVO.getControlString1(), hrleaveVO.getControlString2(),
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.HR.Leave")), true, user, conn);
            }
        }
        // 更新
        else {
            HRLeavePO temp = new HRLeavePO();
            temp.setSid(hRLeave.getSid());
            temp = MySQLDao.load(temp, HRLeavePO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                hRLeave.setSid(MySQLDao.getMaxSid("OA_HRLeave", conn));
                hRLeave.setState(Config.STATE_CURRENT);
                hRLeave.setOperatorId(user.getId());
                hRLeave.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(hRLeave, conn);
                /*更新业务数据*/
                if (count == 1) {
                    count = BizRouteService.insertOrUpdate(hRLeave.getId(), hrleaveVO.getControlString3(),hrleaveVO.getControlString1(), hrleaveVO.getControlString2(),
                            Integer.parseInt(Config.getSystemVariable("WORKFLOWID.HR.Leave")), false, user, conn);
                }
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 根据制定的ID获取数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public HRLeavePO loadHRLeavePO(String id) throws Exception {
        if( StringUtils.isEmpty (id)){
            MyException.newInstance ("请假休假数据获取失败");
        }

        //设置条件
        HRLeavePO po = new HRLeavePO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);

        //获取数据
        po = MySQLDao.load(po, HRLeavePO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     *
     * @param hRLeave
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(HRLeavePO hRLeave, UserPO user, Connection conn) throws Exception {
        int count = 0;

        hRLeave.setState(Config.STATE_CURRENT);
        hRLeave = MySQLDao.load(hRLeave, HRLeavePO.class);
        hRLeave.setState(Config.STATE_UPDATE);

        count = MySQLDao.update(hRLeave, conn);
        if (count == 1) {
            hRLeave.setSid(MySQLDao.getMaxSid("OA_HRLeave", conn));
            hRLeave.setState(Config.STATE_DELETE);
            hRLeave.setOperateTime(TimeUtils.getNow());
            hRLeave.setOperatorId(user.getId());

            count = MySQLDao.insert(hRLeave, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }
    /**
     * 组装数据添加业务状态title
     *
     * @param pager 分页对象
     * @return
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

            HRLeaveVO hrLeavePO = (HRLeaveVO) iteratot.next();
            try {
                //判断数据状态是都为完成状态
                if ("5".equals(hrLeavePO.getCurrentStatus())) {
                    title = "已完成";
                }else if ("3".equals(hrLeavePO.getCurrentStatus())) {
                    title = "中止";
                }
                //非完成状态
                else {
                    //判断阶段是否是开始节点
                    if ("1" .equals( hrLeavePO.getCurrentNodeId())) {
                        title = "申请";
                    }
                    //节点为中间节点
                    else {
                        //根据业务流编号与节点编号获取节点title
                        title = clientApplications.getNodeTitle(Integer.parseInt(Config.getSystemVariable("WORKFLOWID.HR.Leave")), Integer.valueOf(hrLeavePO.getCurrentNodeId()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //添加标题
            hrLeavePO.setCurrentNodeTitle(title);
            //添加到临时集合中
            tempList.add(hrLeavePO);
        }


        Pager page =pager;//创建一个分页对象

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

    /*修改：周海鸿
* 时间:2015-7-20
* 内容：获取请假打印数据*/
    public HRLeaveVO getPrintDate(String id) throws Exception {
        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("用章情况打印数据获取失败");
        }

        //构建sql语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT  ");
        sbSQL.append(" 	*  ");
        sbSQL.append(" FROM  ");
        sbSQL.append(" 	(  ");
        sbSQL.append(" SELECT");
        sbSQL.append("	rl.CURRENTNODE currentNodeId,");
        sbSQL.append("	rl.id routeListId,");
        sbSQL.append("	rl.STATU currentStatus,");
        sbSQL.append("	oh.Sid,");
        sbSQL.append("	oh.Id,");
        sbSQL.append("	oh.State,");
        sbSQL.append("	oh.OperatorId,");
        sbSQL.append("	oh.OperateTime,");
        sbSQL.append("	oh.ApplicantId,");
        sbSQL.append("	sus.`name` AS oh_applicationName,");
        sbSQL.append("	oh.LeaveTypeId,");
        sbSQL.append("	leaveType.v AS leaveTypeName,");
        sbSQL.append("	oh.OtherTypeDescription,");
        sbSQL.append("	oh.Days,");
        sbSQL.append("	oh.WhereToLeave,");
        sbSQL.append("	oh.StartTime,");
        sbSQL.append("	oh.EndTime,");
        sbSQL.append("	oh.HandoverName,");
        sbSQL.append("	oh.Reason,");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	ob.applicantTime as oh_applicationtime");
        sbSQL.append(" FROM");
        sbSQL.append("	oa_hrleave oh,");
        sbSQL.append("	system_user sus,");
        sbSQL.append("	system_kv leaveType,");
        sbSQL.append("	workflow_routelist rl,");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append("	oa_bizroute ob");
        sbSQL.append(" WHERE");
        sbSQL.append("	1 = 1");
        sbSQL.append(" AND oh.state = 0");
        sbSQL.append(" AND sus.state = 0");
        sbSQL.append(" AND oh.ApplicantId = sus.id");
        sbSQL.append(" AND leaveType.k = LeaveTypeId");
        sbSQL.append(" AND leaveType.GroupName = 'oa_hrleave_hrleaveType'");
        sbSQL.append(" AND ob.Id_YWID = oh.Id");
        sbSQL.append(" AND rl.YWID = oh.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" UNION");
        sbSQL.append("	SELECT");
        sbSQL.append("		1 currentNodeId,");
        sbSQL.append("		0 routeListId,");
        sbSQL.append("		rl.STATU currentStatus,");
        sbSQL.append("		oh.Sid,");
        sbSQL.append("		oh.Id,");
        sbSQL.append("		oh.State,");
        sbSQL.append("		oh.OperatorId,");
        sbSQL.append("		oh.OperateTime,");
        sbSQL.append("		oh.ApplicantId,");
        sbSQL.append("		sus.`name` AS oh_applicationName,");
        sbSQL.append("		oh.LeaveTypeId,");
        sbSQL.append("		leaveType.v AS leaveTypeName,");
        sbSQL.append("		oh.OtherTypeDescription,");
        sbSQL.append("		oh.Days,");
        sbSQL.append("		oh.WhereToLeave,");
        sbSQL.append("		oh.StartTime,");
        sbSQL.append("		oh.EndTime,");
        sbSQL.append("		oh.HandoverName,");
        sbSQL.append("		oh.Reason,");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	ob.applicantTime as oh_applicationtime");
        sbSQL.append("	FROM");
        sbSQL.append("		oa_hrleave oh,");
        sbSQL.append("		system_user sus,");
        sbSQL.append("		system_kv leaveType,");
        sbSQL.append("		workflow_routelist rl,");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append("		oa_bizroute ob");
        sbSQL.append("	WHERE");
        sbSQL.append("		1 = 1");
        sbSQL.append("	AND oh.state = 0");
        sbSQL.append("	AND sus.state = 0");
        sbSQL.append("	AND oh.ApplicantId = sus.id");
        sbSQL.append("	AND leaveType.k = LeaveTypeId");
        sbSQL.append("	AND leaveType.GroupName = 'oa_hrleave_hrleaveType'");
        sbSQL.append("	AND ob.Id_YWID = oh.Id");
        sbSQL.append("	AND rl.YWID = oh.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");

        sbSQL.append("	UNION");
        sbSQL.append("		SELECT");
        sbSQL.append("			1 currentNodeId,");
        sbSQL.append("			0 routeListId,");
        sbSQL.append("			- 1 currentStatus,");
        sbSQL.append("			oh.Sid,");
        sbSQL.append("			oh.Id,");
        sbSQL.append("			oh.State,");
        sbSQL.append("			oh.OperatorId,");
        sbSQL.append("			oh.OperateTime,");
        sbSQL.append("			oh.ApplicantId,");
        sbSQL.append("			sus.`name` AS oh_applicationName,");
        sbSQL.append("			oh.LeaveTypeId,");
        sbSQL.append("			leaveType.V AS leaveTypeName,");
        sbSQL.append("			oh.OtherTypeDescription,");
        sbSQL.append("			oh.Days,");
        sbSQL.append("			oh.WhereToLeave,");
        sbSQL.append("			oh.StartTime,");
        sbSQL.append("			oh.EndTime,");
        sbSQL.append("			oh.HandoverName,");
        sbSQL.append("			oh.Reason,");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	ob.applicantTime as oh_applicationtime");
        sbSQL.append("		FROM");
        sbSQL.append("			oa_hrleave oh,");
        sbSQL.append("			system_user sus,");
        sbSQL.append("			system_kv leaveType,");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append("			oa_bizroute ob");
        sbSQL.append("		WHERE");
        sbSQL.append("			1 = 1");
        sbSQL.append("		AND oh.state = 0");
        sbSQL.append("		AND sus.state = 0");
        sbSQL.append("		AND oh.ApplicantId = sus.id");
        sbSQL.append("		AND leaveType.k = LeaveTypeId");
        sbSQL.append("		AND leaveType.GroupName = 'oa_hrleave_hrleaveType'");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append("		AND ob.Id_YWID = oh.Id");
        sbSQL.append("		AND oh.id not IN (");
        sbSQL.append("			SELECT");
        sbSQL.append("				YWID");
        sbSQL.append("			FROM");
        sbSQL.append("				workflow_routelist rl");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("		)");
        sbSQL.append(" 	) d  ");
        sbSQL.append(" WHERE  ");
        sbSQL.append(" 	1 = 1  ");
        sbSQL.append(" AND id = '" + Database.encodeSQL(id) + "'  ");



        //获取数据
        List li = MySQLDao.query(sbSQL.toString(), HRLeaveVO.class, null);



        return (HRLeaveVO) li.get(0);

    }

}