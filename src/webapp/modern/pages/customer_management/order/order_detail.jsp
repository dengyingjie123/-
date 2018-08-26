<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%
    if (!Config.checkLoginedUniversal(request)) {
        response.sendRedirect(Config.getWebRoot() + "/modern/pages/login.jsp");
        return;
    }

    // 如果是游客，必须绑定客户信息
    if (Config.checkIsGuest(request) == 1) {
        CustomerPersonalPO guest = Config.getLoginCustomerInSession(request);
        response.sendRedirect(Config.getModernCustomerManagementPages() + "/customer/customer_bind_wechat.jsp?userInfoId=" + guest.getNationId());
        return;
    }
%>
<%
    String orderId = request.getParameter("orderId");
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
    <script type="application/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            var url = "<%=Config.getWebRoot()%>/modern/s/production/Order_getOrderById.action?orderId=<%=orderId%>";
            $http.post(url, "").success(function(response){
                $scope.order = response.returnValue;
            });

            $http.get('<%=Config.getWebRoot()%>/modern/s/production/Production_getProductionWVOById.action?productionId=<%=productionId%>').success(function(response){
                // alert(response.returnValue['production']['name']);
                $scope.production = response.returnValue['production'];
            });

            $scope.except = function(v) {
                return v == 0 ? "浮动" : v + "%";
            }
        });
    </script>
    <script type="text/javascript">
        function uploadFile() {
            var url = "<%=Config.getWebRoot()%>/modern/s/system/FileUpload_upload.action";

            fm.bindOnSubmitForm('formContractUpload', url, null, function(){
                fm.message('dd','upload done');
            },null);
        }


    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
<div class="easyui-navpanel">
    <div class="header-all">
        <span class="hd-news" onclick="javascript:history.go(-1);">
            <img src="<%=Config.getWebRoot()%>/modern/images/allow-fx.png">
        </span>
        <h3>订单详情</h3>
            <span class="hd-cfact-search">
                <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
            </span>
    </div>
    <section class="givemoney">

        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>基本信息</h3>
            <ul class="pro-info">
                <li>产品名称：<span class="data"> {{order.productionName}}</span></li>
                <li>发行规模：<span class="data"> {{production.size/10000}}万</span></li>
                <li>产品期限：<span class="data"> {{production.investTermView}}</span></li>
                <li>预期收益：<span class="data"> {{except(production.expectedYield)}}</span></li>
                <li>付息方式：<span class="data"> {{production.interestType}}</span></li>
                <%--<li>发行机构：<span class="data"> 德合基金</span></li>--%>
            </ul>
        </div>
        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>投资信息</h3>
            <ul class="pro-info">
                <li>投资客户：<span class="data"> {{order.customerName}}</span></li>
                <li>投资金额：<span class="data"> {{order.money}}</span></li>
                <li>年化收益：<span class="data"> {{order.expectedYield}}%</span></li>
                <li>预约时间：<span class="data"> {{order.appointmentTime}}</span></li>
                <li>打款时间：<span class="data"> {{order.payTime}}</span></li>
                <%--<li>打款银行：<span class="data"> </span></li>--%>
                <%--<li>银行卡号：<span class="data"> </span></li>--%>
            </ul>
        </div>
        <%--<div class="home-recommend-ct">--%>
            <%--<h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>投资信息</h3>--%>
            <%--<ul class="pro-info">--%>
                <%--<li>订单资料：<span class="data">--%>
                    <%--<input type="file" name="datafile" size="40"><input type="submit" value="Send">--%>
                <%--</span></li>--%>
                <%--<li>--%>

                    <%--<form id="formContractUpload" enctype="multipart/form-data" method="post">--%>

                        <%--<div class="fileUpload btn btn-primary">--%>
                            <%--<span id="idCardTips">身份证</span>--%>
                            <%--<input type="file" name="upload" id="upload" class="upload" />--%>
                            <%--<a href="javascript:uploadFile()" class="easyui-linkbutton" iconCls="icon-ok">提交</a>--%>
                        <%--</div>--%>
                    <%--</form>--%>

                <%--</li>--%>
                <%--<li>--%>

                    <%--<form id="formContractUpload" enctype="multipart/form-data" method="post">--%>

                        <%--<div class="fileUpload btn btn-primary">--%>
                            <%--<span id="idCardTips">身份证</span>--%>
                            <%--<input type="file" name="upload" id="upload" class="upload" />--%>
                            <%--<a id="btnFilesUpload" href="javascript:uploadFile()" class="easyui-linkbutton" iconCls="icon-ok">提交</a>--%>
                        <%--</div>--%>
                    <%--</form>--%>

                <%--</li>--%>
            <%--</ul>--%>
        <%--</div>--%>
    </section>
    <%--<div class="fr-ct">--%>
        <%--<div class="fr-rt">--%>
            <%--<button type="button" class="nowbuy">联系客服</button>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%=Config.getModernCustomerManagementFooter("00010")%>
</div>
</body>
</html>