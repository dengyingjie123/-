package com.youngbook.service.customer;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerPointPO;
import com.youngbook.entity.vo.customer.CustomerPointVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2015/4/20.
 * 客户积分
 */
public class CustomerPointService extends BaseService {
    public Pager list(CustomerPointVO customerPointVO, List<KVObject> conditions)throws Exception {
        //组装数据库查询语句
        StringBuffer SQL = new StringBuffer();
        SQL.append("SELECT CC.Sid,CC.Id,CC.State,CC.OperatorId,CC.OperateTime,");
        SQL.append("CC.CustomerId,CC.AvailablePoint,CC.UsedPoint,");
        SQL.append("CC1.Name AS customerName");
        SQL.append(" FROM CRM_CustomerPoint CC, crm_customerpersonal CC1");
        SQL.append(" where CC.customerId= CC1.id AND CC1.state=0 AND CC.state=0");

        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(SQL.toString(), customerPointVO, conditions, request, queryType);
        return pager;
    }

    /**
     * 添加并更新数据
     *
     * @param customerPoint
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(CustomerPointPO customerPoint, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (customerPoint.getId().equals("")) {
            customerPoint.setSid(MySQLDao.getMaxSid("CRM_CustomerPoint", conn));
            customerPoint.setId(IdUtils.getUUID32());
            customerPoint.setState(Config.STATE_CURRENT);
            customerPoint.setOperatorId(user.getId());
            customerPoint.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerPoint, conn);
        }
        // 更新
        else {
            CustomerPointPO temp = new CustomerPointPO();
            temp.setSid(customerPoint.getSid());
            temp = MySQLDao.load(temp, CustomerPointPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerPoint.setSid(MySQLDao.getMaxSid("CRM_CustomerPoint", conn));
                customerPoint.setState(Config.STATE_CURRENT);
                customerPoint.setOperatorId(user.getId());
                customerPoint.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerPoint, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 根据ID获取数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public CustomerPointPO loadCustomerPointPO(String id) throws Exception {
        CustomerPointPO po = new CustomerPointPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, CustomerPointPO.class);
        return po;
    }

    /**
     * 将数据状态修改。
     *
     * @param customerPoint
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(CustomerPointPO customerPoint, UserPO user, Connection conn) throws Exception {
        int count = 0;

        customerPoint.setState(Config.STATE_CURRENT);
        customerPoint = MySQLDao.load(customerPoint, CustomerPointPO.class);
        customerPoint.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(customerPoint, conn);
        if (count == 1) {
            customerPoint.setSid(MySQLDao.getMaxSid("CRM_CustomerPoint", conn));
            customerPoint.setState(Config.STATE_DELETE);
            customerPoint.setOperateTime(TimeUtils.getNow());
            customerPoint.setOperatorId(user.getId());
            count = MySQLDao.insert(customerPoint, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

}
