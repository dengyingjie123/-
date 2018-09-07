package com.youngbook.common.wf.common;

import com.youngbook.common.utils.Utils;
import com.youngbook.common.wf.engines.IBizDao;
import com.youngbook.dao.MySQLDao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class WorkflowDao {
    public static  HashMap dataSnapShot(IBizDao object, Connection conn) throws Exception {

        HashMap map = new HashMap();

        Class clazz = object.getClass();

        object = (IBizDao) MySQLDao.load(object, clazz, conn);

        if (object == null) {
            return map;
        }

        for(Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();

            String getter = "get" + firstLetter + fieldName.substring(1);

            Method getMethod = Utils.getMethod(clazz, getter, new Class[]{});

//            if (getMethod == null) {
//                continue;
//            }

            Object value = getMethod.invoke(object, new Object[] {});

            if (value != null) {
                map.put(fieldName.toUpperCase(), String.valueOf(value));
            }
        }

        return map;
    }
}
