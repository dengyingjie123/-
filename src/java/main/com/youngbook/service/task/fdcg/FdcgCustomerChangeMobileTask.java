package com.youngbook.service.task.fdcg;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.JSONObjectDeserializer;
import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.fdcg.FdcgCustomerPO;
import com.youngbook.entity.po.fdcg.FdcgResponseData;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.system.LogService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Lee on 3/16/2018.
 */
public class FdcgCustomerChangeMobileTask extends Task {

    CustomerPersonalService customerPersonalService = Config.getBeanByName("customerPersonalService", CustomerPersonalService.class);

    public FdcgCustomerChangeMobileTask() {

        this.setName("FdcgCustomerChangeMobileTask");

        this.setStartTime("2010-03-09 00:00:0");
        this.setStopTime("2020-03-09 00:00:0");
        this.setRepeatSecond(10);
    }

    @Override
    public void run() {

        boolean isRunning = true;

        while (isRunning) {

            LogService.console("FdcgCustomerChangeMobileTask running - Start");
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
                dbSQL.addParameter4All("name", "用户更改手机号-异步回调");
                dbSQL.initSQL();

                List<FdcgResponseData> responseDataList = MySQLDao.search(dbSQL, FdcgResponseData.class, conn);

                if (responseDataList == null || responseDataList.size() == 0) {
                    System.out.println("FdcgCustomerChangeMobileTask 完成一次轮询");
                    isRunning = false;
                    continue;
                }

                FdcgResponseData responseData = responseDataList.get(0);

                Map<String, String> map = JSONDao.toMap(responseData.getContent());

                if (map.get("status").equals("1")) {
                    String newPhone = map.get("newPhone");

                    String accountNo = map.get("accountNo");
                    String userName = map.get("userName");


                    FdcgCustomerPO fdcgCustomerPO = customerPersonalService.fdcgLoadFdcgCustomerPO(accountNo, userName, conn);

                    if (fdcgCustomerPO != null) {
                        fdcgCustomerPO.setMobilePhone(newPhone);

                        MySQLDao.insertOrUpdate(fdcgCustomerPO, conn);

                        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(fdcgCustomerPO.getCrmCustomerPersonalId(), conn);
                        customerPersonalPO.setMobile(newPhone);

                        customerPersonalService.insertOrUpdate(customerPersonalPO, "0000", conn);
                    }
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

                Config.sendMessage("fdcg - FdcgCustomerChangeMobileTask", e.getMessage());

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


        LogService.console("FdcgCustomerChangeMobileTask running - Stop");

    }
}
