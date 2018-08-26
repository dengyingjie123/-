package com.youngbook.service.sale;

import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.sale.IPaymentPlanDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.PaymentPlanMoneyPreparePO;
import com.youngbook.entity.vo.Sale.PaymentPlanMoneyPrepareVO;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**z
 * 资金准备service
 *
 * Created by yux on 2016/6/14.
 */
@Component("paymentPlanMoneyPrepareService")
public class PaymentPlanMoneyPrepareService extends BaseService{
    @Autowired
    IPaymentPlanDao paymentPlanDao;

    public Pager list(PaymentPlanVO paymentPlanVO, String startTime, String endTime, String departments, Connection conn) throws Exception{
        return paymentPlanDao.getCapitalPre(paymentPlanVO,startTime,endTime,departments,conn);
    }

    public List<PaymentPlanVO> getCapitalPreDetail(String startTime, String endTime, Connection connection) throws Exception {
        return paymentPlanDao.getCapitalPreDetail(startTime,endTime,connection);

    }

    public int insertOrUpdate(PaymentPlanMoneyPreparePO paymentPlanMoneyPreparePO, PaymentPlanMoneyPrepareVO paymentPlanMoneyPrepareVO, UserPO user, Connection conn) throws Exception {
        if (paymentPlanMoneyPreparePO == null) {
            throw new Exception("资金准备数据提交失败");
        }

        //当前用户操作类是否为空
        if (user == null) {
            throw new Exception("当前用户失效");
        }

        //当前数据链接是否空
        if (conn == null) {
            throw new Exception("链接错误");
        }

        int count = 0;
        if(paymentPlanMoneyPreparePO.getId().equals("")){
            count= MySQLDao.insertOrUpdate(paymentPlanMoneyPreparePO,conn);

            if (count == 1) {
                count = BizRouteService.insertOrUpdate(paymentPlanMoneyPreparePO.getId(), null,null,null,
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Finance.FinanceExpende")), true, user, conn);
            }
        }else {
            count= MySQLDao.insertOrUpdate(paymentPlanMoneyPreparePO,conn);
            if (count == 1) {
                count = BizRouteService.insertOrUpdate(paymentPlanMoneyPreparePO.getId(), null,null,null,
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Finance.FinanceExpende")), false, user, conn);
            }
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }
}