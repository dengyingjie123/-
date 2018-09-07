<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>

<%
    String redirectURL = Config.getWebDehecircle() + "/login/login_mobile_code.jsp";
    response.sendRedirect(redirectURL);
%>
<%
//    String version = request.getParameter("version");
//    System.out.println("version: " + version);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>德合汇</title>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/common.js"></script>
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/css/base.css" />
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/css/swiper.min.css" />


    <!-- Path to Framework7 Library CSS-->
    <!--<link rel="stylesheet" href="include/framework/framework7-1.6.5/css/framework7.material.min.css">-->
    <script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/config.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/framework/<%=Config.getF7Folder()%>/css/framework7.material.css">
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/framework/<%=Config.getF7Folder()%>/css/framework7.material.colors.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/css/my-app.css">
    <!--<link rel="stylesheet/less" type="text/css" href="include/less/my-app.less">-->
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/css/material-icons.css">
</head>
<body>

<div class="login_ct">
    <div class="login_ct_head">
        <p class="text_tip" onclick="window.location='<%=Config.getWebDehecircle()%>/login/login_mobile_code.jsp'">短信验证登录</p>

    </div>
    <div class="login_ct_main">
        <div class="pt_info"><i class="user_ico"></i><input id="txt-login-mobile" name="mobile" type="number" value="13888939712" pattern="\d*" placeholder="请输入您的用户名"/></div>
        <div class="pt_info"><i class="password_ico"></i><input id="txt-login-password" type="password" name="password" value="123456y" placeholder="请输入您的密码"/></div>
        <div class="pt_info"><i class="staff_icon"></i><input  type="text" name="" value="" placeholder="请输入推荐码（选填）"/></div>
        <button id="btn-login-password" class="confirm_exchange mr_bt3">登录</button>
        <p class="agreement_text">登录即代表同意《开普乐信息服务协议》</p>
        <%--<button class="wechat_btn"></button>--%>
    </div>

</div>
<!-- Path to Framework7 Library JS-->
<script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/framework/framework7-1.6.5/js/framework7.js"></script>
<!-- Path to your app js-->

<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
<script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/pages.js"></script>
<script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/my-app.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
</body>
</html>