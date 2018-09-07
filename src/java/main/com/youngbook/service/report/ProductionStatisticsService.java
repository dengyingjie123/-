package com.youngbook.service.report;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.entity.vo.report.ProductionStatisticsVO;
import com.youngbook.service.BaseService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/29/14
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductionStatisticsService extends BaseService {

    public Pager list(ProductionStatisticsVO proStatistics, List<KVObject> conditions, HttpServletRequest request,String saleMan) throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
       //销售人员查询
        if (saleMan != null && !saleMan.equals("")) {
            KVObject sale = new KVObject("userId", " in ("+Database.encodeSQL(saleMan)+")" );
            conditions.add(sale);
        }

        String sql1 ="SELECT o.createTime,u.id userId,p.name production,u.name,count(CustomerId) count,sum(o.Money) money,round(100*(sum(o.Money)/(SELECT sum(o.Money) moneys\n" +
                "FROM system_user u\n" +
                "JOIN crm_order o on o.SaleManId=u.Id and o.State=0 and o.Status=1\n" +
                "WHERE u.state=0 and o.ProductionId=";
        //判断产品id
        String pid= request.getParameter("proStatistics.production");
        String sql2;
        if(pid==null||pid.equals("")){
            sql2="(SELECT id FROM crm_production where state=0 ORDER BY startTime desc limit 0,1)";

        } else {
            sql2="'"+Database.encodeSQL(pid)+"'";
        }
        String sql3="))) proportion\n" +
                "FROM system_user u\n" +
                "JOIN crm_order o on o.SaleManId=u.Id and o.State=0 and o.Status=1\n" +
                "LEFT JOIN crm_production p on p.id=";
        String sql5=" and p.state=0 WHERE u.state=0 and o.ProductionId=";
        String sql4="GROUP BY u.id";
        String sql=sql1+sql2+sql3+sql2+sql5+sql2+sql4;
        Pager pager = Pager.query(sql,proStatistics,conditions,request,queryType);

        return pager;
    }


}

