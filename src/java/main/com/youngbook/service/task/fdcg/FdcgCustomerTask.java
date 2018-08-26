package com.youngbook.service.task.fdcg;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.fdcg.FdcgCustomerPO;
import com.youngbook.entity.po.fdcg.FdcgResponseData;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.system.LogService;

import javax.xml.ws.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lee on 3/16/2018.
 */
public class FdcgCustomerTask extends Task {

    CustomerPersonalService customerPersonalService = Config.getBeanByName("customerPersonalService", CustomerPersonalService.class);

    public FdcgCustomerTask() {

        this.setName("FdcgCustomerTask");

        this.setStartTime("2010-03-09 00:00:0");
        this.setStopTime("2020-03-09 00:00:0");
        this.setRepeatSecond(10);
    }

    @Override
    public void run() {

        boolean isRunning = true;

        while (isRunning) {

            LogService.console("FdcgCustomerTask running - Start");
            Connection conn = null;
            try {


                conn = Database.getConnection();
                conn.setAutoCommit(false);


                /**
                 *
                 1. 搜索ResponseData里关于开户相关的记录
                 2. 调用CustomerService.linkCustomer()，完成客户关联
                 3. 清除日志里关于此用户的开户标记，作用是在一段时间内，防止客户重复开户（待实现）
                 */

                DatabaseSQL dbSQL = DatabaseSQL.newInstance("6AB71803");
                dbSQL.addParameter4All("name", "用户开户-异步回调");
                dbSQL.initSQL();

                List<FdcgResponseData> responseDataList = MySQLDao.search(dbSQL, FdcgResponseData.class, conn);

                if (responseDataList == null || responseDataList.size() == 0) {
                    System.out.println("FdcgCustomerTask 完成一次轮询");
                    isRunning = false;
                    continue;
                }

                FdcgResponseData responseData = responseDataList.get(0);

                FdcgCustomerPO customerPO = JSONDao.parse(responseData.getContent(), FdcgCustomerPO.class);

                if (customerPO != null) {
                    customerPersonalService.fdcgLinkCustomer(customerPO, conn);
                }

                responseData.setDealStatus("1");

                MySQLDao.insertOrUpdate(responseData, conn);

                conn.commit();


            } catch (Exception e) {
                try {
                    if (conn != null) {
                        conn.rollback();
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                Config.sendMessage("fdcg - FdcgCustomerTask", e.getMessage());

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                isRunning = false;
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }


        LogService.console("FdcgCustomerTask running - Stop");

    }
}
