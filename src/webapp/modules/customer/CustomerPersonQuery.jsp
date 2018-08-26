<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/9/16
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
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

<div class="easyui-panel" title="查询" iconCls="icon-search">
    <table border="0" cellpadding="3" cellspacing="0">
        <tr>
            <td>用户名</td>
            <td><input type="text"  id="search_LoginName<%=token %>" style="width:150px;" /></td>
            <td>移动电话</td>
            <td><input type="text" id="search_Mobile<%=token %>" style="width:80px;" /></td>
            <td>工作地址</td>
            <td><input type="text" id="search_WorkAddress<%=token %>" style="width:90px;" /></td>
            <td>
                <a id="btnSearchCustomerPersonal<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
            </td>
            <td>
                <a id="btnResetCustomerPersonal<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
            </td>
        </tr>
    </table>
</div>
<div class="easyui-layout" fit="true">
    <table id="CustomerPersonalQueryTable<%=token%>"></table>

</div>
</body>
</html>