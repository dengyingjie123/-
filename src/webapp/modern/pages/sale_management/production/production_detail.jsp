<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%
    if (Config.getLoginUserInSession(request) == null) {
        response.sendRedirect(Config.getModernSaleManagementPages() + "/system/login.jsp");
        return;
    }
%>
<%
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
        // app.controller('myCtrl', function($scope, $http) {
        app.controller('myCtrl', function($scope, $http,$sce){
            $http.get('<%=Config.getWebRoot()%>/modern/s/production/Production_getProductionWVOById.action?productionId=<%=productionId%>').success(function(response){
                $scope.productionCommissions = response.returnValue['productionCommissionVOs'];
                $scope.productions = response.returnValue['productions'];
                $scope.productionArticles = response.returnValue['productionArticles'];
                // alert(response.returnValue['production']['name']);
                $scope.production = response.returnValue['production'];
            });

            $scope.renderHtml = function(html_code) {
                return $sce.trustAsHtml(html_code);
            };
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">

<div class="easyui-navpanel">
    <div class="header-all">
        <span class="hd-news" onclick="javascript:history.go(-1);">
            <img src="<%=Config.getWebRoot()%>/modern/images/allow-fx.png">
        </span>
        <h3>{{production.name}}</h3>
            <span class="hd-cfact-search">
                <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
            </span>
    </div>

    <section class="pro-details">
        <div class="head-dtl">
            <ul>
                <li>
                    <p>认购金额（万元）</p>
                    <p ng-repeat="pc in productionCommissions">{{pc.sizeStart/10000}}</p>
                </li>
                <li>
                    <p>预期收益率</p>
                    <p ng-repeat="pc in productionCommissions">{{pc.expectedYield==0?"浮动":pc.expectedYield+"%"}}</p>
                </li>
                <li>
                    <p>返佣费率</p>
                    <p ng-repeat="pc in productionCommissions">{{pc.commissionRate * 100}}%</p>
                </li>
            </ul>
        </div>
        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>基本信息</h3>
            <ul class="pro-info">
                <li>发行规模：<span class="data"> {{production.size / 10000}}万</span></li>
                <li>产品期限：<span class="data"> {{production.investTermView}}</span></li>
                <li>付息方式：<span class="data"> {{production.interestType}}</span></li>
                <li>预期年化收益：<span class="data"> {{production.expectedYieldDescription}}</span></li>
                <%--<li>发行机构：<span class="data"> 德合基金</span></li>--%>
            </ul>
        </div>
        <%--<div class="home-recommend-ct">--%>
            <%--<h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>产品描述</h3>--%>
            <%--<div class="pro-hights pd25">--%>
                <%--{{production.productionDescription}}--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="home-recommend-ct" ng-repeat="article in productionArticles">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>{{article.title}}</h3>
            <div class="pro-hights pd25" ng-bind-html="renderHtml(article.content)"></div>
        </div>
        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>立即营销</h3>
            <div class="pro-hights pd25">
                <%--<img class="collect" src="images/collection-ico.png">--%>
                <div class="fr-rt">
                    <button type="button" class="nowbuy" onclick="fm.goto('<%=Config.getWebRoot()%>/modern/pages/sale_management/order/appointment.jsp?productionId=<%=productionId%>');">立即预约</button>
                    <button type="button" class="share-btn" onclick="fm.goto('<%=Config.getModernSaleManagementPages()%>/production/production_detail_share.jsp?productionId=<%=productionId%>');">分享给客户</button>
                    <%--<span>已有<i class="fa64 size26">20</i>人预约，</span>--%>
                    <%--<span>已有<i class="fa64 size26">20</i>打款</span>--%>
                </div>
            </div>
        </div>
    </section>
    <%=Config.getModernSaleManagementFooter("01000")%>
</div>
</body>
</html>