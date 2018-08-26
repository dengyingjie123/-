package com.youngbook.common.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Security;
import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.service.system.LogService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;


public class SecurityInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation ai) throws Exception {

        String message = "";
        boolean isPass = true;

        String methodName = ai.getProxy().getMethod();
        Class clazz = ai.getAction().getClass();
        String actionUrl = clazz.getName() + "." + methodName;

        Struts2Utils.getActionReturnType(ai);

        // 初始化令牌
        // Map session = ai.getInvocationContext().getSession();
        String stokenKeyName = "STOKEN";
        Map session = ai.getInvocationContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String stoken = (String)session.get(stokenKeyName);
        if (StringUtils.isEmpty(stoken)) {
            stoken = IdUtils.getUUID32();
            session.put(stokenKeyName, stoken);
        }

        // 获取移动端基础参数
        String appVersion = HttpUtils.getParameter(request, "appVersion");
        if(StringUtils.isEmpty(appVersion)) {
            appVersion = "";
        }
        String appOSVersion = HttpUtils.getParameter(request, "version");
        if(StringUtils.isEmpty(appOSVersion)) {
            appOSVersion = "";
        }
        String appDeviceType = HttpUtils.getParameter(request, "manufacturer");
        if(StringUtils.isEmpty(appDeviceType)) {
            appDeviceType = "";
        }
        String appDeviceNo = HttpUtils.getParameter(request, "model");
        if(StringUtils.isEmpty(appDeviceNo)) {
            appDeviceNo = "";
        }
        String appOS = HttpUtils.getParameter(request, "platform");
        if(StringUtils.isEmpty(appOS)) {
            appOS = "";
        }
        String appDeviceUUID = HttpUtils.getParameter(request, "appDeviceUUID");
        if(StringUtils.isEmpty(appDeviceUUID)) {
            appDeviceUUID = "";
        }
        String appCordovaVersion = HttpUtils.getParameter(request, "cordova");
        if(StringUtils.isEmpty(appCordovaVersion)) {
            appCordovaVersion = "";
        }

        Object action = ai.getAction();
        boolean isBaseActionInstance = BaseAction.class.isAssignableFrom(action.getClass());

        Connection conn = null;
        if (isBaseActionInstance) {
            BaseAction baseAction = (BaseAction)action;
            conn = baseAction.getConnection();
            boolean hasException = false;


            // 检查注解上的安全验证
            Method method = clazz.getMethod(methodName);
            Security security = method.getAnnotation(Security.class);

            if (security != null) {

                // 网页需要登录
                if (security.needWebLogin()) {

                    if (!session.containsKey(Config.SESSION_LOGINUSER_STRING)) {
                        String url = Config.getWebRoot() + Config.Web_URL_Login;
                        response.sendRedirect(url);
                        MyException.newInstance(Config.getWords("w.gloabl.error.login"), "网站用户登录失败").throwException();
                    }
                }

                // 网页需要验证网页验证码
                if (security.needWebCode()) {
                    boolean isPassWebCode = Config.checkWebCode(request, conn);
                    if (!isPassWebCode) {
                        MyException.newInstance(Config.getWords("w.gloabl.error.webcode"), "验证码输入错误").throwException();
                    }
                }

                //需要token校验
                if(security.needToken()){
                    Config.checkToken(request, conn);
                }

                // 手机端需要验证手机验证码
                if (security.needMobileCode()) {
                    String mobile = request.getParameter("mobile");
                    if(StringUtils.isEmpty(mobile)) {
                        MyException.newInstance(Config.getWords("w.gloabl.error.webcode"), "缺少参数").throwException();
                    }

                    String code = request.getParameter("mobileCode");
                    boolean isPassWebCode = Config.checkMobileCode(mobile, code, conn);
                    if (!isPassWebCode) {
                        MyException.newInstance(Config.getWords("w.gloabl.error.webcode"), "验证码输入错误").throwException();
                    }
                }
            }

            LogPO logPO = new LogPO();

            try {

                // 初始化安全数据
                String ip = Config.getIP(request);
                logPO.setIP(ip);
                logPO.setOperateTime(TimeUtils.getNow());



                logPO.setUrl(actionUrl);

                if (Config.getLoginUserInSession(request) != null) {
                    logPO.setOperatorId(Config.getLoginUserInSession(request).getId());
                    logPO.setOperatorName(Config.getLoginUserInSession(request).getName());
                }
                else if (Config.getLoginCustomerInSession(request) != null) {
                    logPO.setOperatorId(Config.getLoginCustomerInSession(request).getId());
                    logPO.setOperatorName(Config.getLoginCustomerInSession(request).getName());
                }





//                StringBuffer sbParameters = new StringBuffer();
//                sbParameters.append("{");
//                sbParameters.append("'").append("").append("':");
//                sbParameters.append("'").append("").append("'");
//                sbParameters.append("}");

//                logPO.setParameters(sbParameters.toString());
                logPO.setMachineMessage(HttpUtils.getParametersStringValue(request));

                // 检验安全内容
                // 检查访问频率限制
//                isPass = this.checkGloablFrequenceLimit(stoken, conn);
//                if (!isPass) {
//                    slog.setFailType(SecurityLogFailType.FailFrequenceLimit);
//                }
//
//                slog.setOperateResult(isPass ? 1 : 0);
//
//                // 没有通过安全检查
//                if (!isPass) {
//                    message = Config.getWords("w.gloabl.error.frequence.limit");
//
//                    if (Struts2Utils.getActionReturnType(ai).equalsIgnoreCase("html")) {
//                        ReturnObject returnObject = new ReturnObject();
//                        returnObject.setMessage(message);
//                        request.setAttribute("returnObject", returnObject);
//                        return "info";
//                    }
//                    else {
//                        throw new Exception(message);
//                    }
//
//                }

                // 通过后调用后续功能
                return ai.invoke();
            }
            catch (Exception e) {
                hasException = true;

                if (hasException) {
                    LogService.debug("SecurityInterceptor(): 打开数据库连接", this.getClass());
                    conn = Config.getConnection();
                }

                throw e;
            }
            finally {

                /**
                 * 处理日志里的参数和内容
                 */
                if (!StringUtils.isEmpty(logPO.getMachineMessage()) && logPO.getMachineMessage().length() > 1000) {
                    logPO.setMachineMessage(logPO.getMachineMessage().substring(0, 1000));
                }

                if (!StringUtils.isEmpty(logPO.getMachineMessage()) && logPO.getMachineMessage().length() > 1000) {
                    logPO.setMachineMessage(logPO.getMachineMessage().substring(0, 1000));
                }

                MySQLDao.insertOrUpdate(logPO, logPO.getOperatorId(), conn);

                if (hasException && conn != null) {
                    LogService.debug("SecurityInterceptor(): 关闭数据库连接",this.getClass());
                    Database.close(conn);
                }
            }
        }

        return null;

    }


    /**
     * SQL语句中，查出来等于-1表示没有达到足够的统计次数
     * @param stoken
     * @param conn
     * @return
     * @throws Exception
     */
    private boolean checkGloablFrequenceLimit(String stoken, Connection conn) throws Exception {

        // 判断是否需要进行检查，查看配置
        String switchValue = Config.getSecurityGloablFrequenceLimitSwitch();
        if (!switchValue.equalsIgnoreCase("on")) {
            return true;
        }

        // 检查频率
        int frequenceLimitTimes = Config.getSecurityGloablFrequenceLimitTimes();
        int frequenceLimitAverage = Config.getSecurityGloablFrequenceLimitAverage();

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append(" IF (");
        sbSQL.append("     ISNULL(frequence) ,-1,");
        sbSQL.append("     frequence");
        sbSQL.append(" ) frequence");
        sbSQL.append(" FROM");
        sbSQL.append("     (");
        sbSQL.append("         SELECT");
        sbSQL.append("             TIMESTAMPDIFF(");
        sbSQL.append("                 SECOND,");
        sbSQL.append("                 MIN(OperateTime),");
        sbSQL.append("                 MAX(OperateTime)");
        sbSQL.append("             ) / "+frequenceLimitTimes+" frequence");
        sbSQL.append("         FROM");
        sbSQL.append("             (");
        sbSQL.append("                 SELECT");
        sbSQL.append("                     *");
        sbSQL.append("                 FROM");
        sbSQL.append("                     System_SecurityLog");
        sbSQL.append("                 WHERE");
        sbSQL.append("                     stoken = '"+stoken+"'");
        sbSQL.append("                 HAVING (select count(*) >= "+frequenceLimitTimes+"  from system_securitylog where stoken='"+stoken+"')");
        sbSQL.append("                 ORDER BY");
        sbSQL.append("                     OperateTime DESC");
        sbSQL.append("                 LIMIT " + frequenceLimitTimes);
        sbSQL.append("             ) t");
        sbSQL.append("     ) t2");

        double frequenceResult = MySQLDao.query4DoubleValue(sbSQL.toString(), "frequence", conn);

        if (frequenceResult > 0 && frequenceResult < frequenceLimitAverage) {
            return false;
        }
        return true;
    }
}
