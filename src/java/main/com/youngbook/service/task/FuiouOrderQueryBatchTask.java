package com.youngbook.service.task;

import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.service.customer.CustomerAccountService;
import com.youngbook.service.pay.FuiouService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class FuiouOrderQueryBatchTask extends Task {

    OrderService orderService = Config.getBeanByName("orderService", OrderService.class);
    FuiouService fuiouService = Config.getBeanByName("fuiouService", FuiouService.class);
    CustomerAccountService customerAccountService = Config.getBeanByName("customerAccountService", CustomerAccountService.class);
    UserService userService = Config.getBeanByName("userService", UserService.class);


    public FuiouOrderQueryBatchTask() {
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

            // 查询时间段内系统里的未支付的订单
            List<OrderPO> orders = orderService.getWaiting4ConfirmFuiouOrders(queryDate, conn);
            if (orders == null || orders.size() == 0) {
                LogService.console(">>>>>>>>>> 查询结果：【" + queryDate + "】时间段里没有需要确认的订单");
                return;
            }

            // 未支付的订单到富友进行查询
            for(OrderPO order : orders) {
                String orderNum = order.getOrderNum();
                if(StringUtils.isEmpty(orderNum)) {
                    LogService.console(">>>>>>>>>> 注意，有问题：有一条记录没有订单编号，记录 ID 为 " + order.getId());
                } else {
                    System.out.println(">>>>>>>>>> 查询中的订单：" + order.getId() + "，" + order.getOrderNum());
                }
                Boolean result = fuiouService.pcPayQuery(order.getOrderNum(), conn);
                if(result) {
                    // 获取今天的时间，格式为 20161212
                    String now = TimeUtils.getNow();
                    String valueDate = TimeUtils.getTime(now, 1, TimeUtils.DATE);
                    // 获取银行卡列表
                    List<CustomerAccountPO> accounts = customerAccountService.getCustomerAccounts(order.getCustomerId(), conn);
                    if(accounts.size() == 0) {
                        MyException.newInstance(ReturnObjectCode.CUSTOMER_BANKCARD_NOT_BIND, "客户没有绑定银行卡").throwException();
                    }
                    // 获取银行卡
                    CustomerAccountPO account = accounts.get(0);
                    String barkCard = account.getNumber();
                    // 获取用户
                    UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), conn);
                    // 执行业务
                    fuiouService.sellProduction(orderNum, today4Payment, conn);
                    order.setValueDate(valueDate);

                    orderService.saleOrder(order, user.getId(), conn);
                } else {
                    System.out.println(">>>>>>>>>> 在富友中没有支付的订单：" + order.getId() + "，" + order.getOrderNum());
                }
            }

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
