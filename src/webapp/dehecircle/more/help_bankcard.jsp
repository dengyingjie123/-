<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
    String loginCustomerId = Config.getLoginCustomerInSession(request).getId();
%>

<div data-page="help_pay" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">绑定银行卡</div>
            <div class="right">
            </div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="content-block">
            &nbsp;&nbsp;&nbsp;&nbsp;
            1. 单击“我的账号”，在“基本信息”里选择“银行卡”
            <br>
            <img src="<%=Config.getWebPH()%>/include/img/help001.jpg" />
            <br><br>
            &nbsp;&nbsp;&nbsp;&nbsp;
            2. 在弹出的界面中，单击右上角“+”添加银行卡信息
            <br><br>
            <img src="<%=Config.getWebPH()%>/include/img/help002.jpg" />
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>