package com.youngbook.dao.sale;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.Pager;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.PaymentPlanCheckPO;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
public interface IPaymentPlanDao {
    public PaymentPlanVO getCustomerPaymentPlanInfo4ph(String customerId, Connection conn) throws Exception;
    public List<PaymentPlanVO> listPaymentPlanVOByDate(String beginTime, String endTime, Connection conn) throws Exception;
    public PaymentPlanVO loadPaymentPlanVO(String paymentPlanId, Connection conn) throws Exception;
    public Pager listPagePaymentInfo(PaymentPlanVO paymentPlanVO, String productionId, int currentPage, int showRowCount, Connection conn) throws Exception;
    public List<PaymentPlanPO> listPaymentPlanPO(String paymentPlanDate, Connection conn) throws Exception;
    public PaymentPlanCheckPO loadPaymentPlanCheckPO(String paymentPlanDate, Connection conn) throws Exception;
    public Pager listPagerPaymentPlanVO(PaymentPlanVO paymentPlanVO, List<KVObject> conditions, int currentPage, int showRowCount, Connection conn) throws Exception;
    public void setPaymentPlanAccepted(String paymentPlanId, UserPO user, Connection conn) throws Exception;
    public double getCustomerTotalProfitMoney(String customerId, Connection conn) throws Exception;
    public Pager getPaymentPlansVOByDateAndStatus(String begin, String end, int currentPage, int showRowCount, Connection conn) throws Exception;
    public List<PaymentPlanPO> getPaymentPlansByOrderId(String orderId, Connection conn) throws Exception;
    public List<PaymentPlanVO> listPaymentPlanVOByOrderId(String orderId, Connection conn) throws Exception;
    public Pager getCapitalPre(PaymentPlanVO paymentPlanVO, String startTime, String endTime, String departments, Connection conn) throws Exception;

    List<PaymentPlanVO> getCapitalPreDetail(String startTime, String endTime, Connection connection) throws Exception;
}
