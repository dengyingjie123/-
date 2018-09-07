package com.youngbook.service.task;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.system.SmsPO;
import com.youngbook.entity.po.system.SmsStatus;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.system.SmsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lee on 12/4/2015.
 */
public class OrderTask extends Task {


    public OrderTask() {

        this.setName("OrderTask");
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
        this.setRepeatSecond(20 * 60);
    }

    @Override
    public void run() {
        Connection conn = null;
        try {

            System.out.println("开始执行清理超时订单任务");

            conn = Database.getConnection();
            conn.setAutoCommit(false);

            OrderService orderService = Config.getBeanByName("orderService", OrderService.class);

            DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from crm_order o where o.state=0 and o.`Status` in (0) and o.AppointmentTime<DATE_ADD(SYSDATE(),INTERVAL -15 MINUTE)");

            List<OrderPO> orderPOs = MySQLDao.search(dbSQL, OrderPO.class, conn);

            for (int i = 0; orderPOs != null && i < orderPOs.size(); i++) {
                OrderPO orderPO = orderPOs.get(i);

                orderService.appointmentOrderCancel(orderPO.getId(), Config.getDefaultOperatorId(), conn);

                System.out.println("取消订单：" + orderPO.getOrderNum() + "，客户：" + orderPO.getCustomerName());
            }


            conn.commit();
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


        System.out.println("OrderTask running");
    }
}
