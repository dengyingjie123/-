<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/14/14
  Time: 10:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token") ;
    // 创建权限控制的按钮
    ButtonPO btnAdd = new ButtonPO("btnProjectAdd" + token, "添加", "icon-add");
    ButtonPO btnEdit = new ButtonPO("btnProjectEdit" + token, "修改", "icon-edit");
    ButtonPO btnDelete= new ButtonPO("btnProjectDelete" + token, "删除", "icon-cut");

    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnDelete);
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
                <td>名称</td>
                <td><input type="text"  id="search_Name<%=token %>" style="width:150px;" /></td>
                <td>状态</td>
                <td><input type="text" class="easyui-combotree" id="search_Status<%=token %>" style="width:80px;" /></td>
                <td>类型</td>
                <td><input type="text" class="easyui-combotree" id="search_Type<%=token %>" style="width:90px;" /></td>
                <td>
                    <a id="btnSearchProject<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetProject<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ProjectTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>