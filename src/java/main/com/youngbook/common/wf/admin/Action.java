package com.youngbook.common.wf.admin;

import java.sql.Connection;
import java.sql.*;
import java.util.*;
import com.youngbook.common.wf.common.*;
import com.youngbook.dao.MySQLDao;

/**
 * <p>Title: </p>
 * <p>Description: 历史动作表</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Action {
    public Action() {
    }

    /**
     * 数据库字段对应类的每个成员变量
     */
    protected String strYWID = new String(); //业务编号
    protected int intWorkflowID = Integer.MAX_VALUE; //工作流编号
    protected int intID = Integer.MAX_VALUE; //动作编号
    protected int intRouteID = Integer.MAX_VALUE; //路由编号
    protected String strParticipant = new String(); //参与者
    protected String strActionTime = new String(); //动作时间
    protected int intActionType = Integer.MAX_VALUE; //动作类型
    protected int intPreNode = Integer.MAX_VALUE; //上一节点
    protected String strNextNode = new String(); //下一节点

    /**
     * 实例化函数：静态方法，根据ResultSet构造实例
     */
    public static Action getInstance(ResultSet resultset) throws Exception {
        try {
            //创建实例
            Action vo_WORKFLOW_ACTION = new Action();

            //设置属性值
            if (resultset.getString("YWID") != null) {
                vo_WORKFLOW_ACTION.setYWID(resultset.getString("YWID"));
            }
            if (resultset.getString("WORKFLOWID") != null) {
                vo_WORKFLOW_ACTION.setWorkflowID(resultset.getInt("WORKFLOWID"));
            }
            if (resultset.getString("ID") != null) {
                vo_WORKFLOW_ACTION.setID(resultset.getInt("ID"));
            }
            if (resultset.getString("ROUTEID") != null) {
                vo_WORKFLOW_ACTION.setRouteID(resultset.getInt("ROUTEID"));
            }
            if (resultset.getString("PARTICIPANT") != null) {
                vo_WORKFLOW_ACTION.setParticipant(resultset.getString("PARTICIPANT"));
            }
            if (resultset.getString("ACTIONTIME") != null) {
                vo_WORKFLOW_ACTION.setActionTime(resultset.getString("ACTIONTIME"));
            }
            if (resultset.getString("ACTIONTYPE") != null) {
                vo_WORKFLOW_ACTION.setActionType(resultset.getInt("ACTIONTYPE"));
            }
            if (resultset.getString("PRENODE") != null) {
                vo_WORKFLOW_ACTION.setPreNode(resultset.getInt("PRENODE"));
            }
            if (resultset.getString("NEXTNODE") != null) {
                vo_WORKFLOW_ACTION.setNextNode(resultset.getString("NEXTNODE"));
            }
            //返回实例
            return vo_WORKFLOW_ACTION;
        }
        catch (Exception e) {
            //错误处理
            throw e;
        }
    }

    /**
     * 删除方法：根据VO字段内容做为条件删除数据库记录
     */
    public int delete(Connection conn) throws Exception {
        //变量：数据库操作语句
        Statement statement = null;

        try {
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句  根据所有字段删除记录
            sbSQL.append("DELETE WORKFLOW_ACTION");

            //构建精确查询条件
            StringBuffer sbWhere = new StringBuffer();
            if (!getYWID().equals("")) {
                sbWhere.append("YWID=");
                sbWhere.append("'");
                sbWhere.append(getYWID());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyWorkflowID()) {
                sbWhere.append("WORKFLOWID=");
                sbWhere.append(getWorkflowID());
                sbWhere.append(" AND ");
            }
            if (getID() != Integer.MAX_VALUE) {
                sbWhere.append("ID=");
                sbWhere.append("'");
                sbWhere.append(getID());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyRouteID()) {
                sbWhere.append("ROUTEID=");
                sbWhere.append(getRouteID());
                sbWhere.append(" AND ");
            }
            if (!getParticipant().equals("")) {
                sbWhere.append("PARTICIPANT=");
                sbWhere.append("'");
                sbWhere.append(getParticipant());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyActionTime()) {
                sbWhere.append("ACTIONTIME=");
                sbWhere.append(getActionTime());
                sbWhere.append(" AND ");
            }
            if (!isEmptyActionType()) {
                sbWhere.append("ACTIONTYPE=");
                sbWhere.append(getActionType());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreNode()) {
                sbWhere.append("PRENODE=");
                sbWhere.append(getPreNode());
                sbWhere.append(" AND ");
            }
            if (!getNextNode().equals("")) {
                sbWhere.append("NEXTNODE=");
                sbWhere.append("'");
                sbWhere.append(getNextNode());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }

            //整理Where条件，判断是否存在查询条件，如果存在，整理SQL语句
            if (sbWhere.length() > 0) {
                //增加查询条件关键字
                sbWhere.insert(0, " WHERE ");
                //去除最后一个" AND "操作符
                sbWhere.delete(sbWhere.length() - 5, sbWhere.length());
                //将查询条件增加到查询语句中
                sbSQL.append(sbWhere.toString());
            }
            //创建数据库操作语句
            statement = conn.createStatement();
            //执行数据库操作
            int intResult = statement.executeUpdate(sbSQL.toString());
            System.out.println("Action.delete(): " + sbSQL.toString());
            //返回数据操作结果
            return intResult;
        }
        catch (Exception e) {
            //错误处理
            throw e;
        }
        finally {
            //释放数据库语句资源
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 查询方法：根据VO字段内容做为条件做为查询条件
     */
    public List query(Connection conn) throws Exception {
        //变量：数据库操作语句
        Statement statement = null;
        //变量：数据库结果集
        ResultSet resultset = null;

        try {
            //变量：结果集列表
            List listResult = (List) (new ArrayList());
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            StringBuffer sbWhere = new StringBuffer();

            //初始化SQL语句
            sbSQL.append("SELECT YWID,WORKFLOWID,ID,ROUTEID,PARTICIPANT,ACTIONTIME,ACTIONTYPE,PRENODE,NEXTNODE FROM WORKFLOW_ACTION");
            if (!getYWID().equals("")) {
                sbWhere.append("YWID LIKE ");
                sbWhere.append("'%");
                sbWhere.append(getYWID());
                sbWhere.append("%'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyWorkflowID()) {
                sbWhere.append("WORKFLOWID=");
                sbWhere.append(getWorkflowID());
                sbWhere.append(" AND ");
            }
            if (getID() != Integer.MAX_VALUE) {
                sbWhere.append("ID LIKE ");
                sbWhere.append("'%");
                sbWhere.append(getID());
                sbWhere.append("%'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyRouteID()) {
                sbWhere.append("ROUTEID=");
                sbWhere.append(getRouteID());
                sbWhere.append(" AND ");
            }
            if (!getParticipant().equals("")) {
                sbWhere.append("PARTICIPANT LIKE ");
                sbWhere.append("'%");
                sbWhere.append(getParticipant());
                sbWhere.append("%'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyActionTime()) {
                sbWhere.append("ACTIONTIME LIKE '%");
                sbWhere.append(getActionTime());
                sbWhere.append("%' AND ");
            }
            if (!isEmptyActionType()) {
                sbWhere.append("ACTIONTYPE=");
                sbWhere.append(getActionType());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreNode()) {
                sbWhere.append("PRENODE=");
                sbWhere.append(getPreNode());
                sbWhere.append(" AND ");
            }
            if (!getNextNode().equals("")) {
                sbWhere.append("NEXTNODE LIKE ");
                sbWhere.append("'%");
                sbWhere.append(getNextNode());
                sbWhere.append("%'");
                sbWhere.append(" AND ");
            }

            //整理Where条件，判断是否存在查询条件，如果存在，整理SQL语句
            if (sbWhere.length() > 0) {
                //增加查询条件关键字
                sbWhere.insert(0, " WHERE ");
                //去除最后一个" AND "操作符
                sbWhere.delete(sbWhere.length() - 5, sbWhere.length());
                //将查询条件增加到查询语句中
                sbSQL.append(sbWhere.toString());
            }
            sbSQL.append("");
            //创建数据库操作语句
            statement = conn.createStatement();
            //执行数据库操作
            resultset = statement.executeQuery(sbSQL.toString());
            //循环处理结果集中的纪录
            while (resultset.next()) {
                //获得结果实例
                Action voResult = Action.getInstance(resultset);
                //将结果加入结果列表
                listResult.add(voResult);
            }

            //返回数据操作结果
            return listResult;
        }
        catch (Exception e) {
            //错误处理

            throw e;
        }
        finally {
            //释放结果集资源
            if (resultset != null) {
                resultset.close();
            }

            //释放数据库语句资源
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 查询方法：根据VO字段内容做为精确查询条件
     */
    public List queryExact(Connection conn) throws Exception {
        //变量：数据库操作语句
        Statement statement = null;
        //变量：数据库结果集
        ResultSet resultset = null;

        try {
            //变量：结果集列表
            List listResult = (List) (new ArrayList());

            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句
            sbSQL.append("SELECT YWID,WORKFLOWID,ID,ROUTEID,PARTICIPANT,ACTIONTIME,ACTIONTYPE,PRENODE,NEXTNODE FROM WORKFLOW_ACTION");

            //构建精确查询条件
            StringBuffer sbWhere = new StringBuffer();
            if (!getYWID().equals("")) {
                sbWhere.append("YWID=");
                sbWhere.append("'");
                sbWhere.append(getYWID());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyWorkflowID()) {
                sbWhere.append("WORKFLOWID=");
                sbWhere.append(getWorkflowID());
                sbWhere.append(" AND ");
            }
            if (getID() != Integer.MAX_VALUE) {
                sbWhere.append("ID=");
                sbWhere.append("'");
                sbWhere.append(getID());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyRouteID()) {
                sbWhere.append("ROUTEID=");
                sbWhere.append(getRouteID());
                sbWhere.append(" AND ");
            }
            if (!getParticipant().equals("")) {
                sbWhere.append("PARTICIPANT=");
                sbWhere.append("'");
                sbWhere.append(getParticipant());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyActionTime()) {
                sbWhere.append("ACTIONTIME=");
                sbWhere.append(getActionTime());
                sbWhere.append(" AND ");
            }
            if (!isEmptyActionType()) {
                sbWhere.append("ACTIONTYPE=");
                sbWhere.append(getActionType());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreNode()) {
                sbWhere.append("PRENODE=");
                sbWhere.append(getPreNode());
                sbWhere.append(" AND ");
            }
            if (!getNextNode().equals("")) {
                sbWhere.append("NEXTNODE=");
                sbWhere.append("'");
                sbWhere.append(getNextNode());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }

            //整理Where条件，判断是否存在查询条件，如果存在，整理SQL语句
            if (sbWhere.length() > 0) {
                //增加查询条件关键字
                sbWhere.insert(0, " WHERE ");
                //去除最后一个" AND "操作符
                sbWhere.delete(sbWhere.length() - 5, sbWhere.length());
                //将查询条件增加到查询语句中
                sbSQL.append(sbWhere.toString());
            }

            //查询结果安关键字排序
            sbSQL.append("");
            statement = conn.createStatement();
            //执行数据库操作
            resultset = statement.executeQuery(sbSQL.toString());
            //循环处理结果集中的纪录
            while (resultset.next()) {
                //获得结果实例
                Action voResult = Action.getInstance(resultset);
                //将结果加入结果列表
                listResult.add(voResult);
            }
            //返回数据操作结果
            return listResult;
        }
        catch (Exception e) {
            //错误处理

            throw e;
        }
        finally {
            //释放结果集资源
            if (resultset != null) {
                resultset.close();
            }

            //释放数据库语句资源
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 插入方法：将参数VO中的数据插入数据库
     */
    public int insert(Connection conn) throws Exception {
        //变量：数据库操作语句
        Statement statement = null;
        try {
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句
            sbSQL.append("INSERT INTO WORKFLOW_ACTION(YWID,WORKFLOWID,ID,ROUTEID,PARTICIPANT,ACTIONTIME,ACTIONTYPE,PRENODE,NEXTNODE) VALUES (");
            sbSQL.append("'");
            sbSQL.append(getYWID());
            sbSQL.append("'");
            sbSQL.append(",");
            if (!isEmptyWorkflowID()) {
                sbSQL.append(getWorkflowID());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append(",");
            sbSQL.append(intID);
            sbSQL.append(",");
            if (!isEmptyRouteID()) {
                sbSQL.append(getRouteID());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append(",");
            sbSQL.append("'");
            sbSQL.append(getParticipant());
            sbSQL.append("'");
            sbSQL.append(",'");
            if (!isEmptyActionTime()) {
                sbSQL.append(getActionTime());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append("',");
            if (!isEmptyActionType()) {
                sbSQL.append(getActionType());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append(",");
            if (!isEmptyPreNode()) {
                sbSQL.append(getPreNode());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append(",");
            sbSQL.append("'");
            sbSQL.append(getNextNode());
            sbSQL.append("'");
            sbSQL.append(")");
            statement = conn.createStatement();
            //执行数据库操作
            System.out.println("Action.Insert():" + sbSQL.toString());
            int intResult = statement.executeUpdate(sbSQL.toString());
            //返回数据操作结果
            return intResult;
        }
        catch (Exception e) {
            //错误处理
            throw e;
        }
        finally {
            //释放数据库语句资源
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 修改方法：根据VO关键字字段，修改VO设置了值的字段
     */
    public int update(Connection conn) throws Exception {
        //变量：数据库操作语句
        Statement statement = null;
        try {
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();

            //初始化SQL语句 根据主键来更新其它字段
            sbSQL.append("UPDATE WORKFLOW_ACTION SET ");
            if (!isEmptyYWID()) {
                sbSQL.append("YWID=");
                sbSQL.append("'");
                sbSQL.append(getYWID());
                sbSQL.append("'");
                sbSQL.append(",");
            }
            if (!isEmptyWorkflowID()) {
                sbSQL.append("WORKFLOWID=");
                sbSQL.append(getWorkflowID());
                sbSQL.append(",");
            }
            if (!isEmptyID()) {
                sbSQL.append("ID=");
                sbSQL.append("'");
                sbSQL.append(getID());
                sbSQL.append("'");
                sbSQL.append(",");
            }
            if (!isEmptyRouteID()) {
                sbSQL.append("ROUTEID=");
                sbSQL.append(getRouteID());
                sbSQL.append(",");
            }
            if (!isEmptyParticipant()) {
                sbSQL.append("PARTICIPANT=");
                sbSQL.append("'");
                sbSQL.append(getParticipant());
                sbSQL.append("'");
                sbSQL.append(",");
            }
            if (!isEmptyActionTime()) {
                sbSQL.append("ACTIONTIME='");
                sbSQL.append(getActionTime());
                sbSQL.append("',");
            }
            if (!isEmptyActionType()) {
                sbSQL.append("ACTIONTYPE=");
                sbSQL.append(getActionType());
                sbSQL.append(",");
            }
            if (!isEmptyPreNode()) {
                sbSQL.append("PRENODE=");
                sbSQL.append(getPreNode());
                sbSQL.append(",");
            }
            if (!isEmptyNextNode()) {
                sbSQL.append("NEXTNODE=");
                sbSQL.append("'");
                sbSQL.append(getNextNode());
                sbSQL.append("'");
                sbSQL.append(",");
            }

            //不存在任何的可设置字段，错误处理
            if (sbSQL.toString().indexOf(",") < 0) {
                //错误处理
                throw new Exception("Action|update|DbFail|执行Action.update()方法是发生数据库操作失败异常，没有设置更新的字段！");
            }

            //删除更新语句中的最后一个逗号
            sbSQL.delete(sbSQL.length() - 1, sbSQL.length());

            //增加更新条件
            sbSQL.append(" WHERE ");
            //创建数据库操作语句
            statement = conn.createStatement();
            //执行数据库操作
            int intResult = statement.executeUpdate(sbSQL.toString());
            //返回数据操作结果
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            //释放数据库语句资源
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：新增记录，无数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public int insert() throws Exception {
        //创建数据库连接
        Connection conn = Tools.getDBConn();
        try {
            return insert(conn);
        }
        finally {
            //关闭数据库连接
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：修改记录，无数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public int update() throws Exception {
        //创建数据库连接
        Connection conn = Tools.getDBConn();
        try {
            return update(conn);
        }
        finally {
            //关闭数据库连接
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：删除记录，无数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public int delete() throws Exception {
        //创建数据库连接
        Connection conn = Tools.getDBConn();
        try {
            return delete(conn);
        }
        finally {
            //关闭数据库连接
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：删除记录，无数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public List query() throws Exception {
        //创建数据库连接
        Connection conn = Tools.getDBConn();
        try {
            return query(conn);
        }
        finally {
            //关闭数据库连接
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：得到数据库最大值ID+1
     * @param conn
     * @return
     */
    public int getMaxID(Connection conn) throws Exception {

        return MySQLDao.getSequence("system", conn);

//        //变量：数据库操作语句
//        Statement statement = null;
//        //变量：数据库结果集
//        ResultSet resultset = null;
//        try {
//            //创建数据库操作语句
//            statement = conn.createStatement();
//            //执行数据库操作
//            resultset = statement.executeQuery(
//                    "SELECT NVL(MAX(ID),0)+1 AS MAXID FROM WORKFLOW_Action");
//            resultset.next();
//            return resultset.getInt("MAXID");
//        }
//        catch (Exception e) {
//            //错误处理
//            throw e;
//        }
//        finally {
//            //释放结果集资源
//            if (resultset != null) {
//                resultset.close();
//            }
//
//            //释放数据库语句资源
//            if (statement != null) {
//                statement.close();
//            }
//        }
    }

    /**
     * 设置业务编号
     */
    public void setYWID(String YWID) {
        strYWID = YWID;
    }

    /**
     * 设置工作流编号
     */
    public void setWorkflowID(int WorkflowID) {
        intWorkflowID = WorkflowID;
    }

    /**
     * 设置Action编号
     */
    public void setID(int ID) {
        intID = ID;
    }

    /**
     * 设置
     */
    public void setRouteID(int RouteID) {
        intRouteID = RouteID;
    }

    /**
     * 设置参与者
     */
    public void setParticipant(String Participant) {
        strParticipant = Participant;
    }

    /**
     * 设置行为时间
     */
    public void setActionTime(String ActionTime) {
        strActionTime = ActionTime;
    }

    /**
     * 设置行为类型
     */
    public void setActionType(int ActionType) {
        intActionType = ActionType;
    }

    /**
     * 设置前一节点
     */
    public void setPreNode(int PreNode) {
        intPreNode = PreNode;
    }

    /**
     * 设置下一节点
     */
    public void setNextNode(String NextNode) {
        strNextNode = NextNode;
    }

    /**
     * 获取业务编号
     */
    public String getYWID() {
        return strYWID;
    }

    /**
     * 获取工作流编号
     */
    public int getWorkflowID() {
        return intWorkflowID;
    }

    /**
     * 获取Action编号
     */
    public int getID() {
        return intID;
    }

    /**
     * 获取Route编号
     */
    public int getRouteID() {
        return intRouteID;
    }

    /**
     * 获取参与者
     */
    public String getParticipant() {
        return strParticipant;
    }

    /**
     * 获取行为时间
     */
    public String getActionTime() {
        return strActionTime;
    }

    /**
     * 获取行为类型
     */
    public int getActionType() {
        return intActionType;
    }

    /**
     * 获取前一节点
     */
    public int getPreNode() {
        return intPreNode;
    }

    /**
     * 获取下一节点
     */
    public String getNextNode() {
        return strNextNode;
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyYWID() {
        if (strYWID == null || strYWID.equals("")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyWorkflowID() {
        if (intWorkflowID == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyID() {
        if (intID == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyRouteID() {
        if (intRouteID == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyParticipant() {
        if (strParticipant == null || strParticipant.equals("")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyActionTime() {
        if (strActionTime == null || strActionTime.equals("")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyActionType() {
        if (intActionType == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyPreNode() {
        if (intPreNode == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyNextNode() {
        if (strNextNode == null || strNextNode.equals("")) {
            return true;
        }
        else {
            return false;
        }
    }
}
