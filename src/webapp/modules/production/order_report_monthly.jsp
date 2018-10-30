<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>销售月份</td>
                <td>
                    <input id="search_year<%=token%>" class="easyui-combobox" style="width:100px;" data-options="valueField:'id',textField:'text',url:'<%=Config.getWebRoot()%>/getConfig.jsp?k=system.date.year_json',editable:false"></input>
                    <input id="search_month<%=token%>" class="easyui-combobox" style="width:80px;" data-options="valueField:'id',textField:'text',url:'<%=Config.getWebRoot()%>/getConfig.jsp?k=system.date.month_json',editable:false"></input>
                </td>
                <td>
                <td>
                    <a id="btnSearchOrderReportMonthly<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
            </tr>
        </table>
    </div>

    <br>
    <table id="orderReportMonthlyTable<%=token%>"></table>
</div>
</body>
</html>