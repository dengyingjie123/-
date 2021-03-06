<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String version = request.getParameter("version");
    System.out.println("version: " + version);
%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>My App</title>
    <!-- Path to Framework7 Library CSS-->
    <!--<link rel="stylesheet" href="include/framework/framework7-1.6.5/css/framework7.material.min.css">-->
    <script type="text/javascript" src="<%=Config.getWebRootCircle()%>/include/js/config.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <link rel="stylesheet" href="<%=Config.getWebRootCircle()%>/include/framework/framework7-1.6.5/css/framework7.material.css">
    <link rel="stylesheet" href="<%=Config.getWebRootCircle()%>/include/framework/framework7-1.6.5/css/framework7.material.colors.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="<%=Config.getWebRootCircle()%>/include/css/my-app.css">
    <!--<link rel="stylesheet/less" type="text/css" href="include/less/my-app.less">-->
    <link rel="stylesheet" href="<%=Config.getWebRootCircle()%>/include/css/material-icons.css">
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
            <div data-page="login-register" class="page">
                <div class="page-content login-screen-content">

                    <div style="margin: 0 auto; width:100%; text-align: center"><img src="<%=Config.getWebRootCircle()%>/include/img/login_logo.png" style="width: 100px;height:100px;" /></div>

                    <form id="login-register-form">
                        <div class="list-block inputs-list">
                            <ul>
                                <li>
                                    <div class="item-content">
                                        <div class="item-media"><i class="icon material-icons">person</i></div>
                                        <div class="item-inner">
                                            <div class="item-input">
                                                <input name="name" type="text" placeholder="请输入姓名" value=""/>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="item-content">
                                        <div class="item-media"><i class="icon material-icons">phone_iphone</i></div>
                                        <div class="item-inner">
                                            <div class="item-input">
                                                <input name="mobile" type="text" placeholder="请输入手机号" value=""/>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="item-content">
                                        <div class="item-media"><i class="icon material-icons">sms</i></div>
                                        <div class="item-inner">
                                            <div class="item-input">
                                                <input name="mobileCode" type="text" placeholder="请输入短信验证码" value=""/>
                                            </div>
                                        </div>
                                        <div class="item-inner">
                                            <div class="item-input">
                                                <a id="login-register-get-code" href="#" class="button">获取验证码</a>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="item-content">
                                        <div class="item-media"><i class="icon material-icons">lock_outline</i></div>
                                        <div class="item-inner">
                                            <div class="item-input">
                                                <input type="password" name="password" placeholder="请输入密码" value=""/>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="item-content">
                                        <div class="item-media"><i class="icon material-icons">account_circle</i></div>
                                        <div class="item-inner">
                                            <div class="item-input">
                                                <input id="txt-login-referralCode" type="text" name="referralCode" placeholder="请输入推荐码（选填）"/>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div class="content-block"><a id="btn-login-register" href="#" class="button button-fill button-raised">注册</a></div>
                        <div class="content-block">
                            <div class="list-block-label">
                                <div class="row">
                                    <div class="col-30"></div>
                                    <div class="col-50">已有账号？
                                        <a href="javascript:window.location='<%=Config.getWebRootCircle()%>/login.jsp'">登录</a>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <input name="checkCode" type="hidden" />
                        <input id="_s" name="_s" type="hidden" value="2" />
                    </form>
                </div>
            </div>
        </div>

    </div>
    <!-- Second view (for second wrap)-->

</div>
<!-- Path to Framework7 Library JS-->
<script type="text/javascript" src="<%=Config.getWebRootCircle()%>/include/framework/framework7-1.6.5/js/framework7.min.js"></script>
<!-- Path to your app js-->
<script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
<script type="text/javascript" src="<%=Config.getWebRootCircle()%>/include/js/pages.js"></script>
<script type="text/javascript" src="<%=Config.getWebRootCircle()%>/include/js/my-app.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/modern2/include/js/functions.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
<script type="text/javascript">
    onClickGetMobileCode('login-register-get-code','login-register-form');
</script>
</body>
</html>