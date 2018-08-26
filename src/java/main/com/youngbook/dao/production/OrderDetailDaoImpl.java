package com.youngbook.dao.production;

import com.youngbook.common.MyException;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.OrderDetailPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("orderDetailDao")
public class OrderDetailDaoImpl implements IOrderDetailDao {


    /**
     * 保存订单明细
     *
     * 注意，订单明细保存需要在保存订单之前操作
     * @param order
     * @param operationMoney
     * @param operationOrderStatus
     * @param conn
     * @return
     * @throws Exception
     */
    public int saveOrderDetail( OrderPO order, double operationMoney, String operationTime, int operationOrderStatus, String description, String userId, Connection conn) throws Exception {

        OrderDetailPO detail = getOrderDetailFromOrder(order);

        detail.setStatus(operationOrderStatus);
        detail.setMoney(operationMoney);
        detail.setCreateTime(operationTime);
        detail.setDescription(description);

        detail.setStatusName(OrderStatus.getStatusName(detail.getStatus()));

        int nextLine = this.getOrderDetailNextLineNumber(detail.getOrderId(), conn);
        detail.setOrderLine(nextLine);

        MySQLDao.insertOrUpdate(detail, userId, conn);

        return 1;
    }


    public int saveOrderDetail(OrderPO order, double money, String operationTime, String description, String userId, Connection conn) throws Exception {

        OrderDetailPO detail = getOrderDetailFromOrder(order);

        detail.setCreateTime(operationTime);
        detail.setDescription(description);
        detail.setMoney(money);

        detail.setStatusName(OrderStatus.getStatusName(detail.getStatus()));

        int nextLine = this.getOrderDetailNextLineNumber(detail.getOrderId(), conn);
        detail.setOrderLine(nextLine);

        MySQLDao.insertOrUpdate(detail, userId, conn);

        return 1;
    }

    public OrderDetailPO getOrderDetailFromOrder(OrderPO order) throws Exception {
        OrderDetailPO detail = new OrderDetailPO();

        // 给订单详情赋值
        detail.setOrderId(order.getId());
        detail.setOrderNum(order.getOrderNum());

        detail.setCustomerId(order.getCustomerId());
        detail.setCustomerName(order.getCustomerName());

        detail.setProductionId(order.getProductionId());

        detail.setDescription(order.getDescription());
        detail.setCreateTime(order.getCreateTime());
        detail.setStatus(order.getStatus());

        String statusName = OrderStatus.getStatusName(order.getStatus());
        detail.setStatusName(statusName);

        detail.setMoney(order.getMoney());

        return detail;
    }

    /**
     * 获得订单明细下一个OrderLine数值
     *
     * Date: 2016-05-21 11:37:32
     * Author: leevits
     */
    public int getOrderDetailNextLineNumber(String orderId, Connection conn) throws Exception {

        String sql = "select * from crm_orderdetail where state=0 and orderid=?";
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sql);
        dbSQL.addParameter(1, orderId);

        List<OrderDetailPO> details = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), OrderDetailPO.class, null, conn);

        if (details != null) {
            return details.size() + 1;
        }

        return 1;
    }

    /**
     * 通过客户编号获得订单明细
     *
     * Date: 2016-05-22 7:50:43
     * Author: leevits
     */
    public List<OrderDetailPO> getOrderDetailsByCustomerId(String customerId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from crm_OrderDetail d where d.state=0 and d.customerId=?").addParameter(1, customerId);

        List<OrderDetailPO> details = MySQLDao.search(dbSQL, OrderDetailPO.class, conn);

        return details;
    }

}
