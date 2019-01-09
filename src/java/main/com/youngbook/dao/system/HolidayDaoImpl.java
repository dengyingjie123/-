package com.youngbook.dao.system;


import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.BaseDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.HolidayPO;

import org.springframework.stereotype.Component;

import java.sql.Connection;



@Component("holidayDao")
public class HolidayDaoImpl extends BaseDao implements IHolidayDao {



    public HolidayPO loadHolidayPO(String time, Connection conn) throws Exception {
        HolidayPO holidayPO = new HolidayPO();
        holidayPO.setHoliday(time);
        DatabaseSQL databaseSQL = DatabaseSQL.newInstance("81PGFSXM");
        databaseSQL.addParameter4All("time",time);
        databaseSQL.initSQL();
        HolidayPO load = MySQLDao.load(holidayPO, HolidayPO.class, conn);


        return load;
    }

}
