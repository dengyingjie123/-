package com.youngbook.common.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Map;

public class TransactionInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation ai) throws Exception {

        //System.out.println("------------  " + ai.getAction().getClass().getName());
       //System.out.println("------------  " + ai.getProxy().getMethod());

//        String methodName = ai.getProxy().getMethod();
//        Class clazz = ai.getAction().getClass();
//
//        Method method = clazz.getMethod(methodName);
//        Transactional transactional = method.getAnnotation(Transactional.class);
//
//        if (transactional != null) {
//            Connection conn = Config.getConnection();
//
//            if (transactional.need()) {
//                conn.setAutoCommit(false);
//            }
//
//            String r = null;
//            BaseAction action = (BaseAction)ai.getAction();
//
//            if (action.getMylog() == null) {
//                action.setMylog(new MyLog());
//            }
//
//            if (action != null) {
//                action.setConnection(conn);
//                try {
//                    r = ai.invoke();
//                    if (transactional.need()) {
//                        conn.commit();
//                    }
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                    action.getMylog().newLog(e);
//                    if (conn != null && transactional.need()) {
//                        conn.rollback();
//                    }
//                    r = "transaction_error";
//                }
//                finally {
//
//                    action.getMylog().saveLogs();
//
//                    if (conn != null) {
//                        conn.close();
//                    }
//
//                    MyLog.debug("关闭数据库链接");
//                }
//                return r;
//            }
//        }

        return ai.invoke();
    }
}
