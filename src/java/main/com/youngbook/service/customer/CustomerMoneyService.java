package com.youngbook.service.customer;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerMoneyDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerMoneyPO;
import com.youngbook.entity.vo.customer.CustomerMoneyVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("customerMoneyService")
public class CustomerMoneyService extends BaseService{

    @Autowired
    ICustomerMoneyDao customerMoneyDao;

    /**
     * 插入资金为 0 的资金记录
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年5月29日
     *
     * @param customerId
     * @param conn
     * @return
     */
    public Integer insertEmptyCustomerMoney(String customerId, Connection conn) throws Exception {

        CustomerMoneyPO moneyPO = new CustomerMoneyPO();
        moneyPO.setCustomerId(customerId);
        moneyPO.setAvailableMoney(0);
        moneyPO.setExpectedMoney(0);
        moneyPO.setFrozenMoney(0);
        moneyPO.setInvestedMoney(0);
        moneyPO.setTotalProfitMoney(0);

        return customerMoneyDao.insertOrUpdate4W(moneyPO, conn);

    }

    public int insertOrUpdate4W(CustomerMoneyPO customerMoney, Connection conn) throws Exception {
        return customerMoneyDao.insertOrUpdate4W(customerMoney, conn);
    }

    /**
     * 查询客户资金 可用余额是都满足
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerMoneyPO getCustomerMoney(String customerId, Connection conn) throws Exception {
        CustomerMoneyPO customerMoney = new CustomerMoneyPO();
        customerMoney.setState(Config.STATE_CURRENT);
        customerMoney.setCustomerId(customerId);
        customerMoney = MySQLDao.load(customerMoney, CustomerMoneyPO.class, conn);
        return customerMoney;
    }

    /**
     * 查询列表数据
     * @param customerMoneyVO 对应列表对象
     * @param conditions  查询条件
     * @return
     */
    public Pager list(CustomerMoneyVO customerMoneyVO, List<KVObject> conditions) throws Exception{
        //获取HTTP请求对象
        HttpServletRequest request = ServletActionContext.getRequest();

        StringBuffer sbSQL = new StringBuffer();

        //组装SQL语句
        sbSQL.append(" SELECT");
        sbSQL.append(" cm.*,");
        sbSQL.append(" cp.`Name` customerName ");
        sbSQL.append(" FROM ");
        sbSQL.append(" crm_customermoney cm, ");
        sbSQL.append(" crm_customerpersonal cp ");
        sbSQL.append(" WHERE ");
        sbSQL.append(" 1 = 1 ");
        sbSQL.append(" AND cm.CustomerId = cp.id ");
        sbSQL.append(" AND cp.state = 0  ");
        sbSQL.append(" AND cm.State = 0 ");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //返回分页对象
        Pager pager = Pager.query(sbSQL.toString(), customerMoneyVO, conditions, request, queryType);
        return pager;
    }

    // 新增或修改客户资金记录
    public int insertOrUpdate(CustomerMoneyPO customerMoney, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (customerMoney.getId().equals("")) {
            customerMoney.setSid(MySQLDao.getMaxSid("CRM_CustomerMoney", conn));
            customerMoney.setId(IdUtils.getUUID32());
            customerMoney.setState(Config.STATE_CURRENT);
            customerMoney.setOperatorId(user.getId());
            customerMoney.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerMoney, conn);
        }
        // 更新
        else {
            CustomerMoneyPO temp = new CustomerMoneyPO();
            temp.setSid(customerMoney.getSid());
            temp = MySQLDao.load(temp, CustomerMoneyPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerMoney.setSid(MySQLDao.getMaxSid("CRM_CustomerMoney", conn));
                customerMoney.setState(Config.STATE_CURRENT);
                customerMoney.setOperatorId(user.getId());
                customerMoney.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerMoney, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }



    public CustomerMoneyPO loadCustomerMoneyPO(String id) throws Exception{
        CustomerMoneyPO po = new CustomerMoneyPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, CustomerMoneyPO.class);
        return po;
    }


    /**
     * 网站：通过客户 ID 查找用户资金
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public List getByCustomerId4W(String customerId, Connection conn) throws Exception{
        StringBuffer sbSQL = new StringBuffer();
        // 组装 SQL 语句
        sbSQL.append(" SELECT ");
        sbSQL.append(" 	CC.Sid, ");
        sbSQL.append(" 	CC.Id, ");
        sbSQL.append(" 	CC.State, ");
        sbSQL.append(" 	CC.operatorId, ");
        sbSQL.append(" 	CC.operateTime, ");
        sbSQL.append(" 	CC.CustomerId, ");
        sbSQL.append(" 	CC.FrozenMoney, ");
        sbSQL.append(" 	CC.ExpectedMoney, ");
        sbSQL.append(" 	CC.AvailableMoney, ");
        sbSQL.append(" 	CC.InvestedMoney, ");
        sbSQL.append(" 	CC.TotalProfitMoney, ");
        sbSQL.append(" 	cus.`Name` customerName ");
        sbSQL.append(" FROM ");
        sbSQL.append(" 	crm_customermoney CC, ");
        sbSQL.append(" 	crm_customerpersonal cus ");
        sbSQL.append(" WHERE cc.CustomerId = cus.id ");
        sbSQL.append(" AND CC.state = 0 ");
        sbSQL.append(" AND cus.state = 0 ");
        sbSQL.append(" AND cc.customerId = '" + customerId + "' ");
        // 返回 List
        List<KVObject> conditions = new ArrayList<KVObject>();
        List<CustomerMoneyPO> list = MySQLDao.query(sbSQL.toString(), CustomerMoneyPO.class, conditions, conn);
        return list;
    }

}
