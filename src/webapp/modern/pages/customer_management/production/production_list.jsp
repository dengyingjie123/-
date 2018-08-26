<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%
    if (!Config.checkLoginedUniversal(request)) {
        response.sendRedirect(Config.getWebRoot() + "/modern/pages/login.jsp");
        return;
    }
%>
<%
    if (!Config.isCustomerAgreement(request)) {
        response.sendRedirect(Config.getModernCustomerManagementPages() + "/customer/customer_investor_confirm.jsp");
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
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot()%>/modern/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot()%>/modern/css/idangerous.swiper.css">
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="text/javascript">
        window.onload = function() {
            var mySwiper1 = new Swiper(
                    '#header-lst', {
                        freeMode: true,
                        slidesPerView: '3'
                    })
            var tabsSwiper = new Swiper('#tabs-container', {
                speed: 500,
                onSlideChangeStart: function() {
                    $(".pro-tab .active").removeClass('active')
                    $(".pro-tab a").eq(tabsSwiper.activeIndex).addClass('active')
                }
            })
            $(".pro-tab a").on('mousedown', function(e) {

                $(".pro-tab .active").removeClass('active')
                $(this).addClass('active')
                tabsSwiper.slideTo($(this).index())
            })
            $(".pro-tab a").click(function(e) {
                e.preventDefault()
            })
        }
    </script>
    <script type="text/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http) {
            var url = "<%=Config.getWebRoot()%>/modern/c/production/Production_listProductionVO4modernGroupByIncomeType.action";
            $http.get(url).success(function(response) {
                if (response.code == 100) {
                    $scope.type1 = response.returnValue['type1'];
                    $scope.type2 = response.returnValue['type2'];
                    $scope.type3 = response.returnValue['type3'];
                    $scope.productions1 = response.returnValue['productions1'];
                    $scope.productions2 = response.returnValue['productions2'];
                    $scope.productions3 = response.returnValue['productions3'];

                    $scope.clickProduction = function(id){
                        fm.goto('<%=Config.getModernCustomerManagementPages()%>/production/production_detail.jsp?productionId=' + id);
                    }
                }
            });
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
<div class="easyui-navpanel">
    <section class="productList">
        <div id="header-lst">
            <div class="pro-tab swiper-wrapper head-lsts">
                <a class="head-lst-ct swiper-slide active" href="#">
                    <i class="wap-nav1"></i>
                    <span class="cfff">{{type1.v}}</span>
                </a>

                <a class="head-lst-ct swiper-slide" href="#">
                    <i class="wap-nav2"></i>
                    <span class="cfff">{{type2.v}}</span>
                </a>

                <a class="head-lst-ct swiper-slide" href="#">
                    <i class="wap-nav3"></i>
                    <span class="cfff">{{type3.v}}</span>
                </a>
            </div>
        </div>
        <div class="tab-ct">
            <div id="tabs-container" class="swiper-container">
                <div class="swiper-wrapper">

                    <div class="home-recommend-ct swiper-slide">
                        <div class="home-recommend-ct content-slide" ng-click="clickProduction(p.id)" ng-repeat="p in productions1">
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
                                </ul>
                                <div class="w-progressBar">
                                    <p class="w-progressBar-wrap"> <span class="w-progressBar-bar" ng-style="{'width':  (p.saleMoneyPercent * 100) +'%'}"></span> </p>
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

                    <div class="home-recommend-ct swiper-slide">
                        <div class="home-recommend-ct content-slide" ng-click="clickProduction(p.id)" ng-repeat="p in productions2">
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
                                </ul>
                                <div class="w-progressBar">
                                    <p class="w-progressBar-wrap"> <span class="w-progressBar-bar" ng-style="{'width':  (p.saleMoneyPercent * 100) +'%'}"></span> </p>
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

                    <div class="home-recommend-ct swiper-slide">
                        <div class="home-recommend-ct content-slide" ng-click="clickProduction(p.id)" ng-repeat="p in productions3">
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
                                </ul>
                                <div class="w-progressBar">
                                    <p class="w-progressBar-wrap"> <span class="w-progressBar-bar" style="width:0%;"></span> </p>
                                    <div class="w-raise">
                                        <span class="w-raise-ct">正在募集</span>
                                        <%--<span class="w-raise-ct">已募集<i>20%</i></span>--%>
                                        <span class="w-raise-te">募集结束时间：1</span>
                                        <%--<span class="w-raise-te">募集结束时间：<i>2</i>天<i>04</i>小时<i>05</i>分<i>38</i>秒</span>--%>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </section>
    <%=Config.getModernCustomerManagementFooter("01000")%>
</div>
</body>
</html>