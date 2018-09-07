package com.youngbook.action.report.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.IJsonable;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.vo.report.sale.ReportSaleCustomerVO;
import com.youngbook.entity.vo.report.sale.ReportSaleProductionVO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2015/11/15.
 */
public class ReportSaleCustomerAction extends BaseAction {

    public String list() throws Exception {

        // Search_ReportSaleProduction_Time
        String date = HttpUtils.getParameter(getRequest(), "Search_ReportSaleCustomer_Time");
        String productionName = HttpUtils.getParameter(getRequest(), "Search_ReportSaleCustomer_ProductionName");
        String customerName = HttpUtils.getParameter(getRequest(), "Search_ReportSaleCustomer_CustomerName");

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     t_1.*, u.`name` ReferralName");
        sbSQL.append(" FROM");
        sbSQL.append("     (");
        sbSQL.append("         SELECT");
        sbSQL.append("             c.id CustomerId,");
        sbSQL.append("             cc.Number CustomerIdCardNumber,");
        sbSQL.append("             c.`Name` CustomerName,");
        sbSQL.append("             p. NAME ProductionName,");
        sbSQL.append("             o.Money,");
        sbSQL.append("             pc.ExpectedYield,");
        sbSQL.append("             DATE_FORMAT(o.PayTime, '%Y-%m-%d %H:%i:%s') PayTime,");
        sbSQL.append("             DATE_FORMAT(o.valueDate, '%Y-%m-%d') valueDate,");
        sbSQL.append("             DATE_FORMAT(pp.PaymentTime, '%Y-%m-%d') PaymentTime,");
        sbSQL.append("             pp.TotalPaymentPrincipalMoney,");
        sbSQL.append("             pp.TotalProfitMoney,");
        sbSQL.append("             ppKv.v PaymentPlanStatus,");
        sbSQL.append("             o.referralCode referralCode");
        sbSQL.append("         FROM");
        sbSQL.append("             crm_production p,");
        sbSQL.append("             crm_productionhome ph,");
        sbSQL.append("             crm_order o,");
        sbSQL.append("             crm_customerpersonal c,");
        sbSQL.append("             core_paymentplan pp,");
        sbSQL.append("             crm_customercertificate cc,");
        sbSQL.append("             crm_productioncomposition pc,");
        sbSQL.append("             system_kv ppKv");
        sbSQL.append("         WHERE");
        sbSQL.append("             1 = 1");
        sbSQL.append("         AND p.state = 0");
        sbSQL.append("         AND o.state = 0");
        sbSQL.append("         AND ph.State = 0");
        sbSQL.append("         AND c.state = 0");
        sbSQL.append("         AND pp.state = 0");
        sbSQL.append("         AND cc.state = 0");
        sbSQL.append("         AND pc.state = 0");
        sbSQL.append("         AND ppKv.GroupName = 'Sale_PaymentPlan_Status'");
        // sbSQL.append("         AND p.`Name` = '通宝季度享1号'");
        sbSQL.append("         AND ppKv.k = pp.`Status`");
        sbSQL.append("         AND pc.ProductionId = p.id");
        sbSQL.append("         AND o.ProductionCompositionId = pc.id");
        sbSQL.append("         AND c.id = cc.CustomerId");
        sbSQL.append("         AND pp.OrderId = o.id");
        sbSQL.append("         AND o.ProductionId = p.id");
        sbSQL.append("         AND ph.ProjectId = '271758E97BC44BBBA67F01C1D7866308'");
        sbSQL.append("         AND ph.id = p.productHomeId");
        sbSQL.append("         AND o.`Status` in (1,5)");
        sbSQL.append("         AND o.CustomerId = c.id");
        sbSQL.append("     ) t_1");
        sbSQL.append(" LEFT JOIN system_user u ON u.state = 0");
        sbSQL.append(" AND (u.mobile = t_1.referralCode OR  t_1.referralCode=u.referralCode)");
        sbSQL.append(" ORDER BY");
        sbSQL.append("     PayTime desc,");
        sbSQL.append("     `Name`");

        sbSQL.insert(0, "select * from (" ).append(" ) _ft_ WHERE 1=1 ");

        int index = 1;
        List<KVObject> parameters = new ArrayList<KVObject>();

        // AND PayTime = '2015-11-13'
        if (!StringUtils.isEmpty(date)) {
            sbSQL.append(" AND DATE_FORMAT(PayTime,'"+ TimeUtils.Format_YYYY_MM_DD_MysQL+"')=?");
            parameters = Database.addQueryKVObject(index++, date, parameters);
        }

        // productionName
        if (!StringUtils.isEmpty(productionName)) {
            sbSQL.append(" AND ProductionName like ?");
            parameters = Database.addQueryKVObject(index++, "%"+productionName+"%", parameters);
        }

        if (!StringUtils.isEmpty(customerName)) {
            sbSQL.append(" AND CustomerName like ?");
            parameters = Database.addQueryKVObject(index++, "%"+customerName+"%", parameters);
        }

        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = Pager.getInstance(request);
        //pager.getCurrentPage(), pager.getShowRowCount()
        ReportSaleCustomerVO vo = new ReportSaleCustomerVO();
        pager = MySQLDao.search(sbSQL.toString(), parameters, vo, conditions, pager.getCurrentPage(), pager.getShowRowCount(), null, conn);


        // 身份证解密
        List<IJsonable> list = pager.getData();
        for (int i = 0; i < list.size(); i++) {
            ReportSaleCustomerVO tempVO = (ReportSaleCustomerVO)list.get(i);
            String customerIdCardNumber = tempVO.getCustomerIdCardNumber();
            customerIdCardNumber = AesEncrypt.decrypt(customerIdCardNumber);

            // 去除敏感数据
            if (hasPermission("销售报表_客户销售报表_数据查看")) {
                customerIdCardNumber = "****";
            }

            tempVO.setCustomerIdCardNumber(customerIdCardNumber);
        }

        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }
}
