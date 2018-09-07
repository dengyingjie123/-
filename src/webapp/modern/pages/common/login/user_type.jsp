<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/mobiscroll.custom-2.17.1.min.js" ></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="application/javascript">
        $(function () {

            $('#demo').mobiscroll().calendar({
                theme: 'mobiscroll',
                display: 'bottom',
                dateFormat: 'yyyy-mm-dd',
                lang:'zh'
            });

            $('#show').click(function () {
                $('#demo').mobiscroll('show');
                return false;
            });

            $('#clear').click(function () {
                $('#demo').mobiscroll('clear');
                return false;
            });

// $(".selbox-ze").on("touchend touchcancle",function(){
//      $(".selbox-ze").removeClass("sel-box1").addClass("sel-box2");
//      $(this).removeClass("sel-box2").addClass("sel-box1");
//  });
            $("#isman").click(function(){

                $("#iswoman").removeClass("sel-box2").addClass("sel-box1");
                $("#isman").removeClass("sel-box1").addClass("sel-box2");
            });
            $("#iswoman").click(function(){
                $("#isman").removeClass("sel-box2").addClass("sel-box1");
                $("#iswoman").removeClass("sel-box1").addClass("sel-box2");
            });
        });

    </script>
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

            var userType = $("#isman").hasClass('sel-box2') ? "customer" : "user";

            var parameters = {'loginName' : userName, 'password' : fm.getMD5(password), 'userType' : userType}
            var url = "<%=Config.getWebRoot()%>/modern/common/system/loginUniversal";
            fm.post(url, parameters, function(data) {
//                var s = JSON.stringify(data);
//                alert(s);
                if (data['userType'] == 'customer') {
                    fm.goto('<%=Config.getModernCustomerManagementPages()%>/production/home.jsp');
                }
                else {
                    fm.goto('<%=Config.getModernSaleManagementPages()%>/production/home.jsp');
                }
            }, null);
        }
    </script>
    <script>
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            $scope.look = function(){
                fm.goto("<%=Config.getModernSaleManagementPages()%>/production/home.jsp");
            }
            $scope.showInfo = function(){
                fm.goto('<%=Config.getWebRoot()%>/modern/pages/common/info/info_home.jsp');
            }
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
    <div>
        <div class="rgt-hd">
            <img alt="logo" src="<%=Config.getWebRoot()%>/modern/images/logo.png">
            <h3 class="register-name">德合在线</h3>
        </div>
        <section class="customer-details">
            <div class="home-recommend-ct">
                <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>我是投资者</h3>
                <ul class="cutmer-data lbBox nth-childtwo pd25">
                    <li onclick="fm.goto('<%=Config.getWebRoot()%>/modern/pages/common/login/customer_login_mobile_code.jsp')">
                        <i class="wait-ico1 cstmer lineBlock"></i>
                        <em class="myitem-txt lineBlock">短信登录</em>
                        <i class="icon-arrow"></i>
                    </li>
                    <li onclick="fm.goto('<%=Config.getWebRoot()%>/modern/pages/common/login/change_password_mobile.jsp')">
                        <i class="wait-ico1 cstmer lineBlock"></i>
                        <em class="myitem-txt lineBlock">手机号找回密码</em>
                        <i class="icon-arrow"></i>
                    </li>
                    <li ng-click="showInfo()">
                        <i class="wait-ico1 cstmer lineBlock"></i>
                        <em class="myitem-txt lineBlock">忘记手机号</em>
                        <i class="icon-arrow"></i>
                        <span class="info-count"></span>
                    </li>
                </ul>
            </div>
            <div class="home-recommend-ct">
                <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>我是理财经理</h3>
                <ul class="cutmer-data lbBox nth-childtwo pd25">
                    <li ng-click="showInfo()">
                        <i class="wait-ico1 cstmer lineBlock"></i>
                        <em class="myitem-txt lineBlock">手机号找回密码</em>
                        <i class="icon-arrow"></i>
                    </li>
                    <li ng-click="showInfo()">
                        <i class="wait-ico1 cstmer lineBlock"></i>
                        <em class="myitem-txt lineBlock">忘记手机号</em>
                        <i class="icon-arrow"></i>
                        <span class="info-count"></span>
                    </li>
                </ul>
            </div>
            <div class="home-recommend-ct">
                <%--<h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>操作</h3>--%>
                <div class="pro-hights pd25">
                    <%--<img class="collect" src="images/collection-ico.png">--%>
                    <div>
                        <button type="button" class="logout" onclick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </section>
    </div>


    <div id="modern-common-area"></div>
    <div id="loginMessage"></div>
</body>    
</html>