package com.youngbook.common.interceptor;

import com.allinpay.ets.client.StringUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.Struts2Utils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.service.system.LogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;


/**
 * 修改：李昕骏
 * 时间：2015年8月23日 13:24:51
 * 内容：
 * 将事务处理调整到这里来统一分配
 */
public class DefaultInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation ai) throws Exception {

        String r = "";

        String methodName = ai.getProxy().getMethod();
        Object action = ai.getAction();
        boolean isInstance = BaseAction.class.isAssignableFrom(action.getClass());



        // 全局的数据库连接
        Connection conn = null;

        try {
            if (isInstance) {

                BaseAction baseAction = (BaseAction)action;

//                String platform = baseAction.getRequest().getParameter("platform");
//                if(!StringUtil.isEmpty(platform) && !platform.equalsIgnoreCase("Android")) {
//                    throw new Exception("系统繁忙");
//                }

                // 初始化数据库事务
                // MyLog.debug("获得数据库连接");
                conn = Config.getConnection();
                conn.setAutoCommit(false);
                baseAction.setConnection(conn);


                // 初始化返回值
                ReturnObject result = new ReturnObject();
                baseAction.setResult(result);


                // 初始化日志记录

                try {
                    baseAction.getResult().setMessage("操作成功");
                    baseAction.getResult().setCode(ReturnObject.CODE_SUCCESS);

                    // 写文件日志
                    LogService.log2File(baseAction.getClass());

                    r = ai.invoke();

                    // 提交数据库
                    LogService.debug("提交数据库事务", this.getClass());
                    conn.commit();

                    return r;
                }
                catch (Exception e) {

                    e.printStackTrace();

                    LogService.error(e);

                    int code = ReturnObject.CODE_EXCEPTION;
                    String ip = Config.getIP(baseAction.getRequest());
                    String peopleMessage = "";
                    String machineMessage = "";
                    String returnValue = "";

                    // boolean isInstance = BaseAction.class.isAssignableFrom(action.getClass());
                    boolean isMyExceptionInstance = MyException.class.isAssignableFrom(e.getClass());

                    LogPO logPO = new LogPO();

                    if (isMyExceptionInstance) {
                        MyException myException = (MyException) e;

                        code = myException.getCode();

                        peopleMessage = "操作失败，" + myException.getPeopleMessage();

                        machineMessage = myException.getMachineMessage();
                        returnValue = myException.getReturnValue();


                        logPO.setPeopleMessage(peopleMessage);
                        logPO.setMachineMessage(machineMessage);

                    }
                    else {
                        peopleMessage = "操作失败。";
                        machineMessage = e.getMessage();

                    }



                    String className =  ai.getAction().getClass().getName();
                    logPO.setName("系统异常");
                    logPO.setMachineMessage(logPO.getMachineMessage() + "["+className+"]["+e.getClass().getName()+"]["+e.getMessage()+"]");
                    MySQLDao.insertOrUpdate(logPO);

                    LogService.debug("有异常：【"+peopleMessage+"】【"+machineMessage+"】", this.getClass());


                    /**
                     * 发送外部消息
                     */
                    Config.sendMessage(Config.APP_NAME + " 系统异常",  "【"+peopleMessage+"】【"+machineMessage+"】");

                    // 记录日志
//                    mylog.setHasException(true);
//                    mylog.newLog().addName(peopleMessage)
//                            .addContent(machineMessage)
//                            .addIP(ip)
//                            .addURL(this.getClass().getName() + ":" + methodName + "()")
//                            .addType(logType);


                    // 处理返回值
//                    baseAction.getResult().setMessage(mylog.getLastLogMessage());
                    baseAction.getResult().setCode(code);
                    baseAction.getResult().setReturnValue(returnValue);
                    // baseAction.getResult().setException(e);

                    // 回滚数据库
                    LogService.debug("回滚数据库", this.getClass());
                    if (conn != null) {
                        conn.rollback();
                    }



                    if (Struts2Utils.getActionReturnType(ai).equalsIgnoreCase("html")) {
                        ReturnObject returnObject = new ReturnObject();
                        returnObject.setMessage(peopleMessage);
                        baseAction.getRequest().setAttribute("returnObject", returnObject);
                        return "info";
                    }
                    else {
                        baseAction.getResult().setMessage(peopleMessage);
                        baseAction.getResult().setCode(code);
                    }

                }
                finally {
                    Database.close(conn);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }


        return "success";

    }
}
