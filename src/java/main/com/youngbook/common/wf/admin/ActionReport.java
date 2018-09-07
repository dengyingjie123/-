package com.youngbook.common.wf.admin;

import java.util.*;
import java.sql.*;
import com.youngbook.common.wf.admin.*;
import com.youngbook.common.wf.common.*;

/**
 * 程序：李扬
 * 时间：2004-11-15
 * 说明：流转信息类
 *      用于保存相应工作流和业务的流转信息
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class ActionReport
        extends Action {
    protected int intCurrentNode = Integer.MAX_VALUE;
    protected String strForwarded = new String();

    public ActionReport() {
    }

    /**
     * 程序：李扬
     * 时间：2004-11-15
     * 说明：根据工作流编号和业务编号查找该业务的流转信息。
     * @return 由ActionReport组成的List
     * @throws Exception
     */
    public List query() throws Exception {
        //定义数据库连接
        Connection conn = null;
        try {
            List listRouteReport = new ArrayList();
            //获得数据库连接
            conn = Tools.getDBConn();
            //调用重载方法
            listRouteReport = query(conn);
            return listRouteReport;
        }
        catch (Exception e) {
            //异常处理
            throw e;
        }
        finally {
            //关闭数据库连接
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 程序：李扬
     * 时间：2004-11-15
     * 说明：根据工作流编号和业务编号查找该业务的流转信息。
     * @return 由ActionReport组成的List
     * @throws Exception
     */
    public List query(Connection conn) throws Exception {
        //定义数据库资源
        Statement statement = null;
        ResultSet resultset = null;
        try {
            List listRouteReport = new ArrayList();
            //判断工作流编号和业务编号是否为空
            if (isEmptyYWID() || isEmptyWorkflowID()) {
                throw new Exception("ActionReport|query|NoPara|执行ActionReport.query()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)或是业务编号(YWID)。");
            }
            //组织SQL查询语句
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT ACTION.PARTICIPANT, ACTION.ACTIONTIME, ACTION.ID, ACTION.YWID, ACTION.WORKFLOWID, ACTION.ACTIONTYPE, ROUTELIST.CURRENTNODE, ROUTELIST.PRENODE, ROUTELIST.FORWARDED, ROUTELIST.NEXTNODE");
            sbSQL.append(
                    " FROM WORKFLOW_ACTION ACTION, WORKFLOW_ROUTELIST ROUTELIST ");
            sbSQL.append("WHERE ROUTELIST.WORKFLOWID=ACTION.WORKFLOWID AND ROUTELIST.YWID=ACTION.YWID AND ROUTELIST.ID=ACTION.ROUTEID");
            sbSQL.append(" AND ACTION.YWID='");
            sbSQL.append(strYWID);
            sbSQL.append("' AND ACTION.WORKFLOWID=");
            sbSQL.append(intWorkflowID);
            sbSQL.append(" ORDER BY ID");
            //后台打印SQL语句
            System.out.println("ActionReport.query(): " + sbSQL.toString());
            statement = conn.createStatement();
            //执行查询语句
            resultset = statement.executeQuery(sbSQL.toString());

            //循环获得查询结果
            while (resultset.next()) {
                //从ResultSet中构建对象
                ActionReport ap = ActionReport.getActionReportInstance(resultset);
                listRouteReport.add(ap);
            }
            return listRouteReport;

        }
        catch (Exception e) {
            //异常处理
            throw e;
        }
        finally {
            //释放数据库资源
            if (resultset != null) {
                resultset.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 程序：李扬
     * 时间：2004-11-15
     * 说明：从ResultSet中获得ActionReport对象
     * @param resultset ResultSet
     * @return ActionReport对象
     * @throws Exception
     */
    public static ActionReport getActionReportInstance(ResultSet resultset) throws
            Exception {
        try {
            ActionReport ap = new ActionReport();
            if (resultset.getString("YWID") != null) {
                ap.setYWID(resultset.getString("YWID"));
            }
            if (resultset.getString("WORKFLOWID") != null) {
                ap.setWorkflowID(resultset.getInt("WORKFLOWID"));
            }
            if (resultset.getString("ID") != null) {
                ap.setID(resultset.getInt("ID"));
            }
            if (resultset.getString("PARTICIPANT") != null) {
                ap.setParticipant(resultset.getString("PARTICIPANT"));
            }
            if (resultset.getString("ACTIONTIME") != null) {
                ap.setActionTime(resultset.getString("ACTIONTIME"));
            }
            if (resultset.getString("ACTIONTYPE") != null) {
                ap.setActionType(resultset.getInt("ACTIONTYPE"));
            }
            if (resultset.getString("CURRENTNODE") != null) {
                ap.setCurrentNode(resultset.getInt("CURRENTNODE"));
            }
            if (resultset.getString("PRENODE") != null) {
                ap.setPreNode(resultset.getInt("PRENODE"));
            }
            if (resultset.getString("FORWARDED") != null) {
                ap.setForwarded(resultset.getString("FORWARDED"));
            }
            if (resultset.getString("NEXTNODE") != null) {
                ap.setNextNode(resultset.getString("NEXTNODE"));
            }
            return ap;
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获得当前节点编号
     * @return int
     */
    public int getCurrentNode() {
        return intCurrentNode;
    }

    /**
     * 获得转发节点
     * @return String
     */
    public String getForwarded() {
        return strForwarded;
    }

    /**
     * 设置当前节点编号
     * @param CurrentNode int
     */
    public void setCurrentNode(int CurrentNode) {
        intCurrentNode = CurrentNode;
    }

    /**
     * 设置转发节点
     * @param Forwarded String
     */
    public void setForwarded(String Forwarded) {
        strForwarded = Forwarded;
    }

}
