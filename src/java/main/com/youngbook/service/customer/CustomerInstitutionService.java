package com.youngbook.service.customer;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.MoneyUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.allinpaycircle.IAllinpayCircleDao;
import com.youngbook.dao.customer.ICustomerAccountDao;
import com.youngbook.dao.customer.ICustomerCertificateDao;
import com.youngbook.dao.customer.ICustomerInstitutionDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.service.BaseService;
import com.youngbook.entity.po.customer.CustomerInstitutionPO;
import com.youngbook.entity.vo.customer.CustomerInstitutionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-15
 * Time: 下午5:35
 * To change this template use File | Settings | File Templates.
 */
@Component("customerInstitutionService")
public class CustomerInstitutionService extends BaseService {

    @Autowired
    ICustomerAccountDao customerAccountDao;

    @Autowired
    ICustomerCertificateDao customerCertificateDao;

    @Autowired
    ICustomerInstitutionDao customerInstitutionDao;

    @Autowired
    IAllinpayCircleDao allinpayCircleDao;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    /**
     * 通联万小宝
     * 充值-机构自付
     * @param customerPersonalId
     * @param customerInstitutionAccountId
     * @param customerInstitutionId
     * @param money
     * @param productCodeCashAcct
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject allinpayCircle_Pay(String customerPersonalId, String customerInstitutionAccountId, String customerInstitutionId, double money, String productCodeCashAcct, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerPersonalId, conn);

        CustomerAccountPO customerInstitutionAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(customerInstitutionAccountId, conn);

        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(customerInstitutionAccountId, "allinpayCircleBankCode", conn);

        CustomerInstitutionPO customerInstitutionPO = customerInstitutionDao.loadByCustomerInstitutionId(customerInstitutionId, conn);

        String url = "http://118.126.103.58:8574/core/api/APICommand_receiveAllinpayCircle";

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("2080");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", customerPersonalPO.getAllinpayCircle_SignNum());
        transactionPO.getRequest().addItem("pay_mode", "4");
        transactionPO.getRequest().addItem("charge_flag", "1");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", customerInstitutionAccountPO.getNumber());
        transactionPO.getRequest().addItem("tel_num", customerInstitutionPO.getMobile());
        transactionPO.getRequest().addItem("cur_type", "156");
        transactionPO.getRequest().addItem("amt_tran", MoneyUtils.format2Fen(money));
        transactionPO.getRequest().addItem("product_code_cash_acct", productCodeCashAcct);
        transactionPO.getRequest().addItem("resp_url", url);


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        return returnObject;
    }


    /**
     * 销售负责人查询所属销售组的机构客户
     * @param customerInstitutionVO
     * @param conditions
     * @param currentPage
     * @param showRowCount
     * @param userId
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager listCustomers4DistributionToManagedSaleGroup(CustomerInstitutionVO customerInstitutionVO, List<KVObject> conditions, int currentPage, int showRowCount, String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得用户编号").throwException();
        }

        Pager pager = customerInstitutionDao.listCustomers4DistributionToManagedSaleGroup(customerInstitutionVO, conditions, currentPage, showRowCount, userId, conn);

        return pager;
    }


    public Pager listCustomers4DistributionToMe(CustomerInstitutionVO customerInstitutionVO, List<KVObject> conditions, int currentPage, int showRowCount, String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得用户编号").throwException();
        }

        Pager pager = customerInstitutionDao.listCustomers4DistributionToMe(customerInstitutionVO, conditions, currentPage, showRowCount, userId, conn);

        return pager;
    }


    public int passwordUpdate(CustomerInstitutionPO institution, UserPO user, Connection conn) throws Exception {
        int count = 0;
        CustomerInstitutionPO temp = new CustomerInstitutionPO();
        temp.setSid(institution.getSid());
        temp = MySQLDao.load(temp, CustomerInstitutionPO.class);
        temp.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(temp, conn);
        if (count == 1) {
            institution.setSid(MySQLDao.getMaxSid("crm_customerinstitution", conn));
            institution.setState(Config.STATE_CURRENT);
            institution.setOperatorId(user.getId());
            institution.setOperateTime(TimeUtils.getNow());
            institution.setPassword(StringUtils.md5(institution.getPassword()));
            count = MySQLDao.insert(institution, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    /**
     * @param customerInstitution
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(CustomerInstitutionPO customerInstitution, UserPO user, Connection conn) throws Exception {

        int count = 0;
        // 新增
        if (customerInstitution.getId().equals("")) {
            customerInstitution.setSid(MySQLDao.getMaxSid("crm_customerinstitution", conn));
            customerInstitution.setId(IdUtils.getUUID32());
            customerInstitution.setState(Config.STATE_CURRENT);
            customerInstitution.setOperatorId(user.getId());
            customerInstitution.setOperateTime(TimeUtils.getNow());
            customerInstitution.setPassword(StringUtils.md5("123456"));
            count = MySQLDao.insert(customerInstitution, conn);
        }
        // 更新
        else {
            CustomerInstitutionPO temp = new CustomerInstitutionPO();
            temp.setSid(customerInstitution.getSid());
            temp = MySQLDao.load(temp, CustomerInstitutionPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerInstitution.setSid(MySQLDao.getMaxSid("crm_customerinstitution", conn));
                customerInstitution.setState(Config.STATE_CURRENT);
                customerInstitution.setOperatorId(user.getId());
                customerInstitution.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerInstitution, conn);
            }
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    public int delete(CustomerInstitutionPO customerInstitution, String userId, Connection conn) throws Exception {
        int count = 0;
        customerInstitution = MySQLDao.load(customerInstitution, CustomerInstitutionPO.class);
        customerInstitution.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(customerInstitution, conn);
        if (count == 1) {
            customerInstitution.setSid(MySQLDao.getMaxSid("crm_customerinstitution", conn));
            customerInstitution.setState(Config.STATE_DELETE);
            customerInstitution.setOperatorId(userId);
            customerInstitution.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerInstitution, conn);
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        String id = customerInstitution.getId();
        String sql1 = "SELECT * from crm_customeraccount WHERE state=0 and customerId='" + Database.encodeSQL(id) + "'";
        String sql2 = "SELECT * from crm_customerCertificate WHERE state=0 and customerId='" + Database.encodeSQL(id) + "'";
        List<CustomerAccountPO> list1 = MySQLDao.query(sql1, CustomerAccountPO.class, null);
        List<CustomerCertificatePO> list2 = MySQLDao.query(sql2, CustomerCertificatePO.class, null);
        if (list1 != null && list1.size() > 0) {
            for (CustomerAccountPO po : list1) {
                customerAccountDao.removeById(po.getId(), userId, conn);
            }
        }
        if (list2 != null && list2.size() > 0) {
            for (CustomerCertificatePO certificate : list2) {
                customerCertificateDao.delete(certificate, userId, conn);
            }
        }

        return count;
    }


    /***
     * 初始化产品分期管理中融资机构tree列表
     * @param conn
     * @return
     * @throws Exception
     */
    public List<CustomerInstitutionVO> listFinanceInstitution(Connection conn) throws Exception{

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("SELECT id, name text FROM crm_customerinstitution  WHERE 1 = 1 AND State = 0");

        List<CustomerInstitutionVO> customerInstitutionVOs = MySQLDao.search(dbSQL, CustomerInstitutionVO.class, conn);

        return customerInstitutionVOs;
    }



}
