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
<form id="formPermission<%=token%>" name="formPermission" action="" method="post">
<div id="updateTabs<%=token%>" class="easyui-tabs">
        <div title="权限明细" style="padding:10px;background:#fff;border:0px solid #ccc;">
            <div style="height: 263px" align="center">
                <table id="PermissionTable" border="0" cellspacing="5" cellpadding="0">
                </table>
            </div>
        </div>
</div>
</form>
<div region="south" border="false" style="text-align:right;padding:6px;background:#F4F4F4">
    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('PermissionWindow<%=token%>')">取消</a>
</div>
</body>
</html>

