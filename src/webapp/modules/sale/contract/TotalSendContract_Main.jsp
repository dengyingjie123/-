<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
 销售合同调配列表页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
    ButtonPO btnReceiveContract = new ButtonPO("btnReceiveContract" + token, "合同签收", "icon-add","总部销售合同调配_总部销售合同调配_签收");
    ButtonPO btnSetContractStatus= new ButtonPO("btnSetContractStatus" + token, "设置合同状态", "icon-edit");
    ButtonPO btnContractSend = new ButtonPO("btnContractSend" + token, "合同调配", "icon-edit");
    ButtonPO btnListContractRouteList= new ButtonPO("btnListContractRouteList" + token, "查看合同流转", "icon-edit","总部销售合同调配_总部销售合同调配_查看状态");
    ButtonPO btnUpdateDepartment= new ButtonPO("btnUpdateDepartment" + token, "修改财富中心", "icon-edit","总部销售合同调配_总部销售合同调配_修改财富中心");
    ButtonPO btnChangeContractNo = new ButtonPO("btnChangeContractNo" + token, "修改合同编号", "icon-edit","修改合同编号");
    ButtonPO btnChangeProduction = new ButtonPO("btnChangeProduction" + token, "修改合同对应产品", "icon-edit","修改合同对应产品");



//    ButtonPO btnMoveTotalMangerContract= new ButtonPO("btnMoveTotalMangerContract" + token, "移交总部修改财富中心", "icon-edit","总部销售合同调配_总部销售合同调配_移交总部修改财富中心");
    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnReceiveContract);
    toolbar.addButton(btnSetContractStatus);
    toolbar.addButton(btnContractSend);
    toolbar.addButton(btnListContractRouteList);
//    toolbar.addButton(btnMoveTotalMangerContract);
    toolbar.addButton(btnUpdateDepartment);

    toolbar.addButton(btnChangeContractNo);

    toolbar.addButton(btnChangeProduction);

%>
<html>
<head>
    <title>销售合同调配</title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>合同号</td>
                <td><input type="text" id="search_ContractNo<%=token %>" style="width:100px;"/></td>
                <td>产品名称</td>
                <td><input type="text" id="search_ProductionName<%=token %>" style="width:100px;"/></td>
                <td>持有人</td>
                <td><input type="text" id="search_RecieveUserName<%=token %>" style="width:100px;"/></td>
                <td>签约用户</td>
                <td><input type="text" id="search_CustomerName<%=token %>" style="width:100px;"/></td>
                <td>合同状态</td>
                <td><input type="text" class="easyui-combotree" id="search_Status<%=token %>" editable="false" style="width:100px;"/></td>
                <td><a id="btnSearchContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
                <td><a id="btnResetContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a></td>
            </tr>
        </table>
    </div>
    <br>
    <table id="SendContractTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>
