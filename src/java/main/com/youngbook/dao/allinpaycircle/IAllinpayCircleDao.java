package com.youngbook.dao.allinpaycircle;

import com.youngbook.common.ReturnObject;
import com.youngbook.entity.po.allinpaycircle.AllinpayCircleReceiveRawDataPO;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;

import java.sql.Connection;

/**
 * Created by leevits on 7/19/2018.
 */
public interface IAllinpayCircleDao {
    public void dealPayByShare(Connection conn) throws Exception;
    public void dealDepositByInstitution(Connection conn) throws Exception;
    public void dealRawData(Connection conn) throws Exception;
    public AllinpayCircleReceiveRawDataPO saveReceiveRawData(AllinpayCircleReceiveRawDataPO allinpayCircleReceiveRawDataPO, Connection conn) throws Exception;
    public ReturnObject sendTransaction(TransactionPO transactionPO, Connection conn) throws Exception;
}
