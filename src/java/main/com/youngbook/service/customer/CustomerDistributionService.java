package com.youngbook.service.customer;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerDistributionDao;
import com.youngbook.dao.customer.ICustomerInstitutionDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.dao.sale.ISalemanGroupDao;
import com.youngbook.dao.system.IDepartmentDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerDistributionPO;
import com.youngbook.entity.po.customer.CustomerInstitutionPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.entity.vo.customer.CustomerInstitutionAuditVO;
import com.youngbook.entity.vo.customer.CustomerPersonalAuditVO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: XueqingChen
 * Date: 14-10-24
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
@Component("customerDistributionService")
public class CustomerDistributionService extends BaseService {

    @Autowired
    ISalemanGroupDao salemanGroupDao;

    @Autowired
    IDepartmentDao departmentDao;

    @Autowired
    ICustomerDistributionDao customerDistributionDao;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    ICustomerInstitutionDao customerInstitutionDao;

    @Autowired
    IUserDao userDao;


    @Autowired
    ILogDao logDao;


    public int save2DefaultUser(String customerId, String operatorId, Connection conn) throws Exception {
        CustomerDistributionPO customerDistribution = new CustomerDistributionPO();
        customerDistribution.setDepartmentId("E6254378-B44E-4565-808E-75A6704102EE");
        customerDistribution.setSaleGroupId("14B73506EFB7413BB4BC760F6ADA6E01");
        customerDistribution.setSaleManId("30013EAB090A4EB4984C52749C168114");
        customerDistribution.setRemark(0);
        customerDistribution.setCustomerId(customerId);

        LogPO logPO = new LogPO();
        logPO.setPeopleMessage("客户分配给默认销售，客户【"+customerId+"】");
        logPO.setMachineMessage("{'customerId':'" + customerId + "','userId':'" + customerDistribution.getSaleManId()+"'}");
        logPO.setName("客户日志");
        logDao.save(logPO, conn);

        return MySQLDao.insertOrUpdate(customerDistribution, operatorId, conn);
    }


    /**
     * 只分配给一个销售人员
     * @param customerDistribution
     * @param operatorId
     * @param conn
     * @return
     * @throws Exception
     */
    public int distributeToOneSalesman(CustomerDistributionPO customerDistribution, String operatorId, Connection conn) throws Exception {

        String customerId = customerDistribution.getCustomerId();

        StringUtils.checkIsEmpty(customerId, "客户编号为空");

        /**
         * 如果已有分配，则删除原来的分配记录，保证只分配给一个销售
         */
        List<CustomerDistributionPO> customerDistributionPOs = getCustomerDistributionsByCustomerId(customerId, conn);

        for (int i = 0; customerDistributionPOs != null && i < customerDistributionPOs.size(); i++) {
            CustomerDistributionPO customerDistributionPO = customerDistributionPOs.get(i);

            remove(customerDistributionPO.getCustomerId(), customerDistributionPO.getSaleManId(), conn);

        }

        return customerDistributionDao.distributeToSalesman(customerDistribution, operatorId, conn);
    }


    /**
     * 修改人：姚章鹏
     * 内容：修改sql语句，关联saleMan表，
     */
    //获取个人客户审核的列表
    public Pager listPersonalCustomer(String customerName, int currentPage, int showRowCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("C4B71804");
        dbSQL.addParameter4All("customerName", customerName);
        dbSQL.initSQL();

        Pager pager = MySQLDao.search(dbSQL, new CustomerPersonalAuditVO(), null, currentPage, showRowCount, null, conn);

        return pager;
    }
    /**
    * 修改人：:姚章鹏
    * 内容：修改sql语句 将I.id改成CD.id  使其关联客户分配表
    * 时间：2015.7.7
     * */
    //获取机构客户审核的列表
    public Pager listInstitutionCustomer(CustomerInstitutionAuditVO institutionAuditVO, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        List<KVObject> conditions = getCondition(request);
        String sql = "SELECT I.sid,CD.id,I.Name,I.LegalPerson,I.RegisteredCapital,I.Mobile,I.Phone,I.Address,I.Email,\n" +
                "U.name salesmanName,U.Id saleManId,CD.Status,\n" +
                "  cd.STATUS as AuditStatus\n" +
                "FROM crm_customerinstitution I\n" +
                "LEFT JOIN crm_customerdistribution CD on CD.CustomerId=I.id\n" +
                "LEFT JOIN system_user U ON U.Id=CD.SaleManId\n" +
                "WHERE CD.remark =1 and I.state = 0 and CD.state=0 and U.state=0";
        Pager pager = Pager.query(sql, institutionAuditVO, conditions, request, queryType);

        return pager;
    }

    public List<CustomerDistributionPO> getCustomerDistributionsByCustomerId(String customerId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from crm_customerdistribution where state=0 and customerId=?").addParameter(1, customerId);

        List<CustomerDistributionPO> list = MySQLDao.search(dbSQL, CustomerDistributionPO.class, conn);

        return list;
    }



    //取消客户所有分配
    public int cancelDistribution(CustomerDistributionPO customerDistribution, Connection conn, UserPO user) throws Exception {
        int count = 0;
        String sql = "select sid,id,state,operatorId,operateTime,customerId,saleManId,status,remark from crm_CustomerDistribution\n" +
                "where state=0 and customerId='" + Database.encodeSQL(customerDistribution.getCustomerId()) +
                "' and remark='" + customerDistribution.getRemark() + "'";
        List<CustomerDistributionPO> list = MySQLDao.query(sql, CustomerDistributionPO.class, null);
        if (list != null && list.size() > 0) {
            for (CustomerDistributionPO cdPO : list) {
                CustomerDistributionPO temp = cdPO;
                temp.setState(Config.STATE_UPDATE);
                count = MySQLDao.update(temp, conn);
                if (count == 1) {
                    cdPO.setSid(MySQLDao.getMaxSid("crm_customerdistribution", conn));
                    cdPO.setState(Config.STATE_DELETE);
                    cdPO.setOperatorId(user.getId());
                    cdPO.setOperateTime(TimeUtils.getNow());
                    count = MySQLDao.insert(cdPO, conn);
                }
            }
        }
        return count;
    }

    /**
     * 更新客户分配信息
     *
     * 修改人：leevits
     * 时间：2015年7月30日 20:52:12
     * 内容：更改整个实现
     *
     * @param customerDistribution
     * @param conn
     * @param user
     * @return
     * @throws Exception
     */
    public int auditDistribution(CustomerDistributionPO customerDistribution, Connection conn, UserPO user) throws Exception {

        if (null == customerDistribution  ) {
            throw new Exception("请假休假数据提交失败");
        }

        //当前用户操作类是否为空
        if (null == user) {
            throw new Exception("当前用户失效");
        }

        //当前数据链接是否空
        if (null == conn ) {
            throw new Exception("链接错误");
        }
        int count = 0;

        // 保存将要设置的状态
        int currentStatus = customerDistribution.getStatus();

        CustomerDistributionPO temp = new CustomerDistributionPO();
        temp.setId(customerDistribution.getId());  //
        temp.setState(Config.STATE_CURRENT);
        temp = MySQLDao.load(temp, CustomerDistributionPO.class, conn);

        if (temp != null) {
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);

            if (count == 1) {
                temp.setSid(MySQLDao.getMaxSid("crm_customerdistribution", conn));
                temp.setState(Config.STATE_CURRENT);
                temp.setOperatorId(user.getId());
                temp.setOperateTime(TimeUtils.getNow());
                temp.setStatus(currentStatus);
                count = MySQLDao.insert(temp, conn);
            }

            return count;
        }
        else {
            throw new Exception("更新客户分配信息失败");
        }

    }

    /**
     * 获取管理页面传入的查询条件
     *
     * @param request
     * @return List<KVObject>
     */
    private List<KVObject> getCondition(HttpServletRequest request) {

        String customerName = request.getParameter("customerName");
        String customerMobile = request.getParameter("customerMobile");
        String customerStatus = request.getParameter("customerStatus");
        List<KVObject> conditions = new ArrayList<KVObject>();
        if (customerName != null && !customerName.equals("")) {
            KVObject kvProject = new KVObject("Name", " like '%" + Database.encodeSQL(customerName) + "%'");
            conditions.add(kvProject);
        }
        if (customerMobile != null && !customerMobile.equals("")) {
            KVObject kvProject = new KVObject("Mobile", " like '%" + Database.encodeSQL(customerMobile) + "%'");
            conditions.add(kvProject);
        }
        if (customerStatus != null && !customerStatus.equals("")) {
            KVObject kvProject = new KVObject("auditStatus", " like '%" + Database.encodeSQL(customerStatus) + "%'");
            conditions.add(kvProject);
        }

        return conditions;
    }


    /***
     * 删除分配关系
     * @param
     * @param conn
     * @return
     * @throws Exception
     */

    public int remove(String customerId, String userId, Connection conn) throws  Exception {

        if (StringUtils.isEmptyAny(customerId, userId)) {
            MyException.newInstance("传入参数错误，无法删除分配关系").throwException();
        }

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        CustomerInstitutionPO customerInstitutionPO = customerInstitutionDao.loadByCustomerInstitutionId(customerId, conn);

        if (customerPersonalPO == null && customerInstitutionPO == null) {
            MyException.newInstance("无法找到对应客户，无法删除分配关系","customerId="+customerId).throwException();
        }

        String customerName = "";
        if (customerPersonalPO != null) {
            customerName = customerPersonalPO.getName();
        }

        if (customerInstitutionPO != null) {
            customerName = customerInstitutionPO.getName();
        }

        UserPO userPO = userDao.loadUserByUserId(userId, conn);

        if (userPO == null) {
            MyException.newInstance("无法找到对应用户，无法删除分配关系").throwException();
        }

        customerDistributionDao.remove(customerId, userId, conn);

        LogPO logPO = new LogPO();
        logDao.save(logPO, conn);
        logPO.setPeopleMessage("删除客户分配关系，客户【"+customerName+"】销售【"+userPO.getName()+"】");
        logPO.setMachineMessage("{'customerId':'"+customerId+"','userId':'"+userPO.getId()+"'}");
        logPO.setName("客户日志");

        return 1;
    }


    public List load(CustomerDistributionPO customerDistribution,Connection conn) throws Exception{
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("     DepartmentId, ");
        sqlDB.append("     SaleGroupId, ");
        sqlDB.append("     id, ");
        sqlDB.append("     SaleManId ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_CustomerDistribution ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1 ");
        sqlDB.append(" AND state = 0 ");
        sqlDB.append(" AND saleManId = '" + customerDistribution.getSaleManId() + "' ");
        sqlDB.append(" AND customerId = '" + customerDistribution.getCustomerId() + "' ");
        List list = MySQLDao.query(sqlDB.toString(), CustomerDistributionPO.class, null, conn);
        return  list;

    }


    /**
     * 增加注释
     *
     * 传入参数的判断
     * @param userId
     * @param conn
     * @return
     * @throws Exception
     */
    public List<CustomerDistributionPO> listCustomerDistributionPOByUserId(String userId, Connection conn) throws Exception {
        if (userId == null){
            MyException.newInstance("未获得到用户信息").throwException();
        }
        return customerDistributionDao.getListCustomerDistrbutionPOByUserId(userId, conn);
    }


}
