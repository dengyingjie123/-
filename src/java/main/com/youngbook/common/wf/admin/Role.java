package com.youngbook.common.wf.admin;

import java.sql.*;
import java.util.*;

import com.youngbook.common.wf.common.*;
import com.youngbook.dao.MySQLDao;

/**
 * <p>Title: </p>
 * <p>Description: 工作流角色管理类</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Role {
    public Role() {
    }

    protected int intID = Integer.MAX_VALUE;
    protected String strRoleName = new String();
    protected String strUserList = new String();
    public void setID(int ID) {
        intID = ID;
    }

    public void setRoleName(String RoleName) {
        strRoleName = RoleName;
    }

    public void setUserList(String UserList) {
        strUserList = UserList;
    }

    public int getID() {
        return intID;
    }

    public String getRoleName() {
        return strRoleName;
    }

    public String getUserList() {
        return strUserList;
    }

    /**
     * 程序：
     * 说明：新增记录，无数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public int Insert() throws Exception {
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
     * 说明：修改记录，无数据库连接
     * @return 1成功，非1失败
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
     * 说明：删除记录，无数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public int delete() throws Exception {
        Connection conn = Tools.getDBConn();
        try {
            return delete(conn);
        }
        finally {
            conn.close();
        }
    }

    /**
     * 程序：
     * 说明：新增记录，带数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public int insert(Connection conn) throws Exception {
        if (intID == Integer.MAX_VALUE || strRoleName == null ||
                strRoleName.equals("")) {
            throw new Exception(
                    "Role|insert|NoPara|执行Role.insert()方法时发生参数不足异常，无法获得所需的角色编号(ID)或是角色(RoleName)。");
        }
        int result = 1;
        Statement statement = null;
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("INSERT INTO WORKFLOW_ROLE (ID,ROLENAME,USERLIST) VALUES (");
            sbSQL.append(intID);
            sbSQL.append(",");
            sbSQL.append("'");
            sbSQL.append(strRoleName);
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
     * 说明：修改角色，带数据库连接
     * @param conn
     * @return
     */
    public int update(Connection conn) throws Exception {
        if (intID == Integer.MAX_VALUE || strRoleName == null ||
                strRoleName.equals("")) {
            throw new Exception(
                    "Role|update|NoPara|执行Role.update()方法时发生参数不足异常，无法获得所需的角色编号(ID)或是角色(RoleName)。");
        }
        int result = 1;
        Statement statement = null;
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("UPDATE WORKFLOW_ROLE SET ROLENAME='");
            sbSQL.append(strRoleName);
            sbSQL.append("',USERLIST='");
            sbSQL.append(strUserList == null ? "" : strUserList);
            sbSQL.append("' WHERE ID=");
            sbSQL.append(intID);
            statement = conn.createStatement();
            result = statement.executeUpdate(sbSQL.toString());
            System.out.println("Role modify: " + sbSQL.toString());
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
     * 说明：删除角色，带数据库连接
     * @param conn
     * @return
     * @throws java.lang.Exception
     */
    public int delete(Connection conn) throws Exception {
        if (intID == Integer.MAX_VALUE) {
            throw new Exception(
                    "Role|delete|NoPara|执行Role.delete()方法时发生参数不足异常，无法获得所需的角色编号(ID)。");
        }
        int result = 1;
        Statement statement = null;
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("DELETE from WORKFLOW_ROLE WHERE ID=");
            sbSQL.append(intID);
            statement = conn.createStatement();
            System.out.println("Role.delete(): " + sbSQL.toString());
            statement.executeUpdate(sbSQL.toString());
            Participant.RemoveRoleFromParticipant(String.valueOf(intID));
            System.out.println("Role delete: " + sbSQL.toString());
            return result;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public List query() throws Exception {
        Connection conn = Tools.getDBConn();
        List ls = new ArrayList();
        Statement statement = null;
        ResultSet rs = null;
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT * FROM WORKFLOW_ROLE WHERE 1=1 ");
            if (intID != Integer.MAX_VALUE) {
                sbSQL.append(" AND ID=");
                sbSQL.append(intID);
            }
            if (strRoleName != null && !strRoleName.equals("")) {
                sbSQL.append(" AND ROLENAME LIKE '%");
                sbSQL.append(strRoleName);
                sbSQL.append("%'");
            }
            if (strUserList != null && !strUserList.equals("")) {
                sbSQL.append(" AND USERLIST LIKE '%");
                sbSQL.append(strUserList);
                sbSQL.append("%'");
            }

            sbSQL.append(" ORDER BY ID");
            statement = conn.createStatement();
            System.out.println("Role search: " + sbSQL.toString());
            rs = statement.executeQuery(sbSQL.toString());
            while (rs.next()) {
                Role role = new Role();
                role.setID(rs.getInt("ID"));
                role.setRoleName(rs.getString("ROLENAME"));
                if (rs.getString("USERLIST") != null) {
                    role.setUserList(rs.getString("USERLIST"));
                }
                ls.add(role);
            }
            return ls;
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
     * 说明：从用户列表中移去某一用户
     * @param UserName 用户名
     * @return
     */
    public static int RomveUserFromUserList(String UserName) throws Exception {
        if (UserName == null || UserName.equals("")) {
            throw new Exception("Role|RemoveUserFromUserList|NoPara|执行Role.RemoveUserFromUserList()方法时发生参数不足异常，无法获得所需的用户名(UserName)。");
        }
        int result = 1;
        Statement statement = null;
        Connection conn = Tools.getDBConn();
        try {
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("UPDATE WORKFLOW_ROLE SET USERLIST=REPLACE(USERLIST,'|");
            sbSQL.append(UserName);
            sbSQL.append("|','|')");
            statement = conn.createStatement();
            result = statement.executeUpdate(sbSQL.toString());
            return result;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            conn.close();
        }
    }

    /**
     * 程序：李扬
     * 时间：2004-11-22
     * 说明：获得数据库表中最大的用户编号
     * @return int
     * @throws Exception
     */
    public int getMaxID(Connection conn) throws Exception {

        return MySQLDao.getSequence("system", conn);

//        Statement statement = null;
//        ResultSet resultset = null;
//        try {
//            int intResult = Integer.MAX_VALUE;
//            StringBuffer sbSQL = new StringBuffer();
//            sbSQL.append("SELECT NVL(MAX(ID),0)+1 AS MAXID FROM WORKFLOW_ROLE");
//            System.out.println("Role.getMaxID(): " + sbSQL.toString());
//
//            statement = conn.createStatement();
//            resultset = statement.executeQuery(sbSQL.toString());
//
//            if (resultset.next()) {
//                intResult = resultset.getInt("MAXID");
//            }
//            if (intResult == Integer.MAX_VALUE) {
//                throw new Exception(
//                        "Role|getMaxID|DbFail|执行Role.getMaxID()时发生数据库操作异常，无法获得最大的用户编号");
//            }
//            return intResult;
//        }
//        catch (Exception e) {
//            throw e;
//        }
//        finally {
//            if (resultset != null) {
//                resultset.close();
//            }
//            if (statement != null) {
//                statement.close();
//            }
//        }
    }

    /**
     * 程序：李扬
     * 时间：2004-11-22
     * 说明：获得数据库表中最大的用户编号
     * @return int
     * @throws Exception
     */
    public int getMaxID() throws Exception {
        return MySQLDao.getSequence("system");
    }
}
