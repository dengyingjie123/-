package com.youngbook.service.task;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.SmsPO;
import com.youngbook.entity.po.system.SmsStatus;
import com.youngbook.entity.po.system.SmsType;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.SmsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class SmsSenderTask extends Task {

    SmsService smsService = Config.getBeanByName("smsService", SmsService.class);

    public SmsSenderTask() {

        this.setName("SmsSenderTask");

        this.setStartTime("2010-02-08 00:00:0");
        this.setStopTime("2020-02-08 00:00:0");
        this.setRepeatSecond(5);
    }

    @Override
    public void run() {
        LogService.console("SMS running - Start");
        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" SELECT");
            sbSQL.append("     *");
            sbSQL.append(" FROM");
            sbSQL.append("     System_sms");
            sbSQL.append(" WHERE");
            sbSQL.append("     1 = 1");
            sbSQL.append(" and state=" + Config.STATE_CURRENT);
            sbSQL.append(" AND Type = " + SmsType.TYPE_IDENTIFY_CODE);
            sbSQL.append(" AND (FeedbackStatus="+SmsStatus.STATUS_WAIT+")");
            sbSQL.append(" and (WaitingTime<NOW() or WaitingTime is null)");


            List<SmsPO> smses = MySQLDao.query(sbSQL.toString(), SmsPO.class, null, conn);
            UserPO user = new UserPO();
            user.setId("0");


            if ("0".equals(Config.getSystemConfig("system.debug.sms"))) {
                smsService.send(smses, user, conn);
            }

            conn.commit();
        }
        catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }

            } catch (Exception e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }


        LogService.console("SMS running - Stop");
    }
}
