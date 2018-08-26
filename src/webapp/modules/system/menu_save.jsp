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
        <form id="formMenu<%=token %>" name="formMenu" action="" method="post">
            <table align="center" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">菜单编号</td>
                    <td><input name="menu.id" type="text" id="menu.id<%=token %>" readonly="readonly" style="width:270px"/></td>
                </tr>
                <tr>
                    <td align="right">菜单名称</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="menu.name" required="true" missingMessage="必须填写"  style="width:270px"/></td>
                </tr>
                <tr>
                    <td align="right">菜单地址</td>
                    <td><input class="easyui-validatebox" type="text" id="url<%=token %>" name="menu.url" required="true" missingMessage="必须填写" style="width:270px" /></td>
                </tr>
                <tr>
                    <td align="right">权限名称</td>
                    <td><input class="easyui-validatebox" type="text" id="permissionName<%=token %>" name="menu.permissionName" required="true" missingMessage="必须填写"  style="width:270px"/></td>
                </tr>
                <tr>
                    <td align="right">菜单类别</td>
                    <td><input class="easyui-validatebox" type="text" id="type<%=token %>" name="menu.type" required="true" missingMessage="必须填写"  style="width:130px"/>
                    &nbsp;&nbsp;说明：0 - 权限 1 - 菜单</td>
                </tr>
                <tr>
                    <td align="right">父级编号</td>
                    <td><input class="easyui-combotree" id="parentId<%=token %>" name="menu.parentId" data-options="required:true" style="width:270px" /></td>
                </tr>
                <tr>
                    <td align="right">菜单图标</td>
                    <td><input class="easyui-combotree" id="icon<%=token %>" name="menu.icon"  style="width:270px" /></td>
                </tr>
                <tr>
                    <td align="right">菜单排序</td>
                    <td><input name="menu.orders" type="text" id="orders<%=token %>2"  style="width:270px" /></td>
                </tr>
            </table>
        </form>
        <div id="message<%=token%>"></div>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px 0;">
        <a id="submit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('winMenu<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>