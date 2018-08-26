<%--
  Created by IntelliJ IDEA.
  User: 张舜清
  Date: 2015年9月7日15:21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token") ;
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
                <td>日期</td>
                <td><input type="text" class="easyui-datebox" id="search_CustomerDailyPaymentReport_Time<%=token %>" style="width:100px;" /></td>
                <td>
                    <a id="btnCustomerDailyPaymentReportSearch<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnCustomerDailyPaymentReportReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="CustomerDailyPaymentReportTable<%=token%>"></table>

</div>
</body>
</html>