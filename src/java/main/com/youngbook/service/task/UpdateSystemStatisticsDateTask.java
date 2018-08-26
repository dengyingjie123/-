package com.youngbook.service.task;

import com.youngbook.common.utils.runner.Task;
import com.youngbook.service.system.StatisticsService;

/**
 * Created by Administrator on 2015/4/29.
 * 更新系统统计数据
 *
 */

public class UpdateSystemStatisticsDateTask extends Task {

    public UpdateSystemStatisticsDateTask(){
        this.setName("UpdateSystemStatisticsDateTask");
        this.setStartTime("2010-02-08 00:00:0");
        this.setStopTime("2020-02-08 00:00:0");
        // 1 小时执行 1 次
        this.setRepeatSecond(60);
    }


    @Override
    public void run() {
        StatisticsService service = new StatisticsService();
        try {
            service.updateSystemStatisticsData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
