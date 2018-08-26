package com.youngbook.service.customer;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.SmsPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/2/11.
 */
@Component("customerSmsService")
public class CustomerSmsService extends BaseService {

    public int push(List<SmsPO> smsPOs , UserPO user , Connection conn) throws Exception{
        int count = 0;
        for(int i = 0; smsPOs!=null && i < smsPOs.size() ; i++){
            SmsPO smsPO = smsPOs.get(i);
            if(smsPO.getId().equals("")){
                smsPO.setId(IdUtils.getUUID32());
                smsPO.setSid(MySQLDao.getMaxSid("System_Sms" , conn));
                smsPO.setState(Config.STATE_CURRENT);
                smsPO.setOperatorId(user.getId());
                smsPO.setOperateTime(TimeUtils.getNow());
                smsPO.setSenderId(user.getId());
                smsPO.setWaitingTime(TimeUtils.getNow());
                smsPO.setType(0);
                count = MySQLDao.insert(smsPO , conn);
            }
            else {
                SmsPO temp = new SmsPO();
                temp.setSid(smsPO.getSid());
                temp = MySQLDao.load(temp , SmsPO.class);
                temp.setState(Config.STATE_UPDATE);
                count = MySQLDao.update(temp , conn);
                if(count ==1){
                    smsPO.setSid(MySQLDao.getMaxSid("System_Sms" , conn));
                    smsPO.setState(Config.STATE_CURRENT);
                    smsPO.setOperateTime(TimeUtils.getNow());
                    smsPO.setOperatorId(user.getId());
                    count = MySQLDao.insert(smsPO , conn);
                }
            }
            if(count != 1){
                throw new  Exception("数据库异常");
            }
        }
        return count;
    }
}
