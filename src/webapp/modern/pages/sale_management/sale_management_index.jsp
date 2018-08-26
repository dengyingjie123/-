<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%
    if (Config.getLoginUserInSession(request) == null) {
        response.sendRedirect(Config.getModernSaleManagementPages() + "/system/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>产品列表</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/mobile.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot()%>/modern/css/public.css">
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">

<div class="easyui-navpanel">
    <div class="header-all">
        <span class="hd-news" onclick="javascript:history.go(-1);">
            <img src="<%=Config.getWebRoot()%>/modern/images/allow-fx.png">
        </span>
        <h3>我的功能菜单</h3>
            <span class="hd-cfact-search">
                <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
            </span>
    </div>

    <section class="pro-details">
        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>客户报表</h3>
            <ul class="pro-info">
                <li><span class="data" onclick="fm.goto('<%=Config.getModernSaleManagementPages()%>/report/report_customer_new.jsp');">每日新增客户报表</span></li>
            </ul>
            <ul class="pro-info">
                <li><span class="data" onclick="fm.goto('<%=Config.getModernSaleManagementPages()%>/report/report_payment_plan_month.jsp');">兑付计划</span></li>
            </ul>
            <ul class="pro-info">
                <li><span class="data" onclick="fm.goto('<%=Config.getModernSaleManagementPages()%>/report/report_customer_new.jsp');">陆续更新……</span></li>
            </ul>
        </div>
    </section>
    <%=Config.getModernSaleManagementFooter("00001")%>
</div>
</body>
</html>