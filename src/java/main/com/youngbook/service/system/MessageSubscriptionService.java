package com.youngbook.service.system;

/**
 * Created by Jepson on 2015/6/25.
 */
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.MessageSubscriptionPO;
import com.youngbook.service.BaseService;

import java.sql.Connection;

/**
 *
 * 创建一个MessageSubscriptionServlet 类 继承BaseServlet类
 * @author Codemaker
 */

public class MessageSubscriptionService extends BaseService{
    /**
     * 添加或修改数据，并修改数据状态
     * @param messageSubscription
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(MessageSubscriptionPO messageSubscription, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (messageSubscription.getId().equals("")) {
            messageSubscription.setSid(MySQLDao.getMaxSid("system_MessageSubscription", conn));
            messageSubscription.setId(IdUtils.getUUID32());
            messageSubscription.setState(Config.STATE_CURRENT);
            messageSubscription.setOperatorId(user.getId());
            messageSubscription.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(messageSubscription, conn);
        }
        // 更新
        else {
            MessageSubscriptionPO temp = new MessageSubscriptionPO();
            temp.setSid(messageSubscription.getSid());
            temp = MySQLDao.load(temp, MessageSubscriptionPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                messageSubscription.setSid(MySQLDao.getMaxSid("system_MessageSubscription", conn));
                messageSubscription.setState(Config.STATE_CURRENT);
                messageSubscription.setOperatorId(user.getId());
                messageSubscription.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(messageSubscription, conn);
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
    public MessageSubscriptionPO loadMessageSubscriptionPO(String id) throws Exception{
        MessageSubscriptionPO po = new MessageSubscriptionPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, MessageSubscriptionPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     * @param messageSubscription
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(MessageSubscriptionPO messageSubscription, UserPO user, Connection conn) throws Exception {
        int count = 0;

        messageSubscription.setState(Config.STATE_CURRENT);
        messageSubscription = MySQLDao.load(messageSubscription, MessageSubscriptionPO.class);
        messageSubscription.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(messageSubscription, conn);
        if (count == 1) {
            messageSubscription.setSid(MySQLDao.getMaxSid("system_MessageSubscription", conn));
            messageSubscription.setState(Config.STATE_DELETE);
            messageSubscription.setOperateTime(TimeUtils.getNow());
            messageSubscription.setOperatorId(user.getId());
            count = MySQLDao.insert(messageSubscription, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }
}
