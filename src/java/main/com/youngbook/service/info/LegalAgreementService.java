package com.youngbook.service.info;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.info.LegalAgreementPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by 邓超
 * Date 2015-4-1
 */
@Component("legalAgreementService")
public class LegalAgreementService extends BaseService {

    /**
     * 增加或修改
     * @param legalAgreement
     * @param user
     * @param conn
     * @return int
     * @throws Exception
     */
    public int insertOrUpdate(LegalAgreementPO legalAgreement, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (legalAgreement.getId().equals("")) {
            legalAgreement.setSid(MySQLDao.getMaxSid("Info_LegalAgreement", conn));
            legalAgreement.setId(IdUtils.getUUID32());
            legalAgreement.setState(Config.STATE_CURRENT);
            legalAgreement.setOperatorId(user.getId());
            legalAgreement.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(legalAgreement, conn);
        }
        // 更新
        else {
            LegalAgreementPO temp = new LegalAgreementPO();
            temp.setSid(legalAgreement.getSid());
            temp = MySQLDao.load(temp, LegalAgreementPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                legalAgreement.setSid(MySQLDao.getMaxSid("Info_LegalAgreement", conn));
                legalAgreement.setState(Config.STATE_CURRENT);
                legalAgreement.setOperatorId(user.getId());
                legalAgreement.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(legalAgreement, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 删除
     * @param legalAgreement
     * @param user
     * @param conn
     * @return int
     * @throws Exception
     */
    public int delete(LegalAgreementPO legalAgreement, UserPO user, Connection conn) throws Exception {
        int count = 0;
        legalAgreement.setState(Config.STATE_CURRENT);
        legalAgreement = MySQLDao.load(legalAgreement, LegalAgreementPO.class);
        legalAgreement.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(legalAgreement, conn);
        if (count == 1) {
            legalAgreement.setSid(MySQLDao.getMaxSid("Info_LegalAgreement", conn));
            legalAgreement.setState(Config.STATE_DELETE);
            legalAgreement.setOperateTime(TimeUtils.getNow());
            legalAgreement.setOperatorId(user.getId());
            count = MySQLDao.insert(legalAgreement, conn);
        }
        if (count != 1) {
            throw new Exception("删除失败");
        }
        return count;
    }

    /**
     * 修改人：姚章鹏
     * 内容：根据id获取法律协议
     * 时间：2015年8月20日11:11:03
     * @param Id
     * @param conn
     * @return
     * @throws Exception
     */
    public LegalAgreementPO getAgreementById(String Id,Connection conn) throws Exception {
        LegalAgreementPO agereement=new  LegalAgreementPO();
        agereement.setId(Id);
        agereement.setState(Config.STATE_CURRENT);
        agereement = MySQLDao.load(agereement,LegalAgreementPO.class, conn);
        return agereement;
    }

    /**
     * 获取注册协议文章
     * @return
     * @throws Exception
     */
    public LegalAgreementPO getAgreementById() throws Exception {
        LegalAgreementPO agereement=new  LegalAgreementPO();
        agereement.setId(Config.getSystemVariable("web.register.lawId"));
        agereement.setState(Config.STATE_CURRENT);
        agereement = MySQLDao.load(agereement,LegalAgreementPO.class);
        return agereement;
    }

}
