package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.*;
import com.youngbook.annotation.Permission;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerScorePO;
import com.youngbook.entity.po.jeasyui.DataGridColumnPO;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import com.youngbook.entity.po.sale.PaymentPlanStatus;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.entity.vo.customer.CustomerMoneyLogVO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.service.customer.CustomerMoneyLogService;
import com.youngbook.service.customer.CustomerScoreService;
import com.youngbook.service.report.SimpleReportService;
import com.youngbook.service.sale.PaymentPlanService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 兑付计划Action
 * Created by Administrator on 2015/3/25.
 */
public class PaymentPlanAction extends BaseAction {

    //兑付计划对象
    private PaymentPlanPO paymentPlan = new PaymentPlanPO();
    private PaymentPlanVO paymentPlanVO = new PaymentPlanVO();
    //对付计划Service对象

    @Autowired
    PaymentPlanService paymentPlanService;

    @Autowired
    CustomerMoneyLogService customerMoneyLogService;

    @Autowired
    CustomerScoreService customerScoreService;


    public String exportReportMonth() throws Exception {

        String paymentTimeMonth = getHttpRequestParameter("paymentTimeMonth");

        String fileName = Config.getSystemConfig("reportPaymentPlanMonth_template_name");
        fileName = fileName.replaceAll("reportPaymentPlanMonth_template_name", paymentTimeMonth);

        //转换输出格式防止乱码
        fileName = new String(fileName.getBytes("utf8"), "iso8859-1");

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/octet-stream");

        //设置Excel导出文件名称
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        //获取输出流
        ServletOutputStream out = response.getOutputStream();


        try {
            FileInputStream fileInputStream = new FileInputStream(Config.getSystemConfig("reportPaymentPlanMonth_template"));
            HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);

            String sheetName = "Sheet1";
            HSSFSheet sheet = wb.getSheet(sheetName);
            HSSFSheet sheetStyle = wb.getSheet("Template");
            HSSFRow templateRow = ExcelUtils.getRow(1, sheetStyle);

            // ExcelUtils.addMergedRegion("a1", "j1", sheet);
            ExcelUtils.setCellValue("a1", paymentTimeMonth + "兑付明细表", sheet);


            List<PaymentPlanVO> plans = paymentPlanService.getListPaymentPlanVO(paymentTimeMonth, getConnection());

            int offset = 3;
            int weekOfYear = -1;
            List<PaymentPlanVO> plansOfWeek = new ArrayList<PaymentPlanVO>();


            // 合计变量
            double allTotalProfitMoney = 0;
            double allTotalPrincipalMoney = 0;
            double allTotalMoney = 0;

            for (int i = 0; plans != null && i < plans.size(); i++) {
                PaymentPlanVO paymentPlanVO = plans.get(i);

                Date datePaymentPlanTime = TimeUtils.getDate(paymentPlanVO.getPaymentTime());
                int currentWeekOfYear = TimeUtils.getWeekOfYear(datePaymentPlanTime);
                if (weekOfYear != currentWeekOfYear) {

                    // 打印小计内容
                    if (plansOfWeek != null && plansOfWeek.size() > 0) {
                        double profitMoneyTemp = 0;
                        double principalMoneyTemp = 0;
                        double moneyTemp = 0;
                        for (int j = 0; j < plansOfWeek.size(); j++) {
                            PaymentPlanVO tempPlan = plansOfWeek.get(j);
                            profitMoneyTemp += tempPlan.getTotalProfitMoney();
                            principalMoneyTemp += tempPlan.getTotalPaymentPrincipalMoney();
                            moneyTemp += tempPlan.getTotalPaymentMoney();


                            allTotalProfitMoney += tempPlan.getTotalProfitMoney();
                            allTotalPrincipalMoney += tempPlan.getTotalPaymentPrincipalMoney();
                            allTotalMoney += tempPlan.getTotalPaymentMoney();
                        }

                        ExcelUtils.newRow(sheet, offset + i, templateRow);
                        ExcelUtils.setCellValue("a" + (offset + i), "小计", sheet);
                        ExcelUtils.setCellValue("h" + (offset + i), principalMoneyTemp, sheet);
                        ExcelUtils.setCellValue("i" + (offset + i), profitMoneyTemp, sheet);
                        ExcelUtils.setCellValue("j" + (offset + i), moneyTemp, sheet);

                        plansOfWeek = new ArrayList<PaymentPlanVO>();

                        offset++;
                    }
                    weekOfYear = TimeUtils.getWeekOfYear(datePaymentPlanTime);
                }
                plansOfWeek.add(paymentPlanVO);


                ExcelUtils.setCellValue("a" + (offset + i), paymentPlanVO.getCustomerName(), sheet);
                ExcelUtils.setCellValue("b" + (offset + i), paymentPlanVO.getProductionName(), sheet);
                ExcelUtils.setCellValue("c" + (offset + i), paymentPlanVO.getPayTime(), sheet);
                ExcelUtils.setCellValue("d" + (offset + i), paymentPlanVO.getMoney(), sheet);
                ExcelUtils.setCellValue("e" + (offset + i), paymentPlanVO.getInterestDescription(), sheet);
                ExcelUtils.setCellValue("f" + (offset + i), paymentPlanVO.getPaymentTime(), sheet);
                ExcelUtils.setCellValue("g" + (offset + i), paymentPlanVO.getWeekOfDay(), sheet);
                ExcelUtils.setCellValue("h" + (offset + i), paymentPlanVO.getTotalPaymentPrincipalMoney(), sheet);
                ExcelUtils.setCellValue("i" + (offset + i), paymentPlanVO.getTotalProfitMoney(), sheet);
                ExcelUtils.setCellValue("j" + (offset + i), paymentPlanVO.getTotalPaymentPrincipalMoney() + paymentPlanVO.getTotalProfitMoney(), sheet);


                // 打印最后一笔小计
                // 打印小计内容
                if (i == (plans.size() - 1) && plansOfWeek != null && plansOfWeek.size() > 0) {

                    offset++;

                    double profitMoneyTemp = 0;
                    double principalMoneyTemp = 0;
                    double moneyTemp = 0;
                    for (int j = 0; j < plansOfWeek.size(); j++) {
                        PaymentPlanVO tempPlan = plansOfWeek.get(j);
                        profitMoneyTemp += tempPlan.getTotalProfitMoney();
                        principalMoneyTemp += tempPlan.getTotalPaymentPrincipalMoney();
                        moneyTemp += tempPlan.getTotalPaymentMoney();

                        allTotalProfitMoney += tempPlan.getTotalProfitMoney();
                        allTotalPrincipalMoney += tempPlan.getTotalPaymentPrincipalMoney();
                        allTotalMoney += tempPlan.getTotalPaymentMoney();
                    }

                    ExcelUtils.newRow(sheet, offset + i, templateRow);

                    ExcelUtils.setCellValue("a" + (offset + i), "小计", sheet);
                    ExcelUtils.setCellValue("h" + (offset + i), principalMoneyTemp, sheet);
                    ExcelUtils.setCellValue("i" + (offset + i), profitMoneyTemp, sheet);
                    ExcelUtils.setCellValue("j" + (offset + i), moneyTemp, sheet);
                    plansOfWeek = new ArrayList<PaymentPlanVO>();


                    // 打印合计内容
                    offset++;
                    ExcelUtils.newRow(sheet, offset + i, templateRow);

                    ExcelUtils.setCellValue("a" + (offset + i), "合计", sheet);
                    ExcelUtils.setCellValue("h" + (offset + i), allTotalPrincipalMoney, sheet);
                    ExcelUtils.setCellValue("i" + (offset + i), allTotalProfitMoney, sheet);
                    ExcelUtils.setCellValue("j" + (offset + i), allTotalMoney, sheet);


//                    HSSFCellStyle curStyle = ExcelUtils.getCell("a" + (offset + i), sheet).getCellStyle();
//                    HSSFFont font= wb.createFont();
//                    font.setBold(true);
//                    curStyle.setFont(font);
//                    ExcelUtils.getCell("h" + (offset + i), sheet).setCellStyle(curStyle);
                }



            }

            ExcelUtils.removeSheetsExcept(wb, sheetName);
            wb.write(out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            out.flush();
            out.close();
        }

        return SUCCESS;
    }

    public String reportMonth() throws Exception {

        String paymentTime = getHttpRequestParameter("paymentTime");

        List<PaymentPlanVO> paymentPlanVOs = paymentPlanService.getListPaymentPlanVO(paymentTime, getConnection());

        getResult().setReturnValue(paymentPlanVOs);

        return SUCCESS;
    }

    @Permission(require = "兑付计划_兑付计划修改")
    public String savePaymentPlan() throws Exception {
         MySQLDao.insertOrUpdate(paymentPlan, getLoginUser().getId(), getConnection());
        return SUCCESS;
    }

    @Permission(require = "兑付计划_兑付计划修改")
    public String doPaymnetDone() throws Exception {

        String paymentPlanId = getHttpRequestParameter("paymentPlanId");


        PaymentPlanPO paymentPlanPO = paymentPlanService.loadPaymentPlanPO(paymentPlanId, getConnection());

        if (paymentPlanPO != null) {
            paymentPlanService.doPaymnetDone(paymentPlanPO, getLoginUser().getId(), getConnection());
            getResult().setReturnValue("1");
        }

        return SUCCESS;
    }

    public String getPaymentPlansByOrderId() throws Exception {
        String orderId = getHttpRequestParameter("orderId");
        List<PaymentPlanPO> list = paymentPlanService.getPaymentPlansByOrderId(orderId, getConnection());

        Pager pager = Pager.getInstance(list);

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    public String listPaymentPlanVOByOrderId() throws Exception {
        String orderId = getHttpRequestParameter("orderId");
        List<PaymentPlanVO> list = paymentPlanService.listPaymentPlanVOByOrderId(orderId, getConnection());

        Pager pager = Pager.getInstance(list);

        /**
         * 构造汇总
         */
        KVObjects footerDatas = new KVObjects();
        double totalProfitMoney = 0;
        double totalPaymentMoney = 0;
        double totalPaymentPrincipalMoney = 0;

        for (int i = 0; list != null && i < list.size(); i++) {
            PaymentPlanVO paymentPlanVO = list.get(i);

            totalPaymentMoney += paymentPlanVO.getTotalPaymentMoney();
            totalProfitMoney += paymentPlanVO.getTotalProfitMoney();
            totalPaymentPrincipalMoney += paymentPlanVO.getTotalPaymentPrincipalMoney();
        }


        footerDatas.addItem("totalPaymentMoney", totalPaymentMoney);
        footerDatas.addItem("totalProfitMoney", totalProfitMoney);
        footerDatas.addItem("totalPaymentPrincipalMoney", totalPaymentPrincipalMoney);
        footerDatas.addItem("currentInstallment", "小计");

        pager.getFooter().add(footerDatas);

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    public String getCustomerPaymentPlanInfo() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        PaymentPlanVO paymentPlanVO = paymentPlanService.getCustomerPaymentPlanInfo(customerId, getConnection());

        paymentPlanVO.setTotalPaymentPrincipalMoneyFormatted(MoneyUtils.format2String(paymentPlanVO.getTotalPaymentPrincipalMoney()));
        paymentPlanVO.setTotalProfitMoneyFormatted(MoneyUtils.format2String(paymentPlanVO.getTotalProfitMoney()));
        paymentPlanVO.setTotalPaymentMoneyFormatted(MoneyUtils.format2String(paymentPlanVO.getTotalPaymentMoney()));

        getResult().setReturnValue(paymentPlanVO);

        return SUCCESS;
    }


    public String getListPaymentPlanVOByOrderId() throws Exception {
        String orderId = getHttpRequestParameter("orderId");
        List<PaymentPlanVO> list = paymentPlanService.listPaymentPlanVOByOrderId(orderId, getConnection());

        for (int i = 0; list != null && i < list.size(); i++) {
            PaymentPlanVO  paymentPlanVO = list.get(i);
            double totalMoney = 0;

            if (paymentPlanVO.getTotalPaymentPrincipalMoney() != Double.MAX_VALUE) {
                totalMoney += paymentPlanVO.getTotalPaymentPrincipalMoney();
            }

            if (paymentPlanVO.getTotalProfitMoney() != Double.MAX_VALUE) {
                totalMoney += paymentPlanVO.getTotalProfitMoney();
            }

            paymentPlanVO.setTotalPaymentMoneyFormatted(MoneyUtils.format2String(totalMoney));


            String paymentTime = TimeUtils.format(paymentPlanVO.getPaymentTime(), TimeUtils.Format_YYYY_MM_DD_HH_M_S, TimeUtils.Format_YYYY_MM_DD);
            paymentPlanVO.setPaymentTime(paymentTime);

        }

        getResult().setReturnValue(list);

        return SUCCESS;
    }

    /**
     * 获取单个查询对象
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        //设置状态
        paymentPlan.setState(Config.STATE_CURRENT);
        //获取茶查询的对象
        paymentPlan = MySQLDao.load(paymentPlan, PaymentPlanPO.class);
        getResult().setReturnValue(paymentPlan.toJsonObject4Form());
        return SUCCESS;
    }

    @Permission(require = "兑付计划_兑付计划申请")
    public String check() throws Exception {

        String paymentPlanDate = getHttpRequestParameter("paymentPlanDate");

        paymentPlanService.check(paymentPlanDate, getLoginUser(), getConnection());

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    @Permission(require = "兑付计划_兑付计划审核")
    public String check2() throws Exception {

        String paymentPlanDate = getHttpRequestParameter("paymentPlanDate");

        paymentPlanService.check2(paymentPlanDate, getLoginUser(), getConnection());

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    /**
     * 修改兑付计划状态为等待审核
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-11-3
     *
     * @author 邓超
     * @date 2015-11-3
     *
     * @return
     * @throws Exception
     */
    public String updateAuditor() throws Exception {
        Connection conn = getConnection();

        paymentPlan.setState(Config.STATE_CURRENT);
        PaymentPlanPO po = MySQLDao.load(paymentPlan, PaymentPlanPO.class);

        // 如果没有查询到数据，直接返回
        if(po == null) {
            getResult().setReturnValue(0);
            return SUCCESS;
        }

        // 如果兑付计划的已经兑付，不能重复审核，直接返回
        if(po.getStatus() == PaymentPlanStatus.Paid) {
            getResult().setReturnValue(1);
            return SUCCESS;
        }

        // 修改对付计划的状态为等待审核
        po.setStatus(PaymentPlanStatus.Auditing);
        po.setConfirmorId(getLoginUser().getId());
        Integer count = MySQLDao.insertOrUpdate(po, getLoginUser().getId(), conn);

        if(count != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据库异常").throwException();
        }

        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String updateAuditExecuteStatus() throws Exception {
        HttpServletRequest request = this.getRequest();
        Connection conn = getConnection();

        String status = request.getParameter("status");
        if(status == null || "".equals(status)) {
            getResult().setReturnValue(2);
            return SUCCESS;
        }
        int statusInt = Integer.parseInt(status);

        paymentPlan.setState(Config.STATE_CURRENT);
        //paymentPlan.setStatus(Config.getSystemVariableAsInt("paymentPlan.status.Audit.wait"));
        PaymentPlanPO po = MySQLDao.load(paymentPlan, PaymentPlanPO.class);

        // 如果没有查询到数据，直接返回
        if(po == null) {
            getResult().setReturnValue(0);
            return SUCCESS;
        }

        // 如果兑付计划的状态不是审核成功或审核失败，不能被审批，直接返回
        int statusSuccess = PaymentPlanStatus.AuditSuccess;
        int statusFailed = PaymentPlanStatus.AuditFailure;
        if(statusInt != statusSuccess && statusInt != statusFailed) {
            getResult().setReturnValue(1);
            return SUCCESS;
        }

        // 修改对付计划的状态为对应状态
        po.setStatus(statusInt);
        po.setAuditExecutorId(getLoginUser().getId());
        MySQLDao.insertOrUpdate(po, getLoginUser().getId(), conn);
        return SUCCESS;
    }

    @Permission(require = "订单管理_兑付计划确认")
    public String confirm() throws Exception {

        String orderId = getHttpRequestParameter("orderId");

        String userId = Config.getLoginUserInSession(getRequest()).getId();

        paymentPlanService.confirm(orderId, userId, getConnection());

        getResult().setReturnValue("1");

        return SUCCESS;
    }


    /**
     * 获取列表数据
     *
     * @return 返回
     * @throws Exception
     */
    public String getList2() throws Exception {

        Connection conn = getConnection();
        //根据条件查询数据
        // List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, PaymentPlanVO.class);
        String searchPaymentTime = HttpUtils.getParameter(getRequest(), "paymentPlanVO_paymentTime");

        String searchPaymentTimeEnd = null;

        if (StringUtils.isEmpty(searchPaymentTime)) {
            searchPaymentTime = TimeUtils.getNowMonth();
            searchPaymentTimeEnd = TimeUtils.getTime(searchPaymentTime + "-01 00:00:00", 1, TimeUtils.MONTH);
        }
        else {
            searchPaymentTimeEnd = TimeUtils.getTime(searchPaymentTime + "-01 00:00:00", 1, TimeUtils.MONTH);
        }



        //将数据封装起来 到一个分页的类里面
        Pager pager = paymentPlanService.getList2(paymentPlanVO, searchPaymentTime, searchPaymentTimeEnd, conn);

        KVObjects footerDatas = new KVObjects();
        double totalProfitMoney = 0;
        double totalPrincipalMoney = 0;
        double totalPaidProfitMoney = 0;
        double totalPaidPrincipalMoney = 0;

        //设置状态中文到statusName
        List<IJsonable> data = pager.getData();
        for(int i = 0; i < data.size(); i ++) {
            PaymentPlanVO vo = (PaymentPlanVO)data.get(i);
            String statusName = PaymentPlanStatus.getStatusName(vo.getStatus());
            vo.setStatusName(statusName);

            totalProfitMoney += vo.getTotalProfitMoney();
            totalPrincipalMoney += vo.getTotalPaymentPrincipalMoney();
            totalPaidProfitMoney += vo.getPaiedProfitMoney();
            totalPaidPrincipalMoney += vo.getPaiedPrincipalMoney();
        }

        footerDatas.addItem("totalPaymentPrincipalMoney", totalPrincipalMoney);
        footerDatas.addItem("totalProfitMoney", totalProfitMoney);
        footerDatas.addItem("paiedPrincipalMoney", totalPaidPrincipalMoney);
        footerDatas.addItem("paiedProfitMoney", totalPaidProfitMoney);
        pager.getFooter().add(footerDatas);


        //将数据以JSON的方式返回到脚本哪里
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String list() throws Exception {

        //将数据封装起来 到一个分页的类里面
        Pager pager = Pager.getInstance(getRequest());
        pager = paymentPlanService.listPagerPaymentPlanVO(paymentPlanVO, null, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());

        //设置状态中文到statusName
        List<IJsonable> data = pager.getData();
        for(int i = 0; i < data.size(); i ++) {
            PaymentPlanVO vo = (PaymentPlanVO)data.get(i);
            String statusName = PaymentPlanStatus.getStatusName(vo.getStatus());
            vo.setStatusName(statusName);

            String bankNumber = vo.getBankNumber();
            if (!StringUtils.isEmpty(bankNumber)) {
                bankNumber = AesEncrypt.decrypt(bankNumber);
            }
            vo.setBankNumber(bankNumber);
        }
        //将数据以JSON的方式返回到脚本哪里
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    /**
     * 获取状态为等待审核的数据
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-11-03
     *
     * @author 邓超
     * @date 2015-11-03
     *
     * @return 返回
     * @throws Exception
     */
    public String list4AuditSubmitted() throws Exception {
        HttpServletRequest request = getRequest();

        //根据条件查询数据
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, PaymentPlanVO.class);
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "sid " + Database.ORDERBY_DESC));

        //将数据封装起来 到一个分页的类里面
        paymentPlanVO.setStatus(PaymentPlanStatus.Auditing);
        Pager pager = Pager.getInstance(getRequest());
        pager = paymentPlanService.listPagerPaymentPlanVO(paymentPlanVO, conditions, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());

        //设置状态中文到statusName
        List<IJsonable> data = pager.getData();
        for(int i = 0; i < data.size(); i ++) {
            PaymentPlanVO vo = (PaymentPlanVO)data.get(i);
            String statusName = PaymentPlanStatus.getStatusName(vo.getStatus());
            vo.setStatusName(statusName);
        }

        //将数据以JSON的方式返回到脚本哪里
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    /**
     * 创建人：张舜清
     * 时间：2015年9月15日14:47:18
     * 内容：根据兑付的客户id查询对应数据列表
     *
     * @return
     * @throws Exception
     */
    public String customerPersonalQuery() throws Exception {
        HttpServletRequest request = getRequest();
        String customerId = request.getParameter("customerId");
        Connection conn = getConnection();
        CustomerPersonalVO customerPersonalVO = new CustomerPersonalVO();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = paymentPlanService.customerPersonalQuery(customerPersonalVO, customerId, conditions, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月15日17:37:39
     * 内容：根据兑付的订单id查询对应订单数据列表
     *
     * @return
     * @throws Exception
     */
    public String orderQuery() throws Exception {
        HttpServletRequest request = getRequest();
        String orderId = request.getParameter("orderId");
        Connection conn = getConnection();
        OrderVO orderVO = new OrderVO();
        Pager pager = paymentPlanService.orderQuery(orderVO, orderId, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月15日17:53:08
     * 内容：根据兑付产品的id查询出对应数据列表
     *
     * @return
     * @throws Exception
     */
    public String productionQuery() throws Exception {
        HttpServletRequest request = getRequest();
        String productionId = request.getParameter("productionId");
        Connection conn = getConnection();
        ProductionVO productionVO = new ProductionVO();
        Pager pager = paymentPlanService.productionQuery(productionVO, productionId, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月15日18:08:15
     * 内容：根据兑付的客户id查询出对应数据列表
     *
     * @return
     * @throws Exception
     */
    public String customerMoneyLogQuery() throws Exception {
        HttpServletRequest request = getRequest();
        String customerId = request.getParameter("customerId");
        Connection conn = getConnection();

        Pager pager = customerMoneyLogService.customerMoneyLogQuery(customerId, request, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 执行兑付一条对付计划
     * <p/>
     * 将传入指定 ID 的兑付计划进行兑付
     * 修改客户的资金记录
     * 新增客户资金日志
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-7-19
     *
     * @return String
     * @throws Exception
     * @author 邓超
     */
    public String payment() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");

        if (StringUtils.isEmpty(id)) {
            MyException.newInstance("获取参数失败，请检查").throwException();
        }

        int backCode = paymentPlanService.payment(id, getLoginUser(), getConnection());


        //如果返回1.表示客户没有绑定银行卡
        if (backCode == 1) {
            getResult().setReturnValue("1");
        }
        return SUCCESS;
    }


    /**
     * 获取添加工作流编号id
     * @return
     * @throws Exception
     */
    public String getInssertApplayWorlkflowId()throws Exception{
        //判断是否有产品编号
        if(StringUtils.isEmpty(paymentPlanVO.getProductId())){
            getResult().setMessage("产品不能为空，请联系管理员");
            return SUCCESS;
        }
        //判断是否有兑付时间
        if(StringUtils.isEmpty(paymentPlanVO.getPaymentTime())){
            getResult().setMessage("兑付时间不能为空，请联系管理员");
            return SUCCESS;
        }

        String id = paymentPlanService.getInssertApplayWorlkflowId(paymentPlanVO, getLoginUser(), getConnection());
        //判断添加数据是否成功
        if (StringUtils.isEmpty(id)) {
            MyException.newInstance("添加产品兑付工作流失败");
        }
        getResult().setReturnValue(new JSONObject().element("id",id));
        return SUCCESS;
    }


    /**
     * 根据兑付计划的获取兑付计划审批数据
     *
     * @return
     * @throws Exception
     */
    public String getPaymentPlanYW() throws Exception {
        //判断id是否为null
        if (StringUtils.isEmpty(paymentPlan.getId())) {
            MyException.newInstance("兑付审核数据错误，请检查").throwException();
        }
        paymentPlanVO = paymentPlanService.getPaymentPlanYW(paymentPlan.getId(), getLoginUser(), getConnection());

        getResult().setReturnValue(paymentPlanVO);
        return SUCCESS;
    }

    /**
     * 调教申请数据
     *
     * @return
     * @throws Exception
     */
    public String setPaymentPlanYW() throws Exception {
        //判断id是否为null
        if (StringUtils.isEmpty(paymentPlan.getId())) {
            MyException.newInstance("兑付审核数据错误，请检查").throwException();
        }
        //设置申请状态
        PaymentPlanVO paymentPlanVO = paymentPlanService.setPaymentPlanYW(paymentPlan.getId(), getLoginUser(), getConnection());

        //判断是否申请成功
        if (paymentPlanVO != null) {
            getResult().setReturnValue(paymentPlanVO);
        } else {
            getResult().setReturnValue("false");
        }

        return SUCCESS;

    }


    public String loadPaymentData() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String productionId = request.getParameter("productionId");
        Pager pager = Pager.getInstance(getRequest());
        pager = paymentPlanService.listPagePaymentInfo(paymentPlanVO, productionId, pager.getCurrentPage(), pager.getShowRowCount() , getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取兑付计划excel表格
     * @throws Exception
     */
    public void getPaymentExcel() throws  Exception{
        paymentPlanService.getPaymentPlanExcel("2010-1-1", "2099-1-1", getConnection());
    }
    public PaymentPlanPO getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(PaymentPlanPO paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public PaymentPlanVO getPaymentPlanVO() {
        return paymentPlanVO;
    }

    public void setPaymentPlanVO(PaymentPlanVO paymentPlanVO) {
        this.paymentPlanVO = paymentPlanVO;
    }
}
