package com.youngbook.dao.system;

import java.sql.Connection;

/**
 * Created by Lee on 3/21/2017.
 */
public interface ISystemFinanceSimpleRecordDao {
    public void newFinanceRecord(String accountName, int moneyIn, int moneyOut, String openTime, String bizId, String comment, Connection conn) throws Exception;
}
