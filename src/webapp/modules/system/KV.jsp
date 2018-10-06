<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
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
                <td>K键</td>
                <td><input type="text" id="search_K<%=token %>" name="kv.K" style="width:100px;" /></td>
                <td>V键</td>
                <td><input type="text" id="search_V<%=token %>" name="kv.V" style="width:100px;" /></td>
                <td>Parameter</td>
                <td><input type="text" id="search_parameter<%=token %>" name="kv.parameter" style="width:100px;" /></td>
                <td id="search_GroupName_Text<%=token%>">组名</td>
                <td id="search_GroupName_Form<%=token%>"><input type="text" id="search_GroupName<%=token %>" name="kv.GroupName" style="width:100px;" /></td>
                <td>
                    <a id="btnSearchKV<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetKV<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="KVGuanLiTable<%=token%>"></table>

</div>
</body>
</html>