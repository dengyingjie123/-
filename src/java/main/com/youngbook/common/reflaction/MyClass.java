package com.youngbook.common.reflaction;

import com.youngbook.common.utils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/24/15
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyClass {

    public static <T> Object getValueByFieldName(T object, String fieldName) throws Exception {


        for(Field field : object.getClass().getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + field.getName().substring(1);

                Method getMethod = Utils.getMethod(object.getClass(), getMethodName, new Class[]{});
                Object value = getMethod.invoke(object, new Object[] {});
                return value;
            }
        }
        return null;
    }
}
