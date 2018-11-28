package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.calendar.EventPO;
import com.youngbook.entity.po.calendar.EventSourcePO;
import com.youngbook.service.calendar.CalendarService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 11/21/14
 * Time: 9:24 PM
 * Update: Huchaoyi
 * Update Date:11/28/18
 * 优化了显示生日的功能
 * To change this template use File | Settings | File Templates.
 */
public class CalendarAction extends BaseAction {

    @Autowired
    CalendarService calendarService;

    public String buildEvents() throws Exception {

        EventSourcePO birthdaySource = new EventSourcePO();

        String intervalStart = getRequest().getParameter("intervalStart");
        birthdaySource = calendarService.getCustomerBirthdays4MonthView(intervalStart, getLoginUser());


        JSONArray eventSources = new JSONArray();

        eventSources.add(birthdaySource);

        getResult().setReturnValue(eventSources);

        return SUCCESS;
    }




    /**
     * @description
     * 按照现有规范优化了个人日历显示客户生日的功能
     * @author 胡超怡
     *
     * @date 2018/11/28 16:16
     * @return java.lang.String
     * @throws Exception
     */
    public String listCustomerBirthdays() throws Exception {

        String intervalStart = getRequest().getParameter("intervalStart");

        List<EventPO> events = calendarService.getEventPO(intervalStart, getConnection());


        /**
         * 把返回的数据组成json数组格式响应给前台
         */
        JSONArray array = new JSONArray();
        for (EventPO event : events) {
            array.add(event.toJsonObject());
        }
        getResult().setReturnValue(array);

        return SUCCESS;
    }

}
