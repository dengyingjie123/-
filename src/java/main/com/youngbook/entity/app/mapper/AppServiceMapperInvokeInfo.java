package com.youngbook.entity.app.mapper;

import java.lang.reflect.Method;

/**
 * Created by zq on 2015/11/30.
 */
public class AppServiceMapperInvokeInfo<T> {
    private Class<T> invokeClazz;
    private Method invokeMethod;

    public Class<T> getInvokeClazz() {
        return invokeClazz;
    }

    public void setInvokeClazz(Class<T> invokeClazz) {
        this.invokeClazz = invokeClazz;
    }

    public Method getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(Method invokeMethod) {
        this.invokeMethod = invokeMethod;
    }
}
