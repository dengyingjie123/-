<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>关于开普乐</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/about/about.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

</head>

<body>
<jsp:include page="/w2/top.jsp"/>

<div id="position" class="w1000">
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">帮助中心</a> &gt; 新手必读
</div>

<div id="container">
    <div class="tabs-wrap">
        <div class="item" style="display:block">
            <h1>新手必读</h1>
        </div>
    </div>

</div>

<jsp:include page="/w2/bottom.jsp"/>
</body>
</html>
