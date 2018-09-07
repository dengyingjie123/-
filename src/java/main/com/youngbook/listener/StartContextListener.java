package com.youngbook.listener;

import com.youngbook.common.utils.ClassUtil;
import com.youngbook.entity.app.mapper.AppServiceMapperInvokeInfo;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人：quan.zeng
 * 创建时间：2015/11/28
 * 描述：web容器启动监听
 * StartContextListener:
 *
 * 修改人：quan.zeng
 * 修改时间：2015/12/1
 * 描述：mapper通过bean实现
 *
 * 修改人：quan.zeng
 * 修改时间：2015/12/3
 * 描述：
 *
 */
public class StartContextListener implements ServletContextListener {

    public static final String packageName = "com.youngbook.action.api";

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext application = arg0.getServletContext();

        Map<String,AppServiceMapperInvokeInfo> mapper = doScan(packageName);
        //把mapper信息放到全局application域中
        application.setAttribute("APP_SERVICE_MAPPER",mapper);

    }

    private Map<String,AppServiceMapperInvokeInfo> doScan(String packageName){
        List<Class<?>> classes = ClassUtil.getClasses(packageName);

        Map<String,AppServiceMapperInvokeInfo> map = new HashMap<String,AppServiceMapperInvokeInfo>();

        return map;
    }

}