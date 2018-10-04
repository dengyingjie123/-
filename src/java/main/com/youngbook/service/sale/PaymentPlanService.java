package com.youngbook.service.sale;

import com.aipg.transquery.QTDetail;
import com.youngbook.annotation.*;
import com.youngbook.command.CommandExecutor;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.console.ConsoleTable;
import com.youngbook.common.wf.admin.Action;
import com.youngbook.common.wf.admin.RouteList;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.common.wf.services.IBizService;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.CustomerPersonalDaoImpl;
import com.youngbook.dao.customer.ICustomerAccountDao;
import com.youngbook.dao.customer.ICustomerMoneyLogDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.dao.sale.IPaymentPlanDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentDetailPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentStatus;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentType;
import com.youngbook.entity.po.core.OrderPayPO;
import com.youngbook.entity.po.core.TransferPO;
import com.youngbook.entity.po.core.TransferTargetType;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.sale.PaymentPlanCheckPO;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.po.sale.PaymentPlanStatus;
import com.youngbook.entity.po.sale.ProductionFoWorkflowPO;
import com.youngbook.entity.po.wf.BizRoutePO;
import com.youngbook.entity.vo.Sale.PaymentPlanTempVO;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.entity.vo.customer.CustomerVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.entity.vo.report.PaymentPlanReportVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.allinpay.AllinpayBatchPaymentDetailService;
import com.youngbook.service.allinpay.AllinpayBatchPaymentService;
import com.youngbook.service.core.IMoneyTransferService;
import com.youngbook.service.customer.CustomerAccountService;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.customer.CustomerWithdrawService;
import com.youngbook.service.pay.FuiouDirectService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.system.LogService;
import com.youngbook.service.wf.BizRouteService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Service
 * 兑付计划操作对象
 * Created by Administrator on 2015/3/25.
 */
@Component("paymentPlanService")
public class PaymentPlanService extends BaseService implements IBizService {

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    IPaymentPlanDao paymentPlanDao;

    @Autowired
    OrderService orderService;


    @Autowired
    ICustomerAccountDao customerAccountDao;

    @Autowired
    IAllinpayCircleDao allinpayCircleDao;

    @Autowired
    IProductionDao productionDao;

    @Autowired
    ICustomerMoneyLogDao customerMoneyLogDao;

    /**
     * 通联万小宝
     * 单笔还款-机构引入
     * @param customerId
     * @param accountId
     * @param paymentPlanId
     * @param operatorId
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject allinpayCircle_Payback(String customerId, String accountId, String paymentPlanId, String operatorId, Connection conn) throws Exception {

        String url = "";

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);


        PaymentPlanVO paymentPlanVO = paymentPlanDao.loadPaymentPlanVO(paymentPlanId, conn);

        ProductionPO productionPO = productionDao.getProductionById(paymentPlanVO.getProductId(), conn);

        double money = paymentPlanVO.getTotalPaymentPrincipalMoney() + paymentPlanVO.getTotalProfitMoney();

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("2294");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", customerPersonalPO.getAllinpayCircle_SignNum());
        transactionPO.getRequest().addItem("repayment_type", "0");
        transactionPO.getRequest().addItem("pay_mode", "0");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("amt_tran", MoneyUtils.format2Fen(money));
        transactionPO.getRequest().addItem("prod_import_flag", "0");
        transactionPO.getRequest().addItem("product_num", productionPO.getAllinpayCircle_ProductNum());
        transactionPO.getRequest().addItem("resp_url", url);


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        if (returnObject != null) {
            if (returnObject.getCode() == 100) {

                String jsonString = returnObject.getReturnValue().toString();

                if (!StringUtils.isEmpty(jsonString)) {
                    KVObjects kvObjects = JSONDao.toKVObjects(jsonString);

                    XmlHelper helper = new XmlHelper(kvObjects.getItemString("responseXml"));

//                    String signNum = helper.getValue("/transaction/response/sign_num");

                }
            }
        }

        return returnObject;
    }

    @Override
    public void afterOver(BizRoutePO bizdao, RouteList routeList, Action worlkflow, Connection conn) throws Exception {

    }

    public double getCustomerTotalProfitMoney(String customerId, Connection conn) throws Exception {
        return paymentPlanDao.getCustomerTotalProfitMoney(customerId, conn);
    }

    public void confirm(String orderId, String confirmUserId, Connection conn) throws Exception {

        List<PaymentPlanPO> list = paymentPlanDao.getPaymentPlansByOrderId(orderId, conn);

        for (int i = 0; list != null && i < list.size(); i++) {
            PaymentPlanPO paymentPlanPO = list.get(i);

            paymentPlanPO.setConfirmorId(confirmUserId);
            paymentPlanPO.setConfirmTime(TimeUtils.getNow());

            MySQLDao.insertOrUpdate(paymentPlanPO, conn);
        }

    }

    public List<PaymentPlanPO> getPaymentPlansByOrderId(String orderId, Connection conn) throws Exception {
        return paymentPlanDao.getPaymentPlansByOrderId(orderId, conn);
    }

    public List<PaymentPlanVO> listPaymentPlanVOByOrderId(String orderId, Connection conn) throws Exception {
        return paymentPlanDao.listPaymentPlanVOByOrderId(orderId, conn);
    }


    public PaymentPlanVO getCustomerPaymentPlanInfo(String customerId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getCustomerPaymentPlanInfo", this);
        dbSQL.addParameter4All("customerId", customerId);
        dbSQL.initSQL();

        List<PaymentPlanVO> list = MySQLDao.search(dbSQL, PaymentPlanVO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public PaymentPlanVO getCustomerPaymentPlanInfo4ph(String customerId, Connection conn) throws Exception {
        return paymentPlanDao.getCustomerPaymentPlanInfo4ph(customerId, conn);
    }


    /**
     * HOPEWEALTH-1337
     * 获取兑付计划报表
     * @param time
     * @return
     * @throws Exception
     */
    public List<PaymentPlanReportVO> getPaymentPlanMonthlyReport(String time) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" 	pay.Sid AS sid,");
        sb.append(" 	pay.Id AS id,");
        sb.append(" 	odr.id AS orderId,");
        sb.append(" 	odr.ProductionId AS productionId,");
        sb.append(" 	odr.ProductionCompositionId AS compositionId,");
        sb.append(" 	cst.id AS customerId,");
        sb.append(" 	ctf.Number AS customerNumber,");
        sb.append(" 	cst.`Name` AS customerName,");
        sb.append(" 	acc.Bank AS bank,");
        sb.append(" 	acc.Number AS bankNumber,");
        sb.append(" 	prd.`Name` AS productionName,");
        sb.append(" 	odr.Money AS money,");
        sb.append(" 	CONCAT(cps.ExpectedYield, '%') AS expectedYield,");
        sb.append(" 	DATE_FORMAT(odr.PayTime, \"%Y/%m/%d\") AS paytime,");
        sb.append(" 	DATE_FORMAT(odr.valueDate, \"%Y/%m/%d\") AS valueDate,");
        sb.append(" 	DATE_FORMAT(pay.PaymentTime, \"%Y/%m/%d\") AS paymentTime,");
        sb.append(" 	FORMAT(");
        sb.append(" 		pay.TotalPaymentPrincipalMoney,");
        sb.append(" 		2");
        sb.append(" 	) AS principalMoney,");
        sb.append(" 	FORMAT(pay.TotalProfitMoney, 2) AS profitMoney,");
        sb.append(" 	CASE pay.`Status`");
        sb.append(" WHEN 0 THEN");
        sb.append(" 	'未兑付'");
        sb.append(" WHEN 1 THEN");
        sb.append(" 	'已兑付'");
        sb.append(" WHEN 2 THEN");
        sb.append(" 	'兑付失败'");
        sb.append(" WHEN 3 THEN");
        sb.append(" 	'取消兑付'");
        sb.append(" WHEN 4 THEN");
        sb.append(" 	'已转让'");
        sb.append(" WHEN 5 THEN");
        sb.append(" 	'已受理'");
        sb.append(" WHEN 6 THEN");
        sb.append(" 	'等待兑付'");
        sb.append(" WHEN 7 THEN");
        sb.append(" 	'审核中'");
        sb.append(" WHEN 8 THEN");
        sb.append(" 	'审核成功'");
        sb.append(" WHEN 9 THEN");
        sb.append(" 	'审核失败'");
        sb.append(" END AS paymentStatus");
        sb.append(" FROM");
        sb.append(" 	core_paymentplan AS pay,");
        sb.append(" 	crm_order AS odr,");
        sb.append(" 	crm_customerpersonal AS cst,");
        sb.append(" 	crm_customeraccount AS acc,");
        sb.append(" 	crm_customercertificate AS ctf,");
        sb.append(" 	crm_production AS prd,");
        sb.append(" 	crm_productioncomposition AS cps");
        sb.append(" WHERE");
        sb.append(" 	1 = 1");
        sb.append(" AND pay.OrderId = odr.id");
        sb.append(" AND odr.CustomerId = cst.id");
        sb.append(" AND acc.CustomerId = cst.id");
        sb.append(" AND ctf.CustomerId = cst.id");
        sb.append(" AND odr.ProductionId = prd.id");
        sb.append(" AND odr.ProductionCompositionId = cps.id");
        sb.append(" AND pay.State = 0");
        sb.append(" AND odr.State = 0");
        sb.append(" AND cst.state = 0");
        sb.append(" AND acc.state = 0");
        sb.append(" AND ctf.state = 0");
        sb.append(" AND prd.state = 0");
        sb.append(" AND cps.state = 0");
        sb.append(" AND pay.TotalPaymentPrincipalMoney > 100.0"); // 舍去测试数据
        if (!StringUtils.isEmpty(time)) {
            // 加入兑付时间条件
            if (time.length() == 4) {
                sb.append(" AND DATE_FORMAT(pay.PaymentTime, \"%Y\") = '" + time + "'");
            }
            if (time.length() == 6) {
                sb.append(" AND DATE_FORMAT(pay.PaymentTime, \"%Y%m\") = '" + time + "'");
            }
        }
        sb.append(" ORDER BY");
//        sb.append(" 	odr.PayTime,");
        sb.append(" 	pay.PaymentTime");

        List<PaymentPlanReportVO> list = MySQLDao.search(sb.toString(), new ArrayList<KVObject>(), PaymentPlanReportVO.class, null);

        return list;
    }

    public int check2(String paymentPlanDate, UserPO checkUser2, Connection conn) throws Exception {

        /**
         * 查询并更新之前的申请记录
         */
        if (StringUtils.isEmpty(paymentPlanDate)) {
            MyException.newInstance("输入日期格式为空").throwException();
        }


        PaymentPlanCheckPO paymentPlanCheckPO = paymentPlanDao.loadPaymentPlanCheckPO(paymentPlanDate, conn);

        if (paymentPlanCheckPO == null) {
            MyException.newInstance("无法找到兑付计划审核记录", "paymentPlanDate=" + paymentPlanDate).throwException();
        }

        if (StringUtils.isEmpty(paymentPlanCheckPO.getCheckId())) {
            MyException.newInstance("该月兑付计划审核申请尚未提交，请检查！", "paymentPlanDate=" + paymentPlanDate).throwException();
        }

        String now = TimeUtils.getNow();

        paymentPlanCheckPO.setCheckId2(checkUser2.getId());
        paymentPlanCheckPO.setCheckName2(checkUser2.getName());
        paymentPlanCheckPO.setCheckTime2(now);

        MySQLDao.insertOrUpdate(paymentPlanCheckPO, conn);

        /**
         * 修改兑付计划明细的申请记录
         */
        List<PaymentPlanPO> listPaymentPO = paymentPlanDao.listPaymentPlanPO(paymentPlanDate, conn);
        for (int i = 0; listPaymentPO != null && i < listPaymentPO.size(); i++) {
            PaymentPlanPO paymentPlanPO = listPaymentPO.get(i);
            paymentPlanPO.setStatus(8);
            paymentPlanPO.setAuditExecutorId(checkUser2.getId());
            paymentPlanPO.setConfirmTime(now);

            MySQLDao.insertOrUpdate(paymentPlanPO, conn);
        }

        return 1;
    }


    /**
     * 提交兑付计划审核申请
     * @param paymentPlanDate 兑付月份，例如 2017-03
     * @param conn
     * @return
     * @throws Exception
     */
    public int check(String paymentPlanDate, UserPO checkUser, Connection conn) throws Exception {


        /**
         * 查询并更新之前的申请记录
         */
        if (StringUtils.isEmpty(paymentPlanDate)) {
            MyException.newInstance("输入日期格式为空").throwException();
        }


        PaymentPlanCheckPO paymentPlanCheckPO = paymentPlanDao.loadPaymentPlanCheckPO(paymentPlanDate, conn);

        if (paymentPlanCheckPO == null) {
            paymentPlanCheckPO = new PaymentPlanCheckPO();
            paymentPlanCheckPO.setPaymentPlanDate(paymentPlanDate + "-01");
        }


        paymentPlanCheckPO.setCheckId(checkUser.getId());
        paymentPlanCheckPO.setCheckName(checkUser.getName());
        paymentPlanCheckPO.setCheckTime(TimeUtils.getNow());
        paymentPlanCheckPO.setCheckId2("");
        paymentPlanCheckPO.setCheckName2("");
        paymentPlanCheckPO.setCheckTime2(null);

        MySQLDao.insertOrUpdate(paymentPlanCheckPO, conn);


        /**
         * 修改兑付计划明细的申请记录
         */
//        List<PaymentPlanPO> listPaymentPO = paymentPlanDao.listPaymentPlanPO(paymentPlanDate, 0, conn);
//        for (int i = 0; listPaymentPO != null && i < listPaymentPO.size(); i++) {
//            PaymentPlanPO paymentPlanPO = listPaymentPO.get(i);
//            paymentPlanPO.setStatus(7);
////            paymentPlanPO.set
//        }


        return 1;
    }


    /**
     * 附件列表
     *
     * @param paymentPlanVO
     * @return
     * @throws Exception
     */
    public Pager getList2(PaymentPlanVO paymentPlanVO, String startTime, String endTime,  Connection conn) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getReport01", "PaymentPlanServiceSQL", PaymentPlanService.class);
        dbSQL.addParameter4All("paymentPlanStatus", paymentPlanVO.getSearch_status())
                .addParameter4All("startTime", startTime)
                .addParameter4All("endTime", endTime);
        dbSQL.initSQL();
        dbSQL.init4Pager();

        //查询数据
        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), PaymentPlanVO.class, request, conn);

        return pager;
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

        return paymentPlanDao.listPagerPaymentPlanVO(paymentPlanVO, conditions, currentPage, showRowCount, conn);

    }



    /**
     * 创建人：张舜清
     * 时间：2015年9月15日15:50:22
     * 内容：根据兑付数据的客户id查询出数据列表
     *
     * @param customerPersonalVO
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager customerPersonalQuery(CustomerPersonalVO customerPersonalVO, String customerId, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("    c.sid, ");
        sqlDB.append("    c.id, ");
        sqlDB.append("    c.`Name`, ");
        sqlDB.append("    c.LoginName, ");
        sqlDB.append("    kvSex.v Sex, ");
        sqlDB.append("    c.Mobile, ");
        sqlDB.append("    c.Birthday, ");
        sqlDB.append("    c.WorkAddress, ");
        sqlDB.append("    c.HomeAddress, ");
        sqlDB.append("    c.IdentityCardAddress, ");
        sqlDB.append("    c.Phone, ");
        sqlDB.append("    g.`Name` GroupName, ");
        sqlDB.append("    u.`name` SaleManName, ");
        sqlDB.append("    cd.`Status` DistributionStatus ");
        sqlDB.append(" FROM ");
        sqlDB.append("    crm_customerpersonal c, ");
        sqlDB.append("    crm_customerdistribution cd, ");
        sqlDB.append("    system_user u, ");
        sqlDB.append("    CRM_SaleMan sm, ");
        sqlDB.append("    crm_saleman_salemangroup sg, ");
        sqlDB.append("    CRM_SalemanGroup g, ");
        sqlDB.append("    system_kv kvSex ");
        sqlDB.append(" WHERE ");
        sqlDB.append("    1 = 1 ");
        sqlDB.append(" AND c.state = 0 ");
        sqlDB.append(" AND cd.state = 0 ");
        sqlDB.append(" AND u.state = 0 ");
        sqlDB.append(" AND sm.State = 0 ");
        sqlDB.append(" AND g.State = 0 ");
        sqlDB.append(" AND c.Sex = kvSex.K ");
        sqlDB.append(" AND cd.CustomerId = c.id ");
        sqlDB.append(" AND cd.SaleManId = sm.UserId ");
        sqlDB.append(" AND cd.departmentId = g.DepartmentId ");
        sqlDB.append(" AND sg.saleManGroupId = g.Id ");
        sqlDB.append(" AND cd.saleGroupId = g.Id ");
        sqlDB.append(" AND sg.saleManId = u.Id ");
        sqlDB.append(" AND sm.UserId = u.Id ");
        sqlDB.append(" AND c.id = '" + customerId + "' ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sqlDB.toString(), customerPersonalVO, conditions, request, queryType, conn);
        return pager;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月15日17:36:21
     * 内容：根据兑付的订单id查询对应订单数据列表
     *
     * @param orderVO
     * @param orderId
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager orderQuery(OrderVO orderVO, String orderId, HttpServletRequest request, Connection conn) throws Exception {
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("     O.id, ");
        sqlDB.append("     O.sid, ");
        sqlDB.append("     O.OrderNum, ");
        sqlDB.append("     C.LoginName, ");
        sqlDB.append("     C. NAME customerName, ");
        sqlDB.append("     P.`Name` productionName, ");
        sqlDB.append("     PC.`Name` productionCompositionName, ");
        sqlDB.append("     O.createTime, ");
        sqlDB.append("     O.money, ");
        sqlDB.append("     O.Description, ");
        sqlDB.append("     O. STATUS STATUS, ");
        sqlDB.append("     O.customerAttribute, ");
        sqlDB.append("     O.referralCode ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_order O ");
        sqlDB.append(" LEFT JOIN crm_customerpersonal C ON C.id = O.CustomerId ");
        sqlDB.append(" AND C.state = 0 ");
        sqlDB.append(" LEFT JOIN crm_production P ON P.id = O.ProductionId ");
        sqlDB.append(" AND P.state = 0 ");
        sqlDB.append(" LEFT JOIN crm_productioncomposition PC ON PC.id = O.ProductionCompositionId ");
        sqlDB.append(" AND PC.state = 0 ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     O.state = 0 ");
        sqlDB.append(" AND O.customerAttribute = 0 ");
        sqlDB.append(" AND o.id = '" + orderId + "' ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sqlDB.toString(), orderVO, null, request, queryType, conn);
        return pager;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月15日17:52:28
     * 内容：根据兑付产品的id查询出对应数据列表
     *
     * @param productionVO
     * @param productionId
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager productionQuery(ProductionVO productionVO, String productionId, HttpServletRequest request, Connection conn) throws Exception {
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("     product.sid, ");
        sqlDB.append("     product.id, ");
        sqlDB.append("     product.productHomeId, ");
        sqlDB.append("     product.InvestTerm, ");
        sqlDB.append("     product.InvestTermView, ");
        sqlDB.append("     product.paymentProcess, ");
        sqlDB.append("     product.appointmentProcess, ");
        sqlDB.append("     product.saleProcess, ");
        sqlDB.append("     product.productId, ");
        sqlDB.append("     product.size, ");
        sqlDB.append("     product.contractCopies, ");
        sqlDB.append("     product. NAME, ");
        sqlDB.append("     product.startTime, ");
        sqlDB.append("     product.stopTime, ");
        sqlDB.append("     product.ValueDate, ");
        sqlDB.append("     product.ExpiringDate, ");
        sqlDB.append("     product.InterestDate, ");
        sqlDB.append("     product.AppointmentMoney, ");
        sqlDB.append("     product.SaleMoney, ");
        sqlDB.append("     product.WebsiteDisplayName, ");
        sqlDB.append("     CASE STATUS ");
        sqlDB.append(" WHEN 0 THEN ");
        sqlDB.append("     '草稿' ");
        sqlDB.append(" WHEN 1 THEN ");
        sqlDB.append("     '发布待售' ");
        sqlDB.append(" WHEN 2 THEN ");
        sqlDB.append("     '在售' ");
        sqlDB.append(" WHEN 3 THEN ");
        sqlDB.append("     '暂停' ");
        sqlDB.append(" WHEN 4 THEN ");
        sqlDB.append("     '售完' ");
        sqlDB.append(" ELSE ");
        sqlDB.append("     '作废' ");
        sqlDB.append(" END STATUS, ");
        sqlDB.append("  product.interestType, ");
        sqlDB.append("  product.interestCycle, ");
        sqlDB.append("  CASE interestUnit ");
        sqlDB.append(" WHEN 0 THEN ");
        sqlDB.append("     '按日' ");
        sqlDB.append(" WHEN 1 THEN ");
        sqlDB.append("     '按月' ");
        sqlDB.append(" WHEN 2 THEN ");
        sqlDB.append("     '按年' ");
        sqlDB.append(" END interestUnit, ");
        sqlDB.append("  product.interestTimes, ");
        sqlDB.append("  home.ProductionName ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_production product, ");
        sqlDB.append("     crm_productionhome home ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1 ");
        sqlDB.append(" AND product.state = 0 ");
        sqlDB.append(" AND home.State = 0 ");
        sqlDB.append(" AND product.productHomeId = home.Id ");
        sqlDB.append(" AND product.id = '" + productionId + "'");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sqlDB.toString(), productionVO, null, request, queryType, conn);
        return pager;
    }


    public List<PaymentPlanVO> getListPaymentPlanVO(String paymentTime, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("reportMonth", this);
        dbSQL.addParameter4All("paymentTime", paymentTime);
        dbSQL.initSQL();

        List<PaymentPlanVO> list = MySQLDao.search(dbSQL, PaymentPlanVO.class, conn);

        for (int i = 0; list != null && i < list.size(); i++) {
            PaymentPlanVO paymentPlanVO = list.get(i);
            paymentPlanVO.setTotalPaymentMoney(paymentPlanVO.getTotalProfitMoney() + paymentPlanVO.getTotalPaymentPrincipalMoney());

            // 金额保留2位小数
            double profitMoney = NumberUtils.formatDouble(paymentPlanVO.getTotalProfitMoney(), 2);
            paymentPlanVO.setTotalProfitMoney(profitMoney);

            double principalMoney = NumberUtils.formatDouble(paymentPlanVO.getTotalPaymentPrincipalMoney(), 2);
            paymentPlanVO.setTotalPaymentPrincipalMoney(principalMoney);

            double totalMoney = NumberUtils.formatDouble(paymentPlanVO.getTotalPaymentMoney(), 2);
            paymentPlanVO.setTotalPaymentMoney(totalMoney);

            // 星期
            String time = paymentPlanVO.getPaymentTime();
            Date date = TimeUtils.getDate(time);
            String weekOfDay = TimeUtils.getWeekDayChinese(date);
            paymentPlanVO.setWeekOfDay(weekOfDay);

            // 收益方式描述
            StringBuffer interestDescription = new StringBuffer();
            if (paymentPlanVO.getInterestCycle() == 3) {
                interestDescription.append("季度");
            }
            else if (paymentPlanVO.getInterestCycle() == 6) {
                interestDescription.append("半年");
            }
            interestDescription.append("，( ");
            interestDescription.append(paymentPlanVO.getCurrentInstallment() + "/" + paymentPlanVO.getInterestTimes() + " )");

            paymentPlanVO.setInterestDescription(interestDescription.toString());
        }

        return list;
    }



    /**
     * 执行兑付计划
     * @param paymentPlanId
     * @param conn
     * @return
     * @throws Exception
     */
    public int doPayment(String paymentPlanId, Connection conn) throws Exception {


        if (customerPersonalDao == null) {
            customerPersonalDao = Config.getBeanByName("customerPersonalDao", CustomerPersonalDaoImpl.class);
        }

        if (StringUtils.isEmpty(paymentPlanId)) {
            MyException.newInstance("兑付计划编号无效").throwException();
        }

        // 查询对应的兑付计划
        String sql = "select * from core_paymentplan p where 1=1 AND p.state = 0 and p.id =? and p.status=?";
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sql);
        dbSQL.addParameter(1, paymentPlanId).addParameter(2, PaymentPlanStatus.AuditSuccess);
        List<PaymentPlanPO> paymentPlanPOs = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), PaymentPlanPO.class, new ArrayList<KVObject>(), conn);

        if (paymentPlanPOs == null || paymentPlanPOs.size() != 1) {
            MyException.newInstance("没有查询到该兑付计划").throwException();
        }

        PaymentPlanPO paymentPlanPO = paymentPlanPOs.get(0);


        /**
         * 兑付金额判定
         * 0： 全额兑付（默认）
         * 1： 仅兑付收益
         * 2： 仅兑付本金
         */
        String paymentType = CommandExecutor.getConsoleInput("兑付金额部分，0：全额兑付；1：仅兑付收益；2：仅兑付本金?");
        if (StringUtils.isEmpty(paymentType)) {
            paymentType = "0";
        }

        if (paymentType.equals("0")) {
            System.out.println("全额兑付");
        }
        else if (paymentType.equals("1")) {
            System.out.println("仅兑付收益");
        }
        else if (paymentType.equals("2")) {
            System.out.println("仅兑付本金");
        }

        if (!CommandExecutor.getConsoleInput("是否继续？ y/n").equalsIgnoreCase("y")) {
            MyException.newInstance("兑付取消").throwException();
        }

        // 转账信息
        TransferPO transferPO = new TransferPO();
        transferPO.setTargetType(TransferTargetType.Personal);

        //全额兑付
        if (paymentType.equals("0")) {
            double money = NumberUtils.getMoney2Fen(paymentPlanPO.getTotalPaymentMoney()) * 1.0 / 100;
            transferPO.setMoney(money);

        }
        // 仅兑付收益
        else if (paymentType.equals("1")) {
            double money = NumberUtils.getMoney2Fen(paymentPlanPO.getTotalProfitMoney()) * 1.0 / 100;
            transferPO.setMoney(money);

        }
        // 仅兑付本金
        else if (paymentType.equals("2")) {
            double money = NumberUtils.getMoney2Fen(paymentPlanPO.getTotalPaymentPrincipalMoney()) * 1.0 / 100;
            transferPO.setMoney(money);
        }


        /**
         * 获得支付账号
         */
        String accountName = "";
        String bankCode = "";
        String bankNumber = "";
        String bankBranchName = "";  // 开户行
        String cityCode = "";

        // 如果是手机端支付的订单，从OrderPay中获取账号信息
        DatabaseSQL dbSQLOrderPay = new DatabaseSQL();
        dbSQLOrderPay.newSQL("select * from core_orderpay op where 1=1 and op.state=0 and op.OrderId=?");
        dbSQLOrderPay.addParameter(1, paymentPlanPO.getOrderId());
        List<OrderPayPO> orderPayPOs = MySQLDao.search(dbSQLOrderPay.getSQL(), dbSQLOrderPay.getParameters(), OrderPayPO.class, null, conn);

        // 从OrderPay里构建转账信息
        if (orderPayPOs != null && orderPayPOs.size() == 1) {
            OrderPayPO orderPayPO = orderPayPOs.get(0);

            accountName = orderPayPO.getCustomerName();
            bankCode = orderPayPO.getBankCode();
            bankNumber = AesEncrypt.decrypt(orderPayPO.getBankAccount());

        }

        else {

            /**
             * 从订单里获得银行账号
             */
            CustomerAccountPO customerAccountPO = customerAccountDao.getCustomerAccountPO(paymentPlanPO.getOrderId(), conn);
            accountName = customerAccountPO.getName();
            bankCode = customerAccountPO.getBankCode();
            cityCode = customerAccountPO.getCityCode();
            bankBranchName = customerAccountPO.getBankBranchName();
            bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());

        }


        if (StringUtils.isEmptyAny(accountName, bankCode, bankNumber, cityCode, bankBranchName)) {
            MyException.newInstance("银行账号有误", "accountName="+accountName+"&bankCode"+bankCode+"&bankNumber="+bankNumber+"&bankBranchName="+bankBranchName+"&cityCode="+cityCode).throwException();
        }

        transferPO.setTargetAccountName(accountName);
        transferPO.setTargetAccountNo(bankNumber);
        transferPO.setTargetBank(bankCode);
        transferPO.setTargetBankBranchName(bankBranchName);
        transferPO.setTargetCityName(cityCode);


        List<TransferPO> transferPOs = new ArrayList<TransferPO>();

        // 如果金额大于20万，需要分笔支付
        double MAX_MONEY = 2000000.00;
        if (transferPO.getMoney() > MAX_MONEY) {

            double totalMoney = transferPO.getMoney();
            while (totalMoney > MAX_MONEY) {

                // 新创建一个兑付对象
                TransferPO newTransfer = new TransferPO();
                newTransfer.setTargetType(TransferTargetType.Personal);
                newTransfer.setTargetAccountName(transferPO.getTargetAccountName());
                newTransfer.setTargetAccountNo(transferPO.getTargetAccountNo());
                newTransfer.setTargetBank(transferPO.getTargetBank());
                transferPO.setTargetBankBranchName(bankBranchName);
                newTransfer.setTargetCityName(cityCode);

                newTransfer.setMoney(MAX_MONEY);
                transferPOs.add(newTransfer);
                totalMoney -= MAX_MONEY;
            }
            if (totalMoney > 0) {

                // 新创建一个兑付对象
                TransferPO newTransfer = new TransferPO();
                newTransfer.setTargetType(TransferTargetType.Personal);
                newTransfer.setTargetAccountName(transferPO.getTargetAccountName());
                newTransfer.setTargetAccountNo(transferPO.getTargetAccountNo());
                newTransfer.setTargetBank(transferPO.getTargetBank());
                transferPO.setTargetBankBranchName(bankBranchName);
                newTransfer.setTargetCityName(cityCode);

                newTransfer.setMoney(totalMoney);
                transferPOs.add(newTransfer);
            }
        }
        else {
            transferPOs.add(transferPO);
        }



        /**
         * 完成兑付计划数据构建
         *
         * 审核并继续
         *
         * Date: 2016-06-01 13:30:54
         * Author: leevits
         */
        this.printTransfers(transferPOs);

        if (!CommandExecutor.getConsoleInput("是否继续？ y/n").equalsIgnoreCase("y")) {
            MyException.newInstance("兑付取消").throwException();
        }


        IMoneyTransferService service = Config.getBeanByName("fuiouDirectService", FuiouDirectService.class);
        service.doTransfer(transferPOs, conn);


        /**
         * 兑付完成以后，修改各种状态
         */
        doPaymnetDone(paymentPlanPO, "0000", conn);


        return 1;
    }

    public PaymentPlanPO loadPaymentPlanPO(String paymentPlanId, Connection conn) throws Exception {

        PaymentPlanPO paymentPlanPO = new PaymentPlanPO();
        paymentPlanPO.setId(paymentPlanId);
        paymentPlanPO.setState(Config.STATE_CURRENT);

        paymentPlanPO = MySQLDao.load(paymentPlanPO, PaymentPlanPO.class, conn);

        return paymentPlanPO;
    }


    /**
     * 兑付完成以后，修改各种状态
     *
     * 此方法只修改各类状态，不执行资金转账操作
     *
     * @param paymentPlanPO
     * @param conn
     * @return
     * @throws Exception
     */
    public int doPaymnetDone(PaymentPlanPO paymentPlanPO, String userId, Connection conn) throws Exception {

        // 修改兑付计划状态
        paymentPlanPO.setStatus(PaymentPlanStatus.Paid);
        paymentPlanPO.setOperatorId(userId);
        paymentPlanPO.setPaiedPaymentTime(TimeUtils.getNow());
        paymentPlanPO.setPaiedPrincipalMoney(paymentPlanPO.getTotalPaymentPrincipalMoney());
        paymentPlanPO.setPaiedProfitMoney(paymentPlanPO.getTotalProfitMoney());

        int count = MySQLDao.insertOrUpdate(paymentPlanPO, conn);
        if (count != 1) {
            MyException.newInstance("修改兑付计划失败").throwException();
        }


        /**
         * 更新订单状态
         */
        int orderStatus = OrderStatus.Payback;
        if (paymentPlanPO.getCurrentInstallment() < paymentPlanPO.getTotalInstallment()) {
            orderStatus = OrderStatus.PaybackSomePart;
        }
        count = orderService.paybackOrder(paymentPlanPO.getOrderId(), paymentPlanPO.getPaiedPrincipalMoney(), orderStatus, paymentPlanPO.getPaiedPaymentTime(), "0000", conn);
        if (count != 1) {
            MyException.newInstance("保存订单信息失败").throwException();
        }



        // 新增客户资金日志

        // 查询对应的产品
        if (StringUtils.isEmpty(paymentPlanPO.getProductId())) {
            MyException.newInstance("兑付计划中没有关联的产品编号", "productionId=" + paymentPlanPO.getProductId()).throwException();
        }

        String productionSql = "select * from crm_production p where p.state = 0 and p.id = '" + paymentPlanPO.getProductId() + "'";
        List<ProductionPO> producitons = MySQLDao.query(productionSql, ProductionPO.class, new ArrayList<KVObject>(), conn);
        if (producitons == null || producitons.size() != 1) {
            MyException.newInstance("没有查询到对付计划关联的产品记录", "productionId=" + paymentPlanPO.getProductId()).throwException();
        }
        ProductionPO production = producitons.get(0);


        String content = "兑付[" + production.getName() + "]" + paymentPlanPO.getTotalPaymentMoney() + "元，兑付收益"+paymentPlanPO.getTotalProfitMoney()+"元";

        customerMoneyLogDao.newCustomerMoneyLog(paymentPlanPO.getTotalPaymentMoney(), paymentPlanPO.getTotalProfitMoney(), CustomerMoneyLogType.WithdrawOrPayment, content, "1", paymentPlanPO.getId(), paymentPlanPO.getCustomerId(), conn);

        CustomerMoneyLogPO moneyLog = new CustomerMoneyLogPO();
        moneyLog.setType(CustomerMoneyLogType.WithdrawOrPayment); // 兑付类型
        moneyLog.setContent("兑付[" + production.getName() + "]" + paymentPlanPO.getTotalPaymentMoney() + "元");
        moneyLog.setStatus(String.valueOf(Config4Status.CUSTOMER_MONEY_LOG_TYPE_SUCCESS)); // 兑付成功
        moneyLog.setCustomerId(paymentPlanPO.getCustomerId());
        moneyLog.setBizId(paymentPlanPO.getId());

        LogService.info("生成客户资金日志", this.getClass());
        LogService.info(moneyLog);

        count = MySQLDao.insertOrUpdate(moneyLog, conn);
        if (count != 1) {
            MyException.newInstance("保存客户资金日志失败").throwException();
        }

        return 1;
    }


    public void printTransfers(List<TransferPO> transferPOs) {
        ConsoleTable t = ConsoleTable.defaultTable(6);
        for (int i = 0; i < transferPOs.size(); i++) {
            TransferPO transfer = transferPOs.get(i);

            if (i == 0) {
                t.row();
                t.column("账户名").column("账号").column("银行").column("金额").column("城市代码").column("支行名称");
            }

            t.row();
            t.column(transfer.getTargetAccountName()).column(transfer.getTargetAccountNo())
                    .column(transfer.getTargetBank())
                    .column(transfer.getMoney()).column(transfer.getTargetCityName()).column(transfer.getTargetBankBranchName());
        }

        t.print();
    }


    /**
     * 执行兑付一条对付计划
     * <p/>
     * 修改客户的资金记录
     * 添加客户的资金日志
     * <p/>
     * 用法：在 PaymentPlanAction 中调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-7-19
     *
     * @param paymentId
     * @param user
     * @param conn
     * @return Boolean
     * @throws Exception
     * @author 邓超
     */
    public int payment(String paymentId, UserPO user, Connection conn) throws Exception {
        int flag = 0;

        try {

            // 查询对应的兑付计划
            String sql = "select * from core_paymentplan p where p.state = 0 and p.id = '" + paymentId + "'";
            List<PaymentPlanPO> plans = MySQLDao.query(sql, PaymentPlanPO.class, new ArrayList<KVObject>(), conn);
            if (plans.size() != 1) {
                throw new Exception("没有查询到该兑付计划");
            }

            PaymentPlanPO plan = plans.get(0);

            // 检测兑付计划的状态是否为未兑付
            int auditSuccess = PaymentPlanStatus.AuditSuccess;
            if (plan.getStatus() != auditSuccess) {
                throw new Exception("该记录的状态不能兑付");
            }

            // 查询对应的产品
            String productionSql = "select * from crm_production p where p.state = 0 and p.id = '" + plan.getProductId() + "'";
            List<ProductionPO> producitons = MySQLDao.query(productionSql, ProductionPO.class, new ArrayList<KVObject>(), conn);
            if (producitons == null || producitons.size() != 1) {
                throw new Exception("没有查询到对付计划关联的产品记录");
            }
            ProductionPO production = producitons.get(0);


            // 根据要兑付客户的id查询客户账号
            CustomerAccountPO customerAccount = customerAccountDao.getCustomerAccount(plan.getCustomerId(), conn);
            if (customerAccount == null) {
                // 该客户没有绑定银行卡 返回 1 标志
                return 1;
            }

            // 添加通联批量代付明细表
            AllinpayBatchPaymentDetailPO allinpayBatchPaymentDetail = new AllinpayBatchPaymentDetailPO();
            allinpayBatchPaymentDetail.setSn(StringUtils.buildNumberString(MySQLDao.getSequence("SN"), 4));
//            allinpayBatchPaymentDetail.setBank_code(Config4W.BANK_CODE);   // 招商的测试的银行代码 308
            allinpayBatchPaymentDetail.setAccount_type("00"); // 00 银行卡 ,01 存折
            allinpayBatchPaymentDetail.setAccount_no(customerAccount.getNumber());// 银行卡号
            allinpayBatchPaymentDetail.setAccount_name(customerAccount.getName());// 银行账户名字
            allinpayBatchPaymentDetail.setBank_name(customerAccount.getBankBranchName());// 开户名称
            allinpayBatchPaymentDetail.setAccount_pror(0); // 账户属性  0私人
            allinpayBatchPaymentDetail.setAmount((int) (plan.getTotalPaymentMoney() * 100));// 应兑付总金额，应兑付总金额乘 100 转换成分
            allinpayBatchPaymentDetail.setBizId(plan.getId());// 兑付的业务 id
            allinpayBatchPaymentDetail.setBizType(AllinpayBatchPaymentType.Payment);// 兑付类型
            allinpayBatchPaymentDetail.setStatus(Config4Status.STATUS_CUSTOMER_PAYMENT_UNFINISH); // 兑付未成功
            allinpayBatchPaymentDetail.setRevised(Config4Status.STATUS_ALLINPAY_REVISED_NULL); // 修正状态为初始的

            LogService.info("生成通联支付代付明细", this.getClass());
            LogService.info(allinpayBatchPaymentDetail);

            // 插入通联资金表
            AllinpayBatchPaymentDetailService detailService = new AllinpayBatchPaymentDetailService();
            detailService.insertAllinpaybatchPaymentDetail(allinpayBatchPaymentDetail, user, conn);


            // 新增客户资金日志
            CustomerMoneyLogPO moneyLog = new CustomerMoneyLogPO();
            moneyLog.setSid(MySQLDao.getMaxSid("crm_customerMoneyLog", conn));
            moneyLog.setId(plan.getId()); // 兑付业务 id
            moneyLog.setOperatorId(user.getId());
            moneyLog.setOperateTime(TimeUtils.getNow());
            moneyLog.setState(Config.STATE_CURRENT);
            moneyLog.setType(CustomerMoneyLogType.WithdrawOrPayment); // 兑付类型
            moneyLog.setContent("兑付[" + production.getName() + "]" + plan.getTotalPaymentMoney() + "元");
            moneyLog.setStatus(String.valueOf(Config4Status.CUSTOMER_MONEY_PAYMENT_UNFINISH)); // 兑付未成功
            moneyLog.setCustomerId(plan.getCustomerId());
            moneyLog.setBizId(plan.getId());

            LogService.info("生成客户资金日志", this.getClass());
            LogService.info(moneyLog);

            MySQLDao.insert(moneyLog, conn);

            // 将兑付状态改为等待兑付
            plan.setStatus(PaymentPlanStatus.Waiting4Pay);
            MySQLDao.insertOrUpdate(plan, user.getId(), conn);

            return flag;

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 创建人：姚章鹏
     * 时间：2015年8月22日17:16:35
     * 内容：处理兑付业务
     *
     * @param list
     * @param conn
     * @throws Exception
     */
    public void executionPaymentType(List<AllinpayBatchPaymentDetailPO> list, QTDetail detail, Connection conn) throws Exception {
        try {
            AllinpayBatchPaymentDetailService detailService = new AllinpayBatchPaymentDetailService();
            AllinpayBatchPaymentDetailPO temp = detailService.getCustomerAccountById(list.get(0).getBizId(), conn);

            LogService.info(temp);

            // 更改明细表中的业务类型 改为 1 兑付成功
            int tempCount = 0;
            temp.setState(Config.STATE_UPDATE);
            tempCount = MySQLDao.update(temp, conn);

            if (tempCount == 1) {

                temp.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentDetail"));
                temp.setState(Config.STATE_CURRENT);
                temp.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                temp.setOperateTime(TimeUtils.getNow());

                if ("0000".equals(detail.getRET_CODE())) {

                    temp.setStatus(AllinpayBatchPaymentStatus.SUCCESS);

                    // 查询对应的兑付计划
                    String sql = "select * from core_paymentplan p where p.state = 0 and p.id = '" + list.get(0).getBizId() + "'";
                    List<PaymentPlanPO> plans = MySQLDao.query(sql, PaymentPlanPO.class, new ArrayList<KVObject>(), conn);
                    if (plans.size() != 1) {
                        throw new Exception("没有查询到该兑付计划");
                    }
                    PaymentPlanPO plan = plans.get(0);

                    // 检测兑付计划的状态是否为等待兑付
                    if (plan.getStatus() != Integer.parseInt(Config.getSystemVariable("paymentPlan.status.unpaied"))) {
                        throw new Exception("该记录的状态不能兑付");
                    }

                    // 查询对应的产品
                    String productionSql = "select * from crm_production p where p.state = 0 and p.id = '" + plan.getProductId() + "'";
                    List<ProductionPO> producitons = MySQLDao.query(productionSql, ProductionPO.class, new ArrayList<KVObject>(), conn);
                    if (producitons.size() != 1) {
                        throw new Exception("没有查询到对付计划关联的产品记录");
                    }
                    ProductionPO production = producitons.get(0);

                    // 查询对应的客户资金记录
                    String customerId = plan.getCustomerId();   // 客户ID
                    String customerMoneySql = "select * from crm_customermoney m where m.state = 0 and m.customerId = '" + customerId + "'";
                    List<CustomerMoneyPO> moneyList = MySQLDao.query(customerMoneySql, CustomerMoneyPO.class, new ArrayList<KVObject>(), conn);
                    if (moneyList.size() != 1) {
                        throw new Exception("没有查询到兑付计划关联的客户资金记录");
                    }

                    // 原记录改为 Update 状态
                    Double totalPaymentMoney = plan.getTotalPaymentMoney(); // 当期应兑付总金额
                    CustomerMoneyPO customerMoney = moneyList.get(0);
                    customerMoney.setState(Config.STATE_UPDATE);
                    customerMoney.setOperateTime(TimeUtils.getNow());
                    MySQLDao.update(customerMoney, conn);

                    // 创建新的客户资金记录
                    customerMoney.setSid(MySQLDao.getMaxSid("crm_customermoney", conn));
                    customerMoney.setId(IdUtils.getUUID32());
                    customerMoney.setState(Config.STATE_CURRENT);
                    customerMoney.setOperateTime(TimeUtils.getNow());
                    customerMoney.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                    customerMoney.setAvailableMoney(customerMoney.getAvailableMoney() + totalPaymentMoney);
                    MySQLDao.insert(customerMoney, conn);

                    // 添加客户资金日志
                    CustomerMoneyLogPO moneyLog = new CustomerMoneyLogPO();
                    moneyLog.setSid(MySQLDao.getMaxSid("CRM_CustomerMoneyLog", conn));
                    moneyLog.setState(Config.STATE_CURRENT);
                    moneyLog.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                    moneyLog.setOperateTime(TimeUtils.getNow());
                    moneyLog.setType(CustomerMoneyLogType.WithdrawOrPayment); //兑付类型
                    moneyLog.setContent("兑付[" + production.getName() + "]" + totalPaymentMoney + "元");
                    moneyLog.setStatus(String.valueOf(Config4Status.CUSTOMER_MONEY_LOG_TYPE_SUCCESS));//兑付成功
                    moneyLog.setCustomerId(customerId);
                    MySQLDao.insert(moneyLog, conn);

                    // 修改原兑付计划为 Update 状态
                    customerMoney.setSid(MySQLDao.getMaxSid("core_paymentplan", conn));
                    plan.setOperateTime(TimeUtils.getNow());
                    plan.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                    plan.setState(Config.STATE_UPDATE);
                    MySQLDao.update(plan, conn);
                    // 创建已兑付的相同兑付计划
                    customerMoney.setSid(MySQLDao.getMaxSid("core_paymentplan", conn));
                    plan.setOperateTime(TimeUtils.getNow());
                    plan.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                    plan.setState(Config.STATE_CURRENT);
                    plan.setStatus(Integer.parseInt(Config.getSystemVariable("paymentPlan.status.paied")));

                    //修改"已兑付本金金额"和"已兑付收益金额"的数据
                    plan.setPaiedProfitMoney(plan.getTotalPaymentPrincipalMoney()); //修改已兑付本金金额
                    plan.setPaiedProfitMoney(plan.getTotalProfitMoney());// 修改 已兑付收益金额
                    plan.setPaiedPaymentTime(TimeUtils.getNow()); //已经兑付时间
                    MySQLDao.update(plan, conn);

                } else {
                    temp.setStatus(AllinpayBatchPaymentStatus.FAILED);
                    temp.setRevised(Config4Status.STATUS_ALLINPAY_REVISED_FAIL);     // 设为未修正状态，需要通联重新扫描
                }

                tempCount = MySQLDao.insert(temp, conn);

            }

        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * 产品详情页面订单、兑付信息
     *
     * @param paymentPlanVO
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager paymentplanOrderList(PaymentPlanVO paymentPlanVO, List<KVObject> conditions, String orderId) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //组装SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append("  SELECT DISTINCT cp.sid sid ,cp.id id ,cp.type TYPE,cc.Name customerName,cp.orderId orderId, ");
        sql.append("   cp2.Name productName,cp.paymentMoney paymentMoney,cp.paymentTime paymentTime,cp.totalInstallment totalInstallment, ");
        sql.append("   cp.currentInstallment currentInstallment,kv.v statusName ,cp.status search_status ,cp.status STATUS ,cp.totalPaymentMoney totalPaymentMoney, ");
        sql.append("   cp.totalPaymentPrincipalMoney totalPaymentPrincipalMoney , cp.totalProfitMoney totalProfitMoney, cp.paiedPrincipalMoney paiedPrincipalMoney, ");
        sql.append("   cp.paiedProfitMoney paiedProfitMoney,cp.comment, o.orderNum orderName  FROM core_paymentplan cp");
        sql.append("  LEFT JOIN crm_customerpersonal cc ON cc.State=0 and  cc.id = cp.CustomerId ");
        sql.append("  LEFT JOIN crm_production cp2 ON  cp2.state=0 and  cp2.id=cp.ProductId ");
        sql.append("  LEFT JOIN system_kv kv ON kv.GroupName ='Sale_PaymentPlan_Status' and kv.K=cp.Status");
        sql.append("  LEFT JOIN crm_order o ON o.state = 0 and o.id = cp.orderId ");
        sql.append("  WHERE 1=1 ");
        sql.append("   and cp.State=0 ");
        sql.append("   and cp.PaymentTime is not null ");

        if (!StringUtils.isEmpty(paymentPlanVO.getOrderId())) {
            sql.append("AND cp.orderId = '" + Database.encodeSQL(paymentPlanVO.getOrderId()) + "' ");
        }
        if (!StringUtils.isEmpty(orderId)) {
            sql.append("AND o.state=0 ");
            sql.append("AND o.id = '" + Database.encodeSQL(orderId) + "' ");
        }

        //查询数据
        Pager pager = Pager.query(sql.toString(), paymentPlanVO, conditions, request, queryType);

        //保留兑付总金额和收益总金额后两位
        List<IJsonable> data = pager.getData();
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        for (IJsonable dataJson : data) {
            double totalPaymentPrincipalMoney = ((PaymentPlanVO) dataJson).getTotalPaymentPrincipalMoney(); //应兑付本金总金额
            double tPaymentMoney = Double.valueOf(dcmFmt.format(totalPaymentPrincipalMoney));
            ((PaymentPlanVO) dataJson).setTotalPaymentMoney(tPaymentMoney);

            double totalProfitMoney = ((PaymentPlanVO) dataJson).getTotalProfitMoney(); //应兑付收益总金额
            double tTotalProfitMoney = Double.valueOf(dcmFmt.format(totalProfitMoney));
            ((PaymentPlanVO) dataJson).setTotalProfitMoney(tTotalProfitMoney);
        }
        pager.setData(data);

        return pager;
    }




    /**
     * 根据id获取审批信息
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public PaymentPlanVO getPaymentPlanYW(String id, UserPO user, Connection conn) throws Exception {
        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("  SELECT DISTINCT");
        sbSQL.append("  	rl.CURRENTNODE currentNodeId,");
        sbSQL.append("  	rl.id routeListId,");
        sbSQL.append("  	rl.STATU currentStatus,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("  	cp.*");
        sbSQL.append("  FROM");
        sbSQL.append("  	workflow_routelist rl,");
        sbSQL.append("  	core_paymentplan cp,");
        sbSQL.append("  	oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("  	1 = 1");
        sbSQL.append("  	AND  cp.state=" + Config.STATE_CURRENT);
        sbSQL.append("  AND ob.Id_YWID = cp.id");
        sbSQL.append("  AND rl.YWID = cp.id");
        sbSQL.append("  AND rl.STATU IN (1)");
        sbSQL.append("  	AND cp.id = '" + Database.encodeSQL(id) + "'");
        sbSQL.append("  UNION");
        sbSQL.append("  	SELECT DISTINCT");
        sbSQL.append("  		1 currentNodeId,");
        sbSQL.append("  		0 routeListId,");
        sbSQL.append("  		rl.STATU currentStatus,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("  		cp.*");
        sbSQL.append("  	FROM");
        sbSQL.append("  		workflow_routelist rl,");
        sbSQL.append("  		core_paymentplan cp,");
        sbSQL.append("  		oa_bizroute ob");
        sbSQL.append("  	WHERE");
        sbSQL.append("  		1 = 1");
        sbSQL.append("  	AND ob.Id_YWID = cp.id");
        sbSQL.append("  	AND  cp.state=" + Config.STATE_CURRENT);
        sbSQL.append("  	AND rl.YWID = cp.id");
        sbSQL.append(" AND rl.STATU IN (" + Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel") + "," + Config.getSystemVariable("paymentPlan.Route.intStatus.Over") + ")");

        sbSQL.append("  	AND cp.id = '" + Database.encodeSQL(id) + "'");
        sbSQL.append("  UNION");
        sbSQL.append("  	SELECT");
        sbSQL.append("  		1 currentNodeId,");
        sbSQL.append("  		0 routeListId,");
        sbSQL.append("  		-1 currentStatus,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("  		cp.*");
        sbSQL.append("  	FROM");
        sbSQL.append("  		core_paymentplan cp,");
        sbSQL.append("  		oa_bizroute ob");
        sbSQL.append("  	WHERE");
        sbSQL.append("  		1 = 1");
        sbSQL.append("  	AND  cp.state=" + Config.STATE_CURRENT);
        sbSQL.append("  	AND ob.Id_YWID = cp.id");
        sbSQL.append("  	AND cp.id = '" + Database.encodeSQL(id) + "'");
        sbSQL.append("		AND cp.id not IN (");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				YWID");
        sbSQL.append("			FROM");
        sbSQL.append("				workflow_routelist rl");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("		)");

        //查询获取数据域
        List<PaymentPlanVO> productions = MySQLDao.query(sbSQL.toString(), PaymentPlanVO.class, null, conn);

        //获取的数据不等于1
        if (productions.size() == 0) {
            //新增业务数据

            int count = BizRouteService.insertOrUpdate(id, "", "", "", Integer.parseInt(Config.getSystemVariable("WORKFLOWID.PaymentPlam.Check")), true, user, conn);

            //业务数据添加成功
            if (count == 1) {
                productions.add(getPaymentPlanYW(id, user, conn));
            }//添加失败
            else {
                MyException.deal(new Exception("业务数据添加失败！"));
            }
        } else if (productions.size() != 1) {
            MyException.deal(new Exception("获取产品审批数据失败！"));
        }

        return productions.get(0);
    }


    /**
     * 申请兑付
     *
     * @param id
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public PaymentPlanVO setPaymentPlanYW(String id, UserPO user, Connection conn) throws Exception {
        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("  SELECT DISTINCT");
        sbSQL.append("  	rl.CURRENTNODE currentNodeId,");
        sbSQL.append("  	rl.id routeListId,");
        sbSQL.append("  	rl.STATU currentStatus,");
        sbSQL.append("  	ob.submitterId,");
        sbSQL.append("  	ob.submitterTime,");
        sbSQL.append("  	ob.submitterName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("  	cp.*");
        sbSQL.append("  FROM");
        sbSQL.append("  	workflow_routelist rl,");
        sbSQL.append("  	core_paymentplan cp,");
        sbSQL.append("  	oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("  	1 = 1");
        sbSQL.append("  	AND  cp.state=" + Config.STATE_CURRENT);
        sbSQL.append("  AND ob.Id_YWID = cp.id");
        sbSQL.append("  AND rl.YWID = cp.id");
        sbSQL.append("  AND rl.STATU IN (1)");
        sbSQL.append("  AND cp.id = '" + Database.encodeSQL(id) + "'");
        sbSQL.append("  UNION");
        sbSQL.append("  	SELECT DISTINCT");
        sbSQL.append("  		1 currentNodeId,");
        sbSQL.append("  		0 routeListId,");
        sbSQL.append("  		rl.STATU currentStatus,");
        sbSQL.append("  	ob.submitterId,");
        sbSQL.append("  	ob.submitterTime,");
        sbSQL.append("  	ob.submitterName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("  		cp.*");
        sbSQL.append("  	FROM");
        sbSQL.append("  		workflow_routelist rl,");
        sbSQL.append("  		core_paymentplan cp,");
        sbSQL.append("  		oa_bizroute ob");
        sbSQL.append("  	WHERE");
        sbSQL.append("  		1 = 1");
        sbSQL.append("  	AND ob.Id_YWID = cp.id");
        sbSQL.append("  	AND  cp.state=" + Config.STATE_CURRENT);
        sbSQL.append("  	AND rl.YWID = cp.id");
        sbSQL.append(" AND rl.STATU IN (" + Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel") + "," + Config.getSystemVariable("paymentPlan.Route.intStatus.Over") + ")");
        sbSQL.append("  	AND cp.id = '" + Database.encodeSQL(id) + "'");
        sbSQL.append("  UNION");
        sbSQL.append("  	SELECT");
        sbSQL.append("  		1 currentNodeId,");
        sbSQL.append("  		0 routeListId,");
        sbSQL.append("  		-1 currentStatus,");
        sbSQL.append("  	ob.submitterId,");
        sbSQL.append("  	ob.submitterTime,");
        sbSQL.append("  	ob.submitterName,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("  		cp.*");
        sbSQL.append("  	FROM");
        sbSQL.append("  		core_paymentplan cp,");
        sbSQL.append("  		oa_bizroute ob");
        sbSQL.append("  	WHERE");
        sbSQL.append("  		1 = 1");
        sbSQL.append("  	AND  cp.state=" + Config.STATE_CURRENT);
        sbSQL.append("  	AND ob.Id_YWID = cp.id");
        sbSQL.append("  	AND cp.id = '" + Database.encodeSQL(id) + "'");
        sbSQL.append("		AND cp.id not IN (");
        sbSQL.append("			SELECT DISTINCT");
        sbSQL.append("				YWID");
        sbSQL.append("			FROM");
        sbSQL.append("				workflow_routelist rl");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("		)");

        //查询获取数据域
        List<PaymentPlanVO> paymentPlans = MySQLDao.query(sbSQL.toString(), PaymentPlanVO.class, null, conn);

        //获取的数据不等于1
        if (paymentPlans.size() == 0) {
            //新增业务数据
            int count = BizRouteService.insertOrUpdate(id, "", "", "", Integer.parseInt(Config.getSystemVariable("WORKFLOWID.PaymentPlam.Check")), true, user, conn);
            if (count == 1) {
                return this.setPaymentPlanYW(id, user, conn);
            }
           /* //业务数据添加成功
            if (count == 1) {

                //更新兑付计划状态为一申请
                PaymentPlanPO pay = new PaymentPlanPO();
                pay.setId(id);
                //数据状态改为已申请
                pay.setStatus(Integer.parseInt(Config.getSystemVariable("paymentPlan.status.Apply")));
                //更新数据状态
                this.updateStatue(pay, user, conn);

            }//添加失败
            else {
                return null;
            }*/
        }
        return paymentPlans.get(0);
    }


    public List<PaymentPlanVO> listPaymentPlanVOByDate(String beginTime, String endTime, Connection conn) throws Exception {
        return paymentPlanDao.listPaymentPlanVOByDate(beginTime, endTime, conn);
    }

    /**
     * 根据id获取负责表的数据并且返回满足条件的兑付数据信息
     *
     * @param id
     * @return
     */
    public List getPaymentPlanList(String id, Connection conn) throws Exception {
        //获取辅助表信息
        ProductionFoWorkflowService ss = new ProductionFoWorkflowService();
        ProductionFoWorkflowPO pro = ss.load(id, conn);
        StringBuffer sbSQL = new StringBuffer();
        //创造SQL
        sbSQL.append("	select DISTINCT * from  core_paymentplan");
        sbSQL.append("	where 1=1");
        sbSQL.append("	and state = 0");
        sbSQL.append("	and ProductId = '" + pro.getProductId() + "'");
        sbSQL.append("	and PaymentTime ='" + pro.getPaymentTime() + "'");
        //返回列表数据
        List<PaymentPlanPO> ls = MySQLDao.query(sbSQL.toString(), PaymentPlanPO.class, null, conn);
        return ls;
    }


    /**
     * 根据条件获取数据、更改数据的状态为以更新状态
     * 设置数据的兑付状态为以审核状态、新增数据。
     *
     * @param paymentplan
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int updateStatue(PaymentPlanPO paymentplan, UserPO user, Connection conn) throws Exception {
        PaymentPlanPO temp = new PaymentPlanPO();
        //初始化一些必要数据
        temp.setSid(paymentplan.getSid());
        temp.setId(paymentplan.getId());
        temp.setState(Config.STATE_CURRENT);

        //查询数据
        temp = MySQLDao.load(temp, PaymentPlanPO.class);

        //更新数据
        temp.setState(Config.STATE_UPDATE);
        int count = MySQLDao.update(temp, conn);

        //重新插入以一条当前状态的数据
        temp.setSid(MySQLDao.getMaxSid("Core_PaymentPlan", conn));
        temp.setState(Config.STATE_CURRENT);
        temp.setOperatorId(user.getId());
        temp.setOperateTime(TimeUtils.getNow());
        //更改状态为以审核
        temp.setStatus(paymentplan.getStatus());
        count = MySQLDao.insert(temp, conn);

        if (count != 1) {
            throw new Exception("更新兑付计划状态异常");
        }
        return count;
    }


    public Pager listPagePaymentInfo(PaymentPlanVO paymentPlanVO, String productionId, int currentPage, int showRowCount, Connection conn) throws Exception {
        return paymentPlanDao.listPagePaymentInfo(paymentPlanVO, productionId, currentPage, showRowCount, conn);
    }


    /**
     * 根据内容添加工作流
     *
     * @throws Exception
     */
    public String getInssertApplayWorlkflowId(PaymentPlanVO paymentPlan, UserPO user, Connection conn) throws Exception {
        ProductionFoWorkflowPO productionFoWorkflow = new ProductionFoWorkflowPO();
        ProductionFoWorkflowService services = new ProductionFoWorkflowService();
        //设置内容
        productionFoWorkflow.setProductId(paymentPlan.getProductId());
        productionFoWorkflow.setPaymentTime(paymentPlan.getPaymentTime());

        String id = services.insertOf(productionFoWorkflow, user, conn);

        //判断添加数据是否成功
        if (StringUtils.isEmpty(id)) {
            MyException.newInstance("添加产品兑付工作流失败");
        }
        return id;
    }

    /**
     * 获取兑付计划Excel 文件
     *
     * @throws Exception
     */
    public void getPaymentPlanExcel(String StrTime, String Stoptime, Connection conn) throws Exception {
        //获取数据
        List<PaymentPlanTempVO> lists = this.getPaymentplanExcelDate(StrTime, Stoptime, conn);

        //设置日期格式
        Date d = new Date();
        long longTime = d.getTime();
        //输出Excel的文件名称
        String fileName = "兑付计划测试_" + longTime;
        //转换输出格式防止乱码
        fileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/octet-stream");
        //设置Excel导出文件名称
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        //获取输出流
        ServletOutputStream out = response.getOutputStream();

        //创建Excel
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("表格一");  //Excel导出sheet页签名称

        try {
            //创建第一行对象
            HSSFRow onerow = sheet.createRow(0);

            //获取总交易金额
            double sumMoney = 0;
            for (int i = 0; lists.size() > 0 && i < lists.size(); i++) {
                PaymentPlanTempVO p = (PaymentPlanTempVO) lists.get(i);
                sumMoney += p.getoMoney() * 100;
            }


            //获取当前时间
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
            String date = sDateFormat.format(new Date());
            //第一行数据
            onerow.createCell(0).setCellValue("F");
            onerow.createCell(1).setCellValue(date);
            onerow.createCell(2).setCellValue(lists.size());
            onerow.createCell(3).setCellValue(sumMoney);
            onerow.createCell(4).setCellValue("09900");


            //设置第二行
            HSSFRow towrow = sheet.createRow(1);
            String[] headersInfo = {"序号", "通联支付用户编号", "银行代码",
                    "帐号类型",
                    "账号",
                    "户名",
                    "省",
                    "市",
                    "开户行名称",
                    "账户类型",
                    "金额",
                    "货币类型",
                    "协议号",
                    "协议用户编号",
                    "开户证件类型",
                    "证件号",
                    "手机号/小灵通",
                    "自定义用户号",
                    "备注",
                    "反馈码",
                    "原因"};
            // 创建一个居中格式
            HSSFCellStyle style = wb.createCellStyle();
//            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            for (int i = 0; i < headersInfo.length; i++) {
                sheet.setColumnWidth(i, 2000);
                towrow.createCell(i).setCellStyle(style);
                towrow.createCell(i).setCellValue(headersInfo[i]);
            }
            //获取账户与户名


            HSSFRow row;
            //循环创建遍历行数数据
            for (int i = 0; lists.size() > 0 && i < lists.size(); i++) {

                PaymentPlanTempVO p = (PaymentPlanTempVO) lists.get(i);
                //解密客户账户与户名
                String number = AesEncrypt.decrypt(p.getAccountNumber());
                //计算序列
                String sid = "";


                if (i >= 9999 && i < 99999) {
                    sid = "0" + (i + 1);
                } else if (i >= 999 && i < 9999) {
                    sid = "00" + (i + 1);
                } else if (i >= 99 && i < 999) {
                    sid = "000" + (i + 1);
                } else if (i >= 9 && i < 99) {
                    sid = "0000" + (i + 1);
                } else {
                    sid = "00000" + (i + 1);
                }

                String[] ss = {sid, "", p.getBankCode(), "，", number, p.getCustomerName(), "", "", "", "0", "" + (int) p.getoMoney() * 100, "CNY"};
                //户名
                //第三行开始写入数据
                row = sheet.createRow(i + 2);
                //循环传插入列
                for (int j = 0; j < 12; j++) {

//                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    row.createCell(j).setCellStyle(style);
                    row.createCell(j).setCellValue(ss[j]);
                }
            }
            // 将文件存到指定位置

            //获取输出流，写入excel 并关闭
            wb.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            //异常情况下判断流并关闭
            if (null != out) {
                out.flush();
                out.close();
            }
            e.printStackTrace();
        }

    }


    /**
     * 根据sql获取数据列表对象
     *
     * @return
     * @throws Exception
     */
    public List<PaymentPlanTempVO> getPaymentplanExcelDate(String StrTime, String Stoptime, Connection conn) throws Exception {
        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	SELECT");
        sbSQL.append("		customer.personalNumber AS customerPersonalNumber,");
        sbSQL.append("		account.number AS accountNumber,");
        sbSQL.append("		customer.`Name` AS customerName,");
        sbSQL.append("		production. NAME AS productionName,");
        sbSQL.append("		o.money AS oMoney,");
        sbSQL.append("		plan.totalPaymentMoney AS totalPaymentMoney1,");
        sbSQL.append("		plan.totalInstallment AS totalInstallment,");
        sbSQL.append("		plan.currentInstallment AS currentInstallment,");
        sbSQL.append("		o.createTime AS oCreateTime,");
        sbSQL.append("		o.valueDate AS oValueDate,");
        sbSQL.append("		plan.paymentTime AS paymentTime,");
        sbSQL.append("		composition.ExpectedYield AS ExpectedYield,");
        sbSQL.append("		round(plan.totalProfitMoney, 2) AS totalProfitMoney,");
        sbSQL.append("		round(plan.totalPaymentMoney, 2) AS totalPaymentMoney2,");
        sbSQL.append("		round(");
        sbSQL.append("			(");
        sbSQL.append("				SELECT");
        sbSQL.append("					sum(");
        sbSQL.append("						plan2.totalPaymentPrincipalMoney");
        sbSQL.append("					)");
        sbSQL.append("				FROM");
        sbSQL.append("					core_paymentPlan plan2");
        sbSQL.append("				WHERE");
        sbSQL.append("					plan2.state = 0");
        sbSQL.append("				AND plan2.`Status` = 958");
        sbSQL.append("				AND plan2.customerId = customer.id");
        sbSQL.append("			) - plan.totalPaymentPrincipalMoney,");
        sbSQL.append("			2");
        sbSQL.append("		) AS totalPaymentPrincipalMoney,");
        sbSQL.append("		round(");
        sbSQL.append("			(");
        sbSQL.append("				SELECT");
        sbSQL.append("					sum(plan1.totalPaymentMoney)");
        sbSQL.append("				FROM");
        sbSQL.append("					core_paymentPlan plan1");
        sbSQL.append("				WHERE");
        sbSQL.append("					plan1.state = 0");
        sbSQL.append("				AND plan1.`Status` = 958");
        sbSQL.append("				AND plan1.customerId = customer.id");
        sbSQL.append("			) - plan.totalPaymentMoney,");
        sbSQL.append("			2");
        sbSQL.append("		) AS totalPaymentMoney3,");
        sbSQL.append("		plan. `STATUS` AS planSTATUS,");
        sbSQL.append("		(");
        sbSQL.append("			SELECT");
        sbSQL.append("				SUM(o1.money)");
        sbSQL.append("			FROM");
        sbSQL.append("				crm_order o1,");
        sbSQL.append("				crm_customerpersonal customer1");
        sbSQL.append("			WHERE");
        sbSQL.append("				o1.state = 0");
        sbSQL.append("			AND customer1.state = 0");
        sbSQL.append("			AND o1.customerId = customer1.id");
        sbSQL.append("		) AS 01Money,");
        sbSQL.append("		(");
        sbSQL.append("			SELECT");
        sbSQL.append("				SUM(");
        sbSQL.append("					round(");
        sbSQL.append("						pl.totalPaymentPrincipalMoney,");
        sbSQL.append("						2");
        sbSQL.append("					)");
        sbSQL.append("				)");
        sbSQL.append("			FROM");
        sbSQL.append("				core_paymentplan pl,");
        sbSQL.append("				crm_customerpersonal cu");
        sbSQL.append("			WHERE");
        sbSQL.append("				pl.state = 0");
        sbSQL.append("			AND pl.`Status` = 958");
        sbSQL.append("			AND cu.state = 0");
        sbSQL.append("			AND pl.customerId = cu.id");
        sbSQL.append("		) AS totalPaymentPrincipalMoney,");
        sbSQL.append("		(");
        sbSQL.append("			SELECT");
        sbSQL.append("				SUM(");
        sbSQL.append("					round(pl.totalProfitMoney, 2)");
        sbSQL.append("				)");
        sbSQL.append("			FROM");
        sbSQL.append("				core_paymentplan pl,");
        sbSQL.append("				crm_customerpersonal cu");
        sbSQL.append("			WHERE");
        sbSQL.append("				pl.state = 0");
        sbSQL.append("			AND pl.`Status` = 958");
        sbSQL.append("			AND cu.state = 0");
        sbSQL.append("			AND pl.customerId = cu.id");
        sbSQL.append("		) AS totalProfitMoney,");
        sbSQL.append("		(");
        sbSQL.append("			SELECT");
        sbSQL.append("				SUM(");
        sbSQL.append("					round(pl.totalPaymentMoney, 2)");
        sbSQL.append("				)");
        sbSQL.append("			FROM");
        sbSQL.append("				core_paymentplan pl,");
        sbSQL.append("				crm_customerpersonal cu");
        sbSQL.append("			WHERE");
        sbSQL.append("				pl.state = 0");
        sbSQL.append("			AND pl.`Status` = 958");
        sbSQL.append("			AND cu.state = 0");
        sbSQL.append("			AND pl.customerId = cu.id");
        sbSQL.append("		) AS totalPaymentMoney4,");
        sbSQL.append("	account.Bank as bank,");
        sbSQL.append("	account.BankCode as BankCode");
        sbSQL.append("	FROM");
        sbSQL.append("		core_paymentplan plan,");
        sbSQL.append("		crm_customerpersonal customer,");
        sbSQL.append("		crm_customeraccount account,");
        sbSQL.append("		crm_production production,");
        sbSQL.append("		crm_order o,");
        sbSQL.append("		crm_productioncomposition composition");
        sbSQL.append("	WHERE");
        sbSQL.append("		plan.state = 0");
        sbSQL.append("	AND plan. STATUS = 958");
        sbSQL.append("	AND customer.state = 0");
        sbSQL.append("	AND account.state = 0");
        sbSQL.append("	AND production.state = 0");
        sbSQL.append("	AND o.state = 0");
        sbSQL.append("	AND composition.state = 0");
        sbSQL.append("	AND composition.id = o.productionCompositionId");
        sbSQL.append("	AND plan.productCompositionId = composition.id");
        sbSQL.append("	AND plan.orderId = o.id");
        sbSQL.append("	AND plan.productId = production.id");
        sbSQL.append("	AND account.customerId = customer.id");
        sbSQL.append("	AND plan.customerId = customer.id");
        sbSQL.append("	AND plan.paymentTime >= '" + StrTime + "'");
        sbSQL.append("	AND plan.paymentTime <= '" + Stoptime + "';");
        List<PaymentPlanTempVO> lists = MySQLDao.query(sbSQL.toString(), PaymentPlanTempVO.class, null, conn);

        return lists;
    }

    /**
     * 获取客户的兑付计划信息
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月23日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public List<PaymentPlanPO> getCustomerPaymentPlan(String customerId, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("  SELECT ");
        sbSQL.append("  * ");
        sbSQL.append("  FROM ");
        sbSQL.append("  core_paymentplan ");
        sbSQL.append("       WHERE ");
        sbSQL.append("    customerId = ? ");
        sbSQL.append("    AND State = 0 ");
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, customerId, parameters);
        List<PaymentPlanPO> lists = MySQLDao.search(sbSQL.toString(), parameters, PaymentPlanPO.class, null, conn);
        return lists;
    }

}
