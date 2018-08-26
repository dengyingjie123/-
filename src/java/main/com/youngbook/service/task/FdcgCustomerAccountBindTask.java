package com.youngbook.service.task;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.fdcg.FdcgCustomerAccountPO;
import com.youngbook.entity.po.fdcg.FdcgResponseData;
import com.youngbook.service.customer.CustomerAccountService;
import com.youngbook.service.system.LogService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class FdcgCustomerAccountBindTask extends Task {

    CustomerAccountService customerAccountService = Config.getBeanByName("customerAccountService", CustomerAccountService.class);

    public FdcgCustomerAccountBindTask() {

        this.setName("FdcgCustomerAccountBindTask");

        this.setStartTime("2010-03-09 00:00:0");
        this.setStopTime("2020-03-09 00:00:0");
        this.setRepeatSecond(10);

    }

    @Override
    public void run() {

        boolean isRunning = true;

        while (isRunning) {

            LogService.console("FdcgCustomerAccountBindTask running - Start");
            Connection conn = null;
            try {


                conn = Database.getConnection();
                conn.setAutoCommit(false);


                DatabaseSQL dbSQL = DatabaseSQL.newInstance("6AB71803");
                dbSQL.addParameter4All("name", "用户绑定银行卡-异步回调");
                dbSQL.initSQL();

                List<FdcgResponseData> responseDataList = MySQLDao.search(dbSQL, FdcgResponseData.class, conn);

                if (responseDataList == null || responseDataList.size() == 0) {
                    System.out.println("FdcgCustomerAccountBindTask 完成一次轮询");
                    isRunning = false;
                    continue;
                }

                FdcgResponseData responseData = responseDataList.get(0);

                FdcgCustomerAccountPO customerAccountPO = JSONDao.parse(responseData.getContent(), FdcgCustomerAccountPO.class);

                if (customerAccountPO != null) {
                    customerAccountService.fdcgBind(customerAccountPO, conn);
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

                Config.sendMessage("fdcg - FdcgCustomerAccountBindTask", e.getMessage());

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


        LogService.console("FdcgCustomerAccountBindTask running - Stop");

    }

}
