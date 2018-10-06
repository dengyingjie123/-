package com.youngbook.dao.allinpaycircle;

import com.youngbook.entity.po.allinpaycircle.TransactionPO;

import java.sql.Connection;

/**
 * Created by leevits on 7/28/2018.
 */
public interface ITransactionDao {
    public TransactionPO loadByRequestTraceNum(String requestTraceNum, int apiCommandDirection, Connection conn) throws Exception;
}
