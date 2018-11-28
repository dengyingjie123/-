package com.youngbook.dao.system;

import com.youngbook.entity.po.calendar.EventPO;

import java.sql.Connection;
import java.util.List;

public interface ICalendarDao {

    List<EventPO> getEventPO(String intervalStart, Connection conn) throws Exception;
}
