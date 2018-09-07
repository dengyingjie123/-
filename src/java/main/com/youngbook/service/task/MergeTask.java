package com.youngbook.service.task;

import com.youngbook.command.CommandExecutor;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IOrderDetailDao;
import com.youngbook.dao.production.OrderDaoImpl;
import com.youngbook.dao.production.OrderDetailDaoImpl;
import com.youngbook.entity.po.customer.*;
import com.youngbook.entity.po.production.OrderDetailPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.po.sale.PaymentPlanStatus;
import com.youngbook.service.BaseService;
import com.youngbook.service.customer.*;
import com.youngbook.service.production.OrderDetailService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.production.ProductionService;
import com.youngbook.service.sale.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/5/21.
 */
public class MergeTask extends BaseService {

    IOrderDao orderDao = Config.getBeanByName("orderDao", OrderDaoImpl.class);

    IOrderDetailDao orderDetailDao = Config.getBeanByName("orderDetailDao", OrderDetailDaoImpl.class);

    CustomerPersonalService customerPersonalService = Config.getBeanByName("customerPersonalService", CustomerPersonalService.class);

    ProductionService productionService = Config.getBeanByName("productionService", ProductionService.class);

    OrderDetailService orderDetailService = Config.getBeanByName("orderDetailService", OrderDetailService.class);

    CustomerAccountService customerAccountService = Config.getBeanByName("customerAccountService", CustomerAccountService.class);

    CustomerCertificateService customerCertificateService = Config.getBeanByName("customerCertificateService", CustomerCertificateService.class);

    CustomerMoneyLogService customerMoneyLogService = Config.getBeanByName("customerMoneyLogService", CustomerMoneyLogService.class);


    PaymentPlanService paymentPlanService = Config.getBeanByName("paymentPlanService", PaymentPlanService.class);


    public static void main(String [] args) throws Exception{
        MergeTask task = new MergeTask();


//        task.mergeSaleOrders(OrderStatus.Saled);

//        task.mergeSaleHwbanksOrders();

//        task.mergeSaleOrders(OrderStatus.Transfered);


        task.doMergeCustomer();


    }


    public void doMergeCustomer() throws Exception {
        Connection conn = Config.getConnection();
        try {

            List<KVObject> ids = new ArrayList<KVObject>();

            // 何艳
//            ids.add(new KVObject("070569507278440F929F8B113250C811","FC60A4815A2B4704A1FE04F1A97E8C04"));
//            ids.add(new KVObject("070569507278440F929F8B113250C811","656BC30F6A5948E29B1C6231CEED3C0C"));
//            ids.add(new KVObject("070569507278440F929F8B113250C811","186273ED7401462695CB4B38AD463A84"));

            // 刘伟
//            ids.add(new KVObject("BBBBF0AD9E824446BAE1B2DCDA51F9D2","1219782f6e194ed5a12056a7673c32f0"));
            // 刘艳华
//            ids.add(new KVObject("91F64FF6678A4E8F9AAD9E4E30422A80","5E2B01537C5C4DD39E913F039F481645"));
//
//            // 刘锐
//            ids.add(new KVObject("5C6ABAC72737414BBB2CB1727A793A16","E94171323EA743809B38865D67B70DC6"));

            // 刘艳华
//            ids.add(new KVObject("E983A19005954F968EF04FC8B71CA4C0","A2A27B33DC4E4B52BA9FF04904161FB5"));


            // 吴健咏
//            ids.add(new KVObject("A2A27B33DC4E4B52BA9FF04904161FB5","E983A19005954F968EF04FC8B71CA4C0"));

            // 吴若郗
//            ids.add(new KVObject("C4413003AE3E450FA32D0B4B83CF0406","c608d1637ab7433fb6f563f90e964583"));

            // 周宣含
//            ids.add(new KVObject("A1589FCC94CC4824B8A24F3FA391E270","2a8c5b36ede24fc4a617402be9ce6a7f"));

            // 周昆云
//            ids.add(new KVObject("120D68DE6A534F938AFFD73D82DC1D39","90819FAC67F54F4A89F507F79FC1E1B7"));
//            ids.add(new KVObject("120D68DE6A534F938AFFD73D82DC1D39","A92DC7B1F9354C3E889A660322E3AF02"));

            // 和静
//            ids.add(new KVObject("82BE0613CF4B47F8A90B1E30A0E51196","789717a284f845959d794d0f219fa260"));
            // 唐汇惠
//            ids.add(new KVObject("681AFB608AA744CABC58FBBCB379B68C","AB6D79E268654972BEC4F91D25E61CE3"));
            // 崔妍
//            ids.add(new KVObject("B3E6A19B2958424A9A5BF87A316CFB07","FBF061A779F3449DBFC1B032768100D6"));
            // 李力
//            ids.add(new KVObject("867AF6DDA74C42B8BE730824047AF8FB","f72b39239814481db22df43cc89cafb9"));
            // 李咏竹
//            ids.add(new KVObject("22ACB93437B2434B8F795A877E88FB80","5AA49FC5C61B4F7088A43F57C8659F45"));
            // 李扬
//            ids.add(new KVObject("1A2E9D247AD647208D00562E593A40E8","E495082E60AC47179F24FC7DFEE18995"));
//            ids.add(new KVObject("1A2E9D247AD647208D00562E593A40E8","686E404828A44BEFA0419F927CB6940F"));

            // 杨惠芬
//            ids.add(new KVObject("9D9F51737DAF45C5ACA7EC1FEDD21C77","300a5d49e0e248bfaee0d1c44712056d"));
            // 杨梦婷
//            ids.add(new KVObject("E912422EA70044048EA0DC7FC0077A64","25C38FE7684D4709BDAEBA02521FD8F3"));

            // 樊永东
//            ids.add(new KVObject("D7FDC90325FF4781B7B1E8CCDDBAA98B","300AAD6612824D22A02A90A83DAEDB8A"));
            // 王国基
//            ids.add(new KVObject("63CC28BB010E4F62860F758F1473A0FE","fb1d30232c424702973f2bba850d64c7"));

            // 祝芒
//            ids.add(new KVObject("6836BD5A87554A1EBC1BFC99F6B01EAF","d8c4e8471841470db52aba1efd71df49"));
            // 管彦
//            ids.add(new KVObject("EA4AA9530BE84DB18C022C20F2494454","7001E7AC7F4D47348243C037A8B23022"));
            // 董玲芳
//            ids.add(new KVObject("8A52FE63BD0F44479D6228F6F448D697","07FE0CD6BBC146B291DE9FDCD393DC81"));
            // 陈海荣
//            ids.add(new KVObject("15C8D37556964097AD0B182CA471F0E9","AE99FC07FB9D4610BFD1D5DFCA735E83"));
            // 陈玲
//            ids.add(new KVObject("0519CFEBD6B941C5B22884561D14F499","608648986D7F492CB1F4D943720FAC13"));
            // 陶燕
//            ids.add(new KVObject("8690539633E84954B04E70560E554494","56B40DE53D3B4FFF984590C0AC9DCA37"));
            // 黄文艳
//            ids.add(new KVObject("4D8987DADDDA4FCBA9E7CEE22B4DCFFF","55A380D83CAF44439D09828C31A7E9A0"));
//            ids.add(new KVObject("4D8987DADDDA4FCBA9E7CEE22B4DCFFF","314317709EFF4F458078E89679DC2D17"));

            // 买俊
            ids.add(new KVObject("6BD5FCF3E2764794B4684DBC9AC4EEEB","7782537513A14E86A0D92F52A93BE281"));
//            ids.add(new KVObject("EE980BEC84C54525AF057C32C284ED78","13ECB9118D3A4D73A801B92DCB0E864E"));
//            ids.add(new KVObject("",""));
//            ids.add(new KVObject("",""));
//            ids.add(new KVObject("",""));
//            ids.add(new KVObject("",""));


            conn.setAutoCommit(false);


            int index = 0;
            for (KVObject kvObject : ids) {
                System.out.println();
                System.out.println();
                System.out.println();
                index++;
                System.out.println("客户数" + index + "/" + ids.size());
                this.mergeCustomer(kvObject.getKey().toString(), kvObject.getValue().toString(), conn);

                CommandExecutor.checkIfContinue();

                System.out.println();
                System.out.println();
                System.out.println();


            }


            conn.commit();

        }
        catch (Exception e) {
            conn.rollback();
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }


    public void mergeCustomer(String personalNumber1, String personalNumber2, Connection conn) throws Exception {

        CustomerPersonalPO c1 = customerPersonalService.loadByCustomerPersonalNumber(personalNumber1, conn);
        CustomerPersonalPO c2 = customerPersonalService.loadByCustomerPersonalNumber(personalNumber2, conn);


        int count = 0;


        System.out.println("检查姓名");

        System.out.println(c1);
        System.out.println(c2);
        if (!c1.getName().equals(c2.getName())) {
            MyException.newInstance("姓名不同").throwException();
        }




        /**
         * 身份证号
         *
         * Date: 2016-05-21 14:06:02
         * Author: leevits
         */
        CustomerCertificatePO customerCertificate = customerCertificateService.loadByCustomerId(c1.getId(), conn);

        String idNumber = AesEncrypt.decrypt(customerCertificate.getNumber()).trim();


        CustomerCertificatePO cc2 = customerCertificateService.loadByCustomerId(c2.getId(), conn);

        if (cc2 == null) {
            String cmd = CommandExecutor.getConsoleInput("查不到客户2的证件，是否继续 Y/N？");
            if (!cmd.equalsIgnoreCase("y")) {
                MyException.newInstance("身份证号信息缺失").throwException();
            }
        }
        else {
            String idNumber2 = AesEncrypt.decrypt(cc2.getNumber()).trim();

            System.out.println("身份证号： " + idNumber);
            System.out.println("身份证号： " + idNumber2);
            if (!idNumber.equals(idNumber2)) {
                String cmd = CommandExecutor.getConsoleInput("身份证号不同，是否继续 Y/N？");
                if (!cmd.equalsIgnoreCase("y")) {
                    MyException.newInstance("身份证号不同").throwException();
                }
            }
            else {
                // 身份证相同 idnumber2
                count = MySQLDao.remove(cc2, "0000", conn);
                System.out.println("被删除的身份证: " + idNumber2);
                System.out.println(cc2);
                if (count != 1) {
                    MyException.newInstance("清除身份证失败").throwException();
                }
            }

        }





        /**
         * 基本信息
         *
         * Date: 2016-05-21 14:06:02
         * Author: leevits
         */
        System.out.println("手机号 1：" + c1.getMobile());
        System.out.println("手机号 2：" + c2.getMobile());


        if (!c1.getMobile().equals(c2.getMobile())) {
            String cmd = CommandExecutor.getConsoleInput("手机号不同，是否继续 Y/N？");

            if (!cmd.equalsIgnoreCase("y")) {
                MyException.newInstance("手机号不相同").throwException();
            }
        }
        count = MySQLDao.remove(c2, "0000", conn);
        System.out.println("合并的客户: ");
        System.out.println(c2);
        if (count != 1) {
            MyException.newInstance("合并客户基本信息失败").throwException();
        }



        /**
         * 银行卡号
         *
         * Date: 2016-05-21 14:06:02
         * Author: leevits
         */
//        List<CustomerAccountPO> customerAccounts = customerAccountService.getCustomerAccounts(customerId, 0, conn);
//        System.out.println("客户1的银行卡号, 数量 " + customerAccounts.size());
//        for (CustomerAccountPO ca : customerAccounts) {
//            System.out.println(ca);
//        }


        List<CustomerAccountPO> ca2s = customerAccountService.getCustomerAccounts(c2.getId(), conn);

        System.out.println("客户2的银行卡号, 数量 " + ca2s.size());
        for (CustomerAccountPO ca2 : ca2s) {
            System.out.println(ca2);

            ca2.setCustomerId(c1.getId());

            count = MySQLDao.insertOrUpdate(ca2, "0000", conn);
            System.out.println("合并客户银行卡: ");
            System.out.println(ca2);
            if (count != 1) {
                MyException.newInstance("合并客户银行卡").throwException();
            }
        }




        /**
         * 资金记录
         *
         * Date: 2016-05-21 14:06:02
         * Author: leevits
         */
        List<CustomerMoneyLogPO> customerMoneyLogs = customerMoneyLogService.getCustomerMoneyLogsByCustomerId(c2.getId(), conn);

        System.out.println("需要处理的资金记录数：" + (customerMoneyLogs != null ? customerMoneyLogs.size() : 0));

        CommandExecutor.checkIfContinue();

        for (CustomerMoneyLogPO log : customerMoneyLogs) {
            log.setCustomerId(c1.getId());

            count = MySQLDao.insertOrUpdate(log, "0000", conn);
            System.out.println("合并的资金记录: ");
            System.out.println(log);
            if (count != 1) {
                MyException.newInstance("合并资金记录").throwException();
            }
        }


        /**
         * 客户分配
         *
         * Date: 2016-05-21 14:06:02
         * Author: leevits
         */

        CustomerDistributionService customerDistributionService = new CustomerDistributionService();
        List<CustomerDistributionPO> customerDistributionPOs = customerDistributionService.getCustomerDistributionsByCustomerId(c1.getId(), conn);

        System.out.println("需要处理的客户分配数：" + (customerDistributionPOs != null ? customerDistributionPOs.size() : 0));

        CommandExecutor.checkIfContinue();

        for (CustomerDistributionPO distribution : customerDistributionPOs) {
            distribution.setCustomerId(c1.getId());

            count = MySQLDao.insertOrUpdate(distribution, "0000", conn);
            System.out.println("合并的资金记录: ");
            System.out.println(distribution);
            if (count != 1) {
                MyException.newInstance("合并资金记录").throwException();
            }
        }


        // 查找id2名下的订单
        List<OrderPO> order2s = orderDao.getListOrderPOByCustomerId(c2.getId(), conn);
        System.out.println("需要整合的订单数： " + (order2s != null ? order2s.size() : 0));

        CommandExecutor.checkIfContinue();

        if (order2s != null && order2s.size() > 0) {

            for (OrderPO order : order2s) {
                System.out.println("需要整合订单");
                System.out.println(order);



                /**
                 * 整合订单
                 *
                 *
                 * Date: 2016-05-21 14:05:26
                 * Author: leevits
                 */
                order.setCustomerId(c1.getId());


                count = MySQLDao.insertOrUpdate(order, "0000", conn);
                System.out.println("合并的订单: ");
                System.out.println(order);
                if (count != 1) {
                    MyException.newInstance("合并订单失败").throwException();
                }


                /**
                 * 整合订单明细
                 *
                 * Date: 2016-05-21 14:05:47
                 * Author: leevits
                 */
                List<OrderDetailPO> details = orderDetailService.getOrderDetailsByOrderId(order.getId(), conn);
                for (OrderDetailPO detail : details) {
                    detail.setCustomerId(c1.getId());

                    count = MySQLDao.insertOrUpdate(detail, "0000", conn);
                    System.out.println("合并的订单明细: ");
                    System.out.println(detail);
                    if (count != 1) {
                        MyException.newInstance("合并订单失败").throwException();
                    }
                }


                /**
                 * 兑付计划
                 *
                 * Date: 2016-05-21 14:06:02
                 * Author: leevits
                 */
                List<PaymentPlanPO> paymentPlans = paymentPlanService.getPaymentPlansByOrderId(order.getId(), conn);
                System.out.println("需要处理的兑付计划数：" + (paymentPlans != null ? paymentPlans.size() : 0));

                CommandExecutor.checkIfContinue();

                for (PaymentPlanPO paymentPlan : paymentPlans) {
                    paymentPlan.setCustomerId(c1.getId());

                    count = MySQLDao.insertOrUpdate(paymentPlan, "0000", conn);
                    System.out.println("合并的兑付计划: ");
                    System.out.println(paymentPlan);
                    if (count != 1) {
                        MyException.newInstance("合并兑付计划").throwException();
                    }
                }

            }

        }


    }



    public void mergeSaleOrders(int orderStatus) throws Exception {

        Connection conn = Config.getConnection();
        try {

            conn.setAutoCommit(false);
            DatabaseSQL dbSQL = new DatabaseSQL();
            dbSQL.newSQL("select * from crm_order where state=0 and status=?");
            dbSQL.addParameter(1, orderStatus);

            List<OrderPO> orders = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), OrderPO.class, null, conn);
            for (OrderPO order : orders) {
                this.createOrderDetail(order.getId(), orderStatus, conn);
            }

            conn.commit();

        }
        catch (Exception e) {
            conn.rollback();
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }


    public void mergeSaleHwbanksOrders() throws Exception {

        Connection conn = Config.getConnection();
        try {

            conn.setAutoCommit(false);
            DatabaseSQL dbSQL = new DatabaseSQL();
            dbSQL.newSQL("select * from crm_order where state=0 and id in (select orderId from core_paymentplan pp where pp.state=0 and status=?)");
            dbSQL.addParameter(1, PaymentPlanStatus.Paid);

            List<OrderPO> orders = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), OrderPO.class, null, conn);

            System.out.println("合并通宝订单" + orders.size());

            for (OrderPO order : orders) {

                System.out.println("操作订单： " + order);

                this.createOrderDetail(order.getId(), OrderStatus.Payback, conn);



                System.out.println("保存订单信息: " + order);
            }

            conn.commit();

        }
        catch (Exception e) {
            conn.rollback();
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }


    private void createOrderDetail(String orderId, int orderStatus, Connection conn) throws Exception {
        OrderPO order = orderDao.loadByOrderId(orderId, conn);


        System.out.println("开始创建订单明细");
        System.out.println(order);

        if (order == null) {
            MyException.newInstance("无法获得订单信息").throwException();
        }

        if (StringUtils.isEmpty(order.getCustomerName())) {
            CustomerPersonalPO customer = customerPersonalService.loadByCustomerPersonalId(order.getCustomerId(), conn);
            order.setCustomerName(customer.getName());
        }


        if (StringUtils.isEmpty(order.getCustomerName())) {
            MyException.newInstance("无法获得订单客户名称").throwException();
        }


        // 保存订单信息
        int count = MySQLDao.insertOrUpdate(order, conn);
        if (count != 1) {
            MyException.newInstance("保存订单信息失败").throwException();
        }

        if (order.getStatus() == orderStatus) {
            OrderDetailPO detail = orderDetailDao.getOrderDetailFromOrder(order);
            int nextLine = orderDetailDao.getOrderDetailNextLineNumber(order.getId(), conn);
            detail.setOrderLine(nextLine);
            detail.setStatus(orderStatus);

            count = MySQLDao.insertOrUpdate(detail, conn);
            if (count != 1) {
                MyException.newInstance("创建订单明细失败").throwException();
            }

            System.out.println("已创建订单明细");


            order.setStatus(OrderStatus.Payback);

            count = MySQLDao.insertOrUpdate(order, conn);
            if (count != 1) {
                MyException.newInstance("保存订单出错").throwException();
            }
        }



        System.out.println();
        System.out.println();
    }
}
