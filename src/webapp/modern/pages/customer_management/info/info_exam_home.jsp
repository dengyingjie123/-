<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
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

    String answerStatus = request.getParameter("answerStatus");
    String score = request.getParameter("score");


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
        var questionCount = 0;
        var paperId = 1;
        app.controller('myCtrl', function($scope, $http){
            var answerStatus = "<%=answerStatus%>";
            var score = "<%=score%>";

            var description = "";

            if (answerStatus != 0) {
                if (score <= 20) {
                    description = "您属于：保守型";
                }
                else  if (score >= 21 && score < 40) {
                    description = "您属于：稳健型";
                }
                else  if (score >= 41 && score < 60) {
                    description = "您属于：平衡型";
                }
                else  if (score >= 61 && score < 80) {
                    description = "您属于：成长型";
                }
                else  if (score >= 91 && score <= 80) {
                    description = "您属于：进取";
                }
            }

            if (answerStatus == "0") {
                $scope.answerStatusString = "您尚未完成测试，请继续答题";
            }
            else {
                $scope.answerStatusString = "您已完成测试，得分为【"+score+"】,"+description+"，可以重新答题";
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
        <h3>风险测评</h3>
        <span class="hd-cfact-search">
            <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
        </span>
    </div>
    <section class="pro-details">
        <div class="home-recommend-ct">
            <h3 class="question">
                {{answerStatusString}}
            </h3>
            <ul class="pro-info">
                <li onclick="fm.goto('<%=Config.getModernCustomerManagementPages()%>/info/info_exam_question.jsp')">开始做题</li>
                <%--<li>不用做了，我是保守型</li>--%>
                <li style="color: darkred">本问卷旨在了解您对投资风险的承受意愿及能力。问卷结果可能不能完全呈现您面对投资风险的真正态度，您可和您的投资顾问或我们的客服进一步沟通。</li>
                <%--<li>发行机构：<span class="data"> 德合基金</span></li>--%>
            </ul>
        </div>
    </section>
    <%=Config.getModernCustomerManagementFooter("0010")%>
</div>
</body>
</html>