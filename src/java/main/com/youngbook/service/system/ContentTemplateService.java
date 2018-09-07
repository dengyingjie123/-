package com.youngbook.service.system;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.ContentTemplatePO;
import com.youngbook.service.BaseService;

import java.sql.Connection;

/**
 * Created by admin on 2015/4/23.
 */
public class ContentTemplateService extends BaseService {

    public int insertOrUpdate(ContentTemplatePO smsTemplate, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (smsTemplate.getId().equals("")) {
            smsTemplate.setSid(MySQLDao.getMaxSid("System_ContentTemplate", conn));
            smsTemplate.setId(IdUtils.getUUID32());
            smsTemplate.setState(Config.STATE_CURRENT);
            smsTemplate.setOperatorId(user.getId());
            smsTemplate.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(smsTemplate, conn);
        }
        // 更新
        else {
            ContentTemplatePO temp = new ContentTemplatePO();
            temp.setSid(smsTemplate.getSid());
            temp = MySQLDao.load(temp, ContentTemplatePO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                smsTemplate.setSid(MySQLDao.getMaxSid("System_ContentTemplate", conn));
                smsTemplate.setState(Config.STATE_CURRENT);
                smsTemplate.setOperatorId(user.getId());
                smsTemplate.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(smsTemplate, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }
    public ContentTemplatePO loadSmsTemplatePO(String id) throws Exception{
        ContentTemplatePO po = new ContentTemplatePO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, ContentTemplatePO.class);

        return po;
    }

    public int delete(ContentTemplatePO smsTemplate, UserPO user, Connection conn) throws Exception {
        int count = 0;

        smsTemplate.setState(Config.STATE_CURRENT);
        smsTemplate = MySQLDao.load(smsTemplate, ContentTemplatePO.class);
        smsTemplate.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(smsTemplate, conn);
        if (count == 1) {
            smsTemplate.setSid(MySQLDao.getMaxSid("System_ContentTemplate", conn));
            smsTemplate.setState(Config.STATE_DELETE);
            smsTemplate.setOperateTime(TimeUtils.getNow());
            smsTemplate.setOperatorId(user.getId());
            count = MySQLDao.insert(smsTemplate, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }


}
