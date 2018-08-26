package com.youngbook.dao.customer;

import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.fdcg.FdcgCustomerPO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.entity.vo.customer.CustomerVO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
public interface ICustomerPersonalDao {

    public CustomerPersonalPO loadByCustomerPersonalNumber(String personalNumber, Connection conn) throws Exception;
    public FdcgCustomerPO fdcgLoadCustomerPO(String fdcgCustomerId, Connection conn) throws Exception;
    public FdcgCustomerPO fdcgLoadFdcgCustomerPO(String accountNo, String userName, Connection conn) throws Exception;

    public FdcgCustomerPO fdcgLoadFdcgCustomerPOByCrmCustomerPersonalId(String customerPersonalId, Connection conn) throws Exception;

    public FdcgCustomerPO fdcgSave(FdcgCustomerPO customerPO, Connection conn) throws Exception;

    public Pager listPagerCustomerVO(CustomerPersonalVO customerVO, int currentPage, int showRowCount, Connection conn) throws Exception;
    public CustomerVO loadCustomerVO(String customerId, Connection conn) throws Exception;
    public List<CustomerPersonalVO> listCustomerPersonalVO(String userId, String customerId,String referralCode, Connection conn) throws Exception;
    public CustomerPersonalVO loadCustomerVOByCustomerPersonalId(String customerPersonalId, Connection conn) throws Exception;
    public CustomerPersonalPO getCustomerPersonalsByMobile(String mobile, Connection conn) throws Exception;
    public List<CustomerPersonalPO> getCustomerPersonalAssignedByUserId(String userId, Connection conn) throws Exception;
    public String getCustomerPersonalNumber(Connection conn) throws Exception;
    public int initCustomerMoney(CustomerPersonalPO personal, Connection conn) throws Exception;
    public CustomerPersonalPO loadCustomerByLoginName(String loginName, Connection conn) throws Exception;
    public CustomerPersonalPO loadCustomerByMobile(String mobile, Connection conn) throws Exception;
    public CustomerPersonalPO loadByCustomerPersonalId(String customerPersonalId, Connection conn) throws Exception;
    public Integer updateCustomerRealName(String id, String realName, Connection conn) throws Exception;
    public CustomerPersonalPO insertOrUpdate(CustomerPersonalPO customerPersonalPO, String userId, Connection conn) throws Exception;
}
