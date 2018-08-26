<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%
    if (Config.getLoginUserInSession(request) == null) {
        response.sendRedirect(Config.getWebRoot() + "/modern/pages/login.jsp");
        return;
    }
%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">  
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>德合在线</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/mobile.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/color.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot()%>/modern/css/public.css">
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="text/javascript">
        function login() {
            var messageId = 'loginMessage';
            var userName = $('#userName').val();
            var password = $('#password').val();

            if (fm.checkIsTextEmpty(userName)) {
                fm.message('警告', '请输入正确的用户名');
                return;
            }

            if (fm.checkIsTextEmpty(password)) {
                fm.message('警告', '请输入正确的密码');
                return;
            }

            var parameters = {'user.name' : userName, 'user.password' : fm.getMD5(password)}
            var url = "<%=Config.getWebRoot()%>/modern/s/system/dologin";
            fm.post(url, parameters, function(data) {
//                var s = JSON.stringify(data);
//                alert(s);
                fm.goto('<%=Config.getWebRoot()%>/modern/pages/sale_management/production/home.jsp');
            }, null);
        }
    </script>
    <script>
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            $scope.look = function(){
                fm.goto("<%=Config.getModernSaleManagementPages()%>/production/home.jsp");
            }
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
    <div>
        <div class="rgt-hd">
            <img alt="logo" src="<%=Config.getWebRoot()%>/modern/images/logo.png">
        </div>
        <div style="padding:0 10px">
            <div>
                <input id="userName" name="user.ame" class="easyui-textbox" data-options="prompt:'手机号或员工号',iconCls:'icon-man'" style="width:100%;height:38px" type="number" pattern="[0-9]*" inputmode="numeric" value="">
            </div>
            <div>
                <input id="password" name="user.password" class="easyui-passwordbox" data-options="prompt:'密码'" style="width:100%;height:38px" type="letter" value="">
            </div>
            <div style="text-align:center;">
                <a href="javascript:login();" class="easyui-linkbutton c1" style="width:68%;height:35px;font-size: .5rem "><span style="font-size:16px" >登录</span></a>
                <a href="#" class="easyui-linkbutton c5" plain="true" outline="true" style="width:28%;height:35px;font-size:.5rem" onclick="fm.goto('register.jsp');"><span style="font-size:16px">注册</span></a>
            </div>
            <div class="login-lookaround" ng-click="look()">我先随便看看</div>
        </div>
    </div>


    <div id="modern-common-area"></div>
    <div id="loginMessage"></div>
</body>    
</html>