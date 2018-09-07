<%--
  Created by IntelliJ IDEA.
  User: Jayden
  Date: 14-4-29
  Time: 上午11:25
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
<div style="padding:10px; ">
    <table border="0" cellspacing="5" cellpadding="0">
        <tr>
            <td>
                部门组织管理
            </td>
            <td>部门岗位信息</td>
            <td>用户及权限</td>
        </tr>
        <tr>
            <td valign="top">
                <div id="departmentOptionPanel" class="easyui-panel" style="width:200px;height:380px">
                    <ul id="departmentOption<%=token%>" class="easyui-tree"></ul>
                </div>
            </td>
            <td valign="top">
            	<div id="positionPanel" class="easyui-panel" style="width:200px;height:380px">
                    <ul id="positionTree<%=token%>" class="easyui-tree"></ul>
                </div>
            </td>
            <td valign="top">
            	<div id="userAndPermissionPanel" class="easyui-tabs" style="width:450px;height:380px">
                    <div id="user<%=token%>" title="用户" style="padding:2px;">
                    	<table id="userTable<%=token%>"></table>
                    </div>
                    <div id="permission<%=token%>" title="权限" style="padding:2px;;">
                    	<ul id="permissionTree<%=token%>" class="easyui-tree"></ul>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <a id="departmentAdd<%=token %>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-add">添加</a>
                <a id="departmentEdit<%=token%>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
                <a id="departmentDelete<%=token%>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-cut">删除</a>
            </td>
            <td>
            	<a id="btnPositionAdd<%=token %>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-add">添加</a>
                <a id="btnPositionEdit<%=token%>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
                <a id="btnPositionDelete<%=token%>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-cut">删除</a>
            </td>
            <td>
            <a id="btnPermission<%=token %>" href="javascript:void(0)" plain="true" class="easyui-linkbutton" iconCls="icon-ok">添加</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>