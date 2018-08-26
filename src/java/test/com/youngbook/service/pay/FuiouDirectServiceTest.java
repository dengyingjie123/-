package com.youngbook.service.pay;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import org.junit.Test;

import java.sql.Connection;

public class FuiouDirectServiceTest {

    FuiouDirectService fuiouDirectService = Config.getBeanByName("fuiouDirectService", FuiouDirectService.class);

    @Test
    public void testBuildPaymentXMLWithDom4J() throws Exception {
//        fuiouDirectService.buildPaymentXMLWithDom4J();
    }

    @Test
    public void testPayment() throws Exception {
        String bizNo = TimeUtils.getNow(TimeUtils.Format_YYYYMMDDHHMMSS);

        String bankCode = "0308";

        String cityCode = "5840";

        String branchName = "招商银行";

//        String customerName = "李扬";  // 深圳公达资产管理有限公司
        String customerName = "深圳公达资产管理有限公司";  // 深圳公达资产管理有限公司

//        String bankCard = "6225888712203953";  // 755930379010901
        String bankNumber = "755930379010901";  // 755930379010901

        String mobile = "";

        int money = 3;

        Connection conn = Config.getConnection();
        try {
            fuiouDirectService.payment(bizNo, bankCode, cityCode, branchName, bankNumber,customerName, mobile, money, conn);

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }


        // String bizNo, String bankCode, String cityCode, , , , , Integer money, Connection conn

        System.out.println(bizNo);
        //
    }
}