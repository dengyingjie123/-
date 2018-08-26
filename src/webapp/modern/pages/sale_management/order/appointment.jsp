<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%
    if (Config.getLoginUserInSession(request) == null) {
        response.sendRedirect(Config.getModernSaleManagementPages() + "/system/login.jsp");
        return;
    }
%>
<%
    UserPO loginUser = Config.getLoginUserInSession(request);
    String productionId = request.getParameter("productionId");
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
            var url = "<%=Config.getWebRoot()%>/modern/s/production/Production_getProductionById.action?productionId=<%=productionId%>";
            $http.get(url).success(function(response){
                $scope.production = response.returnValue;
            });


            var urlCustomer = "<%=Config.getWebRoot()%>/modern/s/customer/CustomerPersonal_loadCustomerPersonalVOById?customerId=<%=customerId%>";
            $http.get(urlCustomer).success(function(response){
                $scope.customer = response.returnValue;
            });

            $scope.appointment = function() {
                var parameters = $("form").serialize();
//                var d = $("form").serializeArray();
//                alert(JSON.stringify(d));
//                var customerPersonalOutName = $("input[name='customerPersonalOut.name']").val();
                var customerPersonalName = fm.getFormValueByName('personal.name');
                if (fm.checkIsTextEmpty(customerPersonalName)) {
                    fm.message("警告", "请选择客户");
                    return;
                }
//                alert(customerPersonalOutName);
                // alert(parameters);
                var url = "<%=Config.getWebRoot()%>/modern/s/production/Order_appointmentOrder4Modern.action";
                fm.post(url, parameters, function(data){
                    // alert(JSON.stringify(data));
                    fm.goto('<%=Config.getModernSaleManagementPages()%>/user/user_home.jsp');
                }, null);
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
        <h3>产品预约</h3>
            <span class="hd-cfact-search">
                <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
            </span>
    </div>
    <form>
    <section class="bigamount-reserve">
        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>产品名称</h3>
            <ul class="pro-info">
                <li>产品名称：<span class="data color-6e1">{{production.name}}</span></li>
                <li>预计年化收益率：<span class="data color-fa6">{{production.expectedYield == 0 ? '浮动' : production.expectedYield + '%'}}</span></li>
            </ul>
        </div>
        <div class="home-recommend-ct" onclick="fm.goto('<%=Config.getModernSaleManagementPages()%>/customer/customer_selection.jsp?userId=<%=loginUser.getId()%>&productionId=<%=productionId%>&gotoUrl=<%=Config.getModernSaleManagementPages()%>/customer/customer_selection.jsp')">
            <h3 class="tit"><i class="sjx-ico1"></i>
                <i class="triangle-right"></i>
                客户信息
                <i class="icon-arrow"></i>
            </h3>
            <ul class="pro-info">
                <li>客户资料：<span class="data">{{customer.name}}</span></li>
                <li>身份证号：<span class="data">{{customer.idCard}}</span></li>
                <li>手机号码：<span class="data">{{customer.mobile}}</span></li>
            </ul>
        </div>
        <div class="home-recommend-ct ">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>认购金额</h3>
            <div class="subbuy-ipt pd25-all clearfix">
                <input class="subbuy-money" placeholder="请输入认购金额（单位：元）" type="number" pattern="[0-9]*" inputmode="numeric" name="money" value="">
            </div>
        </div>
        <a href="#" class="btn-sure mar-bom cd-popup-trigger" ng-click="appointment()">提交</a>
    </section>
        <input type="hidden" name="personal.id" value="{{customer.id}}">
        <input type="hidden" name="personal.name" value="{{customer.name}}">
        <input type="hidden" name="personal.idCard" value="{{customer.idCard}}">
        <input type="hidden" name="personal.mobile" value="{{customer.mobile}}">
        <input type="hidden" name="productionId" value="{{production.id}}">
    </form>
    <%=Config.getModernSaleManagementFooter("01000")%>
</div>
</body>
</html>