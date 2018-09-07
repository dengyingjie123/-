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
<div class="easyui-layout" fit="true">
	<div region="center" border="false" style="padding:10px;background:#fff">
    <table border="0" cellpadding="3" cellspacing="0">
        <tr>
            <td>姓名</td>
            <td><input type="text" id="search_Name<%=token %>" style="width:150px;" /></td>
            <td>电话</td>
            <td><input type="text" id="search_Mobile<%=token %>" style="width:150px;" /></td>
            <td>
                <a id="btnSearchCustomer<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
            </td>
            <td>
                <a id="btnResetCustomer<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
            </td>
        </tr>
    </table>
    <br/>
    <table id="CustomerTable<%=token%>"></table>
    </div>
	<div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelectedCustomer<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >选择</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerSelectWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>