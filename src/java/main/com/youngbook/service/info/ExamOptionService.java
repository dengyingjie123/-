package com.youngbook.service.info;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.info.ExamOptionPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * 描述：这是培训管理->试题->试题选项对应的Service类。
 * 时间：2015-04-14
 * 创建人：张舜清
 * 修改描述：
 * 修改时间：
 * 修改人：
 * 参考需求：<a href="http://c.hopewealth.net/pages/viewpage.action?pageId=25198880" target="_blank">链接</a>
 * @author 张舜清
 */
@Component("examOptionService")
public class ExamOptionService extends BaseService {
    /**
     * 新增和更新
     * @param examOption
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ExamOptionPO examOption, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (examOption.getId().equals("")) {
            examOption.setSid(MySQLDao.getMaxSid("Info_ExamOption", conn));
            examOption.setId(IdUtils.getUUID32());
            examOption.setState(Config.STATE_CURRENT);
            examOption.setOperatorId(user.getId());
            examOption.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(examOption, conn);
        }
        // 更新
        else {
            ExamOptionPO temp = new ExamOptionPO();
            temp.setSid(examOption.getSid());
            temp = MySQLDao.load(temp, ExamOptionPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                examOption.setSid(MySQLDao.getMaxSid("Info_ExamOption", conn));
                examOption.setState(Config.STATE_CURRENT);
                examOption.setOperatorId(user.getId());
                examOption.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(examOption, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 加载数据
     * @param id
     * @return
     * @throws Exception
     */
    public ExamOptionPO loadExamOptionPO(String id) throws Exception{
        ExamOptionPO po = new ExamOptionPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, ExamOptionPO.class);
        return po;
    }

    /**
     * 删除数据
     * @param examOption
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(ExamOptionPO examOption, UserPO user, Connection conn) throws Exception {
        int count = 0;
        examOption.setState(Config.STATE_CURRENT);
        examOption = MySQLDao.load(examOption, ExamOptionPO.class);
        examOption.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(examOption, conn);
        if (count == 1) {
            examOption.setSid(MySQLDao.getMaxSid("Info_ExamOption", conn));
            examOption.setState(Config.STATE_DELETE);
            examOption.setOperateTime(TimeUtils.getNow());
            examOption.setOperatorId(user.getId());
            count = MySQLDao.insert(examOption, conn);
        }
        if (count != 1) {
            throw new Exception("删除失败");
        }
        return count;
    }
}
