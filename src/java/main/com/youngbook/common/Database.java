package com.youngbook.common;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.IgnoreDB;
import com.youngbook.annotation.Table;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.Utils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.service.system.LogService;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Database {

    public static final int QUERY_EXACTLY = 0;              // 字符串精确查询
    public static final int QUERY_FUZZY = 1;                // 字符串模糊查询
    public static final int NUMBER_BIGGER_THAN = 2;         // 数字大于
    public static final int NUMBER_BIGGER_OR_EQUAL = 3;     // 数字大于等于
    public static final int NUMBER_SMALLER_THAN = 4;        // 数字小于
    public static final int NUMBER_SMALLER_OR_EQUAL = 5;    // 数字小于等于
    public static final int NUMBER_EQUAL = 6;               // 数字等于

    public static final String ORDERBY_DESC = "DESC";
    public static final String ORDERBY_ASC = "ASC";
    public static final String ORDERBY_DEFAULT = "ASC";

    public static final String CONDITION_TYPE_ORDERBY = "CONDITION_TYPE_ORDERBY";
    public static final String CONDITION_TYPE_IN = "CONDITION_TYPE_IN";

    public static final String DATEFORMAT_YYYYMMDD = "'%Y-%m-%d'";
    public static final String DATEFORMAT_YYYYMMDDHH24MISS = "'%Y-%m-%d %H:%i:%S'";


    public static final int TABLE_TYPE_RUNTIME = 0;
    public static final int TABLE_TYPE_BACKUP = 1;

    /**
     * 将敏感字符编码
     *
     * 防止SQL注入
     * ＠author leevits
     *
     * @param s 待编码的SQL语句
     * @return
     */
    public static String encodeSQL(String s) {

//        if (!StringUtils.isEmpty(s)) {
//            MySQLCodec codec = new MySQLCodec(MySQLCodec.Mode.STANDARD);
//            s = ESAPI.encoder().encodeForSQL(codec, s);
//        }

        return s;
    }

    public static void rollback(Connection conn) throws Exception {
        if (conn != null) {
            LogService.debug("关闭数据库链接: " + conn.toString(), Database.class);
            conn.rollback();
        }
    }

    public static void close(Connection conn) throws Exception {
        try {
            if (conn != null) {
                LogService.debug("关闭数据库链接: " + conn.toString(), Database.class);
                conn.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 包含有不支持或敏感的字符
     * @param s
     * @return
     */
    public static boolean containsUnsupportCode(String s) {
        // todo 需要实现

        return false;
    }

    public static boolean isAvailableValue(Object value) {

        if (value == null) {
            return false;
        }

        if (!isSupportType(value.getClass())) {
            return false;
        }

        try {
            String s = String.valueOf(value);
            if (isInteger(value.getClass())) {
                Integer.parseInt(s);
            }

            else if (isFloat(value.getClass())) {
                Float.parseFloat(s);
            }

            else if (isDouble(value.getClass())) {
                Double.parseDouble(s);
            }

            else if (isLong(value.getClass())) {
                Long.parseLong(s);
            }
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    public static Integer getIntValue(Object object) {
        Integer value = null;
        if (Database.isInteger(object.getClass())) {
            value = Integer.parseInt(String.valueOf(object));
            if (value.intValue() == Integer.MAX_VALUE) {
                value = null;
            }
        }
        return value;
    }

    public static Float getFloatValue(Object object) {
        Float value = null;
        if (Database.isFloat(object.getClass())) {
            value = Float.parseFloat(String.valueOf(object));
            if (value.floatValue() == Float.MAX_VALUE) {
                value = null;
            }
        }
        return value;
    }

    public static Double getDoubleValue(Object object) {
        Double value = null;
        if (Database.isDouble(object.getClass())) {
            value = Double.parseDouble(String.valueOf(object));
            if (value.doubleValue() == Double.MAX_VALUE) {
                value = null;
            }
        }
        return value;
    }

    public static String getValueString(Object object) {
        String value = "";
        if (Database.isInteger(object.getClass())) {
            int intValue = Integer.parseInt(String.valueOf(object));
            if (intValue != Integer.MAX_VALUE) {
                value = String.valueOf(intValue);
            }
        } else if (Database.isDouble(object.getClass())) {
            double doubleValue = Double.parseDouble(String.valueOf(object));
            if (doubleValue != Double.MAX_VALUE) {
                value = String.valueOf(doubleValue);
            }
        } else if (Database.isFloat(object.getClass())) {
            float floatValue = Float.parseFloat(String.valueOf(object));
            if (floatValue != Float.MAX_VALUE) {
                value = String.valueOf(floatValue);
            }
        } else {
            value = String.valueOf(object);
        }
        return value;
    }



    private static Properties dbProperties = null;
    private static DataSource dataSource = null;


    /**
     * 通过连接池，获得数据库连接
     *
     * @author leevits
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {

        if (dataSource == null) {
            dbProperties = new Properties();
            dbProperties.load(Database.class.getClassLoader().getResourceAsStream("./database.properties"));
            dataSource = BasicDataSourceFactory.createDataSource(dbProperties);
        }

        Connection conn = dataSource.getConnection();
        LogService.debug("Database.getConnectioin(): 获得数据库连接: " + conn.toString(), Database.class);
        return conn;
    }


    public static String getTablePrimaryKey(Class clazz) throws Exception {
        String name = "";
        if (name == null || name.equals("")) {
            for (Field field : clazz.getDeclaredFields()) {
                Annotation idAnnotation = field.getAnnotation(Id.class);
                if (idAnnotation != null) {
                    name = field.getName();
                    return name;
                }
            }
        }
        return name;
    }

    public static Object getPrimaryKeyValue(Object object) throws Exception {
        Object value = new Object();
        String strPK = Database.getTablePrimaryKey(object.getClass());
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getName().equals(strPK)) {
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + field.getName().substring(1);
                Method getMethod = Utils.getMethod(object.getClass(), getMethodName, new Class[]{});
                value = getMethod.invoke(object, new Object[]{});
                continue;
            }
        }
        return value;
    }


    public static List<KVObject> addQueryKVObject(int index, Object value, List<KVObject> parameters) {

        if (parameters == null) {
            parameters = new ArrayList<KVObject>();
        }

        KVObject parameter = new KVObject(index, value);

        parameters.add(parameter);

        return parameters;
    }

    public static List<KVObject> addQueryKVObject(int index, Object value) {
        return addQueryKVObject(index, value, null);
    }

    public static KVObject buildQueryKVObject(Field field, Object value, QueryType queryType) {
        queryType = (queryType == null ? new QueryType() : queryType);
        KVObject fieldWithValue = new KVObject();
        // 文本类型
        if (Database.isStringType(value.getClass()) && !value.toString().equals("")) {
            // 精确查询
            if (Database.QUERY_EXACTLY == queryType.stringType) {
                fieldWithValue.setKey(field.getName() + "=?");
                // 设置值
                fieldWithValue.setValue(value);
            }
            // 模糊查询
            else {
                fieldWithValue.setKey(field.getName() + " LIKE ?");
                // 设置值
                fieldWithValue.setValue("%" + value + "%");
            }
            return fieldWithValue;
        }
        // 整型
        if (Database.isInteger(value.getClass())) {
            Integer temp = Integer.parseInt(value.toString());
            if (temp == Integer.MAX_VALUE) {
                return null;
            }
            if (Database.NUMBER_EQUAL == queryType.numberType) {
                fieldWithValue.setKey(field.getName() + "=?");
            } else if (queryType.numberType == Database.NUMBER_BIGGER_THAN) {
                fieldWithValue.setKey(field.getName() + "<?");
            } else if (queryType.numberType == Database.NUMBER_BIGGER_OR_EQUAL) {
                fieldWithValue.setKey(field.getName() + ">=?");
            } else if (queryType.numberType == Database.NUMBER_SMALLER_THAN) {
                fieldWithValue.setKey(field.getName() + "<?");
            } else if (queryType.numberType == Database.NUMBER_SMALLER_OR_EQUAL) {
                fieldWithValue.setKey(field.getName() + "<=?");
            }
        }
        // Double
        else if (Database.isDouble(value.getClass())) {
            Double temp = Double.parseDouble(value.toString());
            if (temp == Double.MAX_VALUE) {
                return null;
            }
            if (Database.NUMBER_EQUAL == queryType.numberType) {
                fieldWithValue.setKey(field.getName() + "=?");
            } else if (queryType.numberType == Database.NUMBER_BIGGER_THAN) {
                fieldWithValue.setKey(field.getName() + "<?");
            } else if (queryType.numberType == Database.NUMBER_BIGGER_OR_EQUAL) {
                fieldWithValue.setKey(field.getName() + ">=?");
            } else if (queryType.numberType == Database.NUMBER_SMALLER_THAN) {
                fieldWithValue.setKey(field.getName() + "<?");
            } else if (queryType.numberType == Database.NUMBER_SMALLER_OR_EQUAL) {
                fieldWithValue.setKey(field.getName() + "<=?");
            }
        }
        // Float
        else if (Database.isFloat(value.getClass())) {
            Float temp = Float.parseFloat(value.toString());
            if (temp == Float.MAX_VALUE) {
                return null;
            }
            if (Database.NUMBER_EQUAL == queryType.numberType) {
                fieldWithValue.setKey(field.getName() + "=?");
            } else if (queryType.numberType == Database.NUMBER_BIGGER_THAN) {
                fieldWithValue.setKey(field.getName() + "<?");
            } else if (queryType.numberType == Database.NUMBER_BIGGER_OR_EQUAL) {
                fieldWithValue.setKey(field.getName() + ">=?");
            } else if (queryType.numberType == Database.NUMBER_SMALLER_THAN) {
                fieldWithValue.setKey(field.getName() + "<?");
            } else if (queryType.numberType == Database.NUMBER_SMALLER_OR_EQUAL) {
                fieldWithValue.setKey(field.getName() + "<=?");
            }
        }
        // Long
        else if (Database.isLong(value.getClass())) {
            Long temp = Long.parseLong(value.toString());
            if (temp == Long.MAX_VALUE) {
                return null;
            }
            if (Database.NUMBER_EQUAL == queryType.numberType) {
                fieldWithValue.setKey(field.getName() + "=?");
            } else if (queryType.numberType == Database.NUMBER_BIGGER_THAN) {
                fieldWithValue.setKey(field.getName() + "<?");
            } else if (queryType.numberType == Database.NUMBER_BIGGER_OR_EQUAL) {
                fieldWithValue.setKey(field.getName() + ">=?");
            } else if (queryType.numberType == Database.NUMBER_SMALLER_THAN) {
                fieldWithValue.setKey(field.getName() + "<?");
            } else if (queryType.numberType == Database.NUMBER_SMALLER_OR_EQUAL) {
                fieldWithValue.setKey(field.getName() + "<=?");
            }
        }
        // 没有任何值
        if (fieldWithValue.getKey() == null) {
            return null;
        }
        // 设置值
        fieldWithValue.setValue(value);
        return fieldWithValue;
    }

    /**
     * 构建查询条件语句片段，例如【name='abc' AND】
     * @param field
     * @param value
     * @param queryType
     * @return
     */
    public static String buildQueryString(Field field, Object value, QueryType queryType) {
        if (queryType == null) {
            queryType = new QueryType();
        }
        StringBuffer sbWhereSQL = new StringBuffer();
        String tagBegin = "";
        String tagEnd = "";
        // 检查是否是字符串
        if (Database.isStringType(value.getClass())) {
            String temp = (String) value;
            if (!temp.equals("")) {
                // 精确查询
                if (queryType.stringType == Database.QUERY_EXACTLY) {
                    tagBegin = "='";
                    tagEnd = "'";
                }
                // 模糊查询
                else {
                    tagBegin = " like '%";
                    tagEnd = "%'";
                }

                temp = Database.encodeSQL(String.valueOf(temp));

                sbWhereSQL.append(field.getName() + tagBegin);
                sbWhereSQL.append(temp);
                sbWhereSQL.append(tagEnd + " AND ");
            }
        }
        // 检查是否是整型
        else if (Database.isInteger(value.getClass())) {
            if (value != null && !value.equals("")) {
                int temp = Integer.parseInt(value.toString());
                if (temp != Integer.MAX_VALUE) {
                    // 等于
                    if (queryType.numberType == Database.NUMBER_EQUAL) {
                        tagBegin = "=";
                    } else if (queryType.numberType == Database.NUMBER_BIGGER_THAN) {
                        tagBegin = ">";
                    } else if (queryType.numberType == Database.NUMBER_BIGGER_OR_EQUAL) {
                        tagBegin = ">=";
                    } else if (queryType.numberType == Database.NUMBER_SMALLER_THAN) {
                        tagBegin = "<";
                    } else if (queryType.numberType == Database.NUMBER_SMALLER_OR_EQUAL) {
                        tagBegin = "<=";
                    }
                    sbWhereSQL.append(field.getName() + tagBegin);
                    sbWhereSQL.append(temp);
                    sbWhereSQL.append(tagEnd + " AND ");
                }
            }
        }
        // 检查是否是浮点型
        else if (Database.isDouble(value.getClass())) {
            if (value != null && !value.equals("")) {
                double temp = Double.parseDouble(value.toString());
                // 如果值是0.0或者是Double最大值，则认为是空值
                if (temp != Double.MAX_VALUE && temp != 0.0) {
                    // 等于
                    if (queryType.numberType == Database.NUMBER_EQUAL) {
                        tagBegin = "=";
                    } else if (queryType.numberType == Database.NUMBER_BIGGER_THAN) {
                        tagBegin = ">";
                    } else if (queryType.numberType == Database.NUMBER_BIGGER_OR_EQUAL) {
                        tagBegin = ">=";
                    } else if (queryType.numberType == Database.NUMBER_SMALLER_THAN) {
                        tagBegin = "<";
                    } else if (queryType.numberType == Database.NUMBER_SMALLER_OR_EQUAL) {
                        tagBegin = "<=";
                    }
                    sbWhereSQL.append(field.getName() + tagBegin);
                    sbWhereSQL.append(temp);
                    sbWhereSQL.append(tagEnd + " AND ");
                }
            }

        }
        return sbWhereSQL.toString();
    }

    /**
     * 判断一个列是否被忽略注解标记
     *
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

    /**
     * 从ResultSet构造对象实例
     *
     * @param rs
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getInstanceFromResultSet(ResultSet rs, Class<T> clazz) throws Exception {
        T object = clazz.newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            if (isIgnore(field)) {
                continue;
            }
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + fieldName.substring(1);
            Method setMethod = Utils.getMethod(clazz, setter, new Class[]{field.getType()});
            if (setMethod == null) {
                MyException.newInstance("无法找到方法定义["+setter+"]").throwException();
            }

            Class paramType = setMethod.getParameterTypes()[0];
            String rsGetMethodName = "get" + paramType.getSimpleName();
            Method rsGetMethod = Utils.getMethod(rs.getClass(), rsGetMethodName, new Class[]{String.class});
            try {
                Object value = rsGetMethod.invoke(rs, new String[]{fieldName});

//                MyLog.debug("从数据库获得数值，字段【"+fieldName+"】，值【"+value+"】");

                setMethod.invoke(object, new Object[]{value});
            } catch (InvocationTargetException ite) {
                // 忽略
            }
        }
        return object;
    }

    public static void main(String [] args ) throws Exception {
//        Connection conn = Config.getConnection();
//        String s = Database.getDatabaseName(conn);
//        System.out.println(s);
    }

    public static String getDatabaseName(Connection conn) throws Exception{
        DatabaseMetaData dmd = conn.getMetaData();
        String url = dmd.getURL();
        return url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
    }

    public static int createTableStructure(String fromTableName, String newTableName, Connection conn) throws Exception {

        String sql = "CREATE TABLE `"+newTableName+"` LIKE `"+fromTableName+"`;";

        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sql);
        List list = MySQLDao.search(dbSQL, conn);

        if (list != null) {
            return 1;
        }

        return 0;
    }

    public static boolean tableExists(String tableName, Connection conn) throws Exception {
        String databaseName = Database.getDatabaseName(conn);

        String sql = "show table STATUS  from "+databaseName+" where name='"+tableName+"'";
        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sql);
        List list = MySQLDao.search(dbSQL, conn);

        if (list != null && list.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * 通过注解获得表名
     *
     * @param clazz
     * @return
     * @throws Exception
     */
    public static String getTableName(Class clazz) throws Exception {
        String name = "";
        if (name == null || name.equals("")) {
            Table tableAnnotation = (Table) clazz.getAnnotation(Table.class);
            if (tableAnnotation != null) {
                name = tableAnnotation.name();
            }
        }
        if (name == null || name.trim().equals("")) {
            throw new Exception("获取表名失败，请检查配置类对应的表：" + clazz.getName());
        }
        return name;
    }

    public static String getTableBackupName(Class clazz) throws Exception {
        String name = "";
        if (name == null || name.equals("")) {
            Table tableAnnotation = (Table) clazz.getAnnotation(Table.class);
            if (tableAnnotation != null) {
                name = tableAnnotation.backupTableName();
            }
        }
        if (name == null || name.trim().equals("")) {
            throw new Exception("获取表名失败，请检查配置类对应的表：" + clazz.getName());
        }
        return name;
    }

    /**
     * @param clazz
     * @pdOid a0154e1e-60e7-426c-9f09-fdbe91a6a458
     */
    public static boolean isSimpleType(Class clazz) {
        if (isStringType(clazz) || isNumberType(clazz) || isDateTime(clazz) || isFloat(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * @param clazz
     * @pdOid 4d97a0a8-70c2-4f19-91d4-c02fa30cc701
     */
    public static boolean isNumberType(Class clazz) {
        if (isInteger(clazz) || isDouble(clazz) || isLong(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 判断clazz是否是系统支持的类型
     *
     * @author leevits
     * @param clazz
     * @return
     */
    public static boolean isSupportType(Class clazz) {

        if (isStringType(clazz) || isNumberType(clazz) || isDateTime(clazz)) {
            return true;
        }

        return false;
    }

    /**
     * @param clazz
     * @pdOid 795dd7dc-f04d-48b3-8d1e-b5d6d9e6343a
     */
    public static boolean isStringType(Class clazz) {
        String clazzName = clazz.getName();
        if (clazzName != null && clazzName.equalsIgnoreCase(String.class.getName())) {
            return true;
        }
        return false;
    }

    /**
     * @pdOid 0d0fb717-8b31-47da-8875-245d7a62095d
     */
    public static boolean isInteger(Class clazz) {
        String clazzName = clazz.getName();
        if (clazzName != null && (clazzName.equalsIgnoreCase(Integer.class.getName())
                || clazzName.equalsIgnoreCase(int.class.getName()))) {
            return true;
        }
        return false;
    }

    public static boolean isLong(Class clazz) {
        String clazzName = clazz.getName();
        if (clazzName != null && (clazzName.equalsIgnoreCase(Long.class.getName())
                || clazzName.equalsIgnoreCase(long.class.getName()))) {
            return true;
        }
        return false;
    }

    /**
     * @pdOid 8ab25c65-7cd7-45c9-94cb-86f74b8f2636
     */
    public static boolean isDouble(Class clazz) {
        String clazzName = clazz.getName();
        if (clazzName != null && (clazzName.equalsIgnoreCase(Double.class.getName())
                || clazzName.equalsIgnoreCase(double.class.getName()))) {
            return true;
        }
        return false;
    }

    public static boolean isFloat(Class clazz) {
        String clazzName = clazz.getName();
        if (clazzName != null && (clazzName.equalsIgnoreCase(Float.class.getName())
                || clazzName.equalsIgnoreCase(float.class.getName()))) {
            return true;
        }
        return false;
    }

    public static boolean isBoolean(Class clazz) {
        String clazzName = clazz.getName();
        if (clazzName != null && (clazzName.equalsIgnoreCase(Boolean.class.getName())
                || clazzName.equalsIgnoreCase(boolean.class.getName()))) {
            return true;
        }
        return false;
    }

    /**
     * @pdOid 6047f4af-29e1-4e63-a4e5-54f48071aa4e
     */
    public static boolean isDateTime(Class clazz) {
        String clazzName = clazz.getName();
        if (clazzName != null && clazzName.equalsIgnoreCase(Date.class.getName())) {
            return true;
        }
        return false;
    }

}
