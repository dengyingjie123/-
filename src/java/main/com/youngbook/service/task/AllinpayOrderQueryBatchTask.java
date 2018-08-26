package com.youngbook.service.task;

import com.aipg.merchantorder.MerchantOrderQueryBatchResult;
import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.allinpay.AllinPayOrderPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.service.allinpay.MerchantOrderQueryService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.production.ProductionService;
import com.youngbook.service.system.LogService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lee on 12/4/2015.
 */
public class AllinpayOrderQueryBatchTask extends Task {

    OrderService orderService = Config.getBeanByName("orderService", OrderService.class);

    ProductionService productionService = Config.getBeanByName("productionService", ProductionService.class);

    MerchantOrderQueryService merchantOrderQueryService = Config.getBeanByName("merchantOrderQueryService", MerchantOrderQueryService.class);

    LogService logService = Config.getBeanByName("logService", LogService.class);

    public AllinpayOrderQueryBatchTask() {

        this.setName("AllinpayOrderQueryBatchTask");
//        this.setStartTime ( "2010-01-05 20:52:00" );
//        this.setStopTime ( "2015-2-23 21:01:30" );
//        this.setRepeatSecond ( 5 );
//        this.setStartTime(Config.getValue("//config/timerunner/tasks/task[@name='"
//                            + this.getName() + "']/starttime"));
//        this.setStopTime(Config.getValue("//config/timerunner/tasks/task[@name='"
//                + this.getName() + "']/stoptime"));
//        this.setRepeatSecond (Long.valueOf(Config.getValue("//config/timerunner/tasks/task[@name='"
//                + this.getName() + "']/repeattime")));

        this.setStartTime("2010-02-08 00:00:0");
        this.setStopTime("2020-02-08 00:00:0");
        this.setRepeatSecond(5 * 60);
    }

    @Override
    public void run() {

        try {

            String today = TimeUtils.getNowDate();

            doQuery(today);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("AllinpayOrderQueryBatchTask running");
    }


    public void doQuery(String queryDate) throws Exception {


        Connection conn = null;
        try {

            conn = Database.getConnection();
            conn.setAutoCommit(false);

            List<OrderPO> orders = orderService.getWaiting4ConfirmAllinpayOrders(queryDate, conn);

            if (orders == null || orders.size() == 0) {
                LogService.console("【"+queryDate+"】时间段里没有需要确认的订单");
                return;
            }


            MerchantOrderQueryBatchResult merchantOrderQueryBatchResult = merchantOrderQueryService.queryMany(queryDate);

            if (merchantOrderQueryBatchResult.isVerified()) {

                for (AllinPayOrderPO allinPayOrder : merchantOrderQueryBatchResult.getAllinPayOrders()) {

                    try {
                        productionService.sellProductionManualDo(allinPayOrder.getOrderNo(), allinPayOrder.getOrderDatetime(), conn);
                        String logMessage = "自动任务调整设置通联订单号为【"+allinPayOrder.getOrderNo()+"】的订单状态";
                        logService.save("通联支付", logMessage, null);
                    }
                    catch (Exception ex) {
                        logService.save("通联支付", "doQuery", MyException.getExceptionMessage(ex));
                    }

                }

                conn.commit();
            }


        }
        catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }

            } catch (Exception e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }

}
