package com.youngbook.service.oa.Information;

import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.wf.admin.Action;
import com.youngbook.common.wf.admin.RouteList;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.common.wf.services.IBizService;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.Information.InformationSubmitted2PO;
import com.youngbook.entity.po.oa.Information.InformationSubmittedItem2PO;
import com.youngbook.entity.po.oa.Information.InformationSubmittedItem2Status;
import com.youngbook.entity.po.wf.BizRoutePO;
import com.youngbook.entity.vo.oa.Information.InformationSubmitted2VO;
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
public class InformationSubmitted2Service extends BaseService implements IBizService {

    /**
     * 判断请求是否有效
     * 设置查询类型
     * 创建SQLk语句
     * 设置查询条件
     * 查询并返回分页数据
     * 返回格式化数据
     * 获取数据分页列表
     *
     * @param informationSubmitted2VO 需要封装的实体类
     * @param conditions              范围查询条件对象
     * @param request                 客户请求对象
     * @return
     * @throws Exception 修改人:周海鸿
     *                   事件：2015-7-8
     *                   内容：修改SQL语句
     */
    public Pager list(InformationSubmitted2VO informationSubmitted2VO, HttpServletRequest request, UserPO user, List<KVObject> conditions) throws Exception {

        //判断请求对象是否为空
        if (request == null) {
            throw new Exception("当前请求无效");
        }

        //查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //添加数据排序条件

        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("");

        sbSQL.append(" SELECT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oas.sid,");
        sbSQL.append(" oas.id,");
        sbSQL.append(" oas.state,");
        sbSQL.append(" oas.operatorId,");
        sbSQL.append(" oas.operateTime,");
        sbSQL.append(" oas.ApplicantId,");
        sbSQL.append(" oas.ApplicantName,");
        sbSQL.append(" oas.ApplicationTime,");
        sbSQL.append(" oas.ApplicationPurpose,");
        sbSQL.append(" oas.sentto,");
        sbSQL.append(" oas.isOut,");
        sbSQL.append(" oas.IsAllReceive,");
        sbSQL.append(" oas.IsAllOutBack,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("   	ob.SubmitterId,");
        sbSQL.append("   	ob.WORKFLOWID");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_InformationSubmitted2 oas,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  AND oas.state = 0");
        sbSQL.append("  AND ob.Id_YWID = oas.Id");
        sbSQL.append("  AND rl.YWID = oas.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append("  AND rl.STATU IN (1)");

        sbSQL.append(" UNION");
        sbSQL.append(" SELECT");
        sbSQL.append(" 1 currentNodeId,");
        sbSQL.append(" 0 routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oas.sid,");
        sbSQL.append(" oas.id,");
        sbSQL.append(" oas.state,");
        sbSQL.append(" oas.operatorId,");
        sbSQL.append(" oas.operateTime,");
        sbSQL.append(" oas.ApplicantId,");
        sbSQL.append(" oas.ApplicantName,");
        sbSQL.append(" oas.ApplicationTime,");
        sbSQL.append(" oas.ApplicationPurpose,");
        sbSQL.append(" oas.sentto,");
        sbSQL.append(" oas.isOut,");
        sbSQL.append(" oas.IsAllReceive,");
        sbSQL.append(" oas.IsAllOutBack,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("   	ob.SubmitterId,");
        sbSQL.append("   	ob.WORKFLOWID");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_InformationSubmitted2 oas,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  AND oas.state = 0");
        sbSQL.append("  AND ob.Id_YWID = oas.Id");
        sbSQL.append(" AND rl.YWID = oas.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (" + Config.getSystemVariable("paymentPlan.Route.intStatus.Over") + ")");


        sbSQL.append(" UNION");
        sbSQL.append(" SELECT");
        sbSQL.append(" 1 currentNodeId,");
        sbSQL.append(" 0 routeListId,");
        sbSQL.append(" -1 currentStatus,");
        sbSQL.append(" oas.sid,");
        sbSQL.append(" oas.id,");
        sbSQL.append(" oas.state,");
        sbSQL.append(" oas.operatorId,");
        sbSQL.append(" oas.operateTime,");
        sbSQL.append(" oas.ApplicantId,");
        sbSQL.append(" oas.ApplicantName,");
        sbSQL.append(" oas.ApplicationTime,");
        sbSQL.append(" oas.ApplicationPurpose,");
        sbSQL.append(" oas.sentto,");
        sbSQL.append(" oas.isOut,");
        sbSQL.append(" oas.IsAllReceive,");
        sbSQL.append(" oas.IsAllOutBack,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("   	ob.SubmitterId,");
        sbSQL.append("   	ob.WORKFLOWID");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_InformationSubmitted2 oas,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  AND oas.state = 0");
        sbSQL.append("  AND ob.Id_YWID = oas.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append("  AND oas.id NOT IN(");
        sbSQL.append(" SELECT");
        sbSQL.append("  YWID");
        sbSQL.append("  FROM");
        sbSQL.append("  workflow_routelist rl");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  )");

        //设置查询条件
        conditions.add(new KVObject("SubmitterId = ", "'" + Database.encodeSQL(user.getId()) + "'"));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " currentStatus " + Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //查询数据
        Pager pager = Pager.query(sbSQL.toString(), informationSubmitted2VO, conditions, request, queryType);

        //返回格式化数据
        return insertTitle(pager);
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
     * @param informationSubmitted2VO 需要封装的实体类
     * @param conditions      范围查询条件对象
     * @param request         客户请求对象
     * @return
     * @throws Exception 修改人:周海鸿
     *                   事件：2015-7-8
     *                   内容：修改SQL语句
     */
    public Pager waitList(InformationSubmitted2VO informationSubmitted2VO, HttpServletRequest request, UserPO user, List<KVObject> conditions) throws Exception {

        //判断请求对象是否为空
        if (request == null) {
            throw new Exception("当前请求无效");
        }

        //查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);


        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("");

        sbSQL.append(" SELECT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oas.sid,");
        sbSQL.append(" oas.id,");
        sbSQL.append(" oas.state,");
        sbSQL.append(" oas.operatorId,");
        sbSQL.append(" oas.operateTime,");
        sbSQL.append(" oas.ApplicantId,");
        sbSQL.append(" oas.ApplicantName,");
        sbSQL.append(" oas.ApplicationTime,");
        sbSQL.append(" oas.ApplicationPurpose,");
        sbSQL.append(" oas.sentto,");
        sbSQL.append(" oas.isOut,");
        sbSQL.append(" oas.IsAllReceive,");
        sbSQL.append(" oas.IsAllOutBack,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id, ");
        sbSQL.append("   	ob.SubmitterId,");
        sbSQL.append("   	ob.WORKFLOWID");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_InformationSubmitted2 oas,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  AND oas.state = 0");
        sbSQL.append("  AND ob.Id_YWID = oas.Id");
        sbSQL.append("  AND rl.YWID = oas.Id");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND ob.controlString1  in (" + BizRouteConfig.getDepartmentParentId(user.getId()) + ")");
        sbSQL.append("  AND rl.STATU IN (1)");
        sbSQL.append("   AND rl.CURRENTNODE IN (");
        sbSQL.append("   	SELECT");
        sbSQL.append("   		NODEID");
        sbSQL.append("   	FROM");
        sbSQL.append("   		workflow_participant p,");
        sbSQL.append("   		workflow_role r");
        sbSQL.append("   	WHERE");
        sbSQL.append("   		p.WORKFLOWID = " + Config.getSystemVariable("WORKFLOWID.Information.InformationSubmitted2"));
        sbSQL.append("   	AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");
        sbSQL.append("   	AND r.USERLIST LIKE '%|" + Database.encodeSQL(user.getId()) + "|%'");
        sbSQL.append("   )");

        //设置查询条件
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " currentStatus " + Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //查询数据
        Pager pager = Pager.query(sbSQL.toString(), informationSubmitted2VO, conditions, request, queryType);

        //格式化数据
        return insertTitle(pager);
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
     * @param informationSubmitted2VO 需要封装的实体类
     * @param conditions              范围查询条件对象
     * @param request                 客户请求对象
     * @return
     * @throws Exception 修改人:周海鸿
     *                   事件：2015-7-8
     *                   内容：修改SQL语句
     */
    public Pager particiPantList(InformationSubmitted2VO informationSubmitted2VO, HttpServletRequest request, UserPO user, List<KVObject> conditions) throws Exception {

        //判断请求对象是否为空
        if (request == null) {
            throw new Exception("当前请求无效");
        }

        //查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);


        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("");
        sbSQL.append(" SELECT");
        sbSQL.append(" 	*");
        sbSQL.append(" FROM");
        sbSQL.append(" 	(");

        sbSQL.append(" SELECT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oas.sid,");
        sbSQL.append(" oas.id,");
        sbSQL.append(" oas.state,");
        sbSQL.append(" oas.operatorId,");
        sbSQL.append(" oas.operateTime,");
        sbSQL.append(" oas.ApplicantId,");
        sbSQL.append(" oas.ApplicantName,");
        sbSQL.append(" oas.ApplicationTime,");
        sbSQL.append(" oas.ApplicationPurpose,");
        sbSQL.append(" oas.sentto,");
        sbSQL.append(" oas.isOut,");
        sbSQL.append(" oas.IsAllReceive,");
        sbSQL.append(" oas.IsAllOutBack,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_InformationSubmitted2 oas,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  AND oas.state = 0");
        sbSQL.append("  AND ob.Id_YWID = oas.Id");
        sbSQL.append("  AND rl.YWID = oas.Id");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append("  AND rl.STATU IN (1)");

        sbSQL.append(" UNION");
        sbSQL.append(" SELECT");
        sbSQL.append(" 1 currentNodeId,");
        sbSQL.append(" 0 routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oas.sid,");
        sbSQL.append(" oas.id,");
        sbSQL.append(" oas.state,");
        sbSQL.append(" oas.operatorId,");
        sbSQL.append(" oas.operateTime,");
        sbSQL.append(" oas.ApplicantId,");
        sbSQL.append(" oas.ApplicantName,");
        sbSQL.append(" oas.ApplicationTime,");
        sbSQL.append(" oas.ApplicationPurpose,");
        sbSQL.append(" oas.sentto,");
        sbSQL.append(" oas.isOut,");
        sbSQL.append(" oas.IsAllReceive,");
        sbSQL.append(" oas.IsAllOutBack,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_InformationSubmitted2 oas,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  AND oas.state = 0");
        sbSQL.append("  AND ob.Id_YWID = oas.Id");
        sbSQL.append("  AND rl.YWID = oas.Id");
        sbSQL.append(" AND sd1.id = ob.controlString1 ");
        sbSQL.append(" AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (" + Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel") + "," + Config.getSystemVariable("paymentPlan.Route.intStatus.Over") + ")");


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
        sbSQL.append(" 	AND WORKFLOWID = " + Config.getSystemVariable("WORKFLOWID.Information.InformationSubmitted2"));
        sbSQL.append(" 	AND PARTICIPANT = '" + Database.encodeSQL(user.getId()) + "'");
        sbSQL.append(" ) ");

        //设置查询条件
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " currentStatus " + Database.ORDERBY_ASC));
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //添加数据排序条件
        Pager pager = Pager.query(sbSQL.toString(), informationSubmitted2VO, conditions, request, queryType);

        //格式化数据
        return insertTitle(pager);
    }

    /**
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 对业务表进行添更新操作
     * 返回影响行数
     * <p/>
     * 添加并修改资料报送信息
     * <p/>
     * 修改人：周海鸿
     * 修改时间：2016-1-20
     * 修改时间： 添加注释，添加业务申请人
     *
     * @param informationSubmitted2PO 数据实体类
     * @param user                    当前操作用户类
     * @param conn                    数据链接类
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(InformationSubmitted2PO informationSubmitted2PO, InformationSubmitted2VO informationSubmitted2VO, UserPO user, Connection conn) throws Exception {
        //判断吧数据的有效性
        // 数据操作实体类是否为空
        if (informationSubmitted2PO == null) {
            throw new Exception("资料报送数据提交失败");
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
        if (informationSubmitted2PO.getId().equals("")) {
            informationSubmitted2PO.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted2", conn));
            informationSubmitted2PO.setId(IdUtils.getUUID32());
            informationSubmitted2PO.setState(Config.STATE_CURRENT);
            informationSubmitted2PO.setOperatorId(user.getId());
            informationSubmitted2PO.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(informationSubmitted2PO, conn);
            //不需要外带
            informationSubmitted2PO.setIsOut(InformationSubmittedItem2Status.ISOUT_NO);

            informationSubmitted2PO.setIsAllOutBack("");

            informationSubmitted2PO.setIsAllReceive("");

            // 返回影响行数为1
            if (count == 1) {
                count = BizRouteService.insertOrUpdate(informationSubmitted2PO.getId(), informationSubmitted2VO.getControlString3(), informationSubmitted2VO.getControlString1(), informationSubmitted2VO.getControlString2(),
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Information.InformationSubmitted2")), true, user, conn);
            }
        }
        // 更新
        else {
            InformationSubmitted2PO temp = new InformationSubmitted2PO();
            temp.setState(Config.STATE_CURRENT);
            temp.setId(informationSubmitted2PO.getId());
            temp = MySQLDao.load(temp, InformationSubmitted2PO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);

            //判断影响函数是不是1条
            if (count == 1) {
                //查询类型表是否有需要外带的数据
                InformationSubmittedItem2Service informationSubmittedItem2Service = new InformationSubmittedItem2Service();
                List<InformationSubmittedItem2PO> ls = informationSubmittedItem2Service.list(informationSubmitted2PO.getId(), conn);
                //需要外带
                if (ls.size() >= 1) {
                    informationSubmitted2VO.setIsOut(InformationSubmittedItem2Status.ISOUT_OK);
                    //查询是否全部确认接收

                    List<InformationSubmittedItem2PO> Receivels = informationSubmittedItem2Service.receiveList(informationSubmitted2PO.getId(), conn);
                    if (Receivels.size() >= 1) {
                        //没有全部接收
                        informationSubmitted2PO.setIsAllReceive(InformationSubmittedItem2Status.ISALLRECEIVE_NO);
                        //没有全部归还
                        informationSubmitted2PO.setIsAllOutBack(InformationSubmittedItem2Status.ISALLOUTBACK_NO);
                    } else {
                        //全部接收
                        informationSubmitted2PO.setIsAllReceive(InformationSubmittedItem2Status.ISALLRECEIVE_OK);

                        //查询是否全部确认归还
                        List<InformationSubmittedItem2PO> outBacklist = informationSubmittedItem2Service.outBackList(informationSubmitted2PO.getId(), conn);
                        if (outBacklist.size() >= 1) {
                            //没有全部归还
                            informationSubmitted2PO.setIsAllOutBack(InformationSubmittedItem2Status.ISALLOUTBACK_NO);
                        } else {
                            //全部归还
                            informationSubmitted2PO.setIsAllOutBack(InformationSubmittedItem2Status.ISALLOUTBACK_OK);
                        }
                    }


                } else {
                    //不需要外带
                    informationSubmitted2PO.setIsOut(InformationSubmittedItem2Status.ISOUT_NO);

                    informationSubmitted2PO.setIsAllOutBack("");

                    informationSubmitted2PO.setIsAllReceive("");
                }


                informationSubmitted2PO.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted2", conn));
                informationSubmitted2PO.setState(Config.STATE_CURRENT);
                informationSubmitted2PO.setOperatorId(user.getId());
                informationSubmitted2PO.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(informationSubmitted2PO, conn);

                // 返回影响行数为1
                if (count == 1) {
                    count = BizRouteService.insertOrUpdate(informationSubmitted2PO.getId(), informationSubmitted2VO.getControlString3(), informationSubmitted2VO.getControlString1(), informationSubmitted2VO.getControlString2(),
                            Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Information.InformationSubmitted2")), false, user, conn);
                }
            }
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    /**
     * 判断传入的数据是否为null
     * 执行更新书状态操作
     * 根据条改编数据的状态
     *
     * @param informationSubmitted2PO
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(InformationSubmitted2PO informationSubmitted2PO, UserPO user, Connection conn) throws Exception {
        if (informationSubmitted2PO == null) {
            MyException.newInstance("资料报送信息不能为null");
        }

        int count = 0;
        //设置数据
        informationSubmitted2PO = MySQLDao.load(informationSubmitted2PO, InformationSubmitted2PO.class);
        informationSubmitted2PO.setState(Config.STATE_UPDATE);

        //更新数据
        count = MySQLDao.update(informationSubmitted2PO, conn);

        //添加删除数据
        if (count == 1) {
            informationSubmitted2PO.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted2", conn));
            informationSubmitted2PO.setState(Config.STATE_DELETE);
            informationSubmitted2PO.setOperateTime(TimeUtils.getNow());
            informationSubmitted2PO.setOperatorId(user.getId());
            count = MySQLDao.insert(informationSubmitted2PO, conn);
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    /**
     * 格式化分页数据
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
            InformationSubmitted2VO informationSubmitted2VO = (InformationSubmitted2VO) iteratot.next();

            try {
                //判断数据状态是都为完成状态
                if ("5".equals(informationSubmitted2VO.getCurrentStatus())) {
                    title = "已完成";
                } else if ("3".equals(informationSubmitted2VO.getCurrentStatus())) {
                    title = "中止";
                }
                //非完成状态
                else {
                    //判断阶段是否是开始节点
                    if ("1".equals(informationSubmitted2VO.getCurrentNodeId())) {
                        title = "申请";
                    }
                    //节点为中间节点
                    else {
                        //根据业务流编号与节点编号获取节点title
                        title = clientApplications.getNodeTitle(Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Information.InformationSubmitted2")), Integer.valueOf(informationSubmitted2VO.getCurrentNodeId()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //添加标题
            informationSubmitted2VO.setCurrentNodeTitle(title);

            //添加到临时集合中
            tempList.add(informationSubmitted2VO);
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
    * 时间:2016-1-20
    * 内容：获取资料报送打印数据*/
    public InformationSubmitted2VO getPrintDate(String id) throws Exception {
        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("资料报送情况打印数据获取失败");
        }

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT  ");
        sbSQL.append(" 	*  ");
        sbSQL.append(" FROM  ");
        sbSQL.append(" 	(  ");
        sbSQL.append(" SELECT");
        sbSQL.append(" rl.CURRENTNODE currentNodeId,");
        sbSQL.append(" rl.id routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oas.sid,");
        sbSQL.append(" oas.id,");
        sbSQL.append(" oas.state,");
        sbSQL.append(" oas.operatorId,");
        sbSQL.append(" oas.operateTime,");
        sbSQL.append(" oas.ApplicantId,");
        sbSQL.append(" oas.ApplicantName,");
        sbSQL.append(" oas.ApplicationTime,");
        sbSQL.append(" oas.ApplicationPurpose,");
        sbSQL.append(" oas.sentto,");
        sbSQL.append(" oas.isOut,");
        sbSQL.append(" oas.IsAllReceive,");
        sbSQL.append(" oas.IsAllOutBack,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_InformationSubmitted2 oas,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  AND oas.state = 0");
        sbSQL.append("  AND ob.Id_YWID = oas.Id");
        sbSQL.append("  AND rl.YWID = oas.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append("  AND rl.STATU IN (1)");

        sbSQL.append(" UNION");
        sbSQL.append(" SELECT");
        sbSQL.append(" 1 currentNodeId,");
        sbSQL.append(" 0 routeListId,");
        sbSQL.append(" rl.STATU currentStatus,");
        sbSQL.append(" oas.sid,");
        sbSQL.append(" oas.id,");
        sbSQL.append(" oas.state,");
        sbSQL.append(" oas.operatorId,");
        sbSQL.append(" oas.operateTime,");
        sbSQL.append(" oas.ApplicantId,");
        sbSQL.append(" oas.ApplicantName,");
        sbSQL.append(" oas.ApplicationTime,");
        sbSQL.append(" oas.ApplicationPurpose,");
        sbSQL.append(" oas.sentto,");
        sbSQL.append(" oas.isOut,");
        sbSQL.append(" oas.IsAllReceive,");
        sbSQL.append(" oas.IsAllOutBack,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_InformationSubmitted2 oas,");
        sbSQL.append(" workflow_routelist rl,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  AND oas.state = 0");
        sbSQL.append("  AND ob.Id_YWID = oas.Id");
        sbSQL.append(" AND rl.YWID = oas.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN (" + Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel") + "," + Config.getSystemVariable("paymentPlan.Route.intStatus.Over") + ")");


        sbSQL.append(" UNION");
        sbSQL.append(" SELECT");
        sbSQL.append(" 1 currentNodeId,");
        sbSQL.append(" 0 routeListId,");
        sbSQL.append(" -1 currentStatus,");
        sbSQL.append(" oas.sid,");
        sbSQL.append(" oas.id,");
        sbSQL.append(" oas.state,");
        sbSQL.append(" oas.operatorId,");
        sbSQL.append(" oas.operateTime,");
        sbSQL.append(" oas.ApplicantId,");
        sbSQL.append(" oas.ApplicantName,");
        sbSQL.append(" oas.ApplicationTime,");
        sbSQL.append(" oas.ApplicationPurpose,");

        sbSQL.append(" oas.sentto,");
        sbSQL.append(" oas.isOut,");
        sbSQL.append(" oas.IsAllReceive,");
        sbSQL.append(" oas.IsAllOutBack,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("	sd1.fromName  as controlString1, ");
        sbSQL.append("	sd2.name as controlString2, ");
        sbSQL.append("	sd1.id as controlString1Id, ");
        sbSQL.append("	sd2.id as controlString2Id ");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_InformationSubmitted2 oas,");
        sbSQL.append(" system_department sd1, ");
        sbSQL.append(" system_department sd2, ");
        sbSQL.append(" oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("  AND oas.state = 0");
        sbSQL.append("  AND ob.Id_YWID = oas.Id");
        sbSQL.append("  AND sd1.id = ob.controlString1 ");
        sbSQL.append("  AND sd2.id = ob.controlString2 ");
        sbSQL.append("  AND oas.id NOT IN(");
        sbSQL.append(" SELECT");
        sbSQL.append("  YWID");
        sbSQL.append("  FROM");
        sbSQL.append("  workflow_routelist rl");
        sbSQL.append("  WHERE");
        sbSQL.append("   1 = 1");
        sbSQL.append("   )");
        sbSQL.append(" 	) d  ");
        sbSQL.append(" WHERE  ");
        sbSQL.append(" 	1 = 1  ");
        sbSQL.append(" AND id = '" + Database.encodeSQL(id) + "'  ");

        //获取数据
        List li = MySQLDao.query(sbSQL.toString(), InformationSubmitted2VO.class, null);


        return (InformationSubmitted2VO) li.get(0);

    }

    /**
     * 用来改变数据状态
     *
     * @param bizdao
     * @param routeList
     * @param worlkflow
     * @param conn
     * @throws Exception
     */
    public void afterOver(BizRoutePO bizdao, RouteList routeList, Action worlkflow, Connection conn) throws Exception {
        //获取当前节点
        int node = routeList.getCurrentNode();
        //判断是否属于结束节点
        int overNode = Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Information.InformationSubmitted2.overNote"));
        if (node == overNode) {

            //获取当前操作数据的编号
            String id = bizdao.getId_ywid();
            if (StringUtils.isEmpty(id)) {
                MyException.deal(MyException.newInstance("资料报送申请数据编号获取失败"));
            }

            //获取当前操作用户编号
            String userId = worlkflow.getParticipant();
            if (StringUtils.isEmpty(userId)) {

                MyException.deal(MyException.newInstance("资料报送申请用户数据编号获取失败"));
            }
            //获取用户
            UserPO userPO = new UserPO();
            userPO.setId(userId);
            userPO.setState(Config.STATE_CURRENT);
            userPO = MySQLDao.load(userPO, UserPO.class, conn);


            //获取资料报送数据
            InformationSubmittedItem2Service item2Service = new InformationSubmittedItem2Service();
            List<InformationSubmittedItem2PO> informationSubmittedItem2POs = item2Service.list(id, conn);
            if (informationSubmittedItem2POs.size() > 0) {
                int count = 0;
                //循环更新数据
                for (int i = 0; i < informationSubmittedItem2POs.size(); i++) {
                    //获取数据
                    InformationSubmittedItem2PO temp = informationSubmittedItem2POs.get(i);
                    //不需要外带数据
                    if (temp.getStatus() == InformationSubmittedItem2Status.ISOUT_NO) {
                        //继续下一次循环
                        continue;
                    }
                    temp.setState(Config.STATE_UPDATE);
                    //更新数据
                    count = MySQLDao.update(temp, conn);

                    //如果放回的影响函数为1
                    if (count == 1) {
                        InformationSubmittedItem2PO informationSubmittedItem2PO = new InformationSubmittedItem2PO();
                        informationSubmittedItem2PO.setId(temp.getId());
                        informationSubmittedItem2PO.setApplicationId(id);
                        informationSubmittedItem2PO.setSid(MySQLDao.getMaxSid("oa_InformationSubmittedItem2", conn));
                        informationSubmittedItem2PO.setState(Config.STATE_CURRENT);
                        informationSubmittedItem2PO.setOperatorId(userPO.getId());
                        informationSubmittedItem2PO.setOperateTime(TimeUtils.getNow());
                        //设置确认人信息
                        informationSubmittedItem2PO.setReceiveId(temp.getReceiveId());
                        informationSubmittedItem2PO.setReceiveName(temp.getReceiveName());
                        informationSubmittedItem2PO.setReceiveTime(temp.getReceiveTime());

                        //设置份数
                        informationSubmittedItem2PO.setTopies(temp.getTopies());
                        //设置资料报送
                        informationSubmittedItem2PO.setDataComment(temp.getDataComment());
                        informationSubmittedItem2PO.setDataName(temp.getDataName());
                        //设置外带地点
                        informationSubmittedItem2PO.setSentToAddress(temp.getSentToAddress());
                        //状态
                        informationSubmittedItem2PO.setStatus(temp.getStatus());
                        //确认接收
                        informationSubmittedItem2PO.setReceiveIsConfirm(temp.getReceiveIsConfirm());

                        //设置归还人
                        informationSubmittedItem2PO.setOutBackId(userPO.getId());
                        informationSubmittedItem2PO.setOutBackName(userPO.getName());

                        //确认归还
                        informationSubmittedItem2PO.setOutBackIsConfirm(temp.getOutBackIsConfirm());
                        //插入新数据
                        count = MySQLDao.insert(informationSubmittedItem2PO, conn);
                    }
                }
            }
        }
    }
}
