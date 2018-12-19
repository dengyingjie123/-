package com.youngbook.dao.customer;

import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.MoneyUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.fdcg.FdcgCustomerPO;
import com.youngbook.entity.vo.customer.CustomerAccountVO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.entity.vo.customer.CustomerVO;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("customerPersonalDao")
public class CustomerPersonalDaoImpl implements ICustomerPersonalDao {

    public CustomerPersonalPO insertOrUpdate(CustomerPersonalPO customerPersonalPO, String userId, Connection conn) throws Exception {

        MySQLDao.insertOrUpdate(customerPersonalPO, userId, conn);

        return customerPersonalPO;

    }

    public FdcgCustomerPO fdcgLoadFdcgCustomerPOByCrmCustomerPersonalId(String customerPersonalId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("A9991803");
        dbSQL.addParameter4All("customerPersonalId", customerPersonalId);
        dbSQL.initSQL();

        List<FdcgCustomerPO> customerPOList = MySQLDao.search(dbSQL, FdcgCustomerPO.class, conn);

        if (customerPOList != null && customerPOList.size() == 1) {
            return customerPOList.get(0);
        }

        return null;
    }


    public FdcgCustomerPO fdcgLoadCustomerPO(String fdcgCustomerId, Connection conn) throws Exception {

        FdcgCustomerPO customerPO = new FdcgCustomerPO();
        customerPO.setId(fdcgCustomerId);
        customerPO.setState(Config.STATE_CURRENT);

        customerPO = MySQLDao.load(customerPO, FdcgCustomerPO.class, conn);

        return customerPO;
    }

    public FdcgCustomerPO fdcgLoadFdcgCustomerPO(String accountNo, String userName, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("A9991803");
        dbSQL.addParameter4All("accountNo", accountNo);
        dbSQL.addParameter4All("userName", userName);
        dbSQL.initSQL();

        List<FdcgCustomerPO> customerPOList = MySQLDao.search(dbSQL, FdcgCustomerPO.class, conn);

        if (customerPOList != null && customerPOList.size() == 1) {
            return customerPOList.get(0);
        }

        return null;
    }


    public FdcgCustomerPO fdcgSave(FdcgCustomerPO customerPO, Connection conn) throws Exception {

        MySQLDao.insertOrUpdate(customerPO, conn);

        return customerPO;
    }


    /**
     * 查询所有用户-分页
     * @param customerVO
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager listPagerCustomerVO(CustomerPersonalVO customerVO, int currentPage, int showRowCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("955E1804");
        //dbSQL.addParameter4All("saleManId", customerVO.getSaleManId());
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ order by _ft_.personalNumber desc");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), customerVO, null, currentPage, showRowCount, queryType, conn);

        return pager;
    }



    public List<CustomerPersonalVO> listCustomerPersonalVO(String userId, String customerId, String referralCode, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listCustomerPersonalVO", "CustomerPersonalDaoImplSQL", CustomerPersonalDaoImpl.class);

        dbSQL.addParameter4All("customerId", customerId);
        dbSQL.addParameter4All("userId", userId);
        dbSQL.addParameter4All("referralCode", referralCode);

        dbSQL.initSQL();

        List<CustomerPersonalVO> list = MySQLDao.search(dbSQL, CustomerPersonalVO.class, conn);

        return list;
    }

    public CustomerPersonalVO loadCustomerVOByCustomerPersonalId(String customerPersonalId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("5CDB1807").addParameter4All("customerId", customerPersonalId);
        dbSQL.initSQL();

        List<CustomerPersonalVO> list = MySQLDao.search(dbSQL, CustomerPersonalVO.class, conn);

        if (list != null && list.size() == 1) {
            CustomerPersonalVO customerPersonalVO = list.get(0);

            String totalPaymentPrincipalMoney = MoneyUtils.format2String(customerPersonalVO.getTotalPaymentPrincipalMoney());
            customerPersonalVO.setTotalPaymentPrincipalMoneyWithFormat(totalPaymentPrincipalMoney);

            String totalProfitMoney = MoneyUtils.format2String(customerPersonalVO.getTotalProfitMoney());
            customerPersonalVO.setTotalProfitMoneyWithFormat(totalProfitMoney);

            DatabaseSQL dbSQLCustomerAccount = DatabaseSQL.getInstance("select * from crm_customeraccount a where a.state=0 and a.CustomerId=?").addParameter(1, customerPersonalVO.getId());


            /**
             * 构建银行账号
             */
            List<CustomerAccountVO> accountVOs = MySQLDao.search(dbSQLCustomerAccount, CustomerAccountVO.class, conn);
            for (int i = 0; accountVOs != null && i < accountVOs.size(); i++) {
                CustomerAccountVO accountVO = accountVOs.get(i);
                String numberWithoutMask = AesEncrypt.decrypt(accountVO.getNumber());
                accountVO.setNumberWithoutMask(numberWithoutMask);

                accountVO.setNumber4Short(StringUtils.lastString(numberWithoutMask, 4));
            }
            customerPersonalVO.setAccountVOs(accountVOs);


            /**
             * 构建身份证号
             */
            DatabaseSQL dbSQLCertificate = DatabaseSQL.getInstance("select * from crm_customercertificate a where a.state=0 and a.CustomerId=?").addParameter(1, customerPersonalVO.getId());

            List<CustomerCertificatePO> certificatePOs = MySQLDao.search(dbSQLCertificate, CustomerCertificatePO.class, conn);

            if (certificatePOs != null && certificatePOs.size() == 1) {
                CustomerCertificatePO customerCertificatePO = certificatePOs.get(0);
                String idCardWithoutMask = AesEncrypt.decrypt(customerCertificatePO.getNumber());
                customerPersonalVO.setIdCardNumber(idCardWithoutMask);
            }
            else {
                customerPersonalVO.setIdCardNumber("");
            }


            /**
             * 富滇存管部分
             */

//            dbSQL = DatabaseSQL.newInstance("A9991803");
//            dbSQL.addParameter4All("customerPersonalId", customerPersonalId);
//            dbSQL.initSQL();
//
//            List<FdcgCustomerPO> customerPOList = MySQLDao.search(dbSQL, FdcgCustomerPO.class, conn);
//
//            if (customerPOList != null && customerPOList.size() == 1) {
//                FdcgCustomerPO customerPO = customerPOList.get(0);
//
//                customerPersonalVO.setFdcgAccountNo(customerPO.getAccountNo());
//                customerPersonalVO.setFdcgCustomerId(customerPO.getId());
//                customerPersonalVO.setFdcgUserName(customerPO.getUserName());
//
//            }

            return customerPersonalVO;
        }

        return null;
    }

    public CustomerPersonalPO getCustomerPersonalsByMobile(String mobile, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from crm_customerpersonal where state=0 and mobile=?").addParameter(1, mobile);

        List<CustomerPersonalPO> list = MySQLDao.search(dbSQL, CustomerPersonalPO.class, conn);

        if (list != null && list.size() > 1) {
            MyException.newInstance("有多个客户与此手机号绑定", "mobile=" + mobile).throwException();
        }

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public List<CustomerPersonalPO> getCustomerPersonalAssignedByUserId(String userId, Connection conn) throws Exception {
        List<CustomerPersonalPO> list = null;

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select c.* from crm_customerpersonal c, v_customer_saleman v where 1=1 and c.state=0 and c.id=v.id and v.saleManId=?").addParameter(1, userId);

        list = MySQLDao.search(dbSQL, CustomerPersonalPO.class, conn);

        return list;
    }

    /**
     * 获得个人客户编号
     *
     * @return
     * @throws Exception
     */
    public String getCustomerPersonalNumber(Connection conn) throws Exception {
        //序列
        int sequence = MySQLDao.getSequence("customerCode", conn);
        //补位
        return "04" + StringUtils.buildNumberString(sequence, 8);
    }

    /**
     * 保存客户的资金记录
     * <p/>
     * 创建人：方斌杰
     * 时间：2015-7-8
     * 内容：创建代码
     * <p/>
     * 修改人：邓超
     * 内容：修改注释
     * 时间：2015年12月4日
     *
     * @param personal
     * @param conn
     * @return
     * @throws Exception
     */
    public int initCustomerMoney(CustomerPersonalPO personal, Connection conn) throws Exception {

        // 保存客户资金信息
        CustomerMoneyPO money = new CustomerMoneyPO();
        money.setCustomerId(personal.getId());
        money.setFrozenMoney(0);
        money.setExpectedMoney(0);
        money.setAvailableMoney(0);
        money.setInvestedMoney(0);
        money.setTotalProfitMoney(0);

        int count = MySQLDao.insertOrUpdate(money, conn);
        return count;
    }

    /**
     * 更新客户的真实姓名
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-12-01
     *
     * @param id
     * @param realName
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer updateCustomerRealName(String id, String realName, Connection conn) throws Exception {
        CustomerPersonalPO customerPersonal = new CustomerPersonalPO();
        customerPersonal.setId(id);
        customerPersonal.setState(Config.STATE_CURRENT);
        customerPersonal = MySQLDao.load(customerPersonal, CustomerPersonalPO.class, conn);

        // 更新用户信息
        customerPersonal.setState(Config.STATE_UPDATE);
        customerPersonal.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        customerPersonal.setOperateTime(TimeUtils.getNow());
        Integer count = MySQLDao.update(customerPersonal, conn);

        // 新增加用户信息
        if (count == 1) {
            customerPersonal.setSid(MySQLDao.getMaxSid("crm_customerpersonal", conn));
            customerPersonal.setState(Config.STATE_CURRENT);
            customerPersonal.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            customerPersonal.setOperateTime(TimeUtils.getNow());
            customerPersonal.setName(realName);
            count = MySQLDao.insert(customerPersonal, conn);
        }

        return count;
    }

    public CustomerPersonalPO loadCustomerPO(CustomerPersonalPO customerPersonalPO, Connection conn) throws Exception {

        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("CWQIP5DE");
        databaseSQL.addParameter4All("name", customerPersonalPO.getName());
        databaseSQL.addParameter4All("mobile", customerPersonalPO.getMobile());
        databaseSQL.initSQL();




        List<CustomerPersonalPO> search = MySQLDao.search(databaseSQL, CustomerPersonalPO.class, conn);
        if(search.size() == 1){
            return search.get(0);
        }




        return null;
    }

    /**
     * 网站：通过 Customer mobile 获取 Customer
     * <p/>
     * 修改人：付高杨
     * 时间：3/31/2016
     * 内容：增加异常处理
     * <p/>
     *
     * @param mobile
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerPersonalPO loadCustomerByMobile(String mobile, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     *");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal c");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND c.Mobile = ?");

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, mobile, parameters);

        List<CustomerPersonalPO> customers = MySQLDao.search(sbSQL.toString(), parameters, CustomerPersonalPO.class, null, conn);
        if (customers != null && customers.size() == 1) {
            return customers.get(0);
        }
        return null;
    }

    public CustomerPersonalPO loadCustomerByLoginName(String loginName, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     *");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal c");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND c.loginName = ?");

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, loginName, parameters);

        List<CustomerPersonalPO> customers = MySQLDao.search(sbSQL.toString(), parameters, CustomerPersonalPO.class, null, conn);
        if (customers != null && customers.size() == 1) {
            return customers.get(0);
        }
        return null;
    }

    public CustomerVO loadCustomerVO(String customerId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("loadCustomerVO", this);
        dbSQL.addParameter4All("customerId", customerId);
        dbSQL.initSQL();

        List<CustomerVO> list = MySQLDao.search(dbSQL, CustomerVO.class, conn);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public boolean isCustomerCatalogConfirmed(CustomerPersonalPO customerPersonalPO) {
        if (customerPersonalPO != null && customerPersonalPO.getCustomerCatalogId() != null && customerPersonalPO.getCustomerCatalogId().equals(CustomerCatalog.Confirmed)) {
            return true;
        }

        if (customerPersonalPO != null && customerPersonalPO.getCustomerCatalogId() == null) {
            return true;
        }

        return false;
    }


    public CustomerPersonalPO updateModern(CustomerPersonalPO customer, String userId, Connection conn) throws Exception {

        if (customer == null || StringUtils.isEmpty(customer.getId())) {
            MyException.newInstance("无法获得客户信息").throwException();
        }

        CustomerPersonalPO tempCustomer = loadByCustomerPersonalId(customer.getId(), conn);

        if (tempCustomer == null) {
            MyException.newInstance("无法获得客户信息", "customerId=" + customer.getId()).throwException();
        }

        boolean isCustomerConfirmed = isCustomerCatalogConfirmed(tempCustomer);

        if (isCustomerConfirmed && !tempCustomer.getMobile().equals(customer.getMobile())) {
            MyException.newInstance("客户信息已确认，【手机号】暂时无法修改", "customerId=" + customer.getId()).throwException();
        }

        if (isCustomerConfirmed && !tempCustomer.getName().equals(customer.getName())) {
            MyException.newInstance("客户信息已确认，【姓名】暂时无法修改", "customerId=" + customer.getId()).throwException();
        }

        if (!StringUtils.isEmpty(customer.getName())) {
            tempCustomer.setName(customer.getName());
        }

        if (!StringUtils.isEmpty(customer.getMobile())) {
            tempCustomer.setMobile(customer.getMobile());
        }


        if (!StringUtils.isEmpty(customer.getSex())) {
            tempCustomer.setSex(customer.getSex());
        }

        if (!StringUtils.isEmpty(customer.getBirthday())) {
            tempCustomer.setBirthday(customer.getBirthday());
        }

        if (!StringUtils.isEmpty(customer.getRemark())) {
            tempCustomer.setRemark(customer.getRemark());
        }

        MySQLDao.insertOrUpdate(tempCustomer, userId, conn);

        return tempCustomer;
    }


    public int removeByCustomerId(String customerId, String userId, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonalPO = loadByCustomerPersonalId(customerId, conn);

        if (customerPersonalPO != null) {
            MySQLDao.remove(customerPersonalPO, userId, conn);
        }

        return 1;
    }

    /**
     * 网站：通过 Customer ID 获取 Customer
     * <p/>
     * 修改人：张舜清
     * 时间：7/10/2015
     * 内容：增加异常处理
     * <p/>
     * 修改：李昕骏
     * 时间：2015年11月21日 21:19:18
     * 内容
     * 重写查询方法
     *
     * @param customerPersonalId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerPersonalPO loadByCustomerPersonalId(String customerPersonalId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(customerPersonalId)) {
            MyException.newInstance("客户编号为空", "customerPersonalId="+customerPersonalId).throwException();
        }


        DatabaseSQL dbSQL = DatabaseSQL.newInstance("5EEA1804");
        dbSQL.addParameter4All("customerPersonalId", customerPersonalId);
        dbSQL.initSQL();


        List<CustomerPersonalPO> customers = MySQLDao.search(dbSQL, CustomerPersonalPO.class, conn);
        if (customers != null && customers.size() == 1) {
            return customers.get(0);
        }
        return null;
    }

    public CustomerPersonalPO loadByCustomerPersonalNumber(String personalNumber, Connection conn) throws Exception {

        if (StringUtils.isEmpty(personalNumber)) {
            MyException.newInstance("客户编号为空", "personalNumber="+personalNumber).throwException();
        }


        DatabaseSQL dbSQL = DatabaseSQL.newInstance("5EEA1804");
        dbSQL.addParameter4All("personalNumber", personalNumber);
        dbSQL.initSQL();


        List<CustomerPersonalPO> customers = MySQLDao.search(dbSQL, CustomerPersonalPO.class, conn);
        if (customers != null && customers.size() == 1) {
            return customers.get(0);
        }
        return null;
    }
}
