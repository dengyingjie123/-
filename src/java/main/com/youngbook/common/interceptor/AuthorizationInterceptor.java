package com.youngbook.common.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.SessionConfig;
import com.youngbook.common.utils.StringUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;

public class AuthorizationInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation ai) throws Exception {

        //System.out.println("------------  " + ai.getAction().getClass().getName());
       //System.out.println("------------  " + ai.getProxy().getMethod());

        String methodName = ai.getProxy().getMethod();
        Class clazz = ai.getAction().getClass();

        Method method = clazz.getMethod(methodName);

        Permission permission = method.getAnnotation(Permission.class);


        Map session = ai.getInvocationContext().getSession();


        /**
         * 客户登录
         */
        String loginUserType = (String) session.get(Config.SESSION_LOGINUSER_TYPE);

        if (StringUtils.isEmpty(loginUserType)) {
            return Action.LOGIN;
        }

        if (loginUserType.equals(SessionConfig.LOGIN_USER_TYPE_CUSTOMER_PERSONAL)) {
            return ai.invoke();
        }

        if (loginUserType.equals(SessionConfig.LOGIN_USER_TYPE_USER)) {

            String permissionString = (String) session.get("PERMISSION_STRING");

            if (StringUtils.isEmpty(permissionString)) {
                return Action.LOGIN;
            }

            // 没有限制权限
            if (permission == null) {
                return ai.invoke();
            }

            // 有权限定义，但没有设置权限名
            if (StringUtils.isEmpty(permission.require())) {
                return ai.invoke();
            }


            if (!StringUtils.isEmpty(permission.require())) {
                String [] permissionRequireArray = permission.require().split(",");

                for (int i = 0; permissionRequireArray != null && i < permissionRequireArray.length; i++) {
                    String permissionRequir = permissionRequireArray[i];

                    if (permissionString.contains(permissionRequir)) {
                        // 有权限
                        return ai.invoke();
                    }
                }
            }

            return "permission_error";
        }


        return Action.LOGIN;
    }
}
