package com.youngbook.service.core;

import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.core.APICommandPO;
import com.youngbook.entity.po.core.OrderPayPO;
import com.youngbook.service.BaseService;
import com.youngbook.service.pay.FuiouPaymentService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
@Component("orderPayService")
public class OrderPayService extends BaseService {

    /**
     * 生成支付的实名认证信息
     *
     * 修改：邓超
     * 内容：从 CustomerPersonalService 移动代码至此
     * 时间：2016年5月18日
     *
     * @param bizId
     * @param customerId
     * @param name
     * @param idCard
     * @param bankNumber
     * @param bankCode
     * @param conn
     * @return
     * @throws Exception
     */
    public OrderPayPO buildPayAuthentication(String bizId, String customerId, String name, String idCard, String bankNumber, String bankCode, Connection conn) throws Exception {

        // 获取某个支付业务 ID 的接口指令
        FuiouPaymentService fuiouPaymentService = new FuiouPaymentService();
        APICommandPO commandPO = fuiouPaymentService.loadAPICommandByBizId(bizId, conn);
        if (commandPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有找到对应的支付接口指令").throwException();
        }

        // 查询订单支付
        String commandId = commandPO.getId();
        OrderPayPO orderPay = fuiouPaymentService.loadOrderPay(commandId, customerId, conn);
        if (orderPay == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有找到对应的订单支付内容").throwException();
        }

        orderPay.setCustomerName(name);
        orderPay.setCustomerCertificateType("98");
        orderPay.setCustomerCertificateNo(AesEncrypt.encrypt(idCard));
        orderPay.setBankAccount(AesEncrypt.encrypt(bankNumber));
        orderPay.setBankCode(bankCode);
        Integer orderPayExecCount = MySQLDao.insertOrUpdate(orderPay, conn);

        return orderPayExecCount == 1 ? orderPay : null;

    }

}
