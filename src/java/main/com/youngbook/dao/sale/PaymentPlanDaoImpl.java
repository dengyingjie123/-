package com.youngbook.dao.sale;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.PaymentPlanCheckPO;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.po.sale.PaymentPlanStatus;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Component("paymentPlanDao")
public class PaymentPlanDaoImpl implements IPaymentPlanDao {


    public PaymentPlanVO getCustomerPaymentPlanInfo4ph(String customerId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("ABE81801");
        dbSQL.addParameter4All("customerId", customerId);
        dbSQL.initSQL();

        List<PaymentPlanVO> list = MySQLDao.search(dbSQL, PaymentPlanVO.class, conn);

        if (list != null && list.size() == 1) {

            PaymentPlanVO paymentPlanVO = list.get(0);
            paymentPlanVO.setCustomerId(customerId);

            return paymentPlanVO;
        }

        return null;
    }


    public PaymentPlanVO loadPaymentPlanVO(String paymentPlanId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("loadPaymentPlanVO", this);
        dbSQL.addParameter4All("paymentPlanId", paymentPlanId);
        dbSQL.initSQL();

        List<PaymentPlanVO> list = MySQLDao.search(dbSQL, PaymentPlanVO.class, conn);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public Pager listPagePaymentInfo(PaymentPlanVO paymentPlanVO, String productionId, int currentPage, int showRowCount, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listPagePaymentInfo", this);
        dbSQL.addParameter4All("productionId", productionId);
        dbSQL.initSQL();

        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), paymentPlanVO, null, currentPage, showRowCount, queryType, conn);



        List<IJsonable> data = pager.getData();
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        for (IJsonable dataJson : data) {
            double totalPaymentMoney = ((PaymentPlanVO) dataJson).getTotalPaymentMoney();
            double tPaymentMoney = Double.valueOf(dcmFmt.format(totalPaymentMoney));
            ((PaymentPlanVO) dataJson).setTotalPaymentMoney(tPaymentMoney);

            double totalProfitMoney = ((PaymentPlanVO) dataJson).getTotalProfitMoney();
            double tTotalProfitMoney = Double.valueOf(dcmFmt.format(totalProfitMoney));
            ((PaymentPlanVO) dataJson).setTotalProfitMoney(tTotalProfitMoney);
        }
        pager.setData(data);
        return pager;
    }

    public List<PaymentPlanPO> listPaymentPlanPO(String paymentPlanDate, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listPaymentPlanPO", this);
        dbSQL.addParameter4All("paymentPlanDate", paymentPlanDate);
        dbSQL.initSQL();

        List<PaymentPlanPO> list = MySQLDao.search(dbSQL, PaymentPlanPO.class, conn);

        return list;
    }


    public PaymentPlanCheckPO loadPaymentPlanCheckPO(String paymentPlanDate, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("loadPaymentPlanCheckPO", this);
        dbSQL.addParameter4All("paymentPlanDate", paymentPlanDate);
        dbSQL.initSQL();

        List<PaymentPlanCheckPO> list = MySQLDao.search(dbSQL, PaymentPlanCheckPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 列出数据
     *
     * @param paymentPlanVO
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager listPagerPaymentPlanVO(PaymentPlanVO paymentPlanVO, List<KVObject> conditions, int currentPage, int showRowCount, Connection conn) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listPagerPaymentPlanVO", this);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), paymentPlanVO, conditions, currentPage, showRowCount, queryType, conn);

        List<IJsonable> data = pager.getData();

        for (IJsonable dataJson : data) {
            dealWithPaymentPlanVO((PaymentPlanVO)dataJson);
        }
        pager.setData(data);
        return pager;
    }




    private void dealWithPaymentPlanVO(PaymentPlanVO data) {

        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        double totalPaymentMoney = data.getTotalPaymentMoney(); //应兑付总金额
        double tPaymentMoney = Double.valueOf(dcmFmt.format(totalPaymentMoney));
        data.setTotalPaymentMoney(tPaymentMoney);

        double totalProfitMoney = data.getTotalProfitMoney(); //应兑付收益总金额
        double tTotalProfitMoney = Double.valueOf(dcmFmt.format(totalProfitMoney));
        data.setTotalProfitMoney(tTotalProfitMoney);
    }


    /**
     * 设置兑付计划状态为已接受
     * 等待后台扫描以后，返回最终状态
     *
     * @param paymentPlanId
     * @param conn
     */
    public void setPaymentPlanAccepted(String paymentPlanId, UserPO user, Connection conn) throws Exception {
        String acceptedStatusId = Config.getSystemVariable("paymentPlan.status.Apply");

        PaymentPlanPO paymentPlanPO = new PaymentPlanPO();
        paymentPlanPO.setId(paymentPlanId);
        paymentPlanPO.setState(Config.STATE_CURRENT);
        paymentPlanPO = MySQLDao.load(paymentPlanPO, PaymentPlanPO.class, conn);

        paymentPlanPO.setStatus(Config.getSystemVariableAsInt("paymentPlan.status.accepted"));
        paymentPlanPO.setPaiedPaymentTime(TimeUtils.getNow());

        MySQLDao.insertOrUpdate(paymentPlanPO, user.getId(), conn);

    }

    /**
     * 获得客户目前总收益，不包含已兑付部分
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public double getCustomerTotalProfitMoney(String customerId, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append(" SELECT");
        sbSQL.append("     SUM(pp.TotalProfitMoney) TotalProfitMoney");
        sbSQL.append(" FROM");
        sbSQL.append("     core_paymentplan pp");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND pp.state = 0");
        sbSQL.append(" AND pp.CustomerId = '" + customerId + "'");
        sbSQL.append(" and pp.`Status` " +
                "in (" + PaymentPlanStatus.Unpaid + ", " + PaymentPlanStatus.Waiting4Pay + ", " + PaymentPlanStatus.Auditing + ", " + PaymentPlanStatus.AuditSuccess + ", " + PaymentPlanStatus.AuditFailure + ")");

        List<PaymentPlanPO> totolPaymentPlan = MySQLDao.query(sbSQL.toString(), PaymentPlanPO.class, new ArrayList<KVObject>(), conn);

        if (totolPaymentPlan != null && totolPaymentPlan.size() == 1) {
            return totolPaymentPlan.get(0).getTotalProfitMoney();
        }

        return 0.00;
    }

    /**
     * 通过起止时间查询兑付计划
     * @param beginTime
     * @param endTime
     * @param conn
     * @return
     * @throws Exception
     */
    public List<PaymentPlanVO> listPaymentPlanVOByDate(String beginTime, String endTime, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listPaymentPlanVOByDate", this);
        dbSQL.addParameter4All("beginTime", beginTime);
        dbSQL.addParameter4All("endTime", endTime);
        dbSQL.initSQL();

        List<PaymentPlanVO> list = MySQLDao.search(dbSQL, PaymentPlanVO.class, conn);

        return list;
    }


    public Pager getPaymentPlansVOByDateAndStatus(String begin, String end, int currentPage, int showRowCount, Connection conn) throws Exception {

        PaymentPlanVO paymentPlanVO = new PaymentPlanVO();

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getPaymentPlansVOByDateAndStatus", "PaymentPlanDaoImplSQL", this.getClass());
        dbSQL.addParameter4All("begin", begin).addParameter4All("end", end);
        dbSQL.initSQL();


        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), paymentPlanVO, null, currentPage, showRowCount, queryType, conn);
        return pager;
    }


    /**
     * 产品详情页面订单、兑付信息
     *
     *
     * @throws Exception
     */
    public List<PaymentPlanPO> getPaymentPlansByOrderId(String orderId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getPaymentPlansByOrderId", this);
        dbSQL.addParameter4All("orderId", orderId);
        dbSQL.initSQL();

        List<PaymentPlanPO> paymentPlans = MySQLDao.search(dbSQL, PaymentPlanPO.class, conn);

        return paymentPlans;
    }

    public List<PaymentPlanVO> listPaymentPlanVOByOrderId(String orderId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listPaymentPlanVOByOrderId", "PaymentPlanDaoImplSQL", PaymentPlanDaoImpl.class);
        dbSQL.addParameter4All("orderId", orderId);
        dbSQL.initSQL();

        List<PaymentPlanVO> paymentPlans = MySQLDao.search(dbSQL, PaymentPlanVO.class, conn);

        return paymentPlans;
    }

    @Override
    public Pager getCapitalPre(PaymentPlanVO paymentPlanVO, String startTime, String endTime, String departments, Connection conn) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        DatabaseSQL dbSQL=new DatabaseSQL();
        dbSQL.newSQL("listCapitalPre","PaymentPlanDaoImplSQL",this.getClass());
        dbSQL.addParameter4All("paymentPlanStatus", paymentPlanVO.getSearch_status())
                .addParameter4All("productionName", paymentPlanVO.getProductionName())
                .addParameter4All("startTime", startTime)
                .addParameter4All("endTime", endTime);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_");

        //查询数据
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), PaymentPlanVO.class, request, conn);

        return pager;
    }

    @Override
    public List<PaymentPlanVO> getCapitalPreDetail(String startTime, String endTime, Connection conn) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("capitalPreDetail", "PaymentPlanDaoImplSQL", this.getClass());
        dbSQL.addParameter4All("startTime", startTime)
                .addParameter4All("endTime", endTime);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_");
        //查询数据
        List<PaymentPlanVO> paymentPlanVOs = MySQLDao.search(sbSQL.toString(),dbSQL.getParameters(),PaymentPlanVO.class,null,conn);
        return paymentPlanVOs;
    }


    @Override
    public List<PaymentPlanVO> getListPaymentPlanVO(String paymentTime,Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("822E1801");
        dbSQL.addParameter4All("paymentTime", paymentTime);
        dbSQL.initSQL();

        List<PaymentPlanVO> list = MySQLDao.search(dbSQL, PaymentPlanVO.class, conn);

        return list;
    }
}
