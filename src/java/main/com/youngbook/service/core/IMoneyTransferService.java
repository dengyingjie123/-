package com.youngbook.service.core;

import com.youngbook.common.ReturnObject;
import com.youngbook.entity.po.core.TransferPO;

import java.sql.Connection;
import java.util.List;


public interface IMoneyTransferService {

    public ReturnObject doTransfer(List<TransferPO> transfers, Connection conn) throws Exception;
}
