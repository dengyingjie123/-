package com.youngbook.service.oa.Information;

import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.JSONUtils;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.entity.po.oa.Information.InformationSubmittedPO;
import com.youngbook.entity.vo.oa.Information.InformationSubmittedVO;
import com.youngbook.service.BaseService;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.service.wf.BizRouteService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by haihong on 2015/5/29.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class InformationSubmittedService extends BaseService {
    /**
     * 查询列表数据
     *
     * @param
     * @param conditions 查询条件
     * @return 修改人：周海鸿
     * 修改时间：2015-6-24
     * 修改事件：修改SQL语句 实现列表与业务相关联
     * <p/>
     * * 修改人：周海鸿
     * 修改时间：2015-7-8
     * 修改事件：修改SQL语句 实现列表与业务相关联
     * <p/>
     * 申请列表
     */
    public Pager list(InformationSubmittedVO informationSubmittedVO, UserPO user, List<KVObject> conditions) throws Exception {
        //获取HTTP请求对象
        HttpServletRequest request = ServletActionContext.getRequest();

        // 构造SQL语句
        StringBuffer SQL = new StringBuffer();
        SQL.append("");
        SQL.append(" SELECT DISTINCT");
        SQL.append(" rl.CURRENTNODE currentNodeId,");
        SQL.append(" rl.id  routeListId,");
        SQL.append(" rl.STATU currentStatus,");
        SQL.append(" oi.Sid,");
        SQL.append(" oi.Id,");
        SQL.append(" oi.State,");
        SQL.append(" oi.OperatorId,");
        SQL.append(" oi.OperateTime,");
        SQL.append(" oi.Department,");
        SQL.append(" oi.HandlingId,");
        SQL.append(" oi.MainOrg,");
        SQL.append(" oi.OtherOrg,");
        SQL.append(" oi.Reason,");
        SQL.append(" oi.Content,");
        SQL.append(" oi.SubmitTime,");
        SQL.append(" oi.TransferTime,");
        SQL.append(" oi.TransferOperatorId,");
        SQL.append(" oi.TransferRecipient,");
        SQL.append(" oi.RevertTime,");
        SQL.append(" oi.RevertOperatorId,");
        SQL.append(" oi.RevertRecipientId,");
        SQL.append(" "+ BizRouteConfig.getBizRouteStr());
        SQL.append("	sd1.fromName as  controlString1, ");
        SQL.append("	sd2.name as controlString2, ");
        SQL.append("	sd1.id as controlString1Id, ");
        SQL.append("	sd2.id as controlString2Id, ");
        SQL.append("	ob.SubmitterId,");
        SQL.append(" su1.`name`AS handlingName,");
        SQL.append(" sd.`name`AS departmentName");
        SQL.append(" FROM");
        SQL.append(" oa_informationsubmitted oi,");
        SQL.append(" system_user su1,");
        SQL.append(" system_department sd,");
        SQL.append(" system_department sd1, ");
        SQL.append(" system_department sd2, ");
        SQL.append(" workflow_routelist rl,");
        SQL.append(" oa_bizroute ob");
        SQL.append(" WHERE");
        SQL.append("  1 = 1");
        SQL.append(" AND su1.state = 0");
        SQL.append(" AND oi.state = 0");
        SQL.append(" AND su1.id = oi.HandlingId");
        SQL.append(" AND sd.id = oi.Department");
        SQL.append(" AND sd1.id = ob.controlString1 ");
        SQL.append(" AND sd2.id = ob.controlString2 ");
        SQL.append(" AND ob.Id_YWID = oi.Id");
        SQL.append(" AND rl.YWID = oi.Id");
        SQL.append(" AND rl.STATU IN (1)");

        SQL.append(" UNION");
        SQL.append(" SELECT DISTINCT");
        SQL.append(" -1 currentNodeId,");
        SQL.append(" -1 routeListId,");
        SQL.append(" rl.STATU currentStatus,");
        SQL.append(" oi.Sid,");
        SQL.append(" oi.Id,");
        SQL.append(" oi.State,");
        SQL.append(" oi.OperatorId,");
        SQL.append(" oi.OperateTime,");
        SQL.append(" oi.Department,");
        SQL.append(" oi.HandlingId,");
        SQL.append(" oi.MainOrg,");
        SQL.append(" oi.OtherOrg,");
        SQL.append(" oi.Reason,");
        SQL.append(" oi.Content,");
        SQL.append(" oi.SubmitTime,");
        SQL.append(" oi.TransferTime,");
        SQL.append(" oi.TransferOperatorId,");
        SQL.append(" oi.TransferRecipient,");
        SQL.append(" oi.RevertTime,");
        SQL.append(" oi.RevertOperatorId,");
        SQL.append(" oi.RevertRecipientId,");
        SQL.append(" "+ BizRouteConfig.getBizRouteStr());
        SQL.append("	sd1.fromName as  controlString1, ");
        SQL.append("	sd2.name as controlString2, ");
        SQL.append("	sd1.id as controlString1Id, ");
        SQL.append("	sd2.id as controlString2Id, ");
        SQL.append("	ob.SubmitterId,");
        SQL.append(" su1.`name`AS handlingName,");
        SQL.append(" sd.`name`AS departmentName");
        SQL.append(" FROM");
        SQL.append(" oa_informationsubmitted oi,");
        SQL.append(" system_user su1,");
        SQL.append(" system_department sd,");
        SQL.append(" workflow_routelist rl,");
        SQL.append(" system_department sd1, ");
        SQL.append(" system_department sd2, ");
        SQL.append(" oa_bizroute ob");
        SQL.append(" WHERE");
        SQL.append("  1 = 1");
        SQL.append(" AND su1.state = 0");
        SQL.append(" AND oi.state = 0");
        SQL.append(" AND su1.id = oi.HandlingId");
        SQL.append(" AND sd.id = oi.Department");
        SQL.append(" AND sd1.id = ob.controlString1 ");
        SQL.append(" AND sd2.id = ob.controlString2 ");
        SQL.append(" AND ob.Id_YWID = oi.Id");
        SQL.append(" AND rl.YWID = oi.Id");

        SQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");

        SQL.append(" UNION");
        SQL.append(" SELECT DISTINCT");
        SQL.append(" 1 currentNodeId,");
        SQL.append(" 0 routeListId,");
        SQL.append(" -1 currentStatus,");
        SQL.append(" oi.Sid,");
        SQL.append(" oi.Id,");
        SQL.append(" oi.State,");
        SQL.append(" oi.OperatorId,");
        SQL.append(" oi.OperateTime,");
        SQL.append(" oi.Department,");
        SQL.append(" oi.HandlingId,");
        SQL.append(" oi.MainOrg,");
        SQL.append(" oi.OtherOrg,");
        SQL.append(" oi.Reason,");
        SQL.append(" oi.Content,");
        SQL.append(" oi.SubmitTime,");
        SQL.append(" oi.TransferTime,");
        SQL.append(" oi.TransferOperatorId,");
        SQL.append(" oi.TransferRecipient,");
        SQL.append(" oi.RevertTime,");
        SQL.append(" oi.RevertOperatorId,");
        SQL.append(" oi.RevertRecipientId,");
        SQL.append(" "+ BizRouteConfig.getBizRouteStr());
        SQL.append("	sd1.fromName as  controlString1, ");
        SQL.append("	sd2.name as controlString2, ");
        SQL.append("	sd1.id as controlString1Id, ");
        SQL.append("	sd2.id as controlString2Id, ");
        SQL.append("	ob.SubmitterId,");
        SQL.append(" su1.`name`AS handlingName,");
        SQL.append(" sd.`name`AS departmentName");
        SQL.append(" FROM");
        SQL.append(" oa_informationsubmitted oi,");
        SQL.append(" system_user su1,");
        SQL.append(" system_department sd,");
        SQL.append(" system_department sd1, ");
        SQL.append(" system_department sd2, ");
        SQL.append(" workflow_routelist rl,");
        SQL.append(" oa_bizroute ob");
        SQL.append(" WHERE");
        SQL.append("  1 = 1");
        SQL.append(" AND su1.state = 0");
        SQL.append(" AND oi.state = 0");
        SQL.append(" AND su1.id = oi.HandlingId");
        SQL.append(" AND sd.id = oi.Department");
        SQL.append(" AND sd1.id = ob.controlString1 ");
        SQL.append(" AND sd2.id = ob.controlString2 ");
        SQL.append(" AND ob.Id_YWID = oi.Id");
        SQL.append(" AND rl.STATU IN (1)");
        SQL.append(" AND oi.id NOT IN");
        SQL.append(" (");
        SQL.append(" SELECT");
        SQL.append("  YWID");
        SQL.append(" FROM");
        SQL.append(" workflow_routelist rl");
        SQL.append(" WHERE");
        SQL.append(" 1 = 1");
        SQL.append(" )");
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //返回分页对象
        //添加用户条件
        conditions.add(new KVObject("SubmitterId = ", "'" + Database.encodeSQL(user.getId()) + "'"));
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        Pager pager = Pager.query(SQL.toString(), informationSubmittedVO, conditions, request, queryType);

        return insertTitle(pager);
    }

    /**
     * 查询等待审核列表数据
     *
     * @param
     * @param conditions 查询条件
     * @return 修改人：周海鸿
     * 修改时间：2015-6-24
     * 修改事件：修改SQL语句 实现列表与业务相关联
     * * 修改人：周海鸿
     * 修改时间：2015-7-8
     * 修改事件：修改SQL语句 实现列表与业务相关联
     */
    public Pager waitlist(InformationSubmittedVO informationSubmittedVO, UserPO user, List<KVObject> conditions) throws Exception {
        //获取HTTP请求对象
        HttpServletRequest request = ServletActionContext.getRequest();

        // 构造SQL语句
        StringBuffer SQL = new StringBuffer();
        SQL.append("");
        SQL.append(" SELECT DISTINCT");
        SQL.append(" rl.CURRENTNODE currentNodeId,");
        SQL.append(" rl.id  routeListId,");
        SQL.append(" rl.STATU currentStatus,");
        SQL.append(" oi.Sid,");
        SQL.append(" oi.Id,");
        SQL.append(" oi.State,");
        SQL.append(" oi.OperatorId,");
        SQL.append(" oi.OperateTime,");
        SQL.append(" oi.Department,");
        SQL.append(" oi.HandlingId,");
        SQL.append(" oi.MainOrg,");
        SQL.append(" oi.OtherOrg,");
        SQL.append(" oi.Reason,");
        SQL.append(" oi.Content,");
        SQL.append(" oi.SubmitTime,");
        SQL.append(" oi.TransferTime,");
        SQL.append(" oi.TransferOperatorId,");
        SQL.append(" oi.TransferRecipient,");
        SQL.append(" oi.RevertTime,");
        SQL.append(" oi.RevertOperatorId,");
        SQL.append(" oi.RevertRecipientId,");
        SQL.append(" "+ BizRouteConfig.getBizRouteStr());
        SQL.append("	sd1.fromName as  controlString1, ");
        SQL.append("	sd2.name as controlString2, ");
        SQL.append("	sd1.id as controlString1Id, ");
        SQL.append("	sd2.id as controlString2Id, ");
        SQL.append("   	ob.SubmitterId,");
        SQL.append("   	ob.WORKFLOWID,");
        SQL.append(" su1.`name`AS handlingName,");
        SQL.append(" sd.`name`AS departmentName");
        SQL.append(" FROM");
        SQL.append(" oa_informationsubmitted oi,");
        SQL.append(" system_user su1,");
        SQL.append(" system_department sd,");
         SQL.append(" system_department sd1, ");
         SQL.append(" system_department sd2, ");
        SQL.append(" workflow_routelist rl,");
        SQL.append(" oa_bizroute ob");
        SQL.append(" WHERE");
        SQL.append("  1 = 1");
        SQL.append(" AND su1.state = 0");
        SQL.append(" AND oi.state = 0");
        SQL.append(" AND su1.id = oi.HandlingId");
        SQL.append(" AND sd.id = oi.Department");
         SQL.append(" AND sd1.id = ob.controlString1 ");
         SQL.append(" AND sd2.id = ob.controlString2 ");
         SQL.append(" AND ob.controlString1  in (" + BizRouteConfig.getDepartmentParentId(user.getId())+")");
        SQL.append(" AND ob.Id_YWID = oi.Id");
        SQL.append(" AND rl.YWID = oi.Id");
        SQL.append(" AND rl.STATU IN (1)");
        SQL.append("   AND rl.CURRENTNODE IN (");
        SQL.append("   	SELECT");
        SQL.append("   		NODEID");
        SQL.append("   	FROM");
        SQL.append("   		workflow_participant p,");
        SQL.append("   		workflow_role r");
        SQL.append("   	WHERE");
        SQL.append("   		p.WORKFLOWID = " +          Config.getSystemVariable("WORKFLOWID.Information.Information"));
        SQL.append("   	AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");
        SQL.append("   	AND r.USERLIST LIKE '%|" + Database.encodeSQL(user.getId()) + "|%'");
        SQL.append("   )");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //返回分页对象
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        Pager pager = Pager.query(SQL.toString(), informationSubmittedVO, conditions, request, queryType);

        return insertTitle(pager);
    }

    /**
     * 查询参与列表数据
     *
     * @param
     * @param conditions 查询条件
     * @return 修改人：周海鸿
     * 修改时间：2015-6-24
     * 修改事件：修改SQL语句 实现列表与业务相关联
     * * 修改人：周海鸿
     * 修改时间：2015-7-8
     * 修改事件：修改SQL语句 实现列表与业务相关联
     */
    public Pager Participantlist(InformationSubmittedVO informationSubmittedVO, UserPO user, List<KVObject> conditions) throws Exception {
        //获取HTTP请求对象
        HttpServletRequest request = ServletActionContext.getRequest();

        // 构造SQL语句
        StringBuffer SQL = new StringBuffer();
        SQL.append("");
        SQL.append(" SELECT");
        SQL.append(" 	*");
        SQL.append(" FROM");
        SQL.append(" 	(");
        SQL.append(" SELECT DISTINCT");
        SQL.append(" rl.CURRENTNODE currentNodeId,");
        SQL.append(" rl.id  routeListId,");
        SQL.append(" rl.STATU currentStatus,");
        SQL.append(" oi.Sid,");
        SQL.append(" oi.Id,");
        SQL.append(" oi.State,");
        SQL.append(" oi.OperatorId,");
        SQL.append(" oi.OperateTime,");
        SQL.append(" oi.Department,");
        SQL.append(" oi.HandlingId,");
        SQL.append(" oi.MainOrg,");
        SQL.append(" oi.OtherOrg,");
        SQL.append(" oi.Reason,");
        SQL.append(" oi.Content,");
        SQL.append(" oi.SubmitTime,");
        SQL.append(" oi.TransferTime,");
        SQL.append(" oi.TransferOperatorId,");
        SQL.append(" oi.TransferRecipient,");
        SQL.append(" oi.RevertTime,");
        SQL.append(" oi.RevertOperatorId,");
        SQL.append(" oi.RevertRecipientId,");
        SQL.append(" "+ BizRouteConfig.getBizRouteStr());
        SQL.append("	sd1.fromName as  controlString1, ");
        SQL.append("	sd2.name as controlString2, ");
        SQL.append("	sd1.id as controlString1Id, ");
        SQL.append("	sd2.id as controlString2Id, ");
        SQL.append(" su1.`name`AS handlingName,");
        SQL.append(" sd.`name`AS departmentName");
        SQL.append(" FROM");
        SQL.append(" oa_informationsubmitted oi,");
        SQL.append(" system_user su1,");
        SQL.append(" system_department sd,");
        SQL.append(" system_department sd1,");
        SQL.append(" system_department sd2,");
        SQL.append(" workflow_routelist rl,");
        SQL.append(" oa_bizroute ob");
        SQL.append(" WHERE");
        SQL.append("  1 = 1");
        SQL.append(" AND su1.state = 0");
        SQL.append(" AND oi.state = 0");
        SQL.append(" AND su1.id = oi.HandlingId");
        SQL.append(" AND sd.id = oi.Department");
         SQL.append(" AND sd1.id = ob.controlString1 ");
         SQL.append(" AND sd2.id = ob.controlString2 ");
        SQL.append(" AND ob.Id_YWID = oi.Id");
        SQL.append(" AND rl.YWID = oi.Id");
        SQL.append(" AND rl.STATU IN (1)");

        SQL.append(" UNION");
        SQL.append(" SELECT DISTINCT");
        SQL.append(" -1 currentNodeId,");
        SQL.append(" -1 routeListId,");
        SQL.append(" rl.STATU currentStatus,");
        SQL.append(" oi.Sid,");
        SQL.append(" oi.Id,");
        SQL.append(" oi.State,");
        SQL.append(" oi.OperatorId,");
        SQL.append(" oi.OperateTime,");
        SQL.append(" oi.Department,");
        SQL.append(" oi.HandlingId,");
        SQL.append(" oi.MainOrg,");
        SQL.append(" oi.OtherOrg,");
        SQL.append(" oi.Reason,");
        SQL.append(" oi.Content,");
        SQL.append(" oi.SubmitTime,");
        SQL.append(" oi.TransferTime,");
        SQL.append(" oi.TransferOperatorId,");
        SQL.append(" oi.TransferRecipient,");
        SQL.append(" oi.RevertTime,");
        SQL.append(" oi.RevertOperatorId,");
        SQL.append(" oi.RevertRecipientId,");
        SQL.append(" "+ BizRouteConfig.getBizRouteStr());
        SQL.append("	sd1.fromName as  controlString1, ");
        SQL.append("	sd2.name as controlString2, ");
        SQL.append("	sd1.id as controlString1Id, ");
        SQL.append("	sd2.id as controlString2Id, ");
        SQL.append(" su1.`name`AS handlingName,");
        SQL.append(" sd.`name`AS departmentName");
        SQL.append(" FROM");
        SQL.append(" oa_informationsubmitted oi,");
        SQL.append(" system_user su1,");
        SQL.append(" system_department sd,");
        SQL.append(" system_department sd1,");
        SQL.append(" system_department sd2,");
        SQL.append(" workflow_routelist rl,");
        SQL.append(" oa_bizroute ob");
        SQL.append(" WHERE");
        SQL.append("  1 = 1");
        SQL.append(" AND su1.state = 0");
        SQL.append(" AND oi.state = 0");
        SQL.append(" AND su1.id = oi.HandlingId");
        SQL.append(" AND sd.id = oi.Department");
        SQL.append(" AND ob.Id_YWID = oi.Id");
         SQL.append(" AND sd1.id = ob.controlString1 ");
         SQL.append(" AND sd2.id = ob.controlString2 ");
        SQL.append(" AND rl.YWID = oi.Id");
        SQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");


        SQL.append(" 	) t");
        SQL.append(" WHERE");
        SQL.append(" 	1 = 1");
        SQL.append(" AND t.Id IN (");
        SQL.append(" 	SELECT DISTINCT");
        SQL.append(" 		YWID");
        SQL.append(" 	FROM");
        SQL.append(" 		workflow_action");
        SQL.append(" 	WHERE");
        SQL.append(" 		1 = 1");
        //添加业务编号
        SQL.append(" 	AND WORKFLOWID = " +          Config.getSystemVariable("WORKFLOWID.Information.Information"));
        SQL.append(" 	AND PARTICIPANT = '" + Database.encodeSQL(user.getId()) + "'");
        SQL.append(" )");
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //返回分页对象
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));


        Pager pager = Pager.query(SQL.toString(), informationSubmittedVO, conditions, request, queryType);

        return insertTitle(pager);
    }

    /**
     * 添加或修改数据，并修改数据状态
     *
     * @param informationSubmitted
     * @param user
     * @param conn
     * @return
     * @throws Exception 修改人：周海鸿
     *                   修改时间：2015-7-8
     *                   修改事件：添加数据有效性验证,添加业务数据添加
     */
    public int insertOrUpdate(InformationSubmittedPO informationSubmitted,InformationSubmittedVO informationSubmittedVO, UserPO user, Connection conn) throws Exception {
        //检查数据有效性
        if (informationSubmitted == null) {
            throw new Exception("对外报送数据提交失败");
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
        if (informationSubmitted.getId().equals("")) {
            informationSubmitted.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted", conn));
            informationSubmitted.setId(IdUtils.getUUID32());
            informationSubmitted.setState(Config.STATE_CURRENT);
            informationSubmitted.setOperatorId(user.getId());
            informationSubmitted.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(informationSubmitted, conn);

            /**
             * 2015-6-26 小周
             * 当数据添加成功时 向流程表中添加数据
             */


            if (count == 1) {
                count = BizRouteService.insertOrUpdate(informationSubmitted.getId(), informationSubmittedVO.getControlString3(), informationSubmittedVO.getControlString1(), informationSubmittedVO.getControlString2(),
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Information.Information")), true, user, conn);
            }
        }
        // 更新
        else {
            InformationSubmittedPO temp = new InformationSubmittedPO();
            temp.setSid(informationSubmitted.getSid());
            temp = MySQLDao.load(temp, InformationSubmittedPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                informationSubmitted.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted", conn));
                informationSubmitted.setState(Config.STATE_CURRENT);
                informationSubmitted.setOperatorId(user.getId());
                informationSubmitted.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(informationSubmitted, conn);

                if (count == 1) {
                    count = BizRouteService.insertOrUpdate(informationSubmitted.getId(), informationSubmittedVO.getControlString3() ,informationSubmittedVO.getControlString1(), informationSubmittedVO.getControlString2(),
                            Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Information.Information")), false, user, conn);
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
    public InformationSubmittedPO loadInformationSubmittedPO(String id) throws Exception {
        InformationSubmittedPO po = new InformationSubmittedPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, InformationSubmittedPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     *
     * @param informationSubmitted
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(InformationSubmittedPO informationSubmitted, UserPO user, Connection conn) throws Exception {
        int count = 0;

        informationSubmitted.setState (Config.STATE_CURRENT);
        informationSubmitted = MySQLDao.load(informationSubmitted, InformationSubmittedPO.class);
        informationSubmitted.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(informationSubmitted, conn);
        if (count == 1) {
            informationSubmitted.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted", conn));
            informationSubmitted.setState(Config.STATE_DELETE);
            informationSubmitted.setOperateTime(TimeUtils.getNow());
            informationSubmitted.setOperatorId(user.getId());
            count = MySQLDao.insert(informationSubmitted, conn);
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
            InformationSubmittedVO informationSubmittedVO = (InformationSubmittedVO) iteratot.next();
            try {
                //判断数据状态是都为完成状态
                if ("5".equals(informationSubmittedVO.getCurrentStatus())) {
                    title = "已完成";
                }else if ("3".equals(informationSubmittedVO.getCurrentStatus())) {
                    title = "中止";
                }
                //非完成状态
                else {
                    //判断阶段是否是开始节点
                    if ("1".equals(informationSubmittedVO.getCurrentNodeId())) {
                        title = "申请";
                    }
                    //节点为中间节点
                    else {
                        //根据业务流编号与节点编号获取节点title
                        title = clientApplications.getNodeTitle(Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Information.Information")), Integer.valueOf(informationSubmittedVO.getCurrentNodeId()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //添加标题
            informationSubmittedVO.setCurrentNodeTitle(title);
            //添加到临时集合中
            tempList.add(informationSubmittedVO);
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


    public InformationSubmittedVO getPrintData(String id) throws Exception {
        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("获取对外打印数据失败");
        }

        StringBuffer SQL = new StringBuffer();

        SQL.append(" SELECT  ");
        SQL.append(" 	*  ");
        SQL.append(" FROM  ");
        SQL.append(" 	(  ");
        SQL.append(" SELECT DISTINCT");
        SQL.append(" rl.CURRENTNODE currentNodeId,");
        SQL.append(" rl.id  routeListId,");
        SQL.append(" rl.STATU currentStatus,");
        SQL.append(" oi.Sid,");
        SQL.append(" oi.Id,");
        SQL.append(" oi.State,");
        SQL.append(" oi.OperatorId,");
        SQL.append(" oi.OperateTime,");
        SQL.append(" oi.Department,");
        SQL.append(" oi.HandlingId,");
        SQL.append(" oi.MainOrg,");
        SQL.append(" oi.OtherOrg,");
        SQL.append(" oi.Reason,");
        SQL.append(" oi.Content,");
        SQL.append(" oi.SubmitTime,");
        SQL.append(" oi.TransferTime,");
        SQL.append(" oi.TransferOperatorId,");
        SQL.append(" oi.TransferRecipient,");
        SQL.append(" oi.RevertTime,");
        SQL.append(" oi.RevertOperatorId,");
        SQL.append(" oi.RevertRecipientId,");
        SQL.append(" "+ BizRouteConfig.getBizRouteStr());
        SQL.append("	sd1.fromName as  controlString1, ");
        SQL.append("	sd2.name as controlString2, ");
        SQL.append("	sd1.id as controlString1Id, ");
        SQL.append("	sd2.id as controlString2Id, ");
        SQL.append("ob.applicantName,  ");
        SQL.append("ob.applicantComment,  ");
        SQL.append("ob.applicantTime, ");
        SQL.append(" su1.`name`AS handlingName,");
        SQL.append(" sd.`name`AS departmentName");
        SQL.append(" FROM");
        SQL.append(" oa_informationsubmitted oi,");
        SQL.append(" system_user su1,");
        SQL.append(" system_department sd,");
        SQL.append(" system_department sd1,");
        SQL.append(" system_department sd2,");
        SQL.append(" workflow_routelist rl,");
        SQL.append(" oa_bizroute ob");
        SQL.append(" WHERE");
        SQL.append("  1 = 1");
        SQL.append(" AND su1.state = 0");
        SQL.append(" AND oi.state = 0");
        SQL.append(" AND su1.id = oi.HandlingId");
        SQL.append(" AND sd.id = oi.Department");
        SQL.append(" AND ob.Id_YWID = oi.Id");
        SQL.append(" AND sd1.id = ob.controlString1 ");
        SQL.append(" AND sd2.id = ob.controlString2 ");
        SQL.append(" AND rl.YWID = oi.Id");
        SQL.append(" AND rl.STATU IN (1)");

        SQL.append(" UNION");
        SQL.append(" SELECT DISTINCT");
        SQL.append(" -1 currentNodeId,");
        SQL.append(" -1 routeListId,");
        SQL.append(" rl.STATU currentStatus,");
        SQL.append(" oi.Sid,");
        SQL.append(" oi.Id,");
        SQL.append(" oi.State,");
        SQL.append(" oi.OperatorId,");
        SQL.append(" oi.OperateTime,");
        SQL.append(" oi.Department,");
        SQL.append(" oi.HandlingId,");
        SQL.append(" oi.MainOrg,");
        SQL.append(" oi.OtherOrg,");
        SQL.append(" oi.Reason,");
        SQL.append(" oi.Content,");
        SQL.append(" oi.SubmitTime,");
        SQL.append(" oi.TransferTime,");
        SQL.append(" oi.TransferOperatorId,");
        SQL.append(" oi.TransferRecipient,");
        SQL.append(" oi.RevertTime,");
        SQL.append(" oi.RevertOperatorId,");
        SQL.append(" oi.RevertRecipientId,");
        SQL.append(" "+ BizRouteConfig.getBizRouteStr());
        SQL.append("	sd1.fromName as  controlString1, ");
        SQL.append("	sd2.name as controlString2, ");
        SQL.append("	sd1.id as controlString1Id, ");
        SQL.append("	sd2.id as controlString2Id, ");

        SQL.append("ob.applicantName,  ");
        SQL.append("ob.applicantComment,  ");
        SQL.append("ob.applicantTime, ");
        SQL.append(" su1.`name`AS handlingName,");
        SQL.append(" sd.`name`AS departmentName");
        SQL.append(" FROM");
        SQL.append(" oa_informationsubmitted oi,");
        SQL.append(" system_user su1,");
        SQL.append(" system_department sd,");
        SQL.append(" system_department sd1,");
        SQL.append(" system_department sd2,");
        SQL.append(" workflow_routelist rl,");
        SQL.append(" oa_bizroute ob");
        SQL.append(" WHERE");
        SQL.append("  1 = 1");
        SQL.append(" AND su1.state = 0");
        SQL.append(" AND oi.state = 0");
        SQL.append(" AND su1.id = oi.HandlingId");
        SQL.append(" AND sd.id = oi.Department");
        SQL.append(" AND ob.Id_YWID = oi.Id");
        SQL.append(" AND sd1.id = ob.controlString1 ");
        SQL.append(" AND sd2.id = ob.controlString2 ");
        SQL.append(" AND rl.YWID = oi.Id");
        SQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");


        SQL.append(" UNION");
        SQL.append(" SELECT DISTINCT");
        SQL.append(" 1 currentNodeId,");
        SQL.append(" 0 routeListId,");
        SQL.append(" -1 currentStatus,");
        SQL.append(" oi.Sid,");
        SQL.append(" oi.Id,");
        SQL.append(" oi.State,");
        SQL.append(" oi.OperatorId,");
        SQL.append(" oi.OperateTime,");
        SQL.append(" oi.Department,");
        SQL.append(" oi.HandlingId,");
        SQL.append(" oi.MainOrg,");
        SQL.append(" oi.OtherOrg,");
        SQL.append(" oi.Reason,");
        SQL.append(" oi.Content,");
        SQL.append(" oi.SubmitTime,");
        SQL.append(" oi.TransferTime,");
        SQL.append(" oi.TransferOperatorId,");
        SQL.append(" oi.TransferRecipient,");
        SQL.append(" oi.RevertTime,");
        SQL.append(" oi.RevertOperatorId,");
        SQL.append(" oi.RevertRecipientId,");
        SQL.append(" "+ BizRouteConfig.getBizRouteStr());
        SQL.append("	sd1.fromName as  controlString1, ");
        SQL.append("	sd2.name as controlString2, ");
        SQL.append("	sd1.id as controlString1Id, ");
        SQL.append("	sd2.id as controlString2Id, ");
        SQL.append("ob.applicantName,  ");
        SQL.append("ob.applicantComment,  ");
        SQL.append("ob.applicantTime, ");
        SQL.append(" su1.`name`AS handlingName,");
        SQL.append(" sd.`name`AS departmentName");
        SQL.append(" FROM");
        SQL.append(" oa_informationsubmitted oi,");
        SQL.append(" system_user su1,");
        SQL.append(" system_department sd,");
        SQL.append(" system_department sd1,");
        SQL.append(" system_department sd2,");
        SQL.append(" workflow_routelist rl,");
        SQL.append(" oa_bizroute ob");
        SQL.append(" WHERE");
        SQL.append("  1 = 1");
        SQL.append(" AND su1.state = 0");
        SQL.append(" AND oi.state = 0");
        SQL.append(" AND su1.id = oi.HandlingId");
        SQL.append(" AND sd.id = oi.Department");
        SQL.append(" AND ob.Id_YWID = oi.Id");
        SQL.append(" AND sd1.id = ob.controlString1 ");
        SQL.append(" AND sd2.id = ob.controlString2 ");
        SQL.append(" AND rl.STATU IN (1)");
        SQL.append(" AND oi.id NOT IN");
        SQL.append(" (");
        SQL.append(" SELECT");
        SQL.append("  YWID");
        SQL.append(" FROM");
        SQL.append(" workflow_routelist rl");
        SQL.append(" WHERE");
        SQL.append(" 1 = 1");
        SQL.append(" )");
        SQL.append(" 	) d  ");
        SQL.append(" WHERE  ");
        SQL.append(" 	1 = 1  ");
        SQL.append(" AND id = '" + Database.encodeSQL(id) + "'  ");

        //获取数据
        List li = MySQLDao.query(SQL.toString(), InformationSubmittedVO.class, null);

        if (li.size() > 1) {
            throw new Exception("获取对外资料数据错误");
        }

        return (InformationSubmittedVO) li.get(0);

    }



    /**
     * 获取部门列表
     *
     * @param
     * @return
     */
    public JSONArray getUsers () throws Exception {

        String sql = "select * from system_user where state = 0  ";

        List li = MySQLDao.query (sql, UserPO.class, null);

        //组装json 数组
        JSONObject obj = new JSONObject ();
        JSONArray array = new JSONArray ();
        UserPO temp = null;
        Iterator it = li.iterator ();
        while ( it.hasNext () ) {
            temp = (UserPO) it.next ();
            array.add (JSONUtils.toOBject(obj, temp.getId(), temp.getName()));//装换json对象
        }

        return array;
    }
}
