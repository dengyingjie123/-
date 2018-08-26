package com.youngbook.action.report;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.vo.report.ProductDailyPaymentReportVO;
import com.youngbook.service.report.ProductDailyPaymentReportService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张舜清 on 2015/9/7.
 */
public class ProductDailyPaymentReportAction extends BaseAction {
    ProductDailyPaymentReportVO productDailyPaymentReportVO = new ProductDailyPaymentReportVO();
    ProductDailyPaymentReportService productDailyPaymentReportService = new ProductDailyPaymentReportService();

    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = productDailyPaymentReportService.list(productDailyPaymentReportVO, conditions,productDailyPaymentReportVO.getPaymentTime(), request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public ProductDailyPaymentReportVO getProductDailyPaymentReportVO() {
        return productDailyPaymentReportVO;
    }

    public void setProductDailyPaymentReportVO(ProductDailyPaymentReportVO productDailyPaymentReportVO) {
        this.productDailyPaymentReportVO = productDailyPaymentReportVO;
    }
}
