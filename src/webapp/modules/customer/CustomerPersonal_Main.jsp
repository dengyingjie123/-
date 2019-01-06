<%
/**
修改：李昕骏
时间：2015年8月18日 16:32:31
内容：
取消发送短信按钮
*/
%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token") ;

    // 创建需要权限控制的按钮
    ButtonPO btnQuickAdd = new ButtonPO("btnCustomerPersonalQuickAdd" + token, "快速添加", "icon-add","客户管理_个人客户管理_快速添加");
    ButtonPO btnAdd = new ButtonPO("btnCustomerPersonalAdd" + token, "添加", "icon-add","客户管理_个人客户管理_新建");
    ButtonPO btnEdit = new ButtonPO("btnCustomerPersonalEdit" + token, "修改", "icon-edit");
    ButtonPO btnDistribution = new ButtonPO("btnCustomerDistribution" + token, "客户分配管理", "icon-search", "客户管理_个人客户管理_客户分配管理");
    // ButtonPO btnSms = new ButtonPO("btnSmsSending"+token , "短信发送" , "icon-edit");
    // 创建不需要权限控制的按钮
    ButtonPO btnDelete = new ButtonPO("btnCustomerPersonalDelete" + token, "删除", "icon-cut", "客户管理_个人客户管理_删除");
    ButtonPO btnPassword = new ButtonPO("btnPassword" + token, "密码管理", "icon-search");
    ButtonPO btnAllinpayCircleQueryCashShare = new ButtonPO("btnAllinpayCircleQueryCashShare" + token, "通联资产查询", "icon-search");
//    ButtonPO btnExport = new ButtonPO("btnCustomerPersonalExport" + token, "导出excel", "icon-search");

    ButtonPO btnDial = new ButtonPO("btnCustomerPersonalDial" + token, "呼叫", "icon-search");// HOPEWEALTH-1276 拨打电话

    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnQuickAdd);
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnDelete);
    toolbar.addButton(btnDistribution);
    toolbar.addButton(btnPassword);
    toolbar.addButton(btnAllinpayCircleQueryCashShare);
//    toolbar.addButton(btnDial);// HOPEWEALTH-1276
//    toolbar.addButton(btnExport);
    // toolbar.addButton(btnSms);

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
                <td><input type="text"  id="search_Name<%=token %>" style="width:100px;" /></td>
                <td>移动电话</td>
                <td><input type="text" id="search_Mobile<%=token %>" style="width:80px;" /></td>
                <td>工作地址</td>
                <td><input type="text" id="search_WorkAddress<%=token %>" style="width:90px;" /></td>
                <td>用户名</td>
                <td><input type="text"  id="search_LoginName<%=token %>" style="width:100px;" /></td>
                <td>销售组</td>
                <td><input type="text"  id="search_GroupName<%=token %>" style="width:100px;" /></td>
                <td>销售</td>
                <td><input type="text"  id="search_SaleManName<%=token %>" style="width:100px;" /></td>
                <td>
                    <a id="btnSearchCustomerPersonal<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetCustomerPersonal<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="CustomerPersonalTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>

</div>
</body>
</html>