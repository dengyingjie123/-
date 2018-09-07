package com.youngbook.service.oa.administration;

import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.administration.SealUsageWFAPO;
import com.youngbook.entity.po.wf.BizRoutePO;
import com.youngbook.entity.vo.oa.administration.SealUsageWFAVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 15-4-7
 * Time: 下午9:55
 * To change this template use File | Settings | File Templates.
 */
public class SealUsageWFAService extends BaseService {

    /**
     * 判断请求是否有效
     * 设置查询类型
     * 创建SQLk语句
     * 设置查询条件
     * 查询并返回分页数据
     * 返回格式化数据
     * 获取数据分页列表
     *
     * @param sealUsageWFAVO 需要封装的实体类
     * @param conditions     范围查询条件对象
     * @param request        客户请求对象
     * @return
     * @throws Exception 修改人:周海鸿
     *                   事件：2015-7-8
     *                   内容：修改SQL语句
     */
    public Pager list (SealUsageWFAVO sealUsageWFAVO, HttpServletRequest request, UserPO user, List<KVObject> conditions) throws Exception {

        //判断请求对象是否为空
        if ( request == null ) {
            throw new Exception ("当前请求无效");
        }

        //查询类型
        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //添加数据排序条件

        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append ("");

        sbSQL.append (" SELECT");
        sbSQL.append (" rl.CURRENTNODE currentNodeId,");
        sbSQL.append (" rl.id routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" oas.sid,");
        sbSQL.append (" oas.id,");
        sbSQL.append (" oas.state,");
        sbSQL.append (" oas.operatorId,");
        sbSQL.append (" oas.operateTime,");
        sbSQL.append (" oas.ApplicantId,");
        sbSQL.append (" oas.ApplicantName,");
        sbSQL.append (" oas.ApplicationTime,");
        sbSQL.append (" oas.ApplicationPurpose,");
        sbSQL.append (" oas.CheckerId,");
        sbSQL.append (" oas.CheckerName,");
        sbSQL.append (" oas.CheckTime,");
        sbSQL.append (" oas.CheckReplay,");
        sbSQL.append (" oas.StatusId,");
        sbSQL.append (" oas.sentto,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append ("   	ob.SubmitterId,");
        sbSQL.append ("   	ob.WORKFLOWID");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_sealusagewfa oas,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("  AND oas.state = 0");
        sbSQL.append ("  AND ob.Id_YWID = oas.Id");
        sbSQL.append ("  AND rl.YWID = oas.Id");
        sbSQL.append ("  AND sd1.id = ob.controlString1 ");
        sbSQL.append ("  AND sd2.id = ob.controlString2 ");
        sbSQL.append ("  AND rl.STATU IN (1)");

        sbSQL.append (" UNION");
        sbSQL.append (" SELECT");
        sbSQL.append (" 1 currentNodeId,");
        sbSQL.append (" 0 routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" oas.sid,");
        sbSQL.append (" oas.id,");
        sbSQL.append (" oas.state,");
        sbSQL.append (" oas.operatorId,");
        sbSQL.append (" oas.operateTime,");
        sbSQL.append (" oas.ApplicantId,");
        sbSQL.append (" oas.ApplicantName,");
        sbSQL.append (" oas.ApplicationTime,");
        sbSQL.append (" oas.ApplicationPurpose,");
        sbSQL.append (" oas.CheckerId,");
        sbSQL.append (" oas.CheckerName,");
        sbSQL.append (" oas.CheckTime,");
        sbSQL.append (" oas.CheckReplay,");
        sbSQL.append (" oas.StatusId,");
        sbSQL.append (" oas.sentto,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append ("   	ob.SubmitterId,");
        sbSQL.append ("   	ob.WORKFLOWID");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_sealusagewfa oas,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("  AND oas.state = 0");
        sbSQL.append ("  AND ob.Id_YWID = oas.Id");
        sbSQL.append (" AND rl.YWID = oas.Id");
        sbSQL.append ("  AND sd1.id = ob.controlString1 ");
        sbSQL.append ("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");


        sbSQL.append (" UNION");
        sbSQL.append (" SELECT");
        sbSQL.append (" 1 currentNodeId,");
        sbSQL.append (" 0 routeListId,");
        sbSQL.append (" -1 currentStatus,");
        sbSQL.append (" oas.sid,");
        sbSQL.append (" oas.id,");
        sbSQL.append (" oas.state,");
        sbSQL.append (" oas.operatorId,");
        sbSQL.append (" oas.operateTime,");
        sbSQL.append (" oas.ApplicantId,");
        sbSQL.append (" oas.ApplicantName,");
        sbSQL.append (" oas.ApplicationTime,");
        sbSQL.append (" oas.ApplicationPurpose,");
        sbSQL.append (" oas.CheckerId,");
        sbSQL.append (" oas.CheckerName,");
        sbSQL.append (" oas.CheckTime,");
        sbSQL.append (" oas.CheckReplay,");
        sbSQL.append (" oas.StatusId,");
        sbSQL.append (" oas.sentto,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append ("   	ob.SubmitterId,");
        sbSQL.append ("   	ob.WORKFLOWID");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_sealusagewfa oas,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("  AND oas.state = 0");
        sbSQL.append ("  AND ob.Id_YWID = oas.Id");
        sbSQL.append ("  AND sd1.id = ob.controlString1 ");
        sbSQL.append ("  AND sd2.id = ob.controlString2 ");
        sbSQL.append ("  AND oas.id NOT IN(");
        sbSQL.append (" SELECT");
        sbSQL.append ("  YWID");
        sbSQL.append ("  FROM");
        sbSQL.append ("  workflow_routelist rl");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append("  )");

        //设置查询条件
        conditions.add (new KVObject ("SubmitterId = ", "'" + Database.encodeSQL (user.getId ()) + "'"));
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //查询数据
        Pager pager = Pager.query (sbSQL.toString (), sealUsageWFAVO, conditions, request, queryType);

        //返回格式化数据
        return insertTitle (pager);
    }

    /**
     * * 判断请求是否有效
     * 设置查询类型
     * 创建SQLk语句
     * 设置查询条件
     * 查询并返回分页数据
     * 返回格式化数据
     * 获取数据分页列表
     * 获取等待审核数据列表
     *
     * @param sealUsageWFAVO 需要封装的实体类
     * @param conditions     范围查询条件对象
     * @param request        客户请求对象
     * @return
     * @throws Exception 修改人:周海鸿
     *                   事件：2015-7-8
     *                   内容：修改SQL语句
     */
    public Pager Waitlist (SealUsageWFAVO sealUsageWFAVO, HttpServletRequest request, UserPO user, List<KVObject> conditions) throws Exception {

        //判断请求对象是否为空
        if ( request == null ) {
            throw new Exception ("当前请求无效");
        }

        //查询类型
        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);


        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append ("");

        sbSQL.append (" SELECT");
        sbSQL.append (" rl.CURRENTNODE currentNodeId,");
        sbSQL.append (" rl.id routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" oas.sid,");
        sbSQL.append (" oas.id,");
        sbSQL.append (" oas.state,");
        sbSQL.append (" oas.operatorId,");
        sbSQL.append (" oas.operateTime,");
        sbSQL.append (" oas.ApplicantId,");
        sbSQL.append (" oas.ApplicantName,");
        sbSQL.append (" oas.ApplicationTime,");
        sbSQL.append (" oas.ApplicationPurpose,");
        sbSQL.append (" oas.CheckerId,");
        sbSQL.append (" oas.CheckerName,");
        sbSQL.append (" oas.CheckTime,");
        sbSQL.append (" oas.CheckReplay,");
        sbSQL.append (" oas.StatusId,");
        sbSQL.append (" oas.sentto,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append ("   	ob.SubmitterId,");
        sbSQL.append ("   	ob.WORKFLOWID");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_sealusagewfa oas,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("  AND oas.state = 0");
        sbSQL.append ("  AND ob.Id_YWID = oas.Id");
        sbSQL.append ("  AND rl.YWID = oas.Id");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append (" AND ob.controlString1  in (" + BizRouteConfig.getDepartmentParentId (user.getId ()) + ")");
        sbSQL.append ("  AND rl.STATU IN (1)");
        sbSQL.append ("   AND rl.CURRENTNODE IN (");
        sbSQL.append ("   	SELECT");
        sbSQL.append ("   		NODEID");
        sbSQL.append ("   	FROM");
        sbSQL.append ("   		workflow_participant p,");
        sbSQL.append ("   		workflow_role r");
        sbSQL.append ("   	WHERE");
        sbSQL.append ("   		p.WORKFLOWID = " +          Config.getSystemVariable("WORKFLOWID.Administation.SealUsageWFA"));
        sbSQL.append ("   	AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");
        sbSQL.append ("   	AND r.USERLIST LIKE '%|" + Database.encodeSQL (user.getId ()) + "|%'");
        sbSQL.append ("   )");

        //设置查询条件
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //查询数据
        Pager pager = Pager.query (sbSQL.toString (), sealUsageWFAVO, conditions, request, queryType);

        //格式化数据
        return insertTitle (pager);
    }

    /**
     * * 判断请求是否有效
     * 设置查询类型
     * 创建SQLk语句
     * 查询并返回分页数据
     * 返回格式化数据
     * 获取数据分页列表
     * <p/>
     * 获取已完成列表
     *
     * @param sealUsageWFAVO 需要封装的实体类
     * @param conditions     范围查询条件对象
     * @param request        客户请求对象
     * @return
     * @throws Exception 修改人:周海鸿
     *                   事件：2015-7-8
     *                   内容：修改SQL语句
     */
    public Pager Participantlist (SealUsageWFAVO sealUsageWFAVO, HttpServletRequest request, UserPO user, List<KVObject> conditions) throws Exception {

        //判断请求对象是否为空
        if ( request == null ) {
            throw new Exception ("当前请求无效");
        }

        //查询类型
        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);


        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append ("");
        sbSQL.append (" SELECT");
        sbSQL.append (" 	*");
        sbSQL.append (" FROM");
        sbSQL.append (" 	(");

        sbSQL.append (" SELECT");
        sbSQL.append (" rl.CURRENTNODE currentNodeId,");
        sbSQL.append (" rl.id routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" oas.sid,");
        sbSQL.append (" oas.id,");
        sbSQL.append (" oas.state,");
        sbSQL.append (" oas.operatorId,");
        sbSQL.append (" oas.operateTime,");
        sbSQL.append (" oas.ApplicantId,");
        sbSQL.append (" oas.ApplicantName,");
        sbSQL.append (" oas.ApplicationTime,");
        sbSQL.append (" oas.ApplicationPurpose,");
        sbSQL.append (" oas.CheckerId,");
        sbSQL.append (" oas.CheckerName,");
        sbSQL.append (" oas.CheckTime,");
        sbSQL.append (" oas.CheckReplay,");
        sbSQL.append (" oas.StatusId,");
        sbSQL.append (" oas.sentto,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id ");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_sealusagewfa oas,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("  AND oas.state = 0");
        sbSQL.append ("  AND ob.Id_YWID = oas.Id");
        sbSQL.append ("  AND rl.YWID = oas.Id");
        sbSQL.append (" AND sd1.id = ob.controlString1 ");
        sbSQL.append (" AND sd2.id = ob.controlString2 ");
        sbSQL.append ("  AND rl.STATU IN (1)");

        sbSQL.append (" UNION");
        sbSQL.append (" SELECT");
        sbSQL.append (" 1 currentNodeId,");
        sbSQL.append (" 0 routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" oas.sid,");
        sbSQL.append (" oas.id,");
        sbSQL.append (" oas.state,");
        sbSQL.append (" oas.operatorId,");
        sbSQL.append (" oas.operateTime,");
        sbSQL.append (" oas.ApplicantId,");
        sbSQL.append (" oas.ApplicantName,");
        sbSQL.append (" oas.ApplicationTime,");
        sbSQL.append (" oas.ApplicationPurpose,");
        sbSQL.append (" oas.CheckerId,");
        sbSQL.append (" oas.CheckerName,");
        sbSQL.append (" oas.CheckTime,");
        sbSQL.append (" oas.CheckReplay,");
        sbSQL.append (" oas.StatusId,");
        sbSQL.append (" oas.sentto,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id ");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_sealusagewfa oas,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("  AND oas.state = 0");
        sbSQL.append ("  AND ob.Id_YWID = oas.Id");
        sbSQL.append ("  AND rl.YWID = oas.Id");
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
        sbSQL.append (" 		1 = 1");
        //添加业务编号
        sbSQL.append (" 	AND WORKFLOWID = " +          Config.getSystemVariable("WORKFLOWID.Administation.SealUsageWFA"));
        sbSQL.append (" 	AND PARTICIPANT = '" + Database.encodeSQL (user.getId ()) + "'");
        sbSQL.append (" ) ");

        //设置查询条件
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //添加数据排序条件
        Pager pager = Pager.query (sbSQL.toString (), sealUsageWFAVO, conditions, request, queryType);

        //格式化数据
        return insertTitle (pager);
    }

    /**
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 对业务表进行添更新操作
     * 返回影响行数
     * <p/>
     * 添加并修改用章信息
     * <p/>
     * 修改人：周海鸿
     * 修改时间：2014-7-1
     * 修改时间： 添加注释，添加业务申请人
     *
     * @param sealUsageWFA 数据实体类
     * @param user         当前操作用户类
     * @param conn         数据链接类
     * @return
     * @throws Exception
     */
    public int insertOrUpdate (SealUsageWFAPO sealUsageWFA, SealUsageWFAVO sealUsageWFAVO, UserPO user, Connection conn) throws Exception {
        //判断吧数据的有效性
        // 数据操作实体类是否为空
        if ( sealUsageWFA == null ) {
            throw new Exception ("用章数据提交失败");
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
        //判断id是否为null
        if ( sealUsageWFA.getId ().equals ("") ) {
            sealUsageWFA.setSid (MySQLDao.getMaxSid ("oa_sealusagewfa", conn));
            sealUsageWFA.setId (IdUtils.getUUID32 ());
            sealUsageWFA.setState (Config.STATE_CURRENT);
            sealUsageWFA.setOperatorId (user.getId ());
            sealUsageWFA.setOperateTime (TimeUtils.getNow ());

            count = MySQLDao.insert (sealUsageWFA, conn);
            /**
             *修改人周海鸿
             * 修改时间：2015-6-29
             */
            /* 修改人：周海鸿
            * 修改时间：2014-7-1
            * 修改时间： 添加注释，添加业务申请人
            */
            // 返回影响行数为1
            if (count == 1) {
                count = BizRouteService.insertOrUpdate(sealUsageWFA.getId(), sealUsageWFAVO.getControlString3 (),sealUsageWFAVO.getControlString1 (), sealUsageWFAVO.getControlString2 (),
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Administation.SealUsageWFA")), true, user, conn);
            }
        }
        // 更新
        else {
            SealUsageWFAPO temp = new SealUsageWFAPO ();
            temp.setSid (sealUsageWFA.getSid ());
            temp = MySQLDao.load (temp, SealUsageWFAPO.class);
            temp.setState (Config.STATE_UPDATE);
            count = MySQLDao.update (temp, conn);

            //判断影响函数是不是1条
            if ( count == 1 ) {
                sealUsageWFA.setSid (MySQLDao.getMaxSid ("oa_sealusagewfa", conn));
                sealUsageWFA.setState (Config.STATE_CURRENT);
                sealUsageWFA.setOperatorId (user.getId ());
                sealUsageWFA.setOperateTime (TimeUtils.getNow ());
                count = MySQLDao.insert (sealUsageWFA, conn);
                /**
                 *修改人周海鸿
                 * 修改时间：2015-6-29
                 */
                /* 修改人：周海鸿
                * 修改时间：2014-7-1
                * 修改时间： 添加注释，添加业务申请人
                */
                // 返回影响行数为1
                if (count == 1) {
                    count = BizRouteService.insertOrUpdate(sealUsageWFA.getId(), sealUsageWFAVO.getControlString3(),sealUsageWFAVO.getControlString1(), sealUsageWFAVO.getControlString2(),
                            Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Administation.SealUsageWFA")), false, user, conn);
                }
            }
        }

        if ( count != 1 ) {
            throw new Exception ("数据库异常");
        }

        return count;
    }

    /**
     *
     * 判断传入的数据是否为null
     * 执行更新书状态操作
     * 根据条改编数据的状态
     * @param sealUsageWFA
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete (SealUsageWFAPO sealUsageWFA, UserPO user, Connection conn) throws Exception {
        if(sealUsageWFA == null){
            MyException.newInstance ("用章信息不能为null");
        }

        int count = 0;
        //设置数据
        sealUsageWFA = MySQLDao.load (sealUsageWFA, SealUsageWFAPO.class);
        sealUsageWFA.setState (Config.STATE_UPDATE);

        //更新数据
        count = MySQLDao.update (sealUsageWFA, conn);

        //添加删除数据
        if ( count == 1 ) {
            sealUsageWFA.setSid (MySQLDao.getMaxSid ("oa_sealusagewfa", conn));
            sealUsageWFA.setState (Config.STATE_DELETE);
            sealUsageWFA.setOperateTime (TimeUtils.getNow ());
            sealUsageWFA.setOperatorId (user.getId ());
            count = MySQLDao.insert (sealUsageWFA, conn);
        }

        if ( count != 1 ) {
            throw new Exception ("数据库异常");
        }

        return count;
    }

    /**
     *格式化分页数据
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
            SealUsageWFAVO sealUsageWFAVO = (SealUsageWFAVO) iteratot.next ();

            try {
                //判断数据状态是都为完成状态
                if ( "5".equals (sealUsageWFAVO.getCurrentStatus ()) ) {
                    title = "已完成";
                }else if ("3".equals(sealUsageWFAVO.getCurrentStatus())) {
                    title = "中止";
                }
                //非完成状态
                else {
                    //判断阶段是否是开始节点
                    if ( "1".equals (sealUsageWFAVO.getCurrentNodeId ()) ) {
                        title = "申请";
                    }
                    //节点为中间节点
                    else {
                        //根据业务流编号与节点编号获取节点title
                        title = clientApplications.getNodeTitle (Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Administation.SealUsageWFA")), Integer.valueOf (sealUsageWFAVO.getCurrentNodeId ()));
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace ();
            }
            //添加标题
            sealUsageWFAVO.setCurrentNodeTitle (title);

            //添加到临时集合中
            tempList.add (sealUsageWFAVO);
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

    /*修改：周海鸿
    * 时间:2015-7-20
    * 内容：获取用章打印数据*/
    public SealUsageWFAVO getPrintDate (String id) throws Exception {
        //判断数据有效性
        if ( "".equals (id) || null == id ) {
            throw new Exception ("用章情况打印数据获取失败");
        }

        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append (" SELECT  ");
        sbSQL.append (" 	*  ");
        sbSQL.append (" FROM  ");
        sbSQL.append (" 	(  ");
        sbSQL.append (" SELECT");
        sbSQL.append (" rl.CURRENTNODE currentNodeId,");
        sbSQL.append (" rl.id routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" oas.sid,");
        sbSQL.append (" oas.id,");
        sbSQL.append (" oas.state,");
        sbSQL.append (" oas.operatorId,");
        sbSQL.append (" oas.operateTime,");
        sbSQL.append (" oas.ApplicantId,");
        sbSQL.append (" oas.ApplicantName,");
        sbSQL.append (" oas.ApplicationTime,");
        sbSQL.append (" oas.ApplicationPurpose,");
        sbSQL.append (" oas.CheckerId,");
        sbSQL.append (" oas.CheckerName,");
        sbSQL.append (" oas.CheckTime,");
        sbSQL.append (" oas.CheckReplay,");
        sbSQL.append (" oas.StatusId,");
        sbSQL.append (" oas.sentto,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id ");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_sealusagewfa oas,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("  AND oas.state = 0");
        sbSQL.append ("  AND ob.Id_YWID = oas.Id");
        sbSQL.append ("  AND rl.YWID = oas.Id");
        sbSQL.append ("  AND sd1.id = ob.controlString1 ");
        sbSQL.append ("  AND sd2.id = ob.controlString2 ");
        sbSQL.append ("  AND rl.STATU IN (1)");

        sbSQL.append (" UNION");
        sbSQL.append (" SELECT");
        sbSQL.append (" 1 currentNodeId,");
        sbSQL.append (" 0 routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" oas.sid,");
        sbSQL.append (" oas.id,");
        sbSQL.append (" oas.state,");
        sbSQL.append (" oas.operatorId,");
        sbSQL.append (" oas.operateTime,");
        sbSQL.append (" oas.ApplicantId,");
        sbSQL.append (" oas.ApplicantName,");
        sbSQL.append (" oas.ApplicationTime,");
        sbSQL.append (" oas.ApplicationPurpose,");
        sbSQL.append (" oas.CheckerId,");
        sbSQL.append (" oas.CheckerName,");
        sbSQL.append (" oas.CheckTime,");
        sbSQL.append (" oas.CheckReplay,");
        sbSQL.append (" oas.StatusId,");
        sbSQL.append (" oas.sentto,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id ");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_sealusagewfa oas,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("  AND oas.state = 0");
        sbSQL.append ("  AND ob.Id_YWID = oas.Id");
        sbSQL.append (" AND rl.YWID = oas.Id");
        sbSQL.append ("  AND sd1.id = ob.controlString1 ");
        sbSQL.append ("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");


        sbSQL.append (" UNION");
        sbSQL.append (" SELECT");
        sbSQL.append (" 1 currentNodeId,");
        sbSQL.append (" 0 routeListId,");
        sbSQL.append (" -1 currentStatus,");
        sbSQL.append (" oas.sid,");
        sbSQL.append (" oas.id,");
        sbSQL.append (" oas.state,");
        sbSQL.append (" oas.operatorId,");
        sbSQL.append (" oas.operateTime,");
        sbSQL.append (" oas.ApplicantId,");
        sbSQL.append (" oas.ApplicantName,");
        sbSQL.append (" oas.ApplicationTime,");
        sbSQL.append (" oas.ApplicationPurpose,");
        sbSQL.append (" oas.CheckerId,");
        sbSQL.append (" oas.CheckerName,");
        sbSQL.append (" oas.CheckTime,");
        sbSQL.append (" oas.CheckReplay,");
        sbSQL.append (" oas.StatusId,");
        sbSQL.append (" oas.sentto,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName  as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id ");
        sbSQL.append (" FROM");
        sbSQL.append (" oa_sealusagewfa oas,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("  AND oas.state = 0");
        sbSQL.append ("  AND ob.Id_YWID = oas.Id");
        sbSQL.append ("  AND sd1.id = ob.controlString1 ");
        sbSQL.append ("  AND sd2.id = ob.controlString2 ");
        sbSQL.append ("  AND oas.id NOT IN(");
        sbSQL.append (" SELECT");
        sbSQL.append ("  YWID");
        sbSQL.append ("  FROM");
        sbSQL.append ("  workflow_routelist rl");
        sbSQL.append ("  WHERE");
        sbSQL.append ("   1 = 1");
        sbSQL.append ("   )");
        sbSQL.append (" 	) d  ");
        sbSQL.append (" WHERE  ");
        sbSQL.append (" 	1 = 1  ");
        sbSQL.append (" AND id = '" + Database.encodeSQL (id) + "'  ");

        //获取数据
        List li = MySQLDao.query (sbSQL.toString (), SealUsageWFAVO.class, null);



        return (SealUsageWFAVO) li.get (0);

    }

}
