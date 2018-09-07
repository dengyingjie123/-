package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.system.CodePO;
import com.youngbook.entity.po.system.CodeType;
import com.youngbook.service.system.CaptchaService;
import com.youngbook.service.system.UserService;
import org.apache.struts2.ServletActionContext;
import org.patchca.service.Captcha;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.sql.Connection;

public class CaptchaAction extends BaseAction {

    @Autowired
    UserService userService;

    private InputStream inputStream = null;

    /**
     * 交易平台请求的 Action，生成验证码
     * 前提是网站的 Customer 已经登录
     * 生成验证码后首先把验证信息写如到 system_code 中，并设定过期时间为一分钟
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * 在页面中直接通过 <img src='/core/w/system/...action' /> 即可
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public String buildCaptcha() throws Exception {
        HttpSession session = getRequest().getSession();

        try {
            // 验证码
            Captcha captcha = VarificationUtils.varificationCodeGenerator(5, 5, null, null, null, 0);
            String code = captcha.getChallenge();

            String nowTime = TimeUtils.getNow();
            // 写入数据库，限时为 1 分钟
            CodePO codePO = new CodePO();
            codePO.setId(IdUtils.getUUID32());
            codePO.setCode(code);
            codePO.setCreateTime(nowTime);
            // 用途代号
            int codeType = CodeType.UNKNOWN;
            try {
                String strCodeType = HttpUtils.getParameter(getRequest(), "u");
                codeType = Integer.parseInt(strCodeType);
            }
            catch (Exception e) {
                MyException.newInstance("无法获得验证码用途").throwException();
            }

            codePO.setType(codeType);
            codePO.setAvailableTime(nowTime);
            codePO.setExpiredTime(TimeUtils.getTime(nowTime, 1, "MINUTE"));
            if (session.getAttribute("loginUser") != null) {
                CustomerPersonalPO personalPO = (CustomerPersonalPO) session.getAttribute("loginUser");
                codePO.setUserId(personalPO.getId());
            } else {
                UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"), getConnection());
                if (user != null) {
                    codePO.setUserId(user.getId());
                }
            }
            codePO.setiP(getRequest().getRemoteAddr());
            MySQLDao.insert(codePO, getConnection());
            // 放入 Session
            session.setAttribute("varificationCode", code);
            // 输出
            inputStream = VarificationUtils.convertImageToStream(captcha);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，检测验证码是否正确
     * 前提是网站的 Customer 已经登录
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     *
     * 作者：张舜清
     * 内容：创建代码
     * 时间：2015-7-9
     *
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 张舜清
     */
    public String checkCaptcha() throws Exception {
        String u = HttpUtils.getParameter(getRequest(), "u");
        String captcha = HttpUtils.getParameter(getRequest(), "captcha");

        if (StringUtils.isEmpty(u) || StringUtils.isEmpty(captcha)) {
            MyException.newInstance("无法检查验证码").throwException();
        }

        CaptchaService captchaService = new CaptchaService();
        if(!captchaService.validateCode(captcha, u, getConnection())) {
            // 返回标识0代表正确
            getResult().setReturnValue("0");
        }
        else{
            // 返回标识1代表正确
            getResult().setReturnValue("1");
        }

        return SUCCESS;
    }


    /**
     * 校验验证码
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public static boolean checkCaptcha(HttpServletRequest request, Connection conn) throws Exception {
        String code = request.getParameter("captcha");
        String u = request.getParameter("u");

        CaptchaService service = new CaptchaService();
        return service.validateCode(code, u, conn);
    }


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
