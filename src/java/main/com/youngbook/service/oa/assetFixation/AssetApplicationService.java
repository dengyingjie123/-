package com.youngbook.service.oa.assetFixation;

/**
 * Created by Jepson on 2015/6/5.
 */
// 主表的 Service 代码

import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.assetFixation.AssetApplicationPO;
import com.youngbook.entity.po.wf.BizRoutePO;
import com.youngbook.entity.vo.oa.assetFixation.AssetApplicationVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 创建一个AssetApplicationService 类 继承BaseService类
 *
 * @author Codemaker
 */

public class AssetApplicationService extends BaseService {
    /**
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 对业务表进行添更新操作
     * 返回影响行数
     * 添加或修改数据，并修改数据状态
     *
     * @param assetApplication
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(AssetApplicationPO assetApplication, AssetApplicationVO assetApplicationVO, UserPO user, Connection conn) throws Exception {
        //判断数据有效性
        if (assetApplication == null) {
            throw new Exception("提交失败");
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
        //判断id是否为null
        if (assetApplication.getId().equals("")) {

            assetApplication.setSid(MySQLDao.getMaxSid("oa_AssetApplication", conn));
            assetApplication.setId(IdUtils.getUUID32());
            assetApplication.setState(Config.STATE_CURRENT);
            assetApplication.setOperatorId(user.getId());
            assetApplication.setOperateTime(TimeUtils.getNow());

            //添加
            count = MySQLDao.insert(assetApplication, conn);

            /**
             * 2015-6-24 小周
             * 当数据添加成功时 向流程表中添加数据
             *  修改人：周海鸿
             *  时间：2015-7-1
             *  事情：在固资产申请成功时向业务表添加申请人信息
             *
             */
            if (count == 1) {
                count = BizRouteService.insertOrUpdate(assetApplication.getId(), assetApplicationVO.getControlString3(), assetApplicationVO.getControlString1(), assetApplicationVO.getControlString2(),
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.AssetFixation.AsserApplication")), true, user, conn);
            }

        }
        // 更新
        else {
            AssetApplicationPO temp = new AssetApplicationPO();
            temp.setSid(assetApplication.getSid());
            temp = MySQLDao.load(temp, AssetApplicationPO.class);
            temp.setState(Config.STATE_UPDATE);

            count = MySQLDao.update(temp, conn);

            //更新业务数据
            if (count == 1) {
                assetApplication.setSid(MySQLDao.getMaxSid("oa_AssetApplication", conn));
                assetApplication.setState(Config.STATE_CURRENT);
                assetApplication.setOperatorId(user.getId());
                assetApplication.setOperateTime(TimeUtils.getNow());

                /**
                 * 修改人周海鸿
                 * 修改时间：2015-6-24
                 * 修改事件增减总金额的计算
                 */
                AssetItemService Itemservice = new AssetItemService();
                assetApplication.setMoneys(Double.parseDouble(Itemservice.getMoneys(assetApplication.getId())));

                count = MySQLDao.insert(assetApplication, conn);
                if (count == 1) {

                    count = BizRouteService.insertOrUpdate(assetApplication.getId(), assetApplicationVO.getControlString3(), assetApplicationVO.getControlString1(), assetApplicationVO.getControlString2(),
                            Integer.parseInt(Config.getSystemVariable("WORKFLOWID.AssetFixation.AsserApplication")), false, user, conn);
                }
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 判断id是否为null
     * 创建查询数据
     * <p/>
     * 根据制定的ID获取数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public AssetApplicationPO loadAssetApplicationPO(String id) throws Exception {
        //创建查询数据
        AssetApplicationPO po = new AssetApplicationPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);

        //获取数据
        po = MySQLDao.load(po, AssetApplicationPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     *
     * @param assetApplication
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(AssetApplicationPO assetApplication, UserPO user, Connection conn) throws Exception {
        int count = 0;

        assetApplication.setState(Config.STATE_CURRENT);
        assetApplication = MySQLDao.load(assetApplication, AssetApplicationPO.class);
        assetApplication.setState(Config.STATE_UPDATE);

        //更新数据状态
        count = MySQLDao.update(assetApplication, conn);

        if (count == 1) {

            //设置数据
            assetApplication.setSid(MySQLDao.getMaxSid("oa_AssetApplication", conn));
            assetApplication.setState(Config.STATE_DELETE);
            assetApplication.setOperateTime(TimeUtils.getNow());
            assetApplication.setOperatorId(user.getId());

            //添加数据
            count = MySQLDao.insert(assetApplication, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }


    /**
     * 2015 -6 -24
     * 周海鸿
     * 实现获取列表数据
     *
     * @param assetApplicationVO
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager list(AssetApplicationVO assetApplicationVO, UserPO user, List<KVObject> conditions) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //添加用户条件
        conditions.add(new KVObject("SubmitterId = ", "'" + Database.encodeSQL(user.getId()) + "'"));
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取分页对象
        Pager pager = Pager.query(getSQL(user), assetApplicationVO, conditions, request, queryType);
        //返回数据
        return insertTitle(pager);
    }

    /**
     * 2015 -6 -24
     * 周海鸿
     * 实现获取列表数据
     *
     * @param assetApplicationVO
     * @param conditions
     * @return
     * @throws Exception 修改人：周海鸿
     *                   时间：2015-7-9
     *                   内容：添加等待审核列表
     */
    public Pager Waitlist(AssetApplicationVO assetApplicationVO, UserPO user, List<KVObject> conditions) throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();

        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取分页对象
        Pager pager = Pager.query(getWaitSQL(user), assetApplicationVO, conditions, request, queryType);

        //返回格式化数据
        return insertTitle(pager);
    }

    /**
     * 2015 -6 -24
     * 周海鸿
     * 实现获取列表数据
     *
     * @param assetApplicationVO
     * @param conditions
     * @return
     * @throws Exception 修改人：周海鸿
     *                   时间：2015-7-9
     *                   内容：添加已完成列表
     */
    public Pager Participantlist(AssetApplicationVO assetApplicationVO, UserPO user, List<KVObject> conditions) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取分页对象
        Pager pager = Pager.query(getParticipantSQL(user), assetApplicationVO, conditions, request, queryType);

        //返回格式化数据
        return insertTitle(pager);
    }

    /**
     * 创建一个组织SQL
     * 语句的方法
     *
     * @return
     */
    private String getSQL(UserPO user) throws Exception {

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("");

        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oa.Id,");
        sbSQL.append(" oa.Sid,");
        sbSQL.append(" oa.State,");
        sbSQL.append(" oa.OperatorId,");
        sbSQL.append(" oa.OperateTime,");
        sbSQL.append(" oa.AssetTypeId,");
        sbSQL.append(" oa.DepartmentId,");
        sbSQL.append(" oa.ApplicantId,");
        sbSQL.append(" oa.applicationDepartmentId,");
        sbSQL.append(" oa.Purpose,");
        sbSQL.append(" oa.ProductName,");
        sbSQL.append(" oa.moneys,");

        sbSQL.append(" kv.V AS assetTypeName,");
        sbSQL.append(" u1.NAME AS applicantName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	ob.SubmitterId, ");
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" oa_AssetApplication oa,");

        sbSQL.append(" system_kv kv,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" system_user u1");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND oa.state = 0");
        sbSQL.append(" AND u1.state = 0");

        sbSQL.append(" AND kv.groupName = 'OA_AssetApplicationType'");
        sbSQL.append(" AND kv.k = oa.AssetTypeId");
        sbSQL.append(" AND u1.id = oa.applicantId");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" AND ob.Id_YWID = oa.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.YWID = oa.id");
        sbSQL.append(" UNION");

        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" -1 currentNodeId,");
        sbSQL.append(" -1 routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oa.Id,");
        sbSQL.append(" oa.Sid,");
        sbSQL.append(" oa.State,");
        sbSQL.append(" oa.OperatorId,");
        sbSQL.append(" oa.OperateTime,");
        sbSQL.append(" oa.AssetTypeId,");
        sbSQL.append(" oa.applicationDepartmentId,");
        sbSQL.append(" oa.DepartmentId,");
        sbSQL.append(" oa.ApplicantId,");
        sbSQL.append(" oa.Purpose,");
        sbSQL.append(" oa.ProductName,");
        sbSQL.append(" oa.moneys,");

        sbSQL.append(" kv.V AS assetTypeName,");
        sbSQL.append(" u1.NAME AS applicantName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	ob.SubmitterId, ");
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" oa_AssetApplication oa,");

        sbSQL.append(" system_kv kv,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" system_user u1");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND oa.state = 0");
        sbSQL.append(" AND u1.state = 0");

        sbSQL.append(" AND kv.groupName = 'OA_AssetApplicationType'");
        sbSQL.append(" AND kv.k = oa.AssetTypeId");
        sbSQL.append(" AND u1.id = oa.applicantId");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");

        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");
        sbSQL.append(" AND ob.Id_YWID = oa.Id");
        sbSQL.append(" AND rl.YWID = oa.id");

        sbSQL.append(" UNION ");

        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" 1 currentNodeId,");
        sbSQL.append(" 0 routeListId,");
        sbSQL.append(" -1 currentStatus,");
        sbSQL.append(" oa.Id,");
        sbSQL.append(" oa.Sid,");
        sbSQL.append(" oa.State,");
        sbSQL.append(" oa.OperatorId,");
        sbSQL.append(" oa.OperateTime,");
        sbSQL.append(" oa.AssetTypeId,");
        sbSQL.append(" oa.applicationDepartmentId,");
        sbSQL.append(" oa.DepartmentId,");
        sbSQL.append(" oa.ApplicantId,");
        sbSQL.append(" oa.Purpose,");
        sbSQL.append(" oa.ProductName,");
        sbSQL.append(" oa.moneys,");

        sbSQL.append(" kv.V AS assetTypeName,");
        sbSQL.append(" u1.NAME AS applicantName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	ob.SubmitterId, ");
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" oa_AssetApplication oa,");

        sbSQL.append(" system_kv kv,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob,");
        sbSQL.append(" system_user u1");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND oa.state = 0");
        sbSQL.append(" AND u1.state = 0");

        sbSQL.append(" AND kv.groupName = 'OA_AssetApplicationType'");
        sbSQL.append(" AND kv.k = oa.AssetTypeId");
        sbSQL.append(" AND u1.id = oa.applicantId");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" AND ob.Id_YWID = oa.Id");
        sbSQL.append(" AND oa.id NOT IN (");
        sbSQL.append(" SELECT");
        sbSQL.append(" YWID");
        sbSQL.append(" FROM");
        sbSQL.append(" workflow_routelist rl");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" )");
        return sbSQL.toString();
    }

    /**
     * 创建一个获取等待列表SQL
     * 语句的方法
     *
     * @return
     */
    private String getWaitSQL(UserPO user) throws Exception {

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("");

        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oa.Id,");
        sbSQL.append(" oa.Sid,");
        sbSQL.append(" oa.State,");
        sbSQL.append(" oa.OperatorId,");
        sbSQL.append(" oa.OperateTime,");
        sbSQL.append(" oa.AssetTypeId,");
        sbSQL.append(" oa.DepartmentId,");
        sbSQL.append(" oa.ApplicantId,");
        sbSQL.append(" oa.applicationDepartmentId,");
        sbSQL.append(" oa.Purpose,");
        sbSQL.append(" oa.ProductName,");
        sbSQL.append(" oa.moneys,");

        sbSQL.append(" kv.V AS assetTypeName,");
        sbSQL.append(" u1.NAME AS applicantName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" oa_AssetApplication oa,");

        sbSQL.append(" system_kv kv,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" system_user u1");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND oa.state = 0");
        sbSQL.append(" AND u1.state = 0");

        sbSQL.append(" AND kv.groupName = 'OA_AssetApplicationType'");
        sbSQL.append(" AND kv.k = oa.AssetTypeId");
        sbSQL.append(" AND u1.id = oa.applicantId");
        sbSQL.append(" AND ob.Id_YWID = oa.Id");
        sbSQL.append(" AND rl.YWID = oa.id");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND ob.controlString1  in (" + BizRouteConfig.getDepartmentParentId(user.getId()) + ")");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append("   AND rl.CURRENTNODE IN (");
        sbSQL.append("   	SELECT");
        sbSQL.append("   		NODEID");
        sbSQL.append("   	FROM");
        sbSQL.append("   		workflow_participant p,");
        sbSQL.append("   		workflow_role r");
        sbSQL.append("   	WHERE");
        sbSQL.append("   		p.WORKFLOWID = " + Config.getSystemVariable("WORKFLOWID.AssetFixation.AsserApplication"));
        sbSQL.append("   	AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");
        sbSQL.append("   	AND r.USERLIST LIKE '%|" + Database.encodeSQL(user.getId()) + "|%'");
        sbSQL.append("   )");
        return sbSQL.toString();
    }

    /**
     * 创建一个组织SQL
     * 语句的方法
     *
     * @return
     */
    private String getParticipantSQL(UserPO user) throws Exception {

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("");
        sbSQL.append(" SELECT");
        sbSQL.append(" 	*");
        sbSQL.append(" FROM");
        sbSQL.append(" 	(");
        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oa.Id,");
        sbSQL.append(" oa.Sid,");
        sbSQL.append(" oa.State,");
        sbSQL.append(" oa.OperatorId,");
        sbSQL.append(" oa.OperateTime,");
        sbSQL.append(" oa.AssetTypeId,");
        sbSQL.append(" oa.DepartmentId,");
        sbSQL.append(" oa.ApplicantId,");
        sbSQL.append(" oa.applicationDepartmentId,");
        sbSQL.append(" oa.Purpose,");
        sbSQL.append(" oa.ProductName,");
        sbSQL.append(" oa.moneys,");

        sbSQL.append(" kv.V AS assetTypeName,");
        sbSQL.append(" u1.NAME AS applicantName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" oa_AssetApplication oa,");

        sbSQL.append(" system_kv kv,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" system_user u1");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND oa.state = 0");
        sbSQL.append(" AND u1.state = 0");

        sbSQL.append(" AND kv.groupName = 'OA_AssetApplicationType'");
        sbSQL.append(" AND kv.k = oa.AssetTypeId");
        sbSQL.append(" AND u1.id = oa.applicantId");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND ob.Id_YWID = oa.Id");
        sbSQL.append(" AND rl.YWID = oa.id");
        sbSQL.append(" UNION");

        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" -1 currentNodeId,");
        sbSQL.append(" -1 routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oa.Id,");
        sbSQL.append(" oa.Sid,");
        sbSQL.append(" oa.State,");
        sbSQL.append(" oa.OperatorId,");
        sbSQL.append(" oa.OperateTime,");
        sbSQL.append(" oa.AssetTypeId,");
        sbSQL.append(" oa.applicationDepartmentId,");
        sbSQL.append(" oa.DepartmentId,");
        sbSQL.append(" oa.ApplicantId,");
        sbSQL.append(" oa.Purpose,");
        sbSQL.append(" oa.ProductName,");
        sbSQL.append(" oa.moneys,");

        sbSQL.append(" kv.V AS assetTypeName,");
        sbSQL.append(" u1.NAME AS applicantName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" oa_AssetApplication oa,");

        sbSQL.append(" system_kv kv,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" system_user u1");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND oa.state = 0");
        sbSQL.append(" AND u1.state = 0");

        sbSQL.append(" AND kv.groupName = 'OA_AssetApplicationType'");
        sbSQL.append(" AND kv.k = oa.AssetTypeId");
        sbSQL.append(" AND u1.id = oa.applicantId");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");

        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND ob.Id_YWID = oa.Id");
        sbSQL.append(" AND rl.YWID = oa.id");

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
        sbSQL.append(" 	AND WORKFLOWID = " + Config.getSystemVariable("WORKFLOWID.AssetFixation.AsserApplication"));
        sbSQL.append(" 	AND PARTICIPANT = '" + Database.encodeSQL(user.getId()) + "'");
        sbSQL.append(" )");
        return sbSQL.toString();
    }

    /**
     * 根据分页数据 格式化数据
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
            AssetApplicationVO hrLeavePO = (AssetApplicationVO) iteratot.next();

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
                    if ("1".equals(hrLeavePO.getCurrentNodeId())) {
                        title = "申请";
                    }
                    //节点为中间节点
                    else {
                        //根据业务流编号与节点编号获取节点title
                        title = clientApplications.getNodeTitle(Integer.parseInt(Config.getSystemVariable("WORKFLOWID.AssetFixation.AsserApplication")), Integer.valueOf(hrLeavePO.getCurrentNodeId()));

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

    /*修改:周海鸿
    * 时间：2015-7-20
    * 内容：获取打印数据*/
    public AssetApplicationVO getPrintDate(String id) throws Exception {

        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("固定资产打印数据错误");
        }

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("");
        sbSQL.append(" SELECT");
        sbSQL.append("* from (");
        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oa.Id,");
        sbSQL.append(" oa.Sid,");
        sbSQL.append(" oa.State,");
        sbSQL.append(" oa.OperatorId,");
        sbSQL.append(" oa.OperateTime,");
        sbSQL.append(" oa.AssetTypeId,");
        sbSQL.append(" oa.DepartmentId,");
        sbSQL.append(" oa.ApplicantId,");
        sbSQL.append(" oa.applicationDepartmentId,");
        sbSQL.append(" oa.Purpose,");
        sbSQL.append(" oa.ProductName,");
        sbSQL.append(" oa.moneys,");
        sbSQL.append(" oa.assApplicantTime,");

        sbSQL.append(" kv.V AS assetTypeName,");
        sbSQL.append(" u1.NAME AS applicantName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	ob.SubmitterId, ");
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" oa_AssetApplication oa,");

        sbSQL.append(" system_department sd1,");
        sbSQL.append(" system_department sd2,");
        sbSQL.append(" system_kv kv,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob,");
        sbSQL.append(" system_user u1");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND oa.state = 0");
        sbSQL.append(" AND u1.state = 0");

        sbSQL.append(" AND kv.groupName = 'OA_AssetApplicationType'");
        sbSQL.append(" AND kv.k = oa.AssetTypeId");
        sbSQL.append(" AND u1.id = oa.applicantId");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" AND ob.Id_YWID = oa.Id");
        sbSQL.append(" AND rl.YWID = oa.id");
        sbSQL.append(" UNION");

        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" -1 currentNodeId,");
        sbSQL.append(" -1 routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oa.Id,");
        sbSQL.append(" oa.Sid,");
        sbSQL.append(" oa.State,");
        sbSQL.append(" oa.OperatorId,");
        sbSQL.append(" oa.OperateTime,");
        sbSQL.append(" oa.AssetTypeId,");
        sbSQL.append(" oa.applicationDepartmentId,");
        sbSQL.append(" oa.DepartmentId,");
        sbSQL.append(" oa.ApplicantId,");
        sbSQL.append(" oa.Purpose,");
        sbSQL.append(" oa.ProductName,");
        sbSQL.append(" oa.moneys,");
        sbSQL.append(" oa.assApplicantTime,");

        sbSQL.append(" kv.V AS assetTypeName,");
        sbSQL.append(" u1.NAME AS applicantName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	ob.SubmitterId, ");
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" oa_AssetApplication oa,");

        sbSQL.append(" system_department sd1,");
        sbSQL.append(" system_department sd2,");
        sbSQL.append(" system_kv kv,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob,");
        sbSQL.append(" system_user u1");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND oa.state = 0");
        sbSQL.append(" AND u1.state = 0");

        sbSQL.append(" AND kv.groupName = 'OA_AssetApplicationType'");
        sbSQL.append(" AND kv.k = oa.AssetTypeId");
        sbSQL.append(" AND u1.id = oa.applicantId");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");

        sbSQL.append(" AND ob.Id_YWID = oa.Id");
        sbSQL.append(" AND rl.YWID = oa.id");

        sbSQL.append(" UNION ");

        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append(" 1 currentNodeId,");
        sbSQL.append(" 0 routeListId,");
        sbSQL.append(" -1 currentStatus,");
        sbSQL.append(" oa.Id,");
        sbSQL.append(" oa.Sid,");
        sbSQL.append(" oa.State,");
        sbSQL.append(" oa.OperatorId,");
        sbSQL.append(" oa.OperateTime,");
        sbSQL.append(" oa.AssetTypeId,");
        sbSQL.append(" oa.applicationDepartmentId,");
        sbSQL.append(" oa.DepartmentId,");
        sbSQL.append(" oa.ApplicantId,");
        sbSQL.append(" oa.Purpose,");
        sbSQL.append(" oa.ProductName,");
        sbSQL.append(" oa.moneys,");
        sbSQL.append(" oa.assApplicantTime,");

        sbSQL.append(" kv.V AS assetTypeName,");
        sbSQL.append(" u1.NAME AS applicantName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	ob.SubmitterId, ");
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" oa_AssetApplication oa,");

        sbSQL.append(" system_department sd1,");
        sbSQL.append(" system_department sd2,");
        sbSQL.append(" system_kv kv,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" oa_bizroute ob,");
        sbSQL.append(" system_user u1");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND oa.state = 0");
        sbSQL.append(" AND u1.state = 0");

        sbSQL.append(" AND kv.groupName = 'OA_AssetApplicationType'");
        sbSQL.append(" AND kv.k = oa.AssetTypeId");
        sbSQL.append(" AND u1.id = oa.applicantId");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (1)");
        sbSQL.append(" AND ob.Id_YWID = oa.Id");
        sbSQL.append(" AND oa.id NOT IN (");
        sbSQL.append(" SELECT");
        sbSQL.append(" YWID");
        sbSQL.append(" FROM");
        sbSQL.append(" workflow_routelist rl");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" )");
        sbSQL.append(" ) b ");
        sbSQL.append("where 1=1 ");
        sbSQL.append("AND id = '" + Database.encodeSQL(id) + "'");

        //获取数据
        List li = MySQLDao.query(sbSQL.toString(), AssetApplicationVO.class, null);


        return (AssetApplicationVO) li.get(0);
    }
}
