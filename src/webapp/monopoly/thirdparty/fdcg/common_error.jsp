<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.ReturnObject" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ReturnObject returnObject = new ReturnObject();
    if (request.getAttribute("returnObject") != null) {
        returnObject = (ReturnObject)request.getAttribute("returnObject");
    }
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
    <script type="text/javascript" src="<%=Config.getWebPH()%>/include/framework/framework7-1.6.5/js/framework7.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/framework/third-party/accounting.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js?r=<%=NumberUtils.randomNumbers(5)%>"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
    <!-- Path to your app js-->



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
    <div id="fdcg-common-success" class="view view-main tab active">

        <div class="pages">


            <div data-page="fdcg-common-success-page" class="page">



                <div class="page-content">

                    <div class="navbar">
                        <div class="navbar-inner navbar-ph">
                            <div class="center">德恒普惠 - 操作失败</div>
                        </div>
                    </div>
                    <div class="list-block">
                        <ul>
                            <li>
                                <div class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title"><%=returnObject.getMessage()%></div>
                                    </div>
                                </div>
                            </li>

                        </ul>

                    </div>
                    <div class="content-block">
                        <p class="buttons-row">
                            <%--<a href="#" class="button button-raised button-fill btn-card-delete">解绑</a>--%>
                            <a href="<%=Config.getWebPH()%>/index.jsp" class="button button-raised button-fill">返回</a>
                        </p>
                    </div>


                    <div class="content-block">
                        &nbsp;
                    </div>

                    <div class="content-block">
                        &nbsp;
                    </div>

                </div>
            </div>

        </div>

    </div>
    <!-- Second view (for second wrap)-->

</div>
<!-- Path to Framework7 Library JS-->
<script type="text/javascript" src="<%=Config.getWebPH()%>/include/js/pages.js?r=<%=NumberUtils.randomNumbers(5)%>"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/modern2/include/js/functions.js?r=<%=NumberUtils.randomNumbers(5)%>"></script>
</body>
</html>