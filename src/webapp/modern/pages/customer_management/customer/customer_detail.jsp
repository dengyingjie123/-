<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%
    if (!Config.checkLoginedUniversal(request)) {
        response.sendRedirect(Config.getWebRoot() + "/modern/pages/login.jsp");
        return;
    }
%>
<%
    CustomerPersonalPO customer = Config.getLoginCustomerInSession(request);
    String customerId = Config.getLoginCustomerInSession(request).getId();
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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.service.js"></script>
    <script type="application/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            var urlCustomer = "<%=Config.getWebRoot()%>/modern/c/customer/CustomerPersonal_loadCustomerPersonalVOByCustomerPersonalId?customerId=<%=customerId%>";
            $http.get(urlCustomer).success(function(response){
                $scope.customer = response.returnValue;
            });
            $scope.selectCustomer = function(customerId) {
                fm.goto('<%=Config.getModernCustomerManagementPages()%>/customer/customer_add.jsp?customerId='+customerId+"&gotoUrl=<%=Config.getModernCustomerManagementPages()%>/customer/customer_detail.jsp");
            }
            $scope.showMobile = function(customerId){
                fm.goto('<%=Config.getModernCustomerManagementPages()%>/customer/customer_mobile.jsp?customerId='+customerId+"&gotoUrl=<%=Config.getModernCustomerManagementPages()%>/customer/customer_detail.jsp");
            }
            $scope.removeCustomer = function(customerId, customerName){
                fm.dialogWithYesNo('message', '警告', '是否删客户【'+customerName+'】？', function(){
                    // alert(customerId);
                    var url = "<%=Config.getWebRoot()%>/modern/s/customer/CustomerPersonal_removeCustomerPersonalOut?customerId="+customerId;
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
                fm.goto('<%=Config.getModernCustomerManagementPages()%>/order/order_list.jsp?customerId='+customerId+'&orderType='+orderType);
            }

            $scope.gotoExam = function(exam) {
                fm.goto('<%=Config.getModernCustomerManagementPages()%>/info/info_exam_home.jsp?answerStatus='+exam.status+'&score='+exam.score)
            }

            var examUrl = "<%=Config.getWebRoot()%>/modern/c/info/ExamQuestion_loadCurrentExamAnswerSessionPO?examAnswerSessionStatus=1&paperId=1";
            $http.get(examUrl).success(function(response){
                $scope.exam = response.returnValue;

                var score = response.returnValue['score'];
                var status = response.returnValue['status'];
                var description = "尚未测评";

                if (status != 0) {
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


                $scope.examDescription = description;
            });
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
                <%--<i class="remove-img" ng-click="removeCustomer(customer.id, customer.name)"></i>--%>
            </div>
            <div class="person-info">
                <img class="person-img" src="<%=Config.getWebRoot()%>/modern/images/person-img.png">
                <span class="name"><%=customer.getName()%></span>
            </div>
            <div class="management-data lbBox">
                <div class="pro-info-lt lineBlock">
                    <p class="ct">{{customer.totalPaymentPrincipalMoneyWithFormat}}</p>
                    <p class="text">在投本金</p>
                </div>
                <div class="pro-info-rt lineBlock">
                    <p class="ct">{{customer.totalProfitMoneyWithFormat}}</p>
                    <p class="text">预计到期收益</p>
                </div>
            </div>
        </div>
        <div class="home-recommend-ct">
            <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>客户详情</h3>
            <ul class="cutmer-data lbBox nth-childtwo pd25">
                <li ng-click="selectCustomer(customer.id)">
                    <i class="wait-ico1 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">基本资料</em>
                    <i class="icon-arrow"></i>
                </li>
                <li ng-click="showMobile(customer.id)">
                    <i class="wait-ico1 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">手机号</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">{{customer.mobile}}</span>
                </li>
                <li ng-click="">
                    <i class="wait-ico2 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">银行卡</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">{{customer.accountVOs[0]["bank"] + " " + customer.accountVOs[0]["number4Short"]}}</span>
                </li>
                <li onclick="fm.goto('<%=Config.getModernCustomerManagementPages()%>/customer/customer_password.jsp')">
                    <i class="wait-ico2 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">查询密码</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">点击修改</span>
                </li>
                <li ng-click="gotoExam(exam)">
                    <i class="wait-ico2 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">风险测评</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">{{examDescription}}</span>
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
                <li ng-click="showOrderList(customer.id, '1', customer.soldOrderCount)">
                    <i class="wait-ico5 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">已确认</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">{{customer.soldOrderCount}}</span>
                </li>
                <li>
                    <i class="wait-ico6 cstmer lineBlock"></i>
                    <em class="myitem-txt lineBlock">结束</em>
                    <i class="icon-arrow"></i>
                    <span class="info-count">0</span>
                </li>
            </ul>
        </div>
        <div class="home-recommend-ct">
            <%--<h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>操作</h3>--%>
            <div class="pro-hights pd25">
                <%--<img class="collect" src="images/collection-ico.png">--%>
                <div>
                    <button type="button" class="logout" onclick="fm.goto('<%=Config.getWebRoot()%>/modern/pages/logout.jsp');">退出登录</button>
                </div>
            </div>
        </div>
    </section>
    <%=Config.getModernCustomerManagementFooter("0010")%>
</div>
<div id="message"></div>
</body>
</html>