<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
异常销售合同列表页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
    ButtonPO btnConfirmContract = new ButtonPO("btnConfirmContract" + token, "状态确认并移交管理员", "icon-add", "异常销售合同_异常销售合同_确认移交");
    ButtonPO btnListContractRouteList = new ButtonPO("btnListContractRouteList" + token, "查看合同流转", "icon-edit", "异常销售合同_异常销售合同_查看状态");
    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnConfirmContract);
    toolbar.addButton(btnListContractRouteList);
%>
<html>
<head>
    <title>异常销售合同</title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>合同号</td>
                <td><input type="text" id="search_ContractNo<%=token %>" style="width:180px;"/></td>
                <td>产品名称</td>
                <td><input type="text" id="search_ProductionName<%=token %>" style="width:180px;"/></td>
                <td>签约客户</td>
                <td><input type="text" id="search_CustomerName<%=token %>" style="width:180px;"/></td>
                <td>合同状态</td>
                <td><input type="text" class="easyui-combotree" id="search_Status<%=token %>" editable="false"
                           style="width:180px;"/></td>

            </tr>
        </table>
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>签约开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SigendTime_Start<%=token %>"
                           editable="false"
                           style="width:180px;"/></td>
                <td>签约结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SigendTime_End<%=token %>"
                           editable="false"
                           style="width:180px;"/></td>
                <td>流转状态</td>
                <td><input type="text" class="easyui-combotree" id="search_RouteActionType<%=token %>" editable="false"
                           style="width:180px;"/></td>
                <td>
                    <a id="btnSearchUnsaulContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetUnsaulContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="UnsaulContractTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar =
        <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>
