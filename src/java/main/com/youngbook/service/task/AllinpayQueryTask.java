package com.youngbook.service.task;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.service.allinpay.AllinpayQueryService;

import java.sql.Connection;

public class AllinpayQueryTask extends Task {

    public AllinpayQueryTask() {
        this.setName("AllinpayQueryTask");
        this.setStartTime("2010-02-08 00:00:0");
        this.setStopTime("2020-02-08 00:00:0");
        //秒为单位
        this.setRepeatSecond(5);
    }

    @Override
    public void run() {
        try {
            Connection conn = Config.getConnection();
            try {

                System.out.println("扫描");
                AllinpayQueryService service = new AllinpayQueryService();
                service.query(2, 1, "20150101000000", "20151231000000", Config.getSystemConfig("web.default.operatorId"), conn);

            } catch (Exception e) {

                e.printStackTrace();

            }
            finally {
                Database.close(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}