<%@ page import="com.youngbook.common.ReturnObject" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
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
 
 		<img src="<%=Config.getWebRoot()%>/w2/img/smile.png" />
        <div id="errorMessage">购买产品请求已接受，正在等待银行确认，稍后检查订单状态。</div>
        <div>
            <a id="goToIndex" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="fc-primary">进入首页</a>
        </div>
    </div>

   <jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
