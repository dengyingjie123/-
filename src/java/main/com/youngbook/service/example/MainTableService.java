package com.youngbook.service.example;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.example.MainTablePO;
import com.youngbook.service.BaseService;

import java.sql.Connection;

/**
 * Created by Administrator on 2015/5/11.
 创建一个MainTableServlet 类 继承BaseServlet类
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class MainTableService extends BaseService{
    /**
     *添加或修改数据，并修改数据状态
     * @param mainTable
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(MainTablePO mainTable, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (mainTable.getId().equals("")) {
            mainTable.setSid(MySQLDao.getMaxSid("Example_MainTable", conn));
            mainTable.setId(IdUtils.getUUID32());
            mainTable.setState(Config.STATE_CURRENT);
            mainTable.setOperatorId(user.getId());
            mainTable.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(mainTable, conn);
        }
        // 更新
        else {
            MainTablePO temp = new MainTablePO();
            temp.setSid(mainTable.getSid());
            temp = MySQLDao.load(temp, MainTablePO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                mainTable.setSid(MySQLDao.getMaxSid("Example_MainTable", conn));
                mainTable.setState(Config.STATE_CURRENT);
                mainTable.setOperatorId(user.getId());
                mainTable.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(mainTable, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 根据制定的ID获取数据
     * @param id
     * @return
     * @throws Exception
     */
    public MainTablePO loadMainTablePO(String id) throws Exception{
        MainTablePO po = new MainTablePO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, MainTablePO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     * @param mainTable
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(MainTablePO mainTable, UserPO user, Connection conn) throws Exception {
        int count = 0;

        mainTable.setState(Config.STATE_CURRENT);
        mainTable = MySQLDao.load(mainTable, MainTablePO.class);
        mainTable.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(mainTable, conn);
        if (count == 1) {
            mainTable.setSid(MySQLDao.getMaxSid("Example_MainTable", conn));
            mainTable.setState(Config.STATE_DELETE);
            mainTable.setOperateTime(TimeUtils.getNow());
            mainTable.setOperatorId(user.getId());
            count = MySQLDao.insert(mainTable, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }
}
