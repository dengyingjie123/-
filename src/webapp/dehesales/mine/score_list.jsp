<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
    String loginCustomerId = Config.getLoginCustomerInSession(request).getId();
%>

<div data-page="score-list" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">我的积分</div>
            <div class="right">
            </div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="content-block">
            尊敬的客户，您当前的积分为{{this.score}}，更多积分活动敬请期待。<br><br>
            <img src="<%=Config.getWebPH()%>/include/img/scroe_vipcard_{{this.scoreLevel}}.png" style="width:100%; height:100%" />
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>