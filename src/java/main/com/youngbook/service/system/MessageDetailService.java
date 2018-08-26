package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.MessageDetailPO;
import com.youngbook.entity.po.system.MessagePO;
import com.youngbook.entity.vo.system.MessageVO;
import com.youngbook.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by fugy on 2016/3/11.
 */
public class MessageDetailService extends BaseService {

    public int insertOrUpdate(MessageDetailPO msgDetail, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (msgDetail.getId().equals("")) {
            msgDetail.setSid(MySQLDao.getMaxSid("system_systemmessagedetail", conn));
            msgDetail.setId(IdUtils.getUUID32());
            msgDetail.setState(Config.STATE_CURRENT);
            msgDetail.setOperatorId(user.getId());
            msgDetail.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(msgDetail, conn);
        }
        // 更新
        else {
            MessageDetailPO msgDPO = new MessageDetailPO();
            msgDPO = MySQLDao.load(msgDPO, MessageDetailPO.class);
            msgDPO.setSid(msgDetail.getSid());
            msgDPO.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(msgDPO, conn);
            if (count == 1) {
                msgDPO.setSid(MySQLDao.getMaxSid("system_systemmessagedetail", conn));
                msgDPO.setState(Config.STATE_CURRENT);
                msgDPO.setOperatorId(user.getId());
                msgDPO.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(msgDPO, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    public int delete(MessageDetailPO msgDPO, UserPO user, Connection conn) throws Exception {
        int count = 0;
        msgDPO = MySQLDao.load(msgDPO, MessageDetailPO.class);
        msgDPO.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(msgDPO, conn);
        if (count == 1){
            msgDPO.setSid(MySQLDao.getMaxSid("system_systemmessagedetail", conn));
            msgDPO.setState(Config.STATE_DELETE);
            msgDPO.setOperateTime(TimeUtils.getNow());
            msgDPO.setOperatorId(user.getId());
            count = MySQLDao.insert(msgDPO, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }
}












