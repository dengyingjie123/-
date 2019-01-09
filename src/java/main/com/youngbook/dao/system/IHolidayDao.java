package com.youngbook.dao.system;

import com.youngbook.entity.po.HolidayPO;


import java.sql.Connection;



public interface IHolidayDao {


    public HolidayPO loadHolidayPO(String time, Connection conn) throws Exception;
}
