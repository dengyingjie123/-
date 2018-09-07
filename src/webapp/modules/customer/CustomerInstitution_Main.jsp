
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token") ;
    // 创建权限控制的按钮
    ButtonPO btnAdd = new ButtonPO("btnCustomerInstitutionAdd" + token, "添加", "icon-add");
    ButtonPO btnEdit = new ButtonPO("btnCustomerInstitutionEdit" + token, "修改", "icon-edit");
    ButtonPO btnDelete= new ButtonPO("btnCustomerInstitutionDelete" + token, "删除", "icon-cut");
    ButtonPO btnDistribution = new ButtonPO("btnInstitutionDistribution" + token, "客户分配", "icon-search");
    ButtonPO btnPassword = new ButtonPO("btnPassword" + token, "密码管理", "icon-search");
    ButtonPO btnDial = new ButtonPO("btnCustomerInstitutionDial" + token, "呼叫", "icon-search");// HOPEWEALTH-1276 拨打电话

    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnDelete);
    toolbar.addButton(btnDistribution);
    toolbar.addButton(btnPassword);
//    toolbar.addButton(btnDial);

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
                <td>姓名</td>
                <td><input type="text"  id="search_Name<%=token %>" style="width:150px;" /></td>
                <td>移动电话</td>
                <td><input type="text" id="search_Mobile<%=token %>" style="width:80px;" /></td>
                <td>注册地址</td>
                <td><input type="text" id="search_Address<%=token %>" style="width:90px;" /></td>
                <td>
                    <a id="btnSearchCustomerInstitution<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetCustomerInstitution<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="CustomerInstitutionTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>