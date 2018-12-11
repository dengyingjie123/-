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
                <td>起始日期</td>
                <td><input type="text" class="easyui-datebox" id="Search_Start_Time<%=token %>" style="width:100px;" /></td>
                <td>结束日期</td>
                <td><input type="text" class="easyui-datebox" id="Search_End_Time<%=token %>" style="   width:100px;" /></td>
                <td>产品名</td>
                <td><input type="text"  id="Search_Production_Name<%=token %>" style="width:100px;" /></td>
                <td>
                    <a id="btnSearch<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="specificPaymentTable<%=token%>"></table>
</div>
</body>
</html>