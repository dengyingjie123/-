package com.youngbook.service.task;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.SmsPO;
import com.youngbook.entity.po.system.SmsStatus;
import com.youngbook.entity.po.system.SmsType;
import com.youngbook.service.pay.FuiouService;
import com.youngbook.service.system.LogService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lee on 11/27/2017.
 */
public class FuiouMobilePayCallbackTask extends Task {

    FuiouService fuiouService = Config.getBeanByName("fuiouService", FuiouService.class);

    public FuiouMobilePayCallbackTask() {

        this.setName("FuiouMobilePayCallbackTask");

        this.setStartTime("2010-02-08 00:00:0");
        this.setStopTime("2020-02-08 00:00:0");
        this.setRepeatSecond(30);
    }


    @Override
    public void run() {
        LogService.console("FuiouMobilePayCallbackTask running - Start");
        Connection conn = null;
        try {
            conn = Config.getConnection();
            conn.setAutoCommit(false);

            fuiouService.dealMobilePayCallback(conn);

            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
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


        LogService.console("FuiouMobilePayCallbackTask running - Stop");
    }
}
