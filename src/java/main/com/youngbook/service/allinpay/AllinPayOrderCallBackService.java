package com.youngbook.service.allinpay;

import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.allinpay.AllinPayOrderCallBackPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by 张舜清 on 2015/8/5.
 */
@Component("allinPayOrderCallBackService")
public class AllinPayOrderCallBackService extends BaseService {
    public AllinPayOrderCallBackPO saveAllinPayOrderCallBack(AllinPayOrderCallBackPO allinPayOrderCallBack , Connection conn) throws Exception {
        int count = 0;
        allinPayOrderCallBack.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        allinPayOrderCallBack.setOperateTime(TimeUtils.getNow());

        // 商户订单金额
        allinPayOrderCallBack.setOrderAmount(allinPayOrderCallBack.getOrderAmount() / 100);

        // 订单实际支付金额
        allinPayOrderCallBack.setPayAmount(allinPayOrderCallBack.getPayAmount() / 100);

        count = MySQLDao.insertOrUpdate(allinPayOrderCallBack, conn);

        if (count != 1) {
            MyException.newInstance(Config.getWords("w.pay.production.soldout"), "通联支付返回信息保存失败").throwException();
        }
        return allinPayOrderCallBack;
    }
}
