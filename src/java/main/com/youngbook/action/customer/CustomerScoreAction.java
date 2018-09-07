package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.entity.po.customer.CustomerScorePO;
import com.youngbook.entity.vo.customer.CustomerScoreVO;
import com.youngbook.service.customer.CustomerScoreService;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomerScoreAction extends BaseAction {

    @Autowired
    CustomerScoreService customerScoreService;


    public String getCustomerScore() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        CustomerScoreVO customerScoreVO = customerScoreService.getCustomerScoreVO(customerId, getLoginCustomer().getId(), getConnection());

        if (customerScoreVO != null) {
            getResult().setReturnValue(customerScoreVO);
        }

        return SUCCESS;
    }
}
