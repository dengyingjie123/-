package com.youngbook.command;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.runner.TimeRunner;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.po.sale.PaymentPlanStatus;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.service.iceland.IcelandService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.sale.PaymentPlanService;
import com.youngbook.service.sale.contract.ContractService;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.UserService;
import com.youngbook.service.task.CustomerTask;
import com.youngbook.service.task.FuiouOrderQueryBatchTask;
import com.youngbook.service.task.FuiouOrderQueryTask;
import com.youngbook.service.task.MergeTask;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CommandExecutor {

    @Autowired
    IOrderDao orderDao;

    @Autowired
    OrderService orderService;

    @Autowired
    ContractService contractService;

    LogService logService = Config.getBeanByName("logService", LogService.class);

    IcelandService icelandService = Config.getBeanByName("icelandService", IcelandService.class);

    UserService userService = Config.getBeanByName("userService", UserService.class);

    public static void main(String [] args) {

        String [] commandNames = new String[] {"OrderBuildAndPayManually", "CancelContract", "ExecutePaymentPlan", "distributeCustomerTask","TimeRunner", "Test", "FuiouPCOrderScanAndUpdateTask","FuiouOrderQueryTask", "CustomerTask","icelandCustomerImportTask", "customerMergeTask"};

        try {

            CommandExecutor executor = new CommandExecutor();

            System.out.println("输入'?'或'help'可以获得帮助，输入'exit'退出系统。");
            String command = "";

            while (StringUtils.isEmpty(command)) {
                command = executor.getConsoleInput("请输入指令名称或序号");

                if (command.equals("?") || command.equals("help")) {
                    int commandNameCount = 1;
                    for (String commandName : commandNames) {
                        System.out.println( commandNameCount + ": " + commandName);
                        commandNameCount++;
                    }

                    command = "";
                }

                if (command.equals("exit")) {
                    System.exit(0);
                }
            }

            if (StringUtils.isNumeric(command)) {
                int index = Integer.parseInt(command);
                command = commandNames[index - 1];

                CommandExecutor.checkIfContinue("即将执行：" + command);
            }

            if (command.equals("OrderBuildAndPayManually")) {
                executor.orderBuildAndPayManually();
                return;
            }

            if (command.equals("CancelContract")) {
                executor.cancelContract();
                return;
            }

            if (command.equals("ExecutePaymentPlan")) {
                executor.executePaymentPlan();
                return;
            }


            if (command.equals("FuiouOrderQueryTask")) {
                FuiouOrderQueryTask fuiouOrderQueryTask = new FuiouOrderQueryTask();
                fuiouOrderQueryTask.run();
                return;
            }

            if (command.equals("TimeRunner")) {
                executor.executeTimeRunner();
                return;
            }

            if (command.equals("CustomerTask")) {
                if (!CommandExecutor.getConsoleInput( "请将导入文件保存至【d:/customerTask.xls】，是否继续？ y/n").equalsIgnoreCase("y")) {
                    MyException.newInstance("手动取消").throwException();
                }
                CustomerTask customerTask = new CustomerTask();
                customerTask.doImport("d:/customerTask.xls");
                return;
            }



            // icelandCustomerImportTask

            // customerMergeTask
            if (command.equals("customerMergeTask")) {
                executor.customerMergeTask();
                return;
            }

            if (command.equals("icelandCustomerImportTask")) {
                executor.icelandCustomerImportTask();
                return;
            }

            if (command.equals("Test")) {
                executor.test();
                return;
            }

            if(command.equals("FuiouPCOrderScanAndUpdateTask")) {
                executor.fuiouPCOrderScanAndUpdateTask();
                return ;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeTimeRunner() throws Exception {
        TimeRunner timeRunner = new TimeRunner();
        timeRunner.run();
    }

    public void icelandCustomerImportTask() throws Exception {
        if (!CommandExecutor.getConsoleInput( "请将导入文件保存至【d:/icelandCustomerImportTask.xls】，是否继续？ y/n").equalsIgnoreCase("y")) {
            MyException.newInstance("手动取消").throwException();
        }
        icelandService.importCustomer("d:/icelandCustomerImportTask.xls");
    }


    public static void checkIfContinue(String message) throws Exception {
        if (!CommandExecutor.getConsoleInput( message + "，是否继续？ y/n").equalsIgnoreCase("y")) {
            MyException.newInstance("手动取消").throwException();
        }
    }

    public static void checkIfContinue() throws Exception {
        checkIfContinue("");
    }

    private String getParametersValue(List<KVObject> parameters) {

        StringBuffer sbParameter = new StringBuffer();

        for (KVObject kvObject : parameters) {
            sbParameter.append(kvObject.getKey()).append(" : ").append(kvObject.getValue()).append(",");
        }

        return sbParameter.toString();
    }

    public static String getConsoleInput(String message) throws Exception{
        BufferedReader brCommand = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(message + ": ");
        String input = brCommand.readLine();
        return input;
    }

    private void cancelContract() throws Exception {

        System.out.println("开始执行作废合同");

        String contractNo = getConsoleInput("请输入合同编号，系统规则创建，103开头");
        String userId = getConsoleInput("请输入用户编号");




        Connection conn = Config.getConnection();
        try {


            UserService userService = new UserService();
            UserPositionInfoPO userPositionInfoPO = userService.getUserPositionInfoByUserId(userId, conn);

            contractService.cancelContractByContractNo(contractNo, userPositionInfoPO, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    public void test() throws Exception {
        InputStream in =  Config.getConfigDefaultFileStream();

        Config.getConnection();
        System.out.println(in);
    }

    /**
     * 开启富友 PC 订单扫描和变更任务
     *
     * 目的：扫描某一时间段（通常为今天）的未支付订单，查询在富友端是否已成功支付，
     * 修改我方已支付但响应迟缓的订单状态
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月25日
     *
     * @throws Exception
     */
    public void fuiouPCOrderScanAndUpdateTask() throws Exception {
        TimeRunner.init ();
        TimeRunner.addTask ( new FuiouOrderQueryBatchTask() );
    }

    public void customerMergeTask() throws Exception {

        try {

            String customerPersonalNumber1 = CommandExecutor.getConsoleInput("第一个客户号（PersonalNumber1）？");
            String customerPersonalNumber2 = CommandExecutor.getConsoleInput("第二个客户号（PersonalNumber2）？");


            Connection conn = Config.getConnection();
            try {

                MergeTask mergeTask = new MergeTask();
                mergeTask.mergeCustomer(customerPersonalNumber1, customerPersonalNumber2, conn);

            }
            catch (Exception e) {
                throw e;
            }
            finally {
                Database.close(conn);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void executePaymentPlan() {

        try {

            String paymentDate = CommandExecutor.getConsoleInput("需要兑付的日期？");


            if (StringUtils.isEmpty(paymentDate)) {

                paymentDate = TimeUtils.getNowDate();

                System.out.println("输入的时间为空，默认是当前日期：" + paymentDate);
            }

            CommandExecutor.checkIfContinue("兑付的日期是："+paymentDate);

            Connection conn = Config.getConnection();
            try {

                // 查询对应的兑付计划
                String sql = "select * from core_paymentplan p where 1=1 and p.state = 0 and p.status=? and p.PaymentTime=?";
                DatabaseSQL dbSQL = new DatabaseSQL();
                dbSQL.newSQL(sql);
                dbSQL.addParameter(1, PaymentPlanStatus.AuditSuccess).addParameter(2, paymentDate);
                List<PaymentPlanPO> paymentPlanPOs = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), PaymentPlanPO.class, new ArrayList<KVObject>(), conn);

                PaymentPlanService paymentPlanService = Config.getBeanByName("paymentPlanService", PaymentPlanService.class);

                for (int i = 0; paymentPlanPOs != null && i < paymentPlanPOs.size(); i++) {
                    PaymentPlanPO paymentPlanPO = paymentPlanPOs.get(i);


                    StringBuffer sbSQL = new StringBuffer();
                    sbSQL.append(" SELECT");
                    sbSQL.append("     customer. NAME '客户名称',");
                    sbSQL.append("     product. NAME '产品名称',");
                    sbSQL.append("     payment.id '兑付计划编号',");
                    sbSQL.append("     payment.PaymentTime '兑付时间',");
                    sbSQL.append("     payment.totalPaymentMoney '应兑付总金额'");
                    sbSQL.append(" FROM");
                    sbSQL.append("     Core_PaymentPlan payment,");
                    sbSQL.append("     v_customer_saleman customer,");
                    sbSQL.append("     crm_production product,");
                    sbSQL.append("     crm_order orders");
                    sbSQL.append(" WHERE");
                    sbSQL.append("     1 = 1");
                    sbSQL.append(" AND payment.state = 0");
                    sbSQL.append(" AND customer.state = 0");
                    sbSQL.append(" AND product.state = 0");
                    sbSQL.append(" AND orders.state = 0");
                    sbSQL.append(" AND payment.OrderId = orders.id");
                    sbSQL.append(" AND product.id = payment.ProductId");
                    sbSQL.append(" AND payment.CustomerId = customer.id");
                    sbSQL.append(" AND payment.id = '"+paymentPlanPO.getId()+"'");

                    List<Map<String, Object>> datas = MySQLDao.query(sbSQL.toString());

                    String format = "%10s\t%20s\t%20s\t%30s\n";
                    System.out.format(format, ("客户名称"), ("产品名称"), ("应兑付总金额"), ("兑付时间"));
                    for (Map<String, Object> data : datas) {

                        System.out.format(format, data.get("客户名称"), data.get("产品名称"), data.get("应兑付总金额"), data.get("兑付时间"));
                        System.out.println();

                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        System.out.print("Press YES to continue: ");

                        String command = br.readLine();

                        String log = "执行编号为【"+data.get("兑付计划编号")+"】的兑付计划" + "【"+data.get("客户名称")+"】【"+data.get("产品名称")+"】【"+data.get("应兑付总金额")+"】【"+data.get("兑付时间")+"】";

                        if (command.equals("YES")) {

                            paymentPlanService.doPayment(paymentPlanPO.getId(), conn);
                            logService.save("兑付日志", "执行本条兑付计划",  log, conn);
                        }
                        else {
                            System.out.println("本条兑付计划未执行");
                            logService.save("兑付日志", "本条兑付计划未执行", "取消" + log, conn);
                        }
                        System.out.println();
                    }
                }
            }
            catch (MyException me) {
                logService.save("兑付日志", me.getPeopleMessage(), me.getMachineMessage());
                throw me;
            }
            catch (Exception e) {
                throw e;
            }
            finally {
                Database.close(conn);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 手动创建并支付订单
     * @throws Exception
     */
    private void orderBuildAndPayManually() throws Exception {

        BufferedReader brCommand = null;
        List<KVObject> parameters = new ArrayList<KVObject>();


        System.out.println("开始执行【手动创建并支付订单】");




        brCommand = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入客户编号: ");
        String customerId = brCommand.readLine();
        parameters.add(new KVObject("客户编号", customerId));
        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }


        /**
         * 查询客户信息
         */
        CustomerPersonalPO customer = new CustomerPersonalPO();
        customer.setId(customerId);
        customer.setState(Config.STATE_CURRENT);
        customer = MySQLDao.load(customer, CustomerPersonalPO.class);
        System.out.println("客户名称：" + customer.getName() + " 手机：" + customer.getMobile());




        brCommand = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入产品编号: ");
        String productionId = brCommand.readLine();
        parameters.add(new KVObject("产品编号", productionId));

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得产品编号").throwException();
        }


        /**
         * 查询产品信息
         */
        ProductionPO production = new ProductionPO();
        production.setId(productionId);
        production.setState(Config.STATE_CURRENT);
        production = MySQLDao.load(production, ProductionPO.class);
        System.out.println("产品名称：" + production.getName());




        brCommand = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入投资金额: ");
        String strMoney = brCommand.readLine();
        parameters.add(new KVObject("投资金额", strMoney));

        if (StringUtils.isEmpty(strMoney)) {
            MyException.newInstance("无法获投资金额").throwException();
        }

        double money = Double.parseDouble(strMoney);
        if (money < 5000) {
            MyException.newInstance("投资金额有误").throwException();
        }






        brCommand = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入订单创建时间: ");
        String createTime = brCommand.readLine();
        if (StringUtils.isEmpty(createTime)) {
            createTime = TimeUtils.getNow();
        }
        parameters.add(new KVObject("订单创建时间", createTime));






        brCommand = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入订单支付时间: ");
        String payTime = brCommand.readLine();
        if (StringUtils.isEmpty(payTime)) {
            payTime = TimeUtils.getNow();
        }
        parameters.add(new KVObject("订单支付时间", payTime));






        brCommand = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入推荐人: ");
        String referralCode = brCommand.readLine();
        if (StringUtils.isEmpty(referralCode)) {
            referralCode = "";
        }
        parameters.add(new KVObject("推荐人", referralCode));



        brCommand = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入YES确认继续: ");
        String confirm = brCommand.readLine();

        if (StringUtils.isEmpty(confirm) || !confirm.equals("YES")) {
            System.out.println("指令结束");
            return;
        }


        // 保存请求日志
        String token = IdUtils.getUUID32();
        logService.save("兑付日志", "手动生成并支付订单【" + token + "】", getParametersValue(parameters));





        /**
         *
         * 执行业务
         *
         */


        Connection conn = Config.getConnection();
        try {

            String now = TimeUtils.getNow();
            OrderPO orderPO = orderService.appointmentOrder("", customerId, productionId, null, money, null, true, now, null, createTime, referralCode, OrderStatus.Appointment, null, null, null, conn);

            System.out.println("订单编号" + orderPO.getId());

            System.out.println("开始销售订单");

            String valueDate = TimeUtils.getTime(payTime, 1, TimeUtils.DATE);

            orderPO.setCreateTime(createTime);
            orderPO.setPayTime(payTime);
            orderPO.setValueDate(valueDate);

            orderService.saleOrder(orderPO, customerId, conn);
            System.out.println("结束销售订单");


            // 保存订单请求日志
            logService.save("兑付日志","手动生成并支付订单【"+token+"】", "订单编号【"+orderPO.getId()+"】");

        }
        catch (Exception e) {

            logService.save("兑付日志","手动生成并支付订单【"+token+"】失败", "");


            throw e;
        }
        finally {
            Database.close(conn);
        }

    }



    public void setOrderMoneyTransferStatus() throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select * from crm_order o where 1=1 and o.`Status`=1  and o.ProductionId in (");
        sbSQL.append(" 	select id from crm_production where state=0 and productHomeId in (");
        sbSQL.append(" 		select id from crm_productionhome where State=0 and ProjectId in (");
        sbSQL.append(" 			select id from crm_project where state=0 and `Name`='网站项目'");
        sbSQL.append(" 		)");
        sbSQL.append(" 	)");
        sbSQL.append(" ) ");
        sbSQL.append(" and o.MoneyTransferStatus=1 and o.MoneyTransferTime is not null and o.MoneyTransferTime!=''");

        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sbSQL.toString());

        Connection conn = Config.getConnection();
        try {
            conn.setAutoCommit(false);
            List<OrderPO> list = MySQLDao.search(dbSQL, OrderPO.class, conn);

            for (OrderPO transferedOrder : list) {
                OrderPO opOrder = orderDao.loadByOrderId(transferedOrder.getId(), conn);

                opOrder.setMoneyTransferStatus(transferedOrder.getMoneyTransferStatus());
                opOrder.setMoneyTransferTime(transferedOrder.getMoneyTransferTime());

                MySQLDao.insertOrUpdate(opOrder, conn);
            }


            conn.rollback();
        }
        catch (Exception e) {
            conn.rollback();
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }
}
