package com.youngbook.service.report;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.entity.vo.report.TimeStatisticsVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/30/14
 * Time: 9:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimeStatisticsService  extends BaseService {

    public  List<Object> list(TimeStatisticsVO timeStatistics, List<KVObject> conditions, HttpServletRequest request,String saleMan) throws Exception {
        String time;
        List<Object> objects = new ArrayList<Object>();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //销售人员查询
        if (saleMan != null && !saleMan.equals("")) {
            KVObject sale = new KVObject("userId", " in ("+Database.encodeSQL(saleMan)+")" );
            conditions.add(sale);
        }
        String start=request.getParameter("timeStatistics.saleStart");
        String stop=request.getParameter("timeStatistics.saleEnd");
        if((start==null||start.equals(""))&&(stop==null||stop.equals(""))){
            String date= TimeUtils.getNow();
            String year=date.substring(0,4);
            int startMonth= Integer.parseInt(date.substring(5,7))-2;
            int stopMonth= Integer.parseInt(date.substring(5,7))+1;
            String stmonth=Integer.toString(startMonth);
            String spmonth=Integer.toString(stopMonth);
            start= year+'-'+stmonth+"-01 00:00:00";
            stop=year+'-'+ spmonth+"-01 00:00:00";
            time= "PayTime>'"+Database.encodeSQL(start)+"'and PayTime<'"+Database.encodeSQL(stop)+"'\n" ;
        }else if((start==null||start.equals(""))&&(stop!=null||!stop.equals(""))){
            time= "PayTime<'"+Database.encodeSQL(stop)+"'\n";
        }else if((start!=null||!start.equals(""))&&(stop==null||stop.equals(""))){
            time="PayTime>'"+Database.encodeSQL(start)+"'\n";
        } else{
            time= "PayTime>'"+Database.encodeSQL(start)+"'and PayTime<'"+Database.encodeSQL(stop)+"'\n";
        }
        String sql="SELECT t.salemanId userId,u.name name,t.count count,t.money money,t.proportion proportion from (SELECT salemanId,count(CustomerId) count,sum(Money) money,\n" +
                "round(100*(sum(Money)/(SELECT sum(Money) FROM crm_order where state =0 and Status=1)),2) proportion\n" +
                "FROM crm_order\n" +
                "WHERE "+Database.encodeSQL(time)+" and state=0 and Status=1\n" +
                "GROUP BY SaleManId ) t\n" +
                "left JOIN system_user u ON t.salemanId=u.Id and u.state=0";
        Pager pager = Pager.query(sql,timeStatistics,conditions,request,queryType);

        objects.add(start);
        objects.add(stop);
        objects.add(pager);
        return objects;
    }

}
