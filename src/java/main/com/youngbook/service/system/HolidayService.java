package com.youngbook.service.system;

import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.IHolidayDao;
import com.youngbook.dao.system.IKVDao;
import com.youngbook.entity.po.HolidayPO;
import com.youngbook.entity.po.KVPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component("holidayService")
public class HolidayService extends BaseService {

    @Autowired
    IHolidayDao holidayDao;

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
