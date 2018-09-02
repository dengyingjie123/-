package com.youngbook.dao.production;

import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.MD5Utils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.common.TimePO;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.vo.production.OrderVO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component("orderDao")
public class OrderDaoImpl implements IOrderDao {


    public boolean checkSignatureExists(String signature, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from crm_order where state=0 and signature=?");
        dbSQL.addParameter(1, signature);

        List list = MySQLDao.search(dbSQL, conn);

        if (list != null && list.size() > 0) {
            return true;
        }

        return false;
    }


    public List<OrderPO> getListOrderPOByAccountId(String accountId, Connection conn) throws Exception {


        StringUtils.checkIsEmpty(accountId, "客户账号");

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getListOrderPOByAccountId", this);
        dbSQL.addParameter4All("accountId", accountId);
        dbSQL.initSQL();

        List<OrderPO> list = MySQLDao.search(dbSQL, OrderPO.class, conn);

        return list;
    }


    /**
     * 订单管理主界面数据显示
     *
     * @param orderVO,request
     * @return
     * @throws Exception
     */
    public List<OrderVO> getListOrderVO (OrderVO orderVO, TimePO payTimeTimePO, Connection conn) throws Exception {

        if (payTimeTimePO == null) {
            payTimeTimePO = new TimePO();
            payTimeTimePO.setStartTime(null);
            payTimeTimePO.setStopTime(null);
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("73371804");
        dbSQL.addParameter4All("orderNum", orderVO.getOrderNum());
        dbSQL.addParameter4All("customerName", orderVO.getCustomerName());
        dbSQL.addParameter4All("productionName", orderVO.getProductionName());
        dbSQL.addParameter4All("customerId", orderVO.getCustomerId());
        dbSQL.addParameter4All("status", orderVO.getStatus());
        dbSQL.addParameter4All("salesmanId", orderVO.getSalesmanId());
        dbSQL.addParameter4All("referralCode", orderVO.getReferralCode());
        dbSQL.addParameter4All("salesmanName", orderVO.getSalesmanName());
        dbSQL.addParameter4All("orderStatus", orderVO.getCommonStatus());
        dbSQL.addParameter4All("payTimeStart", payTimeTimePO.getStartTime());
        dbSQL.addParameter4All("payTimeEnd", payTimeTimePO.getStopTime());
        dbSQL.initSQL();

        List<OrderVO> orderVOs = MySQLDao.search(dbSQL, OrderVO.class, conn);


        return orderVOs;
    }


    /**
     * 通过订单编号查找订单
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月10日
     *
     * @param orderNo
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPO getOrderByOrderNo(String orderNo, Connection conn) throws Exception {

        if (StringUtils.isEmpty(orderNo)) {
            MyException.newInstance(Config.getWords4WebGeneralError(), "未找到订单，OrderNo传入为空").throwException();
        }


        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     o.*");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_order o ");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND o.state = 0");
        sbSQL.append(" AND o.orderNum = '" + orderNo + "'");

        List<OrderPO> orders = MySQLDao.query(sbSQL.toString(), OrderPO.class, null, conn);
        if (orders == null || orders.size() != 1) {
            MyException.newInstance(Config.getWords4WebGeneralError(), "未找到订单").throwException();
        }

        return orders.get(0);
    }

    /**
     * 查询一个订单
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月25日
     *
     * @param orderNum
     * @param connection
     * @return
     * @throws Exception
     */
    public OrderVO loadOrderVOByOrderNumber(String orderNum, Connection connection) throws Exception {
        OrderVO vo = null;
        StringBuffer sbSQL = this.getOrderCommonSQL(orderNum);
        List<OrderVO> orders = MySQLDao.query(sbSQL.toString(), OrderVO.class, null, connection);
        if (orders.size() == 1) {
            vo = orders.get(0);
        }
        return vo;
    }

    /**
     * 网站：通过订单ID获取订单
     *
     * @return
     * @throws Exception
     */
    public OrderPO loadOrderByOrderNumber(String orderNum, Connection connection) throws Exception {
        OrderPO po = null;
        StringBuffer sbSQL = this.getOrderCommonSQL(orderNum);
        List<OrderPO> orders = MySQLDao.query(sbSQL.toString(), OrderPO.class, null, connection);
        if (orders.size() == 1) {
            po = orders.get(0);
        }
        return po;
    }

    /**
     * 获取常规的订单查询 SQL
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月25日
     *
     * @param orderNum
     * @return
     * @throws Exception
     */
    private StringBuffer getOrderCommonSQL(String orderNum) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT ");
        sbSQL.append("  *, CASE o.`Status` ");
        sbSQL.append(" WHEN 0 THEN ");
        sbSQL.append("  '未付款' ");
        sbSQL.append("  WHEN 1 THEN ");
        sbSQL.append("   '已支付' ");
        sbSQL.append("  WHEN 2 THEN ");
        sbSQL.append("   '申请退款' ");
        sbSQL.append("  WHEN 3 THEN ");
        sbSQL.append("   '已退款' ");
        sbSQL.append("  WHEN 4 THEN ");
        sbSQL.append("   '作废' ");
        sbSQL.append("  WHEN 5 THEN ");
        sbSQL.append("   '已兑付' ");
        sbSQL.append("  WHEN 6 THEN ");
        sbSQL.append("   '预约超时' ");
        sbSQL.append("  WHEN 7 THEN ");
        sbSQL.append("   '已支付待确认' ");
        sbSQL.append("   END AS statusStr ");
        sbSQL.append("        FROM ");
        sbSQL.append("  crm_order o ");
        sbSQL.append("  WHERE ");
        sbSQL.append("   o.id = '" + Database.encodeSQL(orderNum) + "' ");
        sbSQL.append("  AND o.state = 0 ");
        return sbSQL;
    }

    public OrderPO insertOrUpdate(OrderPO orderPO, String userId, Connection conn) throws Exception {



        MySQLDao.insertOrUpdate(orderPO, userId, conn);

        return orderPO;
    }

    public List<OrderPO> getListOrderPOByCustomerId (String customerId, Connection conn) throws Exception {

        String sql = "select * from crm_order o where o.state=0 and o.customerId=?";
        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sql).addParameter(1, customerId);

        List<OrderPO> orders = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), OrderPO.class, null, conn);

        return orders;
    }


    public OrderPO loadByOrderId(String orderId, Connection conn) throws Exception {
        OrderPO order = new OrderPO();
        order.setId(orderId);
        order.setState(Config.STATE_CURRENT);

        return MySQLDao.load(order, OrderPO.class, conn);
    }

    public OrderPO loadByLikeOrderId(String orderId, Connection conn) throws Exception {
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("41901712");
        dbSQL.addParameter4All("orderId", orderId);
        dbSQL.initSQL();

        List<OrderPO> orders = MySQLDao.search(dbSQL, OrderPO.class, conn);

        if (orders != null && orders.size() == 1) {
            return orders.get(0);
        }

        return null;
    }


    public OrderPO loadOrderPOBy_allinpayCircle_req_trace_num(String allinpayCircle_req_trace_num, Connection conn) throws Exception {
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("41901712");
        dbSQL.addParameter4All("allinpayCircle_req_trace_num", allinpayCircle_req_trace_num);
        dbSQL.initSQL();

        List<OrderPO> orders = MySQLDao.search(dbSQL, OrderPO.class, conn);

        if (orders != null && orders.size() == 1) {
            return orders.get(0);
        }

        return null;
    }

    public OrderVO getOrderVOByOrderId(String orderId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("A2ED1805");
        dbSQL.addParameter4All("orderId", orderId);
        dbSQL.initSQL();

        List<OrderVO> list = MySQLDao.search(dbSQL, OrderVO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }
}
