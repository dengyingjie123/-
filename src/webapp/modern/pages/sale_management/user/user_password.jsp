<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%
    if (Config.getLoginUserInSession(request) == null) {
        response.sendRedirect(Config.getModernSaleManagementPages() + "/system/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>产品列表</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/mobile.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot()%>/modern/css/public.css">
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="application/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            $scope.submit = function(){
                // $('#oldPasswordTips').removeClass("error-tips-ct-hidden").addClass("error-tips-ct");
                var oldPassword = fm.getFormValueByName("oldPassword");
                if (fm.checkIsTextEmpty(oldPassword)) {
                    $('#oldPasswordTips').removeClass("error-tips-ct-hidden").addClass("error-tips-ct");
                    return;
                }

                var password = fm.getFormValueByName("password");
                var password2 = fm.getFormValueByName("password2");
                if (fm.checkIsTextEmpty(password) || fm.checkIsTextEmpty(password2) || password != password2) {
                    $('#password2Tips').removeClass("error-tips-ct-hidden").addClass("error-tips-ct");
                    return;
                }

                var parameters = "oldPassword="+fm.getMD5(oldPassword)+"&password="+fm.getMD5(password);
                var url = "<%=Config.getWebRoot()%>/modern/s/system/User_changePassword.action?"+parameters;
//                alert(url);
                $http.post(url, null).success(function(response){

                    if (response.code != 100) {
                        fm.message("提示", response.message);
                        return;
                    }

                    fm.confirm("提示", response.returnValue, function(){
                        fm.goto("<%=Config.getModernSaleManagementPages()%>/user/user_info.jsp");
                    });

                });

            }

            $scope.oldPasswordClick = function() {
                $('#oldPasswordTips').removeClass("error-tips-ct").addClass("error-tips-ct-hidden");
            }

            $scope.passwordClick = function(){
                $('#password2Tips').removeClass("error-tips-ct").addClass("error-tips-ct-hidden");
            }


        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
<div class="easyui-navpanel">
    <div class="header-all">
        <span class="hd-news" onclick="javascript:history.go(-1);">
            <img src="<%=Config.getWebRoot()%>/modern/images/allow-fx.png">
        </span>
        <h3>密码管理</h3>
        <span class="hd-cfact-search">
            <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
        </span>
    </div>
    <section>
        <form>
        <ul class="bind-ph-num">
            <li>
                <div class="box-ct">
                    <div class="rgttel-ico"><img src="<%=Config.getWebRoot()%>/modern/images/loginpassword-ico.png"></div>
                    <input name="oldPassword" type="password" placeholder="请输入旧登录密码" class="input-box" ng-click="oldPasswordClick()" />
                </div>
                <div id="oldPasswordTips" class="error-tips-ct-hidden">
                    <i class="sjx"></i>
                    <div class="error-tips">
                        <p>密码或帐号错误，请重新输入</p>
                    </div>
                </div>
            </li>
            <li class="relative">
                <div class="box-ct">
                    <div class="rgttel-ico"><img src="<%=Config.getWebRoot()%>/modern/images/loginpassword-ico.png"></div>
                    <input name="password" type="password" placeholder="请输入新登录密码" class="input-box" ng-click="passwordClick()"/>

                </div>
                <div class="error-tips-ct-hidden">
                    <i class="sjx"></i>
                    <div class="error-tips">
                        <p>密码或帐号错误，请重新输入</p>
                    </div>
                </div>
            </li>
            <li class="relative">
                <div class="box-ct">
                    <div class="rgttel-ico"><img src="<%=Config.getWebRoot()%>/modern/images/loginpassword-ico.png"></div>
                    <input name="password2" type="password" placeholder="请再输入新登录密码" class="input-box" ng-click="passwordClick()" />
                </div>
                <div id="password2Tips" class="error-tips-ct-hidden">
                    <i class="sjx"></i>
                    <div class="error-tips">
                        <p>新密码输入有误，请重新输入</p>
                    </div>
                </div>
            </li>
        </ul>
        <a href="#" class="btn-sure mar-bom cd-popup-trigger" ng-click="submit()">确认</a>
        <%--<a href="#" class="btn-sure1 mar-bom cd-popup-trigger ">确认</a>--%>
        </form>
    </section>
    <%=Config.getModernSaleManagementFooter("00010")%>
</div>
</body>
</html>