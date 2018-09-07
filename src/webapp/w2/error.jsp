<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="info.jsp"%>
<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
    } else {
       /* String url = Config.getWebRoot() + Config.Web_URL_Login;
        response.sendRedirect(url);
        return;*/
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>出错了</title>

<link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
<link href="<%=Config.getWebRoot()%>/w2/css/index/error.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

</head>

<body>

    <jsp:include page="/w2/top.jsp"/>

    <div id="position" class="w1000">
    	
    </div>
    
    <div id="container">
 
 		<img src="<%=Config.getWebRoot()%>/w2/img/cry.png" />
        <div id="errorMessage">操作失败：系统繁忙，请稍候再试。</div>
        <div>
            <a id="goToBack" herf="#" onclick="window.history.go(-1)" class="fc-primary" style="cursor: pointer;">返回</a>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">进入首页</a></div>
 
    </div>

    <jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
