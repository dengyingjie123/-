package com.youngbook.service.sale;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.SaleTask4SalemanPO;
import com.youngbook.entity.vo.Sale.SaleTask4SalemanVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2015/5/22.
 * 产品销售小组分配
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class SaleTask4SalemanService extends BaseService {

    /**
     * 获取本销售以分配产品的销售人员列表
     * @param saleTask4Saleman
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager list(SaleTask4SalemanVO saleTask4Saleman, List<KVObject> conditions) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT ss.Sid,ss.Id,ss.State,");
        sbSQL.append(" ss.OperatorId,ss.OperateTime,");
        sbSQL.append(" ss.ProductionId,ss.SaleGroupId,");
        sbSQL.append(" ss.SalemanId,ss.TaskMoney,");
        sbSQL.append(" ss.StartTime,ss.EndTime,");
        sbSQL.append(" ss.WaitingMoney,ss.AppointmengMoney,");
        sbSQL.append(" ss.SoldMoney,ss.TotoalCancelMoney,");
        sbSQL.append(" us.`name` as salemanName");
        sbSQL.append(" FROM sale_saletask4saleman  ss,");
        sbSQL.append(" crm_saleman cs,system_user us");
        sbSQL.append("  Where ss.SalemanId=cs.UserId");
        sbSQL.append("  AND us.Id=cs.UserId");
        sbSQL.append(" AND ss.state =0 ANd cs.state=0 AND us.state=0");

        HttpServletRequest request = ServletActionContext.getRequest();

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), saleTask4Saleman, conditions, request, queryType);

        return pager;
    }

    /**
     * 添加或修改数据，并修改数据状态
     *
     * @param saleTask4Saleman
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(SaleTask4SalemanPO saleTask4Saleman, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (saleTask4Saleman.getId().equals("")) {
            saleTask4Saleman.setSid(MySQLDao.getMaxSid("Sale_SaleTask4Saleman", conn));
            saleTask4Saleman.setId(IdUtils.getUUID32());
            saleTask4Saleman.setState(Config.STATE_CURRENT);
            saleTask4Saleman.setOperatorId(user.getId());
            saleTask4Saleman.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(saleTask4Saleman, conn);
        }
        // 更新
        else {
            SaleTask4SalemanPO temp = new SaleTask4SalemanPO();
            temp.setSid(saleTask4Saleman.getSid());
            temp = MySQLDao.load(temp, SaleTask4SalemanPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                saleTask4Saleman.setSid(MySQLDao.getMaxSid("Sale_SaleTask4Saleman", conn));
                saleTask4Saleman.setState(Config.STATE_CURRENT);
                saleTask4Saleman.setOperatorId(user.getId());
                saleTask4Saleman.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(saleTask4Saleman, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 根据制定的ID获取数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public SaleTask4SalemanPO loadSaleTask4SalemanPO(String id) throws Exception {
        SaleTask4SalemanPO po = new SaleTask4SalemanPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, SaleTask4SalemanPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     *
     * @param saleTask4Saleman
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(SaleTask4SalemanPO saleTask4Saleman, UserPO user, Connection conn) throws Exception {
        int count = 0;

        saleTask4Saleman.setState(Config.STATE_CURRENT);
        saleTask4Saleman = MySQLDao.load(saleTask4Saleman, SaleTask4SalemanPO.class);
        saleTask4Saleman.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(saleTask4Saleman, conn);
        if (count == 1) {
            saleTask4Saleman.setSid(MySQLDao.getMaxSid("Sale_SaleTask4Saleman", conn));
            saleTask4Saleman.setState(Config.STATE_DELETE);
            saleTask4Saleman.setOperateTime(TimeUtils.getNow());
            saleTask4Saleman.setOperatorId(user.getId());
            count = MySQLDao.insert(saleTask4Saleman, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }
}
