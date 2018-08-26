<%@ page import="com.youngbook.common.ReturnObject" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
    }

%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>提示</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/index/info.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

</head>

<body>
<jsp:include page="/w2/top.jsp"/>

<div id="position" class="w1000">

</div>

<div id="container">


    <div >
        <div >
            <div >
                <h1><span ></span></h1>

                <div ><span><strong>帮助</strong></span></div>
            </div>
            <div >
                <span ></span>
            </div>
            <div >
                帮助正文
            </div>
        </div>
    </div>
</div>

<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
