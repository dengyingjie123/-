package com.youngbook.service.allinpay;

import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Bank;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.allinpay.AllinPayUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.allinpay.AllinPayOrderPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderTradingStatus;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by 张舜清 on 2015/8/4.
 */
@Component("allinPayOrderService")
public class AllinPayOrderService extends BaseService {

    public CustomerPersonalPO getCustomerPersonalFromOrderNo(String orderNo, Connection conn) throws Exception {

        if (StringUtils.isEmpty(orderNo)) {
            throw new Exception("传入的通联支付订单编号为空");
        }

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     *");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal c");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND c.id = (");
        sbSQL.append("     SELECT DISTINCT");
        sbSQL.append("         o.CustomerId");
        sbSQL.append("     FROM");
        sbSQL.append("         bank_AllinPayTransfer pay,");
        sbSQL.append("         crm_order o");
        sbSQL.append("     WHERE");
        sbSQL.append("         1 = 1");
        sbSQL.append("     AND o.state = 0");
        sbSQL.append("     AND pay.BizId = o.id");
        sbSQL.append("     AND pay.OrderNo = '"+orderNo+"'");
        sbSQL.append(" )");

        List<CustomerPersonalPO> list = MySQLDao.query(sbSQL.toString(), CustomerPersonalPO.class, null, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 创建人：张舜清
     * 时间：8/4/2015
     * 内容：客户充值生成订单
     *
     * @param customerPersonal
     * @param bizId
     * @param money
     * @param conn
     * @return
     * @throws Exception
     */
    public AllinPayOrderPO build4W(CustomerPersonalPO customerPersonal,String pickupUrl, String receiveUrl, String bizId,String productName, Integer money, Integer payType, Connection conn) throws Exception {
        AllinPayOrderPO allinPayOrder = new AllinPayOrderPO();
        // 我们的数据库规格必有字段
        allinPayOrder.setSid(MySQLDao.getMaxSid("bank_AllinPayTransfer", conn));
        allinPayOrder.setId(IdUtils.getUUID32());
        allinPayOrder.setState(Config.STATE_CURRENT);
        allinPayOrder.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        allinPayOrder.setOperateTime(TimeUtils.getNow());

        // 通联支付
        allinPayOrder.setInputCharset(Config4Bank.INPUT_CHARSET_TYPE_UTF_8);
        allinPayOrder.setPickupUrl(pickupUrl);
        allinPayOrder.setReceiveUrl(receiveUrl);
        allinPayOrder.setLanguage(Config4Bank.LANGUAGE_TYPE_SIMPLIFIED_CHINESE);
        allinPayOrder.setSignType(Config4Bank.SING_TYPE_0);
        allinPayOrder.setMerchantId(Config.getSystemConfig("bank.pay.allinpay.merchantId"));
        allinPayOrder.setPayerName(customerPersonal.getName());
        allinPayOrder.setOrderNo(AllinPayUtils.getAllinPayOrderNumber());
        allinPayOrder.setOrderAmount(money);
        allinPayOrder.setOrderCurrency(Config4Bank.ORDER_CURRENCY_TYPE_0);
        allinPayOrder.setOrderDatetime(AllinPayUtils.getOrderDateTime());
        allinPayOrder.setProductName(productName);
        allinPayOrder.setPayType(payType);
        // 根据通联支付的需求把签名源串和提供的测试key一起加密为大写的md5然后赋值给signMsg
        String md5 = StringUtils.md5WithHex(AllinPayUtils.getSignSrc(allinPayOrder) + "&key=" + Config.getSystemConfig("bank.pay.allinpay.md5key"));
        allinPayOrder.setSignMsg(md5);

        // 我们本身要保存的记录
        allinPayOrder.setBizId(bizId);
        allinPayOrder.setBizType(String.valueOf(Config4Bank.BANK_BIZ_TYPE_RECHARGE));
        allinPayOrder.setTradingStatus(AllinPayOrderTradingStatus.Unaccepted);
        int count = MySQLDao.insert(allinPayOrder, conn);

        if (count != 1) {
            MyException.newInstance("系统繁忙，请稍候再试！", "生成通联支付订单失败！").throwException();
        }

        return allinPayOrder;
    }
}
