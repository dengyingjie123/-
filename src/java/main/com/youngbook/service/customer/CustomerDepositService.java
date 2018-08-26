package com.youngbook.service.customer;


import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.config.Config4W;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerDepositPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.vo.customer.CustomerDepositVO;
import com.youngbook.entity.wvo.customer.CustomerPersonalWVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;


/**
 * Created by Administrator on 2015/4/1.
 * 客户充值
 */
@Component("customerDepositService")
public class CustomerDepositService extends BaseService {

    private CustomerDepositService() {}

    // 新增和更改
    public int insertOrUpdate(CustomerDepositPO customerDeposit, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (customerDeposit.getId().equals("")) {
            customerDeposit.setSid(MySQLDao.getMaxSid("CRM_CustomerDeposit", conn));
            customerDeposit.setId(IdUtils.getUUID32());
            customerDeposit.setState(Config.STATE_CURRENT);
            customerDeposit.setStatus("0"); //默认为0 未充值
            customerDeposit.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerDeposit, conn);
        }
        // 更新
        else {
            CustomerDepositPO temp = new CustomerDepositPO();
            temp.setSid(customerDeposit.getSid());
            temp = MySQLDao.load(temp, CustomerDepositPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerDeposit.setSid(MySQLDao.getMaxSid("CRM_CustomerDeposit", conn));
                customerDeposit.setState(Config.STATE_CURRENT);
                customerDeposit.setStatus("0"); //默认为0 未充值
                customerDeposit.setOperatorId(user.getId());
                customerDeposit.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerDeposit, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 删除
     * @param customerDeposit
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(CustomerDepositPO customerDeposit, UserPO user, Connection conn) throws Exception {
        int count = 0;
        customerDeposit.setState(Config.STATE_CURRENT);
        customerDeposit = MySQLDao.load(customerDeposit, CustomerDepositPO.class);
        customerDeposit.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(customerDeposit, conn);
        if (count == 1) {
            customerDeposit.setSid(MySQLDao.getMaxSid("CRM_CustomerDeposit", conn));
            customerDeposit.setState(Config.STATE_DELETE);
            customerDeposit.setOperateTime(TimeUtils.getNow());
            customerDeposit.setOperatorId(user.getId());
            count = MySQLDao.insert(customerDeposit, conn);
        }
        if (count != 1) {
            throw new Exception("删除失败");
        }
        return count;
    }

    // 获取数据列表集合的方法
    public Pager list(CustomerDepositVO customerDepositVO, List<KVObject> conditions) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        // 多表 SQL 查询语句
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT cd.sid,cd.id,cd.state,cd.CustomerId,cp.`Name` customerName,cd.Money,cd.Rate,cd.`Status`,cd.Time,cd.CustomerIP,cd.Fee,cd.MoneyTransfer,cd.FromAccountId,cd.operateTime");
        sql.append(" FROM crm_customerdeposit cd,crm_customerpersonal cp");
        sql.append(" WHERE 1 = 1 AND cd.state = 0 AND cp.state = 0 AND cd.CustomerId = cp.id order by cd.operateTime desc");
        Pager pager = Pager.query(sql.toString(), customerDepositVO, conditions, request, queryType);
        return pager;
    }

    /**
     * 交易平台请求的 Service，充值
     *
     * 前提是网站的 Customer 已经登录，返回的是充值记录的 ID
     *
     * 用法：new CustomerDepositService().deposit4W()
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @param customerPersonalPO
     * @param money    充值金额
     * @param fee      手续费
     * @param rate     费率
     * @param ip       客户端 IP
     * @conn
     * @return 充值记录的 ID
     * @throws Exception
     */
    public String deposit4W(CustomerPersonalPO customerPersonalPO, Double money, Double fee, Double rate, String ip, Connection conn) throws Exception {
        CustomerDepositPO depositPO = new CustomerDepositPO();
        // 设置充值信息
        depositPO.setCustomerId(customerPersonalPO.getId());
        depositPO.setCustomerIP(ip);
        depositPO.setFee(fee);
        depositPO.setRate(rate);
        depositPO.setMoney(money);
        depositPO.setTime(TimeUtils.getNow());
        // 充值状态：未充值，等银行确认交易成功再修改充值状态
        depositPO.setStatus(String.valueOf(Config4Status.STATUS_CUSTOMER_DEPOSIT_UNFINISH));
        // 设置基础的充值表信息
        depositPO.setOperateTime(TimeUtils.getNow());
        depositPO.setState(Config.STATE_CURRENT);
        depositPO.setSid(MySQLDao.getMaxSid("CRM_CustomerDeposit", conn));
        depositPO.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        depositPO.setId(IdUtils.getUUID32());
        Integer count = MySQLDao.insert(depositPO, conn);
        if(count == 1) {
            return depositPO.getId();
        } else {
            throw new Exception("数据库异常！");
        }
    }

    public int insertOrUpdate4W(CustomerDepositPO customerDeposit, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (customerDeposit.getId().equals("")) {
            customerDeposit.setSid(MySQLDao.getMaxSid("CRM_CustomerDeposit", conn));
            customerDeposit.setId(IdUtils.getUUID32());
            customerDeposit.setState(Config.STATE_CURRENT);
            customerDeposit.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            customerDeposit.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerDeposit, conn);
        }
        // 更新
        else {
            CustomerDepositPO temp = new CustomerDepositPO();
            temp.setSid(customerDeposit.getSid());
            temp = MySQLDao.load(temp, CustomerDepositPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerDeposit.setSid(MySQLDao.getMaxSid("CRM_CustomerDeposit", conn));
                customerDeposit.setState(Config.STATE_CURRENT);
                customerDeposit.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                customerDeposit.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerDeposit, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

}
