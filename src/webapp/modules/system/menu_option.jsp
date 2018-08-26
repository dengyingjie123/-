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

<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
        <form id="formMenuOption<%=token %>" name="formMenu" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">菜单名称</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="permission.menuName" editable="" tyle="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">菜单项名称</td>
                    <td><input class="easyui-validatebox" type="text" id="url<%=token %>" name="permission.permissionName" required="true" missingMessage="必须填写" style="width:200px" /></td>
                </tr>
                <input type="hidden" id="icon<%=token %>" name="permission.menuId"  style="width:200px" />
                <input type="hidden" id="icon<%=token %>" name="permission.id"  style="width:200px" />
            </table>
        </form>
        <div id="message<%=token%>"></div>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px 0;">
        <a id="btnSubmitMenuOption<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('menuOptionWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>