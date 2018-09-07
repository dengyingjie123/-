package com.youngbook.service.task;

import com.youngbook.common.utils.TimeUtils;
import junit.framework.TestCase;

public class AllinpayOrderQueryBatchTaskTest extends TestCase {

    public void testRun() throws Exception {

        AllinpayOrderQueryBatchTask task = new AllinpayOrderQueryBatchTask();
        try {
            String yesterday = TimeUtils.getTime(TimeUtils.getNow(), -1, TimeUtils.DATE);
            yesterday = TimeUtils.format(yesterday, TimeUtils.Format_YYYY_MM_DD_HH_M_S, TimeUtils.Format_YYYY_MM_DD);

            String today = TimeUtils.getNowDate();

            task.doQuery(today);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}