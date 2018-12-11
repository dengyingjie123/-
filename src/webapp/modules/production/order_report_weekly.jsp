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
                <td>周报日期</td>
                <td>
                    <input id="searchDate<%=token%>" name="searchDate" type="text" class="easyui-datebox" />
                </td>
                <td>
                    <a id="btnSearch<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="orderReportWeeklyTable<%=token%>"></table>

</div>
</body>
</html>