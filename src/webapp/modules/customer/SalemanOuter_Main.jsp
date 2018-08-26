<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token");
%>
<%
    // 创建需要权限控制的按钮
    ButtonPO btnAdd = new ButtonPO("btnSalemanOuterAdd" + token, "添加", "icon-add", "系统管理-用户管理-新增");
    ButtonPO btnEdit = new ButtonPO("btnSalemanOuterEdit" + token, "修改", "icon-edit","系统管理-用户管理-修改");
    ButtonPO btnDel = new ButtonPO("btnSalemanOuterDelete" + token, "删除", "icon-cut","系统管理-用户管理-删除");
// 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
// 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnDel);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Untitled Document</title>
</head>

<body>
<div style="padding:5px;">
    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>名称</td>
                <td><input type="text" id="search_user_name<%=token %>" name="user.name" style="width:100px;" /></td>
                <td>电话</td>
                <td><input type="text" id="search_user_mobile<%=token %>" name="user.mobile" style="width:100px;" /></td>
                <td>身份号码</td>
                <td><input type="text" id="search_user_idnumber<%=token %>" name="user.idnumber" style="width:100px;" /></td>
                <td>
                    <a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br />
    <table id="SalemanOuterTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>