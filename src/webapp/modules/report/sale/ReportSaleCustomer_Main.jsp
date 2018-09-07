<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Untitled Document</title>
</head>

<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>产品名称</td>
                <td><input type="text" class="easyui-validatebox" id="Search_ReportSaleCustomer_ProductionName<%=token %>" style="width:100px;" /></td>
                <td>客户名称</td>
                <td><input type="text" class="easyui-validatebox" id="Search_ReportSaleCustomer_CustomerName<%=token %>" style="width:100px;" /></td>
                <td>日期</td>
                <td><input type="text" class="easyui-datebox" id="Search_ReportSaleCustomer_Time<%=token %>" style="width:100px;" /></td>
                <td>
                    <a id="btnReportSaleCustomerSearch<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnReportSaleCustomerSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ReportSaleCustomerTable<%=token%>"></table>
</div>
</body>
</html>