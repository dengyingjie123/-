package com.youngbook.dao.allinpaycircle;

import com.youngbook.common.ReturnObject;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;

import java.sql.Connection;

/**
 * Created by leevits on 7/19/2018.
 */
public interface IAllinpayCircleDao {
    public ReturnObject sendTransaction(TransactionPO transactionPO, Connection conn) throws Exception;
}
