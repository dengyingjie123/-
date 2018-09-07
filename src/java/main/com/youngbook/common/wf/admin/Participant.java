package com.youngbook.common.wf.admin;


import com.youngbook.common.wf.common.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.service.system.LogService;

import java.sql.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: 参与者管理类</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class Participant {
    public Participant() {
    }

    protected int intWorkflowID = Integer.MAX_VALUE; //工作流编号
    protected int intNodeID = Integer.MAX_VALUE; //节点编号
    protected String strRoleID=new String(); //角色组编号串，由|xx|xxx|xxxx|组成
    protected String strUserList=new String(); //用户名串，由|xx|xxx|xx|组成

    public boolean isEmptyWorkflowID() {
        if (intWorkflowID == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isEmptyNodeID() {
        if (intNodeID == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getWorkflowID() {
        return intWorkflowID;
    }

    public int getNodeID() {
        return intNodeID;
    }

    public String getRoleID() {
        return strRoleID;
    }

    public String getUserList() {
        return Tools.Decode(strUserList);
    }

    public void setWorkflowID(int WorkflowID) {
        intWorkflowID = WorkflowID;
    }

    public void setNodeID(int NodeID) {
        intNodeID = NodeID;
    }

    public void setUserList(String UserList) {
        strUserList = Tools.Encode(UserList);
    }

    public void setRoleID(String RoleID) {
        strRoleID = RoleID;
    }

    /**
     * 程序：李扬
     * 时间：2004-10-27
     * 说明：根据WorklfowID, NodeID查询数据库，返回Participant组成的List
     * @throws Exception
     * @return List
     */
    public List query() throws Exception {
        Statement statement = null;
        Connection conn = Tools.getDBConn();
        ResultSet rs = null;
        try {
            List listPT = new ArrayList();
            StringBuffer sbSQL = new StringBuffer();
            StringBuffer sbWhere = new StringBuffer();
            sbSQL.append("SELECT * FROM WORKFLOW_PARTICIPANT ");

            if (!this.isEmptyWorkflowID()) {
                sbWhere.append("WORKFLOWID=");
                sbWhere.append(intWorkflowID);
                sbWhere.append(" AND ");
            }
            if (!this.isEmptyNodeID()) {
                sbWhere.append("NODEID=");
                sbWhere.append(intNodeID);
                sbWhere.append(" AND ");
            }

            if (sbWhere.length() > 0) {
                sbWhere.insert(0, " WHERE ");
                sbWhere.delete(sbWhere.length() - 5, sbWhere.length());
                sbSQL.append(sbWhere.toString());
            }
            statement = conn.createStatement();
            rs = statement.executeQuery(sbSQL.toString());

            while (rs.next()) {
                Participant pt = new Participant();
                if (rs.getString("WORKFLOWID") != null) {
                    pt.setWorkflowID(rs.getInt("WORKFLOWID"));
                }
                if (rs.getString("NODEID") != null) {
                    pt.setNodeID(rs.getInt("NODEID"));
                }
                if (rs.getString("ROLEID") != null) {
                    pt.setRoleID(rs.getString("ROLEID"));
                }
                if (rs.getString("USERLIST") != null) {
                    pt.setUserList(rs.getString("USERLIST"));
                }

                listPT.add(pt);
            }
            return listPT;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            conn.close();
        }
    }

    /**
     * 程序：
     * 说明：新增节点参与者配置，无数据库连接
     * @return
     * @throws java.lang.Exception
     */
    public int insert() throws Exception {
        Connection conn = Tools.getDBConn();
        try {
            return insert(conn);
        }
        finally {
            conn.close();
        }
    }

    /**
     * 程序：
     * 说明：新增节点参与者配置，带数据库连接
     * @param conn
     * @return
     * @throws java.lang.Exception
     */
    public int insert(Connection conn) throws Exception {
        if (intWorkflowID == Integer.MAX_VALUE || intNodeID == Integer.MAX_VALUE) {
            throw new Exception("未指定工作流编号或是未指定节点编号");
        }
        int result = 1;
        Statement statement = null;
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(
                    "INSERT INTO WORKFLOW_Participant (WORKFLOWID,NODEID,ROLEID,USERLIST) VALUES (");
            sbSQL.append(intWorkflowID);
            sbSQL.append(",");
            sbSQL.append(intNodeID);
            sbSQL.append(",'");
            sbSQL.append(strRoleID == null ? "" : strRoleID);
            sbSQL.append("','");
            sbSQL.append(strUserList == null ? "" : strUserList);
            sbSQL.append("')");
            statement = conn.createStatement();
            result = statement.executeUpdate(sbSQL.toString());
            return result;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：修改节点参与者信息，不带数据库连接
     * @return
     * @throws java.lang.Exception
     */
    public int update() throws Exception {
        Connection conn = Tools.getDBConn();
        try {
            return update(conn);
        }
        finally {
            conn.close();
        }
    }

    /**
     * 程序：
     * 说明：修改节点参与者信息，带数据库连接
     * @param conn
     * @return
     * @throws java.lang.Exception
     */
    public int update(Connection conn) throws Exception {
        if (intWorkflowID == Integer.MAX_VALUE || intNodeID == Integer.MAX_VALUE) {
            throw new Exception("Participant|update|NoPara|执行Participant.update()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)或是节点编号(NodeID)。");
        }
        int result = 1;
        Statement statement = null;
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("UPDATE WORKFLOW_Participant SET ROLEID='");
            sbSQL.append(strRoleID == null ? "" : strRoleID);
            sbSQL.append("',USERLIST='");
            sbSQL.append(strUserList == null ? "" : strUserList);
            sbSQL.append("' WHERE WORKFLOWID=");
            sbSQL.append(intWorkflowID);
            sbSQL.append(" AND NODEID=");
            sbSQL.append(intNodeID);
            statement = conn.createStatement();
            result = statement.executeUpdate(sbSQL.toString());
            return result;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：从参与者表中移去某一角色
     * @param RemoveRole
     * @return
     * @throws java.lang.Exception
     */
    public static int RemoveRoleFromParticipant(String RemoveRole) throws
            Exception {
        Statement statement = null;
        Connection conn = Tools.getDBConn();
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("UPDATE WORKFLOW_Participant SET RoleID=REPLACE(RoleID,'|");
            sbSQL.append(RemoveRole);
            sbSQL.append("|','|')");
            statement = conn.createStatement();
            return statement.executeUpdate(sbSQL.toString());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            conn.close();
        }
    }

    /**
     * 程序：
     * 说明：从参与者表中移去某一用户
     * @return
     * @throws java.lang.Exception
     */
    public static int RemoveUserFromParticipant(String RemoveUser) throws
            Exception {
        Statement statement = null;
        Connection conn = Tools.getDBConn();
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(
                    "UPDATE WORKFLOW_Participant SET UserList=REPLACE(UserList,'|");
            sbSQL.append(RemoveUser);
            sbSQL.append("|','|')");
            statement = conn.createStatement();
            return statement.executeUpdate(sbSQL.toString());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            conn.close();
        }
    }

    /**
     * 程序：
     * 说明：删除角色，带数据库连接
     * @param conn
     * @return
     * @throws java.lang.Exception
     */
    public int Delete(Connection conn) throws Exception {
        if (intWorkflowID == Integer.MAX_VALUE) {
            throw new Exception("Participant|Delete|NoPara|执行Participant.Delete()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)。");
        }
        int result = 1;
        Statement statement = null;
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("DELETE WORKFLOW_PARTICIPANT WHERE WORKFLOWID=");
            sbSQL.append(intWorkflowID);
            if (!this.isEmptyNodeID()) {
                sbSQL.append(" AND NODEID=");
                sbSQL.append(intNodeID);
            }
            statement = conn.createStatement();
            result = statement.executeUpdate(sbSQL.toString());
            System.out.println("Participant.delete():" + sbSQL.toString());
            return result;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：删除记录，无数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public int Delete() throws Exception {
        Connection conn = Tools.getDBConn();
        try {
            return Delete(conn);
        }
        finally {
            conn.close();
        }
    }

    /**
     * 程序：
     * 说明：根据用户名和节点编号查询用户是否具体操作权限 返回此用户可操作的所有节点ID
     * @param WorkflowID 工作流编号
     * @param User 用户名
     * @return 用户可操作的节点序号
     */
    public static List SearchNodebyUserID(int WorkflowID, String User) throws
            Exception {
        if (User == null || User.equals("")) {
            throw new Exception("Participant|SearchNodebyUserID|NoPara|执行Participant.SearchNodebyUserID()方法时发生参数不足异常，无法获得所需的用户名(UserID)。");
        }

        List relist = new ArrayList();
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT DISTINCT PART.NODEID AS NODEID FROM WORKFLOW_PARTICIPANT PART,WORKFLOW_ROLE ROLEID WHERE ((");
        sbSQL.append(" PART.ROLEID LIKE CONCAT('%|', roleid.id ,'|%') ");
        sbSQL.append("AND ROLEID.USERLIST LIKE '%|");
        sbSQL.append(User);
        sbSQL.append("|%')");
        sbSQL.append("OR PART.USERLIST LIKE '%|");
        sbSQL.append(User);
        sbSQL.append("|%')");
        sbSQL.append(" AND WORKFLOWID=");
        sbSQL.append(WorkflowID);
        Statement statement = null;
        Connection Conn = Tools.getDBConn();
        ResultSet rs = null;
        try {
            statement = Conn.createStatement();

            LogService.info("Participant.SearchNodebyUserID(): " + sbSQL.toString(), MySQLDao.class);

            rs = statement.executeQuery(sbSQL.toString());
            while (rs.next()) {
                relist.add(rs.getString("NODEID"));
            }
            return relist;
        }
        catch (Exception E) {
            throw E;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            Conn.close();
        }

    }
}
