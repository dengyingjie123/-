package com.youngbook.service.report;

import com.youngbook.common.*;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.vo.report.ProductDailyPaymentReportVO;
import com.youngbook.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by 张舜清 on 2015/9/7.
 */
public class ProductDailyPaymentReportService extends BaseService {
    /**
     * 创建人：张舜清
     * 时间：2015年9月7日15:11:21
     * 内容：查询出数据
     *
     * @param productDailyPaymentReportVO
     * @param conditions
     * @param request
     * @return
     * @throws Exception
     */
    public Pager list(ProductDailyPaymentReportVO productDailyPaymentReportVO, List<KVObject> conditions, String time, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append("SELECT ");
        sqlDB.append("     ( ");
        sqlDB.append("         SELECT ");
        sqlDB.append("             SUM( ");
        sqlDB.append("                 round(pl.totalProfitMoney, 2) ");
        sqlDB.append("             ) ");
        sqlDB.append("         FROM ");
        sqlDB.append("             core_paymentplan pl, ");
        sqlDB.append("             crm_production pr ");
        sqlDB.append("         WHERE ");
        sqlDB.append("             pl.state = 0 ");
        sqlDB.append("         AND pl. STATUS = 958 ");
        sqlDB.append("         AND pr.state = 0 ");
        sqlDB.append("         AND pl.productId = pr.id ");
        //如果后台管理厚币通宝传入日期不为空，则加入日期。如果没有全部都查询出来
        if (!StringUtils.isEmpty(time)) {
            sqlDB.append("         AND pl.paymentTime = '" + Database.encodeSQL(time) + "' ");
        }
        sqlDB.append("     ) AS 'sumTotalProfitMoney', ");
        sqlDB.append("     ( ");
        sqlDB.append("         SELECT ");
        sqlDB.append("             SUM( ");
        sqlDB.append("                 round(pl.totalPaymentMoney, 2) ");
        sqlDB.append("             ) ");
        sqlDB.append("         FROM ");
        sqlDB.append("             core_paymentplan pl, ");
        sqlDB.append("             crm_production pr ");
        sqlDB.append("         WHERE ");
        sqlDB.append("             pl.state = 0 ");
        sqlDB.append("         AND pl. STATUS = 958 ");
        sqlDB.append("         AND pr.state = 0 ");
        sqlDB.append("         AND pl.productId = pr.id ");
        if (!StringUtils.isEmpty(time)) {
            sqlDB.append("         AND pl.paymentTime = '" + Database.encodeSQL(time) + "' ");
        }
        sqlDB.append("     ) AS 'sumTotalPaymentMoney', ");
        sqlDB.append("     ( ");
        sqlDB.append("         SELECT ");
        sqlDB.append("             SUM( ");
        sqlDB.append("                 round( ");
        sqlDB.append("                     pl.totalPaymentPrincipalMoney, ");
        sqlDB.append("                     2 ");
        sqlDB.append("                 ) ");
        sqlDB.append("             ) ");
        sqlDB.append("         FROM ");
        sqlDB.append("             core_paymentplan pl, ");
        sqlDB.append("             crm_production pr ");
        sqlDB.append("         WHERE ");
        sqlDB.append("             pl.state = 0 ");
        sqlDB.append("         AND pl. STATUS = 958 ");
        sqlDB.append("         AND pr.state = 0 ");
        sqlDB.append("         AND pl.productId = pr.id ");
        if (!StringUtils.isEmpty(time)) {
            sqlDB.append("         AND pl.paymentTime = '" + Database.encodeSQL(time) + "' ");
        }
        sqlDB.append("     ) AS 'sumTotalPaymentPrincipalMoney', ");
        sqlDB.append("     production. NAME AS 'productionName', ");
        sqlDB.append("     round(plan.totalProfitMoney, 2) AS 'totalProfitMoney', ");
        sqlDB.append("     round(plan.totalPaymentMoney, 2) AS 'totalPaymentMoney', ");
        sqlDB.append("     round( ");
        sqlDB.append("         plan.totalPaymentPrincipalMoney, ");
        sqlDB.append("         2 ");
        sqlDB.append("     ) AS 'totalPaymentPrincipalMoney', ");
        sqlDB.append("     plan.currentInstallment AS 'currentInstallment', ");
        sqlDB.append("     plan.totalInstallment AS 'totalInstallment', ");
        sqlDB.append("     round( ");
        sqlDB.append("         ( ");
        sqlDB.append("             SELECT ");
        sqlDB.append("                 sum(plan1.totalPaymentMoney) ");
        sqlDB.append("             FROM ");
        sqlDB.append("                 core_paymentPlan plan1 ");
        sqlDB.append("             WHERE ");
        sqlDB.append("                 plan1.state = 0 ");
        sqlDB.append("             AND plan1. STATUS = 958 ");
        sqlDB.append("             AND plan1.productId = production.id ");
        sqlDB.append("         ) - plan.totalPaymentMoney, ");
        sqlDB.append("         2 ");
        sqlDB.append("     ) AS 'surplusPaymentMoney', ");
        sqlDB.append("     round( ");
        sqlDB.append("         ( ");
        sqlDB.append("             SELECT ");
        sqlDB.append("                 sum( ");
        sqlDB.append("                     plan2.totalPaymentPrincipalMoney ");
        sqlDB.append("                 ) ");
        sqlDB.append("             FROM ");
        sqlDB.append("                 core_paymentPlan plan2 ");
        sqlDB.append("             WHERE ");
        sqlDB.append("                 plan2.state = 0 ");
        sqlDB.append("             AND plan2. STATUS = 958 ");
        sqlDB.append("             AND plan2.productId = production.id ");
        sqlDB.append("         ) - plan.totalPaymentPrincipalMoney, ");
        sqlDB.append("         2 ");
        sqlDB.append("     ) AS 'surplusPaymentPrincipalMoney', ");
        sqlDB.append("     plan.`Status` AS 'status', ");
        sqlDB.append("     STR_TO_DATE(plan.PaymentTime,'%Y-%m-%d') AS PaymentTime ");
        sqlDB.append(" FROM ");
        sqlDB.append("     core_paymentplan plan, ");
        sqlDB.append("     crm_production production ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     plan.state = 0 ");
        sqlDB.append(" AND plan. status = 958 ");
        sqlDB.append(" AND production.state = 0 ");
        sqlDB.append(" AND plan.productId = production.id ");
        if (!StringUtils.isEmpty(time)) {
            sqlDB.append(" AND plan.paymentTime = '" + Database.encodeSQL(time) + "' ");
        }

        Pager pager = Pager.query(sqlDB.toString(), productDailyPaymentReportVO, conditions, request, queryType);

        return pager;
    }

    public List<ProductDailyPaymentReportVO> list(String time, Connection conn) throws Exception {
        if (time == null) {
            throw new Exception("打印参数传入错误");
        }
        if (time.length() != 10) {
            throw new Exception("打印参数格式错误");
        }
        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append("SELECT ");
        sqlDB.append("     ( ");
        sqlDB.append("         SELECT ");
        sqlDB.append("             SUM( ");
        sqlDB.append("                 round(pl.totalProfitMoney, 2) ");
        sqlDB.append("             ) ");
        sqlDB.append("         FROM ");
        sqlDB.append("             core_paymentplan pl, ");
        sqlDB.append("             crm_production pr ");
        sqlDB.append("         WHERE ");
        sqlDB.append("             pl.state = 0 ");
        sqlDB.append("         AND pl. STATUS = 958 ");
        sqlDB.append("         AND pr.state = 0 ");
        sqlDB.append("         AND pl.productId = pr.id ");
        sqlDB.append("         AND pl.paymentTime = '" + time + "' ");
        sqlDB.append("     ) AS 'sumTotalProfitMoney', ");
        sqlDB.append("     ( ");
        sqlDB.append("         SELECT ");
        sqlDB.append("             SUM( ");
        sqlDB.append("                 round(pl.totalPaymentMoney, 2) ");
        sqlDB.append("             ) ");
        sqlDB.append("         FROM ");
        sqlDB.append("             core_paymentplan pl, ");
        sqlDB.append("             crm_production pr ");
        sqlDB.append("         WHERE ");
        sqlDB.append("             pl.state = 0 ");
        sqlDB.append("         AND pl. STATUS = 958 ");
        sqlDB.append("         AND pr.state = 0 ");
        sqlDB.append("         AND pl.productId = pr.id ");
        sqlDB.append("         AND pl.paymentTime = '" + time + "' ");
        sqlDB.append("     ) AS 'sumTotalPaymentMoney', ");
        sqlDB.append("     ( ");
        sqlDB.append("         SELECT ");
        sqlDB.append("             SUM( ");
        sqlDB.append("                 round( ");
        sqlDB.append("                     pl.totalPaymentPrincipalMoney, ");
        sqlDB.append("                     2 ");
        sqlDB.append("                 ) ");
        sqlDB.append("             ) ");
        sqlDB.append("         FROM ");
        sqlDB.append("             core_paymentplan pl, ");
        sqlDB.append("             crm_production pr ");
        sqlDB.append("         WHERE ");
        sqlDB.append("             pl.state = 0 ");
        sqlDB.append("         AND pl. STATUS = 958 ");
        sqlDB.append("         AND pr.state = 0 ");
        sqlDB.append("         AND pl.productId = pr.id ");
        sqlDB.append("         AND pl.paymentTime = '" + time + "' ");
        sqlDB.append("     ) AS 'sumTotalPaymentPrincipalMoney', ");
        sqlDB.append("     production. NAME AS 'productionName', ");
        sqlDB.append("     round(plan.totalProfitMoney, 2) AS 'totalProfitMoney', ");
        sqlDB.append("     round(plan.totalPaymentMoney, 2) AS 'totalPaymentMoney', ");
        sqlDB.append("     round( ");
        sqlDB.append("         plan.totalPaymentPrincipalMoney, ");
        sqlDB.append("         2 ");
        sqlDB.append("     ) AS 'totalPaymentPrincipalMoney', ");
        sqlDB.append("     plan.currentInstallment AS 'currentInstallment', ");
        sqlDB.append("     plan.totalInstallment AS 'totalInstallment', ");
        sqlDB.append("     round( ");
        sqlDB.append("         ( ");
        sqlDB.append("             SELECT ");
        sqlDB.append("                 sum(plan1.totalPaymentMoney) ");
        sqlDB.append("             FROM ");
        sqlDB.append("                 core_paymentPlan plan1 ");
        sqlDB.append("             WHERE ");
        sqlDB.append("                 plan1.state = 0 ");
        sqlDB.append("             AND plan1. STATUS = 958 ");
        sqlDB.append("             AND plan1.productId = production.id ");
        sqlDB.append("         ) - plan.totalPaymentMoney, ");
        sqlDB.append("         2 ");
        sqlDB.append("     ) AS 'surplusPaymentMoney', ");
        sqlDB.append("     round( ");
        sqlDB.append("         ( ");
        sqlDB.append("             SELECT ");
        sqlDB.append("                 sum( ");
        sqlDB.append("                     plan2.totalPaymentPrincipalMoney ");
        sqlDB.append("                 ) ");
        sqlDB.append("             FROM ");
        sqlDB.append("                 core_paymentPlan plan2 ");
        sqlDB.append("             WHERE ");
        sqlDB.append("                 plan2.state = 0 ");
        sqlDB.append("             AND plan2. STATUS = 958 ");
        sqlDB.append("             AND plan2.productId = production.id ");
        sqlDB.append("         ) - plan.totalPaymentPrincipalMoney, ");
        sqlDB.append("         2 ");
        sqlDB.append("     ) AS 'surplusPaymentPrincipalMoney', ");
        sqlDB.append("     plan.`Status` AS 'status', ");
        sqlDB.append("     STR_TO_DATE(plan.PaymentTime,'%Y-%m-%d') AS PaymentTime ");
        sqlDB.append(" FROM ");
        sqlDB.append("     core_paymentplan plan, ");
        sqlDB.append("     crm_production production ");
        sqlDB.append(" WHERE ");
        sqlDB.append("     plan.state = 0 ");
        sqlDB.append(" AND plan. status = 958 ");
        sqlDB.append(" AND production.state = 0 ");
        sqlDB.append(" AND plan.productId = production.id ");
        sqlDB.append(" AND plan.paymentTime = '" + time + "' ");

        List<ProductDailyPaymentReportVO> list = MySQLDao.query(sqlDB.toString(), ProductDailyPaymentReportVO.class, null, conn);

        return list;
    }
}
