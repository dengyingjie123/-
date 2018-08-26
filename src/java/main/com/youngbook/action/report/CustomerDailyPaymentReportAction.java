package com.youngbook.action.report;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.vo.report.CustomerDailyPaymentReportVO;
import com.youngbook.service.report.CustomerDailyPaymentReportService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张舜清 on 2015/9/8.
 */
public class CustomerDailyPaymentReportAction extends BaseAction {
    CustomerDailyPaymentReportVO customerDailyPaymentReportVO = new CustomerDailyPaymentReportVO();
    CustomerDailyPaymentReportService customerDailyPaymentReportService = new CustomerDailyPaymentReportService();

    /**
     * 创建人：张舜清
     * 时间：2015年9月8日09:41:04
     * 内容：查询报表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = customerDailyPaymentReportService.list(customerDailyPaymentReportVO, conditions,customerDailyPaymentReportVO.getPaymentTime(), request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public CustomerDailyPaymentReportVO getCustomerDailyPaymentReportVO() {
        return customerDailyPaymentReportVO;
    }

    public void setCustomerDailyPaymentReportVO(CustomerDailyPaymentReportVO customerDailyPaymentReportVO) {
        this.customerDailyPaymentReportVO = customerDailyPaymentReportVO;
    }
}
