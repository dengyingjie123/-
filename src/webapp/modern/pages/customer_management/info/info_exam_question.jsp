<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%
    if (!Config.checkLoginedUniversal(request)) {
        response.sendRedirect(Config.getWebRoot() + "/modern/pages/login.jsp");
        return;
    }

    int questionNO = 1;

    if (StringUtils.isNumeric(request.getParameter("questionNO"))) {
        questionNO = Integer.parseInt(request.getParameter("questionNO"));
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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="application/javascript">
        var app = angular.module('myApp', []);

        var questionNO = <%=questionNO%>;
        var questionCount = 0;
        var paperId = 1;
        app.controller('myCtrl', function($scope, $http){
            var url = "<%=Config.getWebRoot()%>/modern/c/info/ExamQuestion_loadExamQuestionVOByQuestionNO?paperId="+paperId+"&questionNO="+questionNO;
            $http.post(url, null).success(function(response){
                $scope.question = response.returnValue;
                $scope.questionNO = response.returnValue['questionNO'];
                $scope.options = response.returnValue['examOptionPOs'];
                $scope.questionCount = response.returnValue['questionCount'];

                questionCount = response.returnValue['questionCount'];

                if (questionNO == questionCount) {
                    $('#btnNext').text("提交");
                }
            });

            $scope.select = function(questionId,optionId,questionCount) {
                var url = "<%=Config.getWebRoot()%>/modern/c/info/ExamAnswer_answer?paperId="+paperId+"&questionId="+questionId+"&optionId="+optionId;
                $http.post(url, null).success(function(response){
                    // JSON.stringify(response);

                    if (questionNO == questionCount) {

                        var finishUrl = "<%=Config.getWebRoot()%>/modern/c/info/ExamQuestion_finishExamQuestion?examAnswerSessionId=&paperId="+paperId;

                        $http.post(finishUrl, null).success(function(response){
                            if (response.returnValue == "1") {
                                fm.message('提示','谢谢！您已完成此测评!');

                                fm.goto("<%=Config.getModernCustomerManagementPages()%>/customer/customer_detail.jsp");
                            }
                       });
                        return;
                    }

                    if (response.returnValue == "1") {
                        fm.goto('<%=Config.getModernCustomerManagementPages()%>/info/info_exam_question.jsp?questionNO='+(questionNO+1));
                    }
                });
//                alert(questionId + "-" + optionId);
            }

            $scope.next = function(questionCount) {
                if (questionNO == questionCount) {
                    return;
                }
                fm.goto('<%=Config.getModernCustomerManagementPages()%>/info/info_exam_question.jsp?questionNO='+(questionNO+1));
            }

            $scope.pre = function(questionCount) {

                if (questionNO == 1) {
                    return;
                }

                fm.goto('<%=Config.getModernCustomerManagementPages()%>/info/info_exam_question.jsp?questionNO='+(questionNO-1));
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
            <h3 class="question">{{question.question}}
                <br>(第{{questionNO}}题，共{{questionCount}}题)
            </h3>
            <ul class="pro-info">
                <li ng-repeat="option in options" ng-click="select(question.id, option.id,questionCount)">{{option.name}} ： {{option.description}}</li>
                <%--<li>发行机构：<span class="data"> 德合基金</span></li>--%>
            </ul>
        </div>
        <div class="fr-rt">
            <button type="button" class="btn-exam-question-next" ng-click="pre(questionCount)">上一题</button>
            <button id="btnNext" type="button" class="btn-exam-question-next" ng-click="next(questionCount)">下一题</button>
        </div>
    </section>
    <%=Config.getModernCustomerManagementFooter("0010")%>
</div>
</body>
</html>