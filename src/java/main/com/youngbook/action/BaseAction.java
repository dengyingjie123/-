package com.youngbook.action;

import com.opensymphony.xwork2.ActionSupport;
import com.youngbook.common.*;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.ObjectUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.service.system.LogService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

import static com.youngbook.common.config.Config.SESSION_LOGINUSER_STRING;

/**
 * User: Lee
 * Date: 14-5-22
 *
 * 修改人：quan.zeng
 * 修改时间：2015/12/3
 * 描述：添加requestObject属性，添加构造函数
 */
public class BaseAction extends ActionSupport {

    private ReturnObject result = null;

    private Connection connection = null;

    private RequestObject requestObject = null;

    private TokenPO tokenPO = null;

    public BaseAction() {
        try {
            HttpServletRequest request = this.getRequest();
            if (request != null) {
                requestObject = HttpUtils.getInstanceFromRequest(request, RequestObject.class);
            }
        }
        catch (Exception e) {
            LogService.log(e);
        }
    }

    public BaseAction(RequestObject requestObject,Connection conn){
        this.requestObject = requestObject;
        this.connection = conn;
    }

    public String getHttpRequestParameter(String parameter) throws Exception {
        return HttpUtils.getParameter(getRequest(), parameter);
    }

    public TokenPO getToken() throws Exception {
        /**
         *
         * 检查Request的属性里有没有Token实例
         *
         *
         * 有则返回，无则返回null
         *
         *
         */

        /*String token = this.getRequest().getParameter("token");
        if(!StringUtils.isEmpty(token)){
            tokenPO = new TokenPO();
            tokenPO.setToken(token);
            return tokenPO;
        }*/
        Object tokenObj = this.getRequest().getAttribute("tokenPO");
        if(tokenObj!=null){
            tokenPO = (TokenPO)tokenObj;
            return tokenPO;
        }
        return null;
    }


    public boolean hasPermission(String permissionName) {
        String permissionString = (String) getRequest().getSession().getAttribute("PERMISSION_STRING");
        if (!StringUtils.isEmpty(permissionString)) {
            return permissionString.contains(permissionName);
        }
        return false;
    }

    public Permission getPermission() {
        Permission permission = new Permission();
        String permissionString = (String) getRequest().getSession().getAttribute("PERMISSION_STRING");
        if (!StringUtils.isEmpty(permissionString)) {
            permission.setPermission(permissionString);
        }


        return permission;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        try {
            request = ServletActionContext.getRequest();
        }
        catch (Exception e) {
            MyException.quiet(e);
        }


        return request;
    }

    public HttpServletResponse getResponse() {
        HttpServletResponse response = ServletActionContext.getResponse();
        return response;
    }

    public UserPO getLoginUser() throws Exception {

        try {
            UserPO user = ObjectUtils.convert(getRequest().getSession().getAttribute("loginPO"), UserPO.class);

            return user;
        }
        catch (Exception e) {
            MyException.newInstance("获取登录用户失败", e.getMessage()).throwException();
        }

        return null;
    }

    public CustomerPersonalPO getLoginCustomer() throws Exception{
        CustomerPersonalPO customer = null;
        try {
            customer = (CustomerPersonalPO) getRequest().getSession().getAttribute(SESSION_LOGINUSER_STRING);
        }
        catch (Exception e) {
            MyException.deal(e);
        }

        return customer;
    }

    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public RequestObject getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(RequestObject requestObject) {
        this.requestObject = requestObject;
    }
}