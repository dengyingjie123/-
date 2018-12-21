<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String version = request.getParameter("version");
    System.out.println("version: " + version);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>德恒普惠</title>
    <!-- Path to Framework7 Library CSS-->
    <!--<link rel="stylesheet" href="include/framework/framework7-1.6.5/css/framework7.material.min.css">-->
    <script type="text/javascript" src="<%=Config.getWebPH()%>/include/js/config.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <link rel="stylesheet" href="<%=Config.getWebPH()%>/include/framework/framework7-1.6.5/css/framework7.material.css">
    <link rel="stylesheet" href="<%=Config.getWebPH()%>/include/framework/framework7-1.6.5/css/framework7.material.colors.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="<%=Config.getWebPH()%>/include/css/my-app.css">
    <!--<link rel="stylesheet/less" type="text/css" href="include/less/my-app.less">-->
    <link rel="stylesheet" href="<%=Config.getWebPH()%>/include/css/material-icons.css">
</head>
<body>
<!-- Status bar overlay for fullscreen mode-->
<div class="statusbar-overlay"></div>
<!-- Panels overlay-->
<div class="panel-overlay"></div>
<!-- Views, and they are tabs-->
<!-- We need to set "toolbar-through" class on it to keep space for our tab bar-->
<div class="views tabs toolbar-through">
    <!-- Your first view, it is also a .tab and should have "active" class to make it visible by default-->
    <div id="view-order-pay-success" class="view view-main tab active">

        <div class="pages navbar-through">
            <!-- Page, data-page contains page name-->
            <div data-page="login-screen-embedded" class="page">
                <div class="page-content login-screen-content">


                    <div style="margin: 0 auto; width:100%; text-align: center"><img src="<%=Config.getWebRoot()%>/include/images/success.png" style="width: 120px;height:120px;" /></div>
                    <br><br><br>
                    <div style="margin: 0 auto; width:100%; text-align: center">您的支付成功，银行结算需要约3分钟时间</div>
                    <br><br><br>
                    <div class="content-block"><a href="<%=Config.getWebPH()%>/index.jsp?show=2" onclick="goto()" class="button button-fill button-raised">返回</a></div>
                    <br><br>
                    <div style="margin: 0 auto; width:100%; text-align: center"><img src="<%=Config.getWebPH()%>/include/img/login_logo.png" style="width: 50px;height:52px;" /></div>
                </div>
            </div>
        </div>

    </div>
    <!-- Second view (for second wrap)-->

</div>
<!-- Path to Framework7 Library JS-->
<script type="text/javascript" src="<%=Config.getWebPH()%>/include/framework/framework7-1.6.5/js/framework7.js"></script>
<!-- Path to your app js-->
<script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
<script type="text/javascript" src="<%=Config.getWebPH()%>/include/js/pages.js"></script>
<script type="text/javascript" src="<%=Config.getWebPH()%>/include/js/my-app.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
<script>
    function goto(){
        window.location = "<%=Config.getWebPH()%>/index.jsp?show=2";
    }
</script>
</body>
</html>