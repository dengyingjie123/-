package com.youngbook.service.production;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.vo.production.OrderVO;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;

public class OrderServiceTest extends TestCase {

    OrderService orderService = Config.getBeanByName("orderService", OrderService.class);

    @Test
    public void testLoadOrderVO() throws Exception {
        Connection conn = Config.getConnection();
        try {

            String mobile = "13888939712";
            String payCode = "000840";

            OrderVO orderVO = orderService.loadOrderVO(mobile, payCode, conn);

            System.out.println(orderVO.getProductionName());
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    @Test
    public void testBuildAndPayOrder() throws Exception {


        Connection conn = Config.getConnection();
        try {
            // 王国基
            String customerId = "63CC28BB010E4F62860F758F1473A0FE";
            // 通宝年享7号
            String productionId = "ACC58235E9B647FBBDA4F6A5A871DA5B";
            double money = 980000.00;
            String createTime = "2016-04-26 14:00:00";
            String payTime = "2016-04-26 14:00:00";
            String referralCode = "";
            String operatorId = "0000";

//            OrderPO orderPO = orderService.build(customerId, productionId, money,createTime, payTime, referee, operatorId, conn);
//
//            System.out.println("订单编号" + orderPO.getId());
//
//            System.out.println("开始销售订单");
//            boolean needPaymentPlan = true;
//            orderService.saleOnlineOrderDo(orderPO,createTime, payTime, "", customerId, needPaymentPlan, conn);
//            System.out.println("结束销售订单");
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    @Test
    public void testSaleOnlineOrderDo() throws Exception {
        Connection conn = Config.getConnection();
        try {
            OrderPO orderPO = new OrderPO();
            orderPO.setId("BD2991AF90E34FC7A78FD27AB3ECF3FB");

            orderPO = MySQLDao.load(orderPO, OrderPO.class, conn);



            // 王国基
            String customerId = "63CC28BB010E4F62860F758F1473A0FE";
            boolean needPaymentPlan = true;
//            orderService.saleOnlineOrderDo(orderPO, "", customerId, needPaymentPlan, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }
}