<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%
    if (Config.getLoginUserInSession(request) == null) {
        response.sendRedirect(Config.getModernSaleManagementPages() + "/system/login.jsp");
        return;
    }
%>
<%
    String customerId = request.getParameter("customerId");
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
    <script type="application/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            var urlCustomer = "<%=Config.getWebRoot()%>/modern/s/customer/CustomerPersonal_loadCustomerPersonalVOById?customerId=<%=customerId%>";
            $http.get(urlCustomer).success(function(response){
                $scope.customer = response.returnValue;
            });
            $scope.selectCustomer = function(customerId) {
                fm.goto('<%=Config.getModernSaleManagementPages()%>/customer/customer_add.jsp?customerId='+customerId+"&gotoUrl=<%=Config.getModernSaleManagementPages()%>/customer/customer_detail.jsp");
            }
            $scope.removeCustomer = function(customerId, customerName){
                fm.dialogWithYesNo('message', '警告', '是否删客户【'+customerName+'】？', function(){
                    // alert(customerId);
                    var url = "<%=Config.getWebRoot()%>/modern/s/customer/CustomerDistribution_removeByLoginUser?customerId="+customerId;
                    fm.post(url, null, function(){
                        fm.dialogClose('message');
                        fm.goto("<%=Config.getModernSaleManagementPages()%>/customer/customer_list.jsp");
                    }, null);
                });
            }


            $scope.showOrderList = function(customerId, orderType, count){
                if (count == 0) {
                    fm.message('提示', '没有数据');
                    return;
                }
                fm.goto('<%=Config.getModernSaleManagementPages()%>/order/order_list.jsp?customerId='+customerId+'&orderType='+orderType);
            }
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
<div class="easyui-navpanel">
    <section class="customer-details">
        <div class="custer-details">
            <div class="arrow-location">
                <p class="icon-arrow1-ht">
                    <i class="icon-arrow1" onclick="history.go(-1)"></i></p>
                <i class="remove-img" ng-click="removeCustomer(customer.id, customer.name)"></i>
            </div>
            <div class="person-info">
                <img class="person-img" src="<%=Config.getWebRoot()%>/modern/images/person-img.png">
                <span class="name">{{customer.name}}</span>
            </div>
            <div class="management-data lbBox">
                <div class="pro-info-lt lineBlock">
                    <p class="ct">0.00</p>
                    <p class="text">在投金额</p>
                </div>
                <div class="pro-info-rt lineBlock">
                    <p class="ct">0.00</p>
                    <p class="text">即将到期金额</p>
                </div>
            </div>
        </div>
        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>客户详情</h3>
            <ul class="cutmer-data lbBox nth-childtwo pd25">
                <li ng-click="selectCustomer(customer.id)">
                    <i class="wait-ico1 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">客户资料</em>
                    <i class="icon-arrow"></i>
                </li>
            </ul>
        </div>
        <div class="home-recommend-ct ">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>客户投资</h3>
            <ul class="cutmer-data lbBox pd25 ">
                <li ng-click="showOrderList(customer.id, '0', customer.appointmentOrderCount)">
                    <i class="wait-ico3 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">已预约</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">{{customer.appointmentOrderCount}}</span>
                </li>
                <li ng-click="showOrderList(customer.id, '1', customer.saledOrderCount)">
                    <i class="wait-ico5 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">已确认</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">{{customer.saledOrderCount}}</span>
                </li>
                <li>
                    <i class="wait-ico6 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">结束</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">0</span>
                </li>
            </ul>
        </div>

    </section>
    <%=Config.getModernSaleManagementFooter("00100")%>
</div>
<div id="message"></div>
</body>
</html>