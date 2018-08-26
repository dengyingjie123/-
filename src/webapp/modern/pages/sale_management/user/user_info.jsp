<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%
    if (Config.getLoginUserInSession(request) == null) {
        response.sendRedirect(Config.getModernSaleManagementPages() + "/system/login.jsp");
        return;
    }
%>
<%
    UserPO loginUser = Config.getLoginUserInSession(request);
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
    <script type="application/javascript">
        $(document).ready(function() {
            $('.home-recommend-ct').bind('click',function(){
                // var s = JSON.stringify(this);
                var s = $(this).attr('data-options');
                var j = eval("(" + s + ")");
                alert(j['a']);
            });
        });
    </script>
</head>
<body>
<div class="easyui-navpanel">
    <section class="myaccount-personinfo">
        <div class="custer-details">
            <div class="arrow-location" onclick="history.go(-1)"><i class="icon-arrow1"></i></div>
            <div class="person-info">
                <img class="person-img" src="<%=Config.getWebRoot()%>/modern/images/person-img.png">
                <span class="name"><%=loginUser.getName()%></span>
                <span class="industry">行业：金融</span>
                <div class="ranking clearfix"><span class="name">全球排名：</span><i class="count">100</i></div>
            </div>

        </div>
        <div class="home-recommend-ct">

            <ul class="cutmer-data lbBox pd25">
                <li>
                    <i class="wait-ico14 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">我的手机号</em>
                    <%--<i class="icon-arrow"></i>--%>
                    <span class="info-phone"><%=loginUser.getMobile()%></span>
                </li>
                <%--<li>--%>
                    <%--<i class="wait-ico9 cstmer lineBlock"></i>--%>
                    <%--<em class="myitem-txt lineBlock">我的银行卡</em>--%>
                    <%--<i class="icon-arrow"></i>--%>
                    <%--<span class="info-binding">未绑定</span>--%>
                <%--</li>--%>
                <li onclick="fm.goto('user_password.jsp');">
                    <i class="wait-ico10 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">我的密码</em>
                    <i class="icon-arrow"></i>
                </li>
                <%--<li>--%>
                    <%--<i class="wait-ico11 cstmer lineBlock"></i>--%>
                    <%--<em class="myitem-txt lineBlock">我的地址</em>--%>
                    <%--<i class="icon-arrow"></i>--%>
                <%--</li>--%>
                <li>
                    <i class="wait-ico12 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">我的推荐码</em>
                    <i class="info-phone"><%=loginUser.getReferralCode()%></i>
                </li>
            </ul>
        </div>
    </section>
    <%=Config.getModernSaleManagementFooter("00010")%>
</div>
</body>
</html>