package com.youngbook.service.sale;

import com.youngbook.common.Pager;
import com.youngbook.dao.sale.IPaymentPlanDao;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**z
 * 资金准备service
 *
 * Created by yux on 2016/6/14.
 */
@Component("capitalPreService")
public class CapitalPreService extends BaseService{
    @Autowired
    IPaymentPlanDao paymentPlanDao;

    public Pager list(PaymentPlanVO paymentPlanVO, String startTime, String endTime, String departments, Connection conn) throws Exception{
        return paymentPlanDao.getCapitalPre(paymentPlanVO,startTime,endTime,departments,conn);
    }

}