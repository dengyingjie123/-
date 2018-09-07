package com.youngbook.service.allinpay;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentCallbackDetailPO;
import com.youngbook.service.BaseService;

import java.sql.Connection;

/**
 * Created by Lee on 2015/8/26.
 */
public class AllinpayBatchPaymentCallbackDetailService extends BaseService {

    public int insertOrUpdate(AllinpayBatchPaymentCallbackDetailPO po, String userId, Connection conn) throws Exception{

        int count = 0;
        // 新增
        if (po.getId().equals("")) {
            po.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentCallbackDetail", conn));
            po.setId(IdUtils.getUUID32());
            po.setState(Config.STATE_CURRENT);
            po.setOperatorId(userId);
            po.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(po, conn);
        }
        // 更新
        else {
            AllinpayBatchPaymentCallbackDetailPO temp = new AllinpayBatchPaymentCallbackDetailPO();
            temp.setSid(po.getSid());
            temp = MySQLDao.load(temp, AllinpayBatchPaymentCallbackDetailPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                po.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentCallbackDetail", conn));
                po.setState(Config.STATE_CURRENT);
                po.setOperatorId(userId);
                po.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(po, conn);

                // po = MySQLDao.load(po, AllinpayBatchPaymentCallbackDetailPO.class, conn);
            }
        }

        if (count != 1) {
            throw new Exception("更新数据AllinpayBatchPaymentPO失败");
        }

        return count;
    }
}
