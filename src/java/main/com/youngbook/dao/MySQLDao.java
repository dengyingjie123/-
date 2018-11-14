package com.youngbook.dao;

import com.youngbook.annotation.*;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.*;
import com.youngbook.entity.po.UserPO;
import com.youngbook.service.system.LogService;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Lee
 * Date: 14-4-1
 */
public class MySQLDao {

    public static void main(String [] args) throws Exception{

        Connection conn = Config.getConnection();
        try {

            List<KVObject> conditions = new ArrayList<KVObject>();
            QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

            String sql = "select * from system_user where name like ?";

            List<KVObject> parameters = new ArrayList<KVObject>();
            parameters.add(new KVObject(1, "%若%"));

            UserPO user = new UserPO();
            user.setMobile("1");
            Pager pager = MySQLDao.search(sql, parameters, user, null, 1, 15, queryType, conn);
//            for (UserPO t : users) {
//                System.out.println(t.getName());
//            }

            System.out.println(pager.getData().size());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Database.close(conn);
        }
    }


    private static void close(ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
        }
    }

    private static void close(PreparedStatement prestmt) throws Exception {
        if (prestmt != null) {
            prestmt.close();
        }
    }

    private static void close(Connection conn) throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    private static void close(ResultSet rs, PreparedStatement prestmt) throws Exception {
        close(rs);
        close(prestmt);
    }

    private static void close(PreparedStatement prestmt, Connection conn) throws Exception {
        close(prestmt);
        close(conn);
    }

    private static void close(ResultSet rs, PreparedStatement prestmt, Connection conn) throws Exception {
        close(rs);
        close(prestmt);
        close(conn);
    }



    /**
     * 查询符合条件的一条数据
     * @param object
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T load(T object, Class<T> clazz) throws Exception{
        List<T> list = MySQLDao.search(object, clazz, null, null);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     *
     * @param object
     * @param kvObjects
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     * 重载load方法加入限制条件
     */
    public static <T> T load(T object, List<KVObject> kvObjects, Class<T> clazz) throws Exception{
        List<T> list = MySQLDao.search(object, clazz, kvObjects, null);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public static <T> T load(T object, Class<T> clazz, Connection conn) throws Exception{
        List<T> list = MySQLDao.search(object, clazz, null, null, conn);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public static int getSequence(String id) throws Exception {

        Connection conn = Config.getConnection();

        try {
            return MySQLDao.getSequence(id, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }

        return -1;
    }


    public static int getSequence(String id, Connection conn) throws Exception {

        Statement stmt = null;
        ResultSet rs = null;
        int maxId = -1;
        try {
            String sql = "select _nextval('"+id+"') maxid;";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            LogService.info("MySQLDao.getSequence(): " + sql, MySQLDao.class);

            if (rs.next()) {
                maxId = rs.getInt("maxid");
            }

            return maxId;
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return -1;
    }

    public static int getSequence_old(String id, Connection conn) throws Exception {

        Statement stmt = null;
        ResultSet rs = null;
        int maxId = -1;
        try {
            String sql = "SELECT if (max(v) is null, 1, max(v) + 1) maxid FROM system_sequence where id='"+id+"'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            LogService.info("MySQLDao.getSequence(): " + sql, MySQLDao.class);

            if (rs.next()) {
                maxId = rs.getInt("maxid");
            }

            String sqlUpdate = "update system_sequence set v="+maxId+" where id='"+id+"'";
            LogService.info("MySQLDao.getSequence(): " + sqlUpdate, MySQLDao.class);
            stmt.executeUpdate(sqlUpdate);

            return maxId;
        }
        catch (Exception e) {
            MyException.deal(e);

        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return -1;
    }

    /**
     * 设置prepared statement数值
     * @param preStmt
     * @param index
     * @param value
     * @author leevits
     * @throws Exception
     */
    public static void preparedStatementSetValue(int index, Object value, PreparedStatement preStmt) throws Exception {

        if (preStmt == null) {
            throw new Exception("MyDao.preparedStatementSetValue(): PreparedStatement为空");
        }

        if (value == null) {
            throw new Exception("MyDao.preparedStatementSetValue(): 预设值为空");
        }

        if (Database.isStringType(value.getClass())) {
            preStmt.setString(index, value.toString());
        }
        else if (Database.isDouble(value.getClass())) {
            Double doubleValue = Double.parseDouble(value.toString());
            if (doubleValue == Double.MAX_VALUE) {
                doubleValue = null;
                preStmt.setNull(index, Types.DOUBLE);
            }
            else {
                preStmt.setDouble(index, doubleValue);
            }
        }
        else if (Database.isInteger(value.getClass())) {
            Integer intValue = Integer.parseInt(value.toString());
            if (intValue == Integer.MAX_VALUE) {
                intValue = null;
                preStmt.setNull(index, Types.INTEGER);
            }
            else {
                preStmt.setInt(index, intValue);
            }

        }
        else if (Database.isFloat(value.getClass())) {
            Float floatValue = Float.parseFloat(value.toString());
            if (floatValue == Float.MAX_VALUE) {
                floatValue = null;
                preStmt.setNull(index, Types.FLOAT);
            }
            else {
                preStmt.setDouble(index, floatValue);
            }

        }
        else if (Database.isLong(value.getClass())) {
            Long longValue = Long.parseLong(value.toString());
            if (longValue == Long.MAX_VALUE) {
                longValue = null;
            }
            preStmt.setLong(index, longValue);
        }
        else {
            throw new Exception("MyDao.preparedStatementSetValue(): 尚未支持的数据类型："+value.getClass().getName());
        }
    }

    public static int getSequence_new(String id, Connection conn) throws Exception {

        PreparedStatement prestmt = null;
        ResultSet rs = null;
        int maxId = -1;
        try {
            String sql = "SELECT if (max(v) is null, 1, max(v) + 1) maxid FROM system_sequence where id=?";
            prestmt = conn.prepareStatement(sql);
            preparedStatementSetValue(1, id, prestmt);
            rs = prestmt.executeQuery();
            LogService.info("MySQLDao.getSequence(): " + sql, MySQLDao.class);

            if (rs.next()) {
                maxId = rs.getInt("maxid");
            }

            String sqlUpdate = "update system_sequence set v=? where id=?";

            LogService.info("MySQLDao.getSequence(): " + sqlUpdate, MySQLDao.class);

            prestmt = conn.prepareStatement(sqlUpdate);
            preparedStatementSetValue(1, maxId, prestmt);
            preparedStatementSetValue(2, id, prestmt);

            prestmt.executeUpdate();

            return maxId;
        }
        catch (Exception e) {
            MyException.deal(e);

        }
        finally {
            close(rs, prestmt);
        }

        return -1;
    }

    public static int getMaxSid(String tableName) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return getMaxSid(tableName, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }

        return -1;
    }

    /**
     * 获得最大的SID编号
     * @param tableName
     * @param conn
     * @return
     * @throws Exception
     */
    public static int getMaxSid(String tableName, Connection conn) throws Exception {
        int maxId = -1;

//        if (Database.containsUnsupportCode(tableName)) {
//            throw MyException.UNSUPPORT_EXCEPTION;
//        }
//
//        try {
//            maxId = MySQLDao.getSequence(tableName, conn);
//        }
//        catch (Exception e) {
//            LogService.info(e.getMessage(), MySQLDao.class);
//        }
//
//
//        if (maxId > 0) {
//            return maxId;
//        }


        // 没有创建过sequence，用最大sid创建好以后，再返回最大序列

        Statement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT if (max(sid) is null, 1, max(sid) + 1) maxid FROM " + tableName;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            LogService.info("MySQLDao.getmaxSid(): " + sql, MySQLDao.class);

            if (rs.next()) {
                maxId = rs.getInt("maxid");
            }

            return maxId;

            // 保存最新的最大值到序列里，序列是先取值，后累加，所以这里初始化需要+2
//            maxId += 2;
//            String sqlSequence = "insert into system_sequence values ('"+tableName+"', "+ (maxId + 2)+")";
//
//            stmt.executeUpdate(sqlSequence);

        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }




        return maxId;
    }

    /**
     * 获取最大的顺序号并且加1
     * @param tableName
     * @param conn
     * @return
     * @throws Exception
     */
    public static int getMaxOrderNumber(String tableName, Connection conn) throws Exception{
        Integer maxOrderNumber = new Integer(0);

        Statement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT if (max(orderNumber) is null, 1, max(orderNumber) + 1) orderNumber FROM " + tableName;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            LogService.info("MySQLDao.getMaxOrderNumber(): " + sql, MySQLDao.class);

            if (rs.next()) {
                maxOrderNumber = rs.getInt("orderNumber");

            }

        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return maxOrderNumber;
    }

    /**
     * 处理顺序号，返回给Service
     * @param number
     * @return
     * @throws Exception
     */
    public static String getOrderNumber(Integer number) throws Exception{
        String orderNumber = number.toString();
        if (orderNumber.length() == 1){
            orderNumber = "0000" + orderNumber;
        }else if (orderNumber.length() == 2){
            orderNumber = "000" + orderNumber;
        }else if (orderNumber.length() == 3){
            orderNumber = "00" + orderNumber;
        }else if (orderNumber.length() == 4){
            orderNumber = "0" + orderNumber;
        }else {
            return orderNumber;
        }
        return  orderNumber;
    }

    @Deprecated
    public static <T> int deletePhysically(T object) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return deletePhysically(object, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return 0;
    }


    public static int update(String sql) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return update(sql, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return 0;
    }

    public static int primaryUpdate (String sql, List<KVObject> parameters, Connection conn) throws Exception {

        PreparedStatement prestmt = null;

        try {

            prestmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }

            return prestmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int primaryUpdate (DatabaseSQL dbSQL, Connection conn) throws Exception {

        PreparedStatement prestmt = null;

        try {

            String sql = dbSQL.getSQL();
            List<KVObject> parameters = dbSQL.getParameters();

            prestmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }

            return prestmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int update(String sql, Connection conn) throws Exception {
        int count = 0;
        Statement stmt = null;
        try {
            // 获得数据库资源
            stmt = conn.createStatement();

            // 打印日志
            LogService.info("MySQLDao.update(): " + sql, MySQLDao.class);

            // 执行更新操作
            count = stmt.executeUpdate(sql);
        }
        catch (Exception e) {
            // 打印日志
            MyException.deal(e);
            // 设置失败标识
        }
        finally {
            // 释放数据库资源
            if (stmt != null) {
                stmt.close();
            }
        }

        return count;
    }

    @Deprecated
    public static <T> int deletePhysically(T object, Connection conn) throws Exception {

        String strSQL = "delete from {0} where {1}";

        String strPK = Database.getTablePrimaryKey(object.getClass());
        Object value = Database.getPrimaryKeyValue(object);

        if (!Database.isAvailableValue(value)) {
            throw MyException.UNSUPPORT_EXCEPTION;
        }

        String tag = "";
        if (Database.isStringType(value.getClass())) {
            tag = "'";
            value = Database.encodeSQL(String.valueOf(value));
        }

        StringBuffer sbPK = new StringBuffer(strPK).append("=");
        sbPK.append(tag).append(value).append(tag);

        strSQL = MessageFormat.format(strSQL, Database.getTableName(object.getClass()),
                sbPK.toString());

        int count = 0;
        Statement stmt = null;
        try {
            // 获得数据库资源
            stmt = conn.createStatement();

            // 打印日志
            LogService.info("MySQLDao.doDelete(): " + strSQL, MySQLDao.class);

            // 执行更新操作
            count = stmt.executeUpdate(strSQL);
        }
        catch (Exception e) {
            // 打印日志
            MyException.deal(e);
            // 设置失败标识
        }
        finally {
            // 释放数据库资源
            if (stmt != null) {
                stmt.close();
            }
        }

        return count;
    }

    public static <T> int remove(T object, String operatorId) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return remove(object, operatorId, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    public static <T> int remove(T object, Connection conn) throws Exception {
        return remove(object, Config.getDefaultOperatorId(), conn);
    }

    public static <T> int remove(T object, String operatorId, Connection conn) throws Exception {

        Class clazz = object.getClass();

        Method methodGetSid = Utils.getMethod(clazz, "getSid", new Class[]{});
        Method methodGetId = Utils.getMethod(clazz, "getId", new Class[]{});

        Method methodSetSid = Utils.getMethod(clazz, "setSid", new Class[]{int.class});
        Method methodSetId = Utils.getMethod(clazz, "setId", new Class[]{String.class});
        Method methodSetState = Utils.getMethod(clazz, "setState", new Class[]{int.class});
        Method methodSetOperatorId = Utils.getMethod(clazz, "setOperatorId", new Class[]{String.class});
        Method methodSetOperateTime = Utils.getMethod(clazz, "setOperateTime", new Class[]{String.class});

        String tableName = Database.getTableName(clazz);


        Annotation idAnnotationTemp = clazz.getDeclaredField("sid").getAnnotation(Id.class);
        boolean isLongId = false;
        Id idAnnotation = null;
        if (idAnnotationTemp != null) {
            idAnnotation = (Id)idAnnotationTemp;
            if (idAnnotation.type() == IdType.LONG) {
                isLongId = true;
                methodSetSid = Utils.getMethod(clazz, "setSid", new Class[]{long.class});
            }
            else {
                isLongId = false;
                methodSetSid = Utils.getMethod(clazz, "setSid", new Class[]{int.class});
            }
        }


        Object sidValue = methodGetSid.invoke(object, new Object[] {});
        int sidInt = Integer.MAX_VALUE;
        if (!isLongId && sidValue != null) {
            sidInt = (Integer)sidValue;
        }


        long sidLong = Long.MAX_VALUE;
        if (isLongId && sidValue != null) {
            sidLong = (Long)sidValue;
        }


        Object idValue = methodGetId.invoke(object, new Object[] {});
        String id = idValue == null ? "" : (String) idValue;

        if (!isLongId && sidInt == Integer.MAX_VALUE && StringUtils.isEmpty(id)) {
            MyException.newInstance("删除数据失败，无法获得删除数据主键").throwException();
        }


        if (isLongId && sidLong == Long.MAX_VALUE && StringUtils.isEmpty(id)) {
            MyException.newInstance("删除数据失败，无法获得删除数据主键").throwException();
        }


        /**
         * 删除数据都是根据数据主键sid删除
         *
         * 如果待删除数据只有id，则需要查询最新的记录，从而获得sid
         *
         * 得到sid以后，将原有记录改为更新状态，并新插入一条删除状态的数据，实现无数据丢失的删除效果
         */
        Object temp = clazz.newInstance();

        /**
         * 设置主键或组合主键，查询数据库，获得sid
         */
        if (isLongId) {
            if (sidLong == Long.MAX_VALUE) {
                methodSetId.invoke(temp, id);
                methodSetState.invoke(temp, Config.STATE_CURRENT);
            }
            else {
                methodSetSid.invoke(temp, sidLong);
            }
        }
        else {
            if (sidInt == Integer.MAX_VALUE) {
                methodSetId.invoke(temp, id);
                methodSetState.invoke(temp, Config.STATE_CURRENT);
            }
            else {
                methodSetSid.invoke(temp, sidInt);
            }
        }


        temp = MySQLDao.load(temp, clazz, conn);

        if (temp == null) {
            MyException.newInstance("无法删除原有数据，没有找到原始数据", "sid="+sidValue+"&id=" + id).throwException();
        }

        methodSetState.invoke(temp, Config.STATE_UPDATE);
        int count = MySQLDao.update(temp, conn);

        if (count == 1) {

            if (isLongId) {
                sidLong = IdUtils.newLongId();
                methodSetSid.invoke(temp, sidLong);
            }
            else {
                sidInt = MySQLDao.getMaxSid(tableName, conn);
                methodSetSid.invoke(temp, sidInt);
            }


            methodSetState.invoke(temp, Config.STATE_DELETE);
            methodSetOperatorId.invoke(temp, operatorId);
            methodSetOperateTime.invoke(temp, TimeUtils.getNow());
            count = MySQLDao.insert(temp, conn);
        }

        if (count != 1) {
            throw new Exception("删除数据失败");
        }

        return 1;
    }

    public static <T> int update(T object) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return update(object, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return 0;
    }

    public static <T> int update_old(T object, Connection conn) throws Exception {
        String strSQL = "update {0} set {1} where {2}";

        Class clazz = object.getClass();

        String strPK = Database.getTablePrimaryKey(clazz);
        StringBuffer sbFiled = new StringBuffer();
        StringBuffer sbPK = new StringBuffer(strPK).append("=");

        for(Field field : object.getClass().getDeclaredFields()) {

            if (isIgnore(field)) {
                continue;
            }

            String firstLetter = field.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + field.getName().substring(1);

            Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});
            Object value = getMethod.invoke(object, new Object[] {});

            if (value == null) {
                continue;
            }

            String tag = "";
            if (Database.isStringType(field.getType())
                    || Database.isDateTime(field.getType())) {
                tag = "'";
                value = Database.encodeSQL(String.valueOf(value));
            }

            if (field.getName().equals(strPK)) {

                sbPK.append(tag).append(value).append(tag);
                continue;
            }

            // Date Type
            DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
            if (annDataAdapter != null) {
                if (annDataAdapter.fieldType() == FieldType.DATE) {
                    if (value != null && !String.valueOf(value).equals("")) {
                        value = String.valueOf(value);
                    }
                    else {
                        tag = "";
                        value = "null";
                    }
                }
            }

            // 判断是否是数字类型
            // double type
            if (field.getType().getName().equals(double.class.getName())) {
                double tempValue = Double.valueOf(String.valueOf(value));
                if (tempValue == Double.MAX_VALUE) {
                    continue;
                }
            }

            // int type
            if (field.getType().getName().equals(int.class.getName())) {
                int tempValue = Integer.valueOf(String.valueOf(value));
                if (tempValue == Integer.MAX_VALUE) {
                    continue;
                }
            }

            sbFiled.append(field.getName()).append("=");
            sbFiled.append(tag).append(value).append(tag).append(",");

        }

        if (sbFiled.length() > 0) {
            sbFiled.delete(sbFiled.length() - 1, sbFiled.length());
        }

        strSQL = MessageFormat.format(strSQL, Database.getTableName(clazz),
                sbFiled.toString(), sbPK.toString());

        int count = 0;
        // 初始化数据库资源

        Statement stmt = null;
        try {
            // 获得数据库资源
            stmt = conn.createStatement();



            // 打印日志
            LogService.info("MySQL.update(): " + strSQL, MySQLDao.class);

            // 执行更新操作
            count = stmt.executeUpdate(strSQL);
        }
        catch (Exception e) {
            // 打印日志
            MyException.deal(e);
            // 设置失败标识
        }
        finally {
            // 释放数据库资源
            if (stmt != null) {
                stmt.close();
            }
        }

        return count;
    }

    public static <T> int update(T object, Connection conn) throws Exception {
        String strSQL = "update {0} set {1} where {2}";

        Class clazz = object.getClass();

        String strPK = Database.getTablePrimaryKey(clazz);

        if (StringUtils.isEmpty(strPK)) {
            throw new Exception("无法获得更新数据的主键");
        }

        StringBuffer sbFiled = new StringBuffer();
        StringBuffer sbPK = new StringBuffer(strPK).append("=?");
        Object pkValue = null;
        List<KVObject> parameters = new ArrayList<KVObject>();

        int index = 0;

        for(Field field : object.getClass().getDeclaredFields()) {

            KVObject parameter = new KVObject();
            parameter.setIndex(index + 1);

            if (isIgnore(field)) {
                continue;
            }

            String firstLetter = field.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + field.getName().substring(1);

            Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});
            Object value = getMethod.invoke(object, new Object[] {});

            if (value == null) {
                continue;
            }

            // 获得主键的值
            if (field.getName().equals(strPK)) {
                pkValue = value;
                continue;
            }

            // Date Type
            DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
            if (annDataAdapter != null) {
                if (annDataAdapter.fieldType() == FieldType.DATE) {
                    if (value == null || String.valueOf(value).equals("")) {
                        continue;
                    }
                }
            }

            // 判断是否是数字类型
            // double type
            if (field.getType().getName().equals(double.class.getName())) {
                double tempValue = Double.valueOf(String.valueOf(value));
                if (tempValue == Double.MAX_VALUE) {
                    continue;
                }
            }

            // int type
            if (field.getType().getName().equals(int.class.getName())) {
                int tempValue = Integer.valueOf(String.valueOf(value));
                if (tempValue == Integer.MAX_VALUE) {
                    continue;
                }
            }

            sbFiled.append(field.getName()).append("=?,");
            parameter.setValue(value);
            parameters.add(parameter);

            index++;
        }

        if (sbFiled.length() > 0) {
            sbFiled.delete(sbFiled.length() - 1, sbFiled.length());
        }

        // 设置主键的值
        if (pkValue == null) {
            throw new Exception("更新数据失败，无法获得数据主键");
        }
        KVObject pkParameter = new KVObject(index + 1, pkValue);
        parameters.add(pkParameter);

        strSQL = MessageFormat.format(strSQL, Database.getTableName(clazz),
                sbFiled.toString(), sbPK.toString());

        int count = 0;
        // 初始化数据库资源

        PreparedStatement prestmt = null;
        try {
            // 获得数据库资源
            prestmt = conn.prepareStatement(strSQL);

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }

            // 打印日志
            LogService.info("MySQL.update(): " + strSQL, MySQLDao.class);

            // 执行更新操作
            count = prestmt.executeUpdate();
        }
        catch (Exception e) {
            // 打印日志
            MyException.deal(e);
            // 设置失败标识
        }
        finally {
            // 释放数据库资源
            close(prestmt);
        }

        return count;
    }

    /**
     * 为了实现将对象属性设置为null的时候，不改变其在数据库里的原有数值。
     *
     * 即传入的oldObject拥有原有的数值，如果newObject的某个属性被设置成了null，则将oldObject的数值复制到newObject中。
     * 配合MySQLDao.inserOrUpdate使用
     * @param newObject
     * @param oldObject
     * @param <T>
     * @return
     * @throws Exception
     */
    private static <T> T init(T newObject, Object oldObject) throws Exception {

        Class clazz = newObject.getClass();

        for(Field field : newObject.getClass().getDeclaredFields()) {


            if (isIgnore(field)) {
                continue;
            }


            String firstLetter = field.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + field.getName().substring(1);


            Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});


            if (getMethod == null) {
                continue;
            }
//            LogService.debug(getMethodName + " : " + getMethod);
            Object value = getMethod.invoke(newObject, new Object[] {});
            Object oldValue = getMethod.invoke(oldObject, new Object[] {});

            if (value == null && oldValue == null) {
//                String setMethodName = "set" + firstLetter + field
                continue;
            }


            if (value == null && oldValue != null) {
//                String setMethodName = "set" + firstLetter + field
                value = oldValue;
                String setMethodName = "set" + firstLetter + field.getName().substring(1);
                Method setMethod = Utils.getMethod(clazz, setMethodName, new Class[]{field.getType()});
                setMethod.invoke(newObject, new Object[] {value});
            }

            if (!Database.isAvailableValue(value)) {
                throw MyException.UNSUPPORT_EXCEPTION;
            }


            // 是否是系统支持的数据类型
            if (!Database.isSupportType(value.getClass())) {
                throw MyException.UNSUPPORT_EXCEPTION;
            }


            // 日期为空，则不插入
            if (Database.isDateTime(value.getClass())) {
                if (String.valueOf(value).equals("")) {
                    continue;
                }
            }

            if (Database.isInteger(value.getClass())) {
                if (Integer.valueOf(String.valueOf(value)) == Integer.MAX_VALUE) {
                    continue;
                }
            }

            if (Database.isDouble(value.getClass())) {
                if (Double.valueOf(String.valueOf(value)) == Double.MAX_VALUE) {
                    continue;
                }
            }

            if (Database.isFloat(value.getClass())) {
                if (Float.valueOf(String.valueOf(value)) == Float.MAX_VALUE) {
                    continue;
                }
            }

            // Date type
            DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
            if (annDataAdapter != null) {
                if (annDataAdapter.fieldType() == FieldType.DATE) {
                    if (value == null || String.valueOf(value).equals("")) {
                        continue;
                    }
                }
            }
        }

        return newObject;
    }

    public static <T> int insert(T object) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return insert(object, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return 0;
    }

    public static <T> int insert_old(T object , Connection conn) throws Exception {

        Class clazz = object.getClass();

        StringBuffer sbSQL = new StringBuffer();


        sbSQL.append("insert into ").append(Database.getTableName(clazz));
        sbSQL.append(" ({0}) values ({1})");

        StringBuffer sbFiled = new StringBuffer();
        StringBuffer sbValue = new StringBuffer();
        for(Field field : object.getClass().getDeclaredFields()) {

            if (isIgnore(field)) {
                continue;
            }


            String firstLetter = field.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + field.getName().substring(1);

            Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});
            if (getMethod == null) {
                continue;
            }
//            LogService.debug(getMethodName + " : " + getMethod);
            Object value = getMethod.invoke(object, new Object[] {});

            if (value == null) {
                continue;
            }

            if (!Database.isAvailableValue(value)) {
                throw MyException.UNSUPPORT_EXCEPTION;
            }


            String tag = "";

            // 数据库是字符串类型
            if (Database.isStringType(field.getType())
                    || Database.isDateTime(field.getType())) {
                tag = "'";
                value = Database.encodeSQL(String.valueOf(value));
            }



            // 是否是系统支持的数据类型
            if (!Database.isSupportType(value.getClass())) {
                throw MyException.UNSUPPORT_EXCEPTION;
            }


            // 日期为空，则不插入
            if (Database.isDateTime(value.getClass())) {
                if (String.valueOf(value).equals("")) {
                    continue;
                }
                value = Database.encodeSQL(String.valueOf(value));
            }

            if (Database.isInteger(value.getClass())) {
                if (Integer.valueOf(String.valueOf(value)) == Integer.MAX_VALUE) {
                    continue;
                }
            }

            if (Database.isDouble(value.getClass())) {
                if (Double.valueOf(String.valueOf(value)) == Double.MAX_VALUE) {
                    continue;
                }
            }

            if (Database.isFloat(value.getClass())) {
                if (Float.valueOf(String.valueOf(value)) == Float.MAX_VALUE) {
                    continue;
                }
            }

            // Date type
            DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
            if (annDataAdapter != null) {
                if (annDataAdapter.fieldType() == FieldType.DATE) {
                    if (value != null && !String.valueOf(value).equals("")) {
                        value = String.valueOf(value);
                    }
                    else {
                        tag = "";
                        value = "null";
                    }
                }
            }


            sbFiled.append(field.getName()).append(",");
            sbValue.append(tag).append(value).append(tag).append(",");

        }


        // 删除最后一个逗号
        if (object.getClass().getDeclaredFields().length > 0) {
            sbFiled.delete(sbFiled.length() - 1, sbFiled.length());
            sbValue.delete(sbValue.length() - 1, sbValue.length());
        }

        String strSQL = MessageFormat.format(sbSQL.toString(), sbFiled.toString(), sbValue.toString());

        int count = 0;
        Statement stmt = null;
        try {
            // 获得数据库资源
            stmt = conn.createStatement();

            // 打印日志
            LogService.info("MySQLDao.insert(): " + strSQL, MySQLDao.class);

            // 执行增加操作
            count = stmt.executeUpdate(strSQL);
        }
        catch (Exception e) {
            // 打印日志
            MyException.deal(e);
            // 设置失败标识
            count = 0;
        }
        finally {

            // 释放数据库资源
            if (stmt != null) {
                stmt.close();
            }

        }

        return count;

    }

    public static <T> int insertOrUpdate(T object, Connection conn) throws Exception {
        return insertOrUpdate(object, Config.getDefaultOperatorId(), conn);
    }

    public static <T> int insertOrUpdate(T object) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return insertOrUpdate(object, Config.getDefaultOperatorId(), conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    public static <T> int insertOrUpdate(T object, String operatorId, Connection conn) throws Exception {

        if(operatorId == null) {
            operatorId = Config.getDefaultOperatorId();
        }

        Class clazz = object.getClass();


        int count = 0;
        try {

            Method methodSetSid = null;
            Annotation idAnnotationTemp = clazz.getDeclaredField("sid").getAnnotation(Id.class);
            Id idAnnotation = null;
            if (idAnnotationTemp != null) {
                idAnnotation = (Id)idAnnotationTemp;
                if (idAnnotation.type() == IdType.LONG) {
                    methodSetSid = Utils.getMethod(clazz, "setSid", new Class[]{long.class});
                }
                else {
                    methodSetSid = Utils.getMethod(clazz, "setSid", new Class[]{int.class});
                }

            }

            Method methodSetId = Utils.getMethod(clazz, "setId", new Class[]{String.class});
            Method methodSetState = Utils.getMethod(clazz, "setState", new Class[]{int.class});
            Method methodSetOperatorId = Utils.getMethod(clazz, "setOperatorId", new Class[]{String.class});
            Method methodSetOperateTime = Utils.getMethod(clazz, "setOperateTime", new Class[]{String.class});


            Method methodGetSid = Utils.getMethod(clazz, "getSid", new Class[]{});
            Method methodGetId = Utils.getMethod(clazz, "getId", new Class[]{});

            String tableName = Database.getTableName(clazz);

            Object valueOfId = methodGetId.invoke(object, new Object[] {});


            if (valueOfId == null || StringUtils.isEmpty(String.valueOf(valueOfId))) {
                // 新增

                if (idAnnotation != null && idAnnotation.type() == IdType.LONG) {
                    long longSid = IdUtils.newLongId();
                    methodSetSid.invoke(object, longSid);
                }
                else {
                    int intSid = MySQLDao.getMaxSid(tableName, conn);
                    methodSetSid.invoke(object, intSid);
                }

                methodSetId.invoke(object, IdUtils.getUUID32());
                methodSetState.invoke(object, Config.STATE_CURRENT);
                methodSetOperatorId.invoke(object, operatorId);
                methodSetOperateTime.invoke(object, TimeUtils.getNow());
                count = MySQLDao.insert(object, conn);
            }
            else {
                // 更新
                Object temp = clazz.newInstance();
                Object sidValue = methodGetSid.invoke(object, new Object[] {});


                boolean hasIdValue = false;
                Object idValue = methodGetId.invoke(object, new Object[] {});
                String id = idValue == null ? "" : (String) idValue;
                if (!StringUtils.isEmpty(id)) {
                    methodSetId.invoke(temp, id);
                    methodSetState.invoke(temp, Config.STATE_CURRENT);
                    hasIdValue = true;
                }


                /**
                 * 若sid和id同时存在的情况，以id为准
                 */
                if (!hasIdValue) {

                    /**
                     * 设置sid的值
                     */

                    if (idAnnotation != null && idAnnotation.type() == IdType.LONG) {
                        long longSid = sidValue == null ? Long.MAX_VALUE : (Long)sidValue;
                        if (longSid != Long.MAX_VALUE) {
                            methodSetSid.invoke(temp, longSid);
                        }
                    }
                    else {
                        int sid = sidValue == null ? Integer.MAX_VALUE : (Integer)sidValue;
                        if (sid != Integer.MAX_VALUE) {
                            methodSetSid.invoke(temp, sid);
                        }
                    }

                }


                temp = MySQLDao.load(temp,clazz, conn);

                if (temp == null) {
                    throw new Exception("更新数据失败");
                }



                Annotation tableAnnotation = clazz.getAnnotation(Table.class);
                if (tableAnnotation != null) {
                    Table t = (Table)tableAnnotation;
                    String backupTableName = t.backupTableName();

                    if (!StringUtils.isEmpty(backupTableName) && Database.tableExists(backupTableName, conn)) {

                        /**
                         * 将数据保存到备份表里
                         */

                        // 删除实时表数据
                        count = MySQLDao.deletePhysically(temp, conn);
                        if (count == 1) {
                            if (idAnnotation != null && idAnnotation.type() == IdType.LONG) {
                                methodSetSid.invoke(temp, IdUtils.newLongId());
                            }
                            else {
                                methodSetSid.invoke(temp, MySQLDao.getMaxSid(tableName, conn));
                            }
                            count = MySQLDao.insertIntoBackup(temp, conn);
                        }
                    }
                    else {
                        methodSetState.invoke(temp, Config.STATE_UPDATE);
                        count = MySQLDao.update(temp, conn);
                    }
                }

                if (count == 1) {

                    if (idAnnotation != null && idAnnotation.type() == IdType.LONG) {
                        methodSetSid.invoke(object, IdUtils.newLongId());
                    }
                    else {
                        methodSetSid.invoke(object, MySQLDao.getMaxSid(tableName, conn));
                    }
                    methodSetState.invoke(object, Config.STATE_CURRENT);
                    methodSetOperatorId.invoke(object, operatorId);
                    methodSetOperateTime.invoke(object, TimeUtils.getNow());

                    object = MySQLDao.init(object, temp);
                    count = MySQLDao.insert(object, conn);
                }
            }

            if (count != 1) {
                throw new Exception("新增或更新数据失败");
            }

        }
        catch (Exception e) {
            MyException.deal(e);
        }

        return 1;
    }

    public static <T> int insert(T object , Connection conn) throws Exception {
        return insert(object, conn, Database.TABLE_TYPE_RUNTIME);
    }

    public static <T> int insertIntoBackup(T object , Connection conn) throws Exception {
        return insert(object, conn, Database.TABLE_TYPE_BACKUP);
    }

    /**
     *
     * @param object
     * @param conn
     * @param tableType 0: runtime 1: backup
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> int insert(T object , Connection conn, int tableType) throws Exception {

        Class clazz = object.getClass();

        StringBuffer sbSQL = new StringBuffer();

        String tableName = "";

        if (tableType == Database.TABLE_TYPE_RUNTIME) {
            tableName = Database.getTableName(clazz);
        }
        else if (tableType == Database.TABLE_TYPE_BACKUP) {
            tableName = Database.getTableBackupName(clazz);
        }

        sbSQL.append("insert into ").append(tableName);
        sbSQL.append(" ({0}) values ({1})");

        StringBuffer sbFiled = new StringBuffer();
        StringBuffer sbValue = new StringBuffer();
        List<KVObject> parameters = new ArrayList<KVObject>();

        int index = 0;
        for(Field field : object.getClass().getDeclaredFields()) {


            KVObject parameter = new KVObject();
            parameter.setIndex(index + 1);

            if (isIgnore(field)) {
                continue;
            }


            String firstLetter = field.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + field.getName().substring(1);

            Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});
            if (getMethod == null) {
                continue;
            }
//            LogService.debug(getMethodName + " : " + getMethod);
            Object value = getMethod.invoke(object, new Object[] {});

            if (value == null) {
                continue;
            }

            if (!Database.isAvailableValue(value)) {
                throw MyException.UNSUPPORT_EXCEPTION;
            }


            // 是否是系统支持的数据类型
            if (!Database.isSupportType(value.getClass())) {
                throw MyException.UNSUPPORT_EXCEPTION;
            }


            // 日期为空，则不插入
            if (Database.isDateTime(value.getClass())) {
                if (String.valueOf(value).equals("")) {
                    continue;
                }
            }

            if (Database.isInteger(value.getClass())) {
                if (Integer.valueOf(String.valueOf(value)) == Integer.MAX_VALUE) {
                    continue;
                }
            }

            if (Database.isDouble(value.getClass())) {
                if (Double.valueOf(String.valueOf(value)) == Double.MAX_VALUE) {
                    continue;
                }
            }

            if (Database.isFloat(value.getClass())) {
                if (Float.valueOf(String.valueOf(value)) == Float.MAX_VALUE) {
                    continue;
                }
            }

            // Date type
            DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
            if (annDataAdapter != null) {
                if (annDataAdapter.fieldType() == FieldType.DATE) {
                    if (value == null || String.valueOf(value).equals("")) {
                        continue;
                    }
                }
            }


            sbFiled.append(field.getName()).append(",");
            sbValue.append("?,");
            parameter.setValue(value);
            parameters.add(parameter);

            index++;
        }


        // 删除最后一个逗号
        if (object.getClass().getDeclaredFields().length > 0) {
            sbFiled.delete(sbFiled.length() - 1, sbFiled.length());
            sbValue.delete(sbValue.length() - 1, sbValue.length());
        }

        String strSQL = MessageFormat.format(sbSQL.toString(), sbFiled.toString(), sbValue.toString());

        int count = 0;
        PreparedStatement prestmt = null;
        try {
            // 获得数据库资源
            prestmt = conn.prepareStatement(strSQL);

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }

            // 打印日志
            LogService.info("MySQLDao.insert(): " + strSQL, MySQLDao.class);

            // 执行增加操作
            count = prestmt.executeUpdate();
        }
        catch (Exception e) {
            // 打印日志
            MyException.deal(e);
            // 设置失败标识
            count = 0;
        }
        finally {

            close(prestmt);

        }

        return count;

    }

    @Deprecated
    public static Pager query(IJsonable object, int currentPage, int showRowCount,
                               QueryType queryType) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return query(object, currentPage, showRowCount, queryType, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return null;
    }


    public static Pager search(IJsonable object, int currentPage, int showRowCount,
                              QueryType queryType) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return search(object, currentPage, showRowCount, queryType, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return null;
    }


    public static Pager query(IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount,
                              QueryType queryType) throws Exception {
        Connection conn = Config.getConnection();
        try {
            // return query(object,conditions, currentPage, showRowCount, queryType, conn);

            return search(object, conditions, currentPage, showRowCount, queryType, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return null;
    }

    public static Pager search(IJsonable object,List<KVObject> conditions, int currentPage, int showRowCount,
                              QueryType queryType) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return search(object, conditions, currentPage, showRowCount, queryType, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return null;
    }

    public static KVObject buildOrderByCondition(String fieldName, String orderByType) {
        KVObject kvObject = new KVObject();

        if (orderByType == null || orderByType.equals("")) {
            orderByType = Database.ORDERBY_DEFAULT;
        }

        kvObject.setKey(Database.CONDITION_TYPE_ORDERBY);
        kvObject.setValue(fieldName + " " + orderByType);

        return kvObject;
    }

    /**
     * 通过注解，初始化日期类型的按时间段查询方法，并封装到List中，用于后续组织SQL语句的查询部分
     * 例如：
     * 传入request中包含有user_Birthday_Start和user_Birthday_End两个参数，传入对象为User
     * 其中user是json的前缀，此处用“_”连接，防止struts抛ognl.NoSuchPropertyException异常
     * 方法将构造出两个KVObject，保存键-值
     * KVObject1： 键：【Birthday】值：【>='1991-03-01'】
     * KVObject2： 键：【Birthday】值：【<='1991-03-10'】
     * @param request
     * @param clazz
     * @return
     * @throws Exception
     * 修改人：张舜清
     * 修改时间：6/11/15
     *
     */
    public static List<KVObject> getQueryDatetimeParameters(HttpServletRequest request, Class clazz, List<KVObject> params) throws Exception {
        if (params == null) {
            params = new ArrayList<KVObject>();
        }

        Table tableAnnotation = (Table)clazz.getAnnotation(Table.class);
        String jsonPrefix = tableAnnotation.jsonPrefix();
        if (!jsonPrefix.equals("")) {
            jsonPrefix = jsonPrefix +"_";
        }
        for (Field field : clazz.getDeclaredFields()) {

            DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
            // name = tableAnnotation.name();
            if (annDataAdapter != null && annDataAdapter.fieldType() == FieldType.DATE) {
                String keyStart = jsonPrefix + field.getName() + "_Start";
                String valueStart = "";
                /*
                判断是否是移动端传过来的数据，因移动端只能传JSON过来，因此移动端传过来的数据需要
                重新判断处理。
                 */
                if(request.getParameter("req") == null){
//                    System.out.println("1");
                    valueStart = request.getParameter(keyStart);
                }else{
//                    System.out.println("2");
                    String reqStr = request.getParameter("req");
                    JSONObject reqParams = JSONObject.fromObject(reqStr);
                    if(reqParams.has(valueStart)){
                        valueStart = reqParams.getString(keyStart);
                    }
                    System.out.println("VALUEStart = "+valueStart);
                }

                if (valueStart != null && !valueStart.equals("")) {
                    params.add(new KVObject(field.getName(), ">='"+valueStart+"'"));
                }

                String keyEnd = jsonPrefix + field.getName() + "_End";
                String valueEnd = request.getParameter(keyEnd);
                if (valueEnd != null && !valueEnd.equals("")) {
                    params.add(new KVObject(field.getName(), "<='"+valueEnd+"'"));
                }
            }
        }

        return params;
    }

    public static List<KVObject> getQueryDatetimeParameters(HttpServletRequest request, Class clazz) throws Exception {
        return getQueryDatetimeParameters(request, clazz, null);
    }

    public static List<KVObject> getQueryNumberParameters(HttpServletRequest request, Class clazz, List<KVObject> params) throws Exception {

        if (params == null) {
            params = new ArrayList<KVObject>();
        }

        Table tableAnnotation = (Table)clazz.getAnnotation(Table.class);
        String jsonPrefix = tableAnnotation.jsonPrefix();
        if (!jsonPrefix.equals("")) {
            jsonPrefix = jsonPrefix +"_";
        }
        for (Field field : clazz.getDeclaredFields()) {

            if (Database.isNumberType(field.getType())) {
                String keyStart = jsonPrefix + field.getName() + "_Start";
                String valueStart = request.getParameter(keyStart);
                if (valueStart != null && !valueStart.equals("")) {
                    params.add(new KVObject(field.getName(), ">="+valueStart+""));
                }

                String keyEnd = jsonPrefix + field.getName() + "_End";
                String valueEnd = request.getParameter(keyEnd);
                if (valueEnd != null && !valueEnd.equals("")) {
                    params.add(new KVObject(field.getName(), "<="+valueEnd+""));
                }
            }
        }

        return params;
    }

    @Deprecated
    public static Pager query(IJsonable object, int currentPage, int showRowCount,
                              QueryType queryType, Connection conn) throws Exception {
        return query(object, null, currentPage, showRowCount,queryType, conn);
    }


    public static Pager search(IJsonable object, int currentPage, int showRowCount,
                              QueryType queryType, Connection conn) throws Exception {
        return search(object, null, currentPage, showRowCount, queryType, conn);
    }

    public static Pager query(String sql, Class clazz, int currentPage, int showRowCount) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return query(sql, clazz, currentPage, showRowCount, conn);
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
        }
        finally {
            // 释放数据库资源
            Database.close(conn);
        }
        return null;
    }

    public static Pager query(String sql, Class clazz, int currentPage, int showRowCount, Connection conn) throws Exception {
        Pager pager = new Pager();

        // 检查输入Vo参数合法性
//        if (object == null) {
//            // 返回空数值
//            return pager;
//        }
//
//        Class clazz = object.getClass();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            StringBuffer sbSQL = new StringBuffer(sql);
            sbSQL.insert(0, "select SQL_CALC_FOUND_ROWS * from (");
            sbSQL.append(") _t limit "+((currentPage-1)*showRowCount)+", "+showRowCount+";");

            LogService.info("MySQLDao.query(): " + sbSQL.toString(), MySQLDao.class);
            rs = stmt.executeQuery(sbSQL.toString());
            while(rs.next()) {
                IJsonable temp = (IJsonable)Database.getInstanceFromResultSet(rs, clazz);
                pager.getData().add(temp);
            }

            // 查询总行数
            rs = stmt.executeQuery("SELECT FOUND_ROWS() total");
            if (rs.next()) {
                int total = rs.getInt("total");
                pager.setTotal(total);
            }
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            // 释放数据库资源
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return pager;
    }
    /**
     * 分页查询，用于构建JEasyUI的Datagrid
     * @param object
     * @param currentPage
     * @param showRowCount
     * @param queryType
     * @param conn
     * @return
     * @throws Exception
     */
    @Deprecated
    public static Pager query(IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount,
                              QueryType queryType, Connection conn) throws Exception {

        // 保存查询结果
        Pager pager = new Pager();

        // 检查输入Vo参数合法性
        if (object == null) {
            // 返回空数值
            return pager;
        }

        Class clazz = object.getClass();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            StringBuffer sbSQL = new StringBuffer();
            String tableName = Database.getTableName(clazz);
            // 构建查询字段
            StringBuffer sbSelectFields = new StringBuffer();


            // 组织查询条件
            StringBuffer sbWhereSQL = new StringBuffer();
            for(Field field : clazz.getDeclaredFields()) {
                if (!Database.isSimpleType(field.getType())) {
                    continue;
                }

                String fieldName = field.getName();
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + field.getName().substring(1);

                // 构造需要查询的字段，Select与From之间的部分


                DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
                if (annDataAdapter != null) {
                    // 判断是否是日期时间类型
                    if (annDataAdapter.fieldType() == FieldType.DATE) {
                        // 获得日期格式化
                        if (!annDataAdapter.fieldFormat().equals("")) {
                            sbSelectFields.append("date_format("+fieldName+","+annDataAdapter.fieldFormat()+") as "+fieldName+" ");
                            sbSelectFields.append(", ");
                        }
                        else {
                            sbSelectFields.append("date_format("+fieldName+","+Database.DATEFORMAT_YYYYMMDDHH24MISS+") as "+fieldName+" ");
                            sbSelectFields.append(", ");
                        }
                    }
                }
                // 其他类型不做处理
                else {
                    sbSelectFields.append(fieldName).append(", ");
                }

                Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});
                Object value = getMethod.invoke(object, new Object[] {});

                if (value == null) {
                    continue;
                }

                if (!Database.isAvailableValue(value)) {
                    throw MyException.UNSUPPORT_EXCEPTION;
                }


                String queryString = Database.buildQueryString(field, value, queryType);
                sbWhereSQL.append(queryString);


            }

            // 处理查询字段语句
            if (sbSelectFields.length() > 0) {
                sbSelectFields.delete(sbSelectFields.length() - 2, sbSelectFields.length());
            }

            sbSQL.append("SELECT " + sbSelectFields.toString());
            sbSQL.append(" FROM " + tableName);

            // 处理排序语句
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key != Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbWhereSQL.append(key).append(value).append(" AND ");
                }
            }

            // 删除Where语句最后一个“ AND ”
            if (sbWhereSQL.length() > 0) {
                sbWhereSQL.delete(sbWhereSQL.length() - 5, sbWhereSQL.length());
                sbSQL.append(" WHERE ");
                sbSQL.append(sbWhereSQL.toString());
            }

            // 创建Order by
            StringBuilder sbOrderBySQL = new StringBuilder();
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key == Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbOrderBySQL.append(value).append(", ");
                }
            }

            // 删除Order by 语句最后的逗号", "
            if (sbOrderBySQL.length() > 0) {
                sbOrderBySQL.delete(sbOrderBySQL.length() - 2, sbOrderBySQL.length());
                sbSQL.append(" ORDER BY ");
                sbSQL.append(sbOrderBySQL.toString());
            }

            sbSQL.insert(0, "select SQL_CALC_FOUND_ROWS * from (");
            sbSQL.append(") _t limit "+((currentPage-1)*showRowCount)+", "+showRowCount+";");

            LogService.info("MySQLDao.query(): " + sbSQL.toString(), MySQLDao.class);

            rs = stmt.executeQuery(sbSQL.toString());
            while(rs.next()) {
                IJsonable temp = (IJsonable)Database.getInstanceFromResultSet(rs, clazz);
                pager.getData().add(temp);
            }

            // 查询总行数
            rs = stmt.executeQuery("SELECT FOUND_ROWS() total");
            if (rs.next()) {
                int total = rs.getInt("total");
                pager.setTotal(total);
            }
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            // 释放数据库资源
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return pager;
    }

    public static Pager search(IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount,
                              QueryType queryType, Connection conn) throws Exception {

        // 保存查询结果
        Pager pager = new Pager();

        // 检查输入Vo参数合法性
        if (object == null) {
            // 返回空数值
            return pager;
        }

        List<KVObject> parameters = new ArrayList<KVObject>();

        Class clazz = object.getClass();

        PreparedStatement prestmt = null;
        ResultSet rs = null;

        try {
            StringBuffer sbSQL = new StringBuffer();
            String tableName = Database.getTableName(clazz);
            // 构建查询字段
            StringBuffer sbSelectFields = new StringBuffer();


            // 组织查询条件
            StringBuffer sbWhereSQL = new StringBuffer();
            for(Field field : clazz.getDeclaredFields()) {

                IgnoreDB ignoreDB = field.getAnnotation(IgnoreDB.class);
                if (ignoreDB != null) {
                    continue;
                }

                if (!Database.isSimpleType(field.getType())) {
                    continue;
                }

                String fieldName = field.getName();
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + field.getName().substring(1);

                // 构造需要查询的字段，Select与From之间的部分


                DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
                if (annDataAdapter != null) {
                    // 判断是否是日期时间类型
                    if (annDataAdapter.fieldType() == FieldType.DATE) {
                        // 获得日期格式化
                        if (!annDataAdapter.fieldFormat().equals("")) {
                            sbSelectFields.append("date_format("+fieldName+","+annDataAdapter.fieldFormat()+") as "+fieldName+" ");
                            sbSelectFields.append(", ");
                        }
                        else {
                            sbSelectFields.append("date_format("+fieldName+","+Database.DATEFORMAT_YYYYMMDDHH24MISS+") as "+fieldName+" ");
                            sbSelectFields.append(", ");
                        }
                    }
                }
                // 其他类型不做处理
                else {
                    sbSelectFields.append(fieldName).append(", ");
                }

                Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});

                if (getMethod == null) {
                    MyException.newInstance("无法获得getMethod", "methodName=" + getMethodName).throwException();
                }

                Object value = getMethod.invoke(object, new Object[] {});

                if (value == null) {
                    continue;
                }

                if (!Database.isAvailableValue(value)) {
                    throw MyException.UNSUPPORT_EXCEPTION;
                }


                KVObject parameter = Database.buildQueryKVObject(field, value, queryType);
                if (parameter != null) {
                    parameters.add(parameter);
                }
            }

            // 处理查询字段语句
            sbSelectFields = StringUtils.removeLastLetters(sbSelectFields, ", ");

            sbSQL.append("SELECT " + sbSelectFields.toString());
            sbSQL.append(" FROM " + tableName);


            for (int i = 0; i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);

                sbWhereSQL.append(parameter.getKey().toString()).append(" AND ");
                parameter.setIndex(i + 1);
            }

            // 处理非排序语句
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key != Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbWhereSQL.append(key).append(value).append(" AND ");
                }
            }

            // 删除Where语句最后一个“ AND ”
            if (sbWhereSQL.length() > 0) {
                sbWhereSQL.delete(sbWhereSQL.length() - 5, sbWhereSQL.length());
                sbSQL.append(" WHERE ");
                sbSQL.append(sbWhereSQL.toString());
            }

            // 创建Order by
            StringBuilder sbOrderBySQL = new StringBuilder();
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key == Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbOrderBySQL.append(value).append(", ");
                }
            }

            // 删除Order by 语句最后的逗号", "
            if (sbOrderBySQL.length() > 0) {
                sbOrderBySQL.delete(sbOrderBySQL.length() - 2, sbOrderBySQL.length());
                sbSQL.append(" ORDER BY ");
                sbSQL.append(sbOrderBySQL.toString());
            }

            sbSQL.insert(0, "select SQL_CALC_FOUND_ROWS * from (");
            sbSQL.append(") _t limit "+((currentPage-1)*showRowCount)+", "+showRowCount+";");

            LogService.info("MySQLDao.query(): " + sbSQL.toString(), MySQLDao.class);
            prestmt = conn.prepareStatement(sbSQL.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }

            rs = prestmt.executeQuery();

            while(rs.next()) {
                IJsonable temp = (IJsonable)Database.getInstanceFromResultSet(rs, clazz);
                pager.getData().add(temp);
            }

            // 查询总行数
            rs = prestmt.executeQuery("SELECT FOUND_ROWS() total");
            if (rs.next()) {
                int total = rs.getInt("total");
                pager.setTotal(total);
            }
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            // 释放数据库资源
            close(rs, prestmt);
        }

        return pager;
    }

    @Deprecated
    public static Pager query(String sql, IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount,
                              QueryType queryType) throws Exception {
        Connection conn = Config.getConnection();

        try {
            return query(sql, object, conditions, currentPage, showRowCount, queryType, conn);
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
        }
        finally {
            // 释放数据库资源
            Database.close(conn);
        }
        return null;
    }

    public static Pager search(String sql, List<KVObject> parameters, IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount, QueryType queryType) throws Exception {
        Connection conn = Config.getConnection();

        try {
            return search(sql, parameters, object, conditions, currentPage, showRowCount, queryType, conn);
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
        }
        finally {
            // 释放数据库资源
            Database.close(conn);
        }
        return null;
    }

    public static List<Map<String, Object>> query(String sql) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return query(sql, conn);
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
        }
        finally {
            // 释放数据库资源
            Database.close(conn);
        }
        return null;

    }

    public static long query4LongValue(String sql, String columnName, Connection conn) throws Exception {
        List<Map<String, Object>> list = MySQLDao.query(sql, conn);
        return (Long) list.get(0).get(columnName);
    }


    public static double query4DoubleValue(String sql, String columnName, Connection conn) throws Exception {
        List<Map<String, Object>> list = MySQLDao.query(sql, conn);
        return Double.parseDouble(String.valueOf(list.get(0).get(columnName)));
    }


    public static List<Map<String, Object>> query(String sql, Connection conn) throws Exception {

        Statement stmt = null;
        ResultSet rs = null;

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            stmt = conn.createStatement();
            LogService.info("MySQLDao.query(): " + sql, MySQLDao.class);
            rs = stmt.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for(int i = 1; i <= count; i++){
//                    String columnName = rsmd.getColumnName(i);
//                    Object obj = rs.getObject(i);
//                    map.put(columnName,obj);
                    String columnLabel = rsmd.getColumnLabel(i);
                    Object obj = rs.getObject(i);
                    map.put(columnLabel,obj);
                }
                list.add(map);
            }

            return list;
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return null;
    }


    public static <T> List<T> search(DatabaseSQL dbSQL, Class<T> clazz, Connection conn) throws Exception {
        return search(dbSQL.getSQL(), dbSQL.getParameters(), clazz, null, conn);
    }


    public static List<KVObjects> search(DatabaseSQL dbSQL) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return search(dbSQL, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    public static List<KVObjects> search(DatabaseSQL dbSQL, Connection conn) throws Exception {


        PreparedStatement prestmt = null;
        ResultSet rs = null;

        List<KVObjects> list = new ArrayList<KVObjects>();
        try {
            prestmt = conn.prepareStatement(dbSQL.getSQL(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; dbSQL.getParameters() != null && i < dbSQL.getParameters().size(); i++) {
                KVObject parameter = dbSQL.getParameters().get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }

            LogService.info("MySQLDao.query(): " + dbSQL.getSQL(), MySQLDao.class);
            rs = prestmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            while (rs.next()) {
                KVObjects objets = new KVObjects();
                for(int i = 1; i <= count; i++){
//                    String columnName = rsmd.getColumnName(i);
//                    Object obj = rs.getObject(i);
//                    map.put(columnName,obj);
                    String columnLabel = rsmd.getColumnLabel(i);
                    Object obj = rs.getObject(i);

                    KVObject kvObject = new KVObject(i-1, columnLabel, obj);
                    objets.add(kvObject);
                }
                list.add(objets);
            }

            return list;
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            close(rs, prestmt);
        }

        return null;
    }


    /**
     * 从SQL中构造视图
     * @param sql
     * @param object
     * @param conditions
     * @param currentPage
     * @param showRowCount
     * @param queryType
     * @param conn
     * @return
     * @throws Exception
     */
    @Deprecated
    public static Pager query(String sql, IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount,
                              QueryType queryType, Connection conn) throws Exception {

        // 保存查询结果
        Pager pager = new Pager();

        // 检查输入Vo参数合法性
        if (object == null) {
            // 返回空数值
            return pager;
        }

        Class clazz = object.getClass();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            StringBuffer sbSQL = new StringBuffer();

            // 组织查询条件
            StringBuffer sbWhereSQL = new StringBuffer();
            for(Field field : clazz.getDeclaredFields()) {
                //System.out.println("clazz="+field);
                if (!Database.isSimpleType(field.getType())) {
                    continue;
                }

                if (field.getAnnotation(IgnoreDB.class) != null) {
                    continue;
                }

                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + field.getName().substring(1);

                //通过反射的方式运行VO类的get方法，若传来VO中的成员有值，可根据值生成查询语句
                Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});

                LogService.debug("MySQLDao.query(): 获取getMethod方法名["+getMethodName+"]，方法对象：["+getMethod+"]，对象：["+object+"]", MySQLDao.class);

                Object value = getMethod.invoke(object, new Object[] {});
                // System.out.println("ReflectValue="+value);

                if (value == null) {
                    continue;
                }

                String queryString = Database.buildQueryString(field, value, queryType);

                sbWhereSQL.append(queryString);

            }

            // 创建Order by
            StringBuilder sbOrderBySQL = new StringBuilder();
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key == Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();

                    sbOrderBySQL.append(value).append(", ");
                }
            }

            // 删除Order by 语句最后的逗号", "
            if (sbOrderBySQL.length() > 0) {
                sbOrderBySQL.delete(sbOrderBySQL.length() - 2, sbOrderBySQL.length());
                sbOrderBySQL.insert(0, " ORDER BY ");
            }

            sbSQL.append("SELECT * FROM (" + sql + " " + sbOrderBySQL +") t ");

            // 处理排序语句
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();


                if (key != Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbWhereSQL.append(key).append(value).append(" AND ");

                }
            }

            // 删除Where语句最后一个“ AND ”
            if (sbWhereSQL.length() > 0) {

                sbWhereSQL.delete(sbWhereSQL.length() - 5, sbWhereSQL.length());
                sbSQL.append(" WHERE ");
                sbSQL.append(sbWhereSQL.toString());
            }

            sbSQL.insert(0, "select SQL_CALC_FOUND_ROWS * from (");
            sbSQL.append(") _t limit "+((currentPage-1)*showRowCount)+", "+showRowCount+";");

            LogService.info("MySQLDao.query(): " + sbSQL.toString(), MySQLDao.class);
            rs = stmt.executeQuery(sbSQL.toString());

            while(rs.next()) {
                IJsonable temp = (IJsonable)Database.getInstanceFromResultSet(rs, clazz);
                pager.getData().add(temp);
            }

            // 查询总行数
            rs = stmt.executeQuery("SELECT FOUND_ROWS() total");
            if (rs.next()) {
                int total = rs.getInt("total");
                pager.setTotal(total);
            }
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            // 释放数据库资源
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return pager;
    }

    public static Pager search(DatabaseSQL dbSQL, IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount, QueryType queryType, Connection conn) throws Exception {
        return search(dbSQL.getSQL(), dbSQL.getParameters(), object, conditions, currentPage, showRowCount, queryType, conn);
    }


    /**
     *
     * 如果currentPage或showRowCount任意一项等于0，则不分页，显示全部内容
     * @param sql
     * @param parameters
     * @param object
     * @param conditions
     * @param currentPage
     * @param showRowCount
     * @param queryType
     * @param conn
     * @return
     * @throws Exception
     */
    public static Pager search(String sql, List<KVObject> parameters, IJsonable object, List<KVObject> conditions, int currentPage, int showRowCount, QueryType queryType, Connection conn) throws Exception {

        // 保存查询结果
        Pager pager = new Pager();

        // 检查输入Vo参数合法性
        if (object == null) {
            // 返回空数值
            return pager;
        }

        if (parameters == null) {
            parameters = new ArrayList<KVObject>();
        }

        Class clazz = object.getClass();

        PreparedStatement prestmt = null;
        ResultSet rs = null;

        try {

            StringBuffer sbSQL = new StringBuffer();

            // 组织查询条件
            for(Field field : clazz.getDeclaredFields()) {
                //System.out.println("clazz="+field);
                if (!Database.isSimpleType(field.getType())) {
                    continue;
                }

                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + field.getName().substring(1);

                //通过反射的方式运行VO类的get方法，若传来VO中的成员有值，可根据值生成查询语句
                Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});

                if (getMethod != null) {
                    LogService.debug("MySQLDao.search(): 获取getMethod方法名["+getMethodName+"]，方法对象：["+getMethod+"]，对象：["+object+"]", MySQLDao.class);

                    Object value = getMethod.invoke(object, new Object[] {});
                    // System.out.println("ReflectValue="+value);

                    if (value == null) {
                        continue;
                    }


                    String queryString = Database.buildQueryString(field, value, queryType);
                    KVObject parameter = Database.buildQueryKVObject(field, value, queryType);

                    if (parameter != null) {
                        parameters.add(parameter);
                    }
                }

            }

            StringBuffer sbWhere = new StringBuffer();

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                if (parameter.getKey() != null) {
                    sbWhere.append(parameter.getKey().toString()).append(" AND ");
                    parameter.setIndex(i + 1);
                }
            }

            // 创建Order by
            StringBuilder sbOrderBySQL = new StringBuilder();
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key == Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();

                    sbOrderBySQL.append(value).append(", ");
                }
            }

            // 删除Order by 语句最后的逗号", "
            if (sbOrderBySQL.length() > 0) {
                sbOrderBySQL.delete(sbOrderBySQL.length() - 2, sbOrderBySQL.length());
                sbOrderBySQL.insert(0, " ORDER BY ");
            }

            sbSQL.append("SELECT * FROM (" + sql + " " + sbOrderBySQL +") t ");

            // 处理排序语句
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();


                if (key != Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbWhere.append(key).append(value).append(" AND ");

                }
            }

            // 删除Where语句最后一个“ AND ”
            if (sbWhere.length() > 0) {

                sbWhere.delete(sbWhere.length() - 5, sbWhere.length());
                sbSQL.append(" WHERE ");
                sbSQL.append(sbWhere.toString());
            }

            String limit = "";
            if (currentPage > 0 && showRowCount > 0) {
                limit = " limit " +((currentPage-1)*showRowCount)+", "+showRowCount+";";
            }

            sbSQL.insert(0, "select SQL_CALC_FOUND_ROWS * from (");
            sbSQL.append(") _t ").append(limit);

            LogService.info("MySQLDao.query(): " + sbSQL.toString(), MySQLDao.class);
            printParameters(parameters);
            prestmt = conn.prepareStatement(sbSQL.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }

            rs = prestmt.executeQuery();

            while(rs.next()) {
                IJsonable temp = (IJsonable)Database.getInstanceFromResultSet(rs, clazz);
                pager.getData().add(temp);
            }

            // 查询总行数
            rs = prestmt.executeQuery("SELECT FOUND_ROWS() total");
            if (rs.next()) {
                int total = rs.getInt("total");
                pager.setTotal(total);
            }
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            // 释放数据库资源
            close(rs, prestmt);
        }

        return pager;
    }


    public static <T> Pager search(String sql, List<KVObject> parameters, Class<T> clazz, int currentPage, int showRowCount, Connection conn) throws Exception {

        // 保存查询结果
        Pager pager = new Pager();


        if (parameters == null) {
            parameters = new ArrayList<KVObject>();
        }


        PreparedStatement prestmt = null;
        ResultSet rs = null;

        try {

            StringBuffer sbSQL = new StringBuffer(sql);


            sbSQL.insert(0, "select SQL_CALC_FOUND_ROWS * from (");
            sbSQL.append(") _t limit "+((currentPage-1)*showRowCount)+", "+showRowCount+";");


            LogService.info("MySQLDao.query(): " + sbSQL.toString(), MySQLDao.class);
            printParameters(parameters);
            prestmt = conn.prepareStatement(sbSQL.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }

            rs = prestmt.executeQuery();

            while(rs.next()) {
                IJsonable temp = (IJsonable)Database.getInstanceFromResultSet(rs, clazz);
                pager.getData().add(temp);
            }

            // 查询总行数
            rs = prestmt.executeQuery("SELECT FOUND_ROWS() total");
            if (rs.next()) {
                int total = rs.getInt("total");
                pager.setTotal(total);
            }
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            // 释放数据库资源
            close(rs, prestmt);
        }

        return pager;
    }

    @Deprecated
    public static <T> List<T> query(T object, Class<T> clazz, Connection conn) throws Exception {

        return query(object, clazz, null, null, conn);
    }

    @Deprecated
    public static <T> List<T> query(T object, Class<T> clazz) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return query(object, clazz, null, null, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return null;
    }

    @Deprecated
    public static <T> List<T> query(T object, Class<T> clazz,List<KVObject> conditions, QueryType queryType) throws Exception {
        Connection conn = Config.getConnection();
        try {
            // query(object, clazz, conditions, queryType, conn);

            return search(object, clazz, conditions, queryType, conn);
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
        }
        finally {
            // 释放数据库资源
            Database.close(conn);
        }
        return null;
    }

    /**
     * 判断一个列是否被忽略注解标记
     * @param field
     * @return
     */
    public static boolean isIgnore(Field field) {

        if (field == null) {
            return true;
        }

        IgnoreDB ignoreDB = field.getAnnotation(IgnoreDB.class);

        if (ignoreDB != null) {
            return true;
        }
        return false;
    }

    public static <T> List<T> query(String sql,Class<T> clazz,List<KVObject> conditions) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return query(sql, clazz,conditions, conn);
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
        }
        finally {
            // 释放数据库资源
            Database.close(conn);
        }
        return null;
    }



    public static <T> List<T> query(String sql, Class<T> clazz, List<KVObject> conditions, Connection conn) throws Exception {
        List<T> list = new ArrayList<T>();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("select * from (");
            sbSQL.append(sql);
            sbSQL.append(") _t");

            StringBuffer sbWhereSQL = new StringBuffer();
            // 处理排序语句
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = "_t." + kv.getKey();


                if (key != Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbWhereSQL.append(key).append(value).append(" AND ");
                }
            }

            // 删除Order by 语句最后的逗号", "
            if (sbWhereSQL.length() > 0) {
                sbWhereSQL.delete(sbWhereSQL.length() - 5, sbWhereSQL.length());
                sbSQL.append(" where ");
                sbSQL.append(sbWhereSQL.toString());
            }

            // 创建Order by
            StringBuilder sbOrderBySQL = new StringBuilder();
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key == Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbOrderBySQL.append(value).append(", ");
                }
            }

            // 删除Order by 语句最后的逗号", "
            if (sbOrderBySQL.length() > 0) {
                sbOrderBySQL.delete(sbOrderBySQL.length() - 2, sbOrderBySQL.length());
                sbSQL.append(" ORDER BY ");
                sbSQL.append(sbOrderBySQL.toString());
            }


            LogService.info("MySQLDao.query(): " + sql, MySQLDao.class);
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                T temp = Database.getInstanceFromResultSet(rs, clazz);
                list.add(temp);
            }
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            // 释放数据库资源
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return list;
    }

    public static <T> List<T> search(String sql, List<KVObject> parameters, Class<T> clazz, List<KVObject> conditions) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return search(sql, parameters, clazz, conditions, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    public static <T> List<T> search(String sql, List<KVObject> parameters, Class<T> clazz, List<KVObject> conditions, Connection conn) throws Exception {
        List<T> list = new ArrayList<T>();

        PreparedStatement prestmt = null;
        ResultSet rs = null;

        try {
            prestmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,  ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }


            LogService.info("MySQLDao.search(): " + sql, MySQLDao.class);
            printParameters(parameters);
            rs = prestmt.executeQuery();
            while(rs.next()) {
                T temp = Database.getInstanceFromResultSet(rs, clazz);
                list.add(temp);
            }


        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            close(rs, prestmt);
        }

        return list;
    }

    public static void printParameters(List<KVObject> parameters) {
        for (int i = 0; parameters != null && i < parameters.size(); i++) {
            KVObject parameter = parameters.get(i);
            LogService.info("Parameter: Key:["+parameter.getKey()+"] Value:["+parameter.getValue()+"]", MySQLDao.class);
        }
    }

    @Deprecated
    public static <T> List<T> query(T object, Class<T> clazz, List<KVObject> conditions, QueryType queryType, Connection conn) throws Exception {
        List<T> list = new ArrayList<T>();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,  ResultSet.CONCUR_READ_ONLY);
            StringBuffer sbSQL = new StringBuffer();
            String tableName = Database.getTableName(clazz);
            // 构建查询字段
            StringBuffer sbSelectFields = new StringBuffer();

            // 组织查询条件
            StringBuffer sbWhereSQL = new StringBuffer();
            for(Field field : clazz.getDeclaredFields()) {

                IgnoreDB ignoreDB = field.getAnnotation(IgnoreDB.class);
                if (ignoreDB != null) {
                    continue;
                }

                String fieldName = field.getName();
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + field.getName().substring(1);

                // 构造需要查询的字段，Select与From之间的部分


                DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
                if (annDataAdapter != null) {
                    // 判断是否是日期时间类型
                    if (annDataAdapter.fieldType() == FieldType.DATE) {
                        // 获得日期格式化
                        if (!annDataAdapter.fieldFormat().equals("")) {
                            sbSelectFields.append("date_format("+fieldName+","+annDataAdapter.fieldFormat()+") as "+fieldName+" ");
                            sbSelectFields.append(", ");
                        }
                        else {
                            sbSelectFields.append("date_format("+fieldName+","+Database.DATEFORMAT_YYYYMMDDHH24MISS+") as "+fieldName+" ");
                            sbSelectFields.append(", ");
                        }
                    }
                }
                // 其他类型不做处理
                else {
                    sbSelectFields.append(fieldName).append(", ");
                }


                Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});
                Object value = null;
                if (getMethod != null) {
                    value = getMethod.invoke(object, new Object[] {});
                }

                if (value == null) {
                    continue;
                }

                String queryString = Database.buildQueryString(field, value, queryType);
                sbWhereSQL.append(queryString);

            }

            // 处理查询字段语句
            if (sbSelectFields.length() > 0) {
                sbSelectFields.delete(sbSelectFields.length() - 2, sbSelectFields.length());
            }

            sbSQL.append("SELECT " + sbSelectFields.toString());
            sbSQL.append(" FROM " + tableName);

            // 处理排序语句
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key != Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbWhereSQL.append(key).append(value).append(" AND ");
                }
            }

            // 删除Where语句最后一个“ AND ”
            if (sbWhereSQL.length() > 0) {
                sbWhereSQL.delete(sbWhereSQL.length() - 5, sbWhereSQL.length());
                sbSQL.append(" WHERE ");
                sbSQL.append(sbWhereSQL.toString());
            }

            // 创建Order by
            StringBuilder sbOrderBySQL = new StringBuilder();
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key == Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbOrderBySQL.append(value).append(", ");
                }
            }

            // 删除Order by 语句最后的逗号", "
            if (sbOrderBySQL.length() > 0) {
                sbOrderBySQL.delete(sbOrderBySQL.length() - 2, sbOrderBySQL.length());
                sbSQL.append(" ORDER BY ");
                sbSQL.append(sbOrderBySQL.toString());
            }


            LogService.info("MySQLDao.query(): " + sbSQL.toString(), MySQLDao.class);

            rs = stmt.executeQuery(sbSQL.toString());
            while(rs.next()) {
                T temp = Database.getInstanceFromResultSet(rs, clazz);
                list.add(temp);
            }
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            // 释放数据库资源
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return list;
    }

    public static <T> List<T> search(T object, Class<T> clazz, List<KVObject> conditions, QueryType queryType) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return search(object, clazz, conditions, queryType, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    public static <T> List<T> search(T object, Class<T> clazz, Connection conn) throws Exception {
        return search(object, clazz, null, null, conn);
    }

    /**
     * 用 PreparedStatement改写
     * @author leevits
     * @param object
     * @param clazz
     * @param conditions
     * @param queryType
     * @param conn
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> search(T object, Class<T> clazz, List<KVObject> conditions, QueryType queryType, Connection conn) throws Exception {

        List<T> list = new ArrayList<T>();

        PreparedStatement prestmt = null;
        ResultSet rs = null;


        List<KVObject> parameters = new ArrayList<KVObject>();

        try {

            StringBuffer sbSQL = new StringBuffer();
            String tableName = Database.getTableName(clazz);
            // 构建查询字段
            StringBuffer sbSelectFields = new StringBuffer();


            // 处理查询字段语句
            if (sbSelectFields.length() > 0) {
                sbSelectFields.delete(sbSelectFields.length() - 2, sbSelectFields.length());
            }

            // 组织查询条件
            for(Field field : clazz.getDeclaredFields()) {

                IgnoreDB ignoreDB = field.getAnnotation(IgnoreDB.class);
                if (ignoreDB != null) {
                    continue;
                }

                String fieldName = field.getName();
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + field.getName().substring(1);

                // 构造需要查询的字段，Select与From之间的部分


                DataAdapter annDataAdapter = field.getAnnotation(DataAdapter.class);
                if (annDataAdapter != null) {
                    // 判断是否是日期时间类型
                    if (annDataAdapter.fieldType() == FieldType.DATE) {
                        // 获得日期格式化
                        if (!annDataAdapter.fieldFormat().equals("")) {
                            sbSelectFields.append("date_format("+fieldName+","+annDataAdapter.fieldFormat()+") as "+fieldName+" ");
                            sbSelectFields.append(", ");
                        }
                        else {
                            sbSelectFields.append("date_format("+fieldName+","+Database.DATEFORMAT_YYYYMMDDHH24MISS+") as "+fieldName+" ");
                            sbSelectFields.append(", ");
                        }
                    }
                }
                // 其他类型不做处理
                else {
                    sbSelectFields.append(fieldName).append(", ");
                }


                Method getMethod = Utils.getMethod(clazz, getMethodName, new Class[]{});
                Object value = null;
                if (getMethod != null) {
                    value = getMethod.invoke(object, new Object[] {});
                }

                if (value == null) {
                    continue;
                }

                if (!Database.isAvailableValue(value)) {
                    throw MyException.UNSUPPORT_EXCEPTION;
                }


                KVObject parameter = Database.buildQueryKVObject(field, value, queryType);
                if (parameter != null) {
                    parameters.add(parameter);
                }
            }

            sbSelectFields = StringUtils.removeLastLetters(sbSelectFields, ", ");
            sbSQL.append("SELECT " + sbSelectFields.toString());
            sbSQL.append(" FROM " + tableName);

            StringBuffer sbWhere = new StringBuffer();
            for (int i = 0; i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);

                sbWhere.append(parameter.getKey().toString()).append(" AND ");
                parameter.setIndex(i + 1);
            }


            // 处理排序语句
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key != Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbWhere.append(key).append(value).append(" AND ");
                }
            }

            // 删除Where语句最后一个“ AND ”
            if (sbWhere.length() > 0) {
                sbWhere.delete(sbWhere.length() - 5, sbWhere.length());
                sbSQL.append(" WHERE ");
                sbSQL.append(sbWhere.toString());
            }

            // 创建Order by
            StringBuilder sbOrderBySQL = new StringBuilder();
            for (int i = 0; conditions != null && i < conditions.size(); i++) {
                KVObject kv = conditions.get(i);
                String key = (String) kv.getKey();
                if (key == Database.CONDITION_TYPE_ORDERBY) {
                    String value = (String) kv.getValue();
                    sbOrderBySQL.append(value).append(", ");
                }
            }

            // 删除Order by 语句最后的逗号", "
            if (sbOrderBySQL.length() > 0) {
                sbOrderBySQL.delete(sbOrderBySQL.length() - 2, sbOrderBySQL.length());
                sbSQL.append(" ORDER BY ");
                sbSQL.append(sbOrderBySQL.toString());
            }


            LogService.info("MySQLDao.query(): " + sbSQL.toString(), MySQLDao.class);
            printParameters(parameters);
            prestmt = conn.prepareStatement(sbSQL.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,  ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; parameters != null && i < parameters.size(); i++) {
                KVObject parameter = parameters.get(i);
                preparedStatementSetValue(parameter.getIndex(), parameter.getValue(), prestmt);
            }


            rs = prestmt.executeQuery();
            while(rs.next()) {
                T temp = Database.getInstanceFromResultSet(rs, clazz);
                list.add(temp);
            }
        }
        catch (Exception e) {
            // 打印异常日志
            MyException.deal(e);
            //list = new ArrayList<UserPO>();
        }
        finally {
            // 释放数据库资源
            close(rs, prestmt);
        }
        return list;
    }



}
