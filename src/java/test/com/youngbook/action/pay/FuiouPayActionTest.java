package com.youngbook.action.pay;

import com.opensymphony.xwork2.ActionProxy;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;

import java.sql.Connection;

public class FuiouPayActionTest extends StrutsTestCase {

    @Test
    public void testAddSalemanOuterBankCard() throws Exception {

        Connection conn = Config.getConnection();

        try {

            // 设置参数
            super.request.setParameter("bankCard", "6225887867377315");
            super.request.setParameter("bankCode", "308");
            super.request.setParameter("reservedMobile", "18007550150");
            super.request.setParameter("idCard", "441322199305082317");
            super.request.setParameter("salemanName", "邓超");
            super.request.setParameter("salemanId", "d133977a9e304cb29d8dfd60501ac43f");

            // 创建 Action 代理
            ActionProxy proxy = getActionProxy("/api/pay/FuiouPay_addSalemanOuterBankCard");
            FuiouPayAction action = (FuiouPayAction) proxy.getAction();

            // 设置 Action 的链接和返回对象
            action.setConnection(Config.getConnection());
            action.setResult(new ReturnObject());

            // 断言
            assertEquals("success", action.addSalemanOuterBankCard());

        } catch (Exception e) {

            throw e;

        } finally {

            if(conn != null) {
                conn.close();
            }

        }

    }

}