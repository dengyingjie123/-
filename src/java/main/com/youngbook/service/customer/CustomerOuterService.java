package com.youngbook.service.customer;

import com.youngbook.annotation.Security;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerOuterPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.production.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("customerOuterService")
public class CustomerOuterService extends BaseService {

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    /**
     * 设置真实客户和临时客户的关联
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月29日
     *
     * @param realCustomerId
     * @param realCustomerMobile
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer setRealCustomerToTempCustomer(String realCustomerId, String realCustomerMobile, String salemanId, Connection conn) throws Exception {

        // 根据真实客户的手机号码查询出所有对外销售的临时客户
        String sql = "SELECT * FROM crm_customer_outer WHERE state = 0 and mobile = '" + realCustomerMobile + "' and salemanOuterId = '" + salemanId + "' ";

        List<CustomerOuterPO> customers = MySQLDao.query(sql, CustomerOuterPO.class, null);

        // 循环未注册的客户
        Integer count = 0;
        for (CustomerOuterPO customer : customers) {

            String tempRealCustomerId = customer.getCustomerPersonalId();
            // 如果已经在真实客户表里存在，直接跳过
            if(!StringUtils.isEmpty(tempRealCustomerId) && tempRealCustomerId.equals(realCustomerId)) {
                count ++;
                continue ;
            }

            customer.setCustomerPersonalId(realCustomerId);
            count += MySQLDao.insertOrUpdate(customer, conn);
        }
        return count;

    }

    /**
     * 修改手机号码
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月8日
     *
     * @param mobile
     * @param salemanId
     * @param conn
     * @return
     * @throws Exception
     */
    public int updateMobile(String mobile, String salemanId, Connection conn) throws Exception {

        int count = 0;

        CustomerOuterPO po = this.loadCustomerById(salemanId, conn);
        if (po == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_LOGIN_NAME_NOT_EXISTENT, "账户不存在").throwException();
        }
        po.setMobile(mobile);
        count = MySQLDao.insertOrUpdate(po, conn);
        return count;

    }

    /**
     * 通过临时客户 ID 查询信息
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月8日
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerOuterPO loadCustomerById(String id, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     *");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customer_outer c");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND c.id = ?");

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, id, parameters);

        List<CustomerOuterPO> customers = MySQLDao.search(sbSQL.toString(), parameters, CustomerOuterPO.class, null, conn);
        if (customers != null && customers.size() == 1) {
            return customers.get(0);
        }
        return null;
    }

    /**
     * 获取客户列表
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月23日
     *
     * @param salemanOuter
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager listCustomers(UserPO salemanOuter, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {

        // 获取即将到期产品的时间临界值
        Integer preExpireValue = Config.getSystemVariableAsInt("web.production.preExpire.value");

        // 组装 SQL
        StringBuffer sb = new StringBuffer();
        sb.append(" select ");
        sb.append(" DISTINCT ");
        sb.append(" p.id,     ");
        sb.append(" p.name,     ");
        sb.append(" p.mobile,     ");
        sb.append(" co.id as temporaryCustomerId, ");
        sb.append(" ( select count(o.sid) from crm_order o where o.state = 0 and o.customerId = p.id and o.status = " + OrderStatus.Preappointment + " and o.referralCode = '" + salemanOuter.getReferralCode() + "' ) as preAppointmentCount, ");
        sb.append(" ( select count(o.sid) from crm_order o where o.state = 0 and o.customerId = p.id and o.status = " + OrderStatus.Appointment + " and o.referralCode = '" + salemanOuter.getReferralCode() + "') as appointmentCount,      ");
        sb.append(" ( select count(o.sid) from crm_order o where o.state = 0 and o.customerId = p.id and o.status = " + OrderStatus.Saled + " and o.referralCode = '" + salemanOuter.getReferralCode() + "') as saledCount, ");
        sb.append(" ( select sum(o.money) from crm_order o where o.state = 0 and o.customerId = p.id and o.status = " + OrderStatus.Saled + " and o.referralCode = '" + salemanOuter.getReferralCode() + "') as totalInvestedMoney, ");
        sb.append(" ( select count(o.sid) from crm_order o, crm_production n where o.state = 0 and n.state = 0 and o.customerId = p.id and o.productionId = n.id and o.status = " + OrderStatus.Saled + " and o.referralCode = '" + salemanOuter.getReferralCode() + "' and timeStampDiff(month, n.expiringDate, '" + TimeUtils.getNow() + "') = " + preExpireValue + ") as preExpireCount, ");
        sb.append(" ( select sum(o.money) from crm_order o, crm_production n where o.state = 0 and n.state = 0 and o.customerId = p.id and o.productionId = n.id and o.status = " + OrderStatus.Saled + " and o.referralCode = '" + salemanOuter.getReferralCode() + "' and timeStampDiff(month, n.expiringDate, '" + TimeUtils.getNow() + "') = " + preExpireValue + ") as totalPreExpireMoney ");
        sb.append(" from crm_customerpersonal p  ");
        sb.append(" left join crm_customer_outer co on co.state = 0 and co.customerPersonalId = p.id and co.salemanOuterId = '" + salemanOuter.getId() + "', ");
        sb.append(" crm_order o ");
        sb.append(" where p.state = 0 and o.state = 0 and o.customerId = p.id and o.salemanId = '" + salemanOuter.getId() + "' ");

        Pager pager = MySQLDao.query(sb.toString(), CustomerPersonalVO.class, currentPage, showRowCount, conn);

        // 处理推荐人
        for(Object object : pager.getData()) {
            CustomerPersonalVO personalVO = (CustomerPersonalVO)object;
            personalVO.setReferralCode(salemanOuter.getReferralCode());
        }

        return pager;

    }

    /**
     * 获取客户的详细信息（用于销客 - 客户详情里的统计）
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月25日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public CustomerPersonalVO loadCustomerDetail(String customerId, UserPO salemanOuter, Connection conn) throws Exception {

        // 获取即将到期产品的时间临界值
        Integer preExpireValue = Config.getSystemVariableAsInt("web.production.preExpire.value");

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT p.id, p.name, p.mobile, sum(money) as totalInvestedMoney,     ");
        sql.append(" (select sum(money) from crm_order o1 where o1.state = 0 and o1.customerId = p.id and o1.status = " + OrderStatus.Saled + " and timeStampDiff(month, c.expiringDate, '" + TimeUtils.getNow() + "') = " + preExpireValue + ") as totalPreExpireMoney     ");
        sql.append(" FROM    crm_customerpersonal p, crm_order o left join crm_production c on c.state = 0 and o.productionId = c.id  ");
        sql.append(" WHERE    p.state = 0  ");
        sql.append(" and o.state = 0  ");
        sql.append(" and o.customerId = p.id  ");
        sql.append(" and o.productionId = c.id  ");
        sql.append(" and o.referralCode = '" + salemanOuter.getReferralCode() + "'  ");
        sql.append(" and p.id = '" + customerId + "'    ");

        List<CustomerPersonalVO> list = MySQLDao.query(sql.toString(), CustomerPersonalVO.class, new ArrayList<KVObject>(), conn);

        if(list.size() != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }

        return list.get(0);

    }


    /**
     * 获取客户的订单统计
     * 注意：如果传了客户 ID，返回一个客户的统计，如果没有传，返回一个对外销售人员所有客户的统计
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月29日
     *
     * @param customerId
     * @param salemanOuter
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerPersonalVO getCustomerOrderCount(String customerId, UserPO salemanOuter, Connection conn) throws Exception {

        // 如果传了客户 ID，只某个客户订单统计，如果没有，查询销售的所有客户订单统计
        String sql = "";
        if(StringUtils.isEmpty(customerId)) {
            sql = this.getSalemanOrderCountSQL(salemanOuter);
        }
        else {
            sql = this.getCustomerOrderCountSQL(customerId, salemanOuter);
        }

        List<CustomerPersonalVO> list = MySQLDao.query(sql.toString(), CustomerPersonalVO.class, new ArrayList<KVObject>(), conn);
        if(list.size() != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }
        return list.get(0);

    }

    /**
     * 获取对外销售的客户订单统计的 SQL
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月13日
     *
     * @param customerId
     * @param salemanOuter
     * @return
     * @throws Exception
     */
    private String getCustomerOrderCountSQL(String customerId, UserPO salemanOuter) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT sum(money) as totalInvestedMoney,     ");
        sql.append(" (select count(money) from crm_order o2 where o2.state = 0 and o2.referralCode = '" + salemanOuter.getReferralCode() + "' and o2.customerId = p.id and  o2.status = " + OrderStatus.Preappointment + ") as preAppointmentCount,     ");
        sql.append(" (select count(money) from crm_order o3 where o3.state = 0 and o3.referralCode = '" + salemanOuter.getReferralCode() + "' and o3.customerId = p.id and  o3.status = " + OrderStatus.Appointment + ") as appointmentCount,     ");
        sql.append(" (select count(money) from crm_order o3 where o3.state = 0 and o3.referralCode = '" + salemanOuter.getReferralCode() + "' and o3.customerId = p.id and  o3.status = " + OrderStatus.Saled + ") as saledCount,   ");
        sql.append(" (select count(money) from crm_order o3 where o3.state = 0 and o3.referralCode = '" + salemanOuter.getReferralCode() + "' and o3.customerId = p.id and  o3.status = " + OrderStatus.Rebate + ") as rebateCount   ");
        sql.append(" FROM    crm_customerpersonal p, crm_order o, crm_production c   ");
        sql.append(" WHERE    p.state = 0  ");
        sql.append(" and o.state = 0  ");
        sql.append(" and c.state = 0  ");
        sql.append(" and o.customerId = p.id  ");
        sql.append(" and o.productionId = c.id  ");
        sql.append(" and p.id = '" + customerId + "'    ");
        sql.append(" and o.referralCode = '" + salemanOuter.getReferralCode() + "'  ");

        return sql.toString();

    }

    /**
     * 获取对外销售的订单统计的 SQL（统计销售本身，不带他的客户）
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月13日
     *
     * @param salemanOuter
     * @return
     * @throws Exception
     */
    private String getSalemanOrderCountSQL(UserPO salemanOuter) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT sum(money) as totalInvestedMoney,     ");
        sql.append(" (select count(money) from crm_order o2 where o2.state = 0 and o2.salemanId = '" + salemanOuter.getId() + "' and o2.status = " + OrderStatus.Preappointment + ") as preAppointmentCount,     ");
        sql.append(" (select count(money) from crm_order o3 where o3.state = 0 and o3.salemanId = '" + salemanOuter.getId() + "' and o3.status = " + OrderStatus.Appointment + ") as appointmentCount,     ");
        sql.append(" (select count(money) from crm_order o3 where o3.state = 0 and o3.salemanId = '" + salemanOuter.getId() + "' and o3.status = " + OrderStatus.Saled + ") as saledCount,   ");
        sql.append(" (select count(money) from crm_order o3 where o3.state = 0 and o3.salemanId = '" + salemanOuter.getId() + "' and o3.status = " + OrderStatus.Rebate + ") as rebateCount   ");
        sql.append(" FROM    crm_customerpersonal p, crm_order o, crm_production c   ");
        sql.append(" WHERE    p.state = 0  ");
        sql.append(" and o.state = 0  ");
        sql.append(" and c.state = 0  ");
        sql.append(" and o.customerId = p.id  ");
        sql.append(" and o.productionId = c.id  ");
        sql.append(" and o.salemanId = '" + salemanOuter.getId() + "'  ");

        return sql.toString();

    }


    /**
     * 删除一个对外销售人员的客户（临时的）
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月23日
     *
     * @param customerId
     * @param salemanId
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer deleteTemporaryCustomer(String customerId, String salemanId, Connection conn) throws Exception {

        Integer count = 0;

        // 查询客户信息
        CustomerOuterPO outerPO = this.loadTemporaryCustomer(customerId, null, salemanId, conn);
        if(outerPO == null) {
            MyException.newInstance(ReturnObjectCode.CUSTOMER_OUTER_NOT_EXISTENT, "您的客户不存在").throwException();
        }

        // 标识删除
        outerPO.setState(Config.STATE_DELETE);
        count = MySQLDao.insertOrUpdate(outerPO, conn);

        return count;

    }

    /**
     * 获取销售人员的一个客户信息（临时的）
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月23日
     *
     * @param customerId
     * @param realId
     * @param salemanId
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public CustomerOuterPO loadTemporaryCustomer(String customerId, String realId, String salemanId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(customerId) && StringUtils.isEmpty(realId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        CustomerOuterPO outerPO = new CustomerOuterPO();
        outerPO.setState(Config.STATE_CURRENT);

        if(!StringUtils.isEmpty(customerId)) {
            outerPO.setId(customerId);
        }

        if(!StringUtils.isEmpty(realId)) {
            outerPO.setCustomerPersonalId(realId);
        }

        outerPO.setSalemanOuterId(salemanId);
        outerPO = MySQLDao.load(outerPO, CustomerOuterPO.class, conn);

        return outerPO;

    }

    /**
     * 列出对外销售人员的客户
     *
     * @param customerMobile
     * @param salemanId
     * @param conn
     * @return
     * @throws Exception
     */
    public List<CustomerOuterPO> listTemporaryCustomers(String customerMobile, String salemanId, Connection conn) throws Exception {

        String sql = "select * from crm_customer_outer o where o.state = 0 and o.mobile = '" + customerMobile + "' and o.salemanOuterId = '" + salemanId + "'";
        List<CustomerOuterPO> customerOuters = MySQLDao.query(sql, CustomerOuterPO.class, new ArrayList<KVObject>(), conn);
        return customerOuters;

    }

    /**
     * 获取某个对外销售人员的客户列表（临时的）
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月23日
     *
     * @param salemanId
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager listTemporaryBySalemanOuter(String salemanId, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {

        // 定义 SQL
        String sql = "select * from crm_customer_outer co where co.state = 0 and co.salemanOuterId = '" + salemanId + "' and (co.customerPersonalId = '' or co.customerPersonalId = null)";
        // 进行查询
        Pager pager = MySQLDao.query(sql, CustomerOuterPO.class, currentPage, showRowCount, conn);
        return pager;

    }

    /**
     * 添加或修改对外销售的客户（临时的）
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月23日
     *
     * @param temporaryCustomerId
     * @param name
     * @param sex
     * @param mobile
     * @param birthday
     * @param note
     * @param property
     * @param idCardNo
     * @param outerPO
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer insertOrUpdateTemporary(String temporaryCustomerId, String name, String sex, String mobile, String birthday, String note, String property, String idCardNo, UserPO outerPO, Connection conn) throws Exception {

        CustomerOuterPO customerOuterPO = new CustomerOuterPO();
        customerOuterPO.setId(temporaryCustomerId);
        customerOuterPO.setName(name);
        customerOuterPO.setSex(sex);
        customerOuterPO.setMobile(mobile);
        customerOuterPO.setBirthday(birthday);
        customerOuterPO.setNote(note);
        customerOuterPO.setProperty(property);
        customerOuterPO.setIdCardNo(idCardNo);
        customerOuterPO.setSalemanOuterId(outerPO.getId());

        // 查询客户和推荐人是否有关联的记录
        Boolean isLink = OrderService.valiteCustomerAndReferralCode(mobile, outerPO.getReferralCode(), conn);
        if(isLink) {
            // 如果有关联，就把临时客户和真实客户关联
            CustomerPersonalPO personalPO = customerPersonalDao.loadCustomerByMobile(mobile, conn);
            customerOuterPO.setCustomerPersonalId(personalPO.getId());
        }

        Integer count = MySQLDao.insertOrUpdate(customerOuterPO, conn);
        return count == null ? 0 : count;

    }

}
