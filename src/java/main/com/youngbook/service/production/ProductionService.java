package com.youngbook.service.production;


import com.youngbook.action.pay.AllinPayAction;
import com.youngbook.common.*;
import com.youngbook.common.config.BizRouteConfig;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IProductionDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderCallBackPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderTradingStatus;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.po.customer.CustomerMoneyLogPO;
import com.youngbook.entity.po.customer.CustomerRefundPO;
import com.youngbook.entity.po.production.*;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.po.sale.PaymentPlanType;
import com.youngbook.entity.po.sale.PaymentPlanUnit;
import com.youngbook.entity.po.sale.contract.ContractApplicationPO;
import com.youngbook.entity.vo.production.*;
import com.youngbook.entity.wvo.production.ProductionWVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.getInstance;

@Component("productionService")
public class ProductionService extends BaseService {

    @Autowired
    IProductionDao productionDao;

    @Autowired
    IOrderDao orderDao;

    @Autowired
    ILogDao logDao;

    public List<ProductionPO> listProductionPOByProductionNameOrProductionNO(String productionName, String productionNO, Connection conn) throws Exception {
        return productionDao.listProductionPOByProductionNameOrProductionNO(productionName, productionNO, conn);
    }

    public ProductionPO loadProductionById(String id, Connection conn) throws Exception {
        return productionDao.loadProductionById(id, conn);
    }

    public ProductionPO loadProductionById(String id) throws Exception {

        Connection conn = Config.getConnection();
        try {
            return productionDao.loadProductionById(id, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }


    public ProductionWVO getProductionWVOById(String id, Connection conn) throws Exception {
        return productionDao.getProductByProductionIdAndMoney(id, 0, conn);
    }

    public List<ProductionWVO> getListProductionWVOById(String id, Connection conn) throws Exception {
        return productionDao.getListProductByProductionIdAndMoney(id, conn);
    }

    public List<ProductionWVO> getListProductionWVOById(String id) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return productionDao.getListProductByProductionIdAndMoney(id, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    public String getPaymentInfo(String productionId, Connection conn) throws Exception {
        return "这里是融资账号信息. [ProductionService.getPaymentInfo]";
    }

    /**
     * 通过产品编号获得产品的文章，描述等
     * @param productionId
     * @param conn
     * @return
     * @throws Exception
     */
    public List<ArticlePO> getListProductionArticle(String productionId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getListProductionArticle", this);
        dbSQL.addParameter4All("productionId", productionId);
        dbSQL.initSQL();

        List<ArticlePO> listArticlePO = MySQLDao.search(dbSQL, ArticlePO.class, conn);

        return listArticlePO;
    }

    public List<ArticlePO> getListProductionArticle(String productionId) throws Exception {

        Connection conn = Config.getConnection();
        try {
            return getListProductionArticle(productionId, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }


    /**
     * 分页列出产品投资者
     *
     * Date: 2016-05-17 10:30:33
     * Author: leevits
     */
    public Pager listCustomer(String productionId, int currentPage, int showRowCount, Connection conn) throws Exception {

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得产品编号").throwException();
        }

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listCustomer", "ProductionServiceSQL", ProductionService.class);
        dbSQL.addParameter4All("ProductionId", productionId);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");


        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), new ProductionCustomerVO(), null, currentPage, showRowCount, queryType, conn);

        return pager;
    }

    /**
     * 执行手动删除
     * @param orderNo 通联的订单编号
     * @param updateOrderType 0：只更新；1：删除
     * @param updateAllinpayType 0：只更新；1：删除
     * @param conn
     * @return
     * @throws Exception
     */
    public int unsellProductionManualDo(String orderNo, int updateOrderType, int updateAllinpayType, Connection conn) throws Exception {



        if (StringUtils.isEmpty(orderNo)) {
            MyException.newInstance("获取通联的订单编号失败").throwException();
        }

        String logHeader = "手动删除购买产品记录，通联订单号【"+orderNo+"】。";

        /**
         * 开始处理订单
         *
         */
        // 查询获得订单
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     *");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_order");
        sbSQL.append(" WHERE");
        sbSQL.append("     state = 0");
        sbSQL.append(" AND id = (");
        sbSQL.append("     SELECT");
        sbSQL.append("         BizId");
        sbSQL.append("     FROM");
        sbSQL.append("         bank_AllinPayTransfer");
        sbSQL.append("     WHERE");
        sbSQL.append("         1 = 1");
        sbSQL.append("     AND State = 0");
        sbSQL.append("     AND OrderNo = ?");
        sbSQL.append("     LIMIT 1");
        sbSQL.append(" )");
        DatabaseSQL dbQueryOrder = new DatabaseSQL();
        dbQueryOrder.newSQL(sbSQL.toString());
        dbQueryOrder.addParameter(1, orderNo);
        List<OrderPO> listOrders = MySQLDao.search(dbQueryOrder.getSQL(), dbQueryOrder.getParameters(), OrderPO.class, null, conn);
        if (listOrders == null || listOrders.size() != 1) {
            MyException.newInstance("无法查询到订单信息").throwException();
        }

        OrderPO order = listOrders.get(0);

        // 根据传入参数，判断是否删除或更新订单
        boolean isOrderDone = false;
        // 更新订单
        if (updateOrderType == 0) {
            // 更新订单支付状态
            order.setStatus(OrderStatus.Appointment);
            order.setPayTime("");

            // 移除订单兑付信息
            order.setPaybackTime("");

            // 移除订单起息日信息
            order.setValueDate("");

            int tempCount = MySQLDao.insertOrUpdate(order, "0000", conn);
            if (tempCount == 1) {
                logDao.save(logHeader,"删除订单数据","【OrderId:"+order.getId()+", sid:"+order.getSid()+"】", conn);
                isOrderDone = true;
            }
        }
        // 删除订单
        else if (updateOrderType == 1) {
            int tempCount = MySQLDao.remove(order, "0000", conn);
            if (tempCount == 1) {
                logDao.save(logHeader, "删除订单数据","【OrderId:"+order.getId()+", sid:"+order.getSid()+"】", conn);
                isOrderDone = true;
            }
        }

        if (!isOrderDone) {
            MyException.newInstance("订单处理失败").throwException();
        }


        /**
         * 开始处理通联支付订单
         *
         * 由于支付订单和反馈订单可能有多条，我们只处理最近的一条记录
         */

        StringBuffer sbAllinpayOrderSQL = new StringBuffer();
        sbAllinpayOrderSQL.append(" SELECT");
        sbAllinpayOrderSQL.append("     *");
        sbAllinpayOrderSQL.append(" FROM");
        sbAllinpayOrderSQL.append("     bank_AllinPayTransfer");
        sbAllinpayOrderSQL.append(" WHERE");
        sbAllinpayOrderSQL.append("     1 = 1");
        sbAllinpayOrderSQL.append(" AND State = 0");
        sbAllinpayOrderSQL.append(" AND OrderNo = ? ORDER BY Sid desc limit 1;");

        DatabaseSQL dbQueryAllinpayOrder = new DatabaseSQL();
        dbQueryAllinpayOrder.newSQL(sbAllinpayOrderSQL.toString());
        dbQueryAllinpayOrder.addParameter(1, orderNo);

        List<AllinPayOrderPO> listAllinpayOrders = MySQLDao.search(dbQueryAllinpayOrder.getSQL(), dbQueryAllinpayOrder.getParameters(), AllinPayOrderPO.class, null, conn);

        if (listAllinpayOrders == null || listAllinpayOrders.size() != 1) {
            MyException.newInstance("无法查询到通联支付订单信息").throwException();
        }

        AllinPayOrderPO allinPayOrder = listAllinpayOrders.get(0);

        StringBuffer sbAllinpayOrderCallbackSQL = new StringBuffer();
        sbAllinpayOrderCallbackSQL.append(" SELECT");
        sbAllinpayOrderCallbackSQL.append("     *");
        sbAllinpayOrderCallbackSQL.append(" FROM");
        sbAllinpayOrderCallbackSQL.append("     bank_allinpayordercallback");
        sbAllinpayOrderCallbackSQL.append(" WHERE");
        sbAllinpayOrderCallbackSQL.append("     1 = 1");
        sbAllinpayOrderCallbackSQL.append(" AND State = 0");
        sbAllinpayOrderCallbackSQL.append(" AND OrderNo = ? ORDER BY Sid desc limit 1;");

        DatabaseSQL dbQueryAllinpayOrderCallback = new DatabaseSQL();
        dbQueryAllinpayOrderCallback.newSQL(sbAllinpayOrderCallbackSQL.toString());
        dbQueryAllinpayOrderCallback.addParameter(1, orderNo);


        List<AllinPayOrderCallBackPO> listAllinpayOrderCallbacks = MySQLDao.search(dbQueryAllinpayOrderCallback.getSQL(), dbQueryAllinpayOrderCallback.getParameters(), AllinPayOrderCallBackPO.class, null, conn);

        if (listAllinpayOrderCallbacks == null || listAllinpayOrderCallbacks.size() != 1) {
            MyException.newInstance("无法查询到通联支付反馈订单信息").throwException();
        }

        AllinPayOrderCallBackPO allinPayOrderCallback = listAllinpayOrderCallbacks.get(0);

        boolean isAllinpayOrderDone = false;
        // 更新通联订单
        if (updateAllinpayType == 0) {

            allinPayOrderCallback.setOrderAmount(allinPayOrder.getOrderAmount());
            allinPayOrderCallback.setPayDatetime("");
            allinPayOrderCallback.setPayAmount(0);

            int tempCount = MySQLDao.insertOrUpdate(allinPayOrderCallback, "0000", conn);
            if (tempCount == 1) {
                logDao.save(logHeader, "更新通联支付返回订单数据","【AllinPayOrderCallbackId:"+allinPayOrderCallback.getId()+", sid:"+allinPayOrderCallback.getSid()+"】", conn);
                isAllinpayOrderDone = true;
            }

        }
        // 删除通联订单
        else if (updateAllinpayType == 1) {

            int tempCount = MySQLDao.remove(allinPayOrderCallback, "0000", conn);
            if (tempCount == 1) {
                logDao.save(logHeader, "更新通联支付返回订单数据","【AllinPayOrderCallbackId:"+allinPayOrderCallback.getId()+", sid:"+allinPayOrderCallback.getSid()+"】", conn);
                isAllinpayOrderDone = true;
            }
        }

        if (!isAllinpayOrderDone) {
            MyException.newInstance("通联支付反馈订单处理失败").throwException();
        }


        // 更新产品销售金额
        ProductionPO production = new ProductionPO();
        production.setState(Config.STATE_CURRENT);
        production.setId(order.getProductionId());
        production = MySQLDao.load(production, ProductionPO.class, conn);
        int tempProductionCount = MySQLDao.insertOrUpdate(production, conn);
        if (tempProductionCount == 1) {
            logDao.save(logHeader, "更新产品数据","ProductionId:"+production.getId()+"&sid:"+production.getSid()+"", conn);
        }

        if (tempProductionCount != 1) {
            MyException.newInstance("更新产品数据处理失败").throwException();
        }

        // 更新客户管理资金 未实现


        // 删除客户资金记录
        CustomerMoneyLogPO moneylog = new CustomerMoneyLogPO();
        moneylog.setState(Config.STATE_CURRENT);
        moneylog.setCustomerId(order.getCustomerId());
        moneylog.setBizId(order.getId());
        moneylog = MySQLDao.load(moneylog, CustomerMoneyLogPO.class, conn);
        int tempCustomerMoneyLogCount = MySQLDao.remove(moneylog,"0000", conn);
        if (tempCustomerMoneyLogCount == 1) {
            logDao.save(logHeader,"客户资金记录", "客户资金记录，【CustomerMoneyLogId:"+moneylog.getId()+", sid:"+moneylog.getSid()+"】", conn);
        }

        if (tempCustomerMoneyLogCount != 1) {
            MyException.newInstance("客户资金记录失败").throwException();
        }


        // 删除客户退款记录
        CustomerRefundPO refund = new CustomerRefundPO();
        refund.setState(Config.STATE_CURRENT);
        refund.setCustomerId(order.getCustomerId());
        refund.setOrderId(order.getId());

        refund = MySQLDao.load(refund, CustomerRefundPO.class, conn);
        if (refund != null) {
            int tempCustomerRefundCount = MySQLDao.remove(refund,"0000", conn);
            if (tempCustomerRefundCount == 1) {
                logDao.save(logHeader, "客户退款记录","【CustomerRefundId:"+refund.getId()+", sid:"+refund.getSid()+"】",  conn);
            }

            if (tempCustomerRefundCount != 1) {
                MyException.newInstance("客户退款记录失败").throwException();
            }
        }




        // 删除客户兑付计划
        PaymentPlanPO paymentPlan = new PaymentPlanPO();
        paymentPlan.setState(Config.STATE_CURRENT);
        paymentPlan.setCustomerId(order.getCustomerId());
        List<PaymentPlanPO> paymentPlans = MySQLDao.search(paymentPlan, PaymentPlanPO.class, null, null, conn);
        for(PaymentPlanPO tempPaymentPlan : paymentPlans) {
            int tempPaymentPlanCount = MySQLDao.remove(tempPaymentPlan,"0000", conn);
            if (tempPaymentPlanCount == 1) {
                logDao.save(logHeader, "删除客户兑付计划","【Id:"+tempPaymentPlan.getId()+", sid:"+tempPaymentPlan.getSid()+"】", conn);
            }
        }

        return 1;
    }

    public int sellProductionManualDo(String orderNo, String paidTime, Connection conn) throws Exception {

        if (StringUtils.isEmpty(orderNo)) {
            MyException.newInstance("接口参数失败").throwException();
        }


        // 创建 AllinPayOrderPO
        AllinPayOrderPO allinPayOrder = new AllinPayOrderPO();
        allinPayOrder.setOrderNo(orderNo);
        allinPayOrder.setState(Config.STATE_CURRENT);
        allinPayOrder = MySQLDao.load(allinPayOrder, AllinPayOrderPO.class, conn);


        // 创建 allinPayOrderCallBack
        AllinPayOrderCallBackPO allinPayOrderCallBack = new AllinPayOrderCallBackPO();
        allinPayOrderCallBack.setOrderNo(orderNo);
        allinPayOrderCallBack.setPayDatetime(paidTime);

        // 执行购买
        AllinPayAction action = Config.getBeanByName("allinPayAction", AllinPayAction.class);
        action.sellProduction(allinPayOrder, allinPayOrderCallBack, conn);

        // 设置 AllinPayOrderPO 状态为已接受
        allinPayOrder.setTradingStatus(AllinPayOrderTradingStatus.Accepted);
        int count = MySQLDao.insertOrUpdate(allinPayOrder, conn);

        if (count != 1) {
            MyException.newInstance("手动更新订单失败").throwException();
        }


        return 1;
    }

    /**
     * 获取产品列表
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月6日
     *
     * HOPEWEALTH-1293
     * 作者：曾威恺
     * 内容：增加最大返佣率相关内容
     * 时间：2016年03月21日
     *
     * @param currentPage
     * @param showRowCount
     * @return
     * @throws Exception
     */
    public Pager getProductions(String customerTypeStr, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {

        Pager pager = null;

        if(StringUtils.isEmpty(customerTypeStr)) {
            customerTypeStr = "1";
        }
        Integer customerType = Integer.parseInt(customerTypeStr);


        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("	p.id, ");
        sql.append("    p.productId, ");
        sql.append("	p.websiteDisplayName, ");
        sql.append("	p.size, ");
        sql.append("	p.saleMoney, ");
        sql.append("	date_format(p.valueDate,'%Y-%m-%d') as valueDate, ");
        sql.append("	date_format(p.expiringDate,'%Y-%m-%d') as expiringDate, ");
        sql.append("	p. STATUS, ");
        sql.append("  CASE p.`STATUS` ");
        sql.append(" WHEN 0 THEN ");
        sql.append("  '草稿' ");
        sql.append("  WHEN 1 THEN ");
        sql.append("   '发布待售' ");
        sql.append("  WHEN 2 THEN ");
        sql.append("   '在售' ");
        sql.append("  WHEN 3 THEN ");
        sql.append("   '暂停' ");
        sql.append("  WHEN 4 THEN ");
        sql.append("   '售完' ");
        sql.append("  WHEN 5 THEN ");
        sql.append("   '作废' ");
        sql.append("   END AS statusStr,");
        sql.append("	floor(p.saleMoney/p.size*100) as saleProcess, ");
        sql.append("	p.investTermView, ");
        sql.append("    p.interestPaymentDescription as interestType, ");
        sql.append("    p.startTime, ");
        sql.append("    p.stopTime, ");
        sql.append("    p.incomeType, ");
        sql.append("  (select min(c1.expectedYield) from crm_productionComposition c1 where c1.state = 0 and c1.productionId = p.id) as minExpectedYield, ");
        sql.append("  (select max(c1.expectedYield) from crm_productionComposition c1 where c1.state = 0 and c1.productionId = p.id) as maxExpectedYield, ");
        sql.append("  (select min(c1.sizeStart) from crm_productionComposition c1 where c1.state = 0 and c1.productionId = p.id) as minSizeStart ");
        sql.append("FROM ");
        sql.append("	crm_production p, ");
        sql.append(" crm_productionInfo i, ");
        sql.append(" crm_productionHome h, ");
        sql.append(" crm_project j ");
        sql.append("WHERE ");
        sql.append("	p.state = 0 ");
        sql.append("AND i.state = 0 ");
        sql.append("AND p. STATUS = 2 ");
        sql.append("AND p.StopTime > SYSDATE() ");
        sql.append("AND i.productionId = p.id ");
        sql.append("AND h.state = 0 ");
        sql.append("AND j.id = h.projectId ");
        sql.append("AND j.state = 0 ");
        sql.append("AND p.productHomeId = h.id ");

        // 当incomeType为0或1时，加条件，否则是展现全部产品
        sql.append("ORDER BY p.operateTime DESC ");

        pager = MySQLDao.query(sql.toString(), ProductionVO.class, currentPage, showRowCount, conn);
        return pager;
    }

    /**
     * 获取产品
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月6日
     *
     * @param productionId
     * @return
     * @throws Exception
     */
    public Pager loadProduction(String productionId, String customerTypeStr, Connection conn) throws Exception {

        Pager pager = null;

        if("".equals(customerTypeStr)) {
            customerTypeStr = "0";
        }
        Integer customerType = Integer.parseInt(customerTypeStr);



        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("	p.id, ");
        sql.append("    p.productId, ");
        sql.append("	p. NAME, ");
        sql.append("	p.websiteDisplayName, ");
        sql.append("	p.size, ");
        sql.append("	date_format(p.startTime,'%Y-%m-%d') as startTime, ");
        sql.append("	date_format(p.stopTime,'%Y-%m-%d') as stopTime, ");
        sql.append("	date_format(p.valueDate,'%Y-%m-%d') as valueDate, ");
        sql.append("	date_format(p.expiringDate,'%Y-%m-%d') as expiringDate, ");
        sql.append("	date_format(p.interestDate,'%Y-%m-%d') as interestDate, ");
        sql.append("	p.interestCycle, ");
        sql.append("	p.interestPaymentDescription as interestType, ");
        sql.append("	p.interestUnit, ");
        sql.append("	p. STATUS, ");
        sql.append("	p. incomeType, ");
        sql.append("  CASE p.`STATUS` ");
        sql.append(" WHEN 0 THEN ");
        sql.append("  '草稿' ");
        sql.append("  WHEN 1 THEN ");
        sql.append("   '发布待售' ");
        sql.append("  WHEN 2 THEN ");
        sql.append("   '在售' ");
        sql.append("  WHEN 3 THEN ");
        sql.append("   '暂停' ");
        sql.append("  WHEN 4 THEN ");
        sql.append("   '售完' ");
        sql.append("  WHEN 5 THEN ");
        sql.append("   '作废' ");
        sql.append("   END AS statusStr,");
        sql.append("	i.description AS productionDescription, ");
        sql.append("	floor(p.saleMoney / p.size * 100) as saleProcess, ");
        sql.append("	p.investTermView, ");
        sql.append("	p.saleMoney, ");
        sql.append("  (select min(c1.expectedYield) from crm_productionComposition c1 where c1.state = 0 and c1.productionId = p.id) as minExpectedYield, ");
        sql.append("  (select max(c1.expectedYield) from crm_productionComposition c1 where c1.state = 0 and c1.productionId = p.id) as maxExpectedYield, ");
        sql.append("  (select min(c1.sizeStart) from crm_productionComposition c1 where c1.state = 0 and c1.productionId = p.id) as minSizeStart ");
        sql.append("FROM ");
        sql.append("	crm_production p, ");
        sql.append(" crm_productionInfo i ");
        sql.append("WHERE ");
        sql.append("	p.state = 0 ");
        sql.append("AND i.state = 0 ");
        sql.append("AND p. STATUS = 2 ");
        sql.append("AND i.productionId = p.id ");
        sql.append("AND p.id = '" + productionId + "' ");



        pager = MySQLDao.query(sql.toString(), ProductionVO.class, 1, 1, conn);
        return pager;
    }



    public boolean checkIsOk(String productionId, double investMeney) throws Exception {

        if (StringUtils.isEmpty(productionId)) {
            throw new Exception("无法获得产品编号，查询失败");
        }

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     p.id productId,");
        sbSQL.append("     p.websiteDisplayName,");
        sbSQL.append("     p.`Name`,");
        sbSQL.append("     p.ValueDate,");
        sbSQL.append("     p.InterestDate,");
        sbSQL.append("     p.interestType,");
        sbSQL.append("     p.InvestTerm,");
        sbSQL.append("     p.InvestTermView,");
        sbSQL.append("     pc.ExpectedYield,");
        sbSQL.append("     pc.id compositionId,");
        sbSQL.append("     pc.SizeStart");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_production p,");
        sbSQL.append("     crm_productioncomposition pc");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND p.state = 0");
        sbSQL.append(" AND pc.state = 0");
        sbSQL.append(" and p.id=pc.ProductionId");
        sbSQL.append(" and p.id='" + Database.encodeSQL(productionId) + "'");
        sbSQL.append(" and pc.SizeStart <= " + investMeney);
        sbSQL.append(" and pc.SizeStop >= " + investMeney);
        sbSQL.append(" AND p.StopTime >= '"+TimeUtils.getNowDate()+"'");
        sbSQL.append(" AND p.StartTime <= '"+TimeUtils.getNowDate()+"'");




        List<ProductionWVO> list = MySQLDao.query(sbSQL.toString(), ProductionWVO.class, null);

        if (list == null || list.size() != 1) {
            return false;
        }

        return true;
    }


    public Pager list(ProductionVO productionVO, List<KVObject> conditions, String projectNames) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String productionStatus = "";
        String productionOrder = "";
        if (request.getParameter("req") == null) {
            productionStatus = request.getParameter("productionStatus");
            productionOrder = request.getParameter("productionOrder");
        } else {
            String reqStr = request.getParameter("req");
            JSONObject reqParam = JSONObject.fromObject(reqStr);
            if (reqParam.has("productionStatus")) {
                productionStatus = reqParam.getString("productionStatus");
                productionVO.setStatus(productionStatus);
            }
            if (reqParam.has("productionOrder")) {
                productionOrder = reqParam.getString("productionOrder");

            }

        }
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        if (projectNames != null && !projectNames.equals("")) {
            KVObject projectId = new KVObject("projectId", " in (" + projectNames + ")");
            conditions.add(projectId);
        }
        StringBuffer SQL = new StringBuffer();
        SQL.append("SELECT  a.productionDescription,a.InvestTerm, a.InvestTermView, a.paymentProcess,a.appointmentProcess,a.saleProcess,a.sid,a.id,a.productHomeId,a.productId, b.name projectName,a.size,a.contractCopies,a.ContractCount,a.name,a.startTime,a.stopTime,a.ValueDate,a.ExpiringDate,a.InterestDate,a.AppointmentMoney,a.SaleMoney,a.WebsiteDisplayName,\n");
        SQL.append(" case status when 0 then \"草稿\" when 1 then \"发布待售\"when 2 then \"在售\" when 3 then \"暂停\" when 4 then \"售完\"else \"作废\" end status\n");
        SQL.append(",interestType,interestCycle,interestTimes");
        SQL.append(" FROM crm_production a,crm_project b\n");
        SQL.append(" WHERE a.ProjectId= b.id and a.state=0 and b.state=0");


        // 增加按照起息日降序排序
        if (productionOrder != null && !productionOrder.equals("")) {
            if (productionOrder.equals("起息日升序")) {
                conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.ValueDate asc"));
            }
            if (productionOrder.equals("起息日降序")) {
                conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.ValueDate desc"));
            }
            if (productionOrder.equals("到期日升序")) {
                conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.ExpiringDate asc"));
            }
            if (productionOrder.equals("到期日降序")) {
                conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.ExpiringDate desc"));
            }
            if (productionOrder.equals("付息日升序")) {
                conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.InterestDate asc"));
            }
            if (productionOrder.equals("付息日降序")) {
                conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.InterestDate desc"));
            }
            if (productionOrder.equals("顺序升序")) {
                conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.sid asc"));
            }
            if (productionOrder.equals("顺序降序")) {
                conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.InterestDate desc"));
            }
        } else {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.sid desc"));
        }
//        if (productionStatus != null && !productionStatus.equals("")) {
//            SQL.append(" and a.status=2");
//        }
        Pager pager = Pager.query(SQL.toString(), productionVO, conditions, request, queryType);
        return pager;
    }

    public List<ProductionVO> getListProductionVO(ProductionVO productionVO, Connection conn) throws Exception {
        return productionDao.getListProductionVO(productionVO, conn);
    }


    public Pager getProductions (ProductionVO productionVO, int currentPage, int showRowCount, Connection conn) throws Exception {


        DatabaseSQL dbSQL = DatabaseSQL.newInstance("65FE1805");
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_  ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), productionVO, null, currentPage, showRowCount, queryType, conn);

        return pager;
    }


    /**
     * @description 获取在售产品信息
     *
     * @author 苟熙霖
     *
     * @date 2018/11/27 14:11
     * @param productionVO
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return com.youngbook.common.Pager
     * @throws Exception
     */
    public Pager getPagerProductionVO ( ProductionVO productionVO, int currentPage, int showRowCount, Connection conn ) throws Exception {

        Pager pager = productionDao.getPagerProductionVO( productionVO, currentPage, showRowCount,conn );




        if( pager == null ){
            MyException.newInstance( "当前暂无在售产品" ).throwException();
        }




        return pager;
    }


    /**
     * 网站：根据产品获取所有订单
     *
     * @param productionWVO
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager produFindOrder4Web(ProductionWVO productionWVO, List<KVObject> conditions, HttpServletRequest request) throws Exception {

        if (conditions == null) {
            conditions = new ArrayList<KVObject>();
        }
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT concat('**', right(c.`Name`, 1)) customerName,o.Money,o.createTime ");
        sbSQL.append("FROM crm_production p , crm_order o , crm_customerpersonal c ");
        sbSQL.append("WHERE p.id = o.ProductionId ");
        sbSQL.append("AND o.CustomerId = c.id ");
        sbSQL.append("AND p.state = 0 AND o.state = 0 AND c.state = 0 AND o.`Status` = 1 ");
        if (!StringUtils.isEmpty(productionWVO.getId())) {
            sbSQL.append("AND p.id = '" + Database.encodeSQL(productionWVO.getId()) + "' ");
        }
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "o.createTime DESC "));
        Pager pager = Pager.query(sbSQL.toString(), new OrderVO(), conditions, request, queryType);
        return pager;
    }

    /**
     * 移动端：根据产品编号获取所有订单
     *
     * @param id
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager getOrderByProductionId(String id, List<KVObject> conditions, HttpServletRequest request) throws Exception {

        if (conditions == null) {
            conditions = new ArrayList<KVObject>();
        }

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //编写sql语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT REPLACE(right(c.`Name`,2),SUBSTRING(c.`Name`, -2,1),'*')  customerName,o.Money, date_format(o.createTime, '%Y-%m-%d') as createTime ");
        sbSQL.append("FROM crm_production p , crm_order o , crm_customerpersonal c ");
        sbSQL.append("WHERE p.id = o.ProductionId ");
        sbSQL.append("AND o.CustomerId = c.id ");
        sbSQL.append("AND p.state = 0 AND o.state = 0 AND c.state = 0 AND o.`Status` = 1 ");
        if (!StringUtils.isEmpty(id)) {
            sbSQL.append("AND p.id = '" + Database.encodeSQL(id) + "' ");
        }

        //添加查询条件
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "o.createTime DESC "));

        //执行查询
        Pager pager = MySQLDao.search(sbSQL.toString(), null, new OrderVO(), conditions, 1, 30, queryType);
        return pager;
    }

    /**
     * 获取最大的产品编号
     *
     * @param conn 数据库链接
     * @return 最大产品编号+年份
     * @throws Exception
     */
    private static String getNewProductionNo(ProductionPO production, Connection conn) throws Exception {
        ProductionHomePO productionHome = new ProductionHomePO();
        productionHome.setId(production.getProductHomeId());
        productionHome.setState(Config.STATE_CURRENT);
        productionHome = MySQLDao.load(productionHome, ProductionHomePO.class, conn);

        if (productionHome == null) {
            MyException.newInstance("无法获得产品信息").throwException();
        }
        // 获取到当前所选产品头的顺序号
        String productionCode = productionHome.getProductionId();


        // 初始化分期参数
        int staging = 0;
        ProductionPO temp = new ProductionPO();
        temp.setProductHomeId(production.getProductHomeId());
        temp.setState(Config.STATE_CURRENT);
        List<ProductionPO> list = MySQLDao.query(temp, ProductionPO.class, conn);


        if (list == null) {
            MyException.newInstance("无法获得产品信息").throwException();
        }

        // 判断产品分期是否已有，如果已有直接累加上去
        staging = list.size() + 1;

        String newPorductNo = productionCode + StringUtils.buildNumberString(staging, 2);

        if (newPorductNo.length() != 11) {
            MyException.newInstance("创建新产品编号失败").throwException();
        }

        return newPorductNo;
    }


    /**
     * 新增或修改
     *
     * @param production
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ProductionPO production, String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            userId = Config.getDefaultOperatorId();
        }

        int count = 0;

        if (StringUtils.isEmpty(production.getProductionNo())) {
            // 获得产品编号
            String productionNo = ProductionService.getNewProductionNo(production, conn);

            production.setProductionNo(productionNo);
        }

        count = MySQLDao.insertOrUpdate(production, userId, conn);
        if (count != 1) {
            MyException.newInstance("创建产品失败").throwException();
        }

        if (count != 1) {
            MyException.newInstance("创建产品失败").throwException();
        }


        return 1;
    }

    /**
     * 创建人：张舜清
     * 创建时间：6/25/15
     * 描述：重载一个insertOrUpdate方法
     *
     * @param production
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int update(ProductionPO production, ContractApplicationPO contractApplication, UserPO user, Connection conn) throws Exception {
        int count = 0;
        HttpServletRequest request = ServletActionContext.getRequest();
        String contractCount = request.getParameter("contractApplication.counts");

        production.setId(contractApplication.getProductionId());
        production.setState(Config.STATE_CURRENT);
        production = MySQLDao.load(production, ProductionPO.class, conn);
        production.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(production, conn);
        if (count == 1) {
            production.setSid(MySQLDao.getMaxSid("crm_production", conn));
            production.setState(Config.STATE_CURRENT);
            production.setOperatorId(user.getId());
            production.setOperateTime(TimeUtils.getNow());
            production.setContractCount(Integer.parseInt(contractCount));
            count = MySQLDao.insert(production, conn);
            if (count != 1) {
                throw new Exception("数据库异常");
            }
        }
        return count;
    }


    public int del(ProductionPO production, UserPO user, Connection conn) throws Exception {
        int sta = 0;
        production = MySQLDao.load(production, ProductionPO.class);
        String id = production.getId();
        String sql = "SELECT * from crm_productioncomposition WHERE state=0 and productionId='" + Database.encodeSQL(id) + "'";
        List<ProductionCompositionPO> list = MySQLDao.query(sql, ProductionCompositionPO.class, null);
        if (list != null && list.size() != 0) {
            sta = 1;
        }
        return sta;
    }

    public int delete(ProductionPO production, ProductionInfoPO productionInfo, UserPO user, Connection conn) throws Exception {
        int count = 0;
        production = MySQLDao.load(production, ProductionPO.class);
        production.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(production, conn);

        if (count == 1) {
            production.setSid(MySQLDao.getMaxSid("crm_production", conn));
            production.setState(Config.STATE_DELETE);
            production.setOperateTime(TimeUtils.getNow());
            production.setOperatorId(user.getId());
            count = MySQLDao.insert(production, conn);
        } else {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 网站：根据 ID 获取产品，返回 Pager
     *
     * @param id
     * @return Pager
     * @throws Exception
     */
    public Pager getProductById4Web(String id, String money, ProductionWVO productionVO, HttpServletRequest request) throws Exception {
        List<KVObject> conditions = new ArrayList<KVObject>();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String sql = "SELECT a.*, b.expectedYield minExpectedYield, b.id compositionId " +
                "FROM crm_production a, crm_productioncomposition b " +
                "WHERE a.id = b.ProductionId " +
                "AND a.state = 0 " +
                "AND b.state = 0 " +
                "AND a.id = '" + Database.encodeSQL(id) + "' " +
                "AND b.sizeStart < '" + Database.encodeSQL(money) + "' " +
                "AND b.sizeStop >= '" + Database.encodeSQL(money) + "'";
        Pager pager = Pager.query(sql, productionVO, conditions, request, queryType);
        return pager;
    }



    /**
     * 网站：获取产品详情
     *
     * @param id
     * @return Pager
     * @throws Exception
     */
    public Pager getProductDetail4Web(String id, ProductionWVO productionVO, HttpServletRequest request) throws Exception {
        List<KVObject> conditions = new ArrayList<KVObject>();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String sql = "select p.*, i.description, c.expectedYield minExpectedYield, e.expectedYield maxExpectedYield " +
                "from crm_production p, crm_productioninfo i, crm_productioncomposition c, crm_productioncomposition e " +
                "where p.id = i.productionId " +
                "and p.id = c.productionId " +
                "and p.id = e.productionId " +
                "and c.expectedYield in (SELECT min(d.expectedYield) FROM crm_productioncomposition d WHERE d.ProductionId = p.id AND d.state = 0) " +
                "and e.expectedYield in (SELECT max(f.expectedYield) FROM crm_productioncomposition f WHERE f.ProductionId = p.id AND f.state = 0) " +
                "and i.state = 0 " +
                "and p.state = 0 " +
                "and c.state = 0 " +
                "and e.state = 0 " +
                "and p.id = '" + Database.encodeSQL(id) + "' " +
                "limit 0,1";
        Pager pager = Pager.query(sql, productionVO, conditions, request, queryType);
        return pager;
    }

    /**
     * 网站：列出投资列表
     * 修改：周海鸿
     * 时间：2015-7-23
     * 内容：添加条件 使网站出现的项目列表的类型是属于网站的
     *
     * @param productionWVO
     * @return
     * @throws Exception 修改人：姚章鹏
     *                   时间：2015年7月31日15:51:20
     *                   内容：修改sql语句。添加P.WebsiteDisplayName,查出网站显示的名称
     */
    public List<ProductionWVO> list4Website(ProductionWVO productionWVO, String customerTypeStr, Connection conn) throws Exception {

        List<ProductionWVO> list = new ArrayList<ProductionWVO>();

        HttpServletRequest request = ServletActionContext.getRequest();
        String productionStatus = request.getParameter("productionStatus");

        Pager pager = null;

        if("".equals(customerTypeStr)) {
            customerTypeStr = "0";
        }
        Integer customerType = Integer.parseInt(customerTypeStr);


        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT DISTINCT ");
        sbSQL.append("       p.*, ");
        sbSQL.append("       (SELECT MIN(c1.sizeStart) FROM crm_productionComposition c1 WHERE c1.state = 0 AND c1.productionId = p.id) sizeStart, ");
        sbSQL.append("       (SELECT MAX(c2.sizeStop) FROM crm_productionComposition c2 WHERE c2.state = 0 AND c2.productionId = p.id) sizeStop, ");
        sbSQL.append("       (SELECT MIN(c1.expectedYield) FROM crm_productionComposition c1 WHERE c1.state = 0 AND c1.productionId = p.id) minExpectedYield, ");
        sbSQL.append("       (SELECT MAX(c2.expectedYield) FROM crm_productionComposition c2 WHERE c2.state = 0 AND c2.productionId = p.id) maxExpectedYield, ");
        sbSQL.append("       i.description  ");
        sbSQL.append(" FROM ");
        sbSQL.append("       crm_productioninfo i, ");
        sbSQL.append("       crm_production p, ");
        sbSQL.append("       crm_productionComposition c ");
        sbSQL.append(" WHERE p.state = 0  ");
        sbSQL.append("       AND c.state = 0 ");
        sbSQL.append("       AND i.state = 0  ");
        sbSQL.append("       AND i.productionId = p.id  ");
        sbSQL.append("       AND c.productionId = p.id ");
        sbSQL.append("       AND p.status in (" + ProductionStatus.Sale + ", " + ProductionStatus.Sold + ")");

        // TODO 暂时用收益系数为 5 的产品在网上显示，后期添加网上显示字段后删除
        sbSQL.append("       AND p.incomeFactor = 5 ");

        // 增加按照起息日降序排序

        if (productionStatus != null && !productionStatus.equals("")) {
            sbSQL.append(" and p.status=2");
        }

        if (!StringUtils.isEmpty(productionWVO.getId())) {
            sbSQL.append(" and p.id='" + Database.encodeSQL(productionWVO.getId()) + "'");
        }


        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sbSQL.toString());
        list = MySQLDao.search(dbSQL, ProductionWVO.class, conn);
        //当前时间
        String now = TimeUtils.getNow("yyyy-mm-dd");

        for (int i = 0; list != null && i < list.size(); i++) {
            ProductionWVO p = (ProductionWVO) list.get(i);
            //计算结束时间与当前系统时间的差值
            int stoptimeDay = TimeUtils.getDayDifference(now, p.getStopTime(), "yyyy-mm-dd");
            if (stoptimeDay < 0) {
                p.setStopTimeDay("已结束");
            }
            else {
                p.setStopTimeDay(stoptimeDay + "天");
            }
        }
        return list;
    }

    /**
     *
     * 产品详情
     * 作者：quan.zeng
     * 内容：创建代码
     * 时间：2015-12-10
     *
     */
    public ProductionWVO getProductionDetail(ProductionWVO productionWVO, String customerTypeStr, String orderId, Connection conn)throws Exception {
        // 产品详情信息
       List<ProductionWVO> list = this.list4Website(productionWVO, customerTypeStr, conn);

        if (list != null && list.size() > 0) {   //修改为>0 目前数据存在重复数据 HOPEWEALTH-491
            productionWVO = (ProductionWVO) list.get(0);

            //通过订单编号获取订单信息
            OrderPO orderpo = orderDao.loadByOrderId(orderId, conn);
            //判断订单是否已经付款
            if (orderpo.getStatus() == OrderStatus.Saled) {
                //成功付款日期
                String payTime =  orderpo.getPayTime();

                //计算付息日
                //付息周期
                int interestCycle = productionWVO.getInterestCycle();
                //付息单位
                int interestUnit = productionWVO.getInterestUnit();

                String  interestDate ="";
                //判断付息单位
                if (PaymentPlanUnit.UnitDay == interestUnit) {
                    //按日
                    //计算天数
                    interestDate = TimeUtils.getTime(payTime, interestCycle, "DATE");
                } else if (PaymentPlanUnit.UnitMonth == interestUnit) {
                    //按月
                    interestDate = TimeUtils.getTime(payTime, interestCycle, "MONTH");
                } else if (PaymentPlanUnit.UnitYear == (interestUnit)) {
                    //按年
                    interestDate = TimeUtils.getTime(payTime, interestCycle, "YEAR");
                }

                // 付款时间
                Date payTimes = TimeUtils.getDateCommon(payTime);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 周期时间
                Date interestDates = sdf.parse(interestDate);

                // 计算天数
                Calendar cal = getInstance();
                cal.setTime(payTimes);
                long timeValue = cal.getTimeInMillis();

                cal.setTime(interestDates);
                long timeExpiring = cal.getTimeInMillis();

                long betweenDays = (timeExpiring - timeValue) / (1000 * 24 * 3600);

                //付息日
                productionWVO.setValueDate("成功购买后第 2 天");
                ///置付息日
                productionWVO.setInterestDate("成功购买后第 " + (betweenDays+1)+" 天");

            } else {
                //
                productionWVO.setValueDate("- -");
                //设置付息日
                productionWVO.setInterestDate("- - ");
            }
            return productionWVO;
        }
        return null;
    }


    /**
     * 网站：获取丰富条件查询的列表，并分页
     *
     * @param productionVO
     * @param conditions
     * @param request
     * @return
     * @throws Exception *修改人：姚章鹏
     *                   内容：在sql上添加 P.WebsiteDisplayName,查出网站显示的产品名字
     *                   时间：2015年7月31日15:44:59
     */
    public Pager list4WebMuitiQuery(ProductionWVO productionVO, String customerTypeStr, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {

        String productionStatus = request.getParameter("productionStatus");
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        Pager pager = null;

        if("".equals(customerTypeStr)) {
            customerTypeStr = "0";
        }
        Integer customerType = Integer.parseInt(customerTypeStr);


        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("     a.interestType, ");
        sqlDB.append("     a.InvestTerm, ");
        sqlDB.append("     a.InvestTermView, ");
        sqlDB.append("     a.websiteDisplayName, ");
        sqlDB.append("     a.paymentProcess, ");
        sqlDB.append("     a.sid, ");
        sqlDB.append("     a.id, ");
        sqlDB.append("     c.SizeStart,");
        sqlDB.append("     a.projectId, ");
        sqlDB.append("     a.productionNo, ");
        sqlDB.append("     b.NAME projectName, ");
        sqlDB.append("     a.size, ");
        sqlDB.append("     a.NAME, ");
        sqlDB.append("     a.startTime, ");
        sqlDB.append("     a.stopTime, ");
        sqlDB.append("     a.ValueDate, ");
        sqlDB.append("     a.ExpiringDate, ");
        sqlDB.append("     a.InterestDate, ");
        sqlDB.append("     a.AppointmentMoney, ");
        sqlDB.append("     a.SaleMoney, ");
        sqlDB.append("     c.expectedYield minExpectedYield, ");
        sqlDB.append("     e.expectedYield maxExpectedYield, ");
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
        sqlDB.append(" END STATUS ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_production a, ");
        sqlDB.append("     crm_project b, ");
        sqlDB.append("     crm_productionhome home, ");
        sqlDB.append("     crm_productioncomposition c, ");
        sqlDB.append("     crm_productioncomposition e, crm_productionInfo i ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1  ");
        sqlDB.append(" AND home.Id = a.productHomeId ");
        sqlDB.append(" AND home.ProjectId = b.id ");
        sqlDB.append(" AND a.id = c.productionId ");
        sqlDB.append(" AND a.id = e.productionId ");
        sqlDB.append(" AND c.expectedYield IN ( ");
        sqlDB.append("     SELECT ");
        sqlDB.append("         min(d.expectedYield) ");
        sqlDB.append("     FROM ");
        sqlDB.append("         crm_productioncomposition d ");
        sqlDB.append("     WHERE ");
        sqlDB.append("         d.ProductionId = a.id ");
        sqlDB.append("     AND d.state = 0 ");
        sqlDB.append(" )  ");
        sqlDB.append(" AND e.expectedYield IN ( ");
        sqlDB.append("     SELECT ");
        sqlDB.append("         max(f.expectedYield) ");
        sqlDB.append("     FROM ");
        sqlDB.append("         crm_productioncomposition f ");
        sqlDB.append("     WHERE ");
        sqlDB.append("         f.ProductionId = a.id ");
        sqlDB.append("     AND f.state = 0 ");
        sqlDB.append(" )  ");
        sqlDB.append(" AND home.State = 0 ");
        sqlDB.append(" AND a.state = 0  ");
        sqlDB.append(" AND b.state = 0  ");
        sqlDB.append(" AND c.state = 0  ");
        sqlDB.append(" AND e.state = 0 and i.state = 0 and i.productionId = a.id  ");
        sqlDB.append(" AND STATUS = 2 ");
        sqlDB.append(" AND a.incomeFactor = 5");
        sqlDB.append(" AND a.StopTime > SYSDATE() ");

        // 起投金额
        if (!StringUtils.isEmpty(request.getParameter("minMoney"))) {
            sqlDB.append(" AND a.AppointmentMoney >= " + Database.encodeSQL(request.getParameter("minMoney")));
        }
        if (!StringUtils.isEmpty(request.getParameter("maxMoney"))) {
            //没有最小起投金额
            if (StringUtils.isEmpty(request.getParameter("minMoney"))) {
                sqlDB.append(" AND a.AppointmentMoney >= " + Database.encodeSQL(request.getParameter("maxMoney")));
            } else {
                sqlDB.append(" AND a.AppointmentMoney <= " + Database.encodeSQL(request.getParameter("maxMoney")));
            }
        }
        // 投资期限
        if (!StringUtils.isEmpty(request.getParameter("minExpiringDay"))) {
            sqlDB.append(" AND a.InvestTermView >= " + Database.encodeSQL(request.getParameter("minExpiringDay")));
        }
        if (!StringUtils.isEmpty(request.getParameter("maxExpiringDay"))) {

            sqlDB.append(" AND a.InvestTermView <= " + Database.encodeSQL(Database.encodeSQL(request.getParameter("maxExpiringDay"))));
        }
        // 收益率
        if (!StringUtils.isEmpty(request.getParameter("minExpectedYield"))) {
            sqlDB.append(" AND c.expectedYield >= '" + Database.encodeSQL(request.getParameter("minExpectedYield")) + "'");
        }
        if (!StringUtils.isEmpty(request.getParameter("maxExpectedYield"))) {
            //最小收益率为null时
            if (StringUtils.isEmpty(request.getParameter("minExpectedYield"))) {

                sqlDB.append(" AND e.expectedYield >= '" + Database.encodeSQL(request.getParameter("maxExpectedYield")) + "'");
            } else {
                sqlDB.append(" AND e.expectedYield <= '" + Database.encodeSQL(request.getParameter("maxExpectedYield")) + "'");
            }
        }
        //设置 收益方式
        String productType = request.getParameter("productType");
        if (!StringUtils.isEmpty(productType)) {
            if (productType.equals("1")) {
                //一次性本息付款
                productType = PaymentPlanType.OncePaymentText;
            } else {
                throw new Exception("获取收益方式获取商品信息错误");
            }
            sqlDB.append(" AND a.interestType = '" + Database.encodeSQL(productType) + "'");
        }

        // 增加按照起息日降序排序
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "a.ValueDate desc"));
        pager = Pager.query(sqlDB.toString(), productionVO, conditions, request, queryType, conn);


        //循环办遍历 page 计算剩余天数
        List li = pager.getData();
        for (int i = 0; i < li.size(); i++) {
            ProductionWVO p = (ProductionWVO) li.get(i);
            long remainSeconds = TimeUtils.getSecondDifference(TimeUtils.getNowDate(), p.getStopTime(), TimeUtils.Format_YYYY_MM_DD);
            p.setStopTimeDay("已结束");
            if (remainSeconds > 0) {
                p.setStopTimeDay(remainSeconds / TimeUtils.getSecondsOfOneDay() + "天");
            }
        }
        return pager;
    }


    /**
     * 获取产品状态
     * 创建者：姚章鹏
     * 时间：2015年12月17日
     * 内容：根据产品 id 判断产品是否为在售，在售则返回 true
     *
     * @param productionId
     * @param conn
     * @return
     * @throws Exception
     */
    public Boolean getProductionStatus(String productionId, Connection conn) throws Exception {

        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append("select * from crm_production cp where cp.state = 0 and cp.id = ?");
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, productionId, parameters);

        List<ProductionPO> production = MySQLDao.search(sqlDB.toString(), parameters, ProductionPO.class, null, conn);
        if (production == null || production.size() == 0) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有此产品").throwException();
        }

        if (production.get(0).getStatus() == 2) {
            // 订单为在售状态
            return true;
        }

        return false;
    }

    /**
     * 网站：根据产品ID、客户ID获取订单
     *
     * @param productionId
     * @param customerId
     * @return
     * @throws Exception
     */
    public Pager produFindOrderCustomerId4Web(String productionId, String customerId, String orderId) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT o.id id,o.OrderNum orderNum,c.`Name` customerName,p.`Name` productionName, p.InvestTerm, p.InvestTermView,");
        sbSQL.append("o.description description,o.referralCode,o.createTime,o.CustomerId,o.Money money ,o.`Status` status,o.customerAttribute customerAttribute ");
        sbSQL.append(" FROM crm_production p , crm_order o , crm_customerpersonal c ");
        sbSQL.append(" WHERE p.id = o.ProductionId ");
        sbSQL.append(" AND o.CustomerId = c.id ");
        sbSQL.append(" AND p.state = 0 AND o.state = 0 AND c.state = 0 ");
        if (!StringUtils.isEmpty(productionId)) {
            sbSQL.append("AND p.id = '" + Database.encodeSQL(productionId) + "' ");
        }
        if (!StringUtils.isEmpty(customerId)) {
            sbSQL.append("AND o.CustomerId = '" + Database.encodeSQL(customerId) + "' ");
        }
        if (!StringUtils.isEmpty(orderId)) {
            sbSQL.append("AND o.id = '" + Database.encodeSQL(orderId) + "' ");
        }
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "o.createTime DESC "));
        Pager pager = Pager.query(sbSQL.toString(), new OrderVO(), conditions, request, queryType);
        return pager;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年8月18日14:34:28
     * 内容：传入最小金额、最大金额、最小天数、最大天数、最小年化率、最大年化率、数据库连接
     *
     * @param minMoney         最小金额
     * @param maxMoney         最大金额
     * @param minExpiringDay   最小天数
     * @param maxExpiringDay   最大天数
     * @param minExpectedYield 最小年化率
     * @param maxExpectedYield 最大年化率
     * @param conn
     * @return
     * @throws Exception
     */
    public List<ProductionWVO> getProductionList4Index(String minMoney, String maxMoney, String minExpiringDay, String maxExpiringDay, String minExpectedYield, String maxExpectedYield, Connection conn) throws Exception {

        String webProjectId = Config.getSystemConfig("web.webproject.id");

        // 初始化StringBuffer对象
        StringBuffer sqlDB = new StringBuffer();
        // 组装查询SQL语句
        sqlDB.append(" SELECT ");
        sqlDB.append("     a.interestType, ");
        sqlDB.append("     a.InvestTerm, ");
        sqlDB.append("     a.InvestTermView, ");
        sqlDB.append("     a.websiteDisplayName, ");
        sqlDB.append("     a.paymentProcess, ");
        sqlDB.append("     a.sid, ");
        sqlDB.append("     a.id, ");
        sqlDB.append("     a.projectId, ");
        sqlDB.append("     a.productId, ");
        sqlDB.append("     b. NAME projectName, ");
        sqlDB.append("     a.size, ");
        sqlDB.append("     a. NAME, ");
        sqlDB.append("     a.startTime, ");
        sqlDB.append("     a.stopTime, ");
        sqlDB.append("     a.ValueDate, ");
        sqlDB.append("     a.ExpiringDate, ");
        sqlDB.append("     a.InterestDate, ");
        sqlDB.append("     a.AppointmentMoney, ");
        sqlDB.append("     a.SaleMoney, ");
        sqlDB.append("     c.expectedYield minExpectedYield, ");
        sqlDB.append("     e.expectedYield maxExpectedYield, ");
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
        sqlDB.append(" END STATUS ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_production a, ");
        sqlDB.append("     crm_project b, ");
        sqlDB.append("     crm_productionhome home, ");
        sqlDB.append("     crm_productioncomposition c, ");
        sqlDB.append("     crm_productioncomposition e ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1  ");
        sqlDB.append(" AND b.id = '" + webProjectId + "' ");
        sqlDB.append(" AND home.Id = a.productHomeId ");
        sqlDB.append(" AND a.id = c.productionId ");
        sqlDB.append(" AND a.id = e.productionId ");
        sqlDB.append(" AND c.expectedYield IN ( ");
        sqlDB.append("     SELECT ");
        sqlDB.append("         min(d.expectedYield) ");
        sqlDB.append("     FROM ");
        sqlDB.append("         crm_productioncomposition d ");
        sqlDB.append("     WHERE ");
        sqlDB.append("         d.ProductionId = a.id ");
        sqlDB.append("     AND d.state = 0 ");
        sqlDB.append(" )  ");
        sqlDB.append(" AND e.expectedYield IN ( ");
        sqlDB.append("     SELECT ");
        sqlDB.append("         max(f.expectedYield) ");
        sqlDB.append("     FROM ");
        sqlDB.append("         crm_productioncomposition f ");
        sqlDB.append("     WHERE ");
        sqlDB.append("         f.ProductionId = a.id ");
        sqlDB.append("     AND f.state = 0 ");
        sqlDB.append(" )  ");
        sqlDB.append(" AND home.State = 0 ");
        sqlDB.append(" AND a.state = 0  ");
        sqlDB.append(" AND b.state = 0  ");
        sqlDB.append(" AND c.state = 0  ");
        sqlDB.append(" AND e.state = 0  ");
        sqlDB.append(" AND STATUS = 2 ");

        // 起投金额
        if (!StringUtils.isEmpty(minMoney)) {
            sqlDB.append(" AND a.AppointmentMoney >= " + Database.encodeSQL(minMoney));
        }
        if (!StringUtils.isEmpty(maxMoney)) {
            //没有最小起投金额
            if (StringUtils.isEmpty(minMoney)) {
                sqlDB.append(" AND a.AppointmentMoney >= " + Database.encodeSQL(maxMoney));
            } else {
                sqlDB.append(" AND a.AppointmentMoney <= " + Database.encodeSQL(maxMoney));
            }
        }

        // 投资期限
        if (!StringUtils.isEmpty(minExpiringDay)) {
            sqlDB.append(" AND a.InvestTerm >= " + Database.encodeSQL(minExpiringDay));
        }
        if (!StringUtils.isEmpty(maxExpiringDay)) {
            sqlDB.append(" AND a.InvestTerm <= " + Database.encodeSQL(maxExpiringDay));
        }

        // 收益率
        if (!StringUtils.isEmpty(minExpectedYield)) {
            sqlDB.append(" AND c.expectedYield >= '" + Database.encodeSQL(minExpectedYield) + "'");
        }
        if (!StringUtils.isEmpty(maxExpectedYield)) {
            //最小收益率为null时
            if (StringUtils.isEmpty(minExpectedYield)) {
                sqlDB.append(" AND e.expectedYield >= '" + Database.encodeSQL(maxExpectedYield) + "'");
            } else {
                sqlDB.append(" AND e.expectedYield <= '" + Database.encodeSQL(maxExpectedYield) + "'");
            }

        }

        // 返回action给action
        List<ProductionWVO> ProductionWVOList = MySQLDao.query(sqlDB.toString(), ProductionWVO.class, null, conn);
        return ProductionWVOList;
    }


    public ProductionSaleStatisticsVO getProductionSaleStatistics(String productionId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得产品编号").throwException();
        }

        ProductionSaleStatisticsVO statistics = new ProductionSaleStatisticsVO();


        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     sum(money) TotalSaleMoney,");
        sbSQL.append("     sum(RemainMoney) TotalRemainMoney,");
        sbSQL.append("     sum(TotalPaybackMoney) TotalPaybackMoney,");
        sbSQL.append("     sum(TotalTransferMoney) TotalTransferMoney");
        sbSQL.append(" FROM");
        sbSQL.append("     (");
        sbSQL.append("         SELECT");
        sbSQL.append("             o.Money,");
        sbSQL.append("             o.RemainMoney,");
        sbSQL.append("             o.TotalPaybackMoney,");
        sbSQL.append("             o.TotalTransferMoney");
        sbSQL.append("         FROM");
        sbSQL.append("             crm_order o");
        sbSQL.append("         WHERE");
        sbSQL.append("             o.state = 0");
        sbSQL.append("         AND o.ProductionId = ?");
        sbSQL.append("         AND o.`Status` IN (1, 5, 8, 12, 20)");
        sbSQL.append("     ) t;");

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, productionId);

        List<ProductionSaleStatisticsVO> list = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), ProductionSaleStatisticsVO.class, null, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return statistics;
    }


    /**
     * 销售产品
     *
     * @return 1：成功，0：失败
     */
    public int sell(String productionId, double money, Connection conn) throws Exception {

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("产品编号为空").throwException();
        }

        if (money == Double.MAX_VALUE || money <= 0) {
            MyException.newInstance("产品购买金额有误").throwException();
        }


        // 构建产品
        ProductionPO production = productionDao.loadProductionById(productionId, conn);

        if (production == null) {
            MyException.newInstance(Config.getWords4WebGeneralError(), "未找到产品").throwException();
        }


        // 检查产品是否已到期
        long remainSeconds = TimeUtils.getSecondDifference(TimeUtils.getNow(), production.getStopTime(), TimeUtils.Format_YYYY_MM_DD_HH_M_S);
        if (remainSeconds < 0) {
            MyException.newInstance(Config.getWords("w.production.unknown"), "产品已经停止销售").throwException();
        }

        // 检查可用额度
        double needSize = money;
        double remainSize = production.getSize() - getSaleMoney(production.getId(), conn);
        // 没有足够的额度
        if (remainSize < needSize) {
            MyException.newInstance(Config.getWords("w.production.nomoresize"), "没有足够的额度，产品：" + production.getName()).throwException();
        }

        // 是否售罄
        if (remainSize == needSize) {
            production.setStatus(ProductionStatus.Sold);
        }


        productionDao.insertOrUpdate(production, conn);

        return 1;
    }



    /**
     * 周海鸿
     * 2015-8-31
     * 根据产品ID获取产品的审核数据
     *
     * @return
     * @throws Exception
     */
    public ProductionVO getProdectYW(String productid, UserPO user, Connection conn) throws Exception {
        //创建SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("  SELECT");
        sbSQL.append("  	rl.CURRENTNODE currentNodeId,");
        sbSQL.append("  	rl.id routeListId,");
        sbSQL.append("  	rl.STATU currentStatus,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("  	cp.*");
        sbSQL.append("  FROM");
        sbSQL.append("  	workflow_routelist rl,");
        sbSQL.append("  	crm_production cp,");
        sbSQL.append("  	oa_bizroute ob");
        sbSQL.append("  WHERE");
        sbSQL.append("  	1 = 1");
        sbSQL.append("  	AND  cp.state=" + Config.STATE_CURRENT);
        sbSQL.append("  AND ob.Id_YWID = cp.id");
        sbSQL.append("  AND rl.YWID = cp.id");
        sbSQL.append("  AND rl.STATU IN (1)");
        sbSQL.append("  	AND cp.id = '" + com.youngbook.common.Database.encodeSQL(productid) + "'");
        sbSQL.append("  UNION");
        sbSQL.append("  	SELECT");
        sbSQL.append("  		1 currentNodeId,");
        sbSQL.append("  		0 routeListId,");
        sbSQL.append("  		rl.STATU currentStatus,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("  		cp.*");
        sbSQL.append("  	FROM");
        sbSQL.append("  		workflow_routelist rl,");
        sbSQL.append("  		crm_production cp,");
        sbSQL.append("  		oa_bizroute ob");
        sbSQL.append("  	WHERE");
        sbSQL.append("  		1 = 1");
        sbSQL.append("  	AND  cp.state=" + Config.STATE_CURRENT);
        sbSQL.append("  	AND ob.Id_YWID = cp.id");
        sbSQL.append("  	AND rl.YWID = cp.id");
        sbSQL.append(" AND rl.STATU IN (" + Config.getSystemVariable("paymentPlan.Route.intStatus.Cancel") + "," + Config.getSystemVariable("paymentPlan.Route.intStatus.Over") + ")");

        sbSQL.append("  	AND cp.id = '" + com.youngbook.common.Database.encodeSQL(productid) + "'");
        sbSQL.append("  UNION");
        sbSQL.append("  	SELECT");
        sbSQL.append("  		1 currentNodeId,");
        sbSQL.append("  		0 routeListId,");
        sbSQL.append("  		-1 currentStatus,");
        sbSQL.append(" " + BizRouteConfig.getBizRouteStr());
        sbSQL.append("  		cp.*");
        sbSQL.append("  	FROM");
        sbSQL.append("  		crm_production cp,");
        sbSQL.append("  		oa_bizroute ob");
        sbSQL.append("  	WHERE");
        sbSQL.append("  		1 = 1");
        sbSQL.append("  	AND  cp.state=" + Config.STATE_CURRENT);
        sbSQL.append("  	AND ob.Id_YWID = cp.id");
        sbSQL.append("  	AND cp.id = '" + com.youngbook.common.Database.encodeSQL(productid) + "'");
        sbSQL.append("		AND cp.id not IN (");
        sbSQL.append("			SELECT");
        sbSQL.append("				YWID");
        sbSQL.append("			FROM");
        sbSQL.append("				workflow_routelist rl");
        sbSQL.append("			WHERE");
        sbSQL.append("				1 = 1");
        sbSQL.append("		)");

        //查询获取数据域
        List<ProductionVO> productions = MySQLDao.query(sbSQL.toString(), ProductionVO.class, null, conn);

        //获取的数据不等于1
        if (productions.size() == 0) {
            //新增业务数据
            int count = BizRouteService.insertOrUpdate(productid, "", "", "",
                    Integer.parseInt(Config.getSystemVariable("WORKFLOWID.Product.Check")), true, user, conn);

            //业务数据添加成功
            if (count == 1) {
                productions.add(getProdectYW(productid, user, conn));
            }//添加失败
            else {
                MyException.deal(new Exception("业务数据添加失败！"));
            }
        } else if (productions.size() != 1) {
            MyException.deal(new Exception("获取产品审批数据失败！"));
        }
        return productions.get(0);
    }


    public Pager loadProduction(ProductionVO productionVO, List<KVObject> conditions, HttpServletRequest request) throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
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
        sqlDB.append("  case interestUnit when 0 then '按日' when 1 then '按月' when 2 then '按年' end interestUnit,");
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
        sqlDB.append(" AND product.id ='" + Database.encodeSQL(productionVO.getId()) + "'");
//
        Pager pager = Pager.query(sqlDB.toString(), productionVO, conditions, request, queryType);
        return pager;
    }

    public List<ProductionAgreementPO> getProductionAgreement(String orderId, String productId, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     cu.`Name` as customer_name,");
        sbSQL.append("     ccc.Number as Number,");
        sbSQL.append("     pr.investTermView,");
        sbSQL.append("     ord.createTime as createTime,");
        sbSQL.append("     cpc.ExpectedYield as expectedYiel,");
        sbSQL.append("     (select tif.Number from crm_customercertificate tif where tif.CustomerId=cu.id and tif.state=0) as id_number,");
        sbSQL.append("     pr.`Name` as production_name,");
        sbSQL.append("     (SELECT pro.`Name` from crm_project pro,crm_productionhome ho where ho.ProjectId=pro.id and ho.Id=pr.productHomeId and pro.state=0 and ho.State=0) as project_Name,");
        sbSQL.append("     (case ord.customerAttribute WHEN 0 THEN '个人客户' WHEN 1 THEN '机构客户' end) as customer_Attribute,");
        sbSQL.append("     ord.OrderNum as order_Num,");
        sbSQL.append("     truncate(ord.Money,2) as invest_Money,");
        sbSQL.append("     date_format(ord.createTime,'%Y-%m-%d') as invest_Date,");
        sbSQL.append("     pr.InterestTimes as total_Installment,");
        sbSQL.append("     date_format(ord.ValueDate,'%Y-%m-%d') as value_Date");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_order ord,crm_production pr,crm_customerpersonal cu,crm_productioncomposition cpc,crm_customercertificate ccc");
        sbSQL.append(" WHERE");
        sbSQL.append(" ord.ProductionId=pr.id and ord.CustomerId=cu.id ");
        sbSQL.append(" AND ord.state=0 and pr.state=0 and cu.state=0 and ccc.state=0 and cpc.state = 0");
        sbSQL.append(" and ord.id='" + Database.encodeSQL(orderId) + "'");
        sbSQL.append(" and pr.id='" + Database.encodeSQL(productId) + "'");
        sbSQL.append(" and cpc.id=ord.ProductionCompositionId ");
        sbSQL.append(" and cu.id=ccc.CustomerId");
        List<ProductionAgreementPO> list = MySQLDao.query(sbSQL.toString(),ProductionAgreementPO.class,null,conn);

        return list;
    }

    public double getSaleMoney(String productionId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得产品编号").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getSaleMoney", this);
        dbSQL.addParameter4All("productionId", productionId);
        dbSQL.initSQL();

        List<OrderDetailPO> orderDetails = MySQLDao.search(dbSQL, OrderDetailPO.class, conn);

        double saleMoney = 0;
        for (OrderDetailPO orderDetail : orderDetails) {
            saleMoney += orderDetail.getMoney();
        }

        return saleMoney;
    }

    public List<PaymentPlanPO> getPaymentPlan(String orderId, String productId, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     pay.OrderId as orderId,");
        sbSQL.append("     pay.PaymentTime as paymentTime,");
        sbSQL.append("     pay.Type,");
        sbSQL.append("     pay.TotalInstallment,");
        sbSQL.append("     round(pay.TotalProfitMoney, 2) AS totalProfitMoney,");
        sbSQL.append("     round(pay.TotalPaymentMoney, 2) AS totalPaymentMoney");
        sbSQL.append(" FROM");
        sbSQL.append("     Core_PaymentPlan pay");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" and pay.State=0");
        sbSQL.append(" and pay.ProductId='" + Database.encodeSQL(productId) + "'");
        sbSQL.append(" and pay.OrderId='" + Database.encodeSQL(orderId) + "'");
        List<PaymentPlanPO> list = MySQLDao.query(sbSQL.toString(), PaymentPlanPO.class, null, conn);
        return list;
    }

}
