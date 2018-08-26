<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
 空白销售合同列表页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
    ButtonPO btnDistributeContract = new ButtonPO("btnDistributeContract" + token, "合同分配", "icon-add","空白销售合同_空白销售合同_合同分配");
    ButtonPO btnSendContract = new ButtonPO("btnSendContract" + token, "移交调配管理员", "icon-edit","空白销售合同_空白销售合同_移交管理");
    ButtonPO btnListContractRouteList= new ButtonPO("btnListContractRouteList" + token, "查看合同流转", "icon-edit","空白销售合同_空白销售合同_查看状态");
    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnDistributeContract);
    toolbar.addButton(btnSendContract);
    toolbar.addButton(btnListContractRouteList);


%>
<html>
<head>
    <title>空白销售合同</title>
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
                <td>
                    <a id="btnSearchBlankContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetBlankContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>签收开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SigTime_Start<%=token %>"
                           editable="false"
                           style="width:180px;"/></td>
                <td>签收结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SigTime_End<%=token %>"
                           editable="false"
                           style="width:180px;"/></td>

            </tr>
        </table>
    </div>
    <br>
    <table id="BlankContractTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>
