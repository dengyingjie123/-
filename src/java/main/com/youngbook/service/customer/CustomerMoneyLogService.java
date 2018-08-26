package com.youngbook.service.customer;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerMoneyLogPO;
import com.youngbook.entity.po.customer.CustomerMoneyLogStatus;
import com.youngbook.entity.po.customer.CustomerMoneyLogType;
import com.youngbook.entity.vo.customer.CustomerMoneyLogVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.system.LogService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("customerMoneyLogService")
public class CustomerMoneyLogService extends BaseService {

    /**
     * 创建人：张舜清
     * 时间：2015年9月15日18:13:27
     * 内容：根据兑付的客户id查询出对应数据列表
     *
     * @param customerId
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager customerMoneyLogQuery(String customerId, HttpServletRequest request, Connection conn) throws Exception {
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("     cc.*, cp.`Name` CustomerName ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_customermoneylog cc, ");
        sqlDB.append("     crm_customerpersonal cp ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1 ");
        sqlDB.append(" AND cc.state = 0 ");
        sqlDB.append(" AND cp.state = 0 ");
        sqlDB.append(" AND cc.CustomerId = cp.id ");
        sqlDB.append(" AND cp.id='" + customerId + "' ");
        sqlDB.append(" ORDER BY ");
        sqlDB.append("     sid DESC ");

        CustomerMoneyLogVO customerMoneyLogVO = new CustomerMoneyLogVO();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sqlDB.toString(), customerMoneyLogVO, null, request, queryType, conn);
        return pager;
    }

    public List<CustomerMoneyLogPO> getCustomerMoneyLogsByCustomerId (String customerId, Connection conn) throws Exception {

        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT ");
        sqlDB.append("     cc.*, cp.`Name` CustomerName ");
        sqlDB.append(" FROM ");
        sqlDB.append("     crm_customermoneylog cc, ");
        sqlDB.append("     crm_customerpersonal cp ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     1 = 1 ");
        sqlDB.append(" AND cc.state = 0 ");
        sqlDB.append(" AND cp.state = 0 ");
        sqlDB.append(" AND cc.CustomerId = cp.id ");
        sqlDB.append(" AND cp.id=? ");
        sqlDB.append(" ORDER BY ");
        sqlDB.append("     sid DESC ");

        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sqlDB.toString()).addParameter(1, customerId);

        List<CustomerMoneyLogPO> list = MySQLDao.search(dbSQL, CustomerMoneyLogPO.class, conn);

        return list;
    }


    

    // 编写Service的delete
    public int delete(CustomerMoneyLogPO customerMoneyLog, UserPO user, Connection conn) throws Exception {

        int count = 0;
        customerMoneyLog.setState(Config.STATE_CURRENT);
        customerMoneyLog = MySQLDao.load(customerMoneyLog, CustomerMoneyLogPO.class);
        customerMoneyLog.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(customerMoneyLog, conn);
        if (count == 1) {
            customerMoneyLog.setSid(MySQLDao.getMaxSid("CRM_CustomerMoneyLog", conn));
            customerMoneyLog.setState(Config.STATE_DELETE);
            customerMoneyLog.setOperateTime(TimeUtils.getNow());
            customerMoneyLog.setOperatorId(user.getId());
            count = MySQLDao.insert(customerMoneyLog, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    public CustomerMoneyLogPO loadCustomerMoneyLogPO(String id) throws Exception{
        CustomerMoneyLogPO po = new CustomerMoneyLogPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, CustomerMoneyLogPO.class);

        return po;
    }

    public void log4Refund(String customerId, String customerRefundId, double money, String status, Connection conn) throws Exception {
        CustomerMoneyLogPO customerMoneyLog = new CustomerMoneyLogPO();
        customerMoneyLog.setSid(MySQLDao.getMaxSid("crm_customermoneylog", conn));
        customerMoneyLog.setId(IdUtils.getUUID32());
        customerMoneyLog.setState(Config.STATE_CURRENT);
        customerMoneyLog.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        customerMoneyLog.setOperateTime(TimeUtils.getNow());
        customerMoneyLog.setType(CustomerMoneyLogType.Refund);
        customerMoneyLog.setContent("退款" + money + "元");
        customerMoneyLog.setStatus(status);
        customerMoneyLog.setBizId(customerRefundId);
        customerMoneyLog.setCustomerId(customerId);

        LogService.info("新增家客户资金日志", this.getClass());
        LogService.info(customerMoneyLog);
        MySQLDao.insert(customerMoneyLog, conn);
    }

    /**
     * 获取是树型下拉列表
     * @return
     */
    public JSONArray getTypeTree(){
        CustomerMoneyLogType TType = new CustomerMoneyLogType();
        JSONArray array = TType.toJsonArray();
        return array;
    }

    /**
     * 获取是树型下拉列表
     * @return
     */
    public JSONArray getStatusTree(){
        CustomerMoneyLogStatus TStatus = new CustomerMoneyLogStatus();
        JSONArray array = TStatus.toJsonArray();
        return array;
    }


    /**
     *
     * 作者：quan.zeng
     * 内容：创建代码
     * 时间：2015-12-8
     * 描述：通过登录的客户 ID 查询关联的资金日志
     * @param customerId:客户id
     * @return Pager
     * @throws Exception
     */
    public Pager getCustomerMoneyLogs(String customerId) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, CustomerMoneyLogVO.class);
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " cc.operateTime desc"));
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, customerId, parameters);
        String sql = "select cc.type, cc.content, cc.status, cc.customerId, date_format(operateTime,'%Y-%m-%d %H:%i:%s') as operateTime from crm_customermoneylog cc where cc.state = 0 and cc.customerId =?";
        Pager pager = Pager.search(sql,parameters, new CustomerMoneyLogVO(), conditions, request, queryType);
        if(pager!=null){
            return pager;
        }
        return null;
    }
}