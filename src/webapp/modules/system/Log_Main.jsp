<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" import="com.youngbook.common.config.*" %>
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
                <td>操作人</td>
                <td><input type="text" id="search_logPOOperatorName<%=token %>" name="logPO.operatorName" style="width:100px;" /></td>
                <td>名称</td>
                <td><input type="text" id="search_logPOName<%=token %>" name="logPO.name" style="width:100px;" /></td>
                <td>People Message</td>
                <td><input type="text" id="search_peopleMessage<%=token %>" name="logPO.peopleMessage" style="width:100px;" /></td>
                <td>Machine Message</td>
                <td><input type="text" id="search_machineMessage<%=token %>" name="logPO.machineMessage" style="width:100px;" /></td>
                <td>URL</td>
                <td><input type="text" id="search_url<%=token %>" name="logPO.url" style="width:100px;" /></td>
                <td>
                    <a id="btnSearchLog<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchLogReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="LogTable<%=token%>"></table>
</div>
</body>
</html>