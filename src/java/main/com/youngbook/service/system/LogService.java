package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.BasePO;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;


@Component("logService")
public class LogService extends BaseService {

    @Autowired
    ILogDao logDao;

    public Pager listPagerLogPO(LogPO logPO, int currentPage, int showRowCount, Connection conn) throws Exception {
        return logDao.listPagerLogPO(logPO, currentPage, showRowCount, conn);
    }

    public void save(LogPO logPO, Connection conn) throws Exception {
        logDao.save(logPO, conn);
    }


    public void save(String name, String peopleMessage, String machineMessage) throws Exception {
        logDao.save(name, peopleMessage, machineMessage);
    }
    public void save(String name, String peopleMessage, String machineMessage, Connection conn) throws Exception {
        logDao.save(name, peopleMessage, machineMessage, conn);
    }


    public void save(LogPO logPO) throws Exception {
        Connection conn = Config.getConnection();

        try {
            logDao.save(logPO, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }

    }

    public void save(String name, HttpServletRequest request, Connection conn) throws Exception {

        save(name, "", request, conn);

    }

    public void save(String name, String peopleMessage, HttpServletRequest request, Connection conn) throws Exception {

        String value = HttpUtils.getParametersStringValue(request);

        LogPO logPO = new LogPO();
        logPO.setName(name);
        logPO.setPeopleMessage(peopleMessage);
        logPO.setMachineMessage(value);

        save(logPO, conn);

    }


    public static void debug(String log, Class clazz) {
        Logger logger = Logger.getLogger(clazz);
        logger.debug(log);
    }

    public static void log2File(String message, Class clazz) {
        Logger logger = Logger.getLogger(clazz);
        logger.info(message);
    }

    public static void log2File(Class clazz) {

        try {
            Logger logger = Logger.getLogger(clazz);
            HttpServletRequest request = ServletActionContext.getRequest();
            String URI = request.getRequestURI();
            String IP = request.getRemoteHost();

            if (URI == null || URI.equals("") || IP == null || IP.equals("")) {
                logger.info("URI或者IP为空，数据非法请求！");
                return;
            }


            StringBuffer parameter = new StringBuffer();

            for (Object key : request.getParameterMap().keySet()) {
                String[] valueArray = (String[])request.getParameterMap().get(key);
                for (String value : valueArray) {
                    parameter.append(key);
                    parameter.append("=");
                    parameter.append(value);
                    parameter.append("&");
                }
            }
            // 删除参数最后一个&
            parameter = StringUtils.removeLastLetters(parameter, "&");

            logger.info("LOGDATA: {'IP'：'" + IP + "','URI'：'" + URI + "','parameters'：'" + parameter + "'}"); // 信息
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void info(String log, Class clazz) {
        console(log);
        Logger logger = Logger.getLogger(clazz);
        logger.info(log);
    }

    public static void info (BasePO po) {

        console("对象数据【" + po.getClass().getName() + "】" + po.toJsonObject());
        Logger logger = Logger.getLogger(po.getClass());
        logger.info("对象数据【"+po.getClass().getName()+"】" + po.toJsonObject());
    }

    public static void console(String m) {

        System.out.println("====== "+ TimeUtils.getNow()+" =====  :" + m);
    }


    public static void error(Exception e) {
        Logger logger = Logger.getLogger(LogService.class);
        logger.error("ERROR:" + e);
    }

    public static void log(String log) {
        Logger logger = Logger.getLogger(LogService.class);
        logger.info(log);
    }

    public static void log(Exception e) {
        e.printStackTrace();
    }

}
