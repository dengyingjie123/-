package com.youngbook.dao.production;

import com.youngbook.entity.po.common.TimePO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.vo.production.OrderVO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
public interface IOrderDao {

    public OrderPO insertOrUpdate(OrderPO orderPO, String userId, Connection conn) throws Exception;
    public List<OrderVO> getListOrderVO (OrderVO orderVO, TimePO payTimeTimePO, Connection conn) throws Exception;
    public OrderPO loadByLikeOrderId(String orderId, Connection conn) throws Exception;
    public List<OrderPO> getListOrderPOByAccountId(String accountId, Connection conn) throws Exception;
    public OrderVO getOrderVOByOrderId(String orderId, Connection conn) throws Exception;
    public OrderPO getOrderByOrderNo(String orderNo, Connection conn) throws Exception;

    public OrderPO loadByOrderId(String orderId, Connection conn) throws Exception;
    public List<OrderPO> getListOrderPOByCustomerId (String customerId, Connection conn) throws Exception;


    public boolean checkSignatureExists(String signature, Connection conn) throws Exception;
}
