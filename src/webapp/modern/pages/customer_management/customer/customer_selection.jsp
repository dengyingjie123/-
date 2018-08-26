<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%
    String userId = Config.getLoginUserInSession(request).getId();
    String productionId = request.getParameter("productionId");
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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="text/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http) {
            var url = "<%=Config.getWebRoot()%>/modern/s/customer/CustomerPersonal_getCustomerPersonalOutVOs?userId=<%=userId%>";
            $http.get(url).success(function(response) {
                if (response.code == 100) {
                    $scope.customers = response.returnValue;
                }
            });

            $scope.selectCustomer = function(customerId) {
                // alert(customerId);
                fm.goto("<%=Config.getModernSaleManagementPages()%>/order/appointment.jsp?productionId=<%=productionId%>&customerId="+customerId);
            }

            $scope.addCustomer = function() {
                fm.goto('<%=Config.getModernSaleManagementPages()%>/customer/customer_add.jsp?productionId=<%=productionId%>&gotoUrl=<%=Config.getModernSaleManagementPages()%>/customer/customer_selection.jsp');
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
        <h3>选择客户</h3>
        <span class="hd-cfact-search" ng-click="addCustomer();"><img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png"></span>
    </div>
    <section class="my-customer">
        <ul>
            <li class="lbBox cstmer-info" ng-repeat="c in customers" ng-click="selectCustomer(c.id)">
                <dl class="pd25-all">
                    <dt class="lineBlock customer-img"><img src="<%=Config.getWebRoot()%>/modern/images/my-customer-img.png"></dt>
                    <dd class="lineBlock cstmer-all">
                        <div class="cstmer-1 lbBox">
                            <span class="lineBlock name">{{c.name}}</span>
                            <span class="lineBlock phone">{{c.mobile}}</span>
                        </div>
                        <div class="remarks">{{c.remark}}</div>
                    </dd>
                </dl>
            </li>
        </ul>
    </section>
</div>
</body>
</html>