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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script>
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            var url = "<%=Config.getWebRoot()%>/modern/s/production/Order_getOrderInfo.action?referralCode=<%=Config.getLoginUserInSession(request).getReferralCode()%>";
            var parameters = ""
            $http.post(url, parameters).success(function(response){
                $scope.appointmentOrderCount = response.returnValue['appointmentOrderCount'];
                $scope.saledOrderCount = response.returnValue['saledOrderCount'];
            });

            $scope.getOrder = function(orderType, count) {

                if (count == 0) {
                    fm.message("警告", "没有对应数据");
                    return;
                }

                var url = "<%=Config.getModernSaleManagementPages()%>/order/order_list.jsp?orderType="+orderType;
                fm.goto(url);
            }
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
<div class="easyui-navpanel">
    <section class="my-account">
        <div class="account-details">
            <div class="management-data lbBox">
                <div class="pro-info-lt lineBlock">
                    <p class="ct">0.00</p>
                    <p class="text">待兑付的佣金</p>
                </div>
                <div class="pro-info-rt lineBlock">
                    <p class="ct">0.00</p>
                    <p class="text">累计兑付佣金</p>
                </div>
            </div>
        </div>
        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>我的佣金</h3>
            <ul class="cutmer-data lbBox pd25">
                <li ng-click="getOrder('0', appointmentOrderCount)">
                    <i class="wait-ico3 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">已预约</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">{{appointmentOrderCount}}</span>
                </li>
                <li ng-click="getOrder('1', saledOrderCount)">
                    <i class="wait-ico4 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">已确认</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">{{saledOrderCount}}</span>
                </li>
                <li>
                    <i class="wait-ico5 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">待返佣</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">-</span>
                </li>
                <li>
                    <i class="wait-ico7 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">已返佣</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">-</span>
                </li>

            </ul>
        </div>
        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>我的帐户</h3>
            <ul class="cutmer-data lbBox pd25">
                <li onclick="fm.goto('user_info.jsp')">
                    <i class="wait-ico8 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">基本信息</em>
                    <i class="icon-arrow"></i>
                </li>
                <li>
                    <i class="wait-ico9 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">我的银行卡</em>
                    <i class="icon-arrow"></i>
                    <span class="info-binding">未绑定</span>
                </li>
                <%--<li>--%>
                    <%--<i class="wait-ico12 cstmer lineBlock"></i>--%>
                    <%--<em class="myitem-txt lineBlock">我的积分</em>--%>
                    <%--<i class="icon-arrow"></i>--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<i class="wait-ico13 cstmer lineBlock"></i>--%>
                    <%--<em class="myitem-txt lineBlock">我的收藏</em>--%>
                    <%--<i class="icon-arrow"></i>--%>
                    <%--<span class="info-count">2</span>--%>
                <%--</li>--%>
            </ul>
        </div>
        <div class="home-recommend-ct">
            <%--<h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>操作</h3>--%>
            <div class="pro-hights pd25">
                <%--<img class="collect" src="images/collection-ico.png">--%>
                <div>
                    <button type="button" class="logout" onclick="fm.goto('<%=Config.getModernSaleManagementPages()%>/system/system_logout.jsp');">退出登录</button>
                </div>
            </div>
        </div>
    </section>
    <%=Config.getModernSaleManagementFooter("00010")%>
</div>
</body>
</html>