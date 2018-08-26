package com.youngbook.service.allinpay;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.entity.po.core.TransferPO;
import com.youngbook.entity.po.core.TransferTargetType;
import com.youngbook.service.core.IMoneyTransferService;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AllinpayBatchPaymentServiceTest extends TestCase {

    @Test
    public void testBatchDaiFuV2() throws Exception {

        IMoneyTransferService service = new AllinpayBatchPaymentService();


        Connection conn = Config.getConnection();
        try {

            List<TransferPO> transfers = new ArrayList<TransferPO>();

            TransferPO transfer = new TransferPO();

            transfer.setTargetAccountName("李扬");
            transfer.setTargetType(TransferTargetType.Personal);
            transfer.setTargetAccountNo("6225888712203953");
            transfer.setTargetBank("308");
            transfer.setMoney(0.01);

//            transfer.setTargetAccountName("深圳公达资产管理有限公司");
//            transfer.setTargetType(TransferTargetType.Company);
//            transfer.setTargetAccountNo("755930379010901");
//            transfer.setTargetBank("308");
//            transfer.setMoney(0.02);


            transfers.add(transfer);
            service.doTransfer(transfers, conn);

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }
}