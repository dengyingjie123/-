package com.youngbook.common;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.ObjectUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.dao.system.LogDaoImpl;
import com.youngbook.entity.po.BasePO;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.entity.vo.BaseVO;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.sql.Connection;

/**
 * User: Lee
 * See: http://c.youngbook.net/pages/viewpage.action?pageId=8159249
 */
public class MyException extends Exception {


    // 建议用如下属性
    public String peopleMessage = "";
    public String machineMessage = "";
    public String returnValue = "";
    // 以上可用


    // 是否自动记录日志
    private boolean needSaveLog = true;

    public static final Exception UNSUPPORT_EXCEPTION = new Exception("此类型不支持");
    public static final Exception Permission_EXCEPTION = new Exception("权限异常");


    private int code;
    private String message = new String();
    private String location = new String();
//    private Exception exception = null;

    private Connection conn = null;
    private LogPO log = null;

    public MyException() {

    }

    public static String getData(Object object) throws Exception {

        if (object == null) {
            return "null";
        }

        if (ObjectUtils.isInstance(object, BasePO.class)) {
            return((BasePO) object).toJsonObject().toString();
        }

        if (object instanceof BasePO) {
            return((BasePO) object).toJsonObject().toString();
        }

        if (object instanceof BaseVO) {
            return((BaseVO) object).toJsonObject().toString();
        }

        return object.toString();
    }

    public static void quiet(Exception e) {
        e.printStackTrace();
    }

    public static String getExceptionMessage(Exception e) {
        String exceptionMessage = ExceptionUtils.getStackTrace(e);
        return exceptionMessage;
    }

    public LogPO findLog() {
        if (log == null) {
            log = new LogPO();
        }
        return log;
    }

//    public MyException(Exception exception) {
//        this.exception = exception;
//    }



    public static void deal(Exception ex) throws Exception{
        ex.printStackTrace();
        throw ex;
    }

    public static MyException newInstance(String message) {
        MyException myException = new MyException();
        myException.setMessage(message);

        myException.setPeopleMessage(message);
        myException.setMachineMessage(message);
//        myException.setException(new Exception(message));
        Connection conn = null;
        try {
            conn = Config.getConnection();

            LogPO logPO = new LogPO();
            logPO.setName("系统异常");
            logPO.setMachineMessage(message);

            ILogDao logDao = new LogDaoImpl();
            logDao.save(logPO, conn);
        }
        catch (Exception e) {

        }
        finally {
            try {
                Database.close(conn);
            }
            catch (Exception ex) {

            }
        }

        return myException;
    }

    public void save() {
        try {

            Connection conn = Config.getConnection();
            try {

                conn.setAutoCommit(false);

                LogPO logPO = new LogPO();

                logPO.setPeopleMessage(peopleMessage);
                logPO.setMachineMessage(machineMessage);

                MySQLDao.insertOrUpdate(logPO, conn);

                // code here

                conn.commit();
            }
            catch (Exception e) {
                conn.rollback();
                throw e;
            }
            finally {
                Database.close(conn);
            }


        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static MyException newInstance(int code, String message) {
        MyException myException = new MyException();
        myException.setCode(code);
        myException.setMessage(message);

        myException.setPeopleMessage(message);
        myException.setMachineMessage(message);
//        myException.setException(new Exception(message));
        return myException;
    }

    public static MyException newInstance(String peopleMessage, String machineMessage, String returnValue) {
        MyException myException = new MyException();
        myException.setPeopleMessage(peopleMessage);
        myException.setMachineMessage(machineMessage);
        myException.setReturnValue(returnValue);
        return myException;
    }

    public static MyException newInstance(String peopleMessage, String machineMessage) {
        MyException myException = new MyException();
        myException.setPeopleMessage(peopleMessage);
        myException.setMessage(peopleMessage);
        myException.setMachineMessage(machineMessage);
        return myException;
    }


    public MyException addConnection(Connection conn) {
        this.conn = conn;
        return this;
    }

    public MyException addLog(LogPO log) {
        this.log = log;
        return this;
    }

    public MyException addLocation(String location) {
        this.location = location;
        return this;
    }

    public void deal() throws Exception {
//        Connection conn = Config.getConnection();
//        try {
//            if (log == null) {
//                log = new LogPO();
//                log.setContent(message);
//            }
//
//            if (!StringUtils.isEmpty(location)) {
//                log.setName(location);
//            }
//
//            MyLog.log(log, conn);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (conn != null) {
//                conn.close();
//            }
//        }
    }

    public String deal4W() throws Exception {

        deal();

        return "info";
    }

    public void throwException() throws Exception {

//        // 记录日志
//        if (needSaveLog && conn != null) {
//
//            if (log == null) {
//                log = new LogPO();
//                log.setContent(message);
//            }
//
//            if (!StringUtils.isEmpty(location)) {
//                log.setName(location);
//            }
//
//            MyLog.log(log, conn);
//        }

        throw this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public LogPO getLog() {
        return log;
    }

    public void setLog(LogPO log) {
        this.log = log;
    }

    public String getPeopleMessage() {
        return peopleMessage;
    }

    public void setPeopleMessage(String peopleMessage) {
        this.peopleMessage = peopleMessage;
    }

    public String getMachineMessage() {
        return machineMessage;
    }

    public void setMachineMessage(String machineMessage) {
        this.machineMessage = machineMessage;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }
}
