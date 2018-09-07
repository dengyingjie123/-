package com.youngbook.common.utils;

import com.youngbook.common.MyException;

import java.util.List;

/**
 * Created by Lee on 2/8/2017.
 */
public class ObjectUtils {

    public static boolean isListEmpty(List list) {

        if (list == null) {
            return true;
        }

        if (list != null && list.size() == 0) {
            return true;
        }

        return false;
    }

    public static boolean isAssignableFrom(Class clazz, Class superClazz) {
        if (superClazz.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    public static <T> boolean isInstance(Object object, Class<T> expectedType) {

        if (object == null) {
            return false;
        }

        return expectedType.isInstance(object);
    }

    public static <T> T convert(Object object, Class<T> expectedType) throws Exception {

        if (!ObjectUtils.isInstance(object, expectedType)) {
            MyException.newInstance("对象转换失败", MyException.getData(object)).throwException();
        }

        T o = (T)object;
        return o;
    }
}
