package com.youngbook.service.production;

import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.*;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IOrderDetailDao;
import com.youngbook.dao.production.IProductionCompositionDao;
import com.youngbook.dao.production.IProductionDao;
import com.youngbook.dao.sale.IPaymentPlanDao;
import com.youngbook.dao.sale.ISalemanGroupDao;
import com.youngbook.dao.sale.contract.IContractDao;
import com.youngbook.dao.system.IDepartmentDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.common.TimePO;
import com.youngbook.entity.po.core.TransferPO;
import com.youngbook.entity.po.core.TransferTargetType;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.production.*;
import com.youngbook.entity.po.sale.*;
import com.youngbook.entity.po.sale.contract.ContractPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.production.OrderDetailVO;
import com.youngbook.entity.vo.production.OrderReportMonthlyVO;
import com.youngbook.entity.vo.production.OrderReportWeeklyVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.entity.vo.system.MenuVO;
import com.youngbook.entity.wvo.production.ProductionWVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.allinpay.AllinpayBatchPaymentService;
import com.youngbook.service.core.IMoneyTransferService;
import com.youngbook.service.customer.CustomerDistributionService;
import com.youngbook.service.pay.FuiouDirectService;
import net.sf.json.JSONArray;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component("orderService")
public class OrderService extends BaseService {

    @Autowired
    IOrderDao orderDao;

    @Autowired
    IOrderDetailDao orderDetailDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    ISalemanGroupDao salemanGroupDao;

    @Autowired
    IProductionCompositionDao productionCompositionDao;

    @Autowired
    ICustomerMoneyDao customerMoneyDao;

    @Autowired
    ICustomerMoneyLogDao customerMoneyLogDao;

    @Autowired
    IProductionDao productionDao;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    ICustomerInstitutionDao customerInstitutionDao;

    @Autowired
    IPaymentPlanDao paymentPlanDao;

    @Autowired
    ProductionService productionService;

    @Autowired
    IContractDao contractDao;

    @Autowired
    ICustomerAccountDao customerAccountDao;

    @Autowired
    ICustomerDistributionDao customerDistributionDao;

    @Autowired
    CustomerDistributionService customerDistributionService;

    @Autowired
    IDepartmentDao departmentDao;

    @Autowired
    ILogDao logDao;

    public int saveReferralCode(String orderId, String referralCode, String userId, Connection conn) throws Exception {

        OrderPO order = orderDao.loadByOrderId(orderId, conn);

        if (order == null) {
            MyException.newInstance("保存订单推荐码失败", "订单号：" + orderId).throwException();
        }

        order.setReferralCode(referralCode);

        /**
         * 自动分配此客户
         */
        UserPO userPO = userDao.loadByReferralCode(referralCode, conn);

        UserPositionInfoPO userPositionInfo = departmentDao.getDefaultUserPositionInfo(userPO.getId(), conn);

        SalemanGroupPO salemanGroup = salemanGroupDao.getDefaultSalemanGroupByUserId(userPO.getId(), conn);

        customerDistributionDao.distributeCustomer(order.getCustomerId(), userPO.getId(), salemanGroup.getId(), userPositionInfo.getDepartmentId(), 0, true, conn);


        orderDao.insertOrUpdate(order, userId, conn);
        return 1;
    }


    /**
     * 修改订单产品
     *
     * 订单没有兑付的情况下，可以修改订单所对应的产品
     *
     * @author leevits
     *
     * @param orderId 订单编号
     * @param productionId 产品编号
     * @param productionCompositionId 产品构成编号
     * @param money 订单金额
     * @param userId 操作员编号
     * @param connection 数据库连接
     * @return 更新后的订单对象
     * @throws Exception 常规异常
     */
    public OrderPO updateOrderProduction(String orderId, String productionId, String productionCompositionId, double money, String userId, Connection connection) throws  Exception {


        /**
         * 查询是否有该订单，若没有则不能修改
         */
        OrderPO orderPO = orderDao.loadByOrderId(orderId, connection);
        if (orderPO == null) {
            MyException.newInstance("暂无该订单信息，修改失败", "订单号：" + orderId).throwException();
        }




        /**
         * 判断订单状态，以下状态订单不能修改产品信息
         *
         * 部分兑付或已兑付的订单，不能修改订单产品
         */
        if (orderPO.getStatus() == 8) {
            MyException.newInstance("当前订单已部分兑付，无法修改", "订单号：" + orderId).throwException();
        }
        if (orderPO.getStatus() == 5) {
            MyException.newInstance("当前订单已全部兑付，无法修改", "订单号：" + orderId).throwException();
        }





        //查询产品构成对象
        ProductionCompositionPO compositionPO = productionCompositionDao.getProductionCompositionPOByProductionIdAndMoney(productionId, money, connection);
        if(compositionPO == null ){
            MyException.newInstance("暂无相关产品构成信息,请确认后再操作", "订单号：" + orderId).throwException();
        }



        //获取预期收益率
        double expectedYield = compositionPO.getExpectedYield();




        //设置产品相关的信息
        orderPO.setProductionId(productionId);
        orderPO.setProductionCompositionId(productionCompositionId);
        orderPO.setExpectedYield(expectedYield);




        orderPO = orderDao.insertOrUpdate(orderPO, userId, connection);




        return orderPO;
    }


    public OrderPO loadByOrderId(String orderId, Connection conn) throws Exception {
        return orderDao.loadByOrderId(orderId, conn);
    }

    public OrderVO loadOrderVO(String mobile, String payCode, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("2A811712");
        dbSQL.addParameter4All("mobile", mobile);
        dbSQL.addParameter4All("payCode", payCode);
        dbSQL.initSQL();

        List<OrderVO> listOrderVOs = MySQLDao.search(dbSQL, OrderVO.class, conn);

        if (listOrderVOs != null && listOrderVOs.size() == 1) {
            return listOrderVOs.get(0);
        }

        return null;
    }


    public OrderVO loadOrderVOByOrderId(String orderId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("2A811712");
        dbSQL.addParameter4All("orderId", orderId);
        dbSQL.initSQL();

        List<OrderVO> listOrderVOs = MySQLDao.search(dbSQL, OrderVO.class, conn);

        if (listOrderVOs != null && listOrderVOs.size() == 1) {

            OrderVO orderVO = listOrderVOs.get(0);
            orderVO.setPayCode(StringUtils.lastString(orderVO.getOrderNum(), 6));

            // 投资日期
            if (!StringUtils.isEmpty(orderVO.getPayTime())) {
                orderVO.setPayDate(TimeUtils.format(orderVO.getPayTime(), TimeUtils.Format_YYYY_MM_DD_HH_M_S, TimeUtils.Format_YYYY_MM_DD));
            }


            // 格式化起息日
            if (!StringUtils.isEmpty(orderVO.getValueDate())) {
                orderVO.setValueDate(TimeUtils.format(orderVO.getValueDate(), TimeUtils.Format_YYYY_MM_DD_HH_M_S, TimeUtils.Format_YYYY_MM_DD));
            }


            // 格式化投资金额
            if (orderVO.getMoney() != Double.MAX_VALUE) {
                orderVO.setMoneyString(MoneyUtils.format2String(orderVO.getMoney()));
            }

            // 银行账号
            if (!StringUtils.isEmpty(orderVO.getBankNumber())) {
                orderVO.setBankNumber(AesEncrypt.decrypt(orderVO.getBankNumber()));
            }

            return listOrderVOs.get(0);
        }

        return null;
    }

    /**
     * 计算返佣到订单
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月20日
     *
     * @param order
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPO setCommission2Order(OrderPO order, Connection conn) throws Exception {

        // 各种用到的服务



        // 检验订单
        if(order == null) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "程序异常").throwException();
        }
        // 产品构成编号
        String compositionId = order.getProductionCompositionId();
        // 获取产品构成
        ProductionCompositionPO composition = productionCompositionDao.getByProductionId(compositionId, conn);
        if(composition == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }

        // 获取推荐人
        String referralCode = order.getReferralCode();
        // 获取订单金额
        Double money = order.getMoney();

        // sale_4w 所在销售组
        String sale4wGroup = Config.getSystemVariable("web.default.saleGroupId");
        // sale_4w 用户编号
        String sale4wUserId = Config.getSystemConfig("web.default.saleId");

        SalemanGroupPO groupPO = null;

        // 先设定 sale_4w 的销售组
        if(StringUtils.isEmpty(referralCode)) {
            groupPO = salemanGroupDao.loadSalemanGroupPO(sale4wGroup, conn);
        }

        // 先查询内部的销售人员
        UserPO saleman = userDao.loadByReferralCode(referralCode, conn);
        if(saleman != null) {
            groupPO = salemanGroupDao.loadBySaleman(saleman.getId(), conn);
            if(groupPO == null) {
                groupPO = salemanGroupDao.loadSalemanGroupPO(sale4wGroup, conn);
            }
        }
        // 其他情况一律都是 sale_4w 的销售组
        else {
            groupPO = salemanGroupDao.loadSalemanGroupPO(sale4wGroup, conn);
        }

        // 获取返佣等级
        String level = composition.getCommissionLevel();
        // 是否开启返佣修正
        Integer needCorrection = composition.getNeedCommissionCorrection();
        // 是否开启客户类型修正
        Integer needCustomerTypeCorrection = composition.getNeedCustomerTypeCommissionCorrection();

        // 计算返佣率
        Double commissionRate = MoneyUtils.calcCommissionRate(level, groupPO.getAreaId(), needCorrection, needCustomerTypeCorrection, conn);
        // 计算佣金
        Double commissionMoney = MoneyUtils.calcCommissionMoney(money, level, groupPO.getAreaId(), needCorrection, needCustomerTypeCorrection, conn);

        order.setCommissionLevel(level);
        order.setCommissionRate(commissionRate);
        order.setCommissionMoney(commissionMoney);

        return order;

    }

    /**
     * 通过客户的手机号码和推荐人的手机号码，校验两者是否有记录
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月5日
     *
     * @param mobile
     * @param referralCode
     * @param conn
     * @return
     * @throws Exception
     */
    public static Boolean valiteCustomerAndReferralCode(String mobile, String referralCode, Connection conn) throws Exception {

        String sql = "select * from crm_order o, crm_customerpersonal p where o.state = 0 and p.state = 0 and o.customerId = p.id and p.mobile = '" + mobile + "' and o.referralCode = '" + referralCode + "'";
        List<OrderVO> orders = MySQLDao.query(sql, OrderVO.class, new ArrayList<KVObject>(), conn);

        return orders.size() > 0 ? true : false;

    }

    /**
     * 获取对外销售的客户即将到期的订单列表
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月26日
     *
     * @param customerId
     * @param referralCode
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager getPreFinishOrders(String customerId, String referralCode, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {

        // 获取即将到期产品的时间临界值
        Integer preExpireValue = Config.getSystemVariableAsInt("web.production.preExpire.value");
        // 组装 SQL
        String sql = "select o.* from crm_order o, crm_productionComposition c, crm_production p where p.state = 0 and o.productionId = p.id and o.state = 0 and c.state = 0 and o.customerId = '" + customerId + "' and o.status = " + OrderStatus.Saled + " and o.productionCompositionId = c.id and timeStampDiff(month, p.expiringDate, '" + TimeUtils.getNow() + "') = " + preExpireValue;
        // 查询数据
        Pager pager = MySQLDao.search(sql, new ArrayList<KVObject>(), new OrderVO(), new ArrayList<KVObject>(), currentPage, showRowCount, null);

        return pager;

    }

    /**
     * 获取对外销售客户的订单接口
     * <p/>
     * 作者：曾威恺
     * 内容：创建代码
     * 时间：2016年3月28日
     *
     * @param orderId
     * @param conn
     * @return
     * @throws Exception
     */
    public List<OrderVO> getCustomerOuterOrder(String orderId, Connection conn) throws Exception {
        // 查询语句
        StringBuffer sb = new StringBuffer();
        sb.append("  SELECT DISTINCT");
        sb.append("  	ord.id AS id,");
        sb.append("  	ord.CustomerId AS customerId,");
        sb.append("  	ord.ProductionId AS productionId,");
        sb.append("  	ord.salemanId AS salemanId,");
        sb.append("  	cst.`Name` AS customerName,");
        sb.append("  	prd.`Name` AS ProductionName,");
        sb.append("  	prd.Size AS productionScale,");
        sb.append("  	prd.InvestTermView AS productionTerm,");
        sb.append("  	prd.interestType AS interestType,");
        sb.append("  	ord.Money AS money,");
        sb.append("  	(");
        sb.append("  		SELECT");
        sb.append("  			SUM(core.TotalProfitMoney)");
        sb.append("  		FROM");
        sb.append("  			core_paymentplan AS core");
        sb.append("  		WHERE");
        sb.append("  			core.State = 0");
        sb.append("  		AND (");
        sb.append("  			core.`Status` = 0");
        sb.append("  			OR core.`Status` = 1");
        sb.append("  		)");
        sb.append("  		AND core.OrderId = ord.id");
        sb.append("  	) AS expectedProfit,");
        sb.append("  	ord.PayTime AS payTime,");
        sb.append("  	cps.ExpectedYield AS expectedYield,");
        sb.append("  	ord.bankCode AS payBank,");
        sb.append("  	ord.bankCard AS bankNumber");
        sb.append("  FROM");
        sb.append("  	crm_order AS ord,");
        sb.append("  	crm_production AS prd,");
        sb.append("  	crm_productioncomposition AS cps, ");
        sb.append("  	crm_customerpersonal AS cst ");
        sb.append("  WHERE");
        sb.append("  	1 = 1");
        sb.append("  AND ord.CustomerId = cst.id");
        sb.append("  AND ord.ProductionId = prd.id");
        sb.append("  AND ord.ProductionCompositionId = cps.id");
        sb.append("  AND ord.state = 0");
        sb.append("  AND cst.state = 0");
        sb.append("  AND prd.state = 0");
        sb.append("  AND cps.state = 0");
        sb.append("  AND ord.id = ?");

        // 数据库
        DatabaseSQL databaseSQL = new DatabaseSQL();
        // 增加查询语句
        databaseSQL.newSQL(sb.toString());
        // 增加参数
        databaseSQL.addParameter(1, orderId);
        // 查询结果
        List<OrderVO> list = MySQLDao.search(databaseSQL.getSQL(), databaseSQL.getParameters(), OrderVO.class, null, conn);

        return list;
    }

    public List<OrderPO> getOrdersByCustomerId (String customerId, int orderStatus, String referralCode, Connection conn) throws Exception {

//        if (StringUtils.isEmpty(customerId)) {
//            MyException.newInstance("无法获得客户编号").throwException();
//        }


        String sql = "select * from crm_order o where o.state=0 and o.customerId=? and o.status=?";
        // DatabaseSQL dbSQL = DatabaseSQL.getInstance(sql).addParameter(1, customerId).addParameter(2, orderStatus);
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getOrdersByCustomerId", "OrderServiceSQL", OrderService.class);
        dbSQL.addParameter4All("customerId", customerId);
        dbSQL.addParameter4All("orderStatus", orderStatus);
        dbSQL.addParameter4All("referralCode", referralCode);
        dbSQL.initSQL();

        List<OrderPO> orders = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), OrderPO.class, null, conn);

        return orders;
    }


    /**
     * HOPEWEALTH-1305<br/>
     * 通过referee和status获取所有订单信息，关联crm_order, crm_production, crm_customerpersonal, crm_productioncomposition, crm_productioninfo
     *
     * @return
     * @throws Exception
     */
    public Pager getAllOrder(String customerId, String salemanId, String status, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {

        // SQL
        StringBuffer sb = new StringBuffer();

        sb.append(" select  ");
        sb.append(" 	o.id, ");
        sb.append(" 	o.customerId, ");
        sb.append("   p.name as customerName, ");
        sb.append("   i.websiteDisplayName as productionName, ");
        sb.append(" 	o.productionId, ");
        sb.append(" 	o.productionCompositionId, ");
        sb.append(" 	o.money, ");
        sb.append("   c.expectedYield, ");
        sb.append(" 	o.commissionMoney, ");
        sb.append(" 	o.commissionRate, ");
        sb.append(" 	o.status, ");
        sb.append(" 	o.payTime, ");
        sb.append(" 	o.salemanId, ");
        sb.append(" 	o.referralCode ");
        sb.append(" from crm_order o, crm_customerPersonal p, crm_productioncomposition c, crm_productionInfo i  ");
        sb.append(" where o.state = 0  ");
        sb.append(" and i.state = 0 ");
        sb.append(" and p.state = 0 ");
        sb.append(" and c.state = 0 ");
        sb.append(" and p.id = o.customerId ");
        sb.append(" and c.id = o.productionCompositionId ");
        sb.append(" and i.productionId = o.productionId ");
        sb.append(" and o.salemanId = '" + salemanId + "'  ");
        sb.append(" and o.status = " + status + " ");
        sb.append(" and o.customerId = '" + customerId + "'  ");

        Pager pager = MySQLDao.search(sb.toString(), new ArrayList<KVObject>(), new OrderVO(), new ArrayList<KVObject>(), currentPage, showRowCount, null, conn);

        return pager;
    }

    public List<OrderPO> getWaiting4ConfirmAllinpayOrders(String date, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     o.*");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_order o,");
        sbSQL.append("     bank_allinpaytransfer ao");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND o.state = 0");
        sbSQL.append(" AND ao.State = 0");
        sbSQL.append(" AND o.`Status` IN (0, 7)");
        sbSQL.append(" AND o.id = ao.BizId");
        sbSQL.append(" AND DATE_FORMAT(o.createTime, '%Y-%m-%d') = ?");

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, date);

        List<OrderPO> orders = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), OrderPO.class, null, conn);

        return orders;
    }

    /**
     * 查询当天未支付的订单
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月23日
     *
     * @param date
     * @param conn
     * @return
     * @throws Exception
     */
    public List<OrderPO> getWaiting4ConfirmFuiouOrders(String date, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT o.* FROM crm_order o WHERE 1 = 1 AND o.state = 0 AND o.status IN (0, 7) AND DATE_FORMAT(o.createTime, '%Y-%m-%d') = ?");

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, date);

        List<OrderPO> orders = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), OrderPO.class, null, conn);

        return orders;
    }

    /**
     * 通过 ID 获取订单
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月10日
     *
     * @param orderId
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPO getOrderById(String orderId, Connection conn) throws Exception {

        OrderPO orderPO = new OrderPO();
        orderPO.setId(orderId);
        orderPO.setState(Config.STATE_CURRENT);
        return MySQLDao.load(orderPO, OrderPO.class, conn);

    }


    public ReturnObject moneyTransfer2Gongda(String orderId, String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(orderId)) {
            MyException.newInstance("订单号为空，请检查").throwException();
        }

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("moneyTransfer2Gongda", "OrderServiceSQL", OrderService.class);
        dbSQL.addParameter4All("orderId", orderId);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");


        List<OrderPO> orders = MySQLDao.search(dbSQL, OrderPO.class, conn);

        if (orders == null || orders.size() != 1) {
            MyException.newInstance("查找订单信息失败，请确认是否是通宝订单，或者此订单是否已经转账成功", "订单编号: " + orderId).throwException();
        }

        OrderPO order = orders.get(0);

        if (order.getMoneyTransferStatus() == OrderMoneyTransferStatus.Transfered) {
            MyException.newInstance("该订单已经转账，请勿重复操作", "订单编号: " + orderId).throwException();
        }

        if(!NumberUtils.checkNumberIn(order.getPayChannel(), OrderPayChannel.ALLINPAY, OrderPayChannel.FUYOU)) {
            MyException.newInstance("转账失败，该订单不是通联或富友渠道订单", "订单编号: " + orderId).throwException();
        }

        if (order.getMoneyTransferStatus() != 0) {
            MyException.newInstance("转账失败，该订单已经转账，请勿重复转账", "订单编号: " + orderId).throwException();
        }


        double money = order.getMoney();

        List<TransferPO> transfers = new ArrayList<TransferPO>();
        TransferPO transfer = new TransferPO();
        transfer.setTargetAccountName(Config.getSystemConfig("order_moneytransfer.gongda.account_name"));
        transfer.setTargetType(TransferTargetType.Company);
        transfer.setTargetAccountNo(Config.getSystemConfig("order_moneytransfer.gongda.account_number"));
        transfer.setTargetBank(Config.getSystemConfig("order_moneytransfer.gongda.account_bank_code"));
        transfer.setMoney(money);

        transfers.add(transfer);

        IMoneyTransferService moneyTransferService = null;
        if (order.getPayChannel() == OrderPayChannel.ALLINPAY) {
            moneyTransferService = new AllinpayBatchPaymentService();
        }
        else if (order.getPayChannel() == OrderPayChannel.FUYOU) {
            moneyTransferService = new FuiouDirectService();
        }
        else {
            MyException.newInstance("订单转账请求失败，订单支付渠道不支持。").throwException();
        }

        ReturnObject returnObject = moneyTransferService.doTransfer(transfers, conn);


        if (returnObject.getCode() == ReturnObject.CODE_SUCCESS) {
            order.setMoneyTransferStatus(OrderMoneyTransferStatus.Transfered);
            order.setMoneyTransferTime(TimeUtils.getNow());

            orderDao.insertOrUpdate(order, userId, conn);
        }


        return returnObject;
    }





    /**
     * 通过订单编号查询订单
     * @param orderNo
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPO getOrderByOrderNo(String orderNo, Connection conn) throws Exception {
        return orderDao.getOrderByOrderNo(orderNo, conn);
    }


    public boolean checkSignature(OrderPO order, String signature, Connection conn) throws Exception {
        String tempSignature = getSignature(order, conn);
        return tempSignature.equals(signature);
    }

    public String getSignature(OrderPO order, Connection conn) throws Exception  {

        String mask = "1544b8649c8adbe0af99bc9ee2cfbf5c";

        StringBuffer sbSignature = new StringBuffer();

        sbSignature.append(order.getCustomerId());
        sbSignature.append(order.getProductionId());
        sbSignature.append(order.getProductionCompositionId());
        sbSignature.append(order.getMoney());

        String bankNumber = "";
        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByOrderId(order.getId(), conn);
        if (customerAccountPO != null) {
            bankNumber = customerAccountPO.getNumber();
        }
        sbSignature.append(bankNumber);

        sbSignature.append(order.getAppointmentTime());
        sbSignature.append(order.getPayTime());
        sbSignature.append(mask);

        return MD5Utils.MD5Encode(sbSignature.toString());
    }

    public int saleOrderOnlineFormNow(OrderPO order, String userId, Connection conn) throws Exception {
        String now = TimeUtils.getNow();
        String createTime = now;
        String payTime = now;
        String valueDate = TimeUtils.getTime(now, 1, TimeUtils.DATE);

        order.setCreateTime(createTime);
        order.setPayTime(payTime);
        order.setValueDate(valueDate);

        return saleOrder(order, userId, conn);
    }


    public int transferOrder(String orderId, double transferMoney, int orderStatusTransferType, String transferTime, String operatorId, Connection conn) throws Exception {

        OrderPO order = orderDao.loadByOrderId(orderId, conn);

        if (order == null) {
            MyException.newInstance("无法获得订单信息").throwException();
        }

        if (order.getMoney() < transferMoney) {
            MyException.newInstance("转让金额有误").throwException();
        }


        if (!NumberUtils.checkNumberIn(order.getStatus(), OrderStatus.Saled, OrderStatus.Feedback1, OrderStatus.Feedback2)) {
            MyException.newInstance("该订单状态不能转让").throwException();
        }

        if (orderStatusTransferType != OrderStatus.Transfered) {
            MyException.newInstance("订单将要进行的转让操作失败，转让类型错误").throwException();
        }

        order.setStatus(orderStatusTransferType);

        orderDao.insertOrUpdate(order, operatorId, conn);

        orderDetailDao.saveOrderDetail(order, transferMoney, transferTime, orderStatusTransferType,  "订单转让", operatorId, conn);

        return 1;
    }



    /**
     * 订单兑付
     *
     * Date: 2016-05-18 11:07:23
     * Author: leevits
     */
    public int paybackOrder(String orderId, double paybackMoney, int orderStatusPaybackType, String paybackTime, String operatorId, Connection conn) throws Exception {

        OrderPO order = orderDao.loadByOrderId(orderId, conn);

        if (order == null) {
            MyException.newInstance("无法获得订单信息").throwException();
        }


        if (order.getMoney() < paybackMoney) {
            MyException.newInstance("兑付金额有误").throwException();
        }

        if (order.getStatus() != OrderStatus.Saled && order.getStatus() != OrderStatus.PaybackSomePart &&
                order.getStatus() != OrderStatus.Feedback1 && order.getStatus() != OrderStatus.Feedback2) {
            MyException.newInstance("该订单状态不能兑付").throwException();
        }

        if (orderStatusPaybackType != OrderStatus.Payback && orderStatusPaybackType != OrderStatus.PaybackSomePart) {
            MyException.newInstance("订单将要进行的兑付操作失败，兑付类型错误").throwException();
        }

        order.setPaybackTime(paybackTime);
        order.setStatus(orderStatusPaybackType);


        /**
         *  保存订单信息
         *
         * Date: 2016-05-20 9:50:30
         * Author: leevits
         */
        orderDao.insertOrUpdate(order, operatorId, conn);

        orderDetailDao.saveOrderDetail(order, paybackMoney, paybackTime, order.getStatus(), "订单兑付", operatorId, conn);


        return 1;
    }


    public boolean checkSignatureExists(OrderPO order, Connection conn) throws Exception {
        String signature = getSignature(order, conn);
        return orderDao.checkSignatureExists(signature, conn);
    }

    public OrderPO saveOrder(OrderPO order, String userId, Connection conn) throws Exception {

        orderDao.insertOrUpdate(order, userId, conn);

        return order;
    }

    public OrderPO financeConfirm01(OrderPO order, String userId, Connection conn) throws Exception {


        /**
         * 保存订单明细
         */
        orderDetailDao.saveOrderDetail(order, order.getMoney(), TimeUtils.getNow(), OrderStatus.FinanceConfirm01, "财务一次审核", userId, conn);

        orderDao.insertOrUpdate(order, userId, conn);

        return order;
    }

    /**
     * 执行订单的销售
     *
     * 此流程将做以下操作：
     *
     * 1. 销售产品
     * 2. 设置订单打款状态
     * 3. 生成兑付计划
     * 4. 生成客户资金记录
     *
     * @param order
     * @param userId
     * @param conn
     * @return
     * @throws Exception
     */
    public int saleOrder(OrderPO order, String userId, Connection conn) throws Exception {


        /**
         *
         * 检查数据有效性
         *
         */

        if (order == null) {
            MyException.newInstance("订单数据为空").throwException();
        }


        /**
         * 下单之前的订单若不为一下几种状态，则不能完成下单。
         */
        OrderPO orderTemp = orderDao.loadByOrderId(order.getId(), conn);
        if (!NumberUtils.checkNumberIn(orderTemp.getStatus(), OrderStatus.Appointment, OrderStatus.AppointmentWaiting, OrderStatus.SaleAndWaiting, OrderStatus.AppointmentCancel,OrderStatus.FinanceConfirm01)) {
            String statusName = OrderStatus.getStatusName(orderTemp.getStatus());

            logDao.save("订单支付", "订单支付失败","该订单状态有误，此订单状态为【"+orderTemp.getStatus()+"】【"+statusName+"】" + "orderId=" + order.getId() + "&customerName=" + order.getCustomerName());

            MyException.newInstance("该订单状态有误，此订单状态为【"+statusName+"】", "orderId=" + order.getId() + "&customerName=" + order.getCustomerName()).throwException();
        }

        /**
         * 保留日扎帐信息
         */
        if (orderTemp != null) {
            order.setFinanceMoneyConfirm(orderTemp.getFinanceMoneyConfirm());
            order.setFinanceMoneyConfirmTime(orderTemp.getFinanceMoneyConfirmTime());
            order.setFinanceMoneyConfirmUserId(orderTemp.getFinanceMoneyConfirmUserId());
        }


        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("操作订单用户为空").throwException();
        }

        if (conn == null) {
            MyException.newInstance("保存订单时，数据库连接为空").throwException();
        }

        if (StringUtils.isEmpty(order.getPayTime())) {
            MyException.newInstance("保存订单时，订单支付时间为空").throwException();
        }

        if (StringUtils.isEmpty(order.getCreateTime())) {
            MyException.newInstance("保存订单时，订单创建时间为空").throwException();
        }

        if (StringUtils.isEmpty(order.getValueDate())) {
            MyException.newInstance("保存订单时，起息日为空").throwException();
        }


        /**
         * 处理订单对应的收益率
         */
        ProductionCompositionPO productionCompositionPO = productionCompositionDao.getProductionCompositionPOByProductionIdAndMoney(order.getProductionId(), order.getMoney(), conn);

        if (productionCompositionPO == null) {
            MyException.newInstance("无法获得产品构成，创建订单失败", "orderId=" + order.getId()).throwException();
        }


        if (StringUtils.isEmpty(order.getAccountId())) {
            MyException.newInstance("无法获得客户账户信息，创建订单失败", "orderId=" + order.getId()).throwException();
        }

        order.setExpectedYield(productionCompositionPO.getExpectedYield());


        /**
         * 订单推荐码处理
         */

        UserPO referralUser = userDao.loadByReferralCode(order.getReferralCode(), conn);
        if (referralUser != null && StringUtils.isEmpty(userId)) {
            userId = referralUser.getId();
        }


        /**
         *
         *  销售产品
         *
         */
        // 构建产品
        ProductionPO production = new ProductionPO();
        production.setId(order.getProductionId());
        production.setState(Config.STATE_CURRENT);
        production = MySQLDao.load(production, ProductionPO.class, conn);


        int productionSaleStatus = productionService.sell(production.getId(), order.getMoney(), conn);

        if (productionSaleStatus != 1) {
            MyException.newInstance(Config.getWords4WebGeneralError(), "产品购买失败，请检查").throwException();
        }





        /**
         *
         * 设置订单状态
         *
         */

        if (StringUtils.isEmpty(order.getOrderNum())) {
            order.setOrderNum(getOrderNum4Web(conn));
        }


        /**
         * 当订单里没有销售人员的时候，才重置销售
         */
        if (!StringUtils.isEmpty(userId) && StringUtils.isEmpty(order.getSalemanId())) {
            order.setSalemanId(userId);
        }



        // 订单的状态设置为已售
        order.setStatus(OrderStatus.Saled);


        /**
         * 保存订单详情
         *
         * 先保存订单详情，再确定保存订单信息
         */
        orderDetailDao.saveOrderDetail(order, order.getMoney(), order.getPayTime(), OrderStatus.Saled, "订单支付", userId, conn);


        /**
         * 关联合同
         */
        if (!StringUtils.isEmpty(order.getContractNo())) {

            List<ContractPO> contractPOs = contractDao.getContractByContractNO(order.getContractNo(), conn);

            if (contractPOs != null && contractPOs.size() > 0) {

                ContractPO contractPO = contractPOs.get(0);

                contractDao.contractSigned(contractPO.getContractNo(), userId, conn);
            }

        }



        /**
         * 保存订单信息
         * Date: 2016-05-20 9:49:10
         * Author: leevits
         */

        // 设置支付渠道信息
/*        int orderPayChannel = OrderPayChannel.ALLINPAY;
        if (!StringUtils.isEmpty(order.getBankCard())) {
            orderPayChannel = OrderPayChannel.FUYOU;
        }
        order.setPayChannel(orderPayChannel);*/

        orderDao.insertOrUpdate(order, userId, conn);




        /**
         *
         * 生成兑付计划
         *
         */
        generatePaymentPlan(order, "0", userId, conn);


        // 增加客户资金记录
        int customerMoneyExecCount = customerMoneyDao.updateAvailableMoney(order.getMoney(), order.getCustomerId(), conn);
        if(customerMoneyExecCount != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据库异常").throwException();
        }

        // 新增新的资金日志
        String logType = CustomerMoneyLogType.Buy;
        String logContent = CustomerMoneyLogType.Buy + "[" + production.getName() +"]" + order.getMoney() + "元";
        String logStatus = CustomerMoneyLogStatus.Success;

        customerMoneyLogDao.newCustomerMoneyLog(order.getMoney(), 0, logType, logContent, logStatus, order.getId(), order.getCustomerId(), conn);


        /**
         * 如果客户没有分配记录，则将订单归属的销售分配给此用户
         */

        List<CustomerDistributionPO> listCustomerDistrbutionPO = customerDistributionDao.getListCustomerDistrbutionPO(order.getCustomerId(), conn);

        if (listCustomerDistrbutionPO == null || listCustomerDistrbutionPO.size() == 0) {
            CustomerDistributionPO customerDistributionPO = new CustomerDistributionPO();
            customerDistributionPO.setCustomerId(order.getCustomerId());
            customerDistributionPO.setSaleManId(order.getSalemanId());
            customerDistributionDao.distributeToSalesman(customerDistributionPO, userId, conn);
        }



        return 0;
    }


    private int updateContrancts(OrderPO order, String operatorId, Connection conn) throws Exception {
        int count = 0;
        if (!StringUtils.isEmpty(order.getContractNo())) {
            ContractRoutePO po = new ContractRoutePO();
            po.setContractNo(order.getContractNo());
            po.setState(Config.STATE_CURRENT);
            List<ContractRoutePO> contractRoutePOList = MySQLDao.query(po, ContractRoutePO.class, conn);
            for (int i = 0; i < contractRoutePOList.size(); i++) {
                contractRoutePOList.get(i).setState(Config.STATE_UPDATE);
                int count2 = MySQLDao.update(contractRoutePOList.get(i), conn);
                if (count2 == 1) {
                    contractRoutePOList.get(i).setSid(MySQLDao.getMaxSid("CRM_ContractRoute", conn));
                    contractRoutePOList.get(i).setState(Config.STATE_CURRENT);
                    contractRoutePOList.get(i).setOperatorId(operatorId);
                    contractRoutePOList.get(i).setOperateTime(TimeUtils.getNow());
                    contractRoutePOList.get(i).setOrderId(order.getId());
                    // 签约时间暂时定义为订单创建时间
                    contractRoutePOList.get(i).setSignDate(TimeUtils.getNow());
                    count = MySQLDao.insert(contractRoutePOList.get(i), conn);
                }
            }
        }

        return count;
    }



    public OrderPO appointmentOrderFromNow(String orderId, String customerId, String productionId, String productionCompositionId, double money, String payTime, String contractNo, String referralCode, int orderStatusOfAppointmentOrWaitingForConfirm, String accountId, String bankCode, String description, Connection conn) throws Exception {

        String appointmentTime = TimeUtils.getNow();
        String createTime = TimeUtils.getNow();
        boolean isPersonal = true;

        return appointmentOrder(orderId, customerId, productionId, productionCompositionId, money, contractNo, isPersonal, appointmentTime, payTime, createTime, referralCode, orderStatusOfAppointmentOrWaitingForConfirm, accountId, bankCode, description, conn);
    }



    /**
     * 取消预约
     *
     * Date: 2016-05-18 23:45:25
     * Author: leevits
     */
    public int appointmentOrderCancel(String orderId, String userId, Connection conn) throws Exception {
        OrderPO order = orderDao.loadByOrderId(orderId, conn);

        String now = TimeUtils.getNow();

        if (order == null || !NumberUtils.checkNumberIn(order.getStatus(), OrderStatus.Appointment, OrderStatus.AppointmentWaiting)) {
            MyException.newInstance("当前订单状态不支持取消", "orderId=" + orderId).throwException();
        }


        OrderDetailPO detail = orderDetailDao.getOrderDetailFromOrder(order);

        if (order.getStatus() == OrderStatus.Appointment) {
            detail.setStatus(OrderStatus.AppointmentCancel);
            order.setStatus(OrderStatus.AppointmentCancel);
        }
        else if (order.getStatus() == OrderStatus.SaleAndWaiting) {
            detail.setStatus(OrderStatus.SaleAndWaitingCancel);
            order.setStatus(OrderStatus.SaleAndWaitingCancel);
        }
        else {
            MyException.newInstance("当前订单状态不支持取消").throwException();
        }

        orderDao.insertOrUpdate(order, userId, conn);

        orderDetailDao.saveOrderDetail(order, order.getMoney(), now, detail.getStatus(), "订单取消", userId, conn);


        return 1;
    }

    public Pager getReportWeekly(String startTime, String endTime, int currentPage, int showRowCount, Connection conn) throws Exception {

        OrderReportWeeklyVO orderReportWeeklyVO = new OrderReportWeeklyVO();
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("886E1807");
        dbSQL.addParameter4All("startTime", startTime)
                .addParameter4All("endTime", endTime);
        dbSQL.initSQL();

        Pager pager = MySQLDao.search(dbSQL, orderReportWeeklyVO, null, currentPage, showRowCount, null, conn);

        return pager;
    }



    public Pager getReportMonthly(String thisYear, String thisMonth, Connection conn) throws Exception {

        String this_year_begin_time = thisYear + "-01-01 00:00:00";
        String this_month_begin_time = thisYear + "-" + thisMonth + "-01 00:00:00";
        String this_month_end_time = TimeUtils.getTime(TimeUtils.getTime(this_month_begin_time, 1, TimeUtils.MONTH), -1, TimeUtils.SECOND);
        String last_year_begin_time = "2014-01-01 00:00:00";
        String last_year_end_time = TimeUtils.getTime(this_year_begin_time, -1, TimeUtils.SECOND);
        String last_month_begin_time = TimeUtils.getTime(this_month_begin_time, -1, TimeUtils.MONTH);
        String last_month_end_time = TimeUtils.getTime(this_month_begin_time, -1, TimeUtils.SECOND);


        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     s.GroupName, s.`NAME`,");
        sbSQL.append("     (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o");
        sbSQL.append("         WHERE");
        sbSQL.append("                 1 = 1");
        sbSQL.append("         AND o.`status` not in (0)");
        sbSQL.append("         and (");
        sbSQL.append("             (o.paymentPlanStatus not in (5) and o.PayTime<='"+last_year_end_time+"') or");
        sbSQL.append("             (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>='"+last_year_end_time+"' and o.paymentPlanLastTime<=now() and o.payTime<='"+last_year_end_time+"')");
        sbSQL.append("         )");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_remain_year_open,");
        sbSQL.append(" ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money * p.discountRate),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o, crm_production p");
        sbSQL.append("         WHERE");
        sbSQL.append("                 1 = 1");
        sbSQL.append("         AND o.`status` not in (0) and  p.state=0 and o.ProductionId=p.id");
        sbSQL.append("         and (");
        sbSQL.append("                 (o.paymentPlanStatus not in (5) and o.PayTime<='"+last_year_end_time+"') or");
        sbSQL.append("                 (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>='"+last_year_end_time+"' and o.paymentPlanLastTime<=now() and o.payTime<='"+last_year_end_time+"')");
        sbSQL.append("         )");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_remain_year_open_discount_rate,");
        sbSQL.append(" ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o");
        sbSQL.append("         WHERE");
        sbSQL.append("                 1 = 1");
        sbSQL.append("         AND o.`status` not in (0)");
        sbSQL.append("         and (");
        sbSQL.append("                 (o.paymentPlanStatus not in (5) and o.PayTime<='"+last_month_end_time+"') or");
        sbSQL.append("                 (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>='"+last_month_end_time+"' and o.paymentPlanLastTime<=now() and o.payTime<='"+this_month_begin_time+"')");
        sbSQL.append("         )");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_remain_month_open,");
        sbSQL.append("         ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money * p.discountRate),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o, crm_production p");
        sbSQL.append("         WHERE");
        sbSQL.append("                 1 = 1");
        sbSQL.append("         AND o.`status` not in (0) and  p.state=0 and o.ProductionId=p.id");
        sbSQL.append("         and (");
        sbSQL.append("                 (o.paymentPlanStatus not in (5) and o.PayTime<='"+last_month_end_time+"') or");
        sbSQL.append("                 (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>='"+last_month_end_time+"' and o.paymentPlanLastTime<=now() and o.payTime<='"+this_month_begin_time+"')");
        sbSQL.append("         )");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_remain_month_open_discount_rate,");
        sbSQL.append(" ");
        sbSQL.append(" ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 count(DISTINCT o.CustomerId)");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o");
        sbSQL.append("         WHERE");
        sbSQL.append("                 1 = 1");
        sbSQL.append("         AND o.`status` not in (0)");
        sbSQL.append("         and (");
        sbSQL.append("                 (o.paymentPlanStatus not in (5) and o.PayTime<='"+last_month_end_time+"') or");
        sbSQL.append("                 (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>='"+last_month_end_time+"' and o.paymentPlanLastTime<=now() and o.payTime<='"+last_year_end_time+"')");
        sbSQL.append("         )");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) customer_remain_count,");
        sbSQL.append(" ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 count(DISTINCT o.CustomerId)");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o, view_customer c");
        sbSQL.append("         WHERE");
        sbSQL.append("                 o.CustomerId=c.id");
        sbSQL.append("         AND o.`status` not in (0)");
        sbSQL.append("         and o.PayTime>='"+this_month_begin_time+"' and o.PayTime<='"+this_month_end_time+"'");
        sbSQL.append("         and c.CreateTime>='"+this_month_begin_time+"' and c.CreateTime<='"+this_month_end_time+"'");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) customer_new_count,");
        sbSQL.append("         ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o");
        sbSQL.append("         WHERE");
        sbSQL.append("                 1=1");
        sbSQL.append("         AND o.`status` not in (0)");
        sbSQL.append("         and o.PayTime>='"+this_month_begin_time+"' and o.PayTime<='"+this_month_end_time+"'");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_add_this_month,");
        sbSQL.append(" ");
        sbSQL.append(" ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money * p.discountRate),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o, crm_production p");
        sbSQL.append("         WHERE");
        sbSQL.append("                 p.state=0");
        sbSQL.append("         AND o.`status` not in (0) and o.ProductionId=p.id");
        sbSQL.append("         and o.PayTime>='"+this_month_begin_time+"' and o.PayTime<='"+this_month_end_time+"'");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_add_this_month_discount_rate,");
        sbSQL.append(" ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o");
        sbSQL.append("         WHERE");
        sbSQL.append("                 1=1");
        sbSQL.append("         AND o.`status` not in (0)");
        sbSQL.append("         AND o.paymentPlanStatus in (5)");
        sbSQL.append("         and o.paymentPlanLastTime>='"+this_month_begin_time+"' and o.paymentPlanLastTime<='"+this_month_end_time+"'");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_payment_this_month,");
        sbSQL.append("         ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money * p.discountRate),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o, crm_production p");
        sbSQL.append("         WHERE");
        sbSQL.append("                 p.state=0");
        sbSQL.append("         AND o.`status` not in (0)");
        sbSQL.append("         AND o.paymentPlanStatus in (5) and o.ProductionId=p.id");
        sbSQL.append("         and o.paymentPlanLastTime>='"+this_month_begin_time+"' and o.paymentPlanLastTime<='"+this_month_end_time+"'");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_payment_this_month_discount_rate,");
        sbSQL.append("         0 money_new_this_month,");
        sbSQL.append("         0 money_new_this_month_discount_rate,");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o");
        sbSQL.append("         WHERE");
        sbSQL.append("                 1 = 1");
        sbSQL.append("         AND o.`status` not in (0)");
        sbSQL.append("         and (");
        sbSQL.append("                 (o.paymentPlanStatus not in (5) and o.PayTime<='"+this_month_end_time+"') or");
        sbSQL.append("                 (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>='"+this_month_end_time+"' and o.paymentPlanLastTime<=now()  and o.PayTime<=@this_month_end_time)");
        sbSQL.append("         )");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_remain_this_month_end,");
        sbSQL.append("         ");
        sbSQL.append("         (");
        sbSQL.append("         SELECT");
        sbSQL.append("                 IFNULL(sum(o.money * p.discountRate),0)/10000");
        sbSQL.append("         FROM");
        sbSQL.append("                 view_order o, crm_production p");
        sbSQL.append("         WHERE");
        sbSQL.append("                 1 = 1");
        sbSQL.append("         AND o.`status` not in (0) and p.state=0 and o.ProductionId=p.id");
        sbSQL.append("         and (");
        sbSQL.append("                 (o.paymentPlanStatus not in (5) and o.PayTime<='"+this_month_end_time+"') or");
        sbSQL.append("                 (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>='"+this_month_end_time+"' and o.paymentPlanLastTime<=now()  and o.PayTime<=@this_month_end_time)");
        sbSQL.append("         )");
        sbSQL.append("         and o.salesmanId=s.id");
        sbSQL.append("     ) money_remain_this_month_end_discount_rate");
        sbSQL.append(" FROM");
        sbSQL.append("     view_salesman s");
        sbSQL.append(" ORDER BY s.groupname, s.`NAME`");


        OrderReportMonthlyVO orderReportMonthlyVO = new OrderReportMonthlyVO();
        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sbSQL.toString());
        dbSQL.initSQL();

        Pager pager = MySQLDao.search(dbSQL, orderReportMonthlyVO, null, 0, 0, null, conn);

        /**
         * 处理本月新增
         */
        for (int i = 0; pager != null && pager.getData() != null && i < pager.getData().size(); i++) {
            OrderReportMonthlyVO vo = (OrderReportMonthlyVO) pager.getData().get(i);

            double thisMonthNew = vo.getMoney_add_this_month() - vo.getMoney_payment_this_month();
            vo.setMoney_new_this_month(thisMonthNew);

            double thisMonthNew_discountRate = vo.getMoney_add_this_month_discount_rate() - vo.getMoney_payment_this_month_discount_rate();
            vo.setMoney_new_this_month_discount_rate(thisMonthNew_discountRate);
        }

        return pager;
    }


    public OrderPO financeMoneyConfirm(String orderId, String userId, Connection conn) throws Exception {
        OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);

        if (!NumberUtils.checkNumberIn(orderPO.getStatus(), OrderStatus.SaleAndWaiting)) {
            MyException.newInstance("只有状态为【打款】的订单才能进行财务确认", "orderId=" + orderId).throwException();
        }

        orderPO.setFinanceMoneyConfirm("1");
        orderPO.setFinanceMoneyConfirmUserId(userId);
        orderPO.setFinanceMoneyConfirmTime(TimeUtils.getNow());

        orderDao.insertOrUpdate(orderPO, userId, conn);

        orderDetailDao.saveOrderDetail(orderPO, 0, TimeUtils.getNow(), "确认日终扎帐", userId, conn);

        /**
         * 生成兑付计划
         */
        generatePaymentPlan(orderPO, "0", userId, conn);

        return orderPO;
    }

    public OrderPO financeMoneyConfirmCancel(String orderId, String userId, Connection conn) throws Exception {
        OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);

        if (!NumberUtils.checkNumberIn(orderPO.getStatus(), OrderStatus.SaleAndWaiting)) {
            MyException.newInstance("只有状态为【打款】的订单才能进行财务确认", "orderId=" + orderId).throwException();
        }

        orderPO.setFinanceMoneyConfirm("0");
        orderPO.setFinanceMoneyConfirmUserId("");
        orderPO.setFinanceMoneyConfirmTime("");

        orderDao.insertOrUpdate(orderPO, userId, conn);

        orderDetailDao.saveOrderDetail(orderPO, 0, TimeUtils.getNow(), "取消日终扎帐", userId, conn);

        return orderPO;
    }

    /**
     * 网站：添加订单
     *
     * @param customerId
     * @param conn
     * @return OrderPO
     * @throws Exception
     */
    public OrderPO appointmentOrder(String orderId, String customerId, String productionId, String productionCompositionId, double money, String contractNo, boolean isPersonal, String appointmentTime, String payTime, String createTime, String referralCode, int orderStatusOfAppointmentOrWaitingForConfirm, String accountId, String bankCode, String description, Connection conn) throws Exception {

        // 查询该订单关联的产品
        ProductionWVO productionWVO = productionDao.getProductByProductionIdAndMoney(productionId, money, conn);
        if (productionWVO == null) {
            MyException.newInstance("产品信息获取有误，请检查产品编号或者投资金额", "productionId="+productionId+"&money="+money).throwException();
        }

        // 查询产品是否已售罄
        Double size = productionWVO.getSize();
        Double saleMoney = productionWVO.getSaleMoney();
        if (saleMoney >= size) {
            throw new Exception("产品已售罄，无法预约！");
        }

        // 查询订单金额是否符合产品配合和销售金额的差
        if (money > (size - saleMoney)) {
            throw new Exception("您的预约的金额超出产品剩余的销售金额！");
        }

        if (money > productionWVO.getSizeStop()) {
            throw new Exception("您输入的金额超过了此产品允许的最大投资金额，请重新输入！");
        }

        // 结合订单金额，查询符合的产品构成
        ProductionCompositionPO composition = null;


        /**
         * 根据产品构成编号来查询构成
         */
        if (!StringUtils.isEmpty(productionCompositionId)) {
            composition = productionCompositionDao.getProductionCompositionPOByProductionIdAndMoney(productionId, productionCompositionId, money, conn);
        }
        else {
            composition = productionCompositionDao.getProductionCompositionPOByProductionIdAndMoney(productionId, money, conn);
        }

        if (composition == null) {
            throw new Exception("您预约的金额，没有对应的产品构成信息！");
        }

        String customerName = "";
        String customerMobile = "";

        CustomerPersonalPO customer = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        if (customer == null) {
            CustomerInstitutionPO customerInstitution = customerInstitutionDao.loadByCustomerInstitutionId(customerId, conn);

            if (customerInstitution == null) {
                MyException.newInstance("无法找到对应客户信息", "customerId="+customerId).throwException();
            }

            if (customerInstitution != null) {
                customerName = customerInstitution.getName();
                customerMobile = "0000";

                isPersonal = false;
            }
        }
        else {
            customerName = customer.getName();
            customerMobile = customer.getMobile();
        }


        OrderPO order = new OrderPO();
        if (!StringUtils.isEmpty(orderId)) {
            order = orderDao.loadByOrderId(orderId, conn);
            if (order == null) {
                MyException.newInstance("订单为空", "orderId=" + orderId).throwException();
            }

            if (order.getStatus() != 0 && order.getStatus() != 7) {
                MyException.newInstance("订单状态不支持预约或修改", "orderId=" + orderId).throwException();
            }
        }
        else {
            // 生成订单
            String orderNum = this.getOrderNum4Web(conn);
            order.setOrderNum(orderNum); // 订单编号
        }

        order.setProductionId(productionId);
        order.setMoney(money);
        order.setCustomerId(customerId);                        // 客户的 ID
        order.setCustomerName(customerName);
        order.setContractNo(contractNo);
        order.setPayTime(payTime);
        order.setDescription(description);



        if(!StringUtils.isEmpty(accountId)) {
            order.setAccountId(accountId);
        }
        if(!StringUtils.isEmpty(bankCode)) {
            order.setBankCode(bankCode);
        }

        order.setStatus(orderStatusOfAppointmentOrWaitingForConfirm);

        if (order.getStatus() != OrderStatus.Appointment && order.getStatus() != OrderStatus.SaleAndWaiting ) {
            MyException.newInstance("预约订单失败，订单状态当前状态执行预约和打款操作，当前订单状态为【"+order.getStatus()+"】").throwException();
        }

        // 校验推荐码
        UserPO userPO = userDao.loadByReferralCode(referralCode, conn);
        // 如果没有查询到系统里的推荐人，默认使用 sale_4w
        if(userPO == null) {
            userPO = userDao.loadUserByUserId(Config.getSystemConfig("web.default.saleId"), conn);
            referralCode = userPO.getReferralCode();
        }
        order.setReferralCode(referralCode);
        order.setSalemanId(userPO.getId());


        /**
         * 如果是下单并确认，需要保存合同信息
         */
        if (order.getStatus() == OrderStatus.SaleAndWaiting && !StringUtils.isEmpty(contractNo)) {
            contractDao.contractSigned(contractNo, order.getSalemanId(), conn);
        }

        /**
         * 修改合同对应的产品
         */
        if (!StringUtils.isEmpty(contractNo)) {
            contractDao.changeProduction(contractNo, productionId, conn);
        }


        order.setCustomerAttribute(isPersonal ? 0 : 1); // 个人客户或机构客户
        order.setProductionCompositionId(composition.getId());  // 产品构成
        order.setExpectedYield(productionWVO.getExpectedYield());



        // 补充订单表的基础数据
        order.setState(Config.STATE_CURRENT);
        order.setOperateTime(TimeUtils.getNow());
        order.setCreateTime(createTime);
        order.setAppointmentTime(appointmentTime);

        // 如果有此签名的订单，则不能提交
        String signature = getSignature(order, conn);
        boolean isSignatureExists = orderDao.checkSignatureExists(signature, conn);
        if (isSignatureExists) {
            MyException.newInstance("已经有此订单数据，请勿重复提交").throwException();
        }


        order.setSignature(signature);


        // 生成订单
        orderDao.insertOrUpdate(order, order.getSalemanId(), conn);


        /**
         * 生成订单明细
         */
        orderDetailDao.saveOrderDetail(order, money, appointmentTime, orderStatusOfAppointmentOrWaitingForConfirm, "订单预约", order.getSalemanId(), conn);


        /**
         * 自动分配此客户
         */
        List<CustomerDistributionPO> listCustomerDistrbutionPO = customerDistributionDao.getListCustomerDistrbutionPO(order.getCustomerId(), conn);

        if (listCustomerDistrbutionPO == null || listCustomerDistrbutionPO.size() == 0) {
            UserPositionInfoPO userPositionInfo = departmentDao.getDefaultUserPositionInfo(userPO.getId(), conn);

            SalemanGroupPO salemanGroup = salemanGroupDao.getDefaultSalemanGroupByUserId(userPO.getId(), conn);

            customerDistributionDao.distributeCustomer(order.getCustomerId(), userPO.getId(), salemanGroup.getId(), userPositionInfo.getDepartmentId(), 0, true, conn);
        }


        return order;
    }

    public void feedback(OrderPO order, int feedbackStatus, String userId, Connection conn) throws Exception {

        order = orderDao.loadByOrderId(order.getId(), conn);

        if (!NumberUtils.checkNumberIn(order.getStatus(), OrderStatus.Saled, OrderStatus.Feedback1)) {
            MyException.newInstance("订单当前状态不支持此次回访").throwException();
        }


        if (feedbackStatus != OrderStatus.Feedback1 && feedbackStatus != OrderStatus.Feedback2) {
            MyException.newInstance("回访失败，回访状态异常", "订单编号【"+order.getId()+"】回访状态编号【"+feedbackStatus+"】").throwException();
        }

        order.setStatus(feedbackStatus);

        orderDao.insertOrUpdate(order, userId, conn);

        String now = TimeUtils.getNow();

        orderDetailDao.saveOrderDetail(order, 0, now, order.getStatus(),"订单回访", userId, conn);

    }

    public Pager listPagerOrderDetails(String orderId, int currentPage, int showPageCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listPagerOrderDetails", this);
        dbSQL.addParameter4All("orderId", orderId);
        dbSQL.initSQL();

        OrderDetailVO detailVO = new OrderDetailVO();



        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), detailVO, null, currentPage, showPageCount , null, conn);

        return pager;
    }

    public OrderVO getOrderVOByOrderId(String orderId, Connection conn) throws Exception {
        return orderDao.getOrderVOByOrderId(orderId, conn);
    }

    /**
     * 更新推荐人
     *
     *
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月30日
     *
     * @param order
     * @param referralCode
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer updateReferralCode(OrderPO order, String referralCode, Connection conn) throws Exception {

        // 校验订单
        if (order == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到订单内容").throwException();
        }

        // 校验推荐码
        UserPO userPO = userDao.loadByReferralCode(referralCode, conn);
        // 如果没有查询到系统里的推荐人，默认使用 sale_4w
        if(userPO == null) {
            userPO = userDao.loadUserByUserId(Config.getSystemConfig("web.default.saleId"), conn);
            referralCode = userPO.getReferralCode();
        }

        order.setReferralCode(referralCode);

        orderDao.insertOrUpdate(order, order.getSalemanId(), conn);
        return 1;
    }


    /**
     * 获得本订单总收益金额
     * @param orderId
     * @param conn
     * @return
     * @throws Exception
     */
    public double getTotalProfitMoney(String orderId, Connection conn) throws Exception {

        List<PaymentPlanPO> paymentPlanPOs = paymentPlanDao.getPaymentPlansByOrderId(orderId, conn);

        double totalProfit = 0;

        for (int i = 0; paymentPlanPOs != null && i < paymentPlanPOs.size(); i++) {
            PaymentPlanPO paymentPlanPO = paymentPlanPOs.get(i);
            totalProfit += paymentPlanPO.getTotalProfitMoney();
        }

        return totalProfit;
    }


    public List<PaymentPlanPO> calculatePaymentPlan(OrderPO order, Connection conn) throws Exception {

        List<PaymentPlanPO> plans = new ArrayList<PaymentPlanPO>();

        ProductionPO productionPO = productionDao.loadProductionById(order.getProductionId(), conn);


        // 获得起息日
        String interestDate = productionPO.getValueDate();
        if (StringUtils.isEmpty(interestDate)) {
            interestDate = TimeUtils.getTime(order.getPayTime(), 1, TimeUtils.DATE);
        }


        double money = order.getMoney();

        // 周期
        int interestCycle = productionPO.getInterestCycle();

        // 期数
        int interestUnit = productionPO.getInterestUnit();

        int interestTimes = productionPO.getInterestTimes();

        // 收益率
        double profitRate = getExpectedYield(order.getProductionCompositionId(), conn);

        for (int i = 1; i <= interestTimes; i++) {
            PaymentPlanPO paymentPlanPO = getPaymentPlanPOInstance(order);

            // 应兑付利息
            double tempInterest = MoneyUtils.calculateProfit(money, profitRate, interestCycle, interestUnit);




            //应兑付收益总金额
            paymentPlanPO.setTotalProfitMoney(tempInterest);




            // 应兑付本金总金额
            double tempMoney = 0;
            if (i == interestTimes) {
                tempMoney = money;
            }
            paymentPlanPO.setTotalPaymentPrincipalMoney(tempMoney);





            // 应兑付总金额
            double payment = tempInterest;
            if (i == interestTimes) {
                payment = tempInterest + money;
            }
            paymentPlanPO.setTotalPaymentMoney(MoneyUtils.handleDouble(payment, 2));




            // 兑付日期
            String interestUnitName = interestUnit == 0 ? TimeUtils.DATE : (interestUnit == 1 ? TimeUtils.MONTH : TimeUtils.YEAR);
            String tempInterestDate = TimeUtils.getTime(interestDate, interestCycle, interestUnitName);
            tempInterestDate = TimeUtils.format(tempInterestDate, TimeUtils.Format_YYYY_MM_DD_HH_M_S, TimeUtils.Format_YYYY_MM_DD);

            paymentPlanPO.setPaymentTime(tempInterestDate);





            // 兑付总期数
            paymentPlanPO.setTotalInstallment(interestTimes);




            // 当前兑付期数
            paymentPlanPO.setCurrentInstallment(i);




            // 已兑付本金金额
            paymentPlanPO.setPaiedPrincipalMoney(0);




            // 已兑付收益金额
            paymentPlanPO.setPaiedProfitMoney(0);




            //兑付状态 从KV表中获取，生成兑付计划的时候统一设为未兑付
            paymentPlanPO.setStatus(PaymentPlanStatus.Unpaid);


            plans.add(paymentPlanPO);


            // 计算下一个起息日
            interestDate = tempInterestDate;
        }

        return plans;
    }


    /**
     * 根据产品构成 Id 获取该产品的预期收益
     * @return
     */
    private static double getExpectedYield(String compositionId, Connection conn) throws Exception {
        ProductionCompositionPO productionComposition = new ProductionCompositionPO();
        productionComposition.setId(compositionId);
        productionComposition.setState(0);
        productionComposition = MySQLDao.load(productionComposition, ProductionCompositionPO.class, conn);
        double yield = productionComposition.getExpectedYield();

        // 判断获取的收益率，若收益率表示形式不是小数形式，转换为小数形式
        if (yield >= 1) {
            return yield / 100.0;
        } else {
            return yield;
        }

    }

    /**
     * 初始化兑付计划的PO，不同的兑付类型返回的兑付计划PO中，有几个变量均相同，故单独拿出来初始化
     */
    private PaymentPlanPO getPaymentPlanPOInstance(OrderPO orderPO) {
        PaymentPlanPO paymentPlanPO = new PaymentPlanPO();
        paymentPlanPO.setProductId(orderPO.getProductionId());
        paymentPlanPO.setOrderId(orderPO.getId());
        paymentPlanPO.setProductCompositionId(orderPO.getProductionCompositionId());
        paymentPlanPO.setCustomerId(orderPO.getCustomerId());
        return paymentPlanPO;
    }



    /**
     * 获得起息日
     * @param orderId
     * @param conn
     * @return
     * @throws Exception
     */
    public String getValueDate(String orderId, Connection conn) throws Exception {
        OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);

        if (StringUtils.isEmpty(orderPO.getPayTime())) {
            MyException.newInstance("订单尚未支付，无法获得起息日").throwException();
        }

        ProductionPO productionPO = productionDao.loadProductionById(orderPO.getProductionId(), conn);

        if (!StringUtils.isEmpty(productionPO.getValueDate())) {
            return productionPO.getValueDate();
        }


        String valueDate = TimeUtils.getTime(orderPO.getPayTime(), 1, "DATE");

        return valueDate;
    }

    /**
     * 为订单生成对付计划
     *
     * @param order
     * @param operatorId
     * @param generateType 0：未确认的删除，重新生成； 1：已确认的也删除，重新生成
     * @param conn
     * @throws Exception
     * @author 邓超
     */
    public void generatePaymentPlan(OrderPO order, String generateType, String operatorId, Connection conn) throws Exception {

        if (order == null) {
            MyException.newInstance("订单数据参数为空").throwException();
        }


        if (order.getStatus() != OrderStatus.Saled && order.getStatus() != OrderStatus.Feedback1 && !order.getFinanceMoneyConfirm().equals("1")) {
            MyException.newInstance("该笔订单尚未付款，无法生成兑付计划").throwException();
        }


        //付息类型、付息期数
        ProductionPO production = productionDao.loadProductionById(order.getProductionId(), conn);

        if (production == null) {
            MyException.newInstance("查询产品失败").throwException();
        }

        /**
         * 如果已有已执行的兑付计划，则不能重新生成
         */
        List<PaymentPlanPO> paymentPlanPOs = paymentPlanDao.getPaymentPlansByOrderId(order.getId(), conn);
        for (int i = 0; paymentPlanPOs != null && i < paymentPlanPOs.size(); i++) {

            PaymentPlanPO paymentPlanPO = paymentPlanPOs.get(i);

            if (paymentPlanPO.getStatus() != PaymentPlanStatus.Unpaid) {
                MyException.newInstance("存在已执行的兑付计划，无法重新生成，请检查").throwException();
            }
        }


        /**
         * 删除已有但未被确认的兑付计划
         */
        for (int i = 0; paymentPlanPOs != null && i < paymentPlanPOs.size(); i++) {

            PaymentPlanPO paymentPlanPO = paymentPlanPOs.get(i);


            /**
             * 检查是否已确认，如果尚未确认，则删除兑付计划
             */
            if (paymentPlanPO.getConfirmorId().equals("") && generateType.equals("0")) {
                MySQLDao.remove(paymentPlanPO, operatorId, conn);
            }
            else if (generateType.equals("1")) {
                MySQLDao.remove(paymentPlanPO, operatorId, conn);
            }

        }



        //生成兑付数据
        List<PaymentPlanPO> paymentPlanList = calculatePaymentPlan(order, conn);
        //根据产品兑付的期数添加相应的兑付PO
        for (int i = 0; paymentPlanList != null && i < paymentPlanList.size(); i++) {
            // 通过订单添加的对付计划永远是新增，因此 ID 设为空
            int count = MySQLDao.insertOrUpdate(paymentPlanList.get(i), operatorId, conn);

            if (count != 1) {
                MyException.newInstance("生成兑付计划失败").throwException();
            }
        }
    }


    /**
     * 获取订单编号
     * @param conn
     * @return
     * @throws Exception
     */
    public String getOrderNum4Web(Connection conn) throws Exception {
        int orderNO = MySQLDao.getSequence("orderNum",conn);
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(7);
        formatter.setGroupingUsed(false);
        String no = formatter.format(orderNO);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String orderNum = "DH" + year + no;
        return orderNum;
    }



    /**
     * 作废订单，向int setAppointmentOrSaleMoney(int operateStatus,OrderPO order,UserPO user,Connection conn)方法
     * 中传入STATUS_INVALID作废标记。
     *
     * @param order,user,conn
     * @throws Exception
     */
    public int cancel(OrderPO order, UserPO user, Connection conn) throws Exception {
        int count = 0;
        order = MySQLDao.load(order, OrderPO.class);
        order.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(order, conn);
        //获取历史订单状态
        int status = order.getStatus();
        if (count == 1) {

            // TODO leevits 产品增加额度需要补充

            order.setSid(MySQLDao.getMaxSid("crm_order", conn));
            order.setState(Config.STATE_DELETE);
            order.setOperateTime(TimeUtils.getNow());
            order.setOperatorId(user.getId());
            order.setStatus(OrderStatus.Cancel);
            order.setCancelTime(TimeUtils.getNow());
            count = MySQLDao.insert(order, conn);
            if(count== 1 && status == OrderStatus.Saled ){
                String contractNo = order.getContractNo();

                /**
                 * 作废关联合同
                 *
                 * 如果有关联合同，需要取消该合同的关联关系，将合同设置为未签约状态
                 */
                if (!StringUtils.isEmpty(contractNo)) {
                    //取消历史合同号的订单
                    count =  contractDao.cancelSignedContract(contractNo, user, conn);
                }

            }
        }
        return count;
    }

    /**
     * 获取客户的订单数据
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月8日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager getOrders(String customerId, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {

        List<KVObject> kvObjects = new ArrayList<KVObject>();
        // customerKV
        KVObject customerKV = new KVObject();
        customerKV.setIndex(1);
        customerKV.setValue(customerId);
        kvObjects.add(customerKV);


        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" 	o.id, ");
        sb.append("      o.money,");
        sb.append(" 	o. STATUS, ");
        sb.append(" 	o.createTime createTime, ");
        sb.append(" 	( ");
        sb.append(" 		SELECT ");
        sb.append(" 			sum(p2.TotalProfitMoney) ");
        sb.append(" 		FROM ");
        sb.append(" 			core_paymentplan p2 ");
        sb.append(" 		WHERE ");
        sb.append(" 			p2.state = 0 ");
        sb.append(" 		AND (p2.status = 0 || p2.status = 1) ");
        sb.append(" 		AND p2.orderId = o.id ");
        sb.append(" 	) AS expireIncome, ");
        sb.append(" 	CASE o.`Status` ");
        sb.append(" WHEN 0 THEN ");
        sb.append(" 	'未付款' ");
        sb.append(" WHEN 1 THEN ");
        sb.append(" 	'已支付' ");
        sb.append(" WHEN 2 THEN ");
        sb.append(" 	'申请退款' ");
        sb.append(" WHEN 3 THEN ");
        sb.append(" 	'已退款' ");
        sb.append(" WHEN 4 THEN ");
        sb.append(" 	'作废' ");
        sb.append(" WHEN 5 THEN ");
        sb.append(" 	'已兑付' ");
        sb.append(" WHEN 6 THEN ");
        sb.append(" 	'预约超时' ");
        sb.append(" WHEN 7 THEN ");
        sb.append(" 	'已支付待确认' ");
        sb.append(" END AS statusStr, ");
        sb.append("  o.referralCode, ");
        sb.append("  date_format(o.valueDate, '%Y-%m-%d') AS valueDate, ");
        sb.append("  o.salemanId, ");
        sb.append("  date_format(o.payTime, '%Y-%m-%d') AS payTime, ");
        sb.append("  p.id AS productionId, ");
        sb.append("  p.websiteDisplayName AS productionName, ");
        sb.append("  p.interestPaymentDescription AS interestType, ");
        sb.append("  p.investTermView,");
        sb.append("  c.id AS productionCompositionId, ");
        sb.append("  c. NAME AS productionCompositionName, ");
        sb.append("  date_format((select plan.paymentTime from core_paymentplan plan where plan.state = 0 and (plan.status = 0 or plan.status = 958) and plan.customerId = o.customerId and plan.orderId = o.id order by plan.paymentTime limit 0,1), '%Y-%m-%d') as nextPaymentTime");
        sb.append(" FROM ");
        sb.append(" 	crm_order o, ");
        sb.append(" 	crm_production p, ");
        sb.append(" 	crm_productioncomposition c ");
        sb.append(" WHERE ");
        sb.append(" 	o.state = 0 ");
        sb.append(" AND p.state = 0 ");
        sb.append(" AND c.state = 0 ");
        sb.append(" AND o.customerId = ? ");
        sb.append(" AND o.productionId = p.id ");
        sb.append(" AND o.productionCompositionId = c.id ");
        sb.append(" AND o. STATUS IN (" + OrderStatus.Appointment + ", " + OrderStatus.Saled + ", " + OrderStatus.Feedback1 + ", " + OrderStatus.Feedback2 + ", " + OrderStatus.PaybackSomePart + ") ");
        sb.append(" AND p.incomeType = ?");
        sb.append(" AND o.id not in ( select plan.orderId from core_paymentplan plan where plan.state = 0 and plan.orderId = o.id and plan.totalInstallment = plan.currentInstallment and plan.status = 1 )");
        sb.append(" ORDER BY ");
        sb.append(" 	o.createTime DESC ");
        Pager pager = MySQLDao.search(sb.toString(), kvObjects, new OrderVO(), new ArrayList<KVObject>(), currentPage, showRowCount, null, conn);
        return pager;
    }


    /**
     *
     * @param orderVO
     * @param payTimeTimePO
     * @param projectTypeIds 订单产品所属项目的类型编号，如果传入空，则查处所有符合其他条件的订单
     * @param conn
     * @return
     * @throws Exception
     */
    public List<OrderVO> getListOrderVO (OrderVO orderVO, TimePO payTimeTimePO, List<String> projectTypeIds, Connection conn) throws Exception {

        List<OrderVO> orderVOs = orderDao.getListOrderVO(orderVO, payTimeTimePO, conn);


        if (ObjectUtils.isListEmpty(projectTypeIds)) {
            return orderVOs;
        }


        List<OrderVO> list = new ArrayList<OrderVO>();

        for (int i = 0; orderVOs != null && i <orderVOs.size(); i++) {
            OrderVO temp = orderVOs.get(i);

            if (projectTypeIds.contains(temp.getProjectTypeId())) {
                list.add(temp);
            }


            /**
             * 订单状态
             */
            if (temp.getPaymentPlanStatus() == 1) {
                temp.setPaymentPlanStatusName("未兑付");
            }
            else if (temp.getPaymentPlanStatus() == 8) {
                temp.setPaymentPlanStatusName("部分兑付");
            }
            else if (temp.getPaymentPlanStatus() == 5) {
                temp.setPaymentPlanStatusName("已兑付");
            }
        }

        return orderVOs;
    }

    /**
     * 订单管理主界面数据显示
     *
     * orderStatus
     * 这里的订单状态是一个状态集合
     * 0：预约
     * 1：已确认但未最终兑付的订单
     * 2：已兑付的订单
     *
     * @param orderVO,request
     * @return
     * @throws Exception
     */
    public Pager getPagerOrderVO(OrderVO orderVO, String payTimeStart, String payTimeEnd, String referralCode, String salesmanId, String salesmanName, String orderStatus, int currentPage, int showPageCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("73371804");
        dbSQL.addParameter4All("salesmanId", salesmanId);
        dbSQL.addParameter4All("referralCode", referralCode);
        dbSQL.addParameter4All("salesmanName", salesmanName);
        dbSQL.addParameter4All("orderStatus", orderStatus);
        dbSQL.addParameter4All("payTimeStart", payTimeStart);
        dbSQL.addParameter4All("payTimeEnd", payTimeEnd);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ order by createtime desc, payTime desc ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), orderVO, null, currentPage, showPageCount, queryType, conn);


        return pager;
    }


//    /**
//     * 获得订单VO
//     * @param orderVO
//     * @param orderStatus
//     * @param projectTypeIds 订单产品所属项目的类型编号，如果传入空，则查处所有符合其他条件的订单
//     * @param conn
//     * @return
//     * @throws Exception
//     */
//    public List<OrderVO> getListOrderVO(OrderVO orderVO, String orderStatus, List<String> projectTypeIds, Connection conn) throws Exception {
//
//        DatabaseSQL dbSQL = new DatabaseSQL();
//        dbSQL.newSQL("getPagerOrderVO", "OrderServiceSQL", this.getClass());
//        dbSQL.addParameter4All("customerId", orderVO.getCustomerId());
//        dbSQL.addParameter4All("orderStatus", orderStatus);
//        dbSQL.addParameter4All("referralCode", orderVO.getReferralCode());
//        dbSQL.addParameter4All("payTimeStart", null);
//        dbSQL.addParameter4All("payTimeEnd", null);
//
//        dbSQL.initSQL();
//
//        List<OrderVO> orderVOs = MySQLDao.search(dbSQL, OrderVO.class, conn);
//
//        if (ObjectUtils.isListEmpty(projectTypeIds)) {
//            return orderVOs;
//        }
//
//
//        List<OrderVO> list = new ArrayList<OrderVO>();
//
//        for (int i = 0; orderVOs != null && i <orderVOs.size(); i++) {
//            OrderVO temp = orderVOs.get(i);
//
//            if (projectTypeIds.contains(temp.getProjectTypeId())) {
//                list.add(temp);
//            }
//        }
//
//        return list;
//    }



    /**
     * 内容：这个service查询出对应负责人组下所有的销售人员订单，如果该负责人在别的组也是以销售人员的岗位存在，也一并查询出来
     * 创建人：张舜清
     * 时间：2015年8月18日10:25:32
     *
     * @param orderVO 订单VO对象
     * @param user    用户PO对象
     * @param conn    数据库连接
     * @return
     * @throws Exception
     */
    public Pager showListSaleGroup(OrderVO orderVO, String payTimeStart, String payTimeEnd, UserPO user, int currentPage, int showPageCount, Connection conn) throws Exception {


        DatabaseSQL dbSQL = DatabaseSQL.newInstance("29C11806");
        dbSQL.addParameter4All("saleManId", user.getId());
        dbSQL.addParameter4All("payTimeStart", payTimeStart);
        dbSQL.addParameter4All("payTimeEnd", payTimeEnd);
        dbSQL.addParameter4All("referralCode", user.getReferralCode());
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");


        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), orderVO, null, currentPage, showPageCount, queryType, conn);

        return pager;
    }

    /**
     * 获取查询语句
     *
     * @param request
     * @param user
     * @return
     */
    private String getSql(HttpServletRequest request, UserPO user) {
        StringBuffer buf = new StringBuffer();
        String userId = user.getId();
        String sqlPersonal = "SELECT O.id, O.sid, C.id customerId,P.id productionId,O.OrderNum,C.LoginName, C.Name customerName, P.`Name` productionName, PC.`Name` productionCompositionName, O.createTime, O.money, O.Description, O.Status status, O.customerAttribute, O.referralCode " +
                "FROM crm_order O " +
                "LEFT JOIN crm_customerpersonal C ON C.id = O.CustomerId and C.state = 0 " +
                "LEFT JOIN crm_production P ON P.id = O.ProductionId AND P.state = 0 " +
                "LEFT JOIN crm_productioncomposition PC ON PC.id = O.ProductionCompositionId AND PC.state = 0 " +
                "WHERE O.state = 0 AND O.customerAttribute = 0 ";
//        String sqlInstitution = "SELECT O.id, O.sid, O.OrderNum, C.Name customerName, P.`Name` productionName, PC.`Name` productionCompositionName, O.createTime, O.money, O.Description, O.Status status, O.customerAttribute, O.referee " +
//                "FROM crm_order O " +
//                "LEFT JOIN crm_customerinstitution C ON C.id = O.CustomerId AND C.state = 0 " +
//                "LEFT JOIN crm_production P ON P.id = O.ProductionId AND P.state = 0 " +
//                "LEFT JOIN crm_productioncomposition PC ON PC.id = O.ProductionCompositionId AND PC.state = 0 " +
//                "WHERE O.state = 0 and O.customerAttribute = 1";
        String status = request.getParameter("status");
        String sqlCustomer = " AND O.salemanId = '" + Database.encodeSQL(user.getId()) + "'";
        if (status == null || status.equals("")) {
//            buf.append(sqlPersonal).append(sqlCustomer).append(" AND O.Status <> 4").append(" UNION ").append(sqlInstitution).append(sqlCustomer).append(" AND O.Status <> 4");
            buf.append(sqlPersonal).append(sqlCustomer).append(" AND O.Status <> 4");
        } else {
//            buf.append(sqlPersonal).append(sqlCustomer).append(" UNION ").append(sqlInstitution).append(sqlCustomer);
            buf.append(sqlPersonal).append(sqlCustomer);
        }
        buf.append(" ORDER BY createTime DESC");
        return buf.toString();
    }

    private String getSqlById(String productionId) {
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
        sqlDB.append("     1 = 1 ");
        sqlDB.append(" AND O.state = 0 ");
        sqlDB.append(" AND O.customerAttribute = 0 ");
        sqlDB.append(" AND O.productionId='" + productionId + "' ");

        return sqlDB.toString();
    }

    /**
     * 订单编辑页面下拉列表(combotree)控件数据的构造
     *
     * @param sql
     * @return JSONArray
     * @throws Exception
     */
    public JSONArray getMenuArray(String sql) throws Exception {
        MenuVO menu = new MenuVO();
        List<MenuVO> list = MySQLDao.query(sql, MenuVO.class, null);
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            for (MenuVO m : list) {
                array.add(m.toJsonObject4Form());
            }
        }
        return array;
    }

    /**
     * 订单编辑页面下拉列表(combotree)控件数据的构造
     *
     * @param sql
     * @return JSONArray
     * @throws Exception
     */
    public JSONArray getMenuArray(String sql, Connection conn) throws Exception {
        MenuVO menu = new MenuVO();
        List<MenuVO> list = MySQLDao.query(sql, MenuVO.class, null, conn);
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            for (MenuVO m : list) {
                array.add(m.toJsonObject4Form());
            }
        }
        return array;
    }




    /**
     * 网站：通过订单订单编号获取订单
     *
     * @return
     * @throws Exception
     */
    public OrderPO loadOrderByNum(String orderNum, Connection connection) throws Exception {
        OrderPO po = null;
        List<OrderPO> orders = MySQLDao.query("select * from crm_order o where o.orderNum = '" + Database.encodeSQL(orderNum) + "' and o.state = 0", OrderPO.class, null, connection);
        if (orders.size() == 1) {
            po = orders.get(0);
        }
        return po;
    }




    /**
     * 根据订单获取所属的产品
     *
     * @param order
     * @param conn
     * @return
     * @throws Exception
     */
    public ProductionPO getProduction(OrderPO order, Connection conn) throws Exception {
        ProductionPO production = null;
        String sql = "select p.* from crm_order o, crm_production p where o.state = 0 and p.state = 0 and o.id = '" + Database.encodeSQL(order.getId()) + "' and o.productionId = p.id";
        List<ProductionPO> list = MySQLDao.query(sql, ProductionPO.class, new ArrayList<KVObject>(), conn);
        if (list.size() == 1) {
            production = list.get(0);
        }
        return production;
    }





    public Pager loadOrder(OrderVO orderVO, String productionId, List<KVObject> conditions, HttpServletRequest request) throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String sql = getSqlById(productionId);
        Pager pager = Pager.query(sql, orderVO, conditions, request, queryType);

        return pager;
    }

    /**
     * 根据客户编号获取已兑付的订单列表
     * <p/>
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月25日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager loadFinishOrderByCustomerId(String customerId, HttpServletRequest request, Connection conn) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" 	o.id, ");
        sb.append(" 	o.money, ");
        sb.append(" 	o. STATUS, ");
        sb.append(" 	o.createTime createTime, ");
        sb.append("  (select sum(p2.TotalProfitMoney) from core_paymentplan p2 where p2.state = 0 and (p2.status = 0 or p2.status = 1) and p2.orderId = o.id) as expireIncome,");
        sb.append(" 	CASE o. STATUS ");
        sb.append(" WHEN 0 THEN ");
        sb.append(" 	'未付款' ");
        sb.append(" WHEN 1 THEN ");
        sb.append(" 	'已支付' ");
        sb.append(" WHEN 2 THEN ");
        sb.append(" 	'申请退款' ");
        sb.append(" WHEN 3 THEN ");
        sb.append(" 	'已退款' ");
        sb.append(" WHEN 4 THEN ");
        sb.append(" 	'作废' ");
        sb.append(" WHEN 5 THEN ");
        sb.append(" 	'已兑付' ");
        sb.append(" WHEN 6 THEN ");
        sb.append(" 	'预约超时' ");
        sb.append(" WHEN 7 THEN ");
        sb.append(" 	'已支付待确认' ");
        sb.append(" END AS statusStr, ");
        sb.append("  o.referralCode, ");
        sb.append("  date_format(o.valueDate, '%Y-%m-%d') AS valueDate, ");
        sb.append("  o.salemanId, ");
        sb.append("  date_format(o.payTime, '%Y-%m-%d') AS payTime, ");
        sb.append("  cp.id AS productionId, ");
        sb.append("  cp. NAME AS productionName, ");
        sb.append("  cc.id AS productionCompositionId, ");
        sb.append("  cc. NAME AS productionCompositionName, ");
        sb.append("  date_format((select plan.paymentTime from core_paymentplan plan where plan.state = 0 and (plan.status = 0 or plan.status = 958) and plan.customerId = o.customerId and plan.orderId = o.id order by plan.paymentTime limit 0,1), '%Y-%m-%d') as nextPaymentTime");
        sb.append(" FROM ");
        sb.append(" 	core_paymentplan p, ");
        sb.append(" 	crm_order o, ");
        sb.append(" 	crm_production cp, ");
        sb.append(" 	crm_productioncomposition cc ");
        sb.append(" WHERE ");
        sb.append(" 	p.state = 0 ");
        sb.append(" AND p.totalInstallment = currentInstallment ");
        sb.append(" AND p. STATUS = 1 ");
        sb.append(" AND o.state = 0 ");
        sb.append(" AND p.orderId = o.id ");
        sb.append(" AND cp.state = 0 ");
        sb.append(" AND cc.state = 0 ");
        sb.append(" AND o.productionId = cp.id ");
        sb.append(" AND o.productioncompositionId = cc.id ");
        sb.append(" AND cc.productionId = cp.id ");
        sb.append(" AND o.customerId = ? ");
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, customerId, parameters);
        Pager pager = Pager.search(sb.toString(), parameters, new OrderVO(), new ArrayList<KVObject>(), request, null);
        //List<OrderVO> lists = MySQLDao.search(sb.toString(), parameters, OrderVO.class,null,conn);
        return pager;
    }



}
