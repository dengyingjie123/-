package com.youngbook.service.oa.business;

import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.business.BusinessTripApplicationPO;
import com.youngbook.entity.po.wf.BizRoutePO;
import com.youngbook.entity.vo.business.BusinessTripApplicationVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/7/14.
 */
public class BusinessTripApplicationService extends BaseService {
    /**
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 对业务表进行添更新操作
     * 返回影响行数
     * 添加或修改数据，并修改数据状态
     *
     * @param businessTripApplication
     * @param user
     * @param conn
     * @return
     * @throws Exception
     * 修改：周海鸿
     * 时间：2015-7-15
     * 内容：添加业务相关逻辑
     */
    public int insertOrUpdate(BusinessTripApplicationPO businessTripApplication,BusinessTripApplicationVO businessTripApplicationVO, UserPO user, Connection conn) throws Exception {
        //判断数据有效性
        if (businessTripApplication == null) {
            throw new Exception("出差申请数据提交失败");
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
        //判断提交的数据是否是新数据
        if (businessTripApplication.getId().equals("")) {
            //填充数据
            businessTripApplication.setSid(MySQLDao.getMaxSid("OA_BusinessTripApplication", conn));
            businessTripApplication.setId(IdUtils.getUUID32());
            businessTripApplication.setState(Config.STATE_CURRENT);
            businessTripApplication.setOperatorId(user.getId());
            businessTripApplication.setOperateTime(TimeUtils.getNow());

            //添加
            count = MySQLDao.insert(businessTripApplication, conn);
            // 当数据添加成功时 向流程表中添加数据

            if (count == 1) {
                count = BizRouteService.insertOrUpdate(businessTripApplication.getId(), businessTripApplicationVO.getControlString3(), businessTripApplicationVO.getControlString1(), businessTripApplicationVO.getControlString2(),
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Business.Business")), true, user, conn);
            }
        }
        // 数据已经存在进行更新
        else {

            BusinessTripApplicationPO temp = new BusinessTripApplicationPO();
            temp.setSid(businessTripApplication.getSid());
            temp = MySQLDao.load(temp, BusinessTripApplicationPO.class);
            temp.setState(Config.STATE_UPDATE);

            count = MySQLDao.update(temp, conn);
            //数据更新 返回影响行数为一
            if (count == 1) {

                businessTripApplication.setSid(MySQLDao.getMaxSid("OA_BusinessTripApplication", conn));
                businessTripApplication.setState(Config.STATE_CURRENT);
                businessTripApplication.setOperatorId(user.getId());
                businessTripApplication.setOperateTime(TimeUtils.getNow());

                count = MySQLDao.insert(businessTripApplication, conn);

                // 当数据添加成功时 向流程表中添加数据
                if (count == 1) {
                    count = BizRouteService.insertOrUpdate(businessTripApplication.getId(), businessTripApplicationVO.getControlString3(),businessTripApplicationVO.getControlString1(), businessTripApplicationVO.getControlString2(),
                            Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Business.Business")), false, user, conn);
                }
            }
        }
        //数据库影响行数不等1
        if (count != 1) {
            throw new Exception("请假申请数据库异常");
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
    public BusinessTripApplicationPO loadBusinessTripApplicationPO(String id) throws Exception {
        //判断id是否为null
        if (null == id || "".equals(id)) {
            throw new Exception("请假申请数据获取失败");
        }

        //设置查询数据
        BusinessTripApplicationPO po = new BusinessTripApplicationPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);

        //查询数据
        po = MySQLDao.load(po, BusinessTripApplicationPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     *
     * @param businessTripApplication
     * @param
     * @param conn
     * @return 影响行数
     * @throws Exception
     */
    public int delete(BusinessTripApplicationPO businessTripApplication, UserPO user, Connection conn) throws Exception {

        //判断数据有效性
        if (businessTripApplication == null) {
            throw new Exception("出差申请数据提交失败");
        }

        //当前用户操作类是否为空
        if (user == null) {
            throw new Exception("当前用户失效");
        }

        //当前数据链接是否空
        if (conn == null) {
            throw new Exception("链接错误");
        }
        //设置影响行数
        int count = 0;

        businessTripApplication.setState(Config.STATE_CURRENT);
        businessTripApplication = MySQLDao.load(businessTripApplication, BusinessTripApplicationPO.class);
        businessTripApplication.setState(Config.STATE_UPDATE);

        count = MySQLDao.update(businessTripApplication, conn);
        // //数据更新 返回影响行数为一
        if (count == 1) {

            businessTripApplication.setSid(MySQLDao.getMaxSid("OA_BusinessTripApplication", conn));
            businessTripApplication.setState(Config.STATE_DELETE);
            businessTripApplication.setOperateTime(TimeUtils.getNow());
            businessTripApplication.setOperatorId(user.getId());

            count = MySQLDao.insert(businessTripApplication, conn);
        }

        //返回影响行数不为一
        if (count != 1) {
            throw new Exception("请假申请删除失败");
        }

        return count;
    }

    /*修改：周海鸿
    * 时间：2015-7-15
    * 内容：添加获取列表数据*/

    /**
     * 我申请的请假休假数据
     *
     * @param businessTripApplicationVO 数据封装实体类
     * @param conditions                条件查询集合
     * @return 数据分页对象
     * @throws Exception
     */
    public Pager listBusinessTrip(BusinessTripApplicationVO businessTripApplicationVO, UserPO user, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT ");
        sbSQL.append("	rl.CURRENTNODE currentNodeId, ");
        sbSQL.append("	rl.id routeListId, ");
        sbSQL.append("	rl.STATU currentStatus, ");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	ob.SubmitterId, ");
        sbSQL.append("	ob.WORKFLOWID, ");
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	oab.* ");
        sbSQL.append(" FROM ");
        sbSQL.append("	OA_BusinessTripApplication oab, ");
        sbSQL.append("	workflow_routelist rl, ");
        sbSQL.append("	system_department sd1, ");
        sbSQL.append("	system_department sd2, ");
        sbSQL.append("	oa_bizroute ob ");
        sbSQL.append(" WHERE ");
        sbSQL.append("	1 = 1 ");
        sbSQL.append(" AND oab.state = 0 ");
        sbSQL.append(" AND ob.Id_YWID = oab.Id ");
        sbSQL.append(" AND rl.YWID = oab.Id ");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (1) ");
        sbSQL.append(" UNION ");
        sbSQL.append("	SELECT ");
        sbSQL.append("		1 currentNodeId, ");
        sbSQL.append("		0 routeListId, ");
        sbSQL.append("		rl.STATU currentStatus, ");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("		ob.SubmitterId, ");
        sbSQL.append("		ob.WORKFLOWID, ");
        sbSQL.append("		sd1.fromName as controlString1, ");
        sbSQL.append("		sd2.name as controlString2, ");
        sbSQL.append("		sd1.id as controlString1Id, ");
        sbSQL.append("		sd2.id as controlString2Id, ");
        sbSQL.append("		oab.* ");
        sbSQL.append("	FROM ");
        sbSQL.append("		OA_BusinessTripApplication oab, ");
        sbSQL.append("		workflow_routelist rl, ");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append("		oa_bizroute ob ");
        sbSQL.append("	WHERE ");
        sbSQL.append("		1 = 1 ");
        sbSQL.append("	AND oab.state = 0 ");
        sbSQL.append("	AND ob.Id_YWID = oab.Id ");
        sbSQL.append("	AND rl.YWID = oab.Id ");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");
        sbSQL.append("	UNION ");
        sbSQL.append("		SELECT ");
        sbSQL.append("			1 currentNodeId, ");
        sbSQL.append("			0 routeListId, ");
        sbSQL.append("			- 1 currentStatu, ");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("			ob.SubmitterId, ");
        sbSQL.append("			ob.WORKFLOWID, ");
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("			oab.* ");
        sbSQL.append("		FROM ");
        sbSQL.append("			OA_BusinessTripApplication oab, ");
        sbSQL.append("	    system_department sd1, ");
        sbSQL.append("	    system_department sd2, ");
        sbSQL.append("			oa_bizroute ob ");
        sbSQL.append("		WHERE ");
        sbSQL.append("			1 = 1 ");
        sbSQL.append("		AND oab.state = 0 ");
        sbSQL.append("		AND ob.Id_YWID = oab.Id ");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append("		AND oab.id NOT IN ( ");
        sbSQL.append("			SELECT ");
        sbSQL.append("				YWID ");
        sbSQL.append("			FROM ");
        sbSQL.append("				workflow_routelist rl ");
        sbSQL.append("			WHERE ");
        sbSQL.append("				1 = 1 ");
        sbSQL.append("		) ");

        //添加用户条件
        conditions.add(new KVObject("SubmitterId = ", "'" + Database.encodeSQL(user.getId()) + "'"));
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), businessTripApplicationVO, conditions, request, queryType);

        return insertTitle(pager);
    }

    /**
     * 我参与过的请假休假数据
     *
     * @param businessTripApplicationVO
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager listBusinessTripParticipant(BusinessTripApplicationVO businessTripApplicationVO, UserPO user, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append(" 	*");
        sbSQL.append(" FROM");
        sbSQL.append(" 	(");
        sbSQL.append(" SELECT ");
        sbSQL.append("	rl.CURRENTNODE currentNodeId, ");
        sbSQL.append("	rl.id routeListId, ");
        sbSQL.append("	rl.STATU currentStatus, ");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	ob.SubmitterId, ");
        sbSQL.append("	ob.WORKFLOWID, ");
        sbSQL.append("  sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	oab.* ");
        sbSQL.append(" FROM ");
        sbSQL.append("	OA_BusinessTripApplication oab, ");
        sbSQL.append("	workflow_routelist rl, ");
        sbSQL.append("	system_department sd1, ");
        sbSQL.append("	system_department sd2, ");
        sbSQL.append("	oa_bizroute ob ");
        sbSQL.append(" WHERE ");
        sbSQL.append("	1 = 1 ");
        sbSQL.append(" AND oab.state = 0 ");
        sbSQL.append(" AND ob.Id_YWID = oab.Id ");
        sbSQL.append(" AND rl.YWID = oab.Id ");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (1) ");
        sbSQL.append(" UNION ");
        sbSQL.append("	SELECT ");
        sbSQL.append("	1 currentNodeId, ");
        sbSQL.append("	0 routeListId, ");
        sbSQL.append("	rl.STATU currentStatus, ");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	ob.SubmitterId, ");
        sbSQL.append("	ob.WORKFLOWID, ");
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	oab.* ");
        sbSQL.append("	FROM ");
        sbSQL.append("	OA_BusinessTripApplication oab, ");
        sbSQL.append("	workflow_routelist rl, ");
        sbSQL.append("	system_department sd1, ");
        sbSQL.append("	system_department sd2, ");
        sbSQL.append("	oa_bizroute ob ");
        sbSQL.append("	WHERE ");
        sbSQL.append("		1 = 1 ");
        sbSQL.append("	AND oab.state = 0 ");
        sbSQL.append("	AND ob.Id_YWID = oab.Id ");
        sbSQL.append("	AND rl.YWID = oab.Id ");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
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
        sbSQL.append(" 	AND WORKFLOWID = " +                      Config.getSystemVariable("WORKFLOWID.Business.Business"));
        sbSQL.append(" 	AND PARTICIPANT = '" + Database.encodeSQL(user.getId()) + "'");
        sbSQL.append(" )");


        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), businessTripApplicationVO, conditions, request, queryType);

        return insertTitle(pager);
    }

    /**
     * 我等待我审批的请假休假数据
     *
     * @param businessTripApplicationVO
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager listBusinessTripWait(BusinessTripApplicationVO businessTripApplicationVO, UserPO user, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("   SELECT DISTINCT");
        sbSQL.append("	rl.CURRENTNODE currentNodeId, ");
        sbSQL.append("	rl.id routeListId, ");
        sbSQL.append("	rl.STATU currentStatus, ");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	oab.* ");
        sbSQL.append(" FROM ");
        sbSQL.append("	OA_BusinessTripApplication oab, ");
        sbSQL.append("	workflow_routelist rl, ");
        sbSQL.append("	system_department sd1, ");
        sbSQL.append("	system_department sd2, ");
        sbSQL.append("	oa_bizroute ob ");
        sbSQL.append(" WHERE ");
        sbSQL.append("	1 = 1 ");
        sbSQL.append(" AND oab.state = 0 ");
        sbSQL.append(" AND ob.Id_YWID = oab.Id ");
        sbSQL.append(" AND rl.YWID = oab.Id ");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND ob.controlString1  in (" + BizRouteConfig.getDepartmentParentId(user.getId())+")");
        sbSQL.append(" AND rl.STATU IN (1) ");
        sbSQL.append(" AND rl.CURRENTNODE IN (");
        sbSQL.append("   	SELECT");
        sbSQL.append("   		NODEID");
        sbSQL.append("   	FROM");
        sbSQL.append("   		workflow_participant p,");
        sbSQL.append("   		workflow_role r");
        sbSQL.append("   	WHERE");
        sbSQL.append("   		p.WORKFLOWID = " +                      Config.getSystemVariable("WORKFLOWID.Business.Business"));
        sbSQL.append("   	AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");
        sbSQL.append("   	AND r.USERLIST LIKE '%|" + Database.encodeSQL(user.getId()) + "|%'");
        sbSQL.append("   )");


        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), businessTripApplicationVO, conditions, request, queryType);

        return insertTitle(pager);
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
            BusinessTripApplicationVO businessTripApplication = (BusinessTripApplicationVO) iteratot.next();

            try {
                //判断数据状态是都为完成状态
                if ("5".equals(businessTripApplication.getCurrentStatus())) {
                    title = "已完成";
                }else if ("3".equals(businessTripApplication.getCurrentStatus())) {
                    title = "中止";
                }
                //非完成状态
                else {
                    //判断阶段是否是开始节点
                    if ("1".equals(businessTripApplication.getCurrentNodeId())) {
                        title = "申请";
                    }
                    //节点为中间节点
                    else {
                        //根据业务流编号与节点编号获取节点title
                        title = clientApplications.getNodeTitle(Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Business.Business")), Integer.valueOf(businessTripApplication.getCurrentNodeId()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //添加标题
            businessTripApplication.setCurrentNodeTitle(title);

            //添加到临时集合中
            tempList.add(businessTripApplication);
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

    /*修改：周海鸿
    * 时间:2015-7-20
    * 内容：获取用出差申请数据*/
    public BusinessTripApplicationVO getPrintDate(String id) throws Exception {

        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("出差数据打印数据获取失败");
        }

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT  ");
        sbSQL.append(" 	*  ");
        sbSQL.append(" FROM  ");
        sbSQL.append(" 	(  ");
        sbSQL.append("SELECT ");
        sbSQL.append("	rl.CURRENTNODE currentNodeId, ");
        sbSQL.append("	rl.id routeListId, ");
        sbSQL.append("	rl.STATU currentStatus, ");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("	oab.* ");
        sbSQL.append(" FROM ");
        sbSQL.append("	OA_BusinessTripApplication oab, ");
        sbSQL.append("	workflow_routelist rl, ");
        sbSQL.append("	system_department sd1, ");
        sbSQL.append("	system_department sd2, ");
        sbSQL.append("	oa_bizroute ob ");
        sbSQL.append(" WHERE ");
        sbSQL.append("	1 = 1 ");
        sbSQL.append(" AND oab.state = 0 ");
        sbSQL.append(" AND ob.Id_YWID = oab.Id ");
        sbSQL.append(" AND rl.YWID = oab.Id ");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (1) ");
        sbSQL.append(" UNION ");
        sbSQL.append("	SELECT ");
        sbSQL.append("		1 currentNodeId, ");
        sbSQL.append("		0 routeListId, ");
        sbSQL.append("		rl.STATU currentStatus, ");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("		oab.* ");
        sbSQL.append("	FROM ");
        sbSQL.append("		OA_BusinessTripApplication oab, ");
        sbSQL.append("		workflow_routelist rl, ");
        sbSQL.append("	system_department sd1, ");
        sbSQL.append("	system_department sd2, ");
        sbSQL.append("		oa_bizroute ob ");
        sbSQL.append("	WHERE ");
        sbSQL.append("		1 = 1 ");
        sbSQL.append("	AND oab.state = 0 ");
        sbSQL.append("	AND ob.Id_YWID = oab.Id ");
        sbSQL.append("	AND rl.YWID = oab.Id ");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 "); sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");

        sbSQL.append("	UNION ");
        sbSQL.append("		SELECT ");
        sbSQL.append("			1 currentNodeId, ");
        sbSQL.append("			0 routeListId, ");
        sbSQL.append("			- 1 currentStatu, ");
        sbSQL.append(" "+BizRouteConfig.getBizRouteStr());
        sbSQL.append("	'' controlString1, ");
        sbSQL.append("	'' controlString2, ");
        sbSQL.append("	'' controlString1Id, ");
        sbSQL.append("	'' controlString2Id, ");
        sbSQL.append("			oab.* ");
        sbSQL.append("		FROM ");
        sbSQL.append("			OA_BusinessTripApplication oab, ");
        sbSQL.append("			oa_bizroute ob ");
        sbSQL.append("		WHERE ");
        sbSQL.append("			1 = 1 ");
        sbSQL.append("		AND oab.state = 0 ");
        sbSQL.append("		AND ob.Id_YWID = oab.Id ");
        sbSQL.append("		AND oab.id NOT IN ( ");
        sbSQL.append("			SELECT ");
        sbSQL.append("				YWID ");
        sbSQL.append("			FROM ");
        sbSQL.append("				workflow_routelist rl ");
        sbSQL.append("			WHERE ");
        sbSQL.append("				1 = 1 ");
        sbSQL.append("		) ");
        sbSQL.append(" 	) d  ");
        sbSQL.append(" WHERE  ");
        sbSQL.append(" 	1 = 1  ");
        sbSQL.append(" AND id = '" + Database.encodeSQL(id) + "'  ");

        //获取数据
        List li = MySQLDao.query(sbSQL.toString(), BusinessTripApplicationVO.class, null);


        return (BusinessTripApplicationVO) li.get(0);
    }


}
