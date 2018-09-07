<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="text/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http) {
            var url = "<%=Config.getWebRoot()%>/modern/s/production/Production_listProductionVO4modern.action";
            $http.get(url).success(function(response) {
                if (response.code == 100) {
                    $scope.productions = response.returnValue;
                    $scope.clickProduction = function(id){
                        fm.goto('<%=Config.getWebRoot()%>/modern/pages/sale_management/production/production_detail.jsp?productionId=' + id);
                    }
                }
            });
        });
    </script>
</head>
<body>
<div class="easyui-navpanel" ng-app="myApp" ng-controller="myCtrl">
    <section class="aboutus-ct">
        <div class="aboutus-bg">
            <img class="aboutus-bg-ico" src="<%=Config.getWebRoot()%>/modern/images/aboutus-bg.jpg"> </div>
        <div class="aboutus-items">
            <div class="company-info">
                <div class="items clearfix">
                    <%--<span class="aboutusbg-ico1 ico"></span>--%>
                    <div class="text">感谢访问德合在线，如有疑问请拨打热线。</div>
                </div>
            </div>
            <div class="company-info">
                <div class="items clearfix">
                    <span class="aboutusbg-ico4 ico"></span>
                    <div class="text">深圳热线：0755-82573406</div>
                </div>
            </div>
            <div class="company-info">
                <div class="items clearfix">
                    <span class="aboutusbg-ico4 ico"></span>
                    <div class="text">昆明热线：0871-63582368</div>
                </div>
            </div>
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
    <p class="company-name">深圳开普乐科技有限公司技术支持</p>
</div>
</body>
</html>