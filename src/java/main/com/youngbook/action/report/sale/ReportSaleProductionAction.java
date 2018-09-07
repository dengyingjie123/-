package com.youngbook.action.report.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.vo.report.sale.ReportSaleProductionVO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2015/11/15.
 */
public class ReportSaleProductionAction extends BaseAction {

    public String list() throws Exception {

        // Search_ReportSaleProduction_Time
        String date = HttpUtils.getParameter(getRequest(), "Search_ReportSaleProduction_Time");

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("      p.id ProductionId,");
        sbSQL.append("      p. NAME ProductionName,");
        sbSQL.append("      sum(o.Money) Money,");
        sbSQL.append("      pc.ExpectedYield,");
        sbSQL.append("      DATE_FORMAT(o.PayTime,'%Y-%m-%d') PayTime,");
        sbSQL.append("      DATE_FORMAT(o.valueDate,'%Y-%m-%d') valueDate,");
        sbSQL.append("      DATE_FORMAT(pp.PaymentTime,'%Y-%m-%d') PaymentTime,");
        sbSQL.append("      sum(pp.TotalPaymentPrincipalMoney) TotalPaymentPrincipalMoney,");
        sbSQL.append("      sum(pp.TotalProfitMoney) TotalProfitMoney,");
        sbSQL.append("      '未兑付' PaymentPlanStatus");
        sbSQL.append(" FROM");
        sbSQL.append("      crm_production p,");
        sbSQL.append("      crm_productionhome ph,");
        sbSQL.append("      crm_order o,");
        sbSQL.append("      core_paymentplan pp,");
        sbSQL.append("      crm_productioncomposition pc,");
        sbSQL.append("      system_kv ppKv");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND p.state = 0");
        sbSQL.append(" AND o.state = 0");
        sbSQL.append(" AND ph.State = 0");
        sbSQL.append(" AND pp.state = 0");
        sbSQL.append(" AND pc.state = 0");
        sbSQL.append(" AND ppKv.GroupName = 'Sale_PaymentPlan_Status'");
        sbSQL.append(" AND ppKv.k = pp.`Status`");
        sbSQL.append(" AND pc.ProductionId = p.id");
        sbSQL.append(" AND o.ProductionCompositionId = pc.id");
        sbSQL.append(" AND pp.OrderId = o.id");
        sbSQL.append(" AND o.ProductionId = p.id");
        sbSQL.append(" AND ph.ProjectId = '271758E97BC44BBBA67F01C1D7866308'");
        sbSQL.append(" AND ph.id = p.productHomeId");
        sbSQL.append(" AND o.`Status` = 1");
        sbSQL.append(" GROUP BY");
        sbSQL.append("      ProductionId, ProductionName, ExpectedYield, PayTime, ValueDate, PaymentTime");
        sbSQL.append(" ORDER BY");
        sbSQL.append("      p.`Name`,");
        sbSQL.append("      o.PayTime");

        sbSQL.insert(0, "select * from (" ).append(" ) _ft_ WHERE 1=1 ");


        // AND PayTime = '2015-11-13'
        if (!StringUtils.isEmpty(date)) {
            sbSQL.append(" AND PayTime='"+date+"'");
        }


        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = Pager.getInstance(request);
        //pager.getCurrentPage(), pager.getShowRowCount()
        ReportSaleProductionVO vo = new ReportSaleProductionVO();
        pager = MySQLDao.search(sbSQL.toString(), null, vo, conditions, pager.getCurrentPage(), pager.getShowRowCount(), null, conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }
}
