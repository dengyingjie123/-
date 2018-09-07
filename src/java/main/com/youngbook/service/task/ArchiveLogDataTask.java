package com.youngbook.service.task;

import com.youngbook.common.Database;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.dao.MySQLDao;
import com.youngbook.service.system.LogService;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Lee on 2016/6/2.
 */
public class ArchiveLogDataTask extends Task {


    public ArchiveLogDataTask() {

        this.setName("ArchiveLogDataTask");
//        this.setStartTime ( "2010-01-05 20:52:00" );
//        this.setStopTime ( "2015-2-23 21:01:30" );
//        this.setRepeatSecond ( 5 );
//        this.setStartTime(Config.getValue("//config/timerunner/tasks/task[@name='"
//                            + this.getName() + "']/starttime"));
//        this.setStopTime(Config.getValue("//config/timerunner/tasks/task[@name='"
//                + this.getName() + "']/stoptime"));
//        this.setRepeatSecond (Long.valueOf(Config.getValue("//config/timerunner/tasks/task[@name='"
//                + this.getName() + "']/repeattime")));

        this.setStartTime("2010-02-08 00:00:0");
        this.setStopTime("2020-02-08 00:00:0");
        this.setRepeatSecond(60 * 60 * 24);
    }

    @Override
    public void run() {
        Connection conn = null;
        try {

            LogService.info("开始执行历史数据清理任务", this.getClass());


            conn = Database.getConnection();
            conn.setAutoCommit(false);


            /**
             * system_securitylog_archive
             *
             */
            int count = MySQLDao.primaryUpdate("insert into system_securitylog_archive select * from system_securitylog", null, conn);


            if (count > 0) {
                MySQLDao.primaryUpdate("delete from system_securitylog", null, conn);
            }

            LogService.info("清理 system_security 表：" + count, this.getClass());



            /**
             * system_log
             *
             */
            count = MySQLDao.primaryUpdate("INSERT INTO system_log_archive (`id`, `state`, `operatorId`, `operateTime`, `Name`, `Content`, `URL`, `Parameters`, `Type`, `IP`) select `id`, `state`, `operatorId`, `operateTime`, `Name`, `Content`, `URL`, `Parameters`, `Type`, `IP` from system_log;", null, conn);


            if (count > 0) {
                MySQLDao.primaryUpdate("delete from system_log", null, conn);
            }

            LogService.info("清理 system_log 表：" + count, this.getClass());

            LogService.info("开始执行历史数据清理任务", this.getClass());

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


        System.out.println("ArchiveLogDataTask running");
    }
}
