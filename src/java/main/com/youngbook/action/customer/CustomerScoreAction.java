package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.entity.po.customer.CustomerScorePO;
import com.youngbook.service.customer.CustomerScoreService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Lee on 1/9/2018.
 */
public class CustomerScoreAction extends BaseAction {

    @Autowired
    CustomerScoreService customerScoreService;


    public String getCustomerScore() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        CustomerScorePO customerScorePO = customerScoreService.loadCustomerScorePO(customerId, getConnection());

        if (customerScorePO != null) {
            getResult().setReturnValue(customerScorePO);
        }

        return SUCCESS;
    }
}
