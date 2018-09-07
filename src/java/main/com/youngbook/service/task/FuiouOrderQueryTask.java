package com.youngbook.service.task;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderPayChannel;
import com.youngbook.service.customer.CustomerAccountService;
import com.youngbook.service.pay.FuiouDirectService;
import com.youngbook.service.pay.FuiouService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.system.UserService;

import java.nio.channels.Pipe;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class FuiouOrderQueryTask extends Task {

    OrderService orderService = Config.getBeanByName("orderService", OrderService.class);
    FuiouService fuiouService = Config.getBeanByName("fuiouService", FuiouService.class);
    FuiouDirectService fuiouDirectService = Config.getBeanByName("fuiouDirectService", FuiouDirectService.class);
    CustomerAccountService customerAccountService = Config.getBeanByName("customerAccountService", CustomerAccountService.class);
    UserService userService = Config.getBeanByName("userService", UserService.class);


    public FuiouOrderQueryTask() {
        this.setName("FuiouOrderQueryBatchTask");
        this.setStartTime("2010-02-08 00:00:0");
        this.setStopTime("2020-02-08 00:00:0");
        this.setRepeatSecond(5 * 60);
    }


    /**
     * 运行任务
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月23日
     *
     */
    @Override
    public void run() {
        try {
            String today = TimeUtils.getNowDate();
            doQuery(today);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(">>>>>>>>>> 正在运行：富友订单 - 支付确认的批量查询");
    }


    /**
     * 执行查询
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月23日
     *
     * @param queryDate
     * @throws Exception
     */
    public void doQuery(String queryDate) throws Exception {

        Connection conn = null;
        String today4Payment = TimeUtils.getDatePaymentString(new Date());

        try {
            // 获取连接
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            String url = "https://pay.fuiou.com/smAQueryGate.do";

            String orderId = "6245436081899048960";

            Boolean r = fuiouService.pcPayQuery(orderId, conn);

            System.out.println("查询结果： " + r.toString());

            conn.commit();

        }
        catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
