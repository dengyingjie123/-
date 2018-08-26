<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.*" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%
    if (!Config.checkLoginedUniversal(request)) {
        response.sendRedirect(Config.getWebRoot() + "/modern/pages/login.jsp");
        return;
    }
%>
<%
    String orderType = HttpUtils.getParameter(request, "orderType");
    String customerId = HttpUtils.getParameter(request, "customerId");

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
    <script>
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            var url = "<%=Config.getWebRoot()%>/modern/s/production/Order_getOrders4Modern.action?orderType=<%=orderType%>&customerId=<%=customerId%>";
            $http.post(url, "").success(function(response){
                $scope.orders = response.returnValue;
            });

            $scope.showOrderDetails = function(orderId, productionId) {
                fm.goto('<%=Config.getModernSaleManagementPages()%>/order/order_detail.jsp?orderId='+orderId+'&productionId='+productionId);
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
        <h3>订单列表</h3>
            <span class="hd-cfact-search">
                <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
            </span>
    </div>
    <section class="confirm-info">
        <div class="home-recommend-ct" ng-click="showOrderDetails(o.id, o.productionId)" ng-repeat="o in orders">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>{{o.productionName}}</h3>
            <div class="pro-infos lbBox pd25-all">
                <div class="lineBlock pro-info-lt pro-proportion">
                    <p>投资金额<span class="data"> {{o.money}}.00</span></p>
                    <%--<p>返佣金额<span class="data"> 0.00</span></p>--%>
                    <p>投资用户<span class="data"> {{o.customerName}}</span></p>
                    <p>预约时间<span class="data"> {{o.appointmentTime}}</span></p>
                    <p>打款时间<span class="data"> {{o.payTime}}</span></p>
                </div>
                <div class="lineBlock pro-info-rt pro-proportion">
                    <p>预期收益<span class="data"> {{o.expectedYield}}%</span></p>
                    <%--<p>返佣比例<span class="data"> 0.20%</span></p>--%>
                </div>
            </div>
        </div>
    </section>
    <%=Config.getModernSaleManagementFooter("00010")%>
</div>
</body>
</html>