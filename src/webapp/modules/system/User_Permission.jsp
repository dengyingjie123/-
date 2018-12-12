<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<html>
<head>
</head>
<body>
    <div class="easyui-panel" title="查询" iconCls="icon-search" >
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>权限名</td>
                <td><input type="text" id="search_permission_name<%=token %>" name="menu.permissionName" style="width:100px;" /></td>
                <td>
                    <a id="btnSearchPermissionSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchPermissionReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
        <table id="PermissionTable<%=token%>" name="PermissionTable" border="0" cellspacing="5" cellpadding="0">
        </table>
    </div>
<div region="south" border="false" style="text-align:right;padding:6px;background:#F4F4F4">
    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('permissionWindow<%=token%>')">取消</a>
</div>
</body>
</html>

