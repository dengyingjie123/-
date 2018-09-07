<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.TimeUtils" %>
<%
    if (Config.getLoginUserInSession(request) == null) {
        response.sendRedirect(Config.getModernSaleManagementPages() + "/system/login.jsp");
        return;
    }

    String month = request.getParameter("month");
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
        app.controller('myCtrl', function($scope, $http){
        });
    </script>
    <script type="text/javascript">

        $(document).ready(function(){
            initTableCustomerTable();
        });

        function initTableCustomerTable() {
            var strTableId = 'customerTableId';
            var url = "<%=Config.getWebRoot()%>/modern/s/report/SimpleReport_getReport.action?reportName=listReportPaymentPlan_Production_Month";
            url += "&titleConfigString=PaymentPlan_Production_Month";
            url += "&p=month%3d<%=month%>";

            $('#'+strTableId).text("正在查询，请稍等……");

            fm.post(url, null, function(data){
                var columns = fm.convert2Json(data['columnNames']);
                var data = fm.convert2Json(data['data']);

                // alert(JSON.stringify(data));

                $('#' + strTableId).datagrid({
                    // title: '客户列表',
                    url: '',
                    queryParams: {
                        // 此处可定义默认的查询条件
                    },
                    loadMsg: '数据正在加载，请稍后……',
                    rownumbers: true,
                    singleSelect: true,
                    columns:[columns],
                    onLoadSuccess: function () {
                    },
                    onClickRow:function(index, row){
                        fm.goto('<%=Config.getModernSaleManagementPages()%>/report/report_payment_plan_production_customer_month.jsp?month=<%=month%>&productionId='+row["ProductionId"]);
                    }
                });

                $('#'+strTableId).datagrid('loadData', data);
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
        <h3>报表数据</h3>
            <span class="hd-cfact-search">
                <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png">--%>
            </span>
    </div>

    <section class="pro-details">
        <table id="customerTableId" style="height: 550px"></table>
    </section>
    <%=Config.getModernSaleManagementFooter("00001")%>
</div>
</body>
</html>