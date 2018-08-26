package com.youngbook.common.wf.admin;

import com.youngbook.common.wf.processdefine.*;
import com.youngbook.common.wf.common.*;

/**
 * <p>Title: </p>
 * <p>Description: 业务流历史数据记录</p>
 * 编写人： 李扬
 * 编写日期：2004-10-26
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;

public class HistoryData
        extends BizField {
    public HistoryData() {
    }

    protected String strOldVal = new String(); //原值
    protected String strNewVal = new String(); //新值
    protected String strYWID = new String(); //业务流唯一编号
    protected int intWorkflowID = Integer.MAX_VALUE; //工作流编号
    protected int intActionID = Integer.MAX_VALUE; //动作编号

    /**
     * 程序：李扬
     * 时间：2004-10-27
     * 说明：根据对象HistoryData的值，插入数据库
     * @throws Exception
     * @return int 0：失败， 1：成功
     */
    public int insert() throws Exception {
        //获得数据库连接
        Connection conn = Tools.getDBConn();
        try {
            int intResult = 0;
            //调用重载方法
            intResult = insert(conn);
            return intResult;
        }
        catch (Exception e) {
            //异常处理
            throw e;
        }
        finally {
            //关闭数据库连接
            conn.close();
        }
    }

    /**
     * 程序：李扬
     * 时间：2004-10-27
     * 说明：根据对象HistoryData的值，插入数据库
     * @throws Exception
     * @return int 0：失败， 1：成功
     */
    public int insert(Connection conn) throws Exception {
        Statement statement = null;
        try {
            int intResult = 0;

            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句
            sbSQL.append("INSERT INTO Workflow_History (FIELDName,OldVal,newVal,YWID,WorkflowID,ActionID) VALUES (");
            sbSQL.append("'");
            sbSQL.append(this.getName());
            sbSQL.append("'");
            sbSQL.append(",");
            sbSQL.append("'");
            sbSQL.append(this.getOldVal());
            sbSQL.append("'");
            sbSQL.append(",");
            sbSQL.append("'");
            sbSQL.append(this.getNewVal());
            sbSQL.append("'");
            sbSQL.append(",");
            sbSQL.append("'");
            if (!this.isEmptyYWID()) {
                sbSQL.append(this.getYWID());
            }
            else {
                throw new Exception("History|insert|NoPara|执行History.insert()发成参数不足异常，无法获得做需要的业务编号(YWID)");
            }
            sbSQL.append("'");
            sbSQL.append(",");
            if (!this.isEmptyWorkflowID()) {
                sbSQL.append(this.getWorkflowID());
            }
            else {
                throw new Exception("History|insert|NoPara|执行History.insert()发成参数不足异常，无法获得做需要的工作流编号(WorkflowID)");
            }
            sbSQL.append(",");
            if (!this.isEmptyActionID()) {
                sbSQL.append(this.getActionID());
            }
            else {
                throw new Exception("History|insert|NoPara|执行History.insert()发成参数不足异常，无法获得做需要的行为编号(ActionID)");
            }
            sbSQL.append(")");
            //后台打印SQL语句
            System.out.println("HistoryData.insert(): " + sbSQL.toString());
            statement = conn.createStatement();
            //执行数据库操作
            intResult = statement.executeUpdate(sbSQL.toString());
            return intResult;
        }
        catch (Exception e) {
            //异常处理
            throw e;
        }
        finally {
            //释放数据库资源
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 程序：李扬
     * 时间：2004-10-27
     * 说明：根据对象HistoryData的值，删除数据库
     * @throws Exception
     * @return int 0：失败， 成功删除数据条数
     */
    public int delete() throws Exception {
        //创建数据库连接
        Connection conn = null;
        try {
            int intResult = 0;
            //调用重载方法
            conn = Tools.getDBConn();
            intResult = delete(conn);
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            //释放数据库资源
            if (conn != null) {
                conn.close();
            }
        }

    }

    /**
     * 程序：李扬
     * 时间：2004-10-27
     * 说明：根据对象HistoryData的值，删除数据库
     * @throws Exception
     * @return int 0：失败， 1：成功
     */
    public int delete(Connection conn) throws Exception {
        Statement statement = null;
        try {
            int intResult = 0;
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句  根据所有字段删除记录
            sbSQL.append("DELETE WORKFLOW_HISTORY ");

            //构建精确查询条件
            StringBuffer sbWhere = new StringBuffer();
            if (!this.getName().equals("")) {
                //字段名
                sbWhere.append("FIELDName=");
                sbWhere.append("'");
                sbWhere.append(this.getName());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!this.getOldVal().equals("")) {
                //旧值
                sbWhere.append("OldVal=");
                sbWhere.append("'");
                sbWhere.append(this.getOldVal());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!this.getNewVal().equals("")) {
                //新值
                sbWhere.append("NewVal=");
                sbWhere.append("'");
                sbWhere.append(this.getNewVal());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!this.getYWID().equals("")) {
                //业务编号
                sbWhere.append("YWID=");
                sbWhere.append("'");
                sbWhere.append(this.getYWID());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!this.isEmptyWorkflowID()) {
                //工作流编号
                sbWhere.append("WorkflowID=");
                sbWhere.append(this.getWorkflowID());
                sbWhere.append(" AND ");
            }
            if (!this.isEmptyActionID()) {
                //行为编号
                sbWhere.append("ActionID=");
                sbWhere.append(this.getActionID());
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
            System.out.println("HistoryData.delete:" + sbSQL.toString());

            //创建数据库操作语句
            statement = conn.createStatement();

            //执行数据库操作
            intResult = statement.executeUpdate(sbSQL.toString());

            //返回数据操作结果
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            //释放数据库资源
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 程序：李扬
     * 时间：2004-10-27
     * 说明：根据对象HistoryData的值，更新数据库
     * @throws Exception
     * @return int 0：失败， 1：成功
     */
    public int update() throws Exception {
        //创建数据库连接
        Connection conn = null;
        try {
            int intResult = 0;
            conn = Tools.getDBConn();
            intResult = update(conn);
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            //断开数据库连接
            if (conn != null) {
                conn.close();
            }
        }

    }

    /**
     * 程序：李扬
     * 时间：2004-10-27
     * 说明：根据对象HistoryData的值，更新数据库
     * @throws Exception
     * @return int 0：失败， 1：成功
     */
    public int update(Connection conn) throws Exception {
        Statement statement = null;
        try {
            int intResult = 0;
            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句 根据主键来更新其它字段

            sbSQL.append("UPDATE WORKFLOW_HISTORYDATA SET ");
            sbSQL.append("FIELDName=");
            sbSQL.append("'");
            sbSQL.append(this.getName());
            sbSQL.append("'");
            sbSQL.append(",");
            sbSQL.append("OldVal=");
            sbSQL.append("'");
            sbSQL.append(this.getOldVal());
            sbSQL.append("'");
            sbSQL.append(",");
            sbSQL.append("NewVal=");
            sbSQL.append("'");
            sbSQL.append(this.getNewVal());
            sbSQL.append("'");
            sbSQL.append(",");
            sbSQL.append("YWID=");
            sbSQL.append("'");
            sbSQL.append(this.getYWID());
            sbSQL.append("'");
            sbSQL.append(",");
            sbSQL.append("WorkflowID=");
            if (!this.isEmptyWorkflowID()) {
                sbSQL.append(this.getWorkflowID());
            }
            else {
                sbSQL.append("NULL");
            }
            sbSQL.append(",");
            sbSQL.append("ActionID=");
            if (!this.isEmptyActionID()) {
                sbSQL.append(this.getActionID());
            }
            else {
                throw new Exception("History|update|NoPara|执行History.update()发成参数不足异常，无法获得做需要的行为编号(ActionID)");
            }
            sbSQL.append(" WHERE ");
            //打印SQL语句
            System.out.println("HistoryData.update:" + sbSQL.toString());

            //创建数据库操作语句
            statement = conn.createStatement();

            //执行数据库操作
            intResult = statement.executeUpdate(sbSQL.toString());

            //返回数据操作结果
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
     * 程序：李扬
     * 时间：2004-10-27
     * 说明：根据对象HistoryData的值，查询数据库
     * @throws Exception
     * @return List
     */
    public List query() throws Exception {
        //创建数据库连接
        Connection conn = null;
        try {
            List listResult = new ArrayList();
            conn = Tools.getDBConn();
            listResult = query(conn);
            return listResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            //断开数据库连接
            if (conn != null) {
                conn.close();
            }
        }

    }

    /**
     * 程序：李扬
     * 时间：2004-10-27
     * 说明：根据对象HistoryData的值，查询数据库
     * @throws Exception
     * @return List
     */
    public List query(Connection conn) throws Exception {
        Statement statement = null;
        ResultSet resultset = null;
        try {
            //变量：结果集列表
            List listResult = (List) (new ArrayList());

            //SQL语句字符串缓冲变量
            StringBuffer sbSQL = new StringBuffer();
            //初始化SQL语句
            sbSQL.append(
                    "SELECT FieldName,OldVal,NewVal,YWID,WorkflowID,ActionID FROM Workflow_History");

            //构建精确查询条件
            StringBuffer sbWhere = new StringBuffer();
            if (!this.getName().equals("")) {
                sbWhere.append("FIELDName=");
                sbWhere.append("'");
                sbWhere.append(this.getName());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!this.getOldVal().equals("")) {
                sbWhere.append("OldVal=");
                sbWhere.append("'");
                sbWhere.append(this.getOldVal());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!this.getNewVal().equals("")) {
                sbWhere.append("NewVal=");
                sbWhere.append("'");
                sbWhere.append(this.getNewVal());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!this.getYWID().equals("")) {
                sbWhere.append("YWID=");
                sbWhere.append("'");
                sbWhere.append(this.getYWID());
                sbWhere.append("'");
                sbWhere.append(" AND ");
            }
            if (!this.isEmptyWorkflowID()) {
                sbWhere.append("WorkflowID=");
                sbWhere.append(this.getWorkflowID());
                sbWhere.append(" AND ");
            }
            if (!this.isEmptyActionID()) {
                sbWhere.append("ActionID=");
                sbWhere.append(this.getActionID());
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

            System.out.println("HISTORYDATA.queryexact:" + sbSQL.toString());

            //创建数据库操作语句
            statement = conn.createStatement();

            //执行数据库操作
            resultset = statement.executeQuery(sbSQL.toString());

            //循环处理结果集中的纪录
            while (resultset.next()) {
                //获得结果实例
                HistoryData voResult = new HistoryData();
                //Name,OldVal,NewVal,YWID,WorkflowID,ActionID
                if (resultset.getString("FIELDName") != null) {
                    voResult.setName(resultset.getString("FieldName"));
                }
                if (resultset.getString("OldVal") != null) {
                    voResult.setOldVal(resultset.getString("OldVal"));
                }
                if (resultset.getString("NewVal") != null) {
                    voResult.setNewVal(resultset.getString("NewVal"));
                }
                if (resultset.getString("YWID") != null) {
                    voResult.setYWID(resultset.getString("YWID"));
                }
                if (resultset.getString("WorkflowID") != null) {
                    voResult.setWorkflowID(resultset.getInt("WorkflowID"));
                }
                if (resultset.getString("ActionID") != null) {
                    voResult.setActionID(resultset.getInt("ActionID"));
                }
//        //新方法：没有测试
//        voResult = HistoryData.buildObject(resultset);

                //将结果加入结果列表
                listResult.add(voResult);
            }

            //从XML中获得字段的显示名
            for (int i = 0; listResult != null && i < listResult.size(); i++) {
                HistoryData hd = (HistoryData)listResult.get(i);
                String strTitle = BizData.getFieldTitle(intWorkflowID, hd.getName());
                hd.setTitle(strTitle);
            }
            //返回数据操作结果
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

    /**
     * 程序：李扬
     * 时间：2004-12-1
     * 说明：从ResultSet中构建HistoryData对象
     * @param resultset ResultSet
     * @return HistoryData
     * @throws Exception
     */
    public static HistoryData buildObject(ResultSet resultset) throws Exception {
        HistoryData hd = new HistoryData();
        if (resultset.getString("FIELDName") != null) {
            hd.setName(resultset.getString("FieldName"));
        }
        if (resultset.getString("OldVal") != null) {
            hd.setOldVal(resultset.getString("OldVal"));
        }
        if (resultset.getString("NewVal") != null) {
            hd.setNewVal(resultset.getString("NewVal"));
        }
        if (resultset.getString("YWID") != null) {
            hd.setYWID(resultset.getString("YWID"));
        }
        if (resultset.getString("WorkflowID") != null) {
            hd.setWorkflowID(resultset.getInt("WorkflowID"));
        }
        if (resultset.getString("ActionID") != null) {
            hd.setActionID(resultset.getInt("ActionID"));
        }
        return hd;
    }

    /**
     * 设置旧值
     * @param OldVal String
     */
    public void setOldVal(String OldVal) {
        strOldVal = Tools.Encode(OldVal);
    }

    /**
     * 设置新值
     * @param NewVal String
     */
    public void setNewVal(String NewVal) {
        strNewVal = Tools.Encode(NewVal);
    }

    /**
     * 设置业务编号
     * @param YWID String
     */
    public void setYWID(String YWID) {
        strYWID = YWID;
    }

    /**
     * 设置工作流编号
     * @param WorkflowID int
     */
    public void setWorkflowID(int WorkflowID) {
        intWorkflowID = WorkflowID;
    }

    /**
     * 设置行为编号
     * @param ActionID int
     */
    public void setActionID(int ActionID) {
        intActionID = ActionID;
    }

    /**
     * 获得旧值
     * @return String
     */
    public String getOldVal() {
        return Tools.Decode(strOldVal);
    }

    /**
     * 获得新值
     * @return String
     */
    public String getNewVal() {
        return Tools.Decode(strNewVal);
    }

    /**
     * 获得业务编号
     * @return String
     */
    public String getYWID() {
        return strYWID;
    }

    /**
     * 获得工作流编号
     * @return int
     */
    public int getWorkflowID() {
        return intWorkflowID;
    }

    /**
     * 获得行为编号
     * @return int
     */
    public int getActionID() {
        return intActionID;
    }

    /**
     * 判断旧值是否为空
     * @return boolean
     */
    public boolean isEmptyOldVal() {
        if (strOldVal != null && !strOldVal.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 判断新值是否为空
     * @return boolean
     */
    public boolean isEmptyNewVal() {
        if (strNewVal != null && !strNewVal.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 判断业务编号是否为空
     * @return boolean
     */
    public boolean isEmptyYWID() {
        if (strYWID != null && !strYWID.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 判断工作流编号是否为空
     * @return boolean
     */
    public boolean isEmptyWorkflowID() {
        if (intWorkflowID == Integer.MAX_VALUE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断行为编号是否为空
     * @return boolean
     */
    public boolean isEmptyActionID() {
        if (intActionID == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

}
