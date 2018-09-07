<%--
  Created by IntelliJ IDEA.
  User: Jepson
  Date: 2015/6/25
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token");
%>

<%
    // 创建需要权限控制的按钮
    ButtonPO btnAdd = new ButtonPO("btnMessageSubscriptionAdd" + token, "添加", "icon-add", "系统管理-消息订阅-新增");
    ButtonPO btnEdit = new ButtonPO("btnMessageSubscriptionEdit" + token, "修改", "icon-edit","系统管理-消息订阅-修改");
    ButtonPO btnDel = new ButtonPO("btnMessageSubscriptionDelete" + token, "删除", "icon-cut","系统管理-消息订阅-删除");
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
                <td align="right">用户编号</td>
                <td><input  type="text" class="easyui-validatebox" id="search_userId<%=token %>" name="messageSubscription.userId"       style="width:200px"/></td>

                <td align="right">消息类型</td>
                <td><input  class="easyui-combotree"  type="text" id="search_messageTypeId<%=token %>" name="messageSubscription.messageTypeId"     style="width:200px"/></td>

                <td align="right">邮件提醒</td>
                <td><input  type="text" id="search_isEmail<%=token %>" name="messageSubscription.isEmail" class="easyui-combotree"      style="width:200px"/></td>
            </tr>
            <tr>
                <td align="right">短信提醒</td>
                <td><input  type="text" id="search_isSms<%=token %>" name="messageSubscription.isSms"   class="easyui-combotree"    style="width:200px"/></td>

                <td align="right">系统代办提醒</td>
                <td><input  type="text" id="search_isTodoList<%=token %>" name="messageSubscription.isTodoList"  class="easyui-combotree"     style="width:200px"/></td>

                <td colspan="2">
                    <a id="btnMessageSubscriptionSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>

                    <a id="btnMessageSubscriptionSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="MessageSubscriptionTable<%=token%>"   data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>

</div>
</body>
</html>