<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.service.wechat.WeChatService" %>
<%@ page import="com.youngbook.entity.po.wechat.UserInfoPO" %>
<%@ page import="com.youngbook.service.customer.CustomerPersonalService" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%
    String userInfoId = "";
    if (!StringUtils.isEmpty(request.getParameter("userInfoId"))) {
        userInfoId = request.getParameter("userInfoId");
    }

    WeChatService weChatService = Config.getBeanByName("weChatService", WeChatService.class);
    UserInfoPO userInfoPO = weChatService.load(userInfoId);

    CustomerPersonalPO guest = new CustomerPersonalPO();
    guest.setId("0000");
    guest.setLoginName(userInfoPO.getNickname());
    guest.setName(userInfoPO.getNickname());
    guest.setNationId(userInfoId);
    CustomerPersonalService customerPersonalService = Config.getBeanByName("customerPersonalService", CustomerPersonalService.class);
    customerPersonalService.loginFinish(guest, session);

    response.sendRedirect(Config.getModernCustomerManagementPages() + "/production/home.jsp");

%>
