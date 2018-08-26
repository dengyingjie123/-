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

            $scope.goto = function(m){
                alert(m);
            }
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
<div class="easyui-navpanel">
    <div class="header-all">
        <span class="hd-news">
            <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
        </span>
        <h3>德合产品精选</h3>
        <span class="hd-cfact-search">
            <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
        </span>
    </div>

    <div class="home-content">

        <div class="home-recommend-ct" ng-click="clickProduction(p.id)" ng-repeat="p in productions">
            <div class="investment-ct">
                <p class="{{p.status==1?'state statewait':'state stateing'}}"><span class="stateing-text state-sale">{{p.statusName}}</span></p>
                <h3 class="tit-1">{{p.name}}</h3>
                <ul class="regulations-info">
                    <li>
                        <span class="cfs-ge">{{p.minSizeStart/10000}}万</span>
                        <p class="cfs-tet">起投金额</p>
                    </li>
                    <li>
                        <span class="cfs-ge">{{p.investTermView}}</span>
                        <p class="cfs-tet">投资期限</p>
                    </li>
                    <li>
                        <span class="cfs-ge">{{p.maxExpectedYield == 0 ? '浮动' : p.maxExpectedYield + '%'}}</span>
                        <p class="cfs-tet">预期收益</p>
                    </li>
                    <li>
                        <span class="cfs-ge co-fa">{{p.maxCommissionRate * 100}}%</span>
                        <p class="cfs-tet">最高返佣</p>
                    </li>
                </ul>
                <div class="w-progressBar">
                    <p class="w-progressBar-wrap"><span class="w-progressBar-bar" ng-style="{'width':  (p.saleMoneyPercent * 100) +'%'}"></span></p>
                    <div class="w-raise">
                        <span class="w-raise-ct">募集进度: <i>{{p.saleMoneyPercent * 100}}%</i></span>
                        <%--<span class="w-raise-ct">已募集<i>20%</i></span>--%>
                        <span class="w-raise-te">募集结束时间：<i>{{p.remainDays > 0 ? p.remainDays : 0}}</i>&nbsp;天</span>
                        <%--<span class="w-raise-te">募集结束时间：<i>2</i>天<i>04</i>小时<i>05</i>分<i>38</i>秒</span>--%>
                    </div>
                </div>
            </div>
        </div>

    </div>


    <%=Config.getModernSaleManagementFooter("10000")%>
</div>
</body>
</html>