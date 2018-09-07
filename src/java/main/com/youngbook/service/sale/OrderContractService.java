package com.youngbook.service.sale;



import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.OrderContractPO;
import com.youngbook.service.BaseService;
//import com.youngbook.entity.vo.Sale.ContractVO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-29
 * Time: 下午6:23
 * To change this template use File | Settings | File Templates.
 */
public class OrderContractService extends BaseService {

    /**
     *
     * @param oc
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(OrderContractPO oc, UserPO user, Connection conn) throws Exception{

        int count = 0;
        // 新增
        if (oc.getId().equals("")) {
            oc.setSid(MySQLDao.getMaxSid("crm_ordercontract", conn));
            oc.setId(IdUtils.getUUID32());
            oc.setState(Config.STATE_CURRENT);
            oc.setOperatorId(user.getId());
            oc.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(oc, conn);

        }
        // 更新
        else {
            OrderContractPO temp = new OrderContractPO();
            temp.setSid(oc.getSid());
            temp = MySQLDao.load(temp, OrderContractPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                oc.setSid(MySQLDao.getMaxSid("crm_ordercontract", conn));
                oc.setState(Config.STATE_CURRENT);
                oc.setOperatorId(user.getId());
                oc.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(oc, conn);
            }
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }






}
