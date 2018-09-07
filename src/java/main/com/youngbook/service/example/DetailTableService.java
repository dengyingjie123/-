package com.youngbook.service.example;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.example.DetailTablePO;
import com.youngbook.entity.po.example.MainTablePO;
import com.youngbook.service.BaseService;

import java.sql.Connection;

/**
 * Created by Administrator on 2015/5/11.
 创建一个MainTableServlet 类 继承BaseServlet类
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class DetailTableService extends BaseService{
    DetailTablePO detailTable = new DetailTablePO();

    /**
     * 加载
     * @param id
     * @return
     * @throws Exception
     */
    public DetailTablePO loadDetailTablePO(String id) throws Exception{

        detailTable.setId(id);
        detailTable.setState(Config.STATE_CURRENT);
        detailTable = MySQLDao.load(detailTable, DetailTablePO.class);

        return detailTable;
    }

    /**
     * 新增或者更新
     * @param detailTable
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(DetailTablePO detailTable, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (detailTable.getId().equals("")) {
            detailTable.setSid(MySQLDao.getMaxSid("Example_DetailTable", conn));
            detailTable.setId(IdUtils.getUUID32());
            detailTable.setState(Config.STATE_CURRENT);
            detailTable.setOperatorId(user.getId());
            detailTable.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(detailTable, conn);
        }
        // 更新
        else {
            DetailTablePO temp = new DetailTablePO();
            temp.setSid(detailTable.getSid());
            temp = MySQLDao.load(temp, DetailTablePO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                detailTable.setSid(MySQLDao.getMaxSid("Example_DetailTable", conn));
                detailTable.setState(Config.STATE_CURRENT);
                detailTable.setOperatorId(user.getId());
                detailTable.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(detailTable, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 删除
     * @param detailTable
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(DetailTablePO detailTable, UserPO user, Connection conn) throws Exception {
        int count = 0;

        detailTable.setState(Config.STATE_CURRENT);
        detailTable = MySQLDao.load(detailTable, DetailTablePO.class);
        detailTable.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(detailTable, conn);
        if (count == 1) {
            detailTable.setSid(MySQLDao.getMaxSid("Example_DetailTable", conn));
            detailTable.setState(Config.STATE_DELETE);
            detailTable.setOperateTime(TimeUtils.getNow());
            detailTable.setOperatorId(user.getId());
            count = MySQLDao.insert(detailTable, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }
}
