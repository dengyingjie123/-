package com.youngbook.service.oa.finance;


// 构造 FinancePayWFAService 开始 //////////////////////////////////////////////////////

import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.finance.FinancePayWFAPO;
import com.youngbook.entity.po.wf.BizRoutePO;
import com.youngbook.entity.vo.oa.finance.FinancePayWFAVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;
import org.springframework.jca.cci.CannotGetCciConnectionException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 创建一个FinancePayWFAServlet 类 继承BaseServlet类
 *
 * @author Codemaker
 */

public class FinancePayWFAService extends BaseService {
    /**
     *
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 返回影响行数
     * 添加或修改数据，并修改数据状态
     * @param financePayWFA
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate (FinancePayWFAPO financePayWFA, FinancePayWFAVO financePayWFAVO, int workflowId, UserPO user, Connection conn) throws Exception {
        if ( financePayWFA == null ) {
            throw new Exception ("资金支付数据提交失败");
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
        if ( financePayWFA.getId ().equals ("") ) {

            financePayWFA.setSid (MySQLDao.getMaxSid ("OA_FinancePayWFA", conn));
            financePayWFA.setId (IdUtils.getUUID32 ());
            financePayWFA.setState (Config.STATE_CURRENT);
            financePayWFA.setOperatorId (user.getId ());
            financePayWFA.setOperateTime (TimeUtils.getNow ());
            financePayWFA.setApplicantId (user.getId ());
            financePayWFA.setApplicantTime (TimeUtils.getNow ());

            count = MySQLDao.insert (financePayWFA, conn);

            /**
             * 2015 -6 -13 小周
             * 当数据添加成功时 向流程表中添加数据
             */


            if (count == 1) {
                count = BizRouteService.insertOrUpdate(financePayWFA.getId(), financePayWFAVO.getControlString3(),financePayWFAVO.getControlString1(), financePayWFAVO.getControlString2(), workflowId, true, user, conn);
            }
        }
        // 更新
        else {
            FinancePayWFAPO temp = new FinancePayWFAPO ();
            temp.setSid (financePayWFA.getSid ());
            temp = MySQLDao.load (temp, FinancePayWFAPO.class);
            temp.setState (Config.STATE_UPDATE);

            count = MySQLDao.update (temp, conn);
            if ( count == 1 ) {
                financePayWFA.setSid (MySQLDao.getMaxSid ("OA_FinancePayWFA", conn));
                financePayWFA.setState (Config.STATE_CURRENT);
                financePayWFA.setOperatorId (user.getId ());
                financePayWFA.setOperateTime (TimeUtils.getNow ());

                count = MySQLDao.insert (financePayWFA, conn);
                /**
                 * 2015 -6 -13 小周
                 * 当数据添加成功时 向流程表中添加数据
                 */
                if (count == 1) {
                    count = BizRouteService.insertOrUpdate(financePayWFA.getId(), financePayWFAVO.getControlString3(),financePayWFAVO.getControlString1(), financePayWFAVO.getControlString2(),
                            workflowId, false, user, conn);
                }
            }
        }
        if ( count != 1 ) {
            throw new Exception ("数据库异常");
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
    public FinancePayWFAPO loadFinancePayWFAPO (String id) throws Exception {
        if ( StringUtils.isEmpty (id) ) {
            throw new Exception ("数据获取失败");
        }
        FinancePayWFAPO po = new FinancePayWFAPO ();

        po.setId (id);
        po.setState (Config.STATE_CURRENT);
        po = MySQLDao.load (po, FinancePayWFAPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     *
     * @param financePayWFA
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete (FinancePayWFAPO financePayWFA, UserPO user, Connection conn) throws Exception {
        int count = 0;

        financePayWFA.setState (Config.STATE_CURRENT);
        financePayWFA = MySQLDao.load (financePayWFA, FinancePayWFAPO.class);
        financePayWFA.setState (Config.STATE_UPDATE);
        count = MySQLDao.update (financePayWFA, conn);
        if ( count == 1 ) {
            financePayWFA.setSid (MySQLDao.getMaxSid ("OA_FinancePayWFA", conn));
            financePayWFA.setState (Config.STATE_DELETE);
            financePayWFA.setOperateTime (TimeUtils.getNow ());
            financePayWFA.setOperatorId (user.getId ());
            count = MySQLDao.insert (financePayWFA, conn);
        }

        if ( count != 1 ) {
            throw new Exception ("删除失败");
        }

        return count;
    }

    /*修改:周海鸿
    * 时间：2015-7-17
    * 内容：获取打印数据*/
    public FinancePayWFAVO getPrintDate (String id) throws Exception {
        //判断数据有效性
        if ( "".equals (id) || null == id ) {
            throw new Exception ("获取资金支付数据错误");
        }

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer ();

        sbSQL.append (" SELECT");
        sbSQL.append ("* from (");
        sbSQL.append (" SELECT");
        sbSQL.append (" rl.CURRENTNODE currentNodeId,");
        sbSQL.append (" rl.id routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" u. NAME submitterName,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" af.*");
        sbSQL.append (" FROM");
        sbSQL.append (" OA_FinancePayWFA af,");
        sbSQL.append (" system_user u,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append (" WHERE");
        sbSQL.append (" 1 = 1");
        sbSQL.append (" AND u.state = 0");
        sbSQL.append (" AND af.state = 0");
        sbSQL.append (" and af.ApplicantId = u.Id");
        sbSQL.append (" AND rl.YWID = af.Id");
        sbSQL.append (" AND ob.Id_YWID = af.Id");
        sbSQL.append ("  AND sd1.id = ob.controlString1 ");
        sbSQL.append ("  AND sd2.id = ob.controlString2 ");
        sbSQL.append (" AND rl.STATU IN (1)");

        sbSQL.append (" UNION");
        sbSQL.append (" SELECT DISTINCT ");
        sbSQL.append (" -1 currentNodeId,");
        sbSQL.append (" -1 routeListId,");
        sbSQL.append (" rl.STATU currentStatus,");
        sbSQL.append (" u. NAME submitterName,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" af.*");
        sbSQL.append (" FROM");
        sbSQL.append (" OA_FinancePayWFA af,");
        sbSQL.append (" system_user u,");
        sbSQL.append (" workflow_routelist rl,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append (" WHERE");
        sbSQL.append (" 1 = 1");
        sbSQL.append (" AND u.state = 0");
        sbSQL.append (" AND af.state = 0");
        sbSQL.append (" and af.ApplicantId = u.Id");
        sbSQL.append (" AND rl.YWID = af.Id");
        sbSQL.append (" AND ob.Id_YWID = af.Id");
        sbSQL.append ("  AND sd1.id = ob.controlString1 ");
        sbSQL.append ("  AND sd2.id = ob.controlString2 ");
        sbSQL.append(" AND rl.STATU IN ("+Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel")+","+Config.getSystemVariable("paymentPlan.Route.intStatus.Over")+")");

        sbSQL.append (" UNION");
        sbSQL.append (" SELECT DISTINCT ");
        sbSQL.append (" 1 currentNodeId,");
        sbSQL.append (" 0 routeListId,");
        sbSQL.append (" -1 currentStatus,");
        sbSQL.append (" u. NAME submitterName,");
        sbSQL.append (" " + BizRouteConfig.getBizRouteStr ());
        sbSQL.append ("	sd1.fromName as controlString1, ");
        sbSQL.append ("	sd2.name as controlString2, ");
        sbSQL.append ("	sd1.id as controlString1Id, ");
        sbSQL.append ("	sd2.id as controlString2Id, ");
        sbSQL.append (" af.*");
        sbSQL.append (" FROM");
        sbSQL.append (" OA_FinancePayWFA af,");
        sbSQL.append (" system_user u,");
        sbSQL.append (" system_department sd1, ");
        sbSQL.append (" system_department sd2, ");
        sbSQL.append (" oa_bizroute ob");
        sbSQL.append (" WHERE");
        sbSQL.append (" 1 = 1");
        sbSQL.append (" AND u.state = 0");
        sbSQL.append (" AND af.state = 0");
        sbSQL.append (" and af.ApplicantId = u.Id");
        sbSQL.append (" AND ob.Id_YWID = af.Id");
        sbSQL.append ("  AND sd1.id = ob.controlString1 ");
        sbSQL.append ("  AND sd2.id = ob.controlString2 ");
        sbSQL.append (" AND af.id NOT IN(");
        sbSQL.append (" SELECT");
        sbSQL.append (" YWID");
        sbSQL.append (" FROM");
        sbSQL.append (" workflow_routelist rl");
        sbSQL.append (" where 1=1 ");
        sbSQL.append (")");
        sbSQL.append (") b ");
        sbSQL.append ("where 1=1 ");
        sbSQL.append ("AND id = '" + Database.encodeSQL (id) + "'");

        //获取数据
        List li = MySQLDao.query (sbSQL.toString (), FinancePayWFAVO.class, null);



        return (FinancePayWFAVO) li.get (0);
    }

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
    public Pager list (FinancePayWFAVO financePayWFAVO, int workflowId, UserPO user, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {

        if (workflowId == Integer.MAX_VALUE) {
            MyException.newInstance("无法获得资金支付的工作流信息").throwException();
        }

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("list", "FinancePayWFAServiceSQL", FinancePayWFAService.class);
        dbSQL.addParameter4All("userId", user.getId()).addParameter4All("workflowId", workflowId);
        dbSQL.initSQL();


        //获取分页对象
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), financePayWFAVO, conditions, request, queryType, conn);

        return insertTitle (pager, workflowId);
    }

    /**
     * 2015-5-9
     * 海鸿
     * 修改了SQL语句
     *
     * 获取等待审核数据
     */
    public Pager waitList(FinancePayWFAVO financePayWFAVO, int workflowId, UserPO user, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("waitList", "FinancePayWFAServiceSQL", FinancePayWFAService.class);
        dbSQL.addParameter4All("userId", user.getId()).addParameter4All("userList", "|" + user.getId() + "|");
        dbSQL.initSQL();


        //获取分页对象
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), financePayWFAVO, conditions, request, queryType, conn);

        //返回格式化数据
        return insertTitle (pager, workflowId);
    }

    /**
     * 2015-5-9
     * 海鸿
     * 修改了SQL语句
     * 获取以完成数据
     */
    public Pager participantList(FinancePayWFAVO financePayWFAVO, int workflowId, UserPO user, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("participantList", "FinancePayWFAServiceSQL", FinancePayWFAService.class);
        dbSQL.addParameter4All("userId", user.getId());
        dbSQL.initSQL();


        //获取分页对象
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), financePayWFAVO, conditions, request, queryType, conn);

        //格式化数据
        return insertTitle (pager, workflowId);
    }

    /**
     * 组装数据添加业务状态title
     *
     * @param pager 分页对象
     * @return
     */
    public Pager insertTitle (Pager pager, int workflowId) throws Exception {
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
            FinancePayWFAVO financePayWFAVO = (FinancePayWFAVO) iteratot.next ();
            try {
                //判断数据状态是都为完成状态
                if ( "5".equals (financePayWFAVO.getCurrentStatus ()) ) {
                    title = "已完成";
                }else if ("3".equals(financePayWFAVO.getCurrentStatus())) {
                    title = "中止";
                }
                //非完成状态
                else {
                    //判断阶段是否是开始节点
                    if ( "1".equals (financePayWFAVO.getCurrentNodeId ()) ) {
                        title = "申请";
                    }
                    //节点为中间节点
                    else {
                        //根据业务流编号与节点编号获取节点title
                        title = clientApplications.getNodeTitle (workflowId, Integer.valueOf (financePayWFAVO.getCurrentNodeId ()));
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace ();
            }
            //添加标题
            financePayWFAVO.setCurrentNodeTitle (title);
            //添加到临时集合中
            tempList.add (financePayWFAVO);
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


}
