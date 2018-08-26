package com.youngbook.service.customer;

import com.youngbook.common.*;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.vo.customer.CustomerProductionVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2014/11/26.
 */
@Component("customerProductionService")
public class CustomerProductionService extends BaseService {

    /**
     * 修改人：李昕骏<br>
     * 时间：2016年1月23日 17:09:58<br>
     * <br>
     * 添加参数 customerId for HOPEWEALTH-1224<br>
     * 修改人：曾威恺<br>
     * 时间：2016/03/03 18:00<br>
     *
     * @param customerProductionVO
     * @param conditions
     * @param customerId
     * @return
     * @throws Exception
     */
    public Pager list(CustomerProductionVO customerProductionVO, List<KVObject> conditions, String customerId, String customerRemark) throws Exception {

        if (StringUtils.isEmpty(customerId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }

        if (StringUtils.isEmpty(customerRemark)) {
            MyException.newInstance("无法获得客户类型").throwException();
        }

        DatabaseSQL dbSQL = new DatabaseSQL();

        StringBuffer sbSQL = getSQL4Customer(customerRemark);//customerRemark为0代表个人客户，为1代表机构客户。 HOPEWEALTH-1224

        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, customerId);

        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "money desc"));

        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), customerProductionVO, conditions, request, queryType);
        return pager;
    }

    private StringBuffer getSQL4Customer(String customerRemark) {
        StringBuffer sbSQL = new StringBuffer();


        sbSQL.append(" SELECT");
        sbSQL.append("     o.OrderNum,");
        sbSQL.append("     c. NAME CustomerName,");
        sbSQL.append("     p.`Name` NAME,");
        sbSQL.append("     o.PayTime PayTime,");
        sbSQL.append("     o.Money,");
        sbSQL.append("     o.`Status` moneyStatus,");
        sbSQL.append("     kv.v moneyStatusName");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_order o");
        if (StringUtils.checkEquals(customerRemark, "1")) {
            sbSQL.append(" LEFT JOIN crm_customerinstitution c on c.state=0 and o.CustomerId = c.id");
        }
        else {
            sbSQL.append(" LEFT JOIN crm_customerpersonal c on c.state=0 and o.CustomerId = c.id");
        }
        sbSQL.append(" LEFT JOIN crm_production p on p.state = 0 AND o.ProductionId = p.id");
        sbSQL.append(" LEFT JOIN system_kv kv on kv.GroupName='OrderStatus' and kv.K=o.`Status`");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND o.state = 0");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND c.id =?");
        sbSQL.append(" and o.`Status` not in (6)");

        return sbSQL;
    }

    /**
     * 通过客户 ID 查找所属的产品
     *
     * @param customerId
     * @return
     * @throws Exception
     */
    public Pager list4Web(String customerId, Connection conn) throws Exception {

        CustomerProductionVO customerProductionVO = new CustomerProductionVO();

        HttpServletRequest request = ServletActionContext.getRequest();

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     p.id productId,");
        sbSQL.append("     o.id orderId,");
        sbSQL.append("     p.websiteDisplayName,");
        sbSQL.append("     o.valueDate ValueDate,");
        sbSQL.append("     o.createTime CreateTime,");
        sbSQL.append("     o.Money saleMoney,");
        sbSQL.append("     o.`Status`,");
        sbSQL.append("     pp.`Status` PaymentPlanStatus,");
        sbSQL.append("     p.incomeType,");
        sbSQL.append("     ROUND(sum(pp.TotalProfitMoney),2) sumTotalProfitMoney");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal c,");
        sbSQL.append("     crm_production p,");
        sbSQL.append("     crm_order o");
        sbSQL.append("     left join core_paymentplan pp on pp.state = 0 and pp.OrderId = o.id ");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND p.state = 0");
        sbSQL.append(" AND o.state = 0");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND o.`Status` NOT IN (?, ?)");
        sbSQL.append(" AND c.id = ?");
        sbSQL.append(" AND o.CustomerId = c.id");
        sbSQL.append(" AND p.id = o.ProductionId");
        sbSQL.append(" GROUP BY");
        sbSQL.append("     p.`Name`,");
        sbSQL.append("     o.valueDate,");
        sbSQL.append("     o.createTime,");
        sbSQL.append("     o.Money,");
        sbSQL.append("     o.`Status`");
        sbSQL.append(" ORDER BY");
        sbSQL.append("     o.valueDate DESC");

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, OrderStatus.AppointmentTimeout)
                .addParameter(2, OrderStatus.Cancel)
                .addParameter(3, customerId);

        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), customerProductionVO, null, request, null, conn);
        return pager;
    }
}
