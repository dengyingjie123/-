package com.youngbook.service.task.allinpaycircle;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.service.allinpaycircle.AllinpayCircleService;
import com.youngbook.service.system.LogService;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by leevits on 9/1/2018.
 */
public class AllinpayCircleDealWithdrawalByBankNormalTask extends Task {

    AllinpayCircleService allinpayCircleService = Config.getBeanByName("allinpayCircleService", AllinpayCircleService.class);

    public AllinpayCircleDealWithdrawalByBankNormalTask() {

        this.setName("AllinpayCircleDealWithdrawalByBankNormalTask");

        this.setStartTime("2010-02-08 00:00:0");
        this.setStopTime("2020-02-08 00:00:0");
        this.setRepeatSecond(30);
    }

    @Override
    public void run() {

        LogService.console("AllinpayCircleDealWithdrawalByBankNormalTask running - Start");
        Connection conn = null;


        try {
            conn = Config.getConnection();

            conn.setAutoCommit(false);

            allinpayCircleService.dealPayByShare(conn);

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
    }
}
