package com.youngbook.service.production;

import com.youngbook.common.MyException;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IOrderDetailDao;
import com.youngbook.entity.po.production.OrderDetailPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component("orderDetailService")
public class OrderDetailService extends BaseService {

    @Autowired
    IOrderDao orderDao;

    @Autowired
    IOrderDetailDao orderDetailDao;


    public List<OrderDetailPO> getOrderDetailsByOrderId(String orderId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(orderId)) {
            MyException.newInstance("无法获得订单编号", "method=getOrderDetailsByOrderId").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from crm_orderdetail where state=0 and orderId=?").addParameter(1, orderId);

        List<OrderDetailPO> details = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), OrderDetailPO.class, null, conn);

        return details;
    }




}
