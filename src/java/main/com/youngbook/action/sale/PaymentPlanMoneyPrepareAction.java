package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.IJsonable;
import com.youngbook.common.Pager;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.MoneyUtils;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.entity.po.sale.PaymentPlanMoneyPreparePO;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.po.sale.PaymentPlanStatus;
import com.youngbook.entity.vo.Sale.PaymentPlanMoneyPrepareVO;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.service.sale.PaymentPlanMoneyPrepareService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 * 资金准备Action
 * Created by yux on 2016/6/14.
 */
public class PaymentPlanMoneyPrepareAction extends BaseAction {

    private PaymentPlanPO paymentPlanPO=new PaymentPlanPO();
    private PaymentPlanVO paymentPlanVO=new PaymentPlanVO();
    private PaymentPlanMoneyPreparePO paymentPlanMoneyPreparePO=new PaymentPlanMoneyPreparePO();
    private PaymentPlanMoneyPrepareVO paymentPlanMoneyPrepareVO=new PaymentPlanMoneyPrepareVO();
    @Autowired
    PaymentPlanMoneyPrepareService paymentPlanMoneyPrepareService;

    public String list() throws Exception{
        Connection conn = getConnection();
        //根据条件查询数据
        // List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, PaymentPlanVO.class);
        String startTime = HttpUtils.getParameter(getRequest(), "paymentPlanVO_paymentTime_Start");
        String endTime = HttpUtils.getParameter(getRequest(), "paymentPlanVO_paymentTime_End");
        //将数据封装起来 到一个分页的类里面
        Pager pager = paymentPlanMoneyPrepareService.list(paymentPlanVO, startTime, endTime, null, conn);
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



    public String insertOrUpdate() throws Exception{
        //将前台传递的数据保存到数据库中，并且与工作流表连接起来
        paymentPlanMoneyPrepareService.insertOrUpdate(paymentPlanMoneyPreparePO,paymentPlanMoneyPrepareVO,getLoginUser(),getConnection());
        return  SUCCESS;
    }

    public String getCapitalPreDetail() throws Exception{

        String startTime= HttpUtils.getParameter(getRequest(), "startTime");
        String endTime= HttpUtils.getParameter(getRequest(), "endTime");
        paymentPlanMoneyPrepareVO.setCapitalPreStartTime(startTime);
        paymentPlanMoneyPrepareVO.setCapitalPreEndTime(endTime);
        //为capitalPreVO设置时间段的值（startTime+"至"+endTime）
        paymentPlanMoneyPrepareVO.setCapitalPreTimes(startTime+" 至 "+endTime);
        //通过开始时间和结束时间来获取资金准备的详细信息
        List<PaymentPlanVO> paymentPlanVOs = paymentPlanMoneyPrepareService.getCapitalPreDetail(startTime,endTime, Config.getConnection());
        //通过获取到的详细信息来统计总金额
        double totalMoney = 0;
        for (PaymentPlanVO vo : paymentPlanVOs) {
            double formatTotalProfitMoney = MoneyUtils.format2Fen(vo.getTotalProfitMoney());
            vo.setTotalProfitMoney(formatTotalProfitMoney);
            totalMoney += formatTotalProfitMoney;
        }
        paymentPlanMoneyPrepareVO.setMoney(totalMoney);

        //将数据传递给前台
        JSONArray array = new JSONArray ();
        JSONObject json = new JSONObject ();
        json.element ("capitalPreVO", paymentPlanMoneyPrepareVO.toJsonObject4Form());
        json.element ("paymentPlanVOs", JSONDao.getArray(paymentPlanVOs));
        array.add (json);

        ReturnObject result = new ReturnObject();
        result.setCode(ReturnObject.CODE_SUCCESS);
        result.setMessage("操作成功");
        result.setReturnValue(array);

        setResult(result);

        return SUCCESS;
    }

    public String capitalPreApproval(){

        return null;
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

    public PaymentPlanMoneyPreparePO getPaymentPlanMoneyPreparePO() {
        return paymentPlanMoneyPreparePO;
    }

    public void setPaymentPlanMoneyPreparePO(PaymentPlanMoneyPreparePO paymentPlanMoneyPreparePO) {
        this.paymentPlanMoneyPreparePO = paymentPlanMoneyPreparePO;
    }

    public PaymentPlanMoneyPrepareVO getPaymentPlanMoneyPrepareVO() {
        return paymentPlanMoneyPrepareVO;
    }

    public void setPaymentPlanMoneyPrepareVO(PaymentPlanMoneyPrepareVO paymentPlanMoneyPrepareVO) {
        this.paymentPlanMoneyPrepareVO = paymentPlanMoneyPrepareVO;
    }

    public PaymentPlanMoneyPrepareService getPaymentPlanMoneyPrepareService() {
        return paymentPlanMoneyPrepareService;
    }

    public void setPaymentPlanMoneyPrepareService(PaymentPlanMoneyPrepareService paymentPlanMoneyPrepareService) {
        this.paymentPlanMoneyPrepareService = paymentPlanMoneyPrepareService;
    }
}
