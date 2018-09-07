package com.youngbook.service.report;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.entity.vo.report.DetailStatisticeSumVO;
import com.youngbook.entity.vo.report.DetailStatisticsVO;
import com.youngbook.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/30/14
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class DetailStatisticsService extends BaseService {

    public List<Object> list(DetailStatisticsVO detail, List<KVObject> conditions, HttpServletRequest request,String saleMan) throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //销售人员查询
        if (saleMan != null && !saleMan.equals("")) {
            KVObject sale = new KVObject("saleId", " in ("+Database.encodeSQL(saleMan)+")" );
            conditions.add(sale);
        }
        String pid= request.getParameter("detail.production");
        String sql1;
        if(pid==null||pid.equals("")){
            sql1="(SELECT id FROM crm_production where state=0 ORDER BY startTime desc limit 0,1)";

        } else {
            sql1="'"+Database.encodeSQL(pid)+"'";
        }
        String sql="SELECT u.id saleId,p.name productionName,p.size size,personal.Name customerName,o.money money,o.PayTime time,u.name saleName,pc.ExpectedYield proportion,o.CustomerId count\n" +
                "FROM crm_order o\n" +
                "left JOIN crm_customerpersonal personal on o.CustomerId=personal.id and personal.state<>1\n" +
                "LEFT JOIN system_user u on o.SaleManId=u.Id and u.state=0\n" +
                "LEFT JOIN crm_productioncomposition pc on pc.id=o.ProductionCompositionId and pc.state=0\n" +
                "LEFT JOIN crm_production p on p.id="+Database.encodeSQL(sql1)+" and p.state=0 \n" +
                "WHERE o.State=0 and o.Status=1 and o.ProductionId="+Database.encodeSQL(sql1);
        String sql2="SELECT sum(money) sumMoney,format(sum(proportion)/COUNT(count),4) expectedYield,productionName,size\n" +
                "FROM ("+Database.encodeSQL(sql)+") t";
        Pager pager = Pager.query(sql,detail,conditions,request,queryType);

        DetailStatisticeSumVO sum = new DetailStatisticeSumVO();
        Pager pager1 = Pager.query(sql2,sum,null,request,queryType);

//        List<Map<String,Object>> sumData= MySQLDao.query(sql2);
        List<Object> obj = new ArrayList<Object>();
        obj.add(pager);
//        obj.add(sumData);

        obj.add(pager1);
        return obj;
    }


}

