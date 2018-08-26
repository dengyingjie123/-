package com.youngbook.service.customer;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerFeedbackPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("customerFeedbackService")
public class CustomerFeedbackService extends BaseService {


    public Pager listPagerCustomerFeedback(String customerId, String feedbackManId, String startTime, String stopTime,
                                           int currentPage, int showRowCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listPagerCustomerFeedback", this);
        dbSQL.addParameter4All("customerId", customerId);
        dbSQL.addParameter4All("feedbackManId", feedbackManId);
        dbSQL.addParameter4All("startTime", startTime);
        dbSQL.addParameter4All("stopTime", stopTime);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), new CustomerFeedbackPO(), null, currentPage, showRowCount, queryType, conn);

        return pager;
    }


    public List<CustomerFeedbackPO> listCustomerFeedback(String customerId, String feedbackManId, String startTime, String stopTime, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listPagerCustomerFeedback", this);
        dbSQL.addParameter4All("customerId", customerId);
        dbSQL.addParameter4All("feedbackManId", feedbackManId);
        dbSQL.addParameter4All("startTime", startTime);
        dbSQL.addParameter4All("stopTime", stopTime);
        dbSQL.initSQL();


        List<CustomerFeedbackPO> list = MySQLDao.search(dbSQL, CustomerFeedbackPO.class, conn);

        return list;
    }

    /**
     * 删除一条或多条回访数据
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月28日
     *
     * @param ids
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer deleteFeedbacks(String[] ids, Connection conn) throws Exception {

        Integer count = 0;

        for(String id : ids) {

            CustomerFeedbackPO feedbackPO = new CustomerFeedbackPO();
            feedbackPO.setId(id);
            feedbackPO.setState(Config.STATE_CURRENT);
            feedbackPO = MySQLDao.load(feedbackPO, CustomerFeedbackPO.class, conn);

            if(feedbackPO != null) {
                feedbackPO.setState(Config.STATE_DELETE);
                count += MySQLDao.insertOrUpdate(feedbackPO, conn);
            }

        }

        return count;
    }

    /**
     * HOPEWEALTH-1303<br/>
     * 获取所有回访类型，以List形式返回<br/>
     * @return
     * @throws Exception
     */
    public List<KVPO> getFeedbackType() throws Exception {
        //获得所有groupName为CRM_CustomerFeedbackType的KV
        String sql = "select * from system_kv k where k.GroupName = 'CRM_CustomerFeedbackType'";
        List<KVPO> list = MySQLDao.query(sql, KVPO.class, null);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 获取回访人指定客户的回访记录
     *
     * 作者：曾威恺
     * 内容：创建代码
     *
     * 修改：邓超
     * 内容：修改方法名称，新增传入参数 customerId
     * 时间：2016年3月28日
     *
     * @param customerId
     * @param feedbackManId
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager getFeedbacks(String customerId, String feedbackManId, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {
        //获得所有符合条件的feedback
        String sql = "select * from crm_customerfeedback cf where cf.state = 0 and cf.FeedbackManId = '" + feedbackManId + "' and cf.customerId = '" + customerId + "'";
        Pager pager = MySQLDao.search(sql, new ArrayList<KVObject>(), new CustomerFeedbackPO(), new ArrayList<KVObject>(), currentPage, showRowCount, null, conn);
        return pager;
    }


    public CustomerFeedbackPO loadCustomerFeedbackPO(String id) throws Exception{
        CustomerFeedbackPO po = new CustomerFeedbackPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, CustomerFeedbackPO.class);

        return po;
    }
    public CustomerFeedbackPO loadCustomerFeedbackPOSales(String id) throws Exception{
        CustomerFeedbackPO po = new CustomerFeedbackPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, CustomerFeedbackPO.class);

        return po;
    }

    public int delete(CustomerFeedbackPO customerFeedback, UserPO user, Connection conn) throws Exception {
        int count = 0;

        customerFeedback.setState(Config.STATE_CURRENT);
        customerFeedback = MySQLDao.load(customerFeedback, CustomerFeedbackPO.class);
        customerFeedback.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(customerFeedback, conn);
        if (count == 1) {
            customerFeedback.setSid(MySQLDao.getMaxSid("CRM_CustomerFeedback", conn));
            customerFeedback.setState(Config.STATE_DELETE);
            customerFeedback.setOperateTime(TimeUtils.getNow());
            customerFeedback.setOperatorId(user.getId());
            count = MySQLDao.insert(customerFeedback, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    public int insertOrUpdate(CustomerFeedbackPO customerFeedback, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (customerFeedback.getId().equals("")) {
            customerFeedback.setSid(MySQLDao.getMaxSid("CRM_CustomerFeedback", conn));
            customerFeedback.setId(IdUtils.getUUID32());
            customerFeedback.setState(Config.STATE_CURRENT);
            customerFeedback.setOperatorId(user.getId());
            customerFeedback.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerFeedback, conn);
        }
        // 更新
        else {
            CustomerFeedbackPO temp = new CustomerFeedbackPO();
            temp.setSid(customerFeedback.getSid());
            temp = MySQLDao.load(temp, CustomerFeedbackPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerFeedback.setSid(MySQLDao.getMaxSid("CRM_CustomerFeedback", conn));
                customerFeedback.setState(Config.STATE_CURRENT);
                customerFeedback.setOperatorId(user.getId());
                customerFeedback.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerFeedback, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }
}
