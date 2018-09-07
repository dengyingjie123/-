package com.youngbook.service.report;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.vo.report.CustomerDailyPaymentReportVO;
import com.youngbook.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by 张舜清 on 2015/9/8.
 */
public class CustomerDailyPaymentReportService extends BaseService {
    /**
     * 创建人：张舜清
     * 时间：2015年9月7日15:11:21
     * 内容：查询出数据
     *
     * @param customerDailyPaymentReportVO
     * @param conditions
     * @param request
     * @return
     * @throws Exception
     */
    public Pager list(CustomerDailyPaymentReportVO customerDailyPaymentReportVO, List<KVObject> conditions, String time, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append("SELECT ");
        sqlDB.append("    ( ");
        sqlDB.append("       SELECT ");
        sqlDB.append("          SUM( ");
        sqlDB.append("             round(pl.totalProfitMoney, 2) ");
        sqlDB.append("          ) ");
        sqlDB.append("       FROM ");
        sqlDB.append("          core_paymentplan pl, ");
        sqlDB.append("          crm_customerpersonal cu ");
        sqlDB.append("       WHERE ");
        sqlDB.append("          1 = 1 ");
        sqlDB.append("       AND pl.state = 0 ");
        sqlDB.append("       AND pl.`Status` = 958 ");
        sqlDB.append("       AND cu.state = 0 ");
        sqlDB.append("       AND pl.customerId = cu.id ");
        //默认查询出所有未兑付的数据，如果参数日期不为空，则传入查询
        if (!StringUtils.isEmpty(time)){
            sqlDB.append("       AND pl.paymentTime = '" + Database.encodeSQL(time) + "' ");
        }
        sqlDB.append("    ) AS 'sumTotalProfitMoney', ");
        sqlDB.append("    ( ");
        sqlDB.append("       SELECT ");
        sqlDB.append("          SUM( ");
        sqlDB.append("             round(pl.totalPaymentMoney, 2) ");
        sqlDB.append("          ) ");
        sqlDB.append("       FROM ");
        sqlDB.append("          core_paymentplan pl, ");
        sqlDB.append("          crm_customerpersonal cu ");
        sqlDB.append("       WHERE ");
        sqlDB.append("          1 = 1 ");
        sqlDB.append("       AND pl.state = 0 ");
        sqlDB.append("       AND pl.`Status` = 958 ");
        sqlDB.append("       AND cu.state = 0 ");
        sqlDB.append("       AND pl.customerId = cu.id ");
        //默认查询出所有未兑付的数据，如果参数日期不为空，则传入查询
        if (!StringUtils.isEmpty(time)){
            sqlDB.append("       AND pl.paymentTime = '" + Database.encodeSQL(time) + "' ");
        }
        sqlDB.append("    ) AS 'sumTotalPaymentMoney', ");
        sqlDB.append("    ( ");
        sqlDB.append("       SELECT ");
        sqlDB.append("          SUM( ");
        sqlDB.append("             round( ");
        sqlDB.append("                pl.totalPaymentPrincipalMoney, ");
        sqlDB.append("                2 ");
        sqlDB.append("             ) ");
        sqlDB.append("          ) ");
        sqlDB.append("       FROM ");
        sqlDB.append("          core_paymentplan pl, ");
        sqlDB.append("          crm_customerpersonal cu ");
        sqlDB.append("       WHERE ");
        sqlDB.append("          1 = 1 ");
        sqlDB.append("       AND pl.state = 0 ");
        sqlDB.append("       AND pl.`Status` = 958 ");
        sqlDB.append("       AND cu.state = 0 ");
        sqlDB.append("       AND pl.customerId = cu.id ");
        //默认查询出所有未兑付的数据，如果参数日期不为空，则传入查询
        if (!StringUtils.isEmpty(time)){
            sqlDB.append("       AND pl.paymentTime = '" + Database.encodeSQL(time) + "' ");
        }
        sqlDB.append("    ) AS 'sumTotalPaymentPrincipalMoney', ");
        sqlDB.append("    customer.`Name` AS 'customerName', ");
        sqlDB.append("    STR_TO_DATE( ");
        sqlDB.append("       plan.PaymentTime, ");
        sqlDB.append("       '%Y-%m-%d' ");
        sqlDB.append("    ) AS PaymentTime, ");
        sqlDB.append("    round(plan.totalProfitMoney, 2) AS 'totalProfitMoney', ");
        sqlDB.append("    round(plan.totalPaymentMoney, 2) AS 'totalPaymentMoney', ");
        sqlDB.append("    round( ");
        sqlDB.append("       plan.totalPaymentPrincipalMoney, ");
        sqlDB.append("       2 ");
        sqlDB.append("    ) AS 'totalPaymentPrincipalMoney', ");
        sqlDB.append("    plan.currentInstallment AS 'currentInstallment', ");
        sqlDB.append("    plan.totalInstallment AS 'totalInstallment', ");
        sqlDB.append("    round( ");
        sqlDB.append("       ( ");
        sqlDB.append("          SELECT ");
        sqlDB.append("             sum(plan1.totalPaymentMoney) ");
        sqlDB.append("          FROM ");
        sqlDB.append("             core_paymentPlan plan1 ");
        sqlDB.append("          WHERE ");
        sqlDB.append("             1 = 1 ");
        sqlDB.append("          AND plan1.state = 0 ");
        sqlDB.append("          AND plan1.`Status` = 958 ");
        sqlDB.append("          AND plan1.customerId = customer.id ");
        sqlDB.append("       ) - plan.totalPaymentMoney, ");
        sqlDB.append("       2 ");
        sqlDB.append("    ) AS 'surplusPaymentMoney', ");
        sqlDB.append("    round( ");
        sqlDB.append("       ( ");
        sqlDB.append("          SELECT ");
        sqlDB.append("             sum( ");
        sqlDB.append("                plan2.totalPaymentPrincipalMoney ");
        sqlDB.append("             ) ");
        sqlDB.append("          FROM ");
        sqlDB.append("             core_paymentPlan plan2 ");
        sqlDB.append("          WHERE ");
        sqlDB.append("             plan2.state = 0 ");
        sqlDB.append("          AND plan2.`Status` = 958 ");
        sqlDB.append("          AND plan2.customerId = customer.id ");
        sqlDB.append("       ) - plan.totalPaymentPrincipalMoney, ");
        sqlDB.append("       2 ");
        sqlDB.append("    ) AS 'surplusPaymentPrincipalMoney', ");
        sqlDB.append("    plan.`Status` AS 'status' ");
        sqlDB.append(" FROM ");
        sqlDB.append("    core_paymentplan plan, ");
        sqlDB.append("    crm_customerpersonal customer ");
        sqlDB.append(" WHERE ");
        sqlDB.append("    1 = 1 ");
        sqlDB.append(" AND plan.state = 0 ");
        sqlDB.append(" AND plan.`Status` = 958 ");
        sqlDB.append(" AND customer.state = 0 ");
        sqlDB.append(" AND plan.customerId = customer.id ");
        //默认查询出所有未兑付的数据，如果参数日期不为空，则传入查询
        if (!StringUtils.isEmpty(time)){
            sqlDB.append("       AND plan.paymentTime = '" + Database.encodeSQL(time) + "' ");
        }

        Pager pager = Pager.query(sqlDB.toString(), customerDailyPaymentReportVO, conditions, request, queryType);

        return pager;
    }

    /**
     * 创建人：张舜清
     * 时间 ：2015年9月8日09:33:45
     * 内容：厚币通宝客户每日客户兑付金额打印报表查询service
     *
     * @param time
     * @param conn
     * @return
     * @throws Exception
     */
    public List<CustomerDailyPaymentReportVO> list(String time, Connection conn) throws Exception {
        if (time == null) {
            throw new Exception("打印参数传入错误");
        }
        if (time.length() != 10) {
            throw new Exception("打印参数格式错误");
        }
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append("SELECT ");
        sqlDB.append("    ( ");
        sqlDB.append("       SELECT ");
        sqlDB.append("          SUM( ");
        sqlDB.append("             round(pl.totalProfitMoney, 2) ");
        sqlDB.append("          ) ");
        sqlDB.append("       FROM ");
        sqlDB.append("          core_paymentplan pl, ");
        sqlDB.append("          crm_customerpersonal cu ");
        sqlDB.append("       WHERE ");
        sqlDB.append("          1 = 1 ");
        sqlDB.append("       AND pl.state = 0 ");
        sqlDB.append("       AND pl.`Status` = 958 ");
        sqlDB.append("       AND cu.state = 0 ");
        sqlDB.append("       AND pl.customerId = cu.id ");
        sqlDB.append("       AND pl.paymentTime = '" + time + "' ");
        sqlDB.append("    ) AS 'sumTotalProfitMoney', ");
        sqlDB.append("    ( ");
        sqlDB.append("       SELECT ");
        sqlDB.append("          SUM( ");
        sqlDB.append("             round(pl.totalPaymentMoney, 2) ");
        sqlDB.append("          ) ");
        sqlDB.append("       FROM ");
        sqlDB.append("          core_paymentplan pl, ");
        sqlDB.append("          crm_customerpersonal cu ");
        sqlDB.append("       WHERE ");
        sqlDB.append("          1 = 1 ");
        sqlDB.append("       AND pl.state = 0 ");
        sqlDB.append("       AND pl.`Status` = 958 ");
        sqlDB.append("       AND cu.state = 0 ");
        sqlDB.append("       AND pl.customerId = cu.id ");
        sqlDB.append("       AND pl.paymentTime = '" + time + "' ");
        sqlDB.append("    ) AS 'sumTotalPaymentMoney', ");
        sqlDB.append("    ( ");
        sqlDB.append("       SELECT ");
        sqlDB.append("          SUM( ");
        sqlDB.append("             round( ");
        sqlDB.append("                pl.totalPaymentPrincipalMoney, ");
        sqlDB.append("                2 ");
        sqlDB.append("             ) ");
        sqlDB.append("          ) ");
        sqlDB.append("       FROM ");
        sqlDB.append("          core_paymentplan pl, ");
        sqlDB.append("          crm_customerpersonal cu ");
        sqlDB.append("       WHERE ");
        sqlDB.append("          1 = 1 ");
        sqlDB.append("       AND pl.state = 0 ");
        sqlDB.append("       AND pl.`Status` = 958 ");
        sqlDB.append("       AND cu.state = 0 ");
        sqlDB.append("       AND pl.customerId = cu.id ");
        sqlDB.append("       AND pl.paymentTime = '" + time + "' ");
        sqlDB.append("    ) AS 'sumTotalPaymentPrincipalMoney', ");
        sqlDB.append("    customer.`Name` AS 'customerName', ");
        sqlDB.append("    STR_TO_DATE( ");
        sqlDB.append("       plan.PaymentTime, ");
        sqlDB.append("       '%Y-%m-%d' ");
        sqlDB.append("    ) AS PaymentTime, ");
        sqlDB.append("    round(plan.totalProfitMoney, 2) AS 'totalProfitMoney', ");
        sqlDB.append("    round(plan.totalPaymentMoney, 2) AS 'totalPaymentMoney', ");
        sqlDB.append("    round( ");
        sqlDB.append("       plan.totalPaymentPrincipalMoney, ");
        sqlDB.append("       2 ");
        sqlDB.append("    ) AS 'totalPaymentPrincipalMoney', ");
        sqlDB.append("    plan.currentInstallment AS 'currentInstallment', ");
        sqlDB.append("    plan.totalInstallment AS 'totalInstallment', ");
        sqlDB.append("    round( ");
        sqlDB.append("       ( ");
        sqlDB.append("          SELECT ");
        sqlDB.append("             sum(plan1.totalPaymentMoney) ");
        sqlDB.append("          FROM ");
        sqlDB.append("             core_paymentPlan plan1 ");
        sqlDB.append("          WHERE ");
        sqlDB.append("             1 = 1 ");
        sqlDB.append("          AND plan1.state = 0 ");
        sqlDB.append("          AND plan1.`Status` = 958 ");
        sqlDB.append("          AND plan1.customerId = customer.id ");
        sqlDB.append("       ) - plan.totalPaymentMoney, ");
        sqlDB.append("       2 ");
        sqlDB.append("    ) AS 'surplusPaymentMoney', ");
        sqlDB.append("    round( ");
        sqlDB.append("       ( ");
        sqlDB.append("          SELECT ");
        sqlDB.append("             sum( ");
        sqlDB.append("                plan2.totalPaymentPrincipalMoney ");
        sqlDB.append("             ) ");
        sqlDB.append("          FROM ");
        sqlDB.append("             core_paymentPlan plan2 ");
        sqlDB.append("          WHERE ");
        sqlDB.append("             plan2.state = 0 ");
        sqlDB.append("          AND plan2.`Status` = 958 ");
        sqlDB.append("          AND plan2.customerId = customer.id ");
        sqlDB.append("       ) - plan.totalPaymentPrincipalMoney, ");
        sqlDB.append("       2 ");
        sqlDB.append("    ) AS 'surplusPaymentPrincipalMoney', ");
        sqlDB.append("    plan.`Status` AS 'status' ");
        sqlDB.append(" FROM ");
        sqlDB.append("    core_paymentplan plan, ");
        sqlDB.append("    crm_customerpersonal customer ");
        sqlDB.append(" WHERE ");
        sqlDB.append("    1 = 1 ");
        sqlDB.append(" AND plan.state = 0 ");
        sqlDB.append(" AND plan.`Status` = 958 ");
        sqlDB.append(" AND customer.state = 0 ");
        sqlDB.append(" AND plan.customerId = customer.id ");
        sqlDB.append("       AND plan.paymentTime = '" + time + "' ");

        List<CustomerDailyPaymentReportVO> list = MySQLDao.query(sqlDB.toString(), CustomerDailyPaymentReportVO.class, null, conn);

        return list;
    }
}
