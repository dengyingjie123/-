package com.youngbook.action.system;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.Pager;
import com.youngbook.common.ReturnObject;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.calendar.EventPO;
import com.youngbook.entity.po.calendar.EventSourcePO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.service.calendar.CalendarService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 11/21/14
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarAction extends BaseAction {

    private CalendarService service = new CalendarService();

    public String buildEvents() throws Exception {

        EventSourcePO birthdaySource = new EventSourcePO();

        String intervalStart = getRequest().getParameter("intervalStart");
        birthdaySource = service.getCustomerBirthdays4MonthView(intervalStart, getLoginUser());


        JSONArray eventSources = new JSONArray();

        eventSources.add(birthdaySource);

        getResult().setReturnValue(eventSources);

        return SUCCESS;
    }


    /**
     * @description 获取当前用户本月募集资金总额(打款后)
     *
     * @author 苟熙霖
     *
     * @date 2018/12/4 11:14
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String getCurrentMonthRaise () throws Exception {

        OrderPO orderPO = new OrderPO();
        String today = getRequest().getParameter("today");
        String id = getLoginUser().getId();
        List<OrderPO> raises = service.getCurrentMonthRaise(today, id, getConnection());




        Double raise = 0.0;
        for (OrderPO o : raises) {
            double money = o.getMoney();
            raise += money;
        }




        orderPO.setMoney(raise);
        Pager pager = new Pager();
        ArrayList list = new ArrayList();
        list.add(orderPO);
        pager.setData(list);
        getResult().setReturnValue(pager.toJsonObject());




        return SUCCESS;
    }


    public String listCustomerBirthdays() throws Exception {

        String intervalStart = getRequest().getParameter("intervalStart");

        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append(" ");
        sbSQL.append(" SELECT");
        sbSQL.append("     id,");
        sbSQL.append("     NAME title,");
        sbSQL.append("     CONCAT(");
        sbSQL.append("         DATE_FORMAT(date_add('"+ Database.encodeSQL(intervalStart)+ "', INTERVAL - 1 MONTH), '%Y'),");
        sbSQL.append("         '-',");
        sbSQL.append("         DATE_FORMAT(Birthday, '%c-%d')");
        sbSQL.append("     ) START");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal");
        sbSQL.append(" WHERE");
        sbSQL.append("     state = 0");
        sbSQL.append("     AND DATE_FORMAT(birthday, '%c') = DATE_FORMAT(");
                sbSQL.append("         date_add('" + Database.encodeSQL(intervalStart) + "', INTERVAL - 1 MONTH),");
        sbSQL.append("         '%c'");
        sbSQL.append("     )");
        sbSQL.append(" ");
        sbSQL.append(" UNION");
        sbSQL.append(" ");
        sbSQL.append(" ");
        sbSQL.append(" SELECT");
        sbSQL.append("     id,");
        sbSQL.append("     NAME title,");
        sbSQL.append("     CONCAT(");
        sbSQL.append("         DATE_FORMAT(date_add('"+Database.encodeSQL(intervalStart)+"', INTERVAL 0 MONTH), '%Y'),");
        sbSQL.append("         '-',");
        sbSQL.append("         DATE_FORMAT(Birthday, '%c-%d')");
        sbSQL.append("     ) START");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal");
        sbSQL.append(" WHERE");
        sbSQL.append("     state = 0");
        sbSQL.append("     AND DATE_FORMAT(birthday, '%c') = DATE_FORMAT(");
        sbSQL.append("         date_add('"+Database.encodeSQL(intervalStart)+"', INTERVAL 0 MONTH),");
        sbSQL.append("         '%c'");
        sbSQL.append("     )");
        sbSQL.append(" ");
        sbSQL.append(" UNION");
        sbSQL.append(" ");
        sbSQL.append(" ");
        sbSQL.append(" SELECT");
        sbSQL.append("     id,");
        sbSQL.append("     NAME title,");
        sbSQL.append("     CONCAT(");
        sbSQL.append("         DATE_FORMAT(date_add('"+Database.encodeSQL(intervalStart)+"', INTERVAL 1 MONTH), '%Y'),");
        sbSQL.append("         '-',");
        sbSQL.append("         DATE_FORMAT(Birthday, '%c-%d')");
        sbSQL.append("     ) START");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal");
        sbSQL.append(" WHERE");
        sbSQL.append("     state = 0");
        sbSQL.append("     AND DATE_FORMAT(birthday, '%c') = DATE_FORMAT(");
        sbSQL.append("         date_add('"+Database.encodeSQL(intervalStart)+"', INTERVAL 1 MONTH),");
        sbSQL.append("         '%c'");
        sbSQL.append("     )");


        String loginUserId = getLoginUser().getId();

        StringBuffer sbSQLIn = new StringBuffer();
        sbSQLIn.append(" SELECT");
        sbSQLIn.append("      id CustomerId");
        sbSQLIn.append(" FROM");
        sbSQLIn.append("      crm_customerpersonal");
        sbSQLIn.append(" WHERE");
        sbSQLIn.append("      state = 0");
        // 判断权限
        if (!hasPermission("个人日历_查看全部")) {
            sbSQLIn.append(" AND id NOT IN (");
            sbSQLIn.append("      SELECT");
            sbSQLIn.append("           CustomerId");
            sbSQLIn.append("      FROM");
            sbSQLIn.append("           crm_customerdistribution");
            sbSQLIn.append(" )");
        }
        sbSQLIn.append(" ");
        sbSQLIn.append(" UNION");
        sbSQLIn.append(" ");
        sbSQLIn.append(" SELECT");
        sbSQLIn.append("      CustomerId");
        sbSQLIn.append(" FROM");
        sbSQLIn.append("      crm_customerdistribution");
        sbSQLIn.append(" WHERE");
        sbSQLIn.append("      1 = 1");
        sbSQLIn.append(" AND state = 0");
        sbSQLIn.append(" AND STATUS = 1");
        if (!hasPermission("个人日历_查看全部")) {
            sbSQLIn.append(" AND SaleManId = '" + Database.encodeSQL(loginUserId )+ "'");
        }



        sbSQL.insert(0, " Select * from (");
        sbSQL.append(" ) t where t.id in(").append(sbSQLIn).append(")");


        try {
            List<EventPO> events = MySQLDao.query(sbSQL.toString(), EventPO.class, null);
            JSONArray array = new JSONArray();
            for (EventPO event : events) {
                array.add(event.toJsonObject());
            }
            getResult().setReturnValue(array);
        }
        catch (Exception e) {
            getResult().setMessage("失败");
        }
        return SUCCESS;
    }

    public CalendarService getService() {
        return service;
    }

    public void setService(CalendarService service) {
        this.service = service;
    }
}
