<%--
  Created by IntelliJ IDEA.
  User: yux
  Date: 2016/6/16
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>

<%
    String token = request.getParameter("token");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><%=Config.APP_NAME%></title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/default.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/buttons.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.print.css" media='print'>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/third-party/accounting.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyloader.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/system/ConfigClass.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/boot.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/treeplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/validator.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/highcharts4/js/highcharts.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/lib/moment.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/lang/zh-cn.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/callcenter/callcenter.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/sale/CapitalPreClass.js"></script>

    <%--呼叫中心--%>
    <link href="<%=Config.getWebRoot()%>/include/framework/7moor/edb_bar/css/pages.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/framework/7moor/edb_bar/js/icallcenter/global.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/framework/7moor/edb_bar/hojo/hojo.js" djConfig="isDebug:false, parseOnLoad:false"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <script type="text/javascript">
        $(document).ready(function () {
            var capitalPreClass = new CapitalPreClass("<%=token%>");
            capitalPreClass.initModuleForJsp("<%=startTime%>","<%=endTime%>");
        });
    </script>

</head>

<body>
<form id="formBusinessTripApplication<%=token %>" name="formBusinessTripApplication" action="" method="post">
    <table border="0" cellspacing="10" cellpadding="0">
        <tr>
            <td align="right" style="font-size: 22px">资金准备时间段：</td>
            <td><input type="text" id="capitalPreTimes<%=token %>" name="paymentPlanMoneyPrepareVO.capitalPreTimes"
                       style="width:600px;font-size: 22px"/></td>
        </tr>
        <tr>
            <td align="right" style="font-size: 22px">总兑付金额：</td>

            <td><input type="text" id="money<%=token %>" name="paymentPlanMoneyPrepareVO.money"
                       style="width:600px;font-size: 22px"/></td>
        </tr>
        <tr>
            <td align="right" style="font-size: 22px">资金准备明细：</td>
            <td><table id="CapitalPreDetailTable<%=token%>" style="font-size: 22px;"></table></td>
        </tr>

    </table>

</form>
</body>
</html>
