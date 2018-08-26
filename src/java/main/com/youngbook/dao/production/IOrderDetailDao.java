package com.youngbook.dao.production;

import com.youngbook.entity.po.production.OrderDetailPO;
import com.youngbook.entity.po.production.OrderPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
public interface IOrderDetailDao {

    public int getOrderDetailNextLineNumber(String orderId, Connection conn) throws Exception;

    public OrderDetailPO getOrderDetailFromOrder(OrderPO order) throws Exception;

    public int saveOrderDetail( OrderPO order, double operationMoney, String operationTime, int operationOrderStatus, String description, String userId, Connection conn) throws Exception;
    public int saveOrderDetail(OrderPO order, double money, String operationTime, String description, String userId, Connection conn) throws Exception;
    public List<OrderDetailPO> getOrderDetailsByCustomerId(String customerId, Connection conn) throws Exception;
}
