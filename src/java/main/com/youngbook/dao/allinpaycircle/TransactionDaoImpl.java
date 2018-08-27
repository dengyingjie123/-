package com.youngbook.dao.allinpaycircle;

import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import com.youngbook.entity.po.core.APICommandDirection;
import com.youngbook.entity.po.core.APICommandPO;
import com.youngbook.entity.po.core.APICommandType;
import org.apache.poi.util.XMLHelper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by leevits on 7/28/2018.
 */
@Component("transactionDao")
public class TransactionDaoImpl implements ITransactionDao {

    public TransactionPO loadByRequestTraceNum(String requestTraceNum, int apiCommandDirection, Connection conn) throws Exception {



        String apiName = "接收";

        if (apiCommandDirection == APICommandDirection.Send) {
            apiName = "发送";
        }


        DatabaseSQL dbSQL = DatabaseSQL.newInstance("E5F01807").addParameter4All("bizId", requestTraceNum)
                .addParameter4All("apiName", apiName);
        dbSQL.initSQL();

        List<APICommandPO> apiCommandPOs = MySQLDao.search(dbSQL, APICommandPO.class, conn);

        TransactionPO transactionPO = new TransactionPO();

        if (apiCommandPOs != null && apiCommandPOs.size() == 1) {
            APICommandPO apiCommandPO = apiCommandPOs.get(0);

            String command = apiCommandPO.getCommands();

            if (apiCommandPO.getCommandType() == APICommandType.Xml) {

                XmlHelper helper = new XmlHelper(command);

                String processingCode = helper.getValue("/transaction/head/processing_code");
                String transDate = helper.getValue("/transaction/head/trans_date");


                transactionPO.setProcessing_code(processingCode);
                transactionPO.setTrans_date(transDate);

            }
        }



        return transactionPO;
    }
}
