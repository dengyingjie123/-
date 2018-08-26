package com.youngbook.service.system;

import com.youngbook.dao.system.ISystemFinanceSimpleRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 3/21/2017.
 */
@Component("systemFinanceSimpleRecordService")
public class SystemFinanceSimpleRecordService {

    @Autowired
    ISystemFinanceSimpleRecordDao systemFinanceSimpleRecordDao;

    public void newFinanceRecord(String accountName, int moneyIn, int moneyOut, String openTime, String bizId, String comment, Connection conn) throws Exception {
        systemFinanceSimpleRecordDao.newFinanceRecord(accountName, moneyIn, moneyOut, openTime, bizId, comment, conn);
    }
}
