package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.service.system.SmsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/24/15
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class SmsAction extends BaseAction {

    // sendSms4PaymentPlan(String mobile, String paymentPlanId, Connection conn)

    @Autowired
    SmsService smsService;

    public String sendSms4PaymentPlan() throws Exception {

        String paymentPlanId = getHttpRequestParameter("paymentPlanId");

        smsService.sendSms4PaymentPlan(paymentPlanId, getConnection());


        getResult().setReturnValue("1");

        return SUCCESS;
    }
}
