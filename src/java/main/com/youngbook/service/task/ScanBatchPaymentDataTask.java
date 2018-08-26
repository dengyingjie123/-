package com.youngbook.service.task;

import com.youngbook.common.utils.runner.Task;
import com.youngbook.service.allinpay.AllinpayBatchPaymentService;


public class ScanBatchPaymentDataTask extends Task {

    public ScanBatchPaymentDataTask(){
        this.setName("ScanBatchPaymentDataTask");
        this.setStartTime("2010-02-08 00:00:0");
        this.setStopTime("2020-02-08 00:00:0");
        //5秒执行一次 秒为单位
        this.setRepeatSecond(30);
    }

    @Override
    public void run() {

        AllinpayBatchPaymentService allinpayBatchPaymentService = new AllinpayBatchPaymentService();
        try {
            allinpayBatchPaymentService.buildBatchPaymentDatas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
