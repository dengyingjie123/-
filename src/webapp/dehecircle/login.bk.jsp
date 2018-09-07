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
    <title>德合汇</title>
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
<!-- Status bar overlay for fullscreen mode-->
<div class="statusbar-overlay"></div>
<!-- Panels overlay-->
<div class="panel-overlay"></div>
<!-- Views, and they are tabs-->
<!-- We need to set "toolbar-through" class on it to keep space for our tab bar-->
<div class="views tabs toolbar-through">
    <!-- Your first view, it is also a .tab and should have "active" class to make it visible by default-->
    <div id="view-1" class="view view-main tab active">

        <div class="pages navbar-through">
            <!-- Page, data-page contains page name-->
            <div data-page="login-screen-embedded" class="page">
                <div class="page-content login-screen-content">

                    <div style="margin: 0 auto; width:100%; text-align: center"><img src="<%=Config.getWebDehecircle()%>/include/img/login_logo.png" style="width: 102px;height:125px;" /></div>

                    <form>
                        <div class="list-block inputs-list">
                            <ul>
                                <li>
                                    <div class="item-content">
                                        <div class="item-media"><i class="icon material-icons">phone_iphone</i></div>
                                        <div class="item-inner">
                                            <div class="item-input">
                                                <input id="txt-login-mobile" name="mobile" type="number" pattern="\d*" placeholder="手机号" value="13888939712"/>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="item-content">
                                        <div class="item-media"><i class="icon material-icons">lock_outline</i></div>
                                        <div class="item-inner">
                                            <div class="item-input">
                                                <input id="txt-login-password" type="password" name="password" placeholder="密码" value="123456y"/>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>

                        <div class="content-block"><a id="btn-login-password" href="#" class="button button-fill color-01 button-raised">登录</a></div>
                        <div>
                            <div class="row">
                                <div class="col-30">
                                    <%--<a href="javascript:window.location='<%=Config.getWebPH()%>/login/register.jsp'">免费注册</a>--%>
                                    &nbsp;
                                </div>
                                <div class="col-40"><a href="javascript:window.location='<%=Config.getWebDehecircle()%>/login/login_mobile_code.jsp'">新老用户短信登录</a></div>
                            </div>
                        </div>
                        <div class="content-block">
                            &nbsp;
                        </div>
                        <div class="content-block">
                            &nbsp;
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Second view (for second wrap)-->


</div>
<!-- Path to Framework7 Library JS-->
<script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/framework/framework7-1.6.5/js/framework7.js"></script>
<!-- Path to your app js-->
<script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
<script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/pages.js"></script>
<script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/my-app.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
</body>
</html>