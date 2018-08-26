package com.youngbook.common.wf.clientapp;

import java.util.*;
import java.sql.*;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.wf.common.*;
import com.youngbook.common.wf.processdefine.*;
import com.youngbook.common.wf.admin.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.SmsType;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.SmsService;
import org.springframework.beans.factory.annotation.Autowired;


public class ClientApplications {

    private static SmsService smsService = Config.getBeanByName("smsService", SmsService.class);

    public static void sendMessage(RouteList rl, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     rl.WORKFLOWID,");
        sbSQL.append("     rl.ywid,");
        sbSQL.append("     rl.CURRENTNODE,");
        sbSQL.append("     r.USERLIST");
        sbSQL.append(" FROM");
        sbSQL.append("     workflow_routelist rl,");
        sbSQL.append("     workflow_participant p,");
        sbSQL.append("     workflow_role r");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" and rl.ID="+rl.getID());
        sbSQL.append(" and p.WORKFLOWID=rl.WORKFLOWID");
        sbSQL.append(" and p.NODEID=rl.CURRENTNODE    ");
        sbSQL.append(" AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");


        Statement stmt = null;
        ResultSet rs = null;

        List<String> userIds = new ArrayList<String>();
        List<String> userMobiles = new ArrayList<String>();
        String workflowId = "";
        String ywid = "";
        String currentNodeId = "";
        try {

            stmt = conn.createStatement();
            LogService.info("ClientApplications.sendMessage(): " + sbSQL.toString(), ClientApplications.class);
            rs = stmt.executeQuery(sbSQL.toString());

            while (rs.next()) {
                workflowId = rs.getString("WorkflowID");
                ywid = rs.getString("YWID");
                currentNodeId = rs.getString("CURRENTNODE");
                String [] ids = rs.getString("USERLIST").split("\\|");

                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    if (!StringUtils.isEmpty(id)) {
                        userIds.add(id);
                    }
                }
            }


            StringBuffer sbUserIdsIn = new StringBuffer();
            for (String id : userIds) {
                sbUserIdsIn.append("'").append(id).append("',");
            }

            sbUserIdsIn = StringUtils.removeLastLetters(sbUserIdsIn, ",");


            sbSQL = new StringBuffer();
            sbSQL.append(" SELECT");
            sbSQL.append("     *");
            sbSQL.append(" FROM");
            sbSQL.append("     system_user u");
            sbSQL.append(" WHERE");
            sbSQL.append("     1 = 1");
            sbSQL.append(" AND u.state = 0");
            sbSQL.append(" AND u.id In("+sbUserIdsIn+")");


            LogService.info("ClientApplications.sendMessage(): " + sbSQL.toString(), ClientApplications.class);
            rs = stmt.executeQuery(sbSQL.toString());

            List<UserPO> users = MySQLDao.query(sbSQL.toString(), UserPO.class, null, conn);


            for (UserPO user :  users) {
                /*
                * 获取工作流名称*/
                String workflowName = ProcessInfo.getWorkflwoName(Integer.valueOf(workflowId));
                /*
                * 获取节点的标题*/
                String title =getNodeTitle(Integer.valueOf(workflowId),Integer.valueOf(currentNodeId));

                String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");
                smsService.insertSMS(user.getId(), user.getName(), user.getMobile(), "OA通知", "您有1条【"+workflowName+"】流程的【"+title+"】等待您操作。", signature, SmsType.TYPE_OA_MESSAGE, conn);
            }

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        }

    }


    /**
     * 查询当前业务数据状态
     * @param intWorkflowID 当前工作流编号
     * @param intCurrNodeID 当前节点编号
     * @param strFieldName 业务数据项字段名
     * @return 0可编辑 1不可编辑 2
     * @throws java.lang.Exception
     */
    public static int getFieldStatus(int intWorkflowID, int intCurrNodeID, String strFieldName) throws Exception {
        if (strFieldName != null) {
            strFieldName = strFieldName.toUpperCase();
        }

        int intResult = 1;
        Node node = Node.searchNodeObject(intWorkflowID, intCurrNodeID);
        List listBizField = node.getBizField();
        for (int i = 0; listBizField != null && i < listBizField.size(); i++) {
            BizField bf = (BizField) listBizField.get(i);
            if (strFieldName != null && strFieldName.equalsIgnoreCase(bf.getName())) {
                return bf.getState();
            }
        }
        return intResult;
    }

    /**
     * 获得当前工作流节点字段下的背景颜色
     *
     * 便于用户知晓目前需要填写的内容位置
     * @param intWorkflowID
     * @param intCurrNodeID
     * @param intCurrNodeID
     * @param strFieldName
     * @return
     * @throws Exception
     *
     * @author leevits
     */
    public static String getFieldCSS(int intWorkflowID, int intCurrNodeID, String strFieldName) throws Exception {

        int status = ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, strFieldName);

        if (status == 0) {
            return Config.Workflow_Active_Field_CSS;
        }

        return "";
    }

    /**
     * 说明：查询下一可转向节点
     * @param intWorkflowID 工作流编号
     * @param intCurrNodeID 当前节点编号
     * @return
     * @throws java.lang.Exception
     */
    public static List getNextNode(int intWorkflowID, int intCurrNodeID) throws Exception {
        List ls = new ArrayList();
        Node node = Node.searchNodeObject(intWorkflowID, intCurrNodeID);
        //获得当前节点可以转向的节点列表
        List listTransition = node.getTransition();
        for (int i = 0; listTransition != null && i < listTransition.size(); i++) {
            int intNextNodeID = Integer.parseInt((String) listTransition.get(i));
            Node nextNode = Node.searchNodeObject(intWorkflowID, intNextNodeID);
            ls.add(nextNode);
        }
        return ls;
    }
    /**
     * 说明：查询下一可转向节点
     * @param intWorkflowID 工作流编号
     * @param intCurrNodeID 当前节点编号
     * @return
     * @throws java.lang.Exception
     */
  public static List getNextNode(int intWorkflowID, int intCurrNodeID,String Attribute) throws Exception {
      List ls = new ArrayList();
      Node node = Node.searchNodeObject(intWorkflowID, intCurrNodeID,Attribute);
      //获得当前节点可以转向的节点列表
      List listTransition = node.getTransition();
      for (int i = 0; listTransition != null && i < listTransition.size(); i++) {
          int intNextNodeID = Integer.parseInt((String) listTransition.get(i));
          Node nextNode = Node.searchNodeObject(intWorkflowID, intNextNodeID);
          ls.add(nextNode);
      }
      return ls;
    }

    /**
     * 通过工作流编号和用户编号，获得用户所在角色能访问的流转记录
     * 同时也会根据传入的状态进行筛选
     *
     * @author leevits
     * 时间：2015年6月17日 14:24:51
     *
     * @param workflowId 工作流编号
     * @param userId 用户编号
     * @param status 状态 1：Active...
     * @return
     */
    public static List<RouteList> getRouteList(int workflowId, String userId, int status) {
        List<RouteList> list = new ArrayList<RouteList>();

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     *");
        sbSQL.append(" FROM");
        sbSQL.append("     workflow_routelist rl");
        sbSQL.append(" WHERE");
        sbSQL.append("     WORKFLOWID = " + workflowId);
        sbSQL.append(" AND CURRENTNODE in (");
        sbSQL.append("     SELECT");
        sbSQL.append("         NODEID");
        sbSQL.append("     FROM");
        sbSQL.append("         workflow_participant p,");
        sbSQL.append("         workflow_role r");
        sbSQL.append("     WHERE");
        sbSQL.append("         p.WORKFLOWID = " + workflowId);
        sbSQL.append("     AND p.ROLEID LIKE CONCAT('%|', r.ID, '%|')");
        sbSQL.append("     AND r.USERLIST LIKE '%|"+userId+"|%'");
        sbSQL.append(" )");
        sbSQL.append(" AND STATUS = " + status);

        return list;
    }

    public static boolean checkOperableNodeByUserId(int WorkflowID, String UserID, int NodeID) throws Exception {
        List list = getOperableNodebyUserID(WorkflowID, UserID, NodeID);
        for (int i = 0; list != null && i < list.size(); i++) {
            Node node = (Node) list.get(i);
            if (node.getID() == NodeID) {
                return true;
            }
        }
        return false;
    }

    /**
     * 程序：
     * 说明：跟据用户名得到该用户可操作的节点
     * @param WorkflowID 工作流ID
     * @param UserID 用户名
     * @param NodeID 节点编号
     * @return 如果节点编号为Integer.MAX_VALUE则返回用户可操作的所有节点，否则返回用户可操作的指定节点
     */
    public static List getOperableNodebyUserID(int WorkflowID, String UserID,
                                               int NodeID) throws Exception {
        List Nodelist = Participant.SearchNodebyUserID(WorkflowID, UserID);
        List reList = new ArrayList();
        List result = new ArrayList();
        if (NodeID != Integer.MAX_VALUE) {
            Iterator it = Nodelist.iterator();
            while (it.hasNext()) {
                if (NodeID == Integer.parseInt((String) it.next())) {
                    reList.add(String.valueOf(NodeID));
                }
            }
        }
        else {
            reList = Nodelist;
        }
        Iterator it = reList.iterator();
        while (it.hasNext()) {
            Node node = Node.searchNodeObject(WorkflowID, Integer.parseInt((String) it.next()));
            result.add(node);
        }
        return result;
    }

    /**
     * 程序：李扬
     * 时间：2004-12-20
     * 说明：
     * @param intWorkflowID int
     * @param strYWID String
     * @return List
     * @throws Exception
     */
    public static List getRouteListByYWIDAndNodeIDAndStatus(int intWorkflowID, int intNodeID, String strYWID, int routeListStatus, Connection conn) throws Exception {
        RouteList rl = new RouteList();
        rl.setWorkflowID(intWorkflowID);
        rl.setCurrentNode(intNodeID);
        rl.setYWID(strYWID);
        rl.setStatus(routeListStatus);

        return rl.queryExact(conn);
    }


    /**
     * 程序：
     * 说明：根据工作流编号，节点编号，节点状态得到RouteList对象
     * @param WorkflowID 工作流编号
     * @param NodeID 节点编号 如为Integer.MAX_VALUE，则返回所有
     * @return 包含有RouteList的List
     */
    public static List getRouteListByNodeIDAndStatus(int WorkflowID, int NodeID, int routeListStatus, Connection conn) throws Exception {
        RouteList rl = new RouteList();
        rl.setWorkflowID(WorkflowID);
        if (NodeID != Integer.MAX_VALUE) {
            rl.setCurrentNode(NodeID);
        }
        rl.setStatus(routeListStatus);

        return rl.queryExact(conn);
    }

    /**
     * 程序：李扬
     * 时间：2004-11-2
     * 说明：根据UserID、工作流编号和业务号，查找该用户所参与的所有RouteList
     *      如果没有指定工作流编号或者业务号，则查出所有参与的所有RouteList
     * @param strUserID String
     * @param strYwID String
     * @param strWorkflowID String
     * @throws Exception
     * @return List
     */
    public static List searchRoutList(String strUserID, String strYwID, String strWorkflowID) throws Exception {
        RouteList routelist = new RouteList();
        //判断业务号是否为空，如果不为空则作为查询条件
        if (!StringUtils.isEmpty(strYwID)) {
            routelist.setYWID(strYwID);
        }
        //判断工作流编号是否为空，如果不为空则作为查询条件
        if (!StringUtils.isEmpty(strWorkflowID)) {
            routelist.setWorkflowID(Integer.parseInt(strWorkflowID));
        }
        //根据条件进行查询
        List listRouteList = RouteList.searchRouteList(strUserID, routelist);
        return listRouteList;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-2
     * 说明：根据UserID、查找该用户所参与的所有RouteList
     * @param strUserID String
     * @throws Exception
     * @return List
     */
    public static List searchRouteList(String strUserID) throws Exception {
        List listRouteList = ClientApplications.searchRoutList(strUserID, "", "");
        return listRouteList;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-2
     * 说明：根据UserID、业务号，查找该用户所参与的所有RouteList
     * @param strUserID String
     * @param strYwID String
     * @throws Exception
     * @return List
     */
    public static List searchRouteList(String strUserID, String strYwID) throws Exception {
        List listRouteList = ClientApplications.searchRoutList(strUserID, strYwID, "");
        return listRouteList;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-5
     * @param intWorkflowID int
     * @param strYWID String
     * @throws Exception
     * @return List
     */
    public static List getActionReport(int intWorkflowID, String strYWID) throws  Exception {
        ActionReport ap = new ActionReport();
        ap.setWorkflowID(intWorkflowID);
        ap.setYWID(strYWID);
        List listAP = ap.query();
        return listAP;
    }

    public static String getNodeTitle(int workflowId, int nodeId) throws Exception {
        String title = "";
        Node node = Node.searchNodeObject(workflowId, nodeId);
        title = node.getName();
        return title;
    }

    /**
     * 说明：根据工作流编号和业务编号，删除该业务
     * @param intWorkflowID int
     * @param strYWID String
     * @return int
     * @throws Exception
     */
    public static int deleteYW(int intWorkflowID, String strYWID) throws Exception {
        Connection conn = null;
        try {
            conn = Tools.getDBConn();
            conn.setAutoCommit(false);

            //1.删除RouteList中数据
            RouteList routelist = new RouteList();
            routelist.setWorkflowID(intWorkflowID);
            routelist.setYWID(strYWID);
            routelist.delete(conn);

            //2.删除Action中的数据
            Action action = new Action();
            action.setWorkflowID(intWorkflowID);
            action.setYWID(strYWID);
            action.delete(conn);

            //3.删除HistoryData中的数据
            HistoryData hd = new HistoryData();
            hd.setWorkflowID(intWorkflowID);
            hd.setYWID(strYWID);
            hd.delete(conn);

            conn.commit();

            return 1;
        }
        catch (Exception e) {
            conn.rollback();
            throw e;
        }
        finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：查找业务位于哪个位置
     * @param strYWID
     * @param intWorkflowID
     * @return
     */
    public static List getActiveYW(String strYWID, int intWorkflowID, Connection conn) throws Exception {
        RouteList rl = new RouteList();
        rl.setYWID(strYWID);
        rl.setWorkflowID(intWorkflowID);
        rl.setStatus(RouteListStatus.ACTIVE);
        List relist = rl.queryExact(conn);
        return relist;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-22
     * 说明：根据工作流编号，查找该工作流下所有的业务编号
     * @param intWorkflowID int
     * @return List 由业务编号组成
     * @throws Exception
     */
    public static List getYWID(int intWorkflowID) throws Exception {
        RouteList rl = new RouteList();
        rl.setWorkflowID(intWorkflowID);
        List listResult = rl.queryYWID();
        return listResult;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-23
     * 说明：根据工作流编号，业务编号和动作编号，查找历史数据记录
     * @param intWorkflowID int
     * @param strYWID String
     * @param intActionID int
     * @return List 由HistoryData组成
     * @throws Exception
     */
    public static List getHistoryData(int intWorkflowID, String strYWID,
                                      int intActionID) throws Exception {
        HistoryData hd = new HistoryData();
        hd.setWorkflowID(intWorkflowID);
        hd.setYWID(strYWID);
        hd.setActionID(intActionID);
        List listResult = hd.query();
        return listResult;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-24
     * 说明：根据工作流编号和字段名，获得字段显示名
     * @param intWorkflowID int
     * @param strFieldName String
     * @return String
     * @throws Exception
     */
    public static String getFieldTitle(int intWorkflowID, String strFieldName) throws
            Exception {
        String strTitle = new String();
        strTitle = BizData.getFieldTitle(intWorkflowID, strFieldName);
        return strTitle;
    }

    /**
     * 程序：李扬
     * 时间：2005-1-6
     * 说明：根据工作流编号，查出某节点范围内所有要求状态的业务编号
     * @param intWorkflowID int  工作流编号
     * @param sNode int  开始节点编号（包含在内）
     * @param eNode int  结束节点编号（包含在内）
     * @return List  由业务编号组成的List
     * @throws Exception
     */
    public static List getYWIDByNodeAndStatus(int intWorkflowID, int sNode, int eNode, int routeListStatus, Connection conn) throws Exception {
        List list = new ArrayList();
        RouteList rl = new RouteList();
        rl.setWorkflowID(intWorkflowID);
        rl.setStatus(routeListStatus);

        List listRL = rl.queryExact(conn);

        for (int i = 0; listRL != null && i < listRL.size(); i++) {
            rl = (RouteList)listRL.get(i);
            if (rl.getCurrentNode() >= sNode && rl.getCurrentNode() <= eNode) {
                list.add(rl.getYWID());
            }
        }

        return list;
    }
}
