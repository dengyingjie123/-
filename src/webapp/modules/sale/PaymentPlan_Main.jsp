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
                <td>开始兑付月份</td>
                <td>
                	<input id="Search_PaymentPlan_Year<%=token%>" class="easyui-combobox" style="width:100px;" data-options="valueField:'id',textField:'text',url:'<%=Config.getWebRoot()%>/getConfig.jsp?k=system.date.year_json',editable:false"></input>
                    <input id="Search_PaymentPlan_Month<%=token%>" class="easyui-combobox" style="width:80px;" data-options="valueField:'id',textField:'text',url:'<%=Config.getWebRoot()%>/getConfig.jsp?k=system.date.month_json',editable:false"></input>
                </td>
                <td>
                <td>
                    <a id="btnSearchPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="PaymentPlanGuanLiTable2<%=token%>"></table>

</div>
</body>
</html>