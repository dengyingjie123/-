package com.youngbook.service.system;


import com.youngbook.dao.system.IHolidayDao;

import com.youngbook.entity.po.HolidayPO;

import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



@Component("holidayService")
public class HolidayService extends BaseService {

    @Autowired
    IHolidayDao holidayDao;


    /**
     * @description    获取工作日
     *
     * @author 苟熙霖
     *
     * @date 2019/1/9 14:43
     * @param time
     * @param conn
     * @return java.lang.String
     * @throws Exception
     */
   public String getNextWorkDay(String time, Connection conn) throws Exception {

       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");




       for(int i = 0 ; i<=10 ; i++ ) {
           Date parse = sdf.parse(time);
           Calendar dd = Calendar.getInstance();
           dd.setTime(parse);
           time = sdf.format(dd.getTime());




           HolidayPO holidayPO = holidayDao.loadHolidayPO(time, conn);




           if (holidayPO != null) {
               dd.setTime(parse);
               dd.add(Calendar.DAY_OF_MONTH, 1);
               time = sdf.format(dd.getTime());




           }else {
               dd.setTime(parse);
               time = sdf.format(dd.getTime());
               return time;
           }
       }




       return time;
    }

}
