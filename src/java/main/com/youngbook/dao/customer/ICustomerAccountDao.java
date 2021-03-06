package com.youngbook.dao.customer;

import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.fdcg.FdcgCustomerAccountPO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/31.
 */
public interface ICustomerAccountDao {
    public String getBankCodeInKVParameterWithBankCode(String bankCode, String parameterKey, Connection conn) throws Exception;
    public FdcgCustomerAccountPO fdcgGetCustomerAccountPO(String crmCustomerPersonalId, String bindStatus, Connection conn) throws Exception;
    public CustomerAccountPO getCustomerAccount(String customerId, Connection conn) throws Exception;
    public List<CustomerAccountPO> getCustomerAccounts(String customerId, Connection conn) throws Exception;
    public CustomerAccountPO loadCustomerAccountPOByOrderId(String orderId, Connection conn) throws Exception;
    public String getBankCodeInKVParameter(String accountId, String parameterKey, Connection conn) throws Exception;
    public CustomerAccountPO loadCustomerAccountPOByAccountId(String accountId, Connection conn) throws Exception;
    public Integer insertCustomerAccount4W(CustomerAccountPO account, Connection conn) throws Exception;

    public int getBankCardCount(String customerId, Connection conn) throws Exception;

    public Pager list(CustomerAccountPO customerAccount, HttpServletRequest request, Connection conn) throws Exception;

    public int removeById(String customerAccountId, String userId, Connection conn) throws Exception;

    public List<CustomerAccountPO> list(CustomerAccountPO customerAccount, Connection conn) throws Exception;

    public CustomerAccountPO inertOrUpdate(CustomerAccountPO customerAccountPO, String operatorId, Connection conn) throws Exception;
}
