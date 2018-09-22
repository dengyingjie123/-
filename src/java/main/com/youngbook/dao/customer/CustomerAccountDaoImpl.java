package com.youngbook.dao.customer;

import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.system.IKVDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.customer.CustomerAccountStatus;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.fdcg.FdcgCustomerAccountPO;
import com.youngbook.entity.po.production.OrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/5/31.
 */
@Component("customerAccountDao")
public class CustomerAccountDaoImpl implements ICustomerAccountDao {

    @Autowired
    IOrderDao orderDao;

    @Autowired
    IKVDao kvDao;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    public FdcgCustomerAccountPO fdcgGetCustomerAccountPO(String crmCustomerPersonalId, String bindStatus, Connection conn) throws Exception {

        StringUtils.checkIsEmpty(crmCustomerPersonalId, "crmCustomerPersonalId为空");

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("99AD1803");
        dbSQL.addParameter4All("crmCustomerPersonalId", crmCustomerPersonalId);
        dbSQL.addParameter4All("bindStatus", bindStatus);
        dbSQL.initSQL();

        List<FdcgCustomerAccountPO> customerAccountPOs = MySQLDao.search(dbSQL, FdcgCustomerAccountPO.class, conn);

        if (customerAccountPOs != null && customerAccountPOs.size() > 0) {
            return customerAccountPOs.get(0);
        }

        return null;
    }

    /**
     * 根据id查询客户账户
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerAccountPO getCustomerAccount(String customerId, Connection conn) throws Exception {

        List<CustomerAccountPO> listCustomerAccounts = this.getCustomerAccounts(customerId, conn);

        if (listCustomerAccounts != null && listCustomerAccounts.size() > 0){
            return listCustomerAccounts.get(0);
        }

        return null;
    }


    public List<CustomerAccountPO> getCustomerAccounts(String customerId, Connection conn) throws Exception {

        // 组织 SQL 和查询参数
        String sql = "select * from crm_customeraccount a where a.state = 0 and a.customerId = ? order by a.sid asc";
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sql);
        dbSQL.addParameter(1, customerId);

        // 执行查询并返回
        List<CustomerAccountPO> list = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), CustomerAccountPO.class, new ArrayList<KVObject>(), conn);

        // 对银行卡号进行解密
        for (CustomerAccountPO po : list) {
            po.setNumber(AesEncrypt.decrypt(po.getNumber()));
        }

        return list;

    }


    public CustomerAccountPO inertOrUpdate(CustomerAccountPO customerAccountPO, String operatorId, Connection conn) throws Exception {

        //aes加密银行账号
        if(customerAccountPO != null && !StringUtils.isEmpty(customerAccountPO.getNumber())) {

            /**
             * 防止二次加密
             */

            if (StringUtils.isNumeric(customerAccountPO.getNumber())) {
                customerAccountPO.setNumber(AesEncrypt.encrypt(customerAccountPO.getNumber()));
            }
        }


        // 保存账号名称
        if (StringUtils.isEmpty(customerAccountPO.getName()) && !StringUtils.isEmpty(customerAccountPO.getCustomerId())) {
            CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerAccountPO.getCustomerId(), conn);
            customerAccountPO.setName(customerPersonalPO.getName());
        }

        MySQLDao.insertOrUpdate(customerAccountPO, operatorId, conn);


        return customerAccountPO;
    }

    /**
     * 通过KV里获得对应的银行编号
     * @param accountId
     * @param parameterKey
     * @param conn
     * @return
     * @throws Exception
     */
    public String getBankCodeInKVParameter(String accountId, String parameterKey, Connection conn) throws Exception {

        CustomerAccountPO customerAccountPO = loadCustomerAccountPOByAccountId(accountId, conn);

        String bankCode = getBankCodeInKVParameterWithBankCode(customerAccountPO.getBankCode(), parameterKey, conn);

        return bankCode;
    }

    public String getBankCodeInKVParameterWithBankCode(String bankCode, String parameterKey, Connection conn) throws Exception {

        KVPO kvpo = kvDao.loadKVPO(bankCode, "Bank", conn);

        bankCode = StringUtils.getUrlParameters(kvpo.getParameter()).getItemString(parameterKey);

        return bankCode;
    }


    public CustomerAccountPO loadCustomerAccountPOByAccountId(String accountId, Connection conn) throws Exception {

        CustomerAccountPO customerAccountPO = new CustomerAccountPO();
        customerAccountPO.setId(accountId);
        customerAccountPO.setState(Config.STATE_CURRENT);

        customerAccountPO = MySQLDao.load(customerAccountPO, CustomerAccountPO.class, conn);

        if (customerAccountPO == null) {
            MyException.newInstance("无法获得客户账户信息", "accountId=" + accountId).throwException();
        }

        return customerAccountPO;
    }

    public CustomerAccountPO loadCustomerAccountPOByOrderId(String orderId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(orderId)) {
            return null;
        }

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("    ca.*");
        sbSQL.append(" FROM");
        sbSQL.append("    crm_order o,");
        sbSQL.append("    crm_customeraccount ca");
        sbSQL.append(" WHERE");
        sbSQL.append("    1 = 1");
        sbSQL.append(" AND o.state = 0");
        sbSQL.append(" AND ca.state = 0");
        sbSQL.append(" AND o.accountId = ca.id");
        sbSQL.append(" AND o.id = ?");

        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sbSQL);
        dbSQL.addParameter(1, orderId);

        List<CustomerAccountPO> list = MySQLDao.search(dbSQL, CustomerAccountPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 网站：插入客户的银行卡号码信息
     * @param account
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer insertCustomerAccount4W(CustomerAccountPO account, Connection conn) throws Exception {
        return MySQLDao.insertOrUpdate(account, Config.getSystemConfig("web.default.operatorId"), conn);
    }

    /**
     * 删除操作
     *
     * 作者：邓超
     * 内容：新增注释
     * 时间：2015-12-01
     *
     * @param
     * @param conn
     * @return
     * @throws Exception
     */
    public int removeById(String customerAccountId, String userId, Connection conn) throws Exception {


        /**
         * 检查是否有关联的订单
         */

        List<OrderPO> listOrderPOs = orderDao.getListOrderPOByAccountId(customerAccountId, conn);
        if (listOrderPOs != null && listOrderPOs.size() > 0) {
            MyException.newInstance("此账号下存在关联订单，无法删除", "customerAccountId=" + customerAccountId).throwException();
        }

        int count = 0;

        if (!StringUtils.isEmpty(customerAccountId)) {

            CustomerAccountPO customerAccount = new CustomerAccountPO();
            customerAccount.setId(customerAccountId);
            customerAccount.setState(Config.STATE_CURRENT);

            customerAccount = MySQLDao.load(customerAccount, CustomerAccountPO.class, conn);

            if (customerAccount != null) {
                MySQLDao.remove(customerAccount, userId, conn);
            }
        }

        return 1;
    }

    /**
     * 查询客户绑定银行卡的状态
     *
     * 作者：邓超
     * 内容：创建注释
     * 时间：2015-12-01
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public int getBankCardCount(String customerId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("客户编号为空").throwException();
        }

        // 银行卡记录里是否有多条银行卡记录
        CustomerAccountPO customerAccount4Search = new CustomerAccountPO();
        customerAccount4Search.setState(Config.STATE_CURRENT);
        customerAccount4Search.setCustomerId(customerId);
        List<CustomerAccountPO> list = this.list(customerAccount4Search, conn);
        if (list == null || list.size() == 0) {
            return CustomerAccountStatus.NO_CARD;
        }

        if (list.size() == 1) {
            return CustomerAccountStatus.ONLY_ONE_CARD;
        }

        if (list.size() > 1) {
            return CustomerAccountStatus.MORE_THAN_ONE_CARD;
        }

        return CustomerAccountStatus.UNKNOWN;
    }

    /**
     * 查询列表
     *
     * 作者：邓超
     * 内容：新增注释
     * 时间：2015-12-01
     *
     * @param customerAccount
     * @param request
     * @return
     * @throws Exception
     */
    public Pager list(CustomerAccountPO customerAccount, HttpServletRequest request, Connection conn) throws Exception {

        String sql ="SELECT a.* FROM crm_customeraccount a WHERE state = 0 AND a.customerId = ?";

        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sql);
        dbSQL.addParameter(1, customerAccount.getCustomerId());

        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), CustomerAccountPO.class, request, conn);
        return pager;
    }


    /**
     * 列出数据
     *
     * 作者：邓超
     * 内容：创建注释
     * 时间：2015-12-01
     *
     * @param customerAccount
     * @param conn
     * @return
     * @throws Exception
     */
    public List<CustomerAccountPO> list(CustomerAccountPO customerAccount, Connection conn) throws Exception {
        customerAccount.setState(Config.STATE_CURRENT);
        List<CustomerAccountPO> list = MySQLDao.search(customerAccount, CustomerAccountPO.class, null, null, conn);
        return list;
    }
}
