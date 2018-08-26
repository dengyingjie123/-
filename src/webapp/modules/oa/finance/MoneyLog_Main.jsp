<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token") ;
%>
<%
    // 创建需要权限控制的按钮
    ButtonPO btnAdd = new ButtonPO("btnMoneyLogAdd" + token, "添加", "icon-add", "财务管理-记账管理-新增");
    ButtonPO btnEdit = new ButtonPO("btnMoneyLogEdit" + token, "修改", "icon-edit","财务管理-记账管理-修改");
    ButtonPO btnDel = new ButtonPO("btnMoneyLogDelete" + token, "删除", "icon-cut","财务管理-记账管理-删除");
// 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
// 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnDel);
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
                <td>部门</td>
                <td><input type="text" class="easyui-combotree" id="search_Department<%=token %>" style="width:150px;" editable="false" /></td>
                <td>收支</td>
                <td><input type="text" class="easyui-combotree" id="search_InOrOut<%=token %>" style="width:80px;" editable="false" /></td>
                <td>账务开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_MoneyTime_Start<%=token %>" style="width:90px;" editable="false" /></td>
                <td>账务结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_MoneyTime_End<%=token %>" style="width:90px;" editable="false" /></td>
                <td>
                    <a id="btnSearchMoneyLog<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetMoneyLog<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="MoneyLogGuanLiTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>