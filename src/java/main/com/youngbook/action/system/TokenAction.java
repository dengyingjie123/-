package com.youngbook.action.system;

import com.sun.javafx.fxml.expression.Expression;
import com.youngbook.action.BaseAction;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.system.SmsType;
import com.youngbook.entity.po.system.TokenBizType;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.service.system.SmsService;
import com.youngbook.service.system.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

/**
 * Created by Lee on 2016/10/12.
 */
public class TokenAction extends BaseAction {

    @Autowired
    TokenService tokenService;

    @Autowired
    SmsService smsService;


    public String getMobileCode() throws Exception {

        String _system = getHttpRequestParameter("_s");

        if (StringUtils.isEmpty(_system)) {
            MyException.newInstance("参数输入不完整", "_s=" + _system).throwException();
        }

        String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");
        if (_system.equals("2")) {
            signature = Config.getSystemConfig("system.sms.identityCode.signature.financeCircle");
        }
        else if (_system.equals("3")) {
            signature = Config.getSystemConfig("system.oa.identityCode.signature.ph");
        }
        else if (_system.equals("4")) {
            signature = Config.getSystemConfig("system.sms.identityCode.signature.dehecircle");
        }

        Connection conn = getConnection();
        HttpServletRequest request = getRequest();
        String mobileRegister = request.getParameter("mobile");


        if (StringUtils.isEmpty(mobileRegister)) {
            getResult().setMessage("请输入手机号码");
            request.setAttribute("returnObject", getResult());
            return "info";
        }


        if (!StringUtils.checkIsChineseMobile(mobileRegister)) {
            MyException.newInstance("请输入正确的手机号", "mobile=" + mobileRegister).throwException();
        }

        TokenPO tokenPO = tokenService.newToken(mobileRegister, TokenBizType.MobileCode, HttpUtils.getClientIPFromRequest(getRequest()), getConnection());

        // 成功后去调用System_Sms服务，插入数据

        String id = "WEB注册";
        String name = "WEB注册";
        String subject = Config.getSystemVariable("system.oa.sms.identityCode.subject");
        String content = Config.getSystemConfig("web.code.view.content.before") + tokenPO.getToken() + Config.getSystemConfig("web.code.view.content.after");
        Integer type = SmsType.TYPE_IDENTIFY_CODE;
        smsService.insertSMS(id, name, mobileRegister, subject, content, signature, type, conn);

        getResult().setMessage("OK");

        // md5 token
        tokenPO.setToken("");

        getResult().setReturnValue(tokenPO);

        return SUCCESS;
    }


    public String newToken() throws Exception {

        String bizId = getHttpRequestParameter("bizId");
        String bizType = getHttpRequestParameter("bizType");

        if (StringUtils.isEmptyAny(bizId, bizType)) {
            MyException.newInstance("传入参数有误", "bizId=" + bizId + "&bizType=" + bizType).throwException();
        }


        String ip = HttpUtils.getClientIPFromRequest(getRequest());
        TokenPO token = tokenService.newToken(bizId, bizType, ip, getConnection());

        getResult().setReturnValue(token.getToken());

        return SUCCESS;
    }

    public String validateToken() throws Exception {
        String tokenString = getHttpRequestParameter("token");
        String customerId = getHttpRequestParameter("customerId");
        String ip = HttpUtils.getClientIPFromRequest(getRequest());
        TokenPO token = new TokenPO();
        token.setIp(ip);
        token.setBizId(customerId);
        token.setToken(tokenString);

        try {
            tokenService.checkAndRenewToken(token, getConnection());
            getResult().setReturnValue("1");
        }
        catch (Exception e) {
            getResult().setReturnValue("0");
        }

        return SUCCESS;
    }
}
