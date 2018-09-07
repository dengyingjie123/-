package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.IJsonable;
import com.youngbook.common.Pager;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.po.sale.PaymentPlanStatus;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.service.sale.CapitalPreService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * 资金准备Action
 * Created by yux on 2016/6/14.
 */
public class CapitalPreAction extends BaseAction {

    private PaymentPlanPO paymentPlanPO=new PaymentPlanPO();
    private PaymentPlanVO paymentPlanVO=new PaymentPlanVO();
    @Autowired
    CapitalPreService capitalPreService;

    public String list() throws Exception{
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        //根据条件查询数据
        // List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, PaymentPlanVO.class);
        String startTime = HttpUtils.getParameter(getRequest(), "paymentPlanVO_paymentTime_Start");
        String endTime = HttpUtils.getParameter(getRequest(), "paymentPlanVO_paymentTime_End");
        //将数据封装起来 到一个分页的类里面
        Pager pager = capitalPreService.list(paymentPlanVO, startTime, endTime, null, conn);
        //设置状态中文到statusName
        List<IJsonable> data = pager.getData();
        for(int i = 0; i < data.size(); i ++) {
            PaymentPlanVO vo = (PaymentPlanVO)data.get(i);
            String statusName = PaymentPlanStatus.getStatusName(vo.getStatus());
            vo.setStatusName(statusName);
        }
        //将数据以JSON的方式返回到脚本哪里
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public PaymentPlanPO getPaymentPlanPO() {
        return paymentPlanPO;
    }

    public void setPaymentPlanPO(PaymentPlanPO paymentPlanPO) {
        this.paymentPlanPO = paymentPlanPO;
    }

    public PaymentPlanVO getPaymentPlanVO() {
        return paymentPlanVO;
    }

    public void setPaymentPlanVO(PaymentPlanVO paymentPlanVO) {
        this.paymentPlanVO = paymentPlanVO;
    }
}
