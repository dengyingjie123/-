<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
    import="com.youngbook.common.config.*" %>
<%
String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body>
<div style="padding:10px; ">
    <table border="0" cellspacing="5" cellpadding="0">
        <tr>
            <td>
                菜单管理
            </td>
        </tr>
        <tr>
            <td valign="top">
                <div id="MenuPanel" class="easyui-panel" style="width:300px;height:300px">
                    <ul id="systemMenu<%=token%>" class="easyui-tree" ></ul>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <a id="btnMenuAdd<%=token %>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-add">添加</a>
                <a id="btnMenuEdit<%=token%>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
                <a id="btnMenuDelete<%=token%>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-cut">删除</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>