<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/16/14
  Time: 11:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token") ;
    String root=Config.getWebRoot();

    ButtonPO btnCustomerInfo = new ButtonPO("btnCustomerInfo" + token, "客户信息", "icon-search");
    ButtonPO btnPaymentInfo = new ButtonPO("btnPaymentInfo" + token, "兑付信息", "icon-search");
    ButtonPO btnOrderInfo= new ButtonPO("btnOrderInfo" + token, "订单信息", "icon-search");
    ButtonPO btnCustomerMoneyLogInfo= new ButtonPO("btnCustomerMoneyLogInfo" + token, "客户资金信息", "icon-search");

    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnCustomerInfo);
    toolbar.addButton(btnPaymentInfo);
    toolbar.addButton(btnOrderInfo);
    toolbar.addButton(btnCustomerMoneyLogInfo);
%>
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-layout" fit="true" style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>所属产品</td>
                <td><input type="text" class="easyui-combotree" id="search_ProductionName<%=token %>" style="width:150px;" /></td>
                <td>名称</td>
                <td><input type="text"  id="search_Name<%=token %>" style="width:150px;" /></td>

                <td>状态</td>
                <td><input type="text"  id="search_Status<%=token %>" class="easyui-combotree"  data-options="url:'<%=root %>/modules/production/Production_Status.jsp',method:'get'" style="width:150px;" /></td>
                <td>
                    <a id="btnSearchProduction<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetProduction<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ProductionTable<%=token%>" data-options="toolbar:toolbar"></table>

    <div id="ProductionSelectArea<%=token%>" region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelect<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProductionWindow<%=token%>')">取消</a>
    </div>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>