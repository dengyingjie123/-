package com.youngbook.dao.system;

import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.SystemFinanceSimpleRecordPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 3/21/2017.
 */
@Component("systemFinanceSimpleRecordDao")
public class SystemFinanceSimpleRecordDaoImpl implements ISystemFinanceSimpleRecordDao {

    public void newFinanceRecord(String accountName, int moneyIn, int moneyOut, String openTime, String bizId, String comment, Connection conn) throws Exception {

        SystemFinanceSimpleRecordPO systemFinanceSimpleRecordPO = new SystemFinanceSimpleRecordPO();
        systemFinanceSimpleRecordPO.setAccountName(accountName);
        systemFinanceSimpleRecordPO.setMoneyIn(moneyIn);
        systemFinanceSimpleRecordPO.setMoneyOut(moneyOut);
        systemFinanceSimpleRecordPO.setOpenTime(openTime);
        systemFinanceSimpleRecordPO.setBizId(bizId);
        systemFinanceSimpleRecordPO.setComment(comment);

        MySQLDao.insert(systemFinanceSimpleRecordPO, conn);

    }
}
