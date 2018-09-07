package com.youngbook.common.wf.admin;

import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.wf.common.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.BasePO;

import java.util.*;
import java.sql.Connection;
import java.sql.*;

/**
 * <p>Title: </p>
 * <p>Description: 流转路由信息表</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RouteList extends BasePO {

    public RouteList() {
    }

    /**
     * 数据库字段对应类的每个成员变量
     */
    protected String strYWID = new String(); //业务编号
    protected int intWorkflowID = Integer.MAX_VALUE; //工作流编号
    protected int intID = Integer.MAX_VALUE; //活动编号
    protected int intPreID = Integer.MAX_VALUE; //上一活动编号
    protected int intPreNode = Integer.MAX_VALUE; //上一节点编号
    protected int intCurrentNode = Integer.MAX_VALUE; //当前节点编号
    protected String strNextNode= new String(); //下一节点编号
    protected int intStatus = Integer.MAX_VALUE; //当前业务状态 0、其他 1、Active 2、Wait  3、Cancel：中止状态  4、Finish：结束状态  5、Over：业务流完全结束   6、None：不存在此业务流，

    protected String strForwarded = new String(); //已发节点

    /**
     * 程序：李扬
     * 时间：2004-11-2
     * 说明：根据UserID、工作流编号和业务号，查找该用户所参与的所有RouteList
     *      如果没有指定工作流编号或者业务号，则查出所有参与的所有RouteList
     * @param strUserID String
     * @param routelist RouteList
     * @throws Exception
     * @return List
     */
    public static List searchRouteList(String strUserID, RouteList routelist) throws Exception {
        //数据库操作
        Connection conn = null;
        Statement statement = null;
        ResultSet resultset = null;
        try {
            List listRoute = new ArrayList();
            //查询SQL语句
            StringBuffer sbSQL = new StringBuffer();
            //查询条件语句
            StringBuffer sbWhere = new StringBuffer();

            sbSQL.append("SELECT ROUTLIST.* FROM WORKFLOW_ROUTELIST ROUTLIST, WORKFLOW_ACTION ACTION");
            //组织查询条件
            sbWhere.append("ROUTLIST.ID=ACTION.ROUTEID");
            sbWhere.append(" AND ");

            //判断UserID是否为空
            if (StringUtils.isEmpty(strUserID)) {
                throw new Exception("RouteList|searchRouteList|NoPara|执行RouteList.searchRouteList()方法时发生参数不足异常，无法获得所需的用户名(UserID)。");
            }
            sbWhere.append("ACTION.PARTICIPANT='");
            sbWhere.append(strUserID);
            sbWhere.append("' AND ");


            //判断业务号是否为空，不为空则作为查询条件
            if (!routelist.isEmptyYWID()) {
                sbWhere.append("ROUTLIST.YWID='");
                sbWhere.append(routelist.getYWID());
                sbWhere.append("' AND ");
            }

            //判断工作流是否为空，不为空则作为查询条件
            if (!routelist.isEmptyWorkflowID()) {
                sbWhere.append("ROUTLIST.WORKFLOWID=");
                sbWhere.append(routelist.getWorkflowID());
                sbWhere.append(" AND ");
            }

            //整理条件SQL语句
            if (sbWhere.length() > 0) {
                sbWhere.insert(0, " WHERE ");
                //去掉最后一个AND
                sbWhere.delete(sbWhere.length() - 5, sbWhere.length());
                //在查询语句中插入查询条件
                sbSQL.append(sbWhere.toString());
            }
            //连接数据库
            conn = Tools.getDBConn();
            statement = conn.createStatement();
            //打印SQL语句
            System.out.println("ClientApplications查找用户参与路径: " + sbSQL.toString());
            //执行SQL语句
            resultset = statement.executeQuery(sbSQL.toString());
            //循环取得RouteList
            while (resultset.next()) {
                routelist = RouteList.getInstance(resultset);
                listRoute.add(routelist);
            }
            //返回结果
            return listRoute;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            //释放数据库连接等资源
            if (resultset != null) {
                resultset.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public int getStatus() {
        return intStatus;
    }

    public void setStatus(int intStatus) {
        this.intStatus = intStatus;
    }

    /**
     * 设置
     */
    public void setYWID(String YWID) {
        strYWID = YWID;
    }

    /**
     * 设置
     */
    public void setWorkflowID(int WorkflowID) {
        intWorkflowID = WorkflowID;
    }

    /**
     * 设置
     */
    public void setID(int ID) {
        intID = ID;
    }

    /**
     * 设置
     */
    public void setPreID(int PreID) {
        intPreID = PreID;
    }

    /**
     * 设置
     */
    public void setPreNode(int PreNode) {
        intPreNode = PreNode;
    }

    /**
     * 设置
     */
    public void setCurrentNode(int CurrentNode) {
        intCurrentNode = CurrentNode;
    }

    /**
     * 设置
     */
    public void setNextNode(String NextNode) {
        strNextNode= NextNode;
    }


    /**
     * 设置
     */
    public void setForwarded(String Forwarded) {
        strForwarded = Forwarded;
    }

    /**
     * 获取
     */
    public String getYWID() {
        return strYWID;
    }

    /**
     * 获取
     */
    public int getWorkflowID() {
        return intWorkflowID;
    }

    /**
     * 获取
     */
    public int getID() {
        return intID;
    }

    /**
     * 获取
     */
    public int getPreID() {
        return intPreID;
    }

    /**
     * 获取
     */
    public int getPreNode() {
        return intPreNode;
    }

    /**
     * 获取
     */
    public int getCurrentNode() {
        return intCurrentNode;
    }

    /**
     * 获取
     */
    public String getNextNode() {
        return strNextNode;
    }


    /**
     * 获取
     */
    public String getForwarded() {
        return strForwarded;
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyYWID() {
        if (strYWID.equals("")) {
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
    public boolean isEmptyPreID() {
        if (intPreID == Integer.MAX_VALUE) {
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
    public boolean isEmptyCurrentNode() {
        if (intCurrentNode == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyNEXTNODE() {
        if (strNextNode.equals("")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyStatu() {
        if (intStatus == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为空
     */
    public boolean isEmptyForwarded() {
        if (strForwarded.equals("")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 插入方法：将参数VO中的数据插入数据库
     */
    public int insert(Connection conn) throws
            Exception {
        //变量：数据库操作语句
        Statement statement = null;
        try {
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句
            sbSQL.append("INSERT INTO WORKFLOW_ROUTELIST(YWID,WORKFLOWID,ID,PREID,PRENODE,CURRENTNODE,NEXTNODE,STATU,FORWARDED) VALUES (");
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
            sbSQL.append(getID());
            sbSQL.append(",");
            if (!isEmptyPreID()) {
                sbSQL.append(getPreID());
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
            if (!isEmptyCurrentNode()) {
                sbSQL.append(getCurrentNode());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append(",");
            sbSQL.append("'");
            sbSQL.append(getNextNode());
            sbSQL.append("'");
            sbSQL.append(",");
            if (!isEmptyStatu()) {
                sbSQL.append(getStatus());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append(",");
            sbSQL.append("'");
            sbSQL.append(getForwarded());
            sbSQL.append("'");
            sbSQL.append(")");
            //创建数据库操作语句
            statement = conn.createStatement();
            System.out.println("RouteList.Insert:"+sbSQL.toString());
            //执行数据库操作
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
     * 程序：李扬
     * 时间：2004-11-2
     * 说明：根据自身业务号和工作流编号查找数据库，填充其他成员信息
     * @throws Exception
     */
    public void buildself() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultset = null;
        try {
            StringBuffer sbSQL = new StringBuffer();
            StringBuffer sbWhere = new StringBuffer();

            sbSQL.append("SELECT YWID,WORKFLOWID,ID,PREID,PRENODE,CURRENTNODE,NEXTNODE,STATU,FORWARDED FROM WORKFLOW_ROUTELIST WHERE ID=");
            sbSQL.append(intID);
            System.out.println("RouteList.buildself(): " + sbSQL.toString());
            conn = Tools.getDBConn();
            statement = conn.createStatement();
            resultset = statement.executeQuery(sbSQL.toString());
            if (resultset.next()) {
                if (resultset.getString("YWID") != null) {
                    this.setYWID(resultset.getString("YWID"));
                }
                if (resultset.getString("WORKFLOWID") != null) {
                    this.setWorkflowID(resultset.getInt("WORKFLOWID"));
                }
                if (resultset.getString("ID") != null) {
                    this.setID(resultset.getInt("ID"));
                }
                if (resultset.getString("PREID") != null) {
                    this.setPreID(resultset.getInt("PREID"));
                }
                if (resultset.getString("PRENODE") != null) {
                    this.setPreNode(resultset.getInt("PRENODE"));
                }
                if (resultset.getString("CURRENTNODE") != null) {
                    this.setCurrentNode(resultset.getInt("CURRENTNODE"));
                }
                if (resultset.getString("NEXTNODE") != null) {
                    this.setNextNode(resultset.getString("NEXTNODE"));
                }
                if (resultset.getString("Statu") != null) {
                    this.setStatus(resultset.getInt("STATU"));
                }
                if (resultset.getString("FORWARDED") != null) {
                    this.setForwarded(resultset.getString("FORWARDED"));
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (resultset != null) {
                resultset.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 说明：将数据库中工作流编号和业务号等于该RouteList的业务状态全改为此RouteList的业务状态
     * @throws Exception
     * @return int
     */
    public int updateStatu() throws Exception {
        Connection conn = null;
        try {
            conn = Tools.getDBConn();
            return updateStatu(conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 说明：将数据库中工作流编号和业务号等于该RouteList的业务状态全改为此RouteList的业务状态
     * @throws Exception
     * @return int
     */

    public int updateStatu(Connection conn) throws Exception {
        Statement statement = null;
        try {
            int intResult = 0;
            if ( this.isEmptyYWID() || this.isEmptyWorkflowID()) {
                throw new Exception("RouteList|updateStatu|NoPara|执行RouteList.updateStatu()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)或是业务编号(YWID)。");
            }
            if (this.isEmptyStatu()) {
                throw new Exception("RouteList|updateStatu|NoPara|执行RouteList.updateStatu()方法时发生参数不足异常，无法获得所需的状态值(Statu)。");
            }
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("UPDATE WORKFLOW_ROUTELIST SET STATU=");
            sbSQL.append(this.getStatus());
            sbSQL.append(" WHERE WORKFLOWID=");
            sbSQL.append(this.getWorkflowID());
            sbSQL.append(" AND YWID='");
            sbSQL.append(this.getYWID());
            sbSQL.append("'");

            statement = conn.createStatement();
            intResult = statement.executeUpdate(sbSQL.toString());
            System.out.println("RouteList.updateStatu:" + sbSQL.toString());
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
    /**
     * 修改方法：根据VO关键字字段，修改数据库记录的非关键字字段
     */
    public int update(Connection conn) throws
            Exception {
        //变量：数据库操作语句
        Statement statement = null;

        try {
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句 根据主键来更新其它字段

            sbSQL.append("UPDATE WORKFLOW_ROUTELIST SET ");
            sbSQL.append("YWID=");
            sbSQL.append("'");
            sbSQL.append(getYWID());
            sbSQL.append("'");
            sbSQL.append(",");
            sbSQL.append("WORKFLOWID=");
            if (!isEmptyWorkflowID()) {
                sbSQL.append(getWorkflowID());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append(",");
            sbSQL.append("ID=");
            if (!isEmptyID()) {
                sbSQL.append(getID());
            }
            else {
                sbSQL.append("");
            }

            if (!isEmptyPreID()) {
                sbSQL.append(",");
                sbSQL.append("PREID=");
                sbSQL.append(getPreID());
            }
            else {
                sbSQL.append("");
            }

            if (!isEmptyPreNode()) {
                sbSQL.append(",");
                sbSQL.append("PRENODE=");
                sbSQL.append(getPreNode());
            }
            else {
                sbSQL.append("");
            }

            if (!isEmptyCurrentNode()) {
                sbSQL.append(",");
                sbSQL.append("CURRENTNODE=");
                sbSQL.append(getCurrentNode());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append(",");
            sbSQL.append("NEXTNODE=");
            sbSQL.append("'");
            sbSQL.append(getNextNode());
            sbSQL.append("'");
            if (!isEmptyStatu()) {
                sbSQL.append(",");
                sbSQL.append("STATU=");
                sbSQL.append(getStatus());
            }
            else {
                sbSQL.append("");
            }
            sbSQL.append(",");
            sbSQL.append("FORWARDED=");
            sbSQL.append("'");
            sbSQL.append(getForwarded());
            sbSQL.append("'");
            sbSQL.append(" WHERE ID=");
            sbSQL.append(getID());
            //创建数据库操作语句
            statement = conn.createStatement();
            System.out.println("RouteList.Update:" + sbSQL.toString());
            //执行数据库操作
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

    public int updateByField(Connection conn) throws Exception {
        //变量：数据库操作语句
        Statement statement = null;

        try {
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();

            //初始化SQL语句 根据主键来更新其它字段
            sbSQL.append("UPDATE WORKFLOW_ROUTELIST SET ");
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
                sbSQL.append(getID());
                sbSQL.append(",");
            }
            if (!isEmptyPreID()) {
                sbSQL.append("PREID=");
                sbSQL.append(getPreID());
                sbSQL.append(",");
            }
            if (!isEmptyPreNode()) {
                sbSQL.append("PRENODE=");
                sbSQL.append(getPreNode());
                sbSQL.append(",");
            }
            if (!isEmptyCurrentNode()) {
                sbSQL.append("CURRENTNODE=");
                sbSQL.append(getCurrentNode());
                sbSQL.append(",");
            }
            if (!isEmptyNEXTNODE()) {
                sbSQL.append("NEXTNODE=");
                sbSQL.append("'");
                sbSQL.append(getNextNode());
                sbSQL.append("'");
                sbSQL.append(",");
            }
            if (!isEmptyStatu()) {
                sbSQL.append("STATU=");
                sbSQL.append(getStatus());
                sbSQL.append(",");
            }
            if (!isEmptyForwarded()) {
                sbSQL.append("FORWARDED=");
                sbSQL.append("'");
                sbSQL.append(getForwarded());
                sbSQL.append("'");
                sbSQL.append(",");
            }

            //不存在任何的可设置字段，错误处理
            if (sbSQL.toString().indexOf(",") < 0) {
//错误处理
                throw new Exception("RouteList|updateStatu|NoPara|执行RouteList.updateStatu()方法时发生参数不足异常，无法获得所需更新字段。");
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
     * 删除方法：根据VO字段内容做为条件删除数据库记录
     */
    public int delete(Connection conn) throws Exception {
        //变量：数据库操作语句
        Statement statement = null;
        try {
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句  根据所有字段删除记录
            sbSQL.append("DELETE WORKFLOW_ROUTELIST ");

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
            if (!isEmptyID()) {
                sbWhere.append("ID=");
                sbWhere.append(getID());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreID()) {
                sbWhere.append("PREID=");
                sbWhere.append(getPreID());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreNode()) {
                sbWhere.append("PRENODE=");
                sbWhere.append(getPreNode());
                sbWhere.append(" AND ");
            }
            if (!isEmptyCurrentNode()) {
                sbWhere.append("CURRENTNODE=");
                sbWhere.append(getCurrentNode());
                sbWhere.append(" AND ");
            }
            if (!getNextNode().equals("")) {
                sbWhere.append("NEXTNODE=");
                sbWhere.append("'");
                sbWhere.append(getNextNode());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyStatu()) {
                sbWhere.append("STATU=");
                sbWhere.append(getStatus());
                sbWhere.append(" AND ");
            }
            if (!getForwarded().equals("")) {
                sbWhere.append("FORWARDED=");
                sbWhere.append("'");
                sbWhere.append(getForwarded());
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
            System.out.println("RouteList.delete(): " + sbSQL.toString());
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
     * 实例化函数：静态方法，根据ResultSet构造实例
     */
    public static RouteList getInstance(ResultSet resultset) throws Exception {
        try {
            //创建实例
            RouteList rotelist = new RouteList();

            //设置属性值
            if (resultset.getString("YWID") != null) {
                rotelist.setYWID(resultset.getString("YWID"));
            }
            if (resultset.getString("WORKFLOWID") != null) {
                rotelist.setWorkflowID(resultset.getInt("WORKFLOWID"));
            }
            if (resultset.getString("ID") != null) {
                rotelist.setID(resultset.getInt("ID"));
            }
            if (resultset.getString("PREID") != null) {
                rotelist.setPreID(resultset.getInt("PREID"));
            }
            if (resultset.getString("PRENODE") != null) {
                rotelist.setPreNode(resultset.getInt("PRENODE"));
            }
            if (resultset.getString("CURRENTNODE") != null) {
                rotelist.setCurrentNode(resultset.getInt("CURRENTNODE"));
            }
            if (resultset.getString("NEXTNODE") != null) {
                rotelist.setNextNode(resultset.getString("NEXTNODE"));
            }
            if (resultset.getString("STATU") != null) {
                rotelist.setStatus(resultset.getInt("STATU"));
            }
            if (resultset.getString("FORWARDED") != null) {
                rotelist.setForwarded(resultset.getString("FORWARDED"));
            }
            //返回实例
            return rotelist;
        }
        catch (Exception e) {
            //错误处理
            throw e;
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
            sbSQL.append("SELECT YWID,WORKFLOWID,ID,PREID,PRENODE,CURRENTNODE,NEXTNODE,STATU,FORWARDED FROM WORKFLOW_ROUTELIST");
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
            if (!isEmptyID()) {
                sbWhere.append("ID=");
                sbWhere.append(getID());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreID()) {
                sbWhere.append("PREID=");
                sbWhere.append(getPreID());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreNode()) {
                sbWhere.append("PRENODE=");
                sbWhere.append(getPreNode());
                sbWhere.append(" AND ");
            }
            if (!isEmptyCurrentNode()) {
                sbWhere.append("CURRENTNODE=");
                sbWhere.append(getCurrentNode());
                sbWhere.append(" AND ");
            }
            if (!getNextNode().equals("")) {
                sbWhere.append("NEXTNODE LIKE ");
                sbWhere.append("'%");
                sbWhere.append(getNextNode());
                sbWhere.append("%'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyStatu()) {
                sbWhere.append("STATU=");
                sbWhere.append(getStatus());
                sbWhere.append(" AND ");
            }
            if (!getForwarded().equals("")) {
                sbWhere.append("FORWARDED LIKE ");
                sbWhere.append("'%");
                sbWhere.append(getForwarded());
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
            System.out.println("RouteList.query():"+sbSQL.toString());

            resultset = statement.executeQuery(sbSQL.toString());
            //循环处理结果集中的纪录
            while (resultset.next()) {
                //获得结果实例
                RouteList routelist = getInstance(resultset);
                //将结果加入结果列表
                listResult.add(routelist);
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
            sbSQL.append("SELECT YWID,WORKFLOWID,ID,PREID,PRENODE,CURRENTNODE,NEXTNODE,STATU,FORWARDED FROM WORKFLOW_ROUTELIST");

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
            if (!isEmptyID()) {
                sbWhere.append("ID=");
                sbWhere.append(getID());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreID()) {
                sbWhere.append("PREID=");
                sbWhere.append(getPreID());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreNode()) {
                sbWhere.append("PRENODE=");
                sbWhere.append(getPreNode());
                sbWhere.append(" AND ");
            }
            if (!isEmptyCurrentNode()) {
                sbWhere.append("CURRENTNODE=");
                sbWhere.append(getCurrentNode());
                sbWhere.append(" AND ");
            }
            if (!getNextNode().equals("")) {
                sbWhere.append("NEXTNODE=");
                sbWhere.append("'");
                sbWhere.append(getNextNode());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!isEmptyStatu()) {
                sbWhere.append("STATU=");
                sbWhere.append(getStatus());
                sbWhere.append(" AND ");
            }
            if (!getForwarded().equals("")) {
                sbWhere.append("FORWARDED=");
                sbWhere.append("'");
                sbWhere.append(getForwarded());
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

            //排序
//      sbSQL.append(" ORDER BY ID DESC");
            System.out.println("RouteList.queryExcat():" +sbSQL.toString());

            //创建数据库操作语句
            statement = conn.createStatement();

            //执行数据库操作
            resultset = statement.executeQuery(sbSQL.toString());

            //循环处理结果集中的纪录
            while (resultset.next()) {
                //获得结果实例
                RouteList voResult = getInstance(resultset);
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
     * 程序：
     * 说明：新增记录，无数据库连接
     * @return 1成功，非1失败
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
     * 说明：修改记录，无数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public int updateByField() throws Exception {
        Connection conn = Tools.getDBConn();
        try {
            return updateByField(conn);
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
     * 说明：删除记录，无数据库连接
     * @return 1成功，非1失败
     * @throws java.lang.Exception
     */
    public List query() throws Exception {
        Connection conn = Tools.getDBConn();
        try {
            return query(conn);
        }
        finally {
            conn.close();
        }
    }

    /**
     * 程序：
     * 说明：查询业务流当前状态
     * @return 状态描述，
     *       0、其他
     *       1、Active
     *       2、Wait
     *       3、Cancel：中止状态
     *       4、Finish：结束状态(有节点为Finish)
     *       5、Over：业务流完全结束
     *       6、None：不存在此业务流，
     * @throws java.lang.Exception
     */
    public int queryStatus(Connection conn) throws Exception {
        List lsResult = this.queryExact(conn);
        int intResult = 6;
        Iterator itResult = lsResult.iterator();
        while (itResult.hasNext()) {
            intResult = 0;
            intResult = ( (RouteList) itResult.next()).getStatus();
            break;
        }
        return intResult;
    }
    /**
     * 程序：
     * 说明：得到数据库最大值ID+1
     * @param conn
     * @return
     */
    public int getMaxID(Connection conn) throws Exception {

        return MySQLDao.getSequence("system", conn);
    }

    /**
     * 查询方法：根据VO字段内容做为条件做为查询条件，查询非finish状态的记录
     */
    public List queryNoFinish(Connection conn) throws Exception {
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
            sbSQL.append("SELECT YWID,WORKFLOWID,ID,PREID,PRENODE,CURRENTNODE,NEXTNODE,STATU,FORWARDED FROM WORKFLOW_ROUTELIST");
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
            if (!isEmptyID()) {
                sbWhere.append("ID=");
                sbWhere.append(getID());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreID()) {
                sbWhere.append("PREID=");
                sbWhere.append(getPreID());
                sbWhere.append(" AND ");
            }
            if (!isEmptyPreNode()) {
                sbWhere.append("PRENODE=");
                sbWhere.append(getPreNode());
                sbWhere.append(" AND ");
            }
            if (!isEmptyCurrentNode()) {
                sbWhere.append("CURRENTNODE=");
                sbWhere.append(getCurrentNode());
                sbWhere.append(" AND ");
            }
            if (!getNextNode().equals("")) {
                sbWhere.append("NEXTNODE LIKE ");
                sbWhere.append("'%");
                sbWhere.append(getNextNode());
                sbWhere.append("%'");
                sbWhere.append(" AND ");
            }

            sbWhere.append("STATU!=4");
            sbWhere.append(" AND ");

            if (!getForwarded().equals("")) {
                sbWhere.append("FORWARDED LIKE ");
                sbWhere.append("'%");
                sbWhere.append(getForwarded());
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
                RouteList routelist = getInstance(resultset);
                //将结果加入结果列表
                listResult.add(routelist);
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
     * 程序：李扬
     * 时间：2004-11-22
     * @return List
     * @throws Exception
     */
    public List queryYWID() throws Exception {
        Connection conn = Tools.getDBConn();
        try {
            return this.queryYWID(conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public List queryYWID(Connection conn) throws Exception {
        try {
            Statement statement = null;
            ResultSet resultset = null;
            try {
                List listResult = new ArrayList();
                StringBuffer sbSQL = new StringBuffer();
                StringBuffer sbWhere = new StringBuffer();
                sbSQL.append("SELECT DISTINCT(YWID) FROM WORKFLOW_ROUTELIST");
                if (!this.isEmptyWorkflowID()) {
                    sbWhere.append("WORKFLOWID=");
                    sbWhere.append(intWorkflowID);
                }
                if (sbWhere.length() > 0) {
                    sbWhere.insert(0, " WHERE ");
                    sbSQL.append(sbWhere.toString());
                }
                System.out.println("RouteList.queryYWID():" + sbSQL.toString());
                statement = conn.createStatement();
                resultset = statement.executeQuery(sbSQL.toString());

                while (resultset.next()) {
                    if (resultset.getString("YWID") != null &&
                            !resultset.getString("YWID").equals("")) {
                        listResult.add(resultset.getString("YWID"));
                    }
                }
                return listResult;
            }
            catch (Exception e) {
                throw e;
            }
            finally {
                if (resultset != null) {
                    resultset.close();
                }
                if (statement != null) {
                    statement.close();
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
    }

}
